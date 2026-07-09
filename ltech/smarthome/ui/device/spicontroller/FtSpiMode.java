package com.ltech.smarthome.ui.device.spicontroller;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSpiModeBinding;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSpiMode extends BaseNormalFragment<FtSpiModeBinding> {
    private ActSpiControllerVM mViewModel;
    private BaseQuickAdapter<ModeItem, BaseViewHolder> modeAdapter;
    private List<ModeItem> list = new ArrayList();
    private int selectPosition = -1;

    public static class ModeItem {
        public int iconRes;
        public String modeName;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_spi_mode;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.mViewModel = (ActSpiControllerVM) new ViewModelProvider(requireActivity()).get(ActSpiControllerVM.class);
        this.list = getList();
        BaseQuickAdapter<ModeItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ModeItem, BaseViewHolder>(R.layout.item_spi_mode, this.list) { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiMode.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ModeItem item) {
                helper.setText(R.id.tv_mode, item.modeName).setImageResource(R.id.iv_mode, item.iconRes);
                ((AppCompatTextView) helper.getView(R.id.tv_mode)).getPaint().setFakeBoldText(true);
                if (FtSpiMode.this.mViewModel.selectAction) {
                    helper.setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == FtSpiMode.this.selectPosition ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                } else {
                    helper.setGone(R.id.iv_select, false).setBackgroundRes(R.id.layout_mode, helper.getAdapterPosition() == FtSpiMode.this.selectPosition ? R.drawable.shape_light_gray_bt_10 : R.drawable.shape_white_round_bg_10);
                }
            }
        };
        this.modeAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiMode$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtSpiMode.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.modeAdapter.bindToRecyclerView(((FtSpiModeBinding) this.mViewBinding).rvContent);
        ((FtSpiModeBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((FtSpiModeBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((FtSpiModeBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiMode.2
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
            this.mViewModel.selectModeNum = i + 1;
            this.modeAdapter.notifyDataSetChanged();
            if (this.mViewModel.selectAction) {
                this.mViewModel.getModeCmdHelper().playSpiModeWithoutParam(getActivity(), this.mViewModel.selectModeNum, false);
            } else {
                showLoadingDialog("");
                this.mViewModel.queryModeInfo();
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.mViewModel.refreshModeList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiMode$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtSpiMode.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        this.modeAdapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mViewModel.selectPlayList > 0) {
            this.selectPosition = -1;
            this.mViewModel.selectModeNum = 0;
            this.modeAdapter.notifyDataSetChanged();
        }
    }

    private List<ModeItem> getList() {
        ArrayList arrayList = new ArrayList();
        String[] spiModeName = NameRepository.getSpiModeName(getActivity());
        int[] spiModePic = IconRepository.getSpiModePic(getActivity());
        int length = spiModeName.length;
        for (int i = 0; i < length; i++) {
            ModeItem modeItem = new ModeItem();
            modeItem.modeName = spiModeName[i];
            modeItem.iconRes = spiModePic[i];
            arrayList.add(modeItem);
        }
        return arrayList;
    }
}