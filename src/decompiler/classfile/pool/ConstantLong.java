package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ConstantLong extends JavaPoolEntry {

    private long value;

    @Override
    public Result read() throws IOException {
        value = bytes.readLong();
        return Result.OK;
    }
}
