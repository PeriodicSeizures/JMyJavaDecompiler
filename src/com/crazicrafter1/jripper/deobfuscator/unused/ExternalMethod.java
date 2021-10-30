package com.crazicrafter1.jripper.deobfuscator.unused;

import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;

public class ExternalMethod implements IMethod {

    private final ConstantMethodref methodref;

    public ExternalMethod(ConstantMethodref methodref) {
        this.methodref = methodref;
    }
}
