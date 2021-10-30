package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantMethodType extends IDisassembled implements IConstant {

    private int descriptor_index;

    public ConstantMethodType(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        descriptor_index = bytes.readUnsignedShort();
    }

    public String getDescriptor() {
        return (String) getEntry(descriptor_index).get();
    }

    @Override
    public String toString() {
        return "{MethodType} \tdescriptor_index: " + descriptor_index + " (" + getEntry(descriptor_index) + ")";
    }
}
