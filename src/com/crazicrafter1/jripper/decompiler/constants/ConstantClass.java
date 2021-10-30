package com.crazicrafter1.jripper.decompiler.constants;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;

public class ConstantClass extends IDisassembled implements IConstant {

    private int name_index;

    public ConstantClass(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        // read name index
        name_index = bytes.readUnsignedShort();
    }

    @Override
    public String get() {
        return (String) getEntry(name_index).get();
    }

    @Override
    public String toString() {
        return "{Class} \t" + get();
    }
}
