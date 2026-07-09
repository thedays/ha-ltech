package com.ltech.smarthome.ui.device.doorsensor;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActDoorSensorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.log.ActDeviceLog;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActDoorSensor extends BaseControlActivity<ActDoorSensorBinding, ActDoorSensorVM> {
    private ValueAnimator animator;
    private boolean isOpen = true;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_door_sensor;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActDoorSensorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDoorSensorVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        getLogAdapter().bindToRecyclerView(((ActDoorSensorBinding) this.mViewBinding).rvLog);
        ((ActDoorSensorBinding) this.mViewBinding).rvLog.setLayoutManager(new LinearLayoutManager(this));
        ((ActDoorSensorBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActDoorSensor.this.lambda$initView$0((View) obj);
            }
        }));
        if (((ActDoorSensorVM) this.mViewModel).hasOnlineGatewaySupport()) {
            ((ActDoorSensorBinding) this.mViewBinding).tvGatewayState.setVisibility(4);
        } else {
            ((ActDoorSensorBinding) this.mViewBinding).tvGatewayState.setVisibility(0);
        }
        ((ActDoorSensorBinding) this.mViewBinding).tvBattery.setText("--");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id != R.id.layout_auto) {
            if (id != R.id.tv_bottom) {
                return;
            }
            NavUtils.destination(ActDeviceLog.class).withLong("device_id", ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getDeviceId()).withString(Constants.PRODUCT_ID, ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getProductId()).navigation(this);
        } else {
            NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withString(Constants.AUTOMATION_NAME, getString(R.string.automation) + 1).withLong(Constants.CONTROL_ID, ((ActDoorSensorVM) this.mViewModel).controlId).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActDoorSensorVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDoorSensor.this.lambda$startObserve$1((Device) obj);
            }
        });
        MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.BatteryStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActDoorSensor.this.lambda$startObserve$2(responseMsg);
            }
        });
        this.queryLogResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDoorSensor.this.lambda$startObserve$3((Boolean) obj);
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
            try {
                int optInt = new JSONObject(device.getExtParam()).optInt("battery", -1);
                if (optInt != -1) {
                    ((ActDoorSensorBinding) this.mViewBinding).tvBattery.setText(optInt + "%");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        startMove(device.getStatus() == 2, new int[0]);
        refreshDeviceInfo(device.getDeviceId());
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryWaveSensorState(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(ResponseMsg responseMsg) {
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) == ((BleParam) ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getParam(BleParam.class)).getUnicastAddress()) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            ((ActDoorSensorBinding) this.mViewBinding).tvBattery.setText(parseInt + "%");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (bool.booleanValue() && !this.logList.isEmpty()) {
            ((ActDoorSensorBinding) this.mViewBinding).groupLog.setVisibility(0);
            ((ActDoorSensorBinding) this.mViewBinding).tvNoLog.setVisibility(8);
            String date = getDate(this.logList.get(0).getCreatetime());
            ((ActDoorSensorBinding) this.mViewBinding).tvDate.setText(date);
            if (this.logList.size() > 2 && !getDate(this.logList.get(2).getCreatetime()).equals(date)) {
                this.logList.remove(2);
            }
            if (this.logList.size() > 1 && !getDate(this.logList.get(1).getCreatetime()).equals(date)) {
                this.logList.remove(1);
            }
            getLogAdapter().replaceData(this.logList);
            return;
        }
        ((ActDoorSensorBinding) this.mViewBinding).groupLog.setVisibility(8);
        ((ActDoorSensorBinding) this.mViewBinding).tvNoLog.setVisibility(0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActDoorSensorVM) this.mViewModel).controlObject.getValue());
    }

    private String getDate(String time) {
        String formatToday = DateUtils.getFormatToday("yyyy-MM-dd");
        String substring = time.substring(0, time.indexOf(" "));
        return formatToday.equals(substring) ? getString(R.string.today) : substring;
    }

    private void startMove(boolean open, int... duration) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (open) {
            this.animator = ValueAnimator.ofFloat(0.0f, SizeUtils.dp2px(20.0f));
        } else {
            this.animator = ValueAnimator.ofFloat(SizeUtils.dp2px(20.0f), 0.0f);
        }
        this.animator.setDuration(duration.length > 0 ? duration[0] : 0L);
        this.animator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ActDoorSensor.this.lambda$startMove$4(valueAnimator2);
            }
        });
        this.animator.start();
        ((ActDoorSensorBinding) this.mViewBinding).tvState.setText(open ? R.string.door_open : R.string.door_close);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startMove$4(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        ((ActDoorSensorBinding) this.mViewBinding).ivDoorLeft.setTranslationX(-floatValue);
        ((ActDoorSensorBinding) this.mViewBinding).ivDoorRight.setTranslationX(floatValue);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActDoorSensorVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActDoorSensorVM) this.mViewModel).controlId));
    }

    public void refreshData(long deviceId) {
        if (deviceId == ((ActDoorSensorVM) this.mViewModel).controlObject.getValue().getDeviceId()) {
            refreshDeviceInfo(deviceId);
        }
    }

    private void refreshDeviceInfo(final long deviceId) {
        ((ObservableSubscribeProxy) Injection.net().queryDevice(deviceId).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDoorSensor.this.lambda$refreshDeviceInfo$5(deviceId, (ListDeviceResponse.RowsBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshDeviceInfo$5(long j, ListDeviceResponse.RowsBean rowsBean) throws Exception {
        Device value = ((ActDoorSensorVM) this.mViewModel).controlObject.getValue();
        value.setStatus(rowsBean.getStatus());
        Injection.repo().device().saveDevice(value);
        if (value.getStatus() == 1) {
            startMove(false, new int[0]);
        } else {
            startMove(true, new int[0]);
        }
        queryLog(j, 3, 1);
    }
}