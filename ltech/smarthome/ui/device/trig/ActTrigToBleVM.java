package com.ltech.smarthome.ui.device.trig;

import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.TrigToBleExtParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.trig.ActTrigToBleVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrigToBleVM extends BaseViewModel {
    public List<TrigToBleExtParam.TrigRelateInfo> actionContent;
    public SingleLiveEvent<Device> controlDevice = new SingleLiveEvent<>();
    public long controlId;
    public long placeId;
    public int selectChannel;
    public int subType;

    public void setTrigType(final int outputType) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setTbType(ActivityUtils.getTopActivity(), outputType, new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBleVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrigToBleVM.this.lambda$setTrigType$0(outputType, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTrigType$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTbType(i));
        }
    }

    public void queryTrigState(Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelDeviceMotorState(ActivityUtils.getTopActivity());
    }

    /* renamed from: com.ltech.smarthome.ui.device.trig.ActTrigToBleVM$1, reason: invalid class name */
    class AnonymousClass1 implements IAction<ResponseMsg> {
        final /* synthetic */ int val$address;
        final /* synthetic */ String val$data;
        final /* synthetic */ int val$i;
        final /* synthetic */ boolean val$isCancel;
        final /* synthetic */ IAction val$result;
        final /* synthetic */ int val$zone;

        AnonymousClass1(final IAction val$result, final int val$i, final String val$data, final int val$address, final boolean val$isCancel, final int val$zone) {
            this.val$result = val$result;
            this.val$i = val$i;
            this.val$data = val$data;
            this.val$address = val$address;
            this.val$isCancel = val$isCancel;
            this.val$zone = val$zone;
        }

        @Override // com.ltech.smarthome.base.IAction
        public void act(final ResponseMsg responseMsg) {
            if (responseMsg != null) {
                final Device value = ActTrigToBleVM.this.controlDevice.getValue();
                if (value != null && ActTrigToBleVM.this.actionContent != null) {
                    TrigToBleExtParam trigToBleExtParam = new TrigToBleExtParam();
                    for (int i = 0; i < ActTrigToBleVM.this.actionContent.size(); i++) {
                        if (ActTrigToBleVM.this.actionContent.get(i).getActiontype() != 0 || TextUtils.isEmpty(ActTrigToBleVM.this.actionContent.get(i).getCustomerName())) {
                            trigToBleExtParam.setRelateInfo(i + 1, ActTrigToBleVM.this.actionContent.get(i));
                        }
                    }
                    final String relateParamMapString = trigToBleExtParam.getRelateParamMapString();
                    ObservableSubscribeProxy observableSubscribeProxy = (ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), relateParamMapString).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActTrigToBleVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)));
                    final IAction iAction = this.val$result;
                    final int i2 = this.val$i;
                    final String str = this.val$data;
                    final int i3 = this.val$address;
                    final boolean z = this.val$isCancel;
                    final int i4 = this.val$zone;
                    observableSubscribeProxy.subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBleVM$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActTrigToBleVM.AnonymousClass1.this.lambda$act$0(value, relateParamMapString, iAction, responseMsg, i2, str, i3, z, i4, obj);
                        }
                    }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigToBleVM.1.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Throwable throwable) throws Exception {
                            if (AnonymousClass1.this.val$result != null) {
                                AnonymousClass1.this.val$result.act(null);
                            }
                        }
                    });
                    return;
                }
                IAction iAction2 = this.val$result;
                if (iAction2 != null) {
                    iAction2.act(null);
                    return;
                }
                return;
            }
            IAction iAction3 = this.val$result;
            if (iAction3 != null) {
                iAction3.act(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$act$0(Device device, String str, IAction iAction, ResponseMsg responseMsg, int i, String str2, int i2, boolean z, int i3, Object obj) throws Exception {
            device.setExtParam(str);
            Injection.repo().device().saveDevice(device);
            if (iAction != null) {
                iAction.act(responseMsg);
            }
            ReplaceHelper.instance().backupIndexData(ActTrigToBleVM.this.getLifecycleOwner(), device.getDeviceId(), UpdateBackDataRequest.DRY_CONTACT_BLE_BIND, CmdAssistant.getDeviceAssistant(ActTrigToBleVM.this.controlDevice.getValue(), new int[0]).setTrigToBleData(i, str2, i2, z, i3), i);
        }
    }

    public void setTrigData(int i, String data, int address, boolean isCancel, int zone, IAction<ResponseMsg> result) {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setTrigToBleData(ActivityUtils.getTopActivity(), i, data, address, isCancel, zone, new AnonymousClass1(result, i, data, address, isCancel, zone));
    }

    public void cancelTrigData(int i, IAction<ResponseMsg> result) {
        setTrigData(i, "", 0, true, 0, result);
    }

    private String getLocationNameByDevice(long deviceId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceId);
        if (deviceByDeviceId == null) {
            return "";
        }
        return deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
    }

    private String getLocationNameByGroup(long groupId) {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupId);
        if (groupByGroupId == null) {
            return "";
        }
        return groupByGroupId.getFloorName() + groupByGroupId.getRoomName();
    }
}