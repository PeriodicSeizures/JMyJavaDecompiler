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
        return currentClassInstance.constantPoolContainer.getEntry(name_index).toString();
        //return "ConstantClass{}";
    }
}
