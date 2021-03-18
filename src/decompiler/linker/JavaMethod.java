package decompiler.linker;

import decompiler.reader.Opcode;
import decompiler.reader.RawItem;
import decompiler.reader.attributes.CodeAttribute;
import decompiler.reader.attributes.LocalVariableTableAttribute;

import java.util.ArrayList;

public class JavaMethod {

    private final String name;
    private final String signature;
    private final String returnType;

    private final int methodIndex;

    private String[] body;

    public JavaMethod(String name, String signature, String returnType, int methodIndex) {
        this.name = name;
        this.signature = signature;
        this.returnType = returnType;
        this.methodIndex = methodIndex;

        /*
            parse method body
         */

    }

    //public String[]
    // signature
    //public ArrayList<S> body


    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public String getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "JavaMethod{}";
    }

    private void parseRawBody() {
        CodeAttribute codeAttribute = RawItem.currentClassInstance.methodContainer.methods.get(methodIndex).getCode();
        ArrayList<Integer> code = codeAttribute.code;

        LocalVariable[] LOCALS = LocalVariable.from(codeAttribute);

        //ArrayList<String> OPERAND_STACK = new ArrayList<>(); // changes as operations are performed on the stack

        OperandStack STACK = new OperandStack();

        StringBuilder a = new StringBuilder();

        for (int index=0; index<code.size(); index++) {
            int c = code.get(index);
            Opcode opcode = Opcode.getOpcode(c);
            int varargs = Opcode.getVarArgs(code, index);

            String name = opcode.name();

            // then this opcode appends to the stack
            int i;
            if ((i = Opcode.getLoad(code, index)) != -1)
                STACK.push(LOCALS[i].getName());

            else if ((i = Opcode.getStore(code, index)) != -1)
                LOCALS[i].setValue(STACK.pop());

            else if ((i = Opcode.getConst(code, index)) != -9)
                STACK.push("" + i);

            switch (name.substring(name.length() - 3)) {
                case "ADD":
                    STACK.add();
                    break;
                case "SUB":
                    STACK.sub();
                    break;
                case "MUL":
                    STACK.mul();
                    break;
                case "DIV":
                    STACK.div();
                    break;
                case "INC":
                    //LOCALS[code.get(index + 1)].setValue();
            }

            switch (name.substring(name.length() - 2)) {
                case "2I":
                    STACK.toInt();
                    break;
                case "2F":
                    STACK.toFloat();
                    break;
                case "2D":
                    STACK.toDouble();
                    break;
                case "2L":
                    STACK.toLong();
                    break;
                case "2B":
                    STACK.toByte();
                    break;
                case "2C":
                    STACK.toChar();
                    break;
            }



            //if ()


            if (varargs == 0) {// as long as instruction has no arguments, then
                a.append(opcode.name()).append("\n");
            } else {
                a.append(opcode.name()).append("\n"); // has arguments, which should be printed
                int end = i + varargs;
                while (i < end)
                    a.append("  ").append(code.get(++i)).append("\n");
            }


        }

        //codeAttribute.code
    }
}
