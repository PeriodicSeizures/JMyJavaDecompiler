package decompiler.classfile.attributes;

import decompiler.Result;

import java.io.IOException;

public class EnclosingMethodAttribute extends JavaAttribute {

    private int class_index;
    private int method_index;

    @Override
    public Result read() throws IOException {

        class_index = bytes.readUnsignedShort();
        method_index = bytes.readUnsignedShort();

        return Result.OK;
    }

    @Override
    public String toString() {
        return "{EnclosingMethod} ";
    }
}
