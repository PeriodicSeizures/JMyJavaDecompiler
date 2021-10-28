package com.crazicrafter1.jripper.decompiler;

public abstract class IPoolConstant extends IDecompiled {

    public IPoolConstant(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    public Object get() {
        throw new UnsupportedOperationException("This class has no get implementation");
    }
}
