package decompiler.reader;

import java.io.IOException;
import java.util.ArrayList;

public class MethodContainer extends RItem {


    public ArrayList<RMethod> methods = new ArrayList<>();

    @Override
    public void read() throws IOException {

        int methods_count = bytes.readUnsignedShort();

        for (; methods_count > 0; methods_count--) {
            RMethod javaMethod = new RMethod();

            javaMethod.read();

            methods.add(javaMethod);
        }
    }

    public RMethod getMethod(int index) {
        return methods.get(index);
    }

    //public void renameMethod(int index, String newName) {
    //    methods.get(index).
    //}

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("{MethodContainer} ").append("\n");

        for (RMethod javaMethod : methods) {
            s.append(javaMethod.toString()).append("\n");
        }

        return s.toString();
    }

}
