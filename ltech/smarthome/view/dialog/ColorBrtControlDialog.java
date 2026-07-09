package com.ltech.smarthome.view.dialog;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.SeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogColorBrtControlBinding;

/* loaded from: classes4.dex */
public class ColorBrtControlDialog extends SmartDialog<DialogColorBrtControlBinding> {
    private OnColorBrtCallBack callBack;
    private int cw;
    private int cwBrt;
    private int rgbBrt;

    public interface OnColorBrtCallBack {
        void onBrt1Change(int brt, boolean finish);

        void onBrt2Change(int brt, boolean finish);

        void onBrt3Change(int brt, boolean finish);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_color_brt_control;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "ColorBrtControlDialog";
    }

    public static ColorBrtControlDialog asDefault(final int type) {
        return (ColorBrtControlDialog) new ColorBrtControlDialog().setViewConverter(new SmartDialog.ViewConverter<DialogColorBrtControlBinding, ColorBrtControlDialog>() { // from class: com.ltech.smarthome.view.dialog.ColorBrtControlDialog.1
            private CountDownTimer mCountDownTimer;

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(final DialogColorBrtControlBinding viewBinding, final ColorBrtControlDialog dialog) {
                int i = type;
                if (i == 3) {
                    viewBinding.barBrt2.setVisibility(8);
                    viewBinding.barCt.setVisibility(8);
                    viewBinding.tvLabel1.setText(dialog.getString(R.string.brt));
                } else if (i == 4) {
                    viewBinding.barCt.setVisibility(8);
                    viewBinding.tvLabel1.setText(dialog.getString(R.string.rgb_brt));
                    viewBinding.tvLabel2.setText(dialog.getString(R.string.w_value));
                } else {
                    viewBinding.tvLabel1.setText(dialog.getString(R.string.rgb_brt));
                    viewBinding.tvLabel2.setText(dialog.getString(R.string.wy_brt));
                }
                viewBinding.barBrt1.setProgress(dialog.rgbBrt);
                viewBinding.tvBrt1.setText(dialog.rgbBrt + "%");
                viewBinding.barBrt1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.ColorBrtControlDialog.1.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            return;
                        }
                        if (progress <= 0) {
                            seekBar.setProgress(1);
                            return;
                        }
                        viewBinding.tvBrt1.setText(progress + "%");
                        if (dialog.callBack != null) {
                            dialog.callBack.onBrt1Change(progress, false);
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (dialog.callBack != null) {
                            dialog.callBack.onBrt1Change(seekBar.getProgress(), true);
                        }
                        viewBinding.tvBrt1.setText(seekBar.getProgress() + "%");
                    }
                });
                viewBinding.barBrt2.setProgress(dialog.cwBrt);
                viewBinding.tvBrt2.setText(dialog.cwBrt + "%");
                viewBinding.barBrt2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.ColorBrtControlDialog.1.2
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            return;
                        }
                        if (dialog.callBack != null) {
                            dialog.callBack.onBrt2Change(progress, false);
                        }
                        viewBinding.tvBrt2.setText(progress + "%");
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (dialog.callBack != null) {
                            dialog.callBack.onBrt2Change(seekBar.getProgress(), true);
                        }
                        viewBinding.tvBrt2.setText(seekBar.getProgress() + "%");
                    }
                });
                viewBinding.barCt.setProgress(dialog.cw);
                viewBinding.tvCt.setText(String.valueOf(dialog.cw));
                viewBinding.barCt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.ColorBrtControlDialog.1.3
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            return;
                        }
                        if (dialog.callBack != null) {
                            dialog.callBack.onBrt3Change(progress, false);
                        }
                        viewBinding.tvCt.setText(String.valueOf(progress));
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (dialog.callBack != null) {
                            dialog.callBack.onBrt3Change(seekBar.getProgress(), true);
                        }
                        viewBinding.tvCt.setText(String.valueOf(seekBar.getProgress()));
                    }
                });
                viewBinding.bg.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.ColorBrtControlDialog.1.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        dialog.dismissDialog();
                    }
                });
            }
        }).setOutCancel(true).setGravity(17);
    }

    public ColorBrtControlDialog setRgbBrt(int brt) {
        this.rgbBrt = brt;
        return this;
    }

    public ColorBrtControlDialog setCWBrt(int brt) {
        this.cwBrt = brt;
        return this;
    }

    public ColorBrtControlDialog setCw(int brt) {
        this.cw = brt;
        return this;
    }

    public ColorBrtControlDialog setOnColorBrtCallBack(OnColorBrtCallBack callBack) {
        this.callBack = callBack;
        return this;
    }
}