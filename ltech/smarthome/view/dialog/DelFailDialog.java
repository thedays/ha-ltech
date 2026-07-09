package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogDelFailBinding;
import com.ltech.smarthome.view.dialog.DelFailDialog;

/* loaded from: classes4.dex */
public class DelFailDialog extends SmartDialog<DialogDelFailBinding> {
    private String cancelString;
    private IAction<Void> confirmAction;
    private String confirmString;
    private String content;
    private String content2;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_del_fail;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.DelFailDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogDelFailBinding, DelFailDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogDelFailBinding viewBinding, final DelFailDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvCancel.setVisibility(TextUtils.isEmpty(dialog.cancelString) ? 8 : 0);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.tvConfirm.setVisibility(TextUtils.isEmpty(dialog.confirmString) ? 8 : 0);
            viewBinding.tvContent.setText(dialog.content);
            if (!TextUtils.isEmpty(dialog.content2)) {
                viewBinding.vLine.setVisibility(0);
                viewBinding.tvContent2.setVisibility(0);
                viewBinding.tvContent2.setText(dialog.content2);
            } else {
                viewBinding.vLine.setVisibility(8);
                viewBinding.tvContent2.setVisibility(8);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.DelFailDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    DelFailDialog.AnonymousClass1.lambda$convertView$0(DelFailDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(DelFailDialog delFailDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                delFailDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                delFailDialog.dismissDialog();
                if (delFailDialog.confirmAction != null) {
                    delFailDialog.confirmAction.act(null);
                }
            }
        }
    }

    public static DelFailDialog asDefault() {
        return (DelFailDialog) new DelFailDialog().setViewConverter(new AnonymousClass1()).setGravity(17).setMargin(16);
    }

    public DelFailDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public DelFailDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public DelFailDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public DelFailDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public DelFailDialog setContent2(String content2) {
        this.content2 = content2;
        return this;
    }

    public DelFailDialog setConfirmAction(IAction<Void> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "del_fail_dialog";
    }
}