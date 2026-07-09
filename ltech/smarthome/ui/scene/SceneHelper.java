package com.ltech.smarthome.ui.scene;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.justalk.cloud.lemon.MtcUserConstants;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.AppNoticeParam;
import com.ltech.smarthome.model.scene_param.CharSwitch;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.model.scene_param.LocalGroupParam;
import com.ltech.smarthome.model.scene_param.SceneConstants;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.ui.automation.ActSelectAutomationForAction;
import com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectLightStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectRelayStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectSensorStatusDetailCondition;
import com.ltech.smarthome.ui.automation.trigger.ActSelectSensorTriggerCondition;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import com.ltech.smarthome.ui.scene.local.ActSelectDeviceGroupForLocalAction;
import com.ltech.smarthome.ui.scene.local.ActSelectSuperPanel;
import com.ltech.smarthome.ui.select.ActSelectSceneForAction;
import com.ltech.smarthome.utils.BitUtils;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.SmartUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.LightCmdParam;
import com.smart.product_agreement.productBle.CmdBle;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.netty.util.internal.StringUtil;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.ES6Iterator;

/* loaded from: classes4.dex */
public class SceneHelper {
    public static final int BIND_BY_CG_485 = 5;
    public static final int BIND_BY_KNOB_PANEL = 3;
    public static final int BIND_BY_SMART_PANEL = 1;
    public static final int BIND_BY_SUPER_PANEL = 2;
    public static final int BIND_BY_TRIG_TO_TB = 4;
    public static final String CACHE_SELECT_AUTOMATION = "select_automation_cache";
    public static final String CACHE_SELECT_SCENE = "select_scene_cache";
    public static final String GS_DATA = "gsdata";
    public static final String GS_Id = "gsid";
    public static final String GS_NAME = "gsname";
    public static final String OPTION = "option";
    public static final String OPTION_VALUE = "optionvalue";
    public static final String SCENE_PARAM_EXT = "sceneParamExt";
    public static final String SCENE_REPORT_INSTRUCT = "sceneReportInstruct";
    public static final int SELECT_APPNOTICE_ACTION = 5;
    public static final int SELECT_AUTOMATION_ACTION = 3;
    public static final int SELECT_DEVICE_ACTION = 1;
    public static final int SELECT_DEVICE_GROUP_ACTION = 6;
    public static final int SELECT_DEVICE_GROUP_FOR_LOCAL_SCENE_ACTION = 9;
    public static final int SELECT_DEVICE_GROUP_SINGLE_ACTION = 7;
    public static final int SELECT_GROUP_ACTION = 2;
    public static final int SELECT_PLAY_MUSIC = 11;
    public static final int SELECT_SCENE_ACTION = 4;
    public static final int SELECT_SECURITY_SCENE = 12;
    public static final int SELECT_SP_BIND_KEY_DEVICE_GROUP_ACTION = 8;
    public static final int SELECT_VOICE_SPEAK = 10;
    public static final String SONG_NAMES = "songNames";
    public static final int TIME_SPACE_DEFAULT = 1;
    public List<Scene.SceneContent> actionList;
    public boolean automationAction;
    public boolean automationCondition;
    public BaseCmd baseCmd;
    public int bindingType;
    public ArrayMap<String, Object> cacheObject;
    public BaseCmdParam cmdParam;
    public Object conditionParam;
    public int conditionParamType;
    public Object controlObject;
    public int dstAddress;
    public int eventtype;
    public boolean isCgdProAction;
    public boolean isMultiZone;
    public int keyIndex;
    public byte[] macCode;
    public MaskType maskType;
    public long orgFloorId;
    public long orgRoomId;
    public SuperPanelInfo.PanelKeyInfo panelKeyInfo;
    public SuperPanelInfo.PanelKeyLight panelKeyLight;
    public long panelid;
    public DeviceParam selectDeviceParam;
    public String selectInstruct;
    public int timespace;

    private static String getBleCurtainAction(Context context, String option, String optionValue) {
        return optionValue;
    }

    private SceneHelper() {
        this.timespace = 1;
        this.eventtype = 1;
        this.cacheObject = new ArrayMap<>(4);
        this.orgFloorId = -2L;
        this.orgRoomId = -2L;
    }

    private static class Holder {
        private static SceneHelper INSTANCE = new SceneHelper();

        private Holder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void reset() {
            INSTANCE = new SceneHelper();
        }
    }

    public static SceneHelper instance() {
        return Holder.INSTANCE;
    }

    public void reset() {
        Holder.reset();
    }

    public Scene.SceneContent convert2SceneContentInfo() {
        Scene.SceneContent sceneContent = new Scene.SceneContent();
        sceneContent.setAction(PushContentParamKey.ACTION);
        sceneContent.setActionType(this.maskType.value());
        sceneContent.setActionTypeName(this.maskType.typeName());
        sceneContent.setTimeSpace(this.timespace);
        sceneContent.setExecuteCommand(createExecuteCommand(this.controlObject));
        return sceneContent;
    }

    public List<Scene.SceneContent> convert2SceneContents() {
        ArrayList arrayList = new ArrayList();
        if (this.maskType.value() == MaskType.AUTOMATION.value()) {
            for (Automation automation : (List) this.controlObject) {
                Scene.SceneContent sceneContent = new Scene.SceneContent();
                sceneContent.setAction(PushContentParamKey.ACTION);
                sceneContent.setActionType(this.maskType.value());
                sceneContent.setActionTypeName(this.maskType.typeName());
                sceneContent.setTimeSpace(this.timespace);
                sceneContent.setExecuteCommand(createExecuteCommand(automation));
                arrayList.add(sceneContent);
            }
        } else {
            if (this.maskType.value() == MaskType.DEVICE.value() || this.maskType.value() == MaskType.GROUP.value()) {
                Scene.SceneContent sceneContent2 = new Scene.SceneContent();
                sceneContent2.setAction(PushContentParamKey.ACTION);
                sceneContent2.setActionType(this.maskType.value());
                sceneContent2.setActionTypeName(this.maskType.typeName());
                sceneContent2.setTimeSpace(this.timespace);
                sceneContent2.setExecuteCommand(createExecuteCommand(this.controlObject));
                sceneContent2.setActionType(this.maskType.value());
                arrayList.add(sceneContent2);
                return arrayList;
            }
            if (this.maskType.value() == MaskType.DEVICE_GROUP.value()) {
                for (Role role : (List) this.controlObject) {
                    if (role instanceof Device) {
                        this.maskType = MaskType.DEVICE;
                        Scene.SceneContent sceneContent3 = new Scene.SceneContent();
                        sceneContent3.setAction(PushContentParamKey.ACTION);
                        sceneContent3.setActionType(this.maskType.value());
                        sceneContent3.setActionTypeName(this.maskType.typeName());
                        sceneContent3.setTimeSpace(this.timespace);
                        sceneContent3.setExecuteCommand(createExecuteCommand(role));
                        arrayList.add(sceneContent3);
                    } else if (role instanceof Group) {
                        this.maskType = MaskType.GROUP;
                        Scene.SceneContent sceneContent4 = new Scene.SceneContent();
                        sceneContent4.setAction(PushContentParamKey.ACTION);
                        sceneContent4.setActionType(this.maskType.value());
                        sceneContent4.setActionTypeName(this.maskType.typeName());
                        sceneContent4.setTimeSpace(this.timespace);
                        sceneContent4.setExecuteCommand(createExecuteCommand(role));
                        arrayList.add(sceneContent4);
                    }
                }
            } else if (this.maskType.value() == MaskType.SCENE.value()) {
                for (Scene scene : (List) this.controlObject) {
                    Scene.SceneContent sceneContent5 = new Scene.SceneContent();
                    sceneContent5.setAction(PushContentParamKey.ACTION);
                    sceneContent5.setActionType(MaskType.SCENE.value());
                    sceneContent5.setActionTypeName(this.maskType.typeName());
                    sceneContent5.setTimeSpace(this.timespace);
                    sceneContent5.setExecuteCommand(createExecuteCommand(scene));
                    arrayList.add(sceneContent5);
                }
            }
        }
        return arrayList;
    }

