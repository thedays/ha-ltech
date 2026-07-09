package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.CgdProSceneExtParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.scene_param.BaseLocalParam;
import com.ltech.smarthome.model.scene_param.LocalDaliSceneParam;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.model.scene_param.LocalGroupParam;
import io.objectbox.converter.PropertyConverter;
import java.util.List;

/* loaded from: classes4.dex */
public class Scene implements Role {
    public static final int CLOUD = 1;
    public static final int DALI = 4;
    public static final int LOCAL = 2;
    private boolean common;
    private String createTime;
    private boolean dynamic;
    private String extParam;
    private long floorId;
    private String floorName;
    private int iconPos;
    private long id;
    private long macdeviceid;
    private String param;
    private long placeId;
    private long roomId;
    private String roomName;
    private List<SceneContent> sceneContents;
    private long sceneId;
    private int sceneIndex;
    private String sceneName;
    private int sceneNum;
    private int sceneType;
    private long userId;

    @Override // com.ltech.smarthome.model.bean.Role
    public DeviceState getDeviceState() {
        return null;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getHideDevice() {
        return 0;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public /* synthetic */ boolean isVirtual() {
        return Role.CC.$default$isVirtual(this);
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public void setDeviceState(DeviceState deviceState) {
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getFloorName() {
        return this.floorName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getRoomName() {
        return this.roomName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSceneId() {
        return this.sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getSceneName() {
        return this.sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public int getSceneIndex() {
        return this.sceneIndex;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIconPos() {
        return this.iconPos;
    }

    public void setIconPos(int iconPos) {
        this.iconPos = iconPos;
    }

    public List<SceneContent> getSceneContents() {
        return this.sceneContents;
    }

    public void setSceneContents(List<SceneContent> sceneContents) {
        this.sceneContents = sceneContents;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getFloorId() {
        return this.floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Scene) {
            Scene scene = (Scene) var1;
            return scene.getSceneId() == getSceneId() && scene.getPlaceId() == getPlaceId() && scene.getUserId() == getUserId();
        }
        return super.equals(var1);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public void setSceneNum(int sceneNum) {
        this.sceneNum = sceneNum;
    }

    public int getSceneNum() {
        return this.sceneNum;
    }

    public void setSceneType(int sceneType) {
        this.sceneType = sceneType;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getSceneType() {
        return this.sceneType;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public long getMacdeviceid() {
        return this.macdeviceid;
    }

    public void setMacdeviceid(long macdeviceid) {
        this.macdeviceid = macdeviceid;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getExtParam() {
        return this.extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public <T> T getExtParam(Class<T> cls) {
        return (T) GsonUtils.getGson().fromJson(this.extParam, (Class) cls);
    }

    public void setExtParam(Object object) {
        this.extParam = GsonUtils.toJson(object);
    }

    public boolean isCommon() {
        return this.common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public static class SceneContent {
        private String action;
        private int actionType;
        private String actionTypeName;
        private String errorTip = "";
        private String executeCommand;
        private int timeSpace;

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getActionTypeName() {
            return this.actionTypeName;
        }

        public void setActionTypeName(String actionTypeName) {
            this.actionTypeName = actionTypeName;
        }

        public int getActionType() {
            return this.actionType;
        }

        public void setActionType(int actionType) {
            this.actionType = actionType;
        }

        public int getTimeSpace() {
            return this.timeSpace;
        }

        public void setTimeSpace(int timeSpace) {
            this.timeSpace = timeSpace;
        }

        public String getExecuteCommand() {
            return this.executeCommand;
        }

        public void setExecuteCommand(String param) {
            this.executeCommand = param;
        }

        public void setExecuteCommand(Object object) {
            this.executeCommand = GsonUtils.toJson(object);
        }

        public <T> T getExecuteCommand(Class<T> cls) {
            return (T) GsonUtils.getGson().fromJson(this.executeCommand, (Class) cls);
        }

        public String getErrorTip() {
            return this.errorTip;
        }

        public void setErrorTip(String errorTip) {
            this.errorTip = errorTip;
        }

        public static class SceneContentConverter implements PropertyConverter<List<SceneContent>, String> {
            @Override // io.objectbox.converter.PropertyConverter
            public List<SceneContent> convertToEntityProperty(String databaseValue) {
                return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<SceneContent>>(this) { // from class: com.ltech.smarthome.model.bean.Scene.SceneContent.SceneContentConverter.1
                }.getType());
            }

            @Override // io.objectbox.converter.PropertyConverter
            public String convertToDatabaseValue(List<SceneContent> entityProperty) {
                return GsonUtils.toJson(entityProperty, new TypeToken<List<SceneContent>>(this) { // from class: com.ltech.smarthome.model.bean.Scene.SceneContent.SceneContentConverter.2
                }.getType());
            }
        }

        public int getDelayTime(boolean isDynamic) {
            int i;
            BaseLocalParam baseLocalParam = (BaseLocalParam) getExecuteCommand(BaseLocalParam.class);
            if (baseLocalParam.delayIn100Ms == 0) {
                i = this.timeSpace;
            } else {
                if (isDynamic) {
                    return baseLocalParam.delayIn100Ms;
                }
                i = baseLocalParam.delayIn100Ms / 10;
            }
            return i * 10;
        }

        public void setDelayTime(int delayTime) {
            if (MaskType.isDeviceAction(this.actionType)) {
                LocalDeviceParam localDeviceParam = (LocalDeviceParam) getExecuteCommand(LocalDeviceParam.class);
                localDeviceParam.delayIn100Ms = delayTime;
                setExecuteCommand(localDeviceParam);
            } else if (MaskType.isGroupAction(this.actionType)) {
                LocalGroupParam localGroupParam = (LocalGroupParam) getExecuteCommand(LocalGroupParam.class);
                localGroupParam.delayIn100Ms = delayTime;
                setExecuteCommand(localGroupParam);
            } else if (MaskType.isSceneAction(this.actionType)) {
                LocalDaliSceneParam localDaliSceneParam = (LocalDaliSceneParam) getExecuteCommand(LocalDaliSceneParam.class);
                localDaliSceneParam.delayIn100Ms = delayTime;
                setExecuteCommand(localDaliSceneParam);
            }
            this.timeSpace = delayTime / 10;
        }
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getName() {
        return this.sceneName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIndex() {
        return this.sceneIndex;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getObjectId() {
        return this.sceneId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getDaliHidden() {
        CgdProSceneExtParam cgdProSceneExtParam;
        if (this.extParam == null || (cgdProSceneExtParam = (CgdProSceneExtParam) getExtParam(CgdProSceneExtParam.class)) == null) {
            return 0;
        }
        return cgdProSceneExtParam.getDaliHidden();
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getCreatetime() {
        return this.createTime;
    }

    public String toString() {
        return "Scene{id=" + this.id + ", sceneId=" + this.sceneId + ", userId=" + this.userId + ", placeId=" + this.placeId + ", sceneName='" + this.sceneName + "', sceneIndex=" + this.sceneIndex + ", iconPos=" + this.iconPos + ", createTime='" + this.createTime + "', floorId=" + this.floorId + ", roomId=" + this.roomId + ", common=" + this.common + ", dynamic=" + this.dynamic + ", sceneContents=" + this.sceneContents + ", sceneNum=" + this.sceneNum + ", sceneType=" + this.sceneType + ", param='" + this.param + "', macdeviceid=" + this.macdeviceid + ", extParam='" + this.extParam + "', floorName='" + this.floorName + "', roomName='" + this.roomName + "'}";
    }
}