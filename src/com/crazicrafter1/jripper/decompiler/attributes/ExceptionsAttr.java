package com.crazicrafter1.jripper.decompiler.attributes;

import com.crazicrafter1.jripper.decompiler.ByteReader;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.IAttr;

import java.io.IOException;
import java.util.ArrayList;

public class ExceptionsAttr extends IAttr {

    private ArrayList<Integer> exception_index_table = new ArrayList<>();

    public ExceptionsAttr(DecompiledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {

        int number_of_exceptions = bytes.readUnsignedShort();

        for (; number_of_exceptions > 0; number_of_exceptions--) {
            exception_index_table.add(bytes.readUnsignedShort());
        }
    }

    @Override
    public String toString() {
        return "{Exceptions} ";
    }
}
