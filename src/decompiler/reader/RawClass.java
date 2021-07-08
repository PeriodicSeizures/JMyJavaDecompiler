package decompiler.reader;

import decompiler.Result;

import java.io.IOException;

@SuppressWarnings("FieldCanBeLocal")
public class RawClass extends RawItem {

    private static final int ACC_PUBLIC =        0x0001;
    private static final int ACC_FINAL =         0x0010;
    private static final int ACC_SUPER =         0x0020;
    private static final int ACC_INTERFACE =     0x0200;
    private static final int ACC_ABSTRACT =      0x0400;
    private static final int ACC_SYNTHETIC =     0x1000;
    private static final int ACC_ANNOTATION =    0x2000;
    private static final int ACC_ENUM =          0x4000;

    private int minor_version;
    private int major_version;
    private String class_version;

    public ConstantContainer constantPoolContainer = new ConstantContainer();
    private int access_flags;
    public int this_class;
    private int super_class;
    public InterfaceContainer interfaceContainer = new InterfaceContainer();
    public FieldContainer fieldContainer = new FieldContainer();
    public MethodContainer methodContainer = new MethodContainer();
    public AttributeContainer attributeContainer = new AttributeContainer();

    private Result temp;

    public Result read() throws IOException {
        minor_version = bytes.readUnsignedShort();
        major_version = bytes.readUnsignedShort();

        class_version = major_version + "." + minor_version;

        //System.out.println("class version: " + class_version);

        if ((temp = constantPoolContainer.read()) != Result.OK) return temp;
        access_flags = bytes.readUnsignedShort();
        this_class = bytes.readUnsignedShort();
        super_class = bytes.readUnsignedShort();

        interfaceContainer.read();
        fieldContainer.read();
        methodContainer.read();
        attributeContainer.read();

        //System.out.println(constantPoolContainer);
        //System.out.println(attributeContainer);
        //System.out.println(methodContainer);

        return Result.OK;
    }

    public boolean isInterface() {
        return (access_flags & ACC_INTERFACE) == ACC_INTERFACE;
    }

    public boolean isAbstractClass() {
        return (access_flags & ACC_ABSTRACT) == ACC_ABSTRACT && !isInterface();
    }

    public boolean isAnnotation() {
        return (access_flags & ACC_ANNOTATION) == ACC_ANNOTATION;
    }

    public boolean isEnum() {
        return (access_flags & ACC_ENUM) == ACC_ENUM;
    }

    public String getAccessFlags() {
        StringBuilder s = new StringBuilder();
        if ((access_flags & ACC_PUBLIC) == ACC_PUBLIC)
            s.append("public ");

        if ((access_flags & ACC_FINAL) == ACC_FINAL)
            s.append("final ");

        if (isAbstractClass())
            s.append("abstract class");
        else if (isInterface())
            s.append("interface ");
        else if (isAnnotation())
            s.append("@interface ");
        else if (isEnum())
            s.append("enum ");
        else
            s.append("class ");

        return s.toString().trim();
    }

    public int getMinorVersion() {
        return minor_version;
    }

    public int getMajorVersion() {
        return major_version;
    }

    public String getClassVersion() {
        return class_version;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return getEntry(this_class).toJavaSourceCode(context); //.toString();

    //}

    public String getName() {
        return (String) getEntry(this_class).getValue();
    }

    public String getSuperClassName() {
        if (super_class != 0)
            return (String) getEntry(super_class).getValue();
        return "Object"; // must then directly inherit from Object
    }

    public String[] getInterfaces() {
        return interfaceContainer.getInterfaces();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{RawClass} ").append("\n");
        stringBuilder.append("class version: ").append(class_version).append("\n");
        stringBuilder.append("class: ").append(getName()).append("\n");
        stringBuilder.append("super class: ").append(getSuperClassName()).append("\n");
        stringBuilder.append(interfaceContainer).append("\n");
        stringBuilder.append(constantPoolContainer).append("\n");
        stringBuilder.append(fieldContainer).append("\n");
        stringBuilder.append(methodContainer).append("\n");
        stringBuilder.append(attributeContainer).append("\n");

        return stringBuilder.toString();
    }
}
