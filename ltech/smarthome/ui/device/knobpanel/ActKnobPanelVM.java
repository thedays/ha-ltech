package com.ltech.smarthome.ui.device.knobpanel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActKnobPanelVM extends BaseViewModel {
    public static final int ACTION_NUM = 4;
    public Device controlDevice;
    public long controlId;
    public long placeId;
    public RelatedInfoExtParam.RelateInfo relateInfo;
    public RelateInfoAssistant relateInfoAssistant;
    public List<RelateInfoItem> relatedInfoList;
    public static final int[] ICON_ARRAY_MUSIC = {R.mipmap.pic_sq_click, R.mipmap.pic_sq_double_click_music, R.mipmap.pic_sq_whirl, R.mipmap.pic_sq_press_and_hold_music};
    public static final int[] ACTION_ARRAY_MUSIC = {R.string.single_click, R.string.double_click, R.string.rotate, R.string.press_and_rotate};
    public static final int[] ICON_ARRAY = {R.mipmap.pic_sq_click, R.mipmap.pic_sq_double_click, R.mipmap.pic_sq_whirl, R.mipmap.pic_sq_press_and_hold};
    public static final int[] ACTION_ARRAY = {R.string.single_click, R.string.double_click, R.string.rotate, R.string.long_press};
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public List<ActionItem> actionItemList = new ArrayList();

    public static class ActionItem {
        public String actionInfo;
        public String actionName;
        public int iconRes;
    }

    public void initRelateInfoList(Device device) {
        RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant(device);
        this.relateInfoAssistant = relateInfoAssistant;
        RelatedInfoExtParam.RelateInfo relateInfo = relateInfoAssistant.getRelateInfo(0);
        this.relateInfo = relateInfo;
        if (relateInfo == null) {
            this.relateInfo = new RelatedInfoExtParam.RelateInfo();
        }
        this.actionItemList.clear();
        for (int i = 0; i < 4; i++) {
            ActionItem actionItem = new ActionItem();
            if (this.relateInfo.objectId != 0) {
                actionItem.actionInfo = getRelateActionNameRes(ActivityUtils.getTopActivity(), this.relateInfo)[i];
                actionItem.iconRes = getRelateIconRes(this.relateInfo)[i];
                actionItem.actionName = ActivityUtils.getTopActivity().getString(getRelateActionName(this.relateInfo)[i]);
            } else {
                actionItem.iconRes = ICON_ARRAY[i];
                actionItem.actionName = ActivityUtils.getTopActivity().getString(ACTION_ARRAY[i]);
            }
            this.actionItemList.add(actionItem);
        }
    }

    private int[] getRelateActionName(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        if (device.getProductId().equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                            return ACTION_ARRAY_MUSIC;
                        }
                        return ACTION_ARRAY;
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo() && this.groupList.getValue() != null) {
            Iterator<Group> it = this.groupList.getValue().iterator();
            while (it.hasNext()) {
                if (it.next().getGroupId() == relateInfo.objectId) {
                    return ACTION_ARRAY;
                }
            }
        }
        return ACTION_ARRAY;
    }

    private int[] getRelateIconRes(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        if (device.getProductId().equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                            return ICON_ARRAY_MUSIC;
                        }
                        return ICON_ARRAY;
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo() && this.groupList.getValue() != null) {
            Iterator<Group> it = this.groupList.getValue().iterator();
            while (it.hasNext()) {
                if (it.next().getGroupId() == relateInfo.objectId) {
                    return ICON_ARRAY;
                }
            }
        }
        return ICON_ARRAY;
    }

    public void initScreenRelateInfoList(Object object) {
        RelateInfoUtils.initRelateInfoList(object);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        if (this.relatedInfoList == null) {
            this.relatedInfoList = new ArrayList();
        }
        this.relatedInfoList.clear();
        this.relatedInfoList.addAll(RelateInfoUtils.relatedInfoList);
    }

    public String getRelateInfoString(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo != null && relateInfo.objectId != 0) {
            if (relateInfo.isRelateDeviceInfo()) {
                if (this.deviceList.getValue() != null) {
                    for (Device device : this.deviceList.getValue()) {
                        if (device.getDeviceId() == relateInfo.objectId) {
                            return device.getDeviceName();
                        }
                    }
                }
            } else if (relateInfo.isRelateGroupInfo()) {
                if (this.groupList.getValue() != null) {
                    for (Group group : this.groupList.getValue()) {
                        if (group.getGroupId() == relateInfo.objectId) {
                            return group.getGroupName();
                        }
                    }
                }
            } else if (relateInfo.isRelateSceneInfo()) {
                for (Scene scene : Injection.repo().scene().getSceneListByPlaceId(this.placeId, true)) {
                    if (scene.getSceneId() == relateInfo.objectId) {
                        return scene.getSceneName();
                    }
                }
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c9, code lost:
    
        switch(r17) {
            case 0: goto L128;
            case 1: goto L128;
            case 2: goto L128;
            case 3: goto L128;
            case 4: goto L128;
            case 5: goto L127;
            case 6: goto L126;
            case 7: goto L125;
            case 8: goto L127;
            default: goto L131;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d6, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.dry_curtain_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e2, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_musicplayer_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00eb, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_curtain_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ec, code lost:
    
        r3 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType((java.lang.Object) r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f4, code lost:
    
        if (com.ltech.smarthome.model.repo.ProductRepository.isDaliLightGroup(r16) == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00f6, code lost:
    
        r3 = r22.keyActionExtra - 100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00fa, code lost:
    
        if (r3 == 1) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00fc, code lost:
    
        if (r3 == 2) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00fe, code lost:
    
        if (r3 == 3) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0100, code lost:
    
        if (r3 == 4) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0102, code lost:
    
        if (r3 == 5) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x010c, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_ct_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0115, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_rgbwy_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011e, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_rgbw_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x012a, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_rgb_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0133, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_ct_action_array);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x013f, code lost:
    
        return r21.getResources().getStringArray(com.ltech.smarthome.R.array.sq_dim_action_array);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] getRelateActionNameRes(android.content.Context r21, com.ltech.smarthome.model.device_param.RelatedInfoExtParam.RelateInfo r22) {
        /*
            Method dump skipped, instructions count: 610
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM.getRelateActionNameRes(android.content.Context, com.ltech.smarthome.model.device_param.RelatedInfoExtParam$RelateInfo):java.lang.String[]");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c9, code lost:
    
        switch(r17) {
            case 0: goto L127;
            case 1: goto L127;
            case 2: goto L127;
            case 3: goto L127;
            case 4: goto L127;
            case 5: goto L126;
            case 6: goto L125;
            case 7: goto L124;
            case 8: goto L126;
            default: goto L130;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d2, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.dry_curtain_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00da, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.musicplayer_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00df, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.curtain_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e0, code lost:
    
        r3 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType((java.lang.Object) r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e8, code lost:
    
        if (com.ltech.smarthome.model.repo.ProductRepository.isDaliLightGroup(r16) == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ea, code lost:
    
        r3 = r22.keyActionExtra - 100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ee, code lost:
    
        if (r3 == 1) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00f0, code lost:
    
        if (r3 == 2) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00f2, code lost:
    
        if (r3 == 3) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f4, code lost:
    
        if (r3 == 4) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f6, code lost:
    
        if (r3 == 5) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00fc, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.ct_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0101, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.rgbwy_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0106, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.rgbw_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x010e, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.rgb_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0113, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.ct_tip);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x011b, code lost:
    
        return r21.getString(com.ltech.smarthome.R.string.dim_tip);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getInstructionName(android.content.Context r21, com.ltech.smarthome.model.device_param.RelatedInfoExtParam.RelateInfo r22) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM.getInstructionName(android.content.Context, com.ltech.smarthome.model.device_param.RelatedInfoExtParam$RelateInfo):java.lang.String");
    }

    public void unBindRelateInfo(final int position) {
        int i;
        int i2;
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
            int unicastAddress = ((BleParam) this.controlObject.getValue().getParam(BleParam.class)).getUnicastAddress();
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
                    }
                }
            } else if (relateInfo.isRelateDeviceInfo()) {
                i2 = 0;
                for (Device device : Injection.repo().device().getDeviceListByPlaceId(this.placeId)) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        i2 = ProductRepository.getAgreementIdByPid(device.getProductId());
                    }
                }
            } else {
                i = 2;
                CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), unicastAddress, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActKnobPanelVM.this.lambda$unBindRelateInfo$0(position, (Boolean) obj);
                    }
                });
                return;
            }
            i = i2;
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), unicastAddress, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKnobPanelVM.this.lambda$unBindRelateInfo$0(position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetSmartPanelRelateInfo(i);
            uploadData();
        } else {
            dismissLoadingDialog();
            ActivityUtils.getTopActivity().runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM.1
                @Override // java.lang.Runnable
                public void run() {
                    ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
                }
            });
        }
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlObject.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKnobPanelVM.this.lambda$uploadData$1((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActKnobPanelVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKnobPanelVM.this.lambda$uploadData$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$2(Object obj) throws Exception {
        this.controlObject.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.controlObject.getValue());
        MutableLiveData<Device> mutableLiveData = this.controlObject;
        mutableLiveData.setValue(mutableLiveData.getValue());
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }
}