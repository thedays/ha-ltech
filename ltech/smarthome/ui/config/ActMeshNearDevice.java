package com.ltech.smarthome.ui.config;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kookong.app.data.AppConst;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.blemesh.IScanCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.databinding.ActMeshNearDeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActMeshNearDevice extends VMActivity<ActMeshNearDeviceBinding, ActMeshScanVM> {
    private boolean bSearchDevice = false;
    BaseNormalActivity<ActMeshNearDeviceBinding>.BlueToothValueReceiver blueToothValueReceiver;

    static /* synthetic */ void lambda$startObserve$3(Void r0) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mesh_near_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActMeshScanVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActMeshScanVM) this.mViewModel).floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        ((ActMeshScanVM) this.mViewModel).roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        if (((ActMeshScanVM) this.mViewModel).placeId > 0) {
            ConfigHelper.instance().placeId = ((ActMeshScanVM) this.mViewModel).placeId;
        }
        if (((ActMeshScanVM) this.mViewModel).floorId > 0) {
            ConfigHelper.instance().floorId = ((ActMeshScanVM) this.mViewModel).floorId;
        }
        if (((ActMeshScanVM) this.mViewModel).roomId > 0) {
            ConfigHelper.instance().roomId = ((ActMeshScanVM) this.mViewModel).roomId;
        }
        ((ActMeshScanVM) this.mViewModel).deviceList.addAll(ConfigHelper.instance().scanListCache);
        setTitle(getString(R.string.add_device));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.icon_refresh);
        ((ActMeshNearDeviceBinding) this.mViewBinding).searchSpreadView.setAnimate(true);
        long j = AppConst.KOOKONG_BRANDID_MIX_STB;
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 1000 * 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(j);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        ((ActMeshNearDeviceBinding) this.mViewBinding).searchImage.setAnimation(rotateAnimation);
        ((ActMeshNearDeviceBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActMeshNearDevice$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMeshNearDevice.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActMeshScanVM) this.mViewModel).deviceNum.setValue(ActivityUtils.getTopActivity().getString(R.string.scan_device_num, new Object[]{Integer.valueOf(((ActMeshScanVM) this.mViewModel).deviceList.size())}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.tv_add_all) {
            return;
        }
        if (((ActMeshScanVM) this.mViewModel).deviceList == null || ((ActMeshScanVM) this.mViewModel).deviceList.size() == 0) {
            ToastUtils.showShort(R.string.app_add_no_device);
            return;
        }
        Injection.mesh().stopScan();
        ObservableList<ActMeshScanVM.ScanItem> observableList = ((ActMeshScanVM) this.mViewModel).deviceList;
        if (observableList == null || observableList.size() == 0) {
            return;
        }
        NavUtils.destination(ActMeshAddAllDeviceActivity.class).withLong(Constants.PLACE_ID, ((ActMeshScanVM) this.mViewModel).placeId).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injection.mesh().stopCheckNearbyDevice();
        this.blueToothValueReceiver = new BaseNormalActivity.BlueToothValueReceiver(this);
        registerReceiver(this.blueToothValueReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LHomeLog.i(ActMeshNearDevice.class, "onStart: ");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        ConfigHelper.instance().setScanListCache(((ActMeshScanVM) this.mViewModel).deviceList);
        LHomeLog.i(ActMeshNearDevice.class, "onPause: ");
        Injection.mesh().stopScan();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        refreshScanData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ActMeshNearDevice actMeshNearDevice;
        ((ActMeshScanVM) this.mViewModel).showConfigDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshNearDevice$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshNearDevice.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        ((ActMeshScanVM) this.mViewModel).startScanLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshNearDevice$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshNearDevice.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        if (ConfigHelper.instance().placeId > 0) {
            actMeshNearDevice = this;
            ((ActMeshScanVM) this.mViewModel).roomPickHelper.startObserve(actMeshNearDevice, ConfigHelper.instance().placeId, ConfigHelper.instance().roomId);
            ((ActMeshScanVM) actMeshNearDevice.mViewModel).roomPickHelper.getSelectRoomEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActMeshNearDevice$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActMeshNearDevice.lambda$startObserve$3((Void) obj);
                }
            });
        } else {
            actMeshNearDevice = this;
        }
        ((ActMeshNearDeviceBinding) actMeshNearDevice.mViewBinding).tvHelp.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActMeshNearDevice$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActMeshNearDevice.this.lambda$startObserve$4(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (((ActMeshScanVM) this.mViewModel).configBean != null) {
            ((ActMeshScanVM) this.mViewModel).showConfigDialog(bool.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            scanDevice();
        } else {
            Injection.mesh().stopScan();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(View view) {
        NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, getString(R.string.faq_url)).navigation(this);
    }

    private void scanDevice() {
        Injection.mesh().startScan(BleMeshManager.MESH_PROVISIONING_UUID, new IScanCallback() { // from class: com.ltech.smarthome.ui.config.ActMeshNearDevice$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ActMeshNearDevice.this.lambda$scanDevice$5(extendedBluetoothDevice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDevice$5(ExtendedBluetoothDevice extendedBluetoothDevice) {
        LHomeLog.i(ActMeshNearDevice.class, "scanDevice: bluetoothDevice-->" + extendedBluetoothDevice);
        if (((ActMeshScanVM) this.mViewModel).refreshRssi(extendedBluetoothDevice) || !((ActMeshScanVM) this.mViewModel).addDevice(extendedBluetoothDevice) || this.bSearchDevice) {
            return;
        }
        this.bSearchDevice = true;
        changeView();
    }

    private void changeView() {
        if (this.mViewBinding != 0) {
            ((ActMeshNearDeviceBinding) this.mViewBinding).tvNoDevice.setVisibility(0);
            ((ActMeshNearDeviceBinding) this.mViewBinding).tvHelp.setVisibility(0);
        }
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        if (Injection.mesh().getConnectedDevice() != null) {
            Injection.mesh().stopScan();
        }
        Injection.mesh().clear();
        unregisterReceiver(this.blueToothValueReceiver);
        Injection.mesh().setUpgradeStart(false);
        super.onDestroy();
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
        if (resultCode == 5007) {
            NavUtils.destination(ActHome.class).navigation(this);
        } else if (resultCode == 5008) {
            refreshScanData();
        }
    }
}