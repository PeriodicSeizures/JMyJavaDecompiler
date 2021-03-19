package decompiler.linker;

import decompiler.Util;
import decompiler.reader.RawClassFile;
import decompiler.reader.RawField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class JavaClass {

    private String packageName;
    private String name;
    private String superClassName;
    private String flags;
    private HashMap<String, JavaClass> nestedClasses = new HashMap<>();
    private HashMap<String, JavaField> mappedFields = new HashMap<>();
    private ArrayList<JavaField> fields = new ArrayList<>();
    private ArrayList<JavaMethod> methods; // methods should be stored as signatures with names
    private HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private HashSet<String> imports = new HashSet<>();

    // to be referenced by
    //public HashMap<Integer, Integer> constRefFields = new HashMap<>();

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(RawClassFile rawClassFile) {
        flags = rawClassFile.getAccessFlags();

        String[] packageAndClass = Util.getPackageAndClass(rawClassFile.getName());

        packageName = packageAndClass[0];
        name = packageAndClass[1];

        superClassName = Util.getUnqualifiedName(rawClassFile.getSuperClassName());

        for (RawField rawField : rawClassFile.fieldContainer.fields) {
            //String name = Util.getUnqualifiedName(rawField.getName());
            this.fields.add(new JavaField(rawField, imports));
            //mappedFields.put(name, new JavaField());
        }

        this.methods = new DuplicateMethodHandler(rawClassFile.methodContainer).getJavaMethods(imports);


        //System.out.println(flags + name);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (String imp : imports) {
            s.append("import ").append(imp).append(";").append("\n");
        }

        if (packageName != null)
            s.append("package ").append(packageName).append(";\n\n");

        // public class TestClass
        s.append(flags).append(name);

        // super class
        if (!superClassName.equals("Object"))
            s.append(" extends ").append(superClassName);

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
