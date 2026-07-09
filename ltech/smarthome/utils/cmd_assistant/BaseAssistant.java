package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.EurPanelGroupParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.DataPackage;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.IReceiveListener;
import com.smart.product_agreement.extra.CmdConstants;
import com.smart.product_agreement.param.SceneCmdParam;
import com.smart.product_agreement.param.SuperPanelCmdParam;

/* loaded from: classes4.dex */
public class BaseAssistant {
    protected Object controlObject;
    protected int zoneNum;

    public void setControlObject(Object controlObject) {
        this.controlObject = controlObject;
    }

    public void setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
    }

    public void setSingleAddress(BaseCmdParam cmdParam) {
        BleParam bleParam;
        Object obj = this.controlObject;
        if (!(obj instanceof Device) || (bleParam = (BleParam) ((Device) obj).getParam(BleParam.class)) == null) {
            return;
        }
        cmdParam.addExtParam(CmdConstants.DST_ADDRESS, Integer.valueOf(bleParam.getUnicastAddress()));
    }

    public int getPublishAddress() {
        Object obj = this.controlObject;
        if (obj instanceof Device) {
            return ((BleParam) ((Device) obj).getParam(BleParam.class)).getPublicationAddress();
        }
        if (obj instanceof Group) {
            return ((EurPanelGroupParam) ((Group) obj).getParam(EurPanelGroupParam.class)).getPublicationAddress();
        }
        return 0;
    }

    protected int sendWithResult(Context context, BaseCtrlPackage ctrlPacket, BaseCmdParam cmdParam, final IAction<ResponseMsg> result) {
        if (isVirtual() && canFilterVirtual(cmdParam)) {
            ResponseMsg responseMsg = new ResponseMsg();
            responseMsg.setStateCode(0);
            responseMsg.setResData("");
            result.act(responseMsg);
            return 0;
        }
        return Injection.message().create(context).cmdParam(cmdParam).control(ctrlPacket).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.1
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    protected int sendWithResult(Context context, BaseCmd cmd, final IAction<ResponseMsg> result) {
        return Injection.message().create(context).cmd(cmd).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.2
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    protected int sendWithResult(Context context, BaseCmdParam cmdParam, IAction<ResponseMsg> result) {
        return sendWithResult(context, cmdParam, result, false);
    }

    protected int sendWithResult(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg> result, boolean filterVirtual) {
        if (isVirtual() && (filterVirtual || canFilterVirtual(cmdParam))) {
            ResponseMsg responseMsg = new ResponseMsg();
            responseMsg.setStateCode(0);
            result.act(responseMsg);
            return 0;
        }
        return Injection.message().create(context).cmdParam(cmdParam).resendTimes(2).timeOutTime(cmdParam instanceof SceneCmdParam ? 5000 : 4000).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.3
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    protected int sendWithResultWithTimeout(Context context, BaseCmdParam cmdParam, IAction<ResponseMsg> result, int timeOutTime) {
        return sendWithResultWithTimeout(context, cmdParam, result, false, timeOutTime);
    }

    protected int sendWithResultWithTimeout(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg> result, boolean filterVirtual, int timeOutTime) {
        if (isVirtual() && filterVirtual) {
            ResponseMsg responseMsg = new ResponseMsg();
            responseMsg.setStateCode(0);
            result.act(responseMsg);
            return 0;
        }
        return Injection.message().create(context).cmdParam(cmdParam).resendTimes(2).timeOutTime(timeOutTime).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.4
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    protected int sendWithResult15SecondTimeout(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg> result) {
        return Injection.message().create(context).cmdParam(cmdParam).resendTimes(2).timeOutTime(15000).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.5
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    protected int sendWithResultByPublish(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg> result) {
        DataPackage.DataPacketBuilder receiveListener = Injection.message().create(context).cmdParam(cmdParam).resendTimes(2).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.6
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        });
        if (ProductRepository.needPublishAddress(this.controlObject)) {
            CtrlPackage ctrlPackage = new CtrlPackage(2);
            ctrlPackage.setAddress(getPublishAddress());
            receiveListener.control((BaseCtrlPackage) ctrlPackage);
        } else {
            receiveListener.control(this.controlObject);
        }
        return receiveListener.enqueue();
    }

    protected int sendWithResultInterval(Context context, BaseCmdParam cmdParam, int resendTimes, final IAction<ResponseMsg> result) {
        return Injection.message().create(context).cmdParam(cmdParam).resendTimes(resendTimes).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.7
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    protected int sendWithoutResult(Context context, BaseCmdParam cmdParam) {
        return Injection.message().create(context).cmdParam(cmdParam).sendTimes(1).control(this.controlObject).enqueue();
    }

    protected int sendWithoutResult(Context context, BaseCmdParam cmdParam, int sendTime) {
        return Injection.message().create(context).cmdParam(cmdParam).sendTimes(sendTime).control(this.controlObject).enqueue();
    }

    protected int sendWithoutResult(Context context, BaseCtrlPackage ctrlPacket, BaseCmdParam cmdParam) {
        return Injection.message().create(context).cmdParam(cmdParam).sendTimes(1).control(ctrlPacket).enqueue();
    }

    protected int sendWithoutResult(Context context, BaseCtrlPackage ctrlPacket, BaseCmdParam cmdParam, int sendTime) {
        return Injection.message().create(context).cmdParam(cmdParam).sendTimes(sendTime).control(ctrlPacket).enqueue();
    }

    protected int sendWithoutResultInternal(Context context, BaseCmdParam cmdParam, boolean finish) {
        DataPackage.DataPacketBuilder control = Injection.message().create(context).cmdParam(cmdParam).control(this.controlObject);
        if (finish) {
            return control.sendTimes(2).enqueue();
        }
        return control.sendTimes(1).filterEnqueue(150);
    }

    protected int sendWithOnOffResult(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg> result) {
        return Injection.message().create(context).cmdParam(cmdParam).control(this.controlObject).intervalTime(0).resendTimes(0).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant.8
            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }
        }).enqueue();
    }

    protected int sendOnOffWithoutResult(Context context, BaseCmdParam cmdParam, int... sendTime) {
        return Injection.message().create(context).cmdParam(cmdParam).control(this.controlObject).intervalTime(100).sendTimes(sendTime.length > 0 ? sendTime[0] : 5).enqueue();
    }

    protected int sendWithoutResultByPublish(Context context, BaseCmdParam cmdParam, int sendTime, int... intervalTime) {
        DataPackage.DataPacketBuilder sendTimes = Injection.message().create(context).cmdParam(cmdParam).sendTimes(sendTime);
        if (intervalTime.length > 0) {
            sendTimes.intervalTime(intervalTime[0]);
        }
        if (ProductRepository.needPublishAddress(this.controlObject)) {
            CtrlPackage ctrlPackage = new CtrlPackage(2);
            ctrlPackage.setAddress(getPublishAddress());
            sendTimes.control((BaseCtrlPackage) ctrlPackage);
        } else {
            sendTimes.control(this.controlObject);
        }
        return sendTimes.enqueue();
    }

    @SafeVarargs
    protected final int switchOnOff(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        return sendWithOnOffResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$switchOnOff$0(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$switchOnOff$0(IAction[] iActionArr, ResponseMsg responseMsg) {
        if (iActionArr.length > 0) {
            IAction iAction = iActionArr[0];
            if (iAction != null) {
                iAction.act(Boolean.valueOf(responseMsg != null));
            }
        }
    }

    @SafeVarargs
    protected final int playDefaultShowTip(Context context, BaseCmdParam cmdParam, final int position, final IAction<Boolean>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.executing));
        return sendWithResultByPublish(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$playDefaultShowTip$1(position, actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$playDefaultShowTip$1(int i, IAction[] iActionArr, ResponseMsg responseMsg) {
        IAction iAction;
        if (responseMsg == null) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.execute_fail));
        } else if (i == 0) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), R.string.execute_success, R.string.wake_up_mode_execute_success, R.string.ok);
        } else if (i == 1) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), R.string.execute_success, R.string.sun_set_mode_execute_success, R.string.ok);
        }
        if (iActionArr.length <= 0 || (iAction = iActionArr[0]) == null) {
            return;
        }
        iAction.act(Boolean.valueOf(responseMsg != null));
    }

    @SafeVarargs
    protected final int play(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.executing));
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$play$2(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$play$2(IAction[] iActionArr, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.execute_success));
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.execute_fail));
        }
        if (iActionArr.length > 0) {
            IAction iAction = iActionArr[0];
            if (iAction != null) {
                iAction.act(Boolean.valueOf(responseMsg != null));
            }
        }
    }

    @SafeVarargs
    protected final int playMode(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.executing));
        return sendWithResultByPublish(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$playMode$3(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$playMode$3(IAction[] iActionArr, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.execute_success));
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.execute_fail));
        }
        if (iActionArr.length > 0) {
            IAction iAction = iActionArr[0];
            if (iAction != null) {
                iAction.act(Boolean.valueOf(responseMsg != null));
            }
        }
    }

    @SafeVarargs
    protected final int save(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        IAction<Boolean> iAction;
        if (isVirtual()) {
            if (actions.length > 0 && (iAction = actions[0]) != null) {
                iAction.act(true);
            }
            return 0;
        }
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$save$4(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$save$4(IAction[] iActionArr, ResponseMsg responseMsg) {
        IAction iAction;
        if (responseMsg != null) {
            if (responseMsg.getStateCode() != 0) {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            } else {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
            }
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
        }
        boolean z = responseMsg != null && responseMsg.getStateCode() == 0;
        if (iActionArr.length <= 0 || (iAction = iActionArr[0]) == null) {
            return;
        }
        iAction.act(Boolean.valueOf(z));
    }

    @SafeVarargs
    protected final int saveWithResponse(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg>... actions) {
        if (isVirtual()) {
            if (actions.length > 0 && actions[0] != null) {
                ResponseMsg responseMsg = new ResponseMsg();
                responseMsg.setStateCode(0);
                actions[0].act(responseMsg);
            }
            return 0;
        }
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$saveWithResponse$5(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$saveWithResponse$5(IAction[] iActionArr, ResponseMsg responseMsg) {
        IAction iAction;
        if (responseMsg != null) {
            if (responseMsg.getStateCode() != 0) {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            } else {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
            }
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
        }
        if (iActionArr.length <= 0 || (iAction = iActionArr[0]) == null) {
            return;
        }
        iAction.act(responseMsg);
    }

    @SafeVarargs
    protected final int saveWithoutDialog(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$saveWithoutDialog$6(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$saveWithoutDialog$6(IAction[] iActionArr, ResponseMsg responseMsg) {
        IAction iAction;
        boolean z = responseMsg != null && responseMsg.getStateCode() == 0;
        if (iActionArr.length <= 0 || (iAction = iActionArr[0]) == null) {
            return;
        }
        iAction.act(Boolean.valueOf(z));
    }

    @SafeVarargs
    protected final int save(Context context, BaseCtrlPackage ctrlPacket, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        return sendWithResult(context, ctrlPacket, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$save$7(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$save$7(IAction[] iActionArr, ResponseMsg responseMsg) {
        IAction iAction;
        if (responseMsg != null) {
            responseMsg.getStateCode();
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
        }
        boolean z = responseMsg != null && responseMsg.getStateCode() == 0;
        if (iActionArr.length <= 0 || (iAction = iActionArr[0]) == null) {
            return;
        }
        iAction.act(Boolean.valueOf(z));
    }

    @SafeVarargs
    protected final int sendMode(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$sendMode$8(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$sendMode$8(IAction[] iActionArr, ResponseMsg responseMsg) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
        if (responseMsg != null) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_success));
        } else {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_fail));
        }
        if (iActionArr == null || iActionArr.length <= 0) {
            return;
        }
        IAction iAction = iActionArr[0];
        if (iAction != null) {
            iAction.act(Boolean.valueOf(responseMsg != null));
        }
    }

    @SafeVarargs
    protected final int sendModeWithWaitDialog(Context context, BaseCmdParam cmdParam, final IAction<Boolean>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device));
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$sendModeWithWaitDialog$9(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$sendModeWithWaitDialog$9(IAction[] iActionArr, ResponseMsg responseMsg) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
        if (iActionArr == null || iActionArr.length <= 0) {
            return;
        }
        IAction iAction = iActionArr[0];
        if (iAction != null) {
            iAction.act(Boolean.valueOf(responseMsg != null));
        }
    }

    @SafeVarargs
    protected final int sendWithWaitDialog(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg>... actions) {
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device));
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$sendWithWaitDialog$10(actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$sendWithWaitDialog$10(IAction[] iActionArr, ResponseMsg responseMsg) {
        IAction iAction;
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
        }
        if (iActionArr == null || iActionArr.length <= 0 || (iAction = iActionArr[0]) == null) {
            return;
        }
        iAction.act(responseMsg);
    }

    @SafeVarargs
    protected final int sendModeInterval(Context context, BaseCmdParam cmdParam, final boolean isLast, final IAction<Boolean>... actions) {
        return sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$sendModeInterval$11(isLast, actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$sendModeInterval$11(boolean z, IAction[] iActionArr, ResponseMsg responseMsg) {
        if (z) {
            if (responseMsg != null) {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_success));
            } else {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_fail));
            }
        }
        if (iActionArr.length > 0) {
            IAction iAction = iActionArr[0];
            if (iAction != null) {
                iAction.act(Boolean.valueOf(responseMsg != null));
            }
        }
    }

    @SafeVarargs
    public final int sendEngineerMode(Context context, BaseCmdParam cmdParam, final boolean isLast, final IAction<Boolean>... actions) {
        return sendWithResultInterval(context, cmdParam, 1, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.BaseAssistant$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseAssistant.lambda$sendEngineerMode$12(isLast, actions, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$sendEngineerMode$12(boolean z, IAction[] iActionArr, ResponseMsg responseMsg) {
        if (z) {
            if (responseMsg != null && responseMsg.getStateCode() == 0) {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_success));
            } else {
                ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.send_to_device_fail));
            }
        }
        if (iActionArr.length > 0) {
            boolean z2 = false;
            IAction iAction = iActionArr[0];
            if (iAction != null) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    z2 = true;
                }
                iAction.act(Boolean.valueOf(z2));
            }
        }
    }

    private boolean canFilterVirtual(BaseCmdParam cmdParam) {
        return ((cmdParam instanceof SuperPanelCmdParam) || Injection.strategy().getCmdConvertStrategy(2).convert2cmd(cmdParam).getFunCode() == 196) ? false : true;
    }

    private boolean isVirtual() {
        Object obj = this.controlObject;
        if ((obj instanceof Device) && ((Device) obj).isVirtual()) {
            return true;
        }
        Object obj2 = this.controlObject;
        return (obj2 instanceof Group) && ((Group) obj2).isVirtual();
    }
}