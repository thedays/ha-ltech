package com.ltech.smarthome.ui.device.spicontroller;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSpiPlayListBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SpiLightExtParam;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSpiPlayList extends BaseNormalFragment<FtSpiPlayListBinding> {
    private long controlId;
    private SpiLightExtParam extParam;
    private BaseQuickAdapter<SpiLightExtParam.PlayList, BaseViewHolder> mAdapter;
    private ActSpiControllerVM mViewModel;
    private List<SpiLightExtParam.PlayList> list = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_spi_play_list;
    }

    public static FtSpiPlayList newInstance(long controlId) {
        FtSpiPlayList ftSpiPlayList = new FtSpiPlayList();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.CONTROL_ID, controlId);
        ftSpiPlayList.setArguments(bundle);
        return ftSpiPlayList;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.mViewModel = (ActSpiControllerVM) new ViewModelProvider(requireActivity()).get(ActSpiControllerVM.class);
        if (getArguments() != null) {
            this.controlId = getArguments().getLong(Constants.CONTROL_ID);
        }
        BaseQuickAdapter<SpiLightExtParam.PlayList, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SpiLightExtParam.PlayList, BaseViewHolder>(R.layout.item_spi_play_list, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiPlayList.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SpiLightExtParam.PlayList item) {
                helper.setText(R.id.tv_main, FtSpiPlayList.this.getString(R.string.play_list));
                helper.setText(R.id.tv_sub, FtSpiPlayList.this.getString(R.string.spi_mode_number, Integer.valueOf(item.getModes().size())));
                helper.setGone(R.id.iv_more, !FtSpiPlayList.this.mViewModel.selectAction);
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
                if (FtSpiPlayList.this.mViewModel.selectAction) {
                    helper.setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == FtSpiPlayList.this.selectPosition ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                } else {
                    helper.setGone(R.id.iv_select, false).setBackgroundRes(R.id.layout_mode, helper.getAdapterPosition() == FtSpiPlayList.this.selectPosition ? R.drawable.shape_light_gray_bt_10 : R.drawable.shape_white_round_bg_10);
                    helper.addOnClickListener(R.id.iv_more);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiPlayList$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtSpiPlayList.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiPlayList$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtSpiPlayList.this.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtSpiPlayListBinding) this.mViewBinding).rvContent);
        ((FtSpiPlayListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((FtSpiPlayListBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((FtSpiPlayListBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiPlayList.2
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
            this.mViewModel.selectPlayList = i + 1;
            this.mAdapter.notifyDataSetChanged();
            if (this.mViewModel.selectAction) {
                this.mViewModel.getModeCmdHelper().playSpiModeWithoutParam(getActivity(), this.mViewModel.selectModeNum, true);
            } else {
                showLoadingDialog("");
                this.mViewModel.queryPlayListInfo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (view.getId() == R.id.iv_more) {
            NavUtils.destination(ActSpiEditPlayList.class).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.LIST_POSITION, i).withString(Constants.MODE_LIST, GsonUtils.toJson(this.list.get(i).getModes())).navigation(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mViewModel.selectModeNum > 0) {
            this.selectPosition = -1;
            this.mViewModel.selectPlayList = 0;
        }
        List<SpiLightExtParam.PlayList> list = getList();
        this.list = list;
        this.mAdapter.replaceData(list);
    }

    private List<SpiLightExtParam.PlayList> getList() {
        this.extParam = new SpiLightExtParam();
        Device value = this.mViewModel.controlObject.getValue();
        if (value != null && value.getExtParam() != null) {
            SpiLightExtParam spiLightExtParam = (SpiLightExtParam) value.getExtParam(SpiLightExtParam.class);
            this.extParam = spiLightExtParam;
            if (spiLightExtParam != null) {
                return spiLightExtParam.getPlayList();
            }
        }
        return new ArrayList();
    }
}