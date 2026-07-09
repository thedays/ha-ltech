package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
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
import com.ltech.smarthome.databinding.DialogAddCg485DeviceBinding;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.AddCg485DeviceDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class AddCg485DeviceDialog extends SmartDialog<DialogAddCg485DeviceBinding> {
    private String defaultName;
    private boolean iconMode;
    private OnSaveListener mOnSaveListener;
    private String title;
    private ObservableField<String> content = new ObservableField<>("");
    private int selectPosition = -1;
    private int type = 1;

    public interface OnSaveListener {
        void onSave(String name, int type, int iconPos);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_add_cg485_device;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.AddCg485DeviceDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAddCg485DeviceBinding, AddCg485DeviceDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogAddCg485DeviceBinding viewBinding, final AddCg485DeviceDialog dialog) {
            if (dialog.title != null) {
                viewBinding.tvTitle.setText(dialog.title);
            }
            viewBinding.setContent(dialog.content);
            if (dialog.defaultName != null) {
                dialog.content.set(dialog.defaultName);
            }
            if (dialog.iconMode) {
                viewBinding.groupAdd.setVisibility(8);
            }
            dialog.initRv(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.AddCg485DeviceDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    AddCg485DeviceDialog.AnonymousClass1.lambda$convertView$0(AddCg485DeviceDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(AddCg485DeviceDialog addCg485DeviceDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_clear) {
                addCg485DeviceDialog.content.set("");
                return;
            }
            if (id == R.id.tv_cancel) {
                addCg485DeviceDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (!addCg485DeviceDialog.iconMode && TextUtils.isEmpty((CharSequence) addCg485DeviceDialog.content.get())) {
                SmartToast.showShort(R.string.input_name);
                return;
            }
            addCg485DeviceDialog.dismissDialog();
            if (addCg485DeviceDialog.mOnSaveListener != null) {
                addCg485DeviceDialog.mOnSaveListener.onSave(((String) addCg485DeviceDialog.content.get()).trim(), addCg485DeviceDialog.type, addCg485DeviceDialog.selectPosition);
            }
        }
    }

    public static AddCg485DeviceDialog asDefault() {
        return (AddCg485DeviceDialog) new AddCg485DeviceDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogAddCg485DeviceBinding viewBinding) {
        int[] rs485DevicePic = IconRepository.getRs485DevicePic(getContext());
        ArrayList arrayList = new ArrayList();
        for (int i : rs485DevicePic) {
            arrayList.add(Integer.valueOf(i));
        }
        final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_select_icon, arrayList) { // from class: com.ltech.smarthome.view.dialog.AddCg485DeviceDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setGone(R.id.iv_select, true);
                ImageView imageView = (ImageView) helper.getView(R.id.iv_icon);
                imageView.setImageResource(item.intValue());
                imageView.setColorFilter(helper.getAdapterPosition() == AddCg485DeviceDialog.this.selectPosition ? 0 : ContextCompat.getColor(AddCg485DeviceDialog.this.getContext(), R.color.color_border_gray));
                helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((DialogAddCg485DeviceBinding) AddCg485DeviceDialog.this.mViewBinding).rvIcon.getMeasuredWidth() / 4;
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.AddCg485DeviceDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                AddCg485DeviceDialog.this.lambda$initRv$0(baseQuickAdapter, baseQuickAdapter2, view, i2);
            }
        });
        baseQuickAdapter.bindToRecyclerView(viewBinding.rvIcon);
        viewBinding.rvIcon.setHasFixedSize(true);
        viewBinding.rvIcon.setLayoutManager(new GridLayoutManager(getContext(), 4));
        viewBinding.rvIcon.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.AddCg485DeviceDialog.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(SizeUtils.dp2px(2.0f), SizeUtils.dp2px(2.0f), SizeUtils.dp2px(2.0f), SizeUtils.dp2px(2.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    public AddCg485DeviceDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddCg485DeviceDialog setType(int type) {
        this.type = type;
        return this;
    }

    public AddCg485DeviceDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public AddCg485DeviceDialog setDefaultName(String defaultName) {
        this.defaultName = defaultName;
        return this;
    }

    public AddCg485DeviceDialog setIconMode(boolean iconMode) {
        this.iconMode = iconMode;
        return this;
    }

    public AddCg485DeviceDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "add_cg485_device";
    }
}