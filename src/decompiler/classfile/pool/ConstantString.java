package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantString extends JavaPoolEntry {

    private int string_index;

    @Override
    public Result read() throws IOException {
        string_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return getEntry(string_index).toString();
    }

    @Override
    public String toString() {
        return "{ConstantString} \tstring_index: " + string_index + " (" + getEntry(string_index) + ")";
    }
}
