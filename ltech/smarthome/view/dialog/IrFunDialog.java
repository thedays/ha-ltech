package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogIrFunBinding;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.view.dialog.IrFunDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class IrFunDialog extends SmartDialog<DialogIrFunBinding> {
    private List<IrKeyItem> itemList = new ArrayList();
    private OnDialogCallback mDialogCallback;

    public interface OnDialogCallback {
        void onItemClick(View view, IrKeyItem item);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_ir_fun;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.IrFunDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogIrFunBinding, IrFunDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogIrFunBinding viewBinding, final IrFunDialog dialog) {
            final BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_fun, dialog.itemList) { // from class: com.ltech.smarthome.view.dialog.IrFunDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, IrKeyItem item) {
                    helper.setText(R.id.tv_name, item.getName());
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.IrFunDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    IrFunDialog.AnonymousClass1.lambda$convertView$0(IrFunDialog.this, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), 3));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.IrFunDialog.1.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(IrFunDialog irFunDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (irFunDialog.mDialogCallback != null) {
                irFunDialog.mDialogCallback.onItemClick(view, (IrKeyItem) baseQuickAdapter.getData().get(i));
            }
        }
    }

    public static IrFunDialog asDefault() {
        return (IrFunDialog) new IrFunDialog().setViewConverter(new AnonymousClass1()).setGravity(80);
    }

    public IrFunDialog setList(List<IrKeyItem> list) {
        this.itemList.clear();
        this.itemList.addAll(list);
        return this;
    }

    public IrFunDialog setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "ir_fun_dialog";
    }
}