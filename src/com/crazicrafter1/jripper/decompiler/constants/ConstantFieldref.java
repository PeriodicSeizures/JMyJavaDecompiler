package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantFieldref extends IDisassembled implements IConstant {

    private int class_index;
    private int name_and_type_index;

    public ConstantFieldref(DisassembledClass belongingClass) {
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

    public String getFieldName() {
        return ((ConstantNameAndType)getEntry(name_and_type_index)).getName();
    }

    public String getFieldDescriptor() {
        return ((ConstantNameAndType) getEntry(name_and_type_index)).getDescriptor();
    }

    @Override
    public String toString() {
        return "{Fieldref} \tclass: " + getPointingClass() + ", " + getFieldDescriptor() + " " + getFieldName();
    }
}
