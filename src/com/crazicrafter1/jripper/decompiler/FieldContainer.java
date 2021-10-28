package com.crazicrafter1.jripper.decompiler;

import java.io.IOException;
import java.util.ArrayList;

public class FieldContainer extends IDecompiled {

    private ArrayList<DecompiledField> fields = new ArrayList<>();

    public FieldContainer(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        int fields_count = bytes.readUnsignedShort();

        for (; fields_count > 0; fields_count--) {
            DecompiledField field = new DecompiledField(belongingClass);

            field.read(bytes);

            fields.add(field);
        }
    }

    public ArrayList<DecompiledField> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FieldContainer}: ").append("\n");
        for (int i=0; i<fields.size(); i++) {
            DecompiledField rawField = fields.get(i);
            stringBuilder.append("  ").append(i).append(" : ").append(rawField.toString()).append("\n");
        }
        stringBuilder.append("size: ").append(fields.size()).append("\n");
        return stringBuilder.toString();
    }
}
