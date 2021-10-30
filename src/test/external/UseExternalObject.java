package test.external;

import java.util.ArrayList;

public class UseExternalObject {

    public UseExternalObject(ArrayList<StringBuilder> arrayList) {
        arrayList.forEach(stringBuilder -> stringBuilder.append("!"));
    }
}
