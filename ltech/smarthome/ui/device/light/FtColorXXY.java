package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtColorXyyBinding;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.XYCoordinateView;
import java.util.Locale;

/* loaded from: classes4.dex */
public class FtColorXXY extends BaseNormalFragment<FtColorXyyBinding> {
    private ActColorCCLightVM mViewModel;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_color_xyy;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        this.mViewModel = (ActColorCCLightVM) new ViewModelProvider(requireActivity()).get(ActColorCCLightVM.class);
        ((FtColorXyyBinding) this.mViewBinding).xyCoordinateView.setOnColorChangedListener(new XYCoordinateView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorXXY$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.XYCoordinateView.OnColorChangedListener
            public final void onColorChanged(int i, float f, float f2, boolean z) {
                FtColorXXY.this.lambda$initView$0(i, f, f2, z);
            }
        });
        ((FtColorXyyBinding) this.mViewBinding).layoutX.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorXXY$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorXXY.this.lambda$initView$1(i, z, z2);
            }
        });
        ((FtColorXyyBinding) this.mViewBinding).layoutY.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorXXY$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorXXY.this.lambda$initView$2(i, z, z2);
            }
        });
        ((FtColorXyyBinding) this.mViewBinding).layoutX.setProgress(500);
        ((FtColorXyyBinding) this.mViewBinding).layoutY.setProgress(500);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, float f, float f2, boolean z) {
        this.mViewModel.xValue = f;
        this.mViewModel.yValue = f2;
        ((FtColorXyyBinding) this.mViewBinding).layoutX.setProgress((int) (f * 1000.0f));
        ((FtColorXyyBinding) this.mViewBinding).layoutY.setProgress((int) (f2 * 1000.0f));
        sendRgb(this.mViewModel.xValue, this.mViewModel.yValue, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, boolean z, boolean z2) {
        float f = i / 1000.0f;
        ((FtColorXyyBinding) this.mViewBinding).layoutX.setValue(String.format(Locale.US, "%.3f", Float.valueOf(f)));
        if (z2) {
            if (!((FtColorXyyBinding) this.mViewBinding).xyCoordinateView.changePoint(f, this.mViewModel.yValue)) {
                if (z) {
                    ((FtColorXyyBinding) this.mViewBinding).layoutX.setProgress((int) (this.mViewModel.xValue * 1000.0f));
                    return;
                }
                return;
            }
            sendRgb(f, this.mViewModel.yValue, z);
        }
        this.mViewModel.xValue = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, boolean z, boolean z2) {
        float f = i / 1000.0f;
        ((FtColorXyyBinding) this.mViewBinding).layoutY.setValue(String.format(Locale.US, "%.3f", Float.valueOf(f)));
        if (z2) {
            if (!((FtColorXyyBinding) this.mViewBinding).xyCoordinateView.changePoint(this.mViewModel.xValue, f)) {
                if (z) {
                    ((FtColorXyyBinding) this.mViewBinding).layoutY.setProgress((int) (this.mViewModel.yValue * 1000.0f));
                    return;
                }
                return;
            }
            sendRgb(this.mViewModel.xValue, f, z);
        }
        this.mViewModel.yValue = f;
    }

    private void sendRgb(float xPercent, float yPercent, boolean finish) {
        this.mViewModel.getLightCmdHelper().sendXYY(getContext(), (int) (xPercent * 10000.0f), (int) (yPercent * 10000.0f), finish);
    }
}