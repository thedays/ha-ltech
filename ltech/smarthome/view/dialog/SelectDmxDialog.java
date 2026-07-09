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
import com.ltech.smarthome.view.dialog.SelectDmxDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectDmxDialog extends SmartDialog<DialogSelectListBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private int selectPosition = -1;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDmxDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, SelectDmxDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final SelectDmxDialog dialog) {
            final BaseQuickAdapter<CtDmxType, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CtDmxType, BaseViewHolder>(this, R.layout.item_select_subtext_ver, dialog.getContentList()) { // from class: com.ltech.smarthome.view.dialog.SelectDmxDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, CtDmxType item) {
                    helper.setText(R.id.tv_name, item.getName()).setText(R.id.tv_sub_name, item.getSubName()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectDmxDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectDmxDialog.AnonymousClass1.lambda$convertView$0(SelectDmxDialog.this, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDmxDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDmxDialog.AnonymousClass1.lambda$convertView$1(SelectDmxDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectDmxDialog selectDmxDialog, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            selectDmxDialog.setSelectPosition(i);
            baseQuickAdapter.notifyDataSetChanged();
        }

        static /* synthetic */ void lambda$convertView$1(SelectDmxDialog selectDmxDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectDmxDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectDmxDialog.confirmAction != null) {
                    selectDmxDialog.confirmAction.act(Integer.valueOf(selectDmxDialog.selectPosition));
                }
                selectDmxDialog.dismissDialog();
            }
        }
    }

    public static SelectDmxDialog asDefault() {
        return (SelectDmxDialog) new SelectDmxDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<CtDmxType> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CtDmxType(CtDmxType.TYPE_CT1, getString(R.string.ct_dmx_address_C_name), getString(R.string.ct_dmx_address_C_content)));
        arrayList.add(new CtDmxType(CtDmxType.TYPE_CT2, getString(R.string.ct_dmx_address_BRT_name), getString(R.string.ct_dmx_address_BRT_content)));
        return arrayList;
    }

    public static final class CtDmxType {
        private static int TYPE_CT1 = 1;
        private static int TYPE_CT2 = 2;
        private String name;
        private String subName;
        private int type;

        CtDmxType(int type, String name, String subName) {
            this.type = type;
            this.name = name;
            this.subName = subName;
        }

        int getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }
    }

    public SelectDmxDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectDmxDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectDmxDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectDmxDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectDmxDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_dmx_dialog";
    }
}