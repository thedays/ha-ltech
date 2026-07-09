package com.ltech.smarthome.model;

import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ResourceEmptyLiveData<T> extends LiveData<Resource<List<T>>> {
    private static ResourceEmptyLiveData mLiveData = new ResourceEmptyLiveData();

    private ResourceEmptyLiveData() {
        postValue(Resource.success(new ArrayList()));
    }

    public static <T> LiveData<Resource<List<T>>> create() {
        return mLiveData;
    }
}