package com.ltech.smarthome.singleton;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.singleton.StateManager;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class StateManager {
    private static final String TAG = "StateManager";
    private boolean connectOuterNet = true;
    private boolean isRegisterReceiver = false;
    private boolean isChina = true;
    private final BroadcastReceiver stateReceiver = new AnonymousClass1();

    private StateManager() {
    }

    public boolean isChina() {
        return this.isChina;
    }

    public void setChina(boolean china) {
        this.isChina = china;
    }

    public boolean isConnectOuterNet() {
        return this.connectOuterNet;
    }

    public boolean isBluetoothEnabled() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    public boolean isLocationEnabled(final Context context) {
        int i;
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        try {
            i = Settings.Secure.getInt(context.getContentResolver(), "location_mode");
        } catch (Settings.SettingNotFoundException unused) {
            i = 0;
        }
        return i != 0;
    }

    public void registerNetworkReceiver(Context context) {
        if (this.isRegisterReceiver) {
            return;
        }
        this.isRegisterReceiver = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(this.stateReceiver, intentFilter);
    }

    public void unregisterNetworkReceiver(Context context) {
        if (this.isRegisterReceiver) {
            context.unregisterReceiver(this.stateReceiver);
        }
    }

    /* renamed from: com.ltech.smarthome.singleton.StateManager$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                NetworkUtils.isAvailableByPingAsync(new Utils.Consumer() { // from class: com.ltech.smarthome.singleton.StateManager$1$$ExternalSyntheticLambda0
                    @Override // com.blankj.utilcode.util.Utils.Consumer
                    public final void accept(Object obj) {
                        StateManager.AnonymousClass1.this.lambda$onReceive$0((Boolean) obj);
                    }
                });
            }
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                LHomeLog.d(getClass(), "onReceive: ".concat(intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 12 ? "STATE_ON" : "STATE_OFF"));
                Log.i("woaini", "=============" + intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(Boolean bool) {
            StateManager.this.connectOuterNet = bool.booleanValue();
        }
    }
}