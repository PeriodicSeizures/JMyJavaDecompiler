package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantFloat extends RawConstant {

    private float value;

    @Override
    public Result read() throws IOException {
        value = bytes.readFloat();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "{Float} \t" + value;
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
