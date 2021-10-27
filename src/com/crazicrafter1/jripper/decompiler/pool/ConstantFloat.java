package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantFloat extends IPoolConstant {

    private float value;

    public ConstantFloat(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        value = bytes.readFloat();
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    public String toString() {
        return "{Float} \t" + value;
    }
}
