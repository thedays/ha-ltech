package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.IActionWithReturn;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectListBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SingleSelectItemDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SingleSelectItemDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private IActionWithReturn<Integer, Boolean> itemClickAction;
    private int itemLayout;
    private List<ListItem> selectList = new ArrayList();
    private int selectPosition = -1;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SingleSelectItemDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, SingleSelectItemDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final SingleSelectItemDialog dialog) {
            final BaseQuickAdapter<ListItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ListItem, BaseViewHolder>(this, dialog.getItemLayout(), dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.SingleSelectItemDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, ListItem item) {
                    helper.setText(R.id.tv_sub, item.content != null ? item.content : "");
                    helper.setText(R.id.tv_name, item.name).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SingleSelectItemDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SingleSelectItemDialog.AnonymousClass1.lambda$convertView$0(SingleSelectItemDialog.this, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext(), 1, false));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SingleSelectItemDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SingleSelectItemDialog.AnonymousClass1.lambda$convertView$1(SingleSelectItemDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SingleSelectItemDialog singleSelectItemDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (singleSelectItemDialog.itemClickAction != null) {
                if (((Boolean) singleSelectItemDialog.itemClickAction.act(Integer.valueOf(i))).booleanValue()) {
                    singleSelectItemDialog.setSelectPosition(i);
                    baseQuickAdapter.notifyDataSetChanged();
                    return;
                }
                return;
            }
            singleSelectItemDialog.setSelectPosition(i);
            baseQuickAdapter.notifyDataSetChanged();
        }

        static /* synthetic */ void lambda$convertView$1(SingleSelectItemDialog singleSelectItemDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                singleSelectItemDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (singleSelectItemDialog.selectPosition < 0) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            if (singleSelectItemDialog.confirmAction != null) {
                singleSelectItemDialog.confirmAction.act(Integer.valueOf(singleSelectItemDialog.selectPosition));
            }
            singleSelectItemDialog.dismissDialog();
        }
    }

    public static SingleSelectItemDialog asDefault() {
        return (SingleSelectItemDialog) new SingleSelectItemDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public SingleSelectItemDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SingleSelectItemDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SingleSelectItemDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SingleSelectItemDialog setSelectList(List<ListItem> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SingleSelectItemDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SingleSelectItemDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SingleSelectItemDialog setItemClickAction(IActionWithReturn<Integer, Boolean> itemClickAction) {
        this.itemClickAction = itemClickAction;
        return this;
    }

    public SingleSelectItemDialog setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
        return this;
    }

    public int getItemLayout() {
        int i = this.itemLayout;
        return i != 0 ? i : R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "single_select_item_dialog";
    }

    public static final class ListItem {
        public String content;
        public int iconRes;
        public String name;

        public ListItem() {
        }

        public ListItem(int iconRes, String name, String content) {
            this.iconRes = iconRes;
            this.name = name;
            this.content = content;
        }

        public ListItem(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public ListItem(String name) {
            this.name = name;
        }
    }
}