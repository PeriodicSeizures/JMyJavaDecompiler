package decompiler.linker;

import decompiler.Util;
import decompiler.reader.RawClassFile;
import decompiler.reader.RawField;
import decompiler.reader.RawMethod;

import java.util.ArrayList;
import java.util.HashMap;

public class JavaClass {

    private String packageName;
    private String name;
    private String flags;
    private HashMap<String, JavaClass> nestedClasses = new HashMap<>();
    private HashMap<String, JavaField> mappedFields = new HashMap<>();
    private ArrayList<String> fields = new ArrayList<>();
    private ArrayList<JavaMethod> methods = new ArrayList<>(); // methods should be stored as signatures with names
    private HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(RawClassFile rawClassFile) {
        flags = rawClassFile.getAccessFlags();

        String[] packageAndClass = Util.getPackageAndClass(rawClassFile.getName());

        packageName = packageAndClass[0];
        name = packageAndClass[1];

        for (RawField rawField : rawClassFile.fieldContainer.fields) {
            String name = Util.getUnqualifiedName(rawField.getName());
            //this.fields.add(//rawField.toJavaSourceCode(rawClassFile.this_class));
            mappedFields.put(name, new JavaField());
        }

        for (RawMethod javaMethod : rawClassFile.methodContainer.methods) {
            JavaMethod basicMethod = new JavaMethod();
            if (javaMethod.getName().equals("<init>")) {
                basicMethod.name =  name;
            } else {
                basicMethod.name = javaMethod.getName();
            }
            //basicMethod.
            //this.methods.add(javaMethod.)
        }

        //System.out.println(flags + name);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (packageName != null)
            s.append("package ").append(packageName).append(";\n\n");

        // public class TestClass
        s.append(flags).append(name).append(" {\n");

        // fields
        for (String f : fields) {
            s.append("    ").append(f).append(";\n");
        }

        s.append("}");

        return s.toString();
    }
}
