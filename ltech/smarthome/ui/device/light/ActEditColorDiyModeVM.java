package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.GeneralModeInfo;
import com.smart.product_agreement.parser.IModeParser;

/* loaded from: classes4.dex */
public class ActEditColorDiyModeVM extends BaseViewModel {
    public Device controlDevice;
    public long controlId;
    private GeneralModeInfo generalModeInfo;
    public int modePosition;
    public MutableLiveData<GeneralModeInfo> paramLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> previewLiveData = new MutableLiveData<>();
    public SingleLiveEvent<Void> editNameEvent = new SingleLiveEvent<>();
    private int[] modeTypeArray = {2, 1, 3, 4, 5};
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyModeVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActEditColorDiyModeVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_mode_name) {
            this.editNameEvent.call();
        } else {
            if (id != R.id.v_preview) {
                return;
            }
            MutableLiveData<Boolean> mutableLiveData = this.previewLiveData;
            mutableLiveData.setValue(Boolean.valueOf(mutableLiveData.getValue() == null || !this.previewLiveData.getValue().booleanValue()));
        }
    }

    public void setModeType(int position) {
        getModeInfo().setModeType(this.modeTypeArray[position]);
        this.paramLiveData.setValue(getModeInfo());
    }

    public int getModeTypePosition(int modeType) {
        int length = this.modeTypeArray.length;
        for (int i = 0; i < length; i++) {
            if (modeType == this.modeTypeArray[i]) {
                return i;
            }
        }
        return 0;
    }

    public ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlDevice, new int[0]);
    }

    public void queryInfo(final Context context, final int position) {
        showLoading();
        getModeCmdHelper().queryGeneralModeInfo(context, position, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyModeVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditColorDiyModeVM.this.lambda$queryInfo$1(context, position, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryInfo$1(Context context, int i, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            showError();
            return;
        }
        showContent();
        GeneralModeInfo parseGeneralModeInfo = ((IModeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parseGeneralModeInfo(responseMsg.getResData());
        parseGeneralModeInfo.setModeName(LightModeManager.getGeneralModeDefaultName(context)[i]);
        setModeInfo(parseGeneralModeInfo);
    }

    public void setModeInfo(GeneralModeInfo modeInfo) {
        this.generalModeInfo = modeInfo;
        this.paramLiveData.setValue(modeInfo);
    }

    public GeneralModeInfo getModeInfo() {
        return this.generalModeInfo;
    }

    public void saveData(final Context context) {
        CmdAssistant.getModeCmdAssistant(this.controlDevice, new int[0]).saveGeneralModeInfo(context, this.modePosition, getModeInfo(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyModeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditColorDiyModeVM.this.lambda$saveData$2(context, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$2(Context context, Boolean bool) {
        if (bool.booleanValue()) {
            LightModeManager.getColorDiyModeList(context, this.controlDevice.getDeviceId());
            finishActivity(3005, null);
        }
    }

    public void previewMode(Context context, boolean play) {
        if (play) {
            CmdAssistant.getModeCmdAssistant(this.controlDevice, new int[0]).previewGeneralModeInfo(context, this.modePosition, getModeInfo());
        } else {
            CmdAssistant.getModeCmdAssistant(this.controlDevice, new int[0]).pauseGeneralModeInfo(context, this.modePosition, getModeInfo());
        }
    }

    public void sendColor(Context context, int red, int green, int blue, boolean finish) {
        CmdAssistant.getLightCmdAssistant(this.controlDevice, new int[0]).sendRgb(context, Color.rgb(red, green, blue), finish);
    }
}