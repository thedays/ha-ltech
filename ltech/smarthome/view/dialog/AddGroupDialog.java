package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.databinding.ObservableField;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogAddGroupBinding;
import com.ltech.smarthome.view.dialog.AddGroupDialog;

/* loaded from: classes4.dex */
public class AddGroupDialog extends SmartDialog<DialogAddGroupBinding> {
    private String cancelString;
    private IAction<String> confirmAction;
    private String confirmString;
    private ObservableField<String> content = new ObservableField<>();
    private String contentTip;
    private String hint;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_add_group;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.AddGroupDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAddGroupBinding, AddGroupDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogAddGroupBinding viewBinding, final AddGroupDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.tvContentTip.setText(dialog.contentTip);
            viewBinding.setContent(dialog.content);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.AddGroupDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    AddGroupDialog.AnonymousClass1.lambda$convertView$0(AddGroupDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(AddGroupDialog addGroupDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_delete) {
                addGroupDialog.setContent("");
                return;
            }
            if (id != R.id.tv_cancel) {
                if (id == R.id.tv_confirm && addGroupDialog.confirmAction != null) {
                    addGroupDialog.confirmAction.act((String) addGroupDialog.content.get());
                    return;
                }
                return;
            }
            addGroupDialog.dismissDialog();
        }
    }

    public static AddGroupDialog asDefault() {
        return (AddGroupDialog) new AddGroupDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setY(16).setMargin(16);
    }

    public AddGroupDialog setContent(String content) {
        this.content.set(content);
        return this;
    }

    public AddGroupDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddGroupDialog setConfirmAction(IAction<String> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public AddGroupDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public AddGroupDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public AddGroupDialog setContentTip(String contentTip) {
        this.contentTip = contentTip;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "add_group_dialog";
    }
}