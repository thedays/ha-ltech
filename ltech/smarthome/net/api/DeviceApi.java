package com.ltech.smarthome.net.api;

import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.model.key.KeyZone;
import com.ltech.smarthome.net.response.MatterDeviceResponse;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.device.GetDeviceOnlineResponse;
import com.ltech.smarthome.net.response.device.GetReplaceDataResponse;
import com.ltech.smarthome.net.response.device.KeyInfoResponse;
import com.ltech.smarthome.net.response.device.KeyZonesResponse;
import com.ltech.smarthome.net.response.device.ListDaliDeviceResponse;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.net.response.log.ListLogDateResponse;
import com.ltech.smarthome.net.response.log.ListLogResponse;
import com.ltech.smarthome.net.response.rs8.Rs8BrandResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CategoryResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import com.ltech.smarthome.net.response.super_panel.ListNeedUpgradeResponse;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface DeviceApi extends BaseNetApi {
    public static final String FUN_URL_DEVICE_AREA_UPDATE = "ysnetwork.base.area.device.update";
    public static final String FUN_URL_DEVICE_CONTROL = "ysnetwork.base.com.app.device.requestcontrol";
    public static final String FUN_URL_DEVICE_DELETE = "ysnetwork.base.area.device.delete";
    public static final String FUN_URL_DEVICE_LIST = "ysnetwork.base.area.device.list";
    public static final String FUN_URL_DEVICE_UPDATE_INDEX = "ysnetwork.base.com.index.updatedeviceindex";
    public static final String FUN_URL_UPDATE_ALL_INDEX = "ysnetwork.base.com.group.updateallindex";
    public static final String FUN_URL_DEVICE_ADD = ApiConstants.FUN_URL_BASE + ".area.place.device.add";
    public static final String FUN_URL_DEVICE_UPDATE_INFO = ApiConstants.FUN_URL_BASE + ".app.update";
    public static final String FUN_URL_DEVICE_SUBSCRIBE = ApiConstants.FUN_URL_BASE + ".app.device.subscribe";
    public static final String FUN_URL_DEVICE_UNSUBSCRIBE = ApiConstants.FUN_URL_BASE + ".app.device.unsubscribe";
    public static final String FUN_URL_DEVICE_GET_ONLINE_STATUS = ApiConstants.FUN_URL_BASE + ".app.device.getstatus";
    public static final String FUN_URL_DEVICE_SYNC_STATUS = ApiConstants.FUN_URL_BASE + ".app.device.syncstatus";
    public static final String FUN_URL_DEVICE_QUERY_SCENE = ApiConstants.FUN_URL_BASE + ".device.scene.query";
    public static final String FUN_URL_DEVICE_QUERY_GROUP = ApiConstants.FUN_URL_BASE + ".device.group.query";
    public static final String FUN_URL_PANEL_SORT_ROLE = ApiConstants.FUN_URL_BASE + ".panel.set";
    public static final String FUN_URL_DEVICE_QUERY_AUTOMATION = ApiConstants.FUN_URL_BASE + ".device.automationaction.query";
    public static final String FUN_URL_DEVICE_DALI_EDIT = ApiConstants.FUN_URL_BASE + ".area.device.dali.edit";
    public static final String FUN_URL_DEVICE_SCENE_ACTIONS = ApiConstants.FUN_URL_BASE + ".scene.selectscenebydevice";
    public static final String FUN_URL_DEVICE_GET_REPLACE_DATA = ApiConstants.FUN_URL_BASE + ".devicereplace.getdata";
    public static final String FUN_URL_DEVICE_DATA_BACK = ApiConstants.FUN_URL_BASE + ".devicereplace.back";
    public static final String FUN_URL_DEVICE_DATA_BACK_CLEAR = ApiConstants.FUN_URL_BASE + ".devicereplace.deldata";
    public static final String FUN_URL_GET_KEY_INFO_LIST = ApiConstants.FUN_URL_BASE + ".device.keyinfo.list";
    public static final String FUN_URL_GET_KEY_INFO_BIND = ApiConstants.FUN_URL_BASE + ".device.keyinfo.bind";
    public static final String FUN_URL_GET_KEY_INFO_SET = ApiConstants.FUN_URL_BASE + ".device.keyinfo.set";
    public static final String FUN_URL_GET_KEY_INFO_ZONE_UNBIND = ApiConstants.FUN_URL_BASE + ".device.keyinfo.zone.unbind";
    public static final String FUN_URL_GET_KEY_INFO_UNBIND = ApiConstants.FUN_URL_BASE + ".device.keyinfo.unbind";
    public static final String FUN_URL_SUPER_PANEL_REPLACE = ApiConstants.FUN_URL_BASE + ".panel.replacing.send";
    public static final String FUN_URL_KEY_ZONES_SET = ApiConstants.FUN_URL_BASE + ".device.keyzone.set";
    public static final String FUN_URL_KEY_ZONES_UPDATE = ApiConstants.FUN_URL_BASE + ".device.keyzone.update";
    public static final String FUN_URL_GET_KEY_ZONE_LIST = ApiConstants.FUN_URL_BASE + ".device.keyzone.list";
    public static final String FUN_URL_QUERY_DEVICE_LOG = ApiConstants.FUN_URL_BASE + ".device.logs.query";
    public static final String FUN_URL_QUERY_DEVICE_LOG_DATE = ApiConstants.FUN_URL_BASE + ".device.logs.date.query";
    public static final String FUN_QUERY_RS8_CATEGORY = ApiConstants.FUN_URL_BASE + ".codelib.category.list";
    public static final String FUN_QUERY_RS8_BRAND = ApiConstants.FUN_URL_BASE + ".codelib.brand.list";
    public static final String FUN_QUERY_RS8_CODE_LIB = ApiConstants.FUN_URL_BASE + ".codelib.list";
    public static final String FUN_QUERY_RS8_SUB_ADDRESS = ApiConstants.FUN_URL_BASE + ".codelib.setaddress.get";
    public static final String FUN_URL_PANEL_UPGRADE_LIST = ApiConstants.FUN_URL_BASE + ".panel.upgrade.list";
    public static final String FUN_URL_PANEL_UPGRADE_SET = ApiConstants.FUN_URL_BASE + ".panel.upgrade.set";
    public static final String FUN_URL_QUERY_DEVICE = ApiConstants.FUN_URL_BASE + ".device.get";
    public static final String FUN_URL_DEVICE_CONTROLLER = ApiConstants.FUN_URL_BASE + ".device.controller";
    public static final String FUN_URL_QUERY_CG_KIT_DEVICES = ApiConstants.FUN_URL_BASE + ".homekit.info.get";
    public static final String FUN_URL_CG_KIT_UPDATE_SET = ApiConstants.FUN_URL_BASE + ".homekit.updateset";
    public static final String FUN_URL_DEVICE_UPDATE_BATCH = ApiConstants.FUN_URL_BASE + ".device.update.batch";
    public static final String FUN_URL_DEVICE_REPORT_INSTRUCT = ApiConstants.FUN_URL_BASE + ".device.reportinstruct.update";

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<AddDeviceResponse>> addDevice(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<KeyInfo>> bindKeyInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> deviceController(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<QuerySceneActionResponse>> deviceSceneAction(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<KeyZone>> editKeyZone(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetDeviceOnlineResponse>> getDeviceOnlineStatus(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> getDeviceSyncStatus(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<KeyInfoResponse>> getKeyInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListNeedUpgradeResponse>> getNeedUpgradeList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<GetReplaceDataResponse>> getReplaceData(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<KeyZone>>> keyZoneSet(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListAutomationResponse>> listAutomation(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListDeviceResponse>> listDevice(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListSceneResponse>> listScene(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> panelSortRole(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<MatterDeviceResponse>> queryCgKitDevices(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListDeviceResponse.RowsBean>> queryDevice(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<KeyZonesResponse>> queryKeyZones(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListLogResponse>> queryLog(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListLogDateResponse>> queryLogDate(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Rs8BrandResponse>> queryRs8Brand(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Rs8CategoryResponse>> queryRs8Category(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Rs8CodeLibResponse>> queryRs8CodeLib(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<String>> queryRs8SubAddress(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> sendChildMcuUpgrade(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> setCgKitDevices(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<KeyInfo>>> setKeyInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<List<KeyInfo>>> unBindKeyInfo(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<ListDaliDeviceResponse>> updateDaliDeviceList(@Body RequestBody requestBody);

    @POST(ApiConstants.REST_URL)
    Observable<ResultBean<Object>> updateDeviceInfo(@Body RequestBody requestBody);
}