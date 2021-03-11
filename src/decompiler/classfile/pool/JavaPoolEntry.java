package decompiler.classfile.pool;

import decompiler.Result;
import decompiler.classfile.JavaItem;

import java.io.IOException;

public abstract class JavaPoolEntry extends JavaItem {

    /*
        pool item stuff here
     */

    public abstract Result read() throws IOException;

    //protected abstract void read() throws IOException;

}
