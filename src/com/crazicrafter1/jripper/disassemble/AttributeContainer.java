package com.crazicrafter1.jripper.disassemble;

import com.crazicrafter1.jripper.disassemble.attributes.EnumAttr;
import com.crazicrafter1.jripper.disassemble.attributes.IAttr;
import com.crazicrafter1.jripper.util.ByteReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttributeContainer extends IDisassembled {

    private final HashMap<EnumAttr, IAttr> attribute_map = new HashMap<>();
    private final ArrayList<String> ignored = new ArrayList<>();

    public AttributeContainer(DisassembledClass belongingClass) {
        super(belongingClass);
    }

    @Override
    public void read(ByteReader bytes) throws IOException {

        // read the count first
        int attributes_count = bytes.readUnsignedShort();

        for (; attributes_count > 0; attributes_count--) {

            int attribute_name_index = bytes.readUnsignedShort();
            long attribute_length = bytes.readUnsignedInt();


            String name = (String)(getConstant(attribute_name_index).get());

            try {
                EnumAttr atr = EnumAttr.valueOf(name);

                float class_version = Float.parseFloat(getMain().getClassVersion());
                float atr_version = Float.parseFloat(atr.major + "." + atr.minor);

                if (class_version >= atr_version) {
                    IAttr javaAttribute = atr.clazz.getConstructor(DisassembledClass.class).newInstance(getMain());

                    ((IDisassembled)javaAttribute).read(bytes);
                    attribute_map.put(atr, javaAttribute);
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

        for (Map.Entry<EnumAttr, IAttr> entry : attribute_map.entrySet()) {
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

    public IAttr get(EnumAttr atr) {
        return attribute_map.get(atr);
    }
}
