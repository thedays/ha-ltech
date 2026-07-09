package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTimePickerBinding;
import com.ltech.smarthome.view.MyTimePickerView;
import com.ltech.smarthome.view.dialog.TimePickerDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class TimePickerDialog extends SmartDialog<DialogTimePickerBinding> {
    private SelectListener mSelectListener;
    private String minUnit;
    private String secUnit;
    private String title;
    private boolean withUnit;
    private List<String> minList = new ArrayList();
    private List<String> secList = new ArrayList();
    private int selectMinPosition = -1;
    private int selectSecPosition = -1;

    public interface SelectListener {
        void confirm(int minPosition, int secPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_time_picker;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TimePickerDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTimePickerBinding, TimePickerDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogTimePickerBinding viewBinding, final TimePickerDialog dialog) {
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                viewBinding.pickViewMin.setData(dialog.minList);
                viewBinding.pickViewMin.setSelectedPosition(dialog.selectMinPosition);
                viewBinding.pickViewMin.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
                viewBinding.pickViewMin.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.TimePickerDialog$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                    public final void onSelect(int i, String str) {
                        TimePickerDialog.this.selectMinPosition = i;
                    }
                });
                viewBinding.setMinUnit(dialog.minUnit);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickerViewSec.setVisibility(0);
                viewBinding.pickerViewSec.setData(dialog.secList);
                viewBinding.pickerViewSec.setSelectedPosition(dialog.selectSecPosition);
                viewBinding.pickerViewSec.setMainTextColor(ContextCompat.getColor(dialog.getContext(), R.color.color_text_black));
                viewBinding.pickerViewSec.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.view.dialog.TimePickerDialog$1$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
                    public final void onSelect(int i, String str) {
                        TimePickerDialog.this.selectSecPosition = i;
                    }
                });
                viewBinding.setSecUnit(dialog.secUnit);
            }
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TimePickerDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TimePickerDialog.AnonymousClass1.lambda$convertView$2(TimePickerDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$2(TimePickerDialog timePickerDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                timePickerDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (timePickerDialog.mSelectListener != null) {
                    timePickerDialog.mSelectListener.confirm(timePickerDialog.selectMinPosition, timePickerDialog.selectSecPosition);
                }
                timePickerDialog.dismissDialog();
            }
        }
    }

    public static TimePickerDialog asDefault() {
        return (TimePickerDialog) new TimePickerDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
    }

    public TimePickerDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public TimePickerDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public TimePickerDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public TimePickerDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public TimePickerDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TimePickerDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public TimePickerDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public TimePickerDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "time_picker_dialog";
    }

    public TimePickerDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }
}