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
import com.ltech.smarthome.databinding.DialogCenterSelectListBinding;
import com.ltech.smarthome.view.dialog.CenterSelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CenterSelectListDialog extends SmartDialog<DialogCenterSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private String title;
    private List<String> selectList = new ArrayList();
    private List<Integer> selectDrawableList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_center_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CenterSelectListDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCenterSelectListBinding, CenterSelectListDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCenterSelectListBinding viewBinding, final CenterSelectListDialog dialog) {
            final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_select_list, dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.CenterSelectListDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                    if (dialog.selectDrawableList.size() > 0) {
                        dialog.setTextDrawable((AppCompatTextView) helper.getView(R.id.tv_name), helper.getBindingAdapterPosition());
                    }
                }
            };
            final boolean z = this.val$needConfirm;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.CenterSelectListDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    CenterSelectListDialog.AnonymousClass1.lambda$convertView$0(z, dialog, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            if (!this.val$needConfirm) {
                viewBinding.tvConfirm.setVisibility(8);
            }
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CenterSelectListDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CenterSelectListDialog.AnonymousClass1.lambda$convertView$1(CenterSelectListDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, CenterSelectListDialog centerSelectListDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (z) {
                centerSelectListDialog.setSelectPosition(i);
                baseQuickAdapter.notifyDataSetChanged();
            } else {
                if (centerSelectListDialog.confirmAction != null) {
                    centerSelectListDialog.confirmAction.act(Integer.valueOf(i));
                }
                centerSelectListDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$1(CenterSelectListDialog centerSelectListDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                centerSelectListDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (centerSelectListDialog.confirmAction != null) {
                    centerSelectListDialog.confirmAction.act(Integer.valueOf(centerSelectListDialog.selectPosition));
                }
                centerSelectListDialog.dismissDialog();
            }
        }
    }

    public static CenterSelectListDialog asDefault(boolean needConfirm) {
        return (CenterSelectListDialog) new CenterSelectListDialog().setViewConverter(new AnonymousClass1(needConfirm)).setWidth(280).setHeight(0).setGravity(17);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextDrawable(AppCompatTextView textView, int position) {
        Drawable drawable = getResources().getDrawable(this.selectDrawableList.get(position).intValue());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public CenterSelectListDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CenterSelectListDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public CenterSelectListDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public CenterSelectListDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public CenterSelectListDialog setSelectDrawableList(List<Integer> list) {
        this.selectDrawableList.clear();
        this.selectDrawableList.addAll(list);
        return this;
    }

    public CenterSelectListDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public CenterSelectListDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "center_select_list_dialog";
    }
}