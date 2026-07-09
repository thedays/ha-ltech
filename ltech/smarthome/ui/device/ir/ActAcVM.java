package com.ltech.smarthome.ui.device.ir;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.super_panel.SuperPanelVersionHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAcVM extends BaseIrVM {
    public static final int WIND_POS = 1;
    private String stateString;
    public MutableLiveData<ACStateV2> currentState = new MutableLiveData<>();
    public MutableLiveData<Boolean> powerOn = new MutableLiveData<>();
    public MutableLiveData<Boolean> tempControl = new MutableLiveData<>();
    public SingleLiveEvent<Void> reportState = new SingleLiveEvent<>();
    public BindingCommand<View> viewclick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActAcVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAcVM.this.lambda$new$0((View) obj);
        }
    });

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVM
    protected void addKeyItem(List<IrKeyItem> keyItemList) {
        super.addKeyItem(keyItemList);
        keyItemList.add(AcRepository.getAcModeInfo(this.mKKacZipManagerV2.getCurModelType()));
        keyItemList.add(AcRepository.getWindSpeedInfo(this.mKKacZipManagerV2.getCurWindSpeed()));
        keyItemList.add(AcRepository.getDirectTypeInfo(true));
        keyItemList.add(AcRepository.getDirectTypeInfo(false));
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVM
    protected void setKeyEnable(List<IrKeyItem> keyItemList) {
        if (1 == this.mKKacZipManagerV2.getPowerState()) {
            Iterator<IrKeyItem> it = keyItemList.iterator();
            while (it.hasNext()) {
                it.next().setEnable(false);
            }
        } else {
            Iterator<IrKeyItem> it2 = keyItemList.iterator();
            while (it2.hasNext()) {
                it2.next().setEnable(true);
            }
            keyItemList.get(1).setEnable(this.mKKacZipManagerV2.isWindSpeedCanControl());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_minus) {
            this.mKKacZipManagerV2.decreaseTmp();
            sendIrControl();
            refreshState();
        } else if (id == R.id.iv_plus) {
            this.mKKacZipManagerV2.increaseTmp();
            sendIrControl();
            refreshState();
        } else {
            if (id != R.id.view_on_off) {
                return;
            }
            this.mKKacZipManagerV2.changePowerState();
            sendIrControl();
            setSendState();
            refreshState();
        }
    }

    public void initAcState(String state) {
        if (TextUtils.isEmpty(state)) {
            this.mKKacZipManagerV2.setACStateV2FromString("");
        } else {
            this.mKKacZipManagerV2.setACStateV2FromString(state);
        }
        refreshState();
    }

    public void changeMode() {
        this.mKKacZipManagerV2.changeACModel();
        sendIrControl();
        refreshState();
    }

    public void changeWind() {
        this.mKKacZipManagerV2.changeWindSpeed();
        sendIrControl();
        refreshState();
    }

    public void changeDirect(boolean swing) {
        if (swing) {
            this.mKKacZipManagerV2.changeUDWindDirect(ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING);
        } else {
            this.mKKacZipManagerV2.changeUDWindDirect(ACStateV2.UDWindDirectKey.UDDIRECT_KEY_FIX);
        }
        sendIrControl();
        refreshState();
    }

    public void changeTemperature(int temp) {
        this.mKKacZipManagerV2.setTargetTemp(temp);
        sendIrControl();
        refreshState();
    }

    public List<Integer> getAcData() {
        ArrayList arrayList = new ArrayList();
        ACStateV2 aCStateV2 = (ACStateV2) GsonUtils.fromJson(this.mKKacZipManagerV2.getACStateV2InString(), ACStateV2.class);
        arrayList.add(Integer.valueOf(aCStateV2.getCurPowerState()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurTemp()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurModelType()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurWindSpeed()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurUDDirect()));
        return arrayList;
    }

    public void sendIrControl() {
        this.cmdName = ActivityUtils.getTopActivity().getString(R.string.ac_state);
        showLoadingDialog();
        if (useSuperPanelIrControl()) {
            CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).sendIrControlCmd(ActivityUtils.getTopActivity(), this.controlDevice.getValue().getDeviceId(), getAcData(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.ir.ActAcVM.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActAcVM.this.dismissLoadingDialog();
                    if (aBoolean.booleanValue()) {
                        ActAcVM.this.reportState.call();
                    } else {
                        ActAcVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                    }
                }
            });
        } else {
            this.mIrCmdParam = CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]).sendIrComboControl(ActivityUtils.getTopActivity(), this.mParams, this.mKKacZipManagerV2.getACKeyIr(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.ir.ActAcVM.2
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActAcVM.this.dismissLoadingDialog();
                    if (aBoolean.booleanValue()) {
                        if (ActAcVM.this.selectAction) {
                            return;
                        }
                        ActAcVM.this.reportState.call();
                        return;
                    }
                    ActAcVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                }
            }, !this.selectAction);
        }
    }

    private boolean useSuperPanelIrControl() {
        if (!this.selectAction) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.controlDevice.getValue().getMacdeviceid());
            if (this.controlDevice.getValue().getDeviceId() > 0 && deviceByDeviceId != null && SuperPanelVersionHelper.supportIrControlCmd(deviceByDeviceId)) {
                return true;
            }
        }
        return false;
    }

    public String getStateString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AcRepository.getModeTypeString(this.mKKacZipManagerV2.getCurModelType()));
        sb.append(" | ");
        if (this.mKKacZipManagerV2.isWindSpeedCanControl()) {
            sb.append(AcRepository.getWindSpeedString(this.mKKacZipManagerV2.getCurWindSpeed()));
            sb.append(" | ");
        }
        sb.append(AcRepository.getDirectTypeString(this.currentState.getValue().getCurUDDirect()));
        return sb.toString();
    }

    private void refreshState() {
        this.currentState.setValue((ACStateV2) GsonUtils.fromJson(this.mKKacZipManagerV2.getACStateV2InString(), ACStateV2.class));
    }

    @Override // com.ltech.smarthome.base.BaseViewModel, androidx.lifecycle.DefaultLifecycleObserver
    public void onResume(LifecycleOwner owner) {
        if (this.mKKacZipManagerV2 != null) {
            this.mKKacZipManagerV2.onResume();
        }
    }

    @Override // com.ltech.smarthome.base.BaseViewModel, androidx.lifecycle.DefaultLifecycleObserver
    public void onPause(LifecycleOwner owner) {
        if (this.mKKacZipManagerV2 != null) {
            this.stateString = this.mKKacZipManagerV2.getACStateV2InString();
            this.mKKacZipManagerV2.onPause();
        }
    }

    @Override // com.ltech.smarthome.base.BaseViewModel, androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner owner) {
        saveAcState();
    }

    public void saveAcState() {
        if (TextUtils.isEmpty(this.stateString) || this.controlDevice.getValue() == null || this.controlDevice.getValue().getId() <= 0) {
            return;
        }
        this.controlDevice.getValue().getDeviceState().setAcState(this.stateString);
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
    }
}