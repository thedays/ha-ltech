package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.mcu.ListMcuResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface McuApi extends BaseNetApi {
    public static final String FUN_URL_DEVICE_MCU_VERSION = ApiConstants.FUN_URL_BASE + ".leite.app.mcu.deviceversion.list";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListMcuResponse>> getMcuList(@Body RequestBody requestBody);
}