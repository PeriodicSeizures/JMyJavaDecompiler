package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;
import java.util.ArrayList;

public class MethodContainer extends DecompiledItem {

    private ArrayList<DecompiledMethod> methods = new ArrayList<>();

    public MethodContainer(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {

        int methods_count = bytes.readUnsignedShort();

        for (; methods_count > 0; methods_count--) {
            DecompiledMethod javaMethod = new DecompiledMethod(belongingClass);

            javaMethod.read(bytes);

            methods.add(javaMethod);
        }
    }

    public DecompiledMethod getMethod(int index) {
        return methods.get(index);
    }

    // or get method by

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("{MethodContainer} ").append("\n");

        for (DecompiledMethod javaMethod : methods) {
            s.append(javaMethod.toString()).append("\n");
        }

        return s.toString();
    }

}
