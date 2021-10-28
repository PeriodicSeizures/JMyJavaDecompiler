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

import java.util.*;

public class JavaClass extends IDeobfuscated {

    private final DecompiledClass decompiledClass;

    private String packageName;
    private String className;
    private String superClassName;
    private String flags;

    //private HashMap<String, ValidatedMethod> validatedMethods;// = new LinkedHashMap<>();

    //private ArrayList<JavaField> fields = new ArrayList<>();
    private final HashMap<String, JavaField> fields = new LinkedHashMap<>();
    //private ArrayList<JavaMethod> methods = new ArrayList<>(); // methods should be stored as signatures with names
    private final HashMap<String, JavaMethod> methods = new LinkedHashMap<>();

    private final HashSet<String> uniqueMethodNames = new HashSet<>();

    private final HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private final HashSet<String> classImports = new HashSet<>();

    // to be referenced by
    //public HashMap<Integer, Integer> constRefFields = new HashMap<>();

    /*
        dump data of javaClassFile into this where needed
     */
    public JavaClass(JavaJar parentJar, DecompiledClass decompiledClass) {
        super(parentJar);

        this.decompiledClass = decompiledClass;
    }

    @Override
    public void process() {
        String[] packageAndClass = Util.getPackageAndClass(decompiledClass.getPackageAndName());
        if (packageAndClass[0] != null)
            packageName = Util.toValidTypeName(packageAndClass[0].replace('/', '.'));
        else packageName = null;

        className = Util.toValidTypeName(packageAndClass[1]);

        superClassName = Util.toValidTypeName(
                Util.getUnqualifiedName(decompiledClass.getSuperClassPackageAndName()));
        flags = decompiledClass.getAccessFlags();

        for (DecompiledField decompiledField : decompiledClass.fieldContainer.getFields()) {
            JavaField javaField = new JavaField(this, decompiledField);
            //javaField.process();
            this.fields.put(decompiledField.getName(), javaField);
        }

        for (DecompiledMethod decompiledMethod : decompiledClass.methodContainer.getMethods()) {
            // If a duplicate method already exists, then rename
            int ordinal = 0;
            String newName = decompiledMethod.getErasureDescriptor();
            while (uniqueMethodNames.contains(newName)) {
                // generate a unique name
                newName = "renamed_" + (ordinal++);
            }
            String identifier = decompiledMethod.UID();
            JavaMethod javaMethod = new JavaMethod(this, decompiledMethod);
            javaMethod.setMutatedMethodName(newName);

            methods.put(identifier, javaMethod);
            uniqueMethodNames.add(newName);
        }
    }

    public void addClassImports(Set<String> imports) {
        this.classImports.addAll(imports);
    }

    /**
     * Get the JavaMethod associated with the specified constant pool methodRef or interfaceMethodRef
     * @param ref
     * @return
     */
    public JavaMethod getMethodByMethodRef(IPoolConstant ref) {
        if (ref instanceof ConstantMethodref) {
            return methods.get(((ConstantMethodref) ref).UID());
        } else if (ref instanceof ConstantInterfaceMethodref) {
            throw new UnsupportedOperationException("Not yet implemented");
            //return methods.get(imr.getDescriptor());
        }// else
            throw new InvalidTypeException("ref must be some kind of method ref");
    }

    public JavaField getMappedField(int constant_pool_index) {
        String originalName = ((ConstantFieldref)decompiledClass.getEntry(constant_pool_index)).getFieldName();

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



        for (String imp : classImports) {
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
        for (Map.Entry<String, JavaField> entry : fields.entrySet()) {
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
