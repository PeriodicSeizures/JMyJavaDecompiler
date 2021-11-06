package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantLong extends IDisassembled implements IConstant {

    private long value;

    public ConstantLong(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        value = bytes.readLong();
    }

    @Override
    public String toString() {
        return "{Long} \t" + value;
    }

    @Override
    public Long get() {
        return value;
    }
}
