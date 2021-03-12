package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantInvokeDynamic extends JavaPoolEntry {

    private int bootstrap_method_attr_index;
    private int name_and_type_index;

    @Override
    public Result read() throws IOException {
        bootstrap_method_attr_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return super.toJavaSourceCode(class_index);
    }

    @Override
    public String toString() {
        return "{ConstantInvokeDynamic} \tbootstrap_method_attr_index: " + bootstrap_method_attr_index;
        // + "(" + getEntry(b).toString() + ")";
    }
}
