package com.ltech.smarthome.view.dialog;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.BaseAdapter;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogWheelSelectDoubleListBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectDoubleListWheelDialog extends SmartDialog<DialogWheelSelectDoubleListBinding> {
    private String cancelString;
    private IAction<int[]> confirmAction;
    private String confirmString;
    private String specify;
    private String tip;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<String> select2List = new ArrayList();
    private List<Integer> selectDrawableList = new ArrayList();
    private int selectPosition = -1;
    private int select2Position = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_wheel_select_double_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogWheelSelectDoubleListBinding, SelectDoubleListWheelDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogWheelSelectDoubleListBinding viewBinding, final SelectDoubleListWheelDialog dialog) {
            SelectDoubleListWheelDialog.initRv1(dialog, viewBinding);
            SelectDoubleListWheelDialog.initRv2(dialog, viewBinding);
            viewBinding.tvSpecify.setText(dialog.specify);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.tvTip.setText(TextUtils.isEmpty(dialog.tip) ? "" : dialog.tip);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDoubleListWheelDialog.AnonymousClass1.lambda$convertView$0(SelectDoubleListWheelDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectDoubleListWheelDialog selectDoubleListWheelDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectDoubleListWheelDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (selectDoubleListWheelDialog.confirmAction != null) {
                if (selectDoubleListWheelDialog.selectPosition >= selectDoubleListWheelDialog.select2Position) {
                    SmartToast.showCenterShort(StringUtils.getString(R.string.app_str_data_error));
                    return;
                } else {
                    selectDoubleListWheelDialog.confirmAction.act(new int[]{selectDoubleListWheelDialog.selectPosition, selectDoubleListWheelDialog.select2Position});
                }
            }
            selectDoubleListWheelDialog.dismissDialog();
        }
    }

    public static SelectDoubleListWheelDialog asDefault() {
        return (SelectDoubleListWheelDialog) new SelectDoubleListWheelDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initRv1(final SelectDoubleListWheelDialog dialog, DialogWheelSelectDoubleListBinding viewBinding) {
        RecyclerView recyclerView = viewBinding.rvContent;
        final PickerLayoutManager build = new PickerLayoutManager.Builder(dialog.getContext()).setAlpha(true).setMaxItem(5).build();
        recyclerView.setLayoutManager(build);
        build.setRealWidthHeight(true);
        final PickerAdapter pickerAdapter = new PickerAdapter(dialog.getContext(), R.layout.item_wrap_pick);
        pickerAdapter.setPosition(dialog.selectPosition);
        pickerAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.adapter.BaseAdapter.OnItemClickListener
            public final void onItemClick(RecyclerView recyclerView2, View view, int i) {
                SelectDoubleListWheelDialog.lambda$initRv1$0(SelectDoubleListWheelDialog.this, build, recyclerView2, view, i);
            }
        });
        build.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView2, int i) {
                SelectDoubleListWheelDialog.lambda$initRv1$1(SelectDoubleListWheelDialog.this, build, pickerAdapter, recyclerView2, i);
            }
        });
        pickerAdapter.setData(dialog.selectList);
        if (!dialog.selectList.isEmpty()) {
            viewBinding.rvContent.setVisibility(0);
            updateTimeData(dialog.selectList, dialog.selectPosition, viewBinding.rvContent);
        }
        ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        viewBinding.rvContent.setAdapter(pickerAdapter);
    }

    static /* synthetic */ void lambda$initRv1$0(SelectDoubleListWheelDialog selectDoubleListWheelDialog, PickerLayoutManager pickerLayoutManager, RecyclerView recyclerView, View view, int i) {
        selectDoubleListWheelDialog.setSelectPosition(i);
        pickerLayoutManager.scrollToPosition(i);
    }

    static /* synthetic */ void lambda$initRv1$1(SelectDoubleListWheelDialog selectDoubleListWheelDialog, PickerLayoutManager pickerLayoutManager, PickerAdapter pickerAdapter, RecyclerView recyclerView, int i) {
        selectDoubleListWheelDialog.setSelectPosition(pickerLayoutManager.getPickedPosition());
        pickerAdapter.setPosition(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initRv2(final SelectDoubleListWheelDialog dialog, DialogWheelSelectDoubleListBinding viewBinding) {
        RecyclerView recyclerView = viewBinding.rvContent2;
        final PickerLayoutManager build = new PickerLayoutManager.Builder(dialog.getContext()).setAlpha(true).setMaxItem(5).build();
        recyclerView.setLayoutManager(build);
        build.setRealWidthHeight(true);
        final PickerAdapter pickerAdapter = new PickerAdapter(dialog.getContext(), R.layout.item_wrap_pick);
        pickerAdapter.setPosition(dialog.select2Position);
        pickerAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.adapter.BaseAdapter.OnItemClickListener
            public final void onItemClick(RecyclerView recyclerView2, View view, int i) {
                SelectDoubleListWheelDialog.lambda$initRv2$2(SelectDoubleListWheelDialog.this, build, recyclerView2, view, i);
            }
        });
        build.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView2, int i) {
                SelectDoubleListWheelDialog.lambda$initRv2$3(SelectDoubleListWheelDialog.this, build, pickerAdapter, recyclerView2, i);
            }
        });
        pickerAdapter.setData(dialog.select2List);
        if (!dialog.select2List.isEmpty()) {
            viewBinding.rvContent2.setVisibility(0);
            updateTimeData(dialog.select2List, dialog.select2Position, viewBinding.rvContent2);
        }
        ((DefaultItemAnimator) viewBinding.rvContent2.getItemAnimator()).setSupportsChangeAnimations(false);
        viewBinding.rvContent2.setAdapter(pickerAdapter);
    }

    static /* synthetic */ void lambda$initRv2$2(SelectDoubleListWheelDialog selectDoubleListWheelDialog, PickerLayoutManager pickerLayoutManager, RecyclerView recyclerView, View view, int i) {
        selectDoubleListWheelDialog.setSelect2Position(i);
        pickerLayoutManager.scrollToPosition(i);
    }

    static /* synthetic */ void lambda$initRv2$3(SelectDoubleListWheelDialog selectDoubleListWheelDialog, PickerLayoutManager pickerLayoutManager, PickerAdapter pickerAdapter, RecyclerView recyclerView, int i) {
        selectDoubleListWheelDialog.setSelect2Position(pickerLayoutManager.getPickedPosition());
        pickerAdapter.setPosition(i);
    }

    private static void updateTimeData(List<String> dataList, int selectPosition, RecyclerView p) {
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

    private void setTextDrawable(AppCompatTextView textView, int position) {
        Drawable drawable = getResources().getDrawable(this.selectDrawableList.get(position).intValue());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public SelectDoubleListWheelDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectDoubleListWheelDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectDoubleListWheelDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectDoubleListWheelDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        this.select2List.clear();
        this.select2List.addAll(list);
        return this;
    }

    public SelectDoubleListWheelDialog setSelectDrawableList(List<Integer> list) {
        this.selectDrawableList.clear();
        this.selectDrawableList.addAll(list);
        return this;
    }

    public SelectDoubleListWheelDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectDoubleListWheelDialog setSelect2Position(int selectPosition) {
        this.select2Position = selectPosition;
        return this;
    }

    public SelectDoubleListWheelDialog setConfirmAction(IAction<int[]> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectDoubleListWheelDialog setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public SelectDoubleListWheelDialog setSpecify(String specify) {
        this.specify = specify;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_list_dialog";
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