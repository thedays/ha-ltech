package com.ltech.smarthome.ui.camera.config;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.alibaba.fastjson.JSONObject;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActCameraSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.camera.config.ActEzCameraSetting;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.videogo.common.RemoteListContant;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.model.BaseRequset;
import com.videogo.ui.util.EZUtils;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes4.dex */
public class ActEzCameraSetting extends BaseDeviceSetActivity<ActCameraSettingBinding, ActEzCameraSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_camera_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActEzCameraSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEzCameraSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActEzCameraSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActEzCameraSettingVM) this.mViewModel).controlId));
        ((ActEzCameraSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEzCameraSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActEzCameraSettingVM) this.mViewModel).showFlipImageEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEzCameraSetting.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActCameraSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActEzCameraSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ThreadPoolManager.getInstance().execute(new AnonymousClass1());
    }

    /* renamed from: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                final EZDeviceInfo deviceInfo = EZOpenSDK.getInstance().getDeviceInfo(((ActEzCameraSettingVM) ActEzCameraSetting.this.mViewModel).controlDevice.getValue().getDevicesn());
                if (deviceInfo == null || deviceInfo.getCameraInfoList().size() != 1) {
                    return;
                }
                ActEzCameraSetting.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActEzCameraSetting.AnonymousClass1.this.lambda$run$0(deviceInfo);
                    }
                });
                ActEzCameraSetting.this.supportFlip(deviceInfo);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(EZDeviceInfo eZDeviceInfo) {
            ((ActEzCameraSettingVM) ActEzCameraSetting.this.mViewModel).productName.setValue(eZDeviceInfo.getDeviceType());
            ((ActEzCameraSettingVM) ActEzCameraSetting.this.mViewModel).hasProductName.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r2) {
        showLoadingDialog(getString(R.string.saving));
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EZCameraInfo cameraInfoFromDevice = EZUtils.getCameraInfoFromDevice(EZOpenSDK.getInstance().getDeviceInfo(((ActEzCameraSettingVM) ActEzCameraSetting.this.mViewModel).controlDevice.getValue().getDevicesn()), 0);
                    if (cameraInfoFromDevice == null) {
                        return;
                    }
                    ActEzCameraSetting.this.flipImage(cameraInfoFromDevice);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void supportFlip(EZDeviceInfo mDeviceInfo) {
        new OkHttpClient().newCall(new Request.Builder().url("https://open.ys7.com/api/lapp/device/capacity").post(new FormBody.Builder().add(BaseRequset.ACCESSTOKEN, EZOpenSDK.getInstance().getEZAccessToken().getAccessToken()).add("deviceSerial", mDeviceInfo.getDeviceSerial()).build()).build()).enqueue(new AnonymousClass3());
    }

    /* renamed from: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$3, reason: invalid class name */
    class AnonymousClass3 implements Callback {
        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException e) {
        }

        AnonymousClass3() {
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            final boolean z;
            if (response.isSuccessful()) {
                JSONObject parseObject = JSONObject.parseObject(JSONObject.parseObject(response.body().string()).get("data").toString());
                if (parseObject.get("ptz_center_mirror") != null && parseObject.get("ptz_center_mirror").equals("1")) {
                    z = true;
                    ActEzCameraSetting.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActEzCameraSetting.AnonymousClass3.this.lambda$onResponse$0(z);
                        }
                    });
                }
            }
            z = false;
            ActEzCameraSetting.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ActEzCameraSetting.AnonymousClass3.this.lambda$onResponse$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(boolean z) {
            ActEzCameraSetting.this.dismissLoadingDialog();
            if (z) {
                ((ActCameraSettingBinding) ActEzCameraSetting.this.mViewBinding).layoutFlipImage.setVisibility(0);
            } else {
                ((ActCameraSettingBinding) ActEzCameraSetting.this.mViewBinding).layoutFlipImage.setVisibility(8);
            }
        }
    }

    public void flipImage(EZCameraInfo mCameraInfo) {
        new OkHttpClient().newCall(new Request.Builder().url("https://open.ys7.com/api/lapp/device/ptz/mirror").post(new FormBody.Builder().add(BaseRequset.ACCESSTOKEN, EZOpenSDK.getInstance().getEZAccessToken().getAccessToken()).add("deviceSerial", mCameraInfo.getDeviceSerial()).add(RemoteListContant.CHANNELNO_INTENT_KEY, String.valueOf(mCameraInfo.getCameraNo())).add("command", String.valueOf(2)).build()).build()).enqueue(new AnonymousClass4());
    }

    /* renamed from: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$4, reason: invalid class name */
    class AnonymousClass4 implements Callback {
        AnonymousClass4() {
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            final boolean z = response.isSuccessful() && JSONObject.parseObject(response.body().string()).get("code").equals("200");
            ActEzCameraSetting.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ActEzCameraSetting.AnonymousClass4.this.lambda$onResponse$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(boolean z) {
            ActEzCameraSetting.this.dismissLoadingDialog();
            if (z) {
                SmartToast.showShort(R.string.flip_image_success);
            } else {
                SmartToast.showShort(R.string.flip_image_failed);
            }
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException e) {
            ActEzCameraSetting.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSetting$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ActEzCameraSetting.AnonymousClass4.this.lambda$onFailure$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFailure$1() {
            ActEzCameraSetting.this.dismissLoadingDialog();
            SmartToast.showShort(R.string.flip_image_failed);
        }
    }
}