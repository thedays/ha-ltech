package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.music.MusicListResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface MusicApi extends BaseNetApi {
    public static final String FUN_URL_MUSIC_UPDATE = ApiConstants.FUN_URL_BASE + ".musicinfo.update";
    public static final String FUN_URL_MUSIC_GET_LIST = ApiConstants.FUN_URL_BASE + ".musicinfo.list";
    public static final String FUN_URL_MUSIC_GET_LOCAL_LIST = ApiConstants.FUN_URL_BASE + ".panelsong.list";
    public static final String FUN_URL_MUSIC_SORT_LOCAL_LIST = ApiConstants.FUN_URL_BASE + ".panelsong.sorting.set";
    public static final String FUN_URL_MUSIC_DEL_LOCAL_LIST = ApiConstants.FUN_URL_BASE + ".panelsong.delete";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<MusicListResponse>> getLocalMusicList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<MusicListResponse>> getMusicInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> updateMusicInfo(@Body RequestBody requestBody);
}