package com.crazicrafter1.jripper;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.deobfuscator.IObfuscate;
import com.crazicrafter1.jripper.deobfuscator.JavaClass;

import java.io.*;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JRipper {

    private JRipper() {}

    private static DecompiledClass decompileClass(InputStream is) throws IOException {
        ByteReader bytes = new ByteReader(is);

        if (!bytes.compareNext(4, new byte[] {(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE}))
            throw new RuntimeException("No magic header found");

        DecompiledClass javaClassFile = new DecompiledClass();

        javaClassFile.read(bytes);
        bytes.close();

        return javaClassFile;
    }

    public static void deObfuscateJar(File file) throws IOException {
        JarFile jarFile = new JarFile(file);

        for (Enumeration<JarEntry> enumEntry = jarFile.entries(); enumEntry.hasMoreElements(); ) {
            JarEntry entry = enumEntry.nextElement();

            DecompiledClass decompiledClass =
                    JRipper.decompileClass(jarFile.getInputStream(entry));

            JavaClass javaClass = new JavaClass(decompiledClass);
            javaClass.validationPhase();
        }
    }

    public static void deObfuscateClasses(File... files) throws IOException {
        for (File file : files) {
            DecompiledClass decompiledClass =
                    JRipper.decompileClass(new FileInputStream(file));

            JavaClass javaClass = new JavaClass(decompiledClass);
            javaClass.validationPhase();
        }
    }

    private static void linkDeObfuscatedClasses() {
        for (Map.Entry<String, JavaClass> entry : IObfuscate.classes.entrySet()) {
            entry.getValue().linkingPhase();
        }
    }

    public static void dump(File path) throws IOException {

        linkDeObfuscatedClasses();

        path.mkdirs();

        for (JavaClass javaClass : IObfuscate.classes.values()) {
            File file = new File(path, javaClass.getClassName() + ".java");

            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.append(javaClass.toString());
            writer.close();
        }

    }

}
