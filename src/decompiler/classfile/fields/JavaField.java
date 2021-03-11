package decompiler.classfile.fields;

import decompiler.Result;
import decompiler.classfile.AttributeContainer;
import decompiler.classfile.JavaItem;
import decompiler.classfile.attributes.JavaAttribute;

import java.io.IOException;
import java.util.ArrayList;

public class JavaField extends JavaItem {

    /*
        field access flags
     */
    public static final int ACC_PUBLIC =    0x0001;
    public static final int ACC_PRIVATE =   0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC =    0x0008;
    public static final int ACC_FINAL =     0x0010;
    public static final int ACC_VOLATILE =  0x0040;
    public static final int ACC_TRANSIENT = 0x0080;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ENUM =      0x4000;

    /*
        field members
     */
    private int access_flags;
    private int name_index;
    private int descriptor_index;

    private AttributeContainer attribute_container = new AttributeContainer();

    @Override
    public Result read() throws IOException {
        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attribute_container.read();

        return Result.OK;
    }
// volatile public static final transient Object
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if ((access_flags & ACC_VOLATILE) == ACC_VOLATILE)
            s.append("volatile ");

        if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
            s.append("public ");
        else if ((access_flags & ACC_PRIVATE) == ACC_PRIVATE)
            s.append("private ");
        else if ((access_flags & ACC_PROTECTED) == ACC_PROTECTED)
            s.append("protected ");

        if ((access_flags & ACC_STATIC) == ACC_STATIC)
            s.append("static ");

        if ((access_flags & ACC_FINAL) == ACC_FINAL)
            s.append("final ");

        else if ((access_flags & ACC_TRANSIENT) == ACC_TRANSIENT)
            s.append("transient ");

        s.append(currentClassInstance.constantPoolContainer.getEntry(descriptor_index)).append(" ");
        s.append(currentClassInstance.constantPoolContainer.getEntry(name_index)).append("\n");

        return s.toString();
    }
}
