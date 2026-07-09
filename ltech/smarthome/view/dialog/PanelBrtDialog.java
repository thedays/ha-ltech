package com.ltech.smarthome.view.dialog;

import android.widget.SeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogPanelBrtBinding;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.PanelBrtDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class PanelBrtDialog extends SmartDialog<DialogPanelBrtBinding> {
    private int brtProgress;
    private String brtTip;
    private boolean onOff;
    private String onOffTip;
    private OnStateChangedListener stateChangedListener;

    public interface OnStateChangedListener {
        void onBrtChanged(int brtProgress, boolean finish);

        void onOnOffChanged(boolean on);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_panel_brt;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.PanelBrtDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPanelBrtBinding, PanelBrtDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogPanelBrtBinding viewBinding, final PanelBrtDialog dialog) {
            LHomeLog.i(LightQuickDialog.class, "convertView.progress" + dialog.brtProgress);
            viewBinding.tvBrtTip.setText(dialog.brtTip);
            viewBinding.tvOnOff.setText(dialog.onOffTip);
            viewBinding.sbBrt.setProgress(dialog.brtProgress);
            viewBinding.tvBrt.setText(viewBinding.sbBrt.getProgressHasBelowOne());
            viewBinding.sbOn.setCheckedNotByUser(dialog.onOff);
            viewBinding.sbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.PanelBrtDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    PanelBrtDialog.AnonymousClass1.lambda$convertView$0(PanelBrtDialog.this, viewBinding, switchButton, z);
                }
            });
            viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.PanelBrtDialog.1.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        dialog.onOff = true;
                        viewBinding.sbOn.setCheckedNotByUser(true);
                        viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                        if (dialog.stateChangedListener != null) {
                            dialog.stateChangedListener.onBrtChanged(seekBar.getProgress(), false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    dialog.onOff = true;
                    viewBinding.sbOn.setCheckedNotByUser(true);
                    viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    if (dialog.stateChangedListener != null) {
                        dialog.stateChangedListener.onBrtChanged(seekBar.getProgress(), true);
                    }
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(PanelBrtDialog panelBrtDialog, DialogPanelBrtBinding dialogPanelBrtBinding, SwitchButton switchButton, boolean z) {
            panelBrtDialog.onOff = z;
            dialogPanelBrtBinding.sbOn.setCheckedNotByUser(z);
            if (panelBrtDialog.stateChangedListener != null) {
                panelBrtDialog.stateChangedListener.onOnOffChanged(panelBrtDialog.onOff);
            }
        }
    }

    public static PanelBrtDialog asDefault() {
        return (PanelBrtDialog) new PanelBrtDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public PanelBrtDialog setOnOffTip(String onOffTip) {
        this.onOffTip = onOffTip;
        return this;
    }

    public PanelBrtDialog setOnOff(boolean onOff) {
        this.onOff = onOff;
        return this;
    }

    public PanelBrtDialog setBrtTip(String brtTip) {
        this.brtTip = brtTip;
        return this;
    }

    public PanelBrtDialog setBrtProgress(int brtProgress) {
        this.brtProgress = brtProgress;
        return this;
    }

    public PanelBrtDialog setStateChangedListener(OnStateChangedListener stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "panel_brt_dialog";
    }
}