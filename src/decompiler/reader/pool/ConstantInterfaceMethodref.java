package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantInterfaceMethodref extends RawConstant {
    private int class_index; // ok to be signed for easy indexing
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
        return "{IMref} \t" + getEntry(class_index).getValue() + ", " + getEntry(name_and_type_index) + ")";
    }

    public ConstantClass getConstantClass() {
        return (ConstantClass)getEntry(class_index);
    }
}
