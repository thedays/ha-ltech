package com.ltech.smarthome.ui.scene;

import android.text.TextUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActXySelectBinding;
import com.ltech.smarthome.ltnfc.utils.BrightUtils;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.XYCoordinateView;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActXySelect extends BaseNormalActivity<ActXySelectBinding> {
    public int brt = 255;
    private boolean isE6;
    public float xValue;
    public float yValue;

    private float convertToXY(int value) {
        return (value / 65535.0f) * 1000.0f;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_xy_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.set_xy_brt));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        ((ActXySelectBinding) this.mViewBinding).xyCoordinateView.setOnColorChangedListener(new XYCoordinateView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActXySelect$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.XYCoordinateView.OnColorChangedListener
            public final void onColorChanged(int i, float f, float f2, boolean z) {
                ActXySelect.this.lambda$initView$0(i, f, f2, z);
            }
        });
        ((ActXySelectBinding) this.mViewBinding).layoutX.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActXySelect$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActXySelect.this.lambda$initView$1(i, z, z2);
            }
        });
        ((ActXySelectBinding) this.mViewBinding).layoutY.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActXySelect$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActXySelect.this.lambda$initView$2(i, z, z2);
            }
        });
        ((ActXySelectBinding) this.mViewBinding).layoutBrt.setRange(1, 255);
        ((ActXySelectBinding) this.mViewBinding).layoutBrt.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActXySelect$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActXySelect.this.lambda$initView$3(i, z, z2);
            }
        });
        initData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, float f, float f2, boolean z) {
        this.xValue = f * 1000.0f;
        this.yValue = f2 * 1000.0f;
        ((ActXySelectBinding) this.mViewBinding).layoutX.setProgress(Math.round(this.xValue));
        ((ActXySelectBinding) this.mViewBinding).layoutY.setProgress(Math.round(this.yValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, boolean z, boolean z2) {
        float f = i;
        float f2 = f / 1000.0f;
        ((ActXySelectBinding) this.mViewBinding).layoutX.setValue(String.format(Locale.US, "%.3f", Float.valueOf(f2)));
        if (!z2 || ((ActXySelectBinding) this.mViewBinding).xyCoordinateView.changePoint(f2, this.yValue / 1000.0f)) {
            this.xValue = f;
        } else if (z) {
            ((ActXySelectBinding) this.mViewBinding).layoutX.setProgress(Math.round(this.xValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, boolean z, boolean z2) {
        float f = i;
        float f2 = f / 1000.0f;
        ((ActXySelectBinding) this.mViewBinding).layoutY.setValue(String.format(Locale.US, "%.3f", Float.valueOf(f2)));
        if (!z2 || ((ActXySelectBinding) this.mViewBinding).xyCoordinateView.changePoint(this.xValue / 1000.0f, f2)) {
            this.yValue = f;
        } else if (z) {
            ((ActXySelectBinding) this.mViewBinding).layoutY.setProgress(Math.round(this.yValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i, boolean z, boolean z2) {
        this.brt = i;
        ((ActXySelectBinding) this.mViewBinding).layoutBrt.setValue(getBrtString(i));
    }

    private void initData() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.IS_E6, false);
        this.isE6 = booleanExtra;
        if (booleanExtra && !TextUtils.isEmpty(E6Helper.instance().getActionInstruct())) {
            String actionInstruct = E6Helper.instance().getActionInstruct();
            if (Integer.parseInt(actionInstruct.substring(0, 2), 16) == 32) {
                this.xValue = convertToXY(Integer.parseInt(actionInstruct.substring(4, 6) + actionInstruct.substring(2, 4), 16));
                this.yValue = convertToXY(Integer.parseInt(actionInstruct.substring(8, 10) + actionInstruct.substring(6, 8), 16));
                this.brt = Integer.parseInt(actionInstruct.substring(10, 12), 16);
            }
        }
        ((ActXySelectBinding) this.mViewBinding).layoutX.setProgress(Math.round(this.xValue));
        ((ActXySelectBinding) this.mViewBinding).layoutY.setProgress(Math.round(this.yValue));
        ((ActXySelectBinding) this.mViewBinding).layoutBrt.setProgress(this.brt);
        ((ActXySelectBinding) this.mViewBinding).layoutBrt.setValue(getBrtString(this.brt));
    }

    private String getBrtString(int brt) {
        return BrightUtils.getLogPercent().get(Integer.valueOf(brt));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        E6Helper.instance().selectXy(this.xValue, this.yValue, this.brt);
    }
}