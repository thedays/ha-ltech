package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogCenterTipBinding;
import com.ltech.smarthome.view.dialog.CenterTipDialog;

/* loaded from: classes4.dex */
public class CenterTipDialog extends SmartDialog<DialogCenterTipBinding> {
    private String cancelString;
    private int confirmColor;
    private String confirmString;
    private String message;
    private OnConfirmListener onConfirmListener;
    private String title;

    public interface OnConfirmListener {
        void onConfirm();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_center_tip;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CenterTipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCenterTipBinding, CenterTipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCenterTipBinding viewBinding, final CenterTipDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            if (dialog.confirmString == null || dialog.confirmString.isEmpty()) {
                viewBinding.tvConfirm.setVisibility(8);
            }
            viewBinding.tvTip.setText(dialog.message);
            if (dialog.message == null || dialog.message.isEmpty()) {
                viewBinding.tvTip.setVisibility(8);
            }
            if (dialog.confirmColor != 0) {
                viewBinding.tvConfirm.setTextColor(dialog.confirmColor);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CenterTipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CenterTipDialog.AnonymousClass1.lambda$convertView$0(CenterTipDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(CenterTipDialog centerTipDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                centerTipDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (centerTipDialog.onConfirmListener != null) {
                    centerTipDialog.onConfirmListener.onConfirm();
                }
                centerTipDialog.dismissDialog();
            }
        }
    }

    public static CenterTipDialog asDefault() {
        return (CenterTipDialog) new CenterTipDialog().setViewConverter(new AnonymousClass1()).setWidth(300).setHeight(0).setGravity(17);
    }

    public CenterTipDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CenterTipDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public CenterTipDialog setMessageString(String message) {
        this.message = message;
        return this;
    }

    public CenterTipDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public CenterTipDialog setConfirmColor(int color) {
        this.confirmColor = color;
        return this;
    }

    public CenterTipDialog setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "center_tip_dialog";
    }
}