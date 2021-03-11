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
}
