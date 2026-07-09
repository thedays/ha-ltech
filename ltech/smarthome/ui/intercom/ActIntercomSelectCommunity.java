package com.ltech.smarthome.ui.intercom;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomSelectCommunityBinding;
import com.ltech.smarthome.net.response.intercom.CommunityInfoResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIntercomSelectCommunity extends VMActivity<ActIntercomSelectCommunityBinding, ActIntercomLoginVM> {
    private BaseQuickAdapter<CommunityInfoResponse.CommunityInfo, BaseViewHolder> communityAdapter;
    private int curPosition = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_select_community;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.intercom));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.import_mode));
        ((ActIntercomLoginVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        initRv();
        ((ActIntercomLoginVM) this.mViewModel).initData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_community));
    }

    private void initRv() {
        ((ActIntercomSelectCommunityBinding) this.mViewBinding).rvCommunity.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActIntercomSelectCommunityBinding) this.mViewBinding).rvCommunity;
        BaseQuickAdapter<CommunityInfoResponse.CommunityInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CommunityInfoResponse.CommunityInfo, BaseViewHolder>(R.layout.item_select_list_white) { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSelectCommunity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, CommunityInfoResponse.CommunityInfo item) {
                helper.setText(R.id.tv_name, item.getLocation());
                if (ActIntercomSelectCommunity.this.curPosition == helper.getAdapterPosition()) {
                    helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel);
                } else {
                    helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_default);
                }
            }
        };
        this.communityAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.communityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSelectCommunity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActIntercomSelectCommunity.this.lambda$initRv$0(baseQuickAdapter2, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.curPosition = i;
        this.communityAdapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomLoginVM) this.mViewModel).showCommunityInfoListEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSelectCommunity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSelectCommunity.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        this.communityAdapter.setNewData(list);
        if (list.size() > 0) {
            setEditString(getString(R.string.import_mode));
        } else {
            setEditString("");
            showEmpty();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActIntercomLoginVM) this.mViewModel).showCommunityInfoListEvent.getValue().size() == 0) {
            return;
        }
        ((ActIntercomLoginVM) this.mViewModel).selectCommunity(((ActIntercomLoginVM) this.mViewModel).showCommunityInfoListEvent.getValue().get(this.curPosition).getID());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (helper.getCode() == 12) {
            dismissLoadingDialog();
            finishActivity();
            NavUtils.destination(ActIntercom.class).withLong(Constants.PLACE_ID, ((ActIntercomLoginVM) this.mViewModel).placeId).navigation(this);
        }
    }
}