package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DecompiledClass;
import com.crazicrafter1.jripper.decompiler.DecompiledField;
import com.crazicrafter1.jripper.decompiler.DecompiledMethod;
import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantInterfaceMethodref;
import com.crazicrafter1.jripper.decompiler.pool.ConstantMethodref;
import com.crazicrafter1.jripper.decompiler.pool.IMethodRef;

import java.util.*;

public class JavaClass extends IObfuscate {

    private final DecompiledClass decompiledClass;

    private String packageName;
    private String className;
    private String superClassName;
    private String flags;

    /**
     * (Decompiled field name, JavaField)
     */
    private final HashMap<String, JavaField> fields = new LinkedHashMap<>();

    /**
     * (Decompiled method name, JavaMethod)
     */
    private final HashMap<String, JavaMethod> methods = new LinkedHashMap<>();

    /**
     * (Decompiled interface name, JavaClass)
     */
    private final HashMap<String, JavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private final HashSet<String> uniqueMethodErasures = new HashSet<>();
    private final HashSet<String> classImports = new HashSet<>();

    public JavaClass(DecompiledClass decompiledClass) {
        super(null);

        this.decompiledClass = decompiledClass;

        classes.put(decompiledClass.getPackageAndName(), this);
    }

    public DecompiledClass getDecompiledClass() {
        return decompiledClass;
    }

    @Override
    public void validationPhase() {
        String[] packageAndClass = Util.getPackageAndClass(decompiledClass.getPackageAndName());
        if (packageAndClass[0] != null)
            packageName = Util.toValidTypeName(packageAndClass[0].replace("/", "."));
        else packageName = null;

        flags = decompiledClass.getAccessFlags();
        className = Util.toValidTypeName(packageAndClass[1]);
        superClassName = Util.toValidTypeName(Util.getUnqualifiedName(decompiledClass.getSuperClassPackageAndName()));

        for (DecompiledField decompiledField : decompiledClass.fieldContainer.getFields()) {
            JavaField javaField = new JavaField(this, decompiledField);
            javaField.validationPhase();
            this.fields.put(decompiledField.getName(), javaField);
        }

        for (DecompiledMethod decompiledMethod : decompiledClass.methodContainer.getMethods()) {
            final String parameterDescriptor = "(" + decompiledMethod.getParameterDescriptor() + ")";
            int ordinal = 0;
            String uniqueErasure = decompiledMethod.getErasureDescriptor();
            String uniqueName = decompiledMethod.getMethodName();
            // Guarantee a unique name
            while (uniqueMethodErasures.contains(uniqueErasure)) {
                // generate a unique name
                uniqueName = "renamed" + (ordinal++);
                uniqueErasure = uniqueName + parameterDescriptor;
            }
            String identifier = decompiledMethod.GUID();

            JavaMethod javaMethod = new JavaMethod(this, decompiledMethod);
            javaMethod.validationPhase();
            javaMethod.setMutatedMethodName(uniqueName);

            methods.put(identifier, javaMethod);
            uniqueMethodErasures.add(uniqueErasure);
        }
    }

    @Override
    public void linkingPhase() {
        for (JavaField javaField : fields.values()) {
            javaField.linkingPhase();
        }
        for (JavaMethod javaMethod : methods.values()) {
            javaMethod.linkingPhase();
        }
    }

    public void addClassImports(Set<String> imports) {
        this.classImports.addAll(imports);
    }

    public JavaField getInternalJavaField(ConstantFieldref ref) {
        return fields.get(ref.getFieldName());
    }

    /**
     * Get the JavaMethod associated with the specified constant pool methodRef or interfaceMethodRef
     * @param ref ConstantMethodref or ConstantInterfaceMethodref
     * @return
     */
    public JavaMethod getInternalJavaMethod(IMethodRef ref) {
        if (ref instanceof ConstantMethodref) {
            return methods.get(((ConstantMethodref) ref).GUID());
        } else if (ref instanceof ConstantInterfaceMethodref) {
            throw new UnsupportedOperationException("Not yet implemented");
        } else
            throw new RuntimeException("Ref must be of ConstantMethodref or ConstantInterfaceMethodref");
    }

    public JavaClass getSuperClass() {
        String superClassPackageAndName = getDecompiledClass()
                .getSuperClassPackageAndName();

        if (superClassPackageAndName.equals("Ljava/lang/Object"))
            return null;

        return getGlobalJavaClass(superClassPackageAndName);
    }

    public boolean hasJavaMethod(JavaMethod javaMethod) {
        return methods.containsValue(javaMethod);
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
            s.append("package ").append(packageName).append(";\n");



        if (!classImports.isEmpty()) {
            s.append("\n");
            for (String imp : classImports) {
                s.append("import ").append(imp).append(";").append("\n");
            }
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
        for (JavaField javaField : fields.values()) {
            s.append(javaField).append("\n");
        }

        s.append("\n");

        for (JavaMethod javaMethod : methods.values()) {
            s.append(javaMethod).append("\n\n");
        }

        s.append("}");

        return s.toString();
    }
}
