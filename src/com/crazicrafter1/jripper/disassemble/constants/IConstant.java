package com.crazicrafter1.jripper.disassemble.constants;

public interface IConstant {
    default Object get() {
        throw new UnsupportedOperationException("This class has no get implementation");
    }
}
