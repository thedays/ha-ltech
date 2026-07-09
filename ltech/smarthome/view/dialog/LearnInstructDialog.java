package com.ltech.smarthome.view.dialog;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogLearnInstructBinding;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.LearnInstructDialog;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class LearnInstructDialog extends SmartDialog<DialogLearnInstructBinding> {
    private String cancelString;
    private IAction<String> confirmAction;
    private String confirmString;
    private DialogLearnInstructBinding mViewBinding;
    private CountDownTimer timer = new CountDownTimer(30000, 1000) { // from class: com.ltech.smarthome.view.dialog.LearnInstructDialog.1
        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            String str = Math.min((millisUntilFinished / 1000) + 1, 30L) + LearnInstructDialog.this.getString(R.string.sec);
            LearnInstructDialog.this.mViewBinding.tvLearn.setBackgroundResource(R.drawable.shape_light_gray_bt2);
            LearnInstructDialog.this.mViewBinding.tvLearn.setText(LearnInstructDialog.this.getString(R.string.start_learn_with_num, str));
            LearnInstructDialog.this.mViewBinding.tvLearn.setEnabled(false);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            LearnInstructDialog.this.finishLearn();
        }
    };
    private ObservableField<String> content = new ObservableField<>();

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_learn_instruct;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.LearnInstructDialog$2, reason: invalid class name */
    class AnonymousClass2 extends SmartDialog.ViewConverter<DialogLearnInstructBinding, LearnInstructDialog> {
        AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogLearnInstructBinding viewBinding, final LearnInstructDialog dialog) {
            dialog.mViewBinding = viewBinding;
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setContent(dialog.content);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.LearnInstructDialog$2$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    LearnInstructDialog.AnonymousClass2.lambda$convertView$1(LearnInstructDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$1(final LearnInstructDialog learnInstructDialog, View view) {
            switch (view.getId()) {
                case R.id.iv_copy /* 2131296989 */:
                    if (learnInstructDialog.content.get() != null || !TextUtils.isEmpty((CharSequence) learnInstructDialog.content.get())) {
                        Utils.copyText(learnInstructDialog.requireContext(), (String) learnInstructDialog.content.get());
                        break;
                    }
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    learnInstructDialog.timer.cancel();
                    learnInstructDialog.dismissDialog();
                    break;
                case R.id.tv_confirm /* 2131298530 */:
                    String str = (String) learnInstructDialog.content.get();
                    String str2 = str != null ? str : "";
                    if (learnInstructDialog.confirmAction != null) {
                        learnInstructDialog.confirmAction.act(str2);
                    }
                    learnInstructDialog.timer.cancel();
                    learnInstructDialog.dismissDialog();
                    break;
                case R.id.tv_learn /* 2131298736 */:
                    learnInstructDialog.content.set("");
                    learnInstructDialog.timer.start();
                    Cg485Helper.learnInstruct(learnInstructDialog.requireContext(), new IAction() { // from class: com.ltech.smarthome.view.dialog.LearnInstructDialog$2$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            LearnInstructDialog.AnonymousClass2.lambda$convertView$0(LearnInstructDialog.this, (ResponseMsg) obj);
                        }
                    });
                    break;
            }
        }

        static /* synthetic */ void lambda$convertView$0(LearnInstructDialog learnInstructDialog, ResponseMsg responseMsg) {
            if (responseMsg == null || responseMsg.getStateCode() != 0) {
                return;
            }
            learnInstructDialog.content.set(Cg485Helper.getInputCmd(responseMsg.getResData().substring(16)));
            learnInstructDialog.finishLearn();
            learnInstructDialog.timer.cancel();
        }
    }

    public static LearnInstructDialog asDefault() {
        return (LearnInstructDialog) new LearnInstructDialog().setViewConverter(new AnonymousClass2()).setGravity(80).setY(16).setMargin(16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLearn() {
        this.mViewBinding.tvLearn.setBackgroundResource(R.drawable.shape_blue_bt);
        this.mViewBinding.tvLearn.setText(R.string.learn_again);
        this.mViewBinding.tvLearn.setEnabled(true);
    }

    public LearnInstructDialog setContent(String content) {
        this.content.set(content);
        return this;
    }

    public LearnInstructDialog setConfirmAction(IAction<String> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public LearnInstructDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public LearnInstructDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "learn_instruct_dialog";
    }
}