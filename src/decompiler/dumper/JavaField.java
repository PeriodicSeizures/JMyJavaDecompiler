package decompiler.dumper;

import decompiler.reader.JavaUtil;
import decompiler.reader.RField;

import java.util.HashSet;

public class JavaField {

    /*
        fields are class members, not *JUST* variables
     */

    public String type;
    public String name;
    public String flags; // aka static final public private volatile etc...

    public JavaField(RField rField, HashSet<String> retClassImports) {
        this.flags = rField.getAccessFlags();
        this.name = NameUtil.toValidName(rField.getName());
        //if (rawField.getSignature() != null) {
        //    this.type = Util.getType(rawField.getSignature(), retClassImports);
        //} else {
            this.type = NameUtil.toValidName(
                    JavaUtil.getType(rField.getDescriptor(), retClassImports));
        //}
    }

    @Override
    public String toString() {
        return flags + " " + type + " " + name;
    }
}
