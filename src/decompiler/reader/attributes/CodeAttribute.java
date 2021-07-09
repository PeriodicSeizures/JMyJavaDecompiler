package decompiler.reader.attributes;

import decompiler.reader.AttributeContainer;
import decompiler.reader.RItem;
import decompiler.reader.Opcode;

import java.io.IOException;
import java.util.ArrayList;

public class CodeAttribute extends RawAttribute {

    private static class JavaExceptionEntry extends RItem {

        public int start_pc;
        public int end_pc;
        public int handler_pc;
        public int catch_type;

        @Override
        public void read() throws IOException {

            start_pc = bytes.readUnsignedShort();
            end_pc = bytes.readUnsignedShort();
            handler_pc = bytes.readUnsignedShort();
            catch_type = bytes.readUnsignedShort();
        }
    }

    private int max_stack;
    private int max_locals;
    //;
    public ArrayList<Integer> code = new ArrayList<>(); // ???
    //private ;
    public ArrayList<JavaExceptionEntry> exception_table = new ArrayList<>();
    //;
    private AttributeContainer attribute_container = new AttributeContainer();


    @Override
    public void read() throws IOException {
        max_stack = bytes.readUnsignedShort();
        max_locals = bytes.readUnsignedShort();

        long code_length = bytes.readUnsignedInt();
        for (; code_length > 0; code_length--) {
            code.add(bytes.readUnsignedByte());
        }

        int exception_table_length = bytes.readUnsignedShort();
        for (; exception_table_length > 0; exception_table_length--) {
            JavaExceptionEntry entry = new JavaExceptionEntry();
            entry.read();
            exception_table.add(entry);
        }

        attribute_container.read();
    }

    //public ArrayList<LocalVariableTableAttribute.LocalVariableEntry> getLocalVariableTable() {
    //    return ((LocalVariableTableAttribute) attribute_container.get(Attribute.LocalVariableTable)).local_variable_table;
    //}

    @Override
    public String toString() {

        /*
            TODO:
            determine the variable length offset in code[i]... portion
         */

        StringBuilder a = new StringBuilder("{Code} " + code);

        a.append("\n");

        for (int index=0; index<code.size(); index++) {
            int c = code.get(index);
            Opcode opcode = Opcode.getOpcode(c);
            int varargs = Opcode.getVarArgs(code, index);
            a.append(opcode.name()).append("\n");// has arguments, which should be printed
            if (varargs != 0) {// as long as instruction has no arguments, then
                int end = index + varargs;
                while (index < end)
                    a.append("  ").append(code.get(++index)).append("\n");
            }
        }

        a.append("\n");

        a.append(attribute_container);

        return a.toString();
    }

}
