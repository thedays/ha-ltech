package com.ltech.smarthome.ui.config;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.feasycom.feasymesh.library.MeshManagerApi;
import com.feasycom.feasymesh.library.transport.ProvisionedMeshNode;
import com.google.gson.Gson;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.IConnectBleCallback;
import com.ltech.smarthome.blemesh.IProvisioningCallback;
import com.ltech.smarthome.blemesh.IRemoveNodeCallback;
import com.ltech.smarthome.blemesh.IResetNodeCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelBleParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.CharSwitchBean;
import com.ltech.smarthome.net.response.CodeLibraryBean;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.group.AddGroupResponse;
import com.ltech.smarthome.net.response.mesh.GetProvisioningAddressResponse;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.upgrade.ActUpgrade;
import com.ltech.smarthome.upgrade.ActUpgradeIcon;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SetBleTypeDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.blegateway.CmdMeshGateway;
import com.smart.product_agreement.parser.IUpgradeParser;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActMeshScanVM extends BaseViewModel {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    private static final String TAG = "ActMeshScanVM";
    public boolean addFinish;
    public ConfigBean configBean;
    public int connectFailTime;
    public long controlId;
    public ObservableList<ScanItem> deviceList;
    public Device filerDevice;
    public long floorId;
    public boolean isIconUpgrade;
    public List<Device> list;
    MeshManagerApi mMeshManagerApi;
    public long placeId;
    public Listing<Place> placeInfoResult;
    public int pos;
    public long roomId;
    public int upgradePosition;
    public boolean isPROXY = false;
    public MutableLiveData<Boolean> ShowprogressBar = new MutableLiveData<>();
    public MutableLiveData<String> deviceNum = new MutableLiveData<>();
    public SingleLiveEvent<Boolean> showConfigDialog = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> startScanLiveData = new MutableLiveData<>(false);
    public MutableLiveData<List<Device>> addList = new MutableLiveData<>(new ArrayList());
    public MutableLiveData<Integer> upgradeStateLiveData = new MutableLiveData<>(0);
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public SingleLiveEvent<Void> showSuperPanelConfigSuccess = new SingleLiveEvent<>();
    public MutableLiveData<StateParam> updateStateLiveData = new MutableLiveData<>();
    public List<ScanItem> scanItems = new ArrayList();
    public SingleLiveEvent<Void> showFinishDialog = new SingleLiveEvent<>();
    public MutableLiveData<Integer> numSuccessDevice = new MutableLiveData<>(0);
    public boolean addSingleDevice = true;
    CountDownTimer timer = new CountDownTimer(25000, 1000) { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.1
        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "last " + (millisUntilFinished / 1000));
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "onFinish");
            ActMeshScanVM actMeshScanVM = ActMeshScanVM.this;
            actMeshScanVM.setErrorView(actMeshScanVM.getContext().getString(R.string.tip_connect_time_out));
        }
    };
    public ItemBinding<ScanItem> itemBinding = ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda6
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActMeshScanVM.this.lambda$new$3(itemBinding, i, (ActMeshScanVM.ScanItem) obj);
        }
    });
    public BindingRecyclerViewAdapter.ItemIds<ScanItem> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda7
        /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v1 long, still in use, count: 1, list:
              (r1v1 long) from 0x0006: RETURN (r1v1 long)
            	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
            	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
            	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
            	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
            	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
            	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
            */
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int r1, java.lang.Object r2) {
            /*
                r0 = this;
                com.ltech.smarthome.ui.config.ActMeshScanVM$ScanItem r2 = (com.ltech.smarthome.ui.config.ActMeshScanVM.ScanItem) r2
                long r1 = com.ltech.smarthome.ui.config.ActMeshScanVM.lambda$new$4(r1, r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda7.getItemId(int, java.lang.Object):long");
        }
    };
    public List<Floor> mFloorList = new ArrayList();
    public List<Room> mRoomList = new ArrayList();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();

    private int getSignal(int rssi) {
        if (rssi <= -127) {
            return 0;
        }
        if (rssi <= -80) {
            return 1;
        }
        if (rssi <= -60) {
            return 2;
        }
        return rssi <= -40 ? 3 : 4;
    }

    static /* synthetic */ void lambda$setErrorView$34(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$setSuperPanel$32(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseViewModel
    protected void showError() {
        super.showError();
    }

    public ActMeshScanVM() {
        LHomeLog.i(getClass(), TAG);
        this.deviceList = new ObservableArrayList();
        this.mMeshManagerApi = new MeshManagerApi(Utils.getApp());
        this.list = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ItemBinding itemBinding, final int i, final ScanItem scanItem) {
        String string;
        ItemBinding bindExtra = itemBinding.clearExtras().set(40, R.layout.item_mesh_scan).bindExtra(24, scanItem.name).bindExtra(73, Integer.valueOf(getSignal(scanItem.bluetoothDevice.getRssi()))).bindExtra(37, Integer.valueOf(scanItem.productInfo.getDefaultIconRes())).bindExtra(18, getContext().getString(R.string.cur_version_1, scanItem.currentVersion)).bindExtra(49, getContext().getString(R.string.new_version_1, scanItem.newVersion)).bindExtra(77, String.format(Locale.US, "%.2f%%", Float.valueOf(scanItem.progress)));
        if (!scanItem.productInfo.getProductId().equals(ProductId.ID_KEY_SWITCH_1) && !scanItem.productInfo.getProductId().equals(ProductId.ID_KEY_SWITCH_2) && !scanItem.productInfo.getProductId().equals(ProductId.ID_KEY_SWITCH_3) && !scanItem.productInfo.getProductId().equals(ProductId.ID_KEY_SWITCH_4) && !scanItem.productInfo.getProductId().equals(ProductId.ID_BODY_SENSOR) && !scanItem.productInfo.getProductId().equals(ProductId.ID_RC4) && !scanItem.productInfo.getProductId().equals(ProductId.ID_RC4S) && !scanItem.productInfo.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && !scanItem.productInfo.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB) && !scanItem.productInfo.getProductId().equals(ProductId.ID_SMART_PANEL_GQ) && !scanItem.productInfo.getProductId().equals(ProductId.ID_SMART_PANEL_GQX) && !scanItem.productInfo.getProductId().equals(ProductId.ID_DOOR_SENSOR) && !scanItem.productInfo.getProductId().equals(ProductId.ID_RC_B1) && !scanItem.productInfo.getProductId().equals(ProductId.ID_RC_B2) && !scanItem.productInfo.getProductId().equals(ProductId.ID_RC_B5) && !scanItem.productInfo.getProductId().equals(ProductId.ID_RC_B8)) {
            string = ActivityUtils.getTopActivity().getString(R.string.app_upgrade);
        } else {
            string = ActivityUtils.getTopActivity().getString(R.string.app_manual);
        }
        bindExtra.bindExtra(87, string).bindExtra(11, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMeshScanVM.this.lambda$new$0(i, scanItem, (View) obj);
            }
        })).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMeshScanVM.this.lambda$new$2(i, scanItem, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, ScanItem scanItem, View view) {
        LHomeLog.i(getClass(), "************************onConnectSuccess***************************");
        Device device = this.addList.getValue().get(i);
        int unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
        ConfigBean configBean = getConfigBean(device.getProductId(), scanItem, i);
        configBean.unicastAddress = unicastAddress;
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).testDeviceLocation(ActivityUtils.getTopActivity(), configBean.createCtrlPackage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x010f, code lost:
    
        if (r5.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_PANEL_S6B) == false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$new$2(final int r19, final com.ltech.smarthome.ui.config.ActMeshScanVM.ScanItem r20, android.view.View r21) {
        /*
            Method dump skipped, instructions count: 522
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.config.ActMeshScanVM.lambda$new$2(int, com.ltech.smarthome.ui.config.ActMeshScanVM$ScanItem, android.view.View):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ScanItem scanItem, int i, Device device, ImageTipDialog imageTipDialog) {
        upgreade(scanItem, i, device);
        imageTipDialog.dismissDialog();
    }

    private void upgreade(ScanItem item, final int position, final Device device2) {
        BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
        if (connectedDevice != null) {
            LHomeLog.i(getClass(), "代理节点地址 = " + connectedDevice.getAddress() + "   " + item.bluetoothDevice.getAddress() + "  ");
            if (item.bluetoothDevice.getAddress().equals(connectedDevice.getAddress())) {
                this.upgradePosition = position;
                NavUtils.destination(!this.isIconUpgrade ? ActUpgrade.class : ActUpgradeIcon.class).withLong(Constants.CONTROL_ID, device2.getId()).navigation(ActivityUtils.getTopActivity());
                return;
            }
        }
        this.ShowprogressBar.setValue(true);
        Injection.mesh().stopScan();
        Injection.mesh().connect(item.bluetoothDevice, new IConnectBleCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.2
            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice2) {
                LHomeLog.i(getClass(), "************************onConnectSuccess***************************");
                Injection.mesh().setUpgradeStart(true);
                ActMeshScanVM.this.ShowprogressBar.setValue(false);
                ActMeshScanVM.this.upgradePosition = position;
                NavUtils.destination(!ActMeshScanVM.this.isIconUpgrade ? ActUpgrade.class : ActUpgradeIcon.class).withLong(Constants.CONTROL_ID, device2.getId()).navigation(ActivityUtils.getTopActivity());
            }

            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectFail() {
                ActMeshScanVM.this.ShowprogressBar.setValue(false);
                SmartToast.showShort(ActMeshScanVM.this.getContext().getString(R.string.tip_connect_fail));
                LHomeLog.i(getClass(), "************************onConnectFail***************************");
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.3
            @Override // java.lang.Runnable
            public void run() {
                ActMeshScanVM.this.ShowprogressBar.setValue(false);
                SmartToast.showShort(ActMeshScanVM.this.getContext().getString(R.string.tip_connect_fail));
            }
        }, 20000L);
    }

    private void addDeviceAtPosition(ScanItem item, int position) {
        this.configBean = getConfigBean(item.productInfo.getProductId(), item, position);
        nextStep();
    }

    public boolean refreshRssi(ExtendedBluetoothDevice bluetoothDevice) {
        for (int i = 0; i < this.deviceList.size(); i++) {
            ScanItem scanItem = this.deviceList.get(i);
            if (scanItem.matches(bluetoothDevice)) {
                scanItem.bluetoothDevice.setRssi(bluetoothDevice.getRssi());
                this.deviceList.set(i, scanItem);
                return true;
            }
        }
        return false;
    }

    public boolean addDevice(ExtendedBluetoothDevice bluetoothDevice) {
        ProductInfo bleProductInfoByType;
        if ((this.filerDevice != null && !bluetoothDevice.getAddress().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equalsIgnoreCase(this.filerDevice.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) || (bleProductInfoByType = ProductRepository.getBleProductInfoByType(bluetoothDevice.getProductType(), bluetoothDevice.getSubProductType())) == null) {
            return false;
        }
        if (this.filerDevice == null && (bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX))) {
            return false;
        }
        bluetoothDevice.setControlType(bleProductInfoByType.getControlType());
        ScanItem scanItem = new ScanItem(bluetoothDevice, false);
        scanItem.productInfo = bleProductInfoByType;
        scanItem.name = getDeviceName(scanItem, bleProductInfoByType);
        scanItem.autoAdd = ProductRepository.canAutoAdd(bleProductInfoByType);
        this.deviceList.add(scanItem);
        this.deviceNum.setValue(getContext().getString(R.string.scan_device_num, Integer.valueOf(this.deviceList.size())));
        return true;
    }

    public String getDeviceName(ScanItem item, ProductInfo productInfo) {
        String str = "";
        String replace = getContext().getString(productInfo.getAddNameRes()).replace("\n", "");
        if (productInfo.getProductId().equalsIgnoreCase(ProductId.ID_BLE_CURTAIN)) {
            replace = getContext().getString(R.string.app_str_ble_curtain_name);
        }
        if (ProductRepository.isSuperPanel(productInfo.getProductId()) || productInfo.getProductId().equals(ProductId.ID_HOME_KIT)) {
            return replace;
        }
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            item.deviceIndex = i;
            if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
                str = replace + i;
            } else {
                str = replace + " " + i;
            }
            Iterator<ScanItem> it = this.deviceList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (it.next().name.equals(str)) {
                    z = true;
                    break;
                }
            }
            if (Injection.repo().device().isDeviceNameExist(this.placeId, str)) {
                z = true;
            }
        }
        return str;
    }

    public boolean addDeviceForProxy(ExtendedBluetoothDevice bluetoothDevice) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "ActMeshScan.addDeviceForProxy: 11  " + bluetoothDevice.getAddress());
        if (this.filerDevice != null && !bluetoothDevice.getAddress().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equalsIgnoreCase(this.filerDevice.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
            return false;
        }
        for (int i = 0; i < this.list.size(); i++) {
            Device device = this.list.get(i);
            if (!device.isSubDevice() && bluetoothDevice.getAddress().replace(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equals(device.getWifiMac())) {
                ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(device);
                if (bleProductInfoByType == null) {
                    return false;
                }
                LHomeLog.i(Constants.MESH_LOG, getClass(), "ActMeshScan:addDeviceForProxy: 33");
                if (this.filerDevice == null) {
                    if (!this.isIconUpgrade) {
                        if ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL)) || ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) || ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) || ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) || ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) || ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) || ((!AppUtils.isAppDebug() && bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) || bleProductInfoByType.getProductId().equals(ProductId.ID_RC4) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_1) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_2) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_3) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_4)))))))) {
                            return false;
                        }
                    } else if (!bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_PANEL_G4) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                        return false;
                    }
                }
                LHomeLog.i(Constants.MESH_LOG, getClass(), "ActMeshScan.addDeviceForProxy: 44");
                bluetoothDevice.setControlType(bleProductInfoByType.getControlType());
                ScanItem scanItem = new ScanItem(bluetoothDevice, this.isPROXY, ((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
                scanItem.productInfo = bleProductInfoByType;
                scanItem.name = device.getDeviceName();
                scanItem.device = device;
                this.deviceList.add(scanItem);
                List<Device> value = this.addList.getValue();
                value.add(device);
                this.addList.setValue(value);
                this.deviceNum.setValue(getContext().getString(R.string.scan_device_num_2, Integer.valueOf(this.deviceList.size())));
                return true;
            }
        }
        return false;
    }

    public boolean addProxyDevice(BluetoothDevice bluetoothDevice) {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "ActMeshScan.addProxyDevice: 11  " + bluetoothDevice.getAddress());
        if (this.filerDevice != null && !bluetoothDevice.getAddress().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equalsIgnoreCase(this.filerDevice.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
            return false;
        }
        if (this.addList.getValue() != null && this.addList.getValue().size() > 0) {
            for (int i = 0; i < this.addList.getValue().size(); i++) {
                if (this.addList.getValue().get(i).getWifiMac().equals(bluetoothDevice.getAddress().replace(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
                    return false;
                }
            }
        }
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            Device device = this.list.get(i2);
            if (!device.isSubDevice() && bluetoothDevice.getAddress().replace(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equals(device.getWifiMac())) {
                ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(device);
                if (bleProductInfoByType == null) {
                    return false;
                }
                LHomeLog.i(Constants.MESH_LOG, getClass(), "ActMeshScan.addProxyDevice: 33");
                if (this.filerDevice == null) {
                    if (!this.isIconUpgrade) {
                        if (bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || bleProductInfoByType.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX) || bleProductInfoByType.getProductId().equals(ProductId.ID_RC4) || bleProductInfoByType.getProductId().equals(ProductId.ID_RC4S) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_1) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_2) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_3) || bleProductInfoByType.getProductId().equals(ProductId.ID_KEY_SWITCH_4) || bleProductInfoByType.getProductId().equals(ProductId.ID_BODY_SENSOR) || bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_PANEL_GQX) || bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_PANEL_GQ)) {
                            return false;
                        }
                    } else if (!bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_PANEL_G4) && !bleProductInfoByType.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                        return false;
                    }
                }
                ExtendedBluetoothDevice extendedBluetoothDevice = new ExtendedBluetoothDevice();
                extendedBluetoothDevice.setDevice(bluetoothDevice);
                extendedBluetoothDevice.setRssi(99);
                ScanItem scanItem = new ScanItem(extendedBluetoothDevice, this.isPROXY, ((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
                scanItem.productInfo = bleProductInfoByType;
                scanItem.name = device.getDeviceName();
                this.deviceList.add(scanItem);
                List<Device> value = this.addList.getValue();
                value.add(device);
                this.addList.setValue(value);
                this.deviceNum.setValue(getContext().getString(R.string.scan_device_num_2, Integer.valueOf(this.deviceList.size())));
                return true;
            }
        }
        return false;
    }

    public void location(ConfigBean configBean) {
        LHomeLog.i(getClass(), "configBean.createCtrlPackage()" + configBean.createCtrlPackage().getAddress() + "  " + configBean.createCtrlPackage().getControlType());
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).testDeviceLocation(ActivityUtils.getTopActivity(), configBean.createCtrlPackage());
    }

    private void configDevice(int state) {
        if (state != 9999) {
            switch (state) {
                case 1:
                    getConfigInfo();
                    break;
                case 2:
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "FeasyController.ConfigBean.CONNECT");
                    connect();
                    break;
                case 3:
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "FeasyController.配置");
                    provisioningDevice();
                    break;
                case 4:
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "FeasyController.设置地址");
                    setPublishAddress();
                    break;
                case 5:
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "设置类型");
                    setDeviceType();
                    break;
                case 6:
                    if ("bt200".equalsIgnoreCase(this.configBean.btModule)) {
                        setDeviceKeyToDevice();
                        break;
                    } else {
                        saveDevice();
                        break;
                    }
                case 7:
                    if (this.addSingleDevice) {
                        dismissLoadingDialog();
                        this.showConfigDialog.setValue(Boolean.valueOf(ProductRepository.needLocation(this.configBean.configItem.productInfo.getProductId())));
                        break;
                    } else {
                        configDevice(6);
                        break;
                    }
                case 8:
                    saveMeshParam();
                    break;
                case 9:
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "设置输出类型");
                    setOutputType();
                    break;
                case 10:
                    setSuperPanel();
                    break;
                case 11:
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "设置欧式面板参数");
                    setEurConfig();
                    break;
            }
            return;
        }
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.4
            @Override // java.lang.Runnable
            public void run() {
                ActMeshScanVM.this.setSuccessView();
            }
        }, 1500L);
    }

    private void setDeviceKeyToDevice() {
        if (this.addSingleDevice) {
            showLoadingDialog(getContext().getString(R.string.adding));
        }
        Device device = new Device();
        BleParam bleParam = new BleParam();
        bleParam.setUnicastAddress(this.configBean.unicastAddress);
        device.setParam(bleParam);
        device.setProductId(this.configBean.configItem.productInfo.getProductId());
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).setDeviceKey(ActivityUtils.getTopActivity(), this.configBean.deviceKey, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setDeviceKeyToDevice$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceKeyToDevice$5(Boolean bool) {
        if (bool.booleanValue()) {
            this.configBean.deviceKeyStatus = true;
        } else {
            this.configBean.deviceKeyStatus = false;
        }
        saveDevice();
    }

    private void setEurConfig() {
        if (this.configBean.controlType == 0 || this.configBean.buttonFunction == 0 || this.configBean.zoneNumber == 0) {
            nextStep();
        } else {
            showLoadingDialog();
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).sendEurDeviceType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), this.configBean.zoneNumber, this.configBean.controlType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMeshScanVM.this.lambda$setEurConfig$6((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEurConfig$6(Boolean bool) {
        if (bool.booleanValue()) {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "sendEurConfig success ");
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).sendEurDeviceType(this.configBean.zoneNumber, this.configBean.controlType));
            nextStep();
        } else {
            Injection.mesh().clear();
            LHomeLog.i(Constants.MESH_LOG, getClass(), "sendEurConfig fail ");
            dismissLoadingDialog();
            this.configBean.zoneNumber = 0;
            this.configBean.controlType = 0;
            this.configBean.buttonFunction = 0;
        }
    }

    private void getConfigInfo() {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "============getConfigInfo============");
        this.startScanLiveData.setValue(false);
        if (!this.addSingleDevice) {
            this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_LOADING, 0.2f));
        }
        ((ObservableSubscribeProxy) Injection.net().getProvisioningAddress(ConfigHelper.instance().placeId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda33
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$getConfigInfo$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda34
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$getConfigInfo$8((GetProvisioningAddressResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda35
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$getConfigInfo$9((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfigInfo$7(Disposable disposable) throws Exception {
        if (this.addSingleDevice) {
            showLoadingDialog(getContext().getString(R.string.getting_address));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfigInfo$8(GetProvisioningAddressResponse getProvisioningAddressResponse) throws Exception {
        this.configBean.unicastAddress = Integer.parseInt(getProvisioningAddressResponse.getProvisioneraddress(), 16);
        nextStep();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfigInfo$9(Throwable th) throws Exception {
        setErrorView(getContext().getString(R.string.get_address_fail));
    }

    private void connect() {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "============connect============");
        if (this.addSingleDevice) {
            showLoadingDialog(getContext().getString(R.string.configing));
        } else {
            this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_LOADING, 0.4f));
        }
        this.timer.cancel();
        this.timer.start();
        this.connectFailTime = 0;
        connectDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectDevice() {
        Injection.mesh().connect(this.configBean.configItem.bluetoothDevice, new IConnectBleCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.5
            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice) {
                ActMeshScanVM.this.timer.cancel();
                ActMeshScanVM.this.timer.start();
                if (connectedDevice != null && ActMeshScanVM.this.configBean.configItem.bluetoothDevice.getAddress().equalsIgnoreCase(connectedDevice.getAddress())) {
                    ActMeshScanVM.this.configBean.btModule = btModule;
                }
                LHomeLog.i(Constants.MESH_LOG, ActMeshScanVM.this.getClass(), "============onConnectSuccess============");
                ActMeshScanVM.this.nextStep();
            }

            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectFail() {
                LHomeLog.i(Constants.MESH_LOG, ActMeshScanVM.this.getClass(), "============onConnectFail============");
                if (ActMeshScanVM.this.connectFailTime < 3) {
                    ActMeshScanVM.this.timer.cancel();
                    ActMeshScanVM.this.timer.start();
                    ActMeshScanVM.this.connectFailTime++;
                    ActMeshScanVM.this.connectDevice();
                    return;
                }
                ActMeshScanVM.this.connectFailTime = 0;
                ActMeshScanVM.this.dismissLoadingDialog();
                ActMeshScanVM actMeshScanVM = ActMeshScanVM.this;
                actMeshScanVM.setErrorView(actMeshScanVM.getContext().getString(R.string.connect_fail));
            }
        }, true);
    }

    private void provisioningDevice() {
        if (!this.addSingleDevice) {
            this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_LOADING, 0.6f));
        }
        LHomeLog.i(Constants.MESH_LOG, getClass(), "============provisioningDevice============");
        Injection.mesh().startProvisioning(this.configBean.configItem.bluetoothDevice, this.configBean.unicastAddress, Integer.toString(this.configBean.configItem.productInfo.getAgreementId()), new IProvisioningCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.6
            @Override // com.ltech.smarthome.blemesh.IProvisioningCallback
            public void provisioningSuccess(ProvisionedMeshNode node) {
                LHomeLog.i(Constants.MESH_LOG, ActMeshScanVM.this.getClass(), "provisioningSuccess ok");
                ActMeshScanVM.this.timer.cancel();
                ActMeshScanVM.this.configBean.provisioningSuccess = true;
                ActMeshScanVM.this.configBean.deviceKey = node.getDeviceKey();
                ActMeshScanVM.this.configBean.uuid = node.getUuid();
                if (TextUtils.isEmpty(ActMeshScanVM.this.configBean.uuid)) {
                    provisioningFailed("uuid=" + ActMeshScanVM.this.configBean.uuid);
                    return;
                }
                ActMeshScanVM.this.nextStep();
            }

            @Override // com.ltech.smarthome.blemesh.IProvisioningCallback
            public void provisioningFailed(String errorMsg) {
                Injection.mesh().clear();
                ActMeshScanVM.this.timer.cancel();
                ActMeshScanVM.this.dismissLoadingDialog();
                LHomeLog.i(Constants.MESH_LOG, ActMeshScanVM.this.getClass(), "FeasyController.provisioningFailed: " + errorMsg);
                ActMeshScanVM actMeshScanVM = ActMeshScanVM.this;
                actMeshScanVM.setErrorView(actMeshScanVM.getContext().getString(R.string.add_fail));
            }

            @Override // com.ltech.smarthome.blemesh.IProvisioningCallback
            public void provisioningTestSeq() {
                ActMeshScanVM.this.timer.cancel();
                ActMeshScanVM.this.timer.start();
            }
        });
    }

    private void setPublishAddress() {
        LHomeLog.i(Constants.MESH_LOG, getClass(), "============setPublishAddress============");
        ((ObservableSubscribeProxy) Injection.net().addGroup(this.roomPickHelper.getSelectPlaceId(), this.roomPickHelper.getSelectFloorId(), this.roomPickHelper.getSelectRoomId(), "", ProductId.CC.getModuleType(ProductId.BLE_GROUP_REMOTE_CONTROLLER), ProductId.CC.getControlType(ProductId.BLE_GROUP_REMOTE_CONTROLLER), new ArrayList()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new ActMeshScanVM$$ExternalSyntheticLambda37(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda28
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$setPublishAddress$11((AddGroupResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.7
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                Injection.mesh().clear();
                ActMeshScanVM.this.setErrorView(this.errorMessage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPublishAddress$11(AddGroupResponse addGroupResponse) throws Exception {
        this.configBean.groupAddress = Integer.parseInt(addGroupResponse.getIndex(), 16);
        this.configBean.groupId = addGroupResponse.getGroupid();
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).setPublishAddress(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), this.configBean.groupAddress, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda32
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setPublishAddress$10((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPublishAddress$10(Boolean bool) {
        if (bool.booleanValue()) {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "setPublishAddress success ");
            nextStep();
        } else {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "setPublishAddress fail ");
            Injection.mesh().clear();
            setErrorView(getContext().getString(R.string.subscribe_fail));
        }
    }

    public void setTrigType(final int outputType) {
        showLoadingDialog(getContext().getString(R.string.device_set_type));
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTrigType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), outputType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setTrigType$12(outputType, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTrigType$12(int i, Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTrigType(i));
        } else {
            dismissLoadingDialog();
            setErrorView(getContext().getString(R.string.set_device_type_fail));
        }
    }

    public void setTbType(final int outputType) {
        showLoadingDialog(getContext().getString(R.string.device_set_type));
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTbType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), outputType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setTbType$13(outputType, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTbType$13(int i, Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTbType(i));
        } else {
            dismissLoadingDialog();
            setErrorView(getContext().getString(R.string.set_device_type_fail));
        }
    }

    /* renamed from: setEurParam, reason: merged with bridge method [inline-methods] */
    public void lambda$showConfigDialog$23(int zoneControl, int controlType) {
        showLoadingDialog();
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).sendEurDeviceType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), zoneControl, controlType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda23
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setEurParam$14((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEurParam$14(Boolean bool) {
        dismissLoadingDialog();
    }

    /* renamed from: setType, reason: merged with bridge method [inline-methods] */
    public void lambda$showConfigDialog$21(int outputType, final int deviceType, boolean outputChange) {
        showLoadingDialog(getContext().getString(R.string.device_set_type));
        if (outputChange) {
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).setOutputType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), outputType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda24
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMeshScanVM.this.lambda$setType$16(deviceType, (Boolean) obj);
                }
            });
            return;
        }
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
        Activity topActivity = ActivityUtils.getTopActivity();
        CtrlPackage createCtrlPackage = this.configBean.createCtrlPackage();
        if (deviceType == 31) {
            deviceType = 6;
        }
        settingCmdAssistant.setDeviceType(topActivity, createCtrlPackage, deviceType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda25
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setType$17((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setType$16(int i, Boolean bool) {
        if (bool.booleanValue()) {
            SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(null, new int[0]);
            Activity topActivity = ActivityUtils.getTopActivity();
            CtrlPackage createCtrlPackage = this.configBean.createCtrlPackage();
            if (i == 31) {
                i = 6;
            }
            settingCmdAssistant.setDeviceType(topActivity, createCtrlPackage, i, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMeshScanVM.this.lambda$setType$15((Boolean) obj);
                }
            });
            return;
        }
        dismissLoadingDialog();
        setErrorView(getContext().getString(R.string.set_device_type_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setType$15(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
        } else {
            dismissLoadingDialog();
            setErrorView(getContext().getString(R.string.set_device_type_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setType$17(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
        } else {
            dismissLoadingDialog();
            setErrorView(getContext().getString(R.string.set_device_type_fail));
        }
    }

    private void setOutputType() {
        if (this.configBean.outputType == 0) {
            nextStep();
            return;
        }
        showLoadingDialog(getContext().getString(R.string.device_set_type));
        LHomeLog.i(Constants.MESH_LOG, getClass(), "============setOutputType============");
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).setOutputType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), this.configBean.outputType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setOutputType$18((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOutputType$18(Boolean bool) {
        if (bool.booleanValue()) {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "setOutputType success ");
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.OUTPUT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setOutputType(this.configBean.outputType));
            nextStep();
        } else {
            Injection.mesh().clear();
            LHomeLog.i(Constants.MESH_LOG, getClass(), "setOutputType fail ");
            dismissLoadingDialog();
            this.configBean.outputType = 0;
            setErrorView(getContext().getString(R.string.set_device_type_fail));
        }
    }

    private void setDeviceType() {
        if (this.configBean.deviceType == 0) {
            nextStep();
            return;
        }
        if (this.configBean.outputType == 0) {
            showLoadingDialog(getContext().getString(R.string.device_set_type));
        }
        LHomeLog.i(Constants.MESH_LOG, getClass(), "============setDeviceType============");
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).setDeviceType(ActivityUtils.getTopActivity(), this.configBean.createCtrlPackage(), this.configBean.deviceType == 31 ? 6 : this.configBean.deviceType, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$setDeviceType$19((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceType$19(Boolean bool) {
        if (bool.booleanValue()) {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "setDeviceType success ");
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setDeviceType(this.configBean.deviceType == 31 ? 6 : this.configBean.deviceType));
            dismissLoadingDialog();
            nextStep();
            return;
        }
        Injection.mesh().clear();
        LHomeLog.i(Constants.MESH_LOG, getClass(), "setDeviceType fail");
        dismissLoadingDialog();
        this.configBean.deviceType = 0;
        setErrorView(getContext().getString(R.string.set_device_type_fail));
    }

    public void showConfigDialog(boolean needLocation) {
        int i;
        final SetBleTypeDialog onSaveListener = SetBleTypeDialog.asDefault().setContent(this.configBean.configItem.name).setLocation(needLocation).setDeviceIndex(this.configBean.configItem.deviceIndex).setOnLocationListener(new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$showConfigDialog$20((Void) obj);
            }
        }).setOnSaveListener(new SetBleTypeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.8
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public boolean onSave(String name, boolean changeType, int type, int outputType, int floorPos, int roomPos, int zoneControl, int controlType) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                ActMeshScanVM.this.roomPickHelper.setSelectPosition(floorPos, roomPos);
                ActMeshScanVM.this.configBean.floorId = ActMeshScanVM.this.roomPickHelper.getSelectFloorId();
                ActMeshScanVM.this.configBean.roomId = ActMeshScanVM.this.roomPickHelper.getSelectRoomId();
                if (changeType || ActMeshScanVM.this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT) || ActMeshScanVM.this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE) || ActMeshScanVM.this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    ActMeshScanVM.this.configBean.deviceType = type;
                }
                ActMeshScanVM.this.configBean.outputType = outputType;
                ActMeshScanVM.this.configBean.deviceName = name;
                ActMeshScanVM.this.configBean.zoneNumber = zoneControl;
                ActMeshScanVM.this.configBean.controlType = controlType;
                ActMeshScanVM.this.configBean.buttonFunction = controlType <= 2 ? 1 : 2;
                ActMeshScanVM.this.nextStep();
                return true;
            }

            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public void cancel() {
                Injection.mesh().clear();
                ActMeshScanVM.this.setErrorView(StringUtils.getString(R.string.cancel_add));
                ActMeshScanVM.this.deviceList.remove(ActMeshScanVM.this.configBean.configItem);
                ActMeshScanVM.this.deviceNum.setValue(ActMeshScanVM.this.getContext().getString(R.string.scan_device_num, Integer.valueOf(ActMeshScanVM.this.deviceList.size())));
            }
        });
        if (this.configBean.configItem.bluetoothDevice.getControlType() != 0) {
            if (this.configBean.configItem.productInfo.getProductKey().equals("02") && this.configBean.configItem.productInfo.getSubProductKey().equals("06")) {
                onSaveListener.setOnTypeChangeListener(new SetBleTypeDialog.OnChangeTypeListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnChangeTypeListener
                    public final void setType(int i2, int i3, boolean z) {
                        ActMeshScanVM.this.lambda$showConfigDialog$21(i2, i3, z);
                    }
                });
                i = 3;
            } else {
                i = 0;
            }
            onSaveListener.controlType(this.configBean.configItem.bluetoothDevice.getControlType(), this.configBean.configItem.bluetoothDevice.getChangeType(), i);
        }
        if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
            onSaveListener.setIsDryToBle(true);
        }
        if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT) || this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
            onSaveListener.setOnChangeTrigTypeListener(new SetBleTypeDialog.OnChangeTrigTypeListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnChangeTrigTypeListener
                public final void setType(int i2, boolean z) {
                    ActMeshScanVM.this.lambda$showConfigDialog$22(i2, z);
                }
            });
        }
        if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT) || this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
            onSaveListener.subType(0);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_SMART_PANEL_G4TE)) {
            onSaveListener.g4teType(1);
        }
        if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_EUR_PANEL_EB1)) {
            onSaveListener.eurPanelType(1);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_EUR_PANEL_EB2)) {
            onSaveListener.eurPanelType(2);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_EUR_PANEL_EB5)) {
            onSaveListener.eurPanelType(5);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_EUR_PANEL_EB6)) {
            onSaveListener.eurPanelType(6);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_RC_B5)) {
            onSaveListener.eurPanelType(11);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_AS_PANEL_U4S)) {
            onSaveListener.setAsPanelType(4);
        } else if (this.configBean.configItem.productInfo.getProductId().equals(ProductId.ID_AS_PANEL_U5S)) {
            onSaveListener.setAsPanelType(5);
        }
        if (onSaveListener.getEurPanelType() > 0 || onSaveListener.getAsPanelType() > 0) {
            onSaveListener.setOnEurPanelSelectListener(new SetBleTypeDialog.OnEurPanelSelectListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnEurPanelSelectListener
                public final void onSelect(int i2, int i3) {
                    ActMeshScanVM.this.lambda$showConfigDialog$23(i2, i3);
                }
            });
        }
        onSaveListener.setProductId(this.configBean.configItem.productInfo.getProductId());
        RoomPickHelper roomPickHelper = this.roomPickHelper;
        long j = this.floorId;
        if (j <= 0) {
            j = ConfigHelper.instance().floorId;
        }
        roomPickHelper.setCurrentFloorIdPos(j);
        RoomPickHelper roomPickHelper2 = this.roomPickHelper;
        long j2 = this.floorId;
        if (j2 <= 0) {
            j2 = ConfigHelper.instance().floorId;
        }
        long j3 = this.roomId;
        if (j3 <= 0) {
            j3 = ConfigHelper.instance().roomId;
        }
        roomPickHelper2.setCurrentRoomIdPos(j2, j3);
        onSaveListener.setSelectRoom(this.roomPickHelper.canSetRoom()).setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new SetBleTypeDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSelectFloorListener
            public final void selectFloor(SetBleTypeDialog setBleTypeDialog, int i2, String str) {
                ActMeshScanVM.this.lambda$showConfigDialog$24(onSaveListener, setBleTypeDialog, i2, str);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConfigDialog$20(Void r1) {
        location(this.configBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConfigDialog$22(int i, boolean z) {
        if (z) {
            setTbType(i + 1);
        } else {
            setTrigType(i + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConfigDialog$24(SetBleTypeDialog setBleTypeDialog, SetBleTypeDialog setBleTypeDialog2, int i, String str) {
        setBleTypeDialog.setRoomList(this.roomPickHelper.getRoomNames(i));
        setBleTypeDialog.initRoomData();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02cd, code lost:
    
        if (r2.equals(com.ltech.smarthome.model.product.ProductId.ID_SMART_SWITCH_S1_PRO) == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void saveDevice() {
        /*
            Method dump skipped, instructions count: 1406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.config.ActMeshScanVM.saveDevice():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDevice$25(Disposable disposable) throws Exception {
        if (this.addSingleDevice) {
            showLoadingDialog(getContext().getString(R.string.adding));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDevice$26(AddDeviceResponse addDeviceResponse) throws Exception {
        LHomeLog.i(getClass(), "response=" + addDeviceResponse.toString());
        Device addDevice = ConfigHelper.instance().addDevice(addDeviceResponse, new String[0]);
        if (addDevice.getProductId().equals(ProductId.ID_BLE_LIGHT_CT)) {
            setCtRangeValue(addDevice);
        }
        if (addDevice.getProductId().equals(ProductId.ID_HOME_KIT)) {
            setServer(addDevice);
        }
        queryVersion(addDevice);
        if (this.addSingleDevice) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), addDevice.getDeviceId());
            nextStep();
        } else {
            configDevice(ConfigBean.END);
        }
    }

    private void queryVersion(final Device device) {
        LHomeLog.i(getClass(), "send getQueryCmdAssistant:" + device.getDeviceName());
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshScanVM.this.lambda$queryVersion$27(device, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryVersion$27(Device device, ResponseMsg responseMsg) {
        ProductVersionInfo parserUpgradeInfo;
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg);
        if (responseMsg == null) {
            return;
        }
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg.getAgreementId());
        if (responseMsg.getResData().length() < 16) {
            LHomeLog.i(getClass(), "msg.getResData().length()<16 resData-->" + responseMsg.getResData());
            parserUpgradeInfo = null;
        } else {
            LHomeLog.i(getClass(), "msg.getResData() current, resData-->" + responseMsg.getResData());
            parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(com.smart.message.utils.StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        if (parserUpgradeInfo == null) {
            return;
        }
        changeDeviceVersion(device, parserUpgradeInfo.getsVer(), parserUpgradeInfo.gethVer(), parserUpgradeInfo.getDeviceModel());
    }

    private void changeDeviceVersion(final Device device, final String sVersion, final String hVersion, String deviceModel) {
        if (device != null) {
            if (TextUtils.isEmpty(device.getMcuversion()) || TextUtils.isEmpty(device.getFirmwareversion())) {
                final JSONObject parseObject = device.getExtParam() != null ? JSONObject.parseObject(device.getExtParam()) : new JSONObject();
                parseObject.put("binName", (Object) deviceModel);
                ((ObservableSubscribeProxy) Injection.net().updateDeviceVersionAndParamExt(device.getDeviceId(), sVersion, hVersion, GsonUtils.toJson(parseObject)).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda29
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActMeshScanVM.lambda$changeDeviceVersion$28(Device.this, sVersion, hVersion, parseObject, obj);
                    }
                }, new SmartErrorComsumer());
            }
        }
    }

    static /* synthetic */ void lambda$changeDeviceVersion$28(Device device, String str, String str2, JSONObject jSONObject, Object obj) throws Exception {
        device.setMcuversion(str);
        device.setFirmwareversion(str2);
        device.setExtParam(jSONObject.toString());
        Injection.repo().device().saveDevice(device);
    }

    private void setServer(Device device) {
        int i;
        if (ApiConstants.isTestNode()) {
            i = 3;
        } else {
            i = !ApiConstants.isChinaNode() ? 2 : 1;
        }
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).setServer(ActivityUtils.getTopActivity(), i, new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.10
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
            }
        });
    }

    /* renamed from: com.ltech.smarthome.ui.config.ActMeshScanVM$11, reason: invalid class name */
    class AnonymousClass11 implements IAction<ResponseMsg> {
        final /* synthetic */ Device val$device;

        AnonymousClass11(final Device val$device) {
            this.val$device = val$device;
        }

        @Override // com.ltech.smarthome.base.IAction
        public void act(ResponseMsg responseMsg) {
            if (responseMsg == null || responseMsg.getResData().length() < 28) {
                return;
            }
            final int parseInt = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16);
            final int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(24, 28), 16);
            if (parseInt < 1000 || parseInt > 10000) {
                parseInt = 1000;
            }
            if (parseInt2 < parseInt || parseInt2 > 10000) {
                parseInt2 = 10000;
            }
            ObservableSubscribeProxy observableSubscribeProxy = (ObservableSubscribeProxy) Injection.net().updateCtRange(this.val$device.getDeviceId(), parseInt2, parseInt).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActMeshScanVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)));
            final Device device = this.val$device;
            observableSubscribeProxy.subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$11$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActMeshScanVM.AnonymousClass11.lambda$act$0(Device.this, parseInt2, parseInt, obj);
                }
            }, new SmartErrorComsumer());
            LHomeLog.e(getClass(), "支持色温调节" + parseInt + ContainerUtils.KEY_VALUE_DELIMITER + parseInt2);
        }

        static /* synthetic */ void lambda$act$0(Device device, int i, int i2, Object obj) throws Exception {
            device.setMaxkelvin(i);
            device.setMinkelvin(i2);
            Injection.repo().device().saveDevice(device);
        }
    }

    private void setCtRangeValue(Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryCtRange(ActivityUtils.getTopActivity(), new AnonymousClass11(device));
    }

    private String getSmartPanelExParam(String productId) {
        return RelateInfoUtils.initSmartPanelRelateInfoList(productId).getExtParamString();
    }

    private String getEurPanelExParam(int zoneNumber, int colorType) {
        return RelateInfoUtils.initEurPanel(zoneNumber, colorType).getExtParamString();
    }

    private String getAsPanelExParam(int zoneNumber, int colorType) {
        return RelateInfoUtils.initEurPanel(zoneNumber, colorType).getExtParamString();
    }

    private String getCodeLibraryData(String productId, int unicastAddress) {
        productId.hashCode();
        if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3) || productId.equals(ProductId.ID_BLE_CURTAIN)) {
            return CodeLibraryUtil.generateCurtainCodeLibrary(unicastAddress);
        }
        return "{}";
    }

    private void saveMeshParam() {
        final SuperPanelBleParam superPanelBleParam = (SuperPanelBleParam) this.configBean.createBleParam();
        ((ObservableSubscribeProxy) Injection.net().updateParam(this.filerDevice.getDeviceId(), GsonUtils.toJson(this.configBean.groupId == 0 ? HelpUtils.removeObjectKey(superPanelBleParam, "groupId") : superPanelBleParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$saveMeshParam$29((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActMeshScanVM$$ExternalSyntheticLambda37(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$saveMeshParam$30(superPanelBleParam, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.12
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActMeshScanVM.this.setErrorView(this.errorMessage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveMeshParam$29(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveMeshParam$30(SuperPanelBleParam superPanelBleParam, Object obj) throws Exception {
        this.filerDevice.setParam(superPanelBleParam);
        Injection.repo().device().saveDevice(this.filerDevice);
        nextStep();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSuccessView() {
        if (this.addSingleDevice) {
            dismissLoadingDialog();
            showSuccessTipDialog(getContext().getString(R.string.config_success));
        } else {
            this.numSuccessDevice.setValue(Integer.valueOf(this.numSuccessDevice.getValue().intValue() + 1));
            if (this.scanItems.size() == 0) {
                this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_ALL_SUCCESS, 100.0f));
            }
            this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_SUCCESS, 100.0f));
            startAddDeviceAll(false);
        }
        this.configBean.configItem.config = true;
        this.configBean.configItem.name = this.configBean.deviceName;
        if (this.deviceList.size() > this.configBean.configPos) {
            this.deviceList.set(this.configBean.configPos, this.configBean.configItem);
        }
        this.startScanLiveData.setValue(true);
    }

    private void setSuperPanel() {
        if (ProductRepository.isDcaSuperPanel(this.configBean.configItem.productInfo.getProductId())) {
            final SuperPanelExtParam superPanelExtParam = new SuperPanelExtParam();
            int i = 0;
            if (this.filerDevice.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || this.filerDevice.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                superPanelExtParam.setZoneNumber(4);
                while (i < 4) {
                    RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
                    if (i == 0) {
                        relateInfo.name = getContext().getString(R.string.app_str_smart_panel_switch1);
                    } else if (i == 1) {
                        relateInfo.name = getContext().getString(R.string.app_str_smart_panel_switch2);
                    } else if (i == 2) {
                        relateInfo.name = getContext().getString(R.string.app_str_smart_panel_switch3);
                    } else {
                        relateInfo.name = getContext().getString(R.string.app_str_smart_panel_switch4);
                    }
                    i++;
                    superPanelExtParam.setRelateInfo(i, relateInfo);
                }
            } else {
                superPanelExtParam.setZoneNumber(2);
                while (i < 2) {
                    RelatedInfoExtParam.RelateInfo relateInfo2 = new RelatedInfoExtParam.RelateInfo();
                    if (i == 0) {
                        relateInfo2.name = getContext().getString(R.string.app_str_smart_panel_switch1);
                    } else {
                        relateInfo2.name = getContext().getString(R.string.app_str_smart_panel_switch2);
                    }
                    i++;
                    superPanelExtParam.setRelateInfo(i, relateInfo2);
                }
            }
            ((ObservableSubscribeProxy) Injection.net().updateParamExtAndCodeLibrary(this.filerDevice.getDeviceId(), superPanelExtParam.getParamString(), CodeLibraryUtil.getSmartPanelCodeLibrary(this.configBean.unicastAddress, this.configBean.configItem.productInfo.getProductId()), this.filerDevice.getLatitude(), this.filerDevice.getLongitude()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda26
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActMeshScanVM.this.lambda$setSuperPanel$31(superPanelExtParam, obj);
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().updateLocation(this.filerDevice.getDeviceId(), this.filerDevice.getLatitude(), this.filerDevice.getLongitude()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda27
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActMeshScanVM.lambda$setSuperPanel$32(obj);
                }
            });
        }
        dismissLoadingDialog();
        if (ConfigHelper.instance().roomId > 0) {
            this.showSuperPanelConfigSuccess.call();
        }
        this.configBean.configItem.config = true;
        this.configBean.configItem.name = this.configBean.deviceName;
        if (this.deviceList.size() > this.configBean.configPos) {
            this.deviceList.set(this.configBean.configPos, this.configBean.configItem);
        }
        this.startScanLiveData.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSuperPanel$31(SuperPanelExtParam superPanelExtParam, Object obj) throws Exception {
        this.filerDevice.setExtParam(superPanelExtParam.getParamString());
        Injection.repo().device().saveDevice(this.filerDevice);
    }

    public void setErrorView(final String errorMsg) {
        this.timer.cancel();
        LHomeLog.i(Constants.MESH_LOG, getClass(), "setErrorView ");
        if (this.addSingleDevice) {
            dismissLoadingDialog();
            if (!TextUtils.isEmpty(errorMsg)) {
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActMeshScanVM.this.lambda$setErrorView$33(errorMsg);
                    }
                }, 200L);
            }
        } else {
            this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_FAILED, 0.0f));
            startAddDeviceAll(false);
        }
        ThreadUtils.getMainHandler().postDelayed(new AnonymousClass13(errorMsg), 500L);
        if (this.configBean.groupAddress != 0) {
            ((ObservableSubscribeProxy) Injection.net().deleteGroup(this.configBean.groupId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda31
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActMeshScanVM.lambda$setErrorView$34(obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setErrorView$33(String str) {
        showErrorTipDialog(str);
    }

    /* renamed from: com.ltech.smarthome.ui.config.ActMeshScanVM$13, reason: invalid class name */
    class AnonymousClass13 implements Runnable {
        final /* synthetic */ String val$errorMsg;

        AnonymousClass13(final String val$errorMsg) {
            this.val$errorMsg = val$errorMsg;
        }

        @Override // java.lang.Runnable
        public void run() {
            ActMeshScanVM.this.showLoadingDialog();
            Injection.mesh().resetNode(ActMeshScanVM.this.configBean.unicastAddress, new IResetNodeCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.13.1
                @Override // com.ltech.smarthome.blemesh.IResetNodeCallback
                public void resetSuccess() {
                    ActMeshScanVM.this.dismissDialog(AnonymousClass13.this.val$errorMsg);
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "has connect after resetNode");
                    Injection.mesh().disconnect();
                    ActMeshScanVM.this.startScanLiveData.setValue(true);
                }

                @Override // com.ltech.smarthome.blemesh.IResetNodeCallback
                public void resetFail() {
                    LHomeLog.i(Constants.MESH_LOG, getClass(), "has connect resetNode fail after deleteNode");
                    Injection.mesh().removeNode(ActMeshScanVM.this.configBean.unicastAddress, new IRemoveNodeCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.13.1.1
                        @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                        public void removeSuccess() {
                            ActMeshScanVM.this.dismissDialog(AnonymousClass13.this.val$errorMsg);
                            Injection.mesh().disconnect();
                            ActMeshScanVM.this.startScanLiveData.setValue(true);
                        }

                        @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                        public void removeFail() {
                            ActMeshScanVM.this.dismissDialog(AnonymousClass13.this.val$errorMsg);
                            Injection.mesh().disconnect();
                            ActMeshScanVM.this.startScanLiveData.setValue(true);
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog(final String errorMsg) {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.14
            @Override // java.lang.Runnable
            public void run() {
                if (!TextUtils.isEmpty(errorMsg)) {
                    ActMeshScanVM.this.showErrorTipDialog(errorMsg);
                } else {
                    ActMeshScanVM.this.dismissLoadingDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetNode() {
        Injection.mesh().resetNode(this.configBean.unicastAddress, new IResetNodeCallback(this) { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.15
            @Override // com.ltech.smarthome.blemesh.IResetNodeCallback
            public void resetSuccess() {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "has connect after resetNode");
            }

            @Override // com.ltech.smarthome.blemesh.IResetNodeCallback
            public void resetFail() {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "has connect resetNode fail after deleteNode");
            }
        });
    }

    private void reconnect() {
        Injection.mesh().connect(this.configBean.configItem.bluetoothDevice, new IConnectBleCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM.16
            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "has connect resetNode fail reconnect onConnectSuccess");
                ActMeshScanVM.this.resetNode();
            }

            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectFail() {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "has connect resetNode fail reconnect onConnectFail");
            }
        });
    }

    public void nextStep() {
        if (this.addSingleDevice || !this.addFinish) {
            configDevice(this.configBean.nextState());
        }
    }

    public void syncDeviceAndGroup() {
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(this.filerDevice.getPlaceId(), this.filerDevice.getFloorId(), this.filerDevice.getRoomId());
        List<Group> groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(this.filerDevice.getPlaceId(), this.filerDevice.getFloorId(), this.filerDevice.getRoomId());
        List<Scene> sceneListByRoomId = Injection.repo().scene().getSceneListByRoomId(this.filerDevice.getPlaceId(), this.filerDevice.getFloorId(), this.filerDevice.getRoomId(), new boolean[0]);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Device device : deviceListByRoomIdFromDb) {
            if (device.getDeviceId() != this.filerDevice.getDeviceId() && ProductRepository.filterDeviceForSuperPanel(device, this.filerDevice)) {
                arrayList.add(Long.valueOf(device.getDeviceId()));
            }
        }
        for (Group group : groupListByRoomIdFromDb) {
            if (ProductRepository.filterGroupForSuperPanel(group, this.filerDevice)) {
                arrayList2.add(Long.valueOf(group.getGroupId()));
            }
        }
        Iterator<Scene> it = sceneListByRoomId.iterator();
        while (it.hasNext()) {
            arrayList3.add(Long.valueOf(it.next().getSceneId()));
        }
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelDeviceGroupScene(this.filerDevice.getDeviceId(), arrayList, arrayList2, arrayList3).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$syncDeviceAndGroup$35((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActMeshScanVM$$ExternalSyntheticLambda37(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanVM.this.lambda$syncDeviceAndGroup$36((SetSuperPanelResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDeviceAndGroup$35(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDeviceAndGroup$36(SetSuperPanelResponse setSuperPanelResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (setSuperPanelResponse.getDevices() != null) {
            Iterator<SetSuperPanelResponse.DevicesBean> it = setSuperPanelResponse.getDevices().iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().getDeviceid()));
            }
        }
        if (setSuperPanelResponse.getGroups() != null) {
            Iterator<SetSuperPanelResponse.GroupsBean> it2 = setSuperPanelResponse.getGroups().iterator();
            while (it2.hasNext()) {
                arrayList2.add(Long.valueOf(it2.next().getGroupid()));
            }
        }
        if (setSuperPanelResponse.getScenes() != null) {
            Iterator<SetSuperPanelResponse.ScenesBean> it3 = setSuperPanelResponse.getScenes().iterator();
            while (it3.hasNext()) {
                arrayList3.add(Long.valueOf(it3.next().getSceneid()));
            }
        }
        Injection.repo().device().setSuperPanelDeviceAndGroup(setSuperPanelResponse.getInfo().getPanelid(), arrayList, arrayList2);
        Injection.repo().device().setSuperPanelScene(setSuperPanelResponse.getInfo().getPanelid(), arrayList3);
        SmartToast.showShort(StringUtils.getString(R.string.save_success));
        finishActivity();
    }

    public static final class ScanItem {
        public boolean autoAdd;
        public ExtendedBluetoothDevice bluetoothDevice;
        public boolean config;
        public Device device;
        public int extData;
        public boolean isPROXY;
        public String name;
        public ProductInfo productInfo;
        public float progress;
        public boolean supportBleZip;
        public int unicastAdd;
        public int upgradeFlag;
        public UpgradeInfo upgradeInfo;
        public int deviceIndex = -1;
        public String currentVersion = "--";
        public String newVersion = "--";
        public boolean supportBle = false;

        public ScanItem() {
        }

        public ScanItem(ExtendedBluetoothDevice device, boolean isPROXY) {
            this.isPROXY = isPROXY;
            this.bluetoothDevice = device;
        }

        public ScanItem(ExtendedBluetoothDevice device, boolean isPROXY, int unicastAdd) {
            this.isPROXY = isPROXY;
            this.bluetoothDevice = device;
            this.unicastAdd = unicastAdd;
        }

        public boolean matches(ExtendedBluetoothDevice device) {
            return this.bluetoothDevice.getAddress().equals(device.getAddress());
        }

        public void clearVersionInfo() {
            this.currentVersion = "--";
            this.newVersion = "--";
            this.upgradeFlag = 0;
        }
    }

    public static final class ConfigBean {
        public static final int CONNECT = 2;
        public static final int END = 9999;
        public static final int GET_CONFIG_INFO = 1;
        public static final int PROVISIONING = 3;
        public static final int SAVE_DEVICE = 6;
        public static final int SAVE_MESH_PARAM = 8;
        public static final int SET_DEVICE_TYPE = 5;
        public static final int SET_EUR_DEVICE_TYPE = 11;
        public static final int SET_OUTPUT_TYPE = 9;
        public static final int SET_PUBLISH_ADDRESS = 4;
        public static final int SHOW_EDIT_DIALOG = 7;
        public static final int SYNC_DEVICE = 10;
        public String btModule;
        public int buttonFunction;
        public ScanItem configItem;
        public int configPos;
        public int controlType;
        public byte[] deviceKey;
        public boolean deviceKeyStatus;
        public String deviceName;
        public int deviceType;
        public long floorId;
        public int groupAddress;
        public long groupId;
        public int outputType;
        public long roomId;
        public int unicastAddress;
        public String uuid;
        public int zoneNumber;
        private int statePos = -1;
        private List<Integer> processList = new ArrayList();
        public boolean provisioningSuccess = false;

        private ConfigBean(ScanItem scanItem, int position) {
            this.configItem = scanItem;
            this.configPos = position;
            this.deviceName = scanItem.name;
        }

        public static ConfigBean processOne(ScanItem scanItem, int position) {
            ConfigBean configBean = new ConfigBean(scanItem, position);
            configBean.processList.add(1);
            configBean.processList.add(2);
            configBean.processList.add(3);
            configBean.processList.add(7);
            configBean.processList.add(6);
            configBean.processList.add(Integer.valueOf(END));
            return configBean;
        }

        public static ConfigBean processTwo(ScanItem scanItem, int position) {
            ConfigBean configBean = new ConfigBean(scanItem, position);
            configBean.processList.add(1);
            configBean.processList.add(2);
            configBean.processList.add(3);
            configBean.processList.add(7);
            if (scanItem.productInfo.getSubProductKey().equals("06") || scanItem.productInfo.getProductId().equals(ProductId.ID_KNOB_PANEL_E6T)) {
                configBean.processList.add(9);
            }
            configBean.processList.add(5);
            configBean.processList.add(6);
            configBean.processList.add(Integer.valueOf(END));
            return configBean;
        }

        public static ConfigBean processThree(ScanItem scanItem, int position) {
            ConfigBean configBean = new ConfigBean(scanItem, position);
            configBean.processList.add(1);
            configBean.processList.add(2);
            configBean.processList.add(3);
            configBean.processList.add(4);
            configBean.processList.add(7);
            configBean.processList.add(6);
            configBean.processList.add(Integer.valueOf(END));
            return configBean;
        }

        public static ConfigBean processFour(ScanItem scanItem, int position) {
            ConfigBean configBean = new ConfigBean(scanItem, position);
            configBean.processList.add(1);
            configBean.processList.add(2);
            configBean.processList.add(3);
            configBean.processList.add(8);
            configBean.processList.add(10);
            return configBean;
        }

        public static ConfigBean processFive(ScanItem scanItem, int position) {
            ConfigBean configBean = new ConfigBean(scanItem, position);
            configBean.processList.add(1);
            configBean.processList.add(2);
            configBean.processList.add(3);
            if (!scanItem.productInfo.getProductId().equals(ProductId.ID_EUR_PANEL_EB6)) {
                configBean.processList.add(4);
            }
            configBean.processList.add(7);
            configBean.processList.add(11);
            configBean.processList.add(6);
            configBean.processList.add(Integer.valueOf(END));
            return configBean;
        }

        public int nextState() {
            int i = this.statePos + 1;
            this.statePos = i;
            if (i >= this.processList.size()) {
                this.statePos = this.processList.size() - 1;
            }
            return this.processList.get(this.statePos).intValue();
        }

        /* JADX WARN: Code restructure failed: missing block: B:67:0x0078, code lost:
        
            if (com.ltech.smarthome.model.repo.ProductRepository.isEurPanel(r0) != false) goto L29;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.ltech.smarthome.model.device_param.BleParam createBleParam() {
            /*
                Method dump skipped, instructions count: 381
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.config.ActMeshScanVM.ConfigBean.createBleParam():com.ltech.smarthome.model.device_param.BleParam");
        }

        public CtrlPackage createCtrlPackage() {
            CtrlPackage ctrlPackage = new CtrlPackage(this.configItem.productInfo.getAgreementId());
            ctrlPackage.setAddress(this.unicastAddress);
            ctrlPackage.setControlType(2);
            return ctrlPackage;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private ConfigBean getConfigBean(String productId, ScanItem item, int position) {
        char c2;
        switch (productId.hashCode()) {
            case -2060969856:
                if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -1281119909:
                if (productId.equals(ProductId.ID_RC_B2)) {
                    c2 = 28;
                    break;
                }
                c2 = 65535;
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -852623517:
                if (productId.equals(ProductId.ID_RC4S)) {
                    c2 = 30;
                    break;
                }
                c2 = 65535;
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -249671171:
                if (productId.equals(ProductId.ID_RC_B5)) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = 29;
                    break;
                }
                c2 = 65535;
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 31;
                    break;
                }
                c2 = 65535;
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = ' ';
                    break;
                }
                c2 = 65535;
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = '!';
                    break;
                }
                c2 = 65535;
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = '\"';
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = '$';
                    break;
                }
                c2 = 65535;
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = '#';
                    break;
                }
                c2 = 65535;
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                c2 = 65535;
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '%';
                    break;
                }
                c2 = 65535;
                break;
            case 1473345811:
                if (productId.equals(ProductId.ID_RC_B1)) {
                    c2 = 27;
                    break;
                }
                c2 = 65535;
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return ConfigBean.processFour(item, position);
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
                return ConfigBean.processTwo(item, position);
            case 17:
            case 18:
            case 19:
                return ConfigBean.processFive(item, position);
            case 20:
                if (item.bluetoothDevice.getChangeType() == 12) {
                    return ConfigBean.processFive(item, position);
                }
                return ConfigBean.processThree(item, position);
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
                return ConfigBean.processThree(item, position);
            default:
                return ConfigBean.processOne(item, position);
        }
    }

    private String codeLibraryData(int address, String DeviceType) {
        Gson gson = new Gson();
        CodeLibraryBean codeLibraryBean = new CodeLibraryBean();
        int parseInt = Integer.parseInt(DeviceType);
        if (parseInt == 1) {
            codeLibraryBean.m607set(gson.toJson(new CharSwitchBean(getOnoff(false, address))));
            codeLibraryBean.m608set(gson.toJson(new CharSwitchBean(getOnoff(true, address))));
            codeLibraryBean.m596set0(gson.toJson(new CharSwitchBean(getbrightnessE0(1, address))));
            codeLibraryBean.m597set10(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(20), address))));
            codeLibraryBean.m599set20(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(30), address))));
            codeLibraryBean.m600set30(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(40), address))));
            codeLibraryBean.m601set40(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(50), address))));
            codeLibraryBean.m602set50(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(60), address))));
            codeLibraryBean.m603set60(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(70), address))));
            codeLibraryBean.m604set70(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(80), address))));
            codeLibraryBean.m605set80(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(90), address))));
            codeLibraryBean.m606set90(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(100), address))));
            codeLibraryBean.m598set100(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(110), address))));
        } else if (parseInt == 2) {
            codeLibraryBean.m607set(gson.toJson(new CharSwitchBean(getOnoff(false, address))));
            codeLibraryBean.m608set(gson.toJson(new CharSwitchBean(getOnoff(true, address))));
            codeLibraryBean.m596set0(gson.toJson(new CharSwitchBean(getbrightnessDF(1, address))));
            codeLibraryBean.m597set10(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(20), address))));
            codeLibraryBean.m599set20(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(30), address))));
            codeLibraryBean.m600set30(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(40), address))));
            codeLibraryBean.m601set40(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(50), address))));
            codeLibraryBean.m602set50(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(60), address))));
            codeLibraryBean.m603set60(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(70), address))));
            codeLibraryBean.m604set70(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(80), address))));
            codeLibraryBean.m605set80(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(90), address))));
            codeLibraryBean.m606set90(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(100), address))));
            codeLibraryBean.m598set100(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(110), address))));
            codeLibraryBean.m610set(gson.toJson(new CharSwitchBean(getColorDE(255, 0, address))));
            codeLibraryBean.m616set(gson.toJson(new CharSwitchBean(getColorDE(0, 255, address))));
        } else if (parseInt == 3 || parseInt == 4 || parseInt == 5) {
            codeLibraryBean.m607set(gson.toJson(new CharSwitchBean(getOnoff(false, address))));
            codeLibraryBean.m608set(gson.toJson(new CharSwitchBean(getOnoff(true, address))));
            codeLibraryBean.m596set0(gson.toJson(new CharSwitchBean(getbrightness(1, address))));
            codeLibraryBean.m597set10(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(20), address))));
            codeLibraryBean.m599set20(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(30), address))));
            codeLibraryBean.m600set30(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(40), address))));
            codeLibraryBean.m601set40(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(50), address))));
            codeLibraryBean.m602set50(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(60), address))));
            codeLibraryBean.m603set60(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(70), address))));
            codeLibraryBean.m604set70(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(80), address))));
            codeLibraryBean.m605set80(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(90), address))));
            codeLibraryBean.m606set90(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(100), address))));
            codeLibraryBean.m598set100(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(110), address))));
            codeLibraryBean.m612set(gson.toJson(new CharSwitchBean(getColor(255, 0, 0, address))));
            codeLibraryBean.m609set(gson.toJson(new CharSwitchBean(getColor(255, 80, 0, address))));
            codeLibraryBean.m616set(gson.toJson(new CharSwitchBean(getColor(255, 255, 0, address))));
            codeLibraryBean.m613set(gson.toJson(new CharSwitchBean(getColor(0, 255, 0, address))));
            codeLibraryBean.m615set(gson.toJson(new CharSwitchBean(getColor(0, 255, 255, address))));
            codeLibraryBean.m614set(gson.toJson(new CharSwitchBean(getColor(0, 0, 255, address))));
            codeLibraryBean.m611set(gson.toJson(new CharSwitchBean(getColor(160, 32, 240, address))));
            codeLibraryBean.m610set(gson.toJson(new CharSwitchBean(getColor(255, 255, 255, address))));
        }
        String json = gson.toJson(codeLibraryBean);
        LHomeLog.i(getClass(), "CodeLibraryBean =" + json);
        return json;
    }

    private String getOnoff(boolean onoff, int address) {
        BaseCmd onOff = CmdBleFactory.setOnOff(1, onoff);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(onOff.getFunCode()));
        for (byte b2 : onOff.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private String getOnoff(boolean onoff, int address, int pos) {
        BaseCmd onOff = CmdBleFactory.setOnOff(1 << pos, onoff);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(onOff.getFunCode()));
        for (byte b2 : onOff.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private String getbrightness(int brig, int address) {
        BaseCmd wdd = CmdBleFactory.setWDD(1, brig);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wdd.getFunCode()));
        for (byte b2 : wdd.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private String getColor(int R, int G, int B, int address) {
        BaseCmd wdc = CmdBleFactory.setWDC(1, R, G, B);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wdc.getFunCode()));
        for (byte b2 : wdc.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private String getColorDE(int CW, int TY, int address) {
        BaseCmd wde = CmdBleFactory.setWDE(1, CW, TY);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wde.getFunCode()));
        for (byte b2 : wde.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private String getbrightnessDF(int brig, int address) {
        BaseCmd wdf = CmdBleFactory.setWDF(1, brig);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wdf.getFunCode()));
        for (byte b2 : wdf.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private String getbrightnessE0(int brig, int address) {
        BaseCmd we0 = CmdBleFactory.setWE0(1, brig);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(we0.getFunCode()));
        for (byte b2 : we0.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return com.smart.message.utils.StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public Floor checkFloor(List<Floor> floorList) {
        Floor value = this.selectFloor.getValue();
        if (value != null) {
            Iterator<Floor> it = floorList.iterator();
            while (it.hasNext()) {
                if (it.next().getFloorId() == value.getFloorId()) {
                    return value;
                }
            }
        }
        return floorList.get(0);
    }

    public boolean setCurFloor(Floor floor) {
        this.selectFloor.setValue(floor);
        return true;
    }

    public Floor getCurFloor() {
        return this.selectFloor.getValue();
    }

    public Room checkRoom(List<Room> roomList) {
        Room value = this.selectRoom.getValue();
        if (value != null) {
            Iterator<Room> it = roomList.iterator();
            while (it.hasNext()) {
                if (it.next().getRoomId() == value.getRoomId()) {
                    return value;
                }
            }
        }
        return roomList.get(0);
    }

    public boolean setCurRoom(Room room) {
        this.selectRoom.setValue(room);
        return true;
    }

    public Room getCurRoom() {
        return this.selectRoom.getValue();
    }

    public static class StateParam {
        public static int STATE_ALL_SUCCESS = 4;
        public static int STATE_FAILED = 2;
        public static int STATE_LOADING = 3;
        public static int STATE_SUCCESS = 1;
        public int pos;
        public float progress;
        public int state;

        public StateParam(int pos, int state, float progress) {
            this.pos = pos;
            this.state = state;
            this.progress = progress;
        }
    }

    public void setScanItems(List<ScanItem> list) {
        this.scanItems.clear();
        this.scanItems.addAll(list);
    }

    public void startAddDeviceAll(boolean isStart) {
        List<ScanItem> list = this.scanItems;
        if (list == null || list.isEmpty()) {
            if (isStart) {
                return;
            }
            this.showFinishDialog.call();
            this.addSingleDevice = true;
            return;
        }
        if (this.addFinish) {
            return;
        }
        final ScanItem remove = this.scanItems.remove(0);
        this.pos++;
        this.updateStateLiveData.setValue(new StateParam(this.pos - 1, StateParam.STATE_LOADING, 0.0f));
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanVM$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                ActMeshScanVM.this.lambda$startAddDeviceAll$37(remove);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAddDeviceAll$37(ScanItem scanItem) {
        addDeviceAtPosition(scanItem, this.pos);
    }
}