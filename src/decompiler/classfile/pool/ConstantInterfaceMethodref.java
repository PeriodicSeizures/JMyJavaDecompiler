package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantInterfaceMethodref extends JavaPoolEntry {
    private int class_index; // ok to be signed for easy indexing
    private int name_and_type_index;

    @Override
    public Result read() throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return "";
    }

    @Override
    public String toString() {
        return "{IMref} \tclass_index: " + class_index + " (" + getEntry(class_index) + ") " +
                "name_and_type_index: " + name_and_type_index + " (" + getEntry(name_and_type_index) + ")";
    }
}
