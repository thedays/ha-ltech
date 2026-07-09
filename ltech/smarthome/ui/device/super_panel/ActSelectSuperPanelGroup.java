package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseGroupManageActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActGroupManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelGroup extends BaseGroupManageActivity<ActGroupManageBinding, ActSelectSuperPanelGroupVM> {
    private boolean selectAll;

    @Override // com.ltech.smarthome.base.BaseGroupManageActivity
    protected boolean filterGroup(Group group) {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseGroupManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.all_device));
        ((ActGroupManageBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActGroupManageBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{0}));
        ((DefaultItemAnimator) ((ActGroupManageBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.GROUP_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                ((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList.add(Long.valueOf(j));
            }
        }
        ((ActGroupManageBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroup$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectSuperPanelGroup.this.lambda$initView$2((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelGroup(getIntent().getLongExtra("device_id", -1L), ((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroup$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSuperPanelGroup.this.lambda$initView$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroup$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectSuperPanelGroup.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroup$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSuperPanelGroup.this.lambda$initView$1((SetSuperPanelResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(SetSuperPanelResponse setSuperPanelResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (setSuperPanelResponse.getGroups() != null) {
            Iterator<SetSuperPanelResponse.GroupsBean> it = setSuperPanelResponse.getGroups().iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().getGroupid()));
            }
        }
        Injection.repo().device().setSuperPanelGroup(setSuperPanelResponse.getInfo().getPanelid(), arrayList);
        SmartToast.showShort(getString(R.string.save_success));
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseGroupManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSelectSuperPanelGroupVM) this.mViewModel).selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroup$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectSuperPanelGroup.this.lambda$startObserve$3((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        ((ActGroupManageBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{num}));
        this.selectAll = true;
        Iterator<Group> it = ((ActSelectSuperPanelGroupVM) this.mViewModel).mGroupList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (!((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList.contains(Long.valueOf(it.next().getGroupId()))) {
                this.selectAll = false;
                break;
            }
        }
        if (this.selectAll) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        this.selectAll = !this.selectAll;
        if (((ActSelectSuperPanelGroupVM) this.mViewModel).mGroupList.size() > 0) {
            if (this.selectAll) {
                for (Group group : ((ActSelectSuperPanelGroupVM) this.mViewModel).mGroupList) {
                    if (!((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList.contains(Long.valueOf(group.getGroupId()))) {
                        ((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList.add(Long.valueOf(group.getGroupId()));
                    }
                }
            } else {
                ((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList.clear();
            }
            ((ActGroupManageBinding) this.mViewBinding).rvContent.getAdapter().notifyDataSetChanged();
        }
        ((ActSelectSuperPanelGroupVM) this.mViewModel).selectCountLiveData.setValue(Integer.valueOf(((ActSelectSuperPanelGroupVM) this.mViewModel).selectGroupIdList.size()));
    }
}