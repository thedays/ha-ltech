package com.ltech.smarthome.ui.device.cg485;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.model.device_param.Rs8ExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.model.scene_param.LocalGroupParam;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.ConfigDeviceBean;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectCg485Action;
import com.ltech.smarthome.ui.scene.ActSelectCg485Condition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.select.ActSelectAction;
import com.ltech.smarthome.ui.select.ActSelectSceneForAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class Cg485Helper {
    public static int categoryPosition;
    public static Device controlDevice;
    public static int instructPosition;
    public static SuperPanelExtParam extParam = new SuperPanelExtParam();
    public static ContentState contentState = new ContentState();
    public static boolean isDelete485ToBle = false;
    public static boolean isAddBleTo485 = false;
    public static String[] library = {"30 00 00 00 01", "30 00 00 00 02", "30 00 00 00 03", "30 00 00 00 04", "30 00 00 00 05", "30 00 00 00 06", "30 00 00 00 07", "30 00 00 00 08", "30 00 00 00 09", "30 00 00 00 0A", "30 00 00 00 0B", "30 00 00 00 0C", "30 00 00 00 0D", "30 00 00 00 0E", "30 00 00 00 0F", "30 00 00 00 10", "30 00 00 00 11", "30 00 00 00 12", "30 00 00 00 13", "30 00 00 00 14", "30 00 00 00 15", "30 00 00 00 16", "30 00 00 00 17", "30 00 00 00 18", "30 00 00 00 19", "30 00 00 00 1A", "30 00 00 00 1B", "30 00 00 00 1C", "30 00 00 00 1D", "30 00 00 00 1E", "30 00 00 00 1F", "30 00 00 00 20"};

    public static class ContentState {
        public int iconRes = R.mipmap.ic_device_none;
        public String name = "";
        public String action = "";
        public String place = "";
        public int rgbColor = Integer.MIN_VALUE;
    }

    public static void setControlDevice(Device device) {
        controlDevice = device;
        SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        extParam = superPanelExtParam;
        if (superPanelExtParam == null) {
            extParam = new SuperPanelExtParam();
        }
    }

    public static Rs485ExtParam.Category getCategory(int type) {
        if (type == 1) {
            return extParam.getTo485List().get(categoryPosition);
        }
        return extParam.getToBleList().get(categoryPosition);
    }

    public static Rs485ExtParam.Instruct getInstruct(int type) {
        if (type == 1) {
            return extParam.getTo485List().get(categoryPosition).getAction().get(instructPosition);
        }
        return extParam.getToBleList().get(categoryPosition).getAction().get(instructPosition);
    }

    public static void setInstruct(int type, Rs485ExtParam.Instruct instruct) {
        if (type == 1) {
            extParam.getTo485List().get(categoryPosition).getAction().set(instructPosition, instruct);
        } else {
            extParam.getToBleList().get(categoryPosition).getAction().set(instructPosition, instruct);
        }
    }

    public static void addCategory(int type, Rs485ExtParam.Category category) {
        if (type == 1) {
            extParam.getTo485List().add(category);
            categoryPosition = extParam.getTo485List().size() - 1;
        } else {
            isAddBleTo485 = true;
            extParam.getToBleList().add(category);
            categoryPosition = extParam.getToBleList().size() - 1;
        }
    }

    public static void deleteCategory(final BaseNormalActivity activity) {
        isDelete485ToBle = true;
        if (!extParam.getToBleList().get(categoryPosition).getAction().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            Iterator<Rs485ExtParam.Instruct> it = extParam.getToBleList().get(categoryPosition).getAction().iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(it.next().getCmdIdx()));
            }
            activity.showLoadingDialog("");
            deleteInstruct(activity, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    Cg485Helper.lambda$deleteCategory$0(BaseNormalActivity.this, (Boolean) obj);
                }
            });
            return;
        }
        extParam.getToBleList().remove(categoryPosition);
        updateParamExt(activity, true, new boolean[0]);
    }

    static /* synthetic */ void lambda$deleteCategory$0(BaseNormalActivity baseNormalActivity, Boolean bool) {
        if (bool.booleanValue()) {
            extParam.getToBleList().remove(categoryPosition);
            updateParamExt(baseNormalActivity, true, new boolean[0]);
        } else {
            baseNormalActivity.showErrorDialog(baseNormalActivity.getString(R.string.del_fail));
        }
    }

    public static void showBindDialog(final FragmentActivity activity) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(activity.getString(R.string.please_choose)).setCancelString(activity.getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(activity.getString(R.string.device));
        arrayList.add(activity.getString(R.string.local_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                Cg485Helper.lambda$showBindDialog$1(FragmentActivity.this, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(activity);
    }

    static /* synthetic */ void lambda$showBindDialog$1(FragmentActivity fragmentActivity, Integer num) {
        SceneHelper.instance().reset();
        SceneHelper.instance().bindingType = 5;
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSelectAction.class).withLong(Constants.PLACE_ID, controlDevice.getPlaceId()).withLong(Constants.CONTROL_ID, controlDevice.getId()).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(fragmentActivity);
        } else {
            if (intValue != 1) {
                return;
            }
            NavUtils.destination(ActSelectSceneForAction.class).withLong(Constants.PLACE_ID, controlDevice.getPlaceId()).withLong(Constants.CONTROL_ID, controlDevice.getId()).withBoolean(Constants.SELECT_ACTION, true).withInt(Constants.SCENE_TYPE, 2).withDefaultRequestCode().navigation(fragmentActivity);
        }
    }

    public static void setInstruct(Context context, final int editType, final int index, final String btData, final String rsData, final int dataFormat, final IAction<ResponseMsg> result) {
        CmdAssistant.getDeviceAssistant(controlDevice, new int[0]).setRs485Data(context, editType, index, btData, rsData, dataFormat, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    int i = index;
                    if (editType == 1) {
                        i = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                    }
                    int i2 = i;
                    ReplaceHelper.instance().backupIndexData((AppCompatActivity) ActivityUtils.getTopActivity(), Cg485Helper.controlDevice.getDeviceId(), UpdateBackDataRequest.RS485_TO_BLE_ACTION, CmdAssistant.getDeviceAssistant(Cg485Helper.controlDevice, new int[0]).setRs485Data(2, i2, btData, rsData, dataFormat), i2);
                }
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(responseMsg);
                }
            }
        });
    }

    public static void learnInstruct(Context context, IAction<ResponseMsg> result) {
        CmdAssistant.getDeviceAssistant(controlDevice, new int[0]).learnRs485Data(context, result);
    }

    public static void deleteInstruct(Context context, final List<Integer> cmdList, final IAction<Boolean> result) {
        CmdAssistant.getDeviceAssistant(controlDevice, new int[0]).deleteRs485Data(context, cmdList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                Iterator it = cmdList.iterator();
                while (it.hasNext()) {
                    ReplaceHelper.instance().addBackupIndexData(UpdateBackDataRequest.RS485_TO_BLE_ACTION, null, ((Integer) it.next()).intValue());
                }
                ReplaceHelper.instance().backupData((AppCompatActivity) ActivityUtils.getTopActivity(), Cg485Helper.controlDevice.getDeviceId());
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(aBoolean);
                }
            }
        });
    }

    public static void runInstruct(Context context, String rsData, int dataFormat, IAction<Boolean> result) {
        CmdAssistant.getDeviceAssistant(controlDevice, new int[0]).runRs485Data(context, rsData, dataFormat, result);
    }

    public static Rs485ExtParam.RelateInfo convertSceneContent(Scene.SceneContent content) {
        SceneParam sceneParam;
        Rs485ExtParam.RelateInfo relateInfo = new Rs485ExtParam.RelateInfo();
        relateInfo.setActionType(content.getActionType());
        if (MaskType.isDeviceAction(content.getActionType())) {
            LocalDeviceParam localDeviceParam = (LocalDeviceParam) content.getExecuteCommand(LocalDeviceParam.class);
            if (localDeviceParam != null) {
                relateInfo.setObjectId(localDeviceParam.deviceid);
                relateInfo.setInstruct(localDeviceParam.instruct);
                relateInfo.setAddress(localDeviceParam.sceneAddr);
                relateInfo.setZone(localDeviceParam.sceneZone);
                relateInfo.setOption("".equals(localDeviceParam.option) ? 0 : Integer.parseInt(localDeviceParam.option));
                relateInfo.setOptionValue(localDeviceParam.optionvalue);
                return relateInfo;
            }
        } else if (MaskType.isGroupAction(content.getActionType())) {
            LocalGroupParam localGroupParam = (LocalGroupParam) content.getExecuteCommand(LocalGroupParam.class);
            if (localGroupParam != null) {
                relateInfo.setObjectId(localGroupParam.groupid);
                relateInfo.setInstruct(localGroupParam.instruct);
                relateInfo.setAddress(localGroupParam.sceneAddr);
                relateInfo.setZone(localGroupParam.sceneZone);
                relateInfo.setOption("".equals(localGroupParam.option) ? 0 : Integer.parseInt(localGroupParam.option));
                relateInfo.setOptionValue(localGroupParam.optionvalue);
                return relateInfo;
            }
        } else if (MaskType.isSceneAction(content.getActionType()) && (sceneParam = (SceneParam) content.getExecuteCommand(SceneParam.class)) != null) {
            relateInfo.setObjectId(sceneParam.sceneid);
            relateInfo.setInstruct(sceneParam.instruct);
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(sceneParam.sceneid);
            if (sceneBySceneId != null) {
                if (sceneBySceneId.getSceneType() == 4) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(sceneBySceneId.getMacdeviceid());
                    if (deviceByDeviceId != null) {
                        relateInfo.setAddress(deviceByDeviceId.getUnicastAddress());
                    }
                    relateInfo.setZone(DaliProHelper.BROADCAST_ADD);
                    return relateInfo;
                }
                relateInfo.setAddress(65025);
                relateInfo.setZone(1);
            }
        }
        return relateInfo;
    }

    public static String getInstructName(Rs485ExtParam.RelateInfo relateInfo) {
        Scene sceneBySceneId;
        if (MaskType.isDeviceAction(relateInfo.getActionType())) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.getObjectId());
            return deviceByDeviceId != null ? deviceByDeviceId.getDeviceName() : "";
        }
        if (!MaskType.isGroupAction(relateInfo.getActionType())) {
            return (!MaskType.isSceneAction(relateInfo.getActionType()) || (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.getObjectId())) == null) ? "" : sceneBySceneId.getSceneName();
        }
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.getObjectId());
        return groupByGroupId != null ? groupByGroupId.getGroupName() : "";
    }

    public static int getInstructAddress(Rs485ExtParam.RelateInfo relateInfo) {
        Scene sceneBySceneId;
        if (MaskType.isDeviceAction(relateInfo.getActionType())) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.getObjectId());
            if (deviceByDeviceId != null) {
                return deviceByDeviceId.getUnicastAddress();
            }
            return 0;
        }
        if (MaskType.isGroupAction(relateInfo.getActionType())) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.getObjectId());
            if (groupByGroupId != null) {
                return groupByGroupId.getGroupAddress();
            }
            return 0;
        }
        if (!MaskType.isSceneAction(relateInfo.getActionType()) || (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.getObjectId())) == null) {
            return 0;
        }
        if (sceneBySceneId.getSceneType() != 4) {
            return 65025;
        }
        Device deviceByDeviceId2 = Injection.repo().device().getDeviceByDeviceId(sceneBySceneId.getMacdeviceid());
        if (deviceByDeviceId2 != null) {
            return deviceByDeviceId2.getUnicastAddress();
        }
        return 0;
    }

    public static ContentState getRelateAction(Context context, Rs485ExtParam.RelateInfo relateInfo) {
        Scene sceneBySceneId;
        ContentState contentState2 = new ContentState();
        if (MaskType.isDeviceAction(relateInfo.getActionType())) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.getObjectId());
            if (deviceByDeviceId != null) {
                contentState2.iconRes = ProductRepository.getProductIcon(deviceByDeviceId);
                contentState2.name = deviceByDeviceId.getDeviceName();
                contentState2.action = SceneHelper.getDeviceActionString(context, deviceByDeviceId, String.valueOf(relateInfo.getOption()), relateInfo.getOptionValue(), relateInfo.getInstruct(), 0, "");
                if (contentState2.action.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                    contentState2.rgbColor = Color.rgb(Integer.parseInt(contentState2.action.substring(1, 3), 16), Integer.parseInt(contentState2.action.substring(3, 5), 16), Integer.parseInt(contentState2.action.substring(5, 7), 16));
                    contentState2.action = contentState2.action.substring(7);
                }
                contentState2.place = getLocationName(deviceByDeviceId.getFloorId(), deviceByDeviceId.getRoomId());
            }
        } else if (MaskType.isGroupAction(relateInfo.getActionType())) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.getObjectId());
            if (groupByGroupId != null) {
                contentState2.iconRes = ProductRepository.getProductIcon(groupByGroupId);
                contentState2.name = groupByGroupId.getGroupName();
                contentState2.action = SceneHelper.getGroupActionString(context, String.valueOf(relateInfo.getOption()), relateInfo.getOptionValue(), groupByGroupId, relateInfo.getInstruct(), 0);
                if (contentState2.action.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                    contentState2.rgbColor = Color.rgb(Integer.parseInt(contentState2.action.substring(1, 3), 16), Integer.parseInt(contentState2.action.substring(3, 5), 16), Integer.parseInt(contentState2.action.substring(5, 7), 16));
                    contentState2.action = contentState2.action.substring(7);
                }
                contentState2.place = getLocationName(groupByGroupId.getFloorId(), groupByGroupId.getRoomId());
            }
        } else if (MaskType.isSceneAction(relateInfo.getActionType()) && (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.getObjectId())) != null) {
            contentState2.iconRes = SceneHelper.getSceneIcon(context, sceneBySceneId.getIconPos());
            contentState2.name = sceneBySceneId.getSceneName();
            if (sceneBySceneId.getSceneType() == 4) {
                contentState2.action = context.getString(R.string.action_dali_scene_tip);
            } else {
                contentState2.action = context.getString(R.string.action_scene_tip);
            }
            contentState2.place = getLocationName(sceneBySceneId.getFloorId(), sceneBySceneId.getRoomId());
        }
        if (relateInfo.getObjectId() != 0 && TextUtils.isEmpty(contentState2.name)) {
            contentState2.name = relateInfo.getName();
            contentState2.place = relateInfo.getFloorname() + " " + relateInfo.getRoomname();
        }
        return contentState2;
    }

    public static String getInstructByIndex(long deviceId, int cmdIndex) {
        SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) Injection.repo().device().getDeviceByDeviceId(deviceId).getExtParam(SuperPanelExtParam.class);
        extParam = superPanelExtParam;
        if (superPanelExtParam != null && superPanelExtParam.getToBleList().size() != 0) {
            for (int i = 0; i < extParam.getToBleList().size(); i++) {
                for (Rs485ExtParam.Instruct instruct : extParam.getToBleList().get(i).getAction()) {
                    if (instruct.getCmdIdx() == cmdIndex) {
                        return "\"" + instruct.getCmd() + "\"";
                    }
                }
            }
        }
        return TmpConstant.GROUP_ROLE_UNKNOWN;
    }

    public static List<Device> getCgLinkBle485SubDevice(long deviceId, String cmdStr) {
        ArrayList arrayList = new ArrayList();
        for (Device device : Injection.repo().device().getSubDevice(Injection.repo().home().getSelPlace().getPlaceId(), deviceId)) {
            SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
            extParam = superPanelExtParam;
            if (superPanelExtParam == null) {
                extParam = new SuperPanelExtParam();
            }
            List<Rs485ExtParam.Category> to485List = extParam.getTo485List();
            for (int i = 0; i < to485List.size(); i++) {
                Iterator<Rs485ExtParam.Instruct> it = to485List.get(i).getAction().iterator();
                boolean z = false;
                while (it.hasNext()) {
                    if (it.next().getCmd().replaceAll(" ", "").equalsIgnoreCase(cmdStr.replaceAll(" ", ""))) {
                        arrayList.add(device);
                        z = true;
                    }
                }
                if (z) {
                    break;
                }
            }
        }
        return arrayList;
    }

    public static List<Device> getRs8SubDevice(long deviceId, String cmdStr) {
        ArrayList arrayList = new ArrayList();
        for (Device device : Injection.repo().device().getSubDevice(Injection.repo().home().getSelPlace().getPlaceId(), deviceId)) {
            Rs8ExtParam rs8ExtParam = (Rs8ExtParam) device.getExtParam(Rs8ExtParam.class);
            if (rs8ExtParam == null) {
                rs8ExtParam = new Rs8ExtParam();
            }
            List<Rs8CodeLibResponse.CodeLib.Action> actionlist = rs8ExtParam.getActionlist();
            int i = 0;
            while (true) {
                if (i >= actionlist.size()) {
                    break;
                }
                if (actionlist.get(i).getInstruct().replaceAll(" ", "").equalsIgnoreCase(cmdStr.replaceAll(" ", ""))) {
                    arrayList.add(device);
                    break;
                }
                i++;
            }
        }
        return arrayList;
    }

    public static String getInstructByCmd(Device device, String cmdStr) {
        SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        extParam = superPanelExtParam;
        if (superPanelExtParam != null && superPanelExtParam.getTo485List().size() != 0) {
            for (int i = 0; i < extParam.getTo485List().size(); i++) {
                for (Rs485ExtParam.Instruct instruct : extParam.getTo485List().get(i).getAction()) {
                    if (instruct.getDataFormat() == 1) {
                        if (instruct.getCmd().replaceAll(" ", "").equalsIgnoreCase(cmdStr)) {
                            return instruct.getActionName();
                        }
                    } else if (StringUtils.stringToHex(instruct.getCmd().replaceAll("\n", "\r\n")).equalsIgnoreCase(cmdStr)) {
                        return instruct.getActionName();
                    }
                }
            }
        }
        return "\"" + cmdStr + "\"";
    }

    public static String getInstructRs8ByCmd(Device device, String cmdStr) {
        Rs8ExtParam rs8ExtParam = (Rs8ExtParam) device.getExtParam(Rs8ExtParam.class);
        if (rs8ExtParam != null && rs8ExtParam.getActionlist().size() != 0) {
            for (int i = 0; i < rs8ExtParam.getActionlist().size(); i++) {
                Rs8CodeLibResponse.CodeLib.Action action = rs8ExtParam.getActionlist().get(i);
                if (action.getInstruct().replaceAll("\n", "\r\n").equalsIgnoreCase(cmdStr)) {
                    return action.getCname();
                }
            }
        }
        return "\"" + cmdStr + "\"";
    }

    public static int get485InstructTotal() {
        if (extParam == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < extParam.getToBleList().size(); i2++) {
            i += extParam.getToBleList().get(i2).getAction().size();
        }
        return i;
    }

    public static boolean isCmdRepeat(int type, String cmdStr, int dataFormat) {
        List<Rs485ExtParam.Category> toBleList;
        if (dataFormat != 1) {
            cmdStr = StringUtils.stringToHex(cmdStr);
        }
        SuperPanelExtParam superPanelExtParam = extParam;
        if (superPanelExtParam != null) {
            if (type == 1) {
                toBleList = superPanelExtParam.getTo485List();
            } else {
                toBleList = superPanelExtParam.getToBleList();
            }
            for (int i = 0; i < toBleList.size(); i++) {
                for (Rs485ExtParam.Instruct instruct : toBleList.get(i).getAction()) {
                    if (instruct.getDataFormat() == 1) {
                        if (instruct.getCmd().replaceAll(" ", "").equalsIgnoreCase(cmdStr.replaceAll(" ", ""))) {
                            return true;
                        }
                    } else if (StringUtils.stringToHex(instruct.getCmd().replaceAll("\n", "\r\n")).equalsIgnoreCase(cmdStr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void showConditionDialog(final FragmentActivity activity, final Device device, final int index) {
        ArrayList arrayList = new ArrayList();
        controlDevice = device;
        SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        extParam = superPanelExtParam;
        if (superPanelExtParam == null || superPanelExtParam.getToBleList().size() == 0) {
            SmartToast.showShort(activity.getString(R.string.device_no_instruct));
            return;
        }
        for (int i = 0; i < extParam.getToBleList().size(); i++) {
            arrayList.add(extParam.getToBleList().get(i).getCategoryName());
        }
        SelectListDialog.asDefault(false).setTitle(activity.getString(R.string.please_choose)).setCancelString(activity.getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                Cg485Helper.lambda$showConditionDialog$2(Device.this, index, activity, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(activity);
    }

    static /* synthetic */ void lambda$showConditionDialog$2(Device device, int i, FragmentActivity fragmentActivity, Integer num) {
        categoryPosition = num.intValue();
        NavUtils.destination(ActSelectCg485Condition.class).withLong("device_id", device.getDeviceId()).withString(Constants.FLOOR_NAME, device.getFloorName()).withString(Constants.ROOM_NAME, device.getRoomName()).withString("device_name", device.getDeviceName()).withString(Constants.PRODUCT_ID, device.getProductId()).withString(Constants.MAC_ADDRESS, device.getWifiMac()).withInt(Constants.CONDITION_INDEX, i).withDefaultRequestCode().navigation(fragmentActivity);
    }

    public static void showSceneActionDialog(FragmentActivity activity, Device device, boolean isLocal) {
        controlDevice = device;
        SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
        extParam = superPanelExtParam;
        if (superPanelExtParam == null || superPanelExtParam.getTo485List().isEmpty() || extParam.getTo485List().get(0).getAction().isEmpty()) {
            SmartToast.showShort(activity.getString(R.string.device_no_instruct));
        } else {
            goSelectAction(activity, device, isLocal);
        }
    }

    private static void goSelectAction(FragmentActivity activity, Device device, boolean isLocal) {
        NavUtils.destination(ActSelectCg485Action.class).withLong(Constants.CONTROL_ID, device.getId()).withBoolean(Constants.IS_LOCAL_SCENE, isLocal).withString(Constants.PRODUCT_ID, device.getProductId()).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode().navigation(activity);
    }

    public static void updateNameAndParamExt(final BaseNormalActivity activity, final String name, final boolean back, boolean... showSaveDialog) {
        if (showSaveDialog.length > 0 && showSaveDialog[0]) {
            activity.showLoadingDialog(activity.getString(R.string.saving));
        }
        ((ObservableSubscribeProxy) Injection.net().updateNameAndParamExt(controlDevice.getDeviceId(), name, GsonUtils.toJson(extParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Cg485Helper.lambda$updateNameAndParamExt$3(name, back, activity, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper.3
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                BaseNormalActivity.this.dismissLoadingDialog();
            }
        });
    }

    static /* synthetic */ void lambda$updateNameAndParamExt$3(String str, boolean z, BaseNormalActivity baseNormalActivity, Object obj) throws Exception {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(controlDevice.getDeviceId());
        deviceByDeviceId.setDeviceName(str);
        deviceByDeviceId.setExtParam(GsonUtils.toJson(extParam));
        Injection.repo().device().saveDevice(deviceByDeviceId);
        if (z) {
            baseNormalActivity.finish();
        } else {
            baseNormalActivity.showSuccessDialog(baseNormalActivity.getString(R.string.save_success));
        }
    }

    public static void updateParamExt(final BaseNormalActivity activity, final boolean back, boolean... showSaveDialog) {
        if (showSaveDialog.length > 0 && showSaveDialog[0]) {
            activity.showLoadingDialog(activity.getString(R.string.saving));
        }
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(controlDevice.getDeviceId(), GsonUtils.toJson(extParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Cg485Helper.lambda$updateParamExt$4(back, activity, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.cg485.Cg485Helper.4
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                BaseNormalActivity.this.dismissLoadingDialog();
            }
        });
    }

    static /* synthetic */ void lambda$updateParamExt$4(boolean z, BaseNormalActivity baseNormalActivity, Object obj) throws Exception {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(controlDevice.getDeviceId());
        deviceByDeviceId.setExtParam(GsonUtils.toJson(extParam));
        Injection.repo().device().saveDevice(deviceByDeviceId);
        if (z) {
            if (isDelete485ToBle) {
                baseNormalActivity.setResult(5001);
                isDelete485ToBle = false;
            }
            if (isAddBleTo485) {
                NavUtils.destination(ActCg485Device.class).withLong(Constants.CONTROL_ID, controlDevice.getId()).withInt(Constants.COMMAND_TYPE, 2).withDefaultRequestCode().navigation(baseNormalActivity);
                isAddBleTo485 = false;
            }
            baseNormalActivity.finish();
            return;
        }
        baseNormalActivity.showSuccessDialog(baseNormalActivity.getString(R.string.save_success));
    }

    public static String getInputCmd(String s) {
        String replaceAll = s.replaceAll(" ", "");
        if (replaceAll.length() % 2 == 1) {
            replaceAll = replaceAll.substring(0, replaceAll.length() - 1) + "0" + replaceAll.substring(replaceAll.length() - 1);
        }
        return replaceAll.replaceAll("(.{2})", "$1 ").trim();
    }

    private static String getLocationName(long floorId, long roomId) {
        Floor floor = Injection.repo().home().getFloor(floorId);
        Room room = Injection.repo().home().getRoom(roomId);
        if (floor == null || room == null) {
            if (floor != null) {
                return floor.getFloorName();
            }
            return room != null ? room.getRoomName() : ActivityUtils.getTopActivity().getString(R.string.app_str_not_distribution);
        }
        return floor.getFloorName() + " " + room.getRoomName();
    }

    public static Rs485ExtParam.Category getSubCategory(Device device) {
        Rs485ExtParam rs485ExtParam = (Rs485ExtParam) device.getExtParam(Rs485ExtParam.class);
        if (rs485ExtParam == null) {
            rs485ExtParam = new Rs485ExtParam();
        }
        if (rs485ExtParam.getTo485List().isEmpty()) {
            return null;
        }
        return rs485ExtParam.getTo485List().get(0);
    }

    public static int getDeviceImage(Context context, Device device) {
        Rs485ExtParam.Category subCategory = getSubCategory(device);
        if (subCategory == null) {
            return R.mipmap.ic_device_none;
        }
        int[] rs485DevicePic = IconRepository.getRs485DevicePic(context);
        return subCategory.getColorIdx() < rs485DevicePic.length ? rs485DevicePic[subCategory.getColorIdx()] : rs485DevicePic[0];
    }

    public static int getDeviceImage(Context context, int index) {
        int[] rs485DevicePic = IconRepository.getRs485DevicePic(context);
        return index < rs485DevicePic.length ? rs485DevicePic[index] : rs485DevicePic[0];
    }

    public static Device addBleTo485Device(AddDeviceResponse response) {
        Device device = new Device();
        device.setPlaceId(Injection.repo().home().getSelPlace().getPlaceId());
        device.setFloorId(controlDevice.getFloorId());
        device.setRoomId(controlDevice.getRoomId());
        device.setRoomName(controlDevice.getRoomName());
        device.setFloorName(controlDevice.getFloorName());
        device.setProductId(ProductId.CG485_SUB_DEVICE);
        device.setDeviceId(response.getDeviceid());
        device.setPlatFormDeviceId(response.getPlatformdeviceid());
        device.setOnlineFlag(1);
        device.setIndex(1000);
        device.setCreatetime(response.getCreatetime());
        device.setImageIndex(response.getImgindex());
        device.setWifiMac(response.getMac());
        device.setDeviceName(response.getDevicename());
        device.setMacfalg(response.getMacfalg());
        device.setMacdeviceid(response.getMacdeviceid());
        device.setParam(response.getParam());
        device.setExtParam(response.getParamext());
        Injection.repo().device().saveDevice(device);
        return device;
    }

    public static String newBleTo485SubData() {
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).placeid(Injection.repo().home().getSelPlace().getPlaceId()).floorid(controlDevice.getFloorId()).roomid(controlDevice.getRoomId()).mac(controlDevice.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "")).maccode("").devicename(controlDevice.getName()).macfalg(2).codeLibrary(ConfigHelper.instance().codeLibrary).aiPuductType(ProductId.BLE_SUB_TYPE_CG_485).subProductTypeName(ProductId.BLE_SUB_TYPE_CG_485).subManufacturerName("LTECH").subProductName(controlDevice.getName() + "-cg485").macdeviceid(controlDevice.getMacdeviceid()).param(controlDevice.getParam()).paramext(GsonUtils.toJson(extParam)).build().getConfigJson();
    }

    public static Device createBleTo485SubDevice(String name, int imgIndex) {
        Rs485ExtParam rs485ExtParam = new Rs485ExtParam();
        Rs485ExtParam.Category category = new Rs485ExtParam.Category();
        category.setCategoryName(name);
        category.setType(1);
        category.setColorIdx(imgIndex);
        rs485ExtParam.getTo485List().add(category);
        Device device = new Device();
        device.setFloorId(controlDevice.getFloorId());
        device.setRoomId(controlDevice.getRoomId());
        device.setWifiMac(controlDevice.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, ""));
        device.setDeviceName(name);
        device.setMacdeviceid(controlDevice.getDeviceId());
        BleParam bleParam = new BleParam();
        bleParam.setUnicastAddress(controlDevice.getUnicastAddress());
        device.setParam(bleParam);
        device.setExtParam(rs485ExtParam);
        return device;
    }
}