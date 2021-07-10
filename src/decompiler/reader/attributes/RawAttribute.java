package decompiler.reader.attributes;

import decompiler.reader.RItem;

public abstract class RawAttribute extends RItem {

    public enum Attribute {

        /*
            only JVM required attributes are included
            - whatever the machine requires to run is needed to reverse engineer
              a program
         */

        ConstantValue(ConstantValueAttribute.class, 45, 3),   // required
        Code(CodeAttribute.class, 45, 3),            // required
        //StackMapTable(null, 50, 0),
        Exceptions(ExceptionsAttribute.class, 45, 3),      // required
        BootstrapMethods(BootstrapMethodsAttribute.class, 51, 0),

        //Synthetic(null, 45, 3),
        //SourceFile(null, 45, 3), // the original file name
        //SourceDebugExtension(null, 49, 0), // debugging information
        //LineNumberTable(null, 45, 3),
        LocalVariableTable(LocalVariableTableAttribute.class, 45, 3),
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

        public final Class clazz;
        public final int major;
        public final int minor;

        Attribute(Class clazz, int major, int minor) {
            this.clazz = clazz;
            this.major = major;
            this.minor = minor;
        }
    }
}
