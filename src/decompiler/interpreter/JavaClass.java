package decompiler.interpreter;

import decompiler.Util;
import decompiler.reader.RawClass;
import decompiler.reader.RawField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class JavaClass {

    private String packageName;
    private String name;
    private String superClassName;
    private String flags;
    //private HashMap<String, JavaClass> nestedClasses = new HashMap<>();
    //private HashMap<String, JavaField> mappedFields = new HashMap<>();
    //private HashMap<Integer, JavaField> mappedFields = new HashMap<>();         // ordered by constant pool
    //private HashMap<Integer, JavaMethod> mappedMethods = new HashMap<>();       // same ^
    private ArrayList<JavaField> fields = new ArrayList<>();
    private ArrayList<JavaMethod> methods; // methods should be stored as signatures with names
    private HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private HashSet<String> imports = new HashSet<>();

    // to be referenced by
    //public HashMap<Integer, Integer> constRefFields = new HashMap<>();

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(RawClass rawClassFile) {
        flags = rawClassFile.getAccessFlags();

        String[] packageAndClass = Util.getPackageAndClass(rawClassFile.getName());

        packageName = packageAndClass[0];
        name = Util.toValidName(packageAndClass[1]);

        superClassName = Util.toValidTypeName(Util.getUnqualifiedName(rawClassFile.getSuperClassName()));

        for (RawField rawField : rawClassFile.fieldContainer.fields) {
            this.fields.add(new JavaField(rawField, imports));
            //this.mappedFields.put(rawClassFile.fnew JavaField(rawField, imports));
        }

        this.methods = new DuplicateMethodHandler(rawClassFile.methodContainer, imports).getJavaMethods();


        //System.out.println(flags + name);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();



        if (packageName != null)
            s.append("package ").append(packageName).append(";\n\n");



        for (String imp : imports) {
            s.append("import ").append(imp).append(";").append("\n");
        }

        s.append("\n");

        // public class TestClass
        s.append(flags).append(" ").append(name);

        // super class
        if (!superClassName.equals("Object"))
            s.append(" extends ").append(superClassName);

        if (!interfaces.isEmpty()){
            JavaClass[] ord = interfaces.values().toArray(new JavaClass[0]);
            for (int i=0; i < ord.length; i++) {
                s.append(" implements ").append(ord[i].name);
                if (i < ord.length - 1) {
                    s.append(", ");
                }
            }
        }

        s.append(" {\n");

        // fields
        for (JavaField javaField : fields) {
            s.append(javaField).append(";\n");
        }

        for (JavaMethod javaMethod : methods) {
            s.append(javaMethod).append("\n");
        }

        s.append("}");

        return s.toString();
    }
}
