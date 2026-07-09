package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBinding;
import com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class TimeSelectWithMsDialog extends SmartDialog<DialogTimeSelectorWithMsBinding> {
    PickerLayoutManager mMinuteManager;
    PickerLayoutManager mMsManager;
    PickerLayoutManager mSecondManager;
    private SelectListener mSelectListener;
    private String saveText;
    private String title;
    private boolean withUnit;
    boolean supportRightAway = true;
    private List<String> minList = new ArrayList();
    private List<String> secList = new ArrayList();
    private List<String> msList = new ArrayList();
    private int selectMinPosition = -1;
    private int selectSecPosition = -1;
    private int selectMsPosition = -1;

    public interface SelectListener {
        void confirm(int minPosition, int secPosition, int msPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_time_selector_with_ms;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTimeSelectorWithMsBinding, TimeSelectWithMsDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogTimeSelectorWithMsBinding viewBinding, final TimeSelectWithMsDialog dialog) {
            dialog.initRv(viewBinding);
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                TimeSelectWithMsDialog.updateTimeData(dialog.minList, dialog.selectMinPosition, viewBinding.pickViewMin);
            } else {
                viewBinding.pickViewMin.setVisibility(8);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickViewSec.setVisibility(0);
                TimeSelectWithMsDialog.updateTimeData(dialog.secList, dialog.selectSecPosition, viewBinding.pickViewSec);
            } else {
                viewBinding.pickViewSec.setVisibility(8);
            }
            if (!dialog.msList.isEmpty()) {
                viewBinding.pickViewMs.setVisibility(0);
                TimeSelectWithMsDialog.updateTimeData(dialog.msList, dialog.selectMsPosition, viewBinding.pickViewMs);
            } else {
                viewBinding.pickViewMs.setVisibility(8);
            }
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TimeSelectWithMsDialog.AnonymousClass1.lambda$convertView$0(TimeSelectWithMsDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(TimeSelectWithMsDialog timeSelectWithMsDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                timeSelectWithMsDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (timeSelectWithMsDialog.mSelectListener != null) {
                    timeSelectWithMsDialog.mSelectListener.confirm(timeSelectWithMsDialog.mMinuteManager.getPickedPosition(), timeSelectWithMsDialog.mSecondManager.getPickedPosition(), timeSelectWithMsDialog.mMsManager.getPickedPosition());
                }
                timeSelectWithMsDialog.dismissDialog();
            }
        }
    }

    public static TimeSelectWithMsDialog asDefault() {
        return (TimeSelectWithMsDialog) new TimeSelectWithMsDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
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
    public void initRv(final DialogTimeSelectorWithMsBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter3 = new PickerAdapter(getContext());
        pickerAdapter.setData(this.minList);
        pickerAdapter2.setData(this.secList);
        pickerAdapter3.setData(this.msList);
        this.mMinuteManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mSecondManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mMsManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickViewMin.setLayoutManager(this.mMinuteManager);
        viewBinding.pickViewSec.setLayoutManager(this.mSecondManager);
        viewBinding.pickViewMs.setLayoutManager(this.mMsManager);
        viewBinding.pickViewMin.setAdapter(pickerAdapter);
        viewBinding.pickViewSec.setAdapter(pickerAdapter2);
        viewBinding.pickViewMs.setAdapter(pickerAdapter3);
        PickerLayoutManager.OnPickerListener onPickerListener = new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeSelectWithMsDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeSelectWithMsDialog.this.lambda$initRv$0(viewBinding, recyclerView, i);
            }
        };
        this.mMinuteManager.setOnPickerListener(onPickerListener);
        this.mSecondManager.setOnPickerListener(onPickerListener);
        this.mMsManager.setOnPickerListener(onPickerListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(DialogTimeSelectorWithMsBinding dialogTimeSelectorWithMsBinding, RecyclerView recyclerView, int i) {
        if (!this.supportRightAway && this.mMinuteManager.getPickedPosition() == 0 && this.mSecondManager.getPickedPosition() == 0 && this.mMsManager.getPickedPosition() == 0) {
            dialogTimeSelectorWithMsBinding.pickViewSec.scrollToPosition(1);
        }
    }

    public TimeSelectWithMsDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public TimeSelectWithMsDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public TimeSelectWithMsDialog setMsList(List<String> list) {
        this.msList.clear();
        this.msList.addAll(list);
        return this;
    }

    public TimeSelectWithMsDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public TimeSelectWithMsDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public TimeSelectWithMsDialog setSelectMsPosition(int selectMsPosition) {
        this.selectMsPosition = selectMsPosition;
        return this;
    }

    public TimeSelectWithMsDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TimeSelectWithMsDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public TimeSelectWithMsDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public TimeSelectWithMsDialog supportRightAway(boolean support) {
        this.supportRightAway = support;
        return this;
    }

    public TimeSelectWithMsDialog setSelectListener(SelectListener selectListener) {
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