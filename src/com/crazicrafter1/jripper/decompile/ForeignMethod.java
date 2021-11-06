package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.EnumMethod;
import com.crazicrafter1.jripper.types.IBaseClass;
import com.crazicrafter1.jripper.types.IBaseMethod;
import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.types.IMethodDefined;
import com.crazicrafter1.jripper.disassemble.constants.IMethodRef;
import com.crazicrafter1.jripper.util.Util;

import java.util.HashSet;
import java.util.List;

public class ForeignMethod
        extends AbstractForeign
        implements IBaseMethod {

    private final IMethodRef ref;
    private final List<String> parameterTypes;
    private final HashSet<String> inImports = new HashSet<>();
    private final StringBuilder inReturnType = new StringBuilder();

    public ForeignMethod(IMethodRef ref) {
        this.ref = ref;
        parameterTypes = Util.getParameterTypes(getDescriptor(), inImports, inReturnType);
    }

    @Override
    public String getName() {
        return ref.getName();
    }

    public HashSet<String> getImports() {
        return inImports;
    }

    @Override
    public String getDescriptor() {
        return ref.getDescriptor();
    }

    @Override
    public IBaseClass getBaseClass() {
        return getIClass(ref.getReferredClassName());
    }

    @Override
    public String getReturnType() {
        return inReturnType.toString();
    }

    @Override
    public List<String> getParameterTypes() {
        return this.parameterTypes;
    }
}
