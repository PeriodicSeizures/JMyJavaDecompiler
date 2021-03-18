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

    //private String[] body;
    private ArrayList<String> generatedCode = new ArrayList<>();

    public JavaMethod(String name, String signature, String returnType, int methodIndex) {
        this.name = name;
        this.signature = signature;
        this.returnType = returnType;
        this.methodIndex = methodIndex;

        /*
            parse method body
         */
        parseRawBody();
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

            switch (opcode) {
                case ILOAD:         // push local@next
                case FLOAD:
                case DLOAD:
                case LLOAD:
                case ALOAD:         // push ref
                    STACK.push(LOCALS[code.get(++index)].getName());
                    continue;
                case ILOAD_0:       // push local@0
                case FLOAD_0:
                case DLOAD_0:
                case LLOAD_0:
                case ALOAD_0:       // push ref
                    STACK.push(LOCALS[0].getName());
                    continue;
                case ILOAD_1:       // push local@1
                case FLOAD_1:
                case DLOAD_1:
                case LLOAD_1:
                case ALOAD_1:       // push ref
                    STACK.push(LOCALS[1].getName());
                    continue;
                case ILOAD_2:       // push local@2
                case FLOAD_2:
                case DLOAD_2:
                case LLOAD_2:
                case ALOAD_2:       // push ref
                    STACK.push(LOCALS[2].getName());
                    continue;
                case ILOAD_3:       // push local@3
                case FLOAD_3:
                case DLOAD_3:
                case LLOAD_3:
                case ALOAD_3:       // push ref
                    STACK.push(LOCALS[3].getName());
                    continue;
                case IADD:          // add
                case FADD:
                case DADD:
                case LADD:
                    STACK.add();
                    continue;
                case ISUB:          // subtract
                case FSUB:
                case DSUB:
                case LSUB:
                    STACK.sub();
                    continue;
                case IMUL:          // multiply
                case FMUL:
                case DMUL:
                case LMUL:
                    STACK.mul();
                    continue;
                case IDIV:          // divide
                case FDIV:
                case DDIV:
                case LDIV:
                    STACK.div();
                    continue;
                case IINC:          // increment by next byte
                    LocalVariable local = LOCALS[code.get(++index)];

                    generatedCode.add(local.getName() + " += " + code.get(++index) + ";");
                    continue;
                case F2I:           // cast to int
                case D2I:
                case L2I:
                    STACK.toInt();
                    continue;
                case I2F:           // cast to float
                case D2F:
                case L2F:
                    STACK.toFloat();
                    continue;
                case I2D:           // cast to double
                case F2D:
                case L2D:
                    STACK.toDouble();
                    continue;
                case RET:           // return from function (void)
                    generatedCode.add("return;");
                    continue;
                case IRETURN:       // return value from function
                case FRETURN:
                case DRETURN:
                case LRETURN:
                case ARETURN:
                    generatedCode.add("return " + STACK.pop() + ";");
                    continue;
                case ISTORE:        // pop and assign to local@next
                case FSTORE:
                case DSTORE:
                case LSTORE:
                    generatedCode.add(LOCALS[code.get(++index)].getName() + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_0:      // pop and assign to local@0
                case FSTORE_0:
                case DSTORE_0:
                case LSTORE_0:
                    generatedCode.add(LOCALS[0].getName() + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_1:      // pop and assign to local@1
                case FSTORE_1:
                case DSTORE_1:
                case LSTORE_1:
                    generatedCode.add(LOCALS[1].getName() + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_2:      // pop and assign to local@2
                case FSTORE_2:
                case DSTORE_2:
                case LSTORE_2:
                    generatedCode.add(LOCALS[2].getName() + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_3:      // pop and assign to local@3
                case FSTORE_3:
                case DSTORE_3:
                case LSTORE_3:
                    generatedCode.add(LOCALS[3].getName() + " = " + STACK.pop() + ";");
                    continue;
                case ICONST_M1:     // push -1
                    STACK.push("-1");
                    continue;
                case ICONST_0:      // add '0' ? useless
                    continue;
                case ICONST_1:
                    STACK.push("1");
                    continue;
                case ICONST_2:
                    STACK.push("2");
                    continue;
                case ICONST_3:
                    STACK.push("3");
                    continue;
                case ICONST_4:
                    STACK.push("4");
                    continue;
                case ICONST_5:
                    STACK.push("5");
                    continue;
                case BIPUSH:
                    int v = (byte)(int)code.get(++index); // two's complement

                    //v = 255 - v;
                    //v += 1;
                    //v = -v;
                    STACK.push("" + v);
                    continue;
                default:
                    System.out.println("opcode " + name + " is not yet implemented");
            }

            //noinspection ControlFlowStatementWithoutBraces
            if (true)
                return;









            int i;
            if ((i = Opcode.getLoad(code, index)) != -1)
                STACK.push(LOCALS[i].getName());

            else if ((i = Opcode.getStore(code, index)) != -1) {
                //LOCALS[i].setValue(STACK.pop());
                generatedCode.add(LOCALS[i].getName() + " = " + STACK.pop());
                index += 1;
                continue;
            }
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

                    LocalVariable local = LOCALS[code.get(index + 1)];

                    generatedCode.add(local.getName() + " += " + code.get(index + 2));
                    //LOCALS[code.get(index + 1)].setValue();
                    index += 2;
                    continue; // continue the loop
                    //break;
                case "ldc": // just "ldc"
                    STACK.push((String)RawItem.getEntry(code.get(index + 1)).getValue());
                    break;
                default:
                    break;
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
                default:
                    break;
            }


        }

        for (String line : generatedCode) {
            System.out.println(line);
        }

        //codeAttribute.code
    }
}
