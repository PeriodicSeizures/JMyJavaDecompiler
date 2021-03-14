package decompiler.classfile.attributes;

import decompiler.Result;
import decompiler.classfile.AttributeContainer;
import decompiler.classfile.JavaItem;
import decompiler.classfile.bytecode.Opcode;

import java.io.IOException;
import java.util.ArrayList;

public class CodeAttribute extends JavaAttribute {


    /*
        instruction opcodes:
     */
    //private static final String[] OPCODES = new String[] {
    //    "NOP", "ACONST_NULL", "ICONST_M1", "ICONST_0", "ICONST_1", "ICONST_2", "ICONST_3", "ICONST_4", "ICONST_5",
    //    "LCONST_0", "LCONST_1", "FCONST_0", "FCONST_1", "FCONST_2", "DCONST_0", "DCONST_1", "BIPUSH",
    //    "SIPUSH", "LDC", "LDC_W", "LDC2_W", "ILOAD", "LLOAD", "FLOAD", "DLOAD",
    //    "ALOAD", "ILOAD_0", "ILOAD_1", "ILOAD_2", "ILOAD_3", "LLOAD_0", "LLOAD_1", "LLOAD_2",
    //    "LLOAD_3", "FLOAD_0", "FLOAD_1", "FLOAD_2", "FLOAD_3", "DLOAD_0", "DLOAD_1", "DLOAD_2",
    //    "DLOAD_3", "ALOAD_0", "ALOAD_1", "ALOAD_2", "ALOAD_3", "IALOAD", "LALOAD", "FALOAD",
    //    "DALOAD", "AALOAD", "BALOAD", "CALOAD", "SALOAD", "ISTORE", "LSTORE", "FSTORE",
    //    "DSTORE", "ASTORE", "ISTORE_0", "ISTORE_1", "ISTORE_2", "ISTORE_3", "LSTORE_0", "LSTORE_1",
    //    "LSTORE_2", "LSTORE_3", "FSTORE_0", "FSTORE_1", "FSTORE_2", "FSTORE_3", "DSTORE_0", "DSTORE_1",
    //    "DSTORE_2", "DSTORE_3", "ASTORE_0", "ASTORE_1", "ASTORE_2", "ASTORE_3", "IASTORE", "LASTORE",
    //    "FASTORE", "DASTORE", "AASTORE", "BASTORE", "CASTORE", "SASTORE", "POP", "POP2",
    //    "DUP", "DUP_X1", "DUP_X2", "DUP2", "DUP2_X1", "DUP2_X2", "SWAP", "IADD",
    //    "LADD", "FADD", "DADD", "ISUB", "LSUB", "FSUB", "DSUB", "IMUL",
    //    "LMUL", "FMUL", "DMUL", "IDIV", "LDIV", "FDIV", "DDIV", "IREM",
    //    "LREM", "FREM", "DREM", "INEG", "LNEG", "FNEG", "DNEG", "ISHL",
    //    "LSHL", "ISHR", "LSHR", "IUSHR", "LUSHR", "IAND", "LAND", "IOR",
    //    "LOR", "IXOR", "LXOR", "IINC", "I2L", "I2F", "I2D", "L2I",
    //    "L2F", "L2D", "F2I", "F2L", "F2D", "D2I", "D2L", "D2F",
    //    "I2B", "I2C", "I2S", "LCMP", "FCMPL", "FCMPG", "DCMPL", "DCMPG",
    //    "IFEQ", "IFNE", "IFLT", "IFGE", "IFGT", "IFLE", "IF_ICMPEQ", "IF_ICMPNE",
    //    "IF_ICMPLT", "IF_ICMPGE", "IF_ICMPGT", "IF_ICMPLE", "IF_ACMPEQ", "IF_ACMPNE", "GOTO", "JSR",
    //    "RET", "TABLESWITCH", "LOOKUPSWITCH", "IRETURN", "LRETURN", "FRETURN", "DRETURN", "ARETURN",
    //    "RETURN", "GETSTATIC", "PUTSTATIC", "GETFIELD", "PUTFIELD", "INVOKEVIRTUAL", "INVOKESPECIAL", "INVOKESTATIC",
    //    "INVOKEINTERFACE", "INVOKEDYNAMIC", "NEW", "NEWARRAY", "ANEWARRAY", "ARRAYLENGTH", "ATHROW", "CHECKCAST",
    //    "INSTANCEOF", "MONITORENTER", "MONITOREXIT", "WIDE", "MULTIANEWARRAY", "IFNULL", "IFNONNULL", "GOTO_W",
    //    "JSR_W"
    //};

    private static final Opcode[] OPCODES = {
        Opcode.NOP,
        Opcode.ACONST_NULL,
        Opcode.ICONST_M1,
        Opcode.ICONST_0,
        Opcode.ICONST_1,
        Opcode.ICONST_2,
        Opcode.ICONST_3,
        Opcode.ICONST_4,
        Opcode.ICONST_5,
        Opcode.LCONST_0,
        Opcode.LCONST_1,
        Opcode.FCONST_0,
        Opcode.FCONST_1,
        Opcode.FCONST_2,
        Opcode.DCONST_0,
        Opcode.DCONST_1,
        Opcode.BIPUSH,
        Opcode.SIPUSH,
        Opcode.LDC,
        Opcode.LDC_W,
        Opcode.LDC2_W,
        Opcode.ILOAD,
        Opcode.LLOAD,
        Opcode.FLOAD,
        Opcode.DLOAD,
        Opcode.ALOAD,
        Opcode.ILOAD_0,
        Opcode.ILOAD_1,
        Opcode.ILOAD_2,
        Opcode.ILOAD_3,
        Opcode.LLOAD_0,
        Opcode.LLOAD_1,
        Opcode.LLOAD_2,
        Opcode.LLOAD_3,
        Opcode.FLOAD_0,
        Opcode.FLOAD_1,
        Opcode.FLOAD_2,
        Opcode.FLOAD_3,
        Opcode.DLOAD_0,
        Opcode.DLOAD_1,
        Opcode.DLOAD_2,
        Opcode.DLOAD_3,
        Opcode.ALOAD_0,
        Opcode.ALOAD_1,
        Opcode.ALOAD_2,
        Opcode.ALOAD_3,
        Opcode.IALOAD,
        Opcode.LALOAD,
        Opcode.FALOAD,
        Opcode.DALOAD,
        Opcode.AALOAD,
        Opcode.BALOAD,
        Opcode.CALOAD,
        Opcode.SALOAD,
        Opcode.ISTORE,
        Opcode.LSTORE,
        Opcode.FSTORE,
        Opcode.DSTORE,
        Opcode.ASTORE,
        Opcode.ISTORE_0,
        Opcode.ISTORE_1,
        Opcode.ISTORE_2,
        Opcode.ISTORE_3,
        Opcode.LSTORE_0,
        Opcode.LSTORE_1,
        Opcode.LSTORE_2,
        Opcode.LSTORE_3,
        Opcode.FSTORE_0,
        Opcode.FSTORE_1,
        Opcode.FSTORE_2,
        Opcode.FSTORE_3,
        Opcode.DSTORE_0,
        Opcode.DSTORE_1,
        Opcode.DSTORE_2,
        Opcode.DSTORE_3,
        Opcode.ASTORE_0,
        Opcode.ASTORE_1,
        Opcode.ASTORE_2,
        Opcode.ASTORE_3,
        Opcode.IASTORE,
        Opcode.LASTORE,
        Opcode.FASTORE,
        Opcode.DASTORE,
        Opcode.AASTORE,
        Opcode.BASTORE,
        Opcode.CASTORE,
        Opcode.SASTORE,
        Opcode.POP,
        Opcode.POP2,
        Opcode.DUP,
        Opcode.DUP_X1,
        Opcode.DUP_X2,
        Opcode.DUP2,
        Opcode.DUP2_X1,
        Opcode.DUP2_X2,
        Opcode.SWAP,
        Opcode.IADD,
        Opcode.LADD,
        Opcode.FADD,
        Opcode.DADD,
        Opcode.ISUB,
        Opcode.LSUB,
        Opcode.FSUB,
        Opcode.DSUB,
        Opcode.IMUL,
        Opcode.LMUL,
        Opcode.FMUL,
        Opcode.DMUL,
        Opcode.IDIV,
        Opcode.LDIV,
        Opcode.FDIV,
        Opcode.DDIV,
        Opcode.IREM,
        Opcode.LREM,
        Opcode.FREM,
        Opcode.DREM,
        Opcode.INEG,
        Opcode.LNEG,
        Opcode.FNEG,
        Opcode.DNEG,
        Opcode.ISHL,
        Opcode.LSHL,
        Opcode.ISHR,
        Opcode.LSHR,
        Opcode.IUSHR,
        Opcode.LUSHR,
        Opcode.IAND,
        Opcode.LAND,
        Opcode.IOR,
        Opcode.LOR,
        Opcode.IXOR,
        Opcode.LXOR,
        Opcode.IINC,
        Opcode.I2L,
        Opcode.I2F,
        Opcode.I2D,
        Opcode.L2I,
        Opcode.L2F,
        Opcode.L2D,
        Opcode.F2I,
        Opcode.F2L,
        Opcode.F2D,
        Opcode.D2I,
        Opcode.D2L,
        Opcode.D2F,
        Opcode.I2B,
        Opcode.I2C,
        Opcode.I2S,
        Opcode.LCMP,
        Opcode.FCMPL,
        Opcode.FCMPG,
        Opcode.DCMPL,
        Opcode.DCMPG,
        Opcode.IFEQ,
        Opcode.IFNE,
        Opcode.IFLT,
        Opcode.IFGE,
        Opcode.IFGT,
        Opcode.IFLE,
        Opcode.IF_ICMPEQ,
        Opcode.IF_ICMPNE,
        Opcode.IF_ICMPLT,
        Opcode.IF_ICMPGE,
        Opcode.IF_ICMPGT,
        Opcode.IF_ICMPLE,
        Opcode.IF_ACMPEQ,
        Opcode.IF_ACMPNE,
        Opcode.GOTO,
        Opcode.JSR,
        Opcode.RET,
        Opcode.TABLESWITCH,
        Opcode.LOOKUPSWITCH,
        Opcode.IRETURN,
        Opcode.LRETURN,
        Opcode.FRETURN,
        Opcode.DRETURN,
        Opcode.ARETURN,
        Opcode.RETURN,
        Opcode.GETSTATIC,
        Opcode.PUTSTATIC,
        Opcode.GETFIELD,
        Opcode.PUTFIELD,
        Opcode.INVOKEVIRTUAL,
        Opcode.INVOKESPECIAL,
        Opcode.INVOKESTATIC,
        Opcode.INVOKEINTERFACE,
        Opcode.INVOKEDYNAMIC,
        Opcode.NEW,
        Opcode.NEWARRAY,
        Opcode.ANEWARRAY,
        Opcode.ARRAYLENGTH,
        Opcode.ATHROW,
        Opcode.CHECKCAST,
        Opcode.INSTANCEOF,
        Opcode.MONITORENTER,
        Opcode.MONITOREXIT,
        Opcode.WIDE,
        Opcode.MULTIANEWARRAY,
        Opcode.IFNULL,
        Opcode.IFNONNULL,
        Opcode.GOTO_W,
        Opcode.JSR_W
    };


/*
    enum OpCode {
        ;

        AALOAD = 50;
        AASTORE = 83;
        ACONST_NULL = 1;
        ALOAD = 25;
        ALOAD_0 = 42;
        ALOAD_1 = 43;
        ALOAD_2 = 44;
        ALOAD_3 = 45;
        ANEWARRAY = 189;
        ARETURN = 176;
        ARRAYLENGTH = 190;
        ASTORE = 58;
        ASTORE_0 = 75;
        ASTORE_1 = 76;
        ASTORE_2 = 77;
        ASTORE_3 = 78;
        ATHROW = 191;
        BALOAD = 51;
        BASTORE = 84;
        BIPUSH = 16;
        CALOAD = 52;
        CASTORE = 85;
        CHECKCAST = 192;

        D2F = 144;
        D2I = 142;
        D2L = 143;
        DADD = 99;
        DALOAD = 49;
        DASTORE = 82;
        DCMPG = 152;
        DCMPL = 151;
        DCONST_0 = 14;
        DCONST_1 = 15;
        DDIV = 111;
        DLOAD = 24;
        DLOAD_0 = 38;
        DLOAD_1 = 39;
        DLOAD_2 = 40;
        DLOAD_3 = 41;
        DMUL = 107;
        DNEG = 119;
        DREM = 115;
        DRETURN = 175;
        DSTORE = 57;
        DSTORE_0 = 71;
        DSTORE_1 = 72;
        DSTORE_2 = 73;
        DSTORE_3 = 74;
        DSUB = 103;

        DUP = 89;
        DUP_X1 = 90;
        DUP_X2 = 91;
        DUP2 = 92;
        DUP2_X1 = 93;
        DUP2_X2 = 94;

        F2D = 141;
        F2I = 139;
        F2L = 140;
        FADD = 98;
        FALOAD = 48;
        FASTORE = 81;
        FCMPG = 150;
        FCMPL = 149;
        FCONST_0 = 11;
        FCONST_1 = 12;
        FCONST_2 = 13;
        FDIV = 110;
        FLOAD = 23;
        FLOAD_0 = 34;
        FLOAD_1 = 35;
        FLOAD_2 = 36;
        FLOAD_3 = 37;
        FMUL = 106;
        FNEG = 118;
        FREM = 114;
        FRETURN = 174;
        FSTORE = 56;
        FSTORE_0 = 67;
        FSTORE_1 = 68;
        FSTORE_2 = 69;
        FSTORE_3 = 70;
        FSUB = 102;

        GETFIELD = 180;
        GETSTATIC = 178;
        GOTO = 167;
        GOTO_W = 200;

        I2B = 145;
        I2C = 146;
        I2D = 135;
        I2F = 134;
        I2L = 133;
        I2S = 147;
        IADD = 96;
        IALOAD = 46;
        IAND = 126;
        IASTORE = 79;
        ICONST_M1 = 2;
        ICONST_0 = 3;
        ICONST_1 = 4;
        ICONST_2 = 5;
        ICONST_3 = 6;
        ICONST_4 = 7;
        ICONST_5 = 8;
        IDIV = 108;
        IF_ACMPEQ = 165;
        IF_ACMPNE = 166;
        IF_ICMPEQ = 159;
        IF_ICMPNE = 160;
        IF_ICMPLT = 161;
        IF_ICMPGE = 162;
        IF_ICMPGT = 163;
        IF_ICMPLE = 164;
        IFEQ = 153;
        IFNE = 154;
        IFLT = 155;
        IFGE = 156;
        IFGT = 157;
        IFLE = 158;

        IFNONNULL = 199;
        IFNULL = 198;
        IINC = 132;

        ILOAD = 21;
        ILOAD_0 = 26;
        ILOAD_1 = 27;
        ILOAD_2 = 28;
        ILOAD_3 = 29;
        IMUL = 104;
        INEG = 116;

        INSTANCEOF = 193;
        INVOKEDYNAMIC = 186;
        INVOKEINTERFACE = 185;
        INVOKESPECIAL = 183;
        INVOKESTATIC = 184;
        INVOKEVIRTUAL = 182;

        IOR = 128;
        IREM = 112;
        IRETURN = 172;
        ISHL = 120;
        ISHR = 122;
        ISTORE = 54;
        ISTORE_0 = 59;
        ISTORE_1 = 60;
        ISTORE_2 = 61;
        ISTORE_3 = 62;
        ISUB = 100;
        IUSHR = 124;
        IXOR = 130;

        JSR = 168;
        JSR_W = 201;

        L2D = 138;
        L2F = 137;
        L2I = 136;
        LADD = 97;
        LALOAD = 47;
        LAND = 127;
        LASTORE = 80;
        LCMP = 148;
        LCONST_0 = 9;
        LCONST_1 = 10;
        LDC = 18;
        LDC_W = 19;
        LDC2_W = 20;
        LDIV = 109;
        LLOAD = 22;
        LLOAD_0 = 30;
        LLOAD_1 = 31;
        LLOAD_2 = 32;
        LLOAD_3 = 33;
        LMUL = 105;
        LNEG = 117;
        LOOKUPSWITCH = 171;
        LOR = 129;
        LREM = 113;
        LRETURN = 173;
        LSHL = 121;
        LSHR = 123;
        LSTORE = 55;
        LSTORE_0 = 63;
        LSTORE_1 = 64;
        LSTORE_2 = 65;
        LSTORE_3 = 66;
        LSUB = 101;
        LUSHR = 125;
        LXOR = 131;
        MONITORENTER = 194;
        MONITOREXIT = 195;
        MULTINEWARRAY = 197;
        NEW = 187;
        NEWARRAY = 188;
        NOP = 0;                    // do nothing
        POP = 87;
        POP2 = 88;
        PUTFIELD = 181;
        PUTSTATIC = 179;
        RET = 169;
        RETURN = 177;
        SALOAD = 53;
        SASTORE = 86;
        SIPUSH = 17;
        SWAP = 95;
        TABLESWITCH = 170;
        WIDE = 196;
    }
    */

    /*
        given an opcode by int (with associated args), return a java source code string that might compile to that
     */





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

        StringBuilder a = new StringBuilder("{Code} " + code);

        a.append("\n");

        //for (int c : code) {
        for (int index=0; index<code.size(); index++) {
            int c = code.get(index);
            if (c < OPCODES.length) {
                Opcode opcode = OPCODES[c];
                if (opcode.varargs == 0) {// as long as instruction has no arguments, then
                    a.append(opcode.name()).append("\n");
                } else if (opcode.varargs > 0) {
                    a.append(opcode.name()).append("\n"); // has arguments, which should be printed
                    int at = index;
                    for (; index < at + opcode.varargs; index++)
                        a.append("  ").append(code.get(index)).append("\n");
                } else if (opcode.varargs == -1) // has a variable number of arguments
                    a.append(opcode.name()).append(" (variable length)").append("\n");
            }
        }

        return a.toString();
    }
}
