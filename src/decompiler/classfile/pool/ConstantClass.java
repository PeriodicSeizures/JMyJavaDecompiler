package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantClass extends JavaPoolEntry {

    private int name_index;

    @Override
    public Result read() throws IOException {
        // read name index
        name_index = bytes.readUnsignedShort();

        //System.out.println(", name_index: " + name_index);

        return Result.OK;
    }

    // returns the name of the class from name_index
    @Override
    public String toString() {
        return "{ConstantClass} \tname_index: " + name_index + " (" + getEntry(name_index).toString() + ")";
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return getEntry(name_index).toJavaSourceCode(class_index);
    }
}
