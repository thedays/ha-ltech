package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.automation.AddAutomationResponse;
import com.ltech.smarthome.net.response.automation.DetailAutomationResponse;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface AutomationApi extends BaseNetApi {
    public static final String FUN_URL_ADD_AUTOMATION = "ysnetwork.timer.com.automation.add";
    public static final String FUN_URL_DEL_AUTOMATION = "ysnetwork.timer.com.automation.delautomation";
    public static final String FUN_URL_DETAIL_AUTOMATION = "ysnetwork.timer.com.automation.detailautomation";
    public static final String FUN_URL_EDIT_AUTOMATION = "ysnetwork.timer.com.automation.editautomation";
    public static final String FUN_URL_ENABLE_AUTOMATION = "ysnetwork.timer.com.automation.updateautomation";
    public static final String FUN_URL_QUERY_AUTOMATION = "ysnetwork.timer.com.automation.selautomations";
    public static final String FUN_URL_REPORT_AUTOMATION = "ysnetwork.timer.com.automation.mationreport";
    public static final String FUN_URL_SORT_AUTOMATION = "ysnetwork.timer.com.automation.batchsort";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddAutomationResponse>> addAutomation(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<DetailAutomationResponse>> detailAutomation(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListAutomationResponse>> listAutomation(@Body RequestBody requestBody);
}