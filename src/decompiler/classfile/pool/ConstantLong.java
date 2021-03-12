package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantLong extends JavaPoolEntry {

    private long value;

    @Override
    public Result read() throws IOException {
        value = bytes.readLong();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return "" + value;
    }

    @Override
    public String toString() {
        return "{Long} \t" + value;
    }
}
