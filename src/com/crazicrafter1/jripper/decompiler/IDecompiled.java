package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;

public abstract class IDecompiled {

    private final DecompiledClass mainClass;

    public IDecompiled(DecompiledClass mainClass) {
        this.mainClass = mainClass;
    }

    public abstract void read(ByteReader bytes) throws IOException;

    public final DecompiledClass getMainClass() {
        return mainClass;
    }

    public IPoolConstant getEntry(int i) {
        if (mainClass != null)
            return mainClass.constantPoolContainer.getEntry(i);
        return ((DecompiledClass)this).constantPoolContainer.getEntry(i);
    }
}
