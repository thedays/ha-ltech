package com.ltech.smarthome.net;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.GradientScene;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.model.bean.MusicInfo;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SunInfo;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.model.key.KeyZone;
import com.ltech.smarthome.net.TrustAllCertsUtils;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.api.AutomationApi;
import com.ltech.smarthome.net.api.DeviceApi;
import com.ltech.smarthome.net.api.EzCameraApi;
import com.ltech.smarthome.net.api.FloorApi;
import com.ltech.smarthome.net.api.GroupApi;
import com.ltech.smarthome.net.api.IntercomApi;
import com.ltech.smarthome.net.api.MapApi;
import com.ltech.smarthome.net.api.McuApi;
import com.ltech.smarthome.net.api.MeshApi;
import com.ltech.smarthome.net.api.ModeApi;
import com.ltech.smarthome.net.api.MusicApi;
import com.ltech.smarthome.net.api.PhotoApi;
import com.ltech.smarthome.net.api.PlaceApi;
import com.ltech.smarthome.net.api.PlaceUserApi;
import com.ltech.smarthome.net.api.PushApi;
import com.ltech.smarthome.net.api.RoleApi;
import com.ltech.smarthome.net.api.RoomApi;
import com.ltech.smarthome.net.api.SbcApi;
import com.ltech.smarthome.net.api.SceneApi;
import com.ltech.smarthome.net.api.SonosApi;
import com.ltech.smarthome.net.api.SuperPanelApi;
import com.ltech.smarthome.net.api.UserApi;
import com.ltech.smarthome.net.api.VoiceCallApi;
import com.ltech.smarthome.net.exception.ApiException;
import com.ltech.smarthome.net.request.CheckVersionRequest;
import com.ltech.smarthome.net.request.RequestObject;
import com.ltech.smarthome.net.request.VoiceCallMessagePullRequest;
import com.ltech.smarthome.net.request.VoiceCallRequest;
import com.ltech.smarthome.net.request.automation.AddAutomationRequest;
import com.ltech.smarthome.net.request.automation.DelAutomationRequest;
import com.ltech.smarthome.net.request.automation.DetailAutomationRequest;
import com.ltech.smarthome.net.request.automation.EditAutomationRequest;
import com.ltech.smarthome.net.request.automation.EnableAutomationRequest;
import com.ltech.smarthome.net.request.automation.ListAutomationRequest;
import com.ltech.smarthome.net.request.automation.ReportAutomationRequest;
import com.ltech.smarthome.net.request.automation.SortAutomationRequest;
import com.ltech.smarthome.net.request.camera.EzPlaceRequest;
import com.ltech.smarthome.net.request.device.DeleteDeviceHasGroupIdRequest;
import com.ltech.smarthome.net.request.device.DeleteDeviceRequest;
import com.ltech.smarthome.net.request.device.DeviceControlRequest;
import com.ltech.smarthome.net.request.device.DeviceRequest;
import com.ltech.smarthome.net.request.device.GetDeviceOnlineRequest;
import com.ltech.smarthome.net.request.device.KeyInfoRequest;
import com.ltech.smarthome.net.request.device.ListDeviceRequest;
import com.ltech.smarthome.net.request.device.ListMcuRequest;
import com.ltech.smarthome.net.request.device.PanelSortInfoRequest;
import com.ltech.smarthome.net.request.device.QueryByDeviceIdRequest;
import com.ltech.smarthome.net.request.device.SortDeviceAndGroupRequest;
import com.ltech.smarthome.net.request.device.SortDeviceRequest;
import com.ltech.smarthome.net.request.device.SuperPanelReplaceRequest;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.request.device.UpdateDaliDeviceRequest;
import com.ltech.smarthome.net.request.device.UpdateDeviceRequest;
import com.ltech.smarthome.net.request.device.UpdateGroupIconRequest;
import com.ltech.smarthome.net.request.floor.AddFloorRequest;
import com.ltech.smarthome.net.request.floor.DeleteFloorRequest;
import com.ltech.smarthome.net.request.floor.ListFloorRequest;
import com.ltech.smarthome.net.request.floor.SortFloorRequest;
import com.ltech.smarthome.net.request.floor.UpdateFloorRequest;
import com.ltech.smarthome.net.request.group.AddGroupRequest;
import com.ltech.smarthome.net.request.group.DeleteGroupRequest;
import com.ltech.smarthome.net.request.group.DetailGroupRequest;
import com.ltech.smarthome.net.request.group.ListGroupRequest;
import com.ltech.smarthome.net.request.group.QueryByGroupIdRequest;
import com.ltech.smarthome.net.request.group.SortGroupRequest;
import com.ltech.smarthome.net.request.group.UpdateDaliGroupRequest;
import com.ltech.smarthome.net.request.group.UpdateGroupDataRequest;
import com.ltech.smarthome.net.request.group.UpdateGroupParamRequest;
import com.ltech.smarthome.net.request.group.UpdateGroupRequest;
import com.ltech.smarthome.net.request.intercom.ImportCommunityRequest;
import com.ltech.smarthome.net.request.intercom.IntercomBleCodeRequest;
import com.ltech.smarthome.net.request.intercom.IntercomCreateKeyRequest;
import com.ltech.smarthome.net.request.intercom.IntercomLoginRequest;
import com.ltech.smarthome.net.request.intercom.IntercomOpenDoorRemoteRequest;
import com.ltech.smarthome.net.request.intercom.IntercomSetKeyRequest;
import com.ltech.smarthome.net.request.intercom.IntercomTempKeyRequest;
import com.ltech.smarthome.net.request.log.QueryLogRequest;
import com.ltech.smarthome.net.request.mesh.GetProvisioningAddressRequest;
import com.ltech.smarthome.net.request.mode.AddModeRequest;
import com.ltech.smarthome.net.request.mode.DeleteModeRequest;
import com.ltech.smarthome.net.request.mode.ListModeRequest;
import com.ltech.smarthome.net.request.music.MusicGetListRequest;
import com.ltech.smarthome.net.request.music.MusicRequest;
import com.ltech.smarthome.net.request.music.SongRequest;
import com.ltech.smarthome.net.request.photo.DeletePhotoRequest;
import com.ltech.smarthome.net.request.photo.ListPhotoRequest;
import com.ltech.smarthome.net.request.photo.SortPhotoRequest;
import com.ltech.smarthome.net.request.photo.UploadPhotoRequest;
import com.ltech.smarthome.net.request.place.AddPlaceRequest;
import com.ltech.smarthome.net.request.place.DeletePlaceRequest;
import com.ltech.smarthome.net.request.place.DetailPlaceRequest;
import com.ltech.smarthome.net.request.place.ListPlaceRequest;
import com.ltech.smarthome.net.request.place.TransferPlaceRequest;
import com.ltech.smarthome.net.request.place.UpdatePlaceDataRequest;
import com.ltech.smarthome.net.request.place.UpdatePlaceRequest;
import com.ltech.smarthome.net.request.placeuser.AddPlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.AgreeInvitationRequest;
import com.ltech.smarthome.net.request.placeuser.AgreePlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.DeletePlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.InvitationPlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.ListPlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.PlaceUserApplyRequest;
import com.ltech.smarthome.net.request.placeuser.QuitPlaceRequest;
import com.ltech.smarthome.net.request.placeuser.RefuseInvitationRequest;
import com.ltech.smarthome.net.request.placeuser.RefusePlaceUserRequest;
import com.ltech.smarthome.net.request.placeuser.UpdatePlaceUserRequest;
import com.ltech.smarthome.net.request.push.PushBindRequest;
import com.ltech.smarthome.net.request.push.PushMessageDeleteRequest;
import com.ltech.smarthome.net.request.push.PushMsgAppNoticeListRequest;
import com.ltech.smarthome.net.request.push.PushMsgNoHandleListRequest;
import com.ltech.smarthome.net.request.push.PushMsgPlaceListRequest;
import com.ltech.smarthome.net.request.push.PushTestRequest;
import com.ltech.smarthome.net.request.push.SetLanguageRequest;
import com.ltech.smarthome.net.request.role.UpdateDeviceRoleRequest;
import com.ltech.smarthome.net.request.role.UpdateGroupRoleRequest;
import com.ltech.smarthome.net.request.role.UpdateRoomRoleRequest;
import com.ltech.smarthome.net.request.role.UpdateSceneRoleRequest;
import com.ltech.smarthome.net.request.room.AddRoomRequest;
import com.ltech.smarthome.net.request.room.DeleteRoomRequest;
import com.ltech.smarthome.net.request.room.ListRoomRequest;
import com.ltech.smarthome.net.request.room.ListUserRoomRequest;
import com.ltech.smarthome.net.request.room.RoomName;
import com.ltech.smarthome.net.request.room.SortRoomRequest;
import com.ltech.smarthome.net.request.room.UpdateRoomRequest;
import com.ltech.smarthome.net.request.rs8.Rs8Request;
import com.ltech.smarthome.net.request.scene.AddSceneRequest;
import com.ltech.smarthome.net.request.scene.CommonSceneRequest;
import com.ltech.smarthome.net.request.scene.DaliSceneRequest;
import com.ltech.smarthome.net.request.scene.DeleteSceneRequest;
import com.ltech.smarthome.net.request.scene.DetailSceneRequest;
import com.ltech.smarthome.net.request.scene.DeviceActionRequest;
import com.ltech.smarthome.net.request.scene.ExecuteSceneRequest;
import com.ltech.smarthome.net.request.scene.GroupActionRequest;
import com.ltech.smarthome.net.request.scene.ListSceneRequest;
import com.ltech.smarthome.net.request.scene.SortSceneRequest;
import com.ltech.smarthome.net.request.scene.UpdateSceneRequest;
import com.ltech.smarthome.net.request.super_panel.AddSbcPanelRequest;
import com.ltech.smarthome.net.request.super_panel.AddSuperPanelRequest;
import com.ltech.smarthome.net.request.super_panel.AppTokenRequest;
import com.ltech.smarthome.net.request.super_panel.DetailSuperPanelRequest;
import com.ltech.smarthome.net.request.super_panel.NotifySbcRequest;
import com.ltech.smarthome.net.request.super_panel.OauthRequest;
import com.ltech.smarthome.net.request.super_panel.QuerySuperPanelKeywordInfoRequest;
import com.ltech.smarthome.net.request.super_panel.SetSuperPanelKeyInfoRequest;
import com.ltech.smarthome.net.request.super_panel.SetSuperPanelRequest;
import com.ltech.smarthome.net.request.super_panel.SetVoiceControlRangeRequest;
import com.ltech.smarthome.net.request.user.ChangeEmailRequest;
import com.ltech.smarthome.net.request.user.ChangePhoneRequest;
import com.ltech.smarthome.net.request.user.ChangePwdRequest;
import com.ltech.smarthome.net.request.user.EmailCodeRequest;
import com.ltech.smarthome.net.request.user.FeedbackAddRequest;
import com.ltech.smarthome.net.request.user.GetUserInfoRequest;
import com.ltech.smarthome.net.request.user.LoginRequest;
import com.ltech.smarthome.net.request.user.OauthBindRequest;
import com.ltech.smarthome.net.request.user.OauthLoginRequest;
import com.ltech.smarthome.net.request.user.QuerySpeakerPlaceRequest;
import com.ltech.smarthome.net.request.user.RegisterRequest;
import com.ltech.smarthome.net.request.user.RemoveUserRequest;
import com.ltech.smarthome.net.request.user.SetPasswordRequest;
import com.ltech.smarthome.net.request.user.SetSpeakerPlaceRequest;
import com.ltech.smarthome.net.request.user.SmsCodeRequest;
import com.ltech.smarthome.net.request.user.SunTimeRequest;
import com.ltech.smarthome.net.request.user.UpdateUserInfoRequest;
import com.ltech.smarthome.net.response.MatterDeviceResponse;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.net.response.automation.AddAutomationResponse;
import com.ltech.smarthome.net.response.automation.DetailAutomationResponse;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import com.ltech.smarthome.net.response.camera.CreateTokenResponse;
import com.ltech.smarthome.net.response.camera.GetTokenResponse;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.device.GetDeviceOnlineResponse;
import com.ltech.smarthome.net.response.device.GetReplaceDataResponse;
import com.ltech.smarthome.net.response.device.KeyInfoResponse;
import com.ltech.smarthome.net.response.device.KeyZonesResponse;
import com.ltech.smarthome.net.response.device.ListDaliDeviceResponse;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.net.response.floor.AddFloorResponse;
import com.ltech.smarthome.net.response.floor.ListFloorResponse;
import com.ltech.smarthome.net.response.group.AddGroupResponse;
import com.ltech.smarthome.net.response.group.GradientSceneResponse;
import com.ltech.smarthome.net.response.group.ListDaliGroupResponse;
import com.ltech.smarthome.net.response.group.ListGroupResponse;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
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
import com.ltech.smarthome.net.response.log.ListLogDateResponse;
import com.ltech.smarthome.net.response.log.ListLogResponse;
import com.ltech.smarthome.net.response.map.GeocodeResponse;
import com.ltech.smarthome.net.response.map.PlaceResponse;
import com.ltech.smarthome.net.response.mcu.ListMcuResponse;
import com.ltech.smarthome.net.response.mesh.GetProvisioningAddressResponse;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.net.response.music.MusicListResponse;
import com.ltech.smarthome.net.response.photo.ListPhotoResponse;
import com.ltech.smarthome.net.response.place.AddPlaceResponse;
import com.ltech.smarthome.net.response.place.DetailPlaceResponse;
import com.ltech.smarthome.net.response.place.GetPlaceDataResponse;
import com.ltech.smarthome.net.response.place.ListPlaceResponse;
import com.ltech.smarthome.net.response.place.QueryPlaceInfoResponse;
import com.ltech.smarthome.net.response.placeuser.ListPlaceUserResponse;
import com.ltech.smarthome.net.response.push.AppNoticeListResponse;
import com.ltech.smarthome.net.response.push.ListNoHandleMsgResponse;
import com.ltech.smarthome.net.response.push.PlaceMsgListResponse;
import com.ltech.smarthome.net.response.room.AddRoomResponse;
import com.ltech.smarthome.net.response.room.ListRoomResponse;
import com.ltech.smarthome.net.response.room.ListUserRoomResponse;
import com.ltech.smarthome.net.response.rs8.Rs8BrandResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CategoryResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.net.response.scene.AddSceneResponse;
import com.ltech.smarthome.net.response.scene.DetailSceneResponse;
import com.ltech.smarthome.net.response.scene.ListDaliSceneResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import com.ltech.smarthome.net.response.super_panel.AddSuperPanelResponse;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.net.response.super_panel.ListNeedUpgradeResponse;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelKeywordInfoResponse;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelVoiceControlRangeResponse;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.net.response.user.BindUserResponse;
import com.ltech.smarthome.net.response.user.ChangPhoneResponse;
import com.ltech.smarthome.net.response.user.ChangeEmailResponse;
import com.ltech.smarthome.net.response.user.CountryListInfoResponse;
import com.ltech.smarthome.net.response.user.GetUserInfoResponse;
import com.ltech.smarthome.net.response.user.LoginResponse;
import com.ltech.smarthome.net.response.user.QuerySpeakerResponse;
import com.ltech.smarthome.net.response.user.UpdateUserInfoResponse;
import com.ltech.smarthome.net.response.user.UploadImageFileResponse;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.sonos.SonosRequest;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.utils.LHomeLog;
import com.zhuhai.ltech.lt_voice_call_api.bean.QueryPlaceUserAndPanelResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallSetting;
import io.netty.handler.codec.http.HttpHeaders;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes4.dex */
public final class SmartHomeRetrofit extends AbstractRetrofit {
    public static int timeOutSeconds = 15;
    private OkHttpClient client;
    private final HttpLoggingInterceptor loggingInterceptor;

