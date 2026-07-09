package com.ltech.smarthome.blemesh.feasy;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.feasycom.feasymesh.library.ApplicationKey;
import com.feasycom.feasymesh.library.Group;
import com.feasycom.feasymesh.library.MeshManagerApi;
import com.feasycom.feasymesh.library.MeshManagerCallbacks;
import com.feasycom.feasymesh.library.MeshNetwork;
import com.feasycom.feasymesh.library.MeshProvisioningStatusCallbacks;
import com.feasycom.feasymesh.library.MeshStatusCallbacks;
import com.feasycom.feasymesh.library.Provisioner;
import com.feasycom.feasymesh.library.TestSeqCallbacks;
import com.feasycom.feasymesh.library.UnprovisionedBeacon;
import com.feasycom.feasymesh.library.ble.BleManagerCallbacks;
import com.feasycom.feasymesh.library.ble.callback.FailCallback;
import com.feasycom.feasymesh.library.ble.callback.InvalidRequestCallback;
import com.feasycom.feasymesh.library.models.VendorModel;
import com.feasycom.feasymesh.library.provisionerstates.ProvisioningCapabilities;
import com.feasycom.feasymesh.library.provisionerstates.ProvisioningState;
import com.feasycom.feasymesh.library.provisionerstates.UnprovisionedMeshNode;
import com.feasycom.feasymesh.library.transport.ConfigAppKeyAdd;
import com.feasycom.feasymesh.library.transport.ConfigAppKeyStatus;
import com.feasycom.feasymesh.library.transport.ConfigCompositionDataStatus;
import com.feasycom.feasymesh.library.transport.ConfigDefaultTtlGet;
import com.feasycom.feasymesh.library.transport.ConfigDefaultTtlSet;
import com.feasycom.feasymesh.library.transport.ConfigDefaultTtlStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelAppBind;
import com.feasycom.feasymesh.library.transport.ConfigModelAppStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelPublicationStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelSubscriptionAdd;
import com.feasycom.feasymesh.library.transport.ConfigModelSubscriptionDelete;
import com.feasycom.feasymesh.library.transport.ConfigModelSubscriptionStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelSubscriptionVirtualAddressAdd;
import com.feasycom.feasymesh.library.transport.ConfigModelSubscriptionVirtualAddressDelete;
import com.feasycom.feasymesh.library.transport.ConfigNetworkTransmitSet;
import com.feasycom.feasymesh.library.transport.ConfigNetworkTransmitStatus;
import com.feasycom.feasymesh.library.transport.ConfigNodeReset;
import com.feasycom.feasymesh.library.transport.ConfigNodeResetStatus;
import com.feasycom.feasymesh.library.transport.ConfigProxyStatus;
import com.feasycom.feasymesh.library.transport.ConfigRelayStatus;
import com.feasycom.feasymesh.library.transport.ControlMessage;
import com.feasycom.feasymesh.library.transport.Element;
import com.feasycom.feasymesh.library.transport.GenericLevelStatus;
import com.feasycom.feasymesh.library.transport.GenericOnOffStatus;
import com.feasycom.feasymesh.library.transport.MeshMessage;
import com.feasycom.feasymesh.library.transport.MeshModel;
import com.feasycom.feasymesh.library.transport.ProvisionedMeshNode;
import com.feasycom.feasymesh.library.transport.ProxyConfigFilterStatus;
import com.feasycom.feasymesh.library.transport.VendorModelMessageAcked;
import com.feasycom.feasymesh.library.transport.VendorModelMessageStatus;
import com.feasycom.feasymesh.library.transport.VendorModelMessageUnacked;
import com.feasycom.feasymesh.library.utils.AuthenticationOOBMethods;
import com.feasycom.feasymesh.library.utils.MeshAddress;
import com.feasycom.feasymesh.library.utils.MeshParserUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.blemesh.BleMeshManagerCallbacks;
import com.ltech.smarthome.blemesh.IConnectBleCallback;
import com.ltech.smarthome.blemesh.IGroupCallback;
import com.ltech.smarthome.blemesh.IMeshController;
import com.ltech.smarthome.blemesh.IProvisioningCallback;
import com.ltech.smarthome.blemesh.IRefreshNetworkCallback;
import com.ltech.smarthome.blemesh.IRemoveNodeCallback;
import com.ltech.smarthome.blemesh.IResetNodeCallback;
import com.ltech.smarthome.blemesh.ISendCallback;
import com.ltech.smarthome.blemesh.ITestSequenceCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.RecPackage;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

