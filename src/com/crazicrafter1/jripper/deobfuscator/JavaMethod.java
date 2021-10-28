package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.Opcode;
import com.crazicrafter1.jripper.decompiler.DecompiledMethod;
import com.crazicrafter1.jripper.decompiler.attributes.CodeAttr;

import java.util.ArrayList;
import java.util.HashSet;

public class JavaMethod {


    /*
     * Bytecode parsed
     */
    private String mutatedMethodName;
    private final ArrayList<String> args;
    private String mutatedReturnType;       /// TODO this MUST NOT MUTATE
                                            /// should point to a return type,
                                            /// Return type is easy to mutate for validity
                                            /// just validating should be it

    private DecompiledMethod decompiledMethod;

    private ArrayList<String> generatedCode = new ArrayList<>();

    public JavaMethod(DecompiledMethod decompiledMethod, JavaClass parentJavaClass) {

        this.decompiledMethod = decompiledMethod;

        HashSet<String> inImports = new HashSet<>();
        StringBuilder inReturnType = new StringBuilder();
        ArrayList<String> parameterTypes = Util.getParameterTypes(decompiledMethod.getMethodDescriptor(), inImports, inReturnType);

        if (decompiledMethod.isInstanceInitializer()) {
            setMutatedMethodName(parentJavaClass.getClassName());
            this.mutatedReturnType = null;
        }
        else if (decompiledMethod.isStaticInitializer()) {
            mutatedMethodName = null;
            mutatedReturnType = null;
        } else {
            //this.methodName = NameUtil.toValidName(
            //        JavaUtil.getUnqualifiedName(rMethod.getName()));

            setMutatedMethodName(decompiledMethod.getMethodName());
            //this.returnType;

            this.mutatedReturnType = inReturnType.toString();
        }

        this.args = new ArrayList<>();
        for (String parameterType : parameterTypes) {
            //TODO independent wrapper for both TYPE and NAME for locals
            this.args.add(NameUtil.toValidTypeName(parameterType));
        }

        this.decompiledMethod = decompiledMethod;

        parseRawBody();
    }

    public DecompiledMethod getDecompiledMethod() {
        return this.decompiledMethod;
    }

    public void setMutatedMethodName(String newName) {
        this.mutatedMethodName = Util.toValidName(newName);
    }

    // https://stackoverflow.com/questions/61000749/local-variable-table-in-jvm-stack
    private void parseRawBody() {
        CodeAttr codeAttribute = this.getDecompiledMethod().getCode();
        ArrayList<Integer> code = codeAttribute.code;

        LocalVariable[] LOCALS = null; //LocalVariable.from(codeAttribute);

        //ArrayList<String> OPERAND_STACK = new ArrayList<>(); // changes as operations are performed on the stack

        OperandStack STACK = new OperandStack();

        StringBuilder a = new StringBuilder();

        for (int index=0; index<code.size(); index++) {
            int c = code.get(index);
            Opcode opcode = Opcode.getOpcode(c);
            //int varargs = Opcode.getVarArgs(code, index);

            String name = opcode.name();

            //System.out.println(name);

            // then this opcode appends to the stack

            switch (opcode) {
                case ILOAD:         // push local@next
                case FLOAD:
                case DLOAD:
                case LLOAD:
                case ALOAD:         // push ref
                    STACK.push(
                            LOCALS[
                            code.get(++index)].
                                    getName());
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
                case IREM:
                case LREM:
                case FREM:
                case DREM:
                    STACK.rem();
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
                case POP:
                    STACK.pop();
                    continue;
                case POP2:
                    System.out.println(name + " is not completely supported!");
                    STACK.pop2();
                    continue;
                case DUP:
                    STACK.push(STACK.get());
                    continue;
                case DUP_X1:
                    STACK.push(STACK.get(), 2);
                    continue;

                    //case DUP_X2:
                    //    System.out.println(name + " is not completely supported!");

                case RETURN:           // return from function (void)
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
                case FCONST_0:
                case ICONST_0:      // add '0' ? useless
                    continue;
                case FCONST_1:
                case ICONST_1:
                    STACK.push("1");
                    continue;
                case FCONST_2:
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
                case LDC:           // push constant
                    STACK.push("" + decompiledMethod.getBelongingClass().getEntry(code.get(++index)));
                    continue;
                case BIPUSH:
                    int v = (byte) (int) code.get(++index); // two's complement

                    //v = 255 - v;
                    //v += 1;
                    //v = -v;
                    STACK.push("" + v);
                    continue;
                case PUTFIELD:
                    int field_index = (code.get(++index) << 8) | code.get(++index);

                    // set class member field at 'field_index' to popped value

                    // not the best solution below...
                    //generatedCode.add("this." +
                    //        NameUtil.toValidName(IDecompiled.getEntry(field_index).getName()) +
                    //        " = " + STACK.pop() + ";");
                    continue;
                /*
                    TODO:
                    make a static database for retrieving already fixed/corrected
                    constants fields classes methods ...

                    will be able to be retrieved by 'pool index', 'name', etc...
                 */
                case INVOKESPECIAL: {
                    //generatedCode.add("super();");
                    //System.out.println("\nindex: " + index);
                    int constIndex = (code.get(++index) << 8) | code.get(++index);
                    //System.out.println("INVOKE_SPECIAL: " +
                    //        RawItem.getEntry(constIndex).getValue());
                    //System.out.println();
                    continue;
                }
                case NEW: {
                    int constIndex = (code.get(++index) << 8) | code.get(++index);
                    //String newName = NameUtil.toValidTypeName(
                    //        JavaUtil.getUnqualifiedName((String) IDecompiled.getEntry(constIndex).getValue()));

                    //STACK.push("new " + newName + "()");
                    //index += 2;
                    continue;
                }
                case NOP:
                    continue;
                case ANEWARRAY: {
                    String count = STACK.pop();
                    int constIndex = (code.get(++index) << 8) | code.get(++index);
                    //STACK.push("new " + RawItem.getEntry(constIndex).getValue());
                    continue;
                }
                case CHECKCAST:
                    index += 2;
                    continue;
                default:
                    System.out.println("opcode " + name + " is not yet implemented");

            }


        }

    }

    @Override
    public String toString() {
        //System.out.println("DESCRIPTOR: " + getRawMethod().getDescriptor());
        StringBuilder s = new StringBuilder();

        /*
         * static initializer
         */
        if (mutatedMethodName == null && mutatedReturnType == null) {
            s.append("//<clinit>\n");
            s.append("static {");
            s.append("\n");
            s.append("}");
            return s.toString();
        } else if (mutatedReturnType == null) {
            s.append("//<init>\n");
        }

        // access flags
        s.append(getDecompiledMethod().getAccessFlags());

        // return type
        if (mutatedReturnType != null)
            s.append(" ").append(mutatedReturnType);

        // name
        s.append(" ").append(mutatedMethodName);

        // args
        s.append("(");

        if (args != null) {
            for (int i = 0; i < args.size(); i++) {
                s.append(args.get(i));
                if (i != args.size() - 1) {
                    s.append(", ");
                }
            }
        }

        s.append(") {").append("\n");

        // exceptions
        // s.append("throws"); ...

        // body
        for (int i=0; i<generatedCode.size(); i++) {
            String line = generatedCode.get(i);

            if (i == generatedCode.size()-1 && line.equals("return;")) break;

            s.append(line).append("\n");
        }


        // terminal delimiter
        s.append("}");

        return s.toString();
    }
}
