package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JRipper {

    private JRipper() {}

    public static DisassembledClass disassembleClass(InputStream is) throws IOException {
        ByteReader bytes = new ByteReader(is);

        DisassembledClass decompiledClass = new DisassembledClass();

        decompiledClass.read(bytes);
        bytes.close();

        System.out.println("Decompiled " + decompiledClass.getPackageAndName());

        return decompiledClass;
    }

    public static void decompileClass(File file, boolean debug) throws IOException {
        DisassembledClass decompiledClass =
                JRipper.disassembleClass(new FileInputStream(file));

        if (debug) {
            System.out.println(decompiledClass);
        }

        DecompiledJavaClass decompiledJavaClass = new DecompiledJavaClass(decompiledClass);
        decompiledJavaClass.validationPhase();
    }

    public static void decompileJar(File file, boolean debug) throws IOException {
        JarFile jarFile = new JarFile(file);

        for (Enumeration<JarEntry> enumEntry = jarFile.entries(); enumEntry.hasMoreElements(); ) {
            JarEntry entry = enumEntry.nextElement();

            DisassembledClass decompiledClass =
                    JRipper.disassembleClass(jarFile.getInputStream(entry));

            if (debug) {
                System.out.println(decompiledClass);
            }

            DecompiledJavaClass decompiledJavaClass = new DecompiledJavaClass(decompiledClass);
            decompiledJavaClass.validationPhase();
        }
    }

    public static void dump(File path) throws IOException {
        for (Map.Entry<String, DecompiledJavaClass> entry : IDecompiled.classes.entrySet()) {
            entry.getValue().linkingPhase();
        }

        path.mkdirs();

        for (DecompiledJavaClass decompiledJavaClass : IDecompiled.classes.values()) {
            File file = new File(path, decompiledJavaClass.getClassName() + ".java");

            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.append(decompiledJavaClass.toString());
            writer.close();

            System.out.println("Dumped " + file.getPath());
        }

    }

}
