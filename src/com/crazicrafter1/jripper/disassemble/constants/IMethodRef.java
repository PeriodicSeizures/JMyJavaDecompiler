package com.crazicrafter1.jripper.disassemble.constants;

import java.util.List;

public interface IMethodRef extends IMemberRef {
    String getReturnType();
    List<String> getParameterTypes();
}
