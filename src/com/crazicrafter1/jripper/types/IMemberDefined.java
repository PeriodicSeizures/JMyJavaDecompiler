package com.crazicrafter1.jripper.types;

import com.crazicrafter1.jripper.EnumVisibility;

public interface IMemberDefined
        extends IBaseDefined, IBaseMember {

    boolean isPrivate();
    boolean isProtected();
    boolean isStatic();

    @Override
    default EnumVisibility getVisibility() {
        if (isPublic())
            return EnumVisibility.PUBLIC;
        else if (isPrivate())
            return EnumVisibility.PRIVATE;
        else if (isProtected())
            return EnumVisibility.PROTECTED;
        else return EnumVisibility.DEFAULT;
    }

    default String getFormattedFlags() {
        return IBaseDefined.super.getFormattedFlags() +
                (isStatic() ? " static" : "");
    }
}
