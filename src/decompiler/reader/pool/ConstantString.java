package decompiler.reader.pool;

import java.io.IOException;

public class ConstantString extends RawConstant {

    private int string_index;

    @Override
    public void read() throws IOException {
        string_index = bytes.readUnsignedShort();
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return getEntry(string_index).toString();
    //}

    @Override
    public String toString() {
        return "{String} \tstring_index: " + string_index + " (" + getEntry(string_index) + ")";
    }

    @Override
    public Object getValue() {
        return getEntry(string_index).getValue();
    }
}
