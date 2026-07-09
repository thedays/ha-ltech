package com.ltech.smarthome.base;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.atomic.AtomicBoolean;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private static final String TAG = "SingleLiveEvent";
    private final AtomicBoolean mPending;

    public SingleLiveEvent(T value) {
        super(value);
        this.mPending = new AtomicBoolean(false);
    }

    public SingleLiveEvent() {
        this.mPending = new AtomicBoolean(false);
    }

    @Override // androidx.lifecycle.LiveData
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {
        if (hasActiveObservers()) {
            Timber.tag(TAG).w("Multiple observers registered but only one will be notified of changes.", new Object[0]);
        }
        super.observe(owner, new Observer() { // from class: com.ltech.smarthome.base.SingleLiveEvent$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SingleLiveEvent.this.lambda$observe$0(observer, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$observe$0(Observer observer, Object obj) {
        if (this.mPending.compareAndSet(true, false)) {
            observer.onChanged(obj);
        }
    }

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void setValue(T t) {
        this.mPending.set(true);
        super.setValue(t);
    }

    public void call() {
        setValue(null);
    }
}