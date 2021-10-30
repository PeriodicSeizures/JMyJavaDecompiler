//import com.crazicrafter1.jripper.deobfuscator.JRipper;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;

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
        System.out.println("\t\t-dasm [files...]                ");
        System.out.println("\tdeobfuscate the specified class(es)");
        System.out.println("\t\t-crunch [out] ['-files', '-all', '-jar'] [file(s)...] ");
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
        }

        try {
            switch (args[0].toLowerCase()) {
                case "-dasm": {
                    if (args.length < 2)
                        usage();

                    // Decompile classes
                    for (int i = 1; i < args.length; i++) {
                        DisassembledClass disassembledClass = new DisassembledClass();
                        disassembledClass.read(new ByteReader(new FileInputStream(Paths.get(args[i]).toFile())));
                        System.out.println(disassembledClass);
                    }

                    break;
                }
                case "-crunch": {
                    if (args.length < 4)
                        usage();

                    // Decompile and deobfuscate classes

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (true)
            return;

        if (args[0].equalsIgnoreCase("-unpack")) {
            if (args.length < 4) {
                usage();
            }

            File output = Paths.get(args[1]).toFile();

            boolean debug = args[2].equalsIgnoreCase("-dbg");

        } else if (args[0].equalsIgnoreCase("-crunch")) {

/*
            int next = debug ? 3 : 2;
            try {
                switch (args[next]) {
                    case "-files":
                        for (int i = next+1; i < args.length; i++) {
                            File file = Paths.get(args[i]).toFile();
                            JRipper.deObfuscateJar(file, debug);
                        }
                        break;
                    case "-all": {
                        File path = Paths.get(args[next + 1]).toFile();
                        for (File file : path.listFiles()) {
                            String filename = file.getName();
                            int ext = filename.indexOf('.');
                            if (ext != -1 && filename.substring(ext + 1).equals("class")) {
                                JRipper.deObfuscateClass(file, debug);
                            }
                        }
                        break;
                    } case "-jar": {
                        File path = Paths.get(args[next + 1]).toFile();
                        JRipper.deObfuscateJar(path, debug);
                        break;
                    } default:
                        usage();
                }

                output.mkdirs();
                JRipper.dump(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
 */
        } else
            usage();
    }

}
