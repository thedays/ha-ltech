package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogLeftTitleSelectListBinding;
import com.ltech.smarthome.view.dialog.SelectListLeftTitleDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectListLeftTitleDialog extends SmartDialog<DialogLeftTitleSelectListBinding> {
    private IAction<Integer> confirmAction;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<String> subList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_left_title_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectListLeftTitleDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogLeftTitleSelectListBinding, SelectListLeftTitleDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogLeftTitleSelectListBinding viewBinding, final SelectListLeftTitleDialog dialog) {
            final BaseQuickAdapter<Content, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Content, BaseViewHolder>(this, R.layout.item_select_subtext_ver, dialog.getContentList()) { // from class: com.ltech.smarthome.view.dialog.SelectListLeftTitleDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Content item) {
                    helper.setText(R.id.tv_name, item.getName()).setText(R.id.tv_sub_name, item.getSubName()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                }
            };
            final boolean z = this.val$needConfirm;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListLeftTitleDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectListLeftTitleDialog.AnonymousClass1.lambda$convertView$0(z, dialog, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            dialog.getContentList();
            viewBinding.tvSub.setText("(" + dialog.getContentList().size() + ")");
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, SelectListLeftTitleDialog selectListLeftTitleDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (z) {
                selectListLeftTitleDialog.setSelectPosition(i);
                baseQuickAdapter.notifyDataSetChanged();
            } else {
                if (selectListLeftTitleDialog.confirmAction != null) {
                    selectListLeftTitleDialog.confirmAction.act(Integer.valueOf(i));
                }
                selectListLeftTitleDialog.dismissDialog();
            }
        }
    }

    public static SelectListLeftTitleDialog asDefault(boolean needConfirm) {
        return (SelectListLeftTitleDialog) new SelectListLeftTitleDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
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

    public SelectListLeftTitleDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectListLeftTitleDialog setSelectList(List<String> list, List<String> listSub) {
        this.selectList.clear();
        this.selectList.addAll(list);
        this.subList.clear();
        this.subList.addAll(listSub);
        return this;
    }

    public SelectListLeftTitleDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectListLeftTitleDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_list_dialog";
    }
}