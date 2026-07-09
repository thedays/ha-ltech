package com.ltech.smarthome.ui.device.remote_controller;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActKeySwitchVM extends BaseViewModel {
    public Device controlDevice;
    public long controlId;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public long placeId;
    public String productId;
    public RelateInfoAssistant relateInfoAssistant;
    public List<RelateInfoItem> relatedInfoList;
    public Listing<Group> request;

    public void initRelateInfoList(Device device) {
        this.relateInfoAssistant = new RelateInfoAssistant(device);
        int i = 0;
        if (this.relatedInfoList == null) {
            this.relatedInfoList = new ArrayList();
            int zoneCount = ProductRepository.getZoneCount(device.getProductId());
            while (i < zoneCount) {
                RelateInfoItem relateInfoItem = new RelateInfoItem();
                relateInfoItem.relateInfo = this.relateInfoAssistant.getRelateInfo(i);
                relateInfoItem.index = i;
                relateInfoItem.infoName = getRelateInfoString(relateInfoItem.relateInfo);
                relateInfoItem.iconRes = getRelateInfoIcon(relateInfoItem.relateInfo);
                relateInfoItem.actionInfo = getRelateInfo(relateInfoItem.relateInfo);
                this.relatedInfoList.add(relateInfoItem);
                i++;
            }
            return;
        }
        int zoneCount2 = ProductRepository.getZoneCount(device.getProductId());
        while (i < zoneCount2) {
            this.relatedInfoList.get(i).relateInfo = this.relateInfoAssistant.getRelateInfo(i);
            this.relatedInfoList.get(i).index = i;
            this.relatedInfoList.get(i).infoName = getRelateInfoString(this.relateInfoAssistant.getRelateInfo(i));
            this.relatedInfoList.get(i).iconRes = getRelateInfoIcon(this.relateInfoAssistant.getRelateInfo(i));
            this.relatedInfoList.get(i).actionInfo = getRelateInfo(this.relateInfoAssistant.getRelateInfo(i));
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

    public int getRelateInfoIcon(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return R.mipmap.ic_device_none;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            if (this.deviceList.getValue() != null) {
                for (Device device : this.deviceList.getValue()) {
                    if (device.getDeviceId() == relateInfo.objectId) {
                        return ProductRepository.getProductIcon(device);
                    }
                }
            }
        } else if (relateInfo.isRelateGroupInfo() && this.groupList.getValue() != null) {
            for (Group group : this.groupList.getValue()) {
                if (group.getGroupId() == relateInfo.objectId) {
                    return ProductRepository.getProductIcon(group);
                }
            }
        }
        return R.mipmap.ic_device_none;
    }

    /* JADX WARN: Code restructure failed: missing block: B:219:0x0374, code lost:
    
        if (r1 != 8) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0191, code lost:
    
        if (r1 != 8) goto L109;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getRelateInfo(com.ltech.smarthome.model.device_param.RelatedInfoExtParam.RelateInfo r26) {
        /*
            Method dump skipped, instructions count: 1090
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM.getRelateInfo(com.ltech.smarthome.model.device_param.RelatedInfoExtParam$RelateInfo):java.lang.String");
    }

    public void unBindRelateInfo(final int position) {
        int i;
        int i2;
        if (Injection.state().isConnectOuterNet()) {
            int publicationAddress = ((BleParam) this.controlObject.getValue().getParam(BleParam.class)).getPublicationAddress();
            RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(position);
            if (relateInfo == null) {
                uploadData();
                return;
            }
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
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribe(ActivityUtils.getTopActivity(), i, publicationAddress, i2, 1 << position, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKeySwitchVM.this.lambda$unBindRelateInfo$0(position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetRelateInfo(i);
            uploadData();
        } else {
            dismissLoadingDialog();
            ActivityUtils.getTopActivity().runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM.1
                @Override // java.lang.Runnable
                public void run() {
                    ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
                }
            });
        }
    }

    private void uploadData() {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlObject.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKeySwitchVM.this.lambda$uploadData$1((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActKeySwitchVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActKeySwitchVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKeySwitchVM.this.lambda$uploadData$2(obj);
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