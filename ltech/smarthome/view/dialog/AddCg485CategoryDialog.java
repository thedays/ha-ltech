package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogAddCg485CategoryBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.AddCg485CategoryDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class AddCg485CategoryDialog extends SmartDialog<DialogAddCg485CategoryBinding> {
    private boolean colorMode;
    private String defaultName;
    private OnSaveListener mOnSaveListener;
    private String title;
    private ObservableField<String> content = new ObservableField<>("");
    private int selectPosition = -1;
    private int type = 1;

    public interface OnSaveListener {
        void onSave(String name, int type, int colorPos);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_add_cg485_category;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAddCg485CategoryBinding, AddCg485CategoryDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogAddCg485CategoryBinding viewBinding, final AddCg485CategoryDialog dialog) {
            if (dialog.title != null) {
                viewBinding.tvTitle.setText(dialog.title);
            }
            viewBinding.setContent(dialog.content);
            if (dialog.defaultName != null) {
                dialog.content.set(dialog.defaultName);
            }
            if (dialog.colorMode) {
                viewBinding.groupAdd.setVisibility(8);
            }
            dialog.initTypeSelect(viewBinding);
            dialog.initRv(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    AddCg485CategoryDialog.AnonymousClass1.lambda$convertView$0(AddCg485CategoryDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(AddCg485CategoryDialog addCg485CategoryDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_clear) {
                addCg485CategoryDialog.content.set("");
                return;
            }
            if (id == R.id.tv_cancel) {
                addCg485CategoryDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (!addCg485CategoryDialog.colorMode && TextUtils.isEmpty((CharSequence) addCg485CategoryDialog.content.get())) {
                SmartToast.showShort(R.string.input_name);
                return;
            }
            addCg485CategoryDialog.dismissDialog();
            if (addCg485CategoryDialog.mOnSaveListener != null) {
                addCg485CategoryDialog.mOnSaveListener.onSave(((String) addCg485CategoryDialog.content.get()).trim(), addCg485CategoryDialog.type, addCg485CategoryDialog.selectPosition);
            }
        }
    }

    public static AddCg485CategoryDialog asDefault() {
        return (AddCg485CategoryDialog) new AddCg485CategoryDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTypeSelect(final DialogAddCg485CategoryBinding viewBinding) {
        viewBinding.radioBleTo485.setEnable(true);
        viewBinding.radio485ToBle.setEnable(true);
        viewBinding.radioBleTo485.setCheck(this.type == 1);
        viewBinding.radio485ToBle.setCheck(this.type == 2);
        viewBinding.radioBleTo485.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                AddCg485CategoryDialog.this.lambda$initTypeSelect$0(viewBinding, radioImageTextView, z);
            }
        });
        viewBinding.radio485ToBle.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                AddCg485CategoryDialog.this.lambda$initTypeSelect$1(viewBinding, radioImageTextView, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTypeSelect$0(DialogAddCg485CategoryBinding dialogAddCg485CategoryBinding, RadioImageTextView radioImageTextView, boolean z) {
        this.type = 1;
        dialogAddCg485CategoryBinding.radioBleTo485.setCheck(true);
        dialogAddCg485CategoryBinding.radio485ToBle.setCheck(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTypeSelect$1(DialogAddCg485CategoryBinding dialogAddCg485CategoryBinding, RadioImageTextView radioImageTextView, boolean z) {
        this.type = 2;
        dialogAddCg485CategoryBinding.radioBleTo485.setCheck(false);
        dialogAddCg485CategoryBinding.radio485ToBle.setCheck(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogAddCg485CategoryBinding viewBinding) {
        int[] intArray = getContext().getResources().getIntArray(R.array.instruct_color);
        ArrayList arrayList = new ArrayList();
        for (int i : intArray) {
            arrayList.add(Integer.valueOf(i));
        }
        final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_cg485_color, arrayList) { // from class: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.itemView.getLayoutParams().height = SizeUtils.dp2px(42.0f);
                ((CardView) helper.getView(R.id.card)).setCardBackgroundColor(item.intValue());
                helper.setGone(R.id.iv_select, AddCg485CategoryDialog.this.selectPosition == helper.getBindingAdapterPosition());
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                AddCg485CategoryDialog.this.lambda$initRv$2(baseQuickAdapter, baseQuickAdapter2, view, i2);
            }
        });
        baseQuickAdapter.bindToRecyclerView(viewBinding.rvColor);
        viewBinding.rvColor.setHasFixedSize(true);
        viewBinding.rvColor.setLayoutManager(new GridLayoutManager(getContext(), 6));
        viewBinding.rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.AddCg485CategoryDialog.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$2(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    public AddCg485CategoryDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddCg485CategoryDialog setType(int type) {
        this.type = type;
        return this;
    }

    public AddCg485CategoryDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public AddCg485CategoryDialog setDefaultName(String defaultName) {
        this.defaultName = defaultName;
        return this;
    }

    public AddCg485CategoryDialog setColorMode(boolean colorMode) {
        this.colorMode = colorMode;
        return this;
    }

    public AddCg485CategoryDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "add_cg485_class_category";
    }
}