package decompiler.reader.attributes;

import decompiler.Result;
import decompiler.reader.AttributeContainer;
import decompiler.reader.RawItem;
import decompiler.reader.Opcode;

import java.io.IOException;
import java.util.ArrayList;

public class CodeAttribute extends RawAttribute {

    private static class JavaExceptionEntry extends RawItem {

        public int start_pc;
        public int end_pc;
        public int handler_pc;
        public int catch_type;

        @Override
        public Result read() throws IOException {

            start_pc = bytes.readUnsignedShort();
            end_pc = bytes.readUnsignedShort();
            handler_pc = bytes.readUnsignedShort();
            catch_type = bytes.readUnsignedShort();

            return Result.OK;
        }
    }

    private int max_stack;
    private int max_locals;
    //;
    private ArrayList<Integer> code = new ArrayList<>(); // ???
    //private ;
    private ArrayList<JavaExceptionEntry> exception_table = new ArrayList<>();
    //;
    private AttributeContainer attribute_container = new AttributeContainer();


    @Override
    public Result read() throws IOException {
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

        return Result.OK;
    }

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
            int varargs = opcode.getVarArgs(code, index);
            if (varargs == 0) {// as long as instruction has no arguments, then
                a.append(opcode.name()).append("\n");
            } else {
                a.append(opcode.name()).append("\n"); // has arguments, which should be printed
                int end = index + varargs;
                while (index < end)
                    a.append("  ").append(code.get(++index)).append("\n");
            }


        }

        return a.toString();
    }

    /*
    // context should be the method this attribute belongs to, as a RawMethod object
    @Override
    public String toJavaSourceCode(Object context) {
        RawMethod javaMethod = (RawMethod) context;

        StringBuilder a = new StringBuilder();

        //String[] stack = new String[];
        ArrayList<String> stack = new ArrayList<>();

        for (int index=0; index<code.size(); index++) {
            int c = code.get(index);
            Opcode opcode = Opcode.getOpcode(c);

            // loads append specific vars
            if (opcode.name().contains("LOAD")) {
                int i;

                if (opcode.name().contains("_"))
                    i = Integer.parseInt(opcode.name().substring(opcode.name().length()-1));
                else
                    i = code.get(index+1);

                // push element from method signature to stack
                //javaMethod.




            }

            int varargs = opcode.getVarArgs(code, index);
            if (varargs == 0) {// as long as instruction has no arguments, then
                a.append(opcode.name()).append("\n");
            } else {
                a.append(opcode.name()).append("\n"); // has arguments, which should be printed
                int end = index + varargs;
                while (index < end)
                    a.append("  ").append(code.get(++index)).append("\n");
            }


        }

        return a.toString();

    }
    */
}
