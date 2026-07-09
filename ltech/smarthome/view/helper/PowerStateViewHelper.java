package com.ltech.smarthome.view.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ViewPowerStateBleAllBinding;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.SwitchSeekBarView;

/* loaded from: classes4.dex */
public class PowerStateViewHelper {
    private ViewPowerStateBleAllBinding binding;
    private Context context;
    private Object controlObject;
    private CtControlHelper ctHelper;
    private int lightType;
    private PowerState powerState;

    public PowerStateViewHelper(Context context, ViewPowerStateBleAllBinding binding, PowerState powerState, Object controlObject) {
        this.binding = binding;
        this.powerState = powerState;
        this.controlObject = controlObject;
        this.context = context;
    }

    public void initPowerState() {
        int lightColorType = ProductRepository.getLightColorType(this.controlObject);
        this.lightType = lightColorType;
        this.binding.setCtGone(Boolean.valueOf((lightColorType == 2 || lightColorType == 0) ? false : true));
        ViewPowerStateBleAllBinding viewPowerStateBleAllBinding = this.binding;
        int i = this.lightType;
        viewPowerStateBleAllBinding.setRgbGone(Boolean.valueOf(i < 3 && i != 0));
        this.binding.setWGone(Boolean.valueOf(this.lightType != 4));
        ViewPowerStateBleAllBinding viewPowerStateBleAllBinding2 = this.binding;
        int i2 = this.lightType;
        viewPowerStateBleAllBinding2.setCwGone(Boolean.valueOf((i2 == 5 || i2 == 20) ? false : true));
        this.binding.sbRgb.setVisibility(this.lightType < 4 ? 8 : 0);
        if (this.lightType >= 3) {
            this.binding.seekbarBrt.setTitle(this.context.getString(R.string.rgb_brt));
        }
        this.binding.seekbarBrt.setMax(100);
        this.binding.seekbarBrt.setMin(1);
        this.binding.seekbarBrt.needPercent(true);
        this.binding.seekbarBrt.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper.1
            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onCheckedChanged(boolean isChecked) {
            }

            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onProgressChanged(int v, boolean finish) {
                PowerStateViewHelper.this.powerState.brt = LightUtils.progress2BrtHasBelowOne(v);
                if (PowerStateViewHelper.this.lightType == 0) {
                    PowerStateViewHelper.this.powerState.wyBrt = LightUtils.progress2BrtHasBelowOne(v);
                }
                if (PowerStateViewHelper.this.lightType >= 3) {
                    PowerStateViewHelper.this.getLightCmdHelper().sendRgbBrtHas1to9(PowerStateViewHelper.this.context, v, finish);
                } else {
                    PowerStateViewHelper.this.getLightCmdHelper().sendDimBrtD1Has1to9(PowerStateViewHelper.this.context, v, finish);
                }
            }
        });
        int i3 = this.lightType;
        if (i3 == 2 || i3 == 0) {
            this.ctHelper = new CtControlHelper(this.binding.seekbarCt, this.binding.tvCtValue, this.controlObject, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
                public final void onRangeChanged(int i4, boolean z) {
                    PowerStateViewHelper.this.lambda$initPowerState$0(i4, z);
                }
            });
        }
        int i4 = this.lightType;
        if (i4 >= 3 || i4 == 0) {
            this.binding.seekbarRgb.setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper.2
                @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
                public void onColorChangedStart() {
                }

                @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
                public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                    PowerStateViewHelper.this.binding.civColor.setImageDrawable(new ColorDrawable(color));
                    PowerStateViewHelper.this.powerState.setColor(color);
                    if (isFromUser) {
                        PowerStateViewHelper.this.getLightCmdHelper().sendRgb(PowerStateViewHelper.this.context, color, false);
                    }
                }

                @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
                public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                    PowerStateViewHelper.this.binding.civColor.setImageDrawable(new ColorDrawable(color));
                    PowerStateViewHelper.this.powerState.setColor(color);
                    if (isFromUser) {
                        PowerStateViewHelper.this.getLightCmdHelper().sendRgb(PowerStateViewHelper.this.context, color, true);
                    }
                }
            });
            this.binding.sbRgb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    PowerStateViewHelper.this.lambda$initPowerState$1(switchButton, z);
                }
            });
        }
        if (this.lightType == 4) {
            this.binding.switchSeekbarW.setMax(100);
            this.binding.switchSeekbarW.needPercent(true);
            this.binding.switchSeekbarW.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper.3
                @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
                public void onProgressChanged(int v, boolean finish) {
                    PowerStateViewHelper.this.powerState.wyBrt = LightUtils.progress2BrtHasBelowOne(v);
                    PowerStateViewHelper.this.getLightCmdHelper().sendWHas1to9(PowerStateViewHelper.this.context, PowerStateViewHelper.this.powerState.wyBrt, finish);
                }

                @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
                public void onCheckedChanged(boolean z) {
                    if (!z && PowerStateViewHelper.this.powerState.rgbOn == 0) {
                        PowerStateViewHelper.this.binding.switchSeekbarW.setState(true);
                        SmartToast.showShort(R.string.least_open_one);
                    } else {
                        PowerStateViewHelper.this.binding.switchSeekbarW.setEnabled(z);
                        PowerStateViewHelper.this.powerState.cwOn = z ? 1 : 0;
                        PowerStateViewHelper.this.getLightCmdHelper().sendWOnOff(PowerStateViewHelper.this.context, z);
                    }
                }
            });
        }
        int i5 = this.lightType;
        if (i5 == 5 || i5 == 20) {
            this.binding.seekbarBrt.setTitle(this.context.getString(R.string.rgb_brt));
            this.binding.switchSeekbarCw.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper.4
                @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
                public void onProgressChanged(int v, boolean finish) {
                    PowerStateViewHelper.this.powerState.f6273c = 255 - v;
                    PowerStateViewHelper.this.powerState.w = v;
                    PowerStateViewHelper.this.getLightCmdHelper().sendWy(PowerStateViewHelper.this.context, v, finish);
                }

                @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
                public void onCheckedChanged(boolean z) {
                    if (!z && PowerStateViewHelper.this.powerState.rgbOn == 0) {
                        PowerStateViewHelper.this.binding.switchSeekbarCw.setState(true);
                        SmartToast.showShort(R.string.least_open_one);
                        return;
                    }
                    PowerStateViewHelper.this.binding.switchSeekbarCw.setEnabled(z);
                    PowerStateViewHelper.this.binding.seekbarCwBrt.setEnabled(z);
                    PowerStateViewHelper.this.powerState.cwOn = z ? 1 : 0;
                    PowerStateViewHelper.this.getLightCmdHelper().sendWyOnOff(PowerStateViewHelper.this.context, z);
                }
            });
            this.binding.seekbarCwBrt.setMax(100);
            this.binding.seekbarCwBrt.setMin(1);
            this.binding.seekbarCwBrt.needPercent(true);
            this.binding.seekbarCwBrt.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.helper.PowerStateViewHelper.5
                @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
                public void onCheckedChanged(boolean isChecked) {
                }

                @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
                public void onProgressChanged(int v, boolean finish) {
                    PowerStateViewHelper.this.powerState.wyBrt = LightUtils.progress2BrtHasBelowOne(v);
                    PowerStateViewHelper.this.getLightCmdHelper().sendWyBrtHas1to9(PowerStateViewHelper.this.context, PowerStateViewHelper.this.powerState.wyBrt, finish);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPowerState$0(int i, boolean z) {
        this.powerState.f6273c = i;
        this.powerState.w = 255 - i;
        getLightCmdHelper().sendWy(this.context, this.powerState.w, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPowerState$1(SwitchButton switchButton, boolean z) {
        if (!z && this.lightType >= 4 && this.powerState.cwOn == 0) {
            this.binding.sbRgb.setCheckedNotByUser(true);
            SmartToast.showShort(R.string.least_open_one);
            return;
        }
        this.binding.seekbarBrt.setEnabled(z);
        this.binding.seekbarRgb.setEnabled(z);
        if (z && this.powerState.rgbOn == 1) {
            this.binding.civColor.setImageDrawable(new ColorDrawable(this.powerState.getColor()));
        } else {
            this.binding.civColor.setImageDrawable(new ColorDrawable(this.context.getResources().getColor(R.color.color_line_gray)));
        }
        this.powerState.rgbOn = z ? 1 : 0;
        getLightCmdHelper().sendRgbOnOff(this.context, z);
    }

    public void changePowerState(boolean enable) {
        boolean z = false;
        if (this.lightType >= 3) {
            this.binding.seekbarBrt.setEnabled(enable && this.powerState.rgbOn == 1);
        } else {
            this.binding.seekbarBrt.setEnabled(enable);
        }
        this.binding.seekbarCt.setEnabled(enable);
        RangeSeekBar rangeSeekBar = this.binding.seekbarCt;
        int i = R.mipmap.pic_ct;
        rangeSeekBar.setProgressDrawableId(enable ? R.mipmap.pic_ct : R.drawable.style_seekbar_gray);
        RangeSeekBar rangeSeekBar2 = this.binding.seekbarCt;
        if (!enable) {
            i = R.drawable.style_seekbar_gray;
        }
        rangeSeekBar2.setProgressDefaultDrawableId(i);
        this.binding.sbRgb.setEnabled(enable);
        this.binding.sbRgb.setCheckedNotByUser(enable && this.powerState.rgbOn == 1);
        this.binding.seekbarRgb.setEnabled(enable && this.powerState.rgbOn == 1);
        this.binding.switchSeekbarW.setEnabled(enable && this.powerState.cwOn == 1);
        this.binding.switchSeekbarW.setSwitchEnabled(enable);
        this.binding.switchSeekbarCw.setEnabled(enable && this.powerState.cwOn == 1);
        this.binding.switchSeekbarCw.setSwitchEnabled(enable);
        this.binding.seekbarCwBrt.setEnabled(enable && this.powerState.cwOn == 1);
        this.binding.seekbarBrt.setProgressAndValue(LightUtils.brt2ProgressHasBelowZero(this.powerState.brt));
        int i2 = this.lightType;
        if (i2 == 2 || i2 == 0) {
            this.ctHelper.setProgress(this.powerState.f6273c);
        }
        int i3 = this.lightType;
        if (i3 >= 3 || i3 == 0) {
            if (enable && this.powerState.rgbOn == 1) {
                this.binding.civColor.setImageDrawable(new ColorDrawable(this.powerState.getColor()));
            } else {
                this.binding.civColor.setImageDrawable(new ColorDrawable(this.context.getResources().getColor(R.color.color_line_gray)));
            }
        }
        if (this.lightType == 4) {
            this.binding.switchSeekbarW.setCheckedNotByUser(enable && this.powerState.cwOn == 1);
            this.binding.switchSeekbarW.setProgressAndValue(LightUtils.brt2ProgressHasBelowZero(this.powerState.wyBrt));
        }
        int i4 = this.lightType;
        if (i4 == 5 || i4 == 20) {
            SwitchSeekBarView switchSeekBarView = this.binding.switchSeekbarCw;
            if (enable && this.powerState.cwOn == 1) {
                z = true;
            }
            switchSeekBarView.setCheckedNotByUser(z);
            this.binding.switchSeekbarCw.setProgressAndValue(this.powerState.w);
            this.binding.seekbarCwBrt.setProgressAndValue(LightUtils.brt2ProgressHasBelowZero(this.powerState.wyBrt));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]);
    }
}