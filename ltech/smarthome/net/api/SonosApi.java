package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import io.reactivex.Observable;
import java.util.List;
import java.util.Objects;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface SonosApi extends BaseNetApi {
    public static final String GET_SONOS_URL = ApiConstants.FUN_URL_BASE + ".sonos.getauthurl";
    public static final String GET_SONOS_GET_TOKEN = ApiConstants.FUN_URL_BASE + ".sonos.gettoken";
    public static final String GET_SONOS_GET_HOUSEHOLDS = ApiConstants.FUN_URL_BASE + ".sonos.gethouseholds";
    public static final String GET_SONOS_GET_GROUPS = ApiConstants.FUN_URL_BASE + ".sonos.getgroups";
    public static final String GET_SONOS_GET_STATUS = ApiConstants.FUN_URL_BASE + ".sonos.playback.getstatus";
    public static final String GET_SONOS_PLAY_PAUSE = ApiConstants.FUN_URL_BASE + ".sonos.toggleplaypause";
    public static final String GET_SONOS_SET_NEXT = ApiConstants.FUN_URL_BASE + ".sonos.skiptonexttrack";
    public static final String GET_SONOS_SET_PRE = ApiConstants.FUN_URL_BASE + ".sonos.skiptoprevioustrack";
    public static final String GET_SONOS_SET_MODES = ApiConstants.FUN_URL_BASE + ".sonos.setplaymodes";
    public static final String GET_SONOS_GET_VOLUME = ApiConstants.FUN_URL_BASE + ".sonos.groups.getvolume";
    public static final String GET_SONOS_SET_VOLUME = ApiConstants.FUN_URL_BASE + ".sonos.groups.setvolume";
    public static final String GET_SONOS_SET_MUTE = ApiConstants.FUN_URL_BASE + ".sonos.groups.setmute";
    public static final String GET_SONOS_METADATA_GET_STATUS = ApiConstants.FUN_URL_BASE + ".sonos.metadata.getstatus";
    public static final String GET_SONOS_BIND_DEVICE = ApiConstants.FUN_URL_BASE + ".sonos.bind.device";
    public static final String GET_SONOS_FAVORITES = ApiConstants.FUN_URL_BASE + ".sonos.getfavorites";
    public static final String GET_SONOS_UNBIND_DEVICE = ApiConstants.FUN_URL_BASE + ".sonos.unbind.device";
    public static final String GET_SONOS_PLAY_FAVORITES = ApiConstants.FUN_URL_BASE + ".sonos.favorite.load";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<ListDeviceResponse.RowsBean>>> getBindDevices(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SonosResponse>> getSonosGroups(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SonosResponse>> getSonosHouseholds(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SonosResponse.PlayStatus>> getSonosPlayStatus(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SonosResponse>> getSonosToken(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<String>> getSonosUrl(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<SonosResponse.Favorites>>> sonosFavorites(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SonosResponse.Volume>> sonosGetVolume(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SonosResponse.Metadata>> sonosMetadataGetStatus(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Objects>> sonosMode(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Objects>> sonosNext(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Objects>> sonosPlayPause(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Objects>> sonosPre(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Objects>> sonosSetMute(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Objects>> sonosSetVolume(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<String>> sonosUnbind(@Body RequestBody requestBody);
}