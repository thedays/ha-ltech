package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtColorHslBinding;
import com.ltech.smarthome.view.HslColorPickerView;
import com.ltech.smarthome.view.StepSetView;
import java.util.Locale;

/* loaded from: classes4.dex */
public class FtColorHSL extends BaseNormalFragment<FtColorHslBinding> {
    private ActColorCCLightVM mViewModel;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_color_hsl;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        this.mViewModel = (ActColorCCLightVM) new ViewModelProvider(requireActivity()).get(ActColorCCLightVM.class);
        ((FtColorHslBinding) this.mViewBinding).layoutH.setValue(String.format(Locale.US, "%d°", 0));
        ((FtColorHslBinding) this.mViewBinding).layoutS.setValue(String.valueOf(0));
        ((FtColorHslBinding) this.mViewBinding).layoutL.setRange(1, 100);
        ((FtColorHslBinding) this.mViewBinding).ccpv.setOnColorChangedListener(new HslColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorHSL$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.HslColorPickerView.OnColorChangedListener
            public final void onColorChanged(int i, float f, float f2, boolean z) {
                FtColorHSL.this.lambda$initView$0(i, f, f2, z);
            }
        });
        ((FtColorHslBinding) this.mViewBinding).layoutH.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorHSL$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorHSL.this.lambda$initView$1(i, z, z2);
            }
        });
        ((FtColorHslBinding) this.mViewBinding).layoutS.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorHSL$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorHSL.this.lambda$initView$2(i, z, z2);
            }
        });
        ((FtColorHslBinding) this.mViewBinding).layoutL.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorHSL$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                FtColorHSL.this.lambda$initView$3(i, z, z2);
            }
        });
        ((FtColorHslBinding) this.mViewBinding).layoutL.setProgress(50);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, float f, float f2, boolean z) {
        ((FtColorHslBinding) this.mViewBinding).layoutH.setProgress((int) f);
        ((FtColorHslBinding) this.mViewBinding).layoutS.setProgress((int) f2);
        sendRgb(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, boolean z, boolean z2) {
        this.mViewModel.hValue = i;
        ((FtColorHslBinding) this.mViewBinding).layoutH.setValue(String.format(Locale.US, "%d°", Integer.valueOf(i)));
        ((FtColorHslBinding) this.mViewBinding).ccpv.setPoint(i, ((FtColorHslBinding) this.mViewBinding).layoutS.getProgress());
        if (z2) {
            sendRgb(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, boolean z, boolean z2) {
        this.mViewModel.sValue = i;
        ((FtColorHslBinding) this.mViewBinding).ccpv.setPoint(((FtColorHslBinding) this.mViewBinding).layoutH.getProgress(), i);
        if (z2) {
            sendRgb(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i, boolean z, boolean z2) {
        this.mViewModel.lValue = i;
        ((FtColorHslBinding) this.mViewBinding).layoutL.setValue(String.format(Locale.US, "%d%%", Integer.valueOf(i)));
        if (z2) {
            sendRgb(z);
        }
    }

    private void sendRgb(boolean finish) {
        this.mViewModel.getLightCmdHelper().sendHSL(getContext(), this.mViewModel.hValue, this.mViewModel.sValue, this.mViewModel.lValue, finish);
    }
}