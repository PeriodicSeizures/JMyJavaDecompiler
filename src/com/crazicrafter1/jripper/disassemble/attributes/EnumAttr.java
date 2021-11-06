package com.crazicrafter1.jripper.disassemble.attributes;

public enum EnumAttr {

    /*
        only JVM required attributes are included
        - whatever the machine requires to run is needed to reverse engineer
          a program
     */

    ConstantValue(ConstantValueAttr.class, 45, 3),   // required
    Code(CodeAttr.class, 45, 3),            // required
    //StackMapTable(null, 50, 0),
    Exceptions(ExceptionsAttr.class, 45, 3),      // required
    BootstrapMethods(BootstrapMethodsAttr.class, 51, 0),

    //Synthetic(null, 45, 3),
    //SourceFile(null, 45, 3), // the original file name
    //SourceDebugExtension(null, 49, 0), // debugging information
    //LineNumberTable(null, 45, 3),
    //LocalVariableTable(LocalVariableTableAttribute.class, 45, 3),
    //InnerClasses(InnerClassesAttribute.class, 45, 3),
    //EnclosingMethod(EnclosingMethodAttribute.class, 49, 0),
    //Signature(SignatureAttribute.class, 49, 0),
    //LocalVariableTypeTable(null, 49, 0),
    //Deprecated(null, 45, 3),
    //RuntimeVisibleAnnotations(null, 49, 0),           // *
    //RuntimeInvisibleAnnotations(null, 49, 0),         // *
    //RuntimeVisibleParameterAnnotations(null, 49, 0),  // *
    //RuntimeInvisibleParameterAnnotations(null, 49, 0),// *
    //AnnotationDefault(null, 49, 0),                   // *
    //MethodParameters(null, 52, 0) // JAVA SE 8
    ;

    public final Class<? extends IAttr> clazz;
    public final int major;
    public final int minor;

    EnumAttr(Class<? extends IAttr> clazz, int major, int minor) {
        this.clazz = clazz;
        this.major = major;
        this.minor = minor;
    }
}
