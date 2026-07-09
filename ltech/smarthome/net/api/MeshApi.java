package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.mesh.GetProvisioningAddressResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface MeshApi extends BaseNetApi {
    public static final String FUN_URL_GET_ADDRESS = ApiConstants.FUN_URL_BASE + ".area.place.getaddress";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetProvisioningAddressResponse>> getProvisioningAddress(@Body RequestBody requestBody);
}