/* loaded from: classes3.dex */
public class FeasyController implements IMeshController, BleMeshManagerCallbacks, MeshManagerCallbacks, MeshProvisioningStatusCallbacks, MeshStatusCallbacks {
    private static final int ATTENTION_TIMER = 5;
    private static final int MESSAGE_DEVICE_CONNECT_WAIT = 5;
    private static final int MESSAGE_PROVISIONING_COMPLETED = 2;
    private static final int MESSAGE_PROVISIONING_TEST_SEQ = 6;
    private static final int MESSAGE_PROVISIONING_TEST_SEQ_TIMEOUT = 7;
    private static final int MESSAGE_RESET_NODE_TIMEOUT = 4;
    private static final int MESSAGE_SCAN_TIMEOUT = 1;
    private static final int MESSAGE_SET_GROUP_TIMEOUT = 3;
    private static final String TAG = "FeasyController";
    private String btModule;
    private int cloudIvIndex;
    private IAction<Integer> cloudIvIndexUpdataAcion;
    private BluetoothDevice connectedDevice;
    private final BleMeshManager mBleMeshManager;
    private Handler mHandler;
    private IConnectBleCallback mIConnectCallback;
    private IGroupCallback mIGroupCallback;
    private IProvisioningCallback mIProvisioningCallback;
    private IRefreshNetworkCallback mIRefreshNetworkCallback;
    private IResetNodeCallback mIResetNodeCallback;
    private ITestSequenceCallback mITestSequenceCallback;
    private boolean mIsReconnectingFlag;
    private boolean mIsScanning;
    private final MeshManagerApi mMeshManagerApi;
    private MeshNetwork mMeshNetwork;
    private BluetoothDevice mNeedConnectDevice;
    private ProvisionedMeshNode mProvisionedMeshNode;
    private ExtendedBluetoothDevice mProvisioningDevice;
    private boolean mSetupProvisionedNode;
    private int nodeAddress;
    private String nodeName;
    private int unicastAddress;
    private boolean seqSuccess = false;
    private boolean needDelay = false;
    private boolean upgradeStart = false;
    private boolean bleFwUpgradeStart = false;
    private int action = 0;
    private final ScanCallback scanCallback = new ScanCallback() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController.3
        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onBatchScanResults(final List<ScanResult> results) {
        }

        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onScanResult(final int callbackType, final ScanResult result) {
            LHomeLog.d(Constants.MESH_LOG, getClass(), "onScanResult: " + result.getDevice().getAddress());
            ScanRecord scanRecord = result.getScanRecord();
            if (scanRecord != null) {
                LHomeLog.d(Constants.MESH_LOG, getClass(), "onScanResult: scanRecord" + scanRecord.toString());
                byte[] serviceData = BleMeshManager.getServiceData(result, BleMeshManager.MESH_PROXY_UUID);
                if (serviceData != null) {
                    LHomeLog.d(Constants.MESH_LOG, getClass(), "onScanResult: serviceData" + serviceData.toString());
                    if (FeasyController.this.mMeshManagerApi.isAdvertisedWithNodeIdentity(serviceData)) {
                        LHomeLog.d(Constants.MESH_LOG, getClass(), "isAdvertisedWithNodeIdentity");
                        ProvisionedMeshNode provisionedMeshNode = FeasyController.this.mProvisionedMeshNode;
                        if (FeasyController.this.mMeshManagerApi.nodeIdentityMatches(provisionedMeshNode, serviceData)) {
                            LHomeLog.d(Constants.MESH_LOG, getClass(), "nodeIdentityMatches");
                            FeasyController.this.stopScan();
                            FeasyController.this.onProvisionedDeviceFound(provisionedMeshNode, new ExtendedBluetoothDevice(result));
                        }
                    }
                }
            }
        }

        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onScanFailed(final int errorCode) {
            FeasyController.this.setResult(false, "scan failed:" + errorCode);
        }
    };
    private int connectReTryTime = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MeshAction {
        public static final int NONE = 0;
        public static final int STATE_CONFIG_DATA = 5;
        public static final int STATE_CONNECT = 1;
        public static final int STATE_CONNECT_WAIT = 6;
        public static final int STATE_GROUP = 3;
        public static final int STATE_PROVISIONING = 2;
        public static final int STATE_RESET_NODE = 4;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void export() {
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public /* synthetic */ void onBatteryValueReceived(BluetoothDevice bluetoothDevice, int i) {
        BleManagerCallbacks.CC.$default$onBatteryValueReceived(this, bluetoothDevice, i);
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public /* synthetic */ boolean shouldEnableBatteryLevelNotifications(BluetoothDevice bluetoothDevice) {
        return BleManagerCallbacks.CC.$default$shouldEnableBatteryLevelNotifications(this, bluetoothDevice);
    }

    public FeasyController(MeshManagerApi meshManagerApi, BleMeshManager bleMeshManager) {
        this.mMeshManagerApi = meshManagerApi;
        meshManagerApi.setMeshManagerCallbacks(this);
        meshManagerApi.setProvisioningStatusCallbacks(this);
        meshManagerApi.setMeshStatusCallbacks(this);
        meshManagerApi.loadMeshNetwork();
        this.mBleMeshManager = bleMeshManager;
        bleMeshManager.setGattCallbacks(this);
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        FeasyController.this.setResult(false, "scan timeout");
                        FeasyController.this.stopScan();
                        break;
                    case 2:
                        FeasyController.this.startScan();
                        break;
                    case 3:
                    case 4:
                        FeasyController.this.setResult(false, new Object[0]);
                        break;
                    case 5:
                        if (FeasyController.this.needDelay) {
                            Message message = new Message();
                            message.what = msg.what;
                            message.obj = msg.obj;
                            sendMessageDelayed(message, 2000L);
                            break;
                        } else {
                            FeasyController.this.action = 1;
                            if (((Boolean) msg.obj).booleanValue()) {
                                FeasyController.this.setProvisioning(true);
                            }
                            FeasyController feasyController = FeasyController.this;
                            feasyController.connectDevice(feasyController.mNeedConnectDevice);
                            break;
                        }
                    case 6:
                        if (FeasyController.this.isSeqSuccess()) {
                            FeasyController.this.setResult(true, new Object[0]);
                            break;
                        } else {
                            sendEmptyMessageDelayed(6, 500L);
                            break;
                        }
                    case 7:
                        FeasyController.this.setResult(false, "");
                        break;
                }
            }
        };
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void observeTestSequence(ITestSequenceCallback callback) {
        this.mITestSequenceCallback = callback;
    }

    private void testSequence(int address) {
        LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("TestSequence:start address=%s", StringUtils.demToHex(address)));
        this.mMeshManagerApi.testSequence(address, new TestSeqCallbacks() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController.2
            @Override // com.feasycom.feasymesh.library.TestSeqCallbacks
            public void success() {
                FeasyController.this.seqSuccess = true;
                LHomeLog.e(Constants.MESH_LOG, getClass(), "TestSequence:success");
                if (FeasyController.this.action != 2 && FeasyController.this.action != 5 && FeasyController.this.mITestSequenceCallback != null && !FeasyController.this.upgradeStart) {
                    FeasyController.this.mITestSequenceCallback.onTestSuccess();
                }
                if (FeasyController.this.cloudIvIndex >= FeasyController.this.mMeshNetwork.getIvIndex().getIvIndex()) {
                    FeasyController.this.mMeshManagerApi.setIvIndex(FeasyController.this.cloudIvIndex, true);
                } else {
                    if (FeasyController.this.cloudIvIndexUpdataAcion == null || FeasyController.this.mMeshNetwork.getIvIndex().getIvIndex() - FeasyController.this.cloudIvIndex >= 42) {
                        return;
                    }
                    FeasyController.this.cloudIvIndexUpdataAcion.act(Integer.valueOf(FeasyController.this.mMeshNetwork.getIvIndex().getIvIndex()));
                }
            }

            @Override // com.feasycom.feasymesh.library.TestSeqCallbacks
            public void failure() {
                FeasyController.this.seqSuccess = false;
                LHomeLog.e(Constants.MESH_LOG, getClass(), "TestSequence:failure");
                if (FeasyController.this.action == 2 || FeasyController.this.action == 5 || FeasyController.this.mITestSequenceCallback == null || FeasyController.this.upgradeStart) {
                    return;
                }
                FeasyController.this.mITestSequenceCallback.onTestFail();
            }

            @Override // com.feasycom.feasymesh.library.TestSeqCallbacks
            public void nodeIsNull() {
                LHomeLog.e(Constants.MESH_LOG, getClass(), "TestSequence:nodeIsNull");
                FeasyController.this.seqSuccess = false;
                if (FeasyController.this.action == 2 || FeasyController.this.action == 5 || FeasyController.this.mITestSequenceCallback == null || FeasyController.this.upgradeStart) {
                    return;
                }
                FeasyController.this.mITestSequenceCallback.onTestFail();
            }

            @Override // com.feasycom.feasymesh.library.TestSeqCallbacks
            public void notReceiveMeshBeaconMessage() {
                LHomeLog.e(Constants.MESH_LOG, getClass(), "TestSequence:notReceiveMeshBeaconMessage");
                FeasyController.this.seqSuccess = false;
                if (FeasyController.this.action == 2 || FeasyController.this.action == 5 || FeasyController.this.mITestSequenceCallback == null || FeasyController.this.upgradeStart) {
                    return;
                }
                FeasyController.this.mITestSequenceCallback.onTestFail();
            }
        });
    }

    private void stopTestSequence(int status) {
        try {
            LHomeLog.e(Constants.MESH_LOG, getClass(), "TestSequence:stop status=" + status);
            ITestSequenceCallback iTestSequenceCallback = this.mITestSequenceCallback;
            if (iTestSequenceCallback != null) {
                iTestSequenceCallback.stopTestSequence();
            }
            this.mMeshManagerApi.stopTestSequence(status);
        } catch (Exception unused) {
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void refreshNetwork(String networkJson, IRefreshNetworkCallback callback) {
        if (TextUtils.isEmpty(networkJson)) {
            callback.onRefreshFail();
            return;
        }
        this.mIRefreshNetworkCallback = callback;
        LHomeLog.i(Constants.MESH_LOG, getClass(), "start importMeshNetworkJson=" + networkJson);
        this.mMeshManagerApi.importMeshNetworkJson(networkJson);
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onModuleModel(String s) {
        LHomeLog.e(Constants.MESH_LOG, getClass(), "onModuleModel:" + s);
        this.btModule = s;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public String exportMeshNetwork() {
        return this.mMeshManagerApi.exportMeshNetwork();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void clearSharedPreferencesByMeshUUID() {
        this.mMeshManagerApi.clearSharedPreferencesByMeshUUID();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void connect(ExtendedBluetoothDevice device, IConnectBleCallback callback) {
        connect(device, callback, false);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void connect(ExtendedBluetoothDevice device, IConnectBleCallback callback, boolean isAdd) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================connect====================");
        this.needDelay = false;
        this.mNeedConnectDevice = device.getDevice();
        clear();
        if (isConnected()) {
            this.needDelay = true;
            disconnect();
        }
        this.mIConnectCallback = callback;
        this.action = 6;
        Message message = new Message();
        message.obj = Boolean.valueOf(isAdd);
        message.what = 5;
        this.mHandler.sendMessage(message);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void startProvisioning(ExtendedBluetoothDevice device, int address, String deviceName, IProvisioningCallback callback) {
        this.mProvisioningDevice = device;
        LHomeLog.i(Constants.MESH_LOG, getClass(), "====startProvisioning=====");
        clear();
        this.action = 2;
        this.mIProvisioningCallback = callback;
        this.unicastAddress = address;
        this.nodeName = deviceName;
        UnprovisionedBeacon unprovisionedBeacon = (UnprovisionedBeacon) device.getBeacon();
        LHomeLog.i(Constants.MESH_LOG, getClass(), "startProvisioning deviceMac" + this.mProvisioningDevice.getAddress());
        if (unprovisionedBeacon != null) {
            this.mMeshManagerApi.identifyNode(unprovisionedBeacon.getUuid(), 5);
            return;
        }
        byte[] serviceData = BleMeshManager.getServiceData(device.getScanResult(), BleMeshManager.MESH_PROVISIONING_UUID);
        LHomeLog.d(Constants.MESH_LOG, getClass(), "startProvisioning: ");
        if (serviceData != null) {
            this.mMeshManagerApi.identifyNode(this.mMeshManagerApi.getDeviceUuid(serviceData), 5);
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void sendVendorModelMessage(int address, final int opcode, final byte[] parameters, final boolean acknowledged, ISendCallback callback) {
        int i;
        byte[] bArr;
        MeshMessage vendorModelMessageUnacked;
        ApplicationKey applicationKey = this.mMeshNetwork.getAppKeys().get(0);
        MeshParserUtils.toByteArray("000103");
        if (acknowledged) {
            i = opcode;
            bArr = parameters;
            vendorModelMessageUnacked = new VendorModelMessageAcked(applicationKey, 286331153, 4369, i, bArr);
        } else {
            i = opcode;
            bArr = parameters;
            vendorModelMessageUnacked = new VendorModelMessageUnacked(applicationKey, 286331153, 4369, i, bArr);
        }
        if (sendMessage(address, vendorModelMessageUnacked)) {
            if (callback != null) {
                LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("sendSuccess:\naddress=%s\nopcode=%s\ndata=%s", StringUtils.demToHex(address), StringUtils.demToHex(i), StringUtils.btye2Str3(bArr)));
                callback.onSendSuccess();
                return;
            }
            return;
        }
        if (callback != null) {
            LHomeLog.e(Constants.MESH_LOG, getClass(), String.format("sendFail:\naddress=%s\nopcode=%s\ndata=%s", Integer.valueOf(address), StringUtils.demToHex(i), StringUtils.btye2Str3(bArr)));
            callback.onSendFail();
        }
    }

    private boolean sendMessage(int address, MeshMessage message) {
        try {
            this.mMeshManagerApi.createMeshPdu(address, message);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public int createGroup(int groupAddress, final String name) {
        if (!MeshAddress.isValidSubscriptionAddress(groupAddress)) {
            return -1;
        }
        MeshNetwork meshNetwork = this.mMeshNetwork;
        Group createGroup = meshNetwork.createGroup(meshNetwork.getSelectedProvisioner(), groupAddress, name);
        if (this.mMeshNetwork.addGroup(createGroup)) {
            return createGroup.getAddress();
        }
        return -1;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public boolean delGroup(int groupAddress) {
        Group group = this.mMeshNetwork.getGroup(groupAddress);
        if (group == null || this.mMeshNetwork.getModels(group).size() != 0) {
            return false;
        }
        return this.mMeshNetwork.removeGroup(group);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void inGroup(int nodeAddress, int groupAddress, IGroupCallback callback) {
        MeshMessage configModelSubscriptionVirtualAddressAdd;
        LHomeLog.i(Constants.MESH_LOG, getClass(), "inGroup enter");
        this.action = 3;
        this.mIGroupCallback = callback;
        Group group = this.mMeshNetwork.getGroup(groupAddress);
        for (Element element : this.mMeshNetwork.getNode(nodeAddress).getElements().values()) {
            for (MeshModel meshModel : element.getMeshModels().values()) {
                if (meshModel instanceof VendorModel) {
                    int modelId = ((VendorModel) meshModel).getModelId();
                    if (group.getAddressLabel() == null) {
                        configModelSubscriptionVirtualAddressAdd = new ConfigModelSubscriptionAdd(element.getElementAddress(), group.getAddress(), modelId);
                    } else {
                        configModelSubscriptionVirtualAddressAdd = new ConfigModelSubscriptionVirtualAddressAdd(element.getElementAddress(), group.getAddressLabel(), modelId);
                    }
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "sendMessage1");
                    sendMessage(nodeAddress, configModelSubscriptionVirtualAddressAdd);
                    return;
                }
            }
        }
        this.mHandler.sendEmptyMessageDelayed(3, 5000L);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void outGroup(int nodeAddress, int groupAddress, IGroupCallback callback) {
        MeshMessage configModelSubscriptionVirtualAddressDelete;
        LHomeLog.i(Constants.MESH_LOG, getClass(), "outGroup enter");
        this.action = 3;
        this.mIGroupCallback = callback;
        for (Element element : this.mMeshNetwork.getNode(nodeAddress).getElements().values()) {
            for (MeshModel meshModel : element.getMeshModels().values()) {
                if (meshModel instanceof VendorModel) {
                    VendorModel vendorModel = (VendorModel) meshModel;
                    if (MeshAddress.isValidGroupAddress(groupAddress)) {
                        configModelSubscriptionVirtualAddressDelete = new ConfigModelSubscriptionDelete(element.getElementAddress(), groupAddress, vendorModel.getModelId());
                    } else {
                        UUID labelUUID = vendorModel.getLabelUUID(groupAddress);
                        configModelSubscriptionVirtualAddressDelete = labelUUID != null ? new ConfigModelSubscriptionVirtualAddressDelete(element.getElementAddress(), labelUUID, vendorModel.getModelId()) : null;
                    }
                    if (configModelSubscriptionVirtualAddressDelete != null) {
                        LHomeLog.i(Constants.MESH_LOG, getClass(), "sendMessage2");
                        sendMessage(nodeAddress, configModelSubscriptionVirtualAddressDelete);
                        return;
                    }
                    return;
                }
            }
        }
        this.mHandler.sendEmptyMessageDelayed(3, 5000L);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void resetNode(int nodeAddress, IResetNodeCallback callback) {
        this.action = 4;
        this.mIResetNodeCallback = callback;
        this.nodeAddress = nodeAddress;
        if (this.connectedDevice != null) {
            sendMessage(nodeAddress, new ConfigNodeReset());
            this.mHandler.sendEmptyMessageDelayed(4, 5000L);
        } else {
            connectDevice(this.mNeedConnectDevice);
            this.mHandler.sendEmptyMessageDelayed(4, 10000L);
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void removeNode(int nodeAddress, IRemoveNodeCallback callback) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "removeNode enter");
        MeshNetwork meshNetwork = this.mMeshNetwork;
        if (meshNetwork != null) {
            ProvisionedMeshNode node = meshNetwork.getNode(nodeAddress);
            if (node == null) {
                if (callback != null) {
                    callback.removeSuccess();
                    return;
                }
                return;
            }
            LHomeLog.i(Constants.MESH_LOG, getClass(), "meshNode != null");
            boolean deleteNode = this.mMeshNetwork.deleteNode(node);
            LHomeLog.i(Constants.MESH_LOG, getClass(), "deleteNode result -->" + deleteNode);
            if (callback != null) {
                if (deleteNode) {
                    callback.removeSuccess();
                } else {
                    callback.removeFail();
                }
            }
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public boolean isConnected() {
        return this.mBleMeshManager.isDeviceReady();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void disconnect() {
        LHomeLog.e(Constants.MESH_LOG, getClass(), "BLE==>调用断开连接");
        this.mBleMeshManager.disconnect().enqueue();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void clear() {
        this.action = 0;
        stopScan();
        this.mIProvisioningCallback = null;
        this.mIGroupCallback = null;
        this.mIConnectCallback = null;
        this.mIsReconnectingFlag = false;
        this.mSetupProvisionedNode = false;
        this.mProvisionedMeshNode = null;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public int getProvisionerAddress() {
        MeshNetwork meshNetwork = this.mMeshNetwork;
        if (meshNetwork == null || meshNetwork.getSelectedProvisioner() == null) {
            return 0;
        }
        return this.mMeshNetwork.getSelectedProvisioner().getProvisionerAddress().intValue();
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public BluetoothDevice getConnectedDevice() {
        return this.connectedDevice;
    }

    @Override // com.ltech.smarthome.blemesh.BleMeshManagerCallbacks
    public void onDataReceived(BluetoothDevice bluetoothDevice, int mtu, byte[] pdu) {
        if (pdu != null) {
            try {
                if (pdu.length > 0) {
                    this.mMeshManagerApi.handleNotifications(mtu, pdu);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.ltech.smarthome.blemesh.BleMeshManagerCallbacks
    public void onDataSent(BluetoothDevice device, int mtu, byte[] pdu) {
        this.mMeshManagerApi.handleWriteCallbacks(mtu, pdu);
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onDeviceConnecting(BluetoothDevice device) {
        LHomeLog.e(Constants.MESH_LOG, getClass(), "onDeviceConnecting：\ndeviceName:" + device.getName() + "\nmacAddress:" + device.getAddress());
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onDeviceConnected(BluetoothDevice device) {
        LHomeLog.e(Constants.MESH_LOG, getClass(), "onDeviceConnected：\ndeviceName:" + device.getName() + "\nmacAddress:" + device.getAddress());
        this.connectedDevice = device;
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onDeviceDisconnecting(BluetoothDevice device) {
        LHomeLog.e(Constants.MESH_LOG, getClass(), "onDeviceDisconnecting：\ndeviceName:" + device.getName() + "\nmacAddress:" + device.getAddress());
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onDeviceDisconnected(BluetoothDevice device) {
        this.needDelay = false;
        this.seqSuccess = false;
        LHomeLog.e(Constants.MESH_LOG, getClass(), "onDeviceDisconnected：\ndeviceName:" + device.getName() + "\nmacAddress:" + device.getAddress());
        this.connectedDevice = null;
        stopTestSequence(1);
        if (5 == this.action && this.mBleMeshManager.isProvisioningComplete()) {
            setResult(false, "Device disconnected");
        }
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onLinkLossOccurred(BluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onLinkLossOccurred====================");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onServicesDiscovered(BluetoothDevice device, boolean optionalServicesFound) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onServicesDiscovered====================");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onDeviceReady(BluetoothDevice device) {
        LHomeLog.e(Constants.MESH_LOG, getClass(), "onDeviceReady：\ndeviceName:" + device.getName() + "\nmacAddress:" + device.getAddress() + "\naction:" + this.action);
        int i = this.action;
        if (1 == i) {
            setResult(true, new Object[0]);
            return;
        }
        if (5 != i) {
            if (4 == i) {
                sendMessage(this.nodeAddress, new ConfigNodeReset());
                return;
            }
            return;
        }
        if (this.mBleMeshManager.isProvisioningComplete()) {
            if (this.mSetupProvisionedNode) {
                if (this.mMeshNetwork.getSelectedProvisioner().getProvisionerAddress() != null) {
                    if (this.mProvisionedMeshNode != null) {
                        IProvisioningCallback iProvisioningCallback = this.mIProvisioningCallback;
                        if (iProvisioningCallback != null) {
                            iProvisioningCallback.provisioningTestSeq();
                        }
                        this.mHandler.removeMessages(6);
                        this.mHandler.removeMessages(7);
                        this.mHandler.sendEmptyMessage(6);
                        this.mHandler.sendEmptyMessageDelayed(7, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
                        return;
                    }
                    return;
                }
                this.mSetupProvisionedNode = false;
                return;
            }
            return;
        }
        setResult(false, "provision fail");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onBondingRequired(BluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onBondingRequired====================");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onBonded(BluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onBonded====================");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onBondingFailed(BluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onBondingFailed====================");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onError(BluetoothDevice device, String message, int errorCode) {
        int i;
        LHomeLog.i(Constants.MESH_LOG, getClass(), errorCode + "==================onError====================" + message);
        this.seqSuccess = false;
        int i2 = this.action;
        if (i2 != 1 && i2 != 5) {
            setResult(false, message);
        }
        ITestSequenceCallback iTestSequenceCallback = this.mITestSequenceCallback;
        if (iTestSequenceCallback == null || (i = this.action) == 5 || i == 2 || this.upgradeStart) {
            return;
        }
        iTestSequenceCallback.onBleError(errorCode);
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onDeviceNotSupported(BluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onDeviceNotSupported====================");
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onAddress(String s) {
        if (this.bleFwUpgradeStart) {
            return;
        }
        testSequence(MeshParserUtils.hexToInt(s));
    }

    @Override // com.feasycom.feasymesh.library.ble.BleManagerCallbacks
    public void onIvIndex(int index, boolean ivUpdateActive) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "onIvIndex: index  ->  " + index + " cloudIvIndex -> " + this.cloudIvIndex + "   ivUpdateActive  ->  " + ivUpdateActive);
        if (this.cloudIvIndex < index) {
            this.mMeshManagerApi.setIvIndex(index, ivUpdateActive);
        }
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void onNetworkLoaded(MeshNetwork meshNetwork) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onNetworkLoaded====================");
        loadNetwork(meshNetwork);
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void onNetworkLoadFailed(String s) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onNetworkLoadFailed====================");
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void onNetworkUpdated(MeshNetwork meshNetwork) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onNetworkUpdated====================");
        loadNetwork(meshNetwork);
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void onNetworkImported(MeshNetwork meshNetwork) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onNetworkImported====================");
        if (this.mMeshNetwork != null && !meshNetwork.getMeshUUID().equals(this.mMeshNetwork.getMeshUUID())) {
            this.mMeshManagerApi.deleteMeshNetworkFromDb(this.mMeshNetwork);
        }
        IRefreshNetworkCallback iRefreshNetworkCallback = this.mIRefreshNetworkCallback;
        if (iRefreshNetworkCallback != null) {
            iRefreshNetworkCallback.onRefreshSuccess();
        }
        loadNetwork(meshNetwork);
        LHomeLog.i(Constants.MESH_LOG, getClass(), "onNetworkImported NetWorkId" + StringUtils.byte2Str(meshNetwork.getPrimaryNetworkKey().getKey()));
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void onNetworkImportFailed(String s) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onNetworkImportFailed====================" + s);
        IRefreshNetworkCallback iRefreshNetworkCallback = this.mIRefreshNetworkCallback;
        if (iRefreshNetworkCallback != null) {
            iRefreshNetworkCallback.onRefreshFail();
        }
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void sendProvisioningPdu(final UnprovisionedMeshNode meshNode, final byte[] pdu) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "sendProvisioningPdu: 发送数据" + StringUtils.byte2Str(pdu));
        this.mBleMeshManager.sendPdu(pdu);
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public void onMeshPduCreated(final byte[] pdu) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "onMeshPduCreated:sendProvisioningPdu: 发送数据");
        this.mBleMeshManager.sendPdu(pdu);
    }

    @Override // com.feasycom.feasymesh.library.MeshManagerCallbacks
    public int getMtu() {
        return this.mBleMeshManager.getMaximumPacketSize();
    }

    private void loadNetwork(final MeshNetwork meshNetwork) {
        this.mMeshNetwork = meshNetwork;
        if (meshNetwork == null || meshNetwork.isProvisionerSelected() || meshNetwork.getProvisioners().isEmpty()) {
            return;
        }
        Provisioner provisioner = (Provisioner) meshNetwork.getProvisioners().get(0);
        provisioner.setLastSelected(true);
        this.mMeshNetwork.selectProvisioner(provisioner);
    }

    /* renamed from: com.ltech.smarthome.blemesh.feasy.FeasyController$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$feasycom$feasymesh$library$provisionerstates$ProvisioningState$States;

        static {
            int[] iArr = new int[ProvisioningState.States.values().length];
            $SwitchMap$com$feasycom$feasymesh$library$provisionerstates$ProvisioningState$States = iArr;
            try {
                iArr[ProvisioningState.States.PROVISIONING_INVITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$feasycom$feasymesh$library$provisionerstates$ProvisioningState$States[ProvisioningState.States.PROVISIONING_CAPABILITIES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.feasycom.feasymesh.library.MeshProvisioningStatusCallbacks
    public void onProvisioningStateChanged(final UnprovisionedMeshNode meshNode, ProvisioningState.States state, byte[] bytes) {
        final ProvisioningCapabilities provisioningCapabilities;
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onProvisioningStateChanged====================" + state);
        if (AnonymousClass5.$SwitchMap$com$feasycom$feasymesh$library$provisionerstates$ProvisioningState$States[state.ordinal()] == 2 && (provisioningCapabilities = meshNode.getProvisioningCapabilities()) != null) {
            try {
                if (this.mMeshNetwork.assignUnicastAddress(this.unicastAddress)) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            FeasyController.this.lambda$onProvisioningStateChanged$0(provisioningCapabilities, meshNode);
                        }
                    }, 1000L);
                }
            } catch (Exception unused) {
                setResult(false, "Address used");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProvisioningStateChanged$0(ProvisioningCapabilities provisioningCapabilities, UnprovisionedMeshNode unprovisionedMeshNode) {
        if (provisioningCapabilities.getAvailableOOBTypes().size() == 1 && provisioningCapabilities.getAvailableOOBTypes().get(0) == AuthenticationOOBMethods.NO_OOB_AUTHENTICATION) {
            unprovisionedMeshNode.setNodeName(this.nodeName);
            this.mMeshManagerApi.startProvisioning(unprovisionedMeshNode);
        } else {
            setResult(false, "OOB type more than one");
        }
    }

    @Override // com.feasycom.feasymesh.library.MeshProvisioningStatusCallbacks
    public void onProvisioningFailed(UnprovisionedMeshNode unprovisionedMeshNode, ProvisioningState.States states, byte[] bytes) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), unprovisionedMeshNode.getUnicastAddress() + "==================onProvisioningFailed====================" + states);
        setResult(false, states.name());
    }

    @Override // com.feasycom.feasymesh.library.MeshProvisioningStatusCallbacks
    public void onProvisioningCompleted(ProvisionedMeshNode meshNode, ProvisioningState.States states, byte[] bytes) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onProvisioningCompleted====================" + states);
        this.action = 5;
        this.mProvisionedMeshNode = meshNode;
        if (!this.mBleMeshManager.refresh()) {
            ExtendedBluetoothDevice extendedBluetoothDevice = this.mProvisioningDevice;
            if (extendedBluetoothDevice != null) {
                onProvisionedDeviceFound(this.mProvisionedMeshNode, extendedBluetoothDevice);
                return;
            }
            return;
        }
        this.mBleMeshManager.setClearCacheRequired();
        this.mMeshManagerApi.createMeshPdu(meshNode.getUnicastAddress(), new ConfigAppKeyAdd(this.mMeshNetwork.getNetKeys().get(meshNode.getAddedNetKeys().get(0).getIndex()), this.mMeshNetwork.getAppKeys().get(0)));
        this.mMeshManagerApi.addProxy();
        this.seqSuccess = true;
        setResult(true, new Object[0]);
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onTransactionFailed(int i, boolean b2) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onTransactionFailed====================");
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onUnknownPduReceived(int i, byte[] bytes) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onUnknownPduReceived====================");
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onBlockAcknowledgementProcessed(int i, ControlMessage controlMessage) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onBlockAcknowledgementProcessed====================");
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onBlockAcknowledgementReceived(int i, ControlMessage controlMessage) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onBlockAcknowledgementReceived====================" + i);
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onMeshMessageProcessed(int dst, MeshMessage meshMessage) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), dst + "===dst===============onMeshMessageProcessed====================" + meshMessage.getSrc());
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onMeshMessageReceived(int src, MeshMessage meshMessage) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onMeshMessageReceived=================from===" + src);
        final ProvisionedMeshNode node = this.mMeshNetwork.getNode(src);
        if (node != null) {
            if (meshMessage instanceof ConfigCompositionDataStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigCompositionDataStatus====================");
                if (this.mSetupProvisionedNode) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            FeasyController.this.lambda$onMeshMessageReceived$1(node);
                        }
                    }, 0L);
                } else {
                    setResult(false, "ConfigCompositionData failed");
                }
            } else if (meshMessage instanceof ConfigDefaultTtlStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigDefaultTtlStatus====================");
                this.mMeshManagerApi.stopTestSequence(0);
            } else if (meshMessage instanceof ConfigAppKeyStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigAppKeyStatus====================");
                ConfigAppKeyStatus configAppKeyStatus = (ConfigAppKeyStatus) meshMessage;
                if (this.mSetupProvisionedNode && configAppKeyStatus.isSuccessful()) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            FeasyController.this.lambda$onMeshMessageReceived$2(node);
                        }
                    }, 0L);
                } else {
                    setResult(false, "ConfigAppKey failed");
                }
            } else if (meshMessage instanceof ConfigNetworkTransmitStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigNetworkTransmitStatus====================");
                if (this.mSetupProvisionedNode) {
                    for (Element element : this.mMeshNetwork.getNode(node.getUnicastAddress()).getElements().values()) {
                        for (MeshModel meshModel : element.getMeshModels().values()) {
                            if (meshModel instanceof VendorModel) {
                                sendMessage(node.getUnicastAddress(), new ConfigModelAppBind(element.getElementAddress(), meshModel.getModelId(), this.mMeshNetwork.getAppKeys().get(0).getKeyIndex()));
                                return;
                            }
                        }
                    }
                    setResult(false, "ConfigModelAppStatus failed");
                } else {
                    setResult(false, "ConfigNetworkTransmit failed");
                }
            } else if (meshMessage instanceof ProxyConfigFilterStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ProxyConfigFilterStatus====================");
            } else if (meshMessage instanceof ConfigModelAppStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigModelAppStatus====================");
                ConfigModelAppStatus configModelAppStatus = (ConfigModelAppStatus) meshMessage;
                if (this.mSetupProvisionedNode && configModelAppStatus.isSuccessful()) {
                    this.mSetupProvisionedNode = false;
                    setResult(true, "");
                } else {
                    setResult(false, "ConfigModelAppStatus failed");
                }
            } else if (meshMessage instanceof ConfigModelPublicationStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigModelPublicationStatus====================");
            } else if (meshMessage instanceof ConfigModelSubscriptionStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigModelSubscriptionStatus====================");
                this.mHandler.removeMessages(3);
                setResult(((ConfigModelSubscriptionStatus) meshMessage).isSuccessful(), new Object[0]);
            } else if (meshMessage instanceof ConfigRelayStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigRelayStatus====================");
            } else if (meshMessage instanceof ConfigProxyStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigProxyStatus====================");
            } else if (meshMessage instanceof GenericOnOffStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================GenericOnOffStatus====================");
            } else if (meshMessage instanceof GenericLevelStatus) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================GenericLevelStatus====================");
            } else if (meshMessage instanceof VendorModelMessageStatus) {
                String byte2Str = StringUtils.byte2Str(((VendorModelMessageStatus) meshMessage).getAccessPayload());
                LHomeLog.i(Constants.MESH_LOG, getClass(), "==================VendorModelMessageStatus====================" + byte2Str);
                RecPackage recPackage = new RecPackage();
                recPackage.setAgreementId(Integer.parseInt(node.getNodeName()));
                recPackage.setRecData(byte2Str.substring(0, 2) + byte2Str.substring(6));
                recPackage.setRecSource(2);
                if (Injection.message() != null) {
                    Injection.message().handleMessage(recPackage);
                }
            } else {
                setResult(false, "no message");
            }
        } else if (meshMessage instanceof ConfigNodeResetStatus) {
            this.mHandler.removeMessages(4);
            this.mBleMeshManager.setClearCacheRequired();
            ConfigNodeResetStatus configNodeResetStatus = (ConfigNodeResetStatus) meshMessage;
            LHomeLog.i(Constants.MESH_LOG, getClass(), "==================ConfigNodeResetStatus====================status=" + configNodeResetStatus.getStatusCode());
            if (configNodeResetStatus.getStatusCode() == 0) {
                this.mBleMeshManager.setClearCacheRequired();
                setResult(true, new Object[0]);
            } else {
                setResult(false, new Object[0]);
            }
        }
        this.mMeshNetwork = this.mMeshManagerApi.getMeshNetwork();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMeshMessageReceived$1(ProvisionedMeshNode provisionedMeshNode) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "sendMessage4");
        sendMessage(provisionedMeshNode.getUnicastAddress(), new ConfigDefaultTtlGet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMeshMessageReceived$2(ProvisionedMeshNode provisionedMeshNode) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "sendMessage6");
        sendMessage(provisionedMeshNode.getUnicastAddress(), new ConfigNetworkTransmitSet(2, 1));
    }

    @Override // com.feasycom.feasymesh.library.MeshStatusCallbacks
    public void onMessageDecryptionFailed(String s, String s1) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onMessageDecryptionFailed====================");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScan() {
        LHomeLog.d(Constants.MESH_LOG, getClass(), "startScan: FeasyController");
        if (this.mIsScanning) {
            return;
        }
        this.mIsScanning = true;
        ScanSettings build = new ScanSettings.Builder().setScanMode(2).setReportDelay(0L).setUseHardwareFilteringIfSupported(false).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(BleMeshManager.MESH_PROXY_UUID)).build());
        BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        if (scanner == null) {
            SmartToast.showShort(R.string.blue_disconnect);
        } else {
            scanner.startScan(arrayList, build, this.scanCallback);
            this.mHandler.sendEmptyMessageDelayed(1, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopScan() {
        this.mHandler.removeMessages(1);
        BluetoothLeScannerCompat.getScanner().stopScan(this.scanCallback);
        this.mIsScanning = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProvisionedDeviceFound(final ProvisionedMeshNode node, final ExtendedBluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "==================onProvisionedDeviceFound====================");
        this.mSetupProvisionedNode = true;
        this.mProvisionedMeshNode = node;
        this.mIsReconnectingFlag = true;
        this.mHandler.postDelayed(new Runnable() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController.4
            @Override // java.lang.Runnable
            public void run() {
                FeasyController.this.connectDevice(device.getDevice());
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectDevice(final BluetoothDevice device) {
        this.btModule = null;
        LHomeLog.i(Constants.MESH_LOG, getClass(), "connectToProxy: 开始连接" + device.getAddress());
        this.mBleMeshManager.connect(device).invalid(new InvalidRequestCallback() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda0
            @Override // com.feasycom.feasymesh.library.ble.callback.InvalidRequestCallback
            public final void onInvalidRequest() {
                FeasyController.this.lambda$connectDevice$3(device);
            }
        }).fail(new FailCallback() { // from class: com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda1
            @Override // com.feasycom.feasymesh.library.ble.callback.FailCallback
            public final void onRequestFailed(BluetoothDevice bluetoothDevice, int i) {
                FeasyController.this.lambda$connectDevice$4(device, bluetoothDevice, i);
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectDevice$4(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, int i) {
        lambda$connectDevice$3(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: connectFail, reason: merged with bridge method [inline-methods] */
    public void lambda$connectDevice$3(BluetoothDevice device) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "connectFail: 连接失败" + device + "  | " + device.getAddress());
        this.connectReTryTime = this.connectReTryTime + 1;
        setResult(false, "connect timeout");
    }

    public MeshManagerApi getMeshManagerApi() {
        return this.mMeshManagerApi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0030, code lost:
    
        if (r0 != 5) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setResult(final boolean r4, final java.lang.Object... r5) {
        /*
            r3 = this;
            java.lang.Class r0 = r3.getClass()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "setResult: action="
            r1.<init>(r2)
            int r2 = r3.action
            r1.append(r2)
            java.lang.String r2 = "__success="
            r1.append(r2)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "[MeshLog]"
            com.smart.message.utils.LHomeLog.i(r2, r0, r1)
            int r0 = r3.action
            r1 = 1
            if (r0 == r1) goto L54
            r1 = 2
            if (r0 == r1) goto L49
            r1 = 3
            if (r0 == r1) goto L3e
            r1 = 4
            if (r0 == r1) goto L33
            r1 = 5
            if (r0 == r1) goto L49
            goto L5e
        L33:
            android.os.Handler r5 = r3.mHandler
            com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda8 r0 = new com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda8
            r0.<init>()
            r5.post(r0)
            goto L5e
        L3e:
            android.os.Handler r5 = r3.mHandler
            com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda7 r0 = new com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda7
            r0.<init>()
            r5.post(r0)
            goto L5e
        L49:
            android.os.Handler r0 = r3.mHandler
            com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda6 r1 = new com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda6
            r1.<init>()
            r0.post(r1)
            goto L5e
        L54:
            android.os.Handler r5 = r3.mHandler
            com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda5 r0 = new com.ltech.smarthome.blemesh.feasy.FeasyController$$ExternalSyntheticLambda5
            r0.<init>()
            r5.post(r0)
        L5e:
            r4 = 0
            r3.action = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.blemesh.feasy.FeasyController.setResult(boolean, java.lang.Object[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setResult$5(boolean z) {
        if (this.mIConnectCallback != null) {
            if (z) {
                LHomeLog.e(Constants.MESH_LOG, getClass(), "setResult: connect success");
                this.mIConnectCallback.onConnectSuccess(this.btModule, this.connectedDevice);
            } else {
                LHomeLog.e(Constants.MESH_LOG, getClass(), "setResult: connect fail");
                this.mIConnectCallback.onConnectFail();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setResult$6(boolean z, Object[] objArr) {
        IProvisioningCallback iProvisioningCallback = this.mIProvisioningCallback;
        if (iProvisioningCallback != null) {
            this.action = 0;
            if (z) {
                iProvisioningCallback.provisioningSuccess(this.mProvisionedMeshNode);
            } else {
                iProvisioningCallback.provisioningFailed(objArr.length > 0 ? (String) objArr[0] : "");
            }
            this.mIProvisioningCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setResult$7(boolean z) {
        IGroupCallback iGroupCallback = this.mIGroupCallback;
        if (iGroupCallback != null) {
            if (z) {
                iGroupCallback.configSuccess();
            } else {
                iGroupCallback.configFail();
            }
            this.mIGroupCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setResult$8(boolean z) {
        IResetNodeCallback iResetNodeCallback = this.mIResetNodeCallback;
        if (iResetNodeCallback != null) {
            if (z) {
                iResetNodeCallback.resetSuccess();
            } else {
                iResetNodeCallback.resetFail();
            }
            this.mIResetNodeCallback = null;
        }
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public boolean isSeqSuccess() {
        return this.seqSuccess;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setUpgradeStart(boolean upgradeStart) {
        this.upgradeStart = upgradeStart;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setProvisioning(boolean upgradeStart) {
        this.mBleMeshManager.isProvisioning = upgradeStart;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setNodeTtl(int ttl) {
        ProvisionedMeshNode node;
        MeshNetwork meshNetwork = this.mMeshNetwork;
        if (meshNetwork == null || (node = meshNetwork.getNode(BleMeshManager.MESH_PROVISIONING_UUID.toString())) == null) {
            return;
        }
        node.setTtl(Integer.valueOf(ttl));
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setDefaultTtl(int address, int ttl) {
        sendMessage(address, new ConfigDefaultTtlSet(ttl));
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setBleFwUpgradeStart(boolean upgradeStart) {
        this.bleFwUpgradeStart = upgradeStart;
    }

    @Override // com.ltech.smarthome.blemesh.IMeshController
    public void setIvindex(int ivindex, IAction<Integer> iAction) {
        this.cloudIvIndexUpdataAcion = iAction;
        this.cloudIvIndex = ivindex;
        this.mMeshManagerApi.setIvIndex(ivindex, true);
    }
}