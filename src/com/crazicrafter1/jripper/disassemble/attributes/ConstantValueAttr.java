package com.crazicrafter1.jripper.disassemble.attributes;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantValueAttr extends IDisassembled implements IAttr {

    private int constantvalue_index;

    public ConstantValueAttr(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        constantvalue_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{ConstantValue} constantvalue_index: " + constantvalue_index + " (" + getConstant(constantvalue_index) + ")";
    }
}
