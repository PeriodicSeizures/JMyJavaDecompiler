package decompiler.reader.attributes;

import java.io.IOException;

public class ConstantValueAttribute extends RawAttribute {

    private int constantvalue_index;

    @Override
    public void read() throws IOException {
        constantvalue_index = bytes.readUnsignedShort();
    }

    @Override
    public String toString() {
        return "{ConstantValue} constantvalue_index: " + constantvalue_index + " (" + getEntry(constantvalue_index) + ")";
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return getEntry(constantvalue_index).toJavaSourceCode(context);
    //}
}
