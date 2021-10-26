package decompiler.reader;

import decompiler.except.InvalidConstantPoolEntryException;
import decompiler.reader.pool.*;

import java.io.IOException;
import java.util.ArrayList;

public class ConstantContainer extends RItem {

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
    public ArrayList<RawConstant> constants = new ArrayList<>();



    /*
        Some notes (about constants and attributes):
            - invokedynamic instructions are recorded by BootstrapMethods atr

            - BootstrapMethods (atr) contains:
                MethodHandle constant
                ... others ...

            - MethodHandle (pool) can reference:
                Fieldref, Methodref, InterfaceMethodref
     */





    public void read() throws IOException {
        // -1 is intentional according to oracle jvm docs
        final int constant_pool_count = bytes.readUnsignedShort() - 1;

        while (spaced_indexes.size() < constant_pool_count) {
            RawConstant entry;

            // reads the 1 byte tag
            int tag = bytes.readUnsignedByte();

            switch (tag) {
                case CONSTANT_CLASS -> entry = new ConstantClass();
                case CONSTANT_FIELDREF -> entry = new ConstantFieldref();
                case CONSTANT_METHODREF -> entry = new ConstantMethodref();
                case CONSTANT_INTERFACEMETHODREF -> entry = new ConstantInterfaceMethodref();
                case CONSTANT_STRING -> entry = new ConstantString();
                case CONSTANT_INTEGER -> entry = new ConstantInteger();
                case CONSTANT_FLOAT -> entry = new ConstantFloat();
                case CONSTANT_LONG -> entry = new ConstantLong();
                case CONSTANT_DOUBLE -> entry = new ConstantDouble();
                case CONSTANT_NAMEANDTYPE -> entry = new ConstantNameAndType();
                case CONSTANT_UTF8 -> entry = new ConstantUtf8();
                case CONSTANT_METHODHANDLE -> entry = new ConstantMethodHandle();
                case CONSTANT_METHODTYPE -> entry = new ConstantMethodType();
                case CONSTANT_INVOKEDYNAMIC -> entry = new ConstantInvokeDynamic();
                default -> throw new InvalidConstantPoolEntryException("constant tagged " + tag + " is invalid");
            }

            entry.read();

            spaced_indexes.add(constants.size());

            /*
             * Long and Double take up 2 spaces,
             * so add it again to the spaced array
             */
            if (entry instanceof ConstantLong || entry instanceof ConstantDouble) {
                spaced_indexes.add(constants.size());
            }

            constants.add(entry);

        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ConstantPool}: ").append("\n");

        for (int i=1; i<=spaced_indexes.size(); i++) {
            RawConstant javaPoolEntry = RItem.getEntry(i);
            stringBuilder.append("  \t").append(i).append(" : ").append(javaPoolEntry.toString()).append("\n");
        }

        stringBuilder.append("count: ").append(spaced_indexes.size()).append("\n");

        return stringBuilder.toString();
    }

}
