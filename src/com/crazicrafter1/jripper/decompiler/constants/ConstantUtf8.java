package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantUtf8 extends IDisassembled implements IConstant {

    private String value;

    public ConstantUtf8(DisassembledClass belongingClass) {
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
