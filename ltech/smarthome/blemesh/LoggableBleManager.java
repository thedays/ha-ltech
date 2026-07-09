package com.ltech.smarthome.blemesh;

import android.content.Context;
import android.util.Log;
import com.feasycom.feasymesh.library.ble.BleManagerCallbacks;
import com.feasycom.feasymesh.library.ble.LegacyBleManager;
import no.nordicsemi.android.log.ILogSession;
import no.nordicsemi.android.log.LogContract;
import no.nordicsemi.android.log.Logger;

/* loaded from: classes3.dex */
public abstract class LoggableBleManager<T extends BleManagerCallbacks> extends LegacyBleManager<T> {
    private ILogSession mLogSession;

    LoggableBleManager(final Context context) {
        super(context);
    }

    public void setLogger(final ILogSession session) {
        this.mLogSession = session;
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManager, com.feasycom.feasymesh.library.ble.utils.ILogger
    public void log(final int priority, final String message) {
        Logger.log(this.mLogSession, LogContract.Log.Level.fromPriority(priority), message);
        Log.println(priority, "BleManager", message);
    }
}