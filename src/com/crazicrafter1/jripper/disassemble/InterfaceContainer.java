package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.util.ByteReader;

import java.io.IOException;
import java.util.ArrayList;

public final class InterfaceContainer extends IDisassembled {

    private ArrayList<Integer> interfaces = new ArrayList<>();

    InterfaceContainer(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        int interfaces_count = bytes.readUnsignedShort();

        for (; interfaces_count > 0; interfaces_count--)
            interfaces.add(bytes.readUnsignedShort());
    }

    public ArrayList<String> getInterfaces() {
        ArrayList<String> result = new ArrayList<>();
        for (Integer i : interfaces) {
            result.add((String) getConstant(i).get());
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{InterfaceContainer} ").append("\n");

        for (String s : getInterfaces()) {
            stringBuilder.append(s).append(", ");
        }

        if (interfaces.isEmpty())
            stringBuilder.append("  -  ");

        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
