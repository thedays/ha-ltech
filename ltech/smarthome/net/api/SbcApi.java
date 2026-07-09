package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.super_panel.AddSuperPanelResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface SbcApi extends BaseNetApi {
    public static final String FUN_URL_SBC_PANEL_ADD = ApiConstants.FUN_URL_BASE + ".panel.addsbc";
    public static final String FUN_URL_SBC_AUTH_NOTIFY = ApiConstants.FUN_URL_BASE + ".panel.notifysbc";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddSuperPanelResponse>> addSbcPanel(@Body RequestBody requestBody);
}