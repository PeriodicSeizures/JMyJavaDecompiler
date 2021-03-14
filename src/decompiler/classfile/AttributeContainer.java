package decompiler.classfile;

import decompiler.Result;
import decompiler.classfile.attributes.JavaAttribute;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttributeContainer extends JavaItem {

    //public ArrayList<JavaAttribute> attributes = new ArrayList<>();
    public HashMap<JavaAttribute.Attribute, JavaAttribute> attribute_map = new HashMap<>();

    @Override
    public Result read() throws IOException {

        // read the count first
        int attributes_count = bytes.readUnsignedShort();

        for (; attributes_count > 0; attributes_count--) {

            int attribute_name_index = bytes.readUnsignedShort();
            long attribute_length = bytes.readUnsignedInt();


            String name = getEntry(attribute_name_index).toJavaSourceCode(-1);
            //System.out.println("atr: " + name);

            try {
                JavaAttribute.Attribute atr = JavaAttribute.Attribute.valueOf(name);

                float class_version = Float.parseFloat(currentClassInstance.getClassVersion());
                float atr_version = Float.parseFloat(atr.major + "." + atr.minor);

                if (class_version >= atr_version)
                {

                    // then is valid and should read
                    //Class.forName("")
                    JavaAttribute javaAttribute = (JavaAttribute) atr.clazz.newInstance();

                    javaAttribute.read();

                    //attributes.add(javaAttribute);
                    attribute_map.put(atr, javaAttribute);

                } else {
                    // do nothing
                    System.out.println("attribute being ignored due to class version: " + name);
                }

            } catch (Exception e) {
                 // else, attribute is not recognized (is custom)
                System.out.println("attribute being ignored: " + name);
                //noinspection ResultOfMethodCallIgnored
                bytes.skip(attribute_length);
            }

        }

        System.out.println(" --- ");

        return Result.OK;
    }

    @Override
    public String toString() {
        /*
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{Attributes}: ").append("\n");

        for (int i=1; i<attributes.size(); i++) {
            //JavaPoolEntry javaPoolEntry = JavaItem.get(i);
            JavaAttribute atr = attributes.get(i);
            stringBuilder.append("  ").append(i).append(" : ").append(atr.toString()).append("\n");
        }

        stringBuilder.append("size: ").append(attributes.size()).append("\n");

        return stringBuilder.toString();
        */
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{Attributes}: ").append("\n");

        for (Map.Entry<JavaAttribute.Attribute, JavaAttribute> entry : attribute_map.entrySet()) {
            stringBuilder.append("  ").append(entry.getKey().name()).append(" : ").
                    append(entry.getValue().toString()).append("\n");
        }

        stringBuilder.append("size: ").append(attribute_map.size()).append("\n");

        return stringBuilder.toString();
    }

    public JavaAttribute get(JavaAttribute.Attribute atr) {
        return attribute_map.getOrDefault(atr, null);
    }
}
