package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantNameAndType extends IDisassembled implements IConstant {

    private int name_index;
    private int descriptor_index;

    public ConstantNameAndType(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{NameAndType} \t" + getName() + " (" + Util.toValidName(getName()) + "), " + getDescriptor();
    }

    public String getName() {
        return (String) getEntry(name_index).get();
    }

    public String getDescriptor() {
        return (String) getEntry(descriptor_index).get();
    }
}
