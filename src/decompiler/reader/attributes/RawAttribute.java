package decompiler.reader.attributes;

import decompiler.reader.RawItem;

public abstract class RawAttribute extends RawItem {

    public enum Attribute {

        /*
            all useless attributes used for debugging have been omitted
         */

        ConstantValue(ConstantValueAttribute.class, 45, 3),   // required
        Code(CodeAttribute.class, 45, 3),            // required
        //StackMapTable(null, 50, 0),
        Exceptions(ExceptionsAttribute.class, 45, 3),      // required
        InnerClasses(InnerClassesAttribute.class, 45, 3),
        EnclosingMethod(EnclosingMethodAttribute.class, 49, 0),
        //Synthetic(null, 45, 3),
        Signature(SignatureAttribute.class, 49, 0),
        //SourceFile(null, 45, 3), // the original file name
        //SourceDebugExtension(null, 49, 0), // debugging information
        //LineNumberTable(null, 45, 3),
        //LocalVariableTable(null, 45, 3),
        //LocalVariableTypeTable(null, 49, 0),
        //Deprecated(null, 45, 3),
        //RuntimeVisibleAnnotations(null, 49, 0),           // *
        //RuntimeInvisibleAnnotations(null, 49, 0),         // *
        //RuntimeVisibleParameterAnnotations(null, 49, 0),  // *
        //RuntimeInvisibleParameterAnnotations(null, 49, 0),// *
        //AnnotationDefault(null, 49, 0),                   // *
        BootstrapMethods(BootstrapMethodsAttribute.class, 51, 0)
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



    //private int attribute_name_index;
    //protected long attribute_length;
    //private ArrayList<Integer> info = new ArrayList<>();



    //@Override
    //public Result read() throws IOException {

    //    attribute_name_index = bytes.readUnsignedShort();
    //    attribute_length = bytes.readUnsignedInt();

    //    //ConstantUtf8 entry = (ConstantUtf8) currentClassInstance.constantPoolContainer.get(attribute_name_index);

    //    //assert attribute_name_index <

    //    return Result.OK;
    //}
}
