package com.ltech.smarthome.ui.device.kbs;

import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;

/* loaded from: classes4.dex */
public class ActKbsVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    private SwitchPanelState panelState;
    public long placeId;
    public int zoneNum;
    public SingleLiveEvent<Object> controlObject = new SingleLiveEvent<>();
    public MutableLiveData<SwitchPanelState> switchChange = new SingleLiveEvent();

    static /* synthetic */ void lambda$queryPanelState$0(ResponseMsg responseMsg) {
    }

    public void sendSingleOnOff(int position, boolean on) {
        CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), on);
    }

    public void queryPanelState(Object object, int type, final int zoneCount) {
        LHomeLog.i(getClass(), "message_send queryPanelState enter");
        if (this.groupControl) {
            if (object instanceof Group) {
                Group group = (Group) object;
                if (group.getDeviceIds().isEmpty()) {
                    return;
                }
                CmdAssistant.getQueryCmdAssistant(Integer.valueOf(group.getFirstDevUniAddr()), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsVM$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActKbsVM.lambda$queryPanelState$0((ResponseMsg) obj);
                    }
                }, type);
                return;
            }
            return;
        }
        if (object instanceof Device) {
            final Device device = (Device) object;
            CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsVM$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKbsVM.this.lambda$queryPanelState$1(device, zoneCount, (ResponseMsg) obj);
                }
            }, type);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelState$1(Device device, int i, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()));
        } else {
            refreshPanelState(this.panelState);
        }
    }

    public void refreshPanelState(SwitchPanelState panelState) {
        if (panelState != null) {
            this.panelState = panelState;
            this.switchChange.setValue(panelState);
        }
    }
}