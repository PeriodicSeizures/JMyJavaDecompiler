package com.crazicrafter1.jripper.decompiler.attributes;

import com.crazicrafter1.jripper.decompiler.*;

import java.io.IOException;
import java.util.ArrayList;

public class CodeAttr extends IDisassembled implements IAttr {

    private static class JExcept extends IDisassembled {

        public int start_pc;

        public int end_pc;
        public int handler_pc;
        public int catch_type;

        public JExcept(DisassembledClass belongingClass) {
            super(belongingClass);
        }

        @Override
        public void read(ByteReader bytes) throws IOException {
            start_pc = bytes.readUnsignedShort();
            end_pc = bytes.readUnsignedShort();
            handler_pc = bytes.readUnsignedShort();
            catch_type = bytes.readUnsignedShort();
        }

    }

    private int max_stack;

    private int max_locals;
    public ArrayList<Integer> code = new ArrayList<>();

    public ArrayList<JExcept> exception_table = new ArrayList<>();
    private AttributeContainer attribute_container;

    public CodeAttr(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        attribute_container = new AttributeContainer(getMainClass());

        max_stack = bytes.readUnsignedShort();
        max_locals = bytes.readUnsignedShort();

        long code_length = bytes.readUnsignedInt();
        for (; code_length > 0; code_length--) {
            code.add(bytes.readUnsignedByte());
        }

        int exception_table_length = bytes.readUnsignedShort();
        for (; exception_table_length > 0; exception_table_length--) {
            JExcept entry = new JExcept(getMainClass());
            entry.read(bytes);
            exception_table.add(entry);
        }

        attribute_container.read(bytes);
    }

    //public ArrayList<LocalVariableTableAttribute.Var> getLocalVariableTable() {
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
            int varargs = Opcode.getArgCount(code, index);
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
