package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantFloat extends IDisassembled implements IConstant {

    private float value;

    public ConstantFloat(DisassembledClass belongingClass) {
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
