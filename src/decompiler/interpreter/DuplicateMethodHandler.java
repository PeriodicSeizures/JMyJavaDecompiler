package decompiler.interpreter;

import decompiler.Util;
import decompiler.reader.MethodContainer;
import decompiler.reader.RawItem;
import decompiler.reader.RawMethod;

import java.util.ArrayList;
import java.util.HashSet;

public class DuplicateMethodHandler {

    /*
        will sort out any invalid methods
        aka any dipshit duplicate signatures

            - int something(int, int)
            - float something(int, int)
            - boolean something(int, int)
            - String something(int, int)
            - double something(int, int)

        you get the point ...

        if a method signature is duplicated,
        only its name needs to change to something unique
     */


    public class RepairedMethod {

        private final String name;          // original name for matching / reference
        private final String signature;
        private final String returnType;
        //private final String descriptor;

        private String newName;             // new signature unique name

        RepairedMethod(String name, String descriptor, HashSet<String> retClassImports) {
            this.name = name;
            this.signature = Util.getSignature(descriptor, retClassImports, true)[0];
            this.returnType = Util.getReturnType(descriptor, retClassImports);
            //this.descriptor = descriptor;
            this.newName = name;
        }

        public void replaceName(String newName) {
            this.newName = newName;
        }
    }

    // Map of all *unformatted* methods names and their signatures (args and return type)
    //private HashMap<String, ArrayList<EMethod>> mappedMethods = new HashMap<>();
    //public HashMap<String, RepairedMethod> mappedMutatedMethods = new HashMap<>();
    private ArrayList<RepairedMethod> repairedMethods = new ArrayList<>();

    //private HashSet<String> imports = new St;

    public DuplicateMethodHandler(MethodContainer methodContainer, HashSet<String> imports) {

        HashSet<Integer> problematicMethods = new HashSet<>();
        HashSet<String> trackedUniques = new HashSet<>();
        for (int index = 0; index < methodContainer.methods.size(); index++) {
            RawMethod rawMethod = methodContainer.methods.get(index);
            // returns reference not copy
            //ArrayList<EMethod> matches = mappedMethods.getOrDefault(rawMethod.getNewName(), new ArrayList<>());

            //matches.add(new EMethod(rawMethod, index));

            String unique = getAsNameSignature(rawMethod.getName(), rawMethod.getDescriptor());

            if (trackedUniques.contains(unique)) {
                // then is problematic
                problematicMethods.add(index);
                System.out.println("found problematic (duplicate) method");
            } else {
                trackedUniques.add(unique);
            }

            repairedMethods.add(new RepairedMethod(rawMethod.getName(), rawMethod.getDescriptor(), imports));
        }

        /*
            not the best renaming scheme, but it will work
         */
        for (int index : problematicMethods) {
            RepairedMethod m = repairedMethods.get(index);
            m.replaceName(m.name + "_" + Util.toValidTypeName(m.returnType));
        }

    }

    public ArrayList<JavaMethod> getJavaMethods() {

        ArrayList<JavaMethod> javaMethods = new ArrayList<>();

        for (int index = 0; index < repairedMethods.size(); index++) {
            RepairedMethod repairedMethod = repairedMethods.get(index);



            if (repairedMethod.name.equals("<init>"))
                javaMethods.add(new JavaMethod(
                        Util.toValidName(Util.getUnqualifiedName(RawItem.currentClassInstance.getName())),
                        Util.toValidTypeName(repairedMethod.signature),
                        null,
                        index));
            else
                javaMethods.add(new JavaMethod(
                        Util.toValidName(Util.getUnqualifiedName(repairedMethod.name)),
                        Util.toValidTypeName(repairedMethod.signature),
                        Util.toValidTypeName(repairedMethod.returnType),
                        index));
        }

        return javaMethods;
    }

    private static String getAsNameSignature(String name, String descriptor) {
        return name + descriptor.substring(descriptor.indexOf("("), descriptor.indexOf(")") + 1);
    }

}
