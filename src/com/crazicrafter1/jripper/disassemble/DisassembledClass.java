package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.types.IFieldDefined;
import com.crazicrafter1.jripper.types.IMethodDefined;
import com.crazicrafter1.jripper.util.ByteReader;
import com.crazicrafter1.jripper.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public final class DisassembledClass
        extends IDisassembled
        implements IClassDefined {

    private static final int ACC_PUBLIC =        0x0001;
    private static final int ACC_FINAL =         0x0010;
    private static final int ACC_SUPER =         0x0020; // exists for backward compatibility with code compiled by older compilers
    private static final int ACC_INTERFACE =     0x0200;
    private static final int ACC_ABSTRACT =      0x0400;
    private static final int ACC_SYNTHETIC =     0x1000; /* not really needed */
    private static final int ACC_ANNOTATION =    0x2000;
    private static final int ACC_ENUM =          0x4000;

    /*
     * The stuff that makes up a given class file
     */
    private String class_version;

    public ConstantContainer constantPoolContainer = new ConstantContainer(this);
    private int access_flags;
    private int this_class;
    private int super_class;
    public InterfaceContainer interfaceContainer = new InterfaceContainer(this);
    public FieldContainer fieldContainer = new FieldContainer(this);
    public MethodContainer methodContainer = new MethodContainer(this);
    public AttributeContainer attributeContainer = new AttributeContainer(this);

    private final Set<String> importSet = new LinkedHashSet<>();

    DisassembledClass() {
        super(null);
    }

    protected void read(ByteReader bytes) throws IOException {
        if (!bytes.compareNext((byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE))
            throw new RuntimeException("No magic header found");

        int minor_version = bytes.readUnsignedShort();
        int major_version = bytes.readUnsignedShort();

        class_version = major_version + "." + minor_version;

        constantPoolContainer.read(bytes);
        access_flags = bytes.readUnsignedShort();
        this_class = bytes.readUnsignedShort();
        super_class = bytes.readUnsignedShort();
        interfaceContainer.read(bytes);
        fieldContainer.read(bytes);
        methodContainer.read(bytes);
        attributeContainer.read(bytes);
    }

    @Override
    public IFieldDefined getIField(UUID uuid) {
        return fieldContainer.getFields().get(uuid);
    }

    @Override
    public IMethodDefined getIMethod(UUID uuid) {
        return methodContainer.getMethods().get(uuid);
    }

    @Override
    public String getName() {
        return (String) getConstant(this_class).get();
    }

    @Override
    public String getPackageName() {
        String[] pk = Util.getBinaryPackageAndClass(this.getName());
        return pk[0];
    }

    @Override
    public String getSimpleName() {
        String[] pk = Util.getBinaryPackageAndClass(this.getName());
        return pk[1];
    }

    @Override
    public Set<String> getImportSet() {
        return importSet;
    }

    @Override
    public boolean isPublic() {
        return (access_flags & ACC_PUBLIC) == ACC_PUBLIC;
    }

    @Override
    public boolean isInterface() {
        return (access_flags & ACC_INTERFACE) == ACC_INTERFACE;
    }

    @Override
    public boolean isAbstractClass() {
        return (access_flags & ACC_ABSTRACT) == ACC_ABSTRACT && !isInterface();
    }

    @Override
    public boolean isAnnotation() {
        return (access_flags & ACC_ANNOTATION) == ACC_ANNOTATION;
    }

    @Override
    public boolean isEnum() {
        return (access_flags & ACC_ENUM) == ACC_ENUM;
    }

    @Override
    public boolean isFinal() {
        return (access_flags & ACC_FINAL) == ACC_FINAL;
    }

    @Override
    public boolean isSuper() {
        return (access_flags & ACC_SUPER) == ACC_SUPER;
    }

    public String getClassVersion() {
        return class_version;
    }

    /**
     * Get the super class name
     * @return Return 'java/lang/Object' or the class binary name
     */
    @Override
    public String getSuperName() {
        if (super_class != 0)
            return (String) getConstant(super_class).get();
        return OBJECT_CLASS.getName();
    }

    @Override
    public ArrayList<String> getInterfaces() {
        return interfaceContainer.getInterfaces();
    }

    //@Override
    //public IBaseClass getSuperClass() {
    //    throw new NoUsageException("Not implemented");
    //}

    @Override
    public String toString() {
        return  "class version: " + class_version + "\n" +
                "class: " + getName() + "\n" +
                "super class: " + getSuperName() + "\n" +
                interfaceContainer + "\n" +
                constantPoolContainer + "\n" +
                fieldContainer + "\n" +
                methodContainer + "\n" +
                attributeContainer + "\n";
    }
}
