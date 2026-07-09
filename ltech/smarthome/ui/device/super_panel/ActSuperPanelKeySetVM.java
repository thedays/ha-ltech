package com.ltech.smarthome.ui.device.super_panel;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import com.blankj.utilcode.util.ActivityUtils;
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
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSuperPanelKeySetVM extends BaseViewModel {
    public long deviceId;
    public long placeId;
    public String productId;
    public Listing<Device> request;
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public MediatorLiveData<List<Scene>> sceneList = new MediatorLiveData<>();
    public List<SuperPanelInfo.PanelKeyInfo> panelInfoList = new ArrayList(8);
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    public Device getDevice(long deviceId) {
        if (this.deviceList.getValue() == null) {
            return null;
        }
        for (Device device : this.deviceList.getValue()) {
            if (deviceId == device.getDeviceId()) {
                return device;
            }
        }
        return null;
    }

    public Group getGroup(long groupId) {
        if (this.groupList.getValue() == null) {
            return null;
        }
        for (Group group : this.groupList.getValue()) {
            if (groupId == group.getGroupId()) {
                return group;
            }
        }
        return null;
    }

    public Scene getScene(long sceneId) {
        if (this.sceneList.getValue() == null) {
            return null;
        }
        for (Scene scene : this.sceneList.getValue()) {
            if (sceneId == scene.getSceneId()) {
                return scene;
            }
        }
        return null;
    }

    public String getKeyName(SuperPanelInfo.PanelKeyInfo keyInfo) {
        Scene scene;
        Group group;
        Device device;
        if (MaskType.isDeviceAction(keyInfo.getActionType())) {
            DeviceParam deviceParam = (DeviceParam) keyInfo.getExecutecommand(DeviceParam.class);
            return (deviceParam == null || (device = getDevice(deviceParam.deviceid)) == null) ? "" : device.getDeviceName();
        }
        if (MaskType.isGroupAction(keyInfo.getActionType())) {
            GroupParam groupParam = (GroupParam) keyInfo.getExecutecommand(GroupParam.class);
            return (groupParam == null || (group = getGroup(groupParam.groupid)) == null) ? "" : group.getGroupName();
        }
        if (MaskType.isSceneAction(keyInfo.getActionType())) {
            SceneParam sceneParam = (SceneParam) keyInfo.getExecutecommand(SceneParam.class);
            return (sceneParam == null || (scene = getScene(sceneParam.sceneid)) == null) ? "" : scene.getSceneName();
        }
        if (MaskType.isVoiceCallAction(keyInfo.getActionType())) {
            return keyInfo.getKeywordsname();
        }
        return "";
    }

    public void showAddDialog(final Context context, final int position) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(context.getString(R.string.relate_device));
        arrayList.add(context.getString(R.string.relate_scene));
        arrayList.add(context.getString(R.string.relate_dali_scene));
        if (!ProductId.ID_ANDROID_SUPER_PANEL.equals(this.productId)) {
            arrayList.add(context.getString(R.string.voice_call_str));
        }
        arrayList.add(context.getString(R.string.reset_relate));
        SelectListDialog.asDefault(false).setTitle(context.getString(R.string.please_choose)).setCancelString(context.getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySetVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelKeySetVM.this.lambda$showAddDialog$0(position, context, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddDialog$0(int i, Context context, Integer num) {
        SceneHelper.instance().reset();
        SceneHelper.instance().bindingType = 2;
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        SceneHelper.instance().initFloorRoom(deviceByDeviceId.getFloorId(), deviceByDeviceId.getRoomId());
        int intValue = num.intValue();
        if (intValue == 0) {
            SceneHelper.instance().panelid = this.deviceId;
            SceneHelper.instance().keyIndex = i + 1;
            SceneHelper.instance().panelKeyInfo = this.panelInfoList.get(SceneHelper.instance().keyIndex - 1);
            SceneHelper.goSelectAction(ActivityUtils.getTopActivity(), 7, this.placeId);
            return;
        }
        if (intValue == 1) {
            SceneHelper.instance().panelid = this.deviceId;
            SceneHelper.instance().keyIndex = i + 1;
            SceneHelper.goSelectAction(ActivityUtils.getTopActivity(), 4, this.placeId);
            return;
        }
        if (intValue == 2) {
            SceneHelper.instance().panelid = this.deviceId;
            SceneHelper.instance().keyIndex = i + 1;
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
            return;
        }
        if (intValue != 3) {
            if (intValue != 4) {
                return;
            }
            showUnbindDialog(context, i);
        } else {
            if (!ProductId.ID_ANDROID_SUPER_PANEL.equals(this.productId)) {
                SceneHelper.instance().panelid = this.deviceId;
                SceneHelper.instance().keyIndex = i + 1;
                VoiceCallManager.getInstance().showBindListView((FragmentActivity) ActivityUtils.getTopActivity(), this.deviceId, 100);
                return;
            }
            showUnbindDialog(context, i);
        }
    }

    private void showUnbindDialog(final Context context, final int position) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), context.getString(R.string.tips), context.getString(R.string.app_str_cancel_binding_tip)).setCancelable(false).setCancelButton(context.getString(R.string.cancel)).setOkButton(context.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySetVM$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUnbindDialog$3;
                lambda$showUnbindDialog$3 = ActSuperPanelKeySetVM.this.lambda$showUnbindDialog$3(position, context, baseDialog, view);
                return lambda$showUnbindDialog$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUnbindDialog$3(int i, final Context context, BaseDialog baseDialog, View view) {
        SceneHelper.instance().reset();
        SceneHelper.instance().panelid = this.deviceId;
        SceneHelper.instance().keyIndex = i + 1;
        final SuperPanelInfo.PanelKeyInfo convert2SuperPanelKeyInfo = convert2SuperPanelKeyInfo();
        ((ObservableSubscribeProxy) Injection.net().unBindSuperPanelKeywordsInfo(SceneHelper.instance().panelid, convert2SuperPanelKeyInfo).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySetVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelKeySetVM.this.lambda$showUnbindDialog$1(context, (Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySetVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSuperPanelKeySetVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySetVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelKeySetVM.lambda$showUnbindDialog$2(SuperPanelInfo.PanelKeyInfo.this, obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$1(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.saving));
    }

    static /* synthetic */ void lambda$showUnbindDialog$2(SuperPanelInfo.PanelKeyInfo panelKeyInfo, Object obj) throws Exception {
        Injection.repo().device().setSuperPanelKeyInfo(SceneHelper.instance().panelid, panelKeyInfo);
        SmartToast.showShort(R.string.save_success);
    }

    public SuperPanelInfo.PanelKeyInfo convert2SuperPanelKeyInfo() {
        SuperPanelInfo.PanelKeyInfo panelKeyInfo = new SuperPanelInfo.PanelKeyInfo();
        panelKeyInfo.setKeywords(SceneHelper.instance().keyIndex);
        panelKeyInfo.setKeywordsname("0");
        return panelKeyInfo;
    }

    public String getFunString(Context context, SuperPanelInfo.PanelKeyInfo keyInfo) {
        Group group;
        Device device;
        if (MaskType.isDeviceAction(keyInfo.getActionType())) {
            DeviceParam deviceParam = (DeviceParam) keyInfo.getExecutecommand(DeviceParam.class);
            if (deviceParam == null || (device = getDevice(deviceParam.deviceid)) == null) {
                return "";
            }
            SceneHelper.instance().bindingType = 2;
            String format = String.format("%s", SceneHelper.getDeviceActionString(context, device, deviceParam));
            SceneHelper.instance().bindingType = 0;
            return format;
        }
        if (MaskType.isGroupAction(keyInfo.getActionType())) {
            GroupParam groupParam = (GroupParam) keyInfo.getExecutecommand(GroupParam.class);
            if (groupParam == null || (group = getGroup(groupParam.groupid)) == null) {
                return "";
            }
            SceneHelper.instance().bindingType = 2;
            String format2 = String.format("%s", SceneHelper.getGroupActionString(context, groupParam.option, groupParam.optionvalue, group, "", groupParam.sceneZone));
            SceneHelper.instance().bindingType = 0;
            return format2;
        }
        if (MaskType.isSceneAction(keyInfo.getActionType())) {
            SceneParam sceneParam = (SceneParam) keyInfo.getExecutecommand(SceneParam.class);
            if (sceneParam.scenetype == 2) {
                return context.getString(R.string.local_scene);
            }
            if (sceneParam.scenetype == 4) {
                return context.getString(R.string.dali_scene);
            }
            return context.getString(R.string.cloud_scene);
        }
        if (MaskType.isVoiceCallAction(keyInfo.getActionType())) {
            return context.getString(R.string.voice_call_str);
        }
        return "";
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}