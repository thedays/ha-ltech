package com.ltech.smarthome.ui.device.dalipro;

import android.content.Context;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.device_param.CgdProSceneExtParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.smart.message.base.BaseCmdParam;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class DaliProHelper {
    public static int BROADCAST_ADD = 254;
    public static int NORMAL_ZONE_NUMBER = 1;
    public static BaseCmdParam cmdParam;

    public static int getDaliGroupType(Group group) {
        Iterator<Long> it = group.getDeviceIds().iterator();
        int i = 0;
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null) {
                i |= getDaliDeviceType(deviceByDeviceId);
            }
        }
        if (i == 0) {
            return 2;
        }
        return i;
    }

    public static int convertDaliType(Object object) {
        if (object instanceof Device) {
            CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) ((Device) object).getExtParam(CgdProLightExtParam.class);
            if (cgdProLightExtParam != null) {
                return Math.min(cgdProLightExtParam.getDaliType(), 3);
            }
            return 0;
        }
        if (!(object instanceof Group)) {
            return 0;
        }
        switch (getDaliGroupType((Group) object)) {
            case 2:
            case 3:
                return 2;
            case 4:
            case 5:
                return 3;
            case 6:
            case 7:
                return 5;
            default:
                return 1;
        }
    }

    public static int getDaliDeviceType(Device device) {
        CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class);
        if (cgdProLightExtParam.getDaliType() <= 3) {
            return 1 << (cgdProLightExtParam.getDaliType() - 1);
        }
        return 4;
    }

    public static int getGroupAddress(Group group) {
        return ((CgdProGroupExtParam) group.getExtParam(CgdProGroupExtParam.class)).getDaliAddr();
    }

    public static int getDeviceAddress(Device device) {
        return ((CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class)).getDaliAddr();
    }

    public static int getDaliAddress(Role role) {
        if (role instanceof Group) {
            Group group = (Group) role;
            if (group.getExtParam() != null) {
                return getGroupAddress(group);
            }
            return 0;
        }
        if (role instanceof Device) {
            Device device = (Device) role;
            if (device.getExtParam() != null) {
                return getDeviceAddress(device);
            }
            return 0;
        }
        if (role instanceof Scene) {
            return getSceneNum((Scene) role);
        }
        return 0;
    }

    public static int getSceneNum(Scene scene) {
        if (scene.getExtParam() == null) {
            return 0;
        }
        return ((CgdProSceneExtParam) scene.getExtParam(CgdProSceneExtParam.class)).getDaliAddr();
    }

    public static boolean isSceneVisible(Scene scene) {
        return scene.getExtParam() == null || ((CgdProSceneExtParam) scene.getExtParam(CgdProSceneExtParam.class)).getDaliHidden() != 1;
    }

    public static boolean isSupportDim(Role role) {
        return role instanceof Group ? (getDaliGroupType((Group) role) & 1) != 0 : (role instanceof Device) && (getDaliDeviceType((Device) role) & 1) != 0;
    }

    public static boolean isSupportCt(Role role) {
        return role instanceof Group ? (getDaliGroupType((Group) role) & 2) != 0 : (role instanceof Device) && (getDaliDeviceType((Device) role) & 2) != 0;
    }

    public static boolean isSupportColor(Role role) {
        return role instanceof Group ? (getDaliGroupType((Group) role) & 4) != 0 : (role instanceof Device) && (getDaliDeviceType((Device) role) & 4) != 0;
    }

    public static boolean isMultiTypeGroup(Object object) {
        if (!(object instanceof Group) || !ProductRepository.isDaliLightGroup(object)) {
            return false;
        }
        Group group = (Group) object;
        return getDaliGroupType(group) == 3 || getDaliGroupType(group) == 5 || getDaliGroupType(group) == 6 || getDaliGroupType(group) == 7;
    }

    public static int getZoneNum(Object object) {
        if (object instanceof Group) {
            return getGroupAddress((Group) object) + 32768;
        }
        if (object instanceof Device) {
            return getDeviceAddress((Device) object);
        }
        return 1;
    }

    public static List<Integer> getDaliDeviceAddress(List<Long> deviceIds) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = deviceIds.iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null) {
                arrayList.add(Integer.valueOf(getDeviceAddress(deviceByDeviceId)));
            }
        }
        return arrayList;
    }

    public static List<Scene.SceneContent> convert2DaliSceneContent(Map<Long, LocalDeviceParam> actionMap) {
        ArrayList arrayList = new ArrayList();
        if (!actionMap.isEmpty()) {
            for (LocalDeviceParam localDeviceParam : actionMap.values()) {
                Scene.SceneContent sceneContent = new Scene.SceneContent();
                sceneContent.setAction(PushContentParamKey.ACTION);
                sceneContent.setActionType(MaskType.DEVICE.value());
                sceneContent.setActionTypeName("设备");
                sceneContent.setTimeSpace(0);
                sceneContent.setExecuteCommand(GsonUtils.toJson(localDeviceParam));
                arrayList.add(sceneContent);
            }
        }
        return arrayList;
    }

    public static Map<Long, LocalDeviceParam> convert2DaliSceneActionMap(List<Scene.SceneContent> sceneContentList) {
        HashMap hashMap = new HashMap();
        if (sceneContentList != null) {
            for (Scene.SceneContent sceneContent : sceneContentList) {
                if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                    LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                    hashMap.put(Long.valueOf(localDeviceParam.deviceid), localDeviceParam);
                }
            }
        }
        return hashMap;
    }

    public static LocalDeviceParam getDeviceParam(long deviceId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceId);
        LocalDeviceParam localDeviceParam = new LocalDeviceParam();
        localDeviceParam.deviceid = deviceByDeviceId.getDeviceId();
        CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) deviceByDeviceId.getExtParam(CgdProLightExtParam.class);
        BaseCmdParam baseCmdParam = cmdParam;
        if (baseCmdParam != null) {
            if (baseCmdParam.getExtParam(SceneHelper.OPTION) != null) {
                localDeviceParam.option = (String) cmdParam.getExtParam(SceneHelper.OPTION);
            }
            localDeviceParam.instruct = SceneHelper.instance().getConvertCmd(cmdParam);
        }
        localDeviceParam.sceneZone = cgdProLightExtParam.getDaliAddr();
        return localDeviceParam;
    }

    public static String getLightAction(Context context, String option, String instruct, Object obj) {
        option.hashCode();
        if (option.equals("0")) {
            return getLightStaticAction(context, instruct.substring(2), obj);
        }
        if (option.equals("4")) {
            return context.getString(R.string.light_off_1);
        }
        return "";
    }

    public static String getLightStaticAction(Context context, String instruct, Object obj) {
        if (!(obj instanceof Device)) {
            return context.getString(R.string.app_str_valid);
        }
        int daliDeviceType = getDaliDeviceType((Device) obj);
        if (daliDeviceType == 1) {
            if (instruct.length() > 10) {
                return context.getString(R.string.brt) + Constants.COLON_SEPARATOR + LightUtils.brt2PercentHasBelowZero(Integer.parseInt(instruct.substring(6, 8), 16)) + "%";
            }
            return context.getString(R.string.dim_static);
        }
        if (daliDeviceType == 2) {
            if (instruct.length() > 10) {
                int parseInt = Integer.parseInt(instruct.substring(6, 8), 16);
                return context.getString(R.string.ct) + Constants.COLON_SEPARATOR + LightUtils.getKValue(Integer.parseInt(instruct.substring(8, 10), 16), obj) + StringUtils.CR + context.getString(R.string.brt) + Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(parseInt) + "%";
            }
            return context.getString(R.string.ct_static);
        }
        if (instruct.length() >= 12) {
            return MqttTopic.MULTI_LEVEL_WILDCARD + instruct.substring(0, 6) + context.getString(R.string.brt) + Constants.COLON_SEPARATOR + LightUtils.brt2ProgressHasBelowZero(Integer.parseInt(instruct.substring(10, 12), 16)) + "%";
        }
        return context.getString(R.string.rgb_static);
    }
}