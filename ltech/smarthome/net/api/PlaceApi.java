package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.place.AddPlaceResponse;
import com.ltech.smarthome.net.response.place.DetailPlaceResponse;
import com.ltech.smarthome.net.response.place.GetPlaceDataResponse;
import com.ltech.smarthome.net.response.place.ListPlaceResponse;
import com.ltech.smarthome.net.response.place.QueryPlaceInfoResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface PlaceApi extends BaseNetApi {
    public static final String FUN_URL_PLACE_ADD = ApiConstants.FUN_URL_BASE + ".area.place.add";
    public static final String FUN_URL_PLACE_LIST = ApiConstants.FUN_URL_BASE + ".area.place.user.list";
    public static final String FUN_URL_PLACE_DETAIL = ApiConstants.FUN_URL_BASE + ".area.place.user.detail";
    public static final String FUN_URL_PLACE_UPDATE = ApiConstants.FUN_URL_BASE + ".area.place.user.update";
    public static final String FUN_URL_PLACE_DELETE = ApiConstants.FUN_URL_BASE + ".area.place.user.delete";
    public static final String FUN_URL_PLACE_INFO = ApiConstants.FUN_URL_BASE + ".area.place.user.placeinfo";
    public static final String FUN_URL_PLACE_CHANGE = ApiConstants.FUN_URL_BASE + ".area.place.change";
    public static final String FUN_URL_PLACE_DATA_BACK = ApiConstants.FUN_URL_BASE + ".place.property.save";
    public static final String FUN_URL_GET_PLACE_BACK_DATA = ApiConstants.FUN_URL_BASE + ".place.property.getdata";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddPlaceResponse>> addPlace(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<DetailPlaceResponse>> detailPlace(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetPlaceDataResponse>> getPlaceData(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListPlaceResponse>> listPlace(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QueryPlaceInfoResponse>> queryPlaceDetailInfo(@Body RequestBody requestBody);
}