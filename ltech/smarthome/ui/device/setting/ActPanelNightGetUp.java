package com.ltech.smarthome.ui.device.setting;

import android.content.Intent;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActPanelNightGetupBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.product_agreement.bean.SmartPanelNightUpState;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActPanelNightGetUp extends VMActivity<ActPanelNightGetupBinding, ActPanelNightGetUpVM> {
    private int mSelectHour;
    private int mSelectMin;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_panel_night_getup;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_get_up_in_the_night_mode));
        ((ActPanelNightGetUpVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActPanelNightGetUpVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActPanelNightGetUpVM) this.mViewModel).controlId);
        ((ActPanelNightGetupBinding) this.mViewBinding).sbGetUpNightMode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUp.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActPanelNightGetUpVM) ActPanelNightGetUp.this.mViewModel).setSwitchOn(isChecked);
            }
        });
        SmartPanelNightUpState smartPanelNightUpState = (SmartPanelNightUpState) GsonUtils.fromJson(getIntent().getStringExtra(Constants.MODE_DATA), new TypeToken<SmartPanelNightUpState>(this) { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUp.2
        }.getType());
        if (smartPanelNightUpState != null) {
            ((ActPanelNightGetUpVM) this.mViewModel).refreshPanelState(smartPanelNightUpState);
        }
        ((ActPanelNightGetUpVM) this.mViewModel).queryPanelState();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActPanelNightGetUpVM) this.mViewModel).selectStarTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUp.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActPanelNightGetUp.this.showTimeDialog(true);
            }
        });
        ((ActPanelNightGetUpVM) this.mViewModel).selectEndTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUp.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActPanelNightGetUp.this.showTimeDialog(false);
            }
        });
        ((ActPanelNightGetUpVM) this.mViewModel).selectSceneDialogEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUp.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, ((ActPanelNightGetUpVM) ActPanelNightGetUp.this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActPanelNightGetUpVM) ActPanelNightGetUp.this.mViewModel).controlId).withBoolean(Constants.IS_NIGHT_UP, true).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_EXC, ((ActPanelNightGetUpVM) ActPanelNightGetUp.this.mViewModel).selectSceneDialogEvent.getValue().intValue() == 0).withDefaultRequestCode().navigation(ActPanelNightGetUp.this.activity);
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
        ActPanelNightGetUpVM actPanelNightGetUpVM = (ActPanelNightGetUpVM) this.mViewModel;
        this.mSelectHour = isStart ? actPanelNightGetUpVM.getStarH() : actPanelNightGetUpVM.getEndH();
        ActPanelNightGetUpVM actPanelNightGetUpVM2 = (ActPanelNightGetUpVM) this.mViewModel;
        this.mSelectMin = isStart ? actPanelNightGetUpVM2.getStarM() : actPanelNightGetUpVM2.getEndM();
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.setting.ActPanelNightGetUp$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActPanelNightGetUp.this.lambda$showTimeDialog$0(isStart, i3, i4);
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
            ((ActPanelNightGetUpVM) this.mViewModel).setStarTime(this.mSelectHour, this.mSelectMin);
        } else {
            ((ActPanelNightGetUpVM) this.mViewModel).setEndTime(this.mSelectHour, this.mSelectMin);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Scene sceneBySceneId;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3001 || data == null) {
            return;
        }
        long longExtra = data.getLongExtra(Constants.RELATE_ID, -1L);
        if (data.getIntExtra(Constants.GROUP_RELATE, -1) != 3 || (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(longExtra)) == null || ((ActPanelNightGetUpVM) this.mViewModel).selectSceneDialogEvent == null || ((ActPanelNightGetUpVM) this.mViewModel).selectSceneDialogEvent.getValue() == null) {
            return;
        }
        if (((ActPanelNightGetUpVM) this.mViewModel).selectSceneDialogEvent.getValue().intValue() == 0) {
            ((ActPanelNightGetUpVM) this.mViewModel).excSceneText.setValue(sceneBySceneId.getSceneName());
        } else {
            ((ActPanelNightGetUpVM) this.mViewModel).restartSceneText.setValue(sceneBySceneId.getSceneName());
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (((ActPanelNightGetUpVM) this.mViewModel).bundle != null) {
            Intent intent = new Intent();
            intent.putExtra("data", ((ActPanelNightGetUpVM) this.mViewModel).bundle);
            setResult(5008, intent);
        }
        super.back();
    }
}