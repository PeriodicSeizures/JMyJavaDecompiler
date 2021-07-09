package decompiler.shared;

import decompiler.reader.ConstantContainer;
import decompiler.reader.RawClass;
import decompiler.reader.attributes.InnerClassesAttribute;
import decompiler.reader.attributes.RawAttribute;
import decompiler.reader.pool.ConstantClass;
import decompiler.reader.pool.RawConstant;

import java.util.HashMap;

public class GlobalLinker {

    /*
        will keep data all togethor and consistent for jvm
        lookups, especially for renaming classes/fields/methods
     */

    //private static ArrayList<ReferencedClass> classes = new ArrayList<>();
    private static HashMap<String, ReferencedClass> classesByBytecodeName = new HashMap<>();

    // per class constantpool
    private static HashMap<Integer, ReferencedClass> classesByIndex = new HashMap<>();

    // instance methods are invoked with invokevirtual, and specified by string name

    public static void addClasses(RawClass rawClass) {
        ConstantContainer constantContainer = rawClass.constantPoolContainer;
        // look through container, looking for class constants
        //RawAttribute atr = rawClass.attributeContainer.get(RawAttribute.Attribute.InnerClasses);
        //HashMap<String, >
        RawAttribute atr = null;
        for (int i=0; i<constantContainer.spaced_indexes.size(); i++) {

            RawConstant constant = constantContainer.constants.get(
                    constantContainer.spaced_indexes.get(i));

            if (constant instanceof ConstantClass) {

                String name = (String) constant.getValue();

                ReferencedClass referencedClass = null;

                /*
                    Determine is class is an INNERCLASS
                 */

                if (atr != null) {
                    //for (InnerClassesAttribute.InnerClassEntry innerClass : ((InnerClassesAttribute)atr).getClasses())
                    //    if ((String) RawItem.getEntry(innerClass.getInnerClassInfoIndex()).getValue();
                    // check whether this class is an innerclass

                    //if (((InnerClassesAttribute)atr).getClasses().)

                    //referencedClass = new ReferencedClass(name, );
                } else {
                    referencedClass = new ReferencedClass(name, null);
                }
                classesByBytecodeName.putIfAbsent(name, referencedClass);
                classesByIndex.putIfAbsent(i + 1, referencedClass);
            }

        }

    }
}
