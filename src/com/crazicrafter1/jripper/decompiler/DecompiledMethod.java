package com.crazicrafter1.jripper.decompiler;

import com.crazicrafter1.jripper.decompiler.attributes.CodeAttr;
import com.crazicrafter1.jripper.decompiler.attributes.EnumAttr;

import java.io.IOException;
import java.util.Objects;

public class DecompiledMethod extends IDecompiled {

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
    private AttributeContainer attributeContainer;

    public DecompiledMethod(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        attributeContainer = new AttributeContainer(getMainClass());

        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read(bytes);
    }

    /**
     * Get the name + parameter descriptor
     * @return methodName(ILjava/lang/String;)
     */
    public String getErasureDescriptor() {
        if (!isInstanceInitializer() && !isStaticInitializer()) {
            String methodDescriptor = getMethodDescriptor();
            String parameterDescriptor = methodDescriptor.substring(0, methodDescriptor.indexOf(")") + 1);
            return getMethodName() + parameterDescriptor;
        }
        return getMethodName();
    }

    public String getParameterDescriptor() {
        String methodDescriptor = getMethodDescriptor();
        return methodDescriptor.substring(1, methodDescriptor.indexOf(")"));
    }

    public String getMethodDescriptor() {
        return (String) getEntry(descriptor_index).get();
    }

    public String GUID() {
        /// DecompiledMethod::GUID()Ljava/lang/String;
        return getMainClass().getClassName() + "::" + getMethodName() + getMethodDescriptor();
    }

    public boolean isPublic() {
        return (access_flags & ACC_PUBLIC) == ACC_PUBLIC;
    }

    public boolean isPrivate() {
        return (access_flags & ACC_PRIVATE) == ACC_PRIVATE;
    }

    public boolean isProtected() {
        return (access_flags & ACC_PROTECTED) == ACC_PROTECTED;
    }

    public boolean isInstanceInitializer() {
        return getMethodName().equals("<init>");
    }

    public boolean isStaticInitializer() {
        return getMethodName().equals("<clinit>");
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
        if (isPublic())
            s.append("public ");
        if (isPrivate())
            s.append("private ");
        if (isProtected())
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

        return s.toString().trim();
    }

    public CodeAttr getCodeAttr() {
        return (CodeAttr) attributeContainer.get(EnumAttr.Code);
    }

    public String getMethodName() {
        return (String) getEntry(name_index).get();
    }

    @Override
    public String toString() {
        return "{RawMethod} " + getMethodDescriptor() + "\n" +
                attributeContainer;
    }
}
