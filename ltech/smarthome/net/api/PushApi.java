package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.push.AppNoticeListResponse;
import com.ltech.smarthome.net.response.push.ListNoHandleMsgResponse;
import com.ltech.smarthome.net.response.push.PlaceMsgListResponse;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface PushApi extends BaseNetApi {
    public static final String FUN_URL_APP_NOTICE_LIST = "ysnetwork.push.com.message.appnoticelist";
    public static final String FUN_URL_CHANGE_LANGUAGE = "ysnetwork.push.com.message.settype";
    public static final String FUN_URL_MESSAGE_DELETE = "ysnetwork.push.com.message.delete";
    public static final String FUN_URL_MSG_PLACE_LIST = "ysnetwork.push.com.message.placelist";
    public static final String FUN_URL_NOTICE_LIST = "ysnetwork.push.com.message.noticelist";
    public static final String FUN_URL_PUSH_BIND = "ysnetwork.push.com.message.bind";
    public static final String FUN_URL_PUSH_NO_HANDLE_LIST = "ysnetwork.push.com.message.placenothandellist";
    public static final String FUN_URL_PUSH_TEST = "ysnetwork.push.com.message.push";
    public static final String FUN_URL_PUSH_UNBIND = "ysnetwork.push.com.message.unbind";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AppNoticeListResponse>> appNoticeList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<ListNoHandleMsgResponse.RowData>>> listNoHandleMessage(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<PlaceMsgListResponse>> placeMsgList(@Body RequestBody requestBody);
}