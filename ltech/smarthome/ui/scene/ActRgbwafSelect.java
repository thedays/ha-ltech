package com.ltech.smarthome.ui.scene;

import android.text.TextUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActRgbwafSelectBinding;
import com.ltech.smarthome.ltnfc.utils.BrightUtils;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes4.dex */
public class ActRgbwafSelect extends BaseNormalActivity<ActRgbwafSelectBinding> {

    /* renamed from: a, reason: collision with root package name */
    public int f6274a;

    /* renamed from: b, reason: collision with root package name */
    public int f6275b;
    public int brt;
    public int f;
    public int g;
    private boolean isE6;
    public int r;
    public int w;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rgbwaf_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.set_rgbwaf_brt));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarR.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$0(i, z, z2);
            }
        });
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarG.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$1(i, z, z2);
            }
        });
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarB.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$2(i, z, z2);
            }
        });
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarW.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$3(i, z, z2);
            }
        });
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarA.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$4(i, z, z2);
            }
        });
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarF.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$5(i, z, z2);
            }
        });
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarBrt.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActRgbwafSelect$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActRgbwafSelect.this.lambda$initView$6(i, z, z2);
            }
        });
        initData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, boolean z, boolean z2) {
        this.r = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, boolean z, boolean z2) {
        this.g = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, boolean z, boolean z2) {
        this.f6275b = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i, boolean z, boolean z2) {
        this.w = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(int i, boolean z, boolean z2) {
        this.f6274a = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(int i, boolean z, boolean z2) {
        this.f = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(int i, boolean z, boolean z2) {
        this.brt = i;
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarBrt.setValue(getBrtString(i));
    }

    private void initData() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.IS_E6, false);
        this.isE6 = booleanExtra;
        if (booleanExtra && !TextUtils.isEmpty(E6Helper.instance().getActionInstruct())) {
            String actionInstruct = E6Helper.instance().getActionInstruct();
            if (Integer.parseInt(actionInstruct.substring(0, 2), 16) == 33) {
                this.r = Integer.parseInt(actionInstruct.substring(2, 4), 16);
                this.g = Integer.parseInt(actionInstruct.substring(4, 6), 16);
                this.f6275b = Integer.parseInt(actionInstruct.substring(6, 8), 16);
                this.w = Integer.parseInt(actionInstruct.substring(8, 10), 16);
                this.f6274a = Integer.parseInt(actionInstruct.substring(10, 12), 16);
                this.f = Integer.parseInt(actionInstruct.substring(12, 14), 16);
                this.brt = Integer.parseInt(actionInstruct.substring(14, 16), 16);
            }
        }
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarR.setProgress(this.r);
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarG.setProgress(this.g);
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarB.setProgress(this.f6275b);
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarW.setProgress(this.w);
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarA.setProgress(this.f6274a);
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarF.setProgress(this.f);
        ((ActRgbwafSelectBinding) this.mViewBinding).seekbarBrt.setProgress(this.brt);
    }

    private String getBrtString(int brt) {
        return BrightUtils.getLogPercent().get(Integer.valueOf(brt));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        E6Helper.instance().selectRgbwaf(this.r, this.g, this.f6275b, this.w, this.f6274a, this.f, this.brt);
    }
}