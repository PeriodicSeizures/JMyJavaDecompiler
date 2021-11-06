package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.types.IFieldDefined;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.util.Util;

import java.io.IOException;

public final class DisassembledField extends IDisassembled
        implements IDisassembledMember, IFieldDefined {

    /*
        field access flags
     */
    private static final int ACC_VOLATILE =  0x0040;
    private static final int ACC_TRANSIENT = 0x0080;
    private static final int ACC_SYNTHETIC = 0x1000;
    private static final int ACC_ENUM =      0x4000;

    /*
        field members
     */
    private int access_flags;
    private int name_index;
    private int descriptor_index;

    private final AttributeContainer attributeContainer =
            new AttributeContainer(this.getBaseClass());

    DisassembledField(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read(bytes);
    }

    @Override
    public int getAccessFlags() {
        return access_flags;
    }

    public String getName() {
        return (String) getConstant(name_index).get();
    }

    public String getDescriptor() {
        return (String) getConstant(descriptor_index).get();
    }

    @Override
    public DisassembledClass getBaseClass() {
        return getMain();
    }

    @Override
    public String getFormattedFlags() {
        // public static final transient volatile boolean b;
        return getVisibility().getName() +
                (isStatic() ? " static" : "") +
                (isFinal() ? " final" : "") +
                (isVolatile() ? " volatile" : "") +
                (isTransient() ? " transient" : "");
    }

    @Override
    public boolean isVolatile() {
        return (access_flags & ACC_VOLATILE) == ACC_VOLATILE;
    }

    @Override
    public boolean isTransient() {
        return (access_flags & ACC_TRANSIENT) == ACC_TRANSIENT;
    }

    @Override
    public boolean isEnum() {
        return (access_flags & ACC_ENUM) == ACC_ENUM;
    }

    @Override
    public String toString() {
        return "{Field} \t" + getConstant(name_index).get() + "(" + Util.toValidName((String) getConstant(name_index).get()) + "), " +
                getConstant(descriptor_index).get(); // + attributeContainer;
    }
}
