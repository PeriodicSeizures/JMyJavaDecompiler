package decompiler.shared;

public class ReferencedField extends Referenced {

    private ReferencedType type;

    public ReferencedField(String bytecodeName, ReferencedClass ownedClass, String descriptor) {
        super(bytecodeName, ownedClass);
    }

    public ReferencedType getType() {
        return type;
    }
}
