package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantNameAndType extends JavaPoolEntry {

    private int name_index;
    private int descriptor_index;

    @Override
    public Result read() throws IOException {
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        String name = getEntry(name_index).toString();

        String type = getEntry(name_index).toString();

        return type + " " + name;
    }

    @Override
    public String toString() {
        return "{NameAndType} \tname_index: " + name_index + " (" + getEntry(name_index) + ") " +
                "descriptor_index: " + descriptor_index + " (" + getEntry(descriptor_index) + ")";
    }
}
