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
    private List<K> keyList;

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
     *
     * @return list vith values or empty list
     */
    public List<V> getList(K key) {
        List<V> v = get(key);
        if (v == null) {
            v = Collections.emptyList();
        }
        return v;
    }

    /** Removes map entries where value is null or empty list
     */
    public synchronized void removeEmpty() {
        ArrayList<K> keys = new ArrayList<K>(keySet());
        for (K k : keys) {
            final List<V> list = get(k);
            if (list == null || list.isEmpty()) {
                remove(k);
            }
        }
        onKeysChanged();
    }

    public List<K> keyList() {
        return keyList;
    }

    public void onKeysChanged() {
        keyList = new ArrayList<K>(super.keySet());
    }
}
