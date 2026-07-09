package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.widget.SeekBar;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectVolumeBinding;
import com.ltech.smarthome.view.dialog.SelectVolumeDialog;

/* loaded from: classes4.dex */
public class SelectVolumeDialog extends SmartDialog<DialogSelectVolumeBinding> {
    private int brt;
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_volume;
    }

    public static SelectVolumeDialog asDefault() {
        return asDefault(false);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectVolumeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectVolumeBinding, SelectVolumeDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectVolumeBinding viewBinding, final SelectVolumeDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            if (this.val$needConfirm) {
                if (!StringUtils.isEmpty(dialog.cancelString)) {
                    viewBinding.tvCancel.setText(dialog.cancelString);
                }
                if (!StringUtils.isEmpty(dialog.confirmString)) {
                    viewBinding.tvConfirm.setText(dialog.confirmString);
                }
            } else {
                viewBinding.tvCancel.setText("");
                viewBinding.tvConfirm.setText("");
            }
            viewBinding.sbBrt.setProgress(dialog.brt);
            viewBinding.tvBrt.setVisibility(4);
            final boolean z = this.val$needConfirm;
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectVolumeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectVolumeDialog.AnonymousClass1.lambda$convertView$0(SelectVolumeDialog.this, z, (View) obj);
                }
            }));
            viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectVolumeDialog.1.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    dialog.brt = progress;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (AnonymousClass1.this.val$needConfirm || dialog.confirmAction == null) {
                        return;
                    }
                    dialog.confirmAction.act(Integer.valueOf(dialog.brt));
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(SelectVolumeDialog selectVolumeDialog, boolean z, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectVolumeDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (z && selectVolumeDialog.confirmAction != null) {
                    selectVolumeDialog.confirmAction.act(Integer.valueOf(selectVolumeDialog.brt));
                }
                selectVolumeDialog.dismissDialog();
            }
        }
    }

    public static SelectVolumeDialog asDefault(boolean needConfirm) {
        return (SelectVolumeDialog) new SelectVolumeDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
    }

    public SelectVolumeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectVolumeDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectVolumeDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectVolumeDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectVolumeDialog setVolume(int brt) {
        this.brt = brt;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_volume_dialog";
    }
}