package decompiler;

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
    public static String getValidName(String s) {
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
    public static String getValidTypeName(String s) {
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


    // Given a descriptor of a RawField or a RawMethod
    // Returns the string being the return type (unformatted)
    public static String getReturnType(String descriptor) {
        //String r = getEntry(descriptor_index).toString();
        int contains = descriptor.indexOf(")");
        if (contains != -1) { // is a method
            char c = descriptor.charAt(contains + 1);
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
                case 'L': {
                    descriptor = descriptor.substring(contains + 2); //.replaceAll("/", ".").replaceAll(";", "");

                    int d1 = descriptor.indexOf("<");
                    if (d1 != -1) {
                        int d2 = descriptor.lastIndexOf(">");
                        // then is more complicated
                        // can recursively get the type
                        String innerDescriptor = descriptor.substring(d1 + 1, d2);
                        innerDescriptor = getReturnType(innerDescriptor);

                        descriptor = descriptor.substring(0, d1 + 1) +
                                innerDescriptor +
                                descriptor.substring(d2);
                    }

                    //return Util.getUnqualifiedName(
                    //        descriptor.substring(contains + 2));
                    return descriptor.replaceAll("/", ".").replaceAll(";", "");
                }
            }
        } else { // is a field
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
                case 'L': {

                    descriptor = descriptor.substring(1); //.replaceAll("/", ".").replaceAll(";", "");

                    int d1 = descriptor.indexOf("<");
                    if (d1 != -1) {
                        int d2 = descriptor.lastIndexOf(">");
                        // then is more complicated
                        // can recursively get the type
                        StringBuilder finalInner = new StringBuilder();
                        String[] innerDescriptors = descriptor.substring(d1 + 1, d2).split(";");
                        for (int at=0; at < innerDescriptors.length; at++) {
                            String innerDescriptor = innerDescriptors[at];
                            //innerDescriptor = descriptor.substring(d1 + 1, d2);
                            finalInner.append(getReturnType(innerDescriptor));
                            if (at < innerDescriptors.length-1) {
                                finalInner.append(", ");
                            }
                        }
                        descriptor = descriptor.substring(0, d1 + 1) +
                                finalInner +
                                descriptor.substring(d2);
                    }


                    //return Util.getUnqualifiedName(
                    //        descriptor);
                    return descriptor.replaceAll("/", ".").replaceAll(";", "");
                }
            }
        }
        return null;
    }

    //public static String[]

}
