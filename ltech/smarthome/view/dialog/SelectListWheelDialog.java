package com.ltech.smarthome.view.dialog;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.BaseAdapter;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogWheelSelectListBinding;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectListWheelDialog extends SmartDialog<DialogWheelSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String specify;
    private String tip;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<Integer> selectDrawableList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_wheel_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectListWheelDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogWheelSelectListBinding, SelectListWheelDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogWheelSelectListBinding viewBinding, final SelectListWheelDialog dialog) {
            RecyclerView recyclerView = viewBinding.rvContent;
            final PickerLayoutManager build = new PickerLayoutManager.Builder(dialog.getContext()).setAlpha(true).setMaxItem(5).build();
            recyclerView.setLayoutManager(build);
            build.setRealWidthHeight(true);
            PickerAdapter pickerAdapter = new PickerAdapter(dialog.getContext(), R.layout.item_wrap_pick);
            viewBinding.tvSpecify.setText(dialog.specify);
            final boolean z = this.val$needConfirm;
            pickerAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListWheelDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.adapter.BaseAdapter.OnItemClickListener
                public final void onItemClick(RecyclerView recyclerView2, View view, int i) {
                    SelectListWheelDialog.AnonymousClass1.lambda$convertView$0(z, dialog, build, recyclerView2, view, i);
                }
            });
            build.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.view.dialog.SelectListWheelDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
                public final void onPicked(RecyclerView recyclerView2, int i) {
                    SelectListWheelDialog.this.setSelectPosition(build.getPickedPosition());
                }
            });
            pickerAdapter.setData(dialog.selectList);
            if (!dialog.selectList.isEmpty()) {
                viewBinding.rvContent.setVisibility(0);
                SelectListWheelDialog.updateTimeData(dialog.selectList, dialog.selectPosition, viewBinding.rvContent);
            }
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.rvContent.setAdapter(pickerAdapter);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.tvTip.setText(TextUtils.isEmpty(dialog.tip) ? "" : dialog.tip);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectListWheelDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectListWheelDialog.AnonymousClass1.lambda$convertView$2(SelectListWheelDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, SelectListWheelDialog selectListWheelDialog, PickerLayoutManager pickerLayoutManager, RecyclerView recyclerView, View view, int i) {
            if (z) {
                selectListWheelDialog.setSelectPosition(i);
                pickerLayoutManager.scrollToPosition(i);
            } else {
                if (selectListWheelDialog.confirmAction != null) {
                    selectListWheelDialog.confirmAction.act(Integer.valueOf(i));
                }
                selectListWheelDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$2(SelectListWheelDialog selectListWheelDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectListWheelDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectListWheelDialog.confirmAction != null) {
                    selectListWheelDialog.confirmAction.act(Integer.valueOf(selectListWheelDialog.selectPosition));
                }
                selectListWheelDialog.dismissDialog();
            }
        }
    }

    public static SelectListWheelDialog asDefault(boolean needConfirm) {
        return (SelectListWheelDialog) new SelectListWheelDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
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

    private void setTextDrawable(AppCompatTextView textView, int position) {
        Drawable drawable = getResources().getDrawable(this.selectDrawableList.get(position).intValue());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public SelectListWheelDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectListWheelDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectListWheelDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectListWheelDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SelectListWheelDialog setSelectDrawableList(List<Integer> list) {
        this.selectDrawableList.clear();
        this.selectDrawableList.addAll(list);
        return this;
    }

    public SelectListWheelDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectListWheelDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectListWheelDialog setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public SelectListWheelDialog setSpecify(String specify) {
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