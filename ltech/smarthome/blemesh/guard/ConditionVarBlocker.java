package com.ltech.smarthome.blemesh.guard;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class ConditionVarBlocker implements Blocker {
    private final Condition condition;
    private final Lock lock;

    public ConditionVarBlocker(Lock lock) {
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    public ConditionVarBlocker() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
    }

    @Override // com.ltech.smarthome.blemesh.guard.Blocker
    public <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception {
        this.lock.lockInterruptibly();
        try {
            Predicate predicate = guardedAction.guard;
            while (!predicate.evaluate()) {
                this.condition.await();
            }
            return guardedAction.call();
        } finally {
            this.lock.unlock();
        }
    }

    @Override // com.ltech.smarthome.blemesh.guard.Blocker
    public void signalAfter(Callable<Boolean> stateOperation) throws Exception {
        this.lock.lockInterruptibly();
        try {
            if (stateOperation.call().booleanValue()) {
                this.condition.signal();
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override // com.ltech.smarthome.blemesh.guard.Blocker
    public void signal() throws InterruptedException {
        this.lock.lockInterruptibly();
        try {
            this.condition.signal();
        } finally {
            this.lock.unlock();
        }
    }

    @Override // com.ltech.smarthome.blemesh.guard.Blocker
    public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
        this.lock.lockInterruptibly();
        try {
            if (stateOperation.call().booleanValue()) {
                this.condition.signalAll();
            }
        } finally {
            this.lock.unlock();
        }
    }
}