package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.disassemble.IDisassembled;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JRipper {

    private JRipper() {}

    //public static DisassembledClass disassembleClass(String filename) throws IOException {
    //    return IDisassembled.disassemble(new FileInputStream(Paths.get(filename).toFile()));
    //}

    public static DisassembledClass disassembleClass(File file) throws IOException {
        return IDisassembled.disassemble(new FileInputStream(file));
    }

    //public static void decompileClass(String filename) throws IOException {
    //    decompileClass(Paths.get(filename).toFile());
    //}

    public static void decompileClass(File file) throws IOException {
        String name = file.getName();
        int ext = name.lastIndexOf('.');
        if (!name.substring(ext).equals(".class")) {
            System.out.println("Not a .class file");
            return;
        }

        new DecompiledClass(
                disassembleClass(file))
                .validationPhase();
    }

    public static void decompileJar(File file) throws IOException {
        JarFile jarFile = new JarFile(file);

        for (Enumeration<JarEntry> enumEntry = jarFile.entries(); enumEntry.hasMoreElements(); ) {
            JarEntry entry = enumEntry.nextElement();

            DisassembledClass decompiledClass =
                    IDisassembled.disassemble(jarFile.getInputStream(entry));

            DecompiledClass decompiledJavaClass = new DecompiledClass(decompiledClass);
            decompiledJavaClass.validationPhase();
        }
    }

    public static void dump(File path) throws IOException {
        for (Map.Entry<String, DecompiledClass> entry : AbstractDecompiled.classes.entrySet()) {
            entry.getValue().linkingPhase();
        }

        path.mkdirs();

        for (DecompiledClass decompiledClass : AbstractDecompiled.classes.values()) {
            File file = new File(path, decompiledClass.getSimpleName() + ".java");

            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.append(decompiledClass.toString());
            writer.close();

            System.out.println("Dumped " + file.getPath());
        }

    }

}
