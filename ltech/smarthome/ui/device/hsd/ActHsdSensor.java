package com.ltech.smarthome.ui.device.hsd;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActHsdSensorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.HsdExtParam;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.doorsensor.ActDoorSensorVM;
import com.ltech.smarthome.ui.device.microwave_sensor.ActEnvironmentLog;
import com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting;
import com.ltech.smarthome.ui.log.ActDeviceLog;
import com.ltech.smarthome.ui.log.LogHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.product_agreement.bean.WaveSensorState;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActHsdSensor extends BaseControlActivity<ActHsdSensorBinding, ActDoorSensorVM> {
    private HsdExtParam extParam = new HsdExtParam();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_hsd_sensor;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActDoorSensorVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActDoorSensorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActHsdSensorBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensor$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActHsdSensor.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.bt_smart /* 2131296483 */:
                NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withString(Constants.AUTOMATION_NAME, getString(R.string.automation) + 1).withLong(Constants.CONTROL_ID, ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getId()).navigation(this);
                break;
            case R.id.layout_battery /* 2131297363 */:
                NavUtils.destination(ActBattery.class).withLong(Constants.CONTROL_ID, ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getId()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
                break;
            case R.id.layout_sense_setting /* 2131297627 */:
                if (!isOwnerOrManager()) {
                    showNoPermissionDialog();
                    break;
                } else {
                    NavUtils.destination(ActSenseSetting.class).withLong(Constants.CONTROL_ID, ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getId()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
                    break;
                }
            case R.id.tv_environment_log /* 2131298628 */:
                Device value = ((ActDoorSensorVM) this.mViewModel).controlObject.getValue();
                if (value instanceof Device) {
                    NavUtils.destination(ActEnvironmentLog.class).withLong("device_id", value.getDeviceId()).navigation(this);
                    break;
                }
                break;
            case R.id.tv_sense_record /* 2131298951 */:
                Device value2 = ((ActDoorSensorVM) this.mViewModel).controlObject.getValue();
                if (value2 instanceof Device) {
                    Device device = value2;
                    NavUtils.destination(ActDeviceLog.class).withLong("device_id", device.getDeviceId()).withString(Constants.PRODUCT_ID, device.getProductId()).navigation(this);
                    break;
                }
                break;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDoorSensorVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensor$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHsdSensor.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        if (device == null) {
            return;
        }
        setTitle(device.getDeviceName());
        if (!TextUtils.isEmpty(device.getExtParam())) {
            HsdExtParam hsdExtParam = (HsdExtParam) device.getExtParam(HsdExtParam.class);
            this.extParam = hsdExtParam;
            if (hsdExtParam.getBattery() != -1) {
                ((ActHsdSensorBinding) this.mViewBinding).tvBattery.setText(this.extParam.getBattery() + "%");
            }
            ((ActHsdSensorBinding) this.mViewBinding).tvTime.setText(LogHelper.instance().convertDate(this.extParam.getUpdateSensorTime(), "MM/dd HH:mm"));
        }
        changeSensorView(device.getDeviceState().getWaveSensorState());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActDoorSensorVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActDoorSensorVM) this.mViewModel).controlId));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActDoorSensorVM) this.mViewModel).controlObject.getValue());
    }

    private void changeSensorView(WaveSensorState sensorState) {
        ((ActHsdSensorBinding) this.mViewBinding).tvState.setText(getResources().getStringArray(R.array.wave_sensor_state)[sensorState.getCurState()]);
        ((ActHsdSensorBinding) this.mViewBinding).spreadviewSensor.setAnimate(false);
        ((ActHsdSensorBinding) this.mViewBinding).spreadviewSensor.requestLayout();
        ((ActHsdSensorBinding) this.mViewBinding).spreadviewSensor.changeSpreadColor(getResources().getIntArray(R.array.wave_sensor_color)[sensorState.getCurState()]);
        ((ActHsdSensorBinding) this.mViewBinding).spreadviewSensor.setAnimate(true);
    }

    public void refreshData(long deviceId) {
        if (deviceId == ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getDeviceId()) {
            refreshDeviceInfo(deviceId);
        }
    }

    private void refreshDeviceInfo(long deviceId) {
        ((ObservableSubscribeProxy) Injection.net().queryDevice(deviceId).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensor$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHsdSensor.this.lambda$refreshDeviceInfo$2((ListDeviceResponse.RowsBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshDeviceInfo$2(ListDeviceResponse.RowsBean rowsBean) throws Exception {
        Device value = ((ActDoorSensorVM) this.mViewModel).controlObject.getValue();
        value.setExtParam(rowsBean.getParamext());
        Injection.repo().device().saveDevice(value);
        ((ActDoorSensorVM) this.mViewModel).controlObject.setValue(value);
    }
}