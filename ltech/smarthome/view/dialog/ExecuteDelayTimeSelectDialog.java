package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogExecuteDelayTimeSelectorBinding;
import com.ltech.smarthome.view.dialog.ExecuteDelayTimeSelectDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class ExecuteDelayTimeSelectDialog extends SmartDialog<DialogExecuteDelayTimeSelectorBinding> {
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
    private boolean minCenter = false;

    public interface SelectListener {
        void confirm(int minPosition, int secPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_execute_delay_time_selector;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.ExecuteDelayTimeSelectDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogExecuteDelayTimeSelectorBinding, ExecuteDelayTimeSelectDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogExecuteDelayTimeSelectorBinding viewBinding, final ExecuteDelayTimeSelectDialog dialog) {
            dialog.initRv(viewBinding);
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                ExecuteDelayTimeSelectDialog.updateTimeData(dialog.minList, dialog.selectMinPosition, viewBinding.pickViewMin);
                viewBinding.setMinUnit(dialog.minUnit);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickerViewSec.setVisibility(0);
                ExecuteDelayTimeSelectDialog.updateTimeData(dialog.secList, dialog.selectSecPosition, viewBinding.pickerViewSec);
                viewBinding.setSecUnit(dialog.secUnit);
            }
            if (dialog.minList.isEmpty() ? dialog.selectSecPosition < 0 : dialog.selectSecPosition + dialog.selectMinPosition < 0) {
                viewBinding.ivSel1.setImageResource(0);
                viewBinding.ivSel2.setImageResource(R.mipmap.ic_tick_sel);
                viewBinding.pickerViewSec.setVisibility(8);
                viewBinding.pickViewMin.setVisibility(8);
            } else {
                viewBinding.ivSel1.setImageResource(R.mipmap.ic_tick_sel);
                viewBinding.ivSel2.setImageResource(0);
                viewBinding.pickerViewSec.setVisibility(0);
                viewBinding.pickViewMin.setVisibility(0);
            }
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.ExecuteDelayTimeSelectDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ExecuteDelayTimeSelectDialog.AnonymousClass1.lambda$convertView$0(ExecuteDelayTimeSelectDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(ExecuteDelayTimeSelectDialog executeDelayTimeSelectDialog, DialogExecuteDelayTimeSelectorBinding dialogExecuteDelayTimeSelectorBinding, View view) {
            switch (view.getId()) {
                case R.id.lable1 /* 2131297337 */:
                    executeDelayTimeSelectDialog.selectSecPosition = 0;
                    executeDelayTimeSelectDialog.selectMinPosition = 0;
                    dialogExecuteDelayTimeSelectorBinding.pickerViewSec.setVisibility(0);
                    dialogExecuteDelayTimeSelectorBinding.pickViewMin.setVisibility(0);
                    dialogExecuteDelayTimeSelectorBinding.ivSel1.setImageResource(R.mipmap.ic_tick_sel);
                    dialogExecuteDelayTimeSelectorBinding.ivSel2.setImageResource(0);
                    break;
                case R.id.lable2 /* 2131297338 */:
                    executeDelayTimeSelectDialog.selectSecPosition = -1;
                    executeDelayTimeSelectDialog.selectMinPosition = -1;
                    dialogExecuteDelayTimeSelectorBinding.pickerViewSec.setVisibility(8);
                    dialogExecuteDelayTimeSelectorBinding.pickViewMin.setVisibility(8);
                    dialogExecuteDelayTimeSelectorBinding.ivSel1.setImageResource(0);
                    dialogExecuteDelayTimeSelectorBinding.ivSel2.setImageResource(R.mipmap.ic_tick_sel);
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    executeDelayTimeSelectDialog.dismissDialog();
                    break;
                case R.id.tv_finish /* 2131298654 */:
                    if (executeDelayTimeSelectDialog.mSelectListener != null) {
                        if (dialogExecuteDelayTimeSelectorBinding.pickerViewSec.getVisibility() == 8) {
                            executeDelayTimeSelectDialog.mSelectListener.confirm(-1, -1);
                        } else {
                            executeDelayTimeSelectDialog.mSelectListener.confirm(executeDelayTimeSelectDialog.getMinuteManager().getPickedPosition(), executeDelayTimeSelectDialog.getSecondManager().getPickedPosition());
                        }
                    }
                    executeDelayTimeSelectDialog.dismissDialog();
                    break;
            }
        }
    }

    public static ExecuteDelayTimeSelectDialog asDefault() {
        return (ExecuteDelayTimeSelectDialog) new ExecuteDelayTimeSelectDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
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
    public void initRv(final DialogExecuteDelayTimeSelectorBinding viewBinding) {
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
        this.mMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.ExecuteDelayTimeSelectDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                ExecuteDelayTimeSelectDialog.this.lambda$initRv$0(viewBinding, recyclerView, i);
            }
        });
        this.mSecondManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.ExecuteDelayTimeSelectDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i) {
                ExecuteDelayTimeSelectDialog.this.lambda$initRv$1(viewBinding, recyclerView, i);
            }
        });
        if (this.minCenter) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewBinding.pickerViewSec.getLayoutParams();
            layoutParams.startToStart = viewBinding.parent.getId();
            viewBinding.pickerViewSec.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(DialogExecuteDelayTimeSelectorBinding dialogExecuteDelayTimeSelectorBinding, RecyclerView recyclerView, int i) {
        if (!this.supportRightAway && getMinuteManager().getPickedPosition() == 0 && getSecondManager().getPickedPosition() == 0) {
            dialogExecuteDelayTimeSelectorBinding.pickerViewSec.scrollToPosition(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(DialogExecuteDelayTimeSelectorBinding dialogExecuteDelayTimeSelectorBinding, RecyclerView recyclerView, int i) {
        if (!this.supportRightAway && getMinuteManager().getPickedPosition() == 0 && getSecondManager().getPickedPosition() == 0) {
            dialogExecuteDelayTimeSelectorBinding.pickerViewSec.scrollToPosition(1);
        }
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

    public ExecuteDelayTimeSelectDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public ExecuteDelayTimeSelectDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public ExecuteDelayTimeSelectDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public ExecuteDelayTimeSelectDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setMinCenter(boolean minCenter) {
        this.minCenter = minCenter;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    public ExecuteDelayTimeSelectDialog supportRightAway(boolean support) {
        this.supportRightAway = support;
        return this;
    }

    public ExecuteDelayTimeSelectDialog setSelectListener(SelectListener selectListener) {
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