package decompiler.reader.pool;

import java.io.IOException;

public class ConstantClass extends RawConstant {

    private int name_index;

    @Override
    public void read() throws IOException {
        // read name index
        name_index = bytes.readUnsignedShort();
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
    public String getName() {
        return (String) getEntry(name_index).getValue();
    }
}
