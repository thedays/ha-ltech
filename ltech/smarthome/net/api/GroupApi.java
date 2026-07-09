package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import com.ltech.smarthome.net.response.group.AddGroupResponse;
import com.ltech.smarthome.net.response.group.GradientSceneResponse;
import com.ltech.smarthome.net.response.group.ListDaliGroupResponse;
import com.ltech.smarthome.net.response.group.ListGroupResponse;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface GroupApi extends BaseNetApi {
    public static final String FUN_URL_ADD_GROUP = ApiConstants.FUN_URL_BASE + ".group.add";
    public static final String FUN_URL_LIST_GROUP = ApiConstants.FUN_URL_BASE + ".group.list";
    public static final String FUN_URL_DETAIL_GROUP = ApiConstants.FUN_URL_BASE + ".group.detail";
    public static final String FUN_URL_UPDATE_GROUP = ApiConstants.FUN_URL_BASE + ".group.update";
    public static final String FUN_URL_GROUP_SCENE_ACTIONS = ApiConstants.FUN_URL_BASE + ".scene.selectscenebygroup";
    public static final String FUN_URL_UPDATE_GROUP_LOCATION = ApiConstants.FUN_URL_BASE + ".group.updatelocation";
    public static final String FUN_URL_DELETE_GROUP = ApiConstants.FUN_URL_BASE + ".group.delete";
    public static final String FUN_URL_SORT_GROUP = ApiConstants.FUN_URL_BASE + ".group.updateindex";
    public static final String FUN_URL_GROUP_QUERY_SCENE = ApiConstants.FUN_URL_BASE + ".group.scene.query";
    public static final String FUN_URL_GROUP_QUERY_AUTOMATION = ApiConstants.FUN_URL_BASE + ".group.automationaction.query";
    public static final String FUN_URL_GROUP_SYNC_BATCH = ApiConstants.FUN_URL_BASE + ".group.sync.batch";
    public static final String FUN_URL_GROUP_SCENE_ADD = ApiConstants.FUN_URL_BASE + ".group.scene.add";
    public static final String FUN_URL_GROUP_GRADIENT_SCENE_LIST = ApiConstants.FUN_URL_BASE + ".group.scene.list";
    public static final String FUN_URL_GROUP_GRADIENT_SCENE_DELETE = ApiConstants.FUN_URL_BASE + ".group.scene.delete";
    public static final String FUN_URL_GROUP_GRADIENT_SCENE_UPDATE = ApiConstants.FUN_URL_BASE + ".group.scene.update";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddGroupResponse>> addGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> delGradientScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListGroupResponse.RowsBean>> detailGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> editeGradientScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QuerySceneActionResponse>> groupSceneAction(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListAutomationResponse>> listAutomation(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListGroupResponse>> listGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListSceneResponse>> listScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GradientSceneResponse>> queryGradientScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ListDaliGroupResponse> updateDaliGroupList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<UpdateGroupResponse>> updateGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> updateGroupInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<UpdateGroupResponse>> updateGroupLocation(@Body RequestBody requestBody);
}