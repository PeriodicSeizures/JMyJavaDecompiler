package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantMethodref extends IPoolConstant implements IMethodRef {
    private int class_index;
    private int name_and_type_index;

    public ConstantMethodref(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
    }

    @Override
    public ConstantClass getPointingClass() {
        return (ConstantClass) getEntry(class_index);
    }

    public String getMethodDescriptor() {
        return ((ConstantNameAndType) getEntry(name_and_type_index)).getDescriptor();
    }

    public String getMethodName() {
        return ((ConstantNameAndType) getEntry(name_and_type_index)).getName();
    }

    public String GUID() {
        return getMainClass().getClassName() + "::" + getMethodName() + getMethodDescriptor();
    }

    @Override
    public String toString() {
        return "{Methodref} \tclass: " + getPointingClass().get() + ", " + getMethodDescriptor() + " " + getMethodName();
    }
}
