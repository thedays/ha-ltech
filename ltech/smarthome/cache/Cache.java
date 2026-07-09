package com.ltech.smarthome.cache;

import java.util.Set;

/* loaded from: classes3.dex */
public interface Cache<K, V> {

    public interface Factory {
        Cache build(CacheType type);
    }

    void clear();

    boolean containsKey(K key);

    V get(K key);

    int getMaxSize();

    Set<K> keySet();

    V put(K key, V value);

    V remove(K key);

    int size();
}