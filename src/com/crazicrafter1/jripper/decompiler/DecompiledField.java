package com.crazicrafter1.jripper.decompiler;

import com.crazicrafter1.jripper.Util;

import java.io.IOException;

public class DecompiledField extends IDecompiled {

    /*
        field access flags
     */
    private static final int ACC_PUBLIC =    0x0001;
    private static final int ACC_PRIVATE =   0x0002;
    private static final int ACC_PROTECTED = 0x0004;
    private static final int ACC_STATIC =    0x0008;
    private static final int ACC_FINAL =     0x0010;
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

    private AttributeContainer attributeContainer = new AttributeContainer(belongingClass);

    public DecompiledField(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read(bytes);
    }

    public boolean isStatic() {
        return (access_flags & ACC_STATIC) == ACC_STATIC;
    }

    public boolean isFinal() {
        return (access_flags & ACC_FINAL) == ACC_FINAL;
    }

    public boolean isVolatile() {
        return (access_flags & ACC_VOLATILE) == ACC_VOLATILE;
    }

    public boolean isTransient() {
        return (access_flags & ACC_TRANSIENT) == ACC_TRANSIENT;
    }

    public boolean isEnum() {
        return (access_flags & ACC_ENUM) == ACC_ENUM;
    }

    /// volatile public static final transient Object
    public String getAccessFlags() {
        StringBuilder s = new StringBuilder();

        if (isVolatile())
            s.append("volatile ");

        if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
            s.append("public ");
        else if ((access_flags & ACC_PRIVATE) == ACC_PRIVATE)
            s.append("private ");
        else if ((access_flags & ACC_PROTECTED) == ACC_PROTECTED)
            s.append("protected ");

        if (isStatic())
            s.append("static ");

        if (isFinal())
            s.append("final ");

        if (isTransient())
            s.append("transient ");

        return s.toString().trim();
    }

    @Override
    public String toString() {
        return "{RawField} \t" + getEntry(name_index).get() + "(" + Util.toValidName((String) getEntry(name_index).get()) + "), " +
                getEntry(descriptor_index).get() + "\n" + attributeContainer;
    }

    public String getName() {
        return (String) getEntry(name_index).get();
    }

    public String getDescriptor() {
        return (String) getEntry(descriptor_index).get();
    }
}
