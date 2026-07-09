package com.ltech.smarthome.view.dialog;

import android.os.CountDownTimer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogDaliLoadDataBinding;

/* loaded from: classes4.dex */
public class DaliLoadDataDialog extends SmartDialog<DialogDaliLoadDataBinding> {
    private CountDownTimer countDownTimer;
    private OnFinishListener onFinishListener;

    public interface OnFinishListener {
        void onFinish();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_dali_load_data;
    }

    public static DaliLoadDataDialog asDefault() {
        return (DaliLoadDataDialog) new DaliLoadDataDialog().setViewConverter(new SmartDialog.ViewConverter<DialogDaliLoadDataBinding, DaliLoadDataDialog>() { // from class: com.ltech.smarthome.view.dialog.DaliLoadDataDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogDaliLoadDataBinding viewBinding, DaliLoadDataDialog dialog) {
                dialog.initView(viewBinding);
            }
        }).setWidth(300).setHeight(285).setGravity(17);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogDaliLoadDataBinding viewBinding) {
        CountDownTimer countDownTimer = new CountDownTimer(121000L, 1000L) { // from class: com.ltech.smarthome.view.dialog.DaliLoadDataDialog.2
            @Override // android.os.CountDownTimer
            public void onTick(long l) {
                long j = l / 1000;
                viewBinding.tvTime.setText(DaliLoadDataDialog.this.getProgressText((int) j));
                viewBinding.progress.setProgress((int) (120 - j));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                DaliLoadDataDialog.this.dismissDialog();
                if (DaliLoadDataDialog.this.onFinishListener != null) {
                    DaliLoadDataDialog.this.onFinishListener.onFinish();
                }
            }
        };
        this.countDownTimer = countDownTimer;
        countDownTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getProgressText(int progress) {
        return String.format("%d:%02d", Integer.valueOf(progress / 60), Integer.valueOf(progress % 60));
    }

    public void updateProgress(int cur) {
        ((DialogDaliLoadDataBinding) this.mViewBinding).tvDetection.setText(String.format(getString(R.string.dali_address_detecting), Integer.valueOf(cur)));
    }

    public DaliLoadDataDialog setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dali_load_data_dialog";
    }
}