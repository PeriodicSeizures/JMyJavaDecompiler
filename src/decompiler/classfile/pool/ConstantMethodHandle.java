package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantMethodHandle extends JavaPoolEntry {

    private int reference_kind;
    private int reference_index;

    @Override
    public Result read() throws IOException {
        reference_kind = bytes.readUnsignedByte();
        reference_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return "";
    }

    @Override
    public String toString() {
        return "{MethodHandle} \treference_kind: " + reference_kind;
    }
}
