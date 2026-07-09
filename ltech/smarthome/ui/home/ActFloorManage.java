package com.ltech.smarthome.ui.home;

import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActFloorManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActFloorManage extends VMActivity<ActFloorManageBinding, ActFloorManageVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_floor_manage;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_floor).emptyTryStringRes(R.string.add_floor));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.floor_manage));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActFloorManageVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActFloorManageVM) this.mViewModel).mFloorListing = Injection.repo().home().getFloorList(this, ((ActFloorManageVM) this.mViewModel).placeId);
        handleData(((ActFloorManageVM) this.mViewModel).mFloorListing, new IAction() { // from class: com.ltech.smarthome.ui.home.ActFloorManage$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActFloorManage.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        ((ActFloorManageVM) this.mViewModel).mObservableList.clear();
        ((ActFloorManageVM) this.mViewModel).mObservableList.addAll(list);
        if (((ActFloorManageVM) this.mViewModel).mObservableList.size() > 1) {
            setEditImage(R.mipmap.ic_sort);
        } else {
            setEditImage(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        setEditImage(0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(ActSortFloor.class).withLong(Constants.PLACE_ID, ((ActFloorManageVM) this.mViewModel).placeId).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onEmptyTry() {
        super.onEmptyTry();
        NavUtils.destination(ActAddFloor.class).withLong(Constants.PLACE_ID, ((ActFloorManageVM) this.mViewModel).placeId).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActFloorManageVM) this.mViewModel).mFloorListing.retry();
    }
}