package com.ltech.smarthome.ui.device.aspanel;

import android.content.Context;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.PanelState;
import com.smart.product_agreement.parser.IPanelParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAsPanelVM extends BaseViewModel {
    public long controlId;
    public int lightType;
    public PanelState panelState;
    public PanelState.PanelZoneState panelZoneState;
    public long placeId;
    public RelateInfoAssistant relateInfoAssistant;
    public List<AsPanelRelateInfoItem> relatedInfoList;
    public Listing<Group> request;
    public int zoneNum;
    public SingleLiveEvent<Device> controlObject = new SingleLiveEvent<>();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public SingleLiveEvent<Void> showRgbBrtDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showWDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<PanelState.PanelZoneState> panelZoneStateLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> stateOn = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAsPanelVM.this.lambda$new$2((View) obj);
        }
    });

    public static class AsPanelRelateInfoItem {
        public String infoName;
        public RelatedInfoExtParam.RelateInfo relateInfo;

        public boolean isSelect(int zoneNum, int pos) {
            int i = 1 << pos;
            return (zoneNum & i) == i;
        }
    }

    public void initRelateInfoList(Device device) {
        this.relateInfoAssistant = new RelateInfoAssistant(device);
        int i = 0;
        if (this.relatedInfoList == null) {
            this.relatedInfoList = new ArrayList();
            int zoneCount = ProductRepository.getZoneCount(device.getProductId());
            while (i < zoneCount) {
                AsPanelRelateInfoItem asPanelRelateInfoItem = new AsPanelRelateInfoItem();
                asPanelRelateInfoItem.relateInfo = this.relateInfoAssistant.getRelateInfo(i);
                asPanelRelateInfoItem.infoName = getRelateInfoString(asPanelRelateInfoItem.relateInfo);
                this.relatedInfoList.add(asPanelRelateInfoItem);
                i++;
            }
            return;
        }
        int zoneCount2 = ProductRepository.getZoneCount(device.getProductId());
        while (i < zoneCount2) {
            this.relatedInfoList.get(i).relateInfo = this.relateInfoAssistant.getRelateInfo(i);
            this.relatedInfoList.get(i).infoName = getRelateInfoString(this.relateInfoAssistant.getRelateInfo(i));
            i++;
        }
    }

    private String getRelateInfoString(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return null;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        return device.getDeviceName();
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo() && this.groupList.getValue() != null) {
            for (Group group : this.groupList.getValue()) {
                if (group.getGroupId() == relateInfo.objectId) {
                    return group.getGroupName();
                }
            }
        }
        return null;
    }

    public void initPanelState(Device device) {
        if (this.panelState == null) {
            PanelState panelState = new PanelState();
            this.panelState = panelState;
            panelState.setZoneCount(ProductRepository.getZoneCount(device.getProductId()));
            ArrayList arrayList = new ArrayList();
            int zoneCount = this.panelState.getZoneCount();
            int i = 0;
            for (int i2 = 0; i2 < zoneCount; i2++) {
                arrayList.add(new PanelState.PanelZoneState());
                i |= 1 << i2;
            }
            this.panelState.setZoneNum(i);
            this.panelState.setAllOnOff(true);
            this.panelState.setPanelZoneStateList(arrayList);
            this.zoneNum = this.panelState.getZoneNum();
        }
    }

    public void refreshPanelState(PanelState panelState) {
        if (this.panelZoneState == null) {
            this.panelZoneState = new PanelState.PanelZoneState();
        }
        if (panelState != null) {
            this.stateOn.setValue(Boolean.valueOf(panelState.isAllOnOff()));
            if (this.controlObject.getValue() != null) {
                this.controlObject.getValue().getDeviceState().setOn(panelState.isAllOnOff());
            }
            this.panelState = panelState;
            this.zoneNum = panelState.getZoneNum();
            refreshPanelZoneState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        switch (view.getId()) {
            case R.id.iv_mode /* 2131297137 */:
                navigation(NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withInt(Constants.ZONE_NUM, this.zoneNum).withLong(Constants.CONTROL_ID, this.controlId));
                break;
            case R.id.iv_on_off /* 2131297163 */:
                CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), 65535).sendOnOff(ActivityUtils.getTopActivity(), false, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActAsPanelVM.this.lambda$new$0((Boolean) obj);
                    }
                });
                this.stateOn.setValue(false);
                break;
            case R.id.iv_open /* 2131297166 */:
                this.stateOn.setValue(true);
                showContent();
                CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), this.zoneNum).sendOnOff(ActivityUtils.getTopActivity(), true, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActAsPanelVM.this.lambda$new$1((Boolean) obj);
                    }
                });
                break;
            case R.id.iv_rgb /* 2131297213 */:
                LHomeLog.i(getClass(), "click rgbbrt");
                this.showRgbBrtDialogEvent.call();
                break;
            case R.id.iv_scene /* 2131297221 */:
                navigation(NavUtils.destination(ActSavePanelScene.class).withLong(Constants.CONTROL_ID, this.controlId));
                break;
            case R.id.iv_w /* 2131297318 */:
                LHomeLog.i(getClass(), "click w");
                this.showWDialogEvent.call();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Boolean bool) {
        if (bool.booleanValue() && this.controlObject.getValue() == null) {
            this.controlObject.getValue().getDeviceState().setOn(false);
            Injection.repo().device().saveDevice(this.controlObject.getValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Boolean bool) {
        if (bool.booleanValue() && this.controlObject.getValue() == null) {
            this.controlObject.getValue().getDeviceState().setOn(true);
            Injection.repo().device().saveDevice(this.controlObject.getValue());
        }
    }

    public boolean clickSelect(int position) {
        int i = this.zoneNum;
        int i2 = this.relatedInfoList.get(position).isSelect(i, position) ? (~(1 << position)) & i : (1 << position) | i;
        if (i2 == 0) {
            return false;
        }
        this.zoneNum = i2;
        return true;
    }

    private void refreshPanelZoneState() {
        int zoneCount = this.panelState.getZoneCount();
        for (int i = 0; i < zoneCount; i++) {
            int i2 = 1 << i;
            if ((this.zoneNum & i2) == i2) {
                PanelState.PanelZoneState panelZoneState = this.panelState.getPanelZoneStateList().get(i);
                this.panelZoneState = panelZoneState;
                this.panelZoneStateLiveData.setValue(panelZoneState);
                return;
            }
        }
        int zoneCount2 = this.panelState.getZoneCount();
        for (int i3 = 0; i3 < zoneCount2; i3++) {
            this.zoneNum |= 1 << i3;
        }
        PanelState.PanelZoneState panelZoneState2 = this.panelState.getPanelZoneStateList().get(0);
        this.panelZoneState = panelZoneState2;
        this.panelZoneStateLiveData.setValue(panelZoneState2);
    }

    public void executeScene(Context context, int sceneNum) {
        CmdAssistant.getSceneCmdAssistant(this.controlObject.getValue(), 65535).executeScene(context, sceneNum, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ((Boolean) obj).booleanValue();
            }
        });
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), this.zoneNum);
    }

    public void sendSingleOnOff(int position, boolean on) {
        CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), on ? this.zoneNum : 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), on);
    }

    public void queryPanelState(int type) {
        CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAsPanelVM.this.lambda$queryPanelState$4((ResponseMsg) obj);
            }
        }, type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelState$4(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelState(responseMsg.getAgreementId(), responseMsg.getResData()));
        } else {
            refreshPanelState(null);
        }
    }

    public void unBindRelateInfo(final int position) {
        int i;
        int i2;
        if (Injection.state().isConnectOuterNet()) {
            int publicationAddress = ((BleParam) this.controlObject.getValue().getParam(BleParam.class)).getPublicationAddress();
            RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(position);
            if (relateInfo.isRelateGroupInfo()) {
                if (this.groupList.getValue() != null) {
                    i = 0;
                    i2 = 0;
                    for (Group group : this.groupList.getValue()) {
                        if (group.getGroupId() == relateInfo.objectId) {
                            i2 = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
                            i = group.getGroupAddress();
                        }
                    }
                }
                i = 0;
                i2 = 0;
            } else {
                if (relateInfo.isRelateDeviceInfo() && this.deviceList.getValue() != null) {
                    i = 0;
                    i2 = 0;
                    for (Device device : this.deviceList.getValue()) {
                        if (device.getDeviceId() == relateInfo.objectId) {
                            i2 = ProductRepository.getAgreementIdByPid(device.getProductId());
                            i = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                        }
                    }
                }
                i = 0;
                i2 = 0;
            }
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribe(ActivityUtils.getTopActivity(), i, publicationAddress, i2, 1 << position, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAsPanelVM.this.lambda$unBindRelateInfo$5(position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$5(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetRelateInfo(i);
            uploadData();
        } else {
            dismissLoadingDialog();
            ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlObject.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAsPanelVM.this.lambda$uploadData$6((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAsPanelVM.this.lambda$uploadData$7(obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAsPanelVM.this.lambda$uploadData$8((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$7(Object obj) throws Exception {
        this.controlObject.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlObject.getValue());
        SingleLiveEvent<Device> singleLiveEvent = this.controlObject;
        singleLiveEvent.setValue(singleLiveEvent.getValue());
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$8(Throwable th) throws Exception {
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }
}