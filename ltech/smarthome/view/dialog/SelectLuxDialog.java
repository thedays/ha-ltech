package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectLuxBinding;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.dialog.SelectLuxDialog;

/* loaded from: classes4.dex */
public class SelectLuxDialog extends SmartDialog<DialogSelectLuxBinding> {
    private int lux;
    private boolean luxEnable;
    private OnSaveListener onSaveListener;
    private String title;

    public interface OnSaveListener {
        void onSave(int lux);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_lux;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectLuxDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectLuxBinding, SelectLuxDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectLuxBinding viewBinding, final SelectLuxDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectLuxDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectLuxDialog.AnonymousClass1.lambda$convertView$0(SelectLuxDialog.this, viewBinding, (View) obj);
                }
            }));
            dialog.luxEnable = dialog.lux != 0;
            viewBinding.seekbarLux.setRange(1, 1000);
            dialog.lux = Math.max(1, dialog.lux);
            viewBinding.seekbarLux.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.SelectLuxDialog.1.1
                @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
                public void onProgressChanged(int v, boolean finish, boolean fromUser) {
                    dialog.lux = v;
                    viewBinding.seekbarLux.setValue(dialog.getLuxString());
                }
            });
            viewBinding.seekbarLux.setProgress(dialog.lux);
            viewBinding.seekbarLux.setValue(dialog.getLuxString());
            dialog.chooseMode(viewBinding);
            viewBinding.seekbarLux.setEnabled(dialog.luxEnable);
        }

        static /* synthetic */ void lambda$convertView$0(SelectLuxDialog selectLuxDialog, DialogSelectLuxBinding dialogSelectLuxBinding, View view) {
            switch (view.getId()) {
                case R.id.layout_choose_one /* 2131297395 */:
                    selectLuxDialog.luxEnable = true;
                    selectLuxDialog.chooseMode(dialogSelectLuxBinding);
                    dialogSelectLuxBinding.seekbarLux.setEnabled(true);
                    break;
                case R.id.layout_choose_two /* 2131297396 */:
                    selectLuxDialog.luxEnable = false;
                    selectLuxDialog.chooseMode(dialogSelectLuxBinding);
                    dialogSelectLuxBinding.seekbarLux.setEnabled(false);
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    selectLuxDialog.dismissDialog();
                    break;
                case R.id.tv_confirm /* 2131298530 */:
                    if (selectLuxDialog.onSaveListener != null) {
                        selectLuxDialog.onSaveListener.onSave(selectLuxDialog.luxEnable ? selectLuxDialog.lux : 0);
                    }
                    selectLuxDialog.dismissDialog();
                    break;
            }
        }
    }

    public static SelectLuxDialog asDefault() {
        return (SelectLuxDialog) new SelectLuxDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseMode(DialogSelectLuxBinding viewBinding) {
        AppCompatImageView appCompatImageView = viewBinding.ivSelect;
        boolean z = this.luxEnable;
        int i = R.mipmap.ic_tick_sel;
        appCompatImageView.setBackgroundResource(z ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
        AppCompatImageView appCompatImageView2 = viewBinding.ivSelectTwo;
        if (this.luxEnable) {
            i = R.mipmap.ic_tick_default;
        }
        appCompatImageView2.setBackgroundResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getLuxString() {
        int i = this.lux;
        return i == 0 ? getString(R.string.illuminance_value_disable) : getString(R.string.lux_value, Integer.valueOf(i));
    }

    public SelectLuxDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectLuxDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    public SelectLuxDialog setLux(int lux) {
        this.lux = lux;
        return this;
    }
}