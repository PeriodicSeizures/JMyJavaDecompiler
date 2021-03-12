package decompiler.classfile.attributes;

import decompiler.Result;

import java.io.IOException;
import java.util.ArrayList;

public class ExceptionsAttribute extends JavaAttribute {

    private ArrayList<Integer> exception_index_table = new ArrayList<>();

    @Override
    public Result read() throws IOException {

        int number_of_exceptions = bytes.readUnsignedShort();

        for (; number_of_exceptions > 0; number_of_exceptions--) {
            exception_index_table.add(bytes.readUnsignedShort());
        }

        return Result.OK;
    }

    @Override
    public String toString() {
        return "{Exceptions} ";
    }
}
