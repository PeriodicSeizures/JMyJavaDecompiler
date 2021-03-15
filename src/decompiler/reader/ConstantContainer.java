package decompiler.reader;

import decompiler.Result;
import decompiler.reader.pool.*;

import java.io.IOException;
import java.util.ArrayList;

public class ConstantContainer extends RawItem {

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





    public Result read() throws IOException {
        // -1 is intentional according to oracle jvm docs
        int constant_pool_count = bytes.readUnsignedShort() - 1;

        //System.out.println("constant_pool_count: " + constant_pool_count);
        //System.out.println("pool count: " + --constant_pool_count);
        //spaced_indexes.add(0); // dummy add so it starts at 1

        for(; constant_pool_count > 0; constant_pool_count--) {
            RawConstant entry = null;

            // reads the 1 byte tag
            int tag = bytes.readUnsignedByte();

            //System.out.println("tag: " + tag);

            switch (tag) {
                case CONSTANT_CLASS: entry = new ConstantClass(); break;
                case CONSTANT_FIELDREF: entry = new ConstantFieldref(); break;
                case CONSTANT_METHODREF: entry = new ConstantMethodref(); break;
                case CONSTANT_INTERFACEMETHODREF: entry = new ConstantInterfaceMethodref(); break;
                case CONSTANT_STRING: entry = new ConstantString(); break;
                case CONSTANT_INTEGER: entry = new ConstantInteger(); break;
                case CONSTANT_FLOAT: entry = new ConstantFloat(); break;
                case CONSTANT_LONG: entry = new ConstantLong(); break;
                case CONSTANT_DOUBLE: entry = new ConstantDouble(); break;
                case CONSTANT_NAMEANDTYPE: entry = new ConstantNameAndType(); break;
                case CONSTANT_UTF8: entry = new ConstantUtf8(); break;
                case CONSTANT_METHODHANDLE: entry = new ConstantMethodHandle(); break;
                case CONSTANT_METHODTYPE: entry = new ConstantMethodType(); break;
                case CONSTANT_INVOKEDYNAMIC: entry = new ConstantInvokeDynamic(); break;
                default: {
                    //return Result.INVALID_POOL_ENTRY; // error
                    break;
                }
            }

            //if (entry == null) {
            //    System.out.println("size: " + constants.size());
            //    return Result.INVALID_POOL_ENTRY;
            //}

            entry.read();

            spaced_indexes.add(constants.size());

            if (entry instanceof ConstantLong || entry instanceof ConstantDouble) {
                spaced_indexes.add(constants.size());
            }

            constants.add(entry);

        }

        //System.out.println("size: " + constants.size());

        //System.out.println(this.get(14).toString());

        return Result.OK;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ConstantPool}: ").append("\n");

        for (int i=1; i<=spaced_indexes.size(); i++) {
            RawConstant javaPoolEntry = RawItem.getEntry(i);
            stringBuilder.append("  \t").append(i).append(" : ").append(javaPoolEntry.toString()).append("\n");
        }

        stringBuilder.append("count: ").append(spaced_indexes.size()).append("\n");

        return stringBuilder.toString();
    }

    //public RawConstant get(int i) {
    //    return constants.get(spaced_indexes.get(i-1));
    //}

}
