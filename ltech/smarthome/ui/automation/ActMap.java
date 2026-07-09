package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActMapBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.auto.ReachLeaveParam;
import com.ltech.smarthome.net.response.map.GeocodeResponse;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.home.ActSearchPosition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LocationHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.StepSeekBar;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class ActMap extends BaseNormalActivity<ActMapBinding> implements ISelectCondition, LocationSource, AMapLocationListener {
    private String address;
    private String city;
    private double latitude;
    private boolean leaveSomewhere;
    private double longitude;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private Marker marker;
    private AMapLocationClient mlocationClient;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_map;
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectStatusResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectStatusResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActMapBinding) this.mViewBinding).mapView.onCreate(savedInstanceState);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((ActMapBinding) this.mViewBinding).mapView.onSaveInstanceState(outState);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        ((ActMapBinding) this.mViewBinding).mapView.onResume();
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        ((ActMapBinding) this.mViewBinding).mapView.onPause();
        super.onPause();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActMapBinding) this.mViewBinding).mapView.getMap().setMyLocationEnabled(false);
        ((ActMapBinding) this.mViewBinding).mapView.onDestroy();
        AMapLocationClient aMapLocationClient = this.mlocationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.onDestroy();
        }
        super.onDestroy();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.leaveSomewhere = getIntent().getBooleanExtra(Constants.LEAVE_SOMEWHERE, false);
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        setTitle(getString(this.leaveSomewhere ? R.string.leave_somewhere : R.string.reach_somewhere));
        ((ActMapBinding) this.mViewBinding).stepSeekBar.setOnStepChangedListener(new StepSeekBar.OnStepChangedListener() { // from class: com.ltech.smarthome.ui.automation.ActMap$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.StepSeekBar.OnStepChangedListener
            public final void onColorChanged(int i, boolean z) {
                ActMap.this.lambda$initView$0(i, z);
            }
        });
        ((ActMapBinding) this.mViewBinding).tvRange.setText(String.format(Locale.US, "%d%s", Integer.valueOf((((ActMapBinding) this.mViewBinding).stepSeekBar.getCurStep() + 1) * 100), getString(R.string.meter)));
        ((ActMapBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActMap$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMap.this.lambda$initView$1((View) obj);
            }
        }));
        initMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, boolean z) {
        ((ActMapBinding) this.mViewBinding).tvRange.setText(String.format(Locale.US, "%d%s", Integer.valueOf((i + 1) * 100), getString(R.string.meter)));
        refreshLocationView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        if (view.getId() != R.id.layout_search) {
            return;
        }
        NavUtils.destination(ActSearchPosition.class).withString(Constants.CITY, this.city).withString(Constants.ADDRESS, this.address).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (TextUtils.isEmpty(this.address)) {
            SmartToast.showShort(getString(R.string.please_choose));
            return;
        }
        ReachLeaveParam reachLeaveParam = new ReachLeaveParam();
        reachLeaveParam.x = this.longitude;
        reachLeaveParam.y = this.latitude;
        reachLeaveParam.city = this.address;
        reachLeaveParam.type = this.leaveSomewhere ? 1 : 2;
        reachLeaveParam.range = (((ActMapBinding) this.mViewBinding).stepSeekBar.getCurStep() + 1) * 100;
        reachLeaveParam.userid = getIntent().getLongExtra(Constants.USER_ID, -1L);
        SceneHelper.instance().conditionParam = reachLeaveParam;
        SceneHelper.instance().conditionParamType = 1;
        LocationHelper.updateLocationInfo(System.currentTimeMillis(), new LatLng(this.latitude, this.longitude));
        setSelectResult(this);
    }

    private void initMap() {
        ((ActMapBinding) this.mViewBinding).mapView.post(new Runnable() { // from class: com.ltech.smarthome.ui.automation.ActMap$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ActMap.this.lambda$initMap$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMap$2() {
        ((ActMapBinding) this.mViewBinding).mapView.getMap().setLocationSource(this);
        ((ActMapBinding) this.mViewBinding).mapView.getMap().setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(1);
        myLocationStyle.strokeColor(0);
        myLocationStyle.radiusFillColor(0);
        ((ActMapBinding) this.mViewBinding).mapView.getMap().setMyLocationStyle(myLocationStyle);
        ((ActMapBinding) this.mViewBinding).mapView.getMap().setOnMapClickListener(new AMap.OnMapClickListener() { // from class: com.ltech.smarthome.ui.automation.ActMap.1
            @Override // com.amap.api.maps2d.AMap.OnMapClickListener
            public void onMapClick(LatLng latLng) {
                ActMap.this.setLocationData(latLng);
                ActMap.this.addMarker();
            }
        });
    }

    private void setLocationData(LatLng latLng, String a2, String c2) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        if (StringUtils.isTrimEmpty(a2)) {
            Injection.net().geocodeByMapApi(this.longitude + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + this.latitude, "200").enqueue(new Callback<GeocodeResponse>() { // from class: com.ltech.smarthome.ui.automation.ActMap.2
                @Override // retrofit2.Callback
                public void onFailure(Call<GeocodeResponse> call, Throwable t) {
                }

                @Override // retrofit2.Callback
                public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                    if (response.body() == null || response.body().getRegeocode() == null) {
                        return;
                    }
                    ActMap.this.address = response.body().getRegeocode().getFormatted_address();
                    if (response.body().getRegeocode().getAddressComponent() != null) {
                        ActMap.this.city = response.body().getRegeocode().getAddressComponent().getCity();
                    }
                    if (ActMap.this.mViewBinding != null) {
                        ((ActMapBinding) ActMap.this.mViewBinding).tvLocationName.setText(StringUtils.isTrimEmpty(ActMap.this.address) ? ActMap.this.city : ActMap.this.address);
                        ((ActMapBinding) ActMap.this.mViewBinding).tvAddress.setText(StringUtils.isTrimEmpty(ActMap.this.address) ? ActMap.this.city : ActMap.this.address);
                    }
                }
            });
        } else {
            this.address = a2;
            if (!StringUtils.isTrimEmpty(c2)) {
                this.city = c2;
            }
            if (this.mViewBinding != 0) {
                ((ActMapBinding) this.mViewBinding).tvAddress.setText(StringUtils.isTrimEmpty(this.address) ? this.city : this.address);
            }
        }
        refreshLocationView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLocationData(LatLng latLng) {
        setLocationData(latLng, null, null);
    }

    private void refreshLocationView() {
        ((ActMapBinding) this.mViewBinding).mapView.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(this.latitude, this.longitude), 20.0f, 30.0f, 0.0f)));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (5000 != resultCode || data == null) {
            return;
        }
        setLocationData(new LatLng(data.getDoubleExtra(Constants.LATITUDE, -1.0d), data.getDoubleExtra(Constants.LONGITUDE, -1.0d)), data.getStringExtra(Constants.ADDRESS), data.getStringExtra(Constants.CITY));
        addMarker();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMarker() {
        Marker marker = this.marker;
        if (marker != null) {
            marker.setPosition(new LatLng(this.latitude, this.longitude));
        } else {
            this.marker = ((ActMapBinding) this.mViewBinding).mapView.getMap().addMarker(new MarkerOptions().position(new LatLng(this.latitude, this.longitude)));
        }
    }

    @Override // com.amap.api.maps2d.LocationSource
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        this.mListener = onLocationChangedListener;
        if (this.mlocationClient == null) {
            try {
                AMapLocationClient.updatePrivacyShow(this.activity, true, true);
                AMapLocationClient.updatePrivacyAgree(this.activity, true);
                this.mlocationClient = new AMapLocationClient(this);
                this.mLocationOption = new AMapLocationClientOption();
                this.mlocationClient.setLocationListener(this);
                this.mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                this.mLocationOption.setOnceLocation(true);
                this.mlocationClient.setLocationOption(this.mLocationOption);
                this.mlocationClient.startLocation();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override // com.amap.api.maps2d.LocationSource
    public void deactivate() {
        this.mListener = null;
        AMapLocationClient aMapLocationClient = this.mlocationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            this.mlocationClient.onDestroy();
        }
        this.mlocationClient = null;
    }

    @Override // com.amap.api.location.AMapLocationListener
    public void onLocationChanged(AMapLocation amapLocation) {
        if (this.mListener == null || amapLocation == null) {
            return;
        }
        if (amapLocation.getErrorCode() == 0) {
            this.latitude = amapLocation.getLatitude();
            this.longitude = amapLocation.getLongitude();
            this.mListener.onLocationChanged(amapLocation);
            this.address = amapLocation.getAddress();
            this.city = amapLocation.getCity();
            if (this.mViewBinding != 0) {
                ((ActMapBinding) this.mViewBinding).tvLocationName.setText(this.address);
                ((ActMapBinding) this.mViewBinding).tvAddress.setText(this.address);
            }
            if (TextUtils.isEmpty(this.city)) {
                setLocationData(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                return;
            } else {
                refreshLocationView();
                return;
            }
        }
        Log.e("AmapErr", "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo());
    }
}