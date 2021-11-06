package com.crazicrafter1.jripper.disassemble.constants;

import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.IDisassembled;
import com.crazicrafter1.jripper.util.Util;

import java.io.IOException;
import java.util.List;

public class ConstantMethodRef
        extends IDisassembled
        implements IConstant, IMethodRef {

    private int class_index;
    private int name_and_type_index;
    private List<String> parameterTypes;
    private String returnType;

    public ConstantMethodRef(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
    }

    @Override
    public String getReturnType() {
        getParameterTypes();
        return returnType;
    }

    @Override
    public List<String> getParameterTypes() {
        if (returnType == null) {
            StringBuilder builder = new StringBuilder();
            this.parameterTypes = Util.getParameterTypes(getDescriptor(), getBaseClass().getImportSet(), builder);
            this.returnType = builder.toString();
        }
        return this.parameterTypes;
    }

    @Override
    public String getReferredClassName() {
        return (String) getConstant(class_index).get();
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
    public IClassDefined getBaseClass() {
        return this.getMain();
    }

    @Override
    public String toString() {
        return "{MethodRef} \t" + getReferredClassName() + "::" + getName() + getDescriptor();
    }
}
