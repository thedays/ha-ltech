package com.ltech.smarthome.view.dialog;

import android.widget.SeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogWyBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.WyDialog;
import java.util.Locale;

/* loaded from: classes4.dex */
public class WyDialog extends SmartDialog<DialogWyBinding> {
    private static DialogWyBinding mViewBinding;
    private static boolean rgbOn;
    private static boolean wOn;
    private int brtProgress = 0;
    private OnStateChangedListener stateChangedListener;
    private int wy;

    public interface OnStateChangedListener {
        void onRgbOnOffChanged(boolean on);

        void onWyBrtChanged(int brtProgress, boolean finish);

        void onWyChanged(int wy, boolean finish);

        void onWyOnOffChanged(boolean on);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_wy;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.WyDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogWyBinding, WyDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogWyBinding viewBinding, final WyDialog dialog) {
            WyDialog.mViewBinding = viewBinding;
            viewBinding.sbRgbOn.setCheckedNotByUser(WyDialog.rgbOn);
            viewBinding.sbWyOn.setCheckedNotByUser(WyDialog.wOn);
            viewBinding.tvRgbOnOff.setText(dialog.requireContext().getString(R.string.rgb_light_tip));
            viewBinding.sbRgbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.WyDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    WyDialog.AnonymousClass1.lambda$convertView$0(WyDialog.this, switchButton, z);
                }
            });
            viewBinding.tvWyOnOff.setText(dialog.requireContext().getString(R.string.w_light_tip));
            viewBinding.sbWyOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.WyDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    WyDialog.AnonymousClass1.lambda$convertView$1(WyDialog.this, switchButton, z);
                }
            });
            viewBinding.tvBrtTip.setText(dialog.requireContext().getString(R.string.w_value));
            viewBinding.sbBrt.setProgress(dialog.brtProgress);
            viewBinding.tvBrt.setText(viewBinding.sbBrt.getProgressHasBelowOne());
            viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.WyDialog.1.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        viewBinding.sbWyOn.setCheckedNotByUser(true);
                        viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                        if (dialog.stateChangedListener != null) {
                            dialog.stateChangedListener.onWyBrtChanged(seekBar.getProgress(), false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    viewBinding.sbWyOn.setCheckedNotByUser(true);
                    viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    if (dialog.stateChangedListener != null) {
                        dialog.stateChangedListener.onWyBrtChanged(seekBar.getProgress(), true);
                    }
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(WyDialog wyDialog, SwitchButton switchButton, boolean z) {
            if (wyDialog.stateChangedListener != null) {
                wyDialog.stateChangedListener.onRgbOnOffChanged(z);
            }
        }

        static /* synthetic */ void lambda$convertView$1(WyDialog wyDialog, SwitchButton switchButton, boolean z) {
            if (wyDialog.stateChangedListener != null) {
                wyDialog.stateChangedListener.onWyOnOffChanged(z);
            }
        }
    }

    public static WyDialog w() {
        return (WyDialog) new WyDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.WyDialog$2, reason: invalid class name */
    class AnonymousClass2 extends SmartDialog.ViewConverter<DialogWyBinding, WyDialog> {
        AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogWyBinding viewBinding, final WyDialog dialog) {
            WyDialog.mViewBinding = viewBinding;
            viewBinding.sbRgbOn.setCheckedNotByUser(WyDialog.rgbOn);
            viewBinding.sbWyOn.setCheckedNotByUser(WyDialog.wOn);
            viewBinding.tvRgbOnOff.setText(dialog.requireContext().getString(R.string.rgb_light_tip));
            viewBinding.sbRgbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.WyDialog$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    WyDialog.AnonymousClass2.lambda$convertView$0(WyDialog.this, switchButton, z);
                }
            });
            viewBinding.tvWyOnOff.setText(dialog.requireContext().getString(R.string.wy_light_tip));
            viewBinding.sbWyOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.WyDialog$2$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    WyDialog.AnonymousClass2.lambda$convertView$1(WyDialog.this, switchButton, z);
                }
            });
            viewBinding.groupWy.setVisibility(0);
            viewBinding.sbBrt.setProgress(dialog.brtProgress);
            viewBinding.tvBrt.setText(viewBinding.sbBrt.getProgressHasBelowOne());
            viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.WyDialog.2.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                        if (dialog.stateChangedListener != null) {
                            dialog.stateChangedListener.onWyBrtChanged(seekBar.getProgress(), false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    viewBinding.tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    if (dialog.stateChangedListener != null) {
                        dialog.stateChangedListener.onWyBrtChanged(seekBar.getProgress(), true);
                    }
                }
            });
            viewBinding.csbCtBar.setProgress(LightUtils.c2percent(dialog.wy));
            viewBinding.tvCt.setText(String.format(Locale.US, "%d", Integer.valueOf(dialog.wy)));
            viewBinding.csbCtBar.setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener(this) { // from class: com.ltech.smarthome.view.dialog.WyDialog.2.2
                @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
                public void onColorChangedStart() {
                }

                @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
                public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                    if (isFromUser) {
                        viewBinding.sbWyOn.setCheckedNotByUser(true);
                        viewBinding.tvCt.setText(String.format(Locale.US, "%d", Integer.valueOf(LightUtils.percent2C(xProgress))));
                        if (dialog.stateChangedListener != null) {
                            dialog.stateChangedListener.onWyChanged(LightUtils.percent2C(xProgress), false);
                        }
                    }
                }

                @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
                public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                    viewBinding.sbWyOn.setCheckedNotByUser(true);
                    viewBinding.tvCt.setText(String.format(Locale.US, "%d", Integer.valueOf(LightUtils.percent2C(xProgress))));
                    if (dialog.stateChangedListener != null) {
                        dialog.stateChangedListener.onWyChanged(LightUtils.percent2C(xProgress), true);
                    }
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(WyDialog wyDialog, SwitchButton switchButton, boolean z) {
            if (wyDialog.stateChangedListener != null) {
                wyDialog.stateChangedListener.onRgbOnOffChanged(z);
            }
        }

        static /* synthetic */ void lambda$convertView$1(WyDialog wyDialog, SwitchButton switchButton, boolean z) {
            if (wyDialog.stateChangedListener != null) {
                wyDialog.stateChangedListener.onWyOnOffChanged(z);
            }
        }
    }

    public static WyDialog wy() {
        return (WyDialog) new WyDialog().setViewConverter(new AnonymousClass2()).setMargin(16).setY(16).setGravity(80);
    }

    public WyDialog setRgbOn(boolean rgbOn2) {
        rgbOn = rgbOn2;
        return this;
    }

    public WyDialog setWOn(boolean wOn2) {
        wOn = wOn2;
        return this;
    }

    public WyDialog setBrtProgress(int brtProgress) {
        this.brtProgress = brtProgress;
        return this;
    }

    public WyDialog setWy(int wy) {
        this.wy = wy;
        return this;
    }

    public WyDialog setStateChangedListener(OnStateChangedListener stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "wy_dialog";
    }

    public static DialogWyBinding getViewBinding() {
        return mViewBinding;
    }
}