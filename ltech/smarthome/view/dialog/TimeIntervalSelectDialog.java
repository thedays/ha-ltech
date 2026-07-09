package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTimeIntervalSelectorBinding;
import com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class TimeIntervalSelectDialog extends SmartDialog<DialogTimeIntervalSelectorBinding> {
    private String actionTitle;
    private boolean close;
    private String closeTitle;
    PickerLayoutManager mEndMinuteManager;
    PickerLayoutManager mEndSecondManager;
    private SelectListener mSelectListener;
    PickerLayoutManager mStartMinuteManager;
    PickerLayoutManager mStartSecondManager;
    private String minUnit;
    private String saveText;
    private String secUnit;
    private String title;
    private boolean withUnit;
    boolean supportRightAway = true;
    private List<String> minList = new ArrayList();
    private List<String> secList = new ArrayList();
    private int selectStartMinPosition = -1;
    private int selectStartSecPosition = -1;
    private int selectEndMinPosition = -1;
    private int selectEndSecPosition = -1;
    private boolean minCenter = false;

    public interface SelectListener {
        void close();

        void confirm(int startMinPosition, int startSecPosition, int endMinPosition, int endSecPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_time_interval_selector;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTimeIntervalSelectorBinding, TimeIntervalSelectDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogTimeIntervalSelectorBinding viewBinding, final TimeIntervalSelectDialog dialog) {
            for (int i = 0; i < 24; i++) {
                dialog.minList.add(String.format("%02d", Integer.valueOf(i)));
            }
            for (int i2 = 0; i2 < 60; i2++) {
                dialog.secList.add(String.format("%02d", Integer.valueOf(i2)));
            }
            dialog.initRv(viewBinding);
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                viewBinding.pickViewMin2.setVisibility(0);
                TimeIntervalSelectDialog.updateTimeData(dialog.minList, dialog.selectStartMinPosition, viewBinding.pickViewMin);
                TimeIntervalSelectDialog.updateTimeData(dialog.minList, dialog.selectEndMinPosition, viewBinding.pickViewMin2);
                viewBinding.setMinUnit(dialog.minUnit);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickerViewSec.setVisibility(0);
                viewBinding.pickerViewSec2.setVisibility(0);
                TimeIntervalSelectDialog.updateTimeData(dialog.secList, dialog.selectStartSecPosition, viewBinding.pickerViewSec);
                TimeIntervalSelectDialog.updateTimeData(dialog.secList, dialog.selectEndSecPosition, viewBinding.pickerViewSec2);
                viewBinding.setSecUnit(dialog.secUnit);
            }
            if (dialog.selectEndMinPosition == -1 || dialog.selectStartMinPosition == -1 || dialog.selectStartSecPosition == -1 || dialog.selectEndSecPosition == -1) {
                viewBinding.ivSel1.setImageResource(R.mipmap.ic_tick_sel);
                viewBinding.ivSel2.setImageResource(0);
                viewBinding.groupTime.setVisibility(8);
            } else if (dialog.close) {
                viewBinding.ivSel1.setImageResource(R.mipmap.ic_tick_sel);
                viewBinding.ivSel2.setImageResource(0);
                viewBinding.groupTime.setVisibility(8);
            } else {
                viewBinding.ivSel1.setImageResource(0);
                viewBinding.ivSel2.setImageResource(R.mipmap.ic_tick_sel);
                viewBinding.groupTime.setVisibility(0);
            }
            viewBinding.tvTitle.setText(dialog.title);
            if (!StringUtils.isEmpty(dialog.actionTitle)) {
                viewBinding.lable2.setText(dialog.actionTitle);
            }
            if (!StringUtils.isEmpty(dialog.closeTitle)) {
                viewBinding.lable1.setText(dialog.closeTitle);
            }
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TimeIntervalSelectDialog.AnonymousClass1.lambda$convertView$0(DialogTimeIntervalSelectorBinding.this, dialog, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(DialogTimeIntervalSelectorBinding dialogTimeIntervalSelectorBinding, TimeIntervalSelectDialog timeIntervalSelectDialog, View view) {
            switch (view.getId()) {
                case R.id.lable1 /* 2131297337 */:
                    dialogTimeIntervalSelectorBinding.groupTime.setVisibility(8);
                    dialogTimeIntervalSelectorBinding.ivSel1.setImageResource(R.mipmap.ic_tick_sel);
                    dialogTimeIntervalSelectorBinding.ivSel2.setImageResource(0);
                    break;
                case R.id.lable2 /* 2131297338 */:
                    dialogTimeIntervalSelectorBinding.groupTime.setVisibility(0);
                    dialogTimeIntervalSelectorBinding.ivSel1.setImageResource(0);
                    dialogTimeIntervalSelectorBinding.ivSel2.setImageResource(R.mipmap.ic_tick_sel);
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    timeIntervalSelectDialog.dismissDialog();
                    break;
                case R.id.tv_finish /* 2131298654 */:
                    if (timeIntervalSelectDialog.mSelectListener != null) {
                        if (dialogTimeIntervalSelectorBinding.groupTime.getVisibility() == 8) {
                            timeIntervalSelectDialog.mSelectListener.close();
                        } else {
                            timeIntervalSelectDialog.mSelectListener.confirm(timeIntervalSelectDialog.getStartMinuteManager().getPickedPosition(), timeIntervalSelectDialog.getStartSecondManager().getPickedPosition(), timeIntervalSelectDialog.getEndMinuteManager().getPickedPosition(), timeIntervalSelectDialog.getEndSecondManager().getPickedPosition());
                        }
                    }
                    timeIntervalSelectDialog.dismissDialog();
                    break;
            }
        }
    }

    public static TimeIntervalSelectDialog asDefault() {
        return (TimeIntervalSelectDialog) new TimeIntervalSelectDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
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
    public void initRv(final DialogTimeIntervalSelectorBinding viewBinding) {
        final PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        final PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        final PickerAdapter pickerAdapter3 = new PickerAdapter(getContext());
        final PickerAdapter pickerAdapter4 = new PickerAdapter(getContext());
        pickerAdapter.setData(this.minList);
        pickerAdapter2.setData(this.secList);
        pickerAdapter3.setData(this.minList);
        pickerAdapter4.setData(this.secList);
        this.mStartMinuteManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(3).build();
        this.mStartSecondManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(3).build();
        this.mEndMinuteManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(3).build();
        this.mEndSecondManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(3).build();
        viewBinding.pickViewMin.setLayoutManager(this.mStartMinuteManager);
        viewBinding.pickerViewSec.setLayoutManager(this.mStartSecondManager);
        viewBinding.pickViewMin.setAdapter(pickerAdapter);
        viewBinding.pickerViewSec.setAdapter(pickerAdapter2);
        viewBinding.pickViewMin2.setLayoutManager(this.mEndMinuteManager);
        viewBinding.pickerViewSec2.setLayoutManager(this.mEndSecondManager);
        viewBinding.pickViewMin2.setAdapter(pickerAdapter3);
        viewBinding.pickerViewSec2.setAdapter(pickerAdapter4);
        this.mStartMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeIntervalSelectDialog.this.lambda$initRv$0(pickerAdapter, viewBinding, recyclerView, i);
            }
        });
        this.mStartSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeIntervalSelectDialog.this.lambda$initRv$1(pickerAdapter2, viewBinding, recyclerView, i);
            }
        });
        this.mEndMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeIntervalSelectDialog.this.lambda$initRv$2(pickerAdapter3, viewBinding, recyclerView, i);
            }
        });
        this.mEndSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimeIntervalSelectDialog$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                TimeIntervalSelectDialog.this.lambda$initRv$3(pickerAdapter4, viewBinding, recyclerView, i);
            }
        });
        int i = this.selectStartMinPosition;
        if (i != -1 && this.selectStartSecPosition != -1 && this.selectEndMinPosition != -1 && this.selectEndSecPosition != -1) {
            pickerAdapter.setPosition(i);
            pickerAdapter2.setPosition(this.selectStartSecPosition);
            pickerAdapter3.setPosition(this.selectEndMinPosition);
            pickerAdapter4.setPosition(this.selectEndSecPosition);
        }
        if (this.minCenter) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewBinding.tvMinTip.getLayoutParams();
            layoutParams.endToStart = R.id.picker_view_sec;
            viewBinding.tvMinTip.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(PickerAdapter pickerAdapter, DialogTimeIntervalSelectorBinding dialogTimeIntervalSelectorBinding, RecyclerView recyclerView, int i) {
        pickerAdapter.setPosition(i);
        if (!this.supportRightAway && getStartMinuteManager().getPickedPosition() == 0 && getStartSecondManager().getPickedPosition() == 0) {
            dialogTimeIntervalSelectorBinding.pickerViewSec.scrollToPosition(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(PickerAdapter pickerAdapter, DialogTimeIntervalSelectorBinding dialogTimeIntervalSelectorBinding, RecyclerView recyclerView, int i) {
        pickerAdapter.setPosition(i);
        if (!this.supportRightAway && getStartMinuteManager().getPickedPosition() == 0 && getStartSecondManager().getPickedPosition() == 0) {
            dialogTimeIntervalSelectorBinding.pickerViewSec.scrollToPosition(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$2(PickerAdapter pickerAdapter, DialogTimeIntervalSelectorBinding dialogTimeIntervalSelectorBinding, RecyclerView recyclerView, int i) {
        pickerAdapter.setPosition(i);
        if (!this.supportRightAway && getEndMinuteManager().getPickedPosition() == 0 && getEndSecondManager().getPickedPosition() == 0) {
            dialogTimeIntervalSelectorBinding.pickerViewSec2.scrollToPosition(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$3(PickerAdapter pickerAdapter, DialogTimeIntervalSelectorBinding dialogTimeIntervalSelectorBinding, RecyclerView recyclerView, int i) {
        pickerAdapter.setPosition(i);
        if (!this.supportRightAway && getEndMinuteManager().getPickedPosition() == 0 && getEndSecondManager().getPickedPosition() == 0) {
            dialogTimeIntervalSelectorBinding.pickerViewSec2.scrollToPosition(1);
        }
    }

    public PickerLayoutManager getStartMinuteManager() {
        return this.mStartMinuteManager;
    }

    public PickerLayoutManager getStartSecondManager() {
        return this.mStartSecondManager;
    }

    public PickerLayoutManager getEndMinuteManager() {
        return this.mEndMinuteManager;
    }

    public PickerLayoutManager getEndSecondManager() {
        return this.mEndSecondManager;
    }

    public TimeIntervalSelectDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public TimeIntervalSelectDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public TimeIntervalSelectDialog setSelectStartMinPosition(int selectMinPosition) {
        this.selectStartMinPosition = selectMinPosition;
        return this;
    }

    public TimeIntervalSelectDialog setSelectStartSecPosition(int selectSecPosition) {
        this.selectStartSecPosition = selectSecPosition;
        return this;
    }

    public TimeIntervalSelectDialog setSelectEndMinPosition(int selectMinPosition) {
        this.selectEndMinPosition = selectMinPosition;
        return this;
    }

    public TimeIntervalSelectDialog setSelectEndSecPosition(int selectSecPosition) {
        this.selectEndSecPosition = selectSecPosition;
        return this;
    }

    public TimeIntervalSelectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TimeIntervalSelectDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public TimeIntervalSelectDialog setActionTitle(String title) {
        this.actionTitle = title;
        return this;
    }

    public TimeIntervalSelectDialog setCloseTitle(String title) {
        this.closeTitle = title;
        return this;
    }

    public TimeIntervalSelectDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public TimeIntervalSelectDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public TimeIntervalSelectDialog setMinCenter(boolean minCenter) {
        this.minCenter = minCenter;
        return this;
    }

    public TimeIntervalSelectDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    public TimeIntervalSelectDialog supportRightAway(boolean support) {
        this.supportRightAway = support;
        return this;
    }

    public TimeIntervalSelectDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }

    public TimeIntervalSelectDialog setClose(boolean close) {
        this.close = close;
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