package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.ActivityUtils;
import com.feasycom.feasymesh.library.MeshManagerApi;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.blemesh.feasy.FeasyController;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.utils.LHomeLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public class MeshManager implements IMeshManager {
    private ConnectAgent connectAgent = new ConnectAgent();
    private Context context;
    private String currentNetworkUUID;
    private Disposable disposable;
    private IMeshController mIMeshController;
    private ScanManager mScanManager;

    private MeshManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IMeshController getMeshController() {
        if (this.mIMeshController == null) {
            synchronized (IMeshController.class) {
                if (this.mIMeshController == null) {
                    this.mIMeshController = new FeasyController(new MeshManagerApi(MyApplication.getContext()), new BleMeshManager(MyApplication.getContext()));
                }
            }
        }
        return this.mIMeshController;
    }

    private ScanManager getScanManager() {
        if (this.mScanManager == null) {
            synchronized (ScanManager.class) {
                if (this.mScanManager == null) {
                    this.mScanManager = new ScanManager(((FeasyController) getMeshController()).getMeshManagerApi());
                }
            }
        }
        return this.mScanManager;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void init() {
        this.context = MyApplication.getContext();
        getMeshController();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void deInit() {
        IMeshController iMeshController = this.mIMeshController;
        if (iMeshController != null) {
            iMeshController.disconnect();
            this.mIMeshController.clear();
            this.mIMeshController = null;
        }
        ScanManager scanManager = this.mScanManager;
        if (scanManager != null) {
            scanManager.stopScan();
            this.mScanManager = null;
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public boolean useBle() {
        return checkUseBle();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void refreshNetwork(String networkJson, IRefreshNetworkCallback callback) {
        getMeshController().refreshNetwork(networkJson, callback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public String exportMeshNetwork() {
        return getMeshController().exportMeshNetwork();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void clearSharedPreferencesByMeshUUID() {
        getMeshController().clearSharedPreferencesByMeshUUID();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void inGroupByCmd(Object controlObject, int groupAddress, IAction<Boolean> result) {
        if (controlObject instanceof Device) {
            Device device = (Device) controlObject;
            if (RelaySeparationHelper.isSwitchRelay(device)) {
                int relayNum = RelaySeparationHelper.getRelayNum(device);
                LHomeLog.e("inGroupByCmd", getClass(), "num=" + relayNum);
                CmdAssistant.getSettingCmdAssistant(controlObject, 1 << (relayNum - 1)).inGroup(ActivityUtils.getTopActivity(), groupAddress, result);
                return;
            }
        }
        LHomeLog.e("inGroupByCmd", getClass(), "num=没有");
        CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).inGroup(ActivityUtils.getTopActivity(), groupAddress, result);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void inGroupByCmd(Object controlObject, int groupAddress, IAction<Boolean> result, int zone) {
        CmdAssistant.getSettingCmdAssistant(controlObject, zone).inGroup(ActivityUtils.getTopActivity(), groupAddress, result);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void inGroupByCmd(Object controlObject, int groupAddress, int publishAddress, IAction<Boolean> result) {
        if (controlObject instanceof Device) {
            Device device = (Device) controlObject;
            if (RelaySeparationHelper.isSwitchRelay(device)) {
                CmdAssistant.getSettingCmdAssistant(controlObject, 1 << (RelaySeparationHelper.getRelayNum(device) - 1)).inGroupByPublish(ActivityUtils.getTopActivity(), groupAddress, publishAddress, result);
                return;
            }
        }
        CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).inGroupByPublish(ActivityUtils.getTopActivity(), groupAddress, publishAddress, result);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void setIvIndex(int ivindex, IAction<Integer> iAction) {
        getMeshController().setIvindex(ivindex, iAction);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void outGroupByCmd(Object controlObject, int groupAddress, IAction<Boolean> result) {
        if (controlObject instanceof Device) {
            Device device = (Device) controlObject;
            if (RelaySeparationHelper.isSwitchRelay(device)) {
                int relayNum = RelaySeparationHelper.getRelayNum(device);
                LHomeLog.e("outGroupByCmd", getClass(), "num=" + relayNum);
                CmdAssistant.getSettingCmdAssistant(controlObject, 1 << (relayNum - 1)).outGroup(ActivityUtils.getTopActivity(), groupAddress, result);
                return;
            }
        }
        LHomeLog.e("outGroupByCmd", getClass(), "num=没有");
        CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).outGroup(ActivityUtils.getTopActivity(), groupAddress, result);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void resetNodeByCmd(Object controlObject, IAction<Boolean> result) {
        CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).resetNode(ActivityUtils.getTopActivity(), result);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void checkNearbyDevice(String networkUUID, LifecycleOwner owner) {
        startCheckTimer(networkUUID, owner);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void reCheckNearbyDevice(String networkUUID, LifecycleOwner owner) {
        if (!networkUUID.equals(this.currentNetworkUUID)) {
            stopCheckTimer();
        }
        if (hasMeshPermission()) {
            this.connectAgent.reScanAndConnect();
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void checkNearbyDevice(LifecycleOwner owner) {
        stopCheckTimer();
        if (hasMeshPermission()) {
            this.connectAgent.scanAndConnect();
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public void stopCheckNearbyDevice() {
        LHomeLog.i(getClass(), "stopCheckNearbyDevice: ");
        stopCheckTimer();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public boolean hasMeshPermission() {
        if (MyApplication.getContext() == null) {
            return false;
        }
        return Build.VERSION.SDK_INT >= 31 ? AndPermission.hasPermissions(MyApplication.getContext(), "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT") && Injection.state().isBluetoothEnabled() : AndPermission.hasPermissions(MyApplication.getContext(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION) && Injection.state().isBluetoothEnabled() && Injection.state().isLocationEnabled(MyApplication.getContext());
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager
    public boolean blueEnabledLocationDisenable() {
        if (MyApplication.getContext() == null) {
            return false;
        }
        boolean isLocationEnabled = Injection.state().isLocationEnabled(MyApplication.getContext());
        if (!Injection.state().isBluetoothEnabled() || isLocationEnabled) {
            return AndPermission.hasPermissions(MyApplication.getContext(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION) && Injection.state().isBluetoothEnabled() && Injection.state().isLocationEnabled(MyApplication.getContext());
        }
        return true;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public int getProvisionerAddress() {
        return getMeshController().getProvisionerAddress();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public BluetoothDevice getConnectedDevice() {
        return getMeshController().getConnectedDevice();
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public void startScan(UUID filterUuid, IScanCallback callback) {
        LHomeLog.i(getClass(), "startScan: " + filterUuid.toString());
        getScanManager().startScan(filterUuid, callback);
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public void stopScan() {
        getScanManager().stopScan();
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public boolean isScanning() {
        LHomeLog.i(getClass(), "isScanning: " + getScanManager().isScanning());
        return getScanManager().isScanning();
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public ExtendedBluetoothDevice getConnectableDevice() {
        return getScanManager().getConnectableDevice();
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public void clearConnectableDevice(ExtendedBluetoothDevice device) {
        getScanManager().clearConnectableDevice(device);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void connect(ExtendedBluetoothDevice device, IConnectBleCallback callback) {
        connect(device, callback, false);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager, com.ltech.smarthome.blemesh.IMeshController
    public void connect(ExtendedBluetoothDevice device, final IConnectBleCallback callback, boolean isAdd) {
        LHomeLog.i(getClass(), "start connect:" + device.getAddress());
        getMeshController().connect(device, new IConnectBleCallback(this) { // from class: com.ltech.smarthome.blemesh.MeshManager.1
            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice) {
                callback.onConnectSuccess(btModule, connectedDevice);
            }

            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectFail() {
                callback.onConnectFail();
            }
        }, isAdd);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void startProvisioning(ExtendedBluetoothDevice device, int address, String deviceName, IProvisioningCallback callback) {
        getMeshController().startProvisioning(device, address, deviceName, callback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void sendVendorModelMessage(final int address, final int opcode, final byte[] parameters, final boolean acknowledged, final ISendCallback callback) {
        checkConnect(new Runnable() { // from class: com.ltech.smarthome.blemesh.MeshManager.2
            @Override // java.lang.Runnable
            public void run() {
                MeshManager.this.getMeshController().sendVendorModelMessage(address, opcode, parameters, acknowledged, callback);
            }
        }, new Runnable(this) { // from class: com.ltech.smarthome.blemesh.MeshManager.3
            @Override // java.lang.Runnable
            public void run() {
                LHomeLog.e(Constants.MESH_LOG, getClass(), "sendFail:disconnect");
                callback.onSendFail();
            }
        });
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public int createGroup(int groupAddress, String groupName) {
        return getMeshController().createGroup(groupAddress, groupName);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public boolean delGroup(int groupAddress) {
        return getMeshController().delGroup(groupAddress);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inGroup$0(int i, int i2, IGroupCallback iGroupCallback) {
        getMeshController().inGroup(i, i2, iGroupCallback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void inGroup(final int nodeAddress, final int groupAddress, final IGroupCallback callback) {
        Runnable runnable = new Runnable() { // from class: com.ltech.smarthome.blemesh.MeshManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MeshManager.this.lambda$inGroup$0(nodeAddress, groupAddress, callback);
            }
        };
        Objects.requireNonNull(callback);
        checkConnect(runnable, new MeshManager$$ExternalSyntheticLambda4(callback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$outGroup$1(int i, int i2, IGroupCallback iGroupCallback) {
        getMeshController().outGroup(i, i2, iGroupCallback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void outGroup(final int nodeAddress, final int groupAddress, final IGroupCallback callback) {
        Runnable runnable = new Runnable() { // from class: com.ltech.smarthome.blemesh.MeshManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MeshManager.this.lambda$outGroup$1(nodeAddress, groupAddress, callback);
            }
        };
        Objects.requireNonNull(callback);
        checkConnect(runnable, new MeshManager$$ExternalSyntheticLambda4(callback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetNode$2(int i, IResetNodeCallback iResetNodeCallback) {
        getMeshController().resetNode(i, iResetNodeCallback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void resetNode(final int nodeAddress, final IResetNodeCallback callback) {
        Runnable runnable = new Runnable() { // from class: com.ltech.smarthome.blemesh.MeshManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MeshManager.this.lambda$resetNode$2(nodeAddress, callback);
            }
        };
        Objects.requireNonNull(callback);
        checkConnect(runnable, new Runnable() { // from class: com.ltech.smarthome.blemesh.MeshManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                IResetNodeCallback.this.resetFail();
            }
        });
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void removeNode(int nodeAddress, IRemoveNodeCallback callback) {
        getMeshController().removeNode(nodeAddress, callback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public boolean isConnected() {
        return getMeshController().isConnected();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void disconnect() {
        getMeshController().disconnect();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void clear() {
        getMeshController().clear();
    }

    private void checkConnect(Runnable allow, Runnable deny) {
        if (!isConnected()) {
            checkConnected(allow, deny);
        } else {
            allow.run();
        }
    }

    private boolean checkUseBle() {
        if (isConnected()) {
            LHomeLog.i(getClass(), "isConnected");
            return true;
        }
        if (hasMeshPermission()) {
            LHomeLog.i(getClass(), "hasPermission");
            if (getConnectableDevice() != null) {
                LHomeLog.i(getClass(), "has connectDevice");
                return true;
            }
        }
        if (Injection.state().isConnectOuterNet()) {
            return false;
        }
        LHomeLog.i(getClass(), "not ConnectNet");
        return true;
    }

    private void stopCheckTimer() {
        LHomeLog.i(getClass(), "stopCheckTimer: startScan");
        Disposable disposable = this.disposable;
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                this.disposable.dispose();
            }
            this.disposable = null;
        }
        this.connectAgent.stopScanAndConnect();
    }

    private void startCheckTimer(String networkUUID, LifecycleOwner owner) {
        if (networkUUID != null && !networkUUID.equals(this.currentNetworkUUID)) {
            stopCheckTimer();
        }
        if (hasMeshPermission()) {
            this.connectAgent.scanAndConnect();
        }
    }

    public void checkConnected(final Runnable success, final Runnable fail) {
        Observable.just(1).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Consumer() { // from class: com.ltech.smarthome.blemesh.MeshManager$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MeshManager.this.lambda$checkConnected$3(success, fail, (Integer) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.blemesh.MeshManager$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MeshManager.lambda$checkConnected$4(fail, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkConnected$3(Runnable runnable, Runnable runnable2, Integer num) throws Exception {
        this.connectAgent.checkConnected(runnable, runnable2);
    }

    static /* synthetic */ void lambda$checkConnected$4(Runnable runnable, Throwable th) throws Exception {
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshManager, com.ltech.smarthome.blemesh.IMeshController
    public void export() {
        final MeshManagerApi meshManagerApi = ((FeasyController) getMeshController()).getMeshManagerApi();
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (meshManagerApi.getMeshNetwork() != null) {
            String meshName = meshManagerApi.getMeshNetwork().getMeshName();
            LHomeLog.i(getClass(), "MeshJsonBean.meshName=" + meshName);
        }
        final File file = new File(absolutePath, "mesh" + System.currentTimeMillis() + ".txt");
        boolean hasPermissions = AndPermission.hasPermissions(ActivityUtils.getTopActivity(), Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
        LHomeLog.i(getClass(), "MeshJsonBean.hasLocationPermission=" + hasPermissions);
        if (!hasPermissions && Build.VERSION.SDK_INT >= 23) {
            ActivityUtils.getTopActivity().requestPermissions(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            new Thread(new Runnable(this) { // from class: com.ltech.smarthome.blemesh.MeshManager.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(meshManagerApi.exportMeshNetwork().getBytes());
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LHomeLog.i(getClass(), "MeshJsonBean export ok");
                }
            }).start();
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void observeTestSequence(ITestSequenceCallback callback) {
        getMeshController().observeTestSequence(callback);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public boolean isSeqSuccess() {
        return getMeshController().isSeqSuccess();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setUpgradeStart(boolean upgradeStart) {
        getMeshController().setUpgradeStart(upgradeStart);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setBleFwUpgradeStart(boolean upgradeStart) {
        getMeshController().setBleFwUpgradeStart(upgradeStart);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setIvindex(int ivindex, IAction<Integer> iAction) {
        getMeshController().setIvindex(ivindex, iAction);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setProvisioning(boolean upgradeStart) {
        getMeshController().setProvisioning(upgradeStart);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setNodeTtl(int ttl) {
        getMeshController().setNodeTtl(ttl);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setDefaultTtl(int address, int ttl) {
        getMeshController().setDefaultTtl(address, ttl);
    }
}