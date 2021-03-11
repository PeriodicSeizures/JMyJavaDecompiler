package decompiler.classfile.methods;

import decompiler.Result;
import decompiler.classfile.AttributeContainer;
import decompiler.classfile.JavaItem;
import decompiler.classfile.pool.ConstantUtf8;

import java.io.IOException;

public class JavaMethod extends JavaItem {

    /*
        method access flags
     */
    public static final int ACC_PUBLIC =        0x0001;
    public static final int ACC_PRIVATE =       0x0002;
    public static final int ACC_PROTECTED =     0x0004;
    public static final int ACC_STATIC =        0x0008;
    public static final int ACC_FINAL =         0x0010;
    public static final int ACC_SYNCHRONIZED =  0x0020;
    public static final int ACC_BRIDGE =        0x0040;
    public static final int ACC_VARARGS =       0x0080;
    public static final int ACC_NATIVE =        0x0100;
    public static final int ACC_ABSTRACT =      0x0400;
    public static final int ACC_STRICT =        0x0800;
    public static final int ACC_SYNTHETIC =     0x1000;

    /*
        method members
    */
    private int access_flags;
    private int name_index;
    private int descriptor_index;
    private AttributeContainer attributeContainer = new AttributeContainer();

    @Override
    public Result read() throws IOException {
        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read();

        return Result.OK;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        String methodName = ((ConstantUtf8)currentClassInstance.constantPoolContainer.getEntry(name_index)).s;

        //if (methodName.equals("<init>")) {
        //    currentClassInstance.
        //    s.append();
        //} else {
//
        //}

        s.append(methodName);

        return s.toString();
    }
}
