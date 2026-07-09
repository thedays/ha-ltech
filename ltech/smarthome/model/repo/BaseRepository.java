package com.ltech.smarthome.model.repo;

import com.ltech.smarthome.model.BoxBuilderFactory;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import io.objectbox.BoxStore;

/* loaded from: classes4.dex */
public abstract class BaseRepository {
    protected BoxBuilderFactory mBoxBuilderFactory;
    protected BoxStore mBoxStore;
    protected KeyCreator mKeyCreator;
    protected RateLimiter mLimiter;
    protected IUser mUser;

    public BaseRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        this.mBoxStore = boxStore;
        this.mLimiter = limiter;
        this.mKeyCreator = keyCreator;
        this.mUser = user;
        this.mBoxBuilderFactory = new BoxBuilderFactory(this.mBoxStore);
    }
}