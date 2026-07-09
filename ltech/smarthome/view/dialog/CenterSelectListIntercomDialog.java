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
import com.ltech.smarthome.databinding.DialogCenterSelectListBinding;
import com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CenterSelectListIntercomDialog extends SmartDialog<DialogCenterSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private List<String> selectList = new ArrayList();
    private int selectPosition = -1;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_center_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCenterSelectListBinding, CenterSelectListIntercomDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCenterSelectListBinding viewBinding, final CenterSelectListIntercomDialog dialog) {
            final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_simple_select, dialog.selectList) { // from class: com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_name, item);
                }
            };
            final boolean z = this.val$needConfirm;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    CenterSelectListIntercomDialog.AnonymousClass1.lambda$convertView$0(z, dialog, baseQuickAdapter, baseQuickAdapter2, view, i);
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
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CenterSelectListIntercomDialog.AnonymousClass1.lambda$convertView$1(CenterSelectListIntercomDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(boolean z, CenterSelectListIntercomDialog centerSelectListIntercomDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (z) {
                centerSelectListIntercomDialog.setSelectPosition(i);
                baseQuickAdapter.notifyDataSetChanged();
            } else {
                if (centerSelectListIntercomDialog.confirmAction != null) {
                    centerSelectListIntercomDialog.confirmAction.act(Integer.valueOf(i));
                }
                centerSelectListIntercomDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$1(CenterSelectListIntercomDialog centerSelectListIntercomDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                centerSelectListIntercomDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (centerSelectListIntercomDialog.confirmAction != null) {
                    centerSelectListIntercomDialog.confirmAction.act(Integer.valueOf(centerSelectListIntercomDialog.selectPosition));
                }
                centerSelectListIntercomDialog.dismissDialog();
            }
        }
    }

    public static CenterSelectListIntercomDialog asDefault(boolean needConfirm) {
        return (CenterSelectListIntercomDialog) new CenterSelectListIntercomDialog().setViewConverter(new AnonymousClass1(needConfirm)).setWidth(280).setHeight(0).setGravity(17);
    }

    public CenterSelectListIntercomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CenterSelectListIntercomDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public CenterSelectListIntercomDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public CenterSelectListIntercomDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public CenterSelectListIntercomDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public CenterSelectListIntercomDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "center_select_list_dialog";
    }
}