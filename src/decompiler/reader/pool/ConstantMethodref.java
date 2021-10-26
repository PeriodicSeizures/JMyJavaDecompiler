package decompiler.reader.pool;

import java.io.IOException;

public class ConstantMethodref extends RawConstant {
    private int class_index;
    private int name_and_type_index;

    @Override
    public void read() throws IOException {
        class_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return "";
    //}

    @Override
    public String toString() {
        return "{Methodref} \tclass: " + getEntry(class_index).getName() + ", " + getEntry(name_and_type_index);
    }
}
