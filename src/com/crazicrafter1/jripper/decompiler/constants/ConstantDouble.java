package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantDouble extends IDisassembled implements IConstant {

    public double value;

    public ConstantDouble(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        value = bytes.readDouble();
    }

    @Override
    public String toString() {
        return "{Double} \t" + value;
    }

}
