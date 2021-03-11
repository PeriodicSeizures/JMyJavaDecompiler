package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantDouble extends JavaPoolEntry {

    private double value;

    @Override
    public Result read() throws IOException {
        value = bytes.readDouble();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
