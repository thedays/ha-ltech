package com.ltech.smarthome.cache;

import android.app.ActivityManager;
import android.content.Context;

/* loaded from: classes3.dex */
public interface CacheType {
    public static final int ACTIVITY_CACHE_TYPE_ID = 3;
    public static final int CACHE_SERVICE_CACHE_TYPE_ID = 1;
    public static final int EXTRAS_TYPE_ID = 2;
    public static final int FRAGMENT_CACHE_TYPE_ID = 4;
    public static final CacheType RETROFIT_SERVICE_CACHE = new CacheType() { // from class: com.ltech.smarthome.cache.CacheType.1
        private static final int MAX_SIZE = 150;
        private static final float MAX_SIZE_MULTIPLIER = 0.002f;

        @Override // com.ltech.smarthome.cache.CacheType
        public int getCacheTypeId() {
            return 0;
        }

        @Override // com.ltech.smarthome.cache.CacheType
        public int calculateCacheSize(Context context) {
            int memoryClass = (int) (((ActivityManager) context.getSystemService("activity")).getMemoryClass() * 0.002f * 1024.0f);
            if (memoryClass >= 150) {
                return 150;
            }
            return memoryClass;
        }
    };
    public static final int RETROFIT_SERVICE_CACHE_TYPE_ID = 0;

    int calculateCacheSize(Context context);

    int getCacheTypeId();
}