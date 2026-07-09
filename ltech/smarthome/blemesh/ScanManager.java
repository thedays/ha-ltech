package com.ltech.smarthome.blemesh;

import android.bluetooth.BluetoothAdapter;
import android.os.ParcelUuid;
import android.util.ArrayMap;
import com.feasycom.feasymesh.library.MeshBeacon;
import com.feasycom.feasymesh.library.MeshManagerApi;
import com.feasycom.feasymesh.library.MeshNetwork;
import com.feasycom.feasymesh.library.UnprovisionedBeacon;
import com.feasycom.feasymesh.library.transport.ProvisionedMeshNode;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.utils.Constants;
import com.smart.message.utils.LHomeLog;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

/* loaded from: classes3.dex */
public class ScanManager implements IBleScan {
    private static final String TAG = "ScanManager";
    private ExtendedBluetoothDevice curProxyDevice;
    private volatile boolean isScanning;
    private UUID mFilterUuid;
    private MeshManagerApi mMeshManagerApi;
    private String mNetworkId;
    private IScanCallback mScanCallback;
    private ArrayMap<String, ExtendedBluetoothDevice> deviceMap = new ArrayMap<>();
    private final ScanCallback mScanCallbacks = new ScanCallback() { // from class: com.ltech.smarthome.blemesh.ScanManager.1
        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onScanResult(final int callbackType, final ScanResult result) {
            try {
                String name = result.getDevice().getName();
                if (BleMeshManager.MESH_PROVISIONING_UUID.equals(ScanManager.this.mFilterUuid)) {
                    byte[] meshBeaconData = ScanManager.this.mMeshManagerApi.getMeshBeaconData(result.getScanRecord().getBytes());
                    if (meshBeaconData != null) {
                        MeshBeacon meshBeacon = ScanManager.this.mMeshManagerApi.getMeshBeacon(meshBeaconData);
                        if (meshBeacon instanceof UnprovisionedBeacon) {
                            ExtendedBluetoothDevice extendedBluetoothDevice = new ExtendedBluetoothDevice(result, meshBeacon);
                            LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("设备未入网:\ndeviceName:%s\naddress:%s\nproductType:%s\nsubProductType:%s", name, result.getDevice().getAddress(), extendedBluetoothDevice.getProductType(), extendedBluetoothDevice.getSubProductType()));
                            LHomeLog.d(getClass(), "bluetoothDevice : " + extendedBluetoothDevice.toString());
                            ScanManager.this.updateScanData(extendedBluetoothDevice);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (BleMeshManager.MESH_PROXY_UUID.equals(ScanManager.this.mFilterUuid)) {
                    byte[] serviceData = BleMeshManager.getServiceData(result, BleMeshManager.MESH_PROXY_UUID);
                    if (ScanManager.this.mMeshManagerApi != null) {
                        if (ScanManager.this.mMeshManagerApi.isAdvertisingWithNetworkIdentity(serviceData)) {
                            if (ScanManager.this.mMeshManagerApi.networkIdMatches(ScanManager.this.mNetworkId, serviceData)) {
                                ExtendedBluetoothDevice extendedBluetoothDevice2 = new ExtendedBluetoothDevice(result);
                                LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("设备正常入网:\ndeviceName:%s\naddress:%s\nproductType:%s\nsubProductType:%s", name, result.getDevice().getAddress(), extendedBluetoothDevice2.getProductType(), extendedBluetoothDevice2.getSubProductType()));
                                ScanManager.this.curProxyDevice = extendedBluetoothDevice2;
                                ScanManager scanManager = ScanManager.this;
                                scanManager.refreshConnectMap(scanManager.curProxyDevice);
                                ScanManager.this.updateScanData(extendedBluetoothDevice2);
                                return;
                            }
                            return;
                        }
                        if (ScanManager.this.mMeshManagerApi.isAdvertisedWithNodeIdentity(serviceData)) {
                            if (ScanManager.this.checkIfNodeIdentityMatches(serviceData)) {
                                ExtendedBluetoothDevice extendedBluetoothDevice3 = new ExtendedBluetoothDevice(result);
                                LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("设备正常入网:\ndeviceName:%s\naddress:%s\nproductType:%s\nsubProductType:%s", name, result.getDevice().getAddress(), extendedBluetoothDevice3.getProductType(), extendedBluetoothDevice3.getSubProductType()));
                                ScanManager.this.curProxyDevice = extendedBluetoothDevice3;
                                ScanManager scanManager2 = ScanManager.this;
                                scanManager2.refreshConnectMap(scanManager2.curProxyDevice);
                                ScanManager.this.updateScanData(extendedBluetoothDevice3);
                                return;
                            }
                            ExtendedBluetoothDevice extendedBluetoothDevice4 = new ExtendedBluetoothDevice(result);
                            LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("设备网内但是异常:\ndeviceName:%s\naddress:%s\nproductType:%s\nproductType:%s", name, extendedBluetoothDevice4.getAddress(), extendedBluetoothDevice4.getProductType(), extendedBluetoothDevice4.getSubProductType()));
                        }
                    }
                }
            } catch (Exception e) {
                LHomeLog.i(getClass(), "Error: " + e.getMessage());
            }
        }

        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onBatchScanResults(List<ScanResult> results) {
            LHomeLog.i(getClass(), "onBatchScanResults.size: " + results.size());
            super.onBatchScanResults(results);
        }

        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            ScanManager.this.reSetBle(errorCode);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void reSetBle(int errorCode) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        LHomeLog.i(getClass(), "onScanFailed.errorCode: " + errorCode + "___bluetoothAdapter=" + defaultAdapter);
        releaseAllScanClient();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshConnectMap(final ExtendedBluetoothDevice bluetoothDevice) {
        MeshNetwork meshNetwork = this.mMeshManagerApi.getMeshNetwork();
        if (meshNetwork != null) {
            this.deviceMap.put(meshNetwork.getMeshUUID(), bluetoothDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScanData(final ExtendedBluetoothDevice bluetoothDevice) {
        IScanCallback iScanCallback = this.mScanCallback;
        if (iScanCallback != null) {
            iScanCallback.onScanResult(bluetoothDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkIfNodeIdentityMatches(final byte[] serviceData) {
        MeshNetwork meshNetwork = this.mMeshManagerApi.getMeshNetwork();
        if (meshNetwork == null) {
            return false;
        }
        Iterator it = meshNetwork.getNodes().iterator();
        while (it.hasNext()) {
            if (this.mMeshManagerApi.nodeIdentityMatches((ProvisionedMeshNode) it.next(), serviceData)) {
                return true;
            }
        }
        return false;
    }

    ScanManager(final MeshManagerApi meshManagerApi) {
        this.mMeshManagerApi = meshManagerApi;
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public synchronized void startScan(UUID filterUuid, IScanCallback callback) {
        MeshNetwork meshNetwork;
        if (this.isScanning) {
            return;
        }
        this.isScanning = true;
        this.mFilterUuid = filterUuid;
        this.mScanCallback = callback;
        if (filterUuid.equals(BleMeshManager.MESH_PROXY_UUID) && (meshNetwork = this.mMeshManagerApi.getMeshNetwork()) != null && !meshNetwork.getNetKeys().isEmpty()) {
            this.mNetworkId = this.mMeshManagerApi.generateNetworkId(meshNetwork.getNetKeys().get(0).getKey());
        }
        ScanSettings build = new ScanSettings.Builder().setScanMode(2).setReportDelay(0L).setUseHardwareFilteringIfSupported(false).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(filterUuid)).build());
        if (BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner() == null) {
            return;
        }
        LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("startScan\nfilterUuid:%s", filterUuid));
        BluetoothLeScannerCompat.getScanner().startScan(arrayList, build, this.mScanCallbacks);
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public synchronized void stopScan() {
        if (this.isScanning) {
            LHomeLog.e(Constants.MESH_LOG, getClass(), "stopScan");
            BluetoothLeScannerCompat.getScanner().stopScan(this.mScanCallbacks);
            if (BleMeshManager.MESH_PROXY_UUID.equals(this.mFilterUuid)) {
                refreshConnectMap(this.curProxyDevice);
                this.curProxyDevice = null;
            }
            this.isScanning = false;
            this.mScanCallback = null;
        }
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public boolean isScanning() {
        return this.isScanning;
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public ExtendedBluetoothDevice getConnectableDevice() {
        LHomeLog.i(getClass(), "mesh network: " + this.mMeshManagerApi.getMeshNetwork() + "|| deviceMap: " + this.deviceMap.size());
        if (this.mMeshManagerApi.getMeshNetwork() != null) {
            return this.deviceMap.get(this.mMeshManagerApi.getMeshNetwork().getMeshUUID());
        }
        return null;
    }

    @Override // com.ltech.smarthome.blemesh.IBleScan
    public void clearConnectableDevice(ExtendedBluetoothDevice device) {
        ExtendedBluetoothDevice extendedBluetoothDevice;
        if (this.mMeshManagerApi.getMeshNetwork() == null || (extendedBluetoothDevice = this.deviceMap.get(this.mMeshManagerApi.getMeshNetwork().getMeshUUID())) == null || !extendedBluetoothDevice.equals(device)) {
            return;
        }
        this.deviceMap.remove(this.mMeshManagerApi.getMeshNetwork().getMeshUUID());
    }

    public static boolean releaseAllScanClient() {
        Object iBluetoothGatt;
        Method declaredMethod;
        boolean z;
        try {
            Object iBluetoothManager = getIBluetoothManager(BluetoothAdapter.getDefaultAdapter());
            if (iBluetoothManager == null || (iBluetoothGatt = getIBluetoothGatt(iBluetoothManager)) == null) {
                return false;
            }
            Method declaredMethod2 = getDeclaredMethod(iBluetoothGatt, "unregisterClient", (Class<?>[]) new Class[]{Integer.TYPE});
            try {
                declaredMethod = getDeclaredMethod(iBluetoothGatt, "stopScan", (Class<?>[]) new Class[]{Integer.TYPE, Boolean.TYPE});
                z = false;
            } catch (Exception unused) {
                declaredMethod = getDeclaredMethod(iBluetoothGatt, "stopScan", (Class<?>[]) new Class[]{Integer.TYPE});
                z = true;
            }
            for (int i = 0; i <= 40; i++) {
                if (!z) {
                    try {
                        declaredMethod.invoke(iBluetoothGatt, Integer.valueOf(i), false);
                    } catch (Exception unused2) {
                    }
                }
                if (z) {
                    try {
                        declaredMethod.invoke(iBluetoothGatt, Integer.valueOf(i));
                    } catch (Exception unused3) {
                    }
                }
                try {
                    declaredMethod2.invoke(iBluetoothGatt, Integer.valueOf(i));
                } catch (Exception unused4) {
                }
            }
            declaredMethod.setAccessible(false);
            declaredMethod2.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object getIBluetoothGatt(Object mIBluetoothManager) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method declaredMethod = getDeclaredMethod(mIBluetoothManager, "getBluetoothGatt", (Class<?>[]) new Class[0]);
        Object obj = new Object();
        try {
            return declaredMethod.invoke(mIBluetoothManager, null);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return obj;
        }
    }

    public static Object getIBluetoothManager(BluetoothAdapter adapter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return getDeclaredMethod((Class<?>) BluetoothAdapter.class, "getBluetoothManager", (Class<?>[]) new Class[0]).invoke(adapter, null);
    }

    public static Field getDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException {
        Field declaredField = clazz.getDeclaredField(name);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method declaredMethod = clazz.getDeclaredMethod(name, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    public static Field getDeclaredField(Object obj, String name) throws NoSuchFieldException {
        Field declaredField = obj.getClass().getDeclaredField(name);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static Method getDeclaredMethod(Object obj, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method declaredMethod = obj.getClass().getDeclaredMethod(name, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}