package decompiler.reader.pool;

import java.io.IOException;

public class ConstantUtf8 extends RawConstant {

    private String s;

    @Override
    public void read() throws IOException {

        s = bytes.readUTF(); // works as intended
    }

    @Override
    public String toString() {
        return "{Utf8} \t" + s;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return s;
    //}


    @Override
    public Object getValue() {
        return s;
    }
}
