package decompiler.classfile.attributes;

import decompiler.Result;

import java.io.IOException;

public class ConstantValueAttribute extends JavaAttribute {

    private int constantvalue_index;

    @Override
    public Result read() throws IOException {
        constantvalue_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "{ConstantValue} constantvalue_index: " + constantvalue_index + " (" + getEntry(constantvalue_index) + ")";
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return getEntry(constantvalue_index).toJavaSourceCode(class_index);
    }
}
