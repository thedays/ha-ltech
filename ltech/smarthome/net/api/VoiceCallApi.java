package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.zhuhai.ltech.lt_voice_call_api.bean.QueryPlaceUserAndPanelResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallSetting;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface VoiceCallApi {
    public static final String FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_ADD = ApiConstants.FUN_URL_BASE + ".panelvoice.group.add";
    public static final String FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_UPDATE = ApiConstants.FUN_URL_BASE + ".panelvoice.group.update";
    public static final String FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_DELETE = ApiConstants.FUN_URL_BASE + ".panelvoice.group.delete";
    public static final String FUN_URL_PANEL_VOICE_CALL_WHITE_LIST_ADD = ApiConstants.FUN_URL_BASE + ".panelvoice.whitelist.add";
    public static final String FUN_URL_PANEL_VOICE_CALL_WHITE_LIST_UPDATE = ApiConstants.FUN_URL_BASE + ".panelvoice.whitelist.update";
    public static final String FUN_URL_PANEL_VOICE_CALL_UNBIND = ApiConstants.FUN_URL_BASE + ".panelvoice.data.unbind";
    public static final String FUN_URL_PANEL_VOICE_CALL_USER_INFO_GET = ApiConstants.FUN_URL_BASE + ".panelvoice.user.getbyids";
    public static final String FUN_URL_PANEL_VOICE_CALL_SETTING_ADD = ApiConstants.FUN_URL_BASE + ".panelvoice.setting.add";
    public static final String FUN_URL_PANEL_VOICE_CALL_SETTING_GET = ApiConstants.FUN_URL_BASE + ".panelvoice.setting.detail";
    public static final String FUN_URL_PANEL_VOICE_CALL_SETTING_UPDATE = ApiConstants.FUN_URL_BASE + ".panelvoice.setting.update";
    public static final String FUN_URL_PANEL_VOICE_CALL_PLACE_USER_GET = ApiConstants.FUN_URL_BASE + ".panelvoice.user.get";
    public static final String FUN_URL_PANEL_VOICE_CALL_GROUP_GET = ApiConstants.FUN_URL_BASE + ".panelvoice.group.detail";
    public static final String FUN_URL_PANEL_VOICE_CALL_MESSAGE_PULL = ApiConstants.FUN_URL_BASE + ".panelvoice.message.pull";
    public static final String FUN_URL_PANEL_VOICE_CALL_MESSAGE_STATUS_PULL = ApiConstants.FUN_URL_BASE + ".panelvoice.message.status.pull";
    public static final String FUN_URL_PANEL_VOICE_CALL_ACTIVE = ApiConstants.FUN_URL_BASE + ".panelvoice.active";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<VoiceCallSetting>> addSetting(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<VoiceCallGroup>> addVoiceGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QuerySettingResponse.VoiceWhiteList>> addWhiteList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> delVoiceGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> messagePull(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> messageStatusPull(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<VoiceCallGroup>> queryGroup(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QueryPlaceUserAndPanelResponse>> queryPlaceUserAndPanel(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QuerySettingResponse>> querySetting(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> unbind(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<VoiceCallSetting>> updateSetting(@Body RequestBody requestBody);
}