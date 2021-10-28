package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;

public abstract class IDecompiled {

    protected final DecompiledClass belongingClass;

    public IDecompiled(DecompiledClass belongingClass) {
        this.belongingClass = belongingClass;
    }

    public abstract void read(ByteReader bytes) throws IOException;

    public DecompiledClass getBelongingClass() {
        return belongingClass;
    }

    public IPoolConstant getEntry(int i) {
        return belongingClass.constantPoolContainer.getEntry(i);
    }
}
