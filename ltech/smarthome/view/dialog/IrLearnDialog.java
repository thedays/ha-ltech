package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogIrLearnBinding;
import java.util.Locale;

/* loaded from: classes4.dex */
public class IrLearnDialog extends SmartDialog<DialogIrLearnBinding> {
    private int total;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_ir_learn;
    }

    public static IrLearnDialog asDefault() {
        return (IrLearnDialog) new IrLearnDialog().setViewConverter(new SmartDialog.ViewConverter<DialogIrLearnBinding, IrLearnDialog>() { // from class: com.ltech.smarthome.view.dialog.IrLearnDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogIrLearnBinding viewBinding, IrLearnDialog dialog) {
                viewBinding.tvTime.setText(String.format(Locale.US, "%d%s", Integer.valueOf(dialog.total), dialog.getContext().getString(R.string.sec)));
            }
        }).setGravity(17).setOutCancel(true).setMargin(40);
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void reduce() {
        int i = this.total - 1;
        this.total = i;
        if (i <= 0) {
            dismissDialog();
        } else {
            notifyDialog();
        }
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "ir_learn_dialog";
    }
}