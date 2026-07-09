package com.ltech.smarthome.ui.config;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.airoha.btdlib.core.AirohaLink;
import com.airoha.libfota.core.AirohaOtaMgr;
import com.airoha.libfota.core.OnAirohaOtaEventListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.blemesh.IConnectBleCallback;
import com.ltech.smarthome.blemesh.IScanCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.blemesh.feasy.FeasyMeshNetHelper;
import com.ltech.smarthome.databinding.ActMeshScanProxyBinding;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.singleton.MeshOta671;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.upgrade.ota671.DfuCallback;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.SmartUtils;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.parser.IUpgradeParser;
import com.smart.product_agreement.productBle.BleUpgradeHelper;
import com.smart.product_agreement.productBle.BleZipUpgradeHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActMeshScanProxy extends VMActivity<ActMeshScanProxyBinding, ActMeshScanVM> {
    private static final int MSG_CHANGE_ITEM_STATE = 3;
    private static final int MSG_QUERY_DEVICE = 1;
    private static final int MSG_REFRESH_ALL = 4;
    private static final int MSG_REFRESH_DEVICE = 2;
    private static final int MSG_WAIT_SEQ = 5;
    private static final int MSG_WAIT_SEQ_TIME_OUT = 6;
    private static final int QUERY_INTERVAL = 300;
    BlueToothValueReceiver blueToothValueReceiver;
    private IConnectBleCallback connectBleCallback;
    private int curAddress;
    private boolean isUpgrading;
    private AirohaLink mAirohaLink;
    private AirohaOtaMgr mAirohaOtaMgr;
    private int progress;
    private Handler updateHandler;
    private BaseUpgradeHelper upgradeHelper;
    private boolean waitSync;
    private boolean bSearchDevice = false;
    public boolean isPROXY = false;
    private int queryIndex = 0;
    private int upgradeIndex = 0;
    private int connectFailTime = 0;
    private boolean isStop = false;
    private HashMap<Integer, Boolean> queryReadyMap = new HashMap<>();
    private long scanLastTime = 0;
    private final Handler handle = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.10
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                Device device = (Device) msg.obj;
                ActMeshScanProxy.this.handle.removeMessages(1);
                ActMeshScanProxy.this.handle.sendEmptyMessageDelayed(1, 10000L);
                CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelState(ActMeshScanProxy.this.activity, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.10.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg msg2) {
                    }
                }, 0);
            } else if (i == 2) {
                ActMeshScanVM.ScanItem scanItem = (ActMeshScanVM.ScanItem) msg.obj;
                if (scanItem != null) {
                    scanItem.progress = ActMeshScanProxy.this.progress;
                }
                ActMeshScanProxy.this.sendChangeMessage();
                if (ActMeshScanProxy.this.progress <= 99) {
                    ActMeshScanProxy.this.progress++;
                }
                ActMeshScanProxy.this.handle.sendEmptyMessageDelayed(2, 1000L);
            }
            return false;
        }
    });

    static /* synthetic */ void lambda$updateDeviceParam$11(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mesh_scan_proxy;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.firmware_upgrade));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.icon_refresh);
        ((ActMeshScanProxyBinding) this.mViewBinding).tvHelp.getPaint().setFlags(8);
        this.mAirohaLink = new AirohaLink(this);
        this.mAirohaOtaMgr = new AirohaOtaMgr(this.mAirohaLink);
        this.updateHandler = new UpdateHandler();
        ((ActMeshScanProxyBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMeshScanProxy.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        LHomeLog.i(getClass(), "************************ClickCommand***************************");
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() == 1) {
            this.updateHandler.removeCallbacksAndMessages(null);
            Injection.mesh().stopScan();
            ((ActMeshScanVM) this.mViewModel).deviceNum.setValue(getResources().getString(R.string.bluetooth_scan_finish));
            ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.setValue(2);
            for (int i = 0; i < ((ActMeshScanVM) this.mViewModel).deviceList.size(); i++) {
                ActMeshScanVM.ScanItem scanItem = ((ActMeshScanVM) this.mViewModel).deviceList.get(i);
                if (scanItem.upgradeFlag == 1) {
                    scanItem.upgradeFlag = 2;
                    ((ActMeshScanVM) this.mViewModel).deviceList.set(i, scanItem);
                }
            }
            this.upgradeIndex = 0;
            upgradeAll();
            return;
        }
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() == 3) {
            finish();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injection.mesh().stopCheckNearbyDevice();
        this.blueToothValueReceiver = new BlueToothValueReceiver();
        registerReceiver(this.blueToothValueReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        Injection.repo().mcu().getMcuListFromNet(this, Utils.getVersionCode(this));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LHomeLog.i(getClass(), "onStart: ");
        initListData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LHomeLog.i(getClass(), "onStop: ");
        Injection.mesh().stopScan();
        this.isStop = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.isStop = false;
    }

    private void startBleCheck() {
        if (SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class) != null) {
            Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
            Injection.mesh().checkNearbyDevice(FeasyMeshNetHelper.getMeshJsonBean(place, place.getNetKey(), place.getAppKey(), place.getProvisionerAddress()).getMeshUUID(), (LifecycleOwner) ActivityUtils.getTopActivity());
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.isUpgrading) {
            showUpgradingDialog();
        } else if (((ActMeshScanVM) this.mViewModel).controlId != -1) {
            super.back();
        } else {
            finish();
            NavUtils.destination(ActHome.class).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() != 2) {
            refreshScanData();
        }
    }

    private void showUpgradingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getString(R.string.app_str_stop_upgrading_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda11
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUpgradingDialog$1;
                lambda$showUpgradingDialog$1 = ActMeshScanProxy.this.lambda$showUpgradingDialog$1(baseDialog, view);
                return lambda$showUpgradingDialog$1;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUpgradingDialog$1(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.isPROXY = false;
        ((ActMeshScanVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        LHomeLog.i(getClass(), "controlId=" + ((ActMeshScanVM) this.mViewModel).controlId);
        if (((ActMeshScanVM) this.mViewModel).controlId != -1) {
            this.isPROXY = true;
            ((ActMeshScanVM) this.mViewModel).isPROXY = true;
            if (((ActMeshScanVM) this.mViewModel).controlId != 9999) {
                ((ActMeshScanVM) this.mViewModel).filerDevice = Injection.repo().device().getDeviceById(((ActMeshScanVM) this.mViewModel).controlId);
            }
        }
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanProxy.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).addList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanProxy.this.lambda$startObserve$3((List) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanProxy.this.lambda$startObserve$4((Integer) obj);
            }
        });
        ((ActMeshScanProxyBinding) this.mViewBinding).tvHelp.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActMeshScanProxy.this.lambda$startObserve$5(view);
            }
        });
        ((ActMeshScanVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        ((ActMeshScanVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).placeId, floor.getFloorId());
                if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(ActMeshScanProxy.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).mRoomList = roomListByFloorId;
                ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).setCurRoom(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).checkRoom(roomListByFloorId));
                ActMeshScanProxy.this.initRoomSpinner();
            }
        });
        ((ActMeshScanVM) this.mViewModel).selectRoom.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanProxy.this.lambda$startObserve$6((Room) obj);
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(((ActMeshScanVM) this.mViewModel).placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(((ActMeshScanVM) this.mViewModel).placeId);
        floorListByPlaceId.add(0, floor);
        ((ActMeshScanVM) this.mViewModel).mFloorList = floorListByPlaceId;
        ((ActMeshScanVM) this.mViewModel).setCurFloor(((ActMeshScanVM) this.mViewModel).checkFloor(floorListByPlaceId));
        initFloorSpinner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        LHomeLog.i(getClass(), "startScanLiveData=" + bool);
        ((ActMeshScanVM) this.mViewModel).deviceNum.setValue(ActivityUtils.getTopActivity().getString(R.string.scan_device_num_2, new Object[]{Integer.valueOf(((ActMeshScanVM) this.mViewModel).deviceList.size())}));
        if (bool.booleanValue()) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.scanLastTime > 5000) {
                this.scanLastTime = currentTimeMillis;
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ActMeshScanProxy.this.bleIsOk()) {
                            ActMeshScanProxy.this.scanDeviceForProxy();
                        }
                    }
                }, 1000L);
            }
        } else {
            Injection.mesh().stopScan();
        }
        ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.setAnimate(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        if (list.size() > 0) {
            queryAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        if (this.mViewBinding != 0) {
            if (num.intValue() == 0) {
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setText(R.string.upgrade_all);
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setTextColor(getResources().getColor(R.color.color_text_gray));
            } else if (num.intValue() == 1) {
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setText(R.string.upgrade_all);
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setTextColor(getResources().getColor(R.color.color_text_red));
            } else if (num.intValue() == 2) {
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setText(R.string.upgrading);
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setTextColor(getResources().getColor(R.color.color_text_gray));
                Injection.mesh().setUpgradeStart(true);
                getWindow().addFlags(128);
            } else if (num.intValue() == 3) {
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setText(R.string.upgrade_finish);
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setTextColor(getResources().getColor(R.color.color_text_red));
                Injection.mesh().setUpgradeStart(false);
                getWindow().clearFlags(128);
            }
            this.isUpgrading = num.intValue() == 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(View view) {
        NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, getString(R.string.faq_url)).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Room room) {
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() == 2) {
            return;
        }
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(false);
        ((ActMeshScanVM) this.mViewModel).list = Injection.repo().device().getDeviceListByRoomIdFromDb(((ActMeshScanVM) this.mViewModel).placeId, ((ActMeshScanVM) this.mViewModel).getCurFloor().getFloorId(), ((ActMeshScanVM) this.mViewModel).getCurRoom().getRoomId());
        ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.setValue(0);
        this.queryReadyMap.clear();
        ((ActMeshScanVM) this.mViewModel).deviceList.clear();
        ((ActMeshScanVM) this.mViewModel).addList.setValue(new ArrayList());
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanDeviceForProxy() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.4
            @Override // java.lang.Runnable
            public void run() {
                BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
                if (connectedDevice != null) {
                    LHomeLog.i(getClass(), "代理节点地址 = " + connectedDevice.getAddress());
                    if (!((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).addProxyDevice(connectedDevice) || ActMeshScanProxy.this.bSearchDevice) {
                        return;
                    }
                    ActMeshScanProxy.this.changeView();
                }
            }
        });
        Injection.mesh().startScan(BleMeshManager.MESH_PROXY_UUID, new IScanCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ActMeshScanProxy.this.lambda$scanDeviceForProxy$7(extendedBluetoothDevice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDeviceForProxy$7(ExtendedBluetoothDevice extendedBluetoothDevice) {
        if (((ActMeshScanVM) this.mViewModel).refreshRssi(extendedBluetoothDevice) || !((ActMeshScanVM) this.mViewModel).addDeviceForProxy(extendedBluetoothDevice) || this.bSearchDevice) {
            return;
        }
        this.bSearchDevice = true;
        changeView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeView() {
        if (this.mViewBinding != 0) {
            ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.setAnimate(false);
            ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.requestLayout();
            ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.setAnimate(true);
            ((ActMeshScanProxyBinding) this.mViewBinding).tvNoDevice.setVisibility(this.isPROXY ? 8 : 0);
            ((ActMeshScanProxyBinding) this.mViewBinding).tvHelp.setVisibility(this.isPROXY ? 8 : 0);
        }
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActMeshScanVM) this.mViewModel).addList.postValue(new ArrayList());
        this.mAirohaLink.disconnect();
        this.mAirohaLink.close();
        this.mAirohaLink.cancelTimer();
        MeshOta671.getInstance().disconnect();
        ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.clear();
        if (Injection.mesh().getConnectedDevice() != null) {
            Injection.mesh().stopScan();
        }
        Injection.mesh().clear();
        BlueToothValueReceiver blueToothValueReceiver = this.blueToothValueReceiver;
        if (blueToothValueReceiver != null) {
            unregisterReceiver(blueToothValueReceiver);
        }
        Injection.mesh().setUpgradeStart(false);
        Handler handler = this.updateHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.updateHandler = null;
        this.connectBleCallback = null;
        super.onDestroy();
    }

    public class BlueToothValueReceiver extends BroadcastReceiver {
        public static final int DEFAULT_VALUE_BULUETOOTH = 1000;

        public BlueToothValueReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 1000)) {
                    case 10:
                        LogUtils.e("蓝牙已关闭");
                        break;
                    case 11:
                        LogUtils.e("正在打开蓝牙");
                        break;
                    case 12:
                        LogUtils.e("蓝牙已打开");
                        ActMeshScanProxy.this.initListData();
                        break;
                    case 13:
                        LogUtils.e("正在关闭蓝牙");
                        Injection.mesh().stopScan();
                        break;
                    default:
                        LogUtils.e("未知状态");
                        break;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListData() {
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() != 2) {
            ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
            this.updateHandler.sendEmptyMessage(4);
        }
        queryAll();
    }

    private void refreshScanData() {
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(false);
        this.queryReadyMap.clear();
        ((ActMeshScanVM) this.mViewModel).deviceList.clear();
        ((ActMeshScanVM) this.mViewModel).addList.setValue(new ArrayList());
        ((ActMeshScanVM) this.mViewModel).deviceNum.setValue(ActivityUtils.getTopActivity().getString(R.string.scan_device_num_2, new Object[]{Integer.valueOf(((ActMeshScanVM) this.mViewModel).deviceList.size())}));
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActMeshScanVM) this.mViewModel).mFloorList));
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).mFloorList.get(position).equals(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).selectFloor.getValue())) {
                    ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).setCurFloor(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActMeshScanVM) this.mViewModel).mRoomList));
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).mRoomList.get(position).equals(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).selectRoom.getValue())) {
                    ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).setCurRoom(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActMeshScanProxyBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    private class UpdateHandler extends Handler {
        private UpdateHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            boolean z;
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 2 || ActMeshScanProxy.this.isStop) {
                    return;
                }
                ActMeshScanProxy.this.queryDeviceVersion((Device) msg.obj);
                return;
            }
            if (msg.what == 2) {
                if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 2) {
                    return;
                }
                ActMeshScanVM.ScanItem scanItem = (ActMeshScanVM.ScanItem) msg.obj;
                for (int i = 0; i < ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.size(); i++) {
                    ActMeshScanVM.ScanItem scanItem2 = ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.get(i);
                    if (scanItem2.unicastAdd == scanItem.unicastAdd) {
                        scanItem2.supportBle = scanItem.supportBle;
                        scanItem2.supportBleZip = scanItem.supportBleZip;
                        if (scanItem2.currentVersion.equals("--")) {
                            if (scanItem.upgradeInfo != null) {
                                scanItem2.currentVersion = scanItem.currentVersion;
                                scanItem2.newVersion = scanItem.newVersion;
                                scanItem2.upgradeInfo = scanItem.upgradeInfo;
                            }
                            if (scanItem2.upgradeInfo != null && scanItem2.currentVersion.compareTo(scanItem2.newVersion) < 0) {
                                scanItem2.upgradeFlag = 1;
                                if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.getValue().intValue() != 1 && ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.getValue().intValue() != 2) {
                                    ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.setValue(1);
                                }
                            }
                            for (Device device : Injection.repo().device().getDeviceByUnicastAddress(((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).placeId, scanItem2.unicastAdd)) {
                                if (device.getMcuversion() == null || !device.getMcuversion().equals(scanItem2.currentVersion)) {
                                    String replace = scanItem2.currentVersion.replace(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "SVer");
                                    if (!replace.equals(device.getMcuversion())) {
                                        ActMeshScanProxy.this.changeDeviceVersion(replace, scanItem2.upgradeInfo == null ? "" : scanItem2.upgradeInfo.gethVer(), device);
                                    }
                                }
                            }
                            ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.set(i, scanItem2);
                            ActMeshScanProxy.this.queryReadyMap.put(Integer.valueOf(scanItem2.unicastAdd), true);
                        }
                    }
                }
                return;
            }
            if (msg.what == 3) {
                int i2 = msg.arg1 - 1;
                if (i2 < 0) {
                    return;
                }
                ActMeshScanVM.ScanItem scanItem3 = ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.get(i2);
                ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.set(i2, scanItem3);
                if (scanItem3.upgradeFlag == 4 || scanItem3.upgradeFlag == 5) {
                    if (scanItem3.upgradeFlag == 4) {
                        ActMeshScanProxy.this.asDataUpdate(scanItem3.device);
                    }
                    ActMeshScanProxy.this.upgradeAll();
                    return;
                }
                return;
            }
            if (msg.what == 4) {
                int i3 = 0;
                while (true) {
                    if (i3 >= ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.size()) {
                        z = false;
                        break;
                    } else {
                        if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.get(i3).upgradeFlag == 1) {
                            z = true;
                            break;
                        }
                        i3++;
                    }
                }
                if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 1 || !z) {
                    if (((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 0 || z) {
                        return;
                    }
                    ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.setValue(0);
                    return;
                }
                ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).upgradeStateLiveData.setValue(1);
                return;
            }
            if (msg.what == 5) {
                if (Injection.mesh().isSeqSuccess()) {
                    removeMessages(6);
                    Injection.mesh().setNodeTtl(1);
                    if (ActMeshScanProxy.this.filterDevice((Device) msg.obj)) {
                        CmdAssistant.getSettingCmdAssistant(msg.obj, new int[0]).starUpgrade(ActMeshScanProxy.this.activity);
                    }
                    ActMeshScanProxy.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.UpdateHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ActMeshScanProxy.this.upgradeHelper.reset();
                            ActMeshScanProxy.this.upgradeHelper.startUpgrade();
                        }
                    }, 200L);
                    return;
                }
                sendEmptyMessageDelayed(5, 1000L);
                return;
            }
            if (msg.what == 6) {
                removeMessages(5);
                int i4 = msg.arg1 - 1;
                ActMeshScanProxy.this.connectFailTime = 0;
                ActMeshScanVM.ScanItem scanItem4 = ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.get(i4);
                scanItem4.upgradeFlag = 5;
                ((ActMeshScanVM) ActMeshScanProxy.this.mViewModel).deviceList.set(i4, scanItem4);
                ActMeshScanProxy.this.upgradeAll();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryDeviceVersion(final Device device) {
        if (Injection.mesh().isSeqSuccess()) {
            LHomeLog.i(getClass(), "send getQueryCmdAssistant:" + device.getDeviceName());
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMeshScanProxy.this.lambda$queryDeviceVersion$8(device, (ResponseMsg) obj);
                }
            });
            return;
        }
        sendQueryMessage(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceVersion$8(Device device, ResponseMsg responseMsg) {
        ProductVersionInfo parserUpgradeInfo;
        String softwareVersion;
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg);
        if (responseMsg == null) {
            queryAll();
            return;
        }
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg.getAgreementId());
        if (responseMsg.getResData().length() < 16) {
            LHomeLog.i(getClass(), "msg.getResData().length()<16 resData-->" + responseMsg.getResData());
            parserUpgradeInfo = null;
        } else {
            LHomeLog.i(getClass(), "msg.getResData() current, resData-->" + responseMsg.getResData());
            parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        if (parserUpgradeInfo == null) {
            return;
        }
        UpgradeInfo upgradeInfo = UpgradeFactory.getUpgradeInfo(parserUpgradeInfo);
        if (upgradeInfo == null || upgradeInfo.getsVer().compareTo(parserUpgradeInfo.getsVer().toUpperCase()) <= 0) {
            softwareVersion = UpgradeInfo.getSoftwareVersion(parserUpgradeInfo.getsVer());
        } else {
            softwareVersion = UpgradeInfo.getSoftwareVersion(upgradeInfo.getsVer());
        }
        LHomeLog.i(getClass(), "versionInfo.getDeviceModel() =" + parserUpgradeInfo.getDeviceModel() + "  " + device.getDeviceName() + "  " + parserUpgradeInfo.getsVer() + "  " + softwareVersion);
        int parseInt = Integer.parseInt(responseMsg.getDeviceFlag(), 16);
        ActMeshScanVM.ScanItem scanItem = new ActMeshScanVM.ScanItem();
        StringBuilder sb = new StringBuilder(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        sb.append(parserUpgradeInfo.getsVer().substring(4));
        scanItem.currentVersion = sb.toString();
        StringBuilder sb2 = new StringBuilder(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        sb2.append(softwareVersion);
        scanItem.newVersion = sb2.toString();
        scanItem.upgradeInfo = upgradeInfo;
        scanItem.unicastAdd = parseInt;
        if (!this.queryReadyMap.containsKey(Long.valueOf(device.getDeviceId()))) {
            Message message = new Message();
            message.what = 2;
            message.obj = scanItem;
            Handler handler = this.updateHandler;
            if (handler != null) {
                handler.sendMessageDelayed(message, 10L);
            }
        }
        checkSupportBle(device);
    }

    private void checkSupportBle(final Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).querySupportBleUpgrade(this.activity, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.7
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActMeshScanVM.ScanItem scanItem = new ActMeshScanVM.ScanItem();
                if (responseMsg != null && responseMsg.getResData().length() >= 18) {
                    int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                    int parseInt2 = Integer.parseInt(responseMsg.getDeviceFlag(), 16);
                    scanItem.supportBle = parseInt == 2 || parseInt == 3;
                    scanItem.supportBleZip = parseInt == 3;
                    scanItem.unicastAdd = parseInt2;
                    if (!ActMeshScanProxy.this.queryReadyMap.containsKey(Long.valueOf(device.getDeviceId()))) {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = scanItem;
                        if (ActMeshScanProxy.this.updateHandler != null) {
                            ActMeshScanProxy.this.updateHandler.sendMessageDelayed(message, 10L);
                        }
                    }
                }
                ActMeshScanProxy.this.queryAll();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDeviceVersion(final String sVersion, final String hVersion, final Device device) {
        if (device == null || !Injection.state().isConnectOuterNet()) {
            return;
        }
        ((ObservableSubscribeProxy) Injection.net().updateDeviceVersion(device.getDeviceId(), sVersion, hVersion).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanProxy.lambda$changeDeviceVersion$9(Device.this, sVersion, hVersion, obj);
            }
        }, new SmartErrorComsumer());
    }

    static /* synthetic */ void lambda$changeDeviceVersion$9(Device device, String str, String str2, Object obj) throws Exception {
        device.setMcuversion(str);
        device.setFirmwareversion(str2);
        Injection.repo().device().saveDevice(device);
    }

    private void sendQueryMessage(Device device) {
        Handler handler = this.updateHandler;
        if (handler == null || handler.hasMessages(1, device)) {
            return;
        }
        Message message = new Message();
        message.what = 1;
        message.obj = device;
        this.updateHandler.sendMessageDelayed(message, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryAll() {
        if (this.queryIndex >= ((ActMeshScanVM) this.mViewModel).addList.getValue().size()) {
            this.queryIndex = 0;
        }
        for (int i = this.queryIndex; i < ((ActMeshScanVM) this.mViewModel).addList.getValue().size(); i++) {
            Device device = ((ActMeshScanVM) this.mViewModel).addList.getValue().get(i);
            int unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            this.queryIndex++;
            if (!this.queryReadyMap.containsKey(Integer.valueOf(unicastAddress))) {
                sendQueryMessage(device);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChangeMessage() {
        if (this.updateHandler == null) {
            return;
        }
        Message message = new Message();
        message.what = 3;
        message.arg1 = this.upgradeIndex;
        this.updateHandler.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upgradeAll() {
        for (int i = this.upgradeIndex; i < ((ActMeshScanVM) this.mViewModel).addList.getValue().size(); i++) {
            Device device = ((ActMeshScanVM) this.mViewModel).addList.getValue().get(i);
            final ActMeshScanVM.ScanItem scanItem = ((ActMeshScanVM) this.mViewModel).deviceList.get(i);
            this.upgradeIndex++;
            if (scanItem.upgradeFlag == 2) {
                LHomeLog.i(getClass(), "当前升级 = " + this.upgradeIndex);
                this.connectFailTime = 0;
                if (ProductId.ID_HOME_KIT.equals(device.getProductId())) {
                    startCgKitUpgrade(device, scanItem);
                    return;
                } else {
                    connectDevice(device, scanItem);
                    getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActMeshScanProxy.this.lambda$upgradeAll$10(scanItem);
                        }
                    }, 20000L);
                    return;
                }
            }
        }
        if (this.upgradeIndex >= ((ActMeshScanVM) this.mViewModel).addList.getValue().size()) {
            ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.setValue(3);
            this.upgradeIndex = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$upgradeAll$10(ActMeshScanVM.ScanItem scanItem) {
        if (scanItem.upgradeFlag == 2) {
            scanItem.upgradeFlag = 5;
            sendChangeMessage();
        }
    }

    private void startCgKitUpgrade(final Device device, final ActMeshScanVM.ScanItem scanItem) {
        this.waitSync = false;
        MessageManager.getInstance().setCgKitCallBack(new MessageManager.CgKitCallBack() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.8
            @Override // com.smart.message.MessageManager.CgKitCallBack
            public void onDataReceive(ResponseMsg msg) {
                Device device2 = device;
                if (device2 == null || msg == null) {
                    return;
                }
                if (Integer.parseInt(msg.getResData().substring(6, 10), 16) == ((BleParam) device2.getParam(BleParam.class)).getUnicastAddress()) {
                    ActMeshScanProxy.this.checkState(msg, device, scanItem);
                }
            }
        });
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).starUpgrade(this, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.9
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActMeshScanProxy.this.waitSync = true;
                    ActMeshScanProxy.this.progress = 0;
                    ActMeshScanProxy.this.handle.removeMessages(1);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = device;
                    ActMeshScanProxy.this.handle.sendMessage(message);
                    return;
                }
                ActMeshScanProxy.this.handle.removeMessages(1);
                ActMeshScanProxy.this.handle.removeMessages(2);
                if (scanItem.upgradeFlag == 2 || scanItem.upgradeFlag == 3) {
                    ActMeshScanProxy.this.connectFailTime = 0;
                    scanItem.upgradeFlag = 5;
                    ActMeshScanProxy.this.sendChangeMessage();
                }
            }
        });
    }

    public void checkState(ResponseMsg msg, Device device, ActMeshScanVM.ScanItem scanItem) {
        if (this.waitSync) {
            int parseInt = Integer.parseInt(msg.getResData().substring(16, 18), 16);
            if (parseInt == 8 || parseInt == 37 || parseInt == 0) {
                this.handle.removeMessages(1);
                this.handle.removeMessages(2);
                this.waitSync = false;
                scanItem.upgradeFlag = 4;
                scanItem.currentVersion = scanItem.newVersion;
                sendChangeMessage();
                return;
            }
            if (parseInt == 9 || parseInt == 1) {
                this.handle.removeMessages(1);
                this.handle.removeMessages(2);
                if (scanItem.upgradeFlag == 2 || scanItem.upgradeFlag == 3) {
                    this.connectFailTime = 0;
                    scanItem.upgradeFlag = 5;
                    sendChangeMessage();
                    return;
                }
                return;
            }
            Message message = new Message();
            message.what = 2;
            message.obj = scanItem;
            this.handle.sendMessageDelayed(message, 1000L);
        }
    }

    private void newModuleConnectDevice(Device device, final ActMeshScanVM.ScanItem scanItem) {
        final String address = scanItem.bluetoothDevice.getAddress();
        MeshOta671.getInstance().init(this, new DfuCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.11
            @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
            public void DfuStart() {
            }

            @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
            public void DfuSuccess() {
                scanItem.upgradeFlag = 4;
                ActMeshScanVM.ScanItem scanItem2 = scanItem;
                scanItem2.currentVersion = scanItem2.newVersion;
                ActMeshScanProxy.this.sendChangeMessage();
            }

            @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
            public void DfuFail(String var1) {
                MeshOta671.getInstance().disconnect();
                if (scanItem.upgradeFlag == 2 || scanItem.upgradeFlag == 3) {
                    ActMeshScanProxy.this.connectFailTime = 0;
                    scanItem.upgradeFlag = 5;
                    ActMeshScanProxy.this.sendChangeMessage();
                }
            }

            @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
            public void DfuProgress(double var1) {
                scanItem.progress = (float) var1;
                ActMeshScanProxy.this.sendChangeMessage();
            }

            @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
            public void Msg(String var1) {
                LHomeLog.d(getClass(), var1);
            }
        });
        MeshOta671.getInstance().setFile(scanItem.upgradeInfo.getFirmwareData());
        Injection.mesh().disconnect();
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.12
            @Override // java.lang.Runnable
            public void run() {
                if (!MeshOta671.getInstance().connect(address)) {
                    if (scanItem.upgradeFlag == 2) {
                        ActMeshScanProxy.this.connectFailTime = 0;
                        scanItem.upgradeFlag = 5;
                        ActMeshScanProxy.this.sendChangeMessage();
                        return;
                    }
                    return;
                }
                LHomeLog.e("startUpgrade", getClass(), "new module upgrade");
                scanItem.upgradeFlag = 3;
                ActMeshScanProxy.this.sendChangeMessage();
            }
        }, 4000L);
    }

    private void bleConnectDevice(Device device, final ActMeshScanVM.ScanItem scanItem) {
        final String address = scanItem.bluetoothDevice.getAddress();
        if (scanItem.supportBleZip) {
            LHomeLog.e("startUpgrade", getClass(), "ble upgrade");
            this.upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 7, scanItem.upgradeInfo.getFirmwareData(), getMainHandler());
        } else {
            LHomeLog.e("startUpgrade", getClass(), "ble upgrade");
            this.upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 3, scanItem.upgradeInfo.getFirmwareData(), getMainHandler());
        }
        this.upgradeHelper.setUpgradeCallback(new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.13
            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeSuccess(int nextNum) {
                scanItem.upgradeFlag = 4;
                ActMeshScanVM.ScanItem scanItem2 = scanItem;
                scanItem2.currentVersion = scanItem2.newVersion;
                ActMeshScanProxy.this.sendChangeMessage();
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeFail() {
                scanItem.upgradeFlag = 5;
                ActMeshScanProxy.this.sendChangeMessage();
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgrading(float progress) {
                scanItem.progress = progress;
                ActMeshScanProxy.this.sendChangeMessage();
            }
        });
        BaseUpgradeHelper baseUpgradeHelper = this.upgradeHelper;
        if (baseUpgradeHelper instanceof BleUpgradeHelper) {
            ((BleUpgradeHelper) baseUpgradeHelper).setOnDataSendCallBack(new BleUpgradeHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.14
                @Override // com.smart.product_agreement.productBle.BleUpgradeHelper.OnDataSendCallBack
                public void sendUpgradeCmd(byte[] data) {
                    ActMeshScanProxy.this.mAirohaOtaMgr.writeAtCharc(data);
                    LHomeLog.e(getClass(), "send:" + StringUtils.btye2Str2(data));
                }
            });
        } else if (baseUpgradeHelper instanceof BleZipUpgradeHelper) {
            ((BleZipUpgradeHelper) baseUpgradeHelper).setOnDataSendCallBack(new BleZipUpgradeHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.15
                @Override // com.smart.product_agreement.productBle.BleZipUpgradeHelper.OnDataSendCallBack
                public void sendUpgradeCmd(byte[] data) {
                    ActMeshScanProxy.this.mAirohaOtaMgr.writeAtCharc(data);
                    LHomeLog.e(getClass(), "send:" + StringUtils.btye2Str2(data));
                }
            });
        }
        this.mAirohaOtaMgr.setListener(new AnonymousClass16(scanItem));
        Injection.mesh().disconnect();
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.17
            @Override // java.lang.Runnable
            public void run() {
                ActMeshScanProxy.this.mAirohaLink.connect(address);
            }
        }, 2000L);
    }

    /* renamed from: com.ltech.smarthome.ui.config.ActMeshScanProxy$16, reason: invalid class name */
    class AnonymousClass16 implements OnAirohaOtaEventListener {
        final /* synthetic */ ActMeshScanVM.ScanItem val$scanItem;

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBatteryLevel(final int batteryLevel) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleBootCodeNotMatching() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleCodeAreaAddrNotMatching() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnReportProgramThroughput(final float throughput) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnUpdateProgrammingProgress(final float progress) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaChanged() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaStatus(final String workingArea, final String area1Rev, final boolean isArea1Valid, final String area2Rev, final boolean isArea2Valid) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onFwVersion(String ver) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onWaitNewOtaShow() {
        }

        AnonymousClass16(final ActMeshScanVM.ScanItem val$scanItem) {
            this.val$scanItem = val$scanItem;
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnRequestMtuChangeStatus(boolean isAccepted) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnNewMtu(final int mtu) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattConnected() {
            ActMeshScanProxy.this.upgradeHelper.reset(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.1
                @Override // java.lang.Runnable
                public void run() {
                    ActMeshScanProxy.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!ActMeshScanProxy.this.upgradeHelper.startUpgrade()) {
                                if (AnonymousClass16.this.val$scanItem.upgradeFlag == 2) {
                                    ActMeshScanProxy.this.connectFailTime = 0;
                                    AnonymousClass16.this.val$scanItem.upgradeFlag = 5;
                                    ActMeshScanProxy.this.sendChangeMessage();
                                }
                                ActMeshScanProxy.this.mAirohaLink.disconnect();
                                return;
                            }
                            ActMeshScanProxy.this.mAirohaLink.cancelTimer();
                            AnonymousClass16.this.val$scanItem.upgradeFlag = 3;
                            ActMeshScanProxy.this.sendChangeMessage();
                        }
                    }, 200L);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattDisconnected() {
            if (this.val$scanItem.upgradeFlag == 2 || this.val$scanItem.upgradeFlag == 3) {
                ActMeshScanProxy.this.connectFailTime = 0;
                this.val$scanItem.upgradeFlag = 5;
                ActMeshScanProxy.this.sendChangeMessage();
            }
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleOtaDisabled(final byte errorCode) {
            ActMeshScanProxy.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.2
                @Override // java.lang.Runnable
                public void run() {
                    ActMeshScanProxy.this.mAirohaLink.disconnect();
                    if (AnonymousClass16.this.val$scanItem.upgradeFlag == 2) {
                        ActMeshScanProxy.this.connectFailTime = 0;
                        AnonymousClass16.this.val$scanItem.upgradeFlag = 5;
                        ActMeshScanProxy.this.sendChangeMessage();
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBinFileParseException() {
            ActMeshScanProxy.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.3
                @Override // java.lang.Runnable
                public void run() {
                    ActMeshScanProxy.this.mAirohaLink.disconnect();
                    if (AnonymousClass16.this.val$scanItem.upgradeFlag == 2) {
                        ActMeshScanProxy.this.connectFailTime = 0;
                        AnonymousClass16.this.val$scanItem.upgradeFlag = 5;
                        ActMeshScanProxy.this.sendChangeMessage();
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnRetryFailed() {
            ActMeshScanProxy.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.4
                @Override // java.lang.Runnable
                public void run() {
                    ActMeshScanProxy.this.mAirohaLink.disconnect();
                    if (AnonymousClass16.this.val$scanItem.upgradeFlag == 2) {
                        ActMeshScanProxy.this.connectFailTime = 0;
                        AnonymousClass16.this.val$scanItem.upgradeFlag = 5;
                        ActMeshScanProxy.this.sendChangeMessage();
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnStatusError(final byte errorCode, final String errorMsg) {
            ActMeshScanProxy.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.5
                @Override // java.lang.Runnable
                public void run() {
                    if (AnonymousClass16.this.val$scanItem.upgradeFlag == 2) {
                        ActMeshScanProxy.this.connectFailTime = 0;
                        AnonymousClass16.this.val$scanItem.upgradeFlag = 5;
                        ActMeshScanProxy.this.sendChangeMessage();
                    }
                    ActMeshScanProxy.this.mAirohaLink.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onCheckFwVersionFailed() {
            ActMeshScanProxy.this.mAirohaLink.disconnect();
            if (this.val$scanItem.upgradeFlag == 2) {
                ActMeshScanProxy.this.connectFailTime = 0;
                this.val$scanItem.upgradeFlag = 5;
                ActMeshScanProxy.this.sendChangeMessage();
            }
            ActMeshScanProxy.this.mAirohaLink.disconnect();
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onDataCallback(final byte[] s) {
            LHomeLog.e(getClass(), "rec:" + StringUtils.btye2Str2(s));
            ActMeshScanProxy.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.16.6
                @Override // java.lang.Runnable
                public void run() {
                    if (ActMeshScanProxy.this.upgradeHelper instanceof BleUpgradeHelper) {
                        ((BleUpgradeHelper) ActMeshScanProxy.this.upgradeHelper).recUpgradeCmd(StringUtils.byte2Str(s));
                    } else if (ActMeshScanProxy.this.upgradeHelper instanceof BleZipUpgradeHelper) {
                        ((BleZipUpgradeHelper) ActMeshScanProxy.this.upgradeHelper).recUpgradeCmd(StringUtils.byte2Str(s));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectDevice(final Device device, final ActMeshScanVM.ScanItem item) {
        if (ProductRepository.isNewBleModule(device)) {
            if (filterDevice(device)) {
                CmdAssistant.getSettingCmdAssistant(device, new int[0]).starUpgrade(this);
            }
            newModuleConnectDevice(device, item);
            return;
        }
        if (item.supportBle && !item.upgradeInfo.isMeshUpdate()) {
            if (filterDevice(device)) {
                CmdAssistant.getSettingCmdAssistant(device, new int[0]).starUpgrade(this);
            }
            bleConnectDevice(device, item);
            return;
        }
        LHomeLog.e("startUpgrade", getClass(), "mesh upgrade");
        BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
        if (connectedDevice != null) {
            LHomeLog.i(getClass(), "代理节点地址 = " + connectedDevice.getAddress() + "   " + item.bluetoothDevice.getAddress() + "  ");
            if (item.bluetoothDevice.getAddress().equals(connectedDevice.getAddress())) {
                startUpgrade(device, item);
                return;
            }
        }
        LHomeLog.i(Constants.MESH_LOG, getClass(), "开始连接:" + item.bluetoothDevice.getAddress());
        this.connectBleCallback = new IConnectBleCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.18
            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice2) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "**********onConnectSuccess**********");
                ActMeshScanProxy.this.startUpgrade(device, item);
            }

            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectFail() {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "**********onConnectFail**********");
                if (item.upgradeFlag == 2) {
                    if (ActMeshScanProxy.this.connectFailTime <= 3) {
                        ActMeshScanProxy.this.connectFailTime++;
                        ActMeshScanProxy.this.connectDevice(device, item);
                    } else {
                        ActMeshScanProxy.this.connectFailTime = 0;
                        item.upgradeFlag = 5;
                        ActMeshScanProxy.this.sendChangeMessage();
                    }
                }
            }
        };
        Injection.mesh().connect(item.bluetoothDevice, this.connectBleCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpgrade(Device device, final ActMeshScanVM.ScanItem scanItem) {
        this.upgradeHelper = UpgradeFactory.getUpgradeHelper(this, scanItem.upgradeInfo.getHelperId(), scanItem.upgradeInfo.getFirmwareData(), getMainHandler());
        CtrlPackage ctrlPackage = (CtrlPackage) SmartUtils.getICtrlConverter().convert(device);
        if (ProductRepository.isBLeDevice(device.getProductId())) {
            ctrlPackage.setAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
            this.curAddress = ctrlPackage.getAddress();
        }
        scanItem.upgradeFlag = 3;
        sendChangeMessage();
        this.upgradeHelper.setCtrlPackage(ctrlPackage);
        this.upgradeHelper.setUpgradeCallback(new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy.19
            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeSuccess(int nextNum) {
                Injection.mesh().setNodeTtl(16);
                scanItem.upgradeFlag = 4;
                ActMeshScanVM.ScanItem scanItem2 = scanItem;
                scanItem2.currentVersion = scanItem2.newVersion;
                ActMeshScanProxy.this.sendChangeMessage();
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeFail() {
                LHomeLog.i(getClass(), "**********onUpgradeFail**********");
                Injection.mesh().setNodeTtl(16);
                scanItem.upgradeFlag = 5;
                ActMeshScanProxy.this.sendChangeMessage();
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgrading(float progress) {
                scanItem.progress = progress;
                ActMeshScanProxy.this.sendChangeMessage();
            }
        });
        Handler handler = this.updateHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(6);
        this.updateHandler.removeMessages(5);
        Message message = new Message();
        message.what = 5;
        message.obj = device;
        this.updateHandler.sendMessage(message);
        Message message2 = new Message();
        message2.what = 6;
        message2.obj = scanItem;
        message2.arg1 = this.upgradeIndex;
        this.updateHandler.sendMessageDelayed(message2, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (6 == helper.getCode()) {
            ActMeshScanVM.ScanItem scanItem = ((ActMeshScanVM) this.mViewModel).deviceList.get(((ActMeshScanVM) this.mViewModel).upgradePosition);
            scanItem.currentVersion = scanItem.newVersion;
            scanItem.upgradeFlag = 4;
            ((ActMeshScanVM) this.mViewModel).deviceList.set(((ActMeshScanVM) this.mViewModel).upgradePosition, scanItem);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showErrorDialog(String content) {
        super.showErrorDialog(content);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean filterDevice(Device device) {
        if (device == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "221042516351701":
            case "123072510445601":
            case "221030816330401":
            case "121031814513301":
            case "121042516340801":
            case "121042516345401":
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void asDataUpdate(Device device) {
        BleParam bleParam;
        if (device == null || !ProductRepository.isAsPanel(device.getProductId()) || (bleParam = (BleParam) device.getParam(BleParam.class)) == null || bleParam.getPublicationId() != 0 || bleParam.getPublicationAddress() <= 0 || bleParam.getGroupId() <= 0) {
            return;
        }
        bleParam.setPublicationId(bleParam.getGroupId());
        bleParam.setGroupId(0L);
        device.setParam(bleParam);
        Injection.repo().device().saveDevice(device);
        updateDeviceParam(device, bleParam);
    }

    private void updateDeviceParam(Device mDevice, BleParam param) {
        ((ObservableSubscribeProxy) Injection.net().updateParam(mDevice.getDeviceId(), GsonUtils.toJson(param)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanProxy$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMeshScanProxy.lambda$updateDeviceParam$11(obj);
            }
        }, new SmartErrorComsumer());
    }
}