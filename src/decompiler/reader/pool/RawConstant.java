package decompiler.reader.pool;

import decompiler.Result;
import decompiler.linker.JavaClass;
import decompiler.reader.RawClassFile;
import decompiler.reader.RawItem;

import java.io.IOException;

public abstract class RawConstant extends RawItem {

    /*
        pool item stuff here
     */

    //protected String name;

    //public abstract Result read() throws IOException;

    //private RawClassFile rawClassFile;

    public Object getValue() {
        return null;
    }

    //protected abstract void read() throws IOException;

    //public RawClassFile getRawClass() {
    //    return rawClassFile;
    //}

    public String getName() {
        return null;
    }

}
