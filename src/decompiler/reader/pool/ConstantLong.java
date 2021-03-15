package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantLong extends RawConstant {

    private long value;

    @Override
    public Result read() throws IOException {
        value = bytes.readLong();
        return Result.OK;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "" + value;
    //}

    @Override
    public String toString() {
        return "{Long} \t" + value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
