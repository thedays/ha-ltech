package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogE6TipBinding;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;

/* loaded from: classes4.dex */
public class E6TipDialog extends SmartDialog<DialogE6TipBinding> {
    private int type = 1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_e6_tip;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    public static E6TipDialog asDefault() {
        return (E6TipDialog) new E6TipDialog().setViewConverter(new SmartDialog.ViewConverter<DialogE6TipBinding, E6TipDialog>() { // from class: com.ltech.smarthome.view.dialog.E6TipDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogE6TipBinding viewBinding, E6TipDialog dialog) {
                E6Helper.instance().initKnobInfoView(dialog.requireContext(), viewBinding.rvKnobAction, dialog.type);
            }
        }).setGravity(17).setY(16).setMargin(16);
    }

    public E6TipDialog setType(int type) {
        this.type = type;
        return this;
    }
}