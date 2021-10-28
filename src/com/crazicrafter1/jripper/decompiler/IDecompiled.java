package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;

public abstract class IDecompiled {

    private final DecompiledClass mainClass;

    public IDecompiled(DecompiledClass mainClass) {
        this.mainClass = mainClass;
    }

    public abstract void read(ByteReader bytes) throws IOException;

    public DecompiledClass getMainClass() {
        return mainClass;
    }

    public IPoolConstant getEntry(int i) {
        return mainClass.constantPoolContainer.getEntry(i);
    }
}
