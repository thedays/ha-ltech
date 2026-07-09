package com.ltech.smarthome.ui.my;

import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtMessageHomeBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/* loaded from: classes4.dex */
public class FtMessageHome extends VMFragment<FtMessageHomeBinding, FtMessageHomeVM> implements IMessageCenter {
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_message_home;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setEnableLoadMore(false);
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setFooterHeight(100.0f);
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setHeaderHeight(100.0f);
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.my.FtMessageHome$$ExternalSyntheticLambda0
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtMessageHome.this.lambda$initView$0(refreshLayout);
            }
        });
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ltech.smarthome.ui.my.FtMessageHome$$ExternalSyntheticLambda1
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                FtMessageHome.this.lambda$initView$1(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        ((FtMessageHomeVM) this.mViewModel).pageNum = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RefreshLayout refreshLayout) {
        ((FtMessageHomeVM) this.mViewModel).pageNum++;
        loadMoreData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((FtMessageHomeVM) this.mViewModel).updateDataList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.FtMessageHome$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMessageHome.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtMessageHomeVM) this.mViewModel).loadTotalDataEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.FtMessageHome$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMessageHome.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        ((FtMessageHomeVM) this.mViewModel).loadMoreDataFinishEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.FtMessageHome$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMessageHome.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (((FtMessageHomeVM) this.mViewModel).mPlaceMessageList.isEmpty()) {
            showEmpty();
        } else {
            showContent();
        }
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (bool.booleanValue() && !((FtMessageHomeVM) this.mViewModel).mPlaceMessageList.isEmpty()) {
            ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setEnableLoadMore(false);
            if (((FtMessageHomeVM) this.mViewModel).dataList.contains("footer")) {
                return;
            }
            ((FtMessageHomeVM) this.mViewModel).dataList.insertItem("footer");
            return;
        }
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.setEnableLoadMore(true);
        if (((FtMessageHomeVM) this.mViewModel).dataList.contains("footer")) {
            ((FtMessageHomeVM) this.mViewModel).dataList.removeItem("footer");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        ((FtMessageHomeBinding) this.mViewBinding).refreshLayout.finishLoadMore();
    }

    private void getData() {
        ((FtMessageHomeVM) this.mViewModel).getPlaceMessageList();
    }

    private void loadMoreData() {
        ((FtMessageHomeVM) this.mViewModel).getMorePlaceMessageList(((FtMessageHomeVM) this.mViewModel).pageNum);
    }

    @Override // com.ltech.smarthome.ui.my.IMessageCenter
    public void clearData() {
        ((FtMessageHomeVM) this.mViewModel).mPlaceMessageList.clear();
        ((FtMessageHomeVM) this.mViewModel).updateDataList.setValue(true);
    }
}