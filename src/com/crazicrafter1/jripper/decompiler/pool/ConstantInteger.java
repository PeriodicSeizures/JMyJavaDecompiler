package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantInteger extends IPoolConstant {

    private int value;

    public ConstantInteger(DecompiledClass belongingClass) {
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
