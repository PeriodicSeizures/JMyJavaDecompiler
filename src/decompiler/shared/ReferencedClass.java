package decompiler.shared;

import java.util.HashMap;

public class ReferencedClass extends Referenced {

    private HashMap<String, ReferencedField> fieldsByBytecodeName = new HashMap<>();
    //private HashMap<String, ReferencedField> byBytecodeName = new HashMap<>();

    private HashMap<Integer, ReferencedField> fieldsByIndex = new HashMap<>();

    public ReferencedClass(String bytecodeName, ReferencedClass ownedClass) {
        super(bytecodeName, ownedClass);
    }


}
