package com.ltech.smarthome.ui.scene;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.AutomationParam;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.SceneConstants;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.scene.AddSceneResponse;
import com.ltech.smarthome.ui.scene.local.BaseSceneActionVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActAddCloudSceneVM extends BaseSceneActionVM {
    public long floorId;
    public boolean isEdit;
    public Listing<Automation> request;
    public long roomId;
    public long sceneId;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public Scene scene = new Scene();
    public MediatorLiveData<List<Automation>> automationList = new MediatorLiveData<>();
    public MutableLiveData<String> sceneName = new MutableLiveData<>("");
    public MutableLiveData<Integer> sceneIconPos = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalSceneTime = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showEditDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditRoomDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDelRoomDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isShowEditLayout = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isShowSortLayout = new SingleLiveEvent<>(false);
    public SingleLiveEvent<Void> editActionEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> importEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> sortEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> editSuccessEvent = new SingleLiveEvent<>();
    public MutableLiveData<List<NoActionDevice>> noSetActionDeviceEvent = new MutableLiveData<>();
    public List<NoActionDevice> unSupportDevices = new ArrayList();
    public MutableLiveData<List<NoActionDevice>> showUnSupportDeviceDialogEvent = new MutableLiveData<>();
    public int editPosition = -1;
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda7
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAddCloudSceneVM.this.lambda$new$0((View) obj);
        }
    });

    public static class ContentState {
        public String action;
        public int iconRes;
        public boolean isVirtual;
        public String location;
        public String name;
        public int rgbColor = Integer.MIN_VALUE;
        public String state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.footer_view /* 2131296765 */:
                this.showDelRoomDialogEvent.call();
                break;
            case R.id.iv_import /* 2131297105 */:
                this.importEvent.call();
                break;
            case R.id.layout_edit_action /* 2131297456 */:
                this.editActionEvent.call();
                break;
            case R.id.layout_sort /* 2131297654 */:
                this.sortEvent.call();
                break;
            case R.id.tv_mode_name /* 2131298779 */:
                this.showEditDialogEvent.call();
                break;
            case R.id.tv_room_name /* 2131298926 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.v_change_icon /* 2131299137 */:
                navigation(NavUtils.destination(ActSelectIcon.class).withInt(Constants.ICON_POSITION, this.sceneIconPos.getValue().intValue()).withLong(Constants.SCENE_ID, this.sceneId).withDefaultRequestCode());
                break;
        }
    }

    public int getIconRes(Object object) {
        return ProductRepository.getProductIcon(object);
    }

    public ContentState getContentState(Context context, Scene.SceneContent content) {
        if (MaskType.isDeviceAction(content.getActionType())) {
            ContentState contentState = new ContentState();
            DeviceParam deviceParam = (DeviceParam) content.getExecuteCommand(DeviceParam.class);
            if (deviceParam != null) {
                Device device = getDevice(deviceParam.deviceid);
                contentState.iconRes = getIconRes(device);
                if (device != null) {
                    contentState.name = device.getDeviceName();
                    String deviceActionString = SceneHelper.getDeviceActionString(context, device, deviceParam);
                    if (deviceActionString.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        contentState.rgbColor = Color.rgb(Integer.parseInt(deviceActionString.substring(1, 3), 16), Integer.parseInt(deviceActionString.substring(3, 5), 16), Integer.parseInt(deviceActionString.substring(5, 7), 16));
                        contentState.state = deviceActionString.substring(7);
                    } else {
                        contentState.state = deviceActionString;
                    }
                    contentState.isVirtual = device.isVirtual();
                    if (ProductRepository.isSuperPanel(device.getProductId()) && (deviceParam.option.equals("2") || deviceParam.option.equals("3") || deviceParam.option.equals("4"))) {
                        content.setErrorTip(context.getString(R.string.not_support_cloud_tip));
                    }
                } else {
                    contentState.name = "";
                }
                contentState.location = getLocationNameByDevice(deviceParam.deviceid);
                if (device != null && ((ProductRepository.isEurPanel(device.getProductId()) || ProductRepository.isAsPanel(device.getProductId())) && new RelateInfoAssistant(device).getZoneNumber() > 1)) {
                    contentState.location = getLocationNameByDevice(deviceParam.deviceid) + " " + getZoneName(context, deviceParam.sceneZone, deviceParam.option);
                }
                contentState.action = deviceParam.instruct;
            }
            return contentState;
        }
        if (MaskType.isGroupAction(content.getActionType())) {
            ContentState contentState2 = new ContentState();
            GroupParam groupParam = (GroupParam) content.getExecuteCommand(GroupParam.class);
            if (groupParam != null) {
                Group group = getGroup(groupParam.groupid);
                contentState2.iconRes = getIconRes(group);
                contentState2.name = group != null ? group.getGroupName() : "";
                String groupActionString = SceneHelper.getGroupActionString(context, groupParam.option, groupParam.optionvalue, group, groupParam.instruct, groupParam.sceneZone);
                if (groupActionString.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                    contentState2.rgbColor = Color.rgb(Integer.parseInt(groupActionString.substring(1, 3), 16), Integer.parseInt(groupActionString.substring(3, 5), 16), Integer.parseInt(groupActionString.substring(5, 7), 16));
                    contentState2.state = groupActionString.substring(7);
                } else {
                    contentState2.state = groupActionString;
                }
                contentState2.isVirtual = group != null && group.isVirtual();
                contentState2.location = getLocationNameByGroup(groupParam.groupid);
                if (group != null && ((ProductRepository.isEurPanel(group) || ProductRepository.isAsPanel(group)) && new RelateInfoAssistant(group).getZoneNumber() > 1)) {
                    contentState2.location = getLocationNameByGroup(groupParam.groupid) + " " + getZoneName(context, groupParam.sceneZone, groupParam.option);
                }
                contentState2.action = groupParam.instruct;
            }
            return contentState2;
        }
        if (MaskType.isAutomationAction(content.getActionType())) {
            ContentState contentState3 = new ContentState();
            AutomationParam automationParam = (AutomationParam) content.getExecuteCommand(AutomationParam.class);
            if (automationParam != null) {
                Automation automation = getAutomation(automationParam.automationid);
                contentState3.iconRes = R.mipmap.ic_auto_automation;
                contentState3.name = context.getString(R.string.action_automation_tip);
                if (automation != null) {
                    if (1 == automationParam.enable) {
                        contentState3.state = ActivityUtils.getTopActivity().getString(R.string.enable) + "\"" + automation.getName() + "\"";
                        return contentState3;
                    }
                    contentState3.state = ActivityUtils.getTopActivity().getString(R.string.disable) + "\"" + automation.getName() + "\"";
                }
            }
            return contentState3;
        }
        if (MaskType.isSceneAction(content.getActionType())) {
            ContentState contentState4 = new ContentState();
            SceneParam sceneParam = (SceneParam) content.getExecuteCommand(SceneParam.class);
            if (sceneParam != null) {
                contentState4.iconRes = R.mipmap.ic_auto_dali_scene;
                contentState4.name = context.getString(R.string.action_dali_scene_tip);
                Scene scene = getScene(sceneParam.sceneid);
                if (scene != null) {
                    contentState4.state = context.getString(R.string.app_execute) + "\"" + scene.getSceneName() + "\"";
                    contentState4.location = getLocationNameByScene(scene);
                }
                contentState4.action = sceneParam.instruct;
            }
            return contentState4;
        }
        if (!MaskType.isSonosAction(content.getActionType())) {
            return null;
        }
        ContentState contentState5 = new ContentState();
        DeviceParam deviceParam2 = (DeviceParam) content.getExecuteCommand(DeviceParam.class);
        if (deviceParam2 != null) {
            Device device2 = getDevice(deviceParam2.deviceid);
            contentState5.iconRes = getIconRes(device2);
            if (device2 != null) {
                contentState5.name = device2.getDeviceName();
                contentState5.state = SceneHelper.getDeviceActionString(context, device2, deviceParam2);
            } else {
                contentState5.name = "";
            }
            contentState5.location = getLocationNameByDevice(deviceParam2.deviceid);
            contentState5.action = deviceParam2.option;
        }
        return contentState5;
    }

    public void addScene() {
        ((ObservableSubscribeProxy) Injection.net().addScene(1000, this.placeId, this.sceneName.getValue(), this.actionList, this.sceneIconPos.getValue().intValue(), this.floorId, this.roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudSceneVM.this.lambda$addScene$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddCloudSceneVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudSceneVM.this.lambda$addScene$2((AddSceneResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addScene$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addScene$2(AddSceneResponse addSceneResponse) throws Exception {
        Scene scene = new Scene();
        scene.setSceneId(addSceneResponse.getSceneid());
        scene.setPlaceId(addSceneResponse.getPlaceid());
        scene.setSceneIndex(addSceneResponse.getSceneindex());
        scene.setSceneName(addSceneResponse.getScenename());
        scene.setSceneIndex(addSceneResponse.getSceneindex());
        scene.setSceneType(addSceneResponse.getScenetype());
        scene.setIconPos(addSceneResponse.getImgindex());
        scene.setFloorId(addSceneResponse.getFloorid());
        scene.setRoomId(addSceneResponse.getRoomid());
        LHomeLog.i(getClass(), "场景添加后返回数据  = " + GsonUtils.getGson().toJson(scene));
        Injection.repo().scene().saveScene(scene);
        SmartToast.showShort(R.string.save_success);
        this.editSuccessEvent.call();
    }

    public void editScene() {
        ((ObservableSubscribeProxy) Injection.net().updateScene(this.sceneId, this.sceneName.getValue(), this.actionList, this.sceneIconPos.getValue().intValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudSceneVM.this.lambda$editScene$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddCloudSceneVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudSceneVM.this.lambda$editScene$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editScene$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editScene$4(Object obj) throws Exception {
        Injection.repo().scene().updateSceneName(this.sceneId, this.sceneName.getValue());
        Injection.repo().scene().updateSceneIconPosition(this.sceneId, this.sceneIconPos.getValue().intValue());
        Injection.repo().scene().updateSceneContent(this.sceneId, this.actionList);
        SmartToast.showShort(R.string.save_success);
        this.editSuccessEvent.call();
    }

    public void delScene(final long sceneId) {
        ((ObservableSubscribeProxy) Injection.net().deleteScene(sceneId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudSceneVM.this.lambda$delScene$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddCloudSceneVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActAddCloudSceneVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddCloudSceneVM.this.lambda$delScene$6(sceneId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delScene$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delScene$6(long j, Object obj) throws Exception {
        Injection.repo().scene().removeScene(j);
        finishActivity();
    }

    public boolean hasAction() {
        if (this.actionList == null || this.actionList.size() == 0) {
            return false;
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        HashMap hashMap2 = new HashMap();
        this.unSupportDevices.clear();
        for (Scene.SceneContent sceneContent : this.actionList) {
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                if (deviceParam == null) {
                    return false;
                }
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid);
                String str = deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
                if ((deviceParam.option == null || !deviceParam.option.equals(SceneConstants.OPTION_CURRENT_STATE)) && (deviceParam.instruct == null || TextUtils.isEmpty(deviceParam.instruct))) {
                    if (hashMap.containsKey(str)) {
                        hashMap.put(str, ((String) hashMap.get(str)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + deviceByDeviceId.getDeviceName());
                    } else {
                        hashMap.put(str, deviceByDeviceId.getDeviceName());
                    }
                } else if (ProductRepository.isSuperPanel(deviceByDeviceId.getProductId()) && (deviceParam.option.equals("2") || deviceParam.option.equals("3") || deviceParam.option.equals("4"))) {
                    if (hashMap2.containsKey(str)) {
                        hashMap2.put(str, ((String) hashMap.get(str)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + deviceByDeviceId.getDeviceName());
                    } else {
                        hashMap2.put(str, deviceByDeviceId.getDeviceName());
                    }
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
                if (groupParam == null) {
                    return false;
                }
                if (groupParam.option == null || !groupParam.option.equals(SceneConstants.OPTION_CURRENT_STATE)) {
                    if (groupParam.instruct == null || TextUtils.isEmpty(groupParam.instruct)) {
                        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid);
                        String str2 = groupByGroupId.getFloorName() + groupByGroupId.getRoomName();
                        if (hashMap.containsKey(str2)) {
                            hashMap.put(str2, ((String) hashMap.get(str2)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + groupByGroupId.getGroupName());
                        } else {
                            hashMap.put(str2, groupByGroupId.getGroupName());
                        }
                    }
                }
            } else {
                continue;
            }
        }
        if (hashMap.size() > 0) {
            for (Map.Entry entry : hashMap.entrySet()) {
                arrayList.add(new NoActionDevice((String) entry.getKey(), (String) entry.getValue()));
            }
            this.noSetActionDeviceEvent.postValue(arrayList);
            return false;
        }
        if (hashMap2.size() <= 0) {
            return true;
        }
        for (Map.Entry entry2 : hashMap2.entrySet()) {
            this.unSupportDevices.add(new NoActionDevice((String) entry2.getKey(), (String) entry2.getValue()));
        }
        this.showUnSupportDeviceDialogEvent.postValue(this.unSupportDevices);
        return false;
    }

    static class NoActionDevice {
        private String name;
        private String room;

        public NoActionDevice(String room, String name) {
            this.name = name;
            this.room = room;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoom() {
            return this.room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }
}