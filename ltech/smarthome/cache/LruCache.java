package com.ltech.smarthome.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class LruCache<K, V> implements Cache<K, V> {
    private final LinkedHashMap<K, V> cache = new LinkedHashMap<>(100, 0.75f, true);
    private int currentSize = 0;
    private final int initialMaxSize;
    private int maxSize;

    protected int getItemSize(V item) {
        return 1;
    }

    protected void onItemEvicted(K key, V value) {
    }

    public LruCache(int size) {
        this.initialMaxSize = size;
        this.maxSize = size;
    }

    public synchronized void setSizeMultiplier(float multiplier) {
        if (multiplier < 0.0f) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        this.maxSize = Math.round(this.initialMaxSize * multiplier);
        evict();
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized int getMaxSize() {
        return this.maxSize;
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized int size() {
        return this.currentSize;
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized Set<K> keySet() {
        return this.cache.keySet();
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized V get(K key) {
        return this.cache.get(key);
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized V put(K key, V value) {
        if (getItemSize(value) >= this.maxSize) {
            onItemEvicted(key, value);
            return null;
        }
        V put = this.cache.put(key, value);
        if (value != null) {
            this.currentSize += getItemSize(value);
        }
        if (put != null) {
            this.currentSize -= getItemSize(put);
        }
        evict();
        return put;
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized V remove(K key) {
        V remove;
        remove = this.cache.remove(key);
        if (remove != null) {
            this.currentSize -= getItemSize(remove);
        }
        return remove;
    }

    @Override // com.ltech.smarthome.cache.Cache
    public void clear() {
        trimToSize(0);
    }

    protected synchronized void trimToSize(int size) {
        while (this.currentSize > size) {
            Map.Entry<K, V> next = this.cache.entrySet().iterator().next();
            V value = next.getValue();
            this.currentSize -= getItemSize(value);
            K key = next.getKey();
            this.cache.remove(key);
            onItemEvicted(key, value);
        }
    }

    private void evict() {
        trimToSize(this.maxSize);
    }
}