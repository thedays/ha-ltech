package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTimeSelectorWithLimitBinding;
import com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class TimeSelectWithLimitDialog extends SmartDialog<DialogTimeSelectorWithLimitBinding> {
    private boolean isAutomationDelay;
    boolean isSelectCustomize;
    PickerLayoutManager mMinuteManager;
    PickerLayoutManager mSecondManager;
    private SelectListener mSelectListener;
    private String minUnit;
    private String saveText;
    private String secUnit;
    private String title;
    private boolean withUnit;
    boolean supportRightAway = true;
    private List<String> minList = new ArrayList();
    private List<String> secList = new ArrayList();
    private int selectMinPosition = -1;
    private int selectSecPosition = -1;

    public interface SelectListener {
        void confirm(int minPosition, int secPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_time_selector_with_limit;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTimeSelectorWithLimitBinding, TimeSelectWithLimitDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogTimeSelectorWithLimitBinding viewBinding, final TimeSelectWithLimitDialog dialog) {
            dialog.initRv(viewBinding);
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                TimeSelectWithLimitDialog.updateTimeData(dialog.minList, dialog.selectMinPosition, viewBinding.pickViewMin);
                viewBinding.setMinUnit(dialog.minUnit);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickerViewSec.setVisibility(0);
                TimeSelectWithLimitDialog.updateTimeDataSec(dialog.secList, dialog.selectMinPosition, dialog.selectSecPosition, viewBinding.pickerViewSec);
                viewBinding.setSecUnit(dialog.secUnit);
            }
            if (dialog.isAutomationDelay) {
                viewBinding.tvTip.setText(R.string.automation_delay_set_tip);
                viewBinding.ivSelect.setVisibility(8);
                viewBinding.layoutChooseTwo.setVisibility(8);
            }
            dialog.isSelectCustomize = (dialog.selectSecPosition == 5 && dialog.selectMinPosition == 0) ? false : true;
            dialog.selectMode(viewBinding, dialog.isSelectCustomize);
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TimeSelectWithLimitDialog.AnonymousClass1.lambda$convertView$0(TimeSelectWithLimitDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(TimeSelectWithLimitDialog timeSelectWithLimitDialog, DialogTimeSelectorWithLimitBinding dialogTimeSelectorWithLimitBinding, View view) {
            switch (view.getId()) {
                case R.id.layout_choose_one /* 2131297395 */:
                    timeSelectWithLimitDialog.selectMode(dialogTimeSelectorWithLimitBinding, true);
                    break;
                case R.id.layout_choose_two /* 2131297396 */:
                    timeSelectWithLimitDialog.selectMode(dialogTimeSelectorWithLimitBinding, false);
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    timeSelectWithLimitDialog.dismissDialog();
                    break;
                case R.id.tv_finish /* 2131298654 */:
                    if (timeSelectWithLimitDialog.mSelectListener != null) {
                        if (timeSelectWithLimitDialog.isSelectCustomize) {
                            if (timeSelectWithLimitDialog.getMinuteManager().getPickedPosition() == 0 && timeSelectWithLimitDialog.getSecondManager().getPickedPosition() < 30) {
                                timeSelectWithLimitDialog.mSelectListener.confirm(0, 30);
                            } else {
                                timeSelectWithLimitDialog.mSelectListener.confirm(timeSelectWithLimitDialog.getMinuteManager().getPickedPosition(), timeSelectWithLimitDialog.getSecondManager().getPickedPosition());
                            }
                        } else {
                            timeSelectWithLimitDialog.mSelectListener.confirm(0, 5);
                        }
                    }
                    timeSelectWithLimitDialog.dismissDialog();
                    break;
            }
        }
    }

    public static TimeSelectWithLimitDialog asDefault() {
        return (TimeSelectWithLimitDialog) new TimeSelectWithLimitDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
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
    public static void updateTimeDataSec(List<String> dataList, int selectMin, int selectPosition, RecyclerView p) {
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
        if (selectMin == 0 && i2 < 30) {
            i2 = 30;
        }
        p.scrollToPosition(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectMode(DialogTimeSelectorWithLimitBinding viewBinding, boolean isCustomize) {
        if (isCustomize) {
            this.isSelectCustomize = true;
            viewBinding.ivSelect.setBackgroundResource(R.mipmap.ic_tick_sel);
            viewBinding.ivSelect2.setBackgroundResource(R.mipmap.ic_tick_default);
            viewBinding.layoutSelect.setAlpha(1.0f);
            viewBinding.pickerViewSec.suppressLayout(false);
            viewBinding.pickViewMin.suppressLayout(false);
            return;
        }
        this.isSelectCustomize = false;
        viewBinding.ivSelect.setBackgroundResource(R.mipmap.ic_tick_default);
        viewBinding.ivSelect2.setBackgroundResource(R.mipmap.ic_tick_sel);
        viewBinding.layoutSelect.setAlpha(0.5f);
        viewBinding.pickerViewSec.suppressLayout(true);
        viewBinding.pickViewMin.suppressLayout(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(final DialogTimeSelectorWithLimitBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        pickerAdapter.setData(this.minList);
        pickerAdapter2.setData(this.secList);
        this.mMinuteManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mSecondManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickViewMin.setLayoutManager(this.mMinuteManager);
        viewBinding.pickerViewSec.setLayoutManager(this.mSecondManager);
        viewBinding.pickViewMin.setAdapter(pickerAdapter);
        viewBinding.pickerViewSec.setAdapter(pickerAdapter2);
        this.mMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeSelectWithLimitDialog.this.lambda$initRv$0(viewBinding, recyclerView, i);
            }
        });
        this.mSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeSelectWithLimitDialog.this.lambda$initRv$1(viewBinding, recyclerView, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(DialogTimeSelectorWithLimitBinding dialogTimeSelectorWithLimitBinding, RecyclerView recyclerView, int i) {
        if (!this.supportRightAway && getMinuteManager().getPickedPosition() == 0 && getSecondManager().getPickedPosition() == 0) {
            dialogTimeSelectorWithLimitBinding.pickerViewSec.scrollToPosition(1);
        }
        this.selectMinPosition = i;
        if (i != 0 || this.selectSecPosition >= 30) {
            return;
        }
        dialogTimeSelectorWithLimitBinding.pickerViewSec.smoothScrollToPosition(30);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(DialogTimeSelectorWithLimitBinding dialogTimeSelectorWithLimitBinding, RecyclerView recyclerView, int i) {
        if (!this.supportRightAway && getMinuteManager().getPickedPosition() == 0 && getSecondManager().getPickedPosition() == 0) {
            dialogTimeSelectorWithLimitBinding.pickerViewSec.scrollToPosition(1);
        }
        this.selectSecPosition = i;
        if (this.selectMinPosition != 0 || i >= 30) {
            return;
        }
        dialogTimeSelectorWithLimitBinding.pickerViewSec.smoothScrollToPosition(30);
    }

    public PickerLayoutManager getMinuteManager() {
        return this.mMinuteManager;
    }

    public void setMinuteManager(PickerLayoutManager mMinuteManager) {
        this.mMinuteManager = mMinuteManager;
    }

    public PickerLayoutManager getSecondManager() {
        return this.mSecondManager;
    }

    public void setSecondManager(PickerLayoutManager mSecondManager) {
        this.mSecondManager = mSecondManager;
    }

    public TimeSelectWithLimitDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public TimeSelectWithLimitDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public TimeSelectWithLimitDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public TimeSelectWithLimitDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public TimeSelectWithLimitDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TimeSelectWithLimitDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public TimeSelectWithLimitDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public TimeSelectWithLimitDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public TimeSelectWithLimitDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    public TimeSelectWithLimitDialog supportRightAway(boolean support) {
        this.supportRightAway = support;
        return this;
    }

    public TimeSelectWithLimitDialog setAutomationDelay(boolean automationDelay) {
        this.isAutomationDelay = automationDelay;
        return this;
    }

    public TimeSelectWithLimitDialog setSelectListener(SelectListener selectListener) {
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