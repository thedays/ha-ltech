package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectCtBinding;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.dialog.SelectCtDialog;

/* loaded from: classes4.dex */
public class SelectCtDialog extends SmartDialog<DialogSelectCtBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String title;
    private int wy;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_ct;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectCtDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectCtBinding, SelectCtDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectCtBinding viewBinding, final SelectCtDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectCtDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectCtDialog.AnonymousClass1.lambda$convertView$0(SelectCtDialog.this, (View) obj);
                }
            }));
            new CtControlHelper(viewBinding.sbCt, viewBinding.tvCt, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectCtDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
                public final void onRangeChanged(int i, boolean z) {
                    SelectCtDialog.this.wy = 255 - i;
                }
            });
            viewBinding.sbCt.setProgress(255 - dialog.wy);
            viewBinding.tvCt.setText(LightUtils.ctY2K(dialog.wy, 10000, 1000) + "K");
        }

        static /* synthetic */ void lambda$convertView$0(SelectCtDialog selectCtDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectCtDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectCtDialog.confirmAction != null) {
                    selectCtDialog.confirmAction.act(Integer.valueOf(selectCtDialog.wy));
                }
                selectCtDialog.dismissDialog();
            }
        }
    }

    public static SelectCtDialog asDefault() {
        return (SelectCtDialog) new SelectCtDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public SelectCtDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectCtDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectCtDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectCtDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectCtDialog setWy(int wy) {
        this.wy = wy;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }
}