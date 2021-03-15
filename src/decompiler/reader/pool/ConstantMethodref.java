package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantMethodref extends RawConstant {
    private int class_index;
    private int name_and_type_index;

    @Override
    public Result read() throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "";
    //}

    @Override
    public String toString() {
        return "{Methodref} \tclass: " + getEntry(class_index).getValue() + ", " + getEntry(name_and_type_index);
    }
}
