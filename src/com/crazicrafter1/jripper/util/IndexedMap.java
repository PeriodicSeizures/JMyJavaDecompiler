package com.crazicrafter1.jripper.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class IndexedMap<K, V> extends LinkedHashMap<K, V> {

    public IndexedMap() {
    }

    public IndexedMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Map.Entry<K, V> getByIndex(int i) {
        int at = 0;
        for (Map.Entry<K, V> entry : this.entrySet()) {
            if (at == i)
                return entry;
            at++;
        }
        throw new IndexOutOfBoundsException();
    }

}
