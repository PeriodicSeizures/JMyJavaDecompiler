package decompiler.reader.pool;

import java.io.IOException;

public class ConstantLong extends RawConstant {

    private long value;

    @Override
    public void read() throws IOException {
        value = bytes.readLong();
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "" + value;
    //}

    @Override
    public String toString() {
        return "{Long} \t" + value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
