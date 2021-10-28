package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.except.InvalidTypeException;
import com.crazicrafter1.jripper.decompiler.DecompiledField;
import com.crazicrafter1.jripper.decompiler.DecompiledMethod;
import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;
import com.crazicrafter1.jripper.decompiler.IPoolConstant;

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

    //private ArrayList<JavaField> fields = new ArrayList<>();
    private HashMap<String, JavaField> fields = new LinkedHashMap<>();
    //private ArrayList<JavaMethod> methods = new ArrayList<>(); // methods should be stored as signatures with names
    private LinkedHashMap<String, JavaMethod> methods = new LinkedHashMap<>();

    private HashSet<String> uniqueMethodNames = new HashSet<>();

    private HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private HashSet<String> imports = new HashSet<>();

    // to be referenced by
    //public HashMap<Integer, Integer> constRefFields = new HashMap<>();

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(DecompiledClass decompiledClass) {

        this.myDecompiledClass = decompiledClass;

        String[] packageAndClass = Util.getPackageAndClass(decompiledClass.getPackageAndName());
        if (packageAndClass[0] != null)
            packageName = NameUtil.toValidTypeName(packageAndClass[0].replace('/', '.'));
        else packageName = null;

        className = NameUtil.toValidTypeName(packageAndClass[1]);

        superClassName = NameUtil.toValidTypeName(
                Util.getUnqualifiedName(decompiledClass.getSuperClassPackageAndName()));
        flags = decompiledClass.getAccessFlags();

        for (DecompiledField decompiledField : decompiledClass.fieldContainer.getFields()) {
            this.fields.put(decompiledField.getName(), new JavaField(decompiledField, imports));
            //this.mappedFields.put(rawClassFile.fnew JavaField(rawField, imports));
        }

        for (DecompiledMethod decompiledMethod : decompiledClass.methodContainer.getMethods()) {
            // If a duplicate method already exists, then rename
            String erasureDescriptor = decompiledMethod.getErasureDescriptor();
            int rename = 0;
            String newName = decompiledMethod.getMethodName();
            while (uniqueMethodNames.contains(erasureDescriptor)) {
                // generate a unique name
                newName = "renamed_" + (rename++);
            }
            String identifier = decompiledMethod.UID();
            JavaMethod javaMethod = new JavaMethod(decompiledMethod, this);
            javaMethod.setMutatedMethodName(newName);
            methods.put(identifier, javaMethod);
            uniqueMethodNames.add(newName);
        }
    }

    /**
     * Get the JavaMethod associated with the specified constant pool methodRef or interfaceMethodRef
     * @param ref
     * @return
     */
    public JavaMethod getMethodByMethodRef(IPoolConstant ref) {
        if (ref instanceof ConstantMethodref mr) {
            return methods.get(mr.UID());
        } else if (ref instanceof ConstantInterfaceMethodref imr) {
            throw new UnsupportedOperationException("Not yet implemented");
            //return methods.get(imr.getDescriptor());
        }// else
            throw new InvalidTypeException("ref must be some kind of method ref");
    }

    public JavaField getMappedField(int constant_pool_index) {
        String originalName = ((ConstantFieldref)myDecompiledClass.getEntry(constant_pool_index)).getFieldName();

        return fields.get(originalName);
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
        for (var entry : fields.entrySet()) {
            s.append(entry.getKey()).append(";\n");
        }

        s.append("\n");

        //for (JavaMethod javaMethod : methods) {
        //    s.append(javaMethod).append("\n\n");
        //}

        s.append("}");

        return s.toString();
    }
}
