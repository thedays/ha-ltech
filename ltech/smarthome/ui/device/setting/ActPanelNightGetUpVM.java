package com.ltech.smarthome.ui.device.setting;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SmartPanelNightUpState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActPanelNightGetUpVM extends BaseViewModel {
    public Bundle bundle;
    public long controlId;
    public Device device;
    private SmartPanelNightUpState state;
    public MutableLiveData<Boolean> switchOn = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> selectStarTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> selectEndTimeDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> selectSceneDialogEvent = new MutableLiveData<>();
    public MutableLiveData<String> starTimeText = new MutableLiveData<>("22:00");
    public MutableLiveData<String> endTimeText = new MutableLiveData<>("08:00");
    public MutableLiveData<String> excSceneText = new MutableLiveData<>(StringUtils.getString(R.string.no_bind_object));
    public MutableLiveData<String> restartSceneText = new MutableLiveData<>(StringUtils.getString(R.string.no_bind_object));
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUpVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActPanelNightGetUpVM.this.lambda$new$0((View) obj);
        }
    });

    static /* synthetic */ boolean lambda$setting$2(BaseDialog baseDialog, View view) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_end_time /* 2131297463 */:
                this.selectEndTimeDialogEvent.call();
                break;
            case R.id.layout_get_up_night_mode_scene /* 2131297483 */:
                this.selectSceneDialogEvent.setValue(0);
                break;
            case R.id.layout_reset_scene /* 2131297597 */:
                this.selectSceneDialogEvent.setValue(1);
                break;
            case R.id.layout_start_time /* 2131297657 */:
                this.selectStarTimeDialogEvent.call();
                break;
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    public void queryPanelState() {
        CmdAssistant.getQueryCmdAssistant(this.device, new int[0]).queryPanelUpNightState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUpVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelNightGetUpVM.this.lambda$queryPanelState$1((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelState$1(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) this.device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelNightUpState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, responseMsg.getResData()));
        } else {
            refreshPanelState(null);
        }
    }

    public void refreshPanelState(SmartPanelNightUpState smartPanelNightUpState) {
        if (smartPanelNightUpState == null) {
            this.state = new SmartPanelNightUpState();
        } else {
            this.state = smartPanelNightUpState;
        }
        this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.state.getStartH()), Integer.valueOf(this.state.getStartM())));
        this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.state.getEndH()), Integer.valueOf(this.state.getEndM())));
        this.switchOn.setValue(Boolean.valueOf(this.state.getState() == 1));
        Scene localSceneBySceneNum = Injection.repo().scene().getLocalSceneBySceneNum(this.state.getExcScene());
        Scene localSceneBySceneNum2 = Injection.repo().scene().getLocalSceneBySceneNum(this.state.getReStartScene());
        if (localSceneBySceneNum != null) {
            this.excSceneText.setValue(localSceneBySceneNum.getSceneName());
        }
        if (localSceneBySceneNum2 != null) {
            this.restartSceneText.setValue(localSceneBySceneNum2.getSceneName());
        }
    }

    public int getStarH() {
        SmartPanelNightUpState smartPanelNightUpState = this.state;
        if (smartPanelNightUpState != null) {
            return smartPanelNightUpState.getStartH();
        }
        return 0;
    }

    public int getStarM() {
        SmartPanelNightUpState smartPanelNightUpState = this.state;
        if (smartPanelNightUpState != null) {
            return smartPanelNightUpState.getStartM();
        }
        return 0;
    }

    public int getEndH() {
        SmartPanelNightUpState smartPanelNightUpState = this.state;
        if (smartPanelNightUpState != null) {
            return smartPanelNightUpState.getEndH();
        }
        return 0;
    }

    public int getEndM() {
        SmartPanelNightUpState smartPanelNightUpState = this.state;
        if (smartPanelNightUpState != null) {
            return smartPanelNightUpState.getEndM();
        }
        return 0;
    }

    public void setStarTime(int h, int m2) {
        SmartPanelNightUpState smartPanelNightUpState = this.state;
        if (smartPanelNightUpState != null) {
            int endH = ((smartPanelNightUpState.getEndH() * 60) + this.state.getEndM()) - ((h * 60) + m2);
            if (endH > 0 && endH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            }
            this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
            this.state.setStartH(h);
            this.state.setStartM(m2);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(getStarH()));
            arrayList.add(Integer.valueOf(getStarM()));
            arrayList.add(Integer.valueOf(getEndH()));
            arrayList.add(Integer.valueOf(getEndM()));
            setting(2, arrayList, null);
        }
    }

    public void setEndTime(int h, int m2) {
        SmartPanelNightUpState smartPanelNightUpState = this.state;
        if (smartPanelNightUpState != null) {
            int startH = ((h * 60) + m2) - ((smartPanelNightUpState.getStartH() * 60) + this.state.getStartM());
            if (startH > 0 && startH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            }
            this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
            this.state.setEndH(h);
            this.state.setEndM(m2);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(getStarH()));
            arrayList.add(Integer.valueOf(getStarM()));
            arrayList.add(Integer.valueOf(getEndH()));
            arrayList.add(Integer.valueOf(getEndM()));
            setting(2, arrayList, null);
        }
    }

    private void setting(int cmd, List<Integer> data, Bundle bundle) {
        setting(cmd, data, false, bundle);
    }

    private void setting(final int cmd, final List<Integer> data, final boolean isExc, final Bundle bundle) {
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).setSmartPanelNightUpMode(ActivityUtils.getTopActivity(), cmd, data, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUpVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelNightGetUpVM.this.lambda$setting$3(cmd, isExc, data, bundle, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setting$3(int i, boolean z, List list, Bundle bundle, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (i == 3) {
                if (z) {
                    ReplaceHelper.instance().backupData(getLifecycleOwner(), this.device.getDeviceId(), UpdateBackDataRequest.NIGHT_UP_MODE_EXC_SCENE, CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).setSmartPanelNightUpMode(i, list));
                } else {
                    ReplaceHelper.instance().backupData(getLifecycleOwner(), this.device.getDeviceId(), UpdateBackDataRequest.NIGHT_UP_MODE_RESET_SCENE, CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).setSmartPanelNightUpMode(i, list));
                }
            } else if (i == 2) {
                ReplaceHelper.instance().backupData(getLifecycleOwner(), this.device.getDeviceId(), UpdateBackDataRequest.NIGHT_UP_MODE, CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).setSmartPanelNightUpMode(i, list));
            } else {
                ReplaceHelper.instance().backupData(getLifecycleOwner(), this.device.getDeviceId(), UpdateBackDataRequest.NIGHT_UP_MODE_TIME, CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).setSmartPanelNightUpMode(i, list));
            }
            this.bundle = bundle;
            return;
        }
        if (responseMsg != null && responseMsg.getStateCode() == 3 && i == 3) {
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getContext().getString(R.string.app_str_operation_failure), getContext().getString(R.string.local_scene_error_03)).setOkButton(getContext().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUpVM$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActPanelNightGetUpVM.lambda$setting$2(baseDialog, view);
                }
            });
        }
    }

    public void setSwitchOn(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isOn", z);
        this.switchOn.setValue(Boolean.valueOf(z));
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(z ? 1 : 0));
        setting(1, arrayList, bundle);
    }

    public void bindScene(boolean isExc, int sceneNum) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isExc ? 1 : 2));
        arrayList.add(Integer.valueOf(sceneNum));
        setting(3, arrayList, isExc, null);
    }
}