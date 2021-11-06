package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.except.NoUsageException;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.IOException;

public class ConstantMethodHandle extends IDisassembled implements IConstant {

    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_invokeInterface = 9;

    private int reference_kind;
    private int reference_index;

    public ConstantMethodHandle(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        reference_kind = bytes.readUnsignedByte();
        reference_index = bytes.readUnsignedShort();
    }

    public int getHandleKind() {
        return reference_kind;
    }

    public IConstant getRef() {
        return getConstant(reference_index);
    }

    @Override
    public Object get() {
        throw new NoUsageException("Not yet implemented");
    }

    @Override
    public String toString() {
        return "{MethodHandle} \treference_kind: " + reference_kind;
    }
}
