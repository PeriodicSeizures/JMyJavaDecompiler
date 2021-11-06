package com.crazicrafter1.jripper.types;

public interface IFieldDefined
        extends IBaseField, IMemberDefined {
    boolean isVolatile();
    boolean isTransient();
    boolean isEnum();

    @Override
    default String getFormattedFlags() {
        return IMemberDefined.super.getFormattedFlags() +
                (isVolatile() ? " volatile" : "") +
                (isTransient() ? " transient" : "");
    }
}