    public List<Scene.SceneContent> convert2LocalSceneContents() {
        ArrayList arrayList = new ArrayList();
        if (this.maskType.value() == MaskType.LOCAL.value()) {
            Object obj = this.controlObject;
            if ((obj instanceof Device) || (obj instanceof Group)) {
                Scene.SceneContent sceneContent = new Scene.SceneContent();
                sceneContent.setAction(PushContentParamKey.ACTION);
                sceneContent.setActionType((this.controlObject instanceof Device ? MaskType.DEVICE : MaskType.GROUP).value());
                sceneContent.setActionTypeName(this.controlObject instanceof Device ? "设备" : "灯组");
                sceneContent.setTimeSpace(this.timespace);
                sceneContent.setExecuteCommand(createLocalExecuteCommand(this.controlObject));
                arrayList.add(sceneContent);
                return arrayList;
            }
            if (obj instanceof Scene) {
                Scene.SceneContent sceneContent2 = new Scene.SceneContent();
                sceneContent2.setAction(PushContentParamKey.ACTION);
                sceneContent2.setActionType(MaskType.SCENE.value());
                sceneContent2.setActionTypeName(MaskType.SCENE.typeName());
                sceneContent2.setTimeSpace(this.timespace);
                sceneContent2.setExecuteCommand(createLocalExecuteCommand(this.controlObject));
                arrayList.add(sceneContent2);
                return arrayList;
            }
            for (Role role : (List) obj) {
                Scene.SceneContent sceneContent3 = new Scene.SceneContent();
                sceneContent3.setAction(PushContentParamKey.ACTION);
                boolean z = role instanceof Device;
                sceneContent3.setActionType((z ? MaskType.DEVICE : MaskType.GROUP).value());
                sceneContent3.setActionTypeName(z ? "设备" : "灯组");
                if (ProductRepository.isDaliLightGroup(role)) {
                    sceneContent3.setTimeSpace(1);
                } else {
                    sceneContent3.setTimeSpace(0);
                }
                sceneContent3.setExecuteCommand(createLocalExecuteCommand(role));
                arrayList.add(sceneContent3);
            }
        } else if (this.maskType.value() == MaskType.SCENE.value()) {
            for (Scene scene : (List) this.controlObject) {
                Scene.SceneContent sceneContent4 = new Scene.SceneContent();
                sceneContent4.setAction(PushContentParamKey.ACTION);
                sceneContent4.setActionType(MaskType.SCENE.value());
                sceneContent4.setActionTypeName(this.maskType.typeName());
                sceneContent4.setTimeSpace(1);
                sceneContent4.setExecuteCommand(createLocalExecuteCommand(scene));
                arrayList.add(sceneContent4);
            }
        }
        return arrayList;
    }

