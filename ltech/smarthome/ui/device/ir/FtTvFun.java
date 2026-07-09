package com.ltech.smarthome.ui.device.ir;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtTvFunBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.DirectionView;

/* loaded from: classes4.dex */
public class FtTvFun extends VMFragment<FtTvFunBinding, BaseIrVM> {
    private Class clazz;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_tv_fun;
    }

    public static FtTvFun newInstance(Class clazz) {
        FtTvFun ftTvFun = new FtTvFun();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.TV_FUN, clazz);
        ftTvFun.setArguments(bundle);
        return ftTvFun;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMFragment
    public BaseIrVM getViewModel() {
        this.clazz = (Class) getArguments().getSerializable(Constants.TV_FUN);
        return (BaseIrVM) new ViewModelProvider(getActivity()).get(this.clazz);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (ActTvBoxVM.class.equals(this.clazz)) {
            initTvBoxView();
        } else if (ActProjectorVM.class.equals(this.clazz)) {
            initProjectorView();
        } else if (ActTvVM.class.equals(this.clazz)) {
            initTvView();
        } else if (ActStbVM.class.equals(this.clazz)) {
            initStbView();
        }
        ((FtTvFunBinding) this.mViewBinding).directionView.setDirectionClickListener(new DirectionView.IDirectionClickListener() { // from class: com.ltech.smarthome.ui.device.ir.FtTvFun$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.DirectionView.IDirectionClickListener
            public final void onDirection(int i) {
                FtTvFun.this.lambda$initView$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i) {
        if (i == 0) {
            ((BaseIrVM) this.mViewModel).sendIrControl(42);
        } else if (i == 1) {
            ((BaseIrVM) this.mViewModel).sendIrControl(46);
        } else if (i == 2) {
            ((BaseIrVM) this.mViewModel).sendIrControl(47);
        } else if (i == 3) {
            ((BaseIrVM) this.mViewModel).sendIrControl(48);
        } else if (i == 4) {
            ((BaseIrVM) this.mViewModel).sendIrControl(49);
        }
        ((BaseIrVM) this.mViewModel).setSendState();
    }

    private void initStbView() {
        ((FtTvFunBinding) this.mViewBinding).tvSignal.setVisibility(4);
        ((FtTvFunBinding) this.mViewBinding).ivHome.setVisibility(0);
        ((FtTvFunBinding) this.mViewBinding).ivMenu.setVisibility(0);
        ((FtTvFunBinding) this.mViewBinding).ivHome.setImageResource(R.mipmap.icon_ir_replay);
        ((FtTvFunBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.FtTvFun$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtTvFun.this.lambda$initStbView$1((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initStbView$1(View view) {
        switch (view.getId()) {
            case R.id.iv_ch_plus /* 2131296970 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(43);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_ch_reduce /* 2131296971 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(44);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_home /* 2131297093 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(146);
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
                break;
            case R.id.iv_menu /* 2131297133 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(45);
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
                break;
            case R.id.iv_vol_plus /* 2131297313 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(50);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_vol_reduce /* 2131297314 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(51);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
        }
    }

    private void initProjectorView() {
        ((FtTvFunBinding) this.mViewBinding).tvChannel.setText(getString(R.string.zoom));
        ((FtTvFunBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.FtTvFun$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtTvFun.this.lambda$initProjectorView$2((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initProjectorView$2(View view) {
        switch (view.getId()) {
            case R.id.iv_ch_plus /* 2131296970 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(IrKeyRepository.ID_ZOOM_UP);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_ch_reduce /* 2131296971 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(IrKeyRepository.ID_ZOOM_DOWN);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_vol_plus /* 2131297313 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(50);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_vol_reduce /* 2131297314 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(51);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
        }
    }

    private void initTvBoxView() {
        ((FtTvFunBinding) this.mViewBinding).tvSignal.setVisibility(4);
        ((FtTvFunBinding) this.mViewBinding).ivHome.setVisibility(0);
        ((FtTvFunBinding) this.mViewBinding).ivMenu.setVisibility(0);
        ((FtTvFunBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.FtTvFun$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtTvFun.this.lambda$initTvBoxView$3((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTvBoxView$3(View view) {
        switch (view.getId()) {
            case R.id.iv_ch_plus /* 2131296970 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(43);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_ch_reduce /* 2131296971 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(44);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_home /* 2131297093 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(136);
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
                break;
            case R.id.iv_menu /* 2131297133 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(45);
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
                break;
            case R.id.iv_vol_plus /* 2131297313 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(50);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_vol_reduce /* 2131297314 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(51);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
        }
    }

    private void initTvView() {
        ((FtTvFunBinding) this.mViewBinding).tvSignal.setVisibility(0);
        ((FtTvFunBinding) this.mViewBinding).ivMenu.setVisibility(0);
        ((FtTvFunBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.FtTvFun$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtTvFun.this.lambda$initTvView$4((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTvView$4(View view) {
        switch (view.getId()) {
            case R.id.iv_ch_plus /* 2131296970 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(43);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_ch_reduce /* 2131296971 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(44);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_menu /* 2131297133 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(45);
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
                break;
            case R.id.iv_vol_plus /* 2131297313 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(50);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.iv_vol_reduce /* 2131297314 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(51);
                ((BaseIrVM) this.mViewModel).setSendState();
                break;
            case R.id.tv_signal /* 2131298972 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(111);
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
                break;
        }
    }
}