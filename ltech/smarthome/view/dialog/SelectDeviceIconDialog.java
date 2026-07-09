package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogDeviceIconSelectorBinding;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectDeviceIconDialog extends SmartDialog<DialogDeviceIconSelectorBinding> {
    public List<IconContent> dataList;
    private int[] iconRes;
    private int[] iconValueRes;
    private boolean isGroupTag;
    public BaseQuickAdapter<IconContent, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    public static class IconContent {
        public int iconRes;
        public String name;
    }

    public interface OnSaveListener {
        void cancel();

        boolean onSave(int selectPos);
    }

    private int itemLayout() {
        return R.layout.item_select_device_icon;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_device_icon_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDeviceIconDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogDeviceIconSelectorBinding, SelectDeviceIconDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogDeviceIconSelectorBinding viewBinding, final SelectDeviceIconDialog dialog) {
            dialog.initRv(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDeviceIconDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDeviceIconDialog.AnonymousClass1.lambda$convertView$0(SelectDeviceIconDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectDeviceIconDialog selectDeviceIconDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectDeviceIconDialog.dismissDialog();
                if (selectDeviceIconDialog.mOnSaveListener != null) {
                    selectDeviceIconDialog.mOnSaveListener.cancel();
                    return;
                }
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (selectDeviceIconDialog.mOnSaveListener != null) {
                if (selectDeviceIconDialog.mOnSaveListener.onSave(new int[]{1, 4, 10, 9, 8, 7, 3, 2, 16, 5, 6, 11, 15, 13, 14, 12}[selectDeviceIconDialog.selectPosition] - 1)) {
                    selectDeviceIconDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectDeviceIconDialog.dismissDialog();
        }
    }

    public static SelectDeviceIconDialog asDefault() {
        return (SelectDeviceIconDialog) new SelectDeviceIconDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogDeviceIconSelectorBinding viewBinding) {
        this.dataList = getList();
        BaseQuickAdapter<IconContent, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IconContent, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IconContent item) {
                helper.setImageResource(R.id.iv_icon, item.iconRes).setText(R.id.tv_name, item.name);
                ImageView imageView = (ImageView) helper.getView(R.id.iv_icon);
                imageView.setColorFilter(ContextCompat.getColor(SelectDeviceIconDialog.this.getContext(), R.color.color_border_gray));
                imageView.setColorFilter(helper.getAdapterPosition() == SelectDeviceIconDialog.this.selectPosition ? 0 : ContextCompat.getColor(SelectDeviceIconDialog.this.getContext(), R.color.color_border_gray));
                helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((DialogDeviceIconSelectorBinding) SelectDeviceIconDialog.this.mViewBinding).rvContent.getMeasuredWidth() / 4;
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectDeviceIconDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                SelectDeviceIconDialog.this.lambda$initRv$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((DialogDeviceIconSelectorBinding) this.mViewBinding).rvContent);
        viewBinding.rvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        viewBinding.rvContent.setPadding(ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
        viewBinding.rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
    }

    protected List<IconContent> getList() {
        if (this.isGroupTag) {
            this.iconRes = IconRepository.getLightGroupIconSelect(getContext());
            this.iconValueRes = IconRepository.getLightGroupIconValue(getContext());
        } else {
            this.iconRes = IconRepository.getLightIconSelect(getContext());
            this.iconValueRes = IconRepository.getLightIconValue(getContext());
        }
        int i = 0;
        while (true) {
            int[] iArr = this.iconRes;
            if (i >= iArr.length) {
                break;
            }
            int i2 = iArr[i];
            int[] iArr2 = this.iconValueRes;
            int i3 = this.selectPosition;
            if (i3 == -1) {
                i3 = 0;
            }
            if (i2 == iArr2[i3]) {
                this.selectPosition = i;
                break;
            }
            i++;
        }
        String[] lightIconName = NameRepository.getLightIconName(getContext());
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < this.iconRes.length; i4++) {
            IconContent iconContent = new IconContent();
            iconContent.iconRes = this.iconRes[i4];
            iconContent.name = lightIconName[i4];
            arrayList.add(iconContent);
        }
        return arrayList;
    }

    public SelectDeviceIconDialog imageIndex(int imageIndex) {
        this.selectPosition = imageIndex;
        return this;
    }

    public SelectDeviceIconDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_device_icon_selector";
    }

    public SelectDeviceIconDialog setGroupTag(boolean isGroupTag) {
        this.isGroupTag = isGroupTag;
        return this;
    }
}