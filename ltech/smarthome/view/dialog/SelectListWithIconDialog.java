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
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.SelectListWithIconDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectListWithIconDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private boolean[] enableArray;
    private int iconRes;
    private List<String> selectList = new ArrayList();
    private int selectPosition = -1;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectListWithIconDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, SelectListWithIconDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* renamed from: com.ltech.smarthome.view.dialog.SelectListWithIconDialog$1$1, reason: invalid class name and collision with other inner class name */
        class C02141 extends BaseQuickAdapter<String, BaseViewHolder> {
            final /* synthetic */ SelectListWithIconDialog val$dialog;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02141(final AnonymousClass1 this$0, int layoutResId, List data, final SelectListWithIconDialog val$dialog) {
                super(layoutResId, data);
                this.val$dialog = val$dialog;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(final BaseViewHolder helper, String item) {
                int color;
                helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.val$dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                if (this.val$dialog.iconRes != 0) {
                    helper.setImageResource(R.id.iv_icon, this.val$dialog.iconRes);
                    View view = helper.getView(R.id.iv_icon);
                    final SelectListWithIconDialog selectListWithIconDialog = this.val$dialog;
                    view.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListWithIconDialog$1$1$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            Utils.copyText(r0.requireContext(), (String) SelectListWithIconDialog.this.selectList.get(helper.getAdapterPosition()));
                        }
                    });
                } else {
                    helper.setGone(R.id.iv_icon, false);
                }
                if (this.val$dialog.enableArray != null) {
                    if (this.val$dialog.enableArray[helper.getAdapterPosition()]) {
                        color = this.val$dialog.requireContext().getResources().getColor(R.color.color_text_black);
                    } else {
                        color = this.val$dialog.requireContext().getResources().getColor(R.color.color_text_gray);
                    }
                    helper.setTextColor(R.id.tv_name, color);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final SelectListWithIconDialog dialog) {
            final C02141 c02141 = new C02141(this, R.layout.item_select_list_with_icon, dialog.selectList, dialog);
            final boolean z = this.val$needConfirm;
            c02141.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectListWithIconDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    SelectListWithIconDialog.AnonymousClass1.lambda$convertView$0(SelectListWithIconDialog.this, z, c02141, baseQuickAdapter, view, i);
                }
            });
            c02141.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectListWithIconDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectListWithIconDialog.AnonymousClass1.lambda$convertView$1(SelectListWithIconDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectListWithIconDialog selectListWithIconDialog, boolean z, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (selectListWithIconDialog.enableArray == null || selectListWithIconDialog.enableArray[i]) {
                if (z) {
                    selectListWithIconDialog.setSelectPosition(i);
                    baseQuickAdapter.notifyDataSetChanged();
                } else {
                    if (selectListWithIconDialog.confirmAction != null) {
                        selectListWithIconDialog.confirmAction.act(Integer.valueOf(i));
                    }
                    selectListWithIconDialog.dismissDialog();
                }
            }
        }

        static /* synthetic */ void lambda$convertView$1(SelectListWithIconDialog selectListWithIconDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectListWithIconDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (selectListWithIconDialog.selectPosition == -1) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            if (selectListWithIconDialog.confirmAction != null) {
                selectListWithIconDialog.confirmAction.act(Integer.valueOf(selectListWithIconDialog.selectPosition));
            }
            selectListWithIconDialog.dismissDialog();
        }
    }

    public static SelectListWithIconDialog asDefault(boolean needConfirm) {
        return (SelectListWithIconDialog) new SelectListWithIconDialog().setViewConverter(new AnonymousClass1(needConfirm)).setMargin(16).setY(16).setGravity(80);
    }

    public SelectListWithIconDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectListWithIconDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectListWithIconDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectListWithIconDialog setSelectList(List<String> list) {
        this.selectList.clear();
        this.selectList.addAll(list);
        return this;
    }

    public SelectListWithIconDialog setEnableArray(boolean[] enableArray) {
        this.enableArray = enableArray;
        return this;
    }

    public SelectListWithIconDialog setIconRes(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public SelectListWithIconDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectListWithIconDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_list_dialog_with_icon";
    }
}