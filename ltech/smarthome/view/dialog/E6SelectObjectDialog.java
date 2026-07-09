package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectListBinding;
import com.ltech.smarthome.utils.ParamMap;
import com.ltech.smarthome.view.NumberSetView;
import com.ltech.smarthome.view.dialog.E6SelectObjectDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class E6SelectObjectDialog extends SmartDialog<DialogSelectListBinding> {
    private int addNumber;
    private NumberSetView[] addressViewArray = new NumberSetView[2];
    private int controlType;
    private MultipleItemRvAdapter<ParamMap, BaseViewHolder> mAdapter;
    private OnSaveListener onSaveListener;

    public interface OnSaveListener {
        void onSave(int controlType, int addNumber);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.E6SelectObjectDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectListBinding, E6SelectObjectDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectListBinding viewBinding, final E6SelectObjectDialog dialog) {
            viewBinding.tvTitle.setText(R.string.control_object);
            viewBinding.tvConfirm.setText(R.string.save);
            viewBinding.tvCancel.setText(R.string.cancel);
            dialog.initAdapter();
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.E6SelectObjectDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    E6SelectObjectDialog.AnonymousClass1.lambda$convertView$0(E6SelectObjectDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(E6SelectObjectDialog e6SelectObjectDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                e6SelectObjectDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (e6SelectObjectDialog.onSaveListener != null) {
                    e6SelectObjectDialog.save();
                }
                e6SelectObjectDialog.dismissDialog();
            }
        }
    }

    public static E6SelectObjectDialog asDefault() {
        return (E6SelectObjectDialog) new E6SelectObjectDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setY(16).setMargin(16);
    }

    private List<ParamMap> getList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ParamMap(getString(R.string.single_address), 0));
        arrayList.add(new ParamMap(getString(R.string.group_address), 1));
        arrayList.add(new ParamMap(getString(R.string.dali_broadcast), 2));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAdapter() {
        MultipleItemRvAdapter<ParamMap, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ParamMap, BaseViewHolder>(getList()) { // from class: com.ltech.smarthome.view.dialog.E6SelectObjectDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ParamMap item) {
                return item.getValue() == 2 ? 1 : 0;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ParamMap, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.E6SelectObjectDialog.2.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_e6d_address;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, ParamMap item, int position) {
                        helper.itemView.setBackgroundResource(E6SelectObjectDialog.this.controlType == position ? R.color.color_bg_1 : R.color.white);
                        helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.iv_more, E6SelectObjectDialog.this.controlType == position ? R.mipmap.ic_tick_sel : R.color.transparent).setGone(R.id.layout_address, E6SelectObjectDialog.this.controlType == position).setText(R.id.tv_title, String.format("%s(%s)", E6SelectObjectDialog.this.getString(R.string.dali_light), position == 0 ? "0-63" : "0-15"));
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        NumberSetView numberSetView = (NumberSetView) helper.getView(R.id.add_set_view);
                        if (item.getValue() == 0) {
                            numberSetView.setRange(0, 63);
                            numberSetView.setNumber(E6SelectObjectDialog.this.addNumber <= 63 ? E6SelectObjectDialog.this.addNumber : 0);
                        } else {
                            numberSetView.setRange(0, 15);
                            numberSetView.setNumber(E6SelectObjectDialog.this.addNumber <= 15 ? E6SelectObjectDialog.this.addNumber : 0);
                        }
                        numberSetView.setEditable(true);
                        if (E6SelectObjectDialog.this.addressViewArray[position] == null) {
                            E6SelectObjectDialog.this.addressViewArray[position] = numberSetView;
                        }
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, ParamMap data, int position) {
                        if (E6SelectObjectDialog.this.controlType != position) {
                            E6SelectObjectDialog.this.addNumber = 0;
                            E6SelectObjectDialog.this.controlType = position;
                            E6SelectObjectDialog.this.mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ParamMap, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.E6SelectObjectDialog.2.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_e6d_default;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, ParamMap item, int position) {
                        helper.itemView.setBackgroundResource(E6SelectObjectDialog.this.controlType == position ? R.color.color_bg_1 : R.color.white);
                        helper.setText(R.id.tv_name, item.getName()).setImageResource(R.id.iv_more, E6SelectObjectDialog.this.controlType == position ? R.mipmap.ic_tick_sel : R.color.transparent);
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, ParamMap data, int position) {
                        E6SelectObjectDialog.this.controlType = position;
                        E6SelectObjectDialog.this.mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((DialogSelectListBinding) this.mViewBinding).rvContent);
        ((DialogSelectListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        int i = this.controlType;
        if (i == 0) {
            this.addNumber = this.addressViewArray[0].getNumber();
        } else if (i == 1) {
            this.addNumber = this.addressViewArray[1].getNumber();
        } else {
            this.addNumber = 254;
        }
        this.onSaveListener.onSave(this.controlType, this.addNumber);
    }

    public E6SelectObjectDialog setControlType(int type) {
        this.controlType = type;
        return this;
    }

    public E6SelectObjectDialog setAddNumber(int number) {
        this.addNumber = number;
        return this;
    }

    public E6SelectObjectDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }
}