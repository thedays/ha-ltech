package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.floor.AddFloorResponse;
import com.ltech.smarthome.net.response.floor.ListFloorResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface FloorApi extends BaseNetApi {
    public static final String FUN_URL_FLOOR_ADD = ApiConstants.FUN_URL_BASE + ".area.floor.add";
    public static final String FUN_URL_FLOOR_LIST = ApiConstants.FUN_URL_BASE + ".area.floor.list";
    public static final String FUN_URL_FLOOR_UPDATE = ApiConstants.FUN_URL_BASE + ".area.floor.update";
    public static final String FUN_URL_FLOOR_DELETE = ApiConstants.FUN_URL_BASE + ".area.floor.delete";
    public static final String FUN_URL_FLOOR_UPDATE_INDEX = ApiConstants.FUN_URL_BASE + ".index.updatefloorindex";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddFloorResponse>> addFloor(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListFloorResponse>> listFloor(@Body RequestBody requestBody);
}