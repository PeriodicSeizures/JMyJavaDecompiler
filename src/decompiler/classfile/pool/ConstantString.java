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
    public String toString() {
        return getEntry(string_index).toString();
    }
}
