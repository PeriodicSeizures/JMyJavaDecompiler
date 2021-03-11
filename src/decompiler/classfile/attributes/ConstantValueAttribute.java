package decompiler.classfile.attributes;

import decompiler.Result;

import java.io.IOException;

public class ConstantValueAttribute extends JavaAttribute {

    private int constantvalue_index;

    @Override
    public Result read() throws IOException {
        constantvalue_index = bytes.readUnsignedShort();
        return Result.OK;
    }
}
