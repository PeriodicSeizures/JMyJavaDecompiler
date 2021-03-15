package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantString extends RawConstant {

    private int string_index;

    @Override
    public Result read() throws IOException {
        string_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return getEntry(string_index).toString();
    //}

    @Override
    public String toString() {
        return "{String} \tstring_index: " + string_index + " (" + getEntry(string_index) + ")";
    }

    @Override
    public Object getValue() {
        return getEntry(string_index).getValue();
    }
}
