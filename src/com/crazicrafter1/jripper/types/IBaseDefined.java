package com.crazicrafter1.jripper.types;

import com.crazicrafter1.jripper.EnumVisibility;

/**
 * The building block for a defined object
 */
public interface IBaseDefined extends IBase {

    boolean isPublic();
    boolean isFinal();

    EnumVisibility getVisibility();

    default String getFormattedFlags() {
        return getVisibility().getName() +
                (isFinal() ? " final" : "");
    }

}
