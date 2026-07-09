package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogProgressBinding;
import com.ltech.smarthome.view.dialog.ProgressDialog;

/* loaded from: classes4.dex */
public class ProgressDialog extends SmartDialog<DialogProgressBinding> {
    private long currentProgressValue;
    private OnSelectListener mOnSelectListener;
    private long maxProgressValue;
    private int progress;
    private String title;
    private DialogProgressBinding viewBinding;

    public interface OnSelectListener {
        void cancel();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_progress;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.ProgressDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogProgressBinding, ProgressDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogProgressBinding viewBinding, final ProgressDialog dialog) {
            dialog.viewBinding = viewBinding;
            viewBinding.txtDialogTitle.setText(dialog.title);
            viewBinding.progressBar.setProgress(dialog.progress);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.ProgressDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ProgressDialog.AnonymousClass1.lambda$convertView$0(ProgressDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(ProgressDialog progressDialog, View view) {
            if (view.getId() != R.id.btn_selectNegative) {
                return;
            }
            progressDialog.dismissDialog();
            if (progressDialog.mOnSelectListener != null) {
                progressDialog.mOnSelectListener.cancel();
            }
        }
    }

    public static ProgressDialog asDefault() {
        return (ProgressDialog) new ProgressDialog().setViewConverter(new AnonymousClass1()).setOutCancel(false).setGravity(17).setMargin(15);
    }

    public ProgressDialog setOnSelectListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
        return this;
    }

    public ProgressDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        DialogProgressBinding dialogProgressBinding = this.viewBinding;
        if (dialogProgressBinding != null) {
            dialogProgressBinding.progressBar.setProgress(progress);
            this.viewBinding.txtDialogTitle.setText(this.title + "(" + progress + "%)");
        }
    }

    public void setMaxProgressValue(long maxProgressValue) {
        this.maxProgressValue = maxProgressValue;
    }

    public void setCurrentProgressValue(long currentProgressValue) {
        this.currentProgressValue = currentProgressValue;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_progress";
    }
}