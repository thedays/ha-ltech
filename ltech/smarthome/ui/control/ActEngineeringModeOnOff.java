package com.ltech.smarthome.ui.control;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActEngineeringModeOnOffBinding;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActEngineeringModeOnOff extends BaseNormalActivity<ActEngineeringModeOnOffBinding> {
    private int onTime = 3000;
    private int offTime = 3000;
    private boolean multiMode = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_engineering_mode_on_off;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_engineering_mode));
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.MULTI_SELECT, false);
        this.multiMode = booleanExtra;
        setEditString(getString(booleanExtra ? R.string.save : R.string.next));
        ((ActEngineeringModeOnOffBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.ActEngineeringModeOnOff$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEngineeringModeOnOff.this.lambda$initView$0((View) obj);
            }
        }));
        this.onTime = getIntent().getIntExtra(Constants.ON_TIME_VALUE, 3000);
        this.offTime = getIntent().getIntExtra(Constants.OFF_TIME_VALUE, 3000);
        refreshTimeView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.layout_light_off /* 2131297517 */:
                showPickTimeDialog(false);
                break;
            case R.id.layout_light_on /* 2131297518 */:
                showPickTimeDialog(true);
                break;
        }
    }

    private void refreshTimeView() {
        if (this.mViewBinding != 0) {
            ((ActEngineeringModeOnOffBinding) this.mViewBinding).tvOnTime.setText(String.format(Locale.US, "%.1f%s", Float.valueOf(this.onTime / 1000.0f), getString(R.string.sec)));
            ((ActEngineeringModeOnOffBinding) this.mViewBinding).tvOffTime.setText(String.format(Locale.US, "%.1f%s", Float.valueOf(this.offTime / 1000.0f), getString(R.string.sec)));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        SharedPreferenceUtil.edit().keepShared(Constants.ON_TIME_VALUE, this.onTime);
        SharedPreferenceUtil.edit().keepShared(Constants.OFF_TIME_VALUE, this.offTime);
        finishActivity();
        ReplaceHelper.instance().backupPlaceData(this, UpdateBackDataRequest.FADE_TIME, CmdAssistant.getLightCmdAssistant(null, new int[0]).setOnOffTime(this.onTime, this.offTime));
        if (this.multiMode) {
            return;
        }
        NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, 0L)).withInt(Constants.ENGINEER_SET_TYPE, 3).navigation(this);
    }

    private void showPickTimeDialog(final boolean on) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition((on ? this.onTime : this.offTime) / 1000).setSelectSecPosition(((on ? this.onTime : this.offTime) % 1000) / 100).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.control.ActEngineeringModeOnOff$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActEngineeringModeOnOff.this.lambda$showPickTimeDialog$1(on, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPickTimeDialog$1(boolean z, int i, int i2) {
        if (z) {
            this.onTime = (i * 1000) + (i2 * 100);
        } else {
            this.offTime = (i * 1000) + (i2 * 100);
        }
        refreshTimeView();
    }
}