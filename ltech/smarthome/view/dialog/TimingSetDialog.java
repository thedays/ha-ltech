package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.widget.RadioGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTimingSetBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.TimingSetDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class TimingSetDialog extends SmartDialog<DialogTimingSetBinding> {
    private SelectListener mSelectListener;
    PickerLayoutManager pickerManager;
    private String saveText;
    private String title;
    private List<String> selectList = new ArrayList();
    private int selectPosition = -1;
    private boolean selectClose = true;

    public interface SelectListener {
        void confirm(boolean close, int position);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_timing_set;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TimingSetDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTimingSetBinding, TimingSetDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogTimingSetBinding viewBinding, final TimingSetDialog dialog) {
            dialog.initRv(viewBinding);
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            dialog.pickerManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.TimingSetDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    TimingSetDialog.this.selectPosition = i;
                }
            });
            viewBinding.layoutTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.TimingSetDialog.1.1
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    dialog.selectClose = checkedId == R.id.radio_close;
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TimingSetDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TimingSetDialog.AnonymousClass1.lambda$convertView$1(TimingSetDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$1(TimingSetDialog timingSetDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                timingSetDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_finish) {
                return;
            }
            if (timingSetDialog.selectPosition < 0) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            if (timingSetDialog.mSelectListener != null) {
                timingSetDialog.mSelectListener.confirm(timingSetDialog.selectClose, timingSetDialog.selectPosition + 1);
            }
            timingSetDialog.dismissDialog();
        }
    }

    public static TimingSetDialog asDefault() {
        return (TimingSetDialog) new TimingSetDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogTimingSetBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        pickerAdapter.setData(this.selectList);
        this.pickerManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickerView.setLayoutManager(this.pickerManager);
        viewBinding.pickerView.setAdapter(pickerAdapter);
        if (this.selectPosition != -1) {
            viewBinding.pickerView.scrollToPosition(this.selectPosition);
        }
    }

    public TimingSetDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public TimingSetDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public TimingSetDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TimingSetDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public TimingSetDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }
}