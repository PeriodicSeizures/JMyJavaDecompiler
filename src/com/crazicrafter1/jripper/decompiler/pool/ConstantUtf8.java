package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantUtf8 extends IPoolConstant {

    private String value;

    public ConstantUtf8(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        value = bytes.readUTF();
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return "{Utf8} \t" + value;
    }
}
