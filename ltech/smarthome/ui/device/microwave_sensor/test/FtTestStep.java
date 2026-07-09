package com.ltech.smarthome.ui.device.microwave_sensor.test;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtSensorTestStepBinding;
import com.ltech.smarthome.model.bean.Step;
import com.ltech.smarthome.ui.config.OnStepsIntroductionListener;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.utils.MediaUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.TextClickUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.CountDownProgressBar;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtTestStep extends VMFragment<FtSensorTestStepBinding, ActWaveSensorTestVM> {
    private int curStep;
    private boolean isPause;
    private OnStepsIntroductionListener mOnStepsIntroductionListener;
    private Step mStep;
    private ActWaveSensorTestVM mViewModel;
    private boolean testStart;
    private boolean testSuccess;
    CountDownTimer timer = new CountDownTimer(20000, 1000) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep.3
        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            String str = Math.min((millisUntilFinished / 1000) + 1, 20L) + FtTestStep.this.getString(R.string.sec);
            if (FtTestStep.this.mViewBinding != null) {
                if (FtTestStep.this.curStep >= FtTestStep.this.totalSteps || !FtTestStep.this.testSuccess) {
                    ((FtSensorTestStepBinding) FtTestStep.this.mViewBinding).btPrevious.setText(FtTestStep.this.getString(R.string.change_position_with_time, str));
                } else {
                    ((FtSensorTestStepBinding) FtTestStep.this.mViewBinding).btNext.setText(FtTestStep.this.getString(R.string.next_with_time, str));
                }
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (FtTestStep.this.curStep < FtTestStep.this.totalSteps && FtTestStep.this.testSuccess) {
                FtTestStep.this.startTest(true);
            } else {
                FtTestStep.this.startTest(false);
            }
        }
    };
    private int totalSteps;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_sensor_test_step;
    }

    public static FtTestStep create(Step step, int curStep, int totalSteps) {
        FtTestStep ftTestStep = new FtTestStep();
        Bundle bundle = new Bundle();
        bundle.putString("data", GsonUtils.toJson(step));
        bundle.putInt("curStep", curStep);
        bundle.putInt("totalSteps", totalSteps);
        ftTestStep.setArguments(bundle);
        return ftTestStep;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.mViewModel = (ActWaveSensorTestVM) new ViewModelProvider(requireActivity()).get(ActWaveSensorTestVM.class);
        ((FtSensorTestStepBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtTestStep.this.lambda$initView$0((View) obj);
            }
        }));
        ((FtSensorTestStepBinding) this.mViewBinding).tvSensitivity.setText(getResources().getStringArray(R.array.wave_sensor_sensitivity)[this.mViewModel.sensitivity]);
        TextClickUtil.setTextClick(requireContext(), ((FtSensorTestStepBinding) this.mViewBinding).tvFailTip, new ClickableSpan() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(FtTestStep.this.getResources().getColor(R.color.color_text_blue));
                ds.setUnderlineText(true);
                ds.clearShadowLayer();
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                FtTestStep.this.mViewModel.goSensitivitySetting();
            }
        }, getString(R.string.sensitivity), getString(R.string.test_fail_tip, getString(R.string.sensitivity)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.bt_next) {
            if (this.curStep == this.totalSteps) {
                showSuccessMesssage();
                return;
            } else {
                startTest(true);
                return;
            }
        }
        if (id == R.id.bt_previous) {
            startTest(false);
        } else {
            if (id != R.id.layout_set_sensitivity) {
                return;
            }
            this.mViewModel.goSensitivitySetting();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        this.mViewModel.testResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtTestStep.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        this.mViewModel.refreshSentivity.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtTestStep.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (this.testStart) {
            this.testSuccess = bool.booleanValue();
            if (this.mViewBinding == 0 || !bool.booleanValue()) {
                return;
            }
            ((FtSensorTestStepBinding) this.mViewBinding).barCountdown.end();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        this.timer.start();
        ((FtSensorTestStepBinding) this.mViewBinding).tvSensitivity.setText(getResources().getStringArray(R.array.wave_sensor_sensitivity)[this.mViewModel.sensitivity]);
    }

    private void showSuccessMesssage() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.tips), getString(R.string.test_over_success)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.save), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showSuccessMesssage$4;
                lambda$showSuccessMesssage$4 = FtTestStep.this.lambda$showSuccessMesssage$4(baseDialog, view);
                return lambda$showSuccessMesssage$4;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showSuccessMesssage$6;
                lambda$showSuccessMesssage$6 = FtTestStep.this.lambda$showSuccessMesssage$6(baseDialog, view);
                return lambda$showSuccessMesssage$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSuccessMesssage$4(BaseDialog baseDialog, View view) {
        CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(getActivity(), 2, this.mViewModel.testMode, this.mViewModel.sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtTestStep.this.lambda$showSuccessMesssage$3((Boolean) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSuccessMesssage$3(Boolean bool) {
        this.mViewModel.finishEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSuccessMesssage$6(BaseDialog baseDialog, View view) {
        CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(getActivity(), 3, this.mViewModel.testMode, this.mViewModel.sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtTestStep.this.lambda$showSuccessMesssage$5((Boolean) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSuccessMesssage$5(Boolean bool) {
        this.mViewModel.finishEvent.call();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            this.mStep = (Step) GsonUtils.fromJson(getArguments().getString("data", ""), new TypeToken<Step>(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep.2
            }.getType());
            this.curStep = getArguments().getInt("curStep", 0);
            int i = getArguments().getInt("totalSteps", 0);
            this.totalSteps = i;
            if (i == 0) {
                if (getActivity() != null) {
                    getActivity().finish();
                    return;
                }
                return;
            } else if (this.mStep != null) {
                bindView(this.curStep, i);
            }
        }
        this.isPause = false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.timer.cancel();
        this.isPause = true;
    }

    private void bindView(int curStep, int totalSteps) {
        ArrayList arrayList = new ArrayList();
        if (totalSteps == 2) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12_1));
        } else if (totalSteps == 3) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_123_1));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_123_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_123_3));
        } else if (totalSteps == 4) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_1));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_3));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_4));
        }
        if (totalSteps > 1) {
            if (curStep == 1) {
                ((FtSensorTestStepBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(0)).intValue());
            } else if (curStep == 2) {
                ((FtSensorTestStepBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(1)).intValue());
            } else if (curStep == 3) {
                ((FtSensorTestStepBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(2)).intValue());
            } else if (curStep == 4) {
                ((FtSensorTestStepBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(3)).intValue());
            }
        }
        if (this.isPause) {
            return;
        }
        startStepTest();
    }

    private void startStepTest() {
        this.testStart = true;
        this.testSuccess = false;
        this.timer.cancel();
        if (this.mStep != null) {
            ((FtSensorTestStepBinding) this.mViewBinding).tvSteps.setText(this.mStep.getTitle());
            ((FtSensorTestStepBinding) this.mViewBinding).ivStep.setImageResource(this.mStep.getImg());
        }
        ((FtSensorTestStepBinding) this.mViewBinding).btPrevious.setVisibility(8);
        ((FtSensorTestStepBinding) this.mViewBinding).btNext.setVisibility(8);
        ((FtSensorTestStepBinding) this.mViewBinding).layoutSuccessTip.setVisibility(8);
        ((FtSensorTestStepBinding) this.mViewBinding).tvFailTip.setVisibility(8);
        ((FtSensorTestStepBinding) this.mViewBinding).barCountdown.setSecondColor(getResources().getColor(R.color.color_text_blue));
        ((FtSensorTestStepBinding) this.mViewBinding).barCountdown.setDuration(10000, new CountDownProgressBar.OnFinishListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.CountDownProgressBar.OnFinishListener
            public final void onFinish() {
                FtTestStep.this.lambda$startStepTest$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startStepTest$7() {
        this.testStart = false;
        if (this.mViewBinding != 0) {
            ((FtSensorTestStepBinding) this.mViewBinding).btPrevious.setVisibility(0);
            ((FtSensorTestStepBinding) this.mViewBinding).btNext.setVisibility(0);
            ((FtSensorTestStepBinding) this.mViewBinding).btNext.setBackgroundResource(R.drawable.shape_light_blue_bt);
            if (this.curStep == this.totalSteps) {
                ((FtSensorTestStepBinding) this.mViewBinding).btNext.setText(R.string.test_finish);
            } else {
                ((FtSensorTestStepBinding) this.mViewBinding).btPrevious.setText(R.string.change_position);
            }
            this.timer.start();
            if (this.testSuccess) {
                if (this.curStep == this.totalSteps) {
                    ((FtSensorTestStepBinding) this.mViewBinding).btNext.setText(R.string.test_finish);
                    if (this.mViewModel.testMode == 1) {
                        ((FtSensorTestStepBinding) this.mViewBinding).tvSteps.setText(R.string.test_success_mode1);
                    } else if (this.mViewModel.testMode == 2) {
                        ((FtSensorTestStepBinding) this.mViewBinding).tvSteps.setText(R.string.test_success_mode2);
                    } else if (this.mViewModel.testMode == 4) {
                        ((FtSensorTestStepBinding) this.mViewBinding).tvSteps.setText(R.string.test_success_mode4);
                    }
                } else {
                    ((FtSensorTestStepBinding) this.mViewBinding).btNext.setText(R.string.next);
                    if (this.mViewModel.testMode == 2) {
                        ((FtSensorTestStepBinding) this.mViewBinding).tvSteps.setText(R.string.test_success_mode2);
                    }
                }
                ((FtSensorTestStepBinding) this.mViewBinding).barCountdown.setSecondColor(getResources().getColor(R.color.color_text_blue));
                ((FtSensorTestStepBinding) this.mViewBinding).ivStep.setImageResource(R.mipmap.sensor_test_icon_success);
                ((FtSensorTestStepBinding) this.mViewBinding).layoutSuccessTip.setVisibility(0);
                ((FtSensorTestStepBinding) this.mViewBinding).tvFailTip.setVisibility(8);
                MediaUtils.get(requireContext()).playMedia(R.raw.add_success);
                return;
            }
            if (this.curStep == this.totalSteps) {
                ((FtSensorTestStepBinding) this.mViewBinding).btNext.setText(R.string.test_finish);
            } else {
                ((FtSensorTestStepBinding) this.mViewBinding).btNext.setText(R.string.test_skip);
            }
            ((FtSensorTestStepBinding) this.mViewBinding).barCountdown.setSecondColor(getResources().getColor(R.color.progress_red));
            ((FtSensorTestStepBinding) this.mViewBinding).tvSteps.setText(R.string.test_fail_mode);
            ((FtSensorTestStepBinding) this.mViewBinding).ivStep.setImageResource(R.mipmap.sensor_test_icon_fail);
            ((FtSensorTestStepBinding) this.mViewBinding).layoutSuccessTip.setVisibility(8);
            ((FtSensorTestStepBinding) this.mViewBinding).tvFailTip.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTest(final boolean next) {
        if (getActivity() != null) {
            showLoadingDialog("");
        }
        this.mViewModel.getCmdHelper().setTestMode(requireActivity(), 1, this.mViewModel.testMode, this.mViewModel.sensitivity, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtTestStep.this.lambda$startTest$8(next, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startTest$8(boolean z, Boolean bool) {
        dismissLoadingDialog();
        if (!bool.booleanValue()) {
            SmartToast.showCenterShort(getString(R.string.execute_fail));
            return;
        }
        if (z) {
            OnStepsIntroductionListener onStepsIntroductionListener = this.mOnStepsIntroductionListener;
            if (onStepsIntroductionListener != null) {
                onStepsIntroductionListener.next();
                return;
            }
            return;
        }
        startStepTest();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepsIntroductionListener) {
            this.mOnStepsIntroductionListener = (OnStepsIntroductionListener) context;
        }
    }
}