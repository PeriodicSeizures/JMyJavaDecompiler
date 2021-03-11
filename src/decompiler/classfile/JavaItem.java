package decompiler.classfile;

import decompiler.IncrementalBytes;
import decompiler.JavaClassFile;
import decompiler.Result;

import java.io.IOException;

public abstract class JavaItem {

    public static IncrementalBytes bytes;

    public static JavaClassFile currentClassInstance;

    //public static DataInputStream bytes;//

    public abstract Result read() throws IOException;

    boolean verifyValidString(String s) {
        return false;
    }

}
