package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogGradualTimeBinding;
import com.ltech.smarthome.view.MyTimePickerView;
import com.ltech.smarthome.view.dialog.GradualTimeDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class GradualTimeDialog extends SmartDialog<DialogGradualTimeBinding> {
    private String hourUnit;
    private SelectListener mSelectListener;
    private String minUnit;
    private String msUnit;
    private String secUnit;
    private String title;
    private boolean withUnit;
    private List<String> hourList = new ArrayList();
    private List<String> minList = new ArrayList();
    private List<String> secList = new ArrayList();
    private List<String> msList = new ArrayList();
    private int selectHourPosition = -1;
    private int selectMinPosition = -1;
    private int selectSecPosition = -1;
    private int selectMsPosition = -1;

    public interface SelectListener {
        void confirm(int hourPosition, int minPosition, int secPosition, int msPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_gradual_time;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.GradualTimeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogGradualTimeBinding, GradualTimeDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogGradualTimeBinding viewBinding, final GradualTimeDialog dialog) {
            viewBinding.pickViewHour.setData(dialog.hourList);
            viewBinding.pickViewHour.setSelectedPosition(dialog.selectHourPosition);
            viewBinding.pickViewHour.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewHour.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.GradualTimeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    GradualTimeDialog.AnonymousClass1.lambda$convertView$0(GradualTimeDialog.this, viewBinding, i, str);
                }
            });
            viewBinding.setHourUnit(dialog.hourUnit);
            viewBinding.pickViewMin.setData(dialog.minList);
            viewBinding.pickViewMin.setSelectedPosition(dialog.selectMinPosition);
            viewBinding.pickViewMin.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickViewMin.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.GradualTimeDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    GradualTimeDialog.AnonymousClass1.lambda$convertView$1(GradualTimeDialog.this, viewBinding, i, str);
                }
            });
            viewBinding.setMinUnit(dialog.minUnit);
            viewBinding.pickerViewSec.setData(dialog.secList);
            viewBinding.pickerViewSec.setSelectedPosition(dialog.selectSecPosition);
            viewBinding.pickerViewSec.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickerViewSec.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.GradualTimeDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    GradualTimeDialog.AnonymousClass1.lambda$convertView$2(GradualTimeDialog.this, viewBinding, i, str);
                }
            });
            viewBinding.setSecUnit(dialog.secUnit);
            viewBinding.pickerViewMs.setData(dialog.msList);
            viewBinding.pickerViewMs.setSelectedPosition(dialog.selectMsPosition);
            viewBinding.pickerViewMs.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
            viewBinding.pickerViewMs.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.GradualTimeDialog$1$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                public final void onSelect(int i, String str) {
                    GradualTimeDialog.AnonymousClass1.lambda$convertView$3(GradualTimeDialog.this, viewBinding, i, str);
                }
            });
            viewBinding.setMsUnit(dialog.msUnit);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.GradualTimeDialog$1$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    GradualTimeDialog.AnonymousClass1.lambda$convertView$4(GradualTimeDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(GradualTimeDialog gradualTimeDialog, DialogGradualTimeBinding dialogGradualTimeBinding, int i, String str) {
            gradualTimeDialog.selectHourPosition = i;
            gradualTimeDialog.selectMsPosition = 0;
            dialogGradualTimeBinding.pickerViewMs.setData(gradualTimeDialog.msList);
            dialogGradualTimeBinding.pickerViewMs.setSelectedPosition(gradualTimeDialog.selectMsPosition);
            dialogGradualTimeBinding.pickerViewMs.setMainTextColor(ContextCompat.getColor(gradualTimeDialog.getContext(), R.color.color_text_black));
            dialogGradualTimeBinding.setMsUnit(gradualTimeDialog.msUnit);
            gradualTimeDialog.selectSecPosition = 0;
            dialogGradualTimeBinding.pickerViewSec.setData(gradualTimeDialog.secList);
            dialogGradualTimeBinding.pickerViewSec.setSelectedPosition(gradualTimeDialog.selectSecPosition);
            dialogGradualTimeBinding.pickerViewSec.setMainTextColor(ContextCompat.getColor(gradualTimeDialog.getContext(), R.color.color_text_black));
            dialogGradualTimeBinding.setSecUnit(gradualTimeDialog.secUnit);
        }

        static /* synthetic */ void lambda$convertView$1(GradualTimeDialog gradualTimeDialog, DialogGradualTimeBinding dialogGradualTimeBinding, int i, String str) {
            gradualTimeDialog.selectMinPosition = i;
            gradualTimeDialog.selectMsPosition = 0;
            dialogGradualTimeBinding.pickerViewMs.setData(gradualTimeDialog.msList);
            dialogGradualTimeBinding.pickerViewMs.setSelectedPosition(gradualTimeDialog.selectMsPosition);
            dialogGradualTimeBinding.pickerViewMs.setMainTextColor(ContextCompat.getColor(gradualTimeDialog.getContext(), R.color.color_text_black));
            dialogGradualTimeBinding.setMsUnit(gradualTimeDialog.msUnit);
        }

        static /* synthetic */ void lambda$convertView$2(GradualTimeDialog gradualTimeDialog, DialogGradualTimeBinding dialogGradualTimeBinding, int i, String str) {
            gradualTimeDialog.selectSecPosition = i;
            gradualTimeDialog.selectHourPosition = 0;
            dialogGradualTimeBinding.pickViewHour.setData(gradualTimeDialog.hourList);
            dialogGradualTimeBinding.pickViewHour.setSelectedPosition(gradualTimeDialog.selectHourPosition);
            dialogGradualTimeBinding.pickViewHour.setMainTextColor(ContextCompat.getColor(gradualTimeDialog.getContext(), R.color.color_text_black));
            dialogGradualTimeBinding.setHourUnit(gradualTimeDialog.hourUnit);
        }

        static /* synthetic */ void lambda$convertView$3(GradualTimeDialog gradualTimeDialog, DialogGradualTimeBinding dialogGradualTimeBinding, int i, String str) {
            gradualTimeDialog.selectMsPosition = i;
            gradualTimeDialog.selectHourPosition = 0;
            dialogGradualTimeBinding.pickViewHour.setData(gradualTimeDialog.hourList);
            dialogGradualTimeBinding.pickViewHour.setSelectedPosition(gradualTimeDialog.selectHourPosition);
            dialogGradualTimeBinding.pickViewHour.setMainTextColor(ContextCompat.getColor(gradualTimeDialog.getContext(), R.color.color_text_black));
            dialogGradualTimeBinding.setHourUnit(gradualTimeDialog.hourUnit);
            gradualTimeDialog.selectMinPosition = 0;
            dialogGradualTimeBinding.pickViewMin.setData(gradualTimeDialog.minList);
            dialogGradualTimeBinding.pickViewMin.setSelectedPosition(gradualTimeDialog.selectMinPosition);
            dialogGradualTimeBinding.pickViewMin.setMainTextColor(ContextCompat.getColor(gradualTimeDialog.getContext(), R.color.color_text_black));
            dialogGradualTimeBinding.setMinUnit(gradualTimeDialog.minUnit);
        }

        static /* synthetic */ void lambda$convertView$4(GradualTimeDialog gradualTimeDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                gradualTimeDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (gradualTimeDialog.mSelectListener != null) {
                    gradualTimeDialog.mSelectListener.confirm(gradualTimeDialog.selectHourPosition, gradualTimeDialog.selectMinPosition, gradualTimeDialog.selectSecPosition, gradualTimeDialog.selectMsPosition);
                }
                gradualTimeDialog.dismissDialog();
            }
        }
    }

    public static GradualTimeDialog asDefault() {
        return (GradualTimeDialog) new GradualTimeDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
    }

    public GradualTimeDialog setHourList(List<String> list) {
        this.hourList.clear();
        this.hourList.addAll(list);
        return this;
    }

    public GradualTimeDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public GradualTimeDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public GradualTimeDialog setMsList(List<String> list) {
        this.msList.clear();
        this.msList.addAll(list);
        return this;
    }

    public GradualTimeDialog setSelectHourPosition(int selectHourPosition) {
        this.selectHourPosition = selectHourPosition;
        return this;
    }

    public GradualTimeDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public GradualTimeDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public GradualTimeDialog setSelectMsPosition(int selectMsPosition) {
        this.selectMsPosition = selectMsPosition;
        return this;
    }

    public GradualTimeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public GradualTimeDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public GradualTimeDialog setHourUnit(String hourUnit) {
        this.hourUnit = hourUnit;
        return this;
    }

    public GradualTimeDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public GradualTimeDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    public GradualTimeDialog setMsUnit(String msUnit) {
        this.msUnit = msUnit;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "gradual_time_dialog";
    }

    public GradualTimeDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }
}