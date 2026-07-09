package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectEffectPeriodBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;

/* loaded from: classes4.dex */
public class ActSelectEffectPeriod extends VMActivity<ActSelectEffectPeriodBinding, ActSelectEffectPeriodVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_effect_period;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.effective_period));
        setBackImage(R.mipmap.icon_back);
        String stringExtra = getIntent().getStringExtra(Constants.START_TIME);
        String stringExtra2 = getIntent().getStringExtra(Constants.END_TIME);
        ((ActSelectEffectPeriodVM) this.mViewModel).weeks = getIntent().getStringExtra(Constants.WEEKS);
        if (TextUtils.isEmpty(((ActSelectEffectPeriodVM) this.mViewModel).weeks)) {
            ((ActSelectEffectPeriodVM) this.mViewModel).weeks = "";
        }
        if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
            try {
                String[] split = stringExtra.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
                String[] split2 = stringExtra2.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
                ((ActSelectEffectPeriodVM) this.mViewModel).startHourPos = Integer.parseInt(split[0]);
                ((ActSelectEffectPeriodVM) this.mViewModel).startMinPos = Integer.parseInt(split[1]);
                ((ActSelectEffectPeriodVM) this.mViewModel).endHourPos = Integer.parseInt(split2[0]);
                ((ActSelectEffectPeriodVM) this.mViewModel).endMinPos = Integer.parseInt(split2[1]);
            } catch (Exception unused) {
                ((ActSelectEffectPeriodVM) this.mViewModel).startHourPos = 0;
                ((ActSelectEffectPeriodVM) this.mViewModel).startMinPos = 0;
                ((ActSelectEffectPeriodVM) this.mViewModel).endHourPos = 0;
                ((ActSelectEffectPeriodVM) this.mViewModel).endMinPos = 0;
            }
        }
        ((ActSelectEffectPeriodVM) this.mViewModel).showNextDay.setValue(Boolean.valueOf(((ActSelectEffectPeriodVM) this.mViewModel).isNextDay()));
        ((ActSelectEffectPeriodVM) this.mViewModel).startTime.setValue(((ActSelectEffectPeriodVM) this.mViewModel).getTimeString(((ActSelectEffectPeriodVM) this.mViewModel).startHourPos, ((ActSelectEffectPeriodVM) this.mViewModel).startMinPos));
        ((ActSelectEffectPeriodVM) this.mViewModel).endTime.setValue(((ActSelectEffectPeriodVM) this.mViewModel).getTimeString(((ActSelectEffectPeriodVM) this.mViewModel).endHourPos, ((ActSelectEffectPeriodVM) this.mViewModel).endMinPos));
        ((ActSelectEffectPeriodVM) this.mViewModel).repeatTimeLiveData.setValue(((ActSelectEffectPeriodVM) this.mViewModel).weeks);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSelectEffectPeriodVM) this.mViewModel).showEditStartTimeDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriod$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectEffectPeriod.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActSelectEffectPeriodVM) this.mViewModel).showEditEndTimeDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriod$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectEffectPeriod.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActSelectEffectPeriodVM) this.mViewModel).repeatTimeLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriod$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectEffectPeriod.this.lambda$startObserve$4((String) obj);
            }
        });
        ((ActSelectEffectPeriodVM) this.mViewModel).showNextDay.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriod$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectEffectPeriod.this.lambda$startObserve$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r4) {
        showEditTimeDialog(true, ((ActSelectEffectPeriodVM) this.mViewModel).startHourPos, ((ActSelectEffectPeriodVM) this.mViewModel).startMinPos, new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriod$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActSelectEffectPeriod.this.lambda$startObserve$0(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(int i, int i2) {
        ((ActSelectEffectPeriodVM) this.mViewModel).startHourPos = i;
        ((ActSelectEffectPeriodVM) this.mViewModel).startMinPos = i2;
        ((ActSelectEffectPeriodVM) this.mViewModel).startTime.setValue(((ActSelectEffectPeriodVM) this.mViewModel).getTimeString(i, i2));
        ((ActSelectEffectPeriodVM) this.mViewModel).showNextDay.setValue(Boolean.valueOf(((ActSelectEffectPeriodVM) this.mViewModel).isNextDay()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r4) {
        showEditTimeDialog(false, ((ActSelectEffectPeriodVM) this.mViewModel).endHourPos, ((ActSelectEffectPeriodVM) this.mViewModel).endMinPos, new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriod$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActSelectEffectPeriod.this.lambda$startObserve$2(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(int i, int i2) {
        ((ActSelectEffectPeriodVM) this.mViewModel).endHourPos = i;
        ((ActSelectEffectPeriodVM) this.mViewModel).endMinPos = i2;
        ((ActSelectEffectPeriodVM) this.mViewModel).endTime.setValue(((ActSelectEffectPeriodVM) this.mViewModel).getTimeString(i, i2));
        ((ActSelectEffectPeriodVM) this.mViewModel).showNextDay.setValue(Boolean.valueOf(((ActSelectEffectPeriodVM) this.mViewModel).isNextDay()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(String str) {
        if (TextUtils.isEmpty(str)) {
            ((ActSelectEffectPeriodBinding) this.mViewBinding).tvRepeatTime.setText(str);
        } else {
            ((ActSelectEffectPeriodBinding) this.mViewBinding).tvRepeatTime.setText(HelpUtils.getWeeksString(this, str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Boolean bool) {
        ((ActSelectEffectPeriodBinding) this.mViewBinding).date.setText(getString(bool.booleanValue() ? R.string.next_day : R.string.this_day));
    }

    private void showEditTimeDialog(boolean start, int hourPos, int minPos, TimeSelectDialog.SelectListener listener) {
        TimeSelectDialog.asDefault().setTitle(getString(start ? R.string.start_time : R.string.end_time)).setMinList(((ActSelectEffectPeriodVM) this.mViewModel).hourList).setSecList(((ActSelectEffectPeriodVM) this.mViewModel).minList).setSelectMinPosition(hourPos).setSelectSecPosition(minPos).setSelectListener(listener).showDialog(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3004 || data == null) {
            return;
        }
        ((ActSelectEffectPeriodVM) this.mViewModel).weeks = data.getStringExtra(Constants.WEEKS);
        ((ActSelectEffectPeriodVM) this.mViewModel).repeatTimeLiveData.setValue(((ActSelectEffectPeriodVM) this.mViewModel).weeks);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (TextUtils.isEmpty(((ActSelectEffectPeriodVM) this.mViewModel).weeks)) {
            SmartToast.showShort(R.string.please_select_date);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.START_TIME, ((ActSelectEffectPeriodVM) this.mViewModel).getTimeString(((ActSelectEffectPeriodVM) this.mViewModel).startHourPos, ((ActSelectEffectPeriodVM) this.mViewModel).startMinPos));
        intent.putExtra(Constants.END_TIME, ((ActSelectEffectPeriodVM) this.mViewModel).getTimeString(((ActSelectEffectPeriodVM) this.mViewModel).endHourPos, ((ActSelectEffectPeriodVM) this.mViewModel).endMinPos));
        intent.putExtra(Constants.WEEKS, ((ActSelectEffectPeriodVM) this.mViewModel).weeks);
        setResult(3004, intent);
        super.back();
    }
}