package com.ltech.smarthome.ui.home;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActHomeManageBinding;
import com.ltech.smarthome.model.Injection;
import java.util.List;

/* loaded from: classes4.dex */
public class ActHomeManage extends VMActivity<ActHomeManageBinding, ActHomeManageVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_manage;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.home_manage));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActHomeManageVM) this.mViewModel).mPlaceListing = Injection.repo().home().getPlaceList(this);
        handleData(((ActHomeManageVM) this.mViewModel).mPlaceListing, new IAction() { // from class: com.ltech.smarthome.ui.home.ActHomeManage$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeManage.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        ((ActHomeManageVM) this.mViewModel).mObservableList.clear();
        ((ActHomeManageVM) this.mViewModel).mObservableList.addAll(list);
        ((ActHomeManageBinding) this.mViewBinding).layoutLoad.scrollToPosition(0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActHomeManageVM) this.mViewModel).mPlaceListing.retry();
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        ((ActHomeManageVM) this.mViewModel).mPlaceListing.refresh();
    }
}