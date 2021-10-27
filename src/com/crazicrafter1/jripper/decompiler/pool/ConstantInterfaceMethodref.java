package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantInterfaceMethodref extends IPoolConstant {
    int class_index;
    int name_and_type_index;

    public ConstantInterfaceMethodref(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
    }

    public ConstantClass getPointingClass() {
        return (ConstantClass) getEntry(class_index);
    }

    public String getDescriptor() {
        return (String) getEntry(name_and_type_index).get();
    }

    @Override
    public String toString() {
        return "{IMref} \t" + getPointingClass() + ", " + getDescriptor() + ")";
    }

    public ConstantClass getConstantClass() {
        return (ConstantClass)getEntry(class_index);
    }
}
