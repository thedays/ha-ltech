package com.ltech.smarthome.blemesh;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;

/* loaded from: classes3.dex */
public interface IMeshManager extends IBleScan, IMeshController {
    boolean blueEnabledLocationDisenable();

    void checkNearbyDevice(LifecycleOwner owner);

    void checkNearbyDevice(String networkUUID, LifecycleOwner owner);

    @Override // com.ltech.smarthome.blemesh.IMeshController
    void connect(ExtendedBluetoothDevice device, IConnectBleCallback callback, boolean isAdd);

    void deInit();

    @Override // com.ltech.smarthome.blemesh.IMeshController
    void export();

    boolean hasMeshPermission();

    void inGroupByCmd(Object controlObject, int groupAddress, int publishAddress, IAction<Boolean> result);

    void inGroupByCmd(Object controlObject, int groupAddress, IAction<Boolean> result);

    void inGroupByCmd(Object controlObject, int groupAddress, IAction<Boolean> result, int zone);

    void init();

    void outGroupByCmd(Object controlObject, int groupAddress, IAction<Boolean> result);

    void reCheckNearbyDevice(String networkUUID, LifecycleOwner owner);

    void resetNodeByCmd(Object controlObject, IAction<Boolean> result);

    void setIvIndex(int ivindex, IAction<Integer> iAction);

    void stopCheckNearbyDevice();

    boolean useBle();
}