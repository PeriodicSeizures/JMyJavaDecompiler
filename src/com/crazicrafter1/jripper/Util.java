package com.crazicrafter1.jripper;

import com.crazicrafter1.jripper.decompiler.except.InvalidTypeException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    /**
     *
     * @param qualifiedName 'com/package/class' or 'Lcom/package/class;'
     * @return class
     */
    public static String getUnqualifiedName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf("/") + 1).replaceAll(";", "");
    }

    /**
     *
     * @param fullyQualifiedName 'decompiler/reader/pool/ConstantClass'
     * @return {'decompiler/reader/pool', 'ConstantClass'}
     */
    public static String[] getPackageAndClass(String fullyQualifiedName) {
        int lastSlash = fullyQualifiedName.lastIndexOf('/');

        if (lastSlash != -1) {
            String name = getUnqualifiedName(fullyQualifiedName);
            String pkg = fullyQualifiedName.substring(0, fullyQualifiedName.lastIndexOf("/"));
            return new String[] {pkg, name};
        } else {
            return new String[] {null, getUnqualifiedName(fullyQualifiedName)};
        }
    }

    /**
     *
     * @param methodDescriptor A method descriptor,
     *                   "(Ljava/util/ArrayList<Ljava/lang/Integer;>;Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Integer;>;)Ljava/util/ArrayList<Ljava/lang/Object;>;"
     * @return ReturnInfo structure, containing the method arguments in valid Java
     */
    public static ArrayList<String> getParameterTypes(String methodDescriptor, Set<String> outImports, StringBuilder outReturnType) {
        assert methodDescriptor.contains("(") : "Must be a method descriptor";

        ArrayList<String> args = new ArrayList<>();

        StringBuilder remaining = new StringBuilder(
                methodDescriptor.substring(1, methodDescriptor.indexOf(")")));

        while (!remaining.isEmpty()) {
            args.add(getNextFieldType(remaining.toString(), outImports, remaining));
        }

        if (outReturnType != null) {
            outReturnType.replace(0, outReturnType.length(),
                    getFieldType(methodDescriptor.substring(methodDescriptor.indexOf(")") + 1), outImports));
        }

        return args;
    }

    /**
     * Get the left-most type from a parameterDescriptor
     * @param parameterDescriptor A method parameterDescriptor, {FILjava/util/ArrayList<Ljava/lang/Integer;>;}
     * @return A valid Java type, { [float, int, ArrayList<Integer>]}
     */
    private static String getNextFieldType(String parameterDescriptor, Set<String> outImports, StringBuilder outRemainder) {

        assert parameterDescriptor != null : "descriptor must not be null";
        assert !parameterDescriptor.contains("(") : "Must be a field descriptor or stripped method (arg only) descriptor";

        if (outRemainder != null)
            outRemainder.replace(0, outRemainder.length(),
                    parameterDescriptor.substring(1));

        switch (parameterDescriptor.charAt(0)) {
            case 'B': return "byte";
            case 'C': return "char";
            case 'D': return "double";
            case 'F': return "float";
            case 'I': return "int";
            case 'J': return "long";
            case 'S': return "short";
            case 'Z': return "boolean";
            case 'V': return "void";
            case 'L': {
                /*
                 * Remove the first family of delimiters if they are present
                 */
                int openDelimiters = 0;
                for (int i = 0; i < parameterDescriptor.length(); i++) {
                    // if a ';' is found before a <> delimiter
                    // then cut early, and break
                    char ch = parameterDescriptor.charAt(i);
                    if (openDelimiters == 0 && ch == ';') {
                        if (outRemainder != null)
                            outRemainder.replace(0, outRemainder.length(), parameterDescriptor.substring(i + 1));

                        parameterDescriptor = parameterDescriptor.substring(0, i + 1);
                        break;
                    }

                    if (ch == '<') {
                        openDelimiters++;
                    } else if (ch == '>') {
                        openDelimiters--;
                        if (openDelimiters == 0) {
                            // then record
                            if (outRemainder != null)
                                outRemainder.replace(0, outRemainder.length(), parameterDescriptor.substring(i + 2));

                            parameterDescriptor = parameterDescriptor.substring(0, i + 2);
                            break;
                        }
                    }
                }

                // override the default retArguments to not include the current descriptor
                parameterDescriptor = parameterDescriptor.substring(1, parameterDescriptor.length() - 1)
                        .replaceAll("<L", "<")
                        .replaceAll(";L", ",")
                        .replaceAll(";>", ">")
                        .replaceAll("java/lang/", "")
                        .replaceAll("/", ".");

                /*
                 * Add import to set
                 */
                // thanks @Fried Rice On Ice
                Pattern pattern = Pattern.compile("(.*?)(?=<)(?:.+?[\\s<])?");
                Matcher matcher = pattern.matcher(parameterDescriptor);
                while (matcher.find()) {
                    String match = matcher.group(1);

                    if (match.isEmpty()) continue;

                    if (outImports != null)
                        outImports.add(match);

                    parameterDescriptor = parameterDescriptor.replaceAll(
                            match.substring(
                                    0, match.lastIndexOf('.') + 1), "");
                }

                return parameterDescriptor.replaceAll(";", "").
                        replaceAll(",", ", ");
            }
            default: throw new InvalidTypeException("type is invalid; class might be corrupted");
        }
    }


    /**
     *
     * @param fieldDescriptor Ljava/util/ArrayList<Ljava/lang/Integer;>;
     * @param outImports the return Set for imports
     * @return the Java valid type, java.util.ArrayList<Integer>
     */
    public static String getFieldType(String fieldDescriptor, Set<String> outImports) {
        assert !fieldDescriptor.contains("(") : "Must be a field or method return descriptor";

        char c = fieldDescriptor.charAt(0);
        switch (c) {
            case 'B': return "byte";
            case 'C': return "char";
            case 'D': return "double";
            case 'F': return "float";
            case 'I': return "int";
            case 'J': return "long";
            case 'S': return "short";
            case 'Z': return "boolean";
            case 'V': return "void";
            case 'L': {
                fieldDescriptor = fieldDescriptor.substring(1, fieldDescriptor.length() - 1)
                        .replaceAll("<L", "<")
                        .replaceAll(";L", ",")
                        .replaceAll(";>", ">")
                        .replaceAll("java/lang/", "")
                        .replaceAll("/", ".");

                if (outImports != null) {
                    // thanks @Fried Rice On Ice
                    Pattern pattern = Pattern.compile("(.*?)(?=<)(?:.+?[\\s<])?");
                    Matcher matcher = pattern.matcher(fieldDescriptor);
                    while (matcher.find()) {
                        String match = matcher.group(1);

                        if (match.isEmpty()) continue;

                        outImports.add(match);

                        fieldDescriptor = fieldDescriptor.replaceAll(
                                match.substring(
                                        0, match.lastIndexOf('.') + 1), "");
                    }
                }

                return fieldDescriptor.replaceAll(",", ", ");
            }
            default: {
                throw new InvalidTypeException("type " + c + " is invalid");
            }
        }
    }

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
