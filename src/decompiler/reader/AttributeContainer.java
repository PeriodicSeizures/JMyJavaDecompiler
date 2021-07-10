package decompiler.reader;

import decompiler.reader.attributes.RawAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttributeContainer extends RItem {

    private HashMap<RawAttribute.Attribute, RawAttribute> attribute_map = new HashMap<>();
    private ArrayList<String> ignored = new ArrayList<>();

    @Override
    public void read() throws IOException {

        // read the count first
        int attributes_count = bytes.readUnsignedShort();

        for (; attributes_count > 0; attributes_count--) {

            int attribute_name_index = bytes.readUnsignedShort();
            long attribute_length = bytes.readUnsignedInt();


            String name = (String)(getEntry(attribute_name_index).getValue());

            try {
                RawAttribute.Attribute atr = RawAttribute.Attribute.valueOf(name);

                float class_version = Float.parseFloat(currentClassInstance.getClassVersion());
                float atr_version = Float.parseFloat(atr.major + "." + atr.minor);

                if (class_version >= atr_version)
                {
                    RawAttribute javaAttribute = (RawAttribute) atr.clazz.newInstance();

                    javaAttribute.read();
                    attribute_map.put(atr, javaAttribute);

                    //System.out.println("attribute (valid): " + name);

                } else {
                    // do nothing
                    ignored.add(name + " (ver)");
                    //System.out.println("attribute (ver ignored): " + name);
                }

            } catch (Exception e) {
                 // else, attribute is not recognized (is custom)
                //System.out.println("attribute (ignored): " + name);
                ignored.add(name + "(insig)");
                //noinspection ResultOfMethodCallIgnored
                bytes.skip(attribute_length);
            }

        }
        //System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{AttributeContainer} ").append("\n");

        for (Map.Entry<RawAttribute.Attribute, RawAttribute> entry : attribute_map.entrySet()) {
            stringBuilder.append("  ").append(entry.getKey().name()).append(" : ").
                    append(entry.getValue().toString()).append("\n");
        }

        stringBuilder.append("ignored: \n");

        for (String s_ignored : ignored) {
            stringBuilder.append("  ").append(s_ignored).append("\n");
        }

        stringBuilder.append("size: ").append(attribute_map.size()).append("\n");

        return stringBuilder.toString();
    }

    public RawAttribute get(RawAttribute.Attribute atr) {
        return attribute_map.getOrDefault(atr, null);
    }
}
