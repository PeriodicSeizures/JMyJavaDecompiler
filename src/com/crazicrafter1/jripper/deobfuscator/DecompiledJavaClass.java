package com.crazicrafter1.jripper.deobfuscator;

import com.crazicrafter1.jripper.Util;
import com.crazicrafter1.jripper.decompiler.DisassembledClass;
import com.crazicrafter1.jripper.decompiler.DisassembledField;
import com.crazicrafter1.jripper.decompiler.DisassembledMethod;
import com.crazicrafter1.jripper.decompiler.constants.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.constants.IMethodRef;

import java.util.*;

public class DecompiledJavaClass extends IDecompiled {

    private final DisassembledClass disassembledClass;

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
    private final HashMap<String, DecompiledJavaClass> interfaces = new HashMap<>(); // super interfaces of this class

    private final HashSet<String> uniqueMethodErasures = new HashSet<>();
    private final HashSet<String> classImports = new HashSet<>();

    public DecompiledJavaClass(DisassembledClass disassembledClass) {
        super(null);

        this.disassembledClass = disassembledClass;

        classes.put(disassembledClass.getPackageAndName(), this);
    }

    public DisassembledClass getDecompiledClass() {
        return disassembledClass;
    }

    @Override
    public void validationPhase() {
        String[] packageAndClass = Util.getPackageAndClass(disassembledClass.getPackageAndName());
        if (packageAndClass[0] != null)
            packageName = Util.toValidTypeName(packageAndClass[0].replace("/", "."));
        else packageName = null;

        flags = disassembledClass.getAccessFlags();
        className = Util.toValidTypeName(packageAndClass[1]);
        superClassName = Util.toValidTypeName(Util.getUnqualifiedName(disassembledClass.getSuperBinaryName()));

        for (DisassembledField disassembledField : disassembledClass.fieldContainer.getFields()) {
            JavaField javaField = new JavaField(this, disassembledField);
            javaField.validationPhase();
            this.fields.put(disassembledField.getName(), javaField);
        }

        for (DisassembledMethod disassembledMethod : disassembledClass.methodContainer.getMethods()) {
            final String parameterDescriptor = "(" + disassembledMethod.getParameterDescriptor() + ")";
            int ordinal = 0;
            String uniqueErasure = disassembledMethod.getErasureDescriptor();
            String uniqueName = disassembledMethod.getMethodName();
            // Guarantee a unique name
            while (uniqueMethodErasures.contains(uniqueErasure)) {
                // generate a unique name
                uniqueName = "renamed" + (ordinal++);
                uniqueErasure = uniqueName + parameterDescriptor;
            }
            String identifier = disassembledMethod.GUID();

            JavaMethod javaMethod = new JavaMethod(this, disassembledMethod);
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

    public JavaField getInternalField(ConstantFieldref ref) {
        return fields.get(ref.getFieldName());
    }

    /**
     * Get the JavaMethod associated with the specified constant pool methodRef or interfaceMethodRef
     * @param ref ConstantMethodref or ConstantInterfaceMethodref
     * @return
     */
    public JavaMethod getInternalMethod(IMethodRef ref) {
        return methods.get(ref.GUID());
    }

    public DecompiledJavaClass getSuperClass() {
        String superClassPackageAndName = getDecompiledClass()
                .getSuperBinaryName();

        return getGlobalClass(superClassPackageAndName);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        ArrayList<String> lines = new ArrayList<>();

        if (packageName != null)
            lines.add("package " + packageName + ";");



        if (!classImports.isEmpty()) {
            lines.add("");
            for (String imp : classImports) {
                lines.add("import " + imp + ";");
            }
        }

        lines.add("");

        StringBuilder builder = new StringBuilder();
        builder.append(flags).append(" ").append(className);

        // super class
        if (!superClassName.equals("Object"))
            builder.append(" extends ").append(superClassName);

        if (!interfaces.isEmpty()){
            DecompiledJavaClass[] ord = interfaces.values().toArray(new DecompiledJavaClass[0]);
            for (int i=0; i < ord.length; i++) {
                builder.append(" implements ").append(ord[i].className);
                if (i != ord.length - 1) {
                    builder.append(", ");
                }
            }
        }

        builder.append(" {");

        lines.add(builder.toString());

        // fields
        for (JavaField javaField : fields.values()) {
            lines.add(javaField.toString());
        }

        lines.add("");

        for (JavaMethod javaMethod : methods.values()) {
            lines.add(javaMethod.toString());
            lines.add("");
        }

        lines.add("}");

        return Util.combine(lines);
    }
}
