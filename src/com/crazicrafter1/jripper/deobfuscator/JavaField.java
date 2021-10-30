package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DisassembledField;

import java.util.LinkedHashSet;
import java.util.Set;

public class JavaField extends IDecompiled {

    private final DisassembledField disassembledField;

    public String flags; // aka static final public private volatile etc...
    public String type;
    public String name;

    public JavaField(DecompiledJavaClass parentJavaClass, DisassembledField disassembledField) {
        super(parentJavaClass);

        this.disassembledField = disassembledField;
    }

    @Override
    public void validationPhase() {
        this.flags = disassembledField.getAccessFlags();

        Set<String> imports = new LinkedHashSet<>();
        this.type = Util.toValidName(
                Util.getFieldType(disassembledField.getDescriptor(), imports));
        getContainedClass().addClassImports(imports);

        this.name = Util.toValidName(disassembledField.getName());
    }

    @Override
    public void linkingPhase() {

    }

    @Override
    public String toString() {
        return (flags + " " + type + " " + name + ";").trim();
    }
}
