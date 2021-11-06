package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantString extends IDisassembled implements IConstant {

    private int string_index;

    public ConstantString(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        string_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{String} \tstring_index: " + string_index + " (" + getConstant(string_index) + ")";
    }

    public String get() {
        return (String) getConstant(string_index).get();
    }
}
