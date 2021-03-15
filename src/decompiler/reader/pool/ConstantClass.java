package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantClass extends RawConstant {

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
        return "{Class} \t" + getEntry(name_index).getValue();
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return getEntry(name_index).toJavaSourceCode(context);
    //}


    @Override
    public Object getValue() {
        return getEntry(name_index).getValue();
    }
}
