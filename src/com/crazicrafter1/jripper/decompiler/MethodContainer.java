package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;
import java.util.ArrayList;

public class MethodContainer extends IDisassembled {

    private ArrayList<DisassembledMethod> methods = new ArrayList<>();

    public MethodContainer(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {

        int methods_count = bytes.readUnsignedShort();

        for (; methods_count > 0; methods_count--) {
            DisassembledMethod javaMethod = new DisassembledMethod(getMainClass());

            javaMethod.read(bytes);

            methods.add(javaMethod);
        }
    }

    public DisassembledMethod getMethod(int index) {
        return methods.get(index);
    }

    public ArrayList<DisassembledMethod> getMethods() {
        return methods;
    }

    // or get method by

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("{MethodContainer} ").append("\n");

        for (DisassembledMethod javaMethod : methods) {
            s.append(javaMethod.toString()).append("\n");
        }

        return s.toString();
    }

}
