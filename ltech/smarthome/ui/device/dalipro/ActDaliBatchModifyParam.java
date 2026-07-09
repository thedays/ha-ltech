package com.ltech.smarthome.ui.device.dalipro;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliBatchModifyParamBinding;
import com.ltech.smarthome.ltnfc.utils.BrightUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam;
import com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParamVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.DaliTextSeekBarView;
import com.ltech.smarthome.view.DaliTextSeekBarViewNew;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.CenterDaliBatchModifyParamDialog;
import com.ltech.smarthome.view.dialog.MultiSelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDaliBatchModifyParam extends VMActivity<ActDaliBatchModifyParamBinding, ActDaliBatchModifyParamVM> {
    private int actionProgress;
    private CenterDaliBatchModifyParamDialog centerDaliBatchModifyParamDialog;
    private int deviceProgress;
    private long placeId;
    private int min = 2700;
    private int max = 6500;
    private int fadeTimePosition = 1;
    private int fadeTimeCount = 1;
    private List<ActDaliBatchModifyParamVM.BatchSaveItem> errorList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_batch_modify_param;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.batch_modify_param));
        initData();
        initDimCurve();
        initDimRange();
        initDimFadeTime();
        initCtRange();
        initLightOn();
        initFailureStatus();
    }

    private void initData() {
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(j);
                this.placeId = deviceByDeviceId.getPlaceId();
                ((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.add(deviceByDeviceId);
            }
        }
        ((ActDaliBatchModifyParamVM) this.mViewModel).floorList = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        ((ActDaliBatchModifyParamVM) this.mViewModel).roomList = Injection.repo().home().getRoomList(this.placeId);
        for (int i = 0; i < 6; i++) {
            ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.add(Integer.valueOf(i));
        }
    }

    private void initDimCurve() {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLinear.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initDimCurve$0(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLog.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initDimCurve$1(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLog.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).ivLinearLog.setImageResource(R.mipmap.pic_curve_log);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimCurve$0(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLinear.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLog.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).ivLinearLog.setImageResource(R.mipmap.pic_curve_linear);
        setSeekbarList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimCurve$1(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLinear.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLog.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).ivLinearLog.setImageResource(R.mipmap.pic_curve_log);
        setSeekbarList();
    }

    private void initDimRange() {
        setSeekbarList();
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.setValueAlignRight();
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.setValueAlignRight();
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.setOnProgressChangeListener(new DaliTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.view.DaliTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initDimRange$2(i, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.setOnProgressChangeListener(new DaliTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.view.DaliTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initDimRange$3(i, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.setProgress(255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimRange$2(int i, boolean z) {
        if (i > ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.getProgress()) {
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.setProgress(((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.getProgress());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimRange$3(int i, boolean z) {
        if (i < ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.getProgress()) {
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.setProgress(((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.getProgress());
        }
    }

    private void setSeekbarList() {
        ArrayList arrayList;
        if (((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLog.isChecked()) {
            arrayList = new ArrayList(BrightUtils.getLogPercent().values());
        } else {
            arrayList = new ArrayList(BrightUtils.getLinnerPercent().values());
        }
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.setList(arrayList);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.refreshValue();
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.setList(arrayList);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.refreshValue();
    }

    private void initDimFadeTime() {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.setList(Arrays.asList(getResources().getStringArray(R.array.fade_time)));
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.setValueAlpha(0.5f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.setOnProgressChangeListener(new DaliTextSeekBarViewNew.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.view.DaliTextSeekBarViewNew.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initDimFadeTime$4(i, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.setProgress(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimFadeTime$4(int i, boolean z) {
        if (i != 0) {
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvExtendFadeTime.setAlpha(0.5f);
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeValue.setAlpha(0.5f);
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeX.setAlpha(0.5f);
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeTimes.setAlpha(0.5f);
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeEqual.setAlpha(0.5f);
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeTotal.setAlpha(0.5f);
            ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.setValueAlpha(1.0f);
            return;
        }
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvExtendFadeTime.setAlpha(1.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeValue.setAlpha(1.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeX.setAlpha(1.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeTimes.setAlpha(1.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeEqual.setAlpha(1.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeTotal.setAlpha(1.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.setValueAlpha(0.5f);
    }

    private void initCtRange() {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtDefault.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initCtRange$5(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtCustom.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initCtRange$6(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtDefault.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setProgressDrawableId(R.drawable.style_seekbar_gray);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setEnabled(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvKLeft.setText(this.min + "K");
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvKRight.setText(this.max + "K");
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setRange(1000.0f, 10000.0f, 50.0f);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setProgress((float) this.min, (float) this.max);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setOnRangeChangedListener(new OnRangeChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam.1
            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                int i = (int) leftValue;
                if (LightUtils.getStepK(i, 50) >= 1000) {
                    ActDaliBatchModifyParam.this.min = LightUtils.getStepK(i, 50);
                }
                int i2 = (int) rightValue;
                if (LightUtils.getStepK(i2, 50) >= 1000) {
                    ActDaliBatchModifyParam.this.max = LightUtils.getStepK(i2, 50);
                }
                ((ActDaliBatchModifyParamBinding) ActDaliBatchModifyParam.this.mViewBinding).tvKLeft.setText(ActDaliBatchModifyParam.this.min + "K");
                ((ActDaliBatchModifyParamBinding) ActDaliBatchModifyParam.this.mViewBinding).tvKRight.setText(ActDaliBatchModifyParam.this.max + "K");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCtRange$5(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtDefault.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setProgressDrawableId(R.drawable.style_seekbar_gray);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCtRange$6(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtCustom.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setProgressDrawableId(R.mipmap.pic_ct);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).sbBrt.setEnabled(true);
    }

    private void initLightOn() {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.initAll(null);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initLightOn$7(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnNotLight.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda23
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initLightOn$8(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnMemory.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda24
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initLightOn$9(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnCustom.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda25
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initLightOn$10(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLightOn$7(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnNotLight.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnMemory.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLightOn$8(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnNotLight.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnMemory.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLightOn$9(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnNotLight.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnMemory.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLightOn$10(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnNotLight.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnMemory.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnCustom.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.setEnabled(true);
    }

    private void initFailureStatus() {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.initAll(null);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initFailureStatus$11(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureNotLight.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initFailureStatus$12(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureMemory.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initFailureStatus$13(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureCustom.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActDaliBatchModifyParam.this.lambda$initFailureStatus$14(radioImageTextView, z);
            }
        });
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFailureStatus$11(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureNotLight.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureMemory.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFailureStatus$12(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureNotLight.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureMemory.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFailureStatus$13(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureNotLight.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureMemory.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureCustom.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFailureStatus$14(RadioImageTextView radioImageTextView, boolean z) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureNotLight.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureMemory.setCheck(false);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureCustom.setCheck(true);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.setEnabled(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDaliBatchModifyParamVM) this.mViewModel).selectModifyParamEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliBatchModifyParam.this.lambda$startObserve$16((Void) obj);
            }
        });
        ((ActDaliBatchModifyParamVM) this.mViewModel).saveEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliBatchModifyParam.this.lambda$startObserve$17((Void) obj);
            }
        });
        ((ActDaliBatchModifyParamVM) this.mViewModel).selectExtendFadeTimeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliBatchModifyParam.this.lambda$startObserve$19((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$16(Void r10) {
        ArrayList arrayList = new ArrayList();
        MultiSelectListDialog.SelectItem selectItem = new MultiSelectListDialog.SelectItem(getString(R.string.dimming_curve), ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.contains(0));
        MultiSelectListDialog.SelectItem selectItem2 = new MultiSelectListDialog.SelectItem(getString(R.string.dimming_range), ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.contains(1));
        MultiSelectListDialog.SelectItem selectItem3 = new MultiSelectListDialog.SelectItem(getString(R.string.dimming_fade_time), ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.contains(2));
        MultiSelectListDialog.SelectItem selectItem4 = new MultiSelectListDialog.SelectItem(getString(R.string.ct_range), ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.contains(3));
        MultiSelectListDialog.SelectItem selectItem5 = new MultiSelectListDialog.SelectItem(getString(R.string.light_on_state), ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.contains(4));
        MultiSelectListDialog.SelectItem selectItem6 = new MultiSelectListDialog.SelectItem(getString(R.string.failure_state), ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.contains(5));
        arrayList.add(selectItem);
        arrayList.add(selectItem2);
        arrayList.add(selectItem3);
        arrayList.add(selectItem4);
        arrayList.add(selectItem5);
        arrayList.add(selectItem6);
        MultiSelectListDialog.asDefault().setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setTitle(getString(R.string.select_modify_param)).setSelectList(arrayList).setMultiSelectListener(new MultiSelectListDialog.IMultiSelectListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.MultiSelectListDialog.IMultiSelectListener
            public final void onSelect(List list) {
                ActDaliBatchModifyParam.this.lambda$startObserve$15(list);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(List list) {
        ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews = list;
        setSelectView(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$17(Void r1) {
        save();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$19(Void r5) {
        if (((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.getProgress() > 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < 17; i++) {
            arrayList.add(i + "");
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.dimming_fade_time)).setMinList(Arrays.asList(getResources().getStringArray(R.array.custom_fade_time))).setSecList(arrayList).withUnit(true).setMinUnit("x").setMinCenter(true).setSelectMinPosition(this.fadeTimePosition).setSelectSecPosition(this.fadeTimeCount - 1).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i2, int i3) {
                ActDaliBatchModifyParam.this.lambda$startObserve$18(i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$18(int i, int i2) {
        this.fadeTimePosition = i;
        int i3 = i2 + 1;
        this.fadeTimeCount = i3;
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeValue.setText(getResources().getStringArray(R.array.custom_fade_time)[i]);
        AppCompatTextView appCompatTextView = ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeTimes;
        StringBuilder sb = new StringBuilder();
        sb.append(i3);
        String str = "";
        sb.append("");
        appCompatTextView.setText(sb.toString());
        if (i == 0) {
            str = "0ms";
        } else if (i == 1) {
            str = (i3 * 100) + "ms";
        } else if (i == 2) {
            str = i3 + NotifyType.SOUND;
        } else if (i == 3) {
            str = (i3 * 10) + NotifyType.SOUND;
        } else if (i == 4) {
            str = i3 + Constants.MIN;
        }
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).tvFadeTimeTotal.setText(str);
    }

    private void setSelectView(List<Integer> selectPositions) {
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).layoutDimCurve.setVisibility(selectPositions.contains(0) ? 0 : 8);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).layoutDimRange.setVisibility(selectPositions.contains(1) ? 0 : 8);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).layoutFadeTime.setVisibility(selectPositions.contains(2) ? 0 : 8);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).layoutCtRange.setVisibility(selectPositions.contains(3) ? 0 : 8);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).layoutLightOnState.setVisibility(selectPositions.contains(4) ? 0 : 8);
        ((ActDaliBatchModifyParamBinding) this.mViewBinding).layoutFailureState.setVisibility(selectPositions.contains(5) ? 0 : 8);
    }

    public void save() {
        this.actionProgress = 0;
        this.deviceProgress = 0;
        if (((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.size() <= 0 || ((ActDaliBatchModifyParamVM) this.mViewModel).selectViews.size() <= 0) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < ((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.size(); i++) {
            ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem = new ActDaliBatchModifyParamVM.BatchSaveItem(i, ((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.get(i));
            arrayList.add(batchSaveItem);
            arrayList2.add(batchDevice(batchSaveItem));
        }
        CenterDaliBatchModifyParamDialog confirmAction = CenterDaliBatchModifyParamDialog.asDefault().setFloorList(((ActDaliBatchModifyParamVM) this.mViewModel).floorList).setRoomList(((ActDaliBatchModifyParamVM) this.mViewModel).roomList).setBatchSaveItemList(arrayList).setDeviceList(((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices).setTitle(getString(R.string.batch_saving, new Object[]{String.valueOf(1), String.valueOf(((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.size())})).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliBatchModifyParam.this.lambda$save$20((Integer) obj);
            }
        });
        this.centerDaliBatchModifyParamDialog = confirmAction;
        confirmAction.showDialog(this);
        ((ObservableSubscribeProxy) Observable.concat(arrayList2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new io.reactivex.Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam.2
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer i2) {
                CenterDaliBatchModifyParamDialog centerDaliBatchModifyParamDialog = ActDaliBatchModifyParam.this.centerDaliBatchModifyParamDialog;
                ActDaliBatchModifyParam actDaliBatchModifyParam = ActDaliBatchModifyParam.this;
                centerDaliBatchModifyParamDialog.changeTitle(actDaliBatchModifyParam.getString(R.string.batch_saving, new Object[]{String.valueOf(actDaliBatchModifyParam.deviceProgress + 1), String.valueOf(((ActDaliBatchModifyParamVM) ActDaliBatchModifyParam.this.mViewModel).selectDevices.size())}));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ActDaliBatchModifyParam.this.centerDaliBatchModifyParamDialog.setProgress(new ActDaliBatchModifyParamVM.StateParam(((ActDaliBatchModifyParamVM.BatchSaveItem) arrayList.get(r2.size() - 1)).getDevice().getId(), ActDaliBatchModifyParamVM.StateParam.STATE_ALL_COMPLETED, 1.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$20(Integer num) {
        saveError();
    }

    private void saveError() {
        ArrayList arrayList = new ArrayList();
        this.deviceProgress = ((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.size() - this.errorList.size();
        final int size = ((ActDaliBatchModifyParamVM) this.mViewModel).selectDevices.size() - this.errorList.size();
        final ArrayList arrayList2 = new ArrayList(this.errorList);
        ArrayList arrayList3 = new ArrayList();
        for (int i = 0; i < arrayList2.size(); i++) {
            arrayList3.add(((ActDaliBatchModifyParamVM.BatchSaveItem) arrayList2.get(i)).getDevice());
            this.centerDaliBatchModifyParamDialog.setProgress(new ActDaliBatchModifyParamVM.StateParam(((ActDaliBatchModifyParamVM.BatchSaveItem) arrayList2.get(i)).getDevice().getId(), ActDaliBatchModifyParamVM.StateParam.STATE_PENDING, 0.0f));
            arrayList.add(batchDevice((ActDaliBatchModifyParamVM.BatchSaveItem) arrayList2.get(i)));
        }
        this.centerDaliBatchModifyParamDialog.setDeviceList(arrayList3);
        this.errorList.clear();
        ((ObservableSubscribeProxy) Observable.concat(arrayList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new io.reactivex.Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam.3
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer i2) {
                CenterDaliBatchModifyParamDialog centerDaliBatchModifyParamDialog = ActDaliBatchModifyParam.this.centerDaliBatchModifyParamDialog;
                ActDaliBatchModifyParam actDaliBatchModifyParam = ActDaliBatchModifyParam.this;
                centerDaliBatchModifyParamDialog.changeTitle(actDaliBatchModifyParam.getString(R.string.batch_saving, new Object[]{String.valueOf((actDaliBatchModifyParam.deviceProgress - size) + 1), String.valueOf(arrayList2.size())}));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ActDaliBatchModifyParam.this.centerDaliBatchModifyParamDialog.setProgress(new ActDaliBatchModifyParamVM.StateParam(((ActDaliBatchModifyParamVM.BatchSaveItem) arrayList2.get(r2.size() - 1)).getDevice().getId(), ActDaliBatchModifyParamVM.StateParam.STATE_ALL_COMPLETED, 1.0f));
            }
        });
    }

    private Observable<Integer> batchDevice(final ActDaliBatchModifyParamVM.BatchSaveItem item) {
        return Observable.create(new ObservableOnSubscribe<Integer>() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam.4
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                ActDaliBatchModifyParam.this.actionProgress = 0;
                item.setEmitter(emitter);
                emitter.onNext(Integer.valueOf(item.getId()));
                ActDaliBatchModifyParam actDaliBatchModifyParam = ActDaliBatchModifyParam.this;
                actDaliBatchModifyParam.executeSave(item, ((ActDaliBatchModifyParamVM) actDaliBatchModifyParam.mViewModel).selectViews);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeSave(ActDaliBatchModifyParamVM.BatchSaveItem item, List<Integer> selectViews) {
        int intValue = selectViews.get(this.actionProgress).intValue();
        if (intValue == 0) {
            setDimCurve(item, selectViews);
            return;
        }
        if (intValue == 1) {
            setDimRange(item, selectViews);
            return;
        }
        if (intValue == 2) {
            setFadeTime(item, selectViews);
            return;
        }
        if (intValue == 3) {
            setKRange(item, selectViews);
        } else if (intValue == 4) {
            setLightOnState(item, selectViews);
        } else {
            if (intValue != 5) {
                return;
            }
            setFailureState(item, selectViews);
        }
    }

    private void actionNext(ActDaliBatchModifyParamVM.BatchSaveItem item, List<Integer> selectViews) {
        this.actionProgress++;
        this.centerDaliBatchModifyParamDialog.setProgress(new ActDaliBatchModifyParamVM.StateParam(item.getDevice().getId(), ActDaliBatchModifyParamVM.StateParam.STATE_WORKING, this.actionProgress / selectViews.size()));
        if (this.actionProgress < selectViews.size()) {
            executeSave(item, selectViews);
            return;
        }
        this.deviceProgress++;
        this.centerDaliBatchModifyParamDialog.setProgress(new ActDaliBatchModifyParamVM.StateParam(item.getDevice().getId(), ActDaliBatchModifyParamVM.StateParam.STATE_COMPLETED, 1.0f));
        item.getEmitter().onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorAndActionNextDevice(ActDaliBatchModifyParamVM.BatchSaveItem item) {
        this.centerDaliBatchModifyParamDialog.setProgress(new ActDaliBatchModifyParamVM.StateParam(item.getDevice().getId(), ActDaliBatchModifyParamVM.StateParam.STATE_ERROR, 0.0f));
        this.errorList.add(item);
        this.deviceProgress++;
        item.getEmitter().onComplete();
    }

    private void setDimCurve(final ActDaliBatchModifyParamVM.BatchSaveItem item, final List<Integer> selectViews) {
        ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setDimCurve(this, ((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLinear.isChecked() ? 2 : 1, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliBatchModifyParam.this.lambda$setDimCurve$21(item, selectViews, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDimCurve$21(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            actionNext(batchSaveItem, list);
        } else {
            setErrorAndActionNextDevice(batchSaveItem);
        }
    }

    private void setDimRange(final ActDaliBatchModifyParamVM.BatchSaveItem item, final List<Integer> selectViews) {
        int progress = ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMinBtr.getProgress();
        int progress2 = ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarMaxBtr.getProgress();
        ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setDimRange(this, progress, progress2, progress, progress2, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliBatchModifyParam.this.lambda$setDimRange$22(item, selectViews, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDimRange$22(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            actionNext(batchSaveItem, list);
        } else {
            setErrorAndActionNextDevice(batchSaveItem);
        }
    }

    private void setFadeTime(final ActDaliBatchModifyParamVM.BatchSaveItem item, final List<Integer> selectViews) {
        ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setFadeTime(this, ((ActDaliBatchModifyParamBinding) this.mViewBinding).seekbarFadeTime.getProgress(), this.fadeTimePosition, this.fadeTimeCount, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda26
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliBatchModifyParam.this.lambda$setFadeTime$23(item, selectViews, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFadeTime$23(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            actionNext(batchSaveItem, list);
        } else {
            setErrorAndActionNextDevice(batchSaveItem);
        }
    }

    private void setKRange(final ActDaliBatchModifyParamVM.BatchSaveItem item, final List<Integer> selectViews) {
        final int i;
        final int i2;
        if (((ActDaliBatchModifyParamBinding) this.mViewBinding).radioCtDefault.isChecked()) {
            i2 = 6500;
            i = 2700;
        } else {
            i = this.min;
            i2 = this.max;
        }
        if (item.getDevice().getProductId().equals(ProductId.ID_BLE_LIGHT_CT)) {
            ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setKRange(this, i, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliBatchModifyParam.this.lambda$setKRange$25(item, i2, i, selectViews, (Boolean) obj);
                }
            });
        } else {
            actionNext(item, selectViews);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setKRange$25(final ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, final int i, final int i2, final List list, Boolean bool) {
        if (bool.booleanValue()) {
            ((ObservableSubscribeProxy) Injection.net().updateCtRange(batchSaveItem.getDevice().getDeviceId(), i, i2).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliBatchModifyParam.this.lambda$setKRange$24(batchSaveItem, i2, i, list, obj);
                }
            }, new AnonymousClass5(batchSaveItem));
        } else {
            setErrorAndActionNextDevice(batchSaveItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setKRange$24(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, int i, int i2, List list, Object obj) throws Exception {
        batchSaveItem.getDevice().setMinkelvin(i);
        batchSaveItem.getDevice().setMaxkelvin(i2);
        Injection.repo().device().saveDevice(batchSaveItem.getDevice());
        actionNext(batchSaveItem, list);
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$5, reason: invalid class name */
    class AnonymousClass5 implements Consumer<Throwable> {
        final /* synthetic */ ActDaliBatchModifyParamVM.BatchSaveItem val$item;

        AnonymousClass5(final ActDaliBatchModifyParamVM.BatchSaveItem val$item) {
            this.val$item = val$item;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Throwable throwable) throws Exception {
            LightAssistant lightCmdHelper = ((ActDaliBatchModifyParamVM) ActDaliBatchModifyParam.this.mViewModel).getLightCmdHelper(this.val$item.getDevice());
            ActDaliBatchModifyParam actDaliBatchModifyParam = ActDaliBatchModifyParam.this;
            int minkelvin = this.val$item.getDevice().getMinkelvin();
            int maxkelvin = this.val$item.getDevice().getMaxkelvin();
            final ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem = this.val$item;
            lightCmdHelper.setKRange(actDaliBatchModifyParam, minkelvin, maxkelvin, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$5$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliBatchModifyParam.AnonymousClass5.this.lambda$accept$0(batchSaveItem, (Boolean) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, Boolean bool) {
            if (!Injection.state().isConnectOuterNet()) {
                SmartToast.showShort(R.string.no_network);
            }
            ActDaliBatchModifyParam.this.setErrorAndActionNextDevice(batchSaveItem);
        }
    }

    private void setLightOnState(final ActDaliBatchModifyParamVM.BatchSaveItem item, final List<Integer> selectViews) {
        int i;
        int i2;
        if (((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnDefault.isChecked()) {
            i = 1;
        } else if (((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnNotLight.isChecked()) {
            i = 2;
        } else {
            i = 3;
            if (!((ActDaliBatchModifyParamBinding) this.mViewBinding).radioLightOnMemory.isChecked()) {
                int wyBrt = ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.getWyBrt();
                CmdAssistant.getLightCmdAssistant(item.getDevice(), new int[0]).sendDimBrtD1Has1to9(this, LightUtils.brt2ProgressHasBelowZero(wyBrt), true);
                if (DaliProHelper.convertDaliType(item.getDevice()) == 2) {
                    CmdAssistant.getLightCmdAssistant(item.getDevice(), new int[0]).sendCW(this, 255 - ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.getWy(), true);
                } else if (DaliProHelper.convertDaliType(item.getDevice()) == 3) {
                    CmdAssistant.getLightCmdAssistant(item.getDevice(), new int[0]).sendRgb(this, ((ActDaliBatchModifyParamBinding) this.mViewBinding).lightOnActionView.getColor(), true);
                }
                i2 = wyBrt;
                i = 4;
                ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setOnStateBatch(this, i, i2, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda27
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActDaliBatchModifyParam.this.lambda$setLightOnState$26(item, selectViews, (ResponseMsg) obj);
                    }
                });
            }
        }
        i2 = 0;
        ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setOnStateBatch(this, i, i2, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda27
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliBatchModifyParam.this.lambda$setLightOnState$26(item, selectViews, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLightOnState$26(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            actionNext(batchSaveItem, list);
        } else {
            setErrorAndActionNextDevice(batchSaveItem);
        }
    }

    private void setFailureState(final ActDaliBatchModifyParamVM.BatchSaveItem item, final List<Integer> selectViews) {
        int i;
        int i2;
        if (((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureDefault.isChecked()) {
            i = 1;
        } else if (((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureNotLight.isChecked()) {
            i = 2;
        } else {
            i = 3;
            if (!((ActDaliBatchModifyParamBinding) this.mViewBinding).radioFailureMemory.isChecked()) {
                int wyBrt = ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.getWyBrt();
                CmdAssistant.getLightCmdAssistant(item.getDevice(), new int[0]).sendDimBrtD1Has1to9(this, LightUtils.brt2ProgressHasBelowZero(wyBrt), true);
                if (DaliProHelper.convertDaliType(item.getDevice()) == 2) {
                    CmdAssistant.getLightCmdAssistant(item.getDevice(), new int[0]).sendCW(this, 255 - ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.getWy(), true);
                } else if (DaliProHelper.convertDaliType(item.getDevice()) == 3) {
                    CmdAssistant.getLightCmdAssistant(item.getDevice(), new int[0]).sendRgb(this, ((ActDaliBatchModifyParamBinding) this.mViewBinding).failureActionView.getColor(), true);
                }
                i2 = wyBrt;
                i = 4;
                ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setFailStateBatch(this, i, i2, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActDaliBatchModifyParam.this.lambda$setFailureState$27(item, selectViews, (ResponseMsg) obj);
                    }
                });
            }
        }
        i2 = 0;
        ((ActDaliBatchModifyParamVM) this.mViewModel).getLightCmdHelper(item.getDevice()).setFailStateBatch(this, i, i2, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParam$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliBatchModifyParam.this.lambda$setFailureState$27(item, selectViews, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFailureState$27(ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem, List list, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            actionNext(batchSaveItem, list);
        } else {
            setErrorAndActionNextDevice(batchSaveItem);
        }
    }
}