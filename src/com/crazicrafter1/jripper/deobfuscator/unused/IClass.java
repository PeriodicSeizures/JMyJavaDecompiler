package com.crazicrafter1.jripper.deobfuscator.unused;

import com.crazicrafter1.jripper.decompiler.pool.ConstantFieldref;
import com.crazicrafter1.jripper.decompiler.pool.IMethodRef;

public interface IClass {

    String getPackageName();
    String getClassName();
    //String getSuperClassName();

    IClass getSuperClass();

    IMethod getInternalMethod(IMethodRef ref);
    IField getInternalField(ConstantFieldref ref);

}
