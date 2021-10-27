package com.crazicrafter1.jripper;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.except.NoMagicHeaderException;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JRipper {

    private JRipper() {}

    public static DecompiledClass decompileClass(InputStream is) throws IOException {

        ByteReader bytes = new ByteReader(is);

        if (!bytes.compareNext(4, new byte[] {(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE}))
            throw new NoMagicHeaderException("No magic header found");

        DecompiledClass javaClassFile = new DecompiledClass();

        javaClassFile.read(bytes);
        bytes.close();

        return javaClassFile;
    }

    public static HashMap<String, DecompiledClass> decompileJar(String path) throws IOException {
        JarFile jarFile = new JarFile(path);

        HashMap<String, DecompiledClass> classes = new HashMap<>();

        for (Enumeration<JarEntry> enumEntry = jarFile.entries(); enumEntry.hasMoreElements();) {
            var entry = enumEntry.nextElement();

            DecompiledClass decompiledClass = decompileClass(jarFile.getInputStream(entry));

            classes.put(decompiledClass.getPackageAndName(), decompiledClass);
        }

        return classes;
    }
}
