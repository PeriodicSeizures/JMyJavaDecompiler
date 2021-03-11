package decompiler.classfile;

import decompiler.IncrementalBytes;
import decompiler.JavaClassFile;
import decompiler.Result;
import decompiler.classfile.pool.JavaPoolEntry;

import java.io.IOException;

public abstract class JavaItem {

    public static IncrementalBytes bytes;

    public static JavaClassFile currentClassInstance;

    //public static DataInputStream bytes;//

    public abstract Result read() throws IOException;

    //private Object exampleNameLiteral =

    public String getUnqualifiedName(String s) {
        StringBuilder builder = new StringBuilder();
        for (int pt : s.codePoints().toArray()) {
            if ((pt >= 48 && pt <= 57) ||       // 0 -> 9
                    (pt >= 65 && pt <= 90) ||   // A -> Z
                    (pt >= 97 && pt <= 122) ||  // a -> z
                    (pt == 95))                 // _
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

    public String getPackageUnqualifiedName(String s) {
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

    public static JavaPoolEntry getEntry(int i) {
        //return currentClassInstance.constantPoolContainer.getEntry(i);
        return currentClassInstance.constantPoolContainer.constant_pool.get(
                currentClassInstance.constantPoolContainer.spaced_indexes.get(i-1));
    }

}
