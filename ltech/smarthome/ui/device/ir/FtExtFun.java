package com.ltech.smarthome.ui.device.ir;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtTvExtFunBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class FtExtFun extends VMFragment<FtTvExtFunBinding, BaseTvVM> {
    private Class clazz;
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;
    private int remoteId = -1;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_tv_ext_fun;
    }

    public static FtExtFun newInstance(Class clazz) {
        FtExtFun ftExtFun = new FtExtFun();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.TV_MORE_FUN, clazz);
        ftExtFun.setArguments(bundle);
        return ftExtFun;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMFragment
    public BaseTvVM getViewModel() {
        this.clazz = (Class) getArguments().getSerializable(Constants.TV_MORE_FUN);
        return (BaseTvVM) new ViewModelProvider(getActivity()).get(this.clazz);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.remoteId = ((BaseTvVM) this.mViewModel).irData.rid;
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_fun, ((BaseTvVM) this.mViewModel).getItemList()) { // from class: com.ltech.smarthome.ui.device.ir.FtExtFun.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setText(R.id.tv_name, item.getName());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.FtExtFun$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtExtFun.this.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtTvExtFunBinding) this.mViewBinding).rvContent);
        ((FtTvExtFunBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ((FtTvExtFunBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((FtTvExtFunBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((FtTvExtFunBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.ir.FtExtFun.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        ((BaseTvVM) this.mViewModel).clickAnimate(view);
        ((BaseTvVM) this.mViewModel).getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.ir.FtExtFun$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FtExtFun.this.lambda$initView$0(i);
            }
        }, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i) {
        ((BaseTvVM) this.mViewModel).sendIrControl(this.mAdapter.getData().get(i).getFid());
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((BaseTvVM) this.mViewModel).ridLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.FtExtFun$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtExtFun.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        if (num == null || num.intValue() <= 0 || this.remoteId == num.intValue()) {
            return;
        }
        this.remoteId = num.intValue();
        this.mAdapter.setNewData(((BaseTvVM) this.mViewModel).getItemList());
    }
}