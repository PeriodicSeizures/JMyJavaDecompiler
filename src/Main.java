import com.crazicrafter1.jripper.JRipper;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.deobfuscator.JavaClass;

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

        String path = "out/production/JRipper/test/inherited/BaseClass.class";
        String path1 = "out/production/JRipper/test/inherited/SubClass.class";
        String path2 = "out/production/JRipper/test/inherited/OtherClass.class";
        String path3 = "out/production/JRipper/test/inherited/DeeperClass.class";
        //String path = "out/production/JMyJavaDecompiler/test/virtual/ChildClass.class";

        //String path = "out/production/JRipper/test/MethodWithFields.class";
        //String path = "out/production/JRipper/test/SingleConstructor.class";
        //String path = "out/production/JRipper/test/ConstructorMembersGetSet.class";
        //String path = "out/production/JRipper/test/ConstantClass.class";
        //String path = "out/production/JRipper/test/StaticInitializerConstructor.class";
        //String path = "obfuscated/obfnofall.class";
        //String path = "out/production/JRipper/test/MathOperations.class";
        //String path = "out/production/JRipper/test/cmp/IfStatement.class";

        try {

            //File file = Paths.get(path).toFile();
            //if (!file.exists()) {
            //    throw new FileNotFoundException();
            //}

            JRipper.deObfuscateClasses(new File(path2)); //new File(path), new File(path1), new File(path2));

            JRipper.dump(new File("decompiled"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
