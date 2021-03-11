package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ConstantFloat extends JavaPoolEntry {

    private float value;

    @Override
    public Result read() throws IOException {
        value = bytes.readFloat();
        return Result.OK;
    }
}
