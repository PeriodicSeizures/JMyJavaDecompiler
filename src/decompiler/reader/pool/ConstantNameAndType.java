package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantNameAndType extends RawConstant {

    private int name_index;
    private int descriptor_index;

    @Override
    public Result read() throws IOException {
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    String name = getEntry(name_index).toString();

    //    String type = getEntry(name_index).toString();

    //    return type + " " + name;
    //}

    @Override
    public String toString() {
        return "{NameAndType} \t" + getEntry(name_index).getValue() + ", " + getEntry(descriptor_index).getValue();
    }
}
