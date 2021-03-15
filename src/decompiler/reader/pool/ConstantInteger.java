package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantInteger extends RawConstant {

    private int value;

    @Override
    public Result read() throws IOException {
        value = bytes.readInt();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "{Integer} \t" + value;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "" + value;
    //}


    @Override
    public Object getValue() {
        return value;
    }
}
