package com.crazicrafter1.jripper.deobfuscator.unused;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.pool.ConstantClass;
import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.IMethodRef;

import java.util.HashMap;

public class ExternalClass implements IClass {

    private final ConstantClass constantClass;
    private HashMap<IMethodRef, ExternalMethod> methods = new HashMap<>();

    public ExternalClass(ConstantClass constantClass) {
        this.constantClass = constantClass;
    }

    public void addExternalMethod() {

    }

    @Override
    public String getPackageName() {
        String[] pk = Util.getPackageAndClass(constantClass.get());
        return pk[0];
    }

    @Override
    public String getClassName() {
        String[] pk = Util.getPackageAndClass(constantClass.get());
        return pk[1];
    }

    @Override
    public IClass getSuperClass() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public IMethod getInternalMethod(IMethodRef ref) {
        if (ref.getPointingClass() == constantClass) {
            // do something?

        } else
            throw new RuntimeException("Unknown operation");
    }

    @Override
    public IField getInternalField(ConstantFieldref ref) {
        return null;
    }
}
