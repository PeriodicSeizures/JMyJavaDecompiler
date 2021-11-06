package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.util.Util;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantNameAndType extends IDisassembled implements IConstant {

    private int name_index;
    private int descriptor_index;

    public ConstantNameAndType(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{NameAndType} \t" + getName() + " (" + Util.toValidName(getName()) + "), " + getDescriptor();
    }

    public String getName() {
        return (String) getConstant(name_index).get();
    }

    public String getDescriptor() {
        return (String) getConstant(descriptor_index).get();
    }
}
