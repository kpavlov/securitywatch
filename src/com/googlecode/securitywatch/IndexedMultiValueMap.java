package com.googlecode.securitywatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * IndexedMultiValueMap
 * <p/>
 * Revision: 1
 *
 * @author Konstantin Pavlov
 * @since 31.07.2010
 */
public class IndexedMultiValueMap<K, V> extends LinkedHashMap<K, List<V>> {

    public void add(K key, V value) {
        List<V> list = get(key);
        if (list == null) {
            list = new ArrayList<V>();
            super.put(key, list);
        }
        list.add(value);
    }

    /**
     * Returns list of values by given key.
     * <p/>
     * Never returns null
     *
     * @param key a key
     * @return list vith values or empty list
     */
    public List<V> getList(K key) {
        List<V> v = get(key);
        if (v == null) {
            v = Collections.emptyList();
        }
        return v;
    }

    public List<K> keyList() {
        return new ArrayList<K>(super.keySet());
    }
}
