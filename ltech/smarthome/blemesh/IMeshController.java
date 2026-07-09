package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothDevice;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;

/* loaded from: classes3.dex */
public interface IMeshController {
    void clear();

    void clearSharedPreferencesByMeshUUID();

    void connect(ExtendedBluetoothDevice device, IConnectBleCallback callback);

    void connect(ExtendedBluetoothDevice device, IConnectBleCallback callback, boolean isAdd);

    int createGroup(int groupAddress, String groupName);

    boolean delGroup(int groupAddress);

    void disconnect();

    void export();

    String exportMeshNetwork();

    BluetoothDevice getConnectedDevice();

    int getProvisionerAddress();

    void inGroup(int nodeAddress, int groupAddress, IGroupCallback callback);

    boolean isConnected();

    boolean isSeqSuccess();

    void observeTestSequence(ITestSequenceCallback callback);

    void outGroup(int nodeAddress, int groupAddress, IGroupCallback callback);

    void refreshNetwork(String networkJson, IRefreshNetworkCallback callback);

    void removeNode(int nodeAddress, IRemoveNodeCallback callback);

    void resetNode(int nodeAddress, IResetNodeCallback callback);

    void sendVendorModelMessage(int address, final int opcode, final byte[] parameters, final boolean acknowledged, ISendCallback callback);

    void setBleFwUpgradeStart(boolean upgradeStart);

    void setDefaultTtl(int address, int ttl);

    void setIvindex(int ivindex, IAction<Integer> iAction);

    void setNodeTtl(int ttl);

    void setProvisioning(boolean upgradeStart);

    void setUpgradeStart(boolean upgradeStart);

    void startProvisioning(final ExtendedBluetoothDevice device, int address, String deviceName, IProvisioningCallback callback);
}