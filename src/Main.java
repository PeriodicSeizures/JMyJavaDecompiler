import com.crazicrafter1.jripper.decompile.JRipper;

import com.crazicrafter1.jripper.disassemble.IDisassembled;

import java.io.*;
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

    static void usage() {
        System.out.println("Arg usage:");
        System.out.println("\tdisassemble the specified class(es):");
        System.out.println("\t\t-asm [files...]                ");
        System.out.println("\tdecompile the specified class/classes/jar");
        System.out.println("\t\t-crunch [out] [file(s)...] ");
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
        }

        try {
            switch (args[0].toLowerCase()) {
                case "-asm": {
                    if (args.length < 2)
                        usage();

                    // Decompile classes
                    for (int i = 1; i < args.length; i++) {
                        //System.out.println(JRipper.disassembleClass(Paths.get(args[i]).toFile()));
                        System.out.println(
                                IDisassembled.disassemble(new FileInputStream(Paths.get(args[i]).toFile())));
                    }

                    break;
                }
                case "-crunch": {
                    if (args.length < 3)
                        usage();

                    File output = Paths.get(args[1]).toFile();

                    if (args.length == 3) {
                        // directory
                        // single file
                        // single jar
                        File input = Paths.get(args[2]).toFile();
                        if (input.isDirectory()) {
                            for (File file : input.listFiles()) {
                                JRipper.decompileClass(file);
                            }
                        } else {
                            String name = input.getName();
                            int ext = name.lastIndexOf('.');
                            // single class
                            if (name.substring(ext).equals(".class")) {
                                JRipper.decompileClass(input);
                            } else if (name.substring(ext).equals(".jar")) {
                                JRipper.decompileJar(input);
                            } else
                                System.out.println("Invalid filetype, use only .class or .jar");
                        }
                    } else {
                        // all files in args
                        for (int i = 2; i < args.length; i++) {
                            File file = Paths.get(args[i]).toFile();

                            JRipper.decompileClass(file);
                        }
                    }
                    // Decompile and deobfuscate classes

                    output.mkdirs();
                    JRipper.dump(output);

                    break;
                }
                default:
                    usage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
