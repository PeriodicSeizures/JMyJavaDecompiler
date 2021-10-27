package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;

public abstract class DecompiledItem {

    protected final DecompiledClass belongingClass;

    public DecompiledItem(DecompiledClass belongingClass) {
        this.belongingClass = belongingClass;
    }

    public abstract void read(ByteReader bytes) throws IOException;

    public IPoolConstant getEntry(int i) {
        return belongingClass.constantPoolContainer.getEntry(i);
    }
}
