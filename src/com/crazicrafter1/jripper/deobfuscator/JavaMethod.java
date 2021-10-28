package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.Opcode;
import com.crazicrafter1.jripper.decompiler.DecompiledMethod;
import com.crazicrafter1.jripper.decompiler.attributes.CodeAttr;

import java.util.*;

public class JavaMethod extends IDeobfuscated {

    static class Parameter {
        String mutatedType;
        String mutatedName;

        public Parameter(String mutatedType, String mutatedName) {
            this.mutatedType = mutatedType;
            this.mutatedName = mutatedName;
        }
    }

    /*
     * Bytecode parsed
     */
    private String mutatedMethodName;
    //private final ArrayList<String> args = new ArrayList<>();

    // Locals are referenced in method by INDEX,
    // But names have to be unique
    //private final Map<String, Local> locals = new LinkedHashMap<>();
    private final Set<String> locals = new LinkedHashSet<>();
    private String mutatedReturnType;

    private Map<String, Parameter> parameterMap = new LinkedHashMap<>();

    private final DecompiledMethod decompiledMethod;

    private final ArrayList<String> generatedCode = new ArrayList<>();

    public JavaMethod(JavaClass parentJavaClass, DecompiledMethod decompiledMethod) {
        super(parentJavaClass);
        this.decompiledMethod = decompiledMethod;
    }

    @Override
    public void process() {
        HashSet<String> inImports = new HashSet<>();
        StringBuilder inReturnType = new StringBuilder();

        ArrayList<String> parameterTypes =
                Util.getParameterTypes(
                        decompiledMethod.getMethodDescriptor(),
                        inImports,
                        inReturnType);

        /**
         * Parameter types will not this reference,
         * so add this to locals only if
         */

        // Only completely empty initializer
        if (decompiledMethod.isStaticInitializer()) {
            // Nothing
        } else {
            // if instance method or constructor
            if (decompiledMethod.isInstanceInitializer() || !decompiledMethod.isStatic()) {

                locals.add("this");

                // No return type
                if (decompiledMethod.isInstanceInitializer()) {
                    mutatedReturnType = null;
                } else {
                    mutatedReturnType = Util.toValidTypeName(inReturnType.toString());
                }
            } else
                mutatedReturnType = Util.toValidTypeName(inReturnType.toString());

            setMutatedMethodName(decompiledMethod.getMethodName());

            // Has parameters
            parameterTypes.forEach(parameterType -> {
                String validatedType = Util.toValidTypeName(parameterType);
                final String localMutatedName = validatedType.substring(0, 1).toLowerCase() + validatedType.substring(1);

                String end = localMutatedName;
                int id = 0;
                while (locals.contains(end))
                    end = localMutatedName + (id++);

                locals.add(end);
                this.parameterMap.put(end, new Parameter(validatedType, end));
            });
        }

        ((JavaClass)getParentDeobfuscator()).addClassImports(inImports);
    }

    public DecompiledMethod getDecompiledMethod() {
        return this.decompiledMethod;
    }

    public void setMutatedMethodName(String newName) {
        this.mutatedMethodName = Util.toValidName(newName);
    }

    public String getLocal(int index) {
        int i = 0;
        for (String entry : locals) {
            if (i == index) {
                return entry;
            }
            i++;
        }
        throw new IndexOutOfBoundsException("Index " + index + " exceeds local size");
    }

    //private ArrayList<String>

    // https://stackoverflow.com/questions/61000749/local-variable-table-in-jvm-stack
    private void generateMethodBody() {
        CodeAttr codeAttribute = this.getDecompiledMethod().getCode();
        ArrayList<Integer> code = codeAttribute.code;


        //LocalVariable[] LOCALS = null; //LocalVariable.from(codeAttribute);

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
                            getLocal(
                            code.get(++index)));
                    continue;
                case ILOAD_0:       // push local@0
                case FLOAD_0:
                case DLOAD_0:
                case LLOAD_0:
                case ALOAD_0:       // push ref
                    STACK.push(getLocal(0));
                    continue;
                case ILOAD_1:       // push local@1
                case FLOAD_1:
                case DLOAD_1:
                case LLOAD_1:
                case ALOAD_1:       // push ref
                    STACK.push(getLocal(1));
                    continue;
                case ILOAD_2:       // push local@2
                case FLOAD_2:
                case DLOAD_2:
                case LLOAD_2:
                case ALOAD_2:       // push ref
                    STACK.push(getLocal(2));
                    continue;
                case ILOAD_3:       // push local@3
                case FLOAD_3:
                case DLOAD_3:
                case LLOAD_3:
                case ALOAD_3:       // push ref
                    STACK.push(getLocal(3));
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
                    String first = getLocal(++index);

                    generatedCode.add(first + " += " + getLocal(++index) + ";");
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
                    generatedCode.add(getLocal(++index) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_0:      // pop and assign to local@0
                case FSTORE_0:
                case DSTORE_0:
                case LSTORE_0:
                    generatedCode.add(getLocal(0) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_1:      // pop and assign to local@1
                case FSTORE_1:
                case DSTORE_1:
                case LSTORE_1:
                    generatedCode.add(getLocal(1) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_2:      // pop and assign to local@2
                case FSTORE_2:
                case DSTORE_2:
                case LSTORE_2:
                    generatedCode.add(getLocal(2) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_3:      // pop and assign to local@3
                case FSTORE_3:
                case DSTORE_3:
                case LSTORE_3:
                    generatedCode.add(getLocal(3) + " = " + STACK.pop() + ";");
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
                    STACK.push("" + decompiledMethod.getMainClass().getEntry(code.get(++index)));
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


        if (!parameterMap.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, Parameter> entry : parameterMap.entrySet()) {
                s.append(entry.getValue().mutatedType).append(" ").append(entry.getValue().mutatedName);
                if (i != parameterMap.size() - 1) {
                    s.append(", ");
                }
            }
        }

        s.append(") {").append("\n");

        // exceptions
        // s.append("throws"); ...

        generateMethodBody();

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
