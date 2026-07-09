package com.ltech.smarthome.ui.config;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.imageclip.fragment.ActivityResultHelper;
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
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.ui.permission.ActGetMeshPermission;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.SmartUtils;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.MeshUpgradeThemeHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;
import com.smart.product_agreement.utils.GZipUtil;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActMeshScanIcon extends VMActivity<ActMeshScanProxyBinding, ActMeshScanVM> {
    private static final int MSG_CHANGE_ITEM_STATE = 3;
    private static final int MSG_QUERY_DEVICE = 1;
    private static final int MSG_REFRESH_ALL = 4;
    private static final int MSG_REFRESH_DEVICE = 2;
    private static final int MSG_WAIT_SEQ = 5;
    private static final int MSG_WAIT_SEQ_TIME_OUT = 6;
    private static final int QUERY_INTERVAL = 300;
    BlueToothValueReceiver blueToothValueReceiver;
    private IConnectBleCallback connectBleCallback;
    private Device curDevice;
    private ActMeshScanVM.ScanItem curScanItem;
    private int displayType;
    private byte[] iconData;
    private int revFailTime;
    private boolean startQuery;
    private int totalNum;
    private Handler updateHandler;
    private BaseUpgradeHelper upgradeHelper;
    private boolean bSearchDevice = false;
    public boolean isPROXY = false;
    private int queryIndex = 0;
    private int upgradeIndex = 0;
    private int connectFailTime = 0;
    private boolean isStop = false;
    private HashMap<Long, Boolean> queryReadyMap = new HashMap<>();

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
        setTitle(getString(R.string.icon_upgrade));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.icon_refresh);
        ((ActMeshScanProxyBinding) this.mViewBinding).tvHelp.getPaint().setFlags(8);
        this.updateHandler = new UpdateHandler();
        ((ActMeshScanProxyBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMeshScanIcon.this.lambda$initView$0((View) obj);
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
        ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(ActGetMeshPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda3
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActMeshScanIcon.this.lambda$onCreate$1(i, intent);
            }
        });
        this.blueToothValueReceiver = new BlueToothValueReceiver();
        registerReceiver(this.blueToothValueReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(int i, Intent intent) {
        if (i == 1002) {
            LHomeLog.i(getClass(), "onCreate: GET_MESH_PERMISSION_SUCCESS");
        } else if (i == 1003) {
            supportFinishAfterTransition();
        }
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
        this.updateHandler.removeCallbacksAndMessages(null);
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
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() != 2) {
            if (((ActMeshScanVM) this.mViewModel).controlId != -1) {
                super.back();
            } else {
                finish();
                NavUtils.destination(ActHome.class).navigation(this);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.getValue().intValue() != 2) {
            refreshScanData();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.isPROXY = false;
        ((ActMeshScanVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMeshScanVM) this.mViewModel).isIconUpgrade = getIntent().getBooleanExtra(Constants.ICON_UPGRADE, false);
        LHomeLog.i(getClass(), "controlId=" + ((ActMeshScanVM) this.mViewModel).controlId);
        if (((ActMeshScanVM) this.mViewModel).controlId != -1 && ((ActMeshScanVM) this.mViewModel).controlId == 9999) {
            this.isPROXY = true;
            ((ActMeshScanVM) this.mViewModel).isPROXY = true;
        }
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanIcon.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).addList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanIcon.this.lambda$startObserve$3((List) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanIcon.this.lambda$startObserve$4((Integer) obj);
            }
        });
        ((ActMeshScanProxyBinding) this.mViewBinding).tvHelp.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActMeshScanIcon.this.lambda$startObserve$5(view);
            }
        });
        ((ActMeshScanVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        ((ActMeshScanVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).placeId, floor.getFloorId());
                if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(ActMeshScanIcon.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).mRoomList = roomListByFloorId;
                ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).setCurRoom(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).checkRoom(roomListByFloorId));
                ActMeshScanIcon.this.initRoomSpinner();
            }
        });
        ((ActMeshScanVM) this.mViewModel).selectRoom.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScanIcon.this.lambda$startObserve$6((Room) obj);
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
            new Thread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.1
                @Override // java.lang.Runnable
                public void run() {
                    LHomeLog.i(getClass(), "Thread>>>>>>>>>>>>>>>>>>");
                    SystemClock.sleep(1000L);
                    ActMeshScanIcon.this.scanDeviceForProxy();
                }
            }).start();
        } else {
            Injection.mesh().stopScan();
        }
        ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.setAnimate(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        if (list.size() > 0) {
            if (this.queryReadyMap.size() == ((ActMeshScanVM) this.mViewModel).addList.getValue().size()) {
                this.startQuery = false;
            }
            if (this.startQuery) {
                return;
            }
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
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setText(R.string.icon_upgrade_finish);
                ((ActMeshScanProxyBinding) this.mViewBinding).tvUpgrade.setTextColor(getResources().getColor(R.color.color_text_red));
                Injection.mesh().setUpgradeStart(false);
                getWindow().clearFlags(128);
            }
            setBackImage(num.intValue() == 2 ? 0 : R.mipmap.icon_back);
            setEditImage(num.intValue() != 2 ? R.mipmap.icon_refresh : 0);
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
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(((ActMeshScanVM) this.mViewModel).placeId, ((ActMeshScanVM) this.mViewModel).getCurFloor().getFloorId(), ((ActMeshScanVM) this.mViewModel).getCurRoom().getRoomId());
        ((ActMeshScanVM) this.mViewModel).list = deviceListByRoomIdFromDb;
        List<Device> value = ((ActMeshScanVM) this.mViewModel).addList.getValue();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < value.size(); i++) {
            Device device = value.get(i);
            if (deviceListByRoomIdFromDb.contains(device)) {
                arrayList.add(device);
            } else {
                Iterator<ActMeshScanVM.ScanItem> it = ((ActMeshScanVM) this.mViewModel).deviceList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        ActMeshScanVM.ScanItem next = it.next();
                        if (device.getWifiMac().equals(next.bluetoothDevice.getAddress().replace(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""))) {
                            ((ActMeshScanVM) this.mViewModel).deviceList.remove(next);
                            break;
                        }
                    }
                }
            }
        }
        if (arrayList.size() == 0) {
            ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.setValue(0);
        }
        ((ActMeshScanVM) this.mViewModel).addList.setValue(arrayList);
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanDeviceForProxy() {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.3
            @Override // java.lang.Runnable
            public void run() {
                BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
                if (connectedDevice != null) {
                    LHomeLog.i(getClass(), "代理节点地址 = " + connectedDevice.getAddress());
                    if (!((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).addProxyDevice(connectedDevice) || ActMeshScanIcon.this.bSearchDevice) {
                        return;
                    }
                    ActMeshScanIcon.this.changeView();
                }
            }
        });
        Injection.mesh().startScan(BleMeshManager.MESH_PROXY_UUID, new IScanCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ActMeshScanIcon.this.lambda$scanDeviceForProxy$7(extendedBluetoothDevice);
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
        ((ActMeshScanProxyBinding) this.mViewBinding).spreadviewScan.clear();
        if (Injection.mesh().getConnectedDevice() != null) {
            Injection.mesh().stopScan();
        }
        Injection.mesh().clear();
        unregisterReceiver(this.blueToothValueReceiver);
        Injection.mesh().setUpgradeStart(false);
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
                        ActMeshScanIcon.this.initListData();
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
    }

    private void refreshScanData() {
        this.startQuery = false;
        this.queryReadyMap.clear();
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(false);
        ((ActMeshScanVM) this.mViewModel).deviceList.clear();
        ((ActMeshScanVM) this.mViewModel).addList.getValue().clear();
        ((ActMeshScanVM) this.mViewModel).deviceNum.setValue(ActivityUtils.getTopActivity().getString(R.string.scan_device_num_2, new Object[]{Integer.valueOf(((ActMeshScanVM) this.mViewModel).deviceList.size())}));
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActMeshScanVM) this.mViewModel).mFloorList));
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).mFloorList.get(position).equals(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).selectFloor.getValue())) {
                    ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).setCurFloor(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).mFloorList.get(position));
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
        ((ActMeshScanProxyBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).mRoomList.get(position).equals(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).selectRoom.getValue())) {
                    ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).setCurRoom(((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).mRoomList.get(position));
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
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 2 || ActMeshScanIcon.this.isStop) {
                    return;
                }
                ActMeshScanIcon.this.queryDeviceIconVersion((Device) msg.obj);
                return;
            }
            if (msg.what == 2) {
                if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 2) {
                    return;
                }
                ActMeshScanVM.ScanItem scanItem = (ActMeshScanVM.ScanItem) msg.obj;
                for (int i = 0; i < ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.size(); i++) {
                    ActMeshScanVM.ScanItem scanItem2 = ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.get(i);
                    if (scanItem2.unicastAdd == scanItem.unicastAdd && scanItem2.currentVersion.equals("--")) {
                        scanItem2.currentVersion = scanItem.currentVersion;
                        scanItem2.extData = scanItem.extData;
                        scanItem2.newVersion = scanItem.newVersion;
                        if (scanItem2.currentVersion.compareTo(scanItem2.newVersion) < 0) {
                            scanItem2.upgradeFlag = 1;
                            if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.getValue().intValue() != 1 && ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.getValue().intValue() != 2) {
                                ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.setValue(1);
                            }
                        }
                        ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.set(i, scanItem2);
                    }
                }
                return;
            }
            if (msg.what == 3) {
                int i2 = msg.arg1 - 1;
                if (i2 < 0) {
                    return;
                }
                ActMeshScanVM.ScanItem scanItem3 = ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.get(i2);
                ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.set(i2, scanItem3);
                if (scanItem3.upgradeFlag == 4 || scanItem3.upgradeFlag == 5) {
                    ActMeshScanIcon.this.upgradeAll();
                    return;
                }
                return;
            }
            if (msg.what == 4) {
                boolean z = false;
                for (int i3 = 0; i3 < ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.size(); i3++) {
                    if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.get(i3).upgradeFlag == 1) {
                        z = true;
                    }
                }
                if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 1 || !z) {
                    if (((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.getValue().intValue() == 0 || z) {
                        return;
                    }
                    ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.setValue(0);
                    return;
                }
                ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).upgradeStateLiveData.setValue(1);
                return;
            }
            if (msg.what == 5) {
                if (Injection.mesh().isSeqSuccess()) {
                    removeMessages(6);
                    ActMeshScanIcon actMeshScanIcon = ActMeshScanIcon.this;
                    actMeshScanIcon.setIconData(actMeshScanIcon.curDevice, ActMeshScanIcon.this.curScanItem);
                    return;
                }
                sendEmptyMessageDelayed(5, 1000L);
                return;
            }
            if (msg.what == 6) {
                removeMessages(5);
                int i4 = msg.arg1 - 1;
                ActMeshScanIcon.this.connectFailTime = 0;
                ActMeshScanVM.ScanItem scanItem4 = ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.get(i4);
                scanItem4.upgradeFlag = 5;
                ((ActMeshScanVM) ActMeshScanIcon.this.mViewModel).deviceList.set(i4, scanItem4);
                ActMeshScanIcon.this.upgradeAll();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryDeviceIconVersion(final Device device) {
        if (Injection.mesh().isSeqSuccess()) {
            LHomeLog.i(getClass(), "send getQueryCmdAssistant:" + device.getDeviceName());
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryScreenPanelIconStart(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMeshScanIcon.this.lambda$queryDeviceIconVersion$8(device, (ResponseMsg) obj);
                }
            });
            return;
        }
        sendQueryMessage(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceIconVersion$8(Device device, ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg);
        if (responseMsg == null) {
            queryAll();
            return;
        }
        if (responseMsg.getResData().length() < 20) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
        int versionNum = ScreenIconUtils.getVersionNum(parseInt);
        int parseInt2 = Integer.parseInt(responseMsg.getDeviceFlag(), 16);
        ActMeshScanVM.ScanItem scanItem = new ActMeshScanVM.ScanItem();
        scanItem.currentVersion = String.format(Locale.US, "V%.1f", Float.valueOf(versionNum));
        scanItem.newVersion = ScreenIconUtils.LATEST_VERSION;
        scanItem.extData = parseInt;
        scanItem.unicastAdd = parseInt2;
        Message message = new Message();
        message.what = 2;
        message.obj = scanItem;
        Handler handler = this.updateHandler;
        if (handler != null) {
            handler.sendMessageDelayed(message, 10L);
        }
        this.queryReadyMap.put(Long.valueOf(device.getDeviceId()), true);
        queryAll();
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

    private void queryAll() {
        if (this.queryIndex >= ((ActMeshScanVM) this.mViewModel).addList.getValue().size()) {
            this.queryIndex = 0;
        }
        for (int i = this.queryIndex; i < ((ActMeshScanVM) this.mViewModel).addList.getValue().size(); i++) {
            Device device = ((ActMeshScanVM) this.mViewModel).addList.getValue().get(i);
            this.queryIndex++;
            if (!this.queryReadyMap.containsKey(Long.valueOf(device.getDeviceId()))) {
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
                connectDevice(device, scanItem);
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActMeshScanIcon.this.lambda$upgradeAll$9(scanItem);
                    }
                }, 20000L);
                return;
            }
        }
        if (this.upgradeIndex >= ((ActMeshScanVM) this.mViewModel).addList.getValue().size()) {
            ((ActMeshScanVM) this.mViewModel).upgradeStateLiveData.setValue(3);
            this.upgradeIndex = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$upgradeAll$9(ActMeshScanVM.ScanItem scanItem) {
        if (scanItem.upgradeFlag == 2) {
            scanItem.upgradeFlag = 5;
            sendChangeMessage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectDevice(final Device device, final ActMeshScanVM.ScanItem item) {
        BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
        if (connectedDevice != null) {
            LHomeLog.i(getClass(), "代理节点地址 = " + connectedDevice.getAddress() + "   " + item.bluetoothDevice.getAddress() + "  ");
            if (item.bluetoothDevice.getAddress().equals(connectedDevice.getAddress())) {
                startUpgrade(device, item);
                return;
            }
        }
        LHomeLog.i(Constants.MESH_LOG, getClass(), "开始连接:" + item.bluetoothDevice.getAddress());
        this.connectBleCallback = new IConnectBleCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.6
            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectSuccess(String btModule, BluetoothDevice connectedDevice2) {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "**********onConnectSuccess**********");
                ActMeshScanIcon.this.startUpgrade(device, item);
            }

            @Override // com.ltech.smarthome.blemesh.IConnectBleCallback
            public void onConnectFail() {
                LHomeLog.i(Constants.MESH_LOG, getClass(), "**********onConnectFail**********");
                if (item.upgradeFlag == 2) {
                    if (ActMeshScanIcon.this.connectFailTime <= 3) {
                        ActMeshScanIcon.this.connectFailTime++;
                        ActMeshScanIcon.this.connectDevice(device, item);
                    } else {
                        ActMeshScanIcon.this.connectFailTime = 0;
                        item.upgradeFlag = 5;
                        ActMeshScanIcon.this.sendChangeMessage();
                    }
                }
            }
        };
        Injection.mesh().connect(item.bluetoothDevice, this.connectBleCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUpgrade(Device device, ActMeshScanVM.ScanItem scanItem) {
        this.displayType = 1;
        this.iconData = isG4(device) ? ScreenIconUtils.getG4SmallIconData(scanItem.extData) : ScreenIconUtils.getSmallIconData(scanItem.extData);
        this.totalNum = 152 - scanItem.extData;
        if (this.iconData != null) {
            scanItem.upgradeFlag = 3;
            sendChangeMessage();
            Handler handler = this.updateHandler;
            if (handler == null) {
                return;
            }
            this.curDevice = device;
            this.curScanItem = scanItem;
            handler.removeMessages(6);
            this.updateHandler.removeMessages(5);
            Message message = new Message();
            message.what = 5;
            this.updateHandler.sendMessage(message);
            Message message2 = new Message();
            message2.what = 6;
            message2.obj = scanItem;
            message2.arg1 = this.upgradeIndex;
            this.updateHandler.sendMessageDelayed(message2, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
            return;
        }
        scanItem.upgradeFlag = 5;
        sendChangeMessage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isG4(Device device) {
        return device.getProductId().equals(ProductId.ID_SMART_PANEL_G4) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIconData(final Device device, final ActMeshScanVM.ScanItem scanItem) {
        if (device.getProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4) || device.getProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4_PRO)) {
            setNewIconData(device, scanItem);
        } else {
            CmdAssistant.getDeviceAssistant(device, new int[0]).setIconData(this, this.displayType, scanItem.extData, this.iconData, new IAction() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMeshScanIcon.this.lambda$setIconData$10(scanItem, device, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIconData$10(ActMeshScanVM.ScanItem scanItem, Device device, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData().length() < 20) {
            int i = this.revFailTime + 1;
            this.revFailTime = i;
            if (i > 2) {
                scanItem.upgradeFlag = 5;
                sendChangeMessage();
                return;
            }
            return;
        }
        if (Integer.parseInt(responseMsg.getDeviceFlag(), 16) != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress()) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
        this.revFailTime = 0;
        if (this.displayType == 1) {
            this.displayType = 2;
            this.iconData = isG4(device) ? ScreenIconUtils.getG4BigIconData(scanItem.extData) : ScreenIconUtils.getBigIconData(scanItem.extData);
            setIconData(device, scanItem);
            return;
        }
        scanItem.progress = 100.0f - (((152 - parseInt) / this.totalNum) * 100.0f);
        sendChangeMessage();
        if (parseInt < 152) {
            scanItem.extData = parseInt;
            this.displayType = 1;
            this.iconData = isG4(device) ? ScreenIconUtils.getG4SmallIconData(scanItem.extData) : ScreenIconUtils.getSmallIconData(scanItem.extData);
            setIconData(device, scanItem);
            return;
        }
        scanItem.upgradeFlag = 4;
        scanItem.currentVersion = scanItem.newVersion;
        sendChangeMessage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNewIconData(final Device device, final ActMeshScanVM.ScanItem scanItem) {
        BaseUpgradeHelper upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 5, GZipUtil.compress(this.iconData), getMainHandler());
        this.upgradeHelper = upgradeHelper;
        MeshUpgradeThemeHelper meshUpgradeThemeHelper = (MeshUpgradeThemeHelper) upgradeHelper;
        int i = scanItem.extData;
        int i2 = this.displayType;
        meshUpgradeThemeHelper.setHeader(1, i, i2 == 2 ? 36 : 28, i2 == 2 ? 36 : 28, 8, true);
        CtrlPackage ctrlPackage = (CtrlPackage) SmartUtils.getICtrlConverter().convert(device);
        if (ProductRepository.isBLeDevice(device.getProductId())) {
            ctrlPackage.setAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
        }
        scanItem.upgradeFlag = 3;
        sendChangeMessage();
        this.upgradeHelper.setCtrlPackage(ctrlPackage);
        this.upgradeHelper.setUpgradeCallback(new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.7
            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgrading(float progress) {
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeSuccess(int nextNum) {
                if (ActMeshScanIcon.this.displayType == 1) {
                    ActMeshScanIcon.this.displayType = 2;
                    ActMeshScanIcon actMeshScanIcon = ActMeshScanIcon.this;
                    actMeshScanIcon.iconData = actMeshScanIcon.isG4(device) ? ScreenIconUtils.getG4BigIconData(scanItem.extData) : ScreenIconUtils.getBigIconData(scanItem.extData);
                    ActMeshScanIcon.this.setNewIconData(device, scanItem);
                    return;
                }
                scanItem.progress = 100.0f - (((ScreenIconUtils.getG4IconLength() - nextNum) / ActMeshScanIcon.this.totalNum) * 100.0f);
                ActMeshScanIcon.this.sendChangeMessage();
                if (scanItem.extData < ScreenIconUtils.getG4IconLength()) {
                    scanItem.extData = nextNum;
                    ActMeshScanIcon.this.displayType = 1;
                    ActMeshScanIcon actMeshScanIcon2 = ActMeshScanIcon.this;
                    actMeshScanIcon2.iconData = actMeshScanIcon2.isG4(device) ? ScreenIconUtils.getG4SmallIconData(scanItem.extData) : ScreenIconUtils.getSmallIconData(scanItem.extData);
                    ActMeshScanIcon.this.setNewIconData(device, scanItem);
                    return;
                }
                scanItem.upgradeFlag = 4;
                ActMeshScanVM.ScanItem scanItem2 = scanItem;
                scanItem2.currentVersion = scanItem2.newVersion;
                ActMeshScanIcon.this.sendChangeMessage();
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeFail() {
                LHomeLog.i(getClass(), "**********onUpgradeFail**********");
                scanItem.upgradeFlag = 5;
                ActMeshScanIcon.this.sendChangeMessage();
            }
        });
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScanIcon.8
            @Override // java.lang.Runnable
            public void run() {
                ActMeshScanIcon.this.upgradeHelper.reset();
                ActMeshScanIcon.this.upgradeHelper.startUpgrade();
            }
        }, 200L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (7 == helper.getCode()) {
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
}