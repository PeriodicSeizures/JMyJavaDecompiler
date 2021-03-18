package decompiler.linker;

import decompiler.Util;
import decompiler.reader.attributes.CodeAttribute;
import decompiler.reader.attributes.LocalVariableTableAttribute;

import java.util.ArrayList;

public class LocalVariable {

    private final String type;
    private final String name;
    private String value;

    public LocalVariable(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LocalVariable[] from(CodeAttribute atr) {
        ArrayList<LocalVariableTableAttribute.LocalVariableEntry> _locals = atr.getLocalVariableTable();

        LocalVariable[] localVariables = new LocalVariable[_locals.size()];

        for (LocalVariableTableAttribute.LocalVariableEntry entry : _locals) {

            localVariables[entry.getIndex()] = new LocalVariable(
                    Util.getUnqualifiedName(entry.getDescriptor()), entry.getName(), "");

        }
        return localVariables;
    }
}
