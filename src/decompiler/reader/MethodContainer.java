package decompiler.reader;

import decompiler.Result;

import java.io.IOException;
import java.util.ArrayList;

public class MethodContainer extends RawItem {


    public ArrayList<RawMethod> methods = new ArrayList<>();

    @Override
    public Result read() throws IOException {

        int methods_count = bytes.readUnsignedShort();

        for (; methods_count > 0; methods_count--) {
            RawMethod javaMethod = new RawMethod();

            javaMethod.read();

            methods.add(javaMethod);
        }

        return Result.OK;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("{MethodContainer} ").append("\n");

        for (RawMethod javaMethod : methods) {
            s.append(javaMethod.toString()).append("\n");
        }

        return s.toString();
    }
}
