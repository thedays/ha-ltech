package com.ltech.smarthome.ui.control;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtAutomationBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.automation.ActSortAutomation;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes4.dex */
public class FtAutomation extends VMFragment<FtAutomationBinding, FtAutomationVM> implements IIntelligence {
    private long deviceId;
    private boolean groupControl;

    static /* synthetic */ boolean lambda$showNoPermissionDialog$4(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected boolean isRootViewClickEnable() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_automation;
    }

    public static FtAutomation newInstance(long deviceId, boolean groupControl) {
        FtAutomation ftAutomation = new FtAutomation();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        ftAutomation.setArguments(bundle);
        return ftAutomation;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.groupControl = arguments.getBoolean(Constants.GROUP_CONTROL);
            this.deviceId = arguments.getLong("device_id");
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtAutomationBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((FtAutomationBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtAutomationBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtAutomationBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.control.FtAutomation$$ExternalSyntheticLambda5
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtAutomation.this.lambda$initView$0(refreshLayout);
            }
        });
        ((FtAutomationBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(R.string.app_str_search_automation_name);
        ((FtAutomationBinding) this.mViewBinding).searchBar.searchLayout.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.FtAutomation.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavUtils.destination(ActSearchAutomation.class).withLong(Constants.PLACE_ID, ((FtAutomationVM) FtAutomation.this.mViewModel).getCurrentPlace().getPlaceId()).navigation(FtAutomation.this.getActivity());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((FtAutomationVM) this.mViewModel).request != null) {
            Injection.limiter().reset(Injection.keyCreator().automationListKey(((FtAutomationVM) this.mViewModel).getCurrentPlace().getPlaceId()));
            ((FtAutomationVM) this.mViewModel).request.refresh();
        } else {
            queryAutomation();
        }
        ((FtAutomationBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.deviceId > 0) {
            queryAutomation();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        if (this.deviceId <= 0) {
            handleData(((FtAutomationVM) this.mViewModel).automationLiveData, new IAction() { // from class: com.ltech.smarthome.ui.control.FtAutomation$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtAutomation.this.initAdapterData((List) obj);
                }
            });
        }
        ((FtAutomationVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtAutomation$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAutomation.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAdapterData(List<Automation> automations) {
        if (automations.size() > 0) {
            showContent();
            ((FtAutomationVM) this.mViewModel).allData.clear();
            ((FtAutomationVM) this.mViewModel).allData.addAll(automations);
            ((FtAutomationVM) this.mViewModel).automationList.clear();
            ((FtAutomationVM) this.mViewModel).automationList.addAll(automations);
            ((FtAutomationBinding) this.mViewBinding).rvContent.getAdapter().notifyDataSetChanged();
            return;
        }
        showEmpty();
    }

    private void queryAutomation() {
        if (this.groupControl) {
            ((ObservableSubscribeProxy) Injection.net().queryGroupAutomation(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtAutomation$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtAutomation.this.lambda$queryAutomation$2((ListAutomationResponse) obj);
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().queryDeviceAutomation(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtAutomation$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtAutomation.this.lambda$queryAutomation$3((ListAutomationResponse) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryAutomation$2(ListAutomationResponse listAutomationResponse) throws Exception {
        initAdapterData(Injection.repo().auto().getAutomationListFromNet(listAutomationResponse));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryAutomation$3(ListAutomationResponse listAutomationResponse) throws Exception {
        initAdapterData(Injection.repo().auto().getAutomationListFromNet(listAutomationResponse));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
        if (((FtAutomationVM) this.mViewModel).request != null) {
            ((FtAutomationVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showEmpty() {
        super.showEmpty();
        ((FtAutomationVM) this.mViewModel).automationList.clear();
        ((FtAutomationVM) this.mViewModel).allData.clear();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showError() {
        super.showError();
        ((FtAutomationVM) this.mViewModel).automationList.clear();
        ((FtAutomationVM) this.mViewModel).allData.clear();
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSort() {
        if (((FtAutomationVM) this.mViewModel).getCurrentPlace() != null && (((FtAutomationVM) this.mViewModel).getCurrentPlace().isOwner() || ((FtAutomationVM) this.mViewModel).getCurrentPlace().isManager())) {
            NavUtils.destination(ActSortAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).navigation(this);
        } else {
            ((FtAutomationVM) this.mViewModel).showNoPermissionDialogEvent.call();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goAdd() {
        if (((FtAutomationVM) this.mViewModel).getCurrentPlace() != null) {
            if (((FtAutomationVM) this.mViewModel).getCurrentPlace().isOwner() || ((FtAutomationVM) this.mViewModel).getCurrentPlace().isManager()) {
                NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withString(Constants.AUTOMATION_NAME, ((FtAutomationVM) this.mViewModel).getAutomationName(getActivity())).navigation(this);
            } else {
                ((FtAutomationVM) this.mViewModel).showNoPermissionDialogEvent.call();
            }
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSearch() {
        NavUtils.destination(ActSearchAutomation.class).withLong(Constants.PLACE_ID, ((FtAutomationVM) this.mViewModel).getCurrentPlace().getPlaceId()).navigation(getActivity());
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(ActivityUtils.getTopActivity() instanceof ActIntelligence ? R.string.no_relate_automation : R.string.no_automation).emptyTryStringRes(R.string.add_group1));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        goAdd();
    }

    private void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtAutomation$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtAutomation.lambda$showNoPermissionDialog$4(baseDialog, view);
            }
        });
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        if (((FtAutomationVM) this.mViewModel).request != null) {
            ((FtAutomationVM) this.mViewModel).request.refresh();
        } else {
            queryAutomation();
        }
    }
}