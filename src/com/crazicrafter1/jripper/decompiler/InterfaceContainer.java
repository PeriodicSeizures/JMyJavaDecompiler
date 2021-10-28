package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;
import java.util.ArrayList;

public class InterfaceContainer extends IDecompiled {

    private ArrayList<Integer> interfaces = new ArrayList<>();

    public InterfaceContainer(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        int interfaces_count = bytes.readUnsignedShort();

        for (; interfaces_count > 0; interfaces_count--)
            interfaces.add(bytes.readUnsignedShort());
    }

    public String[] getInterfaces() {
        String[] arr = new String[interfaces.size()];
        for (int index = 0; index < interfaces.size(); index++) {
            arr[index] = (String) getEntry(interfaces.get(index)).get();
        }
        return arr;
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
