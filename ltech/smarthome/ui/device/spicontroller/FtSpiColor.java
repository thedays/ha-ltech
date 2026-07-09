package com.ltech.smarthome.ui.device.spicontroller;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSpiColorBinding;
import com.ltech.smarthome.ui.device.light.BrtControlHelper;
import com.ltech.smarthome.view.CircleColorPickerView;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSpiColor extends BaseNormalFragment<FtSpiColorBinding> {
    private BrtControlHelper brtHelper;
    private BaseQuickAdapter<Integer, BaseViewHolder> colorAdapter;
    private ActSpiControllerVM mViewModel;
    private int number;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_spi_color;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.mViewModel = (ActSpiControllerVM) new ViewModelProvider(requireActivity()).get(ActSpiControllerVM.class);
        setColorView();
        setRgbView();
    }

    private void setColorView() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, getColorList(requireActivity())) { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiColor.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        this.colorAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiColor$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtSpiColor.this.lambda$setColorView$0(baseQuickAdapter2, view, i);
            }
        });
        this.colorAdapter.bindToRecyclerView(((FtSpiColorBinding) this.mViewBinding).rvColor);
        ((FtSpiColorBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(getActivity(), 8));
        ((FtSpiColorBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiColor.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mViewModel.getLightCmdHelper().sendRgbDC(getActivity(), this.colorAdapter.getData().get(i).intValue(), true);
    }

    private void setRgbView() {
        ((FtSpiColorBinding) this.mViewBinding).ccpv.setOnColorChangedListener(new CircleColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiColor.3
            @Override // com.ltech.smarthome.view.CircleColorPickerView.OnColorChangedListener
            public void onColorChanged(int color, float xPercent, float yPercent) {
                FtSpiColor.this.number++;
                FtSpiColor.this.mViewModel.getLightCmdHelper().sendRgb(FtSpiColor.this.getActivity(), color, false);
            }

            @Override // com.ltech.smarthome.view.CircleColorPickerView.OnColorChangedListener
            public void onChangedFinish(int color, float xPercent, float yPercent) {
                if (FtSpiColor.this.number > 10) {
                    FtSpiColor.this.mViewModel.getLightCmdHelper().sendRgb(FtSpiColor.this.getActivity(), color, true);
                } else {
                    FtSpiColor.this.mViewModel.getLightCmdHelper().sendRgbDC(FtSpiColor.this.getActivity(), color, true);
                }
                FtSpiColor.this.number = 0;
            }
        });
        this.brtHelper = new BrtControlHelper(((FtSpiColorBinding) this.mViewBinding).sbBrt, ((FtSpiColorBinding) this.mViewBinding).tvBrt, new BrtControlHelper.OnBrtChangedListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.FtSpiColor.4
            @Override // com.ltech.smarthome.ui.device.light.BrtControlHelper.OnBrtChangedListener
            public void onProgressChanged(int progress, boolean finish) {
                FtSpiColor.this.mViewModel.getLightCmdHelper().sendRgbBrtHas1to9(FtSpiColor.this.getActivity(), progress, finish);
            }
        });
        this.brtHelper.setProgress(this.mViewModel.controlObject.getValue().getDeviceState().getRgbBrt());
    }

    public List<Integer> getColorList(Context context) {
        return Ints.asList(context.getResources().getIntArray(R.array.static_default_color));
    }
}