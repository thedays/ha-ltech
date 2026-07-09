package com.ltech.smarthome.blemesh.guard;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public interface Blocker {
    void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;

    <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception;

    void signal() throws InterruptedException;

    void signalAfter(Callable<Boolean> stateOperation) throws Exception;
}