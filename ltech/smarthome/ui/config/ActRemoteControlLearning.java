package com.ltech.smarthome.ui.config;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActProductIntroduction1Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class ActRemoteControlLearning extends BaseNormalActivity<ActProductIntroduction1Binding> {
    private Device device;
    private final int STEP_ONE = 0;
    private final int STEP_TWO = 1;
    private int state = 0;
    private int time = 10;
    private final MyHandler handler = new MyHandler(this);

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_product_introduction_1;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setView();
        this.device = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActProductIntroduction1Binding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActRemoteControlLearning$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActRemoteControlLearning.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.bt_next || this.device == null) {
            return;
        }
        next();
    }

    private void next() {
        setView();
        this.time = 10;
        this.handler.removeMessages(1);
        this.handler.sendEmptyMessageDelayed(1, 1000L);
        ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setEnabled(false);
        showLoadingDialog(getResources().getString(R.string.matching), 10000);
        CmdAssistant.getDeviceAssistant(this.device, new int[0]).remoteControlLearning(ActivityUtils.getTopActivity(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.config.ActRemoteControlLearning.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                ActRemoteControlLearning.this.handler.removeMessages(1);
                ActRemoteControlLearning.this.handler.sendEmptyMessageDelayed(2, 1000L);
                if (aBoolean.booleanValue()) {
                    ActRemoteControlLearning.this.dismissLoadingDialog();
                    ActRemoteControlLearning actRemoteControlLearning = ActRemoteControlLearning.this;
                    actRemoteControlLearning.showSuccessDialog(actRemoteControlLearning.getResources().getString(R.string.matching_success));
                } else {
                    ActRemoteControlLearning.this.dismissLoadingDialog();
                    ActRemoteControlLearning actRemoteControlLearning2 = ActRemoteControlLearning.this;
                    actRemoteControlLearning2.showErrorDialog(actRemoteControlLearning2.getResources().getString(R.string.matching_timeout));
                }
            }
        });
    }

    private void setView() {
        int i = this.state;
        if (i == 0) {
            ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTitle.setText(getString(R.string.step_one));
            ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_remote_control_tip1));
            ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur_rfremote1);
            ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(getString(R.string.next));
            this.state = 1;
            return;
        }
        if (i != 1) {
            return;
        }
        ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTitle.setText(getString(R.string.step_two));
        ((ActProductIntroduction1Binding) this.mViewBinding).tvConfigTip.setText(getString(R.string.add_remote_control_tip2));
        ((ActProductIntroduction1Binding) this.mViewBinding).ivConfigTip.setBackgroundResource(R.mipmap.add_cgcur_rfremote2);
        ((ActProductIntroduction1Binding) this.mViewBinding).btNext.setText(String.format(getString(R.string.matching_countdown), 10));
    }

    private static class MyHandler extends Handler {
        WeakReference<ActRemoteControlLearning> weakReference;

        public MyHandler(ActRemoteControlLearning activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            ActRemoteControlLearning actRemoteControlLearning = this.weakReference.get();
            int i = msg.what;
            if (i != 1) {
                if (i == 2 && actRemoteControlLearning != null) {
                    actRemoteControlLearning.finishActivity();
                    return;
                }
                return;
            }
            if (actRemoteControlLearning == null || actRemoteControlLearning.time <= 0) {
                return;
            }
            actRemoteControlLearning.time--;
            ((ActProductIntroduction1Binding) actRemoteControlLearning.mViewBinding).btNext.setText(String.format(actRemoteControlLearning.getString(R.string.matching_countdown), Integer.valueOf(actRemoteControlLearning.time)));
            sendEmptyMessageDelayed(1, 1000L);
        }
    }
}