package decompiler;

import decompiler.classfile.JavaItem;
import decompiler.classfile.fields.JavaField;
import decompiler.classfile.methods.JavaMethod;
import decompiler.classfile.pool.ConstantUtf8;
import decompiler.classfile.pool.JavaPoolEntry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class WatchList {

    /*
        utility class for debugging where specific utf8 structures are by which classes/methods/fields...
     */

    private static HashSet<String> WATCHER = new HashSet<>(
            Arrays.asList("decompiler/SAMPLER2$AnInnerClass"));

    public static void performSearch(JavaClassFile javaClassFile) {
        // look in all types for the strings /
        for (JavaField javaField : javaClassFile.fieldContainer.fields) {
            for (Field field : javaField.getClass().getFields()) {
                if (field.getName().contains("index")) {
                    field.setAccessible(true);
                    try {
                        int index = field.getInt(javaField);
                        JavaPoolEntry entry = JavaItem.getEntry(index);

                        if (entry instanceof ConstantUtf8 &&
                                WATCHER.contains(entry.toString()))
                        {
                            System.out.println("Field '" + javaField + "' uses a watched string");
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }

        for (JavaMethod javaMethod : javaClassFile.methodContainer.methods) {
            for (Field field : javaMethod.getClass().getFields()) {
                if (field.getName().contains("index")) {
                    field.setAccessible(true);
                    try {
                        int index = field.getInt(javaMethod);
                        JavaPoolEntry entry = JavaItem.getEntry(index);

                        if (entry instanceof ConstantUtf8 &&
                                WATCHER.contains(entry.toString()))
                        {
                            System.out.println("Method '" + javaMethod + "' uses a watched string");
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }

    }

}
