package decompiler.reader.pool;

import java.io.IOException;

public class ConstantInteger extends RawConstant {

    private int value;

    @Override
    public void read() throws IOException {
        value = bytes.readInt();
    }

    @Override
    public String toString() {
        return "{Integer} \t" + value;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "" + value;
    //}


    @Override
    public Object getValue() {
        return value;
    }
}
