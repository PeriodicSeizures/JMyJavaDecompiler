package decompiler.linker;

import decompiler.Util;
import decompiler.reader.RawField;

import java.util.HashSet;

public class JavaField {

    /*
        fields are class members, not *JUST* variables
     */

    public String type;
    public String name;
    public String flags; // aka static final public private volatile etc...

    public JavaField(RawField rawField, HashSet<String> retClassImports) {
        this.flags = rawField.getAccessFlags();
        this.name = Util.toValidName(rawField.getName());
        this.type = Util.getType(rawField.getDescriptor(), retClassImports);
    }

    @Override
    public String toString() {
        return flags + " " + type + " " + name;
    }
}
