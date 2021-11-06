package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.types.IMethodDefined;
import com.crazicrafter1.jripper.disassemble.attributes.CodeAttr;
import com.crazicrafter1.jripper.disassemble.attributes.EnumAttr;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.util.Util;

import java.io.IOException;
import java.util.List;

public final class DisassembledMethod
        extends IDisassembled
        implements IDisassembledMember, IMethodDefined {

    /*
        method access flags
     */
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

    private List<String> parameterTypes;
    private StringBuilder inReturnType;

    DisassembledMethod(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        attributeContainer = new AttributeContainer(this.getMain());

        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read(bytes);
    }

    @Override
    public int getAccessFlags() {
        return access_flags;
    }

    @Override
    public String getName() {
        return (String) getConstant(name_index).get();
    }

    public String getDescriptor() {
        return (String) getConstant(descriptor_index).get();
    }

    @Override
    public IClassDefined getBaseClass() {
        return this.getBaseClass();
    }

    @Override
    public String getReturnType() {
        getParameterTypes();
        return inReturnType.toString();
    }

    @Override
    public List<String> getParameterTypes() {
        if (inReturnType == null) {
            StringBuilder inReturnType = new StringBuilder();
            this.parameterTypes = Util.getParameterTypes(getDescriptor(), getBaseClass().getImportSet(), inReturnType);
        }
        return this.parameterTypes;
    }

    @Override
    public boolean isAbstract() {
        return (access_flags & ACC_ABSTRACT) == ACC_ABSTRACT;
    }

    @Override
    public boolean isSynchronized() {
        return (access_flags & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED;
    }

    @Override
    public boolean hasVarArgs() {
        return (access_flags & ACC_VARARGS) == ACC_VARARGS;
    }

    @Override
    public boolean isStrictFP() {
        return (access_flags & ACC_STRICT) == ACC_STRICT;
    }

    /**
     * Get the name + parameter descriptor
     * @return methodName(ILjava/lang/String;)
     */
    /// Use UUID instead
    public String getErasureDescriptor() {
        if (!getMethodType().isInitializer()) {
            String methodDescriptor = getDescriptor();
            String parameterDescriptor = methodDescriptor.substring(0, methodDescriptor.indexOf(")") + 1);
            return getName() + parameterDescriptor;
        }
        return getName();
    }

    public String getParameterDescriptor() {
        String methodDescriptor = getDescriptor();
        return methodDescriptor.substring(1, methodDescriptor.indexOf(")"));
    }

    public CodeAttr getCodeAttr() {
        return (CodeAttr) attributeContainer.get(EnumAttr.Code);
    }

    @Override
    public String toString() {
        return "{Method} " + getName() + getDescriptor() + "\n" +
                attributeContainer.get(EnumAttr.Code);
    }
}
