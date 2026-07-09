package com.ltech.smarthome.upgrade;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.airoha.libfota.constant.ProductFlag;
import com.airoha.libfota.core.OnAirohaOtaEventListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActBtOtaSingleBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.McuVersion;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.upgrade.BluetoothLeService;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.runtime.Permission;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class ActBtOtaSingle extends VMActivity<ActBtOtaSingleBinding, ActBtOtaVM> {
    private static final int ACTIVITY_CHOOSE_FILE = 1;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    private static final int FILE_SELECT_CODE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    private static final String STR_INVALID = "invalid";
    private static final String STR_PROGRAMMING = "programming";
    private static final String STR_VALID = "valid";
    private static final String STR_WACHANGED = "W.A. changed";
    private static final String TAG = "ActBtOtaSingle";
    private static final int UPGRADE_FAIL = 3;
    private static final int UPGRADE_NEW = 1;
    private static final int UPGRADE_SUCCESS = 2;
    private static final int UPGRADE_WAIT = 5;
    private static final int UPGRADING = 4;
    private int curType;
    private String curVer;
    private Device device;
    private boolean isAtOpen;
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String mFilePath;
    private FilePickerDialog mFilePickerDialog;
    private String mStrErrorReadable;
    private String mStrSelectedBinFileName;
    private McuVersion mcuVersion;
    private boolean mConnected = false;
    private final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            ActBtOtaSingle.this.mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (ActBtOtaSingle.this.mBluetoothLeService != null) {
                ActBtOtaSingle.this.mBluetoothLeService.getAirohaOtaMgr().setListener(ActBtOtaSingle.this.mOnAirohaOtaEventListener);
                if (!ActBtOtaSingle.this.loadFile()) {
                    SmartToast.showShort("没有找到升级文件");
                    ActBtOtaSingle.this.setUpgradeView(3);
                } else {
                    ActBtOtaSingle.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ActBtOtaSingle.this.mBluetoothLeService.connect(ActBtOtaSingle.this.mDeviceAddress);
                            ActBtOtaSingle.this.setUpgradeView(5);
                        }
                    }, 2000L);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            ActBtOtaSingle.this.mBluetoothLeService = null;
        }
    };
    private OnAirohaOtaEventListener mOnAirohaOtaEventListener = new AnonymousClass5();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_bt_ota_single;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ProductFlag.setBuildFor161X(true);
        String stringExtra = getIntent().getStringExtra("DEVICE_ADDRESS");
        this.mDeviceAddress = stringExtra;
        this.mDeviceAddress = stringExtra.replaceAll("..(?!$)", "$0:");
        ((ActBtOtaSingleBinding) this.mViewBinding).tvUpgrade.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (1 == ActBtOtaSingle.this.curType) {
                    ActBtOtaSingle.this.startOTA();
                    ActBtOtaSingle.this.setUpgradeView(4);
                } else {
                    ActBtOtaSingle.this.finishActivity();
                }
            }
        });
        ((ActBtOtaSingleBinding) this.mViewBinding).ivUpgrade.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActBtOtaSingle.this.mConnected) {
                    ActBtOtaSingle.this.mBluetoothLeService.disconnect();
                    ActBtOtaSingle.this.setUpgradeView(3);
                } else {
                    ActBtOtaSingle.this.mBluetoothLeService.connect(ActBtOtaSingle.this.mDeviceAddress);
                    ActBtOtaSingle.this.setUpgradeView(5);
                }
            }
        });
        bindService(new Intent(this, (Class<?>) BluetoothLeService.class), this.mServiceConnection, 1);
        initFileDialog();
        checkReadPermission();
        BluetoothLeService bluetoothLeService = this.mBluetoothLeService;
        if (bluetoothLeService != null) {
            boolean connect = bluetoothLeService.connect(this.mDeviceAddress);
            Log.d(TAG, "Connect request result=" + connect);
        }
        requestRnWpermission();
        setUpgradeView(5);
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
        Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        this.device = deviceById;
        setTitle(deviceById.getDeviceName());
        this.mcuVersion = Injection.repo().mcu().getDeviceVersion(Constants.BT686_MESH);
        Device device = this.device;
        if (device != null) {
            if (device.getProductId().equals(ProductId.ID_KEY_SWITCH_1) || this.device.getProductId().equals(ProductId.ID_KEY_SWITCH_2) || this.device.getProductId().equals(ProductId.ID_KEY_SWITCH_3) || this.device.getProductId().equals(ProductId.ID_KEY_SWITCH_4) || this.device.getProductId().equals(ProductId.ID_BODY_SENSOR) || this.device.getProductId().equals(ProductId.ID_RC4) || this.device.getProductId().equals(ProductId.ID_RC4S) || this.device.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) || this.device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
                this.mcuVersion = Injection.repo().mcu().getDeviceVersion(Constants.BT686_MESH_35);
            }
        }
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
                    ((ActBtOtaSingleBinding) this.mViewBinding).tvUpgradeTip1.setText(path);
                    this.mStrSelectedBinFileName = path;
                    BluetoothLeService bluetoothLeService = this.mBluetoothLeService;
                    if (bluetoothLeService != null) {
                        bluetoothLeService.getAirohaOtaMgr().setOtaBinFileRaw(byteArray);
                        this.mFilePath = null;
                        return;
                    } else {
                        this.mFilePath = FileBrowser.getPathFromUri(this, data2);
                        return;
                    }
                } catch (Exception e) {
                    Log.d(TAG, " inputStream exception:" + e.getMessage());
                    return;
                }
            }
            ((ActBtOtaSingleBinding) this.mViewBinding).tvUpgradeTip1.setText(path + " is not valid.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startOTA() {
        this.mBluetoothLeService.getAirohaOtaMgr().startFota();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        BluetoothLeService bluetoothLeService = this.mBluetoothLeService;
        if (bluetoothLeService != null) {
            bluetoothLeService.getAirohaOtaMgr().close();
            unbindService(this.mServiceConnection);
            this.mBluetoothLeService = null;
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        return new IntentFilter();
    }

    private void checkReadPermission() {
        if (ContextCompat.checkSelfPermission(this, Permission.READ_EXTERNAL_STORAGE) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    private void initFileDialog() {
        DialogProperties dialogProperties = new DialogProperties();
        dialogProperties.selection_mode = 0;
        dialogProperties.selection_type = 0;
        dialogProperties.root = new File(DialogConfigs.DEFAULT_DIR);
        dialogProperties.extensions = new String[]{"bin", "BIN"};
        FilePickerDialog filePickerDialog = new FilePickerDialog(this, dialogProperties);
        this.mFilePickerDialog = filePickerDialog;
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.4
            @Override // com.github.angads25.filepicker.controller.DialogSelectionListener
            public void onSelectedFilePaths(String[] files) {
                if (files == null || files.length <= 0) {
                    return;
                }
                ((ActBtOtaSingleBinding) ActBtOtaSingle.this.mViewBinding).tvUpgradeTip1.setText(files[0]);
                ActBtOtaSingle.this.mStrSelectedBinFileName = files[0];
                ActBtOtaSingle.this.mBluetoothLeService.getAirohaOtaMgr().setOtaBinFileName(files[0]);
            }
        });
    }

    /* renamed from: com.ltech.smarthome.upgrade.ActBtOtaSingle$5, reason: invalid class name */
    class AnonymousClass5 implements OnAirohaOtaEventListener {
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
        public void OnWorkingAreaChanged() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onDataCallback(byte[] s) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onWaitNewOtaShow() {
        }

        AnonymousClass5() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnRequestMtuChangeStatus(boolean isAccepted) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnNewMtu(final int mtu) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattConnected() {
            ActBtOtaSingle.this.mConnected = true;
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.d(ActBtOtaSingle.TAG, "UI OnGattConnected");
                    ActBtOtaSingle.this.invalidateOptionsMenu();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattDisconnected() {
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.2
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOtaSingle.this.mConnected = false;
                    Toast.makeText(ActBtOtaSingle.this, "Disconnected", 0).show();
                    if (ActBtOtaSingle.this.curType != 1) {
                        ActBtOtaSingle.this.setUpgradeView(2);
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaStatus(final String workingArea, final String area1Rev, final boolean isArea1Valid, final String area2Rev, final boolean isArea2Valid) {
            ActBtOtaSingle.this.mBluetoothLeService.getAirohaOtaMgr().writeAtCharc("$OpenFscAtEngine$".getBytes());
            ActBtOtaSingle.this.mBluetoothLeService.getAirohaOtaMgr().writeAtCharc("AT+VER\r\n".getBytes());
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnUpdateProgrammingProgress(final float progress) {
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.3
                @Override // java.lang.Runnable
                public void run() {
                    if (ActBtOtaSingle.this.mViewBinding != null) {
                        ((ActBtOtaSingleBinding) ActBtOtaSingle.this.mViewBinding).tvProgress.setText(String.format("%.1f", Float.valueOf(progress * 100.0f)));
                    }
                    if (progress * 100.0f >= 100.0f) {
                        ActBtOtaSingle.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                ActBtOtaSingle.this.setUpgradeView(2);
                                ActBtOtaSingle.this.mBluetoothLeService.getAirohaOtaMgr().applyNewFw();
                                ((ActBtOtaSingleBinding) ActBtOtaSingle.this.mViewBinding).tvUpgrade.setText("完成");
                            }
                        }, 500L);
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleOtaDisabled(final byte errorCode) {
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.4
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOtaSingle.this.mBluetoothLeService.disconnect();
                    ActBtOtaSingle.this.mStrErrorReadable = String.format("ERROR_CODE 0x%s. %s", Integer.toHexString(errorCode), "OTA function is disabled on the target device");
                    Toast.makeText(ActBtOtaSingle.this, ActBtOtaSingle.this.mStrErrorReadable, 1).show();
                    ActBtOtaSingle.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBinFileParseException() {
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.5
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOtaSingle.this.mBluetoothLeService.disconnect();
                    ActBtOtaSingle.this.mStrErrorReadable = "BIN info can't be parsed correctly";
                    Toast.makeText(ActBtOtaSingle.this, ActBtOtaSingle.this.mStrErrorReadable, 0).show();
                    ActBtOtaSingle.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnRetryFailed() {
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.6
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOtaSingle.this.mBluetoothLeService.disconnect();
                    ActBtOtaSingle.this.mStrErrorReadable = "OnRetryFailed";
                    Toast.makeText(ActBtOtaSingle.this, ActBtOtaSingle.this.mStrErrorReadable, 0).show();
                    ActBtOtaSingle.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnStatusError(final byte errorCode, final String errorMsg) {
            ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.7
                @Override // java.lang.Runnable
                public void run() {
                    ActBtOtaSingle.this.mStrErrorReadable = String.format("ERROR_CODE 0x%s. %s", Integer.toHexString(errorCode), errorMsg);
                    Toast.makeText(ActBtOtaSingle.this, ActBtOtaSingle.this.mStrErrorReadable, 1).show();
                    if (Integer.toHexString(errorCode).equals("55")) {
                        ActBtOtaSingle.this.setUpgradeView(2);
                        ActBtOtaSingle.this.mBluetoothLeService.getAirohaOtaMgr().applyNewFw();
                    } else {
                        ActBtOtaSingle.this.setUpgradeView(3);
                        ActBtOtaSingle.this.mBluetoothLeService.disconnect();
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onFwVersion(final String ver) {
            if (ver.toUpperCase().compareTo(ActBtOtaSingle.this.mcuVersion.getVersionname().toUpperCase()) < 0) {
                ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.8
                    @Override // java.lang.Runnable
                    public void run() {
                        ActBtOtaSingle.this.curVer = ver;
                        ActBtOtaSingle.this.setUpgradeView(1);
                    }
                });
            } else {
                ActBtOtaSingle.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.5.9
                    @Override // java.lang.Runnable
                    public void run() {
                        ActBtOtaSingle.this.curVer = ver;
                        ((ActBtOtaVM) ActBtOtaSingle.this.mViewModel).updateBleVersion(ActBtOtaSingle.this.device, ActBtOtaSingle.this.mcuVersion.getVersionname());
                        ActBtOtaSingle.this.setUpgradeView(2);
                    }
                });
            }
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onCheckFwVersionFailed() {
            ActBtOtaSingle.this.mStrErrorReadable = "checkFwVersionFailed";
            ActBtOtaSingle actBtOtaSingle = ActBtOtaSingle.this;
            Toast.makeText(actBtOtaSingle, actBtOtaSingle.mStrErrorReadable, 1).show();
            ActBtOtaSingle.this.mBluetoothLeService.disconnect();
            ActBtOtaSingle.this.setUpgradeView(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0147, code lost:
    
        if (r7.equals(com.ltech.smarthome.model.product.ProductId.ID_RC4) == false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setUpgradeView(int r11) {
        /*
            Method dump skipped, instructions count: 994
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.upgrade.ActBtOtaSingle.setUpgradeView(int):void");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.curType == 4) {
            showUpgradingDialog();
        } else {
            super.back();
        }
    }

    private void showUpgradingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getString(R.string.app_str_stop_upgrading_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle.6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.upgrade.ActBtOtaSingle$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUpgradingDialog$0;
                lambda$showUpgradingDialog$0 = ActBtOtaSingle.this.lambda$showUpgradingDialog$0(baseDialog, view);
                return lambda$showUpgradingDialog$0;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUpgradingDialog$0(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }
}