package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantDouble extends RawConstant {

    private double value;

    @Override
    public Result read() throws IOException {
        value = bytes.readDouble();
        return Result.OK;
    }

    @Override
    public String toString() {
        return "{Double} \t" + value;
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
