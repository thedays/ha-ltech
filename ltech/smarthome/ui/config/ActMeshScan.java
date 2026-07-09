package com.ltech.smarthome.ui.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.blemesh.IScanCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.databinding.ActMeshScanBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.device.super_panel.ActSelectDeviceGroupForSuperPanel;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActMeshScan extends VMActivity<ActMeshScanBinding, ActMeshScanVM> {
    BlueToothValueReceiver blueToothValueReceiver;
    private ConstraintSet mConstraintSet;
    private boolean bSearchDevice = false;
    private long scanLastTime = 0;
    int retry = 0;

    static /* synthetic */ void lambda$startObserve$2(Void r0) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mesh_scan;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        Injection.mesh().setUpgradeStart(true);
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.icon_refresh);
        ((ActMeshScanBinding) this.mViewBinding).tvHelp.getPaint().setFlags(8);
        ConstraintSet constraintSet = new ConstraintSet();
        this.mConstraintSet = constraintSet;
        constraintSet.clone(this, R.layout.act_mesh_scan_1);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injection.mesh().stopCheckNearbyDevice();
        this.blueToothValueReceiver = new BlueToothValueReceiver();
        registerReceiver(this.blueToothValueReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LHomeLog.i(ActMeshScan.class, "onStart: ");
        initListData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LHomeLog.i(ActMeshScan.class, "onStop: ");
        Injection.mesh().stopScan();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (((ActMeshScanVM) this.mViewModel).controlId != -1) {
            super.back();
        } else {
            NavUtils.destination(ActHome.class).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        refreshScanData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ActMeshScan actMeshScan;
        ((ActMeshScanVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        long longExtra = getIntent().getLongExtra("device_id", -1L);
        if (((ActMeshScanVM) this.mViewModel).controlId != -1) {
            Device deviceById = Injection.repo().device().getDeviceById(((ActMeshScanVM) this.mViewModel).controlId);
            if (((ActMeshScanVM) this.mViewModel).filerDevice == null) {
                ((ActMeshScanVM) this.mViewModel).filerDevice = deviceById;
            }
        } else if (longExtra != -1) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(longExtra);
            if (((ActMeshScanVM) this.mViewModel).filerDevice == null) {
                ((ActMeshScanVM) this.mViewModel).filerDevice = deviceByDeviceId;
            }
        }
        ((ActMeshScanVM) this.mViewModel).showConfigDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScan$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScan.this.lambda$startObserve$0((Boolean) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScan$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshScan.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        if (ConfigHelper.instance().placeId > 0) {
            actMeshScan = this;
            ((ActMeshScanVM) this.mViewModel).roomPickHelper.startObserve(actMeshScan, ConfigHelper.instance().placeId, ConfigHelper.instance().roomId);
            ((ActMeshScanVM) actMeshScan.mViewModel).roomPickHelper.getSelectRoomEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshScan$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActMeshScan.lambda$startObserve$2((Void) obj);
                }
            });
        } else {
            actMeshScan = this;
        }
        ((ActMeshScanBinding) actMeshScan.mViewBinding).tvHelp.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScan$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActMeshScan.this.lambda$startObserve$3(view);
            }
        });
        ((ActMeshScanVM) actMeshScan.mViewModel).showSuperPanelConfigSuccess.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.config.ActMeshScan.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActMeshScan.this.showSuperPanelConfigSuccessDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        if (((ActMeshScanVM) this.mViewModel).configBean != null) {
            ((ActMeshScanVM) this.mViewModel).showConfigDialog(bool.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.scanLastTime > 5000) {
                this.scanLastTime = currentTimeMillis;
                if (bleIsOk()) {
                    scanDevice();
                }
            }
        } else {
            Injection.mesh().stopScan();
        }
        ((ActMeshScanBinding) this.mViewBinding).spreadviewScan.setAnimate(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(View view) {
        NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, getString(R.string.faq_url)).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSuperPanelConfigSuccessDialog() {
        this.retry = 0;
        MessageDialog.show(this, getString(R.string.app_str_super_panel_config_success), getString(R.string.app_str_super_panel_config_success_tip)).setCancelButton(getString(R.string.app_str_manual_sync_device)).setOkButton(getString(R.string.app_str_auto_sync_device)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScan.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ActMeshScan.this.refreshDevice();
                return false;
            }
        }).setOnCancelButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshScan.2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                NavUtils.destination(ActSelectDeviceGroupForSuperPanel.class).withLong(Constants.PLACE_ID, ((ActMeshScanVM) ActMeshScan.this.mViewModel).filerDevice.getPlaceId()).withLong("device_id", ((ActMeshScanVM) ActMeshScan.this.mViewModel).filerDevice.getDeviceId()).withRequestCode(5006).navigation(ActMeshScan.this.activity);
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDevice() {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        if (deviceByDeviceId != null) {
            if (TextUtils.isEmpty(deviceByDeviceId.getMcuversion()) && this.retry <= 4) {
                showLoadingDialog("");
                this.retry++;
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActMeshScan.4
                    @Override // java.lang.Runnable
                    public void run() {
                        ActMeshScan.this.refreshDevice();
                    }
                }, 1000L);
                return;
            }
            ((ActMeshScanVM) this.mViewModel).filerDevice = deviceByDeviceId;
        }
        dismissLoadingDialog();
        ((ActMeshScanVM) this.mViewModel).syncDeviceAndGroup();
    }

    private void scanDevice() {
        Injection.mesh().startScan(BleMeshManager.MESH_PROVISIONING_UUID, new IScanCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshScan$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ActMeshScan.this.lambda$scanDevice$4(extendedBluetoothDevice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDevice$4(ExtendedBluetoothDevice extendedBluetoothDevice) {
        LHomeLog.i(ActMeshScan.class, "scanDevice: bluetoothDevice-->" + extendedBluetoothDevice);
        if (((ActMeshScanVM) this.mViewModel).refreshRssi(extendedBluetoothDevice) || !((ActMeshScanVM) this.mViewModel).addDevice(extendedBluetoothDevice) || this.bSearchDevice) {
            return;
        }
        this.bSearchDevice = true;
        changeView();
    }

    private void changeView() {
        if (this.mViewBinding != 0) {
            ((ActMeshScanBinding) this.mViewBinding).spreadviewScan.setAnimate(false);
            TransitionManager.beginDelayedTransition(((ActMeshScanBinding) this.mViewBinding).constraintlayout);
            this.mConstraintSet.applyTo(((ActMeshScanBinding) this.mViewBinding).constraintlayout);
            ((ActMeshScanBinding) this.mViewBinding).spreadviewScan.requestLayout();
            ((ActMeshScanBinding) this.mViewBinding).spreadviewScan.setAnimate(true);
            ((ActMeshScanBinding) this.mViewBinding).tvNoDevice.setVisibility(0);
            ((ActMeshScanBinding) this.mViewBinding).tvHelp.setVisibility(0);
        }
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActMeshScanBinding) this.mViewBinding).spreadviewScan.clear();
        if (Injection.mesh().getConnectedDevice() != null) {
            Injection.mesh().stopScan();
        }
        Injection.mesh().clear();
        unregisterReceiver(this.blueToothValueReceiver);
        Injection.mesh().setUpgradeStart(false);
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
                        ActMeshScan.this.initListData();
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
        ((ActMeshScanVM) this.mViewModel).deviceList.clear();
        ((ActMeshScanVM) this.mViewModel).addList.getValue().clear();
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    private void refreshScanData() {
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(false);
        ((ActMeshScanVM) this.mViewModel).deviceList.clear();
        ((ActMeshScanVM) this.mViewModel).addList.getValue().clear();
        ((ActMeshScanVM) this.mViewModel).deviceNum.setValue(ActivityUtils.getTopActivity().getString(R.string.scan_device_num, new Object[]{Integer.valueOf(((ActMeshScanVM) this.mViewModel).deviceList.size())}));
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5006) {
            finishActivity();
        }
    }
}