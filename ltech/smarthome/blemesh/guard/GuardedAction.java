package com.ltech.smarthome.blemesh.guard;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public abstract class GuardedAction<V> implements Callable<V> {
    protected final Predicate guard;

    public GuardedAction(Predicate guard) {
        this.guard = guard;
    }
}