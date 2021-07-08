package decompiler.shared;

public class Referenced {

    private final String bytecodeName;
    private String newName;

    private final ReferencedClass ownedClass;

    public Referenced(String bytecodeName, ReferencedClass ownedClass) {
        this.bytecodeName = bytecodeName;
        this.ownedClass = ownedClass;
    }

    public String getBytecodeName() {
        return bytecodeName;
    }

    public String getNewName() {
        return newName;
    }

    void setNewName(String newName) {
        this.newName = newName;
    }

    public ReferencedClass getOwnedClass() {
        return ownedClass;
    }
}
