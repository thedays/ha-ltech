package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.mode.AddModeResponse;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface ModeApi extends BaseNetApi {
    public static final String FUN_URL_MODE_ADD = ApiConstants.FUN_URL_BASE + ".lightmodel.add";
    public static final String FUN_URL_MODE_LIST = ApiConstants.FUN_URL_BASE + ".lightmodel.list";
    public static final String FUN_URL_MODE_DETAIL = ApiConstants.FUN_URL_BASE + ".lightmodel.delete";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddModeResponse>> addMode(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListModeResponse>> getModeList(@Body RequestBody requestBody);
}