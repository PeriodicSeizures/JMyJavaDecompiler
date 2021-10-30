package com.crazicrafter1.jripper.decompiler;

import com.crazicrafter1.jripper.decompiler.constants.*;

import java.io.IOException;
import java.util.ArrayList;

public class ConstantContainer extends IDisassembled {

    private final static int CONSTANT_CLASS =               7;
    private final static int CONSTANT_FIELDREF =            9;
    private final static int CONSTANT_METHODREF =           10;
    private final static int CONSTANT_INTERFACEMETHODREF =  11;
    private final static int CONSTANT_STRING =              8;
    private final static int CONSTANT_INTEGER =             3;
    private final static int CONSTANT_FLOAT =               4;
    private final static int CONSTANT_LONG =                5;
    private final static int CONSTANT_DOUBLE =              6;
    private final static int CONSTANT_NAMEANDTYPE =         12;
    private final static int CONSTANT_UTF8 =                1;
    private final static int CONSTANT_METHODHANDLE =        15;
    private final static int CONSTANT_METHODTYPE =          16;
    private final static int CONSTANT_INVOKEDYNAMIC =       18;

    public ArrayList<Integer> spaced_indexes = new ArrayList<>();
    public ArrayList<IConstant> constants = new ArrayList<>();

    public ConstantContainer(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    /*
        Some notes (about constants and attributes):
            - invokedynamic instructions are recorded by BootstrapMethods atr

            - BootstrapMethods (atr) contains:
                MethodHandle constant
                ... others ...

            - MethodHandle (pool) can reference:
                Fieldref, Methodref, InterfaceMethodref
     */
    public void read(ByteReader bytes) throws IOException {
        // -1 is intentional according to oracle jvm docs
        final int constant_pool_count = bytes.readUnsignedShort() - 1;

        while (spaced_indexes.size() < constant_pool_count) {
            IConstant entry;

            // reads the 1 byte tag
            int tag = bytes.readUnsignedByte();

            switch (tag) {
                case CONSTANT_CLASS: entry = new ConstantClass(getMainClass()); break;
                case CONSTANT_FIELDREF: entry = new ConstantFieldref(getMainClass()); break;
                case CONSTANT_METHODREF: entry = new ConstantMethodref(getMainClass()); break;
                case CONSTANT_INTERFACEMETHODREF: entry = new ConstantInterfaceMethodref(getMainClass()); break;
                case CONSTANT_STRING: entry = new ConstantString(getMainClass()); break;
                case CONSTANT_INTEGER: entry = new ConstantInteger(getMainClass()); break;
                case CONSTANT_FLOAT: entry = new ConstantFloat(getMainClass()); break;
                case CONSTANT_LONG: entry = new ConstantLong(getMainClass()); break;
                case CONSTANT_DOUBLE: entry = new ConstantDouble(getMainClass()); break;
                case CONSTANT_NAMEANDTYPE: entry = new ConstantNameAndType(getMainClass()); break;
                case CONSTANT_UTF8: entry = new ConstantUtf8(getMainClass()); break;
                case CONSTANT_METHODHANDLE: entry = new ConstantMethodHandle(getMainClass()); break;
                case CONSTANT_METHODTYPE: entry = new ConstantMethodType(getMainClass()); break;
                case CONSTANT_INVOKEDYNAMIC: entry = new ConstantInvokeDynamic(getMainClass()); break;
                default: throw new RuntimeException("ConstantPool entry tagged " + tag + " does not exist");
            }

            entry.read(bytes);

            spaced_indexes.add(constants.size());

            /*
             * Long and Double take up 2 spaces,
             * so add it again to the spaced array
             * nifty little hack
             */
            if (entry instanceof ConstantLong || entry instanceof ConstantDouble) {
                spaced_indexes.add(constants.size());
            }

            constants.add(entry);
        }
    }

    public IConstant getEntry(int i) {
        return getMainClass().constantPoolContainer.constants.get(
                getMainClass().constantPoolContainer.spaced_indexes.get(i-1));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ConstantPool}: ").append("\n");

        for (int i=1; i<=spaced_indexes.size(); i++) {
            IConstant javaPoolEntry = getEntry(i);
            stringBuilder.append("  \t").append(i).append(" : ").append(javaPoolEntry.toString()).append("\n");
        }

        stringBuilder.append("count: ").append(spaced_indexes.size()).append("\n");

        return stringBuilder.toString();
    }

}
