package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectListBinding;
import com.ltech.smarthome.view.dialog.MultiSelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MultiSelectListDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private String confirmString;
    private IMultiSelectListener multiSelectListener;
    private List<SelectItem> selectList = new ArrayList();
    private String title;

    public interface IMultiSelectListener {
        void onSelect(List<Integer> selectPositions);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.MultiSelectListDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, MultiSelectListDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final MultiSelectListDialog dialog) {
            final BaseQuickAdapter<SelectItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SelectItem, BaseViewHolder>(this, R.layout.item_select_list, dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.MultiSelectListDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, SelectItem item) {
                    helper.setText(R.id.tv_name, item.getItemName()).setBackgroundRes(R.id.iv_select, item.isSelected() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.MultiSelectListDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    MultiSelectListDialog.AnonymousClass1.lambda$convertView$0(MultiSelectListDialog.this, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.MultiSelectListDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    MultiSelectListDialog.AnonymousClass1.lambda$convertView$1(MultiSelectListDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(MultiSelectListDialog multiSelectListDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            ((SelectItem) multiSelectListDialog.selectList.get(i)).selected = !((SelectItem) multiSelectListDialog.selectList.get(i)).selected;
            baseQuickAdapter.notifyItemChanged(i);
        }

        static /* synthetic */ void lambda$convertView$1(MultiSelectListDialog multiSelectListDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                multiSelectListDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (multiSelectListDialog.multiSelectListener != null) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < multiSelectListDialog.selectList.size(); i++) {
                    if (((SelectItem) multiSelectListDialog.selectList.get(i)).isSelected()) {
                        arrayList.add(Integer.valueOf(i));
                    }
                }
                multiSelectListDialog.multiSelectListener.onSelect(arrayList);
            }
            multiSelectListDialog.dismissDialog();
        }
    }

    public static MultiSelectListDialog asDefault() {
        return (MultiSelectListDialog) new MultiSelectListDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public MultiSelectListDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MultiSelectListDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public MultiSelectListDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public MultiSelectListDialog setSelectList(List<SelectItem> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public MultiSelectListDialog setMultiSelectListener(IMultiSelectListener multiSelectListener) {
        this.multiSelectListener = multiSelectListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_list_dialog";
    }

    public static class SelectItem {
        private String itemName;
        private boolean selected;

        public SelectItem(String itemName, boolean selected) {
            this.itemName = itemName;
            this.selected = selected;
        }

        public String getItemName() {
            return this.itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}