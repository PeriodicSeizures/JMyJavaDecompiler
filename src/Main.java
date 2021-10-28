import com.crazicrafter1.jripper.JRipper;
import com.crazicrafter1.jripper.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

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
     *
     *  -->
     */

    public static void main(String[] args) {

        //String path = "samples/inherit/SubClass.class";
        String path = "out/production/JMyJavaDecompiler/test/virtual/ChildClass.class";

        //String path = "samples/MethodWithFields.class";
        //String path = "samples/SingleConstructor.class";
        //String path = "samples/ConstructorMembersGetSet.class";
        //String path = "samples/ConstantClass.class";
        //String path = "samples/StaticInitializerConstructor.class";
        //String path = "samples/obfnofall.class";
        //String path = "samples/MathOperations.class";

        try {

            File file = Paths.get(path).toFile();

            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            var decompiledClass = JRipper.decompileClass(new FileInputStream(file));
            System.out.println(decompiledClass);

            var deObfuscatedClass = JRipper.deobfuscateClass(decompiledClass);
            System.out.println(deObfuscatedClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
