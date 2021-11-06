package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.types.IMemberDefined;

public interface IDisassembledMember
        extends IMemberDefined {

    int ACC_PUBLIC =        0x0001;
    int ACC_PRIVATE =       0x0002;
    int ACC_PROTECTED =     0x0004;
    int ACC_STATIC =        0x0008;
    int ACC_FINAL =         0x0010;

    int getAccessFlags();

    default boolean isPublic() {
        return (getAccessFlags() & ACC_PUBLIC) == ACC_PUBLIC;
    }

    default boolean isPrivate() {
        return (getAccessFlags() & ACC_PRIVATE) == ACC_PRIVATE;
    }

    default boolean isProtected() {
        return (getAccessFlags() & ACC_PROTECTED) == ACC_PROTECTED;
    }

    default boolean isStatic() {
        return (getAccessFlags() & ACC_STATIC) == ACC_STATIC;
    }

    default boolean isFinal() {
        return (getAccessFlags() & ACC_FINAL) == ACC_FINAL;
    }
}
