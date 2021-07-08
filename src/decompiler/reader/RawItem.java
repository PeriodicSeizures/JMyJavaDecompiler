package decompiler.reader;

import decompiler.Result;
import decompiler.reader.pool.RawConstant;

import java.io.IOException;

public abstract class RawItem {

    public static ClassReader bytes;

    public static RawClass currentClassInstance;

    //public static

    //public static DataInputStream bytes;//

    public abstract Result read() throws IOException;

    // can be overridden to return the string of the data as readable source code
    //public String toJavaSourceCode(Object context) {
    //    return this.toString();
    //}


    public static RawConstant getEntry(int i) {
        System.out.println(i);
        //return currentClassInstance.constantPoolContainer.get(i);
        return currentClassInstance.constantPoolContainer.constants.get(
                currentClassInstance.constantPoolContainer.spaced_indexes.get(i-1));
    }

    public Object getValue() {
        return null;
    }

    public String getName() {
        return null;
    }

}
