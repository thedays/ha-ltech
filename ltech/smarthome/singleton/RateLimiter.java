package com.ltech.smarthome.singleton;

import androidx.collection.ArrayMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class RateLimiter {
    private long timeOut = TimeUnit.MINUTES.toMillis(5);
    private ArrayMap<String, Long> mTimeStamps = new ArrayMap<>(16);

    private RateLimiter() {
    }

    public synchronized boolean shouldFetch(String key) {
        Long l = this.mTimeStamps.get(key);
        if (l == null) {
            refresh(key);
            return true;
        }
        if (System.currentTimeMillis() - l.longValue() <= this.timeOut) {
            return false;
        }
        refresh(key);
        return true;
    }

    public synchronized void refresh(String key) {
        this.mTimeStamps.put(key, Long.valueOf(System.currentTimeMillis()));
    }

    public synchronized void reset(String key) {
        this.mTimeStamps.remove(key);
    }

    public synchronized void clear() {
        this.mTimeStamps.clear();
    }
}