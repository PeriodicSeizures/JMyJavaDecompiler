package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantString extends IDisassembled implements IConstant {

    private int string_index;

    public ConstantString(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        string_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{String} \tstring_index: " + string_index + " (" + getEntry(string_index) + ")";
    }

    public String get() {
        return (String) getEntry(string_index).get();
    }
}
