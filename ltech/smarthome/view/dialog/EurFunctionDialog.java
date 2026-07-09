package com.ltech.smarthome.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.widget.SeekBar;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogEurFunctionBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EurFunctionDialog extends SmartDialog<DialogEurFunctionBinding> {
    public static final int TYPE_CW = 1;
    public static final int TYPE_SATURATION = 3;
    public static final int TYPE_W = 2;
    private Object controlObject;
    private int cwBrt;
    private int cwState;
    private int dialogType = 1;
    private List<Integer> functionStateList = new ArrayList();
    private OnDialogCallback onDialogCallback;
    private int w;
    private int zoneNum;

    public interface OnDialogCallback {
        void dismiss(int w, int cwBrt, int cwState);
    }

    static /* synthetic */ void lambda$setOpenState$0(ResponseMsg responseMsg) {
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_eur_function;
    }

    public static EurFunctionDialog asDefault() {
        return (EurFunctionDialog) new EurFunctionDialog().setViewConverter(new SmartDialog.ViewConverter<DialogEurFunctionBinding, EurFunctionDialog>() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogEurFunctionBinding viewBinding, EurFunctionDialog dialog) {
                EurFunctionDialog.initDialog(dialog, viewBinding);
            }
        }).setMargin(10).setY(16).setGravity(80);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        OnDialogCallback onDialogCallback = this.onDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.dismiss(this.w, this.cwBrt, this.cwState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initDialog(final EurFunctionDialog dialog, final DialogEurFunctionBinding viewBinding) {
        boolean z;
        int i = dialog.dialogType;
        if (i == 1) {
            viewBinding.tvTitle.setText("CW");
            viewBinding.tvFirst.setText(dialog.getActivity().getString(R.string.wy_brt));
            viewBinding.tvSecond.setText(dialog.getActivity().getString(R.string.cw));
            Rect bounds = viewBinding.seekbar.getProgressDrawable().getBounds();
            viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(dialog.getActivity(), R.drawable.style_seekbar_c));
            viewBinding.seekbar.getProgressDrawable().setBounds(bounds);
            viewBinding.seekbarSecond.setMax(255);
            viewBinding.seekbar.setProgress(LightUtils.brt2ProgressHasBelowZero(dialog.cwBrt));
            viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
            viewBinding.seekbarSecond.setProgress(dialog.w);
            viewBinding.tvValueSecond.setText(String.valueOf(dialog.w));
            viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionDialog.this.cwBrt = LightUtils.progress2BrtHasBelowOne(progress);
                        viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
                        if (EurFunctionDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendCtBrtHas1to9(EurFunctionDialog.this.getActivity(), progress, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionDialog.this.cwBrt = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                    viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
                    if (EurFunctionDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendCtBrtHas1to9(EurFunctionDialog.this.getActivity(), seekBar.getProgress(), true);
                    }
                }
            });
            viewBinding.seekbarSecond.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.3
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionDialog.this.w = progress;
                        viewBinding.tvValueSecond.setText(String.valueOf(progress));
                        if (EurFunctionDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendChannelValue((Context) EurFunctionDialog.this.getActivity(), 4, EurFunctionDialog.this.w, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionDialog.this.w = seekBar.getProgress();
                    viewBinding.tvValueSecond.setText(String.valueOf(seekBar.getProgress()));
                    if (EurFunctionDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendChannelValue((Context) EurFunctionDialog.this.getActivity(), 4, EurFunctionDialog.this.w, true);
                    }
                }
            });
            viewBinding.sbRgbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.4
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton switchButton, boolean z2) {
                    DialogEurFunctionBinding.this.seekbar.setEnabled(z2);
                    DialogEurFunctionBinding.this.seekbarSecond.setEnabled(z2);
                    DialogEurFunctionBinding.this.seekbar.setAlpha(z2 ? 1.0f : 0.5f);
                    DialogEurFunctionBinding.this.seekbarSecond.setAlpha(z2 ? 1.0f : 0.5f);
                    dialog.functionStateList.set(3, Integer.valueOf(z2 ? 1 : 0));
                    EurFunctionDialog eurFunctionDialog = dialog;
                    eurFunctionDialog.setOpenState(eurFunctionDialog.functionStateList);
                }
            });
        } else if (i == 2) {
            viewBinding.tvTitle.setText(ExifInterface.LONGITUDE_WEST);
            viewBinding.tvFirst.setVisibility(8);
            viewBinding.groupSecond.setVisibility(8);
            Rect bounds2 = viewBinding.seekbar.getProgressDrawable().getBounds();
            viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(dialog.getActivity(), R.drawable.style_seekbar_yellow));
            viewBinding.seekbar.getProgressDrawable().setBounds(bounds2);
            viewBinding.seekbar.setMax(100);
            viewBinding.seekbar.setProgress(LightUtils.brt2ProgressHasBelowZero(dialog.w));
            viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
            viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.5
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionDialog.this.w = LightUtils.progress2BrtHasBelowOne(progress);
                        viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
                        if (EurFunctionDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendChannelValue((Context) EurFunctionDialog.this.getActivity(), 4, EurFunctionDialog.this.w, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionDialog.this.w = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                    viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
                    if (EurFunctionDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendChannelValue((Context) EurFunctionDialog.this.getActivity(), 4, EurFunctionDialog.this.w, true);
                    }
                }
            });
            viewBinding.sbRgbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.6
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton switchButton, boolean z2) {
                    DialogEurFunctionBinding.this.seekbar.setEnabled(z2);
                    DialogEurFunctionBinding.this.seekbar.setAlpha(z2 ? 1.0f : 0.5f);
                    dialog.functionStateList.set(3, Integer.valueOf(z2 ? 1 : 0));
                    EurFunctionDialog eurFunctionDialog = dialog;
                    eurFunctionDialog.setOpenState(eurFunctionDialog.functionStateList);
                }
            });
        } else if (i == 3) {
            viewBinding.tvTitle.setText(dialog.getActivity().getString(R.string.str_rgb_saturation));
            viewBinding.tvFirst.setVisibility(8);
            viewBinding.sbRgbOn.setVisibility(4);
            viewBinding.groupSecond.setVisibility(8);
            viewBinding.seekbar.setMax(255);
            viewBinding.seekbar.setProgress(dialog.w);
            viewBinding.seekbar.setIncludeZero(true);
            viewBinding.tvValue.setText(String.valueOf(dialog.w));
            viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog.7
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionDialog.this.w = progress;
                        viewBinding.tvValue.setText(String.valueOf(progress));
                        if (EurFunctionDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendRgbSaturation(EurFunctionDialog.this.getActivity(), progress, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionDialog.this.w = seekBar.getProgress();
                    viewBinding.tvValue.setText(String.valueOf(seekBar.getProgress()));
                    if (EurFunctionDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionDialog.this.controlObject, EurFunctionDialog.this.zoneNum).sendRgbSaturation(EurFunctionDialog.this.getActivity(), seekBar.getProgress(), true);
                    }
                }
            });
        }
        if (dialog.functionStateList.size() > 4) {
            int i2 = dialog.dialogType;
            if (i2 == 1) {
                z = dialog.cwState == 1;
                viewBinding.sbRgbOn.setChecked(z);
                viewBinding.seekbar.setEnabled(z);
                viewBinding.seekbarSecond.setEnabled(z);
                viewBinding.seekbar.setAlpha(z ? 1.0f : 0.5f);
                viewBinding.seekbarSecond.setAlpha(z ? 1.0f : 0.5f);
                return;
            }
            if (i2 == 2) {
                z = dialog.cwState == 1;
                viewBinding.sbRgbOn.setChecked(z);
                viewBinding.seekbar.setEnabled(z);
                viewBinding.seekbar.setAlpha(z ? 1.0f : 0.5f);
            }
        }
    }

    public void setOpenState(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        sb.reverse();
        CmdAssistant.getLightCmdAssistant(this.controlObject, this.zoneNum).sendSwitchValue(getActivity(), Integer.parseInt(sb.toString(), 2), new IAction() { // from class: com.ltech.smarthome.view.dialog.EurFunctionDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                EurFunctionDialog.lambda$setOpenState$0((ResponseMsg) obj);
            }
        });
    }

    public EurFunctionDialog setDialogType(int dialogType) {
        this.dialogType = dialogType;
        return this;
    }

    public EurFunctionDialog setFunctionStateList(List<Integer> functionStateList) {
        this.functionStateList = functionStateList;
        return this;
    }

    public EurFunctionDialog setControlObject(Object controlObject) {
        this.controlObject = controlObject;
        return this;
    }

    public EurFunctionDialog setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
        return this;
    }

    public EurFunctionDialog setW(int w) {
        this.w = w;
        return this;
    }

    public EurFunctionDialog setCwState(int cwState) {
        this.cwState = cwState;
        return this;
    }

    public EurFunctionDialog setCwBrt(int cwBrt) {
        this.cwBrt = cwBrt;
        return this;
    }

    public EurFunctionDialog setOnDialogCallback(OnDialogCallback onDialogCallback) {
        this.onDialogCallback = onDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "eur_function_dialog";
    }
}