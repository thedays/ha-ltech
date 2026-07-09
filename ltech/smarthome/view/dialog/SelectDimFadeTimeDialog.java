package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectDimFadeTimeBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.DaliTextSeekBarViewNew;
import com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class SelectDimFadeTimeDialog extends SmartDialog<DialogSelectDimFadeTimeBinding> {
    private String content = "";
    private int dimFadeTime;
    private int dimFadeTimeCount;
    private int dimFadeTimePos;
    private OnSaveListener onSaveListener;

    public interface OnSaveListener {
        void onSave(int dimFadeTimePos, int dimFadeTime, int dimFadeTimeCount);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_dim_fade_time;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectDimFadeTimeBinding, SelectDimFadeTimeDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectDimFadeTimeBinding viewBinding, final SelectDimFadeTimeDialog dialog) {
            dialog.initView(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDimFadeTimeDialog.AnonymousClass1.lambda$convertView$0(SelectDimFadeTimeDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectDimFadeTimeDialog selectDimFadeTimeDialog, DialogSelectDimFadeTimeBinding dialogSelectDimFadeTimeBinding, View view) {
            int id = view.getId();
            if (id == R.id.layout_extend_fade_time) {
                if (dialogSelectDimFadeTimeBinding.seekbarFadeTime.getProgress() == 0) {
                    selectDimFadeTimeDialog.showCustomDialog(dialogSelectDimFadeTimeBinding);
                }
            } else if (id == R.id.tv_cancel) {
                selectDimFadeTimeDialog.dismissDialog();
            } else {
                if (id != R.id.tv_save) {
                    return;
                }
                if (selectDimFadeTimeDialog.onSaveListener != null) {
                    selectDimFadeTimeDialog.onSaveListener.onSave(dialogSelectDimFadeTimeBinding.seekbarFadeTime.getProgress(), selectDimFadeTimeDialog.dimFadeTime, selectDimFadeTimeDialog.dimFadeTimeCount);
                }
                selectDimFadeTimeDialog.dismissDialog();
            }
        }
    }

    public static SelectDimFadeTimeDialog asDefault() {
        return (SelectDimFadeTimeDialog) new SelectDimFadeTimeDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCustomDialog(final DialogSelectDimFadeTimeBinding viewBinding) {
        int i = this.dimFadeTime;
        int i2 = this.dimFadeTimeCount - 1;
        ArrayList arrayList = new ArrayList();
        for (int i3 = 1; i3 < 17; i3++) {
            arrayList.add(i3 + "");
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.dimming_fade_time)).setMinList(Arrays.asList(getResources().getStringArray(R.array.custom_fade_time))).setSecList(arrayList).withUnit(true).setMinUnit("x").setMinCenter(true).setSelectMinPosition(i).setSelectSecPosition(i2).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i4, int i5) {
                SelectDimFadeTimeDialog.this.lambda$showCustomDialog$0(viewBinding, i4, i5);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCustomDialog$0(DialogSelectDimFadeTimeBinding dialogSelectDimFadeTimeBinding, int i, int i2) {
        this.dimFadeTime = i;
        int i3 = i2 + 1;
        this.dimFadeTimeCount = i3;
        dialogSelectDimFadeTimeBinding.tvFadeTimeValue.setText(getResources().getStringArray(R.array.custom_fade_time)[i]);
        dialogSelectDimFadeTimeBinding.tvFadeTimeTimes.setText(i3 + "");
        if (i == 0) {
            this.content = "0ms";
        } else if (i == 1) {
            this.content = (i3 * 100) + "ms";
        } else if (i == 2) {
            this.content = i3 + NotifyType.SOUND;
        } else if (i == 3) {
            this.content = (i3 * 10) + NotifyType.SOUND;
        } else if (i == 4) {
            this.content = i3 + Constants.MIN;
        }
        dialogSelectDimFadeTimeBinding.tvFadeTimeTotal.setText(this.content);
        dialogSelectDimFadeTimeBinding.seekbarFadeTime.setValue(this.content);
    }

    public SelectDimFadeTimeDialog setFadeTimePosition(int dimFadeTimePos) {
        this.dimFadeTimePos = dimFadeTimePos;
        return this;
    }

    public SelectDimFadeTimeDialog setFadeTime(int dimFadeTime) {
        this.dimFadeTime = dimFadeTime;
        return this;
    }

    public SelectDimFadeTimeDialog setFadeTimeCount(int dimFadeTimeCount) {
        this.dimFadeTimeCount = dimFadeTimeCount;
        return this;
    }

    public SelectDimFadeTimeDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogSelectDimFadeTimeBinding viewBinding) {
        viewBinding.seekbarFadeTime.setTitle(getString(R.string.dimming_fade_time));
        viewBinding.seekbarFadeTime.setList(Arrays.asList(getResources().getStringArray(R.array.fade_time)));
        viewBinding.seekbarFadeTime.setProgress(this.dimFadeTimePos);
        int max = Math.max(this.dimFadeTime, 0);
        int max2 = Math.max(this.dimFadeTimeCount, 0);
        viewBinding.tvFadeTimeValue.setText(getResources().getStringArray(R.array.custom_fade_time)[max]);
        viewBinding.tvFadeTimeTimes.setText(max2 + "");
        if (max == 0) {
            this.content = "0ms";
        } else if (max == 1) {
            this.content = (max2 * 100) + "ms";
        } else if (max == 2) {
            this.content = max2 + NotifyType.SOUND;
        } else if (max == 3) {
            this.content = (max2 * 10) + NotifyType.SOUND;
        } else if (max == 4) {
            this.content = max2 + Constants.MIN;
        }
        viewBinding.tvFadeTimeTotal.setText(this.content);
        setCustomFadeTimeView(viewBinding, this.dimFadeTimePos);
        viewBinding.seekbarFadeTime.setOnProgressChangeListener(new DaliTextSeekBarViewNew.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.DaliTextSeekBarViewNew.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                SelectDimFadeTimeDialog.this.lambda$initView$1(viewBinding, i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(DialogSelectDimFadeTimeBinding dialogSelectDimFadeTimeBinding, int i, boolean z) {
        setCustomFadeTimeView(dialogSelectDimFadeTimeBinding, i);
    }

    private void setCustomFadeTimeView(DialogSelectDimFadeTimeBinding viewBinding, int progress) {
        if (progress != 0) {
            viewBinding.tvExtendFadeTime.setAlpha(0.5f);
            viewBinding.tvFadeTimeValue.setAlpha(0.5f);
            viewBinding.tvFadeTimeX.setAlpha(0.5f);
            viewBinding.tvFadeTimeTimes.setAlpha(0.5f);
            viewBinding.tvFadeTimeEqual.setAlpha(0.5f);
            viewBinding.tvFadeTimeTotal.setAlpha(0.5f);
            return;
        }
        viewBinding.tvExtendFadeTime.setAlpha(1.0f);
        viewBinding.tvFadeTimeValue.setAlpha(1.0f);
        viewBinding.tvFadeTimeX.setAlpha(1.0f);
        viewBinding.tvFadeTimeTimes.setAlpha(1.0f);
        viewBinding.tvFadeTimeEqual.setAlpha(1.0f);
        viewBinding.tvFadeTimeTotal.setAlpha(1.0f);
        viewBinding.seekbarFadeTime.setValue(this.content);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_select_dim_fade_time";
    }
}