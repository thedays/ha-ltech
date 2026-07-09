package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface BaseNetApi {
    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> requst(@Body RequestBody requestBody);
}