package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantMethodref extends JavaPoolEntry {
    private int class_index;
    private int name_and_type_index;

    @Override
    public Result read() throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
        return Result.OK;
    }
}
