package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectDimCurveBinding;

/* loaded from: classes4.dex */
public class SelectDimCurveDialog extends SmartDialog<DialogSelectDimCurveBinding> {
    private OnSaveListener onSaveListener;
    private int position = 1;

    public interface OnSaveListener {
        void onSave(int position);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_dim_curve;
    }

    public static SelectDimCurveDialog asDefault() {
        return (SelectDimCurveDialog) new SelectDimCurveDialog().setViewConverter(new SmartDialog.ViewConverter<DialogSelectDimCurveBinding, SelectDimCurveDialog>() { // from class: com.ltech.smarthome.view.dialog.SelectDimCurveDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogSelectDimCurveBinding viewBinding, SelectDimCurveDialog dialog) {
                dialog.initView(viewBinding);
            }
        }).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    public SelectDimCurveDialog setPosition(int position) {
        this.position = position;
        return this;
    }

    public SelectDimCurveDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogSelectDimCurveBinding viewBinding) {
        viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDimCurveDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                SelectDimCurveDialog.this.lambda$initView$0(viewBinding, (View) obj);
            }
        }));
        setSelect(viewBinding, this.position);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(DialogSelectDimCurveBinding dialogSelectDimCurveBinding, View view) {
        switch (view.getId()) {
            case R.id.layout_linear /* 2131297522 */:
                setSelect(dialogSelectDimCurveBinding, 2);
                break;
            case R.id.layout_log /* 2131297526 */:
                setSelect(dialogSelectDimCurveBinding, 1);
                break;
            case R.id.tv_cancel /* 2131298504 */:
                dismissDialog();
                break;
            case R.id.tv_save /* 2131298929 */:
                OnSaveListener onSaveListener = this.onSaveListener;
                if (onSaveListener != null) {
                    onSaveListener.onSave(this.position);
                }
                dismissDialog();
                break;
        }
    }

    private void setSelect(DialogSelectDimCurveBinding viewBinding, int i) {
        if (i == 1) {
            viewBinding.ivLogSelect.setImageResource(R.mipmap.ic_curve_select);
            viewBinding.ivLinearSelect.setImageResource(R.mipmap.ic_curve_not_select);
        } else {
            viewBinding.ivLogSelect.setImageResource(R.mipmap.ic_curve_not_select);
            viewBinding.ivLinearSelect.setImageResource(R.mipmap.ic_curve_select);
        }
        this.position = i;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_select_dim_curve";
    }
}