package decompiler.shared;

import java.util.ArrayList;

public class ReferencedType {

    // Example:
    // Ljava/util/HashMap<Ljava/lang/String;Ljava/util/ArrayList<Ljava/lang/Integer;>;>;

    // what this exact templated object is
    // First iteration:
    // Ljava/util/HashMap
    private ReferencedClass referencedClass;

    // inner types
    // { "Ljava/lang/String", "Ljava/util/ArrayList" }
    private ArrayList<ReferencedType> innerTypes;

    public ReferencedType(String bytecodeName) {
        //super(bytecodeName);
    }



}
