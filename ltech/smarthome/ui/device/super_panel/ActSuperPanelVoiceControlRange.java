package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSuperPanelVoiceControlRange extends VMActivity<ActSuperPanelVoiceControlRangeBinding, ActSuperPanelVoiceControlRangeVM> {
    private int lastSelect;
    private int voiceControlType;
    private List<Long> deviceIdList = new ArrayList();
    private List<Long> groupIdList = new ArrayList();
    private List<Long> sceneIdList = new ArrayList();
    private int saveType = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_voice_control_range;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.voice_control_range));
        setEditString(getString(R.string.save));
        setBackImage(R.mipmap.icon_back);
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.deviceIdList.add(Long.valueOf(j));
            }
        }
        long[] longArrayExtra2 = getIntent().getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            for (long j2 : longArrayExtra2) {
                this.groupIdList.add(Long.valueOf(j2));
            }
        }
        long[] longArrayExtra3 = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra3 != null) {
            for (long j3 : longArrayExtra3) {
                this.sceneIdList.add(Long.valueOf(j3));
            }
        }
        this.voiceControlType = getIntent().getIntExtra(Constants.VOICE_CONTROL_TYPE, 1);
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).selectPosition.setValue(Integer.valueOf(this.voiceControlType));
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).diyCount.setValue(Integer.valueOf(this.deviceIdList.size() + this.groupIdList.size() + this.sceneIdList.size()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRange$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelVoiceControlRange.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).selectPosition.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRange$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelVoiceControlRange.this.lambda$startObserve$1((Integer) obj);
            }
        });
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).diyCount.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRange$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelVoiceControlRange.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        AppCompatImageView appCompatImageView = ((ActSuperPanelVoiceControlRangeBinding) this.mViewBinding).allIcon;
        int intValue = num.intValue();
        int i = R.mipmap.ic_tick_sel;
        appCompatImageView.setImageResource(intValue == 1 ? R.mipmap.ic_tick_sel : 0);
        ((ActSuperPanelVoiceControlRangeBinding) this.mViewBinding).partIcon.setImageResource(num.intValue() == 2 ? R.mipmap.ic_tick_sel : 0);
        AppCompatImageView appCompatImageView2 = ((ActSuperPanelVoiceControlRangeBinding) this.mViewBinding).diyIcon;
        if (num.intValue() != 3) {
            i = 0;
        }
        appCompatImageView2.setImageResource(i);
        setEditString(num.intValue() == 3 ? "" : getString(R.string.save));
        if (this.lastSelect == 3 && num.intValue() == 3) {
            long[] jArr = new long[this.deviceIdList.size()];
            int size = this.deviceIdList.size();
            for (int i2 = 0; i2 < size; i2++) {
                jArr[i2] = this.deviceIdList.get(i2).longValue();
            }
            long[] jArr2 = new long[this.groupIdList.size()];
            int size2 = this.groupIdList.size();
            for (int i3 = 0; i3 < size2; i3++) {
                jArr2[i3] = this.groupIdList.get(i3).longValue();
            }
            long[] jArr3 = new long[this.sceneIdList.size()];
            int size3 = this.sceneIdList.size();
            for (int i4 = 0; i4 < size3; i4++) {
                jArr3[i4] = this.sceneIdList.get(i4).longValue();
            }
            NavUtils.destination(ActSuperPanelVoiceControlRangeRole.class).withLong(Constants.CONTROL_ID, ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlId).withLong(Constants.PLACE_ID, ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.getValue().getPlaceId()).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).withLongArray(Constants.GROUP_ID_ARRAY, jArr2).withLongArray(Constants.SCENE_ID_ARRAY, jArr3).withDefaultRequestCode().navigation(this);
        }
        this.lastSelect = num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((ActSuperPanelVoiceControlRangeBinding) this.mViewBinding).diyCountText.setText(String.valueOf(num));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        Intent intent = new Intent();
        intent.putExtra("voice_control_range_back", this.saveType);
        setResult(-1, intent);
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelVoiceControlRange(((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.getValue().getDeviceId(), this.lastSelect, ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).controlDevice.getValue().getPlaceId(), new ArrayList(), new ArrayList(), new ArrayList()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRange$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelVoiceControlRange.this.lambda$edit$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRange$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSuperPanelVoiceControlRange.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRange$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelVoiceControlRange.this.lambda$edit$4(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$4(Object obj) throws Exception {
        this.saveType = this.lastSelect;
        this.deviceIdList.clear();
        this.groupIdList.clear();
        this.sceneIdList.clear();
        int i = this.lastSelect;
        if (i == 0 || i == 1) {
            ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).diyCount.setValue(0);
        }
        SmartToast.showCenterShort(getString(R.string.save_success));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 || data == null) {
            return;
        }
        this.deviceIdList.clear();
        long[] longArrayExtra = data.getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.deviceIdList.add(Long.valueOf(j));
            }
        }
        this.groupIdList.clear();
        long[] longArrayExtra2 = data.getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra2 != null) {
            for (long j2 : longArrayExtra2) {
                this.groupIdList.add(Long.valueOf(j2));
            }
        }
        this.sceneIdList.clear();
        long[] longArrayExtra3 = data.getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra3 != null) {
            for (long j3 : longArrayExtra3) {
                this.sceneIdList.add(Long.valueOf(j3));
            }
        }
        ((ActSuperPanelVoiceControlRangeVM) this.mViewModel).diyCount.setValue(Integer.valueOf(this.deviceIdList.size() + this.groupIdList.size() + this.sceneIdList.size()));
    }
}