package com.ltech.smarthome.ui.device.mesh_gateway;

import android.os.Bundle;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActNetConnectBinding;
import com.ltech.smarthome.view.TimeProgressBar;

/* loaded from: classes4.dex */
public class BaseConnectNetActivity extends BaseNormalActivity<ActNetConnectBinding> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_net_connect;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActNetConnectBinding) this.mViewBinding).timeBar.setState(2);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActNetConnectBinding) this.mViewBinding).timeBar.destroy();
        super.onDestroy();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActNetConnectBinding) this.mViewBinding).timeBar.setTotalProgress(90);
        ((ActNetConnectBinding) this.mViewBinding).timeBar.setUnit(getString(R.string.s));
        ((ActNetConnectBinding) this.mViewBinding).timeBar.setOnListener(new TimeProgressBar.onListener() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity.1
            @Override // com.ltech.smarthome.view.TimeProgressBar.onListener
            public void onFail() {
                BaseConnectNetActivity.this.connectFail();
            }

            @Override // com.ltech.smarthome.view.TimeProgressBar.onListener
            public void onSuccess() {
                BaseConnectNetActivity.this.connectSuccess();
            }

            @Override // com.ltech.smarthome.view.TimeProgressBar.onListener
            public void onLoading() {
                BaseConnectNetActivity.this.startConfig();
            }
        });
        ((ActNetConnectBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.BaseConnectNetActivity$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                BaseConnectNetActivity.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        back();
    }

    protected void connectSuccess() {
        setSuccessView();
    }

    protected void connectFail() {
        setFailView();
    }

    protected void startConfig() {
        setLoadingView();
    }

    private void setFailView() {
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setVisibility(8);
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setVisibility(8);
        ((ActNetConnectBinding) this.mViewBinding).groupFail.setVisibility(0);
        ((ActNetConnectBinding) this.mViewBinding).btNext.setText(getString(R.string.config_again));
        ((ActNetConnectBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.config_fail));
        ((ActNetConnectBinding) this.mViewBinding).tvConfigTip2.setText(getString(R.string.tip_possible_problem, new Object[]{getString(R.string.tip_help)}));
    }

    private void setLoadingView() {
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setVisibility(0);
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setVisibility(0);
        ((ActNetConnectBinding) this.mViewBinding).btNext.setText(getString(R.string.cancel));
        ((ActNetConnectBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.config_wait));
        ((ActNetConnectBinding) this.mViewBinding).tvConfigTip2.setText(getString(R.string.config_close_tip));
    }

    private void setSuccessView() {
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip1.setVisibility(8);
        ((ActNetConnectBinding) this.mViewBinding).tvProcessTip2.setVisibility(8);
        ((ActNetConnectBinding) this.mViewBinding).btNext.setText(getString(R.string.finish));
        ((ActNetConnectBinding) this.mViewBinding).tvConfigTip.setText(getString(R.string.config_success));
        ((ActNetConnectBinding) this.mViewBinding).tvConfigTip2.setVisibility(8);
    }
}