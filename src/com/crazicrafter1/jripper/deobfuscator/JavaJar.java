package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.DecompiledClass;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.crazicrafter1.jripper.JRipper;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;
import com.crazicrafter1.jripper.decompiler.except.InvalidTypeException;
import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;

public class JavaJar extends IObfuscate {

    /**
     * Stores the decompiled packageAndClassName, and JavaClass
     */
    //private HashMap<String, JavaClass> classes = new HashMap<>();

    private String path;

    public JavaJar(String path) {
        super(null);
        this.path = path;
    }

    @Override
    public void validationPhase() {
        try {
            JarFile jarFile = new JarFile(path);

            for (Enumeration<JarEntry> enumEntry = jarFile.entries(); enumEntry.hasMoreElements(); ) {
                JarEntry entry = enumEntry.nextElement();

                DecompiledClass decompiledClass =
                        JRipper.decompileClass(jarFile.getInputStream(entry));

                JavaClass javaClass = new JavaClass(this, decompiledClass);
                javaClass.validationPhase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void linkingPhase() {
        for (Map.Entry<String, JavaClass> entry : classes.entrySet()) {
            entry.getValue().linkingPhase();
        }
    }
}
