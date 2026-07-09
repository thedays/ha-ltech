package com.ltech.smarthome.net.api;

import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.intercom.CommunityInfoResponse;
import com.ltech.smarthome.net.response.intercom.CreateKeyResponse;
import com.ltech.smarthome.net.response.intercom.GetBleCodeResponse;
import com.ltech.smarthome.net.response.intercom.GetCallLogResponse;
import com.ltech.smarthome.net.response.intercom.GetFaceResponse;
import com.ltech.smarthome.net.response.intercom.GetOpenDoorLogResponse;
import com.ltech.smarthome.net.response.intercom.GetPrivateKeyResponse;
import com.ltech.smarthome.net.response.intercom.IntercomLoginResponse;
import com.ltech.smarthome.net.response.intercom.IntercomTokenResponse;
import com.ltech.smarthome.net.response.intercom.KeyListResponse;
import com.ltech.smarthome.net.response.intercom.UploadFaceResponse;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/* loaded from: classes4.dex */
public interface IntercomApi extends BaseNetApi {
    public static final String FUN_MANAGER_URL_LOGIN = ApiConstants.FUN_URL_BASE + ".akuvox.property.login";
    public static final String FUN_GET_COMMUNITY_INFO = ApiConstants.FUN_URL_BASE + ".akuvox.property.comunityinfo";
    public static final String FUN_IMPORT_COMMUNITY = ApiConstants.FUN_URL_BASE + ".akuvox.property.importcommunity";
    public static final String FUN_INTERCOM_OAUTH_LOGIN = ApiConstants.FUN_URL_BASE + ".akuvox.oauthlogin";
    public static final String FUN_GET_USER_INFO = ApiConstants.FUN_URL_BASE + ".akuvox.userinfo";
    public static final String FUN_GET_KEY_LIST = ApiConstants.FUN_URL_BASE + ".akuvox.opendoortempkeylist";
    public static final String FUN_DELETE_KEY_LIST = ApiConstants.FUN_URL_BASE + ".akuvox.opendoortempkeydelete";
    public static final String FUN_CREATE_KEY = ApiConstants.FUN_URL_BASE + ".akuvox.opendoortempkey";
    public static final String FUN_OPEN_DOOR_REMOTE = ApiConstants.FUN_URL_BASE + ".akuvox.opendoorremote";
    public static final String FUN_GET_PRIVATE_KEY = ApiConstants.FUN_URL_BASE + ".akuvox.opendoorprivatekeylist";
    public static final String FUN_SET_PRIVATE_KEY = ApiConstants.FUN_URL_BASE + ".akuvox.opendoorprivatekey";
    public static final String FUN_GET_FACE_STATUS = ApiConstants.FUN_URL_BASE + ".akuvox.getfacestatus";
    public static final String FUN_DELETE_FACE_STATUS = ApiConstants.FUN_URL_BASE + ".akuvox.deleteface";
    public static final String FUN_GET_OPEN_DOOR_LOG = ApiConstants.FUN_URL_BASE + ".akuvox.selectdoorlog";
    public static final String FUN_GET_CALL_LOG = ApiConstants.FUN_URL_BASE + ".akuvox.selectcalllog";
    public static final String FUN_SET_BLE_CODE = ApiConstants.FUN_URL_BASE + ".akuvox.setbleconf";
    public static final String FUN_GET_BLE_CODE = ApiConstants.FUN_URL_BASE + ".akuvox.getbleconf";

    @POST(ApiConstants.REST_URL)
    Observable<CreateKeyResponse> createKeyList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetBleCodeResponse>> getBleCode(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<GetCallLogResponse> getCallLog(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<CommunityInfoResponse> getCommunityInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetFaceResponse>> getFaceStatus(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<KeyListResponse>> getKeyList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<GetOpenDoorLogResponse> getOpenDoorLog(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetPrivateKeyResponse>> getPrivateKey(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<IntercomTokenResponse>> getToken(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<UserInfoResponse>> getUserInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> importCommunityInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<IntercomLoginResponse>> login(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetBleCodeResponse>> setBleCode(@Body RequestBody requestBody);

    @POST
    @Multipart
    Observable<UploadFaceResponse> uploadFaceFile(@Url String url, @Part MultipartBody.Part part);
}