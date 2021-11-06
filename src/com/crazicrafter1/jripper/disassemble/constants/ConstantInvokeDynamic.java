package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantInvokeDynamic extends IDisassembled implements IConstant {

    private int bootstrap_method_attr_index;
    private int name_and_type_index;

    public ConstantInvokeDynamic(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        bootstrap_method_attr_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{InvokeDynamic} \tbootstrap_method_attr_index: " + bootstrap_method_attr_index;
        // + "(" + get(b).toString() + ")";
    }

}
