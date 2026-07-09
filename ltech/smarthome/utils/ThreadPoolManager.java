package com.ltech.smarthome.utils;

import com.ltech.smarthome.utils.QueryDeviceStateRunnable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ThreadPoolManager {
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private ThreadPoolExecutor executor;
    private LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
    Random random;

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    public void createThreadPool() {
        this.random = new Random();
        if (this.executor == null) {
            this.executor = new ThreadPoolExecutor(10, 50, 3L, TimeUnit.SECONDS, this.linkedBlockingQueue);
        }
    }

    public void execRunnable(ArrayList<QueryDeviceStateRunnable> task) {
        this.linkedBlockingQueue.clear();
        if (task == null) {
            return;
        }
        Iterator<QueryDeviceStateRunnable> it = task.iterator();
        while (it.hasNext()) {
            QueryDeviceStateRunnable next = it.next();
            next.setExecFinish(new QueryDeviceStateRunnable.ExecFinish() { // from class: com.ltech.smarthome.utils.ThreadPoolManager$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.utils.QueryDeviceStateRunnable.ExecFinish
                public final void finish(QueryDeviceStateRunnable queryDeviceStateRunnable) {
                    ThreadPoolManager.this.lambda$execRunnable$0(queryDeviceStateRunnable);
                }
            });
            if (task.size() > 10) {
                next.setSleep((int) (this.random.nextFloat() * 10.0f * 1000.0f));
            } else {
                next.setSleep(0);
            }
            this.executor.execute(next);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$execRunnable$0(QueryDeviceStateRunnable queryDeviceStateRunnable) {
        this.linkedBlockingQueue.remove(queryDeviceStateRunnable);
    }

    public void remove(Runnable task) {
        this.linkedBlockingQueue.clear();
        this.executor.remove(task);
    }

    public void execute(Runnable task) {
        if (this.executor == null) {
            createThreadPool();
        }
        this.executor.execute(task);
    }
}