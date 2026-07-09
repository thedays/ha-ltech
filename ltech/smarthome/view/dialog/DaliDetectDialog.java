package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogDaliDetectBinding;

/* loaded from: classes4.dex */
public class DaliDetectDialog extends SmartDialog<DialogDaliDetectBinding> {
    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_dali_detect;
    }

    public static DaliDetectDialog asDefault(final int progress) {
        return (DaliDetectDialog) new DaliDetectDialog().setViewConverter(new SmartDialog.ViewConverter<DialogDaliDetectBinding, DaliDetectDialog>() { // from class: com.ltech.smarthome.view.dialog.DaliDetectDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogDaliDetectBinding viewBinding, DaliDetectDialog dialog) {
                dialog.initView(viewBinding, progress);
            }
        }).setWidth(125).setHeight(125).setGravity(17).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(DialogDaliDetectBinding viewBinding, int progress) {
        setProgress(progress);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dali_detect_dialog";
    }

    public void setProgress(int progress) {
        if (this.mViewBinding != 0) {
            ((DialogDaliDetectBinding) this.mViewBinding).tvCount.setText(progress + "");
        }
    }
}