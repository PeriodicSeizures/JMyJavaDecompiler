package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DecompiledField;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class JavaField extends IDeobfuscated {

    /*
        fields are class members, not *JUST* variables
     */

    public String type;
    public String name;
    public String flags; // aka static final public private volatile etc...

    private DecompiledField decompiledField;

    public JavaField(JavaClass parentJavaClass, DecompiledField decompiledField) {
        super(parentJavaClass);

        this.decompiledField = decompiledField;
    }

    @Override
    public void process() {
        this.flags = decompiledField.getAccessFlags();
        this.name = Util.toValidName(decompiledField.getName());
        //if (rawField.getSignature() != null) {
        //    this.type = Util.getType(rawField.getSignature(), retClassImports);
        //} else {
        Set<String> imports = new LinkedHashSet<>();
        this.type = Util.toValidName(
                Util.getFieldType(decompiledField.getDescriptor(), imports));
        ((JavaClass)getParentDeobfuscator()).addClassImports(imports);
    }

    @Override
    public String toString() {
        return flags + " " + type + " " + name;
    }
}
