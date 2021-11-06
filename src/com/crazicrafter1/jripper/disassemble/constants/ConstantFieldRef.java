package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantFieldRef
        extends IDisassembled
        implements IConstant, IMemberRef {

    private int class_index;
    private int name_and_type_index;

    public ConstantFieldRef(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
    }

    @Override
    public String getName() {
        return ((ConstantNameAndType) getConstant(name_and_type_index)).getName();
    }

    @Override
    public String getDescriptor() {
        return ((ConstantNameAndType) getConstant(name_and_type_index)).getDescriptor();
    }

    @Override
    public String getReferredClassName() {
        return ((ConstantClass) getConstant(class_index)).get();
    }

    @Override
    public IClassDefined getBaseClass() {
        return getMain();
    }

    @Override
    public String toString() {
        return "{FieldRef} \t" + getDescriptor() + " " + getBaseClass().getName() + "::" + getName();
    }
}
