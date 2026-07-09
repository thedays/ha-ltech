package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.camera.CreateTokenResponse;
import com.ltech.smarthome.net.response.camera.GetTokenResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface EzCameraApi extends BaseNetApi {
    public static final String FUN_CREATE_PLACE_TOKEN = ApiConstants.FUN_URL_BASE + ".yingshi.createplacetoken";
    public static final String FUN_GET_PLACE_TOKEN = ApiConstants.FUN_URL_BASE + ".yingshi.getplacetoken";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<CreateTokenResponse>> createPlaceToken(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetTokenResponse>> getPlaceToken(@Body RequestBody requestBody);
}