package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantLong extends IDisassembled implements IConstant {

    private long value;

    public ConstantLong(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
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
