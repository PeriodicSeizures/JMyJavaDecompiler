package decompiler.reader;

import java.io.IOException;
import java.util.ArrayList;

public class FieldContainer extends RItem {

    public ArrayList<RField> fields = new ArrayList<>();

    @Override
    public void read() throws IOException {

        int fields_count = bytes.readUnsignedShort();

        for (; fields_count > 0; fields_count--) {
            RField field = new RField();

            field.read();

            fields.add(field);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FieldContainer}: ").append("\n");
        for (int i=0; i<fields.size(); i++) {
            RField rawField = fields.get(i);
            stringBuilder.append("  ").append(i).append(" : ").append(rawField.toString()).append("\n");
        }
        stringBuilder.append("size: ").append(fields.size()).append("\n");
        return stringBuilder.toString();
    }
}
