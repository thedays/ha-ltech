package com.ltech.smarthome.singleton;

import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.cache.Cache;
import com.ltech.smarthome.cache.CacheType;
import com.ltech.smarthome.cache.IntelligentCache;
import com.ltech.smarthome.cache.LruCache;

/* loaded from: classes4.dex */
public class CacheManager {
    private Cache.Factory mCacheFactory;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        return (CacheManager) Singleton.getSingleton(CacheManager.class);
    }

    public Cache.Factory getCacheFactory() {
        Cache.Factory factory = this.mCacheFactory;
        return factory == null ? new Cache.Factory() { // from class: com.ltech.smarthome.singleton.CacheManager$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.cache.Cache.Factory
            public final Cache build(CacheType cacheType) {
                return CacheManager.lambda$getCacheFactory$0(cacheType);
            }
        } : factory;
    }

    static /* synthetic */ Cache lambda$getCacheFactory$0(CacheType cacheType) {
        int cacheTypeId = cacheType.getCacheTypeId();
        if (cacheTypeId == 2 || cacheTypeId == 3 || cacheTypeId == 4) {
            return new IntelligentCache(cacheType.calculateCacheSize(Utils.getApp()));
        }
        return new LruCache(cacheType.calculateCacheSize(Utils.getApp()));
    }
}