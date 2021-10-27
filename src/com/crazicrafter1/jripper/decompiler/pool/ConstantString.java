package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantString extends IPoolConstant {

    private int string_index;

    public ConstantString(DecompiledClass belongingClass) {
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
