package com.ltech.smarthome.ui.device.microwave_sensor;

import android.content.Intent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActWaveSensorSelectBleDeviceAndGroup extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        this.listType = 4;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        WaveSensorHelper.instance().relateObject = device;
        WaveSensorHelper.instance().goSelectAction(this, device);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void groupClick(Group group) {
        WaveSensorHelper.instance().relateObject = group;
        WaveSensorHelper.instance().goSelectAction(this, group);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        switch (ProductRepository.getLightColorType((Object) group)) {
            case 12:
            case 13:
            case 15:
            case 16:
            case 22:
            case 23:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
                return false;
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 26:
            default:
                return true;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "221042516351701":
            case "123072510445601":
            case "221030816330401":
            case "121042516340801":
            case "121042516345401":
                return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case "120033108251501":
            case "120033108255901":
            case "120033108263401":
            case "120033108265701":
            case "120033108272201":
            case "3895993722014848":
            case "122110809100701":
            case "4002207473371776":
            case "120122111301201":
            case "4002205681371776":
            case "4002206372514432":
            case "122112209430401":
            case "4002206816422528":
            case "121042516403901":
                return true;
            default:
                return false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            lambda$edit$2(this);
        }
    }
}