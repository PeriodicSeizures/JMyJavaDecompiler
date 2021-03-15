package decompiler.reader;

import decompiler.Result;
import decompiler.reader.attributes.RawAttribute;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttributeContainer extends RawItem {

    private HashMap<RawAttribute.Attribute, RawAttribute> attribute_map = new HashMap<>();

    @Override
    public Result read() throws IOException {

        // read the count first
        int attributes_count = bytes.readUnsignedShort();

        for (; attributes_count > 0; attributes_count--) {

            int attribute_name_index = bytes.readUnsignedShort();
            long attribute_length = bytes.readUnsignedInt();


            String name = (String)(getEntry(attribute_name_index).getValue());
            //System.out.println("atr: " + name);

            try {
                RawAttribute.Attribute atr = RawAttribute.Attribute.valueOf(name);

                float class_version = Float.parseFloat(currentClassInstance.getClassVersion());
                float atr_version = Float.parseFloat(atr.major + "." + atr.minor);

                if (class_version >= atr_version)
                {
                    RawAttribute javaAttribute = (RawAttribute) atr.clazz.newInstance();

                    javaAttribute.read();
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

        //System.out.println(" --- ");

        return Result.OK;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{Attributes}: ").append("\n");

        for (Map.Entry<RawAttribute.Attribute, RawAttribute> entry : attribute_map.entrySet()) {
            stringBuilder.append("  ").append(entry.getKey().name()).append(" : ").
                    append(entry.getValue().toString()).append("\n");
        }

        stringBuilder.append("size: ").append(attribute_map.size()).append("\n");

        return stringBuilder.toString();
    }

    public RawAttribute get(RawAttribute.Attribute atr) {
        return attribute_map.getOrDefault(atr, null);
    }
}
