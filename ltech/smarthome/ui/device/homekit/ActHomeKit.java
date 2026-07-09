package com.ltech.smarthome.ui.device.homekit;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActHomeKitBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.MatterDeviceResponse;
import com.ltech.smarthome.ui.matter.ActMatterSub;
import com.ltech.smarthome.ui.matter.ActMatterSubVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.List;

/* loaded from: classes4.dex */
public class ActHomeKit extends VMActivity<ActHomeKitBinding, ActMatterSubVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_kit;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActMatterSubVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMatterSubVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActMatterSubVM) this.mViewModel).controlId);
        ((ActHomeKitBinding) this.mViewBinding).tvEnableMatter.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((ActMatterSubVM) ActHomeKit.this.mViewModel).isEnable) {
                    NavUtils.destination(ActMatterSub.class).withLong(Constants.PLACE_ID, ((ActMatterSubVM) ActHomeKit.this.mViewModel).device.getPlaceId()).withLong(Constants.CONTROL_ID, ((ActMatterSubVM) ActHomeKit.this.mViewModel).controlId).withInt(Constants.MAX, 80).withString(Constants.MODE_DATA, GsonUtils.toJson(((ActMatterSubVM) ActHomeKit.this.mViewModel).fabricData.getValue())).navigation(ActHomeKit.this.activity);
                } else {
                    ((ActMatterSubVM) ActHomeKit.this.mViewModel).showDialog(ActHomeKit.this.activity);
                }
            }
        });
        ((ActHomeKitBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                ((ActMatterSubVM) ActHomeKit.this.mViewModel).checkFabric();
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMatterSubVM) this.mViewModel).fabricShow.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvEnableMatter.setText(ActHomeKit.this.getString(R.string.play_manage));
                } else {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvEnableMatter.setText(ActHomeKit.this.getString(R.string.enable));
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).matterDevices.observe(this, new Observer<List<MatterDeviceResponse.MatterDevice>>() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MatterDeviceResponse.MatterDevice> matterDevices) {
                if (matterDevices.isEmpty()) {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvSyncNum.setText(String.format(ActHomeKit.this.getString(R.string.app_str_sonos_has_sync_devices_num), 0));
                } else {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvSyncNum.setText(String.format(ActHomeKit.this.getString(R.string.app_str_sonos_has_sync_devices_num), Integer.valueOf(matterDevices.size())));
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).matterScenes.observe(this, new Observer<List<MatterDeviceResponse.MatterDevice>>() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<MatterDeviceResponse.MatterDevice> matterScenes) {
                if (matterScenes.isEmpty()) {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvSyncSceneNum.setText(String.format(ActHomeKit.this.getString(R.string.app_str_has_sync_scenes_num), 0));
                } else {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvSyncSceneNum.setText(String.format(ActHomeKit.this.getString(R.string.app_str_has_sync_scenes_num), Integer.valueOf(matterScenes.size())));
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).empty.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvSyncNum.setText(ActHomeKit.this.getString(R.string.app_home_kit_matter_tip));
                    ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvSyncSceneNum.setText("");
                }
            }
        });
        ((ActMatterSubVM) this.mViewModel).offline.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKit.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).refreshLayout.finishRefresh();
                ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).refreshLayout.setEnableRefresh(!aBoolean.booleanValue());
                ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).viewOffline.setVisibility(aBoolean.booleanValue() ? 8 : 0);
                ((ActHomeKitBinding) ActHomeKit.this.mViewBinding).tvOffline.setVisibility(aBoolean.booleanValue() ? 8 : 0);
            }
        });
        ((ActMatterSubVM) this.mViewModel).loadDevice();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActMatterSubVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActMatterSubVM) this.mViewModel).controlId);
        if (((ActMatterSubVM) this.mViewModel).device != null) {
            setTitle(((ActMatterSubVM) this.mViewModel).device.getDeviceName());
        }
        ((ActMatterSubVM) this.mViewModel).checkFabric();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActMatterSubVM) this.mViewModel).device);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (5002 == resultCode || 5001 == resultCode) {
            finishActivity();
        }
    }
}