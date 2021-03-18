package decompiler.reader.attributes;

import decompiler.Result;
import decompiler.reader.RawItem;

import java.io.IOException;
import java.util.ArrayList;

public class LocalVariableTableAttribute extends RawAttribute {

    public class LocalVariableEntry extends RawItem {

        int start_pc;
        int length;
        int name_index;
        int descriptor_index;
        int index;

        @Override
        public Result read() throws IOException {
            start_pc = bytes.readUnsignedShort();
            length = bytes.readUnsignedShort();
            name_index = bytes.readUnsignedShort();
            descriptor_index = bytes.readUnsignedShort();
            index = bytes.readUnsignedShort();

            return Result.OK;
        }

        @Override
        public String toString() {
            return "  " + start_pc + " " + length + " " +
                    getEntry(name_index).getValue() + " " +
                    getEntry(descriptor_index).getValue() + " " +
                    index;
        }

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

    ArrayList<LocalVariableEntry> local_variable_table = new ArrayList<>();

    @Override
    public Result read() throws IOException {
        int local_variable_table_length = bytes.readUnsignedShort();

        for (; local_variable_table_length > 0; local_variable_table_length--) {
            LocalVariableEntry localVariableEntry = new LocalVariableEntry();
            localVariableEntry.read();
            local_variable_table.add(localVariableEntry);
        }

        return Result.OK;
    }

    @Override
    public String toString() {
        return "{LocalVariableTable} " + local_variable_table;
    }
}
