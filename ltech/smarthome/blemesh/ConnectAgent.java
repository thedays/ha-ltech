package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.blemesh.guard.Blocker;
import com.ltech.smarthome.blemesh.guard.ConditionVarBlocker;
import com.ltech.smarthome.blemesh.guard.GuardedAction;
import com.ltech.smarthome.blemesh.guard.Predicate;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.permission.ActGetMeshPermission;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class ConnectAgent {
    private static volatile boolean isConnecting = false;
    private final Predicate agentConnected = new Predicate() { // from class: com.ltech.smarthome.blemesh.ConnectAgent$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.blemesh.guard.Predicate
        public final boolean evaluate() {
            return ConnectAgent.lambda$new$0();
        }
    };
    private final Blocker blocker = new ConditionVarBlocker();
    private Handler handler = new Handler(Looper.getMainLooper());

    static /* synthetic */ boolean lambda$new$0() {
        return !isConnecting;
    }

    public void checkConnected(final Runnable success, final Runnable fail) throws Exception {
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(this, this.agentConnected) { // from class: com.ltech.smarthome.blemesh.ConnectAgent.1
            @Override // java.util.concurrent.Callable
            public Void call() {
                if (Injection.mesh().isConnected()) {
                    Runnable runnable = success;
                    if (runnable == null) {
                        return null;
                    }
                    runnable.run();
                    return null;
                }
                Runnable runnable2 = fail;
                if (runnable2 == null) {
                    return null;
                }
                runnable2.run();
                return null;
            }
        };
        checkConnect();
        this.blocker.callWithGuard(guardedAction);
    }

    private void checkConnect() {
        if (Injection.mesh().isConnected() || isConnecting) {
            return;
        }
        isConnecting = true;
        if (Injection.mesh().hasMeshPermission()) {
            connect();
        } else {
            this.handler.post(new Runnable() { // from class: com.ltech.smarthome.blemesh.ConnectAgent$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ConnectAgent.this.lambda$checkConnect$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkConnect$2() {
        ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(new Intent(ActivityUtils.getTopActivity(), (Class<?>) ActGetMeshPermission.class), new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.blemesh.ConnectAgent$$ExternalSyntheticLambda0
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ConnectAgent.this.lambda$checkConnect$1(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkConnect$1(int i, Intent intent) {
        if (i == 1002) {
            connect();
            return;
        }
        if (i == 1003) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.permission_deny));
        }
        onConnectFinish();
    }

    private void reconnect() {
        this.handler.postDelayed(new Runnable() { // from class: com.ltech.smarthome.blemesh.ConnectAgent.2
            @Override // java.lang.Runnable
            public void run() {
                final ExtendedBluetoothDevice connectableDevice = Injection.mesh().getConnectableDevice();
                if (connectableDevice == null) {
                    ConnectAgent.this.scanAndConnect();
                } else {
                    Injection.mesh().connect(connectableDevice, new IConnectBleCallback() { // from class: com.ltech.smarthome.blemesh.ConnectAgent.2.1
                        @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
                        public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice) {
                            ConnectAgent.this.onConnectFinish();
                        }

                        @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
                        public void onConnectFail() {
                            ConnectAgent.this.onConnectFinish();
                            Injection.mesh().clearConnectableDevice(connectableDevice);
                        }
                    });
                }
            }
        }, 2000L);
    }

    private void connect() {
        LHomeLog.i(getClass(), "connect state=" + Injection.mesh().isConnected());
        if (Injection.mesh().isConnected()) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.ltech.smarthome.blemesh.ConnectAgent.3
            @Override // java.lang.Runnable
            public void run() {
                final ExtendedBluetoothDevice connectableDevice = Injection.mesh().getConnectableDevice();
                if (connectableDevice == null) {
                    ConnectAgent.this.scanAndConnect();
                } else {
                    Injection.mesh().connect(connectableDevice, new IConnectBleCallback() { // from class: com.ltech.smarthome.blemesh.ConnectAgent.3.1
                        @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
                        public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice) {
                            ConnectAgent.this.onConnectFinish();
                        }

                        @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
                        public void onConnectFail() {
                            ConnectAgent.this.onConnectFinish();
                            Injection.mesh().clearConnectableDevice(connectableDevice);
                        }
                    });
                }
            }
        }, 4000L);
    }

    public void scanAndConnect() {
        LHomeLog.i(getClass(), "startScan isScanning" + Injection.mesh().isScanning());
        if (Injection.mesh().isScanning()) {
            return;
        }
        Injection.mesh().startScan(BleMeshManager.MESH_PROXY_UUID, new IScanCallback() { // from class: com.ltech.smarthome.blemesh.ConnectAgent$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ConnectAgent.this.lambda$scanAndConnect$3(extendedBluetoothDevice);
            }
        });
        this.handler.postDelayed(new ConnectAgent$$ExternalSyntheticLambda2(this), 30000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanAndConnect$3(ExtendedBluetoothDevice extendedBluetoothDevice) {
        LHomeLog.i(getClass(), "startScan scanAndConnect: ");
        this.handler.removeCallbacksAndMessages(null);
        if (Injection.mesh().isScanning()) {
            Injection.mesh().stopScan();
        }
        connect();
    }

    public void reScanAndConnect() {
        LHomeLog.i(getClass(), "reScanAndConnect" + Injection.mesh().isScanning());
        if (Injection.mesh().isScanning()) {
            return;
        }
        Injection.mesh().startScan(BleMeshManager.MESH_PROXY_UUID, new IScanCallback() { // from class: com.ltech.smarthome.blemesh.ConnectAgent$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ConnectAgent.this.lambda$reScanAndConnect$4(extendedBluetoothDevice);
            }
        });
        this.handler.postDelayed(new ConnectAgent$$ExternalSyntheticLambda2(this), 30000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reScanAndConnect$4(ExtendedBluetoothDevice extendedBluetoothDevice) {
        LHomeLog.i(getClass(), "startScan scanAndConnect: ");
        this.handler.removeCallbacksAndMessages(null);
        if (Injection.mesh().isScanning()) {
            Injection.mesh().stopScan();
        }
        reconnect();
    }

    public void stopScanAndConnect() {
        this.handler.removeCallbacksAndMessages(null);
        if (Injection.mesh().isScanning()) {
            Injection.mesh().stopScan();
        }
        if (isConnecting && Injection.mesh().getConnectableDevice() == null) {
            onConnectFinish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectFinish() {
        isConnecting = false;
        try {
            this.blocker.broadcastAfter(new Callable() { // from class: com.ltech.smarthome.blemesh.ConnectAgent$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Boolean bool;
                    bool = Boolean.TRUE;
                    return bool;
                }
            });
        } catch (Exception unused) {
        }
    }
}