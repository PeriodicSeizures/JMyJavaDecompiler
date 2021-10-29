package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DecompiledField;

import java.util.LinkedHashSet;
import java.util.Set;

public class JavaField extends IObfuscate {

    private final DecompiledField decompiledField;

    public String flags; // aka static final public private volatile etc...
    public String type;
    public String name;

    public JavaField(JavaClass parentJavaClass, DecompiledField decompiledField) {
        super(parentJavaClass);

        this.decompiledField = decompiledField;
    }

    @Override
    public void validationPhase() {
        this.flags = decompiledField.getAccessFlags();

        Set<String> imports = new LinkedHashSet<>();
        this.type = Util.toValidName(
                Util.getFieldType(decompiledField.getDescriptor(), imports));
        ((JavaClass) getParentObfuscate()).addClassImports(imports);

        this.name = Util.toValidName(decompiledField.getName());
    }

    @Override
    public void linkingPhase() {

    }

    @Override
    public String toString() {
        return (flags + " " + type + " " + name + ";").trim();
    }
}
