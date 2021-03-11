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
    public String toString() {

        String name = getEntry(name_index).toString();

        String type = getEntry(name_index).toString();

        return type + " " + name;
    }
}
