package com.crazicrafter1.jripper.types;

import com.crazicrafter1.jripper.EnumVisibility;
import com.crazicrafter1.jripper.decompile.ForeignClass;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public interface IClassDefined
        extends IBaseDefined, IBaseClass {

    IBaseClass OBJECT_CLASS = new ForeignClass("java/lang/Object");
    IBaseClass ALL_LANG_CLASS = new ForeignClass("java/lang/*");

    IFieldDefined getIField(UUID uuid);
    IMethodDefined getIMethod(UUID uuid);
    //IField getIField(String name, String descriptor);
    //IField getIMethod(String name, String descriptor);

    String getSuperName();

    boolean isInterface();
    boolean isAbstractClass();
    boolean isAnnotation();
    boolean isEnum();

    boolean isSuper();

    @Override
    default EnumVisibility getVisibility() {
        return isPublic() ? EnumVisibility.PUBLIC
                : EnumVisibility.DEFAULT;
    }

    @Override
    default String getFormattedFlags() {
        return IBaseDefined.super.getFormattedFlags() +
                (isAbstractClass() ? " abstract class" :
                isInterface() ? " interface" :
                isAnnotation() ? " @interface" :
                isEnum() ? " enum" : " class");
    }

    ArrayList<String> getInterfaces();
    Set<String> getImportSet();
}
