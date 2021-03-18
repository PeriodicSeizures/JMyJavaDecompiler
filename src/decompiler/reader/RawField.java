package decompiler.reader;

import decompiler.Result;

import java.io.IOException;

public class RawField extends RawItem {

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

    //private String getSimpleType(int class_index) {
    //    String descriptor = getEntry(descriptor_index).toJavaSourceCode(class_index);

    //    StringBuilder builder = new StringBuilder();
    //    StringBuilder arrayBuilder = new StringBuilder();

    //    int at = 0;
    //    for (; at<descriptor.codePointCount(0, descriptor.length()-1); at++) {
    //        //System.out.println("found: " + pt);
    //        if (descriptor.codePointAt(at) != '[')
    //            break;
    //        arrayBuilder.append("[]");
    //    }

    //    // get the object name from [at, end];
    //    switch(descriptor.codePointAt(at)) {
    //        case 'B': builder.insert(0, "byte"); break;
    //        case 'C': builder.insert(0, "char"); break;
    //        case 'D': builder.insert(0, "double"); break;
    //        case 'F': builder.insert(0, "float"); break;
    //        case 'I': builder.insert(0, "int"); break;
    //        case 'J': builder.insert(0, "long"); break;
    //        case 'S': builder.insert(0, "short"); break;
    //        case 'Z': builder.insert(0, "boolean"); break;
    //        case 'L': // is of an object
    //            String[] packageAndClass = RawItem.getPackageAndClass(descriptor.substring(at+1, descriptor.length()-1));

    //            builder.append(packageAndClass[1]);
    //    }

    //    builder.append(arrayBuilder);

    //    return builder.toString();
    //}

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    StringBuilder s = new StringBuilder();

    //    if ((access_flags & ACC_VOLATILE) == ACC_VOLATILE)
    //        s.append("volatile ");

    //    if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
    //        s.append("public ");
    //    else if ((access_flags & ACC_PRIVATE) == ACC_PRIVATE)
    //        s.append("private ");
    //    else if ((access_flags & ACC_PROTECTED) == ACC_PROTECTED)
    //        s.append("protected ");

    //    if ((access_flags & ACC_STATIC) == ACC_STATIC)
    //        s.append("static ");

    //    if ((access_flags & ACC_FINAL) == ACC_FINAL)
    //        s.append("final ");

    //    else if ((access_flags & ACC_TRANSIENT) == ACC_TRANSIENT)
    //        s.append("transient ");

    //    //s.append(getSimpleType(context)).append(" "); // Object
    //    s.append(RawItem.getUnqualifiedName(getEntry(name_index).toJavaSourceCode(context))); // obj

    //    /*
    //        ONLY STATIC FIELDS CAN HAVE A ConstantValue attribute
    //     */
    //    RawAttribute attribute = attributeContainer.get(RawAttribute.Attribute.ConstantValue);
    //    if (((access_flags & ACC_STATIC) == ACC_STATIC) && attribute != null)
    //        s.append(" = ").append(attribute.toJavaSourceCode(context));
    //    //else System.out.println("WAS NULL (value)");

    //    //System.out.println(attributeContainer.attribute_map.size());

    //    return s.toString();
    //}

    public boolean isStatic() {
        return (access_flags & ACC_STATIC) == ACC_STATIC;
    }

    public boolean isFinal() {
        return (access_flags & ACC_FINAL) == ACC_FINAL;
    }

    public boolean isVolatile() {
        return (access_flags & ACC_VOLATILE) == ACC_VOLATILE;
    }

    public boolean isTransient() {
        return (access_flags & ACC_TRANSIENT) == ACC_TRANSIENT;
    }

    public boolean isEnum() {
        return (access_flags & ACC_ENUM) == ACC_ENUM;
    }

    public String getAccessFlags() {
        StringBuilder s = new StringBuilder();

        if (isVolatile())
            s.append("volatile ");

        if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
            s.append("public ");
        else if ((access_flags & ACC_PRIVATE) == ACC_PRIVATE)
            s.append("private ");
        else if ((access_flags & ACC_PROTECTED) == ACC_PROTECTED)
            s.append("protected ");

        if (isStatic())
            s.append("static ");

        if (isFinal())
            s.append("final ");

        if (isTransient())
            s.append("transient ");

        return s.toString();
    }

    //// volatile public static final transient Object
    @Override
    public String toString() {
        return "{RawField} \tname_index: " + name_index;
    }

    public String getName() {
        return (String)getEntry(name_index).getValue();
    }

    public String getDescriptor() {
        return (String) getEntry(descriptor_index).getValue();
    }

}
