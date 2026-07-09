package com.ltech.smarthome.ui.circadianlighting;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActLightPlanPreview extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        this.listType = 4;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        setSortType(1);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        showLoadingDialog(getString(R.string.app_str_process));
        try {
            JSONObject jSONObject = new JSONObject(getIntent().getStringExtra(Constants.CIRCADIAN_LIGHTING_DATA));
            CmdAssistant.getLightRhythmsCmdAssistant(device, new int[0]).previewRhythmsPlanInfo(this.activity, jSONObject.optInt("mode"), false, jSONObject.optInt("week"), jSONObject.optString("planData"), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanPreview.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ActLightPlanPreview.this.finishActivity();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "120033108251501":
            case "120033108255901":
            case "120033108272201":
                int lightColorType = ProductRepository.getLightColorType((Object) device);
                return (lightColorType == 2 && device.getMaxkelvin() > 0) || lightColorType == 20;
            default:
                return false;
        }
    }
}