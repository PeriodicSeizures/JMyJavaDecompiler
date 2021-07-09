package decompiler.shared;

import java.util.ArrayList;

public class ReferencedMethod extends Referenced {

    private final String bytecodeDescriptor;
    private ArrayList<ReferencedType> referencedSignature;

    public ReferencedMethod(String bytecodeName, ReferencedClass ownedClass, String bytecodeDescriptor) {
        super(bytecodeName, ownedClass);
        this.bytecodeDescriptor = bytecodeDescriptor;
    }

    public String getBytecodeDescriptor() {
        return bytecodeDescriptor;
    }

    public ArrayList<ReferencedType> getReferencedSignature() {
        return referencedSignature;
    }

//public String get
}
