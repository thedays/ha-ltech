package com.ltech.smarthome.utils;

import android.content.Context;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.auto.UserLocationParam;
import com.ltech.smarthome.model.bean.LocationInfo;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class LocationHelper {
    public static final int NOT_REPORT_REGION = 50;
    public static final int REPORT_INTERVAL = 110;
    public static AMapLocationListener sLocationListener = new AMapLocationListener() { // from class: com.ltech.smarthome.utils.LocationHelper.1
        @Override // com.amap.api.location.AMapLocationListener
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation == null) {
                return;
            }
            if (AppUtils.isAppDebug()) {
                LHomeLog.i(getClass(), "location--->" + aMapLocation.getLatitude() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER + aMapLocation.getLongitude());
            }
            LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            LocationInfo locationInfo = (LocationInfo) SharedPreferenceUtil.getBean("location_info_" + Injection.repo().user().getUserId(), LocationInfo.class);
            if (locationInfo != null) {
                long queryLongValue = SharedPreferenceUtil.queryLongValue("last_automation_report_time_" + Injection.repo().user().getUserId());
                LHomeLog.i(getClass(), "lastReportTime--->" + queryLongValue);
                float calculateLineDistance = AMapUtils.calculateLineDistance(locationInfo.getLocation(), latLng);
                long currentTimeMillis = (System.currentTimeMillis() - queryLongValue) / 1000;
                if (AppUtils.isAppDebug()) {
                    SmartToast.showShort("距离上次获取位置移动了" + calculateLineDistance + "\n距离上次上报时间为" + currentTimeMillis);
                }
                if (calculateLineDistance >= 50.0f || currentTimeMillis >= 110) {
                    LocationHelper.reportAutomation(latLng, Injection.repo().user().getUserId(), aMapLocation.getCity());
                }
            }
            LocationHelper.updateLocationInfo(System.currentTimeMillis(), latLng);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static void reportAutomation(LatLng center, long userId, String city) {
        UserLocationParam userLocationParam = new UserLocationParam();
        userLocationParam.userid = userId;
        userLocationParam.x = center.longitude;
        userLocationParam.y = center.latitude;
        userLocationParam.city = city;
        LHomeLog.i(LocationHelper.class, "param -->" + GsonUtils.toJson(userLocationParam));
        ((ObservableSubscribeProxy) Injection.net().reportAutomation(1, GsonUtils.toJson(userLocationParam)).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.utils.LocationHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LocationHelper.lambda$reportAutomation$0(obj);
            }
        }, new SmartErrorComsumer());
    }

    static /* synthetic */ void lambda$reportAutomation$0(Object obj) throws Exception {
        LHomeLog.i(LocationHelper.class, "reportAutomation enter");
        if (AppUtils.isAppDebug()) {
            SmartToast.showShort("上报自动化结束");
        }
        SharedPreferenceUtil.edit().keepShared("last_automation_report_time_" + Injection.repo().user().getUserId(), System.currentTimeMillis());
    }

    public static void updateLocationInfo(long currentTime, LatLng center) {
        SharedPreferenceUtil.edit().putBean("location_info_" + Injection.repo().user().getUserId(), new LocationInfo(currentTime, center));
    }

    public static AMapLocationListener getLocationListener() {
        return sLocationListener;
    }

    public static boolean hasLocationPermission(Context context) {
        String[] strArr;
        if (context.getApplicationInfo().targetSdkVersion >= 29) {
            if (Build.VERSION.SDK_INT >= 29) {
                strArr = new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_BACKGROUND_LOCATION};
            } else {
                strArr = new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};
            }
        } else {
            strArr = new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};
        }
        return AndPermission.hasPermissions(context, strArr) && Injection.state().isLocationEnabled(context);
    }

    public static void testReportAutomation(LatLng center) {
        SharedPreferenceUtil.queryLongValue("last_automation_report_time_" + Injection.repo().user().getUserId());
        UserLocationParam userLocationParam = new UserLocationParam();
        userLocationParam.userid = Injection.repo().user().getUserId();
        userLocationParam.x = center.longitude;
        userLocationParam.y = center.latitude;
        userLocationParam.city = Constants.CITY;
        LHomeLog.i(LocationHelper.class, "param -->" + GsonUtils.toJson(userLocationParam));
        ((ObservableSubscribeProxy) Injection.net().reportAutomation(1, GsonUtils.toJson(userLocationParam)).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.utils.LocationHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LocationHelper.lambda$testReportAutomation$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    static /* synthetic */ void lambda$testReportAutomation$1(Object obj) throws Exception {
        LHomeLog.i(LocationHelper.class, "reportAutomation enter");
        SmartToast.showShort("上报自动化结束");
        updateLocationInfo(System.currentTimeMillis(), new LatLng(22.223436d, 113.485306d));
        SharedPreferenceUtil.edit().keepShared("last_automation_report_time_" + Injection.repo().user().getUserId(), System.currentTimeMillis());
    }
}