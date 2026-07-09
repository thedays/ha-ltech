package com.ltech.smarthome.ui.voicecall.setting;

import android.content.Intent;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActVoiceCallSettingBinding;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActVoiceCallSetting extends VMActivity<ActVoiceCallSettingBinding, ActVoiceCallSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_voice_call_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_voice_call_manage));
        setBackImage(R.mipmap.icon_back);
        ((ActVoiceCallSettingBinding) this.mViewBinding).sbAuto.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActVoiceCallSettingVM) ActVoiceCallSetting.this.mViewModel).enableAutoAnswer(isChecked);
            }
        });
        ((ActVoiceCallSettingBinding) this.mViewBinding).sbDisturb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSetting.2
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActVoiceCallSettingVM) ActVoiceCallSetting.this.mViewModel).doNotDisturb(isChecked);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActVoiceCallSettingVM) this.mViewModel).panelId = getIntent().getLongExtra("device_id", -1L);
        ((ActVoiceCallSettingVM) this.mViewModel).loadData();
        ((ActVoiceCallSettingVM) this.mViewModel).timeStartEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActVoiceCallSetting.this.showTimeDialog(true);
            }
        });
        ((ActVoiceCallSettingVM) this.mViewModel).timeEndEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActVoiceCallSetting.this.showTimeDialog(false);
            }
        });
    }

    public void showTimeDialog(final boolean isStart) {
        int i;
        String[] split = VoiceCallManager.getInstance().getRepeatTime().split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        int i2 = 22;
        try {
            if (isStart) {
                i2 = Integer.parseInt(split[0]) / 100;
                i = Integer.parseInt(split[0]) % 100;
            } else {
                i2 = Integer.parseInt(split[1]) / 100;
                i = Integer.parseInt(split[1]) % 100;
            }
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < 24; i3++) {
            arrayList.add(i3 < 10 ? "0" + i3 : String.valueOf(i3));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i4 = 0; i4 < 60; i4++) {
            arrayList2.add(i4 < 10 ? "0" + i4 : String.valueOf(i4));
        }
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).setSelectMinPosition(i2).setSelectSecPosition(i).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i5, int i6) {
                ActVoiceCallSetting.this.lambda$showTimeDialog$0(isStart, i5, i6);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$0(boolean z, int i, int i2) {
        if (z) {
            ((ActVoiceCallSettingVM) this.mViewModel).setStarTime(i, i2);
        } else {
            ((ActVoiceCallSettingVM) this.mViewModel).setEndTime(i, i2);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3004) {
            if (resultCode == -1) {
                ((ActVoiceCallSettingVM) this.mViewModel).loadData();
            }
        } else if (data != null) {
            ((ActVoiceCallSettingVM) this.mViewModel).setWeek(data.getStringExtra(com.ltech.smarthome.utils.Constants.WEEKS));
            SharedPreferenceUtil.edit().keepShared(com.ltech.smarthome.utils.Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_REPEAT, data.getStringExtra(com.ltech.smarthome.utils.Constants.WEEKS)).apply();
        }
    }
}