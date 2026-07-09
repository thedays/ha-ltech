package com.ltech.smarthome.view.dialog;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogLightQuickBinding;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import com.smart.message.utils.LHomeLog;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes4.dex */
public class LightQuickDialog extends SmartDialog<DialogLightQuickBinding> {
    private static DialogLightQuickBinding mViewBinding;
    private int RgbBrt;
    private int Wy;
    private Object controlObject;
    protected String lightName = "";
    private OnDialogCallback mCallback;
    private Boolean online;
    private int rgb;
    private boolean switchOn;

    public interface OnDialogCallback {
        void onBrtChanged(int progress, boolean finish);

        void onColorChanged(float xProgress, int color, boolean finish);

        void onCtChanged(float xProgress, int color, boolean finish);

        void onMoreAction();

        void onSwitch(boolean on);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_light_quick;
    }

    public static LightQuickDialog rgb() {
        return (LightQuickDialog) new LightQuickDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLightQuickBinding, LightQuickDialog>() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLightQuickBinding viewBinding, LightQuickDialog dialog) {
                LightQuickDialog.mViewBinding = viewBinding;
                dialog.initBrt(viewBinding);
                dialog.initDefault(viewBinding);
                dialog.initRgb(viewBinding);
            }
        }).setGravity(17).setMargin(15);
    }

    public static LightQuickDialog dim() {
        return (LightQuickDialog) new LightQuickDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLightQuickBinding, LightQuickDialog>() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLightQuickBinding viewBinding, LightQuickDialog dialog) {
                LightQuickDialog.mViewBinding = viewBinding;
                dialog.initBrt(viewBinding);
                dialog.initDefault(viewBinding);
                if (viewBinding != null) {
                    viewBinding.groupColor.setVisibility(8);
                    viewBinding.csbColorBar.setVisibility(8);
                }
            }
        }).setGravity(17).setMargin(15);
    }

    public static LightQuickDialog ct() {
        return (LightQuickDialog) new LightQuickDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLightQuickBinding, LightQuickDialog>() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLightQuickBinding viewBinding, LightQuickDialog dialog) {
                LightQuickDialog.mViewBinding = viewBinding;
                dialog.initBrt(viewBinding);
                dialog.initDefault(viewBinding);
                dialog.initCt(viewBinding);
            }
        }).setGravity(17).setMargin(15);
    }

    public static LightQuickDialog ctRgb() {
        return (LightQuickDialog) new LightQuickDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLightQuickBinding, LightQuickDialog>() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLightQuickBinding viewBinding, LightQuickDialog dialog) {
                LightQuickDialog.mViewBinding = viewBinding;
                dialog.initBrt(viewBinding);
                dialog.initDefault(viewBinding);
                dialog.initCt(viewBinding);
                dialog.initRgb(viewBinding);
            }
        }).setGravity(17).setMargin(15);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDefault(final DialogLightQuickBinding viewBinding) {
        String format;
        if (viewBinding == null) {
            return;
        }
        viewBinding.groupCt.setVisibility(8);
        viewBinding.groupColor.setVisibility(8);
        viewBinding.sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                LightQuickDialog.this.lambda$initDefault$0(viewBinding, switchButton, z);
            }
        });
        viewBinding.sb.setCheckedNotByUser(this.switchOn);
        AppCompatTextView appCompatTextView = viewBinding.tvName;
        if (this.online == null) {
            format = this.lightName;
        } else {
            format = String.format("%s|%s", this.lightName, requireContext().getString(this.online.booleanValue() ? R.string.online : R.string.offline));
        }
        appCompatTextView.setText(format);
        viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                LightQuickDialog.this.lambda$initDefault$1((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDefault$0(DialogLightQuickBinding dialogLightQuickBinding, SwitchButton switchButton, boolean z) {
        if (this.mCallback != null) {
            dialogLightQuickBinding.sbBrt.setIncludeZero(!z);
            this.mCallback.onSwitch(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDefault$1(View view) {
        if (view.getId() != R.id.iv_device_more) {
            return;
        }
        OnDialogCallback onDialogCallback = this.mCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onMoreAction();
        }
        dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRgb(final DialogLightQuickBinding viewBinding) {
        if (viewBinding == null) {
            return;
        }
        viewBinding.groupColor.setVisibility(0);
        if (this.rgb == 0) {
            viewBinding.civColor.setImageDrawable(new ColorDrawable(viewBinding.csbColorBar.getSelectColor()));
        } else {
            CircleImageView circleImageView = viewBinding.civColor;
            int i = this.rgb;
            circleImageView.setImageDrawable(new ColorDrawable(getColorFromRGB((i >> 16) & 255, (i >> 8) & 255, i & 255)));
        }
        viewBinding.csbColorBar.setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog.5
            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                if (LightQuickDialog.mViewBinding == null) {
                    return;
                }
                LightQuickDialog.mViewBinding.sb.setCheckedNotByUser(true);
                viewBinding.civColor.setImageDrawable(new ColorDrawable(color));
                if (!isFromUser || LightQuickDialog.this.mCallback == null) {
                    return;
                }
                LightQuickDialog.this.mCallback.onColorChanged(xProgress, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                if (LightQuickDialog.mViewBinding == null) {
                    return;
                }
                LightQuickDialog.mViewBinding.sb.setCheckedNotByUser(true);
                viewBinding.civColor.setImageDrawable(new ColorDrawable(color));
                if (!isFromUser || LightQuickDialog.this.mCallback == null) {
                    return;
                }
                LightQuickDialog.this.mCallback.onColorChanged(xProgress, color, true);
            }
        });
    }

    public int getColorFromRGB(int r, int g, int b2) {
        return (Math.max(0, Math.min(255, r)) << 16) | (-16777216) | (Math.max(0, Math.min(255, g)) << 8) | Math.max(0, Math.min(255, b2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCt(DialogLightQuickBinding viewBinding) {
        if (viewBinding == null) {
            return;
        }
        viewBinding.groupCt.setVisibility(0);
        new CtControlHelper(mViewBinding.sbCt, mViewBinding.tvCtPercent, this.controlObject, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public final void onRangeChanged(int i, boolean z) {
                LightQuickDialog.this.lambda$initCt$2(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCt$2(int i, boolean z) {
        OnDialogCallback onDialogCallback = this.mCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onCtChanged(LightUtils.c2percent(i), 0, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBrt(final DialogLightQuickBinding viewBinding) {
        if (viewBinding == null) {
            return;
        }
        viewBinding.sbBrt.setIncludeZero(!this.switchOn);
        viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.LightQuickDialog.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (LightQuickDialog.mViewBinding == null) {
                    return;
                }
                LightQuickDialog.mViewBinding.sb.setCheckedNotByUser(true);
                if (LightQuickDialog.this.switchOn) {
                    viewBinding.sbBrt.setIncludeZero(false);
                }
                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                viewBinding.tvBrt.setText(lightBrtBar.getProgressHasBelowOne());
                LightQuickDialog.this.RgbBrt = progress;
                LHomeLog.i(LightQuickDialog.class, "onProgressChanged=" + lightBrtBar.getProgressHasBelowOne());
                if (!fromUser || LightQuickDialog.this.mCallback == null) {
                    return;
                }
                LightQuickDialog.this.mCallback.onBrtChanged(progress, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (LightQuickDialog.mViewBinding == null) {
                    return;
                }
                LightQuickDialog.this.RgbBrt = seekBar.getProgress();
                LightQuickDialog.mViewBinding.sb.setCheckedNotByUser(true);
                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                viewBinding.tvBrt.setText(lightBrtBar.getProgressHasBelowOne());
                LHomeLog.i(LightQuickDialog.class, "onStopTrackingTouch=" + lightBrtBar.getProgressHasBelowOne());
                if (LightQuickDialog.this.mCallback != null) {
                    LightQuickDialog.this.mCallback.onBrtChanged(seekBar.getProgress(), true);
                }
            }
        });
        LHomeLog.i(LightQuickDialog.class, "init=" + this.RgbBrt);
        viewBinding.tvBrt.setText(viewBinding.sbBrt.getProgressHasBelowOne());
        viewBinding.sbBrt.setProgress(this.RgbBrt);
    }

    public LightQuickDialog setCallback(OnDialogCallback callback) {
        this.mCallback = callback;
        return this;
    }

    public LightQuickDialog setRgb(int rgb) {
        this.rgb = rgb;
        return this;
    }

    public LightQuickDialog setRgbBrt(int RgbBrt) {
        this.RgbBrt = RgbBrt;
        return this;
    }

    public LightQuickDialog setWy(int Wy) {
        this.Wy = Wy;
        return this;
    }

    public LightQuickDialog setSwitchOn(boolean switchOn) {
        this.switchOn = switchOn;
        return this;
    }

    public LightQuickDialog setOnline(boolean online) {
        this.online = Boolean.valueOf(online);
        return this;
    }

    public LightQuickDialog setLightName(String lightName) {
        this.lightName = lightName;
        return this;
    }

    public LightQuickDialog setControlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "light_quick_dialog";
    }

    public static DialogLightQuickBinding getViewBinding() {
        return mViewBinding;
    }
}