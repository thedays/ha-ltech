package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.room.AddRoomResponse;
import com.ltech.smarthome.net.response.room.ListRoomResponse;
import com.ltech.smarthome.net.response.room.ListUserRoomResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface RoomApi extends BaseNetApi {
    public static final String FUN_URL_ROOM_ADD = ApiConstants.FUN_URL_BASE + ".area.room.add";
    public static final String FUN_URL_ROOM_LIST = ApiConstants.FUN_URL_BASE + ".area.room.list";
    public static final String FUN_URL_ROOM_UPDATE = ApiConstants.FUN_URL_BASE + ".area.room.update";
    public static final String FUN_URL_ROOM_DELETE = ApiConstants.FUN_URL_BASE + ".area.room.delete";
    public static final String FUN_URL_ROOM_USER_LIST = ApiConstants.FUN_URL_BASE + ".area.room.user.list";
    public static final String FUN_URL_ROOM_UPDATE_INDEX = ApiConstants.FUN_URL_BASE + ".index.updateroomindex";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddRoomResponse>> addRoom(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListRoomResponse>> listRoom(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListUserRoomResponse>> listUserRoom(@Body RequestBody requestBody);
}