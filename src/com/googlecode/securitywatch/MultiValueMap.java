package com.googlecode.securitywatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * MultiValueMap
 * <p/>
 * Revision: 1
 * @since 31.07.2010
 *
 * @author maestro
 */
public class MultiValueMap<K,V> extends HashMap<K, List<V>> {

    public void add(K key,V value) {
        List<V> list = get(key);
        if (list == null) {
            list = new ArrayList<V>();
            super.put(key, list);
        }
        list.add(value);
    }

    public List<V> getList(K key) {
        List<V> v = get(key);
        if (v == null) {
            v = Collections.emptyList();
        }
        return v;
    }
}
