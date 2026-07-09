package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTimeSelectorBinding;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.ltech.smarthome.view.layoutmanager.LooperPickerLayoutManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class TimeSelectDialog extends SmartDialog<DialogTimeSelectorBinding> {
    LooperPickerLayoutManager mMinuteManager;
    LooperPickerLayoutManager mSecondManager;
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
        return R.layout.dialog_time_selector;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TimeSelectDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTimeSelectorBinding, TimeSelectDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogTimeSelectorBinding viewBinding, final TimeSelectDialog dialog) {
            dialog.initRv(viewBinding);
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                TimeSelectDialog.updateTimeData(dialog.minList, dialog.selectMinPosition, dialog.getMinuteManager());
                viewBinding.setMinUnit(dialog.minUnit);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickerViewSec.setVisibility(0);
                TimeSelectDialog.updateTimeData(dialog.secList, dialog.selectSecPosition, dialog.getSecondManager());
                viewBinding.setSecUnit(dialog.secUnit);
            }
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TimeSelectDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TimeSelectDialog.AnonymousClass1.lambda$convertView$0(TimeSelectDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(TimeSelectDialog timeSelectDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                timeSelectDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (timeSelectDialog.mSelectListener != null) {
                    timeSelectDialog.mSelectListener.confirm(timeSelectDialog.getMinuteManager().getSelectedPosition(), timeSelectDialog.getSecondManager().getSelectedPosition());
                }
                timeSelectDialog.dismissDialog();
            }
        }
    }

    public static TimeSelectDialog asDefault() {
        return (TimeSelectDialog) new TimeSelectDialog().setViewConverter(new AnonymousClass1()).setHeight(ConvertUtils.dp2px(100.0f)).setOutCancel(true).setMargin(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateTimeData(List<String> dataList, int selectPosition, LooperPickerLayoutManager p) {
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
    public void initRv(final DialogTimeSelectorBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        LooperPickerLayoutManager.Builder builder = new LooperPickerLayoutManager.Builder();
        builder.setVisibleCount(5);
        builder.setIsLoop(true);
        builder.setAlpha(0.5f);
        builder.setScaleY(0.8f);
        builder.setScaleX(0.8f);
        this.mMinuteManager = builder.build();
        LooperPickerLayoutManager.Builder builder2 = new LooperPickerLayoutManager.Builder();
        builder2.setVisibleCount(5);
        builder2.setIsLoop(true);
        builder2.setAlpha(0.5f);
        builder2.setScaleY(0.8f);
        builder2.setScaleX(0.8f);
        this.mSecondManager = builder2.build();
        viewBinding.pickViewMin.setLayoutManager(this.mMinuteManager);
        viewBinding.pickerViewSec.setLayoutManager(this.mSecondManager);
        viewBinding.pickViewMin.setAdapter(pickerAdapter);
        viewBinding.pickerViewSec.setAdapter(pickerAdapter2);
        this.mMinuteManager.addOnItemSelectedListener(new Function1<Integer, Unit>() { // from class: com.ltech.smarthome.view.dialog.TimeSelectDialog.2
            @Override // kotlin.jvm.functions.Function1
            public Unit invoke(Integer integer) {
                if (TimeSelectDialog.this.supportRightAway || TimeSelectDialog.this.getMinuteManager().getSelectedPosition() != 0 || TimeSelectDialog.this.getSecondManager().getSelectedPosition() != 0) {
                    return null;
                }
                viewBinding.pickerViewSec.scrollToPosition(1);
                return null;
            }
        });
        this.mSecondManager.addOnItemSelectedListener(new Function1<Integer, Unit>() { // from class: com.ltech.smarthome.view.dialog.TimeSelectDialog.3
            @Override // kotlin.jvm.functions.Function1
            public Unit invoke(Integer integer) {
                if (TimeSelectDialog.this.supportRightAway || TimeSelectDialog.this.getMinuteManager().getSelectedPosition() != 0 || TimeSelectDialog.this.getSecondManager().getSelectedPosition() != 0) {
                    return null;
                }
                viewBinding.pickerViewSec.scrollToPosition(1);
                return null;
            }
        });
        pickerAdapter.setData(this.minList);
        pickerAdapter2.setData(this.secList);
        if (this.minCenter) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewBinding.tvMinTip.getLayoutParams();
            layoutParams.endToStart = R.id.picker_view_sec;
            viewBinding.tvMinTip.setLayoutParams(layoutParams);
        }
    }

    public LooperPickerLayoutManager getMinuteManager() {
        return this.mMinuteManager;
    }

    public void setMinuteManager(LooperPickerLayoutManager mMinuteManager) {
        this.mMinuteManager = mMinuteManager;
    }

    public LooperPickerLayoutManager getSecondManager() {
        return this.mSecondManager;
    }

    public void setSecondManager(LooperPickerLayoutManager mSecondManager) {
        this.mSecondManager = mSecondManager;
    }

    public TimeSelectDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public TimeSelectDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public TimeSelectDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public TimeSelectDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public TimeSelectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TimeSelectDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public TimeSelectDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public TimeSelectDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public TimeSelectDialog setMinCenter(boolean minCenter) {
        this.minCenter = minCenter;
        return this;
    }

    public TimeSelectDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    public TimeSelectDialog supportRightAway(boolean support) {
        this.supportRightAway = support;
        return this;
    }

    public TimeSelectDialog setSelectListener(SelectListener selectListener) {
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