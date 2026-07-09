package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectDaliScene;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActSelectCgdPro extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
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
        setSortType(1);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        int intExtra = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            NavUtils.destination(ActSmartPanelSelectDaliScene.class).withLong("device_id", device.getDeviceId()).withLong(Constants.RELATE_ID, device.getId()).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, intExtra).withBoolean(Constants.IS_GQ, getIntent().getBooleanExtra(Constants.IS_GQ, false)).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
        } else if (intExtra == -1) {
            NavUtils.destination(ActSelectDaliScene.class).withLong("device_id", device.getDeviceId()).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
        } else {
            NavUtils.destination(ActSmartPanelSelectDaliScene.class).withLong("device_id", device.getDeviceId()).withLong(Constants.RELATE_ID, device.getId()).withBoolean(Constants.IS_NIGHT_UP, getIntent().getBooleanExtra(Constants.IS_NIGHT_UP, false)).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, intExtra).withInt(Constants.CLICK_TYPE, getIntent().getIntExtra(Constants.CLICK_TYPE, 1)).withBoolean(Constants.BATCH_SET_SCENE, getIntent().getBooleanExtra(Constants.BATCH_SET_SCENE, false)).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return device.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            setResult(3001, data);
            finishActivity();
        }
    }
}