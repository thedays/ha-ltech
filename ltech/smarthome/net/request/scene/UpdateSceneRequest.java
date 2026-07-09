package com.ltech.smarthome.net.request.scene;

import com.blankj.utilcode.util.GsonUtils;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class UpdateSceneRequest {
    private HashMap<String, Object> paramMap;

    public static final class ContentInfo {
        public String action;
        public int actiontype;
        public String actiontypename;
        public String executecommand;
        public long scenecontentid;
        public int timespace;
    }

    public String getRequestString() {
        return GsonUtils.toJson(this.paramMap);
    }

    public UpdateSceneRequest(long sceneid, String scenename, List<ContentInfo> content, int imgIndex) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("scenename", scenename);
        this.paramMap.put("content", content);
        this.paramMap.put("imgindex", Integer.valueOf(imgIndex));
    }

    public UpdateSceneRequest(long sceneid, String scenename, List<ContentInfo> content, int imgIndex, String executecommand) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("scenename", scenename);
        this.paramMap.put("content", content);
        this.paramMap.put("imgindex", Integer.valueOf(imgIndex));
        this.paramMap.put("executecommand", executecommand);
    }

    public UpdateSceneRequest(long sceneid, String scenename, int imgindex) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("scenename", scenename);
        this.paramMap.put("imgindex", Integer.valueOf(imgindex));
    }

    public UpdateSceneRequest(long sceneid, int imgindex) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("imgindex", Integer.valueOf(imgindex));
    }

    public UpdateSceneRequest(long sceneid, long floorid, long roomid) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("floorid", Long.valueOf(floorid));
        this.paramMap.put("roomid", Long.valueOf(roomid));
    }

    public UpdateSceneRequest(long sceneid, String paramext) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("paramext", paramext);
    }

    public UpdateSceneRequest(long sceneid, List<ContentInfo> content) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(sceneid));
        this.paramMap.put("content", GsonUtils.toJson(content));
    }

    public UpdateSceneRequest(long j, boolean z) {
        HashMap<String, Object> hashMap = new HashMap<>();
        this.paramMap = hashMap;
        hashMap.put("sceneid", Long.valueOf(j));
        this.paramMap.put("dynamics", Integer.valueOf(z ? 1 : 0));
    }
}