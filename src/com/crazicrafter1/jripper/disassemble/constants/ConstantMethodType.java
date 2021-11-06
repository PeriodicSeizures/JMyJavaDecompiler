package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.except.NoUsageException;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantMethodType extends IDisassembled implements IConstant {

    private int descriptor_index;

    public ConstantMethodType(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        descriptor_index = bytes.readUnsignedShort();
    }

    public String getDescriptor() {
        return (String) getConstant(descriptor_index).get();
    }

    @Override
    public Object get() {
        throw new NoUsageException("Not yet implemented");
    }

    @Override
    public String toString() {
        return "{MethodType} \tdescriptor_index: " + descriptor_index + " (" + getConstant(descriptor_index) + ")";
    }
}
