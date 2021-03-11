package decompiler.classfile;

import decompiler.Result;
import decompiler.classfile.fields.JavaField;

import java.io.IOException;
import java.util.ArrayList;

public class FieldContainer extends JavaItem {

    public ArrayList<JavaField> fields = new ArrayList<>();

    @Override
    public Result read() throws IOException {

        int fields_count = bytes.readUnsignedShort();

        for (; fields_count > 0; fields_count--) {
            JavaField field = new JavaField();

            field.read();

            fields.add(field);
        }

        return Result.OK;
    }
}
