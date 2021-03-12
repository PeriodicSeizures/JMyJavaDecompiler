package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantFloat extends JavaPoolEntry {

    private float value;

    @Override
    public Result read() throws IOException {
        value = bytes.readFloat();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "{ConstantFloat} \t" + value;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return "" + value;

    }
}
