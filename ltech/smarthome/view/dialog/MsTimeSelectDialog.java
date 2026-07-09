package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding;
import com.ltech.smarthome.view.dialog.MsTimeSelectDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MsTimeSelectDialog extends SmartDialog<DialogMsTimeSelectorBinding> {
    private String hourUnit;
    PickerLayoutManager mHourManager;
    PickerLayoutManager mMillSecondManager;
    PickerLayoutManager mMinuteManager;
    PickerLayoutManager mSecondManager;
    private SelectListener mSelectListener;
    private String millSecUnit;
    private String minUnit;
    private String secUnit;
    private String title;
    private boolean withUnit;
    private List<String> hourList = new ArrayList();
    private List<String> minList = new ArrayList();
    private List<String> secList = new ArrayList();
    private List<String> millSecList = new ArrayList();
    private int selectHourPosition = -1;
    private int selectSecPosition = -1;
    private int selectMinPosition = -1;
    private int selectMillSecPosition = -1;

    public interface SelectListener {
        void confirm(int hourPosition, int minutePosition, int secPosition, int millSecPosition);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_ms_time_selector;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.MsTimeSelectDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogMsTimeSelectorBinding, MsTimeSelectDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogMsTimeSelectorBinding viewBinding, final MsTimeSelectDialog dialog) {
            dialog.initRv(viewBinding);
            if (!dialog.hourList.isEmpty()) {
                viewBinding.pickViewHour.setVisibility(0);
                MsTimeSelectDialog.updateTimeData(dialog.hourList, dialog.selectHourPosition, viewBinding.pickViewHour);
                viewBinding.setHourUnit(dialog.hourUnit);
            }
            if (!dialog.minList.isEmpty()) {
                viewBinding.pickViewMin.setVisibility(0);
                MsTimeSelectDialog.updateTimeData(dialog.minList, dialog.selectMinPosition, viewBinding.pickViewMin);
                viewBinding.setMinUnit(dialog.minUnit);
            }
            if (!dialog.secList.isEmpty()) {
                viewBinding.pickerViewSec.setVisibility(0);
                MsTimeSelectDialog.updateTimeData(dialog.secList, dialog.selectSecPosition, viewBinding.pickerViewSec);
                viewBinding.setSecUnit(dialog.secUnit);
            }
            if (!dialog.millSecList.isEmpty()) {
                viewBinding.pickerViewMs.setVisibility(0);
                MsTimeSelectDialog.updateTimeData(dialog.millSecList, dialog.selectMillSecPosition, viewBinding.pickerViewMs);
                viewBinding.setMsUnit(dialog.millSecUnit);
            }
            dialog.getHourManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.MsTimeSelectDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    MsTimeSelectDialog.AnonymousClass1.lambda$convertView$0(MsTimeSelectDialog.this, viewBinding, recyclerView, i);
                }
            });
            dialog.getMinuteManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.MsTimeSelectDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    MsTimeSelectDialog.AnonymousClass1.lambda$convertView$1(MsTimeSelectDialog.this, viewBinding, recyclerView, i);
                }
            });
            dialog.getSecondManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.MsTimeSelectDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    MsTimeSelectDialog.AnonymousClass1.lambda$convertView$2(MsTimeSelectDialog.this, viewBinding, recyclerView, i);
                }
            });
            dialog.getMillSecondManager().setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.MsTimeSelectDialog$1$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    MsTimeSelectDialog.AnonymousClass1.lambda$convertView$3(MsTimeSelectDialog.this, viewBinding, recyclerView, i);
                }
            });
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setWithUnit(Boolean.valueOf(dialog.withUnit));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.MsTimeSelectDialog$1$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    MsTimeSelectDialog.AnonymousClass1.lambda$convertView$4(MsTimeSelectDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(MsTimeSelectDialog msTimeSelectDialog, DialogMsTimeSelectorBinding dialogMsTimeSelectorBinding, RecyclerView recyclerView, int i) {
            msTimeSelectDialog.selectHourPosition = i;
            msTimeSelectDialog.selectMillSecPosition = 0;
            MsTimeSelectDialog.updateTimeData(msTimeSelectDialog.millSecList, msTimeSelectDialog.selectMillSecPosition, dialogMsTimeSelectorBinding.pickerViewMs);
            dialogMsTimeSelectorBinding.setMsUnit(msTimeSelectDialog.millSecUnit);
            msTimeSelectDialog.selectSecPosition = 0;
            MsTimeSelectDialog.updateTimeData(msTimeSelectDialog.secList, msTimeSelectDialog.selectSecPosition, dialogMsTimeSelectorBinding.pickerViewSec);
            dialogMsTimeSelectorBinding.setSecUnit(msTimeSelectDialog.secUnit);
        }

        static /* synthetic */ void lambda$convertView$1(MsTimeSelectDialog msTimeSelectDialog, DialogMsTimeSelectorBinding dialogMsTimeSelectorBinding, RecyclerView recyclerView, int i) {
            msTimeSelectDialog.selectMinPosition = i;
            msTimeSelectDialog.selectMillSecPosition = 0;
            MsTimeSelectDialog.updateTimeData(msTimeSelectDialog.millSecList, msTimeSelectDialog.selectMillSecPosition, dialogMsTimeSelectorBinding.pickerViewMs);
            dialogMsTimeSelectorBinding.setMsUnit(msTimeSelectDialog.millSecUnit);
        }

        static /* synthetic */ void lambda$convertView$2(MsTimeSelectDialog msTimeSelectDialog, DialogMsTimeSelectorBinding dialogMsTimeSelectorBinding, RecyclerView recyclerView, int i) {
            msTimeSelectDialog.selectSecPosition = i;
            msTimeSelectDialog.selectHourPosition = 0;
            MsTimeSelectDialog.updateTimeData(msTimeSelectDialog.hourList, msTimeSelectDialog.selectHourPosition, dialogMsTimeSelectorBinding.pickViewHour);
            dialogMsTimeSelectorBinding.setHourUnit(msTimeSelectDialog.hourUnit);
        }

        static /* synthetic */ void lambda$convertView$3(MsTimeSelectDialog msTimeSelectDialog, DialogMsTimeSelectorBinding dialogMsTimeSelectorBinding, RecyclerView recyclerView, int i) {
            msTimeSelectDialog.selectMillSecPosition = i;
            msTimeSelectDialog.selectHourPosition = 0;
            MsTimeSelectDialog.updateTimeData(msTimeSelectDialog.hourList, msTimeSelectDialog.selectHourPosition, dialogMsTimeSelectorBinding.pickViewHour);
            dialogMsTimeSelectorBinding.setHourUnit(msTimeSelectDialog.hourUnit);
            msTimeSelectDialog.selectMinPosition = 0;
            MsTimeSelectDialog.updateTimeData(msTimeSelectDialog.minList, msTimeSelectDialog.selectMinPosition, dialogMsTimeSelectorBinding.pickViewMin);
            dialogMsTimeSelectorBinding.setMinUnit(msTimeSelectDialog.minUnit);
        }

        static /* synthetic */ void lambda$convertView$4(MsTimeSelectDialog msTimeSelectDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                msTimeSelectDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (msTimeSelectDialog.mSelectListener != null) {
                    msTimeSelectDialog.mSelectListener.confirm(msTimeSelectDialog.getHourManager().getPickedPosition(), msTimeSelectDialog.getMinuteManager().getPickedPosition(), msTimeSelectDialog.getSecondManager().getPickedPosition(), msTimeSelectDialog.getMillSecondManager().getPickedPosition());
                }
                msTimeSelectDialog.dismissDialog();
            }
        }
    }

    public static MsTimeSelectDialog asDefault() {
        return (MsTimeSelectDialog) new MsTimeSelectDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
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
    public void initRv(DialogMsTimeSelectorBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter2 = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter3 = new PickerAdapter(getContext());
        PickerAdapter pickerAdapter4 = new PickerAdapter(getContext());
        pickerAdapter.setData(this.hourList);
        pickerAdapter2.setData(this.minList);
        pickerAdapter3.setData(this.secList);
        pickerAdapter4.setData(this.millSecList);
        this.mHourManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mMinuteManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mSecondManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        this.mMillSecondManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickViewHour.setLayoutManager(this.mHourManager);
        viewBinding.pickViewMin.setLayoutManager(this.mMinuteManager);
        viewBinding.pickerViewSec.setLayoutManager(this.mSecondManager);
        viewBinding.pickerViewMs.setLayoutManager(this.mMillSecondManager);
        viewBinding.pickViewHour.setAdapter(pickerAdapter);
        viewBinding.pickViewMin.setAdapter(pickerAdapter2);
        viewBinding.pickerViewSec.setAdapter(pickerAdapter3);
        viewBinding.pickerViewMs.setAdapter(pickerAdapter4);
    }

    public PickerLayoutManager getHourManager() {
        return this.mHourManager;
    }

    public void setHourManager(PickerLayoutManager mHourManager) {
        this.mHourManager = mHourManager;
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

    public PickerLayoutManager getMillSecondManager() {
        return this.mMillSecondManager;
    }

    public void setMillSecondManager(PickerLayoutManager mMillSecondManager) {
        this.mMillSecondManager = mMillSecondManager;
    }

    public MsTimeSelectDialog setHourList(List<String> list) {
        this.hourList.clear();
        this.hourList.addAll(list);
        return this;
    }

    public MsTimeSelectDialog setMinList(List<String> list) {
        this.minList.clear();
        this.minList.addAll(list);
        return this;
    }

    public MsTimeSelectDialog setSecList(List<String> list) {
        this.secList.clear();
        this.secList.addAll(list);
        return this;
    }

    public MsTimeSelectDialog setMsList(List<String> list) {
        this.millSecList.clear();
        this.millSecList.addAll(list);
        return this;
    }

    public MsTimeSelectDialog setSelectHourPosition(int selectHourPosition) {
        this.selectHourPosition = selectHourPosition;
        return this;
    }

    public MsTimeSelectDialog setSelectMinPosition(int selectMinPosition) {
        this.selectMinPosition = selectMinPosition;
        return this;
    }

    public MsTimeSelectDialog setSelectSecPosition(int selectSecPosition) {
        this.selectSecPosition = selectSecPosition;
        return this;
    }

    public MsTimeSelectDialog setSelectMsPosition(int selectMsPosition) {
        this.selectMillSecPosition = selectMsPosition;
        return this;
    }

    public MsTimeSelectDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MsTimeSelectDialog withUnit(boolean withUnit) {
        this.withUnit = withUnit;
        return this;
    }

    public MsTimeSelectDialog setMinUnit(String minUnit) {
        this.minUnit = minUnit;
        return this;
    }

    public MsTimeSelectDialog setSecUnit(String secUnit) {
        this.secUnit = secUnit;
        return this;
    }

    public MsTimeSelectDialog setHourUnit(String hourUnit) {
        this.hourUnit = hourUnit;
        return this;
    }

    public MsTimeSelectDialog setMsUnit(String millSecUnit) {
        this.millSecUnit = millSecUnit;
        return this;
    }

    public MsTimeSelectDialog setSelectListener(SelectListener selectListener) {
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