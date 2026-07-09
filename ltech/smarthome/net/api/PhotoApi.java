package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.photo.ListPhotoResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface PhotoApi extends BaseNetApi {
    public static final String FUN_URL_PANEL_PICTURE_LIST = ApiConstants.FUN_URL_BASE + ".panelpicture.list";
    public static final String FUN_URL_PANEL_PICTURE_SAVE = ApiConstants.FUN_URL_BASE + ".panelpicture.save";
    public static final String FUN_URL_PANEL_PICTURE_DELETE = ApiConstants.FUN_URL_BASE + ".panelpicture.delete";
    public static final String FUN_URL_PANEL_PICTURE_SORT = ApiConstants.FUN_URL_BASE + ".panelpicture.sorting.set";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListPhotoResponse>> listPhoto(@Body RequestBody requestBody);
}