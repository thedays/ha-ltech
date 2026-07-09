package com.ltech.smarthome.ui.device.mesh_gateway;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMeshGatewayLightSettingVM extends BaseViewModel {
    public static final int RGBBRT = 1;
    public static final int RGBCOLOR = 0;
    public int colorType;
    public long controlId;
    public boolean groupControl;
    public int zoneNum;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MutableLiveData<Boolean> stateOn = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActMeshGatewayLightSettingVM.this.lambda$new$0((View) obj);
        }
    });

    public GatewayAssistant getGatewayCmdHelper() {
        return CmdAssistant.getGatewayAssistant(this.controlObject.getValue(), this.zoneNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_open) {
            return;
        }
        getGatewayCmdHelper().sendAmbientLightOnOff(ActivityUtils.getTopActivity(), true, new IAction[0]);
        this.stateOn.setValue(true);
    }

    public List<Integer> getColorList(Context context) {
        return Ints.asList(context.getResources().getIntArray(R.array.static_default_color));
    }

    public byte[] createSendData(int cmdType, int value) {
        if (cmdType == 0) {
            return new byte[]{-45, (byte) (Color.red(value) & 255), (byte) (Color.green(value) & 255), (byte) (Color.blue(value) & 255)};
        }
        if (cmdType == 1) {
            return new byte[]{-44, (byte) (LightUtils.progress2BrtHasBelowOne(value) & 255)};
        }
        return new byte[4];
    }

    public boolean getBoolean(String s) {
        return !s.equals("00") && s.equals("01");
    }
}