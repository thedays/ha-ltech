package com.ltech.smarthome.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.TrigToBleExtParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.model.scene_param.LocalGroupParam;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class TbUtils {

    public static class ContentState {
        public String action;
        public int address;
        public int iconRes;
        public String name;
        public String state;
        public int rgbColor = Integer.MIN_VALUE;
        public int zone = 1;
    }

    public static List<ContentState> getContentStates(Context context, List<TrigToBleExtParam.TrigRelateInfo> contents) {
        ArrayList arrayList = new ArrayList();
        Iterator<TrigToBleExtParam.TrigRelateInfo> it = contents.iterator();
        while (it.hasNext()) {
            arrayList.add(getContentState(context, it.next()));
        }
        return arrayList;
    }

    public static ContentState getContentState(Context context, TrigToBleExtParam.TrigRelateInfo content) {
        SceneParam sceneParam;
        ContentState contentState = new ContentState();
        if (!TextUtils.isEmpty(content.getCustomerName())) {
            contentState.name = content.getCustomerName();
        }
        if (MaskType.isDeviceAction(content.getActiontype())) {
            LocalDeviceParam localDeviceParam = (LocalDeviceParam) content.getExecuteCommand(LocalDeviceParam.class);
            if (localDeviceParam != null) {
                Device device = getDevice(localDeviceParam.deviceid);
                contentState.iconRes = getIconRes(device);
                if (device != null) {
                    contentState.name = device.getDeviceName();
                    String deviceActionString = SceneHelper.getDeviceActionString(context, device, localDeviceParam);
                    if (deviceActionString.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        contentState.rgbColor = Color.rgb(Integer.parseInt(deviceActionString.substring(1, 3), 16), Integer.parseInt(deviceActionString.substring(3, 5), 16), Integer.parseInt(deviceActionString.substring(5, 7), 16));
                        contentState.state = deviceActionString.substring(7);
                    } else {
                        contentState.state = deviceActionString;
                    }
                    contentState.address = device.getUnicastAddress();
                    contentState.action = localDeviceParam.instruct;
                    contentState.zone = localDeviceParam.sceneZone;
                    return contentState;
                }
                contentState.name = "";
                contentState.state = "";
                contentState.action = "";
                contentState.zone = 1;
                return contentState;
            }
        } else if (MaskType.isGroupAction(content.getActiontype())) {
            LocalGroupParam localGroupParam = (LocalGroupParam) content.getExecuteCommand(LocalGroupParam.class);
            if (localGroupParam != null) {
                Group group = getGroup(localGroupParam.groupid);
                if (group != null) {
                    contentState.iconRes = getIconRes(group);
                    contentState.name = group.getGroupName();
                    String groupActionString = SceneHelper.getGroupActionString(context, localGroupParam.option, localGroupParam.optionvalue, group, localGroupParam.instruct, localGroupParam.sceneZone);
                    if (groupActionString.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        contentState.rgbColor = Color.rgb(Integer.parseInt(groupActionString.substring(1, 3), 16), Integer.parseInt(groupActionString.substring(3, 5), 16), Integer.parseInt(groupActionString.substring(5, 7), 16));
                        contentState.state = groupActionString.substring(7);
                    } else {
                        contentState.state = groupActionString;
                    }
                    contentState.address = group.getGroupAddress();
                    contentState.action = localGroupParam.instruct;
                    contentState.zone = localGroupParam.sceneZone;
                    return contentState;
                }
                contentState.name = "";
                contentState.state = "";
                contentState.action = "";
                contentState.zone = 1;
                return contentState;
            }
        } else if (MaskType.isSceneAction(content.getActiontype()) && (sceneParam = (SceneParam) content.getExecuteCommand(SceneParam.class)) != null) {
            Scene scene = getScene(sceneParam.sceneid);
            if (scene != null) {
                if (scene.getSceneType() == 4) {
                    contentState.iconRes = SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), scene.getIconPos());
                    contentState.name = scene.getSceneName();
                    contentState.state = context.getString(R.string.action_dali_scene_tip);
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(scene.getMacdeviceid());
                    if (deviceByDeviceId != null) {
                        contentState.address = deviceByDeviceId.getUnicastAddress();
                    }
                    contentState.action = sceneParam.instruct;
                    contentState.zone = DaliProHelper.BROADCAST_ADD;
                    return contentState;
                }
                contentState.iconRes = SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), scene.getIconPos());
                contentState.name = scene.getSceneName();
                contentState.state = context.getString(R.string.action_scene_tip);
                contentState.address = 65025;
                contentState.action = sceneParam.instruct;
                contentState.zone = 1;
                return contentState;
            }
            contentState.name = "";
            contentState.state = "";
            contentState.action = "";
            contentState.zone = 1;
        }
        return contentState;
    }

    private static Device getDevice(long deviceId) {
        return Injection.repo().device().getDeviceByDeviceId(deviceId);
    }

    private static Group getGroup(long groupId) {
        return Injection.repo().group().getGroupByGroupId(groupId);
    }

    private static Scene getScene(long sceneId) {
        return Injection.repo().scene().getSceneBySceneId(sceneId);
    }

    private static int getIconRes(Object object) {
        return ProductRepository.getProductIcon(object);
    }
}