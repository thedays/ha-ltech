package com.ltech.smarthome.ui.device.ir;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtTvNumBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class FtTvNum extends VMFragment<FtTvNumBinding, BaseIrVM> {
    private Class clazz;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_tv_num;
    }

    public static FtTvNum newInstance(Class clazz) {
        FtTvNum ftTvNum = new FtTvNum();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.TV_NUMBER, clazz);
        ftTvNum.setArguments(bundle);
        return ftTvNum;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMFragment
    public BaseIrVM getViewModel() {
        this.clazz = (Class) getArguments().getSerializable(Constants.TV_NUMBER);
        return (BaseIrVM) new ViewModelProvider(getActivity()).get(this.clazz);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtTvNumBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.FtTvNum$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtTvNum.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        ((BaseIrVM) this.mViewModel).clickAnimate(view);
        switch (view.getId()) {
            case R.id.tv_0 /* 2131298414 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(56);
                break;
            case R.id.tv_1 /* 2131298415 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(61);
                break;
            case R.id.tv_2 /* 2131298417 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(66);
                break;
            case R.id.tv_3 /* 2131298419 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(71);
                break;
            case R.id.tv_4 /* 2131298421 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(76);
                break;
            case R.id.tv_5 /* 2131298423 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(81);
                break;
            case R.id.tv_6 /* 2131298424 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(86);
                break;
            case R.id.tv_7 /* 2131298425 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(91);
                break;
            case R.id.tv_8 /* 2131298426 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(96);
                break;
            case R.id.tv_9 /* 2131298427 */:
                ((BaseIrVM) this.mViewModel).sendIrControl(101);
                break;
        }
    }
}