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
import com.ltech.smarthome.databinding.DialogSelectListAndPicBinding;
import com.ltech.smarthome.view.dialog.SelectListAndPicDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectListAndPicDialog extends SmartDialog<DialogSelectListAndPicBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<Integer> selectDrawableList = new ArrayList();
    private int selectPosition = -1;
    private int picRes = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list_and_pic;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectListAndPicDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListAndPicBinding, SelectListAndPicDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListAndPicBinding viewBinding, final SelectListAndPicDialog dialog) {
            final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_select_list, dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.SelectListAndPicDialog.1.1
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
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListAndPicDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectListAndPicDialog.AnonymousClass1.lambda$convertView$0(z, dialog, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            if (dialog.picRes > 0) {
                viewBinding.layoutPic.setVisibility(0);
                viewBinding.ivPic.setImageResource(dialog.picRes);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectListAndPicDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectListAndPicDialog.AnonymousClass1.lambda$convertView$1(SelectListAndPicDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, SelectListAndPicDialog selectListAndPicDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (z) {
                selectListAndPicDialog.setSelectPosition(i);
                baseQuickAdapter.notifyDataSetChanged();
            } else {
                if (selectListAndPicDialog.confirmAction != null) {
                    selectListAndPicDialog.confirmAction.act(Integer.valueOf(i));
                }
                selectListAndPicDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$1(SelectListAndPicDialog selectListAndPicDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectListAndPicDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectListAndPicDialog.confirmAction != null) {
                    selectListAndPicDialog.confirmAction.act(Integer.valueOf(selectListAndPicDialog.selectPosition));
                }
                selectListAndPicDialog.dismissDialog();
            }
        }
    }

    public static SelectListAndPicDialog asDefault(boolean needConfirm) {
        return (SelectListAndPicDialog) new SelectListAndPicDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextDrawable(AppCompatTextView textView, int position) {
        Drawable drawable = getResources().getDrawable(this.selectDrawableList.get(position).intValue());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public SelectListAndPicDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectListAndPicDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectListAndPicDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectListAndPicDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SelectListAndPicDialog setPicRes(int picRes) {
        this.picRes = picRes;
        return this;
    }

    public SelectListAndPicDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectListAndPicDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }
}