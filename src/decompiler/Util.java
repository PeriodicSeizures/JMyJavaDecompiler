package decompiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {


    /*
        Given 'com/package/class' or 'Lcom/package/class;'
        Returns 'class'
     */
    public static String getUnqualifiedName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf("/") + 1).replaceAll(";", "");
    }

    /*
        Returns a reformatted string:
            - i0:   a -> Z, _
            - i1->: a -> Z, 0 -> 9, _
     */
    public static String toValidName(String s) {
        StringBuilder builder = new StringBuilder();
        for (int pt : s.codePoints().toArray()) {
            if ((pt >= 48 && pt <= 57) ||       // 0 -> 9
                    (pt >= 65 && pt <= 90) ||   // A -> Z
                    (pt >= 97 && pt <= 122) ||  // a -> z
                    (pt == 95))                 // _
            {
                if (pt >= 48 && pt <= 57) {
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
            if ((pt >= 48 && pt <= 57) ||       // 0 -> 9
                    (pt >= 65 && pt <= 90) ||   // A -> Z
                    (pt >= 97 && pt <= 122) ||  // a -> z
                    (pt == 95) ||               // _
                    (pt == 46) || (pt == 60) || (pt ==62) ||    // . < >
                    (pt == ','))                                // ,
            {
                if (pt >= 48 && pt <= 57) {
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
        Given 'com/package/class'
        Returns {'com/package', 'class'}

        Unexpected behaviour will arise is qualifiedName is in object form, instead of package form
     */
    public static String[] getPackageAndClass(String qualifiedName) {
        // find occurrences of '/'
        int lastSlash = qualifiedName.lastIndexOf('/');

        //System.out.println("  :  " + class_name);
        //class_name = class_name.replaceAll(";", "");
        if (lastSlash != -1) {
            String name = getUnqualifiedName(qualifiedName);
            String pkg = qualifiedName.substring(0, qualifiedName.lastIndexOf("/"));
            return new String[] {pkg, name};
        } else {
            return new String[] {null, getUnqualifiedName(qualifiedName)};
        }
    }

    // Given a descriptor of a RawField-like structure
    // Returns the string being the signature type(s) (unformatted)
    public static String[] getSignature(String descriptor, HashSet<String> retClassImports, boolean concat) {

        System.out.println("DESC: " + descriptor);

        //StringBuilder descriptor = new StringBuilder(desc.substring(1, desc.indexOf(")")));
        //descriptor = descriptor.substring(1, descriptor.indexOf(")"));

        /*
            blank out everything within demiliters
         */
        //StringBuilder descriptor = new StringBuilder(descriptor.substring(1, descriptor.indexOf(")")));

        ArrayList<String> args = new ArrayList<>();

        int start = 0;
        int i = 0;

        int openDelimiters = 0;
        for (; i < descriptor.length(); i++ ) {
            if (descriptor.charAt(i) == '<') {
                openDelimiters++;
            } else if (descriptor.charAt(i) == '>') {
                openDelimiters--;
                if (openDelimiters == 0) {
                    // then record
                    args.add(descriptor.substring(start, i + 2));
                    start = i + 2;
                }
            }
        }

        //for (i=0; i < descriptor.length(); i++) {
        //    descriptor.charAt(i)
        //}

        System.out.println(args);

        //for (String s : args) {
        //    System.out.println(s);
        //}

        //String[] split = new String[]descriptor.split(";");

        //for (i=0; i < split.length; i++) {
        //    split[i] = getType(split[i], retClassImports);
        //}

        for (i=0; i < args.size(); i++) {
            args.set(i, getType(args.get(i), retClassImports));
        }

        if (concat) {
            StringBuilder c = new StringBuilder();
            for (i=0; i<args.size(); i++) {
                c.append(args.get(i));
                if (i < args.size() - 1)
                    c.append(", ");
            }
            return new String[] {c.toString()};
        }

        return args.toArray(new String[0]);
    }

    // Given a fieldlike descriptor
    // Returns the string being the return type (unformatted)
    public static String getType(String descriptor, HashSet<String> retClassImports) {
        //System.out.println("Type: " + descriptor);
        char c = descriptor.charAt(0);
        switch (c) {
            case 'B':
                return "byte";
            case 'C':
                return "char";
            case 'D':
                return "double";
            case 'F':
                return "float";
            case 'I':
                return "int";
            case 'J':
                return "long";
            case 'S':
                return "short";
            case 'Z':
                return "boolean";
            case 'V':
                return "void";
            //case '[':
            //    // count the amount
            //    int count = 1;
            //    int at=0;
            //    while ()
            case 'L': {
                descriptor = descriptor.substring(1, descriptor.length() - 1)
                        .replaceAll("<L", "<")
                        .replaceAll(";L", ",")
                        .replaceAll(";>", ">")
                        .replaceAll("java/lang/", "")
                        .replaceAll("/", ".");

                if (retClassImports != null) {
                    // thanks @Fried Rice On Ice
                    Pattern pattern = Pattern.compile("(.*?)(?=<)(?:.+?[\\s<])?");
                    Matcher matcher = pattern.matcher(descriptor);
                    while (matcher.find()) {
                        String match = matcher.group(1);

                        if (match.isEmpty()) continue;

                        retClassImports.add(match);

                        descriptor = descriptor.replaceAll(
                                match.substring(
                                        0, match.lastIndexOf('.') + 1), "");
                    }

                    // then remove all package references

                }

                return descriptor.replaceAll(",", ", ");
            }
        }

        return null;
    }

    public static String getMethodSignature(String methodDescriptor) {
        return null;
    }

    // Given a descriptor of a RawField or a RawMethod
    // Returns the string being the return type (unformatted)
    public static String getReturnType(String descriptor, HashSet<String> retClassImports) {
        //String r = getEntry(descriptor_index).toString();
        {
            int contains = descriptor.indexOf(")");
            if (contains != -1) // is a method
                descriptor = descriptor.substring(contains + 1);
        }

        return getType(descriptor, retClassImports);
    }

}
