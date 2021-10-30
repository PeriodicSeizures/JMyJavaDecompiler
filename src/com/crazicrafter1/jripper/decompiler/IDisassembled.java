package com.crazicrafter1.jripper.decompiler;

import com.crazicrafter1.jripper.decompiler.constants.IConstant;

import java.io.IOException;

public abstract class IDisassembled {

    private final DisassembledClass mainClass;

    public IDisassembled(DisassembledClass mainClass) {
        this.mainClass = mainClass;
    }

    public abstract void read(ByteReader bytes) throws IOException;

    public final DisassembledClass getMainClass() {
        return mainClass;
    }

    public IConstant getEntry(int i) {
        if (mainClass != null)
            return mainClass.constantPoolContainer.getEntry(i);
        return ((DisassembledClass)this).constantPoolContainer.getEntry(i);
    }
}
