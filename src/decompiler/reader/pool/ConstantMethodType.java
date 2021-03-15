package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantMethodType extends RawConstant {

    private int descriptor_index;

    @Override
    public Result read() throws IOException {
        descriptor_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "";
    //}

    @Override
    public String toString() {
        return "{MethodType} \tdescriptor_index: " + descriptor_index + " (" + getEntry(descriptor_index) + ")";
    }
}
