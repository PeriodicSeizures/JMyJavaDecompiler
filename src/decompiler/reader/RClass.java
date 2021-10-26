package decompiler.reader;

import java.io.IOException;

public class RClass extends RItem {

    private static final int ACC_PUBLIC =        0x0001;
    private static final int ACC_FINAL =         0x0010;
    private static final int ACC_SUPER =         0x0020;
    private static final int ACC_INTERFACE =     0x0200;
    private static final int ACC_ABSTRACT =      0x0400;
    private static final int ACC_SYNTHETIC =     0x1000; /* not really needed */
    private static final int ACC_ANNOTATION =    0x2000;
    private static final int ACC_ENUM =          0x4000;

    /*
     * The stuff that makes up a given class file
     */
    private String class_version;

    public ConstantContainer constantPoolContainer = new ConstantContainer();
    private int access_flags;
    public int this_class;
    private int super_class;
    public InterfaceContainer interfaceContainer = new InterfaceContainer();
    public FieldContainer fieldContainer = new FieldContainer();
    public MethodContainer methodContainer = new MethodContainer();
    public AttributeContainer attributeContainer = new AttributeContainer();

    public void read() throws IOException {
        int minor_version = bytes.readUnsignedShort();
        int major_version = bytes.readUnsignedShort();

        class_version = major_version + "." + minor_version;

        // error var
        constantPoolContainer.read();
        access_flags = bytes.readUnsignedShort();
        this_class = bytes.readUnsignedShort();
        super_class = bytes.readUnsignedShort();

        interfaceContainer.read();
        fieldContainer.read();
        methodContainer.read();
        attributeContainer.read();
    }

    private boolean isInterface() {
        return (access_flags & ACC_INTERFACE) == ACC_INTERFACE;
    }

    private boolean isAbstractClass() {
        return (access_flags & ACC_ABSTRACT) == ACC_ABSTRACT && !isInterface();
    }

    private boolean isAnnotation() {
        return (access_flags & ACC_ANNOTATION) == ACC_ANNOTATION;
    }

    private boolean isEnum() {
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
        else // ordinary class
            s.append("class ");

        return s.toString().trim();
    }

    public String getClassVersion() {
        return class_version;
    }

    /**
     * @return the fully qualified name (package.etc.clazz)
     */
    @Override
    public String getName() {
        return getEntry(this_class).getName();
    }

    public String getSuperClassName() {
        if (super_class != 0)
            return getEntry(super_class).getName();
        return "Object"; // must then directly inherit from Object
    }

    public String[] getInterfaces() {
        return interfaceContainer.getInterfaces();
    }

    @Override
    public String toString() {

        return "{RawClass} " + "\n" +
                "class version: " + class_version + "\n" +
                "class: " + getName() + "\n" +
                "super class: " + getSuperClassName() + "\n" +
                interfaceContainer + "\n" +
                constantPoolContainer + "\n" +
                fieldContainer + "\n" +
                methodContainer + "\n" +
                attributeContainer + "\n";
    }



}
