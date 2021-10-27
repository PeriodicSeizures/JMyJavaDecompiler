package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantClass extends IPoolConstant {

    private int name_index;

    public ConstantClass(DecompiledClass belongingClass) {
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
