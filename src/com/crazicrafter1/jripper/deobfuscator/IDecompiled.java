package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.constants.*;

import java.util.HashMap;

public abstract class IDecompiled {

    public static HashMap<String, DecompiledJavaClass> classes = new HashMap<>();

    private final DecompiledJavaClass containedClass;

    public IDecompiled(DecompiledJavaClass containedClass) {
        this.containedClass = containedClass;
    }

    public final DecompiledJavaClass getContainedClass() {
        return containedClass;
    }

    /**
     * Useful methods to get fields methods and classes globally from jar
     *  - Notes:
     *      Will start at the class it is declared at, and go up the chain to the base class
     *      If is at Object class, return null
     */
    protected final JavaField getGlobalField(ConstantFieldref ref) {
        String pointed = ref.getPointingClass().get();
        // search for field downwards through subclasses

        JavaField javaField;
        DecompiledJavaClass theClazz;
        do {
            theClazz = getGlobalClass(pointed);
            if (theClazz == null)
                return null;

            //theClazz.getSuperClass()

            pointed = theClazz.getDecompiledClass().getSuperBinaryName();
            if (!pointed.startsWith("java"))
                throw new RuntimeException("Could not find field");

        } while ((javaField = theClazz.getInternalField(ref)) == null);

        return javaField;
    }

    protected final JavaField getGlobalField(int constantPoolFieldRefIndex) {
        ConstantFieldref fieldref = (ConstantFieldref) getContainedClass().getDecompiledClass().getEntry(constantPoolFieldRefIndex);
        return getGlobalField(fieldref);
    }

    protected final JavaMethod getGlobalJavaMethod(IMethodRef ref) {
        //String pointed = ref.getPointingClass().get();

        String clazzName = ref.getPointingClass().get();

        DecompiledJavaClass javaClass; // = getGlobalJavaClass(ref.getPointingClass().get());
        JavaMethod javaMethod;
        do {
            javaClass = getGlobalClass(clazzName);

            // If at Object class
            if (javaClass == null)
                return null;

            clazzName = javaClass.getDecompiledClass().getSuperBinaryName();

        } while ((javaMethod = javaClass.getInternalMethod(ref)) == null);

        return javaMethod;
    }

    protected final JavaMethod getGlobalJavaMethod(int constantPoolMethodRefIndex) {
        IMethodRef methodRef = (IMethodRef) getContainedClass().getDecompiledClass().getEntry(constantPoolMethodRefIndex);
        return getGlobalJavaMethod(methodRef);
    }

    /**
     * Get a locally loaded class
     * @param name the binary name of the class
     * @return the class, null if it does not exist
     */
    protected final DecompiledJavaClass getGlobalClass(String name) {
        return classes.get(name);
    }

    /**
     * Decompilation phase step methods
     */
    public abstract void validationPhase();

    public abstract void linkingPhase();
}
