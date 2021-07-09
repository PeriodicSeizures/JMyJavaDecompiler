package decompiler.reader.pool;

import java.io.IOException;

public class ConstantMethodType extends RawConstant {

    private int descriptor_index;

    @Override
    public void read() throws IOException {
        descriptor_index = bytes.readUnsignedShort();
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "";
    //}

    @Override
    public String toString() {
        return "{MethodType} \tdescriptor_index: " + descriptor_index + " (" + getEntry(descriptor_index) + ")";
    }
}
