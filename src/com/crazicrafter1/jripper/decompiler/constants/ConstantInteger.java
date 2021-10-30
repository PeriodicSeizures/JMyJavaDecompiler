package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantInteger extends IDisassembled implements IConstant {

    private int value;

    public ConstantInteger(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        value = bytes.readInt();
    }

    @Override
    public String toString() {
        return "{Integer} \t" + value;
    }

    @Override
    public Integer get() {
        return value;
    }
}
