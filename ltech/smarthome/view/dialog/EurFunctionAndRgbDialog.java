package com.ltech.smarthome.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.widget.SeekBar;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogEurFunctionAndRgbBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.SwitchSeekBarView;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EurFunctionAndRgbDialog extends SmartDialog<DialogEurFunctionAndRgbBinding> {
    public static final int TYPE_CW = 1;
    public static final int TYPE_SATURATION = 3;
    public static final int TYPE_W = 2;

    /* renamed from: b, reason: collision with root package name */
    private int f6281b;
    private Object controlObject;
    private int cwBrt;
    private int g;
    private OnDialogCallback onDialogCallback;
    private int r;
    private int rgbBrt;
    private int w;
    private int zoneNum;
    private int dialogType = 1;
    private List<Integer> functionStateList = new ArrayList();
    private boolean isFirst = true;

    public interface OnDialogCallback {
        void dismiss(int r, int g, int b2, int w, int rgbBrt, int cwBrt, List<Integer> functionStateList);
    }

    static /* synthetic */ void lambda$setOpenState$0(ResponseMsg responseMsg) {
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_eur_function_and_rgb;
    }

    public static EurFunctionAndRgbDialog asDefault() {
        EurFunctionAndRgbDialog eurFunctionAndRgbDialog = new EurFunctionAndRgbDialog();
        eurFunctionAndRgbDialog.setViewConverter(new SmartDialog.ViewConverter<DialogEurFunctionAndRgbBinding, EurFunctionAndRgbDialog>() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogEurFunctionAndRgbBinding viewBinding, EurFunctionAndRgbDialog dialog) {
                EurFunctionAndRgbDialog.initDialog(dialog, viewBinding);
                dialog.isFirst = false;
            }
        }).setMargin(10).setY(16).setGravity(80);
        return eurFunctionAndRgbDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initDialog(final EurFunctionAndRgbDialog dialog, final DialogEurFunctionAndRgbBinding viewBinding) {
        viewBinding.switchSeekbarRgbBrt.setMax(100);
        viewBinding.switchSeekbarRgbBrt.setMin(1);
        viewBinding.switchSeekbarRgbBrt.needPercent(true);
        if (ProductId.ID_AS_PANEL_U4S.equals(AsHelper.getAsProductId(dialog.controlObject))) {
            viewBinding.switchSeekbarRgbBrt.setSwitchVisible(false);
        }
        int i = dialog.dialogType;
        if (i == 1) {
            viewBinding.tvTitle.setText("RGBCW");
            viewBinding.tvSecondTitle.setText(dialog.getActivity().getString(R.string.cw));
            viewBinding.switchSeekbarRgbBrt.setVisibility(0);
            viewBinding.switchSeekbarRgbBrt.setProgressAndValue(LightUtils.brt2ProgressHasBelowZero(dialog.rgbBrt));
            viewBinding.tvFirst.setText(dialog.getActivity().getString(R.string.wy_brt));
            viewBinding.tvSecond.setText(dialog.getActivity().getString(R.string.cw));
            if (dialog.isFirst) {
                Rect bounds = viewBinding.seekbar.getProgressDrawable().getBounds();
                viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(dialog.getActivity(), R.drawable.style_seekbar_c));
                viewBinding.seekbar.getProgressDrawable().setBounds(bounds);
            }
            viewBinding.seekbar.setMax(100);
            viewBinding.seekbarSecond.setMax(255);
            viewBinding.seekbar.setProgress(LightUtils.brt2ProgressHasBelowZero(dialog.cwBrt));
            viewBinding.tvValue.setText(viewBinding.seekbar.getProgressHasBelowOne());
            viewBinding.seekbarSecond.setProgress(dialog.w);
            viewBinding.tvValueSecond.setText(String.valueOf(dialog.w));
            viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionAndRgbDialog.this.cwBrt = LightUtils.progress2BrtHasBelowOne(progress);
                        viewBinding.tvValue.setText(LightUtils.getProgressHasBelowOne(progress));
                        if (EurFunctionAndRgbDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendCtBrtHas0to9(EurFunctionAndRgbDialog.this.getActivity(), progress, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionAndRgbDialog.this.cwBrt = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                    viewBinding.tvValue.setText(LightUtils.getProgressHasBelowOne(seekBar.getProgress()));
                    if (EurFunctionAndRgbDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendCtBrtHas0to9(EurFunctionAndRgbDialog.this.getActivity(), seekBar.getProgress(), true);
                    }
                }
            });
            viewBinding.seekbarSecond.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.3
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionAndRgbDialog.this.w = progress;
                        viewBinding.tvValueSecond.setText(String.valueOf(progress));
                        if (EurFunctionAndRgbDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendWy(EurFunctionAndRgbDialog.this.getActivity(), EurFunctionAndRgbDialog.this.w, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionAndRgbDialog.this.w = seekBar.getProgress();
                    viewBinding.tvValueSecond.setText(String.valueOf(seekBar.getProgress()));
                    if (EurFunctionAndRgbDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendWy(EurFunctionAndRgbDialog.this.getActivity(), EurFunctionAndRgbDialog.this.w, true);
                    }
                }
            });
            viewBinding.sbFunction.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.4
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    DialogEurFunctionAndRgbBinding.this.seekbar.setEnabled(z);
                    DialogEurFunctionAndRgbBinding.this.seekbarSecond.setEnabled(z);
                    DialogEurFunctionAndRgbBinding.this.seekbar.setAlpha(z ? 1.0f : 0.5f);
                    DialogEurFunctionAndRgbBinding.this.seekbarSecond.setAlpha(z ? 1.0f : 0.5f);
                    dialog.functionStateList.set(3, Integer.valueOf(z ? 1 : 0));
                    EurFunctionAndRgbDialog eurFunctionAndRgbDialog = dialog;
                    eurFunctionAndRgbDialog.setOpenState(eurFunctionAndRgbDialog.functionStateList);
                }
            });
        } else if (i == 2) {
            viewBinding.tvTitle.setText("RGBW");
            viewBinding.tvSecondTitle.setText(dialog.getActivity().getString(R.string.w));
            viewBinding.switchSeekbarRgbBrt.setVisibility(0);
            viewBinding.switchSeekbarRgbBrt.setProgressAndValue(LightUtils.brt2ProgressHasBelowZero(dialog.rgbBrt));
            viewBinding.tvFirst.setVisibility(8);
            viewBinding.groupSecond.setVisibility(8);
            if (dialog.isFirst) {
                Rect bounds2 = viewBinding.seekbar.getProgressDrawable().getBounds();
                viewBinding.seekbar.setProgressDrawable(ContextCompat.getDrawable(dialog.getActivity(), R.drawable.style_seekbar_yellow));
                viewBinding.seekbar.getProgressDrawable().setBounds(bounds2);
            }
            viewBinding.seekbar.setIncludeZero(true);
            viewBinding.seekbar.setMax(255);
            viewBinding.seekbar.setProgress(dialog.w);
            viewBinding.tvValue.setText(LightUtils.brt2ProgressHasBelowZero(dialog.w) + "%");
            viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.5
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionAndRgbDialog.this.w = progress;
                        viewBinding.tvValue.setText(LightUtils.brt2ProgressHasBelowZero(EurFunctionAndRgbDialog.this.w) + "%");
                        if (EurFunctionAndRgbDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendChannelValue((Context) EurFunctionAndRgbDialog.this.getActivity(), 4, EurFunctionAndRgbDialog.this.w, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionAndRgbDialog.this.w = seekBar.getProgress();
                    viewBinding.tvValue.setText(LightUtils.brt2ProgressHasBelowZero(seekBar.getProgress()) + "%");
                    if (EurFunctionAndRgbDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendChannelValue((Context) EurFunctionAndRgbDialog.this.getActivity(), 4, EurFunctionAndRgbDialog.this.w, true);
                    }
                }
            });
            viewBinding.sbFunction.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.6
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    DialogEurFunctionAndRgbBinding.this.seekbar.setEnabled(z);
                    DialogEurFunctionAndRgbBinding.this.seekbar.setAlpha(z ? 1.0f : 0.5f);
                    dialog.functionStateList.set(3, Integer.valueOf(z ? 1 : 0));
                    EurFunctionAndRgbDialog eurFunctionAndRgbDialog = dialog;
                    eurFunctionAndRgbDialog.setOpenState(eurFunctionAndRgbDialog.functionStateList);
                }
            });
        } else if (i == 3) {
            viewBinding.tvTitle.setText("RGB");
            viewBinding.tvFirst.setText(dialog.getActivity().getString(R.string.str_rgb_saturation));
            viewBinding.switchSeekbarRgbBrt.setVisibility(0);
            viewBinding.switchSeekbarRgbBrt.setProgressAndValue(LightUtils.brt2ProgressHasBelowZero(dialog.rgbBrt));
            viewBinding.tvSecondTitle.setVisibility(8);
            viewBinding.sbFunction.setVisibility(8);
            viewBinding.groupSecond.setVisibility(8);
            viewBinding.seekbar.setIncludeZero(true);
            viewBinding.seekbar.setMax(100);
            viewBinding.seekbar.setProgress(LightUtils.brt2ProgressHasBelowZero(dialog.w));
            viewBinding.tvValue.setText(LightUtils.brt2ProgressHasBelowZero(dialog.w) + "%");
            viewBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.7
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EurFunctionAndRgbDialog.this.w = LightUtils.progress2BrtIncludeZero(progress);
                        viewBinding.tvValue.setText(LightUtils.getProgressHasBelowOne(progress));
                        if (EurFunctionAndRgbDialog.this.controlObject != null) {
                            CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendRgbSaturation(EurFunctionAndRgbDialog.this.getActivity(), EurFunctionAndRgbDialog.this.w, false);
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EurFunctionAndRgbDialog.this.w = LightUtils.progress2BrtIncludeZero(seekBar.getProgress());
                    viewBinding.tvValue.setText(LightUtils.getProgressHasBelowOne(seekBar.getProgress()));
                    if (EurFunctionAndRgbDialog.this.controlObject != null) {
                        CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendRgbSaturation(EurFunctionAndRgbDialog.this.getActivity(), EurFunctionAndRgbDialog.this.w, true);
                    }
                }
            });
        }
        if (dialog.r == -1) {
            viewBinding.switchSeekbarR.setVisibility(8);
        } else {
            viewBinding.switchSeekbarR.setProgressAndValue(dialog.r);
        }
        if (dialog.g == -1) {
            viewBinding.switchSeekbarG.setVisibility(8);
        } else {
            viewBinding.switchSeekbarG.setProgressAndValue(dialog.g);
        }
        if (dialog.f6281b == -1) {
            viewBinding.switchSeekbarB.setVisibility(8);
        } else {
            viewBinding.switchSeekbarB.setProgressAndValue(dialog.f6281b);
        }
        if (dialog.functionStateList.size() > 4) {
            viewBinding.switchSeekbarR.setState(dialog.functionStateList.get(0).intValue() == 1);
            viewBinding.switchSeekbarG.setState(dialog.functionStateList.get(1).intValue() == 1);
            viewBinding.switchSeekbarB.setState(dialog.functionStateList.get(2).intValue() == 1);
            int i2 = dialog.dialogType;
            if (i2 == 1 || i2 == 2) {
                boolean z = dialog.functionStateList.get(3).intValue() == 1;
                viewBinding.sbFunction.setCheckedNotByUser(z);
                viewBinding.seekbar.setEnabled(z);
                viewBinding.seekbarSecond.setEnabled(z);
                viewBinding.seekbar.setAlpha(z ? 1.0f : 0.5f);
                viewBinding.seekbarSecond.setAlpha(z ? 1.0f : 0.5f);
            }
            viewBinding.switchSeekbarRgbBrt.setState(dialog.functionStateList.get(4).intValue() == 1);
        }
        viewBinding.switchSeekbarR.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.8
            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onProgressChanged(int v, boolean finish) {
                EurFunctionAndRgbDialog.this.r = v;
                CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendChannelValue(EurFunctionAndRgbDialog.this.getActivity(), 1, v, finish);
            }

            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onCheckedChanged(boolean z2) {
                EurFunctionAndRgbDialog.this.functionStateList.set(0, Integer.valueOf(z2 ? 1 : 0));
                EurFunctionAndRgbDialog eurFunctionAndRgbDialog = EurFunctionAndRgbDialog.this;
                eurFunctionAndRgbDialog.setOpenState(eurFunctionAndRgbDialog.functionStateList);
            }
        });
        viewBinding.switchSeekbarG.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.9
            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onProgressChanged(int v, boolean finish) {
                EurFunctionAndRgbDialog.this.g = v;
                CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendChannelValue(EurFunctionAndRgbDialog.this.getActivity(), 2, v, finish);
            }

            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onCheckedChanged(boolean z2) {
                EurFunctionAndRgbDialog.this.functionStateList.set(1, Integer.valueOf(z2 ? 1 : 0));
                EurFunctionAndRgbDialog eurFunctionAndRgbDialog = EurFunctionAndRgbDialog.this;
                eurFunctionAndRgbDialog.setOpenState(eurFunctionAndRgbDialog.functionStateList);
            }
        });
        viewBinding.switchSeekbarB.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.10
            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onProgressChanged(int v, boolean finish) {
                EurFunctionAndRgbDialog.this.f6281b = v;
                CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendChannelValue(EurFunctionAndRgbDialog.this.getActivity(), 3, v, finish);
            }

            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onCheckedChanged(boolean z2) {
                EurFunctionAndRgbDialog.this.functionStateList.set(2, Integer.valueOf(z2 ? 1 : 0));
                EurFunctionAndRgbDialog eurFunctionAndRgbDialog = EurFunctionAndRgbDialog.this;
                eurFunctionAndRgbDialog.setOpenState(eurFunctionAndRgbDialog.functionStateList);
            }
        });
        viewBinding.switchSeekbarRgbBrt.setOnProgressChangeListener(new SwitchSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.11
            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onProgressChanged(int v, boolean finish) {
                EurFunctionAndRgbDialog.this.rgbBrt = LightUtils.progress2BrtHasBelowOne(v);
                CmdAssistant.getLightCmdAssistant(EurFunctionAndRgbDialog.this.controlObject, EurFunctionAndRgbDialog.this.zoneNum).sendRgbBrt(EurFunctionAndRgbDialog.this.getActivity(), v, finish);
            }

            @Override // com.ltech.smarthome.view.SwitchSeekBarView.OnProgressChangeListener
            public void onCheckedChanged(boolean z2) {
                EurFunctionAndRgbDialog.this.functionStateList.set(4, Integer.valueOf(z2 ? 1 : 0));
                EurFunctionAndRgbDialog eurFunctionAndRgbDialog = EurFunctionAndRgbDialog.this;
                eurFunctionAndRgbDialog.setOpenState(eurFunctionAndRgbDialog.functionStateList);
            }
        });
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        OnDialogCallback onDialogCallback = this.onDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.dismiss(this.r, this.g, this.f6281b, this.w, this.rgbBrt, this.cwBrt, this.functionStateList);
        }
    }

    public void setOpenState(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        sb.reverse();
        CmdAssistant.getLightCmdAssistant(this.controlObject, this.zoneNum).sendSwitchValue(getActivity(), Integer.parseInt(sb.toString(), 2), new IAction() { // from class: com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                EurFunctionAndRgbDialog.lambda$setOpenState$0((ResponseMsg) obj);
            }
        });
    }

    public EurFunctionAndRgbDialog setDialogType(int dialogType) {
        this.dialogType = dialogType;
        return this;
    }

    public EurFunctionAndRgbDialog setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
        return this;
    }

    public EurFunctionAndRgbDialog setR(int r) {
        this.r = r;
        return this;
    }

    public EurFunctionAndRgbDialog setG(int g) {
        this.g = g;
        return this;
    }

    public EurFunctionAndRgbDialog setB(int b2) {
        this.f6281b = b2;
        return this;
    }

    public EurFunctionAndRgbDialog setW(int w) {
        this.w = w;
        return this;
    }

    public EurFunctionAndRgbDialog setRgbBrt(int rgbBrt) {
        this.rgbBrt = rgbBrt;
        return this;
    }

    public EurFunctionAndRgbDialog setCwBrt(int cwBrt) {
        this.cwBrt = cwBrt;
        return this;
    }

    public EurFunctionAndRgbDialog setFunctionStateList(List<Integer> functionStateList) {
        this.functionStateList = functionStateList;
        return this;
    }

    public EurFunctionAndRgbDialog setControlObject(Object controlObject) {
        this.controlObject = controlObject;
        return this;
    }

    public EurFunctionAndRgbDialog setOnDialogCallback(OnDialogCallback onDialogCallback) {
        this.onDialogCallback = onDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "eur_function_and_rgb_dialog";
    }
}