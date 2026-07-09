package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogIntercomTimePickerBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.view.dialog.IntercomTimePickerDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class IntercomTimePickerDialog extends SmartDialog<DialogIntercomTimePickerBinding> {
    PickerLayoutManager mDayManager;
    PickerLayoutManager mHourManager;
    PickerLayoutManager mMinuteManager;
    PickerLayoutManager mMonthManager;
    private SelectListener mSelectListener;
    private String saveText;
    private String title;
    private int selectMonthPosition = -1;
    private int selectDayPosition = -1;
    private int selectHourPosition = -1;
    private int selectMinPosition = -1;
    private List<String> monthList = new ArrayList();
    private List<String> dayList = new ArrayList();
    private List<String> hourList = new ArrayList();
    private List<String> minList = new ArrayList();

    public interface SelectListener {
        void confirm(int monthPosition, int dayPosition, int hourPosition, int minPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_intercom_time_picker;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.IntercomTimePickerDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogIntercomTimePickerBinding, IntercomTimePickerDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogIntercomTimePickerBinding viewBinding, final IntercomTimePickerDialog dialog) {
            dialog.initRv(viewBinding);
            if (!dialog.monthList.isEmpty()) {
                IntercomTimePickerDialog.updateTimeData(dialog.monthList, dialog.selectMonthPosition, viewBinding.pickViewMonth);
            }
            if (!dialog.dayList.isEmpty()) {
                IntercomTimePickerDialog.updateTimeData(dialog.dayList, dialog.selectDayPosition, viewBinding.pickViewDay);
            }
            if (!dialog.hourList.isEmpty()) {
                IntercomTimePickerDialog.updateTimeData(dialog.hourList, dialog.selectHourPosition, viewBinding.pickViewHour);
            }
            if (!dialog.minList.isEmpty()) {
                IntercomTimePickerDialog.updateTimeData(dialog.minList, dialog.selectMinPosition, viewBinding.pickViewMin);
            }
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.IntercomTimePickerDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    IntercomTimePickerDialog.AnonymousClass1.lambda$convertView$0(IntercomTimePickerDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(IntercomTimePickerDialog intercomTimePickerDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                intercomTimePickerDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (intercomTimePickerDialog.mSelectListener != null) {
                    intercomTimePickerDialog.mSelectListener.confirm(intercomTimePickerDialog.mMonthManager.getPickedPosition(), intercomTimePickerDialog.mDayManager.getPickedPosition(), intercomTimePickerDialog.mHourManager.getPickedPosition(), intercomTimePickerDialog.mMinuteManager.getPickedPosition());
                }
                intercomTimePickerDialog.dismissDialog();
            }
        }
    }

    public static IntercomTimePickerDialog asDefault() {
        return (IntercomTimePickerDialog) new IntercomTimePickerDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateTimeData(List<String> dataList, int selectPosition, RecyclerView p) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dataList.size(); i++) {
            arrayList.add(new SelectItem(i, dataList.get(i)));
        }
        int i2 = 0;
        for (int i3 = 0; i3 < dataList.size(); i3++) {
            if (((SelectItem) arrayList.get(i3)).position == selectPosition) {
                i2 = i3;
            }
        }
        p.scrollToPosition(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogIntercomTimePickerBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        final PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter3 = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter4 = new PickerAdapter(getContext());
        pickerAdapter.setData(this.monthList);
        pickerAdapter2.setData(this.dayList);
        pickerAdapter3.setData(this.hourList);
        pickerAdapter4.setData(this.minList);
        this.mMonthManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mDayManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mHourManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mMinuteManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickViewMonth.setLayoutManager(this.mMonthManager);
        viewBinding.pickViewDay.setLayoutManager(this.mDayManager);
        viewBinding.pickViewHour.setLayoutManager(this.mHourManager);
        viewBinding.pickViewMin.setLayoutManager(this.mMinuteManager);
        viewBinding.pickViewMonth.setAdapter(pickerAdapter);
        viewBinding.pickViewDay.setAdapter(pickerAdapter2);
        viewBinding.pickViewHour.setAdapter(pickerAdapter3);
        viewBinding.pickViewMin.setAdapter(pickerAdapter4);
        this.mMonthManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.IntercomTimePickerDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                IntercomTimePickerDialog.this.lambda$initRv$0(pickerAdapter2, recyclerView, i);
            }
        });
        final int parseInt = Integer.parseInt(new SimpleDateFormat("dd").format(new Date())) - 1;
        this.mDayManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.IntercomTimePickerDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                IntercomTimePickerDialog.lambda$initRv$1(parseInt, recyclerView, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(PickerAdapter pickerAdapter, RecyclerView recyclerView, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(4);
        arrayList.add(6);
        arrayList.add(9);
        arrayList.add(11);
        if (arrayList.contains(Integer.valueOf(this.mMonthManager.getPickedPosition() - 1))) {
            if (this.dayList.size() == 31) {
                this.dayList.remove(r0.size() - 1);
                pickerAdapter.notifyDataSetChanged();
            }
        } else if (this.dayList.size() == 30) {
            this.dayList.add(ProductId.BLE_SMART_PANEL_SUB_TYPE_S6);
            pickerAdapter.notifyDataSetChanged();
        }
        int i2 = this.selectMonthPosition;
        if (i != i2) {
            recyclerView.smoothScrollToPosition(i2);
        }
    }

    static /* synthetic */ void lambda$initRv$1(int i, RecyclerView recyclerView, int i2) {
        if (i2 - i > 1) {
            recyclerView.smoothScrollToPosition(i + 1);
        } else if (i2 < i) {
            recyclerView.smoothScrollToPosition(i);
        }
    }

    public IntercomTimePickerDialog setMonthList(List<String> list) {
        this.monthList.clear();
        this.monthList.addAll(list);
        return this;
    }

    public IntercomTimePickerDialog setDayList(List<String> list) {
        this.dayList.clear();
        this.dayList.addAll(list);
        return this;
    }

    public IntercomTimePickerDialog setHourList(List<String> list) {
        this.hourList.clear();
        this.hourList.addAll(list);
        return this;
    }

    public IntercomTimePickerDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public IntercomTimePickerDialog setSelectMonthPosition(int selectMonthPosition) {
        this.selectMonthPosition = selectMonthPosition;
        return this;
    }

    public IntercomTimePickerDialog setSelectDayPosition(int selectDayPosition) {
        this.selectDayPosition = selectDayPosition;
        return this;
    }

    public IntercomTimePickerDialog setSelectHourPosition(int selectHourPosition) {
        this.selectHourPosition = selectHourPosition;
        return this;
    }

    public IntercomTimePickerDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public IntercomTimePickerDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public IntercomTimePickerDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public IntercomTimePickerDialog setConfirmListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }

    private static final class SelectItem {
        String itemString;
        int position;

        public SelectItem(int position, String itemString) {
            this.position = position;
            this.itemString = itemString;
        }
    }
}