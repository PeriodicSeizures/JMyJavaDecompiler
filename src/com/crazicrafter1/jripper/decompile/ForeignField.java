package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.types.IBaseClass;
import com.crazicrafter1.jripper.types.IBaseField;
import com.crazicrafter1.jripper.types.IClassDefined;
import com.crazicrafter1.jripper.types.IFieldDefined;
import com.crazicrafter1.jripper.disassemble.constants.ConstantFieldRef;

public class ForeignField
        extends AbstractForeign
        implements IBaseField {

    private final ConstantFieldRef ref;

    public ForeignField(ConstantFieldRef ref) {
        this.ref = ref;
    }

    @Override
    public IBaseClass getBaseClass() {
        return getIClass(ref.getReferredClassName());
    }

    @Override
    public String getDescriptor() {
        return ref.getDescriptor();
    }

    @Override
    public String getName() {
        return ref.getName();
    }
}
