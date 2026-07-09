package com.ltech.smarthome.ui.device.microwave_sensor.test;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSensorNobobyTestBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.MediaUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.TextClickUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.WaveSensorState;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes4.dex */
public class ActSensorNobodyTest extends VMActivity<ActSensorNobobyTestBinding, ActWaveSensorTestVM> {
    private Timer cmdTimer;
    private boolean testStart;
    CountDownTimer timer = new CountDownTimer(120000, 1000) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest.3
        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            if (!ActSensorNobodyTest.this.testStart || ActSensorNobodyTest.this.mViewBinding == null) {
                return;
            }
            ((ActSensorNobobyTestBinding) ActSensorNobodyTest.this.mViewBinding).seekbar.setProgress(((int) (120000 - millisUntilFinished)) / 1000);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (!ActSensorNobodyTest.this.testStart || ActSensorNobodyTest.this.mViewBinding == null) {
                return;
            }
            ((ActSensorNobobyTestBinding) ActSensorNobodyTest.this.mViewBinding).seekbar.setProgress(((ActSensorNobobyTestBinding) ActSensorNobodyTest.this.mViewBinding).seekbar.getMax());
            ((ActSensorNobobyTestBinding) ActSensorNobodyTest.this.mViewBinding).tvFinish.setTextColor(ActSensorNobodyTest.this.getResources().getColor(R.color.sensor_blue));
            MediaUtils.get(ActSensorNobodyTest.this.activity).playMedia(R.raw.add_success);
            ((ActWaveSensorTestVM) ActSensorNobodyTest.this.mViewModel).testResult.setValue(true);
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sensor_noboby_test;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.nobody_test));
        setBackImage(R.mipmap.icon_back);
        ((ActWaveSensorTestVM) this.mViewModel).getSensitivity();
        ((ActSensorNobobyTestBinding) this.mViewBinding).seekbar.setEnabled(false);
        ((ActSensorNobobyTestBinding) this.mViewBinding).layoutProgress.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSensorNobodyTest.this.lambda$initView$0(view);
            }
        });
        startCmdTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (((ActWaveSensorTestVM) this.mViewModel).testResult.getValue().booleanValue()) {
            NavUtils.destination(ActTestModeMain.class).withInt(Constants.SELECT_POSITION, ((ActWaveSensorTestVM) this.mViewModel).sensitivity).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        if (WaveSensorHelper.instance().controlObject instanceof Group) {
            final Group group = (Group) WaveSensorHelper.instance().controlObject;
            refreshSensorState(group.getGroupState().getWaveSensorState());
            if (group != null) {
                MessageManager.getInstance().setWaveSensorStatusCallBack(new MessageManager.WaveSensorStatusCallBack() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$$ExternalSyntheticLambda3
                    @Override // com.smart.message.MessageManager.WaveSensorStatusCallBack
                    public final void onDataReceive(ResponseMsg responseMsg) {
                        ActSensorNobodyTest.this.lambda$startObserve$1(group, responseMsg);
                    }
                });
                return;
            }
            return;
        }
        final Device device = (Device) WaveSensorHelper.instance().controlObject;
        refreshSensorState(device.getDeviceState().getWaveSensorState());
        if (device != null) {
            MessageManager.getInstance().setWaveSensorStatusCallBack(new MessageManager.WaveSensorStatusCallBack() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$$ExternalSyntheticLambda4
                @Override // com.smart.message.MessageManager.WaveSensorStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActSensorNobodyTest.this.lambda$startObserve$2(device, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Group group, ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null && parseInt == ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress()) {
                ((ActWaveSensorTestVM) this.mViewModel).convertState(responseMsg.getResData(), group.getGroupState().getWaveSensorState());
                refreshSensorState(group.getGroupState().getWaveSensorState());
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device, ResponseMsg responseMsg) {
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) == ((BleParam) device.getParam(BleParam.class)).getUnicastAddress()) {
            ((ActWaveSensorTestVM) this.mViewModel).convertState(responseMsg.getResData(), device.getDeviceState().getWaveSensorState());
            refreshSensorState(device.getDeviceState().getWaveSensorState());
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(this.activity, 3, 4, ((ActWaveSensorTestVM) this.mViewModel).sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSensorNobodyTest.this.lambda$back$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$back$3(Boolean bool) {
        super.back();
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.cmdTimer.cancel();
        this.cmdTimer = null;
    }

    private void refreshSensorState(WaveSensorState sensorState) {
        if (this.mViewBinding != 0) {
            ((ActSensorNobobyTestBinding) this.mViewBinding).setStateOn(Boolean.valueOf(sensorState.isEnable()));
            if (sensorState.isEnable()) {
                ((ActSensorNobobyTestBinding) this.mViewBinding).tvState.setText(getResources().getStringArray(R.array.wave_sensor_state)[sensorState.getCurState()]);
                ((ActSensorNobobyTestBinding) this.mViewBinding).spreadviewSensor.setAnimate(false);
                ((ActSensorNobobyTestBinding) this.mViewBinding).spreadviewSensor.requestLayout();
                ((ActSensorNobobyTestBinding) this.mViewBinding).spreadviewSensor.changeSpreadColor(getResources().getIntArray(R.array.wave_sensor_color)[sensorState.getCurState()]);
                ((ActSensorNobobyTestBinding) this.mViewBinding).spreadviewSensor.setAnimate(true);
                setTipText();
                if (((ActWaveSensorTestVM) this.mViewModel).testResult.getValue().booleanValue()) {
                    return;
                }
                if (sensorState.getCurState() == 0) {
                    if (this.testStart) {
                        return;
                    }
                    this.testStart = true;
                    setTipText();
                    ((ActSensorNobobyTestBinding) this.mViewBinding).layoutProgress.setVisibility(0);
                    ((ActSensorNobobyTestBinding) this.mViewBinding).seekbar.setProgress(0);
                    this.timer.start();
                    return;
                }
                if (this.testStart) {
                    this.testStart = false;
                    setTipText();
                    ((ActSensorNobobyTestBinding) this.mViewBinding).layoutProgress.setVisibility(8);
                    ((ActSensorNobobyTestBinding) this.mViewBinding).seekbar.setProgress(0);
                    this.timer.cancel();
                    return;
                }
                return;
            }
            ((ActSensorNobobyTestBinding) this.mViewBinding).tvState.setText(getString(R.string.state_close));
        }
    }

    private void setTipText() {
        String string;
        String string2 = getString(R.string.current_sensitivity, new Object[]{getResources().getStringArray(R.array.wave_sensor_sensitivity)[((ActWaveSensorTestVM) this.mViewModel).sensitivity]});
        if (this.testStart) {
            ((ActSensorNobobyTestBinding) this.mViewBinding).tvTestTipTitle.setVisibility(8);
            string = getString(R.string.state_nobody_tip, new Object[]{string2});
        } else {
            ((ActSensorNobobyTestBinding) this.mViewBinding).tvTestTipTitle.setVisibility(0);
            string = getString(R.string.state_body_tip, new Object[]{string2, "25%"});
        }
        TextClickUtil.setTextClick(this, ((ActSensorNobobyTestBinding) this.mViewBinding).tvTestTip, new ClickableSpan() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ActSensorNobodyTest.this.getResources().getColor(R.color.color_text_blue));
                ds.setUnderlineText(true);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                ((ActWaveSensorTestVM) ActSensorNobodyTest.this.mViewModel).goSensitivitySetting();
            }
        }, string2, string);
    }

    /* renamed from: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$2, reason: invalid class name */
    class AnonymousClass2 extends TimerTask {
        static /* synthetic */ void lambda$run$0(Boolean bool) {
        }

        AnonymousClass2() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(ActSensorNobodyTest.this.activity, 1, 4, ((ActWaveSensorTestVM) ActSensorNobodyTest.this.mViewModel).sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSensorNobodyTest.AnonymousClass2.lambda$run$0((Boolean) obj);
                }
            });
        }
    }

    private void startCmdTimer() {
        Timer timer = new Timer();
        this.cmdTimer = timer;
        timer.schedule(new AnonymousClass2(), 120000L, 120000L);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5006) {
            finishActivity();
            return;
        }
        if (resultCode != 5007 || data == null) {
            return;
        }
        ((ActWaveSensorTestVM) this.mViewModel).sensitivity = data.getIntExtra(Constants.SENSITIVITY, ((ActWaveSensorTestVM) this.mViewModel).sensitivity);
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(this, 1, 4, ((ActWaveSensorTestVM) this.mViewModel).sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSensorNobodyTest.this.lambda$onActivityResult$4((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$4(Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            setTipText();
        } else {
            SmartToast.showCenterShort(getString(R.string.execute_fail));
        }
    }
}