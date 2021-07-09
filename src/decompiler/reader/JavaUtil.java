package decompiler.reader;

import com.sun.istack.internal.NotNull;
import decompiler.except.InvalidTypeException;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaUtil {

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
     * @param descriptor A method descriptor,
     *                   "(Ljava/util/ArrayList<Ljava/lang/Integer;>;Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Integer;>;)Ljava/util/ArrayList<Ljava/lang/Object;>;"
     * @return ReturnInfo structure, containing the method arguments in valid Java
     */
    // TODO
    // java sucks for not using references
    // so the ReturnInfo object is needed to return many types
    // (this is why c++ is better in every way)
    public static ReturnInfo getMethodArguments(@NotNull String descriptor) {

        assert descriptor.contains("(") : "Must be a method descriptor";

        // Strip out everything except for whats in the parentheses
        descriptor = descriptor.substring(1, descriptor.indexOf(")"));

        // Information to be returned
        ReturnInfo returnInfo = new ReturnInfo();

        /*
         * The descriptor will shrink while it gets processed
         */
        ReturnInfo loopRet = new ReturnInfo();
        loopRet.remaining = descriptor;
        //while(!(loopRet = getFirstType(loopRet.remaining)).remaining.isEmpty()) {
        //    // append args to returnInfo
        //    System.out.println(loopRet);
        //    returnInfo.args.add(loopRet.type);
        //    returnInfo.classImports.addAll(loopRet.classImports);
        //}

        while(true) {

            //System.out.println(loopRet);

            if (loopRet.remaining.isEmpty())
                break;

            loopRet = getFirstType(loopRet.remaining);
            returnInfo.args.add(loopRet.type);
            returnInfo.classImports.addAll(loopRet.classImports);
        }

        return returnInfo;
    }

    /**
     *
     * @param descriptor A field-like String descriptor, "Ljava/util/ArrayList<Ljava/lang/Integer;>;"
     * @return A valid Java type, "java.util.ArrayList<Integer>"
     */
    public static ReturnInfo getFirstType(@NotNull String descriptor) {

        assert !descriptor.contains("(") : "Must be a field descriptor or stripped method (arg only) descriptor";

        //System.out.println("Type: " + descriptor);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.remaining = descriptor.substring(1); // set default remaining arguments

        char c = descriptor.charAt(0);
        switch (c) {
            case 'B':
                returnInfo.type = "byte"; break;
            case 'C':
                returnInfo.type = "char"; break;
            case 'D':
                returnInfo.type = "double"; break;
            case 'F':
                returnInfo.type = "float"; break;
            case 'I':
                returnInfo.type = "int"; break;
            case 'J':
                returnInfo.type = "long"; break;
            case 'S':
                returnInfo.type = "short"; break;
            case 'Z':
                returnInfo.type = "boolean"; break;
            case 'V':
                returnInfo.type = "void"; break;
            case 'L': {

                /*
                 * Remove the first family of delimiters if they are present
                 */
                int openDelimiters = 0;
                for (int i = 0; i < descriptor.length(); i++) {
                    // if a ';' is found before a <> delimiter
                    // then cut early, and break
                    char ch = descriptor.charAt(i);
                    if (openDelimiters == 0 && ch == ';') {
                        returnInfo.remaining = descriptor.substring(i + 1);
                        //System.out.println("Here0");
                        descriptor = descriptor.substring(0, i + 1);
                        break;
                    }

                    if (ch == '<') {
                        openDelimiters++;
                    } else if (ch == '>') {
                        openDelimiters--;
                        if (openDelimiters == 0) {
                            // then record
                            returnInfo.remaining = descriptor.substring(i + 2);
                            descriptor = descriptor.substring(0, i + 2);
                            break;
                        }
                    }
                }

                // override the default retArguments to not include the current descriptor


                descriptor = descriptor.substring(1, descriptor.length() - 1)
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
                Matcher matcher = pattern.matcher(descriptor);
                while (matcher.find()) {
                    String match = matcher.group(1);

                    if (match.isEmpty()) continue;

                    returnInfo.classImports.add(match);

                    descriptor = descriptor.replaceAll(
                            match.substring(
                                    0, match.lastIndexOf('.') + 1), "");
                }

                returnInfo.type = descriptor.replaceAll(";", "").
                        replaceAll(",", ", ");
                break;
            }
            default: {
                throw new InvalidTypeException("type " + c + " is invalid");
            }
        }

        return returnInfo;
    }


    /**
     *
     * @param descriptor Ljava/util/ArrayList<Ljava/lang/Integer;>;
     * @param retClassImports the return Set for imports
     * @return the Java valid type, java.util.ArrayList<Integer>
     */
    public static String getType(String descriptor, HashSet<String> retClassImports) {

        assert !descriptor.contains("(") : "Must be a field descriptor or stripped method (arg only) descriptor";

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
            default: {
                throw new InvalidTypeException("type " + c + " is invalid");
            }
        }
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
