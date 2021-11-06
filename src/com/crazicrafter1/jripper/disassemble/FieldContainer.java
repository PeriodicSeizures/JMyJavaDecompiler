package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.util.IndexedMap;
import com.crazicrafter1.jripper.util.ByteReader;

import java.io.IOException;
import java.util.UUID;

public final class FieldContainer extends IDisassembled {

    //private ArrayList<DisassembledField> fields = new ArrayList<>();
    private IndexedMap<UUID, DisassembledField> fields = new IndexedMap<>();

    FieldContainer(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    protected void read(ByteReader bytes) throws IOException {
        int fields_count = bytes.readUnsignedShort();

        for (; fields_count > 0; fields_count--) {
            DisassembledField field = new DisassembledField(getMain());

            field.read(bytes);

            //fields.add(field);
            fields.put(field.getUUID(), field);
        }
    }

    public IndexedMap<UUID, DisassembledField> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FieldContainer}: ").append("\n");
        for (int i=0; i<fields.size(); i++) {
            DisassembledField rawField = fields.getByIndex(i).getValue();
            stringBuilder.append("  ").append(i).append(" : ").append(rawField.toString()).append("\n");
        }
        stringBuilder.append("size: ").append(fields.size()).append("\n");
        return stringBuilder.toString();
    }
}
