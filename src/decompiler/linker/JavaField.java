package decompiler.linker;

import decompiler.Util;
import decompiler.reader.RawField;

public class JavaField {

    /*
        fields are class members, not *JUST* variables
     */

    public String type;
    public String name;
    public String flags; // aka static final public private volatile etc...

    public JavaField(RawField rawField) {
        this.flags = rawField.getAccessFlags();
        this.name = Util.getValidName(rawField.getName());
        this.type = Util.getReturnType(rawField.getDescriptor());
    }
}
