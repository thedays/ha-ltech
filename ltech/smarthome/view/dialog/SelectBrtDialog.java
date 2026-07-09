package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.widget.SeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectBrtBinding;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.SelectBrtDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class SelectBrtDialog extends SmartDialog<DialogSelectBrtBinding> {
    private int brt;
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private boolean halfMode;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_brt;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectBrtDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectBrtBinding, SelectBrtDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectBrtBinding viewBinding, final SelectBrtDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectBrtDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectBrtDialog.AnonymousClass1.lambda$convertView$0(SelectBrtDialog.this, (View) obj);
                }
            }));
            viewBinding.sbBrt.setMax(dialog.halfMode ? 50 : 110);
            viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.SelectBrtDialog.1.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    dialog.brt = progress;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            });
            LHomeLog.i(LightQuickDialog.class, "init=" + dialog.brt);
            viewBinding.sbBrt.setProgress(dialog.brt);
            viewBinding.tvBrt.setText(viewBinding.sbBrt.getProgressHasBelowOne());
        }

        static /* synthetic */ void lambda$convertView$0(SelectBrtDialog selectBrtDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectBrtDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectBrtDialog.confirmAction != null) {
                    selectBrtDialog.confirmAction.act(Integer.valueOf(selectBrtDialog.brt));
                }
                selectBrtDialog.dismissDialog();
            }
        }
    }

    public static SelectBrtDialog asDefault(boolean needConfirm) {
        return (SelectBrtDialog) new SelectBrtDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public SelectBrtDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectBrtDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectBrtDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectBrtDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectBrtDialog setBrt(int brt) {
        this.brt = brt;
        return this;
    }

    public SelectBrtDialog setHalfMode(boolean halfMode) {
        this.halfMode = halfMode;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_brt_dialog";
    }
}