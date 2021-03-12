package printer;

import decompiler.JavaClassFile;
import decompiler.classfile.JavaItem;
import decompiler.classfile.fields.JavaField;
import decompiler.classfile.methods.JavaMethod;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicClass {

    private String packageName;
    private String name;
    private String flags;
    private HashMap<String, BasicClass> nestedClasses = new HashMap<>();
    private ArrayList<String> fields = new ArrayList<>();
    private ArrayList<BasicMethod> methods = new ArrayList<>();
    private HashMap<String, BasicClass> interfaces = new HashMap<>(); // super interfaces of this class

    /*
        dump data of javaClassFile into this where needed
     */
    public BasicClass(JavaClassFile javaClassFile) {
        flags = javaClassFile.getAccessFlags();

        String class_name = javaClassFile.toJavaSourceCode(javaClassFile.this_class);

        System.out.println(class_name);

        String[] packageAndClass = JavaItem.getSimplePackageAndClass(class_name);

        packageName = packageAndClass[0];
        name = packageAndClass[1];

        for (JavaField javaField : javaClassFile.fieldContainer.fields)
            this.fields.add(javaField.toJavaSourceCode(javaClassFile.this_class));

        for (JavaMethod javaMethod : javaClassFile.methodContainer.methods) {
            BasicMethod basicMethod = new BasicMethod();
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
