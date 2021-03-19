import decompiler.JavaDecompiler;
import decompiler.Result;
import decompiler.Util;
import decompiler.linker.JavaClass;

import java.io.*;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        JavaDecompiler decompiler = new JavaDecompiler();
        Result result = decompiler.read("ClassWithConstructor.class");

        System.out.println(decompiler.getRawClassFile());
        System.out.println("Decoding result: " + result.name());
        System.out.println(new JavaClass(decompiler.getRawClassFile()));

    }

}
