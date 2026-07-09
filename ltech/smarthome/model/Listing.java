package com.ltech.smarthome.model;

import androidx.lifecycle.LiveData;
import java.util.List;

/* loaded from: classes4.dex */
public class Listing<T> {
    private Runnable mRunnable;
    private LiveData<Resource<List<T>>> resource;

    public Listing(LiveData<Resource<List<T>>> resource, Runnable runnable) {
        this.resource = resource;
        this.mRunnable = runnable;
    }

    public LiveData<Resource<List<T>>> data() {
        return this.resource;
    }

    public void refresh() {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void retry() {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }
}