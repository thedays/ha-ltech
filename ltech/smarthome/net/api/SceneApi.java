package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.scene.AddSceneResponse;
import com.ltech.smarthome.net.response.scene.DetailSceneResponse;
import com.ltech.smarthome.net.response.scene.ListDaliSceneResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface SceneApi extends BaseNetApi {
    public static final String FUN_URL_SCENE_ADD = ApiConstants.FUN_URL_BASE + ".scene.add";
    public static final String FUN_URL_SCENE_LIST = ApiConstants.FUN_URL_BASE + ".scene.list";
    public static final String FUN_URL_SCENE_DETAIL = ApiConstants.FUN_URL_BASE + ".scene.detail";
    public static final String FUN_URL_SCENE_DELETE = ApiConstants.FUN_URL_BASE + ".scene.del";
    public static final String FUN_URL_SCENE_UPDATE = ApiConstants.FUN_URL_BASE + ".scene.update";
    public static final String FUN_URL_SCENE_ROLE_UPDATE = ApiConstants.FUN_URL_BASE + ".scene.userrole.update";
    public static final String FUN_URL_SCENE_UPDATE_INDEX = ApiConstants.FUN_URL_BASE + ".scene.updatesceneindex";
    public static final String FUN_URL_SCENE_ACTION = ApiConstants.FUN_URL_BASE + ".scene.action";
    public static final String FUN_URL_SCENE_SYNC_BATCH = ApiConstants.FUN_URL_BASE + ".scene.sync.batch";
    public static final String FUN_URL_SCENE_COMMON_SET = ApiConstants.FUN_URL_BASE + ".scene.common.set";
    public static final String FUN_URL_SCENE_COMMON_LIST = ApiConstants.FUN_URL_BASE + ".scene.common.list";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddSceneResponse>> addScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ListDaliSceneResponse> batchSyncScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<DetailSceneResponse>> detailScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListSceneResponse>> listScene(@Body RequestBody requestBody);
}