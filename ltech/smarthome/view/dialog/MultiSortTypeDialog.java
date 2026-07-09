package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectListBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.MultiSortTypeDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MultiSortTypeDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private String confirmString;
    private IMultiSelectListener multiSelectListener;
    private List<SelectItem> selectList = new ArrayList();
    private List<Integer> selectPositions = new ArrayList();
    private String title;

    public interface IMultiSelectListener {
        void onSelect(List<Integer> selectPositions);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.MultiSortTypeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, MultiSortTypeDialog> {
        final /* synthetic */ boolean val$includeGroup;

        AnonymousClass1(final boolean val$includeGroup) {
            this.val$includeGroup = val$includeGroup;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final MultiSortTypeDialog dialog) {
            final BaseQuickAdapter<SelectItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SelectItem, BaseViewHolder>(this, R.layout.item_select_list, dialog.getList(this.val$includeGroup)) { // from class: com.ltech.smarthome.view.dialog.MultiSortTypeDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, SelectItem item) {
                    helper.setText(R.id.tv_name, item.getItemName()).setBackgroundRes(R.id.iv_select, dialog.selectPositions.contains(Integer.valueOf(item.itemValue)) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).setCompoundDrawablesRelative(dialog.getResources().getDrawable(item.getImgRes()), null, null, null);
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.MultiSortTypeDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    MultiSortTypeDialog.AnonymousClass1.lambda$convertView$0(MultiSortTypeDialog.this, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.MultiSortTypeDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    MultiSortTypeDialog.AnonymousClass1.lambda$convertView$1(MultiSortTypeDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(MultiSortTypeDialog multiSortTypeDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            SelectItem selectItem = (SelectItem) multiSortTypeDialog.selectList.get(i);
            if (multiSortTypeDialog.selectPositions.contains(Integer.valueOf(selectItem.getItemValue()))) {
                multiSortTypeDialog.selectPositions.remove(Integer.valueOf(selectItem.getItemValue()));
            } else {
                multiSortTypeDialog.selectPositions.add(Integer.valueOf(selectItem.getItemValue()));
                int itemValue = selectItem.getItemValue();
                if (itemValue == 1) {
                    multiSortTypeDialog.selectPositions.remove((Object) 2);
                } else if (itemValue == 2) {
                    multiSortTypeDialog.selectPositions.remove((Object) 1);
                } else if (itemValue == 3) {
                    multiSortTypeDialog.selectPositions.remove((Object) 4);
                } else if (itemValue == 4) {
                    multiSortTypeDialog.selectPositions.remove((Object) 3);
                }
            }
            baseQuickAdapter.notifyDataSetChanged();
        }

        static /* synthetic */ void lambda$convertView$1(MultiSortTypeDialog multiSortTypeDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                multiSortTypeDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (multiSortTypeDialog.multiSelectListener != null) {
                if (multiSortTypeDialog.selectPositions.isEmpty()) {
                    SmartToast.showShort(R.string.please_choose);
                    return;
                }
                multiSortTypeDialog.multiSelectListener.onSelect(multiSortTypeDialog.selectPositions);
            }
            multiSortTypeDialog.dismissDialog();
        }
    }

    public static MultiSortTypeDialog asDefault(boolean includeGroup) {
        return (MultiSortTypeDialog) new MultiSortTypeDialog().setViewConverter(new AnonymousClass1(includeGroup)).setMargin(16).setY(16).setGravity(80);
    }

    public MultiSortTypeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MultiSortTypeDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public MultiSortTypeDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public MultiSortTypeDialog setSelectPositions(List<Integer> selectPositions) {
        this.selectPositions = selectPositions;
        return this;
    }

    public MultiSortTypeDialog setMultiSelectListener(IMultiSelectListener multiSelectListener) {
        this.multiSelectListener = multiSelectListener;
        return this;
    }

    public List<SelectItem> getList(boolean includeGroup) {
        if (includeGroup) {
            this.selectList.add(new SelectItem(R.mipmap.ic_groups, getString(R.string.app_sort_group_first), 0));
        }
        this.selectList.add(new SelectItem(R.mipmap.ic_time_down, getString(R.string.app_sort_by_time), 1));
        this.selectList.add(new SelectItem(R.mipmap.ic_time_up, getString(R.string.app_sort_by_time_reverse), 2));
        this.selectList.add(new SelectItem(R.mipmap.ic_name_down, getString(R.string.app_sort_by_name), 3));
        this.selectList.add(new SelectItem(R.mipmap.ic_name_up, getString(R.string.app_sort_by_name_reverse), 4));
        return this.selectList;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "multi_sort_type_dialog";
    }

    public static class SelectItem {
        private int imgRes;
        private String itemName;
        private int itemValue;

        public SelectItem(int imgRes, String itemName, int itemValue) {
            this.imgRes = imgRes;
            this.itemName = itemName;
            this.itemValue = itemValue;
        }

        public int getImgRes() {
            return this.imgRes;
        }

        public String getItemName() {
            return this.itemName;
        }

        public int getItemValue() {
            return this.itemValue;
        }
    }
}