package decompiler.classfile.methods;

import decompiler.Result;
import decompiler.classfile.AttributeContainer;
import decompiler.classfile.JavaItem;
import decompiler.classfile.attributes.JavaAttribute;
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

    // uses the descriptor
    private String getReturnType() {

        StringBuilder builder = new StringBuilder();

        String r = getEntry(descriptor_index).toString();
        switch (r.charAt(2)) {
            case 'B': return "byte";
            case 'C': return "char";
            case 'D': return "double";
            case 'F': return "float";
            case 'I': return "int";
            case 'J': return "long";
            case 'S': return "short";
            case 'Z': return "boolean";
            case 'V': return "void";
            case 'L': {
                String[] sp = JavaItem.getSimplePackageAndClass(r.substring(1));

                return sp[1];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "{JavaMethod} " + attributeContainer.get(JavaAttribute.Attribute.Code);
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        StringBuilder s = new StringBuilder();

        //System.out.println("mdescriptor" + get(descriptor_index));

        String name = getEntry(name_index).toString();
        // 60, 62 < >

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

        if ((access_flags & ACC_ABSTRACT) == ACC_ABSTRACT)
            s.append("abstract ");

        if ((access_flags & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED)
            s.append("synchronized ");

        s.append(getReturnType()).append(" ");
        s.append(JavaItem.getUnqualifiedName(name));

        return s.toString();
    }

    public String getName() {
        return getEntry(name_index).toString();
    }
}
