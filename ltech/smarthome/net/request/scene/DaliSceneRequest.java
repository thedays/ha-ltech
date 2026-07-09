package com.ltech.smarthome.net.request.scene;

import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DaliSceneRequest {
    private long floorid;
    private long macdeviceid;
    private long placeid;
    private long roomid;
    private List<SceneInfo> scenes = new ArrayList();

    public static final class ContentInfo {
        public String action;
        public int actiontype;
        public String actiontypename;
        public String executecommand;
        public long scenecontentid;
        public int timespace;
    }

    public static final class SceneInfo {
        public List<ContentInfo> content = new ArrayList();
        public String executecommand;
        public long floorid;
        public String img;
        public int imgindex;
        public String paramext;
        public long roomid;
        public long sceneid;
        public int sceneindex;
        public String scenename;
        public int scenetype;
    }

    public DaliSceneRequest(Device controlDevice, List<Scene> sceneList) {
        this.roomid = controlDevice.getRoomId();
        this.floorid = controlDevice.getFloorId();
        this.placeid = controlDevice.getPlaceId();
        this.macdeviceid = controlDevice.getDeviceId();
        for (Scene scene : sceneList) {
            SceneInfo sceneInfo = new SceneInfo();
            sceneInfo.scenename = scene.getSceneName();
            sceneInfo.sceneid = scene.getSceneId();
            sceneInfo.scenetype = scene.getSceneType();
            sceneInfo.sceneindex = scene.getSceneIndex();
            sceneInfo.executecommand = SceneHelper.instance().getDaliSceneExecuteCommand(scene);
            sceneInfo.imgindex = scene.getIconPos();
            sceneInfo.img = "";
            sceneInfo.paramext = scene.getExtParam();
            sceneInfo.floorid = scene.getFloorId();
            sceneInfo.roomid = scene.getRoomId();
            for (Scene.SceneContent sceneContent : scene.getSceneContents()) {
                ContentInfo contentInfo = new ContentInfo();
                contentInfo.action = sceneContent.getAction();
                contentInfo.executecommand = sceneContent.getExecuteCommand();
                contentInfo.timespace = sceneContent.getTimeSpace();
                contentInfo.actiontypename = sceneContent.getActionTypeName();
                contentInfo.actiontype = sceneContent.getActionType();
                sceneInfo.content.add(contentInfo);
            }
            this.scenes.add(sceneInfo);
        }
    }
}