package com.crazicrafter1.jripper.types;

import com.crazicrafter1.jripper.EnumMethod;

import java.util.List;

public interface IBaseMethod extends IBaseMember {
    String getReturnType();
    List<String> getParameterTypes();
    default EnumMethod getMethodType() {
        return EnumMethod.from(this);
    }
}
