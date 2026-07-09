package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectListBinding;
import com.ltech.smarthome.view.dialog.SelectListSubLineDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectListSubLineDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<String> subList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectListSubLineDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, SelectListSubLineDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final SelectListSubLineDialog dialog) {
            final BaseQuickAdapter<Content, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Content, BaseViewHolder>(this, R.layout.item_select_subtext_ver, dialog.getContentList()) { // from class: com.ltech.smarthome.view.dialog.SelectListSubLineDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Content item) {
                    helper.setText(R.id.tv_name, item.getName()).setGone(R.id.tv_sub_name, !item.getSubName().isEmpty()).setText(R.id.tv_sub_name, item.getSubName()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                }
            };
            final boolean z = this.val$needConfirm;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListSubLineDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectListSubLineDialog.AnonymousClass1.lambda$convertView$0(z, dialog, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            if (dialog.title == null && dialog.cancelString == null && dialog.confirmString == null) {
                viewBinding.tvTitle.setVisibility(8);
            }
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectListSubLineDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectListSubLineDialog.AnonymousClass1.lambda$convertView$1(SelectListSubLineDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, SelectListSubLineDialog selectListSubLineDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (z) {
                selectListSubLineDialog.setSelectPosition(i);
                baseQuickAdapter.notifyDataSetChanged();
            } else {
                if (selectListSubLineDialog.confirmAction != null) {
                    selectListSubLineDialog.confirmAction.act(Integer.valueOf(i));
                }
                selectListSubLineDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$1(SelectListSubLineDialog selectListSubLineDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectListSubLineDialog.dismissDialog();
            } else if (id == R.id.tv_confirm && selectListSubLineDialog.selectPosition != -1) {
                if (selectListSubLineDialog.confirmAction != null) {
                    selectListSubLineDialog.confirmAction.act(Integer.valueOf(selectListSubLineDialog.selectPosition));
                }
                selectListSubLineDialog.dismissDialog();
            }
        }
    }

    public static SelectListSubLineDialog asDefault(boolean needConfirm) {
        return (SelectListSubLineDialog) new SelectListSubLineDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Content> getContentList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectList.size(); i++) {
            arrayList.add(new Content(this.selectList.get(i), this.subList.get(i)));
        }
        return arrayList;
    }

    public static final class Content {
        private String name;
        private String subName;

        Content(String name, String subName) {
            this.name = name;
            this.subName = subName;
        }

        public String getName() {
            return this.name;
        }

        public String getSubName() {
            return this.subName;
        }
    }

    public SelectListSubLineDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectListSubLineDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectListSubLineDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectListSubLineDialog setSelectList(List<String> list, List<String> listSub) {
        this.selectList.clear();
        this.selectList.addAll(list);
        this.subList.clear();
        this.subList.addAll(listSub);
        return this;
    }

    public SelectListSubLineDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectListSubLineDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_list_dialog";
    }
}