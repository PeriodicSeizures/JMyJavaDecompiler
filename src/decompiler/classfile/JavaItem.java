package decompiler.classfile;

import decompiler.JavaClassReader;
import decompiler.JavaClassFile;
import decompiler.Result;
import decompiler.classfile.pool.JavaPoolEntry;

import java.io.IOException;

public abstract class JavaItem {

    public static JavaClassReader bytes;

    public static JavaClassFile currentClassInstance;

    //public static DataInputStream bytes;//

    public abstract Result read() throws IOException;

    // can be overridden to return the string of the data as readable source code
    public String toJavaSourceCode(int class_index) {
        return this.toString();
    }

    /*
        Returns a reformatted string, with only var-name friendly characters
     */
    public static String getUnqualifiedName(String s) {
        StringBuilder builder = new StringBuilder();
        for (int pt : s.codePoints().toArray()) {
            if ((pt >= 48 && pt <= 57) ||       // 0 -> 9
                    (pt >= 65 && pt <= 90) ||   // A -> Z
                    (pt >= 97 && pt <= 122) ||  // a -> z
                    (pt == 95) ||               // _
                    (pt == 60) || (pt == 62))
            {
                builder.append((char)pt);
                continue;
            }

            // else, nuke that codepoint
            if (builder.length() == 0) builder.append("_");
            builder.append(Integer.toHexString(pt));
        }
        return builder.toString();
    }

    /*
        Returns a reformatted string, but allows '.' (for class packages)
     */
    public static String getQualifiedName(String s) {
        StringBuilder builder = new StringBuilder();
        for (int pt : s.codePoints().toArray()) {
            if ((pt >= 48 && pt <= 57) ||       // 0 -> 9
                    (pt >= 65 && pt <= 90) ||   // A -> Z
                    (pt >= 97 && pt <= 122) ||  // a -> z
                    (pt == 95) ||               // _
                    (pt == 46))                 // .
            {
                builder.append((char)pt);
                continue;
            }

            // else, nuke that codepoint
            if (builder.length() == 0) builder.append("_");
            builder.append(Integer.toHexString(pt));
        }
        return builder.toString();
    }

    //@Deprecated
    //public static String getPackageUnqualifiedName(String s) {
    //    StringBuilder builder = new StringBuilder();
    //    for (int pt : s.codePoints().toArray()) {
    //        if ((pt >= 48 && pt <= 57) ||       // 0 -> 9
    //                (pt >= 65 && pt <= 90) ||   // A -> Z
    //                (pt >= 97 && pt <= 122) ||  // a -> z
    //                (pt == 95) ||               // _
    //                (pt == 46))                 // .
    //        {
    //            builder.append((char)pt);
    //            continue;
    //        }

    //        // else, nuke that codepoint
    //        if (builder.length() == 0) builder.append("_");
    //        builder.append(Integer.toHexString(pt));
    //    }
    //    return builder.toString();
    //}

    /*
        Returns an unqualified array[2] {package, class name}
        (class_name should be in the form of "package1/package2/.../class")
     */
    public static String[] getSimplePackageAndClass(String class_name) {
        // find occurrences of '/'
        int lastSlash = class_name.lastIndexOf('/');

        //System.out.println("  :  " + class_name);
        class_name = class_name.replaceAll(";", "");
        if (lastSlash != -1) {
            String name = getUnqualifiedName(class_name.substring(lastSlash+1));
            String pkg = getQualifiedName(class_name.substring(0, lastSlash).replaceAll("/", "."));
            return new String[] {pkg, name};
        } else {
            return new String[] {null, getUnqualifiedName(class_name)};
        }
    }

    public static JavaPoolEntry getEntry(int i) {
        //return currentClassInstance.constantPoolContainer.getEntry(i);
        return currentClassInstance.constantPoolContainer.constant_pool.get(
                currentClassInstance.constantPoolContainer.spaced_indexes.get(i-1));
    }

}
