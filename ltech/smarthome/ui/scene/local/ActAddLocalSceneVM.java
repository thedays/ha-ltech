package com.ltech.smarthome.ui.scene.local;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.GradientSceneAction;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.LocalSceneParam;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.AutomationParam;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.LocalDaliSceneParam;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.model.scene_param.LocalGroupParam;
import com.ltech.smarthome.model.scene_param.SceneConstants;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.scene.AddSceneResponse;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.scene.ActSelectIcon;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.dialog.v3.WaitDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActAddLocalSceneVM extends BaseSceneActionVM {
    private List<Long> delDeviceIds;
    public long floorId;
    public boolean isEdit;
    private int mSaveNum;
    private Map<Long, Integer> receiveDeviceMap;
    public Listing<Scene> request;
    public long roomId;
    private ArrayList<Long> saveList;
    private Map<Long, List<LocalSceneParam>> saveMap;
    public long sceneId;
    public int sceneNum;
    private int totalSaveAction;
    public List<Long> lastSceneDeviceIds = new ArrayList();
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public Scene scene = new Scene();
    public MutableLiveData<String> sceneName = new MutableLiveData<>("");
    public MutableLiveData<Integer> sceneIconPos = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalSceneTime = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showEditDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditRoomDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDelRoomDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isShowEditLayout = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isShowSortLayout = new SingleLiveEvent<>(false);
    public SingleLiveEvent<String> showErrorDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<List<NoActionDevice>> showDeviceFailDialogEvent = new MutableLiveData<>();
    public MutableLiveData<List<NoActionDevice>> showDeviceDeleteFailDialogEvent = new MutableLiveData<>();
    public MutableLiveData<List<NoActionDevice>> showAutoDeviceFailDialogEvent = new MutableLiveData<>();
    public MutableLiveData<List<NoActionDevice>> showSameActionDialogEvent = new SingleLiveEvent();
    public SingleLiveEvent<Void> editActionEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> importEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> sortEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> editSuccessEvent = new SingleLiveEvent<>();
    public MutableLiveData<List<NoActionDevice>> noSetActionDeviceEvent = new MutableLiveData<>();
    public SingleLiveEvent<Void> refreshErrorEvent = new SingleLiveEvent<>();
    public boolean refreshErrorTip = false;
    public Map<Long, Map<Integer, String>> sameActionMap = new LinkedHashMap();
    public List<SameDevice> errorDeviceList = new ArrayList();
    public int editPosition = -1;
    private boolean isDel = false;
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAddLocalSceneVM.this.lambda$new$0((View) obj);
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

    public ContentState getContentState(Context context, Scene.SceneContent content, int index) {
        Group group;
        GroupParam groupParam;
        ContentState contentState;
        String groupActionString;
        boolean z;
        BaseIrParam baseIrParam;
        if (MaskType.isDeviceAction(content.getActionType())) {
            ContentState contentState2 = new ContentState();
            DeviceParam deviceParam = (DeviceParam) content.getExecuteCommand(DeviceParam.class);
            if (deviceParam != null) {
                Device device = getDevice(deviceParam.deviceid);
                contentState2.iconRes = getIconRes(device);
                if (device != null) {
                    contentState2.name = device.getDeviceName();
                    String deviceActionString = SceneHelper.getDeviceActionString(context, device, deviceParam);
                    if (!TextUtils.isEmpty(deviceActionString) && deviceActionString.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        contentState2.rgbColor = Color.rgb(Integer.parseInt(deviceActionString.substring(1, 3), 16), Integer.parseInt(deviceActionString.substring(3, 5), 16), Integer.parseInt(deviceActionString.substring(5, 7), 16));
                        contentState2.state = deviceActionString.substring(7);
                    } else {
                        contentState2.state = deviceActionString;
                    }
                    z = device.isSubDevice() && (baseIrParam = (BaseIrParam) device.getParam(BaseIrParam.class)) != null && baseIrParam.getUnicastAddress() == 0;
                    contentState2.isVirtual = device.isVirtual();
                } else {
                    contentState2.name = "";
                    z = false;
                }
                contentState2.location = getLocationNameByDevice(deviceParam.deviceid);
                if (device != null && ((ProductRepository.isEurPanel(device.getProductId()) || ProductRepository.isAsPanel(device.getProductId())) && new RelateInfoAssistant(device).getZoneNumber() > 1)) {
                    contentState2.location = getLocationNameByDevice(deviceParam.deviceid) + " " + getZoneName(context, deviceParam.sceneZone, deviceParam.option);
                }
                contentState2.action = deviceParam.instruct;
                if (this.refreshErrorTip) {
                    if (z) {
                        content.setErrorTip(context.getString(R.string.not_support_local_tip));
                        return contentState2;
                    }
                    if (!SceneConstants.OPTION_CURRENT_STATE.equals(deviceParam.option) && TextUtils.isEmpty(contentState2.action)) {
                        content.setErrorTip(context.getString(R.string.app_str_scene_add_action));
                        return contentState2;
                    }
                    content.setErrorTip(getErrorTip(context, index, device != null && device.isSubDevice()));
                }
            }
            return contentState2;
        }
        if (MaskType.isGroupAction(content.getActionType())) {
            ContentState contentState3 = new ContentState();
            GroupParam groupParam2 = (GroupParam) content.getExecuteCommand(GroupParam.class);
            if (groupParam2 == null) {
                return contentState3;
            }
            Group group2 = getGroup(groupParam2.groupid);
            contentState3.iconRes = getIconRes(group2);
            contentState3.name = group2 != null ? group2.getGroupName() : "";
            if (isLightGroup(group2) && "13".equals(groupParam2.option)) {
                contentState = contentState3;
                groupParam = groupParam2;
                groupActionString = ((LocalGroupParam) content.getExecuteCommand(LocalGroupParam.class)).gsName;
                group = group2;
            } else {
                group = group2;
                groupParam = groupParam2;
                contentState = contentState3;
                groupActionString = SceneHelper.getGroupActionString(context, groupParam2.option, groupParam2.optionvalue, group, groupParam2.instruct, groupParam2.sceneZone);
            }
            if (!TextUtils.isEmpty(groupActionString) && groupActionString.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                contentState.rgbColor = Color.rgb(Integer.parseInt(groupActionString.substring(1, 3), 16), Integer.parseInt(groupActionString.substring(3, 5), 16), Integer.parseInt(groupActionString.substring(5, 7), 16));
                contentState.state = groupActionString.substring(7);
            } else {
                contentState.state = groupActionString;
            }
            contentState.isVirtual = group != null && group.isVirtual();
            GroupParam groupParam3 = groupParam;
            contentState.location = getLocationNameByGroup(groupParam3.groupid);
            if (group != null && ((ProductRepository.isEurPanel(group) || ProductRepository.isAsPanel(group)) && new RelateInfoAssistant(group).getZoneNumber() > 1)) {
                contentState.location = getLocationNameByGroup(groupParam3.groupid) + " " + getZoneName(context, groupParam3.sceneZone, groupParam3.option);
            }
            contentState.action = groupParam3.instruct;
            if (!this.refreshErrorTip) {
                return contentState;
            }
            if (isLightGroup(group) && "13".equals(groupParam3.option)) {
                return contentState;
            }
            if (!SceneConstants.OPTION_CURRENT_STATE.equals(groupParam3.option) && TextUtils.isEmpty(contentState.action)) {
                content.setErrorTip(context.getString(R.string.app_str_scene_add_action));
                return contentState;
            }
            content.setErrorTip(getErrorTip(context, index, false));
            return contentState;
        }
        if (MaskType.isAutomationAction(content.getActionType())) {
            ContentState contentState4 = new ContentState();
            AutomationParam automationParam = (AutomationParam) content.getExecuteCommand(AutomationParam.class);
            if (automationParam != null) {
                Automation automation = getAutomation(automationParam.automationid);
                contentState4.iconRes = R.mipmap.ic_auto_automation;
                contentState4.name = context.getString(R.string.action_automation_tip);
                if (automation != null) {
                    if (1 == automationParam.enable) {
                        contentState4.state = context.getString(R.string.enable) + "\"" + automation.getName() + "\"";
                    } else {
                        contentState4.state = context.getString(R.string.disable) + "\"" + automation.getName() + "\"";
                    }
                }
                content.setErrorTip(context.getString(R.string.not_support_local_tip));
            }
            return contentState4;
        }
        if (!MaskType.isSceneAction(content.getActionType())) {
            return null;
        }
        ContentState contentState5 = new ContentState();
        SceneParam sceneParam = (SceneParam) content.getExecuteCommand(SceneParam.class);
        if (sceneParam != null) {
            contentState5.iconRes = R.mipmap.ic_auto_dali_scene;
            contentState5.name = context.getString(R.string.action_dali_scene_tip);
            Scene scene = getScene(sceneParam.sceneid);
            if (scene != null) {
                contentState5.state = context.getString(R.string.app_execute) + "\"" + scene.getSceneName() + "\"";
                contentState5.location = getLocationNameByScene(scene);
            }
        }
        return contentState5;
    }

    private String getErrorTip(Context context, int index, boolean isSubDevice) {
        if (!this.sameActionMap.isEmpty()) {
            for (Map.Entry<Long, Map<Integer, String>> entry : this.sameActionMap.entrySet()) {
                Map<Integer, String> value = entry.getValue();
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(entry.getKey().longValue());
                if (value.size() > 1 && !RelaySeparationHelper.isRelaySeparationDevice(deviceByDeviceId)) {
                    Iterator<Integer> it = value.keySet().iterator();
                    while (it.hasNext()) {
                        if (it.next().intValue() == index) {
                            return context.getString(R.string.local_scene_error_same_time);
                        }
                    }
                }
            }
        }
        for (SameDevice sameDevice : this.errorDeviceList) {
            Map<Long, List<LocalSceneParam>> map = this.saveMap;
            if (map != null && map.containsKey(Long.valueOf(sameDevice.deviceId))) {
                Iterator<LocalSceneParam> it2 = this.saveMap.get(Long.valueOf(sameDevice.deviceId)).iterator();
                while (it2.hasNext()) {
                    if (it2.next().getIndex() == index) {
                        if (sameDevice.errorCode == -1) {
                            if (isSubDevice) {
                                return context.getString(R.string.gateway_offline_error);
                            }
                            return context.getString(R.string.offline);
                        }
                        if (sameDevice.errorCode == 153) {
                            return context.getString(R.string.local_scene_error_99);
                        }
                        if (sameDevice.errorCode == 11) {
                            return context.getString(R.string.local_scene_error_0B);
                        }
                        if (sameDevice.errorCode == 12) {
                            return context.getString(R.string.local_scene_error_0C);
                        }
                        if (sameDevice.errorCode == 13) {
                            return context.getString(R.string.local_scene_error_0D);
                        }
                        if (sameDevice.errorCode == 14) {
                            return context.getString(R.string.local_scene_error_0E);
                        }
                        if (sameDevice.errorCode == 15) {
                            return context.getString(R.string.local_scene_error_0F);
                        }
                        if (sameDevice.errorCode == 3) {
                            return context.getString(R.string.local_scene_error_03);
                        }
                    }
                }
            }
        }
        return "";
    }

    public Automation getCurActionAutomation(AutomationParam automationParam) {
        Automation automation = getAutomation(automationParam.automationid);
        if (automation == null) {
            return null;
        }
        automation.setEnable(automationParam.enable);
        return automation;
    }

    @Override // com.ltech.smarthome.ui.scene.local.BaseSceneActionVM
    public List<Automation> getCurActionAutomation() {
        AutomationParam automationParam;
        Automation automation;
        ArrayList arrayList = new ArrayList();
        for (Scene.SceneContent sceneContent : this.actionList) {
            if (MaskType.isAutomationAction(sceneContent.getActionType()) && (automationParam = (AutomationParam) sceneContent.getExecuteCommand(AutomationParam.class)) != null && (automation = getAutomation(automationParam.automationid)) != null) {
                automation.setEnable(automationParam.enable);
                arrayList.add(automation);
            }
        }
        return arrayList;
    }

    public void addScene() {
        ((ObservableSubscribeProxy) Injection.net().addScene(Integer.MAX_VALUE, this.placeId, this.sceneName.getValue(), this.actionList, this.sceneIconPos.getValue().intValue(), this.floorId, this.roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalSceneVM.this.lambda$addScene$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddLocalSceneVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalSceneVM.this.lambda$addScene$2((AddSceneResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addScene$1(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addScene$2(AddSceneResponse addSceneResponse) throws Exception {
        Scene scene = new Scene();
        scene.setSceneId(addSceneResponse.getSceneid());
        scene.setPlaceId(addSceneResponse.getPlaceid());
        scene.setSceneIndex(addSceneResponse.getSceneindex());
        scene.setSceneName(addSceneResponse.getScenename());
        scene.setSceneIndex(addSceneResponse.getSceneindex());
        scene.setIconPos(addSceneResponse.getImgindex());
        LHomeLog.i(getClass(), "场景添加后返回数据  = " + GsonUtils.getGson().toJson(scene));
        Injection.repo().scene().saveScene(scene);
        finishActivity();
    }

    public void delScene(final long sceneId) {
        ((ObservableSubscribeProxy) Injection.net().deleteScene(sceneId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalSceneVM.this.lambda$delScene$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddLocalSceneVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalSceneVM.this.lambda$delScene$4(sceneId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delScene$3(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delScene$4(long j, Object obj) throws Exception {
        CmdAssistant.getSceneCmdAssistant(null, new int[0]).delLocalScene(ActivityUtils.getTopActivity(), this.sceneNum);
        Injection.repo().scene().removeScene(j);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.1
            @Override // java.lang.Runnable
            public void run() {
                ActAddLocalSceneVM.this.dismissLoadingDialog();
                ActAddLocalSceneVM.this.finishActivity();
            }
        }, 1000L);
    }

    public boolean check(boolean isBySave) {
        BaseIrParam baseIrParam;
        ActAddLocalSceneVM actAddLocalSceneVM = this;
        actAddLocalSceneVM.isDel = false;
        actAddLocalSceneVM.sameActionMap = new LinkedHashMap();
        actAddLocalSceneVM.saveMap = new HashMap();
        actAddLocalSceneVM.saveList = new ArrayList<>();
        if (actAddLocalSceneVM.actionList != null && actAddLocalSceneVM.actionList.size() != 0) {
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            int i = 0;
            int i2 = 0;
            while (i2 < actAddLocalSceneVM.actionList.size()) {
                Scene.SceneContent sceneContent = actAddLocalSceneVM.actionList.get(i2);
                if (i2 != 0) {
                    i += actAddLocalSceneVM.actionList.get(i2 - 1).getDelayTime(actAddLocalSceneVM.scene.isDynamic());
                }
                int i3 = i;
                if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                    LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                    if (localDeviceParam != null) {
                        Device device = actAddLocalSceneVM.getDevice(localDeviceParam.deviceid);
                        if (device != null && device.isSubDevice() && (baseIrParam = (BaseIrParam) device.getParam(BaseIrParam.class)) != null && baseIrParam.getUnicastAddress() == 0) {
                            String str = device.getFloorName() + " " + device.getRoomName();
                            if (hashMap.containsKey(str)) {
                                hashMap.put(str, ((String) hashMap.get(str)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + device.getDeviceName());
                            } else {
                                hashMap.put(str, device.getDeviceName());
                            }
                        }
                        if (device != null && device.getParam() != null) {
                            actAddLocalSceneVM.setDeviceAction(device, localDeviceParam.option, localDeviceParam.optionvalue, localDeviceParam.instruct, i3, localDeviceParam.sceneZone, localDeviceParam.sceneAddr, localDeviceParam.songNames, i2, isBySave);
                        }
                    }
                } else {
                    if (MaskType.isGroupAction(sceneContent.getActionType())) {
                        LocalGroupParam localGroupParam = (LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class);
                        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(localGroupParam.groupid);
                        if (ProductRepository.isDaliLightGroup(groupByGroupId)) {
                            actAddLocalSceneVM = this;
                            actAddLocalSceneVM.setDaliGroupAction(groupByGroupId, localGroupParam.option, localGroupParam.optionvalue, localGroupParam.instruct, i3, localGroupParam.sceneZone, localGroupParam.sceneAddr, i2);
                        } else {
                            actAddLocalSceneVM = this;
                            if (groupByGroupId != null) {
                                Group groupByGroupId2 = groupByGroupId.getMaingroupid() > 0 ? Injection.repo().group().getGroupByGroupId(groupByGroupId.getMaingroupid()) : groupByGroupId;
                                if (groupByGroupId2 != null && groupByGroupId2.getDeviceIds() != null) {
                                    List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(groupByGroupId2.getDeviceIds());
                                    if (devicesByIds != null && !devicesByIds.isEmpty()) {
                                        if (actAddLocalSceneVM.isLightGroup(groupByGroupId) && "13".equals(localGroupParam.option) && !localGroupParam.gsActions.isEmpty()) {
                                            for (Device device2 : devicesByIds) {
                                                Iterator<GradientSceneAction> it = localGroupParam.gsActions.iterator();
                                                while (true) {
                                                    if (it.hasNext()) {
                                                        GradientSceneAction next = it.next();
                                                        if (device2.getDeviceId() == next.getDeviceId()) {
                                                            actAddLocalSceneVM.setDeviceAction(device2, localGroupParam.option, localGroupParam.optionvalue, next.getInstruct().substring(0, 2) + next.getInstruct().substring(6), i3, localGroupParam.sceneZone, localGroupParam.sceneAddr, "", i2, isBySave);
                                                            break;
                                                        }
                                                        actAddLocalSceneVM = this;
                                                    }
                                                }
                                                actAddLocalSceneVM = this;
                                            }
                                        } else {
                                            for (Device device3 : devicesByIds) {
                                                if (RelaySeparationHelper.isSwitchRelay(device3)) {
                                                    localGroupParam.sceneZone = 1 << (RelaySeparationHelper.getRelayNum(device3) - 1);
                                                }
                                                setDeviceAction(device3, localGroupParam.option, localGroupParam.optionvalue, localGroupParam.instruct, i3, localGroupParam.sceneZone, localGroupParam.sceneAddr, "", i2, isBySave);
                                            }
                                        }
                                    } else {
                                        localGroupParam.delayIn100Ms = sceneContent.getDelayTime(actAddLocalSceneVM.scene.isDynamic());
                                        localGroupParam.setTotalDelay(i3);
                                        sceneContent.setExecuteCommand(localGroupParam);
                                        actAddLocalSceneVM.actionList.set(i2, sceneContent);
                                    }
                                }
                            }
                        }
                    } else {
                        actAddLocalSceneVM = this;
                        if (MaskType.isAutomationAction(sceneContent.getActionType())) {
                            AutomationParam automationParam = (AutomationParam) sceneContent.getExecuteCommand(AutomationParam.class);
                            Automation automation = actAddLocalSceneVM.getAutomation(automationParam.automationid);
                            if (automation != null) {
                                String str2 = 1 == automationParam.enable ? actAddLocalSceneVM.getContext().getString(R.string.enable) + "\"" + automation.getName() + "\"" : actAddLocalSceneVM.getContext().getString(R.string.disable) + "\"" + automation.getName() + "\"";
                                String string = actAddLocalSceneVM.getContext().getString(R.string.automation);
                                if (hashMap.containsKey(string)) {
                                    hashMap.put(string, ((String) hashMap.get(string)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + str2);
                                } else {
                                    hashMap.put(string, str2);
                                }
                            }
                        } else if (MaskType.isSceneAction(sceneContent.getActionType())) {
                            LocalDaliSceneParam localDaliSceneParam = (LocalDaliSceneParam) sceneContent.getExecuteCommand(LocalDaliSceneParam.class);
                            Scene scene = actAddLocalSceneVM.getScene(localDaliSceneParam.sceneid);
                            if (scene != null) {
                                actAddLocalSceneVM.setDeviceAction(actAddLocalSceneVM.getDevice(scene.getMacdeviceid()), "", "", localDaliSceneParam.instruct, i3, localDaliSceneParam.sceneZone, localDaliSceneParam.sceneAddr, "", i2, isBySave);
                            }
                        }
                    }
                    i2++;
                    i = i3;
                }
                actAddLocalSceneVM = this;
                i2++;
                i = i3;
            }
            if (hashMap.size() > 0 && isBySave) {
                for (Map.Entry entry : hashMap.entrySet()) {
                    arrayList.add(new NoActionDevice((String) entry.getKey(), (String) entry.getValue()));
                }
                actAddLocalSceneVM.showAutoDeviceFailDialogEvent.postValue(arrayList);
                return false;
            }
            if (!actAddLocalSceneVM.sameActionMap.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                for (Map.Entry<Long, Map<Integer, String>> entry2 : actAddLocalSceneVM.sameActionMap.entrySet()) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(entry2.getKey().longValue());
                    StringBuilder sb = new StringBuilder();
                    Map<Integer, String> value = entry2.getValue();
                    if (value.size() > 1 && !RelaySeparationHelper.isRelaySeparationDevice(deviceByDeviceId)) {
                        Iterator<Integer> it2 = value.keySet().iterator();
                        while (it2.hasNext()) {
                            sb.append(value.get(it2.next()));
                            sb.append("、");
                        }
                        if (deviceByDeviceId.getProductId().equals(ProductId.ID_BLE_HAM) || deviceByDeviceId.getProductId().equals(ProductId.ID_CENTRE_AIR_GATEWAY) || deviceByDeviceId.getProductId().equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                            arrayList2.add(new SameDevice("", deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName(), sb.toString()));
                        } else {
                            arrayList2.add(new SameDevice(deviceByDeviceId.getName(), deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName(), sb.toString()));
                        }
                    }
                }
                if (arrayList2.size() > 0) {
                    if (isBySave) {
                        actAddLocalSceneVM.showSameActionDialogEvent.setValue(actAddLocalSceneVM.getShowFailDevice(arrayList2));
                    } else {
                        actAddLocalSceneVM.refreshErrorEvent.call();
                    }
                    return false;
                }
                actAddLocalSceneVM.refreshErrorEvent.call();
            }
        }
        return true;
    }

    private boolean isLightGroup(Group group) {
        if (group == null) {
            return false;
        }
        return Integer.parseInt(group.getControlType()) == 101 || group.getGroupKey().equals(ProductId.BLE_GROUP_DIM_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_CT_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGB_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGBW_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGBWY_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGBWY_CC_LIGHT) || group.getGroupKey().equals("06");
    }

    public boolean checkSupportDynamic() {
        if (this.actionList != null && !this.actionList.isEmpty()) {
            for (int i = 0; i < this.actionList.size(); i++) {
                Scene.SceneContent sceneContent = this.actionList.get(i);
                if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                    LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                    if (localDeviceParam != null && !ProductRepository.supportDynamicScene(getDevice(localDeviceParam.deviceid))) {
                        return false;
                    }
                } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                    if (!ProductRepository.supportDynamicScene(Injection.repo().group().getGroupByGroupId(((LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class)).groupid))) {
                        return false;
                    }
                } else if (MaskType.isSceneAction(sceneContent.getActionType())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void batchControl(List<Observable<SameDevice>> request) {
        Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SameDevice>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.2
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
                ActAddLocalSceneVM.this.errorDeviceList.clear();
            }

            @Override // io.reactivex.Observer
            public void onNext(SameDevice device) {
                if (device.getErrorCode() != 0) {
                    ActAddLocalSceneVM.this.errorDeviceList.add(device);
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                ActAddLocalSceneVM.this.dismissLoadingDialog();
                onComplete();
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (!ActAddLocalSceneVM.this.errorDeviceList.isEmpty()) {
                    ActAddLocalSceneVM actAddLocalSceneVM = ActAddLocalSceneVM.this;
                    List<NoActionDevice> showFailDevice = actAddLocalSceneVM.getShowFailDevice(actAddLocalSceneVM.errorDeviceList);
                    if (!showFailDevice.isEmpty()) {
                        ActAddLocalSceneVM.this.showDeviceFailDialogEvent.postValue(showFailDevice);
                    }
                    CmdAssistant.getSceneCmdAssistant(null, new int[0]).saveLocalScene(ActivityUtils.getTopActivity(), ActAddLocalSceneVM.this.sceneNum);
                    ActAddLocalSceneVM.this.dismissLoadingDialog();
                } else {
                    if (ActAddLocalSceneVM.this.isDel) {
                        ActAddLocalSceneVM actAddLocalSceneVM2 = ActAddLocalSceneVM.this;
                        actAddLocalSceneVM2.delScene(actAddLocalSceneVM2.sceneId);
                    } else {
                        CmdAssistant.getSceneCmdAssistant(null, new int[0]).saveLocalScene(ActivityUtils.getTopActivity(), ActAddLocalSceneVM.this.sceneNum);
                        ActAddLocalSceneVM.this.dismissLoadingDialog();
                        SmartToast.showShort(R.string.save_success);
                    }
                    ActAddLocalSceneVM.this.isDel = false;
                }
                ActAddLocalSceneVM.this.refreshErrorEvent.call();
            }
        });
    }

    public void saveData() {
        ArrayList arrayList = new ArrayList();
        this.delDeviceIds = Utils.getReduceaListThanbList(this.saveList, this.lastSceneDeviceIds);
        showSaveLoadingDialog("");
        if (!this.delDeviceIds.isEmpty()) {
            for (int i = 0; i < this.delDeviceIds.size(); i++) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.delDeviceIds.get(i).longValue());
                if (deviceByDeviceId != null && !deviceByDeviceId.isVirtual()) {
                    arrayList.add(delTempScene(deviceByDeviceId));
                }
            }
            if (!arrayList.isEmpty()) {
                Observable.concat(arrayList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SameDevice>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.3
                    @Override // io.reactivex.Observer
                    public void onSubscribe(Disposable d2) {
                        ActAddLocalSceneVM.this.errorDeviceList.clear();
                    }

                    @Override // io.reactivex.Observer
                    public void onNext(SameDevice device) {
                        if (device.getErrorCode() == 0 || device.getErrorCode() == 153) {
                            return;
                        }
                        ActAddLocalSceneVM.this.errorDeviceList.add(device);
                    }

                    @Override // io.reactivex.Observer
                    public void onError(Throwable e) {
                        onComplete();
                    }

                    @Override // io.reactivex.Observer
                    public void onComplete() {
                        if (!ActAddLocalSceneVM.this.errorDeviceList.isEmpty()) {
                            ActAddLocalSceneVM.this.dismissLoadingDialog();
                            ActAddLocalSceneVM actAddLocalSceneVM = ActAddLocalSceneVM.this;
                            List<NoActionDevice> showFailDevice = actAddLocalSceneVM.getShowFailDevice(actAddLocalSceneVM.errorDeviceList);
                            if (!showFailDevice.isEmpty()) {
                                ActAddLocalSceneVM.this.showDeviceDeleteFailDialogEvent.postValue(showFailDevice);
                            }
                        } else {
                            ActAddLocalSceneVM.this.saveLocalAndServer(true);
                        }
                        ActAddLocalSceneVM.this.refreshErrorEvent.call();
                    }
                });
                return;
            } else {
                saveLocalAndServer(false);
                return;
            }
        }
        saveLocalAndServer(false);
    }

    private void addData(List<Long> retryIds) {
        ArrayList arrayList = new ArrayList();
        if (!this.saveMap.isEmpty()) {
            this.receiveDeviceMap = new HashMap();
            this.totalSaveAction = 0;
            for (Map.Entry<Long, List<LocalSceneParam>> entry : this.saveMap.entrySet()) {
                Long key = entry.getKey();
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(key.longValue());
                if (!deviceByDeviceId.isVirtual()) {
                    List<LocalSceneParam> value = entry.getValue();
                    this.receiveDeviceMap.put(key, 0);
                    for (int i = 0; i < value.size(); i++) {
                        LocalSceneParam localSceneParam = value.get(i);
                        if (retryIds != null) {
                            if (retryIds.contains(key)) {
                                arrayList.add(saveTempData(deviceByDeviceId, localSceneParam));
                                this.totalSaveAction++;
                            }
                        } else {
                            arrayList.add(saveTempData(deviceByDeviceId, localSceneParam));
                            this.totalSaveAction++;
                        }
                    }
                }
            }
            showSaveLoadingDialog(String.format(getContext().getString(R.string.config), String.format(Locale.US, "%d%%", 0)));
        }
        this.mSaveNum = 0;
        if (!arrayList.isEmpty()) {
            batchControl(arrayList);
        } else {
            dismissLoadingDialog();
        }
    }

    private void addData() {
        addData(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveLocalAndServer(final boolean needAffirmFrame) {
        ((ObservableSubscribeProxy) Injection.net().updateLocalScene(this.sceneId, this.sceneName.getValue(), this.actionList, this.sceneIconPos.getValue().intValue(), SceneHelper.instance().getLocalExecuteCommand(this.sceneNum)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddLocalSceneVM.this.lambda$saveLocalAndServer$5(needAffirmFrame, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActAddLocalSceneVM actAddLocalSceneVM = ActAddLocalSceneVM.this;
                actAddLocalSceneVM.showErrorTipDialog(actAddLocalSceneVM.getContext().getString(R.string.save_fail));
                CmdAssistant.getSceneCmdAssistant(null, new int[0]).clearLocalSceneCache(ActivityUtils.getTopActivity(), ActAddLocalSceneVM.this.sceneNum);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveLocalAndServer$5(boolean z, Object obj) throws Exception {
        Injection.repo().scene().updateSceneName(this.sceneId, this.sceneName.getValue());
        Injection.repo().scene().updateSceneIconPosition(this.sceneId, this.sceneIconPos.getValue().intValue());
        Injection.repo().scene().updateSceneContent(this.sceneId, this.actionList);
        if (z) {
            CmdAssistant.getSceneCmdAssistant(null, new int[0]).saveLocalScene(ActivityUtils.getTopActivity(), this.sceneNum);
        }
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.5
            @Override // java.lang.Runnable
            public void run() {
                ActAddLocalSceneVM.this.editSuccessEvent.call();
                ActAddLocalSceneVM actAddLocalSceneVM = ActAddLocalSceneVM.this;
                actAddLocalSceneVM.checkVirtualUpdate(actAddLocalSceneVM.actionList);
            }
        }, 1000L);
        addData();
    }

    private void setDeviceAction(Device device, String option, String optionvalue, String instruct, int atTime, int zoneNum, int address, String songNames, int index, boolean isBySave) {
        List<LocalSceneParam> arrayList;
        LocalDaliSceneParam localDaliSceneParam;
        long deviceId = !device.isSubDevice() ? device.getDeviceId() : device.getMacdeviceid();
        if (this.saveMap.containsKey(Long.valueOf(deviceId))) {
            arrayList = this.saveMap.get(Long.valueOf(deviceId));
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
        } else {
            arrayList = new ArrayList<>();
        }
        List<LocalSceneParam> list = arrayList;
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        String deviceActionString = SceneHelper.getDeviceActionString(ActivityUtils.getTopActivity(), device, option, optionvalue, instruct, zoneNum, songNames);
        if (this.sameActionMap.containsKey(Long.valueOf(deviceId))) {
            if (list.get(list.size() - 1).getTime() == atTime) {
                linkedHashMap = this.sameActionMap.get(Long.valueOf(deviceId));
            } else if (this.sameActionMap.get(Long.valueOf(deviceId)).size() > 1) {
                linkedHashMap = this.sameActionMap.get(Long.valueOf(deviceId));
            }
        }
        if (!device.isSubDevice() || RelaySeparationHelper.isRelaySeparationSub(device)) {
            linkedHashMap.put(Integer.valueOf(index), deviceActionString);
        } else {
            linkedHashMap.put(Integer.valueOf(index), device.getName() + " | " + deviceActionString);
        }
        this.sameActionMap.put(Long.valueOf(deviceId), linkedHashMap);
        this.saveList.add(Long.valueOf(deviceId));
        int size = list.size() + 1;
        if (isBySave) {
            Scene.SceneContent sceneContent = this.actionList.get(index);
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                if (localDeviceParam != null) {
                    localDeviceParam.sceneStep = size;
                    localDeviceParam.delayIn100Ms = sceneContent.getDelayTime(this.scene.isDynamic());
                    localDeviceParam.setTotalDelay(atTime);
                    sceneContent.setExecuteCommand(localDeviceParam);
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                LocalGroupParam localGroupParam = (LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class);
                if (localGroupParam != null) {
                    localGroupParam.sceneStep = size;
                    localGroupParam.delayIn100Ms = sceneContent.getDelayTime(this.scene.isDynamic());
                    localGroupParam.setTotalDelay(atTime);
                    sceneContent.setExecuteCommand(localGroupParam);
                }
            } else if (MaskType.isSceneAction(sceneContent.getActionType()) && (localDaliSceneParam = (LocalDaliSceneParam) sceneContent.getExecuteCommand(LocalDaliSceneParam.class)) != null) {
                localDaliSceneParam.sceneStep = size;
                localDaliSceneParam.delayIn100Ms = sceneContent.getDelayTime(this.scene.isDynamic());
                localDaliSceneParam.setTotalDelay(atTime);
                sceneContent.setExecuteCommand(localDaliSceneParam);
            }
            this.actionList.set(index, sceneContent);
        }
        list.add(new LocalSceneParam(device.getDeviceId(), atTime, size, instruct, zoneNum, address, option != null && option.equals(SceneConstants.OPTION_CURRENT_STATE), ProductRepository.getInfraredType(device.getProductId()), index));
        this.saveMap.put(Long.valueOf(deviceId), list);
    }

    private boolean setDaliGroupAction(Group group, String option, String optionvalue, String instruct, int atTime, int zoneNum, int address, int index) {
        List<LocalSceneParam> arrayList;
        LocalGroupParam localGroupParam;
        long macdeviceid = group.getMacdeviceid();
        if (this.saveMap.containsKey(Long.valueOf(macdeviceid))) {
            arrayList = this.saveMap.get(Long.valueOf(macdeviceid));
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
        } else {
            arrayList = new ArrayList<>();
        }
        List<LocalSceneParam> list = arrayList;
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        String groupActionString = SceneHelper.getGroupActionString(ActivityUtils.getTopActivity(), option, optionvalue, group, instruct, 0);
        if (this.sameActionMap.containsKey(Long.valueOf(macdeviceid))) {
            if (list.get(list.size() - 1).getTime() == atTime) {
                linkedHashMap = this.sameActionMap.get(Long.valueOf(macdeviceid));
            } else if (this.sameActionMap.get(Long.valueOf(macdeviceid)).size() > 1) {
                linkedHashMap = this.sameActionMap.get(Long.valueOf(macdeviceid));
            }
        }
        linkedHashMap.put(Integer.valueOf(index), group.getName() + " | " + groupActionString);
        this.sameActionMap.put(Long.valueOf(macdeviceid), linkedHashMap);
        this.saveList.add(Long.valueOf(macdeviceid));
        int size = list.size() + 1;
        Scene.SceneContent sceneContent = this.actionList.get(index);
        if (MaskType.isGroupAction(sceneContent.getActionType()) && (localGroupParam = (LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class)) != null) {
            localGroupParam.sceneStep = size;
            localGroupParam.delayIn100Ms = sceneContent.getDelayTime(this.scene.isDynamic());
            localGroupParam.setTotalDelay(atTime);
            sceneContent.setExecuteCommand(localGroupParam);
        }
        this.actionList.set(index, sceneContent);
        list.add(new LocalSceneParam(group.getGroupId(), atTime, list.size() + 1, instruct, zoneNum, address, option != null && option.equals(SceneConstants.OPTION_CURRENT_STATE), 0, index));
        this.saveMap.put(Long.valueOf(macdeviceid), list);
        return true;
    }

    private Observable<SameDevice> saveTempData(final Device device, final LocalSceneParam content) {
        return Observable.create(new ObservableOnSubscribe<SameDevice>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.6
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<SameDevice> emitter) throws Exception {
                if (!ActAddLocalSceneVM.this.saveMap.isEmpty() && !ActAddLocalSceneVM.this.receiveDeviceMap.containsKey(Long.valueOf(device.getDeviceId()))) {
                    String format = String.format(Locale.US, "%d%%", Integer.valueOf((int) ((ActAddLocalSceneVM.this.mSaveNum / ActAddLocalSceneVM.this.totalSaveAction) * 100.0f)));
                    ActAddLocalSceneVM actAddLocalSceneVM = ActAddLocalSceneVM.this;
                    actAddLocalSceneVM.showSaveLoadingDialog(String.format(actAddLocalSceneVM.getContext().getString(R.string.config), format));
                    emitter.onNext(ActAddLocalSceneVM.this.getSameDeviceByResponse(device, null));
                    emitter.onComplete();
                    return;
                }
                String instruct = content.getInstruct();
                if (content.getInfraredType() != 0) {
                    instruct = StringUtils.demToHex(content.getDeviceId(), 16) + instruct;
                }
                CmdAssistant.getSceneCmdAssistant(device, new int[0]).saveLocalSceneAction(ActivityUtils.getTopActivity(), ActAddLocalSceneVM.this.sceneNum, instruct, content.getStep(), content.getTime(), content.getZoneNum(), content.getAddress(), content.isCurState(), content.getInfraredType(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.6.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg responseMsg) {
                        String str;
                        float f;
                        int i;
                        LocalDaliSceneParam localDaliSceneParam;
                        int unicastAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                        if (responseMsg == null) {
                            str = "%d%%";
                            f = 100.0f;
                        } else {
                            if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) != unicastAddress) {
                                ActAddLocalSceneVM.this.showSaveLoadingDialog(String.format(ActAddLocalSceneVM.this.getContext().getString(R.string.config), String.format(Locale.US, "%d%%", Integer.valueOf((int) ((ActAddLocalSceneVM.this.mSaveNum / ActAddLocalSceneVM.this.totalSaveAction) * 100.0f)))));
                                emitter.onNext(ActAddLocalSceneVM.this.getSameDeviceByResponse(device, null));
                                emitter.onComplete();
                                return;
                            }
                            Scene.SceneContent sceneContent = ActAddLocalSceneVM.this.actionList.get(content.getIndex());
                            f = 100.0f;
                            if (!MaskType.isDeviceAction(sceneContent.getActionType())) {
                                str = "%d%%";
                                if (MaskType.isGroupAction(sceneContent.getActionType())) {
                                    LocalGroupParam localGroupParam = (LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class);
                                    if (localGroupParam != null) {
                                        localGroupParam.sceneStep = content.getStep();
                                        localGroupParam.delayIn100Ms = sceneContent.getDelayTime(ActAddLocalSceneVM.this.scene.isDynamic());
                                        localGroupParam.setTotalDelay(content.getTime());
                                        if (content.isCurState()) {
                                            localGroupParam.instruct = responseMsg.getResData();
                                            if (localGroupParam.instruct.length() >= 18) {
                                                localGroupParam.instruct = localGroupParam.instruct.substring(16);
                                                String substring = localGroupParam.instruct.substring(0, 2);
                                                if (substring.endsWith("C7")) {
                                                    localGroupParam.option = "4";
                                                } else if (substring.endsWith("C9")) {
                                                    localGroupParam.option = "2";
                                                } else if (substring.endsWith("CA")) {
                                                    if (ProductRepository.getLightColorType((Object) device) == 1) {
                                                        localGroupParam.option = "5";
                                                    } else if (ProductRepository.getLightColorType((Object) device) == 2) {
                                                        localGroupParam.option = "6";
                                                    } else {
                                                        localGroupParam.option = "3";
                                                    }
                                                } else if (substring.endsWith("C8")) {
                                                    localGroupParam.option = "1";
                                                } else if (substring.endsWith("D5")) {
                                                    localGroupParam.option = "9";
                                                } else {
                                                    localGroupParam.option = "0";
                                                }
                                                if (substring.endsWith("C9") || substring.endsWith("CA") || substring.endsWith("C8")) {
                                                    int parseInt = Integer.parseInt(localGroupParam.instruct.substring(localGroupParam.instruct.length() - 4), 16);
                                                    for (int i2 = 0; i2 < 16; i2++) {
                                                        int i3 = 1 << i2;
                                                        if (i3 == (parseInt & i3)) {
                                                            localGroupParam.optionvalue = i2 + "";
                                                        }
                                                    }
                                                } else if (substring.endsWith("D5") && localGroupParam.instruct.length() >= 8 && Integer.parseInt(localGroupParam.instruct.substring(4, 6), 16) == 2) {
                                                    if (Integer.parseInt(localGroupParam.instruct.substring(6, 8), 16) == 0) {
                                                        localGroupParam.optionvalue = ActAddLocalSceneVM.this.getContext().getString(R.string.app_str_circadian_lighting_pause) + ActAddLocalSceneVM.this.getContext().getString(R.string.app_circadian_lighting);
                                                    } else {
                                                        localGroupParam.optionvalue = ActAddLocalSceneVM.this.getContext().getString(R.string.app_str_circadian_lighting_continue) + ActAddLocalSceneVM.this.getContext().getString(R.string.app_circadian_lighting);
                                                    }
                                                }
                                            } else {
                                                localGroupParam.option = "0";
                                            }
                                        }
                                        sceneContent.setExecuteCommand(localGroupParam);
                                        ActAddLocalSceneVM.this.actionList.set(content.getIndex(), sceneContent);
                                    }
                                } else if (MaskType.isSceneAction(sceneContent.getActionType()) && (localDaliSceneParam = (LocalDaliSceneParam) sceneContent.getExecuteCommand(LocalDaliSceneParam.class)) != null) {
                                    localDaliSceneParam.sceneStep = content.getStep();
                                    localDaliSceneParam.delayIn100Ms = sceneContent.getDelayTime(ActAddLocalSceneVM.this.scene.isDynamic());
                                    localDaliSceneParam.setTotalDelay(content.getTime());
                                    sceneContent.setExecuteCommand(localDaliSceneParam);
                                    ActAddLocalSceneVM.this.actionList.set(content.getIndex(), sceneContent);
                                }
                            } else {
                                LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                                if (localDeviceParam == null) {
                                    str = "%d%%";
                                } else {
                                    localDeviceParam.sceneStep = content.getStep();
                                    localDeviceParam.delayIn100Ms = sceneContent.getDelayTime(ActAddLocalSceneVM.this.scene.isDynamic());
                                    localDeviceParam.setTotalDelay(content.getTime());
                                    if (!content.isCurState()) {
                                        str = "%d%%";
                                    } else {
                                        localDeviceParam.instruct = responseMsg.getResData();
                                        if (localDeviceParam.instruct.length() < 18) {
                                            str = "%d%%";
                                            localDeviceParam.option = "0";
                                        } else {
                                            localDeviceParam.instruct = localDeviceParam.instruct.substring(16);
                                            str = "%d%%";
                                            String substring2 = localDeviceParam.instruct.substring(0, 2);
                                            if (substring2.endsWith("C7")) {
                                                localDeviceParam.option = "4";
                                            } else if (substring2.endsWith("C9")) {
                                                localDeviceParam.option = "2";
                                            } else if (substring2.endsWith("CA")) {
                                                if (ProductRepository.getLightColorType((Object) device) == 1) {
                                                    localDeviceParam.option = "5";
                                                } else if (ProductRepository.getLightColorType((Object) device) == 2) {
                                                    localDeviceParam.option = "6";
                                                } else {
                                                    localDeviceParam.option = "3";
                                                }
                                            } else if (substring2.endsWith("C8")) {
                                                localDeviceParam.option = "1";
                                            } else if (substring2.endsWith("D5")) {
                                                localDeviceParam.option = "9";
                                            } else {
                                                localDeviceParam.option = "0";
                                            }
                                            if (substring2.endsWith("C9") || substring2.endsWith("CA") || substring2.endsWith("C8")) {
                                                int parseInt2 = Integer.parseInt(localDeviceParam.instruct.substring(localDeviceParam.instruct.length() - 4), 16);
                                                int i4 = 0;
                                                for (int i5 = 16; i4 < i5; i5 = 16) {
                                                    int i6 = 1 << i4;
                                                    if (i6 == (parseInt2 & i6)) {
                                                        localDeviceParam.optionvalue = i4 + "";
                                                    }
                                                    i4++;
                                                }
                                            } else if (substring2.endsWith("D5") && localDeviceParam.instruct.length() >= 8 && Integer.parseInt(localDeviceParam.instruct.substring(4, 6), 16) == 2) {
                                                if (Integer.parseInt(localDeviceParam.instruct.substring(6, 8), 16) == 0) {
                                                    localDeviceParam.optionvalue = ActAddLocalSceneVM.this.getContext().getString(R.string.app_str_circadian_lighting_pause) + ActAddLocalSceneVM.this.getContext().getString(R.string.app_circadian_lighting);
                                                } else {
                                                    localDeviceParam.optionvalue = ActAddLocalSceneVM.this.getContext().getString(R.string.app_str_circadian_lighting_continue) + ActAddLocalSceneVM.this.getContext().getString(R.string.app_circadian_lighting);
                                                }
                                            }
                                        }
                                    }
                                    sceneContent.setExecuteCommand(localDeviceParam);
                                    ActAddLocalSceneVM.this.actionList.set(content.getIndex(), sceneContent);
                                }
                            }
                        }
                        if (ActAddLocalSceneVM.this.receiveDeviceMap == null || ActAddLocalSceneVM.this.receiveDeviceMap.get(Long.valueOf(device.getDeviceId())) == null) {
                            i = 0;
                        } else {
                            i = ((Integer) ActAddLocalSceneVM.this.receiveDeviceMap.get(Long.valueOf(device.getDeviceId()))).intValue() + 1;
                            ActAddLocalSceneVM.this.receiveDeviceMap.put(Long.valueOf(device.getDeviceId()), Integer.valueOf(i));
                        }
                        if (i == ((List) ActAddLocalSceneVM.this.saveMap.get(Long.valueOf(device.getDeviceId()))).size()) {
                            ActAddLocalSceneVM.this.receiveDeviceMap.remove(Long.valueOf(device.getDeviceId()));
                        }
                        ActAddLocalSceneVM.this.mSaveNum++;
                        ActAddLocalSceneVM.this.showSaveLoadingDialog(String.format(ActAddLocalSceneVM.this.getContext().getString(R.string.config), String.format(Locale.US, str, Integer.valueOf((int) ((ActAddLocalSceneVM.this.mSaveNum / ActAddLocalSceneVM.this.totalSaveAction) * f)))));
                        SameDevice sameDeviceByResponse = ActAddLocalSceneVM.this.getSameDeviceByResponse(device, responseMsg);
                        if (sameDeviceByResponse.getErrorCode() == -1) {
                            ActAddLocalSceneVM.this.receiveDeviceMap.remove(Long.valueOf(device.getDeviceId()));
                        } else if (sameDeviceByResponse.getErrorCode() == 14) {
                            ActAddLocalSceneVM.this.errorDeviceList.add(sameDeviceByResponse);
                            emitter.onError(new Throwable());
                        }
                        emitter.onNext(sameDeviceByResponse);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    private Observable<SameDevice> delTempScene(final Device device) {
        return Observable.create(new ObservableOnSubscribe<SameDevice>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.7
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<SameDevice> emitter) throws Exception {
                CmdAssistant.getSceneCmdAssistant(device, new int[0]).delDeviceLocalScene(ActAddLocalSceneVM.this.getContext(), ActAddLocalSceneVM.this.sceneNum, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM.7.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg responseMsg) {
                        emitter.onNext(ActAddLocalSceneVM.this.getSameDeviceByResponse(device, responseMsg));
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SameDevice getSameDeviceByResponse(Device device, ResponseMsg responseMsg) {
        DeviceParam deviceParam;
        String str = device.getFloorName() + " " + device.getRoomName();
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 9 || responseMsg.getStateCode() == 153) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_99), 153);
            }
            if (responseMsg.getStateCode() == 11) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_0B), 11);
            }
            if (responseMsg.getStateCode() == 12) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_0C), 12);
            }
            if (responseMsg.getStateCode() == 13) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_0D), 13);
            }
            if (responseMsg.getStateCode() == 14) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_0E), 14);
            }
            if (responseMsg.getStateCode() == 15) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_0F), 15);
            }
            if (responseMsg.getStateCode() == 3) {
                return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.local_scene_error_03), 3);
            }
            return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.online), 0);
        }
        for (int i = 0; i < this.actionList.size(); i++) {
            Scene.SceneContent sceneContent = this.actionList.get(i);
            if (MaskType.isDeviceAction(sceneContent.getActionType()) && (deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class)) != null) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid);
                if (deviceByDeviceId.getMacdeviceid() == device.getDeviceId() && !RelaySeparationHelper.isRelaySeparationSub(deviceByDeviceId)) {
                    return new SameDevice(device.getDeviceId(), deviceByDeviceId.getName(), deviceByDeviceId.getFloorName() + " " + deviceByDeviceId.getRoomName(), getContext().getString(R.string.gateway_offline_error), -1);
                }
            }
        }
        return new SameDevice(device.getDeviceId(), device.getName(), str, getContext().getString(R.string.offline), -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<NoActionDevice> getShowFailDevice(List<SameDevice> deviceList) {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (SameDevice sameDevice : deviceList) {
            if (sameDevice != null) {
                if (hashMap.containsKey(sameDevice.room)) {
                    String str = (String) hashMap.get(sameDevice.room);
                    if (!str.split("\\|")[0].trim().equals(sameDevice.getName())) {
                        String str2 = sameDevice.room;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append("\n");
                        sb.append(sameDevice.getName());
                        sb.append("".equals(sameDevice.getName()) ? "" : " | ");
                        sb.append(sameDevice.getAction());
                        hashMap.put(str2, sb.toString());
                    }
                } else {
                    String str3 = sameDevice.room;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(sameDevice.getName());
                    sb2.append("".equals(sameDevice.getName()) ? "" : " | ");
                    sb2.append(sameDevice.getAction());
                    hashMap.put(str3, sb2.toString());
                }
            }
        }
        if (hashMap.size() > 0) {
            for (Map.Entry entry : hashMap.entrySet()) {
                arrayList.add(new NoActionDevice((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        return arrayList;
    }

    public void setLastDevice() {
        GroupParam groupParam;
        Group groupByGroupId;
        Device deviceByDeviceId;
        if (this.actionList != null) {
            this.lastSceneDeviceIds = new ArrayList();
            for (int i = 0; i < this.actionList.size(); i++) {
                Scene.SceneContent sceneContent = this.actionList.get(i);
                if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                    DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                    if (deviceParam != null && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid)) != null) {
                        this.lastSceneDeviceIds.add(Long.valueOf(!deviceByDeviceId.isSubDevice() ? deviceByDeviceId.getDeviceId() : deviceByDeviceId.getMacdeviceid()));
                    }
                } else if (MaskType.isGroupAction(sceneContent.getActionType()) && (groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class)) != null && (groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid)) != null) {
                    if (ProductRepository.isDaliLightGroup(groupByGroupId)) {
                        this.lastSceneDeviceIds.add(Long.valueOf(groupByGroupId.getMacdeviceid()));
                    } else if (groupByGroupId.getDeviceIds() != null) {
                        this.lastSceneDeviceIds.addAll(groupByGroupId.getDeviceIds());
                    }
                }
            }
            Utils.cleanDisRepet(this.lastSceneDeviceIds);
        }
    }

    public void delScene() {
        GroupParam groupParam;
        Group groupByGroupId;
        if (this.actionList != null) {
            showLoadingDialog();
            ArrayList arrayList = new ArrayList();
            this.delDeviceIds = new ArrayList();
            for (int i = 0; i < this.actionList.size(); i++) {
                Scene.SceneContent sceneContent = this.actionList.get(i);
                if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                    DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                    if (deviceParam != null && deviceParam.instruct != null && !TextUtils.isEmpty(deviceParam.instruct)) {
                        this.delDeviceIds.add(Long.valueOf(deviceParam.deviceid));
                    }
                } else if (MaskType.isGroupAction(sceneContent.getActionType()) && (groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class)) != null && groupParam.instruct != null && !TextUtils.isEmpty(groupParam.instruct) && (groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid)) != null) {
                    if (ProductRepository.isDaliLightGroup(groupByGroupId)) {
                        this.delDeviceIds.add(Long.valueOf(groupByGroupId.getMacdeviceid()));
                    } else if (groupByGroupId.getDeviceIds() != null) {
                        this.delDeviceIds.addAll(groupByGroupId.getDeviceIds());
                    }
                }
            }
            Utils.cleanDisRepet(this.delDeviceIds);
            for (int i2 = 0; i2 < this.delDeviceIds.size(); i2++) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.delDeviceIds.get(i2).longValue());
                if (!deviceByDeviceId.isVirtual()) {
                    arrayList.add(delTempScene(deviceByDeviceId));
                }
            }
            if (!arrayList.isEmpty()) {
                this.isDel = true;
                batchControl(arrayList);
            } else {
                CmdAssistant.getSceneCmdAssistant(null, new int[0]).delDeviceLocalScene(ActivityUtils.getTopActivity(), this.sceneNum);
                delScene(this.sceneId);
                dismissLoadingDialog();
            }
        }
    }

    public boolean hasAction() {
        BaseIrParam baseIrParam;
        if (this.actionList == null || this.actionList.isEmpty()) {
            return false;
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (Scene.SceneContent sceneContent : this.actionList) {
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                if (deviceParam == null) {
                    return false;
                }
                if (deviceParam.option == null || !deviceParam.option.equals(SceneConstants.OPTION_CURRENT_STATE)) {
                    if (deviceParam.instruct == null || TextUtils.isEmpty(deviceParam.instruct)) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid);
                        String str = deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
                        if (!deviceByDeviceId.isSubDevice() || (baseIrParam = (BaseIrParam) deviceByDeviceId.getParam(BaseIrParam.class)) == null || baseIrParam.getUnicastAddress() != 0) {
                            if (hashMap.containsKey(str)) {
                                hashMap.put(str, ((String) hashMap.get(str)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + deviceByDeviceId.getDeviceName());
                            } else {
                                hashMap.put(str, deviceByDeviceId.getDeviceName());
                            }
                        }
                    }
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
                if (groupParam == null) {
                    return false;
                }
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid);
                if (!isLightGroup(groupByGroupId) || !"13".equals(groupParam.option)) {
                    if (!groupParam.option.equals(SceneConstants.OPTION_CURRENT_STATE) && (groupParam.instruct == null || TextUtils.isEmpty(groupParam.instruct))) {
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
        if (hashMap.isEmpty()) {
            return true;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            arrayList.add(new NoActionDevice((String) entry.getKey(), (String) entry.getValue()));
        }
        this.noSetActionDeviceEvent.postValue(arrayList);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSaveLoadingDialog(String content) {
        WaitDialog.showWaitDialog((AppCompatActivity) ActivityUtils.getTopActivity(), content, R.layout.dialog_save_local_scene);
    }

    public void retrySingleStep(Scene.SceneContent content) {
        Scene scene;
        Device deviceByDeviceId;
        ArrayList arrayList = new ArrayList();
        if (MaskType.isDeviceAction(content.getActionType())) {
            LocalDeviceParam localDeviceParam = (LocalDeviceParam) content.getExecuteCommand(LocalDeviceParam.class);
            if (localDeviceParam != null && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(localDeviceParam.deviceid)) != null) {
                arrayList.add(Long.valueOf(!deviceByDeviceId.isSubDevice() ? deviceByDeviceId.getDeviceId() : deviceByDeviceId.getMacdeviceid()));
            }
        } else if (MaskType.isGroupAction(content.getActionType())) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(((LocalGroupParam) content.getExecuteCommand(LocalGroupParam.class)).groupid);
            if (groupByGroupId != null) {
                if (groupByGroupId.getMaingroupid() > 0) {
                    groupByGroupId = Injection.repo().group().getGroupByGroupId(groupByGroupId.getMaingroupid());
                }
                if (groupByGroupId != null && groupByGroupId.getDeviceIds() != null) {
                    arrayList.addAll(groupByGroupId.getDeviceIds());
                }
            }
        } else if (MaskType.isAutomationAction(content.getActionType())) {
            arrayList.add(Long.valueOf(getAutomation(((AutomationParam) content.getExecuteCommand(AutomationParam.class)).automationid).getGatewayDeviceId()));
        } else if (MaskType.isSceneAction(content.getActionType()) && (scene = getScene(((LocalDaliSceneParam) content.getExecuteCommand(LocalDaliSceneParam.class)).sceneid)) != null) {
            arrayList.add(Long.valueOf(scene.getMacdeviceid()));
        }
        addData(arrayList);
    }

    public void retryFailDevice() {
        ArrayList arrayList = new ArrayList();
        Iterator<SameDevice> it = this.errorDeviceList.iterator();
        while (it.hasNext()) {
            arrayList.add(Long.valueOf(it.next().getDeviceId()));
        }
        addData(arrayList);
    }

    static class SameDevice {
        private String action;
        private long deviceId;
        private int errorCode;
        private String name;
        private String room;

        public SameDevice(String name, String room, String action) {
            this.name = name;
            this.room = room;
            this.action = action;
        }

        public SameDevice(long deviceId, String name, String room, String action, int errorCode) {
            this.deviceId = deviceId;
            this.name = name;
            this.room = room;
            this.action = action;
            this.errorCode = errorCode;
        }

        public long getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceId(long deviceId) {
            this.deviceId = deviceId;
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

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getErrorCode() {
            return this.errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
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