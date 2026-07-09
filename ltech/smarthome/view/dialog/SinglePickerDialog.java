package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSinglePickerBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SinglePickerDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class SinglePickerDialog extends SmartDialog<DialogSinglePickerBinding> {
    private SelectListener mSelectListener;
    PickerLayoutManager pickerManager;
    private String saveText;
    private List<String> selectList = new ArrayList();
    private int selectPosition = -1;
    private String title;

    public interface SelectListener {
        void confirm(int position);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_single_picker;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SinglePickerDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSinglePickerBinding, SinglePickerDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSinglePickerBinding viewBinding, final SinglePickerDialog dialog) {
            dialog.initRv(viewBinding);
            viewBinding.tvTitle.setText(dialog.title);
            if (dialog.saveText != null) {
                viewBinding.tvFinish.setText(dialog.saveText);
            }
            dialog.pickerManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.SinglePickerDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView, int i) {
                    SinglePickerDialog.this.selectPosition = i;
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SinglePickerDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SinglePickerDialog.AnonymousClass1.lambda$convertView$1(SinglePickerDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$1(SinglePickerDialog singlePickerDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                singlePickerDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_finish) {
                return;
            }
            if (singlePickerDialog.selectPosition < 0) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            if (singlePickerDialog.mSelectListener != null) {
                singlePickerDialog.mSelectListener.confirm(singlePickerDialog.selectPosition);
            }
            singlePickerDialog.dismissDialog();
        }
    }

    public static SinglePickerDialog asDefault() {
        return (SinglePickerDialog) new SinglePickerDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogSinglePickerBinding viewBinding) {
        PickerAdapter pickerAdapter = new PickerAdapter(getContext());
        pickerAdapter.setData(this.selectList);
        this.pickerManager = new PickerLayoutManager.Builder(getContext()).setAlpha(true).setMaxItem(5).build();
        viewBinding.pickerView.setLayoutManager(this.pickerManager);
        viewBinding.pickerView.setAdapter(pickerAdapter);
        if (this.selectPosition != -1) {
            viewBinding.pickerView.scrollToPosition(this.selectPosition);
        }
    }

    public SinglePickerDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SinglePickerDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SinglePickerDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SinglePickerDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public SinglePickerDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }
}