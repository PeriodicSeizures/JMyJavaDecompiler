package com.crazicrafter1.jripper.decompiler.constants;

public interface IMethodRef extends IRef {

    ConstantClass getPointingClass();

    String getMethodName();

    String getMethodDescriptor();

}
