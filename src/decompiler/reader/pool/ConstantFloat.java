package decompiler.reader.pool;

import java.io.IOException;

public class ConstantFloat extends RawConstant {

    private float value;

    @Override
    public void read() throws IOException {
        value = bytes.readFloat();
    }

    @Override
    public String toString() {
        return "{Float} \t" + value;
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
