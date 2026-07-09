package com.ltech.smarthome.ui.device.screenpanel;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSelectIconsBinding;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.view.dialog.SetScreenDisplayDialog;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class FtSelectIcons extends BaseNormalFragment<FtSelectIconsBinding> {
    private int[] iconResDefault;
    private int[] iconResSelect;
    private int iconType;
    private BaseQuickAdapter<Integer, BaseViewHolder> mIconAdapter;
    private BaseQuickAdapter<String, BaseViewHolder> mTextAdapter;
    private int screenType;
    private String[] stringRes;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;
    private int iconIndex = -1;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_select_icons;
    }

    public int getIconIndex() {
        return this.iconIndex;
    }

    public static FtSelectIcons newInstance(int screenType, int iconType) {
        FtSelectIcons ftSelectIcons = new FtSelectIcons();
        Bundle bundle = new Bundle();
        bundle.putInt("screenType", screenType);
        bundle.putInt("iconType", iconType);
        ftSelectIcons.setArguments(bundle);
        return ftSelectIcons;
    }

    public static FtSelectIcons newInstance(int screenType, int iconType, int iconIndex) {
        FtSelectIcons ftSelectIcons = new FtSelectIcons();
        Bundle bundle = new Bundle();
        bundle.putInt("screenType", screenType);
        bundle.putInt("iconType", iconType);
        bundle.putInt("iconIndex", iconIndex);
        ftSelectIcons.setArguments(bundle);
        return ftSelectIcons;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            this.screenType = getArguments().getInt("screenType", 1);
            this.iconType = getArguments().getInt("iconType", 1);
        }
        int i = this.screenType;
        int i2 = R.layout.item_screen_info_input;
        if (i == 1) {
            int i3 = this.iconType;
            if (i3 == 1) {
                this.stringRes = getResources().getStringArray(R.array.scene_name_tip);
            } else if (i3 == 2) {
                this.stringRes = getResources().getStringArray(R.array.room_name_tip);
            } else {
                this.stringRes = getResources().getStringArray(R.array.device_name_tip);
            }
            BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(i2, Arrays.asList(this.stringRes)) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtSelectIcons.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, String item) {
                    helper.setGone(R.id.tv_name, true).setGone(R.id.iv_icon, false).setText(R.id.tv_name, item);
                    helper.setBackgroundRes(R.id.layout_item_bg, R.drawable.selector_round_stroke_5);
                    helper.setTextColor(R.id.tv_name, FtSelectIcons.this.getResources().getColor(R.color.selector_select_icon_text_click));
                }
            };
            this.mTextAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtSelectIcons$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i4) {
                    FtSelectIcons.this.lambda$initView$0(baseQuickAdapter2, view, i4);
                }
            });
            this.mTextAdapter.bindToRecyclerView(((FtSelectIconsBinding) this.mViewBinding).rvContent);
        } else {
            this.selectPosition = getArguments().getInt("iconIndex", 1000) % 1000;
            int i4 = this.iconType;
            if (i4 == 1) {
                this.iconResDefault = ScreenIconUtils.sceneDefault;
                this.iconResSelect = ScreenIconUtils.sceneSelect;
            } else if (i4 == 2) {
                this.iconResDefault = ScreenIconUtils.locDefault;
                this.iconResSelect = ScreenIconUtils.locSelect;
            } else {
                this.iconResDefault = ScreenIconUtils.deviceDefault;
                this.iconResSelect = ScreenIconUtils.deviceSelect;
            }
            BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Integer, BaseViewHolder>(i2, Arrays.asList(ArrayUtils.toObject(this.iconResDefault))) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtSelectIcons.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    helper.setGone(R.id.tv_name, false).setGone(R.id.iv_icon, true).setImageResource(R.id.iv_icon, item.intValue());
                    if (FtSelectIcons.this.selectPosition == helper.getBindingAdapterPosition()) {
                        helper.setImageResource(R.id.iv_icon, FtSelectIcons.this.iconResSelect[helper.getBindingAdapterPosition()]);
                        helper.setBackgroundRes(R.id.layout_item_bg, R.drawable.shape_round_pink_5);
                        helper.setTextColor(R.id.tv_name, FtSelectIcons.this.getResources().getColor(R.color.red));
                    } else {
                        helper.setImageResource(R.id.iv_icon, FtSelectIcons.this.iconResDefault[helper.getBindingAdapterPosition()]);
                        helper.setBackgroundRes(R.id.layout_item_bg, R.drawable.shape_round_stroke_gray_5);
                        helper.setTextColor(R.id.tv_name, FtSelectIcons.this.getResources().getColor(R.color.color_text_light_black));
                    }
                }
            };
            this.mIconAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.FtSelectIcons$$ExternalSyntheticLambda1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i5) {
                    FtSelectIcons.this.lambda$initView$1(baseQuickAdapter3, view, i5);
                }
            });
            this.mIconAdapter.bindToRecyclerView(((FtSelectIconsBinding) this.mViewBinding).rvContent);
        }
        ((FtSelectIconsBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ((FtSelectIconsBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.FtSelectIcons.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            baseQuickAdapter.notifyItemChanged(i);
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        SetScreenDisplayDialog.getViewBinding().inputEdtTxt.setText(this.mTextAdapter.getData().get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            baseQuickAdapter.notifyItemChanged(i);
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        SetScreenDisplayDialog.setIconIndex((this.iconType * 1000) + i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.screenType == 1) {
            BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = this.mTextAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyItemChanged(this.selectPosition);
                this.selectPosition = -1;
                return;
            }
            return;
        }
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = this.mIconAdapter;
        if (baseQuickAdapter2 != null) {
            baseQuickAdapter2.notifyItemChanged(this.selectPosition);
            this.selectPosition = -1;
        }
    }
}