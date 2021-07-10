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
     *  -
     */

    public static void main(String[] args) {

        //String path = "samples/ConstructorMembersGetSet.class";
        //String path = "samples/ConstantClass.class";
        //String path = "samples/StaticInitializerConstructor.class";
        String path = "samples/obfnofall.class";

        //String path = "samples/MathOperations.class";

        JavaDecompiler decompiler = new JavaDecompiler();

        try {
            decompiler.read(path);

            System.out.println("Decoding was successful");

            System.out.println(new JavaClass(decompiler.getRClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(decompiler.getRClass());

        //System.out.println(decompiler.getJClass().attributeContainer.);





    }

}
