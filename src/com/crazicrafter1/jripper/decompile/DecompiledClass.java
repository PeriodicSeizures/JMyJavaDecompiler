package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.except.NoUsageException;
import com.crazicrafter1.jripper.types.IBaseClass;
import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.types.IFieldDefined;
import com.crazicrafter1.jripper.types.IMethodDefined;
import com.crazicrafter1.jripper.util.Util;
import com.crazicrafter1.jripper.disassemble.DisassembledClass;
import com.crazicrafter1.jripper.disassemble.DisassembledField;
import com.crazicrafter1.jripper.disassemble.DisassembledMethod;

import java.util.*;

public final class DecompiledClass
        extends AbstractDecompiled
        implements IClassDefined {

    //public synchronized static final strictfp float f() {
    //    return 0.f;
    //}

    private String classSimpleName;
    private String packageName;

    /**
     * All fields of this class
     * (binaryFieldName, decompiled)
     */
    private final HashMap<UUID, DecompiledField> fields = new LinkedHashMap<>();

    /**
     * All methods of this class
     * (methodUUID, decompiled)
     */
    private final HashMap<UUID, DecompiledMethod> methods = new LinkedHashMap<>();

    /**
     * All implemented interfaces
     * (binaryClassName, decompiled)
     */
    private final HashMap<String, DecompiledClass> interfaces = new HashMap<>();

    private final HashSet<String> uniqueMethodErasures = new HashSet<>();
    private final HashSet<String> classImports = new HashSet<>();

    public DecompiledClass(DisassembledClass disassembledClass) {
        super(null, disassembledClass);

        classes.put(disassembledClass.getName(), this);
    }

    @Override
    public boolean isPublic() {
        return getDisassemblyUnit().isPublic();
    }

    @Override
    public boolean isFinal() {
        return getDisassemblyUnit().isFinal();
    }

    @Override
    public boolean isInterface() {
        return getDisassemblyUnit().isInterface();
    }

    @Override
    public boolean isAbstractClass() {
        return getDisassemblyUnit().isAbstractClass();
    }

    @Override
    public boolean isAnnotation() {
        return getDisassemblyUnit().isAnnotation();
    }

    @Override
    public boolean isEnum() {
        return getDisassemblyUnit().isEnum();
    }

    @Override
    public boolean isSuper() {
        return getDisassemblyUnit().isSuper();
    }

    @Override
    public ArrayList<String> getInterfaces() {
        return getDisassemblyUnit().getInterfaces();
    }

    @Override
    public IFieldDefined getIField(UUID uuid) {
        return fields.get(uuid);
    }

    @Override
    public IMethodDefined getIMethod(UUID uuid) {
        return methods.get(uuid);
    }

    @Override
    public DisassembledClass getDisassemblyUnit() {
        return (DisassembledClass) super.getDisassemblyUnit();
    }

    @Override
    public void validationPhase() {
        DisassembledClass unit = getDisassemblyUnit();

        String[] packageAndClass = Util.getBinaryPackageAndClass(
                unit.getName());
        if (packageAndClass[0] != null)
            packageName = Util.toValidTypeName(packageAndClass[0].replace("/", "."));
        else packageName = null;

        classSimpleName = Util.toValidName(packageAndClass[1]);
        //superClassName = Util.toValidName(Util.getBinarySimpleName(unit.getSuperBinaryName()));

        for (DisassembledField disassembledField : unit.fieldContainer.getFields().values()) {
            DecompiledField javaField = new DecompiledField(this, disassembledField);
            javaField.validationPhase();
            this.fields.put(disassembledField.getUUID(), javaField);
        }

        for (DisassembledMethod disassembledMethod : unit.methodContainer.getMethods().values()) {
            final String parameterDescriptor = "(" + disassembledMethod.getParameterDescriptor() + ")";
            int ordinal = 0;
            String uniqueErasure = disassembledMethod.getErasureDescriptor();
            String uniqueName = disassembledMethod.getName();
            // Guarantee a unique name
            while (uniqueMethodErasures.contains(uniqueErasure)) {
                // generate a unique name
                uniqueName = "renamed" + (ordinal++);
                uniqueErasure = uniqueName + parameterDescriptor;
            }
            UUID methodUUID = disassembledMethod.getUUID();

            DecompiledMethod decompiledMethod = new DecompiledMethod(this, disassembledMethod);
            decompiledMethod.validationPhase();
            decompiledMethod.setMethodName(uniqueName);

            methods.put(methodUUID, decompiledMethod);
            uniqueMethodErasures.add(uniqueErasure);
        }
    }

    @Override
    public void linkingPhase() {
        for (DecompiledField decompiledField : fields.values()) {
            decompiledField.linkingPhase();
        }
        for (DecompiledMethod decompiledMethod : methods.values()) {
            decompiledMethod.linkingPhase();
        }
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getSimpleName() {
        return classSimpleName;
    }

    @Override
    public String getName() {
        if (packageName != null)
            return packageName + "." + classSimpleName;
        return classSimpleName;
    }

    @Override
    public String getSuperName() {
        return getDisassemblyUnit().getSuperName();
    }

    public IBaseClass getSuperClass() {
        String superClassBinaryName = getDisassemblyUnit()
                .getSuperName();

        return getIClass(superClassBinaryName);
    }

    boolean derivesFrom(IClassDefined parent) {
        IBaseClass selfClass = this;

        while (!selfClass.equals(parent)) {
            // - IBaseClass has no way of getting superclass
            // because it is not meant to retrieve a super
            // - This behaviour is ordinary from a JVM standpoint,
            // but for a decompiler, it means
            // that needed information is explicitly missing

            // If super instanceof DecompiledClass, easy get
            if (selfClass instanceof DecompiledClass) {
                selfClass = ((DecompiledClass) selfClass).getSuperClass();
            } else {
                throw new NoUsageException("Super class is external; unable to resolve");
                //return false;
            }

            if (selfClass.equals(OBJECT_CLASS)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Set<String> getImportSet() {
        return this.classImports;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IBaseClass))
            return false;

        if (obj == this)
            return true;

        return this.getName().equals(
                ((IBaseClass) obj).getName());
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
        builder.append(getDisassemblyUnit().getFormattedFlags()).append(" ").append(classSimpleName);

        // super class
        IBaseClass superClazz = getSuperClass();
        if (!superClazz.equals(OBJECT_CLASS))
            builder.append(" extends ").append(superClazz.getSimpleName());

        if (!interfaces.isEmpty()){
            DecompiledClass[] ord = interfaces.values().toArray(new DecompiledClass[0]);
            for (int i=0; i < ord.length; i++) {
                builder.append(" implements ").append(ord[i].classSimpleName);
                if (i != ord.length - 1) {
                    builder.append(", ");
                }
            }
        }

        builder.append(" {");

        lines.add(builder.toString());

        // fields
        for (DecompiledField decompiledField : fields.values()) {
            lines.add(decompiledField.toString());
        }

        lines.add("");

        for (DecompiledMethod decompiledMethod : methods.values()) {
            lines.add(decompiledMethod.toString());
            lines.add("");
        }

        lines.add("}");

        return Util.combine(lines);
    }
}
