package com.ltech.smarthome.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public final class Singleton {
    private static volatile ConcurrentHashMap<Class, Object> INSTANCE_MAP = new ConcurrentHashMap<>();

    private Singleton() {
    }

    public static <T> T getSingleton(Class<T> cls) {
        T t = (T) INSTANCE_MAP.get(cls);
        if (t == null) {
            try {
                synchronized (Singleton.class) {
                    t = (T) INSTANCE_MAP.get(cls);
                    if (t == null) {
                        Constructor<T> declaredConstructor = cls.getDeclaredConstructor(null);
                        declaredConstructor.setAccessible(true);
                        T newInstance = declaredConstructor.newInstance(null);
                        t = (T) INSTANCE_MAP.putIfAbsent(cls, newInstance);
                        if (t == null) {
                            t = newInstance;
                        }
                    }
                }
                return t;
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T> void removeSingleton(Class<T> type) {
        INSTANCE_MAP.remove(type);
    }
}