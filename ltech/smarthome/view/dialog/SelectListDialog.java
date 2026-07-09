package com.ltech.smarthome.view.dialog;

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectListDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<Integer> selectDrawableList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectListDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, SelectListDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final SelectListDialog dialog) {
            final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_select_list, dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.SelectListDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                    if (dialog.selectDrawableList.size() > 0) {
                        dialog.setTextDrawable((AppCompatTextView) helper.getView(R.id.tv_name), helper.getBindingAdapterPosition());
                    }
                }
            };
            final boolean z = this.val$needConfirm;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectListDialog.AnonymousClass1.lambda$convertView$0(z, dialog, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectListDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectListDialog.AnonymousClass1.lambda$convertView$1(SelectListDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, SelectListDialog selectListDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (z) {
                selectListDialog.setSelectPosition(i);
                baseQuickAdapter.notifyDataSetChanged();
            } else {
                if (selectListDialog.confirmAction != null) {
                    selectListDialog.confirmAction.act(Integer.valueOf(i));
                }
                selectListDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$1(SelectListDialog selectListDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectListDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectListDialog.confirmAction != null) {
                    selectListDialog.confirmAction.act(Integer.valueOf(selectListDialog.selectPosition));
                }
                selectListDialog.dismissDialog();
            }
        }
    }

    public static SelectListDialog asDefault(boolean needConfirm) {
        return (SelectListDialog) new SelectListDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextDrawable(AppCompatTextView textView, int position) {
        Drawable drawable = getResources().getDrawable(this.selectDrawableList.get(position).intValue());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public SelectListDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectListDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectListDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectListDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SelectListDialog setSelectDrawableList(List<Integer> list) {
        this.selectDrawableList.clear();
        this.selectDrawableList.addAll(list);
        return this;
    }

    public SelectListDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectListDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_list_dialog";
    }
}