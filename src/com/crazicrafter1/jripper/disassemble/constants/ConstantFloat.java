package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantFloat extends IDisassembled implements IConstant {

    private float value;

    public ConstantFloat(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
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
