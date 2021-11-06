package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.disassemble.constants.IConstant;
import com.crazicrafter1.jripper.except.NoUsageException;
import com.crazicrafter1.jripper.util.ByteReader;

import java.io.IOException;
import java.io.InputStream;

public abstract class IDisassembled {

    private final DisassembledClass mainClass;

    protected IDisassembled(DisassembledClass mainClass) {
        this.mainClass = mainClass;
    }

    protected abstract void read(ByteReader bytes) throws IOException;

    public final DisassembledClass getMain() {
        if (mainClass == null)
            throw new NoUsageException("Redundant call, bad design");
          //  return (DisassembledClass) this;
        return mainClass;
    }

    public IConstant getConstant(int i) {
        if (mainClass != null)
            return mainClass.constantPoolContainer.getConstant(i);
        return ((DisassembledClass)this).constantPoolContainer.getConstant(i);
    }

    public static DisassembledClass disassemble(InputStream is) throws IOException {
        ByteReader bytes = new ByteReader(is);

        DisassembledClass decompiledClass = new DisassembledClass();

        decompiledClass.read(bytes);
        bytes.close();

        System.out.println("Disassembled " + decompiledClass.getName());

        return decompiledClass;
    }
}
