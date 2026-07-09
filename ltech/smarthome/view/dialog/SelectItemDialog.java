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
import com.ltech.smarthome.databinding.DialogSelectItemBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectItemDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectItemDialog extends SmartDialog<DialogSelectItemBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private IActionWithReturn<Integer, Boolean> itemClickAction;
    private List<ListItem> selectList = new ArrayList();
    private int selectPosition = -1;
    private String title;

    public static final class ListItem {
        public String content;
        public int iconRes;
        public String name;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_item;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectItemDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectItemBinding, SelectItemDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectItemBinding viewBinding, final SelectItemDialog dialog) {
            final BaseQuickAdapter<ListItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ListItem, BaseViewHolder>(this, R.layout.item_select_gateway, dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.SelectItemDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, ListItem item) {
                    helper.setText(R.id.tv_name, item.name).setText(R.id.tv_content, item.content).setImageResource(R.id.iv_icon, item.iconRes).setBackgroundRes(R.id.iv_icon_bg, R.mipmap.icon_circle_bg).setBackgroundRes(R.id.iv_tick, R.mipmap.icon_circle_tick).setVisible(R.id.iv_tick, helper.getAdapterPosition() == dialog.selectPosition);
                    helper.getView(R.id.layout_item_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.getView(R.id.layout_item_bg).getLayoutParams().width = (int) ((viewBinding.rvContent.getMeasuredHeight() * 4) / 5.0f);
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectItemDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectItemDialog.AnonymousClass1.lambda$convertView$0(SelectItemDialog.this, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            dialog.selectPosition = 0;
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext(), 0, false));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectItemDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectItemDialog.AnonymousClass1.lambda$convertView$1(SelectItemDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectItemDialog selectItemDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (selectItemDialog.itemClickAction != null) {
                if (((Boolean) selectItemDialog.itemClickAction.act(Integer.valueOf(i))).booleanValue()) {
                    selectItemDialog.setSelectPosition(i);
                    baseQuickAdapter.notifyDataSetChanged();
                    return;
                }
                return;
            }
            selectItemDialog.setSelectPosition(i);
            baseQuickAdapter.notifyDataSetChanged();
        }

        static /* synthetic */ void lambda$convertView$1(SelectItemDialog selectItemDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectItemDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (selectItemDialog.selectPosition < 0) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            if (selectItemDialog.confirmAction != null) {
                selectItemDialog.confirmAction.act(Integer.valueOf(selectItemDialog.selectPosition));
            }
            selectItemDialog.dismissDialog();
        }
    }

    public static SelectItemDialog asDefault() {
        return (SelectItemDialog) new SelectItemDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public SelectItemDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectItemDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectItemDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectItemDialog setSelectList(List<ListItem> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SelectItemDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectItemDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectItemDialog setItemClickAction(IActionWithReturn<Integer, Boolean> itemClickAction) {
        this.itemClickAction = itemClickAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_item_dialog";
    }
}