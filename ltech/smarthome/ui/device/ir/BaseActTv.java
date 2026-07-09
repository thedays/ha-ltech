package com.ltech.smarthome.ui.device.ir;

import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActTvBinding;
import com.ltech.smarthome.ui.device.ir.BaseTvVM;
import java.util.Locale;

/* loaded from: classes4.dex */
public abstract class BaseActTv<VM extends BaseTvVM> extends BaseIrVMActivity<ActTvBinding, VM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_tv;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseTvVM) this.mViewModel).changeFragmentEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseActTv$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseActTv.this.lambda$startObserve$0((String) obj);
            }
        });
        ((BaseTvVM) this.mViewModel).changeFragmentEvent.setValue(BaseTvVM.KEY_FUN);
        ((BaseTvVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseActTv$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseActTv.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        ((BaseTvVM) this.mViewModel).currentFragmentKeyEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseActTv$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseActTv.this.lambda$startObserve$2((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ((BaseTvVM) this.mViewModel).switchFragment(this, str, R.id.frame_layout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActTvBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActTvBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (BaseTvVM.KEY_FUN.equals(str)) {
            ((ActTvBinding) this.mViewBinding).tvNum.setBackgroundResource(R.mipmap.icon_ir_bg);
            ((ActTvBinding) this.mViewBinding).tvNum.setText(getString(R.string.key_num));
            ((ActTvBinding) this.mViewBinding).tvMore.setBackgroundResource(R.mipmap.icon_ir_bg);
            ((ActTvBinding) this.mViewBinding).tvMore.setText(getString(R.string.more));
            return;
        }
        if (BaseTvVM.KEY_NUM.equals(str)) {
            ((ActTvBinding) this.mViewBinding).tvNum.setBackgroundResource(R.mipmap.icon_ir_down);
            ((ActTvBinding) this.mViewBinding).tvNum.setText("");
            ((ActTvBinding) this.mViewBinding).tvMore.setBackgroundResource(R.mipmap.icon_ir_bg);
            ((ActTvBinding) this.mViewBinding).tvMore.setText(getString(R.string.more));
            return;
        }
        if (BaseTvVM.KEY_EXT_FUN.equals(str)) {
            ((ActTvBinding) this.mViewBinding).tvNum.setBackgroundResource(R.mipmap.icon_ir_bg);
            ((ActTvBinding) this.mViewBinding).tvNum.setText(getString(R.string.key_num));
            ((ActTvBinding) this.mViewBinding).tvMore.setBackgroundResource(R.mipmap.icon_ir_down);
            ((ActTvBinding) this.mViewBinding).tvMore.setText("");
        }
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void getRcCodeSuccess(int ridPos) {
        ((ActTvBinding) this.mViewBinding).cardviewAdd.setVisibility(0);
        ((ActTvBinding) this.mViewBinding).tvIndex.setText(String.format(Locale.US, "%d/%d", Integer.valueOf(ridPos + 1), Integer.valueOf(((BaseTvVM) this.mViewModel).rids.size())));
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void initControlView() {
        setEditImage(R.mipmap.ic_setting);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void initSelectActionView() {
        setEditString(getString(R.string.save));
    }
}