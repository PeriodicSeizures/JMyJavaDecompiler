package com.crazicrafter1.jripper.decompiler.constants;

public interface IConstant {
    default Object get() {
        throw new UnsupportedOperationException("This class has no get implementation");
    }
}
