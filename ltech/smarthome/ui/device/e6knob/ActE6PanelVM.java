package com.ltech.smarthome.ui.device.e6knob;

import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.E6ExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.light.ActColorLight;
import com.ltech.smarthome.ui.device.light.ActCtLight;
import com.ltech.smarthome.ui.device.light.ActDimLight;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActE6PanelVM extends BaseViewModel {
    public long controlId;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public E6ExtParam extParam = new E6ExtParam();
    public String productId;

    public DeviceAssistant getCmdHelper(int position) {
        return CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), 1 << position);
    }

    public void goControl(int lightType) {
        NavUtils.Builder destination;
        if (lightType == 1) {
            destination = NavUtils.destination(ActDimLight.class);
        } else if (lightType == 3 || lightType == 4 || lightType == 5) {
            destination = NavUtils.destination(ActColorLight.class);
        } else {
            destination = NavUtils.destination(ActCtLight.class);
        }
        navigation(destination.withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.LIGHT_TYPE, lightType).withBoolean(Constants.IS_E6, true).withBoolean(Constants.GROUP_CONTROL, false));
    }

    public void convertParams(String resData) {
        String substring = resData.substring(16);
        if (ProductId.ID_KNOB_PANEL_E6D.equals(this.productId) || ProductId.ID_KNOB_PANEL_E6M.equals(this.productId)) {
            E6ExtParam.E6Action e6Action = new E6ExtParam.E6Action();
            e6Action.setObjectInstruct(substring.substring(0, 6));
            if (!e6Action.getObjectInstruct().equals(this.extParam.getAction(0).getObjectInstruct())) {
                ReplaceHelper.instance().addBackupIndexData(UpdateBackDataRequest.CONTROL_OBJECT, getCmdHelper(0).setE6Object(e6Action.getObjectInstruct()), 0);
            }
            this.extParam.setAction(0, e6Action);
            substring = substring.substring(6);
        }
        for (int i = 1; i <= 4; i++) {
            if (substring.length() >= i * 22) {
                E6ExtParam.E6Action e6Action2 = new E6ExtParam.E6Action();
                int i2 = (i - 1) * 22;
                int i3 = i2 + 6;
                e6Action2.setObjectInstruct(substring.substring(i2, i3));
                e6Action2.setActionInstruct(substring.substring(i3, i2 + 22));
                if ((ProductId.ID_KNOB_PANEL_E6D.equals(this.productId) || ProductId.ID_KNOB_PANEL_E6M.equals(this.productId)) && !e6Action2.getObjectInstruct().equals(this.extParam.getAction(i).getObjectInstruct())) {
                    ReplaceHelper.instance().addBackupIndexData(UpdateBackDataRequest.CONTROL_OBJECT, getCmdHelper(i).setE6Object(e6Action2.getObjectInstruct()), i);
                }
                if (!e6Action2.getActionInstruct().equals(this.extParam.getAction(i).getActionInstruct())) {
                    ReplaceHelper.instance().addBackupIndexData(UpdateBackDataRequest.CONTROL_ACTION, getCmdHelper(i).setE6Action(e6Action2.getActionInstruct()), i);
                }
                this.extParam.setAction(i, e6Action2);
            }
        }
        if (!ReplaceHelper.instance().dataEmpty()) {
            uploadData();
        } else {
            this.controlObject.getValue().setExtParam(this.extParam.getParamString());
            Injection.repo().device().saveDevice(this.controlObject.getValue());
        }
        ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlObject.getValue().getDeviceId());
    }

    public void executeCurScene(final Context context, int position) {
        showLoadingDialog();
        CmdAssistant.getSceneCmdAssistant(this.controlObject.getValue(), new int[0]).executeEurCurrentScene(context, position + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelVM.this.lambda$executeCurScene$0(context, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$executeCurScene$0(Context context, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                showSuccessTipDialog(context.getString(R.string.execute_success));
                return;
            } else {
                SmartToast.showCenterShort(context.getString(R.string.execute_fail));
                return;
            }
        }
        SmartToast.showCenterShort(context.getString(R.string.execute_fail));
    }

    public void uploadData() {
        final Device value = this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), this.extParam.getParamString()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActE6PanelVM.this.lambda$uploadData$1(value, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActE6PanelVM.this.lambda$uploadData$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$1(Device device, Object obj) throws Exception {
        device.setExtParam(this.extParam.getParamString());
        Injection.repo().device().saveDevice(device);
        this.controlObject.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$2(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }
}