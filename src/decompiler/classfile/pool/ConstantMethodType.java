package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantMethodType extends JavaPoolEntry {

    private int descriptor_index;

    @Override
    public Result read() throws IOException {
        descriptor_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return "";
    }

    @Override
    public String toString() {
        return "{ConstantClass} \tdescriptor_index: " + descriptor_index + " (" + getEntry(descriptor_index) + ")";
    }
}
