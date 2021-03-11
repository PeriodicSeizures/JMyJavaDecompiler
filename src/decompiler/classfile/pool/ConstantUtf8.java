package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantUtf8 extends JavaPoolEntry {

    public String s;

    @Override
    public Result read() throws IOException {

        s = bytes.readUTF(); // works as intended

        System.out.println(s);

        return Result.OK;
    }

    @Override
    public String toString() {
        return s;
    }
}
