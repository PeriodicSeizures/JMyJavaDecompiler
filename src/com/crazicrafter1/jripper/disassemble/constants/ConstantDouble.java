package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantDouble extends IDisassembled implements IConstant {

    public double value;

    public ConstantDouble(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        value = bytes.readDouble();
    }

    @Override
    public String toString() {
        return "{Double} \t" + value;
    }

}
