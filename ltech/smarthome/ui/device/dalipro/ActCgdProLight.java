package com.ltech.smarthome.ui.device.dalipro;

import android.graphics.Typeface;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActCgdProLightBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import java.util.Objects;

/* loaded from: classes4.dex */
public class ActCgdProLight extends BaseControlActivity<ActCgdProLightBinding, ActCgdProLightVM> {
    private int currentTab;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_cgd_pro_light;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActCgdProLightBinding) this.mViewBinding).title.grayLine.setVisibility(8);
        ((ActCgdProLightVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Device deviceById = Injection.repo().device().getDeviceById(((ActCgdProLightVM) this.mViewModel).controlId);
        if (deviceById == null) {
            finishActivity();
        } else {
            ((ActCgdProLightVM) this.mViewModel).initFragmentList(deviceById.getDeviceId());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ((ActCgdProLightVM) this.mViewModel).fragmentList.get(0)).commit();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActCgdProLightVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLight$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCgdProLight.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActCgdProLightVM) this.mViewModel).chooseTabEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLight$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCgdProLight.this.lambda$startObserve$1((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        setTitle(device.getDeviceName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        AppCompatTextView appCompatTextView = ((ActCgdProLightBinding) this.mViewBinding).tvDaliScene;
        int intValue = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        int i = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
        AppCompatTextView appCompatTextView2 = ((ActCgdProLightBinding) this.mViewBinding).tvDaliScene;
        int intValue2 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        appCompatTextView2.setTypeface(intValue2 == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView3 = ((ActCgdProLightBinding) this.mViewBinding).tvGroupAndAddress;
        int intValue3 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        appCompatTextView3.setBackgroundResource(intValue3 == 1 ? R.drawable.shape_white_round_bg_10 : 0);
        AppCompatTextView appCompatTextView4 = ((ActCgdProLightBinding) this.mViewBinding).tvGroupAndAddress;
        int intValue4 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        appCompatTextView4.setTypeface(intValue4 == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView5 = ((ActCgdProLightBinding) this.mViewBinding).tvBroadcast;
        int intValue5 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        if (intValue5 != 2) {
            i = 0;
        }
        appCompatTextView5.setBackgroundResource(i);
        AppCompatTextView appCompatTextView6 = ((ActCgdProLightBinding) this.mViewBinding).tvBroadcast;
        int intValue6 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        appCompatTextView6.setTypeface(intValue6 == 2 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        View view = ((ActCgdProLightBinding) this.mViewBinding).leftLine;
        int intValue7 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        view.setVisibility(intValue7 == 2 ? 0 : 8);
        View view2 = ((ActCgdProLightBinding) this.mViewBinding).rightLine;
        int intValue8 = num.intValue();
        Objects.requireNonNull((ActCgdProLightVM) this.mViewModel);
        view2.setVisibility(intValue8 != 0 ? 8 : 0);
        changeFragment(num.intValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActCgdProLightVM) this.mViewModel).controlObject.getValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        Device deviceById = Injection.repo().device().getDeviceById(((ActCgdProLightVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActCgdProLightVM) this.mViewModel).controlObject.setValue(deviceById);
        }
    }

    private void changeFragment(int tabPosition) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.hide(((ActCgdProLightVM) this.mViewModel).fragmentList.get(this.currentTab));
        this.currentTab = tabPosition;
        if (((ActCgdProLightVM) this.mViewModel).fragmentList.get(this.currentTab).isAdded()) {
            beginTransaction.show(((ActCgdProLightVM) this.mViewModel).fragmentList.get(this.currentTab));
        } else {
            beginTransaction.add(R.id.fragment_container, ((ActCgdProLightVM) this.mViewModel).fragmentList.get(this.currentTab));
        }
        beginTransaction.commit();
    }
}