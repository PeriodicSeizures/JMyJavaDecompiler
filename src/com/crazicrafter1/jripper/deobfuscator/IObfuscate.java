package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;
import com.crazicrafter1.jripper.decompiler.pool.IMethodRef;

import java.util.HashMap;

public abstract class IObfuscate {

    private JavaClass containedClass;

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
    protected final JavaField getJavaField(ConstantFieldref ref) {
        return classes.get(ref.getPointingClass().get()).getInternalJavaField(ref);
    }

    protected final JavaMethod getJavaMethod(IMethodRef ref) {
        if (ref instanceof ConstantMethodref) {
            JavaClass javaClass = classes.get(((ConstantMethodref) ref).getPointingClass().get());
            return javaClass.getInternalJavaMethod(ref);
        } else if (ref instanceof ConstantInterfaceMethodref) {
            return classes.get(((ConstantInterfaceMethodref) ref).getPointingClass().get()).getInternalJavaMethod(ref);
        }
        throw new RuntimeException("Constant must be of a method or interface reference");
    }

    protected final JavaClass getJavaClass(String packageAndName) {
        return classes.get(packageAndName);
    }

    protected final JavaField getJavaField(int constantPoolFieldRefIndex) {
        return getJavaField((ConstantFieldref) getContainedClass().getDecompiledClass().getEntry(constantPoolFieldRefIndex));
    }

    protected final JavaMethod getJavaMethod(int constantPoolMethodRefIndex) {
        return getJavaMethod((IMethodRef) getContainedClass().getDecompiledClass().getEntry(constantPoolMethodRefIndex));
    }

    /**
     * Deobfuscation phase step methods
     */
    public abstract void validationPhase();

    public abstract void linkingPhase();
}
