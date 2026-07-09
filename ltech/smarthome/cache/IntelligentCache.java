package com.ltech.smarthome.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class IntelligentCache<V> implements Cache<String, V> {
    public static final String KEY_KEEP = "Keep=";
    private final Cache<String, V> mCache;
    private final Map<String, V> mMap = new HashMap();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ltech.smarthome.cache.Cache
    public /* bridge */ /* synthetic */ Object put(String key, Object value) {
        return put2(key, (String) value);
    }

    public IntelligentCache(int size) {
        this.mCache = new LruCache(size);
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized int size() {
        return this.mMap.size() + this.mCache.size();
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized int getMaxSize() {
        return this.mMap.size() + this.mCache.getMaxSize();
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized V get(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return this.mMap.get(key);
        }
        return this.mCache.get(key);
    }

    /* renamed from: put, reason: avoid collision after fix types in other method */
    public synchronized V put2(String key, V value) {
        if (key.startsWith(KEY_KEEP)) {
            return this.mMap.put(key, value);
        }
        return this.mCache.put(key, value);
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized V remove(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return this.mMap.remove(key);
        }
        return this.mCache.remove(key);
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized boolean containsKey(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return this.mMap.containsKey(key);
        }
        return this.mCache.containsKey(key);
    }

    @Override // com.ltech.smarthome.cache.Cache
    public synchronized Set<String> keySet() {
        Set<String> keySet;
        keySet = this.mCache.keySet();
        keySet.addAll(this.mMap.keySet());
        return keySet;
    }

    @Override // com.ltech.smarthome.cache.Cache
    public void clear() {
        this.mCache.clear();
        this.mMap.clear();
    }

    public static String getKeyOfKeep(String key) {
        return KEY_KEEP + key;
    }
}