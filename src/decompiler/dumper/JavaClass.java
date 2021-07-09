package decompiler.dumper;

import decompiler.reader.JavaUtil;
import decompiler.reader.RClass;
import decompiler.reader.RField;
import decompiler.reader.RMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class JavaClass {

    private RClass myRClass;

    private String packageName;
    private String className;
    private String superClassName;
    private String flags;

    private ArrayList<JavaField> fields = new ArrayList<>();
    private ArrayList<JavaMethod> methods = new ArrayList<>(); // methods should be stored as signatures with names
    private HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private HashSet<String> imports = new HashSet<>();

    // to be referenced by
    //public HashMap<Integer, Integer> constRefFields = new HashMap<>();

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(RClass rClass) {

        this.myRClass = rClass;

        System.out.println(rClass.getName());

        String[] packageAndClass = JavaUtil.getPackageAndClass(rClass.getName());
        if (packageAndClass[0] != null)
            packageName = NameUtil.toValidTypeName(packageAndClass[0].replace('/', '.'));
        else packageName = null;

        className = NameUtil.toValidTypeName(packageAndClass[1]);

        superClassName = NameUtil.toValidTypeName(
                JavaUtil.getUnqualifiedName(rClass.getSuperClassName()));
        flags = rClass.getAccessFlags();

        for (RField rField : rClass.fieldContainer.fields) {
            this.fields.add(new JavaField(rField, imports));
            //this.mappedFields.put(rawClassFile.fnew JavaField(rawField, imports));
        }

        for (RMethod rMethod : rClass.methodContainer.methods) {
            this.methods.add(new JavaMethod(rMethod, this));
        }

        //this.methods = new DuplicateMethodHandler(rawClassFile.methodContainer,
        // imports).getJavaMethods();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getSuperClassName() {
        return superClassName;
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
        s.append(flags).append(" ").append(className);

        // super class
        if (!superClassName.equals("Object"))
            s.append(" extends ").append(superClassName);

        if (!interfaces.isEmpty()){
            JavaClass[] ord = interfaces.values().toArray(new JavaClass[0]);
            for (int i=0; i < ord.length; i++) {
                s.append(" implements ").append(ord[i].className);
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

        s.append("\n");

        for (JavaMethod javaMethod : methods) {
            s.append(javaMethod).append("\n\n");
        }

        s.append("}");

        return s.toString();
    }
}
