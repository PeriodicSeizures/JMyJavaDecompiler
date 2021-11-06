package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.types.IBaseField;
import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.types.IFieldDefined;
import com.crazicrafter1.jripper.util.Util;
import com.crazicrafter1.jripper.disassemble.DisassembledField;

/**
 * https://www.benf.org/other/cfr/obfuscation_field_renaming.html
 */
public class DecompiledField
        extends AbstractDecompiled
        implements IFieldDefined {

    private String flags; // aka static final public private volatile etc...
    private String type;
    private String name;

    public DecompiledField(DecompiledClass parentJavaClass, DisassembledField disassembledField) {
        super(parentJavaClass, disassembledField);
    }

    @Override
    public DisassembledField getDisassemblyUnit() {
        return (DisassembledField) super.getDisassemblyUnit();
    }

    @Override
    public void validationPhase() {
        DisassembledField unit = getDisassemblyUnit();

        this.flags = unit.getFormattedFlags();

        this.type = Util.toValidTypeName(
                Util.getFieldType(getDescriptor(), getImportSet()));

        this.name = Util.toValidName(unit.getName());
    }

    @Override
    public void linkingPhase() {}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IClassDefined getBaseClass() {
        return getHolder();
    }

    @Override
    public String getDescriptor() {
        return getDisassemblyUnit().getDescriptor();
    }

    @Override
    public boolean isPublic() {
        return getDisassemblyUnit().isPublic();
    }

    @Override
    public boolean isFinal() {
        return getDisassemblyUnit().isFinal();
    }

    @Override
    public boolean isVolatile() {
        return getDisassemblyUnit().isVolatile();
    }

    @Override
    public boolean isTransient() {
        return getDisassemblyUnit().isTransient();
    }

    @Override
    public boolean isEnum() {
        return getDisassemblyUnit().isEnum();
    }

    @Override
    public boolean isPrivate() {
        return getDisassemblyUnit().isPrivate();
    }

    @Override
    public boolean isProtected() {
        return getDisassemblyUnit().isProtected();
    }

    @Override
    public boolean isStatic() {
        return getDisassemblyUnit().isStatic();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IBaseField))
            return false;

        if (obj == this)
            return true;

        IBaseField field = (IBaseField) obj;

        return this.getBaseClass().equals(field.getBaseClass()) &&
                this.getName().equals(field.getName()) &&
                this.getDescriptor().equals(field.getDescriptor());
    }

    @Override
    public String toString() {
        return (flags + " " + type + " " + name + ";").trim();
    }
}
