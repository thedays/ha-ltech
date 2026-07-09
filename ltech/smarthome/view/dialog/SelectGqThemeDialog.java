package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectGqThemeBinding;
import com.ltech.smarthome.view.dialog.SelectGqThemeDialog;
import com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectGqThemeDialog extends SmartDialog<DialogSelectGqThemeBinding> {
    private String cancelString;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private ImageView[] imageViews;
    private List<Item> itemList;
    private int selectedPos = 0;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_gq_theme;
    }

    public static SelectGqThemeDialog asDefault() {
        return asDefault(false);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectGqThemeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectGqThemeBinding, SelectGqThemeDialog> {
        final /* synthetic */ boolean val$needConfirm;

        AnonymousClass1(final boolean val$needConfirm) {
            this.val$needConfirm = val$needConfirm;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSelectGqThemeBinding viewBinding, final SelectGqThemeDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            if (this.val$needConfirm) {
                if (!StringUtils.isEmpty(dialog.cancelString)) {
                    viewBinding.tvCancel.setText(dialog.cancelString);
                }
                if (!StringUtils.isEmpty(dialog.confirmString)) {
                    viewBinding.tvConfirm.setText(dialog.confirmString);
                }
            } else {
                viewBinding.tvCancel.setText("");
                viewBinding.tvConfirm.setText("");
            }
            final boolean z = this.val$needConfirm;
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectGqThemeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectGqThemeDialog.AnonymousClass1.lambda$convertView$0(SelectGqThemeDialog.this, z, (View) obj);
                }
            }));
            for (int i = 0; i < dialog.imageViews.length; i++) {
                ImageView imageView = dialog.imageViews[i];
                if (imageView != null) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(4.0f), ConvertUtils.dp2px(4.0f));
                    layoutParams.setMargins(2, 0, 2, 0);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(R.drawable.shape_ovl_8000000);
                    viewBinding.layoutPoint.addView(imageView);
                }
            }
            RecyclerView recyclerView = viewBinding.rv;
            PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(1, 1);
            recyclerView.setLayoutManager(pagerGridLayoutManager);
            pagerGridLayoutManager.setPagerChangedListener(new PagerGridLayoutManager.PagerChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectGqThemeDialog.1.1
                @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
                public void onPagerCountChanged(int pagerCount) {
                }

                @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
                public void onPagerIndexSelected(int prePagerIndex, int currentPagerIndex) {
                    dialog.selectedPos = currentPagerIndex;
                    dialog.imageViews[currentPagerIndex].setImageResource(R.drawable.shape_ovl_ff00000);
                    if (prePagerIndex >= 0) {
                        dialog.imageViews[prePagerIndex].setImageResource(R.drawable.shape_ovl_8000000);
                    }
                    if (AnonymousClass1.this.val$needConfirm || dialog.confirmAction == null) {
                        return;
                    }
                    dialog.confirmAction.act(Integer.valueOf(dialog.selectedPos));
                }
            });
            if (dialog.itemList == null) {
                dialog.itemList = new ArrayList();
            }
            viewBinding.rv.setAdapter(new BaseQuickAdapter<Item, BaseViewHolder>(this, R.layout.item_pub_iv, dialog.itemList) { // from class: com.ltech.smarthome.view.dialog.SelectGqThemeDialog.1.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder baseViewHolder, Item item) {
                    if (!StringUtils.isEmpty(item.url)) {
                        new RequestOptions();
                        Glide.with(this.mContext).load(item.url).apply((BaseRequestOptions<?>) RequestOptions.circleCropTransform()).into((ImageView) baseViewHolder.getView(R.id.iv));
                        return;
                    }
                    baseViewHolder.setImageResource(R.id.iv, item.img);
                }
            });
            pagerGridLayoutManager.scrollToPagerIndex(dialog.selectedPos);
        }

        static /* synthetic */ void lambda$convertView$0(SelectGqThemeDialog selectGqThemeDialog, boolean z, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectGqThemeDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (z && selectGqThemeDialog.confirmAction != null) {
                    selectGqThemeDialog.confirmAction.act(Integer.valueOf(selectGqThemeDialog.selectedPos));
                }
                selectGqThemeDialog.dismissDialog();
            }
        }
    }

    public static SelectGqThemeDialog asDefault(boolean needConfirm) {
        return (SelectGqThemeDialog) new SelectGqThemeDialog().setViewConverter(new AnonymousClass1(needConfirm)).setGravity(80);
    }

    public SelectGqThemeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectGqThemeDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectGqThemeDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectGqThemeDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectGqThemeDialog setData(List<Item> itemList) {
        this.itemList = itemList;
        this.imageViews = new ImageView[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            this.imageViews[i] = new ImageView(ActivityUtils.getTopActivity());
        }
        return this;
    }

    public SelectGqThemeDialog selectedPos(int pos) {
        this.selectedPos = pos;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "SelectGqThemeDialog";
    }

    public static class Item {
        private int img;
        private String url;

        public Item(int img) {
            this.img = img;
        }

        public Item(String url) {
            this.url = url;
        }
    }
}