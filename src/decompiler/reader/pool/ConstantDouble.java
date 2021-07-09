package decompiler.reader.pool;

import java.io.IOException;

public class ConstantDouble extends RawConstant {

    private double value;

    @Override
    public void read() throws IOException {
        value = bytes.readDouble();
    }

    @Override
    public String toString() {
        return "{Double} \t" + value;
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
