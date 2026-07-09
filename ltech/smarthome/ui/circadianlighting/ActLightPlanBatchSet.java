package com.ltech.smarthome.ui.circadianlighting;

import android.content.Intent;
import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActLightPlanBatchSetBinding;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class ActLightPlanBatchSet extends VMActivity<ActLightPlanBatchSetBinding, ActLightPlanBatchVM> {
    private int mSelectHour;
    private int mSelectMin;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_plan_batch_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_circadian_lighting));
        setBackImage(R.mipmap.icon_back);
        ((ActLightPlanBatchVM) this.mViewModel).sunRiseText.setValue(SharedPreferenceUtil.queryValue(Constants.SUN_RISE));
        ((ActLightPlanBatchVM) this.mViewModel).sunSetText.setValue(SharedPreferenceUtil.queryValue(Constants.SUN_SET));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActLightPlanBatchVM) this.mViewModel).syncPlanList();
        ((ActLightPlanBatchVM) this.mViewModel).getSunTime();
        int queryIntValue = SharedPreferenceUtil.queryIntValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP);
        if (queryIntValue != 0) {
            ((ActLightPlanBatchVM) this.mViewModel).showRhythmsModel.setValue(Integer.valueOf(queryIntValue));
        }
        ((ActLightPlanBatchVM) this.mViewModel).repeatTimeLiveData.observe(this, new Observer<String>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(String s) {
                if (TextUtils.isEmpty(s)) {
                    ((ActLightPlanBatchSetBinding) ActLightPlanBatchSet.this.mViewBinding).repeatWeekTv.setText(ActLightPlanBatchSet.this.getString(R.string.only_once));
                } else {
                    ((ActLightPlanBatchSetBinding) ActLightPlanBatchSet.this.mViewBinding).repeatWeekTv.setText(HelpUtils.getWeeksStringNew(ActLightPlanBatchSet.this.activity, s));
                }
            }
        });
        ((ActLightPlanBatchVM) this.mViewModel).selectEndTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightPlanBatchSet.this.showTimeDialog(false);
            }
        });
        ((ActLightPlanBatchVM) this.mViewModel).selectStarTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightPlanBatchSet.this.showTimeDialog(true);
            }
        });
        ((ActLightPlanBatchVM) this.mViewModel).showPlanListEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightPlanBatchSet.this.showPlanListDialog();
            }
        });
        String queryValue = SharedPreferenceUtil.queryValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_REPEAT);
        if (queryValue != null) {
            ((ActLightPlanBatchVM) this.mViewModel).setWeek(queryValue);
        }
        ((ActLightPlanBatchVM) this.mViewModel).showRhythmsModel.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                int queryIntValue2;
                int intValue = integer.intValue();
                if (intValue != 2) {
                    if (intValue == 3 && (queryIntValue2 = SharedPreferenceUtil.queryIntValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_PLAN)) > 0) {
                        ((ActLightPlanBatchVM) ActLightPlanBatchSet.this.mViewModel).setPlan(queryIntValue2);
                        return;
                    }
                    return;
                }
                String queryValue2 = SharedPreferenceUtil.queryValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_START);
                String queryValue3 = SharedPreferenceUtil.queryValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_END);
                ((ActLightPlanBatchVM) ActLightPlanBatchSet.this.mViewModel).initStartAndEndTime(queryValue2, queryValue3);
                if (queryValue2 != null && !TextUtils.isEmpty(queryValue2)) {
                    String[] split = queryValue2.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
                    if (split.length == 2) {
                        ((ActLightPlanBatchVM) ActLightPlanBatchSet.this.mViewModel).setStarTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                    }
                }
                if (queryValue3 == null || TextUtils.isEmpty(queryValue3)) {
                    return;
                }
                String[] split2 = queryValue3.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
                if (split2.length == 2) {
                    ((ActLightPlanBatchVM) ActLightPlanBatchSet.this.mViewModel).setEndTime(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                }
            }
        });
    }

    public void showTimeDialog(final boolean isStart) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ActLightPlanBatchVM actLightPlanBatchVM = (ActLightPlanBatchVM) this.mViewModel;
        this.mSelectHour = isStart ? actLightPlanBatchVM.getStarH() : actLightPlanBatchVM.getEndH();
        ActLightPlanBatchVM actLightPlanBatchVM2 = (ActLightPlanBatchVM) this.mViewModel;
        this.mSelectMin = isStart ? actLightPlanBatchVM2.getStarM() : actLightPlanBatchVM2.getEndM();
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightPlanBatchSet.this.lambda$showTimeDialog$0(isStart, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$0(boolean z, int i, int i2) {
        if (this.mSelectHour == i && this.mSelectMin == i2) {
            return;
        }
        this.mSelectHour = i;
        this.mSelectMin = i2;
        if (z) {
            SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_START, this.mSelectHour + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + this.mSelectMin).apply();
            ((ActLightPlanBatchVM) this.mViewModel).setStarTime(this.mSelectHour, this.mSelectMin);
            return;
        }
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_END, this.mSelectHour + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + this.mSelectMin).apply();
        ((ActLightPlanBatchVM) this.mViewModel).setEndTime(this.mSelectHour, this.mSelectMin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPlanListDialog() {
        ArrayList arrayList = new ArrayList();
        Iterator<ListModeResponse.RowsBean> it = ((ActLightPlanBatchVM) this.mViewModel).mRhythmsList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getModename());
        }
        SelectListDialog selectPosition = SelectListDialog.asDefault(true).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(((ActLightPlanBatchVM) this.mViewModel).mCurPlan == 0 ? 0 : ((ActLightPlanBatchVM) this.mViewModel).mCurPlan - 1);
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightPlanBatchSet.this.lambda$showPlanListDialog$1((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlanListDialog$1(Integer num) {
        ((ActLightPlanBatchVM) this.mViewModel).setPlan(num.intValue() + 1);
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_PLAN, num.intValue() + 1);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            if (resultCode != 3004 || data == null) {
                return;
            }
            ((ActLightPlanBatchVM) this.mViewModel).setWeek(data.getStringExtra(Constants.WEEKS));
            SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_REPEAT, data.getStringExtra(Constants.WEEKS)).apply();
            return;
        }
        if (requestCode == 5009) {
            ((ActLightPlanBatchVM) this.mViewModel).syncPlanList();
            if (data != null) {
                int intExtra = data.getIntExtra("index", 1);
                String stringExtra = data.getStringExtra("data");
                int i = intExtra - 1;
                ListModeResponse.RowsBean rowsBean = ((ActLightPlanBatchVM) this.mViewModel).mRhythmsList.get(i);
                rowsBean.setContent(stringExtra);
                ((ActLightPlanBatchVM) this.mViewModel).mRhythmsList.set(i, rowsBean);
                ((ActLightPlanBatchVM) this.mViewModel).setPlan(intExtra);
                SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_PLAN, intExtra);
            }
        }
    }
}