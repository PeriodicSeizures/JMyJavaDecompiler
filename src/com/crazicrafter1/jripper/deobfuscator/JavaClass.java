package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.JavaUtil;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.except.InvalidTypeException;
import com.crazicrafter1.jripper.decompiler.DecompiledField;
import com.crazicrafter1.jripper.decompiler.DecompiledMethod;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;
import com.crazicrafter1.jripper.deobfuscator.valid.ValidatedMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class JavaClass {

    private DecompiledClass myDecompiledClass;

    private String packageName;
    private String className;
    private String superClassName;
    private String flags;

    //private HashMap<String, ValidatedMethod> validatedMethods;// = new LinkedHashMap<>();

    private ArrayList<JavaField> fields = new ArrayList<>();
    //private ArrayList<JavaMethod> methods = new ArrayList<>(); // methods should be stored as signatures with names
    private LinkedHashMap<String, JavaMethod> methods = new LinkedHashMap<>();

    private HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private HashSet<String> imports = new HashSet<>();

    // to be referenced by
    //public HashMap<Integer, Integer> constRefFields = new HashMap<>();

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(DecompiledClass decompiledClass) {

        this.myDecompiledClass = decompiledClass;

        System.out.println(decompiledClass.getPackageAndName());

        String[] packageAndClass = JavaUtil.getPackageAndClass(decompiledClass.getPackageAndName());
        if (packageAndClass[0] != null)
            packageName = NameUtil.toValidTypeName(packageAndClass[0].replace('/', '.'));
        else packageName = null;

        className = NameUtil.toValidTypeName(packageAndClass[1]);

        superClassName = NameUtil.toValidTypeName(
                JavaUtil.getUnqualifiedName(decompiledClass.getSuperClassPackageAndName()));
        flags = decompiledClass.getAccessFlags();

        for (DecompiledField decompiledField : decompiledClass.fieldContainer.fields) {
            this.fields.add(new JavaField(decompiledField, imports));
            //this.mappedFields.put(rawClassFile.fnew JavaField(rawField, imports));
        }


        for (DecompiledMethod decompiledMethod : decompiledClass.methodContainer.methods) {
            //validatedMethods.
            var v
            if (methods.get(decompiledMethod.getName())) {

            }
            // test for a collision between name and signatures
            this.methods.add(new JavaMethod(decompiledMethod, this));
        }

        validatedMethods = new LinkedHashMap<>(this.methods);

        for ()
        validatedMethods

        //this.methods = new DuplicateMethodHandler(rawClassFile.methodContainer,
        // imports).getJavaMethods();
    }

    /**
     * Get the JavaMethod associated with the specified constant pool methodRef or interfaceMethodRef
     * @param ref
     * @return
     */
    public JavaMethod getMethodByMethodRef(IPoolConstant ref) {
        if (ref instanceof ConstantMethodref mr) {
            return methods.get(mr.name_and_type_index)
        } else if (ref instanceof ConstantInterfaceMethodref imr) {

        } else
            throw new InvalidTypeException("ref must be some kind of method ref");
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
