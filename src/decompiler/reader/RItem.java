package decompiler.reader;

import decompiler.reader.pool.RawConstant;

import java.io.IOException;

public abstract class RItem {

    public static ClassReader bytes;

    public static RClass currentClassInstance;

    public abstract void read() throws IOException;

    public static RawConstant getEntry(int i) {
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
