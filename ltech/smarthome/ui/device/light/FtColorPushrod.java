package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtColorPushrodBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes4.dex */
public class FtColorPushrod extends BaseNormalFragment<FtColorPushrodBinding> {
    private ActColorCCLightVM mViewModel;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_color_pushrod;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        this.mViewModel = (ActColorCCLightVM) new ViewModelProvider(requireActivity()).get(ActColorCCLightVM.class);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarTotalBrt.setRange(1, 100, 0);
        initListener();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.mViewModel.refreshObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtColorPushrod.this.lambda$startObserve$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Boolean bool) {
        if (!bool.booleanValue()) {
            ActColorCCLightVM actColorCCLightVM = this.mViewModel;
            actColorCCLightVM.red = actColorCCLightVM.deviceState.getRed();
            ActColorCCLightVM actColorCCLightVM2 = this.mViewModel;
            actColorCCLightVM2.green = actColorCCLightVM2.deviceState.getGreen();
            ActColorCCLightVM actColorCCLightVM3 = this.mViewModel;
            actColorCCLightVM3.blue = actColorCCLightVM3.deviceState.getBlue();
            ActColorCCLightVM actColorCCLightVM4 = this.mViewModel;
            actColorCCLightVM4.f6271c = actColorCCLightVM4.deviceState.getCold();
            ActColorCCLightVM actColorCCLightVM5 = this.mViewModel;
            actColorCCLightVM5.w = actColorCCLightVM5.deviceState.getWarm();
            ActColorCCLightVM actColorCCLightVM6 = this.mViewModel;
            actColorCCLightVM6.totalBrt = actColorCCLightVM6.deviceState.getTotalBrt();
        }
        ((FtColorPushrodBinding) this.mViewBinding).seekbarR.setProgress(this.mViewModel.red);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarG.setProgress(this.mViewModel.green);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarB.setProgress(this.mViewModel.blue);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarC.setProgress(this.mViewModel.f6271c);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarW.setProgress(this.mViewModel.w);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarTotalBrt.setProgress(this.mViewModel.totalBrt);
        ((FtColorPushrodBinding) this.mViewBinding).seekbarTotalBrt.setValue(LightUtils.getProgressHasBelowOne(this.mViewModel.totalBrt));
    }

    private void initListener() {
        ((FtColorPushrodBinding) this.mViewBinding).seekbarR.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorPushrod.this.lambda$initListener$1(i, z);
            }
        });
        ((FtColorPushrodBinding) this.mViewBinding).seekbarG.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorPushrod.this.lambda$initListener$2(i, z);
            }
        });
        ((FtColorPushrodBinding) this.mViewBinding).seekbarB.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorPushrod.this.lambda$initListener$3(i, z);
            }
        });
        ((FtColorPushrodBinding) this.mViewBinding).seekbarC.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorPushrod.this.lambda$initListener$4(i, z);
            }
        });
        ((FtColorPushrodBinding) this.mViewBinding).seekbarW.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorPushrod.this.lambda$initListener$5(i, z);
            }
        });
        ((FtColorPushrodBinding) this.mViewBinding).seekbarTotalBrt.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorPushrod$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorPushrod.this.lambda$initListener$6(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$1(int i, boolean z) {
        this.mViewModel.red = i;
        sendRgbcw(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$2(int i, boolean z) {
        this.mViewModel.green = i;
        sendRgbcw(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$3(int i, boolean z) {
        this.mViewModel.blue = i;
        sendRgbcw(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$4(int i, boolean z) {
        this.mViewModel.f6271c = i;
        sendRgbcw(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$5(int i, boolean z) {
        this.mViewModel.w = i;
        sendRgbcw(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$6(int i, boolean z) {
        ((FtColorPushrodBinding) this.mViewBinding).seekbarTotalBrt.setValue(LightUtils.getProgressHasBelowOne(i));
        this.mViewModel.setTotalBrt(LightUtils.progress2BrtHasBelowOne(i));
        sendRgbcw(z);
    }

    private void sendRgbcw(boolean finish) {
        this.mViewModel.getLightCmdHelper().sendRgbcw(getContext(), this.mViewModel.totalBrt, this.mViewModel.red, this.mViewModel.green, this.mViewModel.blue, this.mViewModel.f6271c, this.mViewModel.w, finish);
    }
}