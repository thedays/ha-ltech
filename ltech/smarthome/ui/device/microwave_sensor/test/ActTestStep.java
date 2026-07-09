package com.ltech.smarthome.ui.device.microwave_sensor.test;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActTestStepBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.config.OnStepsIntroductionListener;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.DeviceAddStepsHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTestStep extends VMActivity<ActTestStepBinding, ActWaveSensorTestVM> implements OnStepsIntroductionListener {
    private Object controlObject;
    private List<Fragment> fragments;
    private int mCurPostion = 0;

    @Override // com.ltech.smarthome.ui.config.OnStepsIntroductionListener
    public void previous() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_test_step;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActWaveSensorTestVM) this.mViewModel).getSensitivity();
        ((ActWaveSensorTestVM) this.mViewModel).testMode = getIntent().getIntExtra(Constants.TEST_MODE, 1);
        initByTestMode(((ActWaveSensorTestVM) this.mViewModel).testMode);
        initViewPager();
        this.controlObject = WaveSensorHelper.instance().controlObject;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        startObjectObserve();
        ((ActWaveSensorTestVM) this.mViewModel).finishEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestStep$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActTestStep.this.lambda$startObserve$0((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        setResult(5006);
        finishActivity();
    }

    private void startObjectObserve() {
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            MessageManager.getInstance().setWaveSensorStatusCallBack(new MessageManager.WaveSensorStatusCallBack() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestStep$$ExternalSyntheticLambda2
                @Override // com.smart.message.MessageManager.WaveSensorStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActTestStep.this.lambda$startObjectObserve$1(group, responseMsg);
                }
            });
        } else {
            final Device device = (Device) obj;
            if (device != null) {
                MessageManager.getInstance().setWaveSensorStatusCallBack(new MessageManager.WaveSensorStatusCallBack() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestStep$$ExternalSyntheticLambda3
                    @Override // com.smart.message.MessageManager.WaveSensorStatusCallBack
                    public final void onDataReceive(ResponseMsg responseMsg) {
                        ActTestStep.this.lambda$startObjectObserve$2(device, responseMsg);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$1(Group group, ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null && parseInt == ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress()) {
                if (Integer.parseInt(responseMsg.getResData().substring(14, 16), 16) == 1) {
                    ((ActWaveSensorTestVM) this.mViewModel).testResult.setValue(true);
                    return;
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$2(Device device, ResponseMsg responseMsg) {
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) == ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() && Integer.parseInt(responseMsg.getResData().substring(14, 16), 16) == 1) {
            ((ActWaveSensorTestVM) this.mViewModel).testResult.setValue(true);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(this.activity, 3, ((ActWaveSensorTestVM) this.mViewModel).testMode, ((ActWaveSensorTestVM) this.mViewModel).sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestStep$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTestStep.this.lambda$back$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$back$3(Boolean bool) {
        super.back();
    }

    private void initByTestMode(int mode) {
        int i;
        if (mode == 1) {
            this.fragments = new DeviceAddStepsHelper().withNormalStep(getString(R.string.test_mode1_step), R.mipmap.sensor_test1_step).getSensorData();
            i = R.string.test_mode_1;
        } else if (mode == 2) {
            this.fragments = new DeviceAddStepsHelper().withNormalStep(getString(R.string.test_mode2_step1), R.mipmap.sensor_test2_step1).withNormalStep(getString(R.string.test_mode2_step2), R.mipmap.sensor_test2_step2).withNormalStep(getString(R.string.test_mode2_step3), R.mipmap.sensor_test2_step3).getSensorData();
            i = R.string.test_mode_2;
        } else if (mode == 3) {
            this.fragments = new DeviceAddStepsHelper().withNormalStep(getString(R.string.test_mode3_step), R.mipmap.sensor_test3_step).withNormalStep(getString(R.string.test_mode3_step), R.mipmap.sensor_test3_step).withNormalStep(getString(R.string.test_mode3_step), R.mipmap.sensor_test3_step).getSensorData();
            i = R.string.test_mode_3;
        } else if (mode != 4) {
            i = 0;
        } else {
            this.fragments = new DeviceAddStepsHelper().withNormalStep(getString(R.string.test_mode2_step2), R.mipmap.sensor_test2_step2).getSensorData();
            i = R.string.test_mode;
        }
        setTitle(getString(i));
    }

    private void initViewPager() {
        ((ActTestStepBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestStep.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return (Fragment) ActTestStep.this.fragments.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ActTestStep.this.fragments.size();
            }
        });
        ((ActTestStepBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
        ((ActTestStepBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestStep.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ActTestStep.this.mCurPostion = position;
            }
        });
    }

    @Override // com.ltech.smarthome.ui.config.OnStepsIntroductionListener
    public void next() {
        if (this.mCurPostion == this.fragments.size() - 1) {
            return;
        }
        this.mCurPostion++;
        ((ActTestStepBinding) this.mViewBinding).viewpager.setCurrentItem(this.mCurPostion, true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 5007 || data == null) {
            return;
        }
        ((ActWaveSensorTestVM) this.mViewModel).sensitivity = data.getIntExtra(Constants.SENSITIVITY, ((ActWaveSensorTestVM) this.mViewModel).sensitivity);
        ((ActWaveSensorTestVM) this.mViewModel).refreshSentivity.setValue(Integer.valueOf(((ActWaveSensorTestVM) this.mViewModel).sensitivity));
    }
}