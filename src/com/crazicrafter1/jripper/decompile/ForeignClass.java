package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.types.IBaseClass;
import com.crazicrafter1.jripper.util.Util;

public class ForeignClass
        extends AbstractForeign
        implements IBaseClass {

    private final String binaryName;

    public ForeignClass(String binaryName) {
        this.binaryName = binaryName;
    }

    @Override
    public String getPackageName() {
        String[] pk = Util.getBinaryPackageAndClass(binaryName);
        return pk[0];
    }

    @Override
    public String getSimpleName() {
        String[] pk = Util.getBinaryPackageAndClass(binaryName);
        return pk[1];
    }

    @Override
    public String getName() {
        return binaryName;
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

    /*
    @Override
    public IField getInternalField(@NotNull ConstantFieldRef ref) {
        if (ref.getBinaryClassName().get().equals(binaryName)) {
            return new ForeignField(ref);
        }
        return null;
    }

    @Override
    public IMethod getInternalMethod(@NotNull IMethodRef ref) {
        if (ref.getPointingClass().get().equals(binaryName)) {
            return new ForeignMethod(ref);
        }
        return null;
    }
*/
}
