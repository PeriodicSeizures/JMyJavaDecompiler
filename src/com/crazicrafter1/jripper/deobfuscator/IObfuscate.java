package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.IPoolConstant;
import com.crazicrafter1.jripper.decompiler.except.InvalidTypeException;
import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;

import java.util.HashMap;

public abstract class IObfuscate {

    private IObfuscate parentObfuscate;

    protected static HashMap<String, JavaClass> classes = new HashMap<>();

    public IObfuscate(IObfuscate parentObfuscate) {
        this.parentObfuscate = parentObfuscate;
    }

    public IObfuscate getParentObfuscate() {
        return parentObfuscate;
    }

    /**
     * Useful methods to get fields methods and classes globally from jar
     */
    protected final JavaField getJavaField(ConstantFieldref ref) {
        return classes.get(ref.getPointingClass().get()).getInternalJavaField(ref);
    }

    protected final JavaMethod getJavaMethod(IPoolConstant ref) {
        if (ref instanceof ConstantMethodref) {
            return classes.get(((ConstantMethodref) ref).getPointingClass().get()).getInternalJavaMethod(ref);
        } else if (ref instanceof ConstantInterfaceMethodref) {
            return classes.get(((ConstantInterfaceMethodref) ref).getPointingClass().get()).getInternalJavaMethod(ref);
        }
        throw new InvalidTypeException("Constant must be of a method or interface reference");
    }

    //protected final JavaClass getJavaClass()

    /**
     * Deobfuscation phase step methods
     */
    public abstract void validationPhase();

    public abstract void linkingPhase();
}
