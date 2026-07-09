package com.ltech.smarthome.ui.scene.local;

import android.content.Intent;
import android.text.TextUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActSelectSuperPanel extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private int sceneType;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        this.listType = 4;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        setSortType(1);
        this.sceneType = getIntent().getIntExtra(Constants.LOCAL_SCENE_TYPE, -1);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        SceneHelper.instance().maskType = MaskType.LOCAL;
        SceneHelper.instance().controlObject = device;
        int i = this.sceneType;
        if (i == 10) {
            NavUtils.destination(ActSelectVoiceSpeak.class).withLong(Constants.CONTROL_ID, device.getId()).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
        } else if (i == 11) {
            NavUtils.destination(ActSelectSuperPanelMusic.class).withLong(Constants.CONTROL_ID, device.getId()).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
        } else if (i == 12) {
            NavUtils.destination(ActSelectSuperPanelSecurity.class).withLong(Constants.CONTROL_ID, device.getId()).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
            return this.sceneType == 11 && !TextUtils.isEmpty(device.getMcuversion());
        }
        if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
            if (this.sceneType == 12) {
                if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    return true;
                }
            } else {
                return !TextUtils.isEmpty(device.getMcuversion());
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            setResult(3001);
            finishActivity();
        }
    }
}