package decompiler.reader.attributes;

import java.io.IOException;
import java.util.ArrayList;

public class ExceptionsAttribute extends RawAttribute {

    private ArrayList<Integer> exception_index_table = new ArrayList<>();

    @Override
    public void read() throws IOException {

        int number_of_exceptions = bytes.readUnsignedShort();

        for (; number_of_exceptions > 0; number_of_exceptions--) {
            exception_index_table.add(bytes.readUnsignedShort());
        }
    }

    @Override
    public String toString() {
        return "{Exceptions} ";
    }
}
