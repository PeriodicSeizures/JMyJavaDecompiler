package com.crazicrafter1.jripper.deobfuscator;

public abstract class IDeobfuscated {

    private IDeobfuscated parentDeobfuscator;

    public IDeobfuscated(IDeobfuscated parentDeobfuscator) {
        this.parentDeobfuscator = parentDeobfuscator;
    }

    public IDeobfuscated getParentDeobfuscator() {
        return parentDeobfuscator;
    }

    public abstract void process();
}
