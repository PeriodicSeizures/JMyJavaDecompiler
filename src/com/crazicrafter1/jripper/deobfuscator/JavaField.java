package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DecompiledField;

import java.util.HashSet;

public class JavaField {

    /*
        fields are class members, not *JUST* variables
     */

    public String type;
    public String name;
    public String flags; // aka static final public private volatile etc...

    public JavaField(DecompiledField decompiledField, HashSet<String> retClassImports) {
        this.flags = decompiledField.getAccessFlags();
        this.name = NameUtil.toValidName(decompiledField.getName());
        //if (rawField.getSignature() != null) {
        //    this.type = Util.getType(rawField.getSignature(), retClassImports);
        //} else {
            this.type = NameUtil.toValidName(
                    Util.getFieldType(decompiledField.getDescriptor(), retClassImports));
        //}
    }

    @Override
    public String toString() {
        return flags + " " + type + " " + name;
    }
}
