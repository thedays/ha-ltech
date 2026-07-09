package com.ltech.smarthome.upgrade;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.airoha.libfota.constant.ProductFlag;
import com.airoha.libfota.core.OnAirohaOtaEventListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.blemesh.IRefreshNetworkCallback;
import com.ltech.smarthome.databinding.ActBtOtaBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.McuVersion;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.upgrade.ActBtOtaVM;
import com.ltech.smarthome.upgrade.BluetoothLeService;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.xiaomi.mipush.sdk.Constants;
import com.yanzhenjie.permission.runtime.Permission;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

/* loaded from: classes4.dex */
public class ActBtOta extends VMActivity<ActBtOtaBinding, ActBtOtaVM> implements TextWatcher {
    private static final int ACTIVITY_CHOOSE_FILE = 1;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    private static final int FILE_SELECT_CODE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    private static final String STR_INVALID = "invalid";
    private static final String STR_PROGRAMMING = "programming";
    private static final String STR_VALID = "valid";
    private static final String STR_WACHANGED = "W.A. changed";
    private static final String TAG = "ActBtOta";
    private boolean isUpgrading;
    private BluetoothLeService mBluetoothLeService;
    private FilePickerDialog mFilePickerDialog;
    private String mStrErrorReadable;
    private McuVersion mcuVersion;
    private String networkJson;
    private boolean mConnected = false;
    private int curPos = -1;
    private final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.ltech.smarthome.upgrade.ActBtOta.7
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            LHomeLog.e(getClass(), "onServiceConnected");
            ActBtOta.this.mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (ActBtOta.this.mBluetoothLeService != null) {
                ActBtOta.this.mBluetoothLeService.getAirohaOtaMgr().setListener(ActBtOta.this.mOnAirohaOtaEventListener);
                if (!ActBtOta.this.loadFile()) {
                    SmartToast.showShort("没有找到升级文件");
                } else {
                    ActBtOta.this.connect();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            ActBtOta.this.mBluetoothLeService = null;
            LHomeLog.e(getClass(), "onServiceDisconnected");
        }
    };
    private final OnAirohaOtaEventListener mOnAirohaOtaEventListener = new AnonymousClass9();
    private final ScanCallback mScanCallbacks = new ScanCallback() { // from class: com.ltech.smarthome.upgrade.ActBtOta.12
        @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
        public void onScanResult(final int callbackType, final ScanResult result) {
            String replace = result.getDevice().getAddress().replace(Constants.COLON_SEPARATOR, "");
            for (Device device : ((ActBtOtaVM) ActBtOta.this.mViewModel).tempData) {
                if (ActBtOta.this.filterDevice(device) && replace.equals(device.getWifiMac()) && !((ActBtOtaVM) ActBtOta.this.mViewModel).tempIdData.contains(replace)) {
                    ((ActBtOtaVM) ActBtOta.this.mViewModel).tempIdData.add(replace);
                    ((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.add(new ActBtOtaVM.ScanItem(device));
                }
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
        }
    };

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_bt_ota;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        Injection.mesh().setBleFwUpgradeStart(true);
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.icon_refresh);
        setTitle(getString(R.string.bl_firmware_upgrade));
        ProductFlag.setBuildFor161X(true);
        ((ActBtOtaBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.upgrade.ActBtOta.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((ActBtOtaBinding) ActBtOta.this.mViewBinding).tvBottom.getText().toString().equals(ActBtOta.this.getString(R.string.finish))) {
                    ActBtOta.this.finishActivity();
                    return;
                }
                BluetoothLeScannerCompat.getScanner().stopScan(ActBtOta.this.mScanCallbacks);
                ActBtOta.this.networkJson = Injection.mesh().exportMeshNetwork();
                Injection.mesh().deInit();
                ((ActBtOtaBinding) ActBtOta.this.mViewBinding).tvBottom.setEnabled(false);
                ((ActBtOtaBinding) ActBtOta.this.mViewBinding).tvBottom.setText(R.string.upgrading);
                ActBtOta.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ActBtOta.this.bindService(new Intent(ActBtOta.this.activity, (Class<?>) BluetoothLeService.class), ActBtOta.this.mServiceConnection, 1);
                    }
                }, 2000L);
            }
        });
        checkReadPermission();
        requestRnWpermission();
        if (Build.VERSION.SDK_INT >= 28) {
            requestFspermission();
        }
    }

    private void requestFspermission() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.FOREGROUND_SERVICE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.FOREGROUND_SERVICE"}, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect() {
        this.curPos++;
        if (((ActBtOtaVM) this.mViewModel).mDeviceList.size() > 0 && ((ActBtOtaVM) this.mViewModel).mDeviceList.size() > this.curPos) {
            this.isUpgrading = true;
            ActBtOtaVM.ScanItem scanItem = ((ActBtOtaVM) this.mViewModel).mDeviceList.get(this.curPos);
            if (scanItem != null && scanItem.getDevice() != null && !scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_1) && !scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_2) && !scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_3) && !scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_4) && !scanItem.getDevice().getProductId().equals(ProductId.ID_BODY_SENSOR) && !scanItem.getDevice().getProductId().equals(ProductId.ID_RC4) && !scanItem.getDevice().getProductId().equals(ProductId.ID_RC4S) && !scanItem.getDevice().getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && !scanItem.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
                ((ActBtOtaVM) this.mViewModel).canClickItem = false;
                this.mBluetoothLeService.connect(scanItem.getDevice().getWifiMac().replaceAll("..(?!$)", "$0:"));
                scanItem.setUpgradeFlag(1);
                if (((ActBtOtaBinding) this.mViewBinding).rvContent.getAdapter() != null) {
                    ((ActBtOtaBinding) this.mViewBinding).rvContent.getAdapter().notifyItemChanged(this.curPos);
                    return;
                }
                return;
            }
            connect();
            return;
        }
        this.isUpgrading = false;
        ((ActBtOtaBinding) this.mViewBinding).tvBottom.setEnabled(true);
        ((ActBtOtaBinding) this.mViewBinding).tvBottom.setText(R.string.finish);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean loadFile() {
        McuVersion mcuVersion = this.mcuVersion;
        if (mcuVersion == null) {
            return false;
        }
        byte[] firmwareData = mcuVersion.getFirmwareData();
        BluetoothLeService bluetoothLeService = this.mBluetoothLeService;
        if (bluetoothLeService == null) {
            return false;
        }
        bluetoothLeService.getAirohaOtaMgr().setOtaBinFileRaw(firmwareData);
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.mcuVersion = Injection.repo().mcu().getDeviceVersion(com.ltech.smarthome.utils.Constants.BT686_MESH);
        ((ActBtOtaVM) this.mViewModel).placeId = getIntent().getLongExtra(com.ltech.smarthome.utils.Constants.PLACE_ID, 0L);
        setBackImage(R.mipmap.icon_back);
        ((ActBtOtaBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActBtOtaBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.upgrade.ActBtOta.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActBtOtaBinding) ActBtOta.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        handleData(Transformations.switchMap(((ActBtOtaVM) this.mViewModel).selectRoom, new Function1() { // from class: com.ltech.smarthome.upgrade.ActBtOta$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = ActBtOta.this.lambda$startObserve$0((Room) obj);
                return lambda$startObserve$0;
            }
        }), new IAction() { // from class: com.ltech.smarthome.upgrade.ActBtOta$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBtOta.this.lambda$startObserve$1((List) obj);
            }
        });
        ((ActBtOtaVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.upgrade.ActBtOta.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((ActBtOtaVM) ActBtOta.this.mViewModel).placeId, floor.getFloorId());
                if (((ActBtOtaVM) ActBtOta.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(ActBtOta.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((ActBtOtaVM) ActBtOta.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((ActBtOtaVM) ActBtOta.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((ActBtOtaVM) ActBtOta.this.mViewModel).mRoomList = roomListByFloorId;
                ((ActBtOtaVM) ActBtOta.this.mViewModel).setCurRoom(((ActBtOtaVM) ActBtOta.this.mViewModel).checkRoom(roomListByFloorId));
                ActBtOta.this.initRoomSpinner();
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(((ActBtOtaVM) this.mViewModel).placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(((ActBtOtaVM) this.mViewModel).placeId);
        floorListByPlaceId.add(0, floor);
        ((ActBtOtaVM) this.mViewModel).mFloorList = floorListByPlaceId;
        ((ActBtOtaVM) this.mViewModel).setCurFloor(((ActBtOtaVM) this.mViewModel).checkFloor(floorListByPlaceId));
        initFloorSpinner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Room room) {
        BluetoothLeScannerCompat.getScanner().stopScan(this.mScanCallbacks);
        if (room == null) {
            return ResourceEmptyLiveData.create();
        }
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(((ActBtOtaVM) this.mViewModel).placeId, ((ActBtOtaVM) this.mViewModel).getCurRoom().getFloorId(), ((ActBtOtaVM) this.mViewModel).getCurRoom().getRoomId());
        Collections.sort(deviceListByRoomIdFromDb, new Comparator<Device>(this) { // from class: com.ltech.smarthome.upgrade.ActBtOta.3
            @Override // java.util.Comparator
            public int compare(Device o1, Device o2) {
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() < o2.getIndex()) {
                    return -1;
                }
                if (o1.getCreatetime() == null || o2.getCreatetime() == null) {
                    return 0;
                }
                return o1.getCreatetime().compareTo(o2.getCreatetime());
            }
        });
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.setValue(Resource.success(deviceListByRoomIdFromDb));
        return mediatorLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        this.curPos = -1;
        ((ActBtOtaVM) this.mViewModel).tempData.clear();
        ((ActBtOtaVM) this.mViewModel).tempIdData.clear();
        ((ActBtOtaVM) this.mViewModel).mDeviceList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            if (device != null && (device.getProductId().equals(ProductId.ID_KEY_SWITCH_1) || device.getProductId().equals(ProductId.ID_KEY_SWITCH_2) || device.getProductId().equals(ProductId.ID_KEY_SWITCH_3) || device.getProductId().equals(ProductId.ID_KEY_SWITCH_4) || device.getProductId().equals(ProductId.ID_BODY_SENSOR) || device.getProductId().equals(ProductId.ID_RC4) || device.getProductId().equals(ProductId.ID_RC4S) || device.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB))) {
                ((ActBtOtaVM) this.mViewModel).tempData.add(device);
                ((ActBtOtaVM) this.mViewModel).mDeviceList.add(new ActBtOtaVM.ScanItem(device));
            } else if (filterDevice(device)) {
                ((ActBtOtaVM) this.mViewModel).tempData.add(device);
            }
        }
        BluetoothLeScannerCompat.getScanner().startScan(this.mScanCallbacks);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActBtOtaVM) this.mViewModel).mFloorList));
        ((ActBtOtaBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActBtOtaBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActBtOtaBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.upgrade.ActBtOta.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActBtOtaVM) ActBtOta.this.mViewModel).mFloorList.get(position).equals(((ActBtOtaVM) ActBtOta.this.mViewModel).selectFloor.getValue())) {
                    ((ActBtOtaVM) ActBtOta.this.mViewModel).setCurFloor(((ActBtOtaVM) ActBtOta.this.mViewModel).mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActBtOtaBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActBtOtaBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActBtOtaVM) this.mViewModel).mRoomList));
        ((ActBtOtaBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.upgrade.ActBtOta.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActBtOtaVM) ActBtOta.this.mViewModel).mRoomList.get(position).equals(((ActBtOtaVM) ActBtOta.this.mViewModel).selectRoom.getValue())) {
                    ((ActBtOtaVM) ActBtOta.this.mViewModel).setCurRoom(((ActBtOtaVM) ActBtOta.this.mViewModel).mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActBtOtaBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActBtOtaBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActBtOtaBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActBtOtaBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActBtOtaVM) this.mViewModel).placeInfoResult.retry();
    }

    protected boolean filterDevice(Device device) {
        if (!device.isSubDevice() && this.mcuVersion != null && ((device.getBleversion() == null || device.getBleversion().toUpperCase().compareTo(this.mcuVersion.getVersionname().toUpperCase()) != 0) && !ProductRepository.isNewBleModule(device))) {
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "123031312002001":
                case "4057094887997440":
                case "120042314364601":
                case "120042314375001":
                case "120042314380701":
                case "120042314382401":
                case "120042314384101":
                case "120042616112401":
                case "122110709484501":
                    break;
                default:
                    return ProductRepository.isBLeDevice(device.getProductId()) || ProductRepository.isSuperPanel(device.getProductId());
            }
        }
        return false;
    }

    private void requestRnWpermission() {
        if (ActivityCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("application/octet-stream");
        startActivityForResult(Intent.createChooser(intent, "Choose a file"), 1);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 1) {
            Uri data2 = data.getData();
            String path = data2.getPath();
            if (path != null && path.length() > 0 && path.endsWith(".bin")) {
                try {
                    InputStream openInputStream = getContentResolver().openInputStream(data2);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bArr = new byte[512];
                    while (true) {
                        int read = openInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        } else {
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    BluetoothLeService bluetoothLeService = this.mBluetoothLeService;
                    if (bluetoothLeService != null) {
                        bluetoothLeService.getAirohaOtaMgr().setOtaBinFileRaw(byteArray);
                        ((ActBtOtaBinding) this.mViewBinding).tvBottom.setEnabled(true);
                        return;
                    }
                    return;
                } catch (Exception e) {
                    Log.d(TAG, " inputStream exception:" + e.getMessage());
                    return;
                }
            }
            SmartToast.showShort(path + " is not valid.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startOTA() {
        this.mBluetoothLeService.getAirohaLink().cancelTimer();
        LHomeLog.e(getClass(), "startOTA");
        this.mBluetoothLeService.getAirohaOtaMgr().startFota();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Injection.mesh().setBleFwUpgradeStart(false);
        BluetoothLeService bluetoothLeService = this.mBluetoothLeService;
        if (bluetoothLeService != null) {
            bluetoothLeService.getAirohaLink().cancelTimer();
            this.mBluetoothLeService.getAirohaLink().disconnect();
            this.mBluetoothLeService.getAirohaLink().close();
            this.mBluetoothLeService.getAirohaOtaMgr().close();
            unbindService(this.mServiceConnection);
            this.mBluetoothLeService = null;
        }
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.8
            @Override // java.lang.Runnable
            public void run() {
                ActBtOta actBtOta = ActBtOta.this;
                actBtOta.setEditString(actBtOta.getString(resourceId));
            }
        });
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        return new IntentFilter();
    }

    private void checkReadPermission() {
        if (ContextCompat.checkSelfPermission(this, Permission.READ_EXTERNAL_STORAGE) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    /* renamed from: com.ltech.smarthome.upgrade.ActBtOta$9, reason: invalid class name */
    class AnonymousClass9 implements OnAirohaOtaEventListener {
        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBatteryLevel(final int batteryLevel) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnReportProgramThroughput(final float throughput) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaChanged() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onDataCallback(byte[] s) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onWaitNewOtaShow() {
        }

        AnonymousClass9() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnRequestMtuChangeStatus(boolean isAccepted) {
            if (isAccepted) {
                ActBtOta.this.mStrErrorReadable = "request MTU accepted";
            } else {
                ActBtOta.this.mStrErrorReadable = "request MTU not accepted";
            }
            LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnNewMtu(final int mtu) {
            LHomeLog.e(getClass(), "MTU changed to: " + mtu);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattConnected() {
            ActBtOta.this.mConnected = true;
            LHomeLog.e(getClass(), "OnGattConnected");
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattDisconnected() {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.1
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOtaVM.ScanItem scanItem;
                    ActBtOta.this.mConnected = false;
                    LHomeLog.e(getClass(), "Disconnected");
                    Toast.makeText(ActBtOta.this, "Disconnected", 0).show();
                    if (((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.size() > ActBtOta.this.curPos && (scanItem = ((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.get(ActBtOta.this.curPos)) != null && scanItem.getUpgradeFlag() != 3) {
                        scanItem.setUpgradeFlag(4);
                        ((ActBtOtaBinding) ActBtOta.this.mViewBinding).rvContent.getAdapter().notifyItemChanged(ActBtOta.this.curPos);
                    }
                    ActBtOta.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ActBtOta.this.connect();
                        }
                    }, 1000L);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaStatus(final String workingArea, final String area1Rev, final boolean isArea1Valid, final String area2Rev, final boolean isArea2Valid) {
            ActBtOta.this.mBluetoothLeService.getAirohaOtaMgr().writeAtCharc("$OpenFscAtEngine$".getBytes());
            ActBtOta.this.mBluetoothLeService.getAirohaOtaMgr().writeAtCharc("AT+VER\r\n".getBytes());
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnUpdateProgrammingProgress(final float progress) {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.2
                @Override // java.lang.Runnable
                public void run() {
                    if (((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.size() > ActBtOta.this.curPos) {
                        final ActBtOtaVM.ScanItem scanItem = ((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.get(ActBtOta.this.curPos);
                        if (scanItem.getUpgradeFlag() != 2) {
                            scanItem.setUpgradeFlag(2);
                        }
                        scanItem.setProgress(String.format("%.1f", Float.valueOf(progress * 100.0f)));
                        ((ActBtOtaBinding) ActBtOta.this.mViewBinding).rvContent.getAdapter().notifyItemChanged(ActBtOta.this.curPos);
                        if (progress * 100.0f >= 100.0f) {
                            ActBtOta.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    scanItem.setUpgradeFlag(3);
                                    ((ActBtOtaBinding) ActBtOta.this.mViewBinding).rvContent.getAdapter().notifyItemChanged(ActBtOta.this.curPos);
                                    ActBtOta.this.mBluetoothLeService.getAirohaOtaMgr().applyNewFw();
                                    ActBtOta.this.mBluetoothLeService.getAirohaLink().startTimer();
                                }
                            }, 500L);
                        }
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleBootCodeNotMatching() {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.3
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOta.this.mStrErrorReadable = "Boot Code Not Matching";
                    ActBtOta.this.otaError();
                    LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
                    ActBtOta.this.mBluetoothLeService.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleCodeAreaAddrNotMatching() {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.4
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOta.this.mStrErrorReadable = "Code Area Address Not Matching";
                    ActBtOta.this.otaError();
                    LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
                    ActBtOta.this.mBluetoothLeService.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleOtaDisabled(final byte errorCode) {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.5
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOta.this.otaError();
                    ActBtOta.this.mStrErrorReadable = String.format("ERROR_CODE 0x%s. %s", Integer.toHexString(errorCode), "OTA function is disabled on the target device");
                    LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
                    Toast.makeText(ActBtOta.this, ActBtOta.this.mStrErrorReadable, 1).show();
                    ActBtOta.this.mBluetoothLeService.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBinFileParseException() {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.6
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOta.this.otaError();
                    ActBtOta.this.mStrErrorReadable = "BIN info can't be parsed correctly";
                    LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
                    Toast.makeText(ActBtOta.this, ActBtOta.this.mStrErrorReadable, 0).show();
                    ActBtOta.this.mBluetoothLeService.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnRetryFailed() {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.7
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOta.this.otaError();
                    ActBtOta.this.mStrErrorReadable = "OnRetryFailed";
                    LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
                    Toast.makeText(ActBtOta.this, ActBtOta.this.mStrErrorReadable, 0).show();
                    ActBtOta.this.mBluetoothLeService.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnStatusError(final byte errorCode, final String errorMsg) {
            ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.8
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOta.this.otaError();
                    ActBtOta.this.mStrErrorReadable = String.format("ERROR_CODE 0x%s. %s", Integer.toHexString(errorCode), errorMsg);
                    LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
                    Toast.makeText(ActBtOta.this, ActBtOta.this.mStrErrorReadable, 1).show();
                    if (Integer.toHexString(errorCode).equals("55")) {
                        ActBtOta.this.mBluetoothLeService.getAirohaLink().startTimer();
                        ActBtOta.this.mBluetoothLeService.getAirohaOtaMgr().applyNewFw();
                    } else {
                        ActBtOta.this.mBluetoothLeService.disconnect();
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onFwVersion(String ver) {
            LHomeLog.e(getClass(), "fw ver");
            ActBtOta.this.mBluetoothLeService.getAirohaLink().cancelTimer();
            if (ver.toUpperCase().compareTo(ActBtOta.this.mcuVersion.getVersionname().toUpperCase()) != 0) {
                ActBtOta.this.startOTA();
            } else {
                ActBtOta.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.9
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ActBtOta.this.curPos < ((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.size()) {
                            LHomeLog.e(getClass(), "already OTA");
                            ActBtOtaVM.ScanItem scanItem = ((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.get(ActBtOta.this.curPos);
                            scanItem.setUpgradeFlag(3);
                            ((ActBtOtaBinding) ActBtOta.this.mViewBinding).rvContent.getAdapter().notifyItemChanged(ActBtOta.this.curPos);
                            ActBtOta.this.mBluetoothLeService.disconnect();
                            ((ActBtOtaVM) ActBtOta.this.mViewModel).updateBleVersion(scanItem.getDevice(), ActBtOta.this.mcuVersion.getVersionname());
                            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.9.9.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    ActBtOta.this.connect();
                                }
                            }, 3000L);
                        }
                    }
                });
            }
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onCheckFwVersionFailed() {
            ActBtOta.this.otaError();
            ActBtOta.this.mStrErrorReadable = "onCheckFwVersionFailed";
            LHomeLog.e(getClass(), ActBtOta.this.mStrErrorReadable);
            ActBtOta actBtOta = ActBtOta.this;
            Toast.makeText(actBtOta, actBtOta.mStrErrorReadable, 0).show();
            ActBtOta.this.mBluetoothLeService.disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void otaError() {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOta.10
            @Override // java.lang.Runnable
            public void run() {
                ((ActBtOtaVM) ActBtOta.this.mViewModel).mDeviceList.get(ActBtOta.this.curPos).setUpgradeFlag(4);
                ((ActBtOtaBinding) ActBtOta.this.mViewBinding).rvContent.getAdapter().notifyItemChanged(ActBtOta.this.curPos);
            }
        });
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
        ((ActBtOtaVM) this.mViewModel).search(s.toString());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        BluetoothLeScannerCompat.getScanner().stopScan(this.mScanCallbacks);
        Injection.mesh().init();
        Injection.mesh().refreshNetwork(this.networkJson, new IRefreshNetworkCallback(this) { // from class: com.ltech.smarthome.upgrade.ActBtOta.11
            @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
            public void onRefreshFail() {
            }

            @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
            public void onRefreshSuccess() {
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActBtOtaBinding) this.mViewBinding).tvBottom.isEnabled()) {
            ((ActBtOtaBinding) this.mViewBinding).tvBottom.setText(R.string.upgrade_all);
            ((ActBtOtaVM) this.mViewModel).tempIdData.clear();
            ((ActBtOtaVM) this.mViewModel).mDeviceList.clear();
            BluetoothLeScannerCompat.getScanner().stopScan(this.mScanCallbacks);
            BluetoothLeScannerCompat.getScanner().startScan(this.mScanCallbacks);
        }
    }

    static class BtVersion {
        private String mac;
        private int ver;

        public BtVersion(String mac, int ver) {
            this.mac = mac;
            this.ver = ver;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public int getVer() {
            return this.ver;
        }

        public void setVer(int ver) {
            this.ver = ver;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.isUpgrading) {
            showUpgradingDialog();
        } else {
            super.back();
        }
    }

    private void showUpgradingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getString(R.string.app_str_stop_upgrading_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.upgrade.ActBtOta.13
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.upgrade.ActBtOta$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUpgradingDialog$2;
                lambda$showUpgradingDialog$2 = ActBtOta.this.lambda$showUpgradingDialog$2(baseDialog, view);
                return lambda$showUpgradingDialog$2;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUpgradingDialog$2(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }
}