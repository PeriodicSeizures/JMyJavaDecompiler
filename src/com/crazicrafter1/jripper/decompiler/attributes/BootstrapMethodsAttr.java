package com.crazicrafter1.jripper.decompiler.attributes;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.IDisassembled;

import java.io.IOException;
import java.util.ArrayList;

public class BootstrapMethodsAttr extends IDisassembled implements IAttr {

    private static class BootstrapMethod extends IDisassembled {

        public int bootstrap_method_ref;
        public ArrayList<Integer> bootstrap_arguments = new ArrayList<>();

        public BootstrapMethod(DisassembledClass belongingClass) {
            super(belongingClass);
        }

        @Override
        public void read(ByteReader bytes) throws IOException {

            bootstrap_method_ref = bytes.readUnsignedShort();
            int num_bootstrap_arguments = bytes.readUnsignedShort();

            for (; num_bootstrap_arguments > 0; num_bootstrap_arguments--) {
                bootstrap_arguments.add(bytes.readUnsignedShort());
            }
        }
    }

    private final ArrayList<BootstrapMethod> bootstrap_methods = new ArrayList<>();

    public BootstrapMethodsAttr(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {
        int num_bootstrap_methods = bytes.readUnsignedShort();

        for (;num_bootstrap_methods > 0; num_bootstrap_methods--) {
            BootstrapMethod bootstrapMethod = new BootstrapMethod(getMainClass());

            bootstrapMethod.read(bytes);

            bootstrap_methods.add(bootstrapMethod);
        }
    }

    @Override
    public String toString() {
        return "{BootstrapMethods}";
    }
}
