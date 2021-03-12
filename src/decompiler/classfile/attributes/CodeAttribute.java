package decompiler.classfile.attributes;

import decompiler.Result;
import decompiler.classfile.AttributeContainer;
import decompiler.classfile.JavaItem;

import java.io.IOException;
import java.util.ArrayList;

public class CodeAttribute extends JavaAttribute {

    private static class JavaExceptionEntry extends JavaItem {

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
            try to return the body of the method
         */



        return "CodeAttribute{}";
    }
}
