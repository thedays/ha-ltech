package com.ltech.smarthome.down;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/* loaded from: classes3.dex */
public class RxBus {
    private static volatile RxBus mInstance;
    private final Subject<Object> mBus;

    private RxBus() {
        this.mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getDefault() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = Holder.BUS;
                }
            }
        }
        return mInstance;
    }

    public void post(Object obj) {
        this.mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> cls) {
        return (Observable<T>) this.mBus.ofType(cls);
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();

        private Holder() {
        }
    }
}