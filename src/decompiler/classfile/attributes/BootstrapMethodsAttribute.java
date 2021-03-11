package decompiler.classfile.attributes;

import decompiler.Result;
import decompiler.classfile.JavaItem;

import java.io.IOException;
import java.util.ArrayList;

public class BootstrapMethodsAttribute extends JavaAttribute {

    private class BootstrapMethod extends JavaItem {

        public int bootstrap_method_ref;
        //public int num_bootstrap_arguments;
        public ArrayList<Integer> bootstrap_arguments = new ArrayList<>();

        @Override
        public Result read() throws IOException {

            bootstrap_method_ref = bytes.readUnsignedShort();
            int num_bootstrap_arguments = bytes.readUnsignedShort();

            for (; num_bootstrap_arguments > 0; num_bootstrap_arguments--) {
                bootstrap_arguments.add(bytes.readUnsignedShort());
            }

            return Result.OK;
        }
    }

    private ArrayList<BootstrapMethod> bootstrap_methods = new ArrayList<>();

    @Override
    public Result read() throws IOException {
        int num_bootstrap_methods = bytes.readUnsignedShort();

        for (;num_bootstrap_methods > 0; num_bootstrap_methods--) {
            BootstrapMethod bootstrapMethod = new BootstrapMethod();

            bootstrapMethod.read();

            bootstrap_methods.add(bootstrapMethod);
        }

        return Result.OK;
    }
}
