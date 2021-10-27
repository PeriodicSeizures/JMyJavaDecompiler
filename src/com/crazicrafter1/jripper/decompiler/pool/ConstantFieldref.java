package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantFieldref extends IPoolConstant {

    private int class_index;
    private int name_and_type_index;

    public ConstantFieldref(DecompiledClass belongingClass) {
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

    public String getName() {
        return ((ConstantNameAndType)getEntry(name_and_type_index)).getName();
    }

    public String getDescriptor() {
        return ((ConstantNameAndType) getEntry(name_and_type_index)).getDescriptor();
    }

    @Override
    public String toString() {
        return "{Fieldref} \tclass: " + getPointingClass() + ", " + getDescriptor();
    }
}
