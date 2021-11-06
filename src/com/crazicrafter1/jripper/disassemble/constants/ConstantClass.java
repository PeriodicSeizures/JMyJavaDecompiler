package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantClass extends IDisassembled implements IConstant {

    private int name_index;

    public ConstantClass(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        // read name index
        name_index = bytes.readUnsignedShort();
    }

    @Override
    public String get() {
        return (String) getConstant(name_index).get();
    }

    @Override
    public String toString() {
        return "{Class} \t" + get();
    }
}
