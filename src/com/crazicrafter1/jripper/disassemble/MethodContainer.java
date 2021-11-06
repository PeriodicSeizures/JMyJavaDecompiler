package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.util.IndexedMap;
import com.crazicrafter1.jripper.util.ByteReader;

import java.io.IOException;
import java.util.UUID;

public final class MethodContainer extends IDisassembled {

    private IndexedMap<UUID, DisassembledMethod> methods = new IndexedMap<>();

    MethodContainer(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {

        int methods_count = bytes.readUnsignedShort();

        for (; methods_count > 0; methods_count--) {
            DisassembledMethod javaMethod = new DisassembledMethod(getMain());

            javaMethod.read(bytes);

            methods.put(javaMethod.getUUID(), javaMethod);
        }
    }

    public IndexedMap<UUID, DisassembledMethod> getMethods() {
        return methods;
    }

    // or get method by

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        for (DisassembledMethod javaMethod : methods.values()) {
            s.append(javaMethod.toString()).append("\n");
        }

        return s.toString();
    }

}
