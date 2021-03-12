package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantFieldref extends JavaPoolEntry {

    private int class_index;
    private int name_and_type_index;

    //static int count = 0;

    @Override
    public Result read() throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
        //count++;
        return Result.OK;
    }

    @Override
    public String toString() {

        String declaration = getEntry(name_and_type_index).toString();

        //System.out.println("DECLARA");

        return declaration;
    }
}
