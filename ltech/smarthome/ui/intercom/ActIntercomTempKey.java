package com.ltech.smarthome.ui.intercom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.lifecycle.Observer;
import com.google.firebase.messaging.Constants;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomTempKeyBinding;
import com.ltech.smarthome.net.response.intercom.KeyListResponse;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.IntercomTimePickerDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIntercomTempKey extends VMActivity<ActIntercomTempKeyBinding, ActIntercomTempKeyVM> {
    private int selectEffectiveDayPosition;
    private int selectEffectiveMonthPosition;
    private int selectExpirationDayPosition;
    private int selectExpirationHourPosition;
    private int selectExpirationMinPosition;
    private int selectExpirationMonthPosition;
    private String year;
    private int countSelPosition = 0;
    List<String> countList = new ArrayList();
    private List<String> monthList = new ArrayList();
    private List<String> dayList = new ArrayList();
    private List<String> hourList = new ArrayList();
    private List<String> minList = new ArrayList();
    private int selectEffectiveHourPosition = 0;
    private int selectEffectiveMinPosition = 0;

    public boolean has31Days(int month) {
        return month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_temp_key;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        for (int i = 1; i < 101; i++) {
            this.countList.add(String.valueOf(i));
        }
        for (int i2 = 1; i2 < 13; i2++) {
            this.monthList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        for (int i3 = 1; i3 < 32; i3++) {
            this.dayList.add(i3 < 10 ? "0" + i3 : String.valueOf(i3));
        }
        for (int i4 = 0; i4 < 24; i4++) {
            this.hourList.add(i4 < 10 ? "0" + i4 : String.valueOf(i4));
        }
        for (int i5 = 0; i5 < 60; i5++) {
            this.minList.add(i5 < 10 ? "0" + i5 : String.valueOf(i5));
        }
        Date date = new Date();
        this.year = new SimpleDateFormat("yyyy").format(date);
        String format = new SimpleDateFormat("MM").format(date);
        String format2 = new SimpleDateFormat("dd").format(date);
        this.selectEffectiveMonthPosition = Integer.parseInt(format) - 1;
        this.selectEffectiveDayPosition = Integer.parseInt(format2) - 1;
        this.selectExpirationMonthPosition = Integer.parseInt(format) - 1;
        if (has31Days(Integer.parseInt(format))) {
            if (Integer.parseInt(format2) + 1 > 31) {
                this.selectExpirationDayPosition = 0;
                this.selectExpirationMonthPosition = Integer.parseInt(format);
            } else {
                this.selectExpirationDayPosition = Integer.parseInt(format2);
            }
        } else if (Integer.parseInt(format2) + 1 > 30) {
            this.selectExpirationDayPosition = 0;
            this.selectExpirationMonthPosition = Integer.parseInt(format);
        } else {
            this.selectExpirationDayPosition = Integer.parseInt(format2);
        }
        this.selectExpirationHourPosition = this.hourList.size() - 1;
        this.selectExpirationMinPosition = this.minList.size() - 1;
        ((ActIntercomTempKeyBinding) this.mViewBinding).tvKeyCount.setText(this.countList.get(this.countSelPosition));
        ((ActIntercomTempKeyBinding) this.mViewBinding).tvEffectiveTime.setText(String.format("%s-%s-%s %s:%s", this.year, this.monthList.get(this.selectEffectiveMonthPosition), this.dayList.get(this.selectEffectiveDayPosition), this.hourList.get(this.selectEffectiveHourPosition), this.minList.get(this.selectEffectiveMinPosition)));
        ((ActIntercomTempKeyBinding) this.mViewBinding).tvExpirationTime.setText(String.format("%s-%s-%s %s:%s", this.year, this.monthList.get(this.selectExpirationMonthPosition), this.dayList.get(this.selectExpirationDayPosition), this.hourList.get(this.selectExpirationHourPosition), this.minList.get(this.selectExpirationMinPosition)));
        if (!isWechatInstalled(this)) {
            ((ActIntercomTempKeyBinding) this.mViewBinding).ivWechat.setVisibility(8);
            ((ActIntercomTempKeyBinding) this.mViewBinding).tvWechat.setVisibility(8);
        }
        ((ActIntercomTempKeyVM) this.mViewModel).getTempKey();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomTempKeyVM) this.mViewModel).showCountDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActIntercomTempKeyVM) this.mViewModel).showEffectiveDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActIntercomTempKeyVM) this.mViewModel).showExpirationDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActIntercomTempKeyVM) this.mViewModel).isCreateKeyMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$6((Boolean) obj);
            }
        });
        ((ActIntercomTempKeyVM) this.mViewModel).tempKey.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$7((KeyListResponse.OpenDoorTempKey) obj);
            }
        });
        ((ActIntercomTempKeyVM) this.mViewModel).clickMessageEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActIntercomTempKeyVM) this.mViewModel).clickWechatEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomTempKey.this.lambda$startObserve$11((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r2) {
        TimeSelectDialog.asDefault().setSaveText(getString(R.string.save)).setTitle(getString(R.string.intercom_key_use_counts)).setMinList(this.countList).setSelectMinPosition(this.countSelPosition).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActIntercomTempKey.this.lambda$startObserve$0(i, i2);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(int i, int i2) {
        this.countSelPosition = i;
        ((ActIntercomTempKeyBinding) this.mViewBinding).tvKeyCount.setText(this.countList.get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r2) {
        IntercomTimePickerDialog.asDefault().setSaveText(getString(R.string.save)).setTitle(getString(R.string.intercom_key_effective_time)).setMonthList(this.monthList).setDayList(this.dayList).setHourList(this.hourList).setMinList(this.minList).setSelectMonthPosition(this.selectEffectiveMonthPosition).setSelectDayPosition(this.selectEffectiveDayPosition).setSelectHourPosition(0).setSelectMinPosition(0).setConfirmListener(new IntercomTimePickerDialog.SelectListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.dialog.IntercomTimePickerDialog.SelectListener
            public final void confirm(int i, int i2, int i3, int i4) {
                ActIntercomTempKey.this.lambda$startObserve$2(i, i2, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(int i, int i2, int i3, int i4) {
        this.selectEffectiveMonthPosition = i;
        this.selectEffectiveDayPosition = i2;
        this.selectEffectiveHourPosition = i3;
        this.selectEffectiveMinPosition = i4;
        ((ActIntercomTempKeyBinding) this.mViewBinding).tvEffectiveTime.setText(String.format("%s-%s-%s %s:%s", this.year, this.monthList.get(this.selectEffectiveMonthPosition), this.dayList.get(this.selectEffectiveDayPosition), this.hourList.get(this.selectEffectiveHourPosition), this.minList.get(this.selectEffectiveMinPosition)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r2) {
        IntercomTimePickerDialog.asDefault().setSaveText(getString(R.string.save)).setTitle(getString(R.string.intercom_key_expiration_time)).setMonthList(this.monthList).setDayList(this.dayList).setHourList(this.hourList).setMinList(this.minList).setSelectMonthPosition(this.selectExpirationMonthPosition).setSelectDayPosition(this.selectExpirationDayPosition).setSelectHourPosition(0).setSelectMinPosition(0).setConfirmListener(new IntercomTimePickerDialog.SelectListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.dialog.IntercomTimePickerDialog.SelectListener
            public final void confirm(int i, int i2, int i3, int i4) {
                ActIntercomTempKey.this.lambda$startObserve$4(i, i2, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(int i, int i2, int i3, int i4) {
        this.selectExpirationMonthPosition = i;
        this.selectExpirationDayPosition = i2;
        this.selectExpirationHourPosition = i3;
        this.selectExpirationMinPosition = i4;
        ((ActIntercomTempKeyBinding) this.mViewBinding).tvExpirationTime.setText(String.format("%s-%s-%s %s:%s", this.year, this.monthList.get(this.selectExpirationMonthPosition), this.dayList.get(this.selectExpirationDayPosition), this.hourList.get(this.selectExpirationHourPosition), this.minList.get(this.selectExpirationMinPosition)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        ((ActIntercomTempKeyBinding) this.mViewBinding).layoutSetKey.setVisibility(bool.booleanValue() ? 0 : 8);
        ((ActIntercomTempKeyBinding) this.mViewBinding).layoutShowKey.setVisibility(bool.booleanValue() ? 8 : 0);
        setTitle(bool.booleanValue() ? getString(R.string.intercom_creact_temp_key) : "");
        setEditString(getString(bool.booleanValue() ? R.string.save : R.string.intercom_reset));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(KeyListResponse.OpenDoorTempKey openDoorTempKey) {
        if (openDoorTempKey != null) {
            ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempKey.setText(String.valueOf(openDoorTempKey.getTemp_key()));
            ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempEffectiveTime.setText(openDoorTempKey.getBegin_time().substring(0, 16));
            ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempExpirationTime.setText(openDoorTempKey.getEnd_time().substring(0, 16));
            ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempKeyCount.setText(String.format(getResources().getString(R.string.intercom_use_count), Integer.valueOf(openDoorTempKey.getCounts())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r4) {
        SmartToast.showCenterShort(getString(R.string.intercom_copy_success));
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActIntercomTempKey.this.lambda$startObserve$8();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8() {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(Constants.ScionAnalytics.PARAM_LABEL, String.format(getResources().getString(R.string.cpoy_message), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempKey.getText().toString(), Integer.valueOf(((ActIntercomTempKeyVM) this.mViewModel).tempKey.getValue().getCounts()), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempEffectiveTime.getText().toString(), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempExpirationTime.getText().toString())));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("sms:"));
        intent.putExtra("sms_body", String.format(getResources().getString(R.string.cpoy_message), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempKey.getText().toString(), Integer.valueOf(((ActIntercomTempKeyVM) this.mViewModel).tempKey.getValue().getCounts()), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempEffectiveTime.getText().toString(), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempExpirationTime.getText().toString()));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Void r4) {
        SmartToast.showCenterShort(getString(R.string.intercom_copy_success));
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKey$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActIntercomTempKey.this.lambda$startObserve$10();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10() {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(Constants.ScionAnalytics.PARAM_LABEL, String.format(getResources().getString(R.string.cpoy_message), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempKey.getText().toString(), Integer.valueOf(((ActIntercomTempKeyVM) this.mViewModel).tempKey.getValue().getCounts()), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempEffectiveTime.getText().toString(), ((ActIntercomTempKeyBinding) this.mViewBinding).tvTempExpirationTime.getText().toString())));
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addFlags(268435456);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    public boolean isWechatInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.tencent.mm", 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActIntercomTempKeyVM) this.mViewModel).isCreateKeyMode.getValue().booleanValue()) {
            String format = String.format("%s-%s-%s %s:%s", this.year, this.monthList.get(this.selectEffectiveMonthPosition), this.dayList.get(this.selectEffectiveDayPosition), this.hourList.get(this.selectEffectiveHourPosition), this.minList.get(this.selectEffectiveMinPosition));
            String format2 = String.format("%s-%s-%s %s:%s", this.year, this.monthList.get(this.selectExpirationMonthPosition), this.dayList.get(this.selectExpirationDayPosition), this.hourList.get(this.selectExpirationHourPosition), this.minList.get(this.selectExpirationMinPosition));
            if (((ActIntercomTempKeyVM) this.mViewModel).tempKey.getValue() != null) {
                ((ActIntercomTempKeyVM) this.mViewModel).deleteAndCreateTempKey(format, format2, this.countSelPosition + 1);
                return;
            } else {
                ((ActIntercomTempKeyVM) this.mViewModel).createNewKey(format, format2, this.countSelPosition + 1);
                return;
            }
        }
        ((ActIntercomTempKeyVM) this.mViewModel).isCreateKeyMode.setValue(Boolean.valueOf(true ^ ((ActIntercomTempKeyVM) this.mViewModel).isCreateKeyMode.getValue().booleanValue()));
    }
}