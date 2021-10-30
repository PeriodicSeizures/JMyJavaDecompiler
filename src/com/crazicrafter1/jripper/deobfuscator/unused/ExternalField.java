package com.crazicrafter1.jripper.deobfuscator.unused;

import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;

public class ExternalField implements IField {

    private final ConstantFieldref fieldref;

    public ExternalField(ConstantFieldref fieldref) {
        this.fieldref = fieldref;
    }
}
