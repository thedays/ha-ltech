package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.super_panel.AddSuperPanelResponse;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelKeywordInfoResponse;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelVoiceControlRangeResponse;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface SuperPanelApi extends BaseNetApi {
    public static final String FUN_URL_PANEL_ADD = ApiConstants.FUN_URL_BASE + ".panel.add";
    public static final String FUN_URL_PANEL_UPDATE = ApiConstants.FUN_URL_BASE + ".panel.update";
    public static final String FUN_URL_PANEL_SET = ApiConstants.FUN_URL_BASE + ".panel.set";
    public static final String FUN_URL_PANEL_UPDATE_SET = ApiConstants.FUN_URL_BASE + ".panel.updateset";
    public static final String FUN_URL_PANEL_DETAIL = ApiConstants.FUN_URL_BASE + ".panel.detail";
    public static final String FUN_URL_PANEL_KEY_INFO = ApiConstants.FUN_URL_BASE + ".panel.keywords.info";
    public static final String FUN_URL_PANEL_KEY_INFO_SET = ApiConstants.FUN_URL_BASE + ".panel.keywords";
    public static final String FUN_URL_PLACE_APPTOKEN = ApiConstants.FUN_URL_BASE + ".panel.getapptoken";
    public static final String FUN_URL_PLACE_OAUTH = ApiConstants.FUN_URL_BASE + ".panel.oauth";
    public static final String FUN_URL_SET_VOICE_CONTROL_RANGE = ApiConstants.FUN_URL_BASE + ".panelauthority.save";
    public static final String FUN_URL_QUERY_VOICE_CONTROL_RANGE = ApiConstants.FUN_URL_BASE + ".panelauthority.query";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddSuperPanelResponse>> addSuperPanel(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<DetailSuperPanelResponse>> detailSuperPanel(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> getAppToken(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> oauth(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QuerySuperPanelKeywordInfoResponse>> querySuperPanelKeywordsInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QuerySuperPanelVoiceControlRangeResponse>> queryVoiceControlRange(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SetSuperPanelResponse>> setSuperPanel(@Body RequestBody requestBody);
}