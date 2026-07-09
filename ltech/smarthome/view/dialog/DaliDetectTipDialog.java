package com.ltech.smarthome.view.dialog;

import android.animation.ValueAnimator;
import android.os.CountDownTimer;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogDaliDetectTipBinding;
import com.ltech.smarthome.view.dialog.DaliDetectTipDialog;

/* loaded from: classes4.dex */
public class DaliDetectTipDialog extends SmartDialog<DialogDaliDetectTipBinding> {
    private CountDownTimer countDownTimer;
    private OnCreateDetectDialogListener onCreateDetectDialogListener;
    private boolean isShowTip = false;
    private int tipType = 1;
    private int progress = 0;

    public interface OnCreateDetectDialogListener {
        void onCreateDetectDialog(DaliDetectDialog dialog);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_dali_detect_tip;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.DaliDetectTipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogDaliDetectTipBinding, DaliDetectTipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogDaliDetectTipBinding viewBinding, final DaliDetectTipDialog dialog) {
            dialog.initView(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.DaliDetectTipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    DaliDetectTipDialog.AnonymousClass1.lambda$convertView$0(DaliDetectTipDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(DaliDetectTipDialog daliDetectTipDialog, View view) {
            if (view.getId() != R.id.iv_doubt) {
                return;
            }
            daliDetectTipDialog.showOrCloseTip();
        }
    }

    public static DaliDetectTipDialog asDefault() {
        return (DaliDetectTipDialog) new DaliDetectTipDialog().setViewConverter(new AnonymousClass1()).setWidth(0).setHeight(0).setGravity(17).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogDaliDetectTipBinding viewBinding) {
        CountDownTimer countDownTimer = new CountDownTimer(601000L, 1000L) { // from class: com.ltech.smarthome.view.dialog.DaliDetectTipDialog.2
            @Override // android.os.CountDownTimer
            public void onTick(long l) {
                DaliDetectTipDialog.this.setTip(l, viewBinding);
                viewBinding.progress.setProgress((int) (600 - (l / 1000)));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                DaliDetectTipDialog.this.dismissDialog();
            }
        };
        this.countDownTimer = countDownTimer;
        countDownTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTip(long l, DialogDaliDetectTipBinding viewBinding) {
        if (l % 3 == 0) {
            int i = this.tipType;
            if (i == 1) {
                this.tipType = i + 1;
                viewBinding.tvTip.setText(R.string.dali_detect_tip1);
            } else if (i == 2) {
                this.tipType = i + 1;
                viewBinding.tvTip.setText(R.string.dali_detect_tip2);
            } else {
                this.tipType = 1;
                viewBinding.tvTip.setText(R.string.dali_detect_tip3);
            }
        }
    }

    public void showOrCloseTip() {
        if (!this.isShowTip) {
            this.isShowTip = true;
            ((DialogDaliDetectTipBinding) this.mViewBinding).layoutTip.setVisibility(0);
        } else {
            this.isShowTip = false;
            ((DialogDaliDetectTipBinding) this.mViewBinding).layoutTip.setVisibility(4);
        }
    }

    public void toDetectDialog() {
        this.countDownTimer.cancel();
        ValueAnimator duration = ValueAnimator.ofInt(((DialogDaliDetectTipBinding) this.mViewBinding).progress.getProgress(), 600).setDuration(1200L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.dialog.DaliDetectTipDialog$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DaliDetectTipDialog.this.lambda$toDetectDialog$0(valueAnimator);
            }
        });
        duration.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toDetectDialog$0(ValueAnimator valueAnimator) {
        if (this.mViewBinding != 0) {
            ((DialogDaliDetectTipBinding) this.mViewBinding).progress.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
        if (((Integer) valueAnimator.getAnimatedValue()).intValue() == 600) {
            DaliDetectDialog asDefault = DaliDetectDialog.asDefault(this.progress);
            OnCreateDetectDialogListener onCreateDetectDialogListener = this.onCreateDetectDialogListener;
            if (onCreateDetectDialogListener != null) {
                onCreateDetectDialogListener.onCreateDetectDialog(asDefault);
            }
            asDefault.showDialog(getActivity());
            dismissDialog();
        }
    }

    public DaliDetectTipDialog setOnCreateDetectDialogListener(OnCreateDetectDialogListener onCreateDetectDialogListener) {
        this.onCreateDetectDialogListener = onCreateDetectDialogListener;
        return this;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dali_detect_tip_dialog";
    }
}