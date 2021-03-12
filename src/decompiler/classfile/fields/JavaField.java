package decompiler.classfile.fields;

import decompiler.Result;
import decompiler.classfile.AttributeContainer;
import decompiler.classfile.JavaItem;
import decompiler.classfile.attributes.JavaAttribute;

import java.io.IOException;

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

    private AttributeContainer attributeContainer = new AttributeContainer();

    @Override
    public Result read() throws IOException {
        access_flags = bytes.readUnsignedShort();
        name_index = bytes.readUnsignedShort();
        descriptor_index = bytes.readUnsignedShort();

        attributeContainer.read();

        return Result.OK;
    }

    private String getSimpleType(int class_index) {
        String descriptor = getEntry(descriptor_index).toJavaSourceCode(class_index);

        StringBuilder builder = new StringBuilder();
        StringBuilder arrayBuilder = new StringBuilder();

        int at = 0;
        for (; at<descriptor.codePointCount(0, descriptor.length()-1); at++) {
            //System.out.println("found: " + pt);
            if (descriptor.codePointAt(at) != '[')
                break;
            arrayBuilder.append("[]");
        }

        // get the object name from [at, end];
        switch(descriptor.codePointAt(at)) {
            case 'B': builder.insert(0, "byte"); break;
            case 'C': builder.insert(0, "char"); break;
            case 'D': builder.insert(0, "double"); break;
            case 'F': builder.insert(0, "float"); break;
            case 'I': builder.insert(0, "int"); break;
            case 'J': builder.insert(0, "long"); break;
            case 'S': builder.insert(0, "short"); break;
            case 'Z': builder.insert(0, "boolean"); break;
            case 'L': // is of an object
                String[] packageAndClass = JavaItem.getSimplePackageAndClass(descriptor.substring(at+1, descriptor.length()-1));

                builder.append(packageAndClass[1]);
        }

        builder.append(arrayBuilder);

        return builder.toString();
    }

    @Override
    public String toJavaSourceCode(int class_index) {
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

        s.append(getSimpleType(class_index)).append(" "); // Object
        s.append(JavaItem.getUnqualifiedName(getEntry(name_index).toJavaSourceCode(class_index))); // obj

        /*
            ONLY STATIC FIELDS CAN HAVE A ConstantValue attribute
         */
        JavaAttribute attribute = attributeContainer.get(JavaAttribute.Attribute.ConstantValue);
        if (((access_flags & ACC_STATIC) == ACC_STATIC) && attribute != null)
            s.append(" = ").append(attribute.toJavaSourceCode(class_index));
        //else System.out.println("WAS NULL (value)");

        //System.out.println(attributeContainer.attribute_map.size());

        return s.toString();
    }

    // volatile public static final transient Object
    @Override
    public String toString() {
        return "{JavaField} \tname_index: " + name_index;
    }

}
