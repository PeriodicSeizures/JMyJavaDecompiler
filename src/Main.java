import decompiler.JavaDecompiler;
import decompiler.dumper.JavaClass;

public class Main {

    /*
     * Special notes on the JVM:
     *  - allows for methods with duplicate signatures
     *      + int something(int a, int b)
     *      + float something(int a, int b)
     *      + only occurs when the methods are in same method
     *      + illegal in standard java
     *
     *  - <init> is instance initializer (class constructor)
     *  - <clinit> is static initializer (braces)
     *      static {
     *
     *      }
     *
     * "On class method invocation, any parameters are passed in consecutive
     * local variables starting from local variable 0. On instance method
     * invocation, local variable 0 is always used to pass a reference
     * to the object on which the instance method is being invoked
     * (this in the Java programming language). Any parameters
     * are subsequently passed in consecutive local
     * variables starting from local variable 1."
     * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.6.1
     *
     *  --> LocalVariableTable Attribute
     *      - provides descriptor information
     *
     *  --> LocalVariableTypeTable Attribute
     *      - provides signature information
     *      - important for generics (or templates in C++)
     */

    public static void main(String[] args) {

        //String path = "samples/SingleConstructor.class";
        //String path = "samples/ConstructorMembersGetSet.class";
        //String path = "samples/ConstantClass.class";
        //String path = "samples/StaticInitializerConstructor.class";
        String path = "samples/obfnofall.class";
        //String path = "samples/MathOperations.class";

        JavaDecompiler decompiler = new JavaDecompiler();

        try {
            decompiler.read(path);

            System.out.println("Decoding was successful");

            var rClass = decompiler.getRClass();

            System.out.println(rClass);

            var myJavaClass = new JavaClass(rClass);

            //System.out.println(decompiler.getRClass().attributeContainer.);

            // will output generated data to the specified stream
            //myJavaClass.dump()

            System.out.println(myJavaClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(decompiler.getRClass());

        //System.out.println(decompiler.getJClass().attributeContainer.);





    }

}
