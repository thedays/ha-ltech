package com.ltech.smarthome.ui.intercom;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtLogBinding;
import com.ltech.smarthome.ui.item.CallLogItem;
import com.ltech.smarthome.view.TimeLineView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtCallLog extends VMFragment<FtLogBinding, FtLogVM> {
    private MultipleItemRvAdapter<CallLogItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_log;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        initRv();
        initRefresh();
        ((FtLogVM) this.mViewModel).getCallData();
    }

    private void initRefresh() {
        ((FtLogBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtLogBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtLogBinding) this.mViewBinding).refreshLayout.setHeaderHeight(100.0f);
        ((FtLogBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.intercom.FtCallLog$$ExternalSyntheticLambda1
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtCallLog.this.lambda$initRefresh$0(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefresh$0(RefreshLayout refreshLayout) {
        refreshData();
        refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyTryStringRes(R.string.retry).emptyStringRes(R.string.no_data));
    }

    private void initRv() {
        MultipleItemRvAdapter<CallLogItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<CallLogItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.intercom.FtCallLog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(CallLogItem entity) {
                return entity.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<CallLogItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.intercom.FtCallLog.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_intercom_log_call;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, CallLogItem data, int position) {
                        helper.setText(R.id.tv_time, data.getItem().getTime().size() > 1 ? data.getItem().getTime().get(1) : data.getItem().getTime().get(0)).setText(R.id.tv_name, data.getItem().getDevLocation());
                        TimeLineView timeLineView = (TimeLineView) helper.getView(R.id.line);
                        if (data.getPositionType() == FtLogVM.POSITION_TYPE_FIRST) {
                            timeLineView.setShowTopLine(false);
                            timeLineView.setShowBottomLine(true);
                        } else if (data.getPositionType() == FtLogVM.POSITION_TYPE_END) {
                            timeLineView.setShowTopLine(true);
                            timeLineView.setShowBottomLine(false);
                        } else {
                            timeLineView.setShowTopLine(true);
                            timeLineView.setShowBottomLine(true);
                        }
                        if (data.getItem().getIsAnswer() == 0) {
                            helper.setTextColor(R.id.tv_time, FtCallLog.this.getResources().getColor(R.color.color_text_red)).setTextColor(R.id.tv_name, FtCallLog.this.getResources().getColor(R.color.color_text_red));
                            helper.setText(R.id.tv_status, R.string.not_answer);
                            helper.setGone(R.id.tv_status, false);
                        } else {
                            helper.setTextColor(R.id.tv_time, FtCallLog.this.getResources().getColor(R.color.color_text_dark_gray)).setTextColor(R.id.tv_name, FtCallLog.this.getResources().getColor(R.color.item_music_time_color));
                            helper.setGone(R.id.tv_status, true);
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<CallLogItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.intercom.FtCallLog.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_intercom_log_title;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, CallLogItem data, int position) {
                        helper.setText(R.id.tv_title, data.getTitle());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<CallLogItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.intercom.FtCallLog.1.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, CallLogItem data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_empty_foot_15;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((FtLogBinding) this.mViewBinding).rv);
        ((FtLogBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((FtLogVM) this.mViewModel).callRefreshData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.FtCallLog$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtCallLog.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        if (list.size() > 0) {
            this.mAdapter.replaceData(list);
        } else {
            showEmpty();
        }
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        ((FtLogVM) this.mViewModel).getCallData();
    }
}