package com.crazicrafter1.jripper.decompiler.attributes;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IAttr;

import java.io.IOException;

public class ConstantValueAttr extends IAttr {

    private int constantvalue_index;

    public ConstantValueAttr(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        constantvalue_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{ConstantValue} constantvalue_index: " + constantvalue_index + " (" + getEntry(constantvalue_index) + ")";
    }
}
