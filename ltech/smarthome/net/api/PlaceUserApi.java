package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.placeuser.ListPlaceUserResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface PlaceUserApi extends BaseNetApi {
    public static final String FUN_URL_PLACE_USER_ADD = ApiConstants.FUN_URL_BASE + ".placeuser.add";
    public static final String FUN_URL_PLACE_USER_LIST = ApiConstants.FUN_URL_BASE + ".placeuser.list";
    public static final String FUN_URL_PLACE_USER_UPDATE = ApiConstants.FUN_URL_BASE + ".placeuser.update";
    public static final String FUN_URL_PLACE_USER_DELETE = ApiConstants.FUN_URL_BASE + ".placeuser.delete";
    public static final String FUN_URL_PLACE_USER_ROLE_LIST = ApiConstants.FUN_URL_BASE + ".placeuser.devicerole.list";
    public static final String FUN_URL_PLACE_USER_APPLY = ApiConstants.FUN_URL_BASE + ".placeuser.apply";
    public static final String FUN_URL_MANGER_REFUSE = ApiConstants.FUN_URL_BASE + ".placeuser.refuse";
    public static final String FUN_URL_MANGER_AGREE = ApiConstants.FUN_URL_BASE + ".placeuser.agree";
    public static final String FUN_URL_PLACE_USER_INVITATION = ApiConstants.FUN_URL_BASE + ".placeuser.invitation";
    public static final String FUN_URL_PLACE_USER_AGREE_INVITATION = ApiConstants.FUN_URL_BASE + ".placeuser.agreeinvitation";
    public static final String FUN_URL_PLACE_USER_REFUSE_INVITATION = ApiConstants.FUN_URL_BASE + ".placeuser.refuseinvitation";
    public static final String FUN_URL_PLACE_USER_OUT = ApiConstants.FUN_URL_BASE + ".placeuser.out";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListPlaceUserResponse>> listPlaceUser(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResponseBody> listPlaceUserRole(@Body RequestBody requestBody);
}