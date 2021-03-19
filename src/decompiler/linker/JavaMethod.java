package decompiler.linker;

import decompiler.Util;
import decompiler.reader.Opcode;
import decompiler.reader.RawItem;
import decompiler.reader.RawMethod;
import decompiler.reader.attributes.CodeAttribute;

import java.util.ArrayList;

public class JavaMethod {

    private final String name;
    private final String signature;
    private final String returnType;

    private final int methodIndex;
    //private final JavaClass myClass;

    //private String[] body;
    private ArrayList<String> generatedCode = new ArrayList<>();

    public JavaMethod(String name, String signature, String returnType, int methodIndex) {//}, JavaClass myClass) {
        this.name = name;
        this.signature = signature;

        this.returnType = returnType;

        this.methodIndex = methodIndex;



        //this.myClass = myClass;

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

    private RawMethod getRawMethod() {
        return RawItem.currentClassInstance.methodContainer.methods.get(methodIndex);
    }

    private void parseRawBody() {
        CodeAttribute codeAttribute = this.getRawMethod().getCode();
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
                case PUTFIELD:
                    int field_index = (code.get(++index) << 8) | code.get(++index);

                    // set class member field at 'field_index' to popped value

                    // not the best solution below...
                    generatedCode.add("this." +
                            Util.toValidName(RawItem.getEntry(field_index).getName()) +
                                    " = " + STACK.pop() + ";");

                    /*
                        TODO:
                        make a static database for retrieving already fixed/corrected
                        constants fields classes methods ...

                        will be able to be retrieved by 'pool index', 'name', etc...
                     */
                case INVOKESPECIAL:
                    index+=2;
                    continue;
                default:
                    System.out.println("opcode " + name + " is not yet implemented");
            }

        }

        //for (String line : generatedCode) {
        //    System.out.println(line);
        //}

        //codeAttribute.code
    }

    @Override
    public String toString() {
        //System.out.println("DESCRIPTOR: " + getRawMethod().getDescriptor());
        StringBuilder s = new StringBuilder();

        // access flags
        s.append(getRawMethod().getAccessFlags());

        // return type
        if (returnType != null)
            s.append(returnType);

        // name
        s.append(name);

        // args
        s.append("(").append(this.signature).append(") {").append("\n");

        // exceptions
        // s.append("throws"); ...

        // body
        for (String line : generatedCode)
            s.append(line).append("\n");

        // terminal delimiter
        s.append("}");

        return s.toString();
    }
}
