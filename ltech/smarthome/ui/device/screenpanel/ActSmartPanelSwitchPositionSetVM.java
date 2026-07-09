package com.ltech.smarthome.ui.device.screenpanel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.SmartPanelParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartPanelSwitchPositionSetVM extends BaseViewModel {
    public long controlId;
    private int curScreen;
    public boolean groupControl;
    public List<Integer> oldList;
    public long placeId;
    public RelateInfoAssistant relateInfoAssistant;
    public List<RelateInfoItem> relatedInfoList;
    public Map<Integer, Object> relayPositionMap;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public List<Integer> switchPosition = new ArrayList();
    public List<Integer> newPosition = new ArrayList();

    private int getRelayNum(int type) {
        if (21 == type || type == 18 || type == 11) {
            return 4;
        }
        if (19 == type) {
            return 3;
        }
        if (type == 8) {
            return 1;
        }
        if (type == 9) {
            return 2;
        }
        return type == 10 ? 3 : 0;
    }

    public void initRelateInfoList(Object object) {
        RelateInfoUtils.initRelateInfoList(object);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        if (this.relatedInfoList == null) {
            this.relatedInfoList = new ArrayList();
        }
        this.relatedInfoList.clear();
        this.relatedInfoList.addAll(RelateInfoUtils.relatedInfoList);
        this.relayPositionMap = new HashMap();
        boolean z = true;
        int i = 0;
        for (int i2 = 0; i2 < this.relatedInfoList.size(); i2++) {
            RelateInfoItem relateInfoItem = this.relatedInfoList.get(i2);
            if (relateInfoItem.relateInfo != null && relateInfoItem.relateInfo.switchIndex != 0) {
                this.switchPosition.add(Integer.valueOf(relateInfoItem.relateInfo.switchIndex));
                if (object instanceof Device) {
                    this.relayPositionMap.put(Integer.valueOf(relateInfoItem.relateInfo.switchIndex), RelaySeparationHelper.getRelaySubDevice((Device) object, relateInfoItem.relateInfo.switchIndex));
                } else if (object instanceof Group) {
                    this.relayPositionMap.put(Integer.valueOf(relateInfoItem.relateInfo.switchIndex), RelaySeparationHelper.getRelaySubGroup((Group) object, relateInfoItem.relateInfo.switchIndex));
                }
                z = false;
            } else {
                this.switchPosition.add(0);
            }
        }
        if (z) {
            int relayNum = getRelayNum(ProductRepository.getLightColorType(this.controlObject.getValue()));
            while (i < relayNum) {
                int i3 = i + 1;
                this.switchPosition.set(i, Integer.valueOf(i3));
                if (object instanceof Device) {
                    this.relayPositionMap.put(Integer.valueOf(i3), RelaySeparationHelper.getRelaySubDevice((Device) object, i3));
                } else if (object instanceof Group) {
                    this.relayPositionMap.put(Integer.valueOf(i3), RelaySeparationHelper.getRelaySubGroup((Group) object, i3));
                }
                RelateInfoItem relateInfoItem2 = this.relatedInfoList.get(i);
                if (relateInfoItem2.relateInfo != null) {
                    relateInfoItem2.relateInfo.switchIndex = i3;
                    RelateInfoUtils.relatedInfoList.set(i, relateInfoItem2);
                    this.relateInfoAssistant.setRelateInfo(i, relateInfoItem2.relateInfo);
                }
                i = i3;
            }
        }
        this.newPosition.addAll(this.switchPosition);
        ArrayList arrayList = new ArrayList();
        this.oldList = arrayList;
        arrayList.addAll(this.switchPosition);
    }

    public void save() {
        final int[] ints = getInts(ProductRepository.getLightColorType(this.controlObject.getValue()));
        for (int i = 0; i < ints.length; i++) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.newPosition.size()) {
                    break;
                }
                if (this.newPosition.get(i2).intValue() == i + 1) {
                    ints[i] = i2 + 1;
                    break;
                }
                i2++;
            }
        }
        showLoadingDialog(getContext().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).relayMapping(getContext(), ints, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                ActSmartPanelSwitchPositionSetVM.this.dismissLoadingDialog();
                if (!aBoolean.booleanValue()) {
                    SmartToast.showCenterShort(ActSmartPanelSwitchPositionSetVM.this.getContext().getString(R.string.save_fail));
                    return;
                }
                if (ActSmartPanelSwitchPositionSetVM.this.newPosition.size() != ActSmartPanelSwitchPositionSetVM.this.relatedInfoList.size()) {
                    SmartToast.showCenterShort(ActSmartPanelSwitchPositionSetVM.this.getContext().getString(R.string.save_fail));
                } else {
                    for (int i3 = 0; i3 < ActSmartPanelSwitchPositionSetVM.this.relatedInfoList.size(); i3++) {
                        RelateInfoItem relateInfoItem = ActSmartPanelSwitchPositionSetVM.this.relatedInfoList.get(i3);
                        if (relateInfoItem.relateInfo != null && relateInfoItem.relateInfo.switchIndex != ActSmartPanelSwitchPositionSetVM.this.newPosition.get(i3).intValue()) {
                            relateInfoItem.relateInfo.switchIndex = ActSmartPanelSwitchPositionSetVM.this.newPosition.get(i3).intValue();
                            if (relateInfoItem.infoName == null || relateInfoItem.type == 0) {
                                relateInfoItem.relateInfo.screenType = RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue();
                                relateInfoItem.relateInfo.screenStr = "";
                                relateInfoItem.relateInfo.delay = 0;
                            }
                        }
                    }
                    Object value = ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue();
                    if (value instanceof Device) {
                        Device device = (Device) value;
                        if (!ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId()) && !ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId()) && !ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId()) && !ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId()) && !ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId()) && !ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId()) && !ProductId.ID_SMART_SWITCH_S6.equals(device.getProductId())) {
                            ActSmartPanelSwitchPositionSetVM.this.setScreen();
                        } else {
                            ActSmartPanelSwitchPositionSetVM actSmartPanelSwitchPositionSetVM = ActSmartPanelSwitchPositionSetVM.this;
                            actSmartPanelSwitchPositionSetVM.updateParamExt(device, actSmartPanelSwitchPositionSetVM.relateInfoAssistant.getExtParamString());
                        }
                    } else {
                        Object value2 = ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue();
                        if (value2 instanceof Group) {
                            Group group = (Group) value2;
                            if (ActSmartPanelSwitchPositionSetVM.this.getProPanelCount(group) > 0) {
                                ActSmartPanelSwitchPositionSetVM.this.setScreen();
                            } else {
                                ActSmartPanelSwitchPositionSetVM actSmartPanelSwitchPositionSetVM2 = ActSmartPanelSwitchPositionSetVM.this;
                                actSmartPanelSwitchPositionSetVM2.updateParamExt(group, actSmartPanelSwitchPositionSetVM2.relateInfoAssistant.getExtParamString());
                            }
                        }
                    }
                }
                Object value3 = ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue();
                if (value3 instanceof Device) {
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.RELAY_POSITION, CmdAssistant.getDeviceAssistant(ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue(), new int[0]).relayMapping(ints));
                    ReplaceHelper.instance().backupData(ActSmartPanelSwitchPositionSetVM.this.getLifecycleOwner(), ((Device) value3).getDeviceId());
                }
            }
        });
    }

    public int getProPanelCount(Group group) {
        int i = 0;
        if (group.getDeviceIds().size() > 0) {
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

    private int[] getInts(int type) {
        int[] iArr = new int[0];
        if (type == 19) {
            return new int[3];
        }
        if (type == 21 || type == 18 || type == 11) {
            return new int[4];
        }
        if (type == 8) {
            return new int[1];
        }
        if (type == 9) {
            return new int[2];
        }
        return type == 10 ? new int[3] : iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreen() {
        SmartPanelParam.DisplayParam displayParam;
        SmartPanelParam.DisplayParam displayParam2;
        Group relaySubGroup;
        String name;
        SmartPanelParam.DisplayParam displayParam3;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.relatedInfoList.size(); i++) {
            RelateInfoItem relateInfoItem = this.relatedInfoList.get(i);
            int i2 = relateInfoItem.relateInfo.switchIndex;
            RelateInfoAssistant relateInfoAssistant = this.relateInfoAssistant;
            boolean z = relateInfoAssistant != null && relateInfoAssistant.getSwitchScreenBigIcon() == 2;
            SmartPanelParam.DisplayParam displayParam4 = null;
            if (relateInfoItem.infoName != null && relateInfoItem.type != 0) {
                try {
                } catch (Exception e) {
                    e = e;
                }
                if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                    displayParam3 = new SmartPanelParam.DisplayParam();
                    try {
                        String[] split = relateInfoItem.relateInfo.screenStr.split("\n");
                        displayParam3.setFirstType(1);
                        displayParam3.setFirstData(split[0].getBytes("gb2312"));
                        if (!isSingleLine() && split.length > 1) {
                            displayParam3.setSecondType(1);
                            displayParam3.setSecondData(split[1].getBytes("gb2312"));
                        }
                    } catch (Exception e2) {
                        e = e2;
                        displayParam4 = displayParam3;
                        LHomeLog.e(getClass(), e.toString());
                        arrayList.add(displayParam4);
                        this.relateInfoAssistant.setRelateInfo(i, relateInfoItem.relateInfo);
                    }
                } else if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                    displayParam3 = new SmartPanelParam.DisplayParam();
                    displayParam3.setFirstType(2);
                    displayParam3.setSecondType(0);
                    int sendIconIndex = ScreenIconUtils.getSendIconIndex(relateInfoItem.relateInfo.iconIndex);
                    displayParam3.setFirstData(new byte[]{(byte) ((sendIconIndex >> 8) & 255), (byte) (sendIconIndex & 255)});
                } else {
                    if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                        displayParam3 = new SmartPanelParam.DisplayParam();
                        displayParam3.setFirstType(2);
                        int sendIconIndex2 = ScreenIconUtils.getSendIconIndex(relateInfoItem.relateInfo.iconIndex);
                        displayParam3.setFirstData(new byte[]{(byte) ((sendIconIndex2 >> 8) & 255), (byte) (sendIconIndex2 & 255)});
                        displayParam3.setSecondType(1);
                        displayParam3.setSecondData(relateInfoItem.relateInfo.screenStr.getBytes("gb2312"));
                    }
                    arrayList.add(displayParam4);
                }
                displayParam4 = displayParam3;
                arrayList.add(displayParam4);
            } else if (i2 > 0) {
                if (relateInfoItem.relateInfo.screenType == 0) {
                    Object value = this.controlObject.getValue();
                    if (value instanceof Device) {
                        Device relaySubDevice = RelaySeparationHelper.getRelaySubDevice((Device) value, i2);
                        if (relaySubDevice != null) {
                            name = relaySubDevice.getName();
                        }
                        name = null;
                    } else {
                        Object value2 = this.controlObject.getValue();
                        if ((value2 instanceof Group) && (relaySubGroup = RelaySeparationHelper.getRelaySubGroup((Group) value2, i2)) != null) {
                            name = relaySubGroup.getName();
                        }
                        name = null;
                    }
                    if (name != null) {
                        try {
                            SmartPanelParam.DisplayParam displayParam5 = new SmartPanelParam.DisplayParam();
                            try {
                                String[] split2 = getString(name, z).split("\n");
                                displayParam5.setFirstType(1);
                                displayParam5.setFirstData(split2[0].getBytes("gb2312"));
                                if (!isSingleLine() && split2.length > 1) {
                                    displayParam5.setSecondType(1);
                                    displayParam5.setSecondData(split2[1].getBytes("gb2312"));
                                }
                            } catch (Exception unused) {
                            }
                            displayParam4 = displayParam5;
                        } catch (Exception unused2) {
                        }
                    }
                } else {
                    try {
                        if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                            displayParam2 = new SmartPanelParam.DisplayParam();
                            try {
                                String[] split3 = relateInfoItem.relateInfo.screenStr.split("\n");
                                displayParam2.setFirstType(1);
                                displayParam2.setFirstData(split3[0].getBytes("gb2312"));
                                if (!isSingleLine() && split3.length > 1) {
                                    displayParam2.setSecondType(1);
                                    displayParam2.setSecondData(split3[1].getBytes("gb2312"));
                                }
                            } catch (Exception e3) {
                                e = e3;
                                displayParam4 = displayParam2;
                                LHomeLog.e(getClass(), e.toString());
                                arrayList.add(displayParam4);
                                this.relateInfoAssistant.setRelateInfo(i, relateInfoItem.relateInfo);
                            }
                        } else if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                            displayParam2 = new SmartPanelParam.DisplayParam();
                            displayParam2.setFirstType(2);
                            displayParam2.setSecondType(0);
                            int sendIconIndex3 = ScreenIconUtils.getSendIconIndex(relateInfoItem.relateInfo.iconIndex);
                            displayParam2.setFirstData(new byte[]{(byte) ((sendIconIndex3 >> 8) & 255), (byte) (sendIconIndex3 & 255)});
                        } else if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                            displayParam2 = new SmartPanelParam.DisplayParam();
                            displayParam2.setFirstType(2);
                            int sendIconIndex4 = ScreenIconUtils.getSendIconIndex(relateInfoItem.relateInfo.iconIndex);
                            displayParam2.setFirstData(new byte[]{(byte) ((sendIconIndex4 >> 8) & 255), (byte) (sendIconIndex4 & 255)});
                            displayParam2.setSecondType(1);
                            displayParam2.setSecondData(relateInfoItem.relateInfo.screenStr.getBytes("gb2312"));
                        }
                        displayParam4 = displayParam2;
                    } catch (Exception e4) {
                        e = e4;
                    }
                }
                arrayList.add(displayParam4);
            } else {
                try {
                } catch (Exception e5) {
                    e = e5;
                }
                if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                    displayParam = new SmartPanelParam.DisplayParam();
                    try {
                        String[] split4 = relateInfoItem.relateInfo.screenStr.split("\n");
                        displayParam.setFirstType(1);
                        displayParam.setFirstData(split4[0].getBytes("gb2312"));
                        if (!isSingleLine() && split4.length > 1) {
                            displayParam.setSecondType(1);
                            displayParam.setSecondData(split4[1].getBytes("gb2312"));
                        }
                    } catch (Exception e6) {
                        e = e6;
                        displayParam4 = displayParam;
                        LHomeLog.e(getClass(), e.toString());
                        arrayList.add(displayParam4);
                        this.relateInfoAssistant.setRelateInfo(i, relateInfoItem.relateInfo);
                    }
                } else if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                    displayParam = new SmartPanelParam.DisplayParam();
                    displayParam.setFirstType(2);
                    displayParam.setSecondType(0);
                    int sendIconIndex5 = ScreenIconUtils.getSendIconIndex(relateInfoItem.relateInfo.iconIndex);
                    displayParam.setFirstData(new byte[]{(byte) ((sendIconIndex5 >> 8) & 255), (byte) (sendIconIndex5 & 255)});
                } else {
                    if (relateInfoItem.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                        displayParam = new SmartPanelParam.DisplayParam();
                        displayParam.setFirstType(2);
                        int sendIconIndex6 = ScreenIconUtils.getSendIconIndex(relateInfoItem.relateInfo.iconIndex);
                        displayParam.setFirstData(new byte[]{(byte) ((sendIconIndex6 >> 8) & 255), (byte) (sendIconIndex6 & 255)});
                        displayParam.setSecondType(1);
                        displayParam.setSecondData(relateInfoItem.relateInfo.screenStr.getBytes("gb2312"));
                    }
                    arrayList.add(displayParam4);
                }
                displayParam4 = displayParam;
                arrayList.add(displayParam4);
            }
            this.relateInfoAssistant.setRelateInfo(i, relateInfoItem.relateInfo);
        }
        ThreadUtils.getMainHandler().postDelayed(new AnonymousClass2(arrayList), 500L);
    }

    /* renamed from: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ List val$displayParamList;

        AnonymousClass2(final List val$displayParamList) {
            this.val$displayParamList = val$displayParamList;
        }

        @Override // java.lang.Runnable
        public void run() {
            ActSmartPanelSwitchPositionSetVM actSmartPanelSwitchPositionSetVM = ActSmartPanelSwitchPositionSetVM.this;
            actSmartPanelSwitchPositionSetVM.showLoadingDialog(actSmartPanelSwitchPositionSetVM.getContext().getString(R.string.saving));
            CmdAssistant.getDeviceAssistant(ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue(), new int[0]).setPanelScreenData(ActivityUtils.getTopActivity(), this.val$displayParamList, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSwitchPositionSetVM.AnonymousClass2.this.lambda$run$0((ResponseMsg) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(ResponseMsg responseMsg) {
            if (responseMsg != null && responseMsg.getStateCode() == 0) {
                Object value = ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue();
                if (value instanceof Device) {
                    ActSmartPanelSwitchPositionSetVM actSmartPanelSwitchPositionSetVM = ActSmartPanelSwitchPositionSetVM.this;
                    actSmartPanelSwitchPositionSetVM.updateParamExt((Device) value, actSmartPanelSwitchPositionSetVM.relateInfoAssistant.getExtParamString());
                    return;
                }
                Object value2 = ActSmartPanelSwitchPositionSetVM.this.controlObject.getValue();
                if (value2 instanceof Group) {
                    ActSmartPanelSwitchPositionSetVM actSmartPanelSwitchPositionSetVM2 = ActSmartPanelSwitchPositionSetVM.this;
                    actSmartPanelSwitchPositionSetVM2.updateParamExt((Group) value2, actSmartPanelSwitchPositionSetVM2.relateInfoAssistant.getExtParamString());
                    return;
                }
                return;
            }
            ActSmartPanelSwitchPositionSetVM.this.dismissLoadingDialog();
            SmartToast.showCenterShort(ActSmartPanelSwitchPositionSetVM.this.getContext().getString(R.string.save_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateParamExt(final Device device, final String extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM.3
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ActSmartPanelSwitchPositionSetVM.this.dismissLoadingDialog();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSwitchPositionSetVM.this.lambda$updateParamExt$0(device, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$0(Device device, String str, Object obj) throws Exception {
        dismissLoadingDialog();
        SmartToast.showCenterShort(getContext().getString(R.string.save_success));
        device.setExtParam(str);
        Injection.repo().device().saveDevice(device);
        this.oldList = this.newPosition;
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateParamExt(final Group group, final String extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM.4
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ActSmartPanelSwitchPositionSetVM.this.dismissLoadingDialog();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSetVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSwitchPositionSetVM.this.lambda$updateParamExt$1(group, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$1(Group group, String str, Object obj) throws Exception {
        dismissLoadingDialog();
        SmartToast.showCenterShort(getContext().getString(R.string.save_success));
        group.setExtParam(str);
        Injection.repo().group().saveGroup(group);
        this.oldList = this.newPosition;
        finishActivity();
    }

    public int getCurScreen() {
        return this.curScreen;
    }

    public void setCurScreen(int curScreen) {
        this.curScreen = curScreen;
    }

    private static String getString(String input, boolean isOpenElderLyMode) {
        int i = isOpenElderLyMode ? 9 : 12;
        if (getEncodeLength(input) <= i) {
            return input;
        }
        if (!input.contains("\n")) {
            for (int i2 = 1; i2 <= input.length(); i2++) {
                if (getEncodeLength(input.substring(0, i2)) > i) {
                    StringBuilder sb = new StringBuilder();
                    int i3 = i2 - 1;
                    sb.append(input.substring(0, i3));
                    sb.append("\n");
                    sb.append(input.substring(i3));
                    return sb.toString();
                }
            }
            return input;
        }
        String[] split = input.split("\n");
        if (split.length <= 1 || getEncodeLength(split[1]) <= i) {
            return input;
        }
        return split[0] + "\n" + StringUtils.getSecondLine(split[1], isOpenElderLyMode);
    }

    private static int getEncodeLength(String input) {
        try {
            return input.getBytes("gbk").length;
        } catch (UnsupportedEncodingException unused) {
            return input.length();
        }
    }

    private boolean isSingleLine() {
        return ProductRepository.getLightColorType(this.controlObject.getValue()) == 19 || ProductRepository.getLightColorType(this.controlObject.getValue()) == 21;
    }
}