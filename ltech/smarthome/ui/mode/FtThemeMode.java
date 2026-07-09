package com.ltech.smarthome.ui.mode;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSelectThemeBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtThemeMode extends BaseNormalFragment<FtSelectThemeBinding> {
    private OnItemClickCallback callback;
    private int lightType;
    private BaseQuickAdapter<ModeItem, BaseViewHolder> themeAdapter;
    private List<ModeItem> list = new ArrayList();
    private int selectPosition = -1;
    private String[] modeNames = null;

    public static class ModeItem {
        public int iconRes;
        public String modeContent;
        public String modeName;
    }

    public interface OnItemClickCallback {
        void click(int position);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_select_theme;
    }

    public static FtThemeMode newInstance(int lightType) {
        FtThemeMode ftThemeMode = new FtThemeMode();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.LIGHT_TYPE, lightType);
        ftThemeMode.setArguments(bundle);
        return ftThemeMode;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            this.lightType = getArguments().getInt(Constants.LIGHT_TYPE, 1);
        }
        this.list = getList();
        BaseQuickAdapter<ModeItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ModeItem, BaseViewHolder>(R.layout.item_default_mode_select, this.list) { // from class: com.ltech.smarthome.ui.mode.FtThemeMode.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ModeItem item) {
                helper.setText(R.id.tv_mode_name, item.modeName).setText(R.id.tv_mode_content, item.modeContent).setBackgroundRes(R.id.iv_cover, item.iconRes).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == FtThemeMode.this.selectPosition ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                ((AppCompatTextView) helper.getView(R.id.tv_mode_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.themeAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.mode.FtThemeMode$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtThemeMode.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.themeAdapter.bindToRecyclerView(((FtSelectThemeBinding) this.mViewBinding).rvContent);
        ((FtSelectThemeBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((FtSelectThemeBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((FtSelectThemeBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.mode.FtThemeMode.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(10.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(10.0f), ConvertUtils.dp2px(5.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            this.themeAdapter.notifyDataSetChanged();
            this.callback.click(this.selectPosition);
        }
    }

    private List<ModeItem> getList() {
        int[] dimDefaultModePic;
        String[] dimDefaultModeContent;
        ArrayList arrayList = new ArrayList();
        int i = this.lightType;
        if (i == 1) {
            this.modeNames = NameRepository.getDimDefaultModeName(getActivity());
            dimDefaultModePic = IconRepository.getDimDefaultModePic(getActivity());
            dimDefaultModeContent = NameRepository.getDimDefaultModeContent(getActivity());
        } else if (i == 2) {
            this.modeNames = NameRepository.getCtDefaultModeName(getActivity());
            dimDefaultModePic = IconRepository.getCtDefaultModePic(getActivity());
            dimDefaultModeContent = NameRepository.getCtDefaultModeContent(getActivity());
        } else if (i == 3 || i == 4 || i == 5 || i == 20) {
            this.modeNames = NameRepository.getDefaultModeName(getActivity());
            dimDefaultModePic = IconRepository.getDefaultModePic(getActivity());
            dimDefaultModeContent = NameRepository.getDefaultModeContent(getActivity());
        } else {
            dimDefaultModePic = null;
            dimDefaultModeContent = null;
        }
        int length = this.modeNames.length;
        for (int i2 = 0; i2 < length; i2++) {
            ModeItem modeItem = new ModeItem();
            modeItem.modeName = this.modeNames[i2];
            modeItem.modeContent = dimDefaultModeContent[i2];
            modeItem.iconRes = dimDefaultModePic[i2];
            arrayList.add(modeItem);
        }
        return arrayList;
    }

    public void setCallback(OnItemClickCallback callback) {
        this.callback = callback;
    }

    public String getSelectThemeName() {
        return this.modeNames[this.selectPosition];
    }
}