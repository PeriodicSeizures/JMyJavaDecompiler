package decompiler;

import decompiler.classfile.*;
import decompiler.classfile.attributes.JavaAttribute;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class JavaClassFile extends JavaItem {

    public static final int ACC_PUBLIC =        0x0001;
    public static final int ACC_FINAL =         0x0010;
    public static final int ACC_SUPER =         0x0020;
    public static final int ACC_INTERFACE =     0x0200;
    public static final int ACC_ABSTRACT =      0x0400;
    public static final int ACC_SYNTHETIC =     0x1000;
    public static final int ACC_ANNOTATION =    0x2000;
    public static final int ACC_ENUM =          0x4000;

    private int minor_version;
    private int major_version;
    private String class_version;

    public ConstantPoolContainer constantPoolContainer = new ConstantPoolContainer();
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

        System.out.println("class version: " + class_version);

        if ((temp = constantPoolContainer.read()) != Result.OK) return temp;
        access_flags = bytes.readUnsignedShort();
        this_class = bytes.readUnsignedShort();
        super_class = bytes.readUnsignedShort();

        interfaceContainer.read();
        fieldContainer.read();
        methodContainer.read();
        attributeContainer.read();

        System.out.println(constantPoolContainer);
        //System.out.println(methodContainer);

        return Result.OK;
    }

    public String getSuperClassName() {
        if (super_class != 0)
            return getEntry(super_class).toJavaSourceCode(this_class);
        return "Object";
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

    public int getMinorVersion() {
        return minor_version;
    }

    public int getMajorVersion() {
        return major_version;
    }

    public String getClassVersion() {
        return class_version;
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

        return s.toString();
    }

    @Override
    public String toJavaSourceCode(int class_index) {
        return getEntry(this_class).toJavaSourceCode(class_index); //.toString();

    }

    @Override
    public String toString() {
        return "{JavaClassFile} ";
    }
}
