package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.types.*;
import com.crazicrafter1.jripper.disassemble.constants.IMethodRef;
import com.crazicrafter1.jripper.disassemble.IDisassembled;
import com.crazicrafter1.jripper.disassemble.constants.*;
import com.crazicrafter1.jripper.except.NoUsageException;
import com.sun.istack.internal.NotNull;

import java.util.Set;

public abstract class AbstractDecompiled
        extends ClassNamespace {

    //static HashMap<String, DecompiledClass> classes = new HashMap<>();

    private final DecompiledClass containedClass;
    private final IDisassembled disassemblyUnit;

    AbstractDecompiled(DecompiledClass containedClass, IDisassembled disassemblyUnit) {
        this.containedClass = containedClass;
        this.disassemblyUnit = disassemblyUnit;
    }

    //public abstract String getCustomName();

    public final DecompiledClass getHolder() {
        if (containedClass == null)
            throw new NoUsageException("Should not use getHolder() with Class");
        return containedClass;
    }

    public IDisassembled getDisassemblyUnit() {
        return disassemblyUnit;
    }
/*
    @Override
    public final IMethod getIMethod(IMethodRef ref) {
        String binaryClassName = ref.getBinaryClassName();

        IClass iClass;
        IMethod iMethod;

        // Search upwards to parent class
        do {
            iClass = getIClass(binaryClassName);

            // If at Object class
            if (iClass instanceof ForeignClass)
                return new ForeignMethod(ref);

            binaryClassName = iClass.getSuperBinaryName();

        } while ((iMethod = iClass.getIMethod(ref.getUUID())) == null);

        return iMethod;
    }

    @Override
    public final IField getIField(ConstantFieldRef ref) {
        String binaryClassName = ref.getBinaryClassName();

        IField iField;
        IClass iClass;
        // Search upwards to parent class
        do {
            iClass = getIClass(binaryClassName);

            // If at Object class
            if (iClass instanceof ForeignClass)
                return new ForeignField(ref);

            binaryClassName = iClass.getSuperBinaryName();

        } while ((iField = iClass.getIField(ref.getBinaryFieldName())) == null);

        return iField;
    }

 */

    @NotNull
    public IBaseClass getIClass(int constantPoolMethodRefIndex) {
        ConstantClass ref = (ConstantClass) getHolder().getDisassemblyUnit().getConstant(constantPoolMethodRefIndex);

        DecompiledClass decompiledClass = classes.get(ref.get());
        if (decompiledClass != null)
            return decompiledClass;
        return new ForeignClass(ref.get());
    }

    public final IBaseField getIField(int constantPoolFieldRefIndex) {
        ConstantFieldRef ref = (ConstantFieldRef) getHolder().getDisassemblyUnit().getConstant(constantPoolFieldRefIndex);
        return getIField(ref);
    }

    protected final IBaseMethod getIMethod(int constantPoolMethodRefIndex) {
        IMethodRef methodRef = (IMethodRef) getHolder().getDisassemblyUnit().getConstant(constantPoolMethodRefIndex);
        return getIMethod(methodRef);
    }

    protected Set<String> getImportSet() {
        return getHolder().getImportSet();
    }

    /**
     * Decompile steps
     */
    public abstract void validationPhase();

    public abstract void linkingPhase();
}
