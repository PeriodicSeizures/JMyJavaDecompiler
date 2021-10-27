package com.crazicrafter1.jripper.deobfuscator;

public class NameUtil {


    /*
        Returns a reformatted string:
            - i0:   a -> Z, _
            - i1->: a -> Z, 0 -> 9, _
     */
    public static String toValidName(String s) {
        StringBuilder builder = new StringBuilder();
        for (int pt : s.codePoints().toArray()) {
            if ((pt >= '0' && pt <= '9') ||
                    (pt >= 'A' && pt <= 'Z') ||
                    (pt >= 'a' && pt <= 'z') ||
                    (pt == '_'))
            {
                // if starts with a number
                if (pt <= '9') {
                    builder.append("_");
                }
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
        Returns a reformatted string:
            - i0:   a -> Z, _
            - i1->: a -> Z, 0 -> 9, <, >, _, ., ,
     */
    public static String toValidTypeName(String s) {
        StringBuilder builder = new StringBuilder();
        for (int pt : s.codePoints().toArray()) {
            if ((pt >= '0' && pt <= '9') ||
                    (pt >= 'A' && pt <= 'Z') ||
                    (pt >= 'a' && pt <= 'z') ||
                    (pt == '_') ||
                    (pt == '.') || (pt == '<') || (pt == '>') ||
                    (pt == ',')) // slash for unique package case
            {
                if (pt >= '0' && pt <= '9') {
                    builder.append("_");
                }
                builder.append((char)pt);
                continue;
            }
            // else, nuke that codepoint
            if (builder.length() == 0) builder.append("_");
            builder.append(Integer.toHexString(pt));
        }
        return builder.toString();
    }


}
