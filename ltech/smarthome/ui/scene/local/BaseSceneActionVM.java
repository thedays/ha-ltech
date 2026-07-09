package com.ltech.smarthome.ui.scene.local;

import android.content.Context;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.scene_param.AutomationParam;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes4.dex */
public class BaseSceneActionVM extends BaseViewModel {
    public List<Scene.SceneContent> actionList;
    public long placeId;

    public List<Automation> getCurActionAutomation() {
        AutomationParam automationParam;
        Automation automation;
        ArrayList arrayList = new ArrayList();
        List<Scene.SceneContent> list = this.actionList;
        if (list != null) {
            for (Scene.SceneContent sceneContent : list) {
                if (MaskType.isAutomationAction(sceneContent.getActionType()) && (automationParam = (AutomationParam) sceneContent.getExecuteCommand(AutomationParam.class)) != null && (automation = getAutomation(automationParam.automationid)) != null) {
                    automation.setEnable(automationParam.enable);
                    arrayList.add(automation);
                }
            }
        }
        return arrayList;
    }

    public Device getDevice(long deviceId) {
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
        if (deviceListByPlaceId == null) {
            return null;
        }
        for (Device device : deviceListByPlaceId) {
            if (deviceId == device.getDeviceId()) {
                return device;
            }
        }
        return null;
    }

    public Group getGroup(long groupId) {
        List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(this.placeId);
        if (groupListByPlaceId == null) {
            return null;
        }
        for (Group group : groupListByPlaceId) {
            if (groupId == group.getGroupId()) {
                return group;
            }
        }
        return null;
    }

    public Automation getAutomation(long automationId) {
        List<Automation> automationListByPlaceId = Injection.repo().auto().getAutomationListByPlaceId(this.placeId);
        if (automationListByPlaceId == null) {
            return null;
        }
        for (Automation automation : automationListByPlaceId) {
            if (automationId == automation.getAutomationId()) {
                return automation;
            }
        }
        return null;
    }

    public Scene getScene(long sceneId) {
        return Injection.repo().scene().getSceneBySceneId(sceneId);
    }

    public String getLocationNameByDevice(long deviceId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceId);
        if (deviceByDeviceId == null) {
            return "";
        }
        return deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
    }

    public String getLocationNameByGroup(long groupId) {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupId);
        return groupByGroupId == null ? "" : getLocationName(groupByGroupId.getFloorId(), groupByGroupId.getRoomId());
    }

    public String getLocationNameByScene(Scene scene) {
        return getLocationName(scene.getFloorId(), scene.getRoomId());
    }

    private String getLocationName(long floorId, long roomId) {
        Floor floor = Injection.repo().home().getFloor(floorId);
        Room room = Injection.repo().home().getRoom(roomId);
        if (floor == null || room == null) {
            if (floor != null) {
                return floor.getFloorName();
            }
            return room != null ? room.getRoomName() : ActivityUtils.getTopActivity().getString(R.string.app_str_not_distribution);
        }
        return floor.getFloorName() + room.getRoomName();
    }

    public String getZoneName(Context context, int sceneZone, String option) {
        if (sceneZone == 0 || "11".equals(option) || "12".equals(option) || "".equals(option)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (((sceneZone >> i) & 1) != 0) {
                if (sb.length() == 0) {
                    sb.append(i + 1);
                } else {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    sb.append(i + 1);
                }
            }
        }
        return context.getString(R.string.zone) + ((Object) sb);
    }

    protected void checkVirtualUpdate(List<Scene.SceneContent> actionList) {
        Device device;
        GroupParam groupParam;
        HashSet hashSet = new HashSet();
        for (Scene.SceneContent sceneContent : actionList) {
            if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                DeviceParam deviceParam = (DeviceParam) sceneContent.getExecuteCommand(DeviceParam.class);
                if (deviceParam != null && (device = getDevice(deviceParam.deviceid)) != null && device.isVirtual() && device.getWritable() == 0) {
                    hashSet.add(Long.valueOf(device.getDeviceId()));
                }
            } else if (MaskType.isGroupAction(sceneContent.getActionType()) && (groupParam = (GroupParam) sceneContent.getExecuteCommand(GroupParam.class)) != null) {
                Group group = getGroup(groupParam.groupid);
                if (!group.getDeviceIds().isEmpty()) {
                    hashSet.addAll(group.getDeviceIds());
                }
            }
        }
        if (hashSet.isEmpty()) {
            return;
        }
        WriteVirtualHelper.instance().updateDeviceWritable(new ArrayList(hashSet));
    }
}