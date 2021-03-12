package decompiler.classfile;

import decompiler.Result;
import decompiler.classfile.fields.JavaField;
import decompiler.classfile.pool.JavaPoolEntry;

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

    //@Override
    //public String toString() {
    //    StringBuilder stringBuilder = new StringBuilder();

    //    stringBuilder.append("{ConstantPool}: ").append("\n");

    //    for (int i=1; i<spaced_indexes.size(); i++) {
    //        JavaPoolEntry javaPoolEntry = JavaItem.get(i);
    //        stringBuilder.append("  ").append(i).append(" : ").append(javaPoolEntry.toString()).append("\n");
    //    }

    //    stringBuilder.append("size: ").append(spaced_indexes.size()).append("\n");

    //    return stringBuilder.toString();
    //}
}
