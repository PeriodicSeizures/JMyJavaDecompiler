package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantInteger extends JavaPoolEntry {

    private int value;

    @Override
    public Result read() throws IOException {
        value = bytes.readInt();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "{ConstantInteger} \t" + value;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return "" + value;
    }
}
