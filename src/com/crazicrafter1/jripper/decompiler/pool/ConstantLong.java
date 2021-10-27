package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantLong extends IPoolConstant {

    private long value;

    public ConstantLong(DecompiledClass belongingClass) {
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
