package com.ltech.smarthome.ui.replace;

import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActReplaceBinding;
import com.ltech.smarthome.model.Injection;

/* loaded from: classes4.dex */
public class ActReplace extends VMActivity<ActReplaceBinding, ActReplaceVM> {
    private static final int REPLACE_FAIL = 200;
    private static final int REPLACE_SUCCESS = 100;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_replace;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(0);
        setTitle(getString(R.string.device_replace));
        ((ActReplaceVM) this.mViewModel).setContext(this);
        ((ActReplaceVM) this.mViewModel).oldDevice = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.replace.ActReplace$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActReplace.this.lambda$initView$0();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        ((ActReplaceVM) this.mViewModel).startReplace(ReplaceHelper.instance().newDevice);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (((ActReplaceVM) this.mViewModel).progress.getValue().intValue() == 100) {
            setResult(3023);
            finish();
        } else {
            super.back();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (((ActReplaceVM) this.mViewModel).progress.getValue().intValue() == 100 || ((ActReplaceVM) this.mViewModel).progress.getValue().intValue() == 200) {
            super.onBackPressed();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActReplaceVM) this.mViewModel).totalProgress.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.replace.ActReplace$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActReplace.this.lambda$startObserve$1((Integer) obj);
            }
        });
        ((ActReplaceVM) this.mViewModel).progress.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.replace.ActReplace$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActReplace.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        ((ActReplaceBinding) this.mViewBinding).seekbar.setMax(num.intValue());
        ((ActReplaceBinding) this.mViewBinding).seekbar.setProgress(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        setUpgradeView(num.intValue());
    }

    private void setUpgradeView(int progress) {
        if (this.mViewBinding == 0) {
            return;
        }
        if (progress == 100) {
            setBackImage(R.mipmap.icon_back);
            ((ActReplaceBinding) this.mViewBinding).seekbar.setProgress(((ActReplaceBinding) this.mViewBinding).seekbar.getMax());
            ((ActReplaceBinding) this.mViewBinding).ivReplace.setImageResource(R.mipmap.ic_replace_succes);
            ((ActReplaceBinding) this.mViewBinding).tvReplace.setText(R.string.str_replace_success);
            ((ActReplaceBinding) this.mViewBinding).tvReplaceTip.setVisibility(0);
            ((ActReplaceBinding) this.mViewBinding).tvReplaceTip.setText(R.string.old_device_restore);
            ((ActReplaceBinding) this.mViewBinding).seekbar.setVisibility(8);
            return;
        }
        if (progress == 200) {
            setBackImage(R.mipmap.icon_back);
            ((ActReplaceBinding) this.mViewBinding).ivReplace.setImageResource(R.mipmap.ic_replace_fail);
            ((ActReplaceBinding) this.mViewBinding).tvReplace.setText(R.string.str_replace_fail);
            ((ActReplaceBinding) this.mViewBinding).tvReplaceTip.setVisibility(8);
            ((ActReplaceBinding) this.mViewBinding).seekbar.setVisibility(8);
            ((ActReplaceBinding) this.mViewBinding).tvTip.setVisibility(0);
            return;
        }
        setBackImage(0);
        ((ActReplaceBinding) this.mViewBinding).ivReplace.setImageResource(R.mipmap.ic_replacing);
        ((ActReplaceBinding) this.mViewBinding).tvReplace.setText(R.string.str_replacing);
        if (((ActReplaceBinding) this.mViewBinding).tvReplaceTip.getVisibility() == 8 || ((ActReplaceBinding) this.mViewBinding).seekbar.getVisibility() == 8) {
            ((ActReplaceBinding) this.mViewBinding).tvReplaceTip.setVisibility(0);
            ((ActReplaceBinding) this.mViewBinding).seekbar.setVisibility(0);
        }
        ((ActReplaceBinding) this.mViewBinding).seekbar.setProgress(progress);
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        ((ActReplaceVM) this.mViewModel).nextStep();
    }

    public void setStatus(int status) {
        ((ActReplaceVM) this.mViewModel).setStatus(status);
    }
}