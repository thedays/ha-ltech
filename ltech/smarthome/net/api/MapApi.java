package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.map.GeocodeResponse;
import com.ltech.smarthome.net.response.map.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes4.dex */
public interface MapApi {
    @GET("https://restapi.amap.com/v3/geocode/regeo?")
    Call<GeocodeResponse> geocode(@Query("key") String key, @Query("location") String location, @Query("radius") String radius);

    @GET("https://restapi.amap.com/v5/place/text?")
    Call<PlaceResponse> getPlace(@Query("key") String key, @Query("keywords") String keywords, @Query("region") String region);
}