package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantInteger extends IDisassembled implements IConstant {

    private int value;

    public ConstantInteger(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
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