    private SmartHomeRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(this) { // from class: com.ltech.smarthome.net.SmartHomeRetrofit.1
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public void log(String message) {
                try {
                    String decode = URLDecoder.decode(message.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8");
                    if (decode.length() <= 3072) {
                        LHomeLog.e(Constants.HTTP_LOG, getClass(), decode);
                        return;
                    }
                    while (decode.length() > 3072) {
                        String substring = decode.substring(0, 3072);
                        decode = decode.replace(substring, "");
                        LHomeLog.e(Constants.HTTP_LOG, getClass(), substring);
                    }
                    LHomeLog.e(Constants.HTTP_LOG, getClass(), decode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LHomeLog.e(Constants.HTTP_LOG, getClass(), e.getMessage());
                }
            }
        });
        this.loggingInterceptor = httpLoggingInterceptor;
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        this.client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).connectTimeout(timeOutSeconds, TimeUnit.SECONDS).readTimeout(timeOutSeconds, TimeUnit.SECONDS).sslSocketFactory(TrustAllCertsUtils.createSSLSocketFactory(), TrustAllCertsUtils.getInstance()).hostnameVerifier(new TrustAllCertsUtils.TrustAllHostnameVerifier()).build();
        this.mRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.getAppDomain()).client(this.client).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson())).build();
    }

    public void setNewRetrofit(int timeOutSeconds2) {
        long j = timeOutSeconds2;
        this.client = new OkHttpClient.Builder().addInterceptor(this.loggingInterceptor).connectTimeout(j, TimeUnit.SECONDS).readTimeout(j, TimeUnit.SECONDS).sslSocketFactory(TrustAllCertsUtils.createSSLSocketFactory(), TrustAllCertsUtils.getInstance()).hostnameVerifier(new TrustAllCertsUtils.TrustAllHostnameVerifier()).build();
        this.mRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.getAppDomain()).client(this.client).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson())).build();
    }

    public int getClientTime() {
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient != null) {
            return okHttpClient.connectTimeoutMillis();
        }
        return 0;
    }

    private RequestBody getRequestBody(String method, Object object) {
        return RequestBody.create(GsonUtils.getGson().toJson(new RequestObject(method, Injection.repo().user().getAppId(), Injection.repo().user().getSecretKey(), Injection.repo().user().getSession(), object instanceof String ? (String) object : GsonUtils.getGson().toJson(object))), MediaType.parse("application/json"));
    }

    private RequestBody getDefaultRequestBody(String method, Object object) {
        String str;
        if (object == null) {
            str = "";
        } else {
            str = GsonUtils.getGson().toJson(object);
        }
        return RequestBody.create(GsonUtils.getGson().toJson(new RequestObject(method, UserApi.APP_ID_DEFAULT, UserApi.SECRET_KEY_DEFAULT, UserApi.SESSION_DEFAULT, str)), MediaType.parse("application/json"));
    }

    private <T> ObservableTransformer<ResultBean<T>, T> filterStatus() {
        return new ObservableTransformer() { // from class: com.ltech.smarthome.net.SmartHomeRetrofit$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                ObservableSource map;
                map = observable.map(new Function() { // from class: com.ltech.smarthome.net.SmartHomeRetrofit$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return SmartHomeRetrofit.lambda$filterStatus$0((ResultBean) obj);
                    }
                });
                return map;
            }
        };
    }

    static /* synthetic */ Object lambda$filterStatus$0(ResultBean resultBean) throws Exception {
        if (resultBean.getRet() == 10) {
            Injection.logout();
            throw new ApiException(resultBean);
        }
        if (resultBean.getRet() == 0) {
            return resultBean.getData() == null ? resultBean.getMessage() : resultBean.getData();
        }
        throw new ApiException(resultBean);
    }

    public Observable<Object> register(String account, String code, String pwd, String type, String sysnode) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getDefaultRequestBody(UserApi.FUN_URL_REGISTER, new RegisterRequest(UserApi.APP_ID_DEFAULT, type, account, code, pwd, sysnode))).compose(filterStatus());
    }

    public Observable<LoginResponse> login(String loginName, String pwd) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).login(getDefaultRequestBody(UserApi.FUN_URL_LOGIN, new LoginRequest(UserApi.APP_ID_DEFAULT, loginName, pwd, "3", Injection.push().getPushId()))).compose(filterStatus());
    }

    public Observable<LoginResponse> login() {
        return ((UserApi) obtainRetrofitService(UserApi.class)).login(getDefaultRequestBody(UserApi.FUN_URL_LOGIN, new LoginRequest(UserApi.APP_ID_DEFAULT, Injection.push().getPushId(), "3", Injection.push().getPushId()))).compose(filterStatus());
    }

    public Observable<LoginResponse> oauthLogin(String openid, String source, String accessToken, String name) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).oauthLogin(getDefaultRequestBody(UserApi.FUN_URL_OAUTH_LOGIN, new OauthLoginRequest(openid, source, accessToken, "3", name))).compose(filterStatus());
    }

    public Observable<LoginResponse> oauthBind(String openid, String code, String source, String account, boolean isPhone, String password) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).oauthBind(getDefaultRequestBody(UserApi.FUN_URL_OAUTH_BIND, new OauthBindRequest(openid, Long.toString(UserApi.APP_ID_DEFAULT), code, "3", isPhone ? "1" : "2", source, account, password))).compose(filterStatus());
    }

    public Observable<Object> setPassword(String account, String pwd, String code, String type) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getDefaultRequestBody(UserApi.FUN_URL_PASSWORD_SET, new SetPasswordRequest(Long.toString(UserApi.APP_ID_DEFAULT), account, pwd, code, type))).compose(filterStatus());
    }

    public Observable<Object> sendEmailCode(String email, int actionType) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getDefaultRequestBody(UserApi.FUN_URL_SEND_EMAIL_CODE, new EmailCodeRequest(email, actionType))).compose(filterStatus());
    }

    public Observable<Object> sendSmsCode(String phone, int actionType) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getDefaultRequestBody(UserApi.FUN_URL_SEND_SMS_CODE, new SmsCodeRequest(phone, actionType))).compose(filterStatus());
    }

    public Observable<BindUserResponse> bindUser() {
        return ((UserApi) obtainRetrofitService(UserApi.class)).bindUser(getRequestBody(UserApi.FUN_URL_BIND_USER, new Object())).compose(filterStatus());
    }

    public Observable<GetUserInfoResponse> getUserInfo() {
        return ((UserApi) obtainRetrofitService(UserApi.class)).getUserInfo(getRequestBody(UserApi.FUN_URL_USER_INFO_GET, new GetUserInfoRequest(Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<UpdateUserInfoResponse> updateUserName(String name) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).updateUserInfo(getRequestBody(UserApi.FUN_URL_USER_INFO_UPDATE, new UpdateUserInfoRequest(Injection.repo().user().getUserId()).setUsername(name))).compose(filterStatus());
    }

    public Observable<UpdateUserInfoResponse> updateUserHead(String url) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).updateUserInfo(getRequestBody(UserApi.FUN_URL_USER_INFO_UPDATE, new UpdateUserInfoRequest(Injection.repo().user().getUserId()).setHeadUrl(url))).compose(filterStatus());
    }

    public Observable<ChangPhoneResponse> changePhone(String phone, String code) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).changePhone(getRequestBody(UserApi.FUN_URL_CHANGE_PHONE, new ChangePhoneRequest(Injection.repo().user().getUserId(), phone, code))).compose(filterStatus());
    }

    public Observable<ChangeEmailResponse> changeEmail(String email, String code) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).changeEmail(getRequestBody(UserApi.FUN_URL_CHANGE_EMAIL, new ChangeEmailRequest(Injection.repo().user().getUserId(), email, code))).compose(filterStatus());
    }

    public Observable<Object> changePwd(String oldPwd, String newPwd) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getRequestBody(UserApi.FUN_URL_CHANGE_PWD, new ChangePwdRequest(oldPwd, newPwd))).compose(filterStatus());
    }

    public Observable<Object> removeUser() {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getRequestBody(UserApi.FUN_URL_USER_REMOVE, new RemoveUserRequest(Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<UploadImageFileResponse> uploadHeaderFile(File file) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).uploadHeadFile(UserApi.FUN_URL_UPLOAD_USER_HEAD_STAGE, MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(file, MediaType.parse(HttpHeaders.Values.MULTIPART_FORM_DATA)))).compose(filterStatus());
    }

    public Observable<Object> setSpeakerPlace(long placeId, String platform) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getRequestBody(UserApi.SPEAKER_SET_PLACE, new SetSpeakerPlaceRequest(placeId, platform))).compose(filterStatus());
    }

    public Observable<List<QuerySpeakerResponse>> querySpeakerPlace(String platform) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).querySpeakerPlace(getRequestBody(UserApi.SPEAKER_QUERY_PLACE, new QuerySpeakerPlaceRequest(platform, Injection.repo().home().getSelPlace().getPlaceId()))).compose(filterStatus());
    }

    public Observable<ResultBean<String>> getSonosUrl(long placeId) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.placeid = placeId;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).getSonosUrl(getRequestBody(SonosApi.GET_SONOS_URL, sonosRequest));
    }

    public Observable<SonosResponse> getSonosToken(String code) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.code = code;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).getSonosToken(getRequestBody(SonosApi.GET_SONOS_GET_TOKEN, sonosRequest)).compose(filterStatus());
    }

    public Observable<SonosResponse> getSonosHouseholds() {
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).getSonosHouseholds(getRequestBody(SonosApi.GET_SONOS_GET_HOUSEHOLDS, new Object())).compose(filterStatus());
    }

    public Observable<SonosResponse> getSonosGroups(String householdId) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.householdId = householdId;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).getSonosGroups(getRequestBody(SonosApi.GET_SONOS_GET_GROUPS, sonosRequest)).compose(filterStatus());
    }

    public Observable<List<ListDeviceResponse.RowsBean>> getSonosBindDevice(long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).getBindDevices(getRequestBody(SonosApi.GET_SONOS_BIND_DEVICE, sonosRequest)).compose(filterStatus());
    }

    public Observable<SonosResponse.PlayStatus> getSonosStatus(long deviceid, long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).getSonosPlayStatus(getRequestBody(SonosApi.GET_SONOS_GET_STATUS, sonosRequest)).compose(filterStatus());
    }

    public Observable<Objects> sonosPlayPause(long deviceid, long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosPlayPause(getRequestBody(SonosApi.GET_SONOS_PLAY_PAUSE, sonosRequest)).compose(filterStatus());
    }

    public Observable<Objects> sonosNext(long deviceid, long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosNext(getRequestBody(SonosApi.GET_SONOS_SET_NEXT, sonosRequest)).compose(filterStatus());
    }

    public Observable<Objects> sonosPre(long deviceid, long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosPre(getRequestBody(SonosApi.GET_SONOS_SET_PRE, sonosRequest)).compose(filterStatus());
    }

    public Observable<Objects> sonosMode(long deviceid, long placeid, boolean repeat, boolean repeatOne, boolean shuffle, boolean crossfade) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        sonosRequest.repeat = repeat;
        sonosRequest.repeatOne = repeatOne;
        sonosRequest.shuffle = shuffle;
        sonosRequest.crossfade = crossfade;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosMode(getRequestBody(SonosApi.GET_SONOS_SET_MODES, sonosRequest)).compose(filterStatus());
    }

    public Observable<String> sonosUnbind(long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosUnbind(getRequestBody(SonosApi.GET_SONOS_UNBIND_DEVICE, sonosRequest)).compose(filterStatus());
    }

    public Observable<SonosResponse.Volume> sonosGetVolume(long deviceid, long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosGetVolume(getRequestBody(SonosApi.GET_SONOS_GET_VOLUME, sonosRequest)).compose(filterStatus());
    }

    public Observable<Objects> sonosSetVolume(long deviceid, long placeid, int progress) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.volume = progress;
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosMode(getRequestBody(SonosApi.GET_SONOS_SET_VOLUME, sonosRequest)).compose(filterStatus());
    }

    public Observable<SonosResponse.Metadata> sonosGetMetadataStatus(long deviceid, long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosMetadataGetStatus(getRequestBody(SonosApi.GET_SONOS_METADATA_GET_STATUS, sonosRequest)).compose(filterStatus());
    }

    public Observable<Objects> sonosSetMute(long deviceid, long placeid, boolean b2) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.muted = b2;
        sonosRequest.deviceid = deviceid;
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosMode(getRequestBody(SonosApi.GET_SONOS_SET_MUTE, sonosRequest)).compose(filterStatus());
    }

    public Observable<List<SonosResponse.Favorites>> sonosFavorites(long placeid) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.placeid = placeid;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosFavorites(getRequestBody(SonosApi.GET_SONOS_FAVORITES, sonosRequest)).compose(filterStatus());
    }

    public Observable<List<SonosResponse.Favorites>> sonosPlayFavorites(long placeid, long deviceid, long favoriteId) {
        SonosRequest sonosRequest = new SonosRequest();
        sonosRequest.placeid = placeid;
        sonosRequest.deviceid = deviceid;
        sonosRequest.favoriteId = favoriteId;
        return ((SonosApi) obtainRetrofitService(SonosApi.class)).sonosFavorites(getRequestBody(SonosApi.GET_SONOS_PLAY_FAVORITES, sonosRequest)).compose(filterStatus());
    }

    public Observable<AddPlaceResponse> addPlace(String placeName, List<RoomName> rooms, String location, double latitude, double longitude, String netKey, String appKey, String meshuuid) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).addPlace(getRequestBody(PlaceApi.FUN_URL_PLACE_ADD, new AddPlaceRequest(placeName, location, latitude, longitude, rooms, netKey, appKey, meshuuid))).compose(filterStatus());
    }

    public Observable<ListPlaceResponse> listPlace() {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).listPlace(getRequestBody(PlaceApi.FUN_URL_PLACE_LIST, new ListPlaceRequest(Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<DetailPlaceResponse> detailPlace(long placeId) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).detailPlace(getRequestBody(PlaceApi.FUN_URL_PLACE_DETAIL, new DetailPlaceRequest(placeId, Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<Object> updatePlaceName(long placeId, String placeName) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_UPDATE, new UpdatePlaceRequest(placeId, Injection.repo().user().getUserId()).setPlacename(placeName))).compose(filterStatus());
    }

    public Observable<Object> updatePlaceIvIndex(long placeId, int ivindex) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_UPDATE, new UpdatePlaceRequest(placeId, Injection.repo().user().getUserId()).setIvIndex(ivindex))).compose(filterStatus());
    }

    public Observable<Object> updatePlaceLocation(long placeId, String location, double latitude, double longitude) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_UPDATE, new UpdatePlaceRequest(placeId, Injection.repo().user().getUserId()).setLocation(location).setLatitude(latitude).setLongitude(longitude))).compose(filterStatus());
    }

    public Observable<Object> updateAppToken(long placeId, String appToken, int expiresIn, int createdAt) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_UPDATE, new UpdatePlaceRequest(placeId, Injection.repo().user().getUserId()).setAppToken(appToken).setExpiresIn(expiresIn).setCreatedAt(createdAt))).compose(filterStatus());
    }

    public Observable<Object> deletePlace(long placeId) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_DELETE, new DeletePlaceRequest(placeId, Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<Object> getAppToken(long placeId) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).getAppToken(getRequestBody(SuperPanelApi.FUN_URL_PLACE_APPTOKEN, new AppTokenRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> oauth(long placeId, String token) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).oauth(getRequestBody(SuperPanelApi.FUN_URL_PLACE_OAUTH, new OauthRequest(placeId, token))).compose(filterStatus());
    }

    public Observable<QueryPlaceInfoResponse> queryPlaceDetailInfo(long placeId) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).queryPlaceDetailInfo(getRequestBody(PlaceApi.FUN_URL_PLACE_INFO, new ListPlaceRequest(Injection.repo().user().getUserId(), placeId))).compose(filterStatus());
    }

    public Observable<QueryPlaceInfoResponse> queryPlaceDetailInfo(long placeId, long userId) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).queryPlaceDetailInfo(getRequestBody(PlaceApi.FUN_URL_PLACE_INFO, new ListPlaceRequest(userId, placeId))).compose(filterStatus());
    }

    public Observable<AddSceneResponse> addScene(int sceneIndex, long placeId, String sceneName, List<Scene.SceneContent> content, int imgIndex, long floorId, long roomId) {
        return addScene(sceneIndex, placeId, sceneName, content, imgIndex, floorId, roomId, false);
    }

    public Observable<AddSceneResponse> addScene(int sceneIndex, long placeId, String sceneName, List<Scene.SceneContent> content, int imgIndex, long floorId, long roomId, boolean isLocal) {
        ArrayList arrayList = new ArrayList(content.size());
        for (int i = 0; i < content.size(); i++) {
            Scene.SceneContent sceneContent = content.get(i);
            AddSceneRequest.ContentInfo contentInfo = new AddSceneRequest.ContentInfo();
            contentInfo.actiontype = sceneContent.getActionType();
            contentInfo.action = sceneContent.getAction();
            contentInfo.timespace = sceneContent.getTimeSpace();
            contentInfo.executecommand = sceneContent.getExecuteCommand();
            contentInfo.actiontypename = sceneContent.getActionTypeName();
            arrayList.add(contentInfo);
        }
        LHomeLog.i(getClass(), "场景添加GSON数据为 ：" + GsonUtils.getGson().toJson(new AddSceneRequest(sceneIndex, placeId, sceneName, arrayList, imgIndex, floorId, roomId)));
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).addScene(getRequestBody(SceneApi.FUN_URL_SCENE_ADD, new AddSceneRequest(sceneIndex, placeId, sceneName, arrayList, imgIndex, floorId, roomId, isLocal ? 2 : 1))).compose(filterStatus());
    }

    public Observable<Object> updateScene(long sceneId, String sceneName, List<Scene.SceneContent> content, int imgIndex) {
        ArrayList arrayList = new ArrayList(content.size());
        for (int i = 0; i < content.size(); i++) {
            Scene.SceneContent sceneContent = content.get(i);
            UpdateSceneRequest.ContentInfo contentInfo = new UpdateSceneRequest.ContentInfo();
            contentInfo.actiontype = sceneContent.getActionType();
            contentInfo.action = sceneContent.getAction();
            contentInfo.timespace = sceneContent.getTimeSpace();
            contentInfo.executecommand = sceneContent.getExecuteCommand();
            contentInfo.actiontypename = sceneContent.getActionTypeName();
            arrayList.add(contentInfo);
        }
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, sceneName, arrayList, imgIndex).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateSceneName(long sceneId, String sceneName, int index) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, sceneName, index).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateSceneicon(long sceneId, int iconPos) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, iconPos).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateSceneRoom(long sceneId, long floorId, long roomId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, floorId, roomId).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateSceneExtParam(long sceneId, String extParam) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, extParam).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateSceneContent(long sceneId, List<Scene.SceneContent> content) {
        ArrayList arrayList = new ArrayList(content.size());
        for (int i = 0; i < content.size(); i++) {
            Scene.SceneContent sceneContent = content.get(i);
            UpdateSceneRequest.ContentInfo contentInfo = new UpdateSceneRequest.ContentInfo();
            contentInfo.actiontype = sceneContent.getActionType();
            contentInfo.action = sceneContent.getAction();
            contentInfo.timespace = sceneContent.getTimeSpace();
            contentInfo.executecommand = sceneContent.getExecuteCommand();
            contentInfo.actiontypename = sceneContent.getActionTypeName();
            arrayList.add(contentInfo);
        }
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, arrayList).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateLocalScene(long sceneId, String sceneName, List<Scene.SceneContent> content, int imgIndex, String executecommand) {
        ArrayList arrayList = new ArrayList(content.size());
        for (int i = 0; i < content.size(); i++) {
            Scene.SceneContent sceneContent = content.get(i);
            UpdateSceneRequest.ContentInfo contentInfo = new UpdateSceneRequest.ContentInfo();
            contentInfo.actiontype = sceneContent.getActionType();
            contentInfo.action = sceneContent.getAction();
            contentInfo.timespace = sceneContent.getTimeSpace();
            contentInfo.executecommand = sceneContent.getExecuteCommand();
            contentInfo.actiontypename = sceneContent.getActionTypeName();
            arrayList.add(contentInfo);
        }
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, sceneName, arrayList, imgIndex, executecommand).getRequestString())).compose(filterStatus());
    }

    public Observable<ListSceneResponse> listAllSceneByUser(long placeId, long userId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).listScene(getRequestBody(SceneApi.FUN_URL_SCENE_LIST, new ListSceneRequest(placeId, 3, userId))).compose(filterStatus());
    }

    public Observable<ListSceneResponse> listScene(long placeId, int sceneType) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).listScene(getRequestBody(SceneApi.FUN_URL_SCENE_LIST, new ListSceneRequest(placeId, sceneType, Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<ListSceneResponse> listSceneAll(long placeId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).listScene(getRequestBody(SceneApi.FUN_URL_SCENE_LIST, new ListSceneRequest(placeId, 3, Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<ListSceneResponse> listSceneCommon(long placeId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).listScene(getRequestBody(SceneApi.FUN_URL_SCENE_COMMON_LIST, new ListSceneRequest(placeId, 3, Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<DetailSceneResponse> detailScene(long sceneId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).detailScene(getRequestBody(SceneApi.FUN_URL_SCENE_DETAIL, new DetailSceneRequest(sceneId))).compose(filterStatus());
    }

    public Observable<Object> deleteScene(long sceneId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_DELETE, new DeleteSceneRequest(Long.valueOf(sceneId)))).compose(filterStatus());
    }

    public Observable<Object> sortScene(List<SortSceneRequest.SceneSortBean> list) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE_INDEX, new SortSceneRequest(list))).compose(filterStatus());
    }

    public Observable<Object> executeScene(long sceneId) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_ACTION, new ExecuteSceneRequest(sceneId))).compose(filterStatus());
    }

    public Observable<Object> setCommonScene(long sceneId, boolean common) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_COMMON_SET, new CommonSceneRequest(sceneId, common))).compose(filterStatus());
    }

    public Observable<Object> setDynamicScene(long sceneId, boolean dynamic) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).requst(getRequestBody(SceneApi.FUN_URL_SCENE_UPDATE, new UpdateSceneRequest(sceneId, dynamic).getRequestString())).compose(filterStatus());
    }

    public Observable<AddFloorResponse> addFloor(long placeId, String floorName, List<String> rooms) {
        return ((FloorApi) obtainRetrofitService(FloorApi.class)).addFloor(getRequestBody(FloorApi.FUN_URL_FLOOR_ADD, new AddFloorRequest(placeId, floorName, rooms))).compose(filterStatus());
    }

    public Observable<ListFloorResponse> listFloor(long placeId) {
        return ((FloorApi) obtainRetrofitService(FloorApi.class)).listFloor(getRequestBody(FloorApi.FUN_URL_FLOOR_LIST, new ListFloorRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> deleteFloor(long floorId) {
        return ((FloorApi) obtainRetrofitService(FloorApi.class)).requst(getRequestBody(FloorApi.FUN_URL_FLOOR_DELETE, new DeleteFloorRequest(floorId))).compose(filterStatus());
    }

    public Observable<Object> updateFloor(long floorId, String floorName) {
        return ((FloorApi) obtainRetrofitService(FloorApi.class)).requst(getRequestBody(FloorApi.FUN_URL_FLOOR_UPDATE, new UpdateFloorRequest(floorId, floorName))).compose(filterStatus());
    }

    public Observable<Object> sortFloor(List<SortFloorRequest.FloorSortBean> list) {
        return ((FloorApi) obtainRetrofitService(FloorApi.class)).requst(getRequestBody(FloorApi.FUN_URL_FLOOR_UPDATE_INDEX, new SortFloorRequest(list))).compose(filterStatus());
    }

    public Observable<AddRoomResponse> addRoom(long floorId, String roomName) {
        return ((RoomApi) obtainRetrofitService(RoomApi.class)).addRoom(getRequestBody(RoomApi.FUN_URL_ROOM_ADD, new AddRoomRequest(floorId, roomName))).compose(filterStatus());
    }

    public Observable<ListRoomResponse> listRoom(long floorId) {
        return ((RoomApi) obtainRetrofitService(RoomApi.class)).listRoom(getRequestBody(RoomApi.FUN_URL_ROOM_LIST, new ListRoomRequest(floorId))).compose(filterStatus());
    }

    public Observable<Object> updateRoom(long roomId, String roomName) {
        return ((RoomApi) obtainRetrofitService(RoomApi.class)).requst(getRequestBody(RoomApi.FUN_URL_ROOM_UPDATE, new UpdateRoomRequest(roomId, roomName))).compose(filterStatus());
    }

    public Observable<Object> deleteRoom(long roomId) {
        return ((RoomApi) obtainRetrofitService(RoomApi.class)).requst(getRequestBody(RoomApi.FUN_URL_ROOM_DELETE, new DeleteRoomRequest(roomId))).compose(filterStatus());
    }

    public Observable<ListUserRoomResponse> listUserRoom(long placeId) {
        return ((RoomApi) obtainRetrofitService(RoomApi.class)).listUserRoom(getRequestBody(RoomApi.FUN_URL_ROOM_USER_LIST, new ListUserRoomRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> sortRoom(List<SortRoomRequest.RoomSortBean> list) {
        return ((RoomApi) obtainRetrofitService(RoomApi.class)).requst(getRequestBody(RoomApi.FUN_URL_ROOM_UPDATE_INDEX, new SortRoomRequest(list))).compose(filterStatus());
    }

    public Observable<AddDeviceResponse> addDevice(String deviceBean) {
        LHomeLog.i(getClass(), "saveDevice  deviceBean= " + deviceBean);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).addDevice(getRequestBody(DeviceApi.FUN_URL_DEVICE_ADD, deviceBean)).compose(filterStatus());
    }

    public Observable<ListDeviceResponse> listDevice(long placeId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).listDevice(getRequestBody(DeviceApi.FUN_URL_DEVICE_LIST, new ListDeviceRequest(placeId))).compose(filterStatus());
    }

    public Observable<ListDeviceResponse> listDevice(long placeId, long userId) {
        return ((RoleApi) obtainRetrofitService(RoleApi.class)).listDevice(getRequestBody(RoleApi.FUN_URL_DEVICE_ROLE_LIST, new ListDeviceRequest(placeId, userId))).compose(filterStatus());
    }

    public Observable<Object> deleteDevice(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_DELETE, new DeleteDeviceRequest(deviceId))).compose(filterStatus());
    }

    public Observable<Object> deleteDevice(long deviceId, long groupId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_DELETE, new DeleteDeviceHasGroupIdRequest(deviceId, groupId))).compose(filterStatus());
    }

    public Observable<Object> updateDevicePlace(long deviceId, long roomId, boolean updatesub) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setRoomId(roomId);
        updateDeviceRequest.setUpdateSub(updatesub);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_AREA_UPDATE, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceName(long deviceId, String name) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setDeviceName(name);
        LHomeLog.i(getClass(), "updateDeviceName");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceNameAndImgIndex(long deviceId, String name, int index) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setDeviceName(name);
        updateDeviceRequest.setImageIndex(index);
        LHomeLog.i(getClass(), "updateDeviceNameAndImgIndex");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateSubDeviceHide(long deviceId, boolean b2) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setSubHide(b2);
        LHomeLog.i(getClass(), "updateSubDeviceHide");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updatePanelColor(long deviceId, int color) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setPanelColor(color + "");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateCtRange(long deviceId, int max, int min) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setCtRange(max, min);
        LHomeLog.i(getClass(), "updateCtRange");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceMac(long deviceId, String mac) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setMac(mac);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceVersion(long deviceId, String sVersion, String hVersion) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setMcuVersion(sVersion);
        updateDeviceRequest.setFirmwareVersion(hVersion);
        LHomeLog.i(getClass(), "updateDeviceVersion");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceVersionAndParamExt(long deviceId, String sVersion, String hVersion, String extParam) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setMcuVersion(sVersion);
        updateDeviceRequest.setFirmwareVersion(hVersion);
        updateDeviceRequest.setParamExt(extParam);
        LHomeLog.i(getClass(), "updateDeviceVersion");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceBleVersion(long deviceId, String version) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setBleVersion(version);
        LHomeLog.i(getClass(), "updateDeviceBleVersion");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceImgIndex(long deviceId, int imgIndex) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setImageIndex(imgIndex);
        LHomeLog.i(getClass(), "updateDeviceImgIndex");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceVirtual(long deviceId, int virtual, int writable) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setVirtual(virtual);
        if (writable > 0) {
            updateDeviceRequest.setWritable(writable);
        }
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> queryDeviceGroup(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_QUERY_GROUP, new QueryByDeviceIdRequest(deviceId))).compose(filterStatus());
    }

    public Observable<Object> panelSortRole(long deviceId, List<PanelSortInfoRequest.DeviceSortBean> devices, List<PanelSortInfoRequest.GroupSortBean> groups) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).panelSortRole(getRequestBody(DeviceApi.FUN_URL_PANEL_SORT_ROLE, new PanelSortInfoRequest(deviceId, devices, groups))).compose(filterStatus());
    }

    public Observable<Object> panelSortRole(long deviceId, List<PanelSortInfoRequest.DeviceSortBean> devices, List<PanelSortInfoRequest.GroupSortBean> groups, List<PanelSortInfoRequest.SceneSortBean> scenes) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).panelSortRole(getRequestBody(DeviceApi.FUN_URL_PANEL_SORT_ROLE, new PanelSortInfoRequest(deviceId, devices, groups, scenes))).compose(filterStatus());
    }

    public Observable<Object> panelSortScene(long deviceId, List<PanelSortInfoRequest.SceneSortBean> sceneSortBeans) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).panelSortRole(getRequestBody(DeviceApi.FUN_URL_PANEL_SORT_ROLE, new PanelSortInfoRequest(deviceId, sceneSortBeans))).compose(filterStatus());
    }

    public Observable<ListSceneResponse> queryDeviceScene(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).listScene(getRequestBody(DeviceApi.FUN_URL_DEVICE_QUERY_SCENE, new QueryByDeviceIdRequest(deviceId))).compose(filterStatus());
    }

    public Observable<ListAutomationResponse> queryDeviceAutomation(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).listAutomation(getRequestBody(DeviceApi.FUN_URL_DEVICE_QUERY_AUTOMATION, new QueryByDeviceIdRequest(deviceId))).compose(filterStatus());
    }

    public Observable<GetReplaceDataResponse> queryReplaceData(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).getReplaceData(getRequestBody(DeviceApi.FUN_URL_DEVICE_GET_REPLACE_DATA, new QueryByDeviceIdRequest(deviceId))).compose(filterStatus());
    }

    public Observable<Object> backupDeviceData(List<UpdateBackDataRequest.RowsBean> list) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_DATA_BACK, new UpdateBackDataRequest(list))).compose(filterStatus());
    }

    public Observable<Object> clearBackupData(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_DATA_BACK_CLEAR, new QueryByDeviceIdRequest(deviceId))).compose(filterStatus());
    }

    public Observable<Object> updateGroupDeviceIcon(long groupId, int iconindex) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, new UpdateGroupIconRequest(groupId, iconindex))).compose(filterStatus());
    }

    public Observable<Object> updateParams(long deviceId, String param, String extParam) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setParam(param);
        updateDeviceRequest.setParamExt(extParam);
        LHomeLog.i(getClass(), "updateParams");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateParams(long j, String str, String str2, boolean z) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(j);
        updateDeviceRequest.setParam(str);
        updateDeviceRequest.setParamExt(str2);
        updateDeviceRequest.setSubOutGroup(z ? 1 : 0);
        LHomeLog.i(getClass(), "updateParams");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateParam(long deviceId, String param) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setParam(param);
        LHomeLog.i(getClass(), "updateParam");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateParamAndMac(long deviceId, String param, String mac, int writable) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setParam(param);
        updateDeviceRequest.setMac(mac);
        updateDeviceRequest.setWritable(writable);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateDeviceParamByReplace(long deviceId, String mac, String param, String mcuversion, String bleversion) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setMac(mac);
        updateDeviceRequest.setParam(param);
        updateDeviceRequest.setMcuVersion(mcuversion);
        updateDeviceRequest.setBleVersion(bleversion);
        updateDeviceRequest.setReplace(true);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateParamExt(long deviceId, String extParam) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setParamExt(extParam);
        LHomeLog.i(getClass(), "updateParamExt");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateNameAndParamExt(long deviceId, String name, String extParam) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setDeviceName(name);
        updateDeviceRequest.setParamExt(extParam);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateParamExtAndCodeLibrary(long deviceId, String extParam, String codeLibrary, double latitude, double longitude) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setParamExt(extParam);
        updateDeviceRequest.setCodeLibrary(codeLibrary);
        updateDeviceRequest.setLatitude(latitude);
        updateDeviceRequest.setLongitude(longitude);
        LHomeLog.i(getClass(), "updateParamExtAndCodeLibrary");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateLocation(long deviceId, double latitude, double longitude) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setLatitude(latitude);
        updateDeviceRequest.setLongitude(longitude);
        LHomeLog.i(getClass(), "updateLocation");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updatePlatformId(long deviceId, String platformId) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setPlatformDeviceId(platformId);
        LHomeLog.i(getClass(), "updatePlatformId");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateCodeLibrary(long deviceId, String codeLibrary) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setCodeLibrary(codeLibrary);
        LHomeLog.i(getClass(), "updateCodeLibrary");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updatePresetKValues(long deviceId, Map<String, KValue> presetKValues) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setPresetKValues(presetKValues);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INFO, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateReportInstruct(long deviceId, String instruct) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setReportInstruct(instruct);
        updateDeviceRequest.setPanelUpdate(false);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_DEVICE_REPORT_INSTRUCT, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> superPanelReplace(long oldDeviceId, long newDeviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(DeviceApi.FUN_URL_SUPER_PANEL_REPLACE, new SuperPanelReplaceRequest(oldDeviceId, newDeviceId))).compose(filterStatus());
    }

    public Observable<Object> requestDeviceControl(String[] platFormDeviceIds) {
        ArrayList arrayList = new ArrayList();
        for (String str : platFormDeviceIds) {
            arrayList.add(new DeviceControlRequest.PlatformDeviceId(str));
        }
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_CONTROL, new DeviceControlRequest().setPlatformdeviceids(arrayList))).compose(filterStatus());
    }

    public Observable<Object> requestDeviceControl(long[] deviceIds) {
        ArrayList arrayList = new ArrayList();
        for (long j : deviceIds) {
            arrayList.add(new DeviceControlRequest.DeviceId(j));
        }
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_CONTROL, new DeviceControlRequest().setDeviceIds(arrayList))).compose(filterStatus());
    }

    public Observable<GetDeviceOnlineResponse> getDeviceOnlineStatus(String productId, String platformDeviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).getDeviceOnlineStatus(getRequestBody(DeviceApi.FUN_URL_DEVICE_GET_ONLINE_STATUS, new GetDeviceOnlineRequest(productId, platformDeviceId))).compose(filterStatus());
    }

    public Observable<Object> getDeviceSyncStatus(long placeId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).getDeviceSyncStatus(getRequestBody(DeviceApi.FUN_URL_DEVICE_SYNC_STATUS, new ListUserRoomRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> subscribeDevice() {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_SUBSCRIBE, new Object())).compose(filterStatus());
    }

    public Observable<Object> unsubscribeDevice() {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_UNSUBSCRIBE, new Object())).compose(filterStatus());
    }

    public Observable<Object> sortDevice(List<SortDeviceRequest.DeviceSortBean> list) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_INDEX, new SortDeviceRequest(list))).compose(filterStatus());
    }

    public Observable<Object> sortDeviceAndGroup(SortDeviceAndGroupRequest request) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).requst(getRequestBody(DeviceApi.FUN_URL_UPDATE_ALL_INDEX, request)).compose(filterStatus());
    }

    private Observable<QuerySceneActionResponse> getDeviceSceneAction(long deviceId, int type) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).deviceSceneAction(getRequestBody(DeviceApi.FUN_URL_DEVICE_SCENE_ACTIONS, new DeviceActionRequest(deviceId, type))).compose(filterStatus());
    }

    public Observable<QuerySceneActionResponse> getDeviceLocalSceneAction(long deviceId) {
        return getDeviceSceneAction(deviceId, 2);
    }

    public Observable<ListPlaceUserResponse> listPlaceUser(long placeId) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).listPlaceUser(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_LIST, new ListPlaceUserRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> addPlaceUser(AddPlaceUserRequest request) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_ADD, request)).compose(filterStatus());
    }

    public Observable<Object> deletePlaceUser(long placeId, long placeUserId) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_DELETE, new DeletePlaceUserRequest(placeId, placeUserId))).compose(filterStatus());
    }

    public Observable<Object> updatePlaceUser(long placeUserId, int roleType, String remark) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_UPDATE, new UpdatePlaceUserRequest(placeUserId, roleType, remark))).compose(filterStatus());
    }

    public Observable<Object> applyJoinPlace(PlaceUserApplyRequest request) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_APPLY, request)).compose(filterStatus());
    }

    public Observable<Object> agreeJoinPlace(AgreePlaceUserRequest request) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_MANGER_AGREE, request)).compose(filterStatus());
    }

    public Observable<Object> refuseJoinPlace(RefusePlaceUserRequest request) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_MANGER_REFUSE, request)).compose(filterStatus());
    }

    public Observable<Object> invitePlaceUser(InvitationPlaceUserRequest request) {
        Log.i("jiang", "invitePlaceUser");
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_INVITATION, request)).compose(filterStatus());
    }

    public Observable<Object> agreeInvitation(AgreeInvitationRequest request) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_AGREE_INVITATION, request)).compose(filterStatus());
    }

    public Observable<Object> refuseInvitation(RefuseInvitationRequest request) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_REFUSE_INVITATION, request)).compose(filterStatus());
    }

    public Observable<Object> quitPlace(long placeId) {
        return ((PlaceUserApi) obtainRetrofitService(PlaceUserApi.class)).requst(getRequestBody(PlaceUserApi.FUN_URL_PLACE_USER_OUT, new QuitPlaceRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> transferPlace(long placeId, long userId) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_CHANGE, new TransferPlaceRequest(placeId, userId))).compose(filterStatus());
    }

    public Observable<List<ListNoHandleMsgResponse.RowData>> listNoHandleMessage() {
        return ((PushApi) obtainRetrofitService(PushApi.class)).listNoHandleMessage(getRequestBody(PushApi.FUN_URL_PUSH_NO_HANDLE_LIST, new PushMsgNoHandleListRequest())).compose(filterStatus());
    }

    public Observable<AppNoticeListResponse> getAppNoticeList(int pagenum) {
        return ((PushApi) obtainRetrofitService(PushApi.class)).appNoticeList(getRequestBody(PushApi.FUN_URL_APP_NOTICE_LIST, new PushMsgAppNoticeListRequest(pagenum))).compose(filterStatus());
    }

    public Observable<PlaceMsgListResponse> getPlaceMsgList(int pagenum) {
        return ((PushApi) obtainRetrofitService(PushApi.class)).placeMsgList(getRequestBody(PushApi.FUN_URL_MSG_PLACE_LIST, new PushMsgPlaceListRequest(pagenum))).compose(filterStatus());
    }

    public Observable<Object> deleteMessage(int type) {
        return ((PushApi) obtainRetrofitService(PushApi.class)).requst(getRequestBody(PushApi.FUN_URL_MESSAGE_DELETE, new PushMessageDeleteRequest(type))).compose(filterStatus());
    }

    public Observable<Object> addFeedback(String remark) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).requst(getRequestBody(UserApi.FUN_URL_FEEDBACK_ADD, new FeedbackAddRequest(remark))).compose(filterStatus());
    }

    public Observable<Object> updateRoomRole(UpdateRoomRoleRequest updateRoomRoleRequest) {
        return ((RoleApi) obtainRetrofitService(RoleApi.class)).requst(getRequestBody(RoleApi.FUN_URL_ROOM_ROLE_UPDATE, updateRoomRoleRequest)).compose(filterStatus());
    }

    public Observable<Object> updateDeviceRole(UpdateDeviceRoleRequest updateDeviceRoleRequest) {
        return ((RoleApi) obtainRetrofitService(RoleApi.class)).requst(getRequestBody(RoleApi.FUN_URL_DEVICE_ROLE_UPDATE, updateDeviceRoleRequest)).compose(filterStatus());
    }

    public Observable<Object> updateGroupRole(UpdateGroupRoleRequest updateGroupRoleRequest) {
        return ((RoleApi) obtainRetrofitService(RoleApi.class)).requst(getRequestBody(RoleApi.FUN_URL_GROUP_ROLE_UPDATE, updateGroupRoleRequest)).compose(filterStatus());
    }

    public Observable<Object> updateSceneRole(UpdateSceneRoleRequest updateSceneRoleRequest) {
        return ((RoleApi) obtainRetrofitService(RoleApi.class)).requst(getRequestBody(RoleApi.FUN_URL_SCENE_ROLE_UPDATE, updateSceneRoleRequest)).compose(filterStatus());
    }

    public Observable<ListAutomationResponse> listAutomation(long placeId) {
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).listAutomation(getRequestBody(AutomationApi.FUN_URL_QUERY_AUTOMATION, new ListAutomationRequest(placeId))).compose(filterStatus());
    }

    public Observable<AddAutomationResponse> addAutomation(Automation automation) {
        AddAutomationRequest addAutomationRequest = new AddAutomationRequest(automation.getPlaceId());
        addAutomationRequest.setAutomationname(automation.getName());
        addAutomationRequest.setStarttime(automation.getStartTime());
        addAutomationRequest.setEndtime(automation.getEndTime());
        addAutomationRequest.setWeeks(automation.getWeeks());
        addAutomationRequest.setTimezone(automation.getTimeZone());
        addAutomationRequest.setPicindex(automation.getPicIndex());
        addAutomationRequest.setEnable(Automation.getEnableValue(automation.isEnable()));
        addAutomationRequest.setConditions(automation.getConditions());
        addAutomationRequest.setActions(automation.getActions());
        addAutomationRequest.setConditiontype(automation.getConditionType());
        addAutomationRequest.setAutomationtype(automation.getAutomationType());
        addAutomationRequest.setIntervaltime(automation.getIntervaltime());
        addAutomationRequest.setCycleindex(automation.getCycleindex());
        if (automation.getAutomationType() == 2) {
            addAutomationRequest.setGatewaydeviceid(automation.getGatewayDeviceId());
        }
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).addAutomation(getRequestBody(AutomationApi.FUN_URL_ADD_AUTOMATION, addAutomationRequest)).compose(filterStatus());
    }

    public Observable<Object> editAutomation(Automation automation) {
        EditAutomationRequest editAutomationRequest = new EditAutomationRequest(automation.getAutomationId());
        editAutomationRequest.setAutomationname(automation.getName());
        editAutomationRequest.setStarttime(automation.getStartTime());
        editAutomationRequest.setEndtime(automation.getEndTime());
        editAutomationRequest.setWeeks(automation.getWeeks());
        editAutomationRequest.setTimezone(automation.getTimeZone());
        editAutomationRequest.setPicindex(automation.getPicIndex());
        editAutomationRequest.setConditions(automation.getConditions());
        editAutomationRequest.setActions(automation.getActions());
        editAutomationRequest.setConditionType(automation.getConditionType());
        editAutomationRequest.setAutomationtype(automation.getAutomationType());
        editAutomationRequest.setIntervaltime(automation.getIntervaltime());
        editAutomationRequest.setCycleindex(automation.getCycleindex());
        if (automation.getAutomationType() == 2) {
            editAutomationRequest.setGatewaydeviceid(automation.getGatewayDeviceId());
        }
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).requst(getRequestBody(AutomationApi.FUN_URL_EDIT_AUTOMATION, editAutomationRequest)).compose(filterStatus());
    }

    public Observable<Object> enableAutomation(long automationId, boolean enable) {
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).requst(getRequestBody(AutomationApi.FUN_URL_ENABLE_AUTOMATION, new EnableAutomationRequest(automationId, Automation.getEnableValue(enable)))).compose(filterStatus());
    }

    public Observable<Object> sortAutomation(List<Long> automationIds) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = automationIds.iterator();
        while (it.hasNext()) {
            arrayList.add(new SortAutomationRequest.AutomationId(it.next().longValue()));
        }
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).requst(getRequestBody(AutomationApi.FUN_URL_SORT_AUTOMATION, new SortAutomationRequest(arrayList))).compose(filterStatus());
    }

    public Observable<Object> removeAutomation(long automationId) {
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).requst(getRequestBody(AutomationApi.FUN_URL_DEL_AUTOMATION, new DelAutomationRequest(automationId))).compose(filterStatus());
    }

    public Observable<DetailAutomationResponse> detailAutomation(long automationId) {
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).detailAutomation(getRequestBody(AutomationApi.FUN_URL_DETAIL_AUTOMATION, new DetailAutomationRequest(automationId))).compose(filterStatus());
    }

    public Observable<Object> reportAutomation(int type, String params) {
        return ((AutomationApi) obtainRetrofitService(AutomationApi.class)).requst(getRequestBody(AutomationApi.FUN_URL_REPORT_AUTOMATION, new ReportAutomationRequest(type, params))).compose(filterStatus());
    }

    public Observable<AddGroupResponse> addGroup(long placeId, long floorId, long roomId, String groupName, String groupType, String colorType, List<Long> deviceIds) {
        ArrayList arrayList = new ArrayList(deviceIds.size());
        for (Long l : deviceIds) {
            AddGroupRequest.AddGroupContent addGroupContent = new AddGroupRequest.AddGroupContent();
            addGroupContent.deviceid = l.longValue();
            arrayList.add(addGroupContent);
        }
        if (colorType.equals(Integer.toString(8)) || colorType.equals(Integer.toString(9)) || colorType.equals(Integer.toString(10)) || colorType.equals(Integer.toString(18)) || colorType.equals(Integer.toString(11)) || colorType.equals(Integer.toString(19)) || colorType.equals(Integer.toString(21))) {
            return ((GroupApi) obtainRetrofitService(GroupApi.class)).addGroup(getRequestBody(GroupApi.FUN_URL_ADD_GROUP, new AddGroupRequest(placeId, floorId, roomId, groupName, groupType, colorType, arrayList, RelateInfoUtils.initSmartPanelRelateInfoList(Integer.parseInt(colorType)).getExtParamString()))).compose(filterStatus());
        }
        if (colorType.equals(Integer.toString(22)) || colorType.equals(Integer.toString(23)) || colorType.equals(Integer.toString(24)) || colorType.equals(Integer.toString(27)) || colorType.equals(Integer.toString(28)) || colorType.equals(Integer.toString(29)) || colorType.equals(Integer.toString(30))) {
            return ((GroupApi) obtainRetrofitService(GroupApi.class)).addGroup(getRequestBody(GroupApi.FUN_URL_ADD_GROUP, new AddGroupRequest(placeId, floorId, roomId, groupName, groupType, colorType, arrayList, EurHelper.paramExt, GsonUtils.toJson(EurHelper.eurGroupParam)))).compose(filterStatus());
        }
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).addGroup(getRequestBody(GroupApi.FUN_URL_ADD_GROUP, new AddGroupRequest(placeId, floorId, roomId, groupName, groupType, colorType, arrayList))).compose(filterStatus());
    }

    public Observable<Object> updateGroupLocation(long groupId, long floor, long roomId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroup(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP_LOCATION, new UpdateGroupRequest(groupId, floor, roomId))).compose(filterStatus());
    }

    public Observable<ListGroupResponse> listGroup(long placeId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).listGroup(getRequestBody(GroupApi.FUN_URL_LIST_GROUP, new ListGroupRequest(placeId, Injection.repo().user().getUserId()))).compose(filterStatus());
    }

    public Observable<Object> updateSubGroupHide(long groupId, boolean b2) {
        UpdateGroupDataRequest updateGroupDataRequest = new UpdateGroupDataRequest();
        updateGroupDataRequest.setGroupId(groupId);
        updateGroupDataRequest.setSubHide(b2);
        LHomeLog.i(getClass(), "updateSubGroupHide");
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDeviceInfo(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupDataRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<ListGroupResponse> listGroup(long placeId, long userId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).listGroup(getRequestBody(GroupApi.FUN_URL_LIST_GROUP, new ListGroupRequest(placeId, userId))).compose(filterStatus());
    }

    public Observable<ListGroupResponse.RowsBean> detailGroup(long groupId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).detailGroup(getRequestBody(GroupApi.FUN_URL_DETAIL_GROUP, new DetailGroupRequest(groupId))).compose(filterStatus());
    }

    public Observable<Object> sortGroup(List<Long> groupids) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < groupids.size(); i++) {
            arrayList.add(new SortGroupRequest.GroupId(groupids.get(i).longValue()));
        }
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).requst(getRequestBody(GroupApi.FUN_URL_SORT_GROUP, new SortGroupRequest(arrayList))).compose(filterStatus());
    }

    public Observable<Object> deleteGroup(long groupId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).requst(getRequestBody(GroupApi.FUN_URL_DELETE_GROUP, new DeleteGroupRequest(groupId).getRequestString())).compose(filterStatus());
    }

    public Observable<Object> deleteGroup(long groupId, long publicationId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).requst(getRequestBody(GroupApi.FUN_URL_DELETE_GROUP, new DeleteGroupRequest(groupId, publicationId).getRequestString())).compose(filterStatus());
    }

    public Observable<UpdateGroupResponse> updateGroupName(long groupId, String groupName) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroup(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, new UpdateGroupRequest(groupId, groupName))).compose(filterStatus());
    }

    public Observable<UpdateGroupResponse> updateGroupCodeLibrary(long groupId, String json) {
        UpdateGroupRequest updateGroupRequest = new UpdateGroupRequest();
        updateGroupRequest.setGroupid(groupId);
        updateGroupRequest.setInstruct(json);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroup(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupRequest)).compose(filterStatus());
    }

    public Observable<UpdateGroupResponse> updateGroupDevices(long groupId, List<Long> deviceIds) {
        ArrayList arrayList = new ArrayList(deviceIds.size());
        for (Long l : deviceIds) {
            UpdateGroupRequest.UpdateGroupContent updateGroupContent = new UpdateGroupRequest.UpdateGroupContent();
            updateGroupContent.deviceid = l.longValue();
            arrayList.add(updateGroupContent);
        }
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroup(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, new UpdateGroupRequest(groupId, arrayList))).compose(filterStatus());
    }

    public Observable<UpdateGroupResponse> updateDaliGroupDevices(long groupId, List<Long> deviceIds, String extParam) {
        ArrayList arrayList = new ArrayList(deviceIds.size());
        for (Long l : deviceIds) {
            UpdateGroupRequest.UpdateGroupContent updateGroupContent = new UpdateGroupRequest.UpdateGroupContent();
            updateGroupContent.deviceid = l.longValue();
            arrayList.add(updateGroupContent);
        }
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        updateGroupParamRequest.setParamExt(extParam);
        updateGroupParamRequest.setDevices(arrayList);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroup(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateGroupParamExt(long groupId, String extParam) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        updateGroupParamRequest.setParamExt(extParam);
        LHomeLog.i(getClass(), "updateGroupParamExt");
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroupInfo(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateGroupPresetKValues(long groupId, Map<String, KValue> presetKValues) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        updateGroupParamRequest.setPresetKValues(presetKValues);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroupInfo(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateGroupLeaderSup(long groupId, int v) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        updateGroupParamRequest.setLeaderSup(v);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroupInfo(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateColorPalette(long groupId, int type, String url) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        updateGroupParamRequest.setColorPaletteType(type);
        updateGroupParamRequest.setColorPaletteUrl(url);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroupInfo(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> updateGroupDeviceIndex(long groupId, long[] ids) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        updateGroupParamRequest.setGroupDeviceIndexs(ids);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroupInfo(getRequestBody(GroupApi.FUN_URL_UPDATE_GROUP, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> addGroupScene(GradientScene gradientScene) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateGroupInfo(getRequestBody(GroupApi.FUN_URL_GROUP_SCENE_ADD, gradientScene)).compose(filterStatus());
    }

    public Observable<GradientSceneResponse> queryGroupGradientScene(long groupId) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGroupId(groupId);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).queryGradientScene(getRequestBody(GroupApi.FUN_URL_GROUP_GRADIENT_SCENE_LIST, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> delGroupGradientScene(long gsId) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGsId(gsId);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).delGradientScene(getRequestBody(GroupApi.FUN_URL_GROUP_GRADIENT_SCENE_DELETE, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Object> editGroupGradientSceneName(long gsId, String name) {
        UpdateGroupParamRequest updateGroupParamRequest = new UpdateGroupParamRequest();
        updateGroupParamRequest.setGsId(gsId);
        updateGroupParamRequest.setGsName(name);
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).editeGradientScene(getRequestBody(GroupApi.FUN_URL_GROUP_GRADIENT_SCENE_UPDATE, updateGroupParamRequest.getRequestString())).compose(filterStatus());
    }

    private Observable<QuerySceneActionResponse> getGroupSceneAction(long groupId, int type) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).groupSceneAction(getRequestBody(GroupApi.FUN_URL_GROUP_SCENE_ACTIONS, new GroupActionRequest(groupId, type))).compose(filterStatus());
    }

    public Observable<QuerySceneActionResponse> getGroupLocalSceneAction(long groupId) {
        return getGroupSceneAction(groupId, 2);
    }

    public Observable<QuerySceneActionResponse> getGroupCloudSceneAction(long groupId) {
        return getGroupSceneAction(groupId, 1);
    }

    public Observable<ListSceneResponse> queryGroupScene(long groupId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).listScene(getRequestBody(GroupApi.FUN_URL_GROUP_QUERY_SCENE, new QueryByGroupIdRequest(groupId))).compose(filterStatus());
    }

    public Observable<ListAutomationResponse> queryGroupAutomation(long groupId) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).listAutomation(getRequestBody(GroupApi.FUN_URL_GROUP_QUERY_AUTOMATION, new QueryByGroupIdRequest(groupId))).compose(filterStatus());
    }

    public Observable<Object> pushBind(String deviceSn) {
        return ((PushApi) obtainRetrofitService(PushApi.class)).requst(getRequestBody(PushApi.FUN_URL_PUSH_BIND, new PushBindRequest(deviceSn))).compose(filterStatus());
    }

    public Observable<Object> pushUnBind() {
        return ((PushApi) obtainRetrofitService(PushApi.class)).requst(getRequestBody(PushApi.FUN_URL_PUSH_UNBIND, new Object())).compose(filterStatus());
    }

    public Observable<Object> pushTest(PushTestRequest.PushTestRequestBuilder builder) {
        return ((PushApi) obtainRetrofitService(PushApi.class)).requst(getRequestBody(PushApi.FUN_URL_PUSH_TEST, builder.build())).compose(filterStatus());
    }

    public Observable<Object> setLanguage(int type) {
        return ((PushApi) obtainRetrofitService(PushApi.class)).requst(getRequestBody(PushApi.FUN_URL_CHANGE_LANGUAGE, new SetLanguageRequest(type))).compose(filterStatus());
    }

    public Observable<GetProvisioningAddressResponse> getProvisioningAddress(long placeId) {
        return ((MeshApi) obtainRetrofitService(MeshApi.class)).getProvisioningAddress(getRequestBody(MeshApi.FUN_URL_GET_ADDRESS, new GetProvisioningAddressRequest(Injection.repo().user().getUserId(), placeId))).compose(filterStatus());
    }

    public Observable<AddSuperPanelResponse> addSuperPanel(String panelinfo, String panelname, String productid, String placeid, long roomid, long floorid) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).addSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_ADD, new AddSuperPanelRequest(panelinfo, panelname, productid, placeid, roomid, floorid))).compose(filterStatus());
    }

    public Observable<SetSuperPanelResponse> setSuperPanelDeviceAndGroup(long panelId, List<Long> deviceIdList, List<Long> groupIdList) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<Long> it = deviceIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(new SetSuperPanelRequest.DeviceContent(it.next().longValue()));
        }
        Iterator<Long> it2 = groupIdList.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new SetSuperPanelRequest.GroupContent(it2.next().longValue()));
        }
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).setSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_SET, new SetSuperPanelRequest(panelId).setDevices(arrayList).setGroups(arrayList2))).compose(filterStatus());
    }

    public Observable<Object> setSuperPanelVoiceControlRange(long panelId, int type, long placeId, List<Long> deviceIdList, List<Long> groupIdList, List<Long> sceneIdList) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<Long> it = deviceIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toString());
        }
        Iterator<Long> it2 = groupIdList.iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().toString());
        }
        Iterator<Long> it3 = sceneIdList.iterator();
        while (it3.hasNext()) {
            arrayList3.add(it3.next().toString());
        }
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).requst(getRequestBody(SuperPanelApi.FUN_URL_SET_VOICE_CONTROL_RANGE, new SetVoiceControlRangeRequest(panelId, type, placeId, arrayList, arrayList2, arrayList3))).compose(filterStatus());
    }

    public Observable<QuerySuperPanelVoiceControlRangeResponse> querySuperPanelVoiceControlRange(long panelId) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).queryVoiceControlRange(getRequestBody(SuperPanelApi.FUN_URL_QUERY_VOICE_CONTROL_RANGE, new DetailSuperPanelRequest(panelId))).compose(filterStatus());
    }

    public Observable<SetSuperPanelResponse> setSuperPanelDeviceGroupScene(long panelId, List<Long> deviceIdList, List<Long> groupIdList, List<Long> sceneIdList) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<Long> it = deviceIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(new SetSuperPanelRequest.DeviceContent(it.next().longValue()));
        }
        Iterator<Long> it2 = groupIdList.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new SetSuperPanelRequest.GroupContent(it2.next().longValue()));
        }
        Iterator<Long> it3 = sceneIdList.iterator();
        while (it3.hasNext()) {
            arrayList3.add(new SetSuperPanelRequest.SceneContent(it3.next().longValue()));
        }
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).setSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_SET, new SetSuperPanelRequest(panelId).setDevices(arrayList).setGroups(arrayList2).setScenes(arrayList3))).compose(filterStatus());
    }

    public Observable<SetSuperPanelResponse> setSuperPanelDevice(long panelId, List<Long> deviceIdList) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = deviceIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(new SetSuperPanelRequest.DeviceContent(it.next().longValue()));
        }
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).setSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_SET, new SetSuperPanelRequest(panelId).setDevices(arrayList))).compose(filterStatus());
    }

    public Observable<SetSuperPanelResponse> setSuperPanelScene(long panelId, List<Long> sceneIdList) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = sceneIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(new SetSuperPanelRequest.SceneContent(it.next().longValue()));
        }
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).setSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_SET, new SetSuperPanelRequest(panelId).setScenes(arrayList))).compose(filterStatus());
    }

    public Observable<SetSuperPanelResponse> setSuperPanelGroup(long panelId, List<Long> groupIdList) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = groupIdList.iterator();
        while (it.hasNext()) {
            arrayList.add(new SetSuperPanelRequest.GroupContent(it.next().longValue()));
        }
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).setSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_SET, new SetSuperPanelRequest(panelId).setGroups(arrayList))).compose(filterStatus());
    }

    public Observable<DetailSuperPanelResponse> detailSuperPanel(long panelid) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).detailSuperPanel(getRequestBody(SuperPanelApi.FUN_URL_PANEL_DETAIL, new DetailSuperPanelRequest(panelid))).compose(filterStatus());
    }

    public Observable<QuerySuperPanelKeywordInfoResponse> querySuperPanelKeywordsInfo(long panelid) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).querySuperPanelKeywordsInfo(getRequestBody(SuperPanelApi.FUN_URL_PANEL_KEY_INFO, new QuerySuperPanelKeywordInfoRequest(panelid))).compose(filterStatus());
    }

    public Observable<Object> setSuperPanelKeywordsInfo(long panelid, SuperPanelInfo.PanelKeyInfo keyInfo) {
        SetSuperPanelKeyInfoRequest.ContentInfo contentInfo = new SetSuperPanelKeyInfoRequest.ContentInfo();
        contentInfo.actiontype = keyInfo.getActionType();
        contentInfo.action = keyInfo.getAction();
        contentInfo.timespace = keyInfo.getTimeSpace();
        contentInfo.executecommand = keyInfo.getExecutecommand();
        contentInfo.actiontypename = keyInfo.getActionTypeName();
        ArrayList arrayList = new ArrayList();
        arrayList.add(contentInfo);
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).requst(getRequestBody(SuperPanelApi.FUN_URL_PANEL_KEY_INFO_SET, new SetSuperPanelKeyInfoRequest(panelid, keyInfo.getKeywordsname(), keyInfo.getKeywords(), arrayList))).compose(filterStatus());
    }

    public Observable<Object> unBindSuperPanelKeywordsInfo(long panelid, SuperPanelInfo.PanelKeyInfo keyInfo) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).requst(getRequestBody(SuperPanelApi.FUN_URL_PANEL_KEY_INFO_SET, new SetSuperPanelKeyInfoRequest(panelid, keyInfo.getKeywordsname(), keyInfo.getKeywords(), new ArrayList()))).compose(filterStatus());
    }

    public Observable<AddSuperPanelResponse> addSbcPanel(String panelinfo, String panelname, String productid, String placeid, long roomid, long floorid, String authCode, String codeVerifier, String aispeechUserId, String clientId) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).addSuperPanel(getRequestBody(SbcApi.FUN_URL_SBC_PANEL_ADD, new AddSbcPanelRequest(panelinfo, panelname, productid, placeid, roomid, floorid, authCode, codeVerifier, aispeechUserId, clientId))).compose(filterStatus());
    }

    public Observable<Object> notifySbc(long placeid) {
        return ((SuperPanelApi) obtainRetrofitService(SuperPanelApi.class)).addSuperPanel(getRequestBody(SbcApi.FUN_URL_SBC_AUTH_NOTIFY, new NotifySbcRequest(placeid))).compose(filterStatus());
    }

    public Observable<Object> addMode(String modeName, long placeId, int deviceType, int modeType, int modeIndex, int picIndex, String content, String parms, int playTimes) {
        return ((ModeApi) obtainRetrofitService(ModeApi.class)).addMode(getRequestBody(ModeApi.FUN_URL_MODE_ADD, new AddModeRequest(modeName, placeId, Injection.repo().user().getUserId(), deviceType, modeType, modeIndex, picIndex, content, parms, playTimes))).compose(filterStatus());
    }

    public Observable<ListModeResponse> getModeList(long placeId, int deviceType, int modeType) {
        return ((ModeApi) obtainRetrofitService(ModeApi.class)).getModeList(getRequestBody(ModeApi.FUN_URL_MODE_LIST, new ListModeRequest(placeId, Injection.repo().user().getUserId(), deviceType, modeType))).compose(filterStatus());
    }

    public Observable<Object> deleteMode(long placeId, int deviceType, int modeType, int modeIndex) {
        return ((ModeApi) obtainRetrofitService(ModeApi.class)).requst(getRequestBody(ModeApi.FUN_URL_MODE_DETAIL, new DeleteModeRequest(placeId, Injection.repo().user().getUserId(), deviceType, modeType, modeIndex))).compose(filterStatus());
    }

    public Observable<CountryListInfoResponse> getCountryList() {
        return ((UserApi) obtainRetrofitService(UserApi.class)).getCountryList(getDefaultRequestBody(UserApi.FUN_URL_GET_COUNTRY_INFO_LIST, null)).compose(filterStatus());
    }

    public Observable<VersionInfo> checkAppVersion(String apptypecode, String versionnum, String packname) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).checkVersionUpdate(getRequestBody(UserApi.CHECK_VERSION_UPDATE, new CheckVersionRequest(apptypecode, versionnum, packname))).compose(filterStatus());
    }

    public Observable<ListMcuResponse> getMcuList(int versionnum) {
        return ((McuApi) obtainRetrofitService(McuApi.class)).getMcuList(getRequestBody(McuApi.FUN_URL_DEVICE_MCU_VERSION, new ListMcuRequest(versionnum))).compose(filterStatus());
    }

    public Observable<SunInfo.SunBean> getSunTime(String location) {
        return ((UserApi) obtainRetrofitService(UserApi.class)).getSunTime(getRequestBody(UserApi.SUN_TIME, new SunTimeRequest(location))).compose(filterStatus());
    }

    public Observable<CreateTokenResponse> createPlaceToken(long placeid, boolean forceFlush) {
        return ((EzCameraApi) obtainRetrofitService(EzCameraApi.class)).createPlaceToken(getRequestBody(EzCameraApi.FUN_CREATE_PLACE_TOKEN, new EzPlaceRequest(placeid, forceFlush))).compose(filterStatus());
    }

    public Observable<GetTokenResponse> getPlaceToken(long placeid, boolean forceFlush) {
        return ((EzCameraApi) obtainRetrofitService(EzCameraApi.class)).getPlaceToken(getRequestBody(EzCameraApi.FUN_GET_PLACE_TOKEN, new EzPlaceRequest(placeid, forceFlush))).compose(filterStatus());
    }

    public Observable<Object> updateMusicInfo(String mac, List<MusicInfo> musicInfoList) {
        return ((MusicApi) obtainRetrofitService(MusicApi.class)).updateMusicInfo(getRequestBody(MusicApi.FUN_URL_MUSIC_UPDATE, new MusicRequest(mac, musicInfoList))).compose(filterStatus());
    }

    public Observable<MusicListResponse> getMusicInfo(String mac) {
        return ((MusicApi) obtainRetrofitService(MusicApi.class)).getMusicInfo(getRequestBody(MusicApi.FUN_URL_MUSIC_GET_LIST, new MusicGetListRequest(mac))).compose(filterStatus());
    }

    public Observable<Object> delLocalMusic(SongRequest request) {
        return ((MusicApi) obtainRetrofitService(MusicApi.class)).getMusicInfo(getRequestBody(MusicApi.FUN_URL_MUSIC_DEL_LOCAL_LIST, request)).compose(filterStatus());
    }

    public Observable<Object> sortLocalMusic(SongRequest request) {
        return ((MusicApi) obtainRetrofitService(MusicApi.class)).getMusicInfo(getRequestBody(MusicApi.FUN_URL_MUSIC_SORT_LOCAL_LIST, request)).compose(filterStatus());
    }

    public Observable<MusicListResponse> getLocalMusicMusic(String mac) {
        return ((MusicApi) obtainRetrofitService(MusicApi.class)).getLocalMusicList(getRequestBody(MusicApi.FUN_URL_MUSIC_GET_LOCAL_LIST, new MusicGetListRequest(mac))).compose(filterStatus());
    }

    public Observable<ListDaliDeviceResponse> updateDaliDeviceList(Device device, List<Device> deviceList) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).updateDaliDeviceList(getRequestBody(DeviceApi.FUN_URL_DEVICE_DALI_EDIT, new UpdateDaliDeviceRequest(device.getRoomId(), device.getFloorId(), device.getPlaceId(), device.getDeviceId(), deviceList))).compose(filterStatus());
    }

    public Observable<ListDaliGroupResponse> updateDaliGroupList(Device device, List<Group> groupList) {
        return ((GroupApi) obtainRetrofitService(GroupApi.class)).updateDaliGroupList(getRequestBody(GroupApi.FUN_URL_GROUP_SYNC_BATCH, new UpdateDaliGroupRequest(device, groupList)));
    }

    public Observable<ListDaliSceneResponse> updateDaliSceneList(Device device, List<Scene> sceneList) {
        return ((SceneApi) obtainRetrofitService(SceneApi.class)).batchSyncScene(getRequestBody(SceneApi.FUN_URL_SCENE_SYNC_BATCH, new DaliSceneRequest(device, sceneList)));
    }

    public Observable<Object> activeVoiceCall(String mac) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setMac(mac);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).querySetting(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_ACTIVE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<QuerySettingResponse> queryVoiceCallSetting(long panelId, String mac) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelid(panelId);
        voiceCallRequest.setMac(mac);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).querySetting(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_SETTING_GET, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallSetting> addSetting(long panelId, String mac, int autoAnswer, int busy, String week, String start, String end) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelid(panelId);
        voiceCallRequest.setMac(mac);
        voiceCallRequest.setIsautoanswer(autoAnswer);
        voiceCallRequest.setIsbusymode(busy);
        voiceCallRequest.setBusymodeweeks(week);
        voiceCallRequest.setBusymodestart(start);
        voiceCallRequest.setBusymodeend(end);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addSetting(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_SETTING_ADD, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallSetting> updateSetting(long id, int autoAnswer, int busy, String week, String start, String end) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setIsautoanswer(autoAnswer);
        voiceCallRequest.setIsbusymode(busy);
        voiceCallRequest.setBusymodeweeks(week);
        voiceCallRequest.setBusymodestart(start);
        voiceCallRequest.setBusymodeend(end);
        voiceCallRequest.setPanelvoicesettingid(id);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).updateSetting(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_SETTING_UPDATE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<QuerySettingResponse.VoiceWhiteList> addWhiteList(long panelId, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelid(panelId);
        voiceCallRequest.setWhiteList(ids);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addWhiteList(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_WHITE_LIST_ADD, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<QuerySettingResponse.VoiceWhiteList> updateWhiteList(long id, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelvoicewhitelistid(id);
        voiceCallRequest.setWhiteList(ids);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addWhiteList(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_WHITE_LIST_UPDATE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<Object> unbind(long id, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelid(id);
        voiceCallRequest.setUseridList(ids);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).unbind(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_UNBIND, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<QueryPlaceUserAndPanelResponse> queryPlaceUserAndPanel(long placeId, long panelId) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPlaceid(placeId);
        voiceCallRequest.setPanelid(panelId);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).queryPlaceUserAndPanel(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_PLACE_USER_GET, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<Object> messageGroupPull(long groupId) {
        VoiceCallMessagePullRequest voiceCallMessagePullRequest = new VoiceCallMessagePullRequest();
        voiceCallMessagePullRequest.setPanelvoicegroupid(groupId);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).messagePull(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_MESSAGE_PULL, voiceCallMessagePullRequest)).compose(filterStatus());
    }

    public Observable<Object> messageUserPull(long userId) {
        VoiceCallMessagePullRequest voiceCallMessagePullRequest = new VoiceCallMessagePullRequest();
        voiceCallMessagePullRequest.setUserid(userId);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).messagePull(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_MESSAGE_PULL, voiceCallMessagePullRequest)).compose(filterStatus());
    }

    public Observable<Object> messageStatusPull(long groupId, List<Long> userIds, int statusType) {
        VoiceCallMessagePullRequest voiceCallMessagePullRequest = new VoiceCallMessagePullRequest();
        voiceCallMessagePullRequest.setUserids(userIds);
        voiceCallMessagePullRequest.setPanelvoicegroupid(groupId);
        voiceCallMessagePullRequest.setStatusType(statusType);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).messageStatusPull(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_MESSAGE_STATUS_PULL, voiceCallMessagePullRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallGroup> queryGroup(long groupId) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelvoicegroupid(groupId);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).queryGroup(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_GROUP_GET, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallGroup> addVoiceGroup(long panelId, String name, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelid(panelId);
        voiceCallRequest.setGroupList(ids);
        voiceCallRequest.setName(name);
        voiceCallRequest.setType(1);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addVoiceGroup(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_ADD, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallGroup> updateVoiceGroup(long groupId, String name, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelvoicegroupid(groupId);
        voiceCallRequest.setGroupList(ids);
        voiceCallRequest.setName(name);
        voiceCallRequest.setType(1);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addVoiceGroup(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_UPDATE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallGroup> addTempVoiceGroup(long panelId, String name, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelid(panelId);
        voiceCallRequest.setGroupList(ids);
        voiceCallRequest.setName(name);
        voiceCallRequest.setType(2);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addVoiceGroup(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_UPDATE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<VoiceCallGroup> addTempVoiceGroup(String mac, String name, List<Long> ids) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setMac(mac);
        voiceCallRequest.setGroupList(ids);
        voiceCallRequest.setName(name);
        voiceCallRequest.setType(2);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).addVoiceGroup(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_UPDATE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<Object> delVoiceGroup(long groupId) {
        VoiceCallRequest voiceCallRequest = new VoiceCallRequest();
        voiceCallRequest.setPanelvoicegroupid(groupId);
        return ((VoiceCallApi) obtainRetrofitService(VoiceCallApi.class)).delVoiceGroup(getRequestBody(VoiceCallApi.FUN_URL_PANEL_VOICE_CALL_VOICE_GROUP_DELETE, voiceCallRequest)).compose(filterStatus());
    }

    public Observable<ListPhotoResponse> listPhoto(long panelid, String mac) {
        return ((PhotoApi) obtainRetrofitService(PhotoApi.class)).listPhoto(getRequestBody(PhotoApi.FUN_URL_PANEL_PICTURE_LIST, new ListPhotoRequest(panelid, mac))).compose(filterStatus());
    }

    public Observable<Object> uploadPhotoList(long panelid, String mac, List<UploadPhotoRequest.Photo> pictures) {
        return ((PhotoApi) obtainRetrofitService(PhotoApi.class)).requst(getRequestBody(PhotoApi.FUN_URL_PANEL_PICTURE_SAVE, new UploadPhotoRequest(panelid, mac, pictures))).compose(filterStatus());
    }

    public Observable<Object> deletePhoto(List<Long> pictureids) {
        return ((PhotoApi) obtainRetrofitService(PhotoApi.class)).requst(getRequestBody(PhotoApi.FUN_URL_PANEL_PICTURE_DELETE, new DeletePhotoRequest(pictureids))).compose(filterStatus());
    }

    public Observable<Object> sortPhoto(List<SortPhotoRequest.Bean> beans) {
        return ((PhotoApi) obtainRetrofitService(PhotoApi.class)).requst(getRequestBody(PhotoApi.FUN_URL_PANEL_PICTURE_SORT, new SortPhotoRequest(beans))).compose(filterStatus());
    }

    public Observable<IntercomLoginResponse> intercomLogin(String account, String passwd) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).login(getRequestBody(IntercomApi.FUN_MANAGER_URL_LOGIN, new IntercomLoginRequest(account, passwd))).compose(filterStatus());
    }

    public Observable<CommunityInfoResponse> getCommunityInfo() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getCommunityInfo(getRequestBody(IntercomApi.FUN_GET_COMMUNITY_INFO, ""));
    }

    public Observable<ResultBean<KeyListResponse>> getKeyList() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getKeyList(getRequestBody(IntercomApi.FUN_GET_KEY_LIST, ""));
    }

    public Observable<GetBleCodeResponse> setBleCode() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).setBleCode(getRequestBody(IntercomApi.FUN_SET_BLE_CODE, new IntercomBleCodeRequest(1))).compose(filterStatus());
    }

    public Observable<GetBleCodeResponse> getBleCode() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getBleCode(getRequestBody(IntercomApi.FUN_GET_BLE_CODE, "")).compose(filterStatus());
    }

    public Observable<IntercomTokenResponse> getToken(long placeId) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getToken(getRequestBody(IntercomApi.FUN_INTERCOM_OAUTH_LOGIN, new NotifySbcRequest(placeId))).compose(filterStatus());
    }

    public Observable<Object> deleteKeyList(List<Long> keyList) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).requst(getRequestBody(IntercomApi.FUN_DELETE_KEY_LIST, new IntercomTempKeyRequest(keyList))).compose(filterStatus());
    }

    public Observable<Object> openDoor(String mac, long relay) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).requst(getRequestBody(IntercomApi.FUN_OPEN_DOOR_REMOTE, new IntercomOpenDoorRemoteRequest(mac, relay))).compose(filterStatus());
    }

    public Observable<Object> setPrivateKey(String key, long placeId) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).requst(getRequestBody(IntercomApi.FUN_SET_PRIVATE_KEY, new IntercomSetKeyRequest(key, placeId))).compose(filterStatus());
    }

    public Observable<Object> deleteFace() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).requst(getRequestBody(IntercomApi.FUN_DELETE_FACE_STATUS, "")).compose(filterStatus());
    }

    public Observable<GetPrivateKeyResponse> getPrivateKey(long placeId) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getPrivateKey(getRequestBody(IntercomApi.FUN_GET_PRIVATE_KEY, new NotifySbcRequest(placeId))).compose(filterStatus());
    }

    public Observable<GetFaceResponse> getFaceStatus() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getFaceStatus(getRequestBody(IntercomApi.FUN_GET_FACE_STATUS, "")).compose(filterStatus());
    }

    public Observable<GetCallLogResponse> getCallLog() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getCallLog(getRequestBody(IntercomApi.FUN_GET_CALL_LOG, ""));
    }

    public Observable<GetOpenDoorLogResponse> getOpenDoorLog() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getOpenDoorLog(getRequestBody(IntercomApi.FUN_GET_OPEN_DOOR_LOG, ""));
    }

    public Observable<CreateKeyResponse> createOpenDoorKey(String beginTime, String endTime, int count) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).createKeyList(getRequestBody(IntercomApi.FUN_CREATE_KEY, new IntercomCreateKeyRequest(beginTime, endTime, count)));
    }

    public Observable<UploadFaceResponse> uploadFaceFile(File file, String url) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).uploadFaceFile(url, MultipartBody.Part.createFormData("fileupload", "face.jpg", RequestBody.create(file, MediaType.parse(HttpHeaders.Values.MULTIPART_FORM_DATA))));
    }

    public Observable<UserInfoResponse> getIntercomUserInfo() {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).getUserInfo(getRequestBody(IntercomApi.FUN_GET_USER_INFO, "")).compose(filterStatus());
    }

    public Observable<ResultBean<Object>> importCommunity(long id, long placeId) {
        return ((IntercomApi) obtainRetrofitService(IntercomApi.class)).importCommunityInfo(getRequestBody(IntercomApi.FUN_IMPORT_COMMUNITY, new ImportCommunityRequest(id, placeId)));
    }

    public Call<PlaceResponse> getPlaceByMapApi(String keywords, String region) {
        return ((MapApi) obtainRetrofitService(MapApi.class)).getPlace(Constants.AMAP_WEB_KEY, keywords, region);
    }

    public Call<GeocodeResponse> geocodeByMapApi(String location, String radius) {
        return ((MapApi) obtainRetrofitService(MapApi.class)).geocode(Constants.AMAP_WEB_KEY, location, radius);
    }

    public Observable<KeyInfoResponse> getKeyInfo(long deviceId) {
        KeyInfoRequest keyInfoRequest = new KeyInfoRequest();
        keyInfoRequest.setDeviceid(deviceId);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).getKeyInfo(getRequestBody(DeviceApi.FUN_URL_GET_KEY_INFO_LIST, keyInfoRequest)).compose(filterStatus());
    }

    public Observable<KeyZonesResponse> getKeyZone(long deviceId) {
        KeyInfoRequest keyInfoRequest = new KeyInfoRequest();
        keyInfoRequest.setDeviceid(deviceId);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryKeyZones(getRequestBody(DeviceApi.FUN_URL_GET_KEY_ZONE_LIST, keyInfoRequest)).compose(filterStatus());
    }

    public Observable<List<KeyInfo>> bindKeyInfo(long deviceId, List<KeyInfo> list) {
        KeyInfoRequest keyInfoRequest = new KeyInfoRequest();
        keyInfoRequest.setDeviceid(deviceId);
        keyInfoRequest.setKeyinfos(list);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).setKeyInfo(getRequestBody(DeviceApi.FUN_URL_GET_KEY_INFO_SET, keyInfoRequest)).compose(filterStatus());
    }

    public Observable<Object> bindKeyInfo(long deviceId, KeyInfo keyInfo) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).bindKeyInfo(getRequestBody(DeviceApi.FUN_URL_GET_KEY_INFO_BIND, keyInfo)).compose(filterStatus());
    }

    public Observable<Object> unbindKeyInfo(long deviceId, int zone) {
        KeyInfoRequest keyInfoRequest = new KeyInfoRequest();
        keyInfoRequest.setDeviceid(deviceId);
        keyInfoRequest.setZoneNum(zone);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).unBindKeyInfo(getRequestBody(DeviceApi.FUN_URL_GET_KEY_INFO_ZONE_UNBIND, keyInfoRequest)).compose(filterStatus());
    }

    public Observable<Object> keyZonesSet(long deviceId, List<KeyZone> keyZones) {
        KeyInfoRequest keyInfoRequest = new KeyInfoRequest();
        keyInfoRequest.setDeviceid(deviceId);
        keyInfoRequest.setKeyZones(keyZones);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).keyZoneSet(getRequestBody(DeviceApi.FUN_URL_KEY_ZONES_SET, keyInfoRequest)).compose(filterStatus());
    }

    public Observable<Object> editKeyZone(KeyZone zone) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).editKeyZone(getRequestBody(DeviceApi.FUN_URL_KEY_ZONES_UPDATE, zone)).compose(filterStatus());
    }

    public Observable<ListLogResponse> queryLog(long deviceId, int pageSize, int pageNum) {
        QueryLogRequest queryLogRequest = new QueryLogRequest();
        queryLogRequest.setDeviceId(deviceId);
        queryLogRequest.setPageSize(pageSize);
        queryLogRequest.setPageNum(pageNum);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryLog(getRequestBody(DeviceApi.FUN_URL_QUERY_DEVICE_LOG, queryLogRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<ListLogResponse> queryLog(long deviceId, String beginTime, String endTime) {
        QueryLogRequest queryLogRequest = new QueryLogRequest();
        queryLogRequest.setDeviceId(deviceId);
        queryLogRequest.setBeginTime(beginTime);
        queryLogRequest.setEndTime(endTime);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryLog(getRequestBody(DeviceApi.FUN_URL_QUERY_DEVICE_LOG, queryLogRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<ListLogResponse> queryLog(long deviceId, String propertyCode, String beginTime, String endTime) {
        QueryLogRequest queryLogRequest = new QueryLogRequest();
        queryLogRequest.setDeviceId(deviceId);
        queryLogRequest.setPropertyCode(propertyCode);
        queryLogRequest.setBeginTime(beginTime);
        queryLogRequest.setEndTime(endTime);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryLog(getRequestBody(DeviceApi.FUN_URL_QUERY_DEVICE_LOG, queryLogRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<ListLogResponse> queryLog(long deviceId, String[] codeList, String beginTime, String endTime) {
        QueryLogRequest queryLogRequest = new QueryLogRequest();
        queryLogRequest.setDeviceId(deviceId);
        queryLogRequest.setCodeList(codeList);
        queryLogRequest.setBeginTime(beginTime);
        queryLogRequest.setEndTime(endTime);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryLog(getRequestBody(DeviceApi.FUN_URL_QUERY_DEVICE_LOG, queryLogRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<ListLogDateResponse> queryLogDate(long deviceId, String propertyCode, String month) {
        QueryLogRequest queryLogRequest = new QueryLogRequest();
        queryLogRequest.setDeviceId(deviceId);
        queryLogRequest.setPropertyCode(propertyCode);
        queryLogRequest.setDateMonth(month);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryLogDate(getRequestBody(DeviceApi.FUN_URL_QUERY_DEVICE_LOG_DATE, queryLogRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<Rs8CategoryResponse> queryRs8Category() {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryRs8Category(getRequestBody(DeviceApi.FUN_QUERY_RS8_CATEGORY, new Object())).compose(filterStatus());
    }

    public Observable<Rs8BrandResponse> queryRs8Brand(long category) {
        Rs8Request rs8Request = new Rs8Request();
        rs8Request.setCategoryid(category);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryRs8Brand(getRequestBody(DeviceApi.FUN_QUERY_RS8_BRAND, rs8Request)).compose(filterStatus());
    }

    public Observable<String> queryRs8SubAddress(long brandId, String address) {
        Rs8Request rs8Request = new Rs8Request();
        rs8Request.setBrandid(brandId);
        rs8Request.setAddress(address);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryRs8SubAddress(getRequestBody(DeviceApi.FUN_QUERY_RS8_SUB_ADDRESS, rs8Request)).compose(filterStatus());
    }

    public Observable<Rs8CodeLibResponse> queryRs8CodeLib(long category, long brand, String address) {
        Rs8Request rs8Request = new Rs8Request();
        rs8Request.setCategoryid(category);
        rs8Request.setBrandid(brand);
        rs8Request.setAddress(address);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryRs8CodeLib(getRequestBody(DeviceApi.FUN_QUERY_RS8_CODE_LIB, rs8Request)).compose(filterStatus());
    }

    public Observable<Rs8CodeLibResponse> queryRs8CodeLibByLibId(long codeLibId, long brand, String address) {
        Rs8Request rs8Request = new Rs8Request();
        rs8Request.setCodelibid(codeLibId);
        rs8Request.setAddress(address);
        rs8Request.setBrandid(brand);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryRs8CodeLib(getRequestBody(DeviceApi.FUN_QUERY_RS8_CODE_LIB, rs8Request)).compose(filterStatus());
    }

    public Observable<ListNeedUpgradeResponse> queryNeedUpgradeList(long panelId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).getNeedUpgradeList(getRequestBody(DeviceApi.FUN_URL_PANEL_UPGRADE_LIST, new SetSuperPanelRequest(panelId))).compose(filterStatus());
    }

    public Observable<Object> sendChildMcuUpgrade(long panelId, List<Long> ids) {
        SetSuperPanelRequest setSuperPanelRequest = new SetSuperPanelRequest(panelId);
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = ids.iterator();
        while (it.hasNext()) {
            arrayList.add(new SetSuperPanelRequest.DeviceContent(it.next().longValue()));
        }
        setSuperPanelRequest.setDevices(arrayList);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).sendChildMcuUpgrade(getRequestBody(DeviceApi.FUN_URL_PANEL_UPGRADE_SET, setSuperPanelRequest)).compose(filterStatus());
    }

    public Observable<ListDeviceResponse.RowsBean> queryDevice(long deviceId) {
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryDevice(getRequestBody(DeviceApi.FUN_URL_QUERY_DEVICE, new QueryByDeviceIdRequest(deviceId))).compose(filterStatus());
    }

    public Observable<Object> deviceController(long deviceId, String name) {
        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
        updateDeviceRequest.setDeviceId(deviceId);
        updateDeviceRequest.setItem(name);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).deviceController(getRequestBody(DeviceApi.FUN_URL_DEVICE_CONTROLLER, updateDeviceRequest.getRequestString())).compose(filterStatus());
    }

    public Observable<MatterDeviceResponse> getCgKitSyncDeviceByMatter(long deviceId, String mac) {
        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setDeviceid(deviceId);
        deviceRequest.setMac(mac);
        deviceRequest.setPlatform(2);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).queryCgKitDevices(getRequestBody(DeviceApi.FUN_URL_QUERY_CG_KIT_DEVICES, deviceRequest)).compose(filterStatus());
    }

    public Observable<Object> syncCgKitSyncDeviceByMatter(long panelId, String mac, List<Long> deviceIdList, List<Long> groupIdList, List<Integer> deviceSort, List<Integer> groupSort) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < deviceIdList.size(); i++) {
            Long l = deviceIdList.get(i);
            arrayList.add(new SetSuperPanelRequest.DeviceContent(l.longValue(), deviceSort.get(i).intValue()));
        }
        for (int i2 = 0; i2 < groupIdList.size(); i2++) {
            Long l2 = groupIdList.get(i2);
            arrayList2.add(new SetSuperPanelRequest.GroupContent(l2.longValue(), groupSort.get(i2).intValue()));
        }
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).setCgKitDevices(getRequestBody(DeviceApi.FUN_URL_CG_KIT_UPDATE_SET, new SetSuperPanelRequest(panelId, mac, 2).setDevices(arrayList).setGroups(arrayList2))).compose(filterStatus());
    }

    public Observable<Object> syncCgKitSyncSceneByMatter(long panelId, String mac, List<Long> sceneIdList, List<Integer> sceneSort) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < sceneIdList.size(); i++) {
            Long l = sceneIdList.get(i);
            arrayList.add(new SetSuperPanelRequest.SceneContent(l.longValue(), sceneSort.get(i).intValue()));
        }
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).setCgKitDevices(getRequestBody(DeviceApi.FUN_URL_CG_KIT_UPDATE_SET, new SetSuperPanelRequest(panelId, mac, 2).setScenes(arrayList))).compose(filterStatus());
    }

    public Observable<Object> updateDeviceBatch(List<Long> deviceIds, int writable) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = deviceIds.iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest();
            updateDeviceRequest.setDeviceId(longValue);
            updateDeviceRequest.setWritable(writable);
            arrayList.add(updateDeviceRequest.getParamMap());
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("devices", (Object) arrayList);
        return ((DeviceApi) obtainRetrofitService(DeviceApi.class)).deviceController(getRequestBody(DeviceApi.FUN_URL_DEVICE_UPDATE_BATCH, GsonUtils.toJson(jSONObject))).compose(filterStatus());
    }

    public Observable<Object> backupPlaceData(long placeId, JSONObject jsonObject) {
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).requst(getRequestBody(PlaceApi.FUN_URL_PLACE_DATA_BACK, new UpdatePlaceDataRequest(placeId, jsonObject))).compose(filterStatus());
    }

    public Observable<GetPlaceDataResponse> queryPlaceData(long placeId) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(PushContentParamKey.PLACE_ID, (Object) Long.valueOf(placeId));
        return ((PlaceApi) obtainRetrofitService(PlaceApi.class)).getPlaceData(getRequestBody(PlaceApi.FUN_URL_GET_PLACE_BACK_DATA, GsonUtils.toJson(jSONObject))).compose(filterStatus());
    }
}