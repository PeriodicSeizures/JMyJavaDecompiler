package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.*;
import com.crazicrafter1.jripper.disassemble.constants.ConstantClass;
import com.crazicrafter1.jripper.disassemble.constants.IMethodRef;
import com.crazicrafter1.jripper.types.*;
import com.crazicrafter1.jripper.util.Util;
import com.crazicrafter1.jripper.disassemble.Opcode;
import com.crazicrafter1.jripper.disassemble.DisassembledMethod;
import com.crazicrafter1.jripper.disassemble.attributes.CodeAttr;

import java.util.*;

/**
 * https://www.benf.org/other/cfr/obfuscation_method_renaming.html
 */
public class DecompiledMethod
        extends AbstractDecompiled
        implements IMethodDefined {

    static class Parameter {
        String typeName;
        String name;

        public Parameter(String typeName, String name) {
            this.typeName = typeName;
            this.name = name;
        }
    }

    private String methodName;
    private final Set<String> locals = new LinkedHashSet<>();
    private String returnType;

    private Map<String, Parameter> parameterMap = new LinkedHashMap<>();

    private final ArrayList<String> generatedCodeBody = new ArrayList<>();

    public DecompiledMethod(DecompiledClass parentJavaClass, DisassembledMethod disassembledMethod) {
        super(parentJavaClass, disassembledMethod);
    }

    @Override
    public String getName() {
        return methodName;
    }

    @Override
    public DisassembledMethod getDisassemblyUnit() {
        return (DisassembledMethod) super.getDisassemblyUnit();
    }

    @Override
    public boolean isPublic() {
        return getDisassemblyUnit().isPublic();
    }

    @Override
    public boolean isFinal() {
        return getDisassemblyUnit().isFinal();
    }

    @Override
    public boolean isPrivate() {
        return getDisassemblyUnit().isPrivate();
    }

    @Override
    public boolean isProtected() {
        return getDisassemblyUnit().isProtected();
    }

    @Override
    public boolean isStatic() {
        return getDisassemblyUnit().isStatic();
    }

    public void setMethodName(String newName) {
        if (!getMethodType().isInitializer())
            this.methodName = Util.toValidName(newName);
    }

    public String getLocal(int index) {
        int i = 0;
        for (String entry : this.locals) {
            if (i == index) {
                return entry;
            }
            i++;
        }
        throw new IndexOutOfBoundsException("Index " + index + " exceeds method locals size");
    }

    private void generateMethodBody() {
        CodeAttr codeAttribute = this.getDisassemblyUnit().getCodeAttr();
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

                    generatedCodeBody.add(first + " += " + getLocal(++index) + ";");
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
                    generatedCodeBody.add("return;");
                    continue;
                case IRETURN:       // return value from function
                case FRETURN:
                case DRETURN:
                case LRETURN:
                case ARETURN:
                    generatedCodeBody.add("return " + STACK.pop() + ";");
                    continue;
                case ISTORE:        // pop and assign to local@next
                case FSTORE:
                case DSTORE:
                case LSTORE:
                    generatedCodeBody.add(getLocal(++index) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_0:      // pop and assign to local@0
                case FSTORE_0:
                case DSTORE_0:
                case LSTORE_0:
                    generatedCodeBody.add(getLocal(0) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_1:      // pop and assign to local@1
                case FSTORE_1:
                case DSTORE_1:
                case LSTORE_1:
                    generatedCodeBody.add(getLocal(1) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_2:      // pop and assign to local@2
                case FSTORE_2:
                case DSTORE_2:
                case LSTORE_2:
                    generatedCodeBody.add(getLocal(2) + " = " + STACK.pop() + ";");
                    continue;
                case ISTORE_3:      // pop and assign to local@3
                case FSTORE_3:
                case DSTORE_3:
                case LSTORE_3:
                    generatedCodeBody.add(getLocal(3) + " = " + STACK.pop() + ";");
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
                    STACK.push("" + getDisassemblyUnit().getConstant(code.get(++index)).get());
                    continue;
                case BIPUSH:
                    int v = (byte) (int) code.get(++index); // two's complement

                    //v = 255 - v;
                    //v += 1;
                    //v = -v;
                    STACK.push("" + v);
                    continue;
                case PUTFIELD: {
                    int field_index = (code.get(++index) << 8) | code.get(++index);

                    String[] popped = STACK.pop2();

                    IBaseField field = getIField(field_index);

                    generatedCodeBody.add("((" + field.getBaseClass().getSimpleName() + ")" +
                            popped[0] + ")." +
                            field.getName() +
                            " = " + popped[1] + ";");

                    //generatedCodeBody.add(popped[0] + "." +
                    //        getIField(field_index).getName()
                    //        + " = " + popped[1] + ";");

                    continue;
                }
                case GETFIELD: {
                    int field_index = (code.get(++index) << 8) | code.get(++index);

                    String ref = STACK.pop();

                    // ref must be remanaged, overridden?
                    // to allow for super
                    IBaseField field = getIField(field_index);

                        // this
                    STACK.push("((" + field.getBaseClass().getSimpleName() + ")" +
                            ref + ")." +
                            field.getName());

                    continue;
                }
                case PUTSTATIC: {
                    int field_index = (code.get(++index) << 8) | code.get(++index);

                    String value = STACK.pop();

                    IBaseField field = getIField(field_index);

                    generatedCodeBody.add(
                            field.getBaseClass().getSimpleName() + "." +
                                    field.getName() + " = " + value + ";");

                    continue;
                }
                case GETSTATIC: {
                    int field_index = (code.get(++index) << 8) | code.get(++index);

                    IBaseField field = getIField(field_index);

                    STACK.push(field.getBaseClass().getSimpleName() + "." + field.getName());

                    continue;
                }
                case NEW: {
                    final int class_index = (code.get(++index) << 8) | code.get(++index);

                    IBaseClass baseClass = getIClass(class_index);

                    STACK.newObject(baseClass);

                    continue;
                }
                case INVOKESPECIAL: {
                    final int method_index = (code.get(++index) << 8) | code.get(++index);

                    IBaseMethod method = getIMethod(method_index);

                    generatedCodeBody.add(STACK.invokeSpecial(method));

                    if (true)
                        continue;
/*
                    if (method.getIClass().getSuperClass().equals(this.getIClass())) {

                        if (method instanceof DecompiledMethod &&
                                ((DecompiledMethod) method).getAccess() == EnumAccess.PROTECTED) {

                            // Then invoke
                            generatedCodeBody.add(method.getIClass().getSimpleName() + ".");

                        } else if (((DecompiledClass) getIClass()).getDisassemblyUnit().isSuper() &&
                        !method.getType().isInstance()) {

                            // Then find by lookup

                        }
                    }

 */



                    if (EnumMethod.from(method.getName()) != null) {
                        // test what object is on stack

                        //if ()

                        List<String> parameters = method.getParameterTypes();
                        StringBuilder builder = new StringBuilder("super(");
                        if (!parameters.isEmpty()) {
                            // Pop the function arguments from stack,
                            // Including 'this' object reference (with the +1)
                            ArrayList<String> popped = new ArrayList<>();
                            for (int pIndex = 0; pIndex < parameters.size()+1; pIndex++) {
                                popped.add(STACK.pop());
                            }

                            // Remove 'this' object reference
                            popped.remove(popped.size()-1);

                            // Popped should now be the param size
                            assert popped.size() == parameters.size() : "Popped should match parameters size";

                            // Now reverse popped to iterate in arg order
                            Collections.reverse(popped);

                            for (int pIndex = 0; pIndex < popped.size(); pIndex++) {
                                builder.append(popped.get(pIndex));
                                if (pIndex != popped.size() - 1)
                                    builder.append(", ");
                            }
                        }
                        builder.append(");");
                        generatedCodeBody.add(builder.toString());
                    }

                    continue;
                }
                case INVOKEVIRTUAL: {
                    final int method_index = (code.get(++index) << 8) | code.get(++index);

                    IBaseMethod method = getIMethod(method_index);

                    generatedCodeBody.add(STACK.invokeVirtual(method));

                    continue;
                }
                case NOP:
                    continue;
                //case ANEWARRAY: {
                //    String count = STACK.pop();
                //    int constIndex = (code.get(++index) << 8) | code.get(++index);
                //    //STACK.push("new " + RawItem.getEntry(constIndex).getValue());
                //    continue;
                //}
                case CHECKCAST:
                    index += 2;
                    continue;
                default:
                    System.out.println("opcode " + name + " is not yet implemented");

            }


        }

    }

    public EnumVisibility getAccess() {
        return getDisassemblyUnit().getVisibility();
    }

    @Override
    public void validationPhase() {
        DisassembledMethod unit = getDisassemblyUnit();

        StringBuilder inReturnType = new StringBuilder();

        ArrayList<String> parameterTypes =
                Util.getParameterTypes(
                        unit.getDescriptor(),
                        getImportSet(),
                        inReturnType);



        /**
         * Parameter types will not this reference,
         * so add this to locals only if
         */

        // Only completely empty initializer
        if (unit.getMethodType() == EnumMethod.STATIC_INITIALIZER) {
            // Nothing
        } else {
            // if instance method or constructor
            if (unit.getMethodType().isInstance()) {

                locals.add("this");

                // No return type
                if (unit.getMethodType() == EnumMethod.INSTANCE_INITIALIZER) {
                    returnType = null;
                    methodName = getHolder().getSimpleName();
                } else {
                    returnType = Util.toValidTypeName(inReturnType.toString());
                }
            } else
                returnType = Util.toValidTypeName(inReturnType.toString());

            setMethodName(unit.getName());

            // Has parameters
            parameterTypes.forEach(parameterType -> {
                final String validatedType = Util.toValidTypeName(parameterType);
                final String localMutatedName = validatedType.substring(0, 1).toLowerCase();

                String name = localMutatedName;
                int ordinal = 0;
                while (locals.contains(name) || name.equals(validatedType))
                    name = localMutatedName + (ordinal++);

                locals.add(name);

                this.parameterMap.put(name, new Parameter(validatedType, name));
            });
        }
    }

    @Override
    public void linkingPhase() {
        generateMethodBody();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IBaseMethod))
            return false;

        if (obj == this)
            return true;

        IBaseMethod other = (IBaseMethod) obj;

        return this.getBaseClass().equals(other.getBaseClass()) &&
                this.getName().equals(other.getName()) &&
                this.getDescriptor().equals(other.getDescriptor());
    }

    @Override
    public IClassDefined getBaseClass() {
        return getHolder();
    }

    @Override
    public String getDescriptor() {
        return getDisassemblyUnit().getDescriptor();
    }

    @Override
    public String getReturnType() {
        return returnType;
    }

    @Override
    public EnumMethod getMethodType() {
        return getDisassemblyUnit().getMethodType();
    }

    @Override
    public List<String> getParameterTypes() {
        List<String> parameterTypes = new ArrayList<>();

        for (Parameter p : parameterMap.values()) {
            parameterTypes.add(p.typeName);
        }

        return parameterTypes;
    }

    @Override
    public String toString() {

        ArrayList<String> lines = new ArrayList<>();

        /*
         * static initializer
         */
        if (methodName == null && returnType == null) {
            lines.add("//<clinit>");
            lines.add("static {");
            lines.add("");
            lines.add("}");

            return Util.beautify(lines);
        } else if (returnType == null) {
            lines.add("//<init>");
        }

        // access flags
        StringBuilder flags = new StringBuilder(getDisassemblyUnit().getAccessFlags());

        // return type
        if (returnType != null)
            flags.append(" ").append(returnType);

        // name
        flags.append(" ").append(methodName).append("(");

        if (!parameterMap.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, Parameter> entry : parameterMap.entrySet()) {
                flags.append(entry.getValue().typeName).append(" ").append(entry.getValue().name);
                if (i != parameterMap.size() - 1) {
                    flags.append(", ");
                }
                i++;
            }
        }

        flags.append(") {");

        lines.add(flags.toString());

        // body
        for (int i = 0; i< generatedCodeBody.size(); i++) {
            String line = generatedCodeBody.get(i);

            if (i == generatedCodeBody.size()-1 && line.equals("return;")) break;

            lines.add(line);
        }

        lines.add("}");

        return Util.beautify(lines);
    }
}
