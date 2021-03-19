package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantFieldref extends RawConstant {

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
        return "{Fieldref} \tclass: " + getEntry(class_index).getValue() + ", " + getEntry(name_and_type_index);
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    String declaration = getEntry(name_and_type_index).toString();

    //    //System.out.println("DECLARA");

    //    return declaration;
    //}


    @Override
    public String getName() {
        return getEntry(name_and_type_index).getName();
    }
}
