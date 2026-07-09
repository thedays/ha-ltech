package com.ltech.smarthome.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.jeremyliao.liveeventbus.LiveEventBus;

/* loaded from: classes4.dex */
public final class EventBusUtils {
    private static final String DEFAULT_KEY = "LiveData";

    private EventBusUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void register(LifecycleOwner owner, Observer<LiveBusHelper> observer) {
        LiveEventBus.get(DEFAULT_KEY, LiveBusHelper.class).observe(owner, observer);
    }

    public static void unregister(Observer<LiveBusHelper> observer) {
        LiveEventBus.get(DEFAULT_KEY, LiveBusHelper.class).removeObserver(observer);
    }

    public static void post(LiveBusHelper helper) {
        LiveEventBus.get(DEFAULT_KEY, LiveBusHelper.class).post(helper);
    }

    public static void postDelay(LiveBusHelper helper, long delay) {
        LiveEventBus.get(DEFAULT_KEY, LiveBusHelper.class).postDelay(helper, delay);
    }
}