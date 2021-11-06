package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.types.*;
import com.crazicrafter1.jripper.disassemble.constants.ConstantFieldRef;
import com.crazicrafter1.jripper.disassemble.constants.IMethodRef;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.UUID;

public abstract class ClassNamespace {

    static HashMap<String, DecompiledClass> classes = new HashMap<>();

    /**
     * Retrieve the representation of a class by its name
     * @param binaryClassName class name, 'java/lang/Object'
     * @return a wrapper representing the class
     */
    @NotNull
    public static IBaseClass getIClass(@NotNull String binaryClassName) {
        DecompiledClass decompiledClass = classes.get(binaryClassName);
        if (decompiledClass != null)
            return decompiledClass;
        return new ForeignClass(binaryClassName);
    }

    /**
     * Retrieve the representation of a method by its class name and its method descriptor
     * @param binaryClassName class name, 'java/lang/Object'
     * @param uuid unique method uuid
     * @return a wrapper representing the method
     */
    @Nullable
    public static IBaseMethod getIMethod(@NotNull String binaryClassName, @NotNull UUID uuid) {
        IBaseClass clazz;
        IBaseMethod method = null;

        while (method == null) {
            clazz = getIClass(binaryClassName);
            if (!(clazz instanceof IClassDefined))
                return null;

            method = ((IClassDefined) clazz).getIMethod(uuid);
            binaryClassName = ((IClassDefined) clazz).getSuperName();
        }
        return method;
    }

    /**
     * Retrieve the representation of a method by its method reference
     * @param ref a {@link IMethodRef}
     * @return a wrapper representing the method
     */
    @NotNull
    public static IBaseMethod getIMethod(@NotNull IMethodRef ref) {
        IBaseMethod method = getIMethod(ref.getReferredClassName(), ref.getUUID());
        if (method == null)
            return new ForeignMethod(ref);
        return method;
    }

    /**
     * Retrieve the representation of a field by its class name and its field name
     * @param binaryClassName class name, 'java/lang/Object'
     * @param uuid field name
     * @return a wrapper representing the field
     */
    @NotNull
    public static IBaseField getIField(@NotNull String binaryClassName, @NotNull UUID uuid) {
        IBaseClass clazz;
        IBaseField field = null;

        while (field == null) {
            clazz = getIClass(binaryClassName);
            if (!(clazz instanceof IClassDefined))
                return null;
            field = ((IClassDefined) clazz).getIField(uuid);

            binaryClassName = ((IClassDefined) clazz).getSuperName();
        }
        return field;
    }

    /**
     * Retrieve the representation of a field by its field reference
     * @param ref a {@link ConstantFieldRef}
     * @return a wrapper representing the field
     */
    @NotNull
    public static IBaseField getIField(@NotNull ConstantFieldRef ref) {
        IBaseField field = getIField(ref.getReferredClassName(), ref.getUUID());
        if (field == null)
            return new ForeignField(ref);
        return field;
    }
}
