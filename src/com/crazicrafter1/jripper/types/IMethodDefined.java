package com.crazicrafter1.jripper.types;

import com.crazicrafter1.jripper.EnumMethod;

import java.util.List;

public interface IMethodDefined
        extends IBaseMethod, IMemberDefined {

    default boolean isAbstract() {
        throw UNSAFE_EXCEPT;
    }
    default boolean isSynchronized() {
        throw UNSAFE_EXCEPT;
    }
    default boolean hasVarArgs() {
        throw UNSAFE_EXCEPT;
    }
    default boolean isStrictFP() {
        throw UNSAFE_EXCEPT;
    }

    @Override
    default String getFormattedFlags() {
        return IMemberDefined.super.getFormattedFlags() +
                (isAbstract() ? " abstract" : "") +
                (isSynchronized() ? " synchronized" : "") +
                (isStrictFP() ? " strictfp" : "");
    }
}
