package decompiler.classfile;

import decompiler.Result;
import decompiler.classfile.methods.JavaMethod;

import java.io.IOException;
import java.util.ArrayList;

public class MethodContainer extends JavaItem {


    public ArrayList<JavaMethod> methods = new ArrayList<>();

    @Override
    public Result read() throws IOException {

        int methods_count = bytes.readUnsignedShort();

        for (; methods_count > 0; methods_count--) {
            JavaMethod javaMethod = new JavaMethod();

            javaMethod.read();

            methods.add(javaMethod);
        }

        return Result.OK;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        for (JavaMethod javaMethod : methods) {
            s.append(javaMethod.toString()).append("\n");
        }

        return s.toString();
    }
}
