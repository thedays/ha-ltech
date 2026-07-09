package com.ltech.smarthome.net.api;

import com.ltech.smarthome.model.bean.SunInfo;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.net.response.user.BindUserResponse;
import com.ltech.smarthome.net.response.user.ChangPhoneResponse;
import com.ltech.smarthome.net.response.user.ChangeEmailResponse;
import com.ltech.smarthome.net.response.user.CountryListInfoResponse;
import com.ltech.smarthome.net.response.user.GetUserInfoResponse;
import com.ltech.smarthome.net.response.user.LoginResponse;
import com.ltech.smarthome.net.response.user.QuerySpeakerResponse;
import com.ltech.smarthome.net.response.user.UpdateUserInfoResponse;
import com.ltech.smarthome.net.response.user.UploadImageFileResponse;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/* loaded from: classes4.dex */
public interface UserApi extends BaseNetApi {
    public static final long APP_ID_DEFAULT = 119011416470103L;
    public static final int CODE_ACTION_TYPE_DEFAULT = 1;
    public static final int CODE_ACTION_TYPE_FIND_PWD = 4;
    public static final int CODE_ACTION_TYPE_LOGIN = 3;
    public static final int CODE_ACTION_TYPE_REGISTER = 2;
    public static final String DEVICE_TYPE_ANDROID = "2";
    public static final String DEVICE_TYPE_IOS = "3";
    public static final String DEVICE_TYPE_PC = "1";
    public static final String DEVICE_TYPE_SOFEWARE = "4";
    public static final String FUN_URL_UPLOAD_USER_HEAD_DEV = "http://trytest.granwin.com:8088/ysnetworkweb/file/upload.do";
    public static final String FUN_URL_UPLOAD_USER_HEAD_STAGE = "https://apic.ltsys.com.cn/ysnetworkweb/file/uploading.do";
    public static final String SECRET_KEY_DEFAULT = "XmbIvNhMxKQuVd03";
    public static final String SESSION_DEFAULT = "cqq577bze26adn1wbt5uz0nwk7zlfs03";
    public static final String SOURCE_FACEBOOK = "Facebook";
    public static final String SOURCE_QQ = "QQ";
    public static final String SOURCE_TWITTER = "Twitter";
    public static final String SOURCE_WENXIN = "weixin";
    public static final String TYPE_EMAIL = "2";
    public static final String TYPE_PHONE = "1";
    public static final String FUN_URL_REGISTER = ApiConstants.FUN_URL_BASE + ".user.register";
    public static final String FUN_URL_LOGIN = ApiConstants.FUN_URL_BASE + ".user.app.login";
    public static final String FUN_URL_OAUTH_LOGIN = ApiConstants.FUN_URL_BASE + ".user.oauthlogin";
    public static final String FUN_URL_OAUTH_BIND = ApiConstants.FUN_URL_BASE + ".user.oauthbind";
    public static final String FUN_URL_PASSWORD_SET = ApiConstants.FUN_URL_BASE + ".user.password.set";
    public static final String FUN_URL_SEND_EMAIL_CODE = ApiConstants.FUN_URL_BASE + ".email.sendcode";
    public static final String FUN_URL_SEND_SMS_CODE = ApiConstants.FUN_URL_BASE + ".sms.verifycode.send";
    public static final String FUN_URL_BIND_USER = ApiConstants.FUN_URL_BASE + ".deviceparam.binduser";
    public static final String FUN_URL_USER_INFO_GET = ApiConstants.FUN_URL_BASE + ".user.get";
    public static final String FUN_URL_USER_INFO_UPDATE = ApiConstants.FUN_URL_BASE + ".user.newupdate";
    public static final String FUN_URL_CHANGE_PHONE = ApiConstants.FUN_URL_BASE + ".user.changephone";
    public static final String FUN_URL_CHANGE_EMAIL = ApiConstants.FUN_URL_BASE + ".user.changeemail";
    public static final String FUN_URL_CHANGE_PWD = ApiConstants.FUN_URL_BASE + ".user.password.rest";
    public static final String FUN_URL_USER_REMOVE = ApiConstants.FUN_URL_BASE + ".user.remove";
    public static final String FUN_URL_GET_COUNTRY_INFO_LIST = ApiConstants.FUN_URL_BASE + ".region.selcountrys";
    public static final String CHECK_VERSION_UPDATE = ApiConstants.FUN_URL_BASE + ".appversion.check";
    public static final String FUN_URL_FEEDBACK_ADD = ApiConstants.FUN_URL_BASE + ".feedback.add";
    public static final String FUN_URL_FEEDBACK_LIST = ApiConstants.FUN_URL_BASE + ".feedback.list";
    public static final String SUN_TIME = ApiConstants.FUN_URL_BASE + ".app.weather.sun";
    public static final String SPEAKER_SET_PLACE = ApiConstants.FUN_URL_BASE + ".baseplace.set";
    public static final String SPEAKER_QUERY_PLACE = ApiConstants.FUN_URL_BASE + ".baseplace.query";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<BindUserResponse>> bindUser(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ChangeEmailResponse>> changeEmail(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ChangPhoneResponse>> changePhone(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<VersionInfo>> checkVersionUpdate(@Body RequestBody requestBody);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<CountryListInfoResponse>> getCountryList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<SunInfo.SunBean>> getSunTime(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetUserInfoResponse>> getUserInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<LoginResponse>> login(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<LoginResponse>> oauthBind(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<LoginResponse>> oauthLogin(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<QuerySpeakerResponse>>> querySpeakerPlace(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<UpdateUserInfoResponse>> updateUserInfo(@Body RequestBody requestBody);

    @POST
    @Multipart
    Observable<ResultBean<UploadImageFileResponse>> uploadHeadFile(@Url String url, @Part MultipartBody.Part part);
}