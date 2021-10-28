package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.decompiler.DecompiledClass;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.crazicrafter1.jripper.JRipper;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;
import com.crazicrafter1.jripper.decompiler.except.InvalidTypeException;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;

public class JavaJar extends IDeobfuscated {

    private HashMap<String, JavaClass> classes = new HashMap<>();

    private String path;

    public JavaJar(String path) {
        super(null);
        this.path = path;
    }

    /**
     * Get the method referenced by the {@link IPoolConstant}
     * Must be of {@link ConstantMethodref} or {@link ConstantInterfaceMethodref}
     * @param ref
     * @return
     */
    JavaMethod getJavaMethod(IPoolConstant ref) {
        if (ref instanceof ConstantMethodref) {
            return classes.get(((ConstantMethodref) ref).getPointingClass().get()).getMethodByMethodRef(ref);
        } else if (ref instanceof ConstantInterfaceMethodref) {
            return classes.get(((ConstantInterfaceMethodref) ref).getPointingClass().get()).getMethodByMethodRef(ref);
        }
        throw new InvalidTypeException("Constant must be of a method or interface reference");
    }



    @Override
    public void process() {
        try {
            JarFile jarFile = new JarFile(path);

            for (Enumeration<JarEntry> enumEntry = jarFile.entries(); enumEntry.hasMoreElements(); ) {
                JarEntry entry = enumEntry.nextElement();

                DecompiledClass decompiledClass =
                        JRipper.decompileClass(jarFile.getInputStream(entry));

                JavaClass javaClass = new JavaClass(this, decompiledClass);
                javaClass.process();

                classes.put(decompiledClass.getPackageAndName(), javaClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
