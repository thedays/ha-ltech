package com.ltech.smarthome.ui.device.smartpanel;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.extra.TabContentdefault;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelDetail;
import com.ltech.smarthome.ui.device.screenpanel.FtPageScreenPanelSwitch;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SmartPanelScreenState;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartPanelVM extends BaseViewModel {
    public long controlId;
    public int controlType;
    public List<Device> devices;
    public boolean groupControl;
    public List<Group> groups;
    public boolean isOpenElderLyMode;
    public HashMap<Integer, RelateInfoItem> longClickMap;
    public SwitchPanelState panelState;
    public SwitchPanelState.SwitchPanelZoneState panelZoneState;
    public long placeId;
    public String productId;
    public RelateInfoAssistant relateInfoAssistant;
    public List<RelateInfoItem> relatedInfoList;
    public Map<Integer, Object> relayObjectMap;
    public Map<Integer, Integer> relayPositiontMap;
    public Listing<Group> request;
    public List<Scene> scenes;
    public int screenCount;
    public int screensaverMod;
    public int screensaverTime;
    public int zoneCount;
    public int zoneNum;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<Object> updateUIEvent = new MutableLiveData<>();
    public MutableLiveData<List<SwitchPanelState.SwitchPanelZoneState>> panelZoneStateLiveData = new MutableLiveData<>();
    public List<TabContentdefault> tabs = new ArrayList();
    public MutableLiveData<Integer> curScreen = new MutableLiveData<>(0);
    public int[] themeNum = {1, 2, 3};
    public int[][] screenTime = {new int[]{8, 0, 17, 59}, new int[]{18, 0, 21, 59}, new int[]{22, 0, 7, 59}};
    public boolean[] isScreenTimeClose = {false, false, false};
    public boolean[] isScreenShow = {true, true, true};
    public List<Integer> downloadThemes = new ArrayList();
    public MutableLiveData<Boolean> screenStateEvent = new MutableLiveData<>();
    public SingleLiveEvent<Void> sceneBrtEvent = new SingleLiveEvent<>();
    public boolean isPro = true;
    public final int[] themeBgPic = {R.mipmap.g4_img_4, R.mipmap.g4_img_1, R.mipmap.g4_img_5, R.mipmap.g4_img_2, R.mipmap.g4_img_3, R.mipmap.g4_img_6, R.mipmap.g4_img_7, R.mipmap.g4_img_8, R.mipmap.g4_img_9, R.mipmap.g4_img_10, R.mipmap.g4_img_11};

    private int getSubscribeZone() {
        return 1;
    }

    static /* synthetic */ boolean lambda$groupSubscribe$16(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$panelSubscribe$18(BaseDialog baseDialog, View view) {
        return false;
    }

    public void initRelateInfoList(Object object) {
        RelateInfoUtils.initRelateInfoList(object);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        if (this.relatedInfoList == null) {
            this.relatedInfoList = new ArrayList();
        }
        this.relatedInfoList.clear();
        this.relatedInfoList.addAll(RelateInfoUtils.relatedInfoList);
        this.relayObjectMap = new HashMap();
        this.relayPositiontMap = new HashMap();
        this.longClickMap = new HashMap<>();
        int i = 0;
        int i2 = 1;
        boolean z = true;
        for (int i3 = 0; i3 < this.relatedInfoList.size(); i3++) {
            RelateInfoItem relateInfoItem = this.relatedInfoList.get(i3);
            if (relateInfoItem.relateInfo != null && relateInfoItem.relateInfo.switchIndex != 0) {
                if (object instanceof Device) {
                    this.relayObjectMap.put(Integer.valueOf(relateInfoItem.relateInfo.switchIndex), RelaySeparationHelper.getRelaySubDevice((Device) object, relateInfoItem.relateInfo.switchIndex));
                } else if (object instanceof Group) {
                    this.relayObjectMap.put(Integer.valueOf(relateInfoItem.relateInfo.switchIndex), RelaySeparationHelper.getRelaySubGroup((Group) object, relateInfoItem.relateInfo.switchIndex));
                }
                this.relayPositiontMap.put(Integer.valueOf(relateInfoItem.relateInfo.switchIndex), Integer.valueOf(i3));
                z = false;
            }
            RelatedInfoExtParam.RelateInfo relateLongClickInfo = this.relateInfoAssistant.getRelateLongClickInfo(i3);
            if (relateLongClickInfo != null) {
                RelateInfoItem relateInfoItem2 = new RelateInfoItem();
                relateInfoItem2.relateInfo = relateLongClickInfo;
                relateInfoItem2.infoName = RelateInfoUtils.getRelateInfoString(relateInfoItem2.relateInfo);
                relateInfoItem2.iconRes = RelateInfoUtils.getRelateInfoIcon(relateInfoItem2.relateInfo);
                relateInfoItem2.actionInfo = RelateInfoUtils.getRelateLongClickInfo(relateInfoItem2.relateInfo);
                relateInfoItem2.type = RelateInfoUtils.getRelateInfoType(relateInfoItem2.relateInfo);
                this.longClickMap.put(Integer.valueOf(i3), relateInfoItem2);
            }
        }
        if (z) {
            int lightColorType = ProductRepository.getLightColorType(this.controlObject.getValue());
            if (21 == lightColorType || lightColorType == 18 || lightColorType == 11) {
                i2 = 4;
            } else {
                if (19 != lightColorType) {
                    if (lightColorType != 8) {
                        if (lightColorType == 9) {
                            i2 = 2;
                        } else if (lightColorType != 10) {
                            i2 = 0;
                        }
                    }
                }
                i2 = 3;
            }
            while (i < i2) {
                int i4 = i + 1;
                if (object instanceof Device) {
                    this.relayObjectMap.put(Integer.valueOf(i4), RelaySeparationHelper.getRelaySubDevice((Device) object, i4));
                } else if (object instanceof Group) {
                    this.relayObjectMap.put(Integer.valueOf(i4), RelaySeparationHelper.getRelaySubGroup((Group) object, i4));
                }
                this.relayPositiontMap.put(Integer.valueOf(i4), Integer.valueOf(i));
                RelateInfoItem relateInfoItem3 = this.relatedInfoList.get(i);
                if (relateInfoItem3.relateInfo != null) {
                    relateInfoItem3.relateInfo.switchIndex = i4;
                    RelateInfoUtils.relatedInfoList.set(i, relateInfoItem3);
                    this.relateInfoAssistant.setRelateInfo(i, relateInfoItem3.relateInfo);
                }
                i = i4;
            }
        }
    }

    public void initPanelState(Object object) {
        int i = 0;
        if (object instanceof Device) {
            Device device = (Device) object;
            if (this.panelState == null) {
                SwitchPanelState switchPanelState = new SwitchPanelState();
                this.panelState = switchPanelState;
                switchPanelState.setZoneCount(ProductRepository.getZoneCount(device.getProductId()));
                ArrayList arrayList = new ArrayList();
                device.getDeviceState().getOnOffStates();
                int zoneCount = this.panelState.getZoneCount();
                int i2 = 0;
                while (i < zoneCount) {
                    arrayList.add(new SwitchPanelState.SwitchPanelZoneState());
                    i2 |= 1 << i;
                    i++;
                }
                this.panelState.setZoneNum(i2);
                this.panelState.setSwitchPanelZoneStateList(arrayList);
                this.zoneNum = this.panelState.getZoneNum();
            }
            refreshPanelState(this.panelState);
            return;
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            if (group.getDeviceIds().isEmpty() || this.panelState == null) {
                SwitchPanelState switchPanelState2 = new SwitchPanelState();
                this.panelState = switchPanelState2;
                switchPanelState2.setZoneCount(ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)));
                ArrayList arrayList2 = new ArrayList();
                List<Boolean> onOffStates = group.getGroupState().getOnOffStates();
                int zoneCount2 = this.panelState.getZoneCount();
                int i3 = 0;
                while (i < zoneCount2) {
                    SwitchPanelState.SwitchPanelZoneState switchPanelZoneState = new SwitchPanelState.SwitchPanelZoneState();
                    if (group.getDeviceIds().isEmpty() && onOffStates != null && i < onOffStates.size()) {
                        switchPanelZoneState.setSwitchOnOff(onOffStates.get(i).booleanValue());
                    }
                    arrayList2.add(switchPanelZoneState);
                    i3 |= 1 << i;
                    i++;
                }
                this.panelState.setZoneNum(i3);
                this.panelState.setSwitchPanelZoneStateList(arrayList2);
                this.zoneNum = this.panelState.getZoneNum();
            }
            refreshPanelState(this.panelState);
        }
    }

    public void refreshPanelState(SwitchPanelState panelState) {
        if (this.panelZoneState == null) {
            this.panelZoneState = new SwitchPanelState.SwitchPanelZoneState();
        }
        if (panelState != null) {
            this.panelState = panelState;
            this.zoneNum = panelState.getZoneNum();
            refreshPanelZoneState();
        }
    }

    private void refreshPanelZoneState() {
        if (this.groupControl) {
            Object value = this.controlObject.getValue();
            if (value instanceof Group) {
                Group group = (Group) value;
                ArrayList arrayList = new ArrayList();
                int zoneCount = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group));
                for (int i = 0; i < zoneCount; i++) {
                    if (i >= this.panelState.getSwitchPanelZoneStateList().size()) {
                        arrayList.add(false);
                    } else {
                        arrayList.add(Boolean.valueOf(this.panelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff()));
                    }
                }
                Group groupById = Injection.repo().group().getGroupById(group.getId());
                groupById.getGroupState().setOnOffStates(arrayList);
                Injection.repo().group().saveGroup(groupById);
                Iterator<Long> it = groupById.getDeviceIds().iterator();
                while (it.hasNext()) {
                    long longValue = it.next().longValue();
                    List<Device> value2 = Injection.repo().device().getDeviceListCache().getValue();
                    if (value2 != null) {
                        for (int i2 = 0; i2 < value2.size(); i2++) {
                            if (longValue == value2.get(i2).getDeviceId()) {
                                value2.get(i2).getDeviceState().setOnOffStates(arrayList);
                                Injection.repo().device().saveDevice(value2.get(i2));
                            }
                        }
                    }
                }
            }
        }
        this.panelZoneStateLiveData.setValue(this.panelState.getSwitchPanelZoneStateList());
    }

    public void unBindRelateInfo(final int position) {
        int groupAddress;
        int i;
        int i2;
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
            if (!this.groupControl) {
                groupAddress = ((BleParam) ((Device) this.controlObject.getValue()).getParam(BleParam.class)).getUnicastAddress();
            } else {
                groupAddress = ((Group) this.controlObject.getValue()).getGroupAddress();
            }
            int i3 = groupAddress;
            RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(position);
            if (relateInfo == null) {
                uploadData();
                return;
            }
            if (relateInfo.isRelateGroupInfo()) {
                i2 = 0;
                for (Group group : Injection.repo().group().getGroupListByPlaceId(this.placeId)) {
                    if (group.getGroupId() == relateInfo.objectId) {
                        i2 = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
                        group.getGroupAddress();
                    }
                }
            } else if (relateInfo.isRelateDeviceInfo()) {
                i2 = 0;
                for (Device device : Injection.repo().device().getDeviceListByPlaceId(this.placeId)) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        i2 = ProductRepository.getAgreementIdByPid(device.getProductId());
                        ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                    }
                }
            } else {
                i = 2;
                CmdAssistant.getSettingCmdAssistant(this.controlObject.getValue(), new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), i3, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSmartPanelVM.this.lambda$unBindRelateInfo$1(position, (Boolean) obj);
                    }
                });
                return;
            }
            i = i2;
            CmdAssistant.getSettingCmdAssistant(this.controlObject.getValue(), new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), i3, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelVM.this.lambda$unBindRelateInfo$1(position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$1(final int i, Boolean bool) {
        if (bool.booleanValue()) {
            if (this.groupControl) {
                this.relateInfoAssistant.resetSmartPanelRelateInfo(i);
                this.relateInfoAssistant.setRelateLongClickInfo(i, new RelatedInfoExtParam.RelateInfo());
            } else {
                this.relateInfoAssistant.resetSmartPanelRelateInfo(((Device) this.controlObject.getValue()).getProductId(), i);
                this.relateInfoAssistant.setRelateLongClickInfo(i, new RelatedInfoExtParam.RelateInfo());
            }
            uploadData();
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ActSmartPanelVM.this.lambda$unBindRelateInfo$0(i);
                }
            }, 200L);
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ActSmartPanelVM.this.isPro && RelaySeparationHelper.isRelaySeparationDevice(ActSmartPanelVM.this.controlObject.getValue())) {
                        ActSmartPanelVM.this.resetScreen(i);
                    }
                }
            }, 500L);
            return;
        }
        dismissLoadingDialog();
        ActivityUtils.getTopActivity().runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.2
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$0(int i) {
        if (this.groupControl) {
            return;
        }
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.BIND, null);
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LONG_BIND, null);
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, null);
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LONG_TEXT, null);
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.K_VALUE, null);
        ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), ((Device) this.controlObject.getValue()).getDeviceId(), i + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetScreen(int position) {
        Group relaySubGroup;
        int i = this.relateInfoAssistant.getRelateInfo(position).switchIndex;
        if (i > 0) {
            Object value = this.controlObject.getValue();
            String str = null;
            if (value instanceof Device) {
                Device relaySubDevice = RelaySeparationHelper.getRelaySubDevice((Device) value, i);
                if (relaySubDevice != null) {
                    str = relaySubDevice.getName();
                }
            } else {
                Object value2 = this.controlObject.getValue();
                if ((value2 instanceof Group) && (relaySubGroup = RelaySeparationHelper.getRelaySubGroup((Group) value2, i)) != null) {
                    str = relaySubGroup.getName();
                }
            }
            String str2 = str;
            if (str2 != null) {
                RelaySeparationHelper.setScreenData(ActivityUtils.getTopActivity(), position, str2, this.controlObject.getValue(), this.relateInfoAssistant, true, new IAction<Integer>(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.3
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Integer integer) {
                    }
                });
            }
        }
    }

    public void sendSingleOnOff(int position, boolean on) {
        CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), on);
    }

    public void sendBindCommand(int position, boolean on) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), 1 << position).panelBindCmdControl(ActivityUtils.getTopActivity());
    }

    public void queryPanelState(Object object, int type, final int zoneCount) {
        LHomeLog.i(getClass(), "message_send queryPanelState enter");
        if (this.groupControl) {
            if (object instanceof Group) {
                Group group = (Group) object;
                if (group.getDeviceIds().isEmpty()) {
                    return;
                }
                final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
                CmdAssistant.getQueryCmdAssistant(deviceByDeviceId, new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSmartPanelVM.this.lambda$queryPanelState$2(deviceByDeviceId, zoneCount, (ResponseMsg) obj);
                    }
                }, type);
                return;
            }
            return;
        }
        if (object instanceof Device) {
            final Device device = (Device) object;
            CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelVM.this.lambda$queryPanelState$3(device, zoneCount, (ResponseMsg) obj);
                }
            }, type);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelState$2(Device device, int i, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()));
        } else {
            refreshPanelState(this.panelState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelState$3(Device device, int i, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()));
        } else {
            refreshPanelState(this.panelState);
        }
    }

    public void uploadData() {
        if (this.groupControl) {
            final Group group = (Group) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda15
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelVM.this.lambda$uploadData$4((Disposable) obj);
                }
            }).doFinally(new ActSmartPanelVM$$ExternalSyntheticLambda16(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda17
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelVM.this.lambda$uploadData$5(group, obj);
                }
            }, new SmartErrorComsumer());
        } else {
            final Device device = (Device) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda18
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelVM.this.lambda$uploadData$6((Disposable) obj);
                }
            }).doFinally(new ActSmartPanelVM$$ExternalSyntheticLambda16(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda19
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelVM.this.lambda$uploadData$7(device, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$5(Group group, Object obj) throws Exception {
        group.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(group);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.updateUIEvent.setValue(group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$7(Device device, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.updateUIEvent.setValue(device);
    }

    public int getProPanelCount(Group group) {
        int i = 0;
        if (group != null && !group.getDeviceIds().isEmpty()) {
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                if (deviceByDeviceId != null && ProductRepository.isScreenPanel(deviceByDeviceId.getProductId())) {
                    i++;
                }
            }
        }
        return i;
    }

    public void changeShowType(final Group group, int showType) {
        if (this.relateInfoAssistant.getSwitchShowType() != showType) {
            this.relateInfoAssistant.setSwitchShowType(showType);
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), this.relateInfoAssistant.getExtParamString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelVM.this.lambda$changeShowType$8(group, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeShowType$8(Group group, Object obj) throws Exception {
        group.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(group);
    }

    private void uploadDataS8() {
        final Device device = (Device) this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelVM.this.lambda$uploadDataS8$9((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelVM$$ExternalSyntheticLambda16(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelVM.this.lambda$uploadDataS8$10(device, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDataS8$9(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDataS8$10(Device device, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.updateUIEvent.setValue(device);
    }

    public void uploadClearDataS8() {
        final Device device = (Device) this.controlObject.getValue();
        if (this.relateInfoAssistant == null) {
            this.relateInfoAssistant = new RelateInfoAssistant(device);
        }
        for (int i = 0; i < 8; i++) {
            this.relateInfoAssistant.resetSmartPanelRelateInfo(i);
        }
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelVM.this.lambda$uploadClearDataS8$11(device, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadClearDataS8$11(Device device, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
    }

    public void updateGroupStates(boolean[] selectArray, Group group) {
        ArrayList arrayList = new ArrayList();
        for (boolean z : selectArray) {
            arrayList.add(Boolean.valueOf(z));
        }
        group.getGroupState().setOnOffStates(arrayList);
        Injection.repo().group().saveGroup(group);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
            for (int i = 0; i < value.size(); i++) {
                if (longValue == value.get(i).getDeviceId()) {
                    value.get(i).getDeviceState().setOnOffStates(arrayList);
                    Injection.repo().device().saveDevice(value.get(i));
                }
            }
        }
    }

    public void showBindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getContext().getString(R.string.please_choose)).setCancelString(getContext().getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getContext().getString(R.string.device));
        arrayList.add(getContext().getString(R.string.local_scene));
        arrayList.add(getContext().getString(R.string.dali_scene));
        if (!this.groupControl) {
            arrayList.add(getContext().getString(R.string.link_automation));
        }
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelVM.this.lambda$showBindDialog$12(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$12(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue != 0) {
            if (intValue == 1) {
                NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                return;
            } else if (intValue == 2) {
                NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                return;
            } else {
                if (intValue != 3) {
                    return;
                }
                NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.RELATED_POSITION, i).withLong(Constants.CONTROL_ID, this.controlId).navigation(ActivityUtils.getTopActivity());
                return;
            }
        }
        long j = 0;
        if (this.groupControl) {
            Group group = (Group) this.controlObject.getValue();
            if (group != null) {
                j = group.getGroupId();
            }
        } else {
            Device device = (Device) this.controlObject.getValue();
            if (device != null) {
                j = device.getDeviceId();
            }
        }
        NavUtils.destination(ActSmartPanelSelectBleDeviceAndGroupNew.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.RELATED_POSITION, i).withString(Constants.PRODUCT_ID, this.productId).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, this.controlType).withLong(Constants.ROLE_ID, j).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
    }

    public void initTabList() {
        if (this.tabs.isEmpty()) {
            this.tabs.add(new TabContentdefault(StringUtils.getString(R.string.switch_state), new FtPageScreenPanelSwitch()));
            if (ProductRepository.getLightColorType(this.controlObject.getValue()) == 19 || ProductRepository.getLightColorType(this.controlObject.getValue()) == 8 || ProductRepository.getLightColorType(this.controlObject.getValue()) == 9 || ProductRepository.getLightColorType(this.controlObject.getValue()) == 10 || ProductRepository.getLightColorType(this.controlObject.getValue()) == 18 || ProductRepository.getLightColorType(this.controlObject.getValue()) == 11) {
                if (this.isPro) {
                    this.tabs.add(new TabContentdefault(StringUtils.getString(R.string.app_str_screen_and_key), FtPageScreenPanelDetail.newInstance(0)));
                    return;
                } else {
                    this.tabs.add(new TabContentdefault(StringUtils.getString(R.string.parameter_setting), FtPageScreenPanelDetail.newInstance(0)));
                    return;
                }
            }
            for (int i = 0; i < this.screenCount; i++) {
                this.tabs.add(new TabContentdefault(StringUtils.getStringArray(R.array.page_screen_sp_tab)[i], FtPageScreenPanelDetail.newInstance(i)));
            }
        }
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void setScreenShowTime(final int curScreenNum) {
        int[] iArr = this.screenTime[curScreenNum];
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setScreenShowTime(getContext(), 1 << curScreenNum, iArr[0], iArr[1], iArr[2], iArr[3], new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActSmartPanelVM.this.isScreenTimeClose[curScreenNum] = false;
                    Object value = ActSmartPanelVM.this.controlObject.getValue();
                    if (value instanceof Device) {
                        Device device = (Device) value;
                        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SCREEN_VISIBLE_TIME, CmdAssistant.getDeviceAssistant(device, new int[0]).setScreenShowTime(1 << curScreenNum, ActSmartPanelVM.this.screenTime[curScreenNum][0], ActSmartPanelVM.this.screenTime[curScreenNum][1], ActSmartPanelVM.this.screenTime[curScreenNum][2], ActSmartPanelVM.this.screenTime[curScreenNum][3]));
                        ReplaceHelper.instance().backupIndexData(ActSmartPanelVM.this.getLifecycleOwner(), device.getDeviceId(), curScreenNum + 1);
                    } else {
                        Object value2 = ActSmartPanelVM.this.controlObject.getValue();
                        if (value2 instanceof Group) {
                            ActSmartPanelVM.this.queryPanelScreenState((Group) value2);
                        }
                    }
                }
            }
        });
        setSmartPanelShowTimeClose(curScreenNum, false);
    }

    public void setSmartPanelShowTimeClose(int curScreenNum) {
        setSmartPanelShowTimeClose(curScreenNum, true);
    }

    public void setSmartPanelShowTimeClose(final int curScreenNum, final boolean b2) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setSmartPanelShowTimeClose(getContext(), 1 << curScreenNum, b2, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActSmartPanelVM.this.isScreenTimeClose[curScreenNum] = b2;
                    if (ActSmartPanelVM.this.controlObject.getValue() instanceof Device) {
                        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SCREEN_VISIBLE_TIME_ENABLED, CmdAssistant.getDeviceAssistant(ActSmartPanelVM.this.controlObject.getValue(), new int[0]).setSmartPanelShowTimeClose(1 << curScreenNum, b2));
                        ReplaceHelper.instance().backupIndexData(ActSmartPanelVM.this.getLifecycleOwner(), ((Device) ActSmartPanelVM.this.controlObject.getValue()).getDeviceId(), curScreenNum + 1);
                    }
                }
            }
        });
    }

    public void setSmartPanelShowScreen(final int curScreenNum, final boolean b2) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setSmartPanelShowScreen(getContext(), 1 << curScreenNum, b2, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.6
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActSmartPanelVM.this.isScreenShow[curScreenNum] = b2;
                    Object value = ActSmartPanelVM.this.controlObject.getValue();
                    if (value instanceof Device) {
                        Device device = (Device) value;
                        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SCREEN_VISIBLE, CmdAssistant.getDeviceAssistant(device, new int[0]).setSmartPanelShowScreen(1 << curScreenNum, b2));
                        ReplaceHelper.instance().backupIndexData(ActSmartPanelVM.this.getLifecycleOwner(), device.getDeviceId(), curScreenNum + 1);
                    } else {
                        Object value2 = ActSmartPanelVM.this.controlObject.getValue();
                        if (value2 instanceof Group) {
                            ActSmartPanelVM.this.queryPanelScreenState((Group) value2);
                        }
                    }
                }
            }
        });
    }

    public void queryPanelScreenState(Object object) {
        LHomeLog.i(getClass(), "message_send queryPanelScreenState enter");
        if (this.groupControl) {
            if (object instanceof Group) {
                Group group = (Group) object;
                if (group.getDeviceIds().isEmpty()) {
                    return;
                }
                final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
                CmdAssistant.getQueryCmdAssistant(deviceByDeviceId, new int[0]).queryPanelScreenState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda11
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSmartPanelVM.this.lambda$queryPanelScreenState$13(deviceByDeviceId, (ResponseMsg) obj);
                    }
                });
                return;
            }
            return;
        }
        if (object instanceof Device) {
            final Device device = (Device) object;
            CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelScreenState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelVM.this.lambda$queryPanelScreenState$14(device, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelScreenState$13(Device device, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            parserPanelScreenState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelScreenState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, this.zoneCount, responseMsg.getResData()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPanelScreenState$14(Device device, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            parserPanelScreenState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelScreenState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, this.zoneCount, responseMsg.getResData()));
        }
    }

    private void parserPanelScreenState(SmartPanelScreenState smartPanelScreenState) {
        if (smartPanelScreenState != null) {
            Object value = this.controlObject.getValue();
            if (value instanceof Group) {
                Group group = (Group) value;
                group.setScreenSetting(GsonUtils.toJson(smartPanelScreenState));
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                groupByGroupId.setScreenSetting(GsonUtils.toJson(smartPanelScreenState));
                Injection.repo().group().saveGroup(groupByGroupId);
            }
            this.themeNum = new int[smartPanelScreenState.getTheme().size()];
            for (int i = 0; i < smartPanelScreenState.getTheme().size(); i++) {
                this.themeNum[i] = smartPanelScreenState.getTheme().get(i).intValue();
            }
            this.screensaverTime = smartPanelScreenState.getScreensaverTime();
            this.screensaverMod = smartPanelScreenState.getScreensaverMode();
            if (smartPanelScreenState.getScreens() != null) {
                for (int i2 = 0; i2 < smartPanelScreenState.getScreens().size(); i2++) {
                    SmartPanelScreenState.ScreenInfo screenInfo = smartPanelScreenState.getScreens().get(i2);
                    this.isScreenShow[i2] = screenInfo.isShow();
                    this.isScreenTimeClose[i2] = screenInfo.isScreenTimeClose();
                    this.screenTime[i2][0] = screenInfo.getStartM();
                    this.screenTime[i2][1] = screenInfo.getStartS();
                    this.screenTime[i2][2] = screenInfo.getEndM();
                    this.screenTime[i2][3] = screenInfo.getEndS();
                }
            }
            if (smartPanelScreenState.getDownloads() != null && !smartPanelScreenState.getDownloads().isEmpty()) {
                this.downloadThemes = smartPanelScreenState.getDownloads();
            }
            this.screenStateEvent.setValue(true);
        }
    }

    public int getCurScreen() {
        if (this.curScreen.getValue() != null) {
            return this.curScreen.getValue().intValue();
        }
        return 0;
    }

    public void setDelayActionTime(final int pos, final int time, final IAction<Integer> iAction) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), 1 << pos).setDelayTime(ActivityUtils.getTopActivity(), time, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelVM.this.lambda$setDelayActionTime$15(iAction, time, pos, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDelayActionTime$15(IAction iAction, int i, int i2, Boolean bool) {
        if (bool.booleanValue()) {
            iAction.act(Integer.valueOf(i));
            if (this.controlObject.getValue() instanceof Device) {
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LONG_BIND, null);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SWITCH_ACTION_DELAY, CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), 1 << i2).setDelayTime(i));
                ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), ((Device) this.controlObject.getValue()).getDeviceId(), i2 + 1);
            }
        }
    }

    public void changePanelScreen(int i) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setChangeCurScreen(getContext(), i);
    }

    public void loadSettingStatus(final Group group) {
        CmdAssistant.getQueryCmdAssistant(group, new int[0]).querySmartPanelSetting(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.7
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                ActSmartPanelVM.this.dismissLoadingDialog();
                if (msg == null) {
                    return;
                }
                BleParam bleParam = (BleParam) group.getParam(BleParam.class);
                SmartPanelSettingState parserSmartPanelSettingState = ((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSmartPanelSettingState(msg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, msg.getResData());
                if (parserSmartPanelSettingState != null) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                    groupByGroupId.setPanelSettingState(GsonUtils.toJson(parserSmartPanelSettingState));
                    Injection.repo().group().saveGroup(groupByGroupId);
                }
            }
        });
    }

    private void groupSubscribe(int selectPosition, int subscribeAddress) {
        Group group = (Group) this.controlObject.getValue();
        CmdAssistant.getSettingCmdAssistant(group, new int[0]).subscribeInSwitchPanelScene(getContext(), 1 << selectPosition, group.getGroupAddress(), subscribeAddress, 2, getSubscribeZone(), this.relateInfoAssistant.getRelateLongClickInfo(selectPosition).action, this.relateInfoAssistant.getRelateLongClickInfo(selectPosition).type, this.relateInfoAssistant.getRelateLongClickInfo(selectPosition).keyActionExtra, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelVM.this.lambda$groupSubscribe$17((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$groupSubscribe$17(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                uploadData();
                return;
            } else {
                if (responseMsg.getStateCode() == 3) {
                    dismissLoadingDialog();
                    MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), StringUtils.getString(R.string.app_str_operation_failure), StringUtils.getString(R.string.local_scene_error_03)).setOkButton(StringUtils.getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda10
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public final boolean onClick(BaseDialog baseDialog, View view) {
                            return ActSmartPanelVM.lambda$groupSubscribe$16(baseDialog, view);
                        }
                    });
                    return;
                }
                return;
            }
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    private void panelSubscribe(final int selectPosition, final int subscribeAddress) {
        final Device device = (Device) this.controlObject.getValue();
        showLoadingDialog(StringUtils.getString(R.string.subscribing));
        int unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
        final int subscribeZone = getSubscribeZone();
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).subscribeInSwitchPanelScene(getContext(), 1 << selectPosition, unicastAddress, subscribeAddress, 2, subscribeZone, this.relateInfoAssistant.getRelateLongClickInfo(selectPosition).action, this.relateInfoAssistant.getRelateLongClickInfo(selectPosition).type, this.relateInfoAssistant.getRelateLongClickInfo(selectPosition).keyActionExtra, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelVM.this.lambda$panelSubscribe$19(device, selectPosition, subscribeAddress, subscribeZone, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$panelSubscribe$19(Device device, int i, int i2, int i3, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                uploadData();
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SWITCH_ACTION_DELAY, null);
                ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LONG_BIND, CmdAssistant.getSettingCmdAssistant(device, new int[0]).subscribeInSwitchPanelScene(1 << i, i2, i3, this.relateInfoAssistant.getRelateLongClickInfo(i).action, this.relateInfoAssistant.getRelateLongClickInfo(i).type, this.relateInfoAssistant.getRelateLongClickInfo(i).keyActionExtra));
                ReplaceHelper.instance().backupIndexData(getLifecycleOwner(), device.getDeviceId(), i + 1);
                return;
            }
            if (responseMsg.getStateCode() == 3) {
                dismissLoadingDialog();
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), StringUtils.getString(R.string.app_str_operation_failure), StringUtils.getString(R.string.local_scene_error_03)).setOkButton(StringUtils.getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM$$ExternalSyntheticLambda20
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActSmartPanelVM.lambda$panelSubscribe$18(baseDialog, view);
                    }
                });
                return;
            }
            return;
        }
        dismissLoadingDialog();
        showFailDialog();
    }

    private void showFailDialog() {
        ThreadUtils.runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM.8
            @Override // java.lang.Runnable
            public void run() {
                ViewHelpUtil.showBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
            }
        });
    }

    public void subscribe(int selectPosition, int i, long objectId) {
        Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(objectId);
        RelatedInfoExtParam.RelateInfo RelatedSceneInfo = RelatedInfoExtParam.RelateInfo.RelatedSceneInfo(sceneBySceneId.getSceneId());
        RelatedSceneInfo.keyActionExtra = sceneBySceneId.getSceneNum();
        RelatedSceneInfo.action = i;
        RelatedSceneInfo.type = 3;
        this.relateInfoAssistant.setRelateLongClickInfo(selectPosition, RelatedSceneInfo);
        if (this.groupControl) {
            groupSubscribe(selectPosition, 65025);
        } else {
            panelSubscribe(selectPosition, 65025);
        }
    }
}