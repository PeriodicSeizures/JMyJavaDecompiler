package decompiler.reader;

import decompiler.Result;
import decompiler.reader.pool.RawConstant;

import java.io.IOException;
import java.util.ArrayList;

public class FieldContainer extends RawItem {

    public ArrayList<RawField> fields = new ArrayList<>();

    @Override
    public Result read() throws IOException {

        int fields_count = bytes.readUnsignedShort();

        for (; fields_count > 0; fields_count--) {
            RawField field = new RawField();

            field.read();

            fields.add(field);
        }

        return Result.OK;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FieldContainer}: ").append("\n");
        for (int i=0; i<fields.size(); i++) {
            RawField rawField = fields.get(i);
            stringBuilder.append("  ").append(i).append(" : ").append(rawField.toString()).append("\n");
        }
        stringBuilder.append("size: ").append(fields.size()).append("\n");
        return stringBuilder.toString();
    }
}
