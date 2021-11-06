package com.crazicrafter1.jripper;

import com.crazicrafter1.jripper.decompile.DecompiledMethod;
import com.crazicrafter1.jripper.decompile.ForeignMethod;
import com.crazicrafter1.jripper.disassemble.DisassembledMethod;
import com.crazicrafter1.jripper.disassemble.IDisassembled;
import com.crazicrafter1.jripper.except.NoUsageException;
import com.crazicrafter1.jripper.types.IBaseMethod;
import com.crazicrafter1.jripper.types.IMethodDefined;
import com.sun.istack.internal.NotNull;

public enum EnumMethod {
    INSTANCE_INITIALIZER(true, true),
    INSTANCE(false, true),
    STATIC_INITIALIZER(true, false),
    STATIC(false, false);

    private final boolean isInitializer;
    private final boolean isInstance;

    EnumMethod(boolean initializer, boolean instance) {
        this.isInitializer = initializer;
        this.isInstance = instance;
    }

    public boolean isInitializer() {
        return isInitializer;
    }

    public boolean isInstance() {
        return isInstance;
    }

    @Deprecated
    public static EnumMethod from(@NotNull String methodBinaryName) {
        switch (methodBinaryName) {
            case "<init>":
                return EnumMethod.INSTANCE_INITIALIZER;
            case "<clinit>":
                return EnumMethod.STATIC_INITIALIZER;
            default:
                return null;
        }
    }

    public static EnumMethod from(IBaseMethod method) {
        if (method instanceof DecompiledMethod) {
            DecompiledMethod decompiledMethod = (DecompiledMethod) method;
            switch (decompiledMethod.getDisassemblyUnit().getName()) {
                case "<init>":
                    return EnumMethod.INSTANCE_INITIALIZER;
                case "<clinit>":
                    return EnumMethod.STATIC_INITIALIZER;
                default:
                    if (decompiledMethod.isStatic())
                        return STATIC;
                    return INSTANCE;
            }
        } else if (method instanceof ForeignMethod) {
            switch (method.getName()) {
                case "<init>":
                    return EnumMethod.INSTANCE_INITIALIZER;
                case "<clinit>":
                    return EnumMethod.STATIC_INITIALIZER;
                default:
                    return null;
            }
        } else if (method instanceof DisassembledMethod) {
            DisassembledMethod disassembledMethod = (DisassembledMethod) method;
            switch (disassembledMethod.getName()) {
                case "<init>":
                    return EnumMethod.INSTANCE_INITIALIZER;
                case "<clinit>":
                    return EnumMethod.STATIC_INITIALIZER;
                default:
                    if (disassembledMethod.isStatic())
                        return STATIC;
                    return INSTANCE;
            }
        }
        throw new NoUsageException("Unexpected");
    }
}
