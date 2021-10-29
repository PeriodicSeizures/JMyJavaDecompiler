package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.pool.*;

import java.util.HashMap;

public abstract class IObfuscate {

    private final JavaClass containedClass;

    public static HashMap<String, JavaClass> classes = new HashMap<>();

    public IObfuscate(JavaClass containedClass) {
        this.containedClass = containedClass;
    }

    public final JavaClass getContainedClass() {
        return containedClass;
    }

    /**
     * Useful methods to get fields methods and classes globally from jar
     */
    protected final JavaField getGlobalJavaField(ConstantFieldref ref) {
        String pointed = ref.getPointingClass().get();
        // search for field downwards through subclasses

        JavaField javaField;
        JavaClass javaClass;
        do {
            javaClass = getGlobalJavaClass(pointed);
            if (javaClass == null)
                return null;

            pointed = getContainedClass().getDecompiledClass().getSuperClassName();
            if (pointed == null)
                throw new RuntimeException("Could not find field");

        } while ((javaField = javaClass.getInternalJavaField(ref)) == null);

        return javaField;
    }

    protected final JavaField getGlobalJavaField(int constantPoolFieldRefIndex) {
        ConstantFieldref fieldref = (ConstantFieldref) getContainedClass().getDecompiledClass().getEntry(constantPoolFieldRefIndex);
        return getGlobalJavaField(fieldref);
    }

    protected final JavaMethod getGlobalJavaMethod(IMethodRef ref) {
        String pointed = ref.getPointingClass().get();

        JavaClass javaClass;
        JavaMethod javaMethod;
        do {
            javaClass = getGlobalJavaClass(pointed);
            if (javaClass == null)
                return null;

            pointed = getContainedClass().getDecompiledClass().getSuperClassName();
            if (pointed == null)
                throw new RuntimeException("Could not find method");

        } while ((javaMethod = javaClass.getInternalJavaMethod(ref)) == null);

        return javaMethod;
    }

    protected final JavaMethod getGlobalJavaMethod(int constantPoolMethodRefIndex) {
        IMethodRef methodRef = (IMethodRef) getContainedClass().getDecompiledClass().getEntry(constantPoolMethodRefIndex);
        return getGlobalJavaMethod(methodRef);
    }

    protected final JavaClass getGlobalJavaClass(String packageAndName) {
        if (packageAndName.equals("java/lang/Object"))
            return null;

        JavaClass javaClass = classes.get(packageAndName);
        if (javaClass == null)
            throw new RuntimeException("Class " + packageAndName + " was not found");

        return javaClass;
    }

    /**
     * Deobfuscation phase step methods
     */
    public abstract void validationPhase();

    public abstract void linkingPhase();
}
