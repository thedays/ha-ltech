package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface RoleApi extends BaseNetApi {
    public static final String FUN_URL_DEVICE_ROLE_LIST = "ysnetwork.base.com.placeuser.devicerole.list";
    public static final String FUN_URL_DEVICE_ROLE_UPDATE = "ysnetwork.base.com.placeuser.devicerole.update";
    public static final String FUN_URL_GROUP_ROLE_UPDATE = "ysnetwork.base.com.group.updaterole";
    public static final String FUN_URL_ROOM_ROLE_UPDATE = "ysnetwork.base.com.area.roomrole.user.update";
    public static final String FUN_URL_SCENE_ROLE_UPDATE = "ysnetwork.base.com.scene.userrole.update";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListDeviceResponse>> listDevice(@Body RequestBody requestBody);
}