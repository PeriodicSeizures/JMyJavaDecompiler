package UNUSED;

import com.crazicrafter1.decompiler.reader.RItem;
import com.crazicrafter1.decompiler.reader.attributes.RawAttribute;

import java.io.IOException;
import java.util.ArrayList;

public class LocalVariableTableAttribute extends RawAttribute {

    public static class Var extends RItem {

        private int start_pc;
        private int length;
        private int name_index;
        private int descriptor_index;
        private int index;

        @Override
        public void read() throws IOException {
            start_pc = bytes.readUnsignedShort();
            length = bytes.readUnsignedShort();
            name_index = bytes.readUnsignedShort();
            descriptor_index = bytes.readUnsignedShort();
            index = bytes.readUnsignedShort();
        }

        @Override
        public String toString() {
            return "  " + start_pc + " " + length + " " +
                    getEntry(name_index).getValue() + " " +
                    getEntry(descriptor_index).getValue() + " " +
                    index;
        }

        @Override
        public String getName() {
            return (String) getEntry(name_index).getValue();
        }

        public String getDescriptor() {
            return (String) getEntry(descriptor_index).getValue();
        }

        public int getIndex() {
            return index;
        }
    }

    public ArrayList<Var> local_variable_table = new ArrayList<>();

    @Override
    public void read() throws IOException {
        int local_variable_table_length = bytes.readUnsignedShort();

        for (; local_variable_table_length > 0; local_variable_table_length--) {
            Var var = new Var();
            var.read();
            local_variable_table.add(var);
        }
    }

    @Override
    public String toString() {
        return "{LocalVariableTable} " + local_variable_table;
    }
}
