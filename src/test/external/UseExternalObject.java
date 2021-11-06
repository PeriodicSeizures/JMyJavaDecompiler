package test.external;

import java.util.ArrayList;
import java.util.Objects;

public class UseExternalObject {

    public UseExternalObject(ArrayList<StringBuilder> arrayList) {
        Objects.requireNonNull(arrayList);
        arrayList.remove(arrayList.size() - 1);
        //arrayList.forEach(stringBuilder -> stringBuilder.append("!"));
    }
}
