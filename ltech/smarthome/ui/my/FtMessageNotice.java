package com.ltech.smarthome.ui.my;

import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtMessageNoticeBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/* loaded from: classes4.dex */
public class FtMessageNotice extends VMFragment<FtMessageNoticeBinding, FtMessageNoticeVM> implements IMessageCenter {
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_message_notice;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setEnableLoadMore(false);
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setFooterHeight(100.0f);
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setHeaderHeight(100.0f);
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.my.FtMessageNotice$$ExternalSyntheticLambda3
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtMessageNotice.this.lambda$initView$0(refreshLayout);
            }
        });
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ltech.smarthome.ui.my.FtMessageNotice$$ExternalSyntheticLambda4
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                FtMessageNotice.this.lambda$initView$1(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        ((FtMessageNoticeVM) this.mViewModel).pageNum = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RefreshLayout refreshLayout) {
        ((FtMessageNoticeVM) this.mViewModel).pageNum++;
        loadMoreData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((FtMessageNoticeVM) this.mViewModel).updateDataList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.FtMessageNotice$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMessageNotice.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtMessageNoticeVM) this.mViewModel).loadTotalDataEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.FtMessageNotice$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMessageNotice.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        ((FtMessageNoticeVM) this.mViewModel).loadMoreDataFinishEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.my.FtMessageNotice$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMessageNotice.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (((FtMessageNoticeVM) this.mViewModel).mAppNoticeList.isEmpty()) {
            showEmpty();
        } else {
            showContent();
        }
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (bool.booleanValue() && !((FtMessageNoticeVM) this.mViewModel).mAppNoticeList.isEmpty()) {
            ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setEnableLoadMore(false);
            if (((FtMessageNoticeVM) this.mViewModel).dataList.contains("footer")) {
                return;
            }
            ((FtMessageNoticeVM) this.mViewModel).dataList.insertItem("footer");
            return;
        }
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.setEnableLoadMore(true);
        if (((FtMessageNoticeVM) this.mViewModel).dataList.contains("footer")) {
            ((FtMessageNoticeVM) this.mViewModel).dataList.removeItem("footer");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        ((FtMessageNoticeBinding) this.mViewBinding).refreshLayout.finishLoadMore();
    }

    private void getData() {
        ((FtMessageNoticeVM) this.mViewModel).getAppNoticeList();
    }

    private void loadMoreData() {
        ((FtMessageNoticeVM) this.mViewModel).getMoreAppNoticeList(((FtMessageNoticeVM) this.mViewModel).pageNum);
    }

    @Override // com.ltech.smarthome.ui.my.IMessageCenter
    public void clearData() {
        ((FtMessageNoticeVM) this.mViewModel).mAppNoticeList.clear();
        ((FtMessageNoticeVM) this.mViewModel).updateDataList.setValue(true);
    }
}