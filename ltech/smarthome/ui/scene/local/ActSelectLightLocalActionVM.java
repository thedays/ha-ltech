package com.ltech.smarthome.ui.scene.local;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.scene_param.SceneConstants;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.smart.product_agreement.param.LightCmdParam;

/* loaded from: classes4.dex */
public class ActSelectLightLocalActionVM extends BaseViewModel {
    public void selectClose(int... zoneNum) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(zoneNum.length > 0 ? zoneNum[0] : 1);
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(false);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "4");
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.close));
        SceneHelper.instance().cmdParam = lightCmdParam;
    }

    public void selectOn(int... zoneNum) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(zoneNum.length > 0 ? zoneNum[0] : 1);
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(true);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "7");
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.function_open));
        SceneHelper.instance().cmdParam = lightCmdParam;
    }

    public void selectCurrentState() {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(51);
        lightCmdParam.addExtParam(SceneHelper.OPTION, SceneConstants.OPTION_CURRENT_STATE);
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_current_state));
        SceneHelper.instance().cmdParam = lightCmdParam;
    }
}