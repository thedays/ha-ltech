package com.ltech.smarthome.ui.home;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Lifecycle;
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
import com.ltech.smarthome.databinding.ActHomePositionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.map.GeocodeResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class ActHomePosition extends BaseNormalActivity<ActHomePositionBinding> implements LocationSource, AMapLocationListener {
    private String address;
    private String city;
    private double latitude;
    private double longitude;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private Marker marker;
    private AMapLocationClient mlocationClient;
    private long placeId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_position;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActHomePositionBinding) this.mViewBinding).mapView.onCreate(savedInstanceState);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((ActHomePositionBinding) this.mViewBinding).mapView.onSaveInstanceState(outState);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        ((ActHomePositionBinding) this.mViewBinding).mapView.onResume();
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        ((ActHomePositionBinding) this.mViewBinding).mapView.onPause();
        super.onPause();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().setMyLocationEnabled(false);
        ((ActHomePositionBinding) this.mViewBinding).mapView.onDestroy();
        AMapLocationClient aMapLocationClient = this.mlocationClient;
        if (aMapLocationClient != null) {
            aMapLocationClient.onDestroy();
        }
        super.onDestroy();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.home_position));
        long longExtra = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.placeId = longExtra;
        if (longExtra != -1) {
            setEditString(getString(R.string.save));
        }
        ((ActHomePositionBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.home.ActHomePosition$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActHomePosition.this.lambda$initView$0((View) obj);
            }
        }));
        initMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.layout_search) {
            return;
        }
        NavUtils.destination(ActSearchPosition.class).withString(Constants.CITY, this.city).withString(Constants.ADDRESS, this.address).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.placeId == -1) {
            Intent intent = new Intent();
            intent.putExtra(Constants.ADDRESS, this.address);
            intent.putExtra(Constants.LATITUDE, this.latitude);
            intent.putExtra(Constants.LONGITUDE, this.longitude);
            setResult(5000, intent);
        }
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.placeId != -1) {
            if (TextUtils.isEmpty(this.address)) {
                SmartToast.showShort(R.string.select_home_position);
            } else {
                ((ObservableSubscribeProxy) Injection.net().updatePlaceLocation(this.placeId, this.address, this.latitude, this.longitude).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomePosition$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActHomePosition.this.lambda$edit$1((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActHomePosition$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        ActHomePosition.this.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomePosition$$ExternalSyntheticLambda4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActHomePosition.this.lambda$edit$2(obj);
                    }
                }, new SmartErrorComsumer());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Object obj) throws Exception {
        Injection.repo().home().updatePlaceLocation(this.placeId, this.address, this.latitude, this.longitude);
        finishActivity();
    }

    private void initMap() {
        ((ActHomePositionBinding) this.mViewBinding).mapView.post(new Runnable() { // from class: com.ltech.smarthome.ui.home.ActHomePosition$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActHomePosition.this.lambda$initMap$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMap$3() {
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().setLocationSource(this);
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(1);
        myLocationStyle.strokeColor(0);
        myLocationStyle.radiusFillColor(0);
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().setMyLocationStyle(myLocationStyle);
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().setOnMapClickListener(new AMap.OnMapClickListener() { // from class: com.ltech.smarthome.ui.home.ActHomePosition.1
            @Override // com.amap.api.maps2d.AMap.OnMapClickListener
            public void onMapClick(LatLng latLng) {
                ActHomePosition.this.setLocationData(latLng);
                ActHomePosition.this.addMarker();
            }
        });
        ((ActHomePositionBinding) this.mViewBinding).ivLocalMy.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.home.ActHomePosition.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Location myLocation = ((ActHomePositionBinding) ActHomePosition.this.mViewBinding).mapView.getMap().getMyLocation();
                if (myLocation != null) {
                    ActHomePosition.this.refreshLocationView(myLocation);
                }
            }
        });
    }

    private void setLocationData(LatLng latLng, String a2, String c2) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        if (StringUtils.isTrimEmpty(a2)) {
            Injection.net().geocodeByMapApi(this.longitude + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + this.latitude, "200").enqueue(new Callback<GeocodeResponse>() { // from class: com.ltech.smarthome.ui.home.ActHomePosition.3
                @Override // retrofit2.Callback
                public void onFailure(Call<GeocodeResponse> call, Throwable t) {
                }

                @Override // retrofit2.Callback
                public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                    if (response.body() == null || response.body().getRegeocode() == null) {
                        return;
                    }
                    ActHomePosition.this.address = response.body().getRegeocode().getFormatted_address();
                    if (response.body().getRegeocode().getAddressComponent() != null) {
                        ActHomePosition.this.city = response.body().getRegeocode().getAddressComponent().getCity();
                    }
                    if (ActHomePosition.this.mViewBinding != null) {
                        ((ActHomePositionBinding) ActHomePosition.this.mViewBinding).tvAddress.setText(StringUtils.isTrimEmpty(ActHomePosition.this.address) ? ActHomePosition.this.city : ActHomePosition.this.address);
                    }
                }
            });
        } else {
            this.address = a2;
            if (!StringUtils.isTrimEmpty(c2)) {
                this.city = c2;
            }
            if (this.mViewBinding != 0) {
                ((ActHomePositionBinding) this.mViewBinding).tvAddress.setText(StringUtils.isTrimEmpty(this.address) ? this.city : this.address);
            }
        }
        refreshLocationView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLocationData(LatLng latLng) {
        setLocationData(latLng, null, null);
    }

    private void refreshLocationView() {
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(this.latitude, this.longitude), 20.0f, 30.0f, 0.0f)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshLocationView(Location myLocation) {
        ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 20.0f, 30.0f, 0.0f)));
        Injection.net().geocodeByMapApi(myLocation.getLongitude() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + myLocation.getLatitude(), "200").enqueue(new Callback<GeocodeResponse>() { // from class: com.ltech.smarthome.ui.home.ActHomePosition.4
            @Override // retrofit2.Callback
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                if (response.body() == null || response.body().getRegeocode() == null) {
                    return;
                }
                ActHomePosition.this.address = response.body().getRegeocode().getFormatted_address();
                if (response.body().getRegeocode().getAddressComponent() != null) {
                    ActHomePosition.this.city = response.body().getRegeocode().getAddressComponent().getCity();
                }
                if (ActHomePosition.this.mViewBinding != null) {
                    ((ActHomePositionBinding) ActHomePosition.this.mViewBinding).tvAddress.setText(StringUtils.isTrimEmpty(ActHomePosition.this.city) ? "" : ActHomePosition.this.city);
                }
            }
        });
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
            this.marker = ((ActHomePositionBinding) this.mViewBinding).mapView.getMap().addMarker(new MarkerOptions().position(new LatLng(this.latitude, this.longitude)));
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
            this.city = amapLocation.getCity();
            this.address = amapLocation.getAddress();
            if (this.mViewBinding != 0) {
                ((ActHomePositionBinding) this.mViewBinding).tvAddress.setText(amapLocation.getAddress());
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