package decompiler.reader;

import decompiler.Result;
import decompiler.Util;
import decompiler.reader.attributes.CodeAttribute;
import decompiler.reader.attributes.RawAttribute;
import decompiler.reader.attributes.SignatureAttribute;

import java.io.IOException;
import java.util.ArrayList;

public class RawMethod extends RawItem {

    /*
        method access flags
     */
    private static final int ACC_PUBLIC =        0x0001;
    private static final int ACC_PRIVATE =       0x0002;
    private static final int ACC_PROTECTED =     0x0004;
    private static final int ACC_STATIC =        0x0008;
    private static final int ACC_FINAL =         0x0010;
    private static final int ACC_SYNCHRONIZED =  0x0020;
    private static final int ACC_BRIDGE =        0x0040;
    private static final int ACC_VARARGS =       0x0080;
    private static final int ACC_NATIVE =        0x0100;
    private static final int ACC_ABSTRACT =      0x0400;
    private static final int ACC_STRICT =        0x0800;
    private static final int ACC_SYNTHETIC =     0x1000;

    /*
        method members
    */
    private int access_flags;
    private int name_index;
    private int descriptor_index;
    private AttributeContainer attributeContainer = new AttributeContainer();

    @Override
    public Result read() throws IOException {
        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read();

        return Result.OK;
    }

    public boolean isStatic() {
        return (access_flags & ACC_STATIC) == ACC_STATIC;
    }

    public boolean isFinal() {
        return (access_flags & ACC_FINAL) == ACC_FINAL;
    }

    public boolean isSynchronized() {
        return (access_flags & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED;
    }

    public boolean hasVarArgs() {
        return (access_flags & ACC_VARARGS) == ACC_VARARGS;
    }

    public boolean isAbstract() {
        return (access_flags & ACC_ABSTRACT) == ACC_ABSTRACT;
    }

    public boolean isStrictFP() {
        return (access_flags & ACC_STRICT) == ACC_STRICT;
    }

    public String getAccessFlags() {
        StringBuilder s = new StringBuilder();
        if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
            s.append("public ");
        if ((access_flags & ACC_PRIVATE) == ACC_PRIVATE)
            s.append("private ");
        if ((access_flags & ACC_PROTECTED) == ACC_PROTECTED)
            s.append("protected ");

        if (isFinal())
            s.append("final ");

        if (isStatic())
            s.append("static ");

        if (isSynchronized())
            s.append("synchronized ");

        if (isStrictFP())
            s.append("strictfp ");

        if (isAbstract())
            s.append("abstract ");

        return s.toString();
    }

    public String getDescriptor() {
        return (String) getEntry(descriptor_index).getValue();
    }

    public CodeAttribute getCode() {
        return (CodeAttribute) attributeContainer.get(RawAttribute.Attribute.Code);
    }

    //public SignatureAttribute getSignature() {
    //    return (SignatureAttribute) attributeContainer.get(RawAttribute.Attribute.Signature);
    //}

    @Override
    public String toString() {
        return "{RawMethod} " + getEntry(descriptor_index).getValue() + "\n" +
                attributeContainer;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    StringBuilder s = new StringBuilder();

    //    //System.out.println("mdescriptor" + get(descriptor_index));

    //    String name = getEntry(name_index).toString();
    //    // 60, 62 < >

    //    if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
    //        s.append("public ");
    //    else if ((access_flags & ACC_PRIVATE) == ACC_PRIVATE)
    //        s.append("private ");
    //    else if ((access_flags & ACC_PROTECTED) == ACC_PROTECTED)
    //        s.append("protected ");

    //    if ((access_flags & ACC_STATIC) == ACC_STATIC)
    //        s.append("static ");

    //    if ((access_flags & ACC_FINAL) == ACC_FINAL)
    //        s.append("final ");

    //    if ((access_flags & ACC_ABSTRACT) == ACC_ABSTRACT)
    //        s.append("abstract ");

    //    if ((access_flags & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED)
    //        s.append("synchronized ");

    //    s.append(getReturnType()).append(" ");
    //    s.append(RawItem.getUnqualifiedName(name));

    //    return s.toString();
    //}

    //public String getSignature() {
    //    return this.
    //}

    public String getName() {
        return (String) getEntry(name_index).getValue();
    }
}
