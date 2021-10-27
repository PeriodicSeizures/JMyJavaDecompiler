package com.crazicrafter1.jripper.decompiler.pool;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

import java.io.IOException;

public class ConstantMethodHandle extends IPoolConstant {

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

    public ConstantMethodHandle(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        reference_kind = bytes.readUnsignedByte();
        reference_index = bytes.readUnsignedShort();
    }

    public int getHandleKind() {
        return reference_kind;
    }

    public IPoolConstant getRef() {
        return getEntry(reference_index);
    }

    @Override
    public String toString() {
        return "{MethodHandle} \treference_kind: " + reference_kind;
    }
}