    public List<Scene.SceneContent> convert2ImportList(Context context, boolean isLocalScene) {
        ArrayList arrayList = new ArrayList();
        for (Scene.SceneContent sceneContent : this.actionList) {
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid);
                if (isLightDevice(deviceByDeviceId)) {
                    if (isLocalScene) {
                        sceneContent.setExecuteCommand(createLocalExecuteCommand(deviceByDeviceId));
                    } else {
                        this.maskType = MaskType.DEVICE;
                        sceneContent.setExecuteCommand(createExecuteCommand(deviceByDeviceId));
                    }
                    CmdAssistant.getLightCmdAssistant(deviceByDeviceId, new int[0]).sendOnOff(context, false);
                } else {
                    deviceParam.instruct = "";
                    deviceParam.option = "";
                    deviceParam.optionvalue = "";
                    sceneContent.setExecuteCommand(deviceParam);
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid);
                if (isLightGroup(groupByGroupId)) {
                    if (isLocalScene) {
                        sceneContent.setExecuteCommand(createLocalExecuteCommand(groupByGroupId));
                    } else {
                        this.maskType = MaskType.GROUP;
                        sceneContent.setExecuteCommand(createExecuteCommand(groupByGroupId));
                    }
                    CmdAssistant.getLightCmdAssistant(groupByGroupId, new int[0]).sendOnOff(context, false);
                } else {
                    groupParam.instruct = "";
                    groupParam.option = "";
                    groupParam.optionvalue = "";
                    sceneContent.setExecuteCommand(groupParam);
                }
            }
            arrayList.add(sceneContent);
        }
        return arrayList;
    }

    public List<Scene.SceneContent> convert2ImportListWithAction(boolean isLocalScene) {
        CharSwitch charSwitch;
        CharSwitch charSwitch2;
        ArrayList arrayList = new ArrayList();
        for (Scene.SceneContent sceneContent : this.actionList) {
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                if (isLocalScene) {
                    LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                    if (localDeviceParam.instruct.startsWith("{") && localDeviceParam.instruct.endsWith("}") && (charSwitch2 = (CharSwitch) GsonUtils.fromJson(localDeviceParam.instruct, CharSwitch.class)) != null) {
                        localDeviceParam.sceneAddr = Integer.parseInt(localDeviceParam.instruct.substring(18, 22), 16);
                        localDeviceParam.instruct = charSwitch2.getCharSwitch().substring(22);
                        localDeviceParam.instruct = localDeviceParam.instruct.substring(0, 2) + localDeviceParam.instruct.substring(6, localDeviceParam.instruct.length() - 2);
                    }
                    sceneContent.setExecuteCommand(localDeviceParam);
                } else {
                    DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                    if (!deviceParam.instruct.contains("{")) {
                        LocalDeviceParam localDeviceParam2 = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                        localDeviceParam2.instruct = localDeviceParam2.instruct.substring(0, 2) + StringUtils.addZeroForNum(Integer.toHexString(localDeviceParam2.sceneZone), 4) + localDeviceParam2.instruct.substring(2);
                        deviceParam.instruct = convertLocalCmdToCloud(localDeviceParam2.sceneAddr, localDeviceParam2.instruct);
                        if (sceneContent.getTimeSpace() == 0) {
                            sceneContent.setTimeSpace(1);
                        }
                    }
                    sceneContent.setExecuteCommand(deviceParam);
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                if (isLocalScene) {
                    LocalGroupParam localGroupParam = (LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class);
                    if (localGroupParam.instruct.startsWith("{") && localGroupParam.instruct.endsWith("}") && (charSwitch = (CharSwitch) GsonUtils.fromJson(localGroupParam.instruct, CharSwitch.class)) != null) {
                        localGroupParam.sceneAddr = Integer.parseInt(localGroupParam.instruct.substring(18, 22), 16);
                        localGroupParam.instruct = charSwitch.getCharSwitch().substring(22);
                        localGroupParam.instruct = localGroupParam.instruct.substring(0, 2) + localGroupParam.instruct.substring(6, localGroupParam.instruct.length() - 2);
                    }
                    sceneContent.setExecuteCommand(localGroupParam);
                } else {
                    GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
                    if (!groupParam.instruct.contains("{")) {
                        LocalGroupParam localGroupParam2 = (LocalGroupParam) sceneContent.getExecuteCommand(LocalGroupParam.class);
                        localGroupParam2.instruct = localGroupParam2.instruct.substring(0, 2) + StringUtils.addZeroForNum(Integer.toHexString(localGroupParam2.sceneZone), 4) + localGroupParam2.instruct.substring(2);
                        groupParam.instruct = convertLocalCmdToCloud(localGroupParam2.sceneAddr, localGroupParam2.instruct);
                    }
                    sceneContent.setExecuteCommand(groupParam);
                }
            }
            arrayList.add(sceneContent);
        }
        return arrayList;
    }

    private String convertLocalCmdToCloud(int address, String cmdStr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < cmdStr.length() / 2; i++) {
            int i2 = i * 2;
            arrayList.add(Integer.valueOf(Integer.parseInt(cmdStr.substring(i2, i2 + 2), 16)));
        }
        return createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, Injection.strategy().getCmdConvertStrategy(3).convert2cmd(new CmdBle(((Integer) arrayList.get(0)).intValue(), arrayList.subList(1, arrayList.size())), Integer.valueOf(address)).value(new Object[0]))));
    }

    public void closeLightDevice(List<Scene.SceneContent> sceneContentList, Context context) {
        for (Scene.SceneContent sceneContent : sceneContentList) {
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid);
                if (isLightDevice(deviceByDeviceId) && deviceParam.option.equals("4")) {
                    CmdAssistant.getLightCmdAssistant(deviceByDeviceId, new int[0]).sendOnOff(context, false);
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType())) {
                GroupParam groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class);
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid);
                if (isLightGroup(groupByGroupId) && groupParam.option.equals("4")) {
                    CmdAssistant.getLightCmdAssistant(groupByGroupId, new int[0]).sendOnOff(context, false);
                }
            }
        }
    }

    private boolean isLightGroup(Group group) {
        if (group == null) {
            return false;
        }
        return Integer.parseInt(group.getControlType()) == 101 || group.getGroupKey().equals(ProductId.BLE_GROUP_DIM_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_CT_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGB_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGBW_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGBWY_LIGHT) || group.getGroupKey().equals(ProductId.BLE_GROUP_RGBWY_CC_LIGHT) || group.getGroupKey().equals("06");
    }

    private boolean isLightDevice(Device device) {
        String productId = device.getProductId();
        return productId.equals(ProductId.ID_BLE_LIGHT_DIM) || productId.equals(ProductId.ID_BLE_LIGHT_CT) || productId.equals(ProductId.ID_BLE_LIGHT_RGB) || productId.equals(ProductId.ID_BLE_LIGHT_RGBW) || productId.equals(ProductId.ID_BLE_LIGHT_RGBWY) || productId.equals(ProductId.ID_BLE_SWITCH) || productId.equals(ProductId.ID_BLE_LIGHT_SPI) || productId.equals(ProductId.ID_KNOB_PANEL_E6A) || productId.equals(ProductId.ID_KNOB_PANEL_E6D) || productId.equals(ProductId.ID_KNOB_PANEL_E6M) || productId.equals(ProductId.ID_KNOB_PANEL_E6T);
    }

    public SuperPanelInfo.PanelKeyInfo convert2SuperPanelKeyInfo() {
        SuperPanelInfo.PanelKeyInfo panelKeyInfo = new SuperPanelInfo.PanelKeyInfo();
        panelKeyInfo.setActionType(this.maskType.value());
        panelKeyInfo.setActionTypeName(this.maskType.typeName());
        panelKeyInfo.setTimeSpace(this.timespace);
        panelKeyInfo.setKeywords(this.keyIndex);
        if (this.panelKeyLight != null) {
            JSONObject parseObject = JSONObject.parseObject(createKeyLightBindCommand(this.controlObject));
            parseObject.remove("instruct");
            parseObject.put("combinationCmd", (Object) GsonUtils.toJson(this.panelKeyLight));
            panelKeyInfo.setExecutecommand(parseObject.toJSONString());
        } else {
            panelKeyInfo.setExecutecommand(createExecuteCommand(this.controlObject));
        }
        if (this.maskType.value() == MaskType.SCENE.value()) {
            panelKeyInfo.setKeywordsname(((Scene) this.controlObject).getSceneName());
            return panelKeyInfo;
        }
        if (this.maskType.value() == MaskType.GROUP.value()) {
            panelKeyInfo.setKeywordsname(((Group) this.controlObject).getGroupName());
            return panelKeyInfo;
        }
        if (this.maskType.value() == MaskType.DEVICE.value()) {
            panelKeyInfo.setKeywordsname(((Device) this.controlObject).getDeviceName());
            return panelKeyInfo;
        }
        if (this.maskType.value() == MaskType.SONOS.value()) {
            panelKeyInfo.setKeywordsname(((Device) this.controlObject).getDeviceName());
            panelKeyInfo.setActionType(MaskType.SONOS.value());
            return panelKeyInfo;
        }
        if (this.maskType.value() == MaskType.VOICE_CALL.value()) {
            panelKeyInfo.setKeywordsname(((JSONObject) this.controlObject).getString(MtcUserConstants.MTC_USER_ID_USERNAME));
        }
        return panelKeyInfo;
    }

    public List<Automation.Action> convert2AutomationAction() {
        ArrayList arrayList = new ArrayList();
        if (this.maskType.value() == MaskType.SCENE.value()) {
            for (Scene scene : (List) this.controlObject) {
                Automation.Action action = new Automation.Action();
                action.setActiontype(this.maskType.value());
                action.setActiondelays(this.timespace);
                action.setParams(createExecuteCommand(scene));
                arrayList.add(action);
            }
        } else if (this.maskType.value() == MaskType.AUTOMATION.value()) {
            for (Automation automation : (List) this.controlObject) {
                Automation.Action action2 = new Automation.Action();
                action2.setActiontype(this.maskType.value());
                action2.setActiondelays(this.timespace);
                action2.setParams(createExecuteCommand(automation));
                arrayList.add(action2);
            }
        } else {
            if (this.maskType.value() == MaskType.DEVICE.value() || this.maskType.value() == MaskType.GROUP.value()) {
                Automation.Action action3 = new Automation.Action();
                action3.setActiontype(this.maskType.value());
                action3.setActiondelays(this.timespace);
                action3.setParams(createExecuteCommand(this.controlObject));
                action3.setActiontype(this.maskType.value());
                arrayList.add(action3);
                return arrayList;
            }
            if (this.maskType.value() == MaskType.APP_NOTICE.value()) {
                Automation.Action action4 = new Automation.Action();
                action4.setActiontype(this.maskType.value());
                action4.setActiondelays(this.timespace);
                action4.setParams(createExecuteCommand(this.controlObject));
                arrayList.add(action4);
                return arrayList;
            }
            if (this.maskType.value() == MaskType.DEVICE_GROUP.value()) {
                for (Role role : (List) this.controlObject) {
                    if (role instanceof Device) {
                        this.maskType = MaskType.DEVICE;
                    } else if (role instanceof Group) {
                        this.maskType = MaskType.GROUP;
                    }
                    Automation.Action action5 = new Automation.Action();
                    action5.setActiontype(this.maskType.value());
                    action5.setActiondelays(this.timespace);
                    action5.setParams(createExecuteCommand(role));
                    arrayList.add(action5);
                }
            }
        }
        return arrayList;
    }

    public Automation.Condition convert2AutomationCondition() {
        Automation.Condition condition = new Automation.Condition();
        condition.setParamtype(this.conditionParamType);
        condition.setEventtype(this.eventtype);
        Object obj = this.conditionParam;
        if (obj instanceof DeviceConditionParam) {
            DeviceConditionParam deviceConditionParam = (DeviceConditionParam) obj;
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceConditionParam.deviceid);
            if (deviceByDeviceId != null) {
                deviceConditionParam.unicastAddress = deviceByDeviceId.getUnicastAddress();
            }
        }
        condition.setParams(GsonUtils.toJson(this.conditionParam));
        Object obj2 = this.conditionParam;
        if (obj2 instanceof DeviceConditionParam) {
            DeviceConditionParam deviceConditionParam2 = (DeviceConditionParam) obj2;
            if (deviceConditionParam2.operator == 0 && deviceConditionParam2.value == 0) {
                condition.setParams(HelpUtils.removeObjectKey(deviceConditionParam2, new ArrayList(Arrays.asList("operator", ES6Iterator.VALUE_PROPERTY, "value2", "subIndex"))));
            }
        }
        return condition;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x024f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String createExecuteCommand(java.lang.Object r24) {
        /*
            Method dump skipped, instructions count: 1982
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.SceneHelper.createExecuteCommand(java.lang.Object):java.lang.String");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:172:0x04d3  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x04f0  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x050b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String createLocalExecuteCommand(java.lang.Object r22) {
        /*
            Method dump skipped, instructions count: 1570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.SceneHelper.createLocalExecuteCommand(java.lang.Object):java.lang.String");
    }

    private void createLightCloseCmd(Object object) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        if (ProductRepository.isDaliLightGroup(object)) {
            lightCmdParam.setZoneNum(DaliProHelper.getZoneNum(object));
        } else if (ProductRepository.isRelaySeparationSub(object)) {
            lightCmdParam.setZoneNum(RelaySeparationHelper.getZoneNum(object));
        }
        lightCmdParam.setOn(false);
        lightCmdParam.addExtParam(OPTION, "4");
        lightCmdParam.addExtParam(OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.close));
        this.cmdParam = lightCmdParam;
    }

    public void selectCurrentState() {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(51);
        lightCmdParam.addExtParam(OPTION, SceneConstants.OPTION_CURRENT_STATE);
        lightCmdParam.addExtParam(OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.app_str_local_scene_current_state));
        instance().cmdParam = lightCmdParam;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String createKeyLightBindCommand(Object obj) {
        DeviceParam deviceParam;
        if (this.maskType.value() == MaskType.GROUP.value()) {
            GroupParam groupParam = new GroupParam();
            groupParam.groupid = ((Group) obj).getGroupId();
            BaseCmdParam baseCmdParam = this.cmdParam;
            deviceParam = groupParam;
            if (baseCmdParam != null) {
                groupParam.option = (String) baseCmdParam.getExtParam(OPTION);
                groupParam.optionvalue = (String) this.cmdParam.getExtParam(OPTION_VALUE);
                deviceParam = groupParam;
            }
        } else if (this.maskType.value() == MaskType.DEVICE.value()) {
            DeviceParam deviceParam2 = new DeviceParam();
            deviceParam2.deviceid = ((Device) obj).getDeviceId();
            BaseCmdParam baseCmdParam2 = this.cmdParam;
            deviceParam = deviceParam2;
            if (baseCmdParam2 != null) {
                deviceParam2.option = (String) baseCmdParam2.getExtParam(OPTION);
                deviceParam2.optionvalue = (String) this.cmdParam.getExtParam(OPTION_VALUE);
                deviceParam = deviceParam2;
            }
        } else {
            deviceParam = null;
        }
        this.cmdParam = null;
        return GsonUtils.toJson(deviceParam);
    }

    public String getLocalExecuteCommand(int sceneNum) {
        BaseCmd executeLocalScene = CmdBleFactory.executeLocalScene(1, sceneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65025);
        String createCmdData = createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData3(ctrlPackage.getAddress(), Injection.strategy().getCmdConvertStrategy(3).convert2cmd(executeLocalScene, Integer.valueOf(ctrlPackage.getAddress())).value(new Object[0]))));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("instruct", (Object) createCmdData);
        return jSONObject.toJSONString();
    }

    public String getDaliSceneExecuteCommand(Scene scene) {
        BaseCmd executeDaliScene = CmdBleFactory.executeDaliScene(DaliProHelper.BROADCAST_ADD, scene.getSceneNum());
        CtrlPackage ctrlPackage = (CtrlPackage) SmartUtils.getICtrlConverter().convert(Injection.repo().device().getDeviceByDeviceId(scene.getMacdeviceid()));
        String createCmdData = createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData3(ctrlPackage.getAddress(), Injection.strategy().getCmdConvertStrategy(3).convert2cmd(executeDaliScene, Integer.valueOf(ctrlPackage.getAddress())).value(new Object[0]))));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("instruct", (Object) createCmdData);
        return jSONObject.toJSONString();
    }

    public static String createCmdData(String cmd) {
        CharSwitch charSwitch = new CharSwitch();
        charSwitch.setCharSwitch(cmd);
        return GsonUtils.toJson(charSwitch);
    }

    public static String getLightCmdData(String cmd) {
        CharSwitch charSwitch;
        if (cmd.startsWith("{") && cmd.endsWith("}") && (charSwitch = (CharSwitch) GsonUtils.fromJson(cmd, CharSwitch.class)) != null) {
            String substring = charSwitch.getCharSwitch().substring(22);
            cmd = substring.substring(0, 2) + substring.substring(6, substring.length() - 2);
        }
        if (cmd.length() > 10 && "D1".equalsIgnoreCase(cmd.substring(0, 2))) {
            return cmd.substring(2);
        }
        if (cmd.length() <= 4 || !"D8".equalsIgnoreCase(cmd.substring(0, 2))) {
            return cmd.length() >= 4 ? cmd : "";
        }
        if ("01".equals(cmd.substring(2, 4))) {
            return cmd.substring(2);
        }
        if ("02".equals(cmd.substring(2, 4))) {
            return cmd.substring(4);
        }
        return "";
    }

    public static String getCmdData(String cmd) {
        CharSwitch charSwitch;
        if (!cmd.startsWith("{") || !cmd.endsWith("}") || (charSwitch = (CharSwitch) GsonUtils.fromJson(cmd, CharSwitch.class)) == null) {
            return cmd;
        }
        String substring = charSwitch.getCharSwitch().substring(22);
        return substring.substring(0, 2) + substring.substring(6, substring.length() - 2);
    }

    public void initFloorRoom(long floorId, long roomId) {
        this.orgFloorId = floorId;
        this.orgRoomId = roomId;
    }

    public static void goSelectAction(Activity activity, int selectType, long placeId) {
        goSelectAction(activity, selectType, placeId, -1);
    }

    public static void goSelectAction(Activity activity, int selectType, long placeId, int sceneNum) {
        goSelectAction(activity, selectType, placeId, sceneNum, false);
    }

    public static void goSelectAction(Activity activity, int selectType, long placeId, int sceneNum, boolean dynamic) {
        NavUtils.Builder destination;
        DeviceParam deviceParam;
        if (selectType == 1) {
            destination = NavUtils.destination(ActSelectDeviceForAction.class);
        } else if (selectType == 2) {
            destination = NavUtils.destination(ActSelectGroupForAction.class);
        } else if (selectType == 3) {
            destination = NavUtils.destination(ActSelectAutomationForAction.class);
        } else if (selectType == 4) {
            destination = NavUtils.destination(ActSelectSceneForAction.class);
        } else if (selectType == 6) {
            destination = NavUtils.destination(ActSelectDeviceGroupForAction.class);
        } else if (selectType == 7) {
            destination = NavUtils.destination(ActSuperPanelBindKeyForAction.class);
            if (instance().panelKeyInfo != null && MaskType.isDeviceAction(instance().panelKeyInfo.getActionType()) && (deviceParam = (DeviceParam) instance().panelKeyInfo.getExecutecommand(DeviceParam.class)) != null) {
                instance().selectDeviceParam = deviceParam;
            }
        } else if (selectType != 9) {
            destination = null;
        } else {
            destination = NavUtils.destination(ActSelectDeviceGroupForLocalAction.class);
            destination.withInt(Constants.SCENE_NUM, sceneNum);
            destination.withBoolean(Constants.IS_DYNAMIC_SCENE, dynamic);
        }
        if (destination != null) {
            destination.withLong(Constants.PLACE_ID, placeId).withLong(Constants.FLOOR_ID, instance().orgFloorId).withLong(Constants.ROOM_ID, instance().orgRoomId).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(activity);
        }
    }

    public static void goSelectVoiceAndMusic(Activity activity, int selectType, long placeId, int sceneNum) {
        NavUtils.Builder destination = NavUtils.destination(ActSelectSuperPanel.class);
        destination.withInt(Constants.SCENE_NUM, sceneNum).withInt(Constants.LOCAL_SCENE_TYPE, selectType);
        destination.withLong(Constants.PLACE_ID, placeId).withLong(Constants.FLOOR_ID, instance().orgFloorId).withLong(Constants.ROOM_ID, instance().orgRoomId).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(activity);
    }

    public static void goSelectSecurityScene(Activity activity, int selectType, long placeId, int sceneNum) {
        NavUtils.Builder destination = NavUtils.destination(ActSelectSuperPanel.class);
        destination.withInt(Constants.SCENE_NUM, sceneNum).withInt(Constants.LOCAL_SCENE_TYPE, selectType);
        destination.withLong(Constants.PLACE_ID, placeId).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(activity);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x032f, code lost:
    
        if (r15.equals(com.ltech.smarthome.model.product.ProductId.ID_WIFI_LIGHT_CT) == false) goto L6;
     */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0671  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x069b  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x06ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.ltech.smarthome.utils.NavUtils.Builder goSelectAction(java.lang.Object r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 2328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.SceneHelper.goSelectAction(java.lang.Object, boolean):com.ltech.smarthome.utils.NavUtils$Builder");
    }

    public NavUtils.Builder goSelectAction(Object object) {
        return goSelectAction(object, false);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:272:0x07a8  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x073c  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0771  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.ltech.smarthome.utils.NavUtils.Builder goSelectLocalAction(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 2416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.SceneHelper.goSelectLocalAction(java.lang.Object):com.ltech.smarthome.utils.NavUtils$Builder");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static String getDeviceActionString(Context context, Device device, String option, String optionValue, String instruct, int sceneZone, String songNames) {
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2133025272:
                if (productId.equals(ProductId.CG485_SUB_DEVICE)) {
                    c2 = 0;
                    break;
                }
                break;
            case -2133025271:
                if (productId.equals(ProductId.CGRS8_SUB_DEVICE)) {
                    c2 = 1;
                    break;
                }
                break;
            case -2126431781:
                if (productId.equals(ProductId.ID_IR_DIY)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 16;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 17;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 18;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 19;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 20;
                    break;
                }
                break;
            case 2003796:
                if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                    c2 = 21;
                    break;
                }
                break;
            case 2003797:
                if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                    c2 = 22;
                    break;
                }
                break;
            case 2003798:
                if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                    c2 = 23;
                    break;
                }
                break;
            case 2256539:
                if (productId.equals(ProductId.ID_IR_STB)) {
                    c2 = 24;
                    break;
                }
                break;
            case 2256540:
                if (productId.equals(ProductId.ID_IR_TV)) {
                    c2 = 25;
                    break;
                }
                break;
            case 2256541:
                if (productId.equals(ProductId.ID_IR_TV_BOX)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 2256543:
                if (productId.equals(ProductId.ID_IR_AC)) {
                    c2 = 27;
                    break;
                }
                break;
            case 2256544:
                if (productId.equals(ProductId.ID_IR_PRO)) {
                    c2 = 28;
                    break;
                }
                break;
            case 2256546:
                if (productId.equals(ProductId.ID_IR_FAN)) {
                    c2 = 29;
                    break;
                }
                break;
            case 69952758:
                if (productId.equals(ProductId.ID_IR_AIR_CLEANER)) {
                    c2 = 30;
                    break;
                }
                break;
            case 69952759:
                if (productId.equals(ProductId.ID_IR_WATER_HEATER)) {
                    c2 = 31;
                    break;
                }
                break;
            case 69953013:
                if (productId.equals(ProductId.ID_IR_CURTAIN)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 69953014:
                if (productId.equals(ProductId.ID_IR_HANGER)) {
                    c2 = '!';
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = '#';
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = '$';
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = '%';
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '(';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = ')';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '*';
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '+';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = '/';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '0';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = '1';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '2';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '3';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '4';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '5';
                    break;
                }
                break;
            case 2061235487:
                if (productId.equals(ProductId.ID_WIFI_SONOS)) {
                    c2 = '6';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = '7';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case '\t':
                return getRs485Action(instruct, device);
            case 1:
            case 2:
            case '\r':
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '#':
            case '/':
            case '0':
            case '7':
                return optionValue;
            case 3:
            case '\b':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '+':
            case '1':
                return getEurPanelAction(context, option, optionValue, ProductRepository.isAsPanel(device) ? AsHelper.convertType(device) : EurHelper.convertType(device), instruct, sceneZone, device);
            case 4:
            case 5:
            case 6:
            case 7:
            case 17:
            case 18:
            case 19:
            case '$':
            case '*':
            case '3':
            case '4':
                String[] switchNameArray = RelateInfoUtils.getSwitchNameArray(device);
                if (RelaySeparationHelper.isRelaySeparationDevice(device)) {
                    switchNameArray = RelaySeparationHelper.getRelaySubNameArray(device);
                }
                return getSmartPanelAction(context, switchNameArray, option, optionValue);
            case '\n':
            case 11:
            case 14:
            case 20:
            case '%':
            case '-':
            case '.':
                return getSuperPanelAction(context, option, optionValue, instruct, device, songNames);
            case '\f':
            case '2':
            case '5':
                return getBleCurtainAction(context, option, optionValue);
            case 15:
            case 16:
                return getSmartPanelAction(context, RelaySeparationHelper.getRelaySubNameArray(device), option, optionValue);
            case '\"':
                return getLightSwitchAction(context, option, optionValue);
            case ',':
                return getSpiLightAction(context, option, optionValue, instruct, device);
            case '6':
                return getSonosAction(context, option, optionValue);
            default:
                return getLightAction(context, option, optionValue, ProductRepository.getLightColorType((Object) device), instruct, device);
        }
    }

    public static String getDeviceActionString(Context context, Device device, LocalDeviceParam deviceParam) {
        return getDeviceActionString(context, device, deviceParam.option, deviceParam.optionvalue, deviceParam.instruct, deviceParam.sceneZone, deviceParam.songNames);
    }

    public static String getDeviceActionString(Context context, Device device, DeviceParam deviceParam) {
        return getDeviceActionString(context, device, deviceParam.option, deviceParam.optionvalue, deviceParam.instruct, deviceParam.sceneZone, deviceParam.songNames);
    }

    private static String getSonosAction(Context context, String option, String optionValue) {
        String[] sonosActionName = NameRepository.getSonosActionName(context);
        try {
            int parseInt = Integer.parseInt(option) - 1;
            if (parseInt == 0) {
                return com.blankj.utilcode.util.StringUtils.getString(R.string.musicplayer_action_play) + " " + ((SonosResponse.Favorites) GsonUtils.fromJson(optionValue, new TypeToken<SonosResponse.Favorites>() { // from class: com.ltech.smarthome.ui.scene.SceneHelper.2
                }.getType())).getName();
            }
            if (parseInt < 10) {
                return sonosActionName[parseInt];
            }
            return sonosActionName[parseInt] + optionValue + "%";
        } catch (Exception e) {
            e.printStackTrace();
            return optionValue;
        }
    }

    public static String getSmartPanelAction(Context context, String[] array, String option, String optionValue) {
        option.hashCode();
        int i = 0;
        if (option.equals("0")) {
            if (isInteger(optionValue)) {
                if (Integer.parseInt(optionValue) < 4) {
                    return context.getString(R.string.switch_on) + array[Integer.parseInt(optionValue) < array.length ? Integer.parseInt(optionValue) : array.length - 1];
                }
                int parseInt = Integer.parseInt(optionValue) - 4;
                StringBuilder sb = new StringBuilder();
                while (i < 4) {
                    if (((parseInt >> i) & 1) != 0) {
                        if (sb.length() == 0) {
                            sb.append(array[i]);
                        } else {
                            sb.append("、" + array[i]);
                        }
                    }
                    i++;
                }
                if (sb.length() == 0) {
                    return "";
                }
                return context.getString(R.string.switch_on) + sb.toString();
            }
        } else {
            if (!option.equals("1")) {
                return "";
            }
            if (isInteger(optionValue)) {
                if (Integer.parseInt(optionValue) < 4) {
                    return context.getString(R.string.switch_close) + array[Integer.parseInt(optionValue) < array.length ? Integer.parseInt(optionValue) : array.length - 1];
                }
                int parseInt2 = Integer.parseInt(optionValue) - 4;
                StringBuilder sb2 = new StringBuilder();
                while (i < 4) {
                    if (((parseInt2 >> i) & 1) != 0) {
                        if (sb2.length() == 0) {
                            sb2.append(array[i]);
                        } else {
                            sb2.append("、" + array[i]);
                        }
                    }
                    i++;
                }
                if (sb2.length() == 0) {
                    return "";
                }
                return context.getString(R.string.switch_close) + sb2.toString();
            }
        }
        return optionValue;
    }

    private static String getLightSwitchAction(Context context, String option, String optionValue) {
        option.hashCode();
        if (option.equals("4")) {
            return context.getString(R.string.off);
        }
        return !option.equals("7") ? optionValue : context.getString(R.string.on);
    }

    private static String getRs485Action(String instruct, Device device) {
        if (instruct.length() > 6) {
            return Cg485Helper.getInstructByCmd(device, getCmdData(instruct).substring(6));
        }
        return "";
    }

    private static String getEurPanelAction(Context context, String option, String optionValue, int controlType, String instruct, int sceneZone, Object object) {
        int i;
        option.hashCode();
        switch (option) {
            case "0":
                if (ProductId.ID_EUR_PANEL_EB6.equals(EurHelper.getEurProductId(object))) {
                    return context.getString(R.string.switch_on);
                }
                break;
            case "4":
                if (ProductId.ID_EUR_PANEL_EB6.equals(EurHelper.getEurProductId(object))) {
                    return context.getString(R.string.close);
                }
                break;
            case "11":
            case "12":
                StringBuilder sb = new StringBuilder();
                for (i = 0; i < 4; i++) {
                    if (((sceneZone >> i) & 1) != 0) {
                        if (sb.length() == 0) {
                            sb.append(i + 1);
                        } else {
                            sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                            sb.append(i + 1);
                        }
                    }
                }
                if ("11".equals(option)) {
                    if (sb.length() == 0) {
                        return "";
                    }
                    return context.getString(R.string.open_zone) + ((Object) sb);
                }
                if (sb.length() == 0) {
                    return "";
                }
                return context.getString(R.string.close_zone) + ((Object) sb);
        }
        return getLightAction(context, option, optionValue, controlType, instruct, object);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getSuperPanelAction(android.content.Context r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, com.ltech.smarthome.model.bean.Device r22, java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 856
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.SceneHelper.getSuperPanelAction(android.content.Context, java.lang.String, java.lang.String, java.lang.String, com.ltech.smarthome.model.bean.Device, java.lang.String):java.lang.String");
    }

    private static String getMusicVolumeAndLoop(Context context, int volume, int loop) {
        String str = "";
        if (volume == -1 || loop == -1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (volume != 255) {
            StringBuilder sb2 = new StringBuilder(" ");
            sb2.append(String.format(Locale.US, context.getString(R.string.volume) + "%d%%", Integer.valueOf(volume)));
            str = sb2.toString();
        }
        sb.append(str);
        sb.append(" ");
        sb.append(context.getString(loop == 1 ? R.string.play_loop : R.string.only_once));
        return sb.toString();
    }

    public static String getSceneActionString(Context context, Scene scene) {
        if (scene.getSceneType() == 2) {
            return context.getString(R.string.local_scene);
        }
        if (scene.getSceneType() == 4) {
            return context.getString(R.string.dali_scene);
        }
        return context.getString(R.string.cloud_scene);
    }

    public static String getGroupActionString(Context context, String option, String optionValue, Group group, String instruct, int sceneZone) {
        int parseInt = group == null ? 0 : Integer.parseInt(group.getControlType());
        switch (parseInt) {
            case 7:
                return getLightSwitchAction(context, option, optionValue);
            case 8:
            case 9:
            case 10:
            case 11:
            case 18:
            case 21:
            case 26:
                String[] switchNameArray = RelateInfoUtils.getSwitchNameArray(group);
                if (RelaySeparationHelper.isRelaySeparationDevice(group)) {
                    String[] relaySubNameArray = RelaySeparationHelper.getRelaySubNameArray(group);
                    if (relaySubNameArray.length > 0) {
                        switchNameArray = relaySubNameArray;
                    }
                }
                return getSmartPanelAction(context, switchNameArray, option, optionValue);
            case 12:
            case 14:
            case 16:
                return getBleCurtainAction(context, option, optionValue);
            case 13:
            case 15:
            case 25:
                return optionValue;
            case 17:
            case 20:
            default:
                return getLightAction(context, option, optionValue, parseInt, instruct, group);
            case 19:
                return getSmartPanelAction(context, RelaySeparationHelper.getRelaySubNameArray(group), option, optionValue);
            case 22:
            case 23:
            case 24:
            case 27:
            case 28:
            case 29:
            case 30:
                return getEurPanelAction(context, option, optionValue, EurHelper.convertType(group), instruct, sceneZone, group);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00a5, code lost:
    
        if (r8.equals("2") == false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getLightAction(android.content.Context r7, java.lang.String r8, java.lang.String r9, int r10, java.lang.String r11, java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.SceneHelper.getLightAction(android.content.Context, java.lang.String, java.lang.String, int, java.lang.String, java.lang.Object):java.lang.String");
    }

    private static String getSpiLightAction(Context context, String option, String optionValue, String instruct, Object obj) {
        LHomeLog.i(SceneHelper.class, "getLightAction:+option=" + option + "___optionValue=" + optionValue);
        option.hashCode();
        switch (option) {
            case "0":
                return getLightStaticAction(context, 17, optionValue, getLightCmdData(instruct), obj);
            case "4":
                return context.getString(R.string.light_off_1);
            case "10":
                String[] spiModeName = NameRepository.getSpiModeName(context);
                if (!"".equals(instruct)) {
                    int[] convertStringToArray = BitUtils.convertStringToArray(getCmdData(instruct));
                    if (convertStringToArray.length > 0 && convertStringToArray[3] == 0) {
                        return NameRepository.getSpiModeName(context)[BitUtils.convertIntToBit(convertStringToArray[9] | (convertStringToArray[8] << 8))];
                    }
                } else if (isInteger(optionValue) && Integer.parseInt(optionValue) < spiModeName.length) {
                    return spiModeName[Integer.parseInt(optionValue) - 1];
                }
                return "";
            case "11":
                return context.getString(R.string.play_list);
            default:
                return optionValue;
        }
    }

    public static String getLightStaticAction(Context context, int controlType, String optionValue, String instruct, Object obj) {
        int convertDaliType = ProductRepository.isDaliLightGroup(obj) ? DaliProHelper.convertDaliType(obj) : controlType;
        if (convertDaliType == 1) {
            if (instruct.length() > 10) {
                return context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2PercentHasBelowZero(Integer.parseInt(instruct.substring(6, 8), 16)) + "%";
            }
            return context.getString(R.string.dim_static);
        }
        if (convertDaliType == 2) {
            if (instruct.length() > 10) {
                int parseInt = Integer.parseInt(instruct.substring(6, 8), 16);
                return context.getString(R.string.ct) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.getKValue(Integer.parseInt(instruct.substring(8, 10), 16), obj) + " " + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt) + "%";
            }
            return context.getString(R.string.ct_static);
        }
        if (instruct.length() >= 12 && "C6".equalsIgnoreCase(instruct.substring(0, 2))) {
            int parseInt2 = Integer.parseInt(instruct.substring(2, 4), 16);
            if (parseInt2 == 3) {
                return context.getString(R.string.action_xxy, Float.valueOf(Integer.parseInt(instruct.substring(4, 8), 16) / 10000.0f), Float.valueOf(Integer.parseInt(instruct.substring(8, 12), 16) / 10000.0f));
            }
            if (parseInt2 == 4) {
                return context.getString(R.string.action_hsl, Integer.valueOf(Integer.parseInt(instruct.substring(4, 8), 16)), Integer.valueOf(Integer.parseInt(instruct.substring(8, 10), 16)), Integer.valueOf(Integer.parseInt(instruct.substring(10, 12), 16)));
            }
            if (parseInt2 == 5) {
                int parseInt3 = Integer.parseInt(instruct.substring(4, 8), 16);
                int parseInt4 = Integer.parseInt(instruct.substring(8, 12), 16);
                if (parseInt4 > 200) {
                    parseInt4 -= 65536;
                }
                int parseInt5 = Integer.parseInt(instruct.substring(12, 14), 16);
                if (!TextUtils.isEmpty(optionValue)) {
                    return context.getString(R.string.action_cct_point, Integer.valueOf(parseInt3), optionValue, LightUtils.brt2ProgressHasBelowZero(parseInt5) + "%");
                }
                return context.getString(R.string.action_cct, Integer.valueOf(parseInt3), Float.valueOf(parseInt4 / 10000.0f), LightUtils.brt2ProgressHasBelowZero(parseInt5) + "%");
            }
        } else {
            if (instruct.length() >= 16 && instruct.startsWith("53")) {
                int parseInt6 = Integer.parseInt(instruct.substring(2, 4), 16);
                String substring = instruct.substring(4, 10);
                int parseInt7 = Integer.parseInt(instruct.substring(10, 12), 16);
                return MqttTopic.MULTI_LEVEL_WILDCARD + substring + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt6) + "% " + context.getString(R.string.cw_ct) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.getKValue(Integer.parseInt(instruct.substring(14, 16), 16), obj) + " " + context.getString(R.string.wy_brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt7) + "%";
            }
            if (instruct.length() >= 14 && "DA".equalsIgnoreCase(instruct.substring(0, 2))) {
                return "R:" + Integer.parseInt(instruct.substring(2, 4), 16) + " G:" + Integer.parseInt(instruct.substring(4, 6), 16) + " B:" + Integer.parseInt(instruct.substring(6, 8), 16) + " C:" + Integer.parseInt(instruct.substring(8, 10), 16) + " W:" + Integer.parseInt(instruct.substring(10, 12), 16) + " " + context.getString(R.string.total_brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(Integer.parseInt(instruct.substring(12, 14), 16)) + "%";
            }
            if (instruct.length() >= 12) {
                String substring2 = instruct.substring(0, 6);
                int parseInt8 = Integer.parseInt(instruct.substring(6, 8), 16);
                int parseInt9 = Integer.parseInt(instruct.substring(8, 10), 16);
                int parseInt10 = Integer.parseInt(instruct.substring(10, 12), 16);
                if (convertDaliType == 3 || convertDaliType == 17) {
                    return MqttTopic.MULTI_LEVEL_WILDCARD + substring2 + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt10) + "%";
                }
                if (convertDaliType == 4) {
                    return MqttTopic.MULTI_LEVEL_WILDCARD + substring2 + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt10) + "% " + context.getString(R.string.w) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt8) + "%";
                }
                return MqttTopic.MULTI_LEVEL_WILDCARD + substring2 + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt10) + "% " + context.getString(R.string.cw1) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + parseInt9 + " " + LightUtils.brt2ProgressHasBelowZero(parseInt8) + "%";
            }
        }
        return context.getString(R.string.rgb_static);
    }

    public static String getLightStaticActionWithType(Context context, String instruct, Object obj) {
        int parseInt = Integer.parseInt(instruct.substring(0, 2), 16);
        if (parseInt == 1) {
            return context.getString(R.string.ct) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + Integer.parseInt(instruct.substring(2, 6), 16) + "K " + context.getString(R.string.total_brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(Integer.parseInt(instruct.substring(6, 8), 16)) + "%";
        }
        if (parseInt == 16) {
            return context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2PercentHasBelowZero(Integer.parseInt(instruct.substring(2, 4), 16)) + "%";
        }
        if (parseInt == 32) {
            int parseInt2 = Integer.parseInt(instruct.substring(2, 4), 16);
            return context.getString(R.string.ct) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.getKValue(Integer.parseInt(instruct.substring(4, 6), 16), obj) + " " + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt2) + "%";
        }
        if (instance().bindingType == 2) {
            return context.getString(R.string.color);
        }
        int parseInt3 = Integer.parseInt(instruct.substring(2, 4), 16);
        return MqttTopic.MULTI_LEVEL_WILDCARD + instruct.substring(4, 10) + context.getString(R.string.brt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt3) + "%";
    }

    public String getConvertCmd(BaseCmdParam cmdParam) {
        BaseCmd convert2cmd = Injection.strategy().getCmdConvertStrategy(2).convert2cmd(cmdParam);
        String str = StringUtils.demToHexDouble(convert2cmd.getFunCode()) + StringUtils.byte2Str(convert2cmd.value(new Object[0]));
        if (str.length() < 6) {
            return str;
        }
        return str.substring(0, 2) + str.substring(6);
    }

    public void goSelectCondition(Activity activity, Device device, int index) {
        NavUtils.Builder destination;
        if (ProductId.ID_KEY_SWITCH_1.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_KEY_SWITCH_2.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_KEY_SWITCH_3.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_KEY_SWITCH_4.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_BODY_SENSOR.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorConditionAction.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("01"));
        } else if (ProductId.ID_SENSOR_MR03.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorConditionAction.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("03"));
        } else if (ProductId.ID_SENSOR_MR04.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorConditionAction.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("04"));
        } else if (ProductId.ID_SENSOR_MS03.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorTriggerCondition.class);
        } else if (ProductId.ID_SENSOR_HSD.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorTriggerCondition.class);
        } else if (ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_SCENE_PANEL_S8.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 8);
        } else if (ProductId.ID_SMART_PANEL_S6B.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionActionDialog.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 6);
        } else if (ProductId.ID_SMART_SWITCH_S6_PRO.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S6.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionActionDialog.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 6);
        } else if (ProductId.ID_SMART_SWITCH_S1_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_SWITCH_SQ.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_SQB.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_SQ_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_DRY_CONTACT_TO_BLE.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            if (device.getParam(DryContactBleParam.class) != null) {
                if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 0) {
                    destination.withInt(Constants.SWITCH_KEY_SUM, 16);
                } else if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 1) {
                    destination.withInt(Constants.SWITCH_KEY_SUM, 17);
                } else {
                    destination.withInt(Constants.SWITCH_KEY_SUM, 15);
                }
            } else {
                destination.withInt(Constants.SWITCH_KEY_SUM, 15);
            }
        } else if (ProductId.ID_ANDROID_SUPER_PANEL.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 8);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else {
            if (ProductId.ID_RS485_BLE.equals(device.getProductId())) {
                Cg485Helper.showConditionDialog((FragmentActivity) activity, device, index);
            } else if (ProductRepository.isAsPanel(device.getProductId()) || ProductRepository.isEurPanel(device.getProductId()) || ProductRepository.isRcB(device.getProductId())) {
                destination = NavUtils.destination(ActSelecEurConditionAction.class);
            } else if (ProductId.ID_DOOR_SENSOR.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectDefaultConditionDialog.class);
            } else if (ProductId.ID_SMART_PANEL_GQ.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_GQX.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 1);
            } else if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 12);
            } else if (ProductId.ID_RC4S.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectZoneConditionAction.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 4);
            } else if (ProductId.ID_BLE_CURTAIN.equals(device.getProductId()) || ProductId.ID_BLE_CURTAIN_CG_CURH3.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectCurtainConditionAction.class);
            }
            destination = null;
        }
        if (destination != null) {
            destination.withLong("device_id", device.getDeviceId()).withString(Constants.FLOOR_NAME, device.getFloorName()).withString(Constants.ROOM_NAME, device.getRoomName()).withString("device_name", device.getDeviceName()).withString(Constants.PRODUCT_ID, device.getProductId()).withString(Constants.MAC_ADDRESS, device.getWifiMac()).withInt(Constants.CONDITION_INDEX, index).withDefaultRequestCode().navigation(activity);
        }
    }

    public void goSelectStatusCondition(Activity activity, Device device, int index) {
        NavUtils.Builder destination;
        if (ProductId.ID_BODY_SENSOR.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorStatusDetailCondition.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("01"));
        } else if (ProductId.ID_SENSOR_MR03.equals(device.getProductId()) || ProductId.ID_SENSOR_MR04.equals(device.getProductId()) || ProductId.ID_SENSOR_MS03.equals(device.getProductId()) || ProductId.ID_SENSOR_HSD.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorStatusDetailCondition.class);
        } else if (ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_SMART_SWITCH_S6_PRO.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S6.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_SWITCH_S1_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_BLE_CURTAIN.equals(device.getProductId()) || ProductId.ID_BLE_CURTAIN_CG_CURH3.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectCurtainStatusDetailCondition.class);
        } else {
            destination = (ProductId.ID_BLE_LIGHT_DIM.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_CT.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_RGB.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_RGBW.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_RGBWY.equals(device.getProductId()) || ProductId.ID_BLE_SWITCH.equals(device.getProductId())) ? NavUtils.destination(ActSelectLightStatusDetailCondition.class) : null;
        }
        if (destination != null) {
            destination.withLong("device_id", device.getDeviceId()).withString(Constants.FLOOR_NAME, device.getFloorName()).withString(Constants.ROOM_NAME, device.getRoomName()).withString("device_name", device.getDeviceName()).withString(Constants.PRODUCT_ID, device.getProductId()).withString(Constants.MAC_ADDRESS, device.getWifiMac()).withInt(Constants.CONDITION_INDEX, index).withDefaultRequestCode().navigation(activity);
        }
    }

    public void goSelectZone(Activity activity, boolean isLocalScene) {
        NavUtils.destination(ActSelectZone.class).withBoolean(Constants.IS_LOCAL_SCENE, isLocalScene).withDefaultRequestCode().navigation(activity);
    }

    public void saveSelectResult(LifecycleOwner owner, final IAction<Boolean> result) {
        if (this.panelid == 0) {
            result.act(false);
        } else {
            final SuperPanelInfo.PanelKeyInfo convert2SuperPanelKeyInfo = instance().convert2SuperPanelKeyInfo();
            ((ObservableSubscribeProxy) Injection.net().setSuperPanelKeywordsInfo(instance().panelid, convert2SuperPanelKeyInfo).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.SceneHelper$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.SceneHelper$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ((BaseNormalActivity) ActivityUtils.getTopActivity()).dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.SceneHelper$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SceneHelper.lambda$saveSelectResult$2(SuperPanelInfo.PanelKeyInfo.this, result, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    static /* synthetic */ void lambda$saveSelectResult$2(SuperPanelInfo.PanelKeyInfo panelKeyInfo, IAction iAction, Object obj) throws Exception {
        Injection.repo().device().setSuperPanelKeyInfo(instance().panelid, panelKeyInfo);
        SmartToast.showShort(R.string.save_success);
        iAction.act(true);
    }

    public static int getSceneIcon(Context context, int iconPos) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        int[] sceneIcons = IconRepository.getSceneIcons(context);
        if (iconPos >= 0 && iconPos < sceneIcons.length) {
            return sceneIcons[iconPos];
        }
        return sceneIcons[0];
    }

    public static int getAutomationPic(Context context, int index) {
        int[] automationPic = IconRepository.getAutomationPic(context);
        if (index > 0 && index < automationPic.length) {
            return automationPic[index];
        }
        return automationPic[0];
    }

    public Object getControlObject() {
        return this.controlObject;
    }

    public static boolean isInteger(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(str).matches();
    }

    public static AppNoticeParam createAppNoticeParam(Context context, String automationName) {
        AppNoticeParam appNoticeParam = new AppNoticeParam();
        appNoticeParam.title = context.getString(R.string.app_str_notice);
        appNoticeParam.body = "\"" + automationName + "\"" + context.getString(R.string.app_str_has_been_executed);
        appNoticeParam.users = new ArrayList();
        return appNoticeParam;
    }

    public boolean isSupportNewMusic(Device device) {
        if (device == null || device.getMcuversion() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122042815485901":
                if (device.getMcuversion().compareTo("1.1.5") >= 0) {
                }
                break;
            case "122080911090801":
            case "121052512023201":
                if (device.getMcuversion().compareTo("2.2.6") >= 0) {
                }
                break;
        }
        return false;
    }

    public boolean needUseOptionValue(String option) {
        option.hashCode();
        return option.equals("11");
    }
}