package com.ltech.smarthome.ui.my;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActMessageCenterBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.my.ActMessageCenterVM;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActMessageCenter extends VMActivity<ActMessageCenterBinding, ActMessageCenterVM> {
    static /* synthetic */ boolean lambda$showClearMessageDialog$3(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_message_center;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_notify));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.app_str_clear));
        initViewPager(false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        showClearMessageDialog(((ActMessageCenterBinding) this.mViewBinding).tabs.getSelectedTabPosition());
    }

    private void showClearMessageDialog(final int selectedTabPosition) {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_clear_data_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showClearMessageDialog$2;
                lambda$showClearMessageDialog$2 = ActMessageCenter.this.lambda$showClearMessageDialog$2(selectedTabPosition, baseDialog, view);
                return lambda$showClearMessageDialog$2;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActMessageCenter.lambda$showClearMessageDialog$3(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showClearMessageDialog$2(final int i, BaseDialog baseDialog, View view) {
        if (i == 0) {
            ((ActMessageCenterVM) this.mViewModel).messageType = 2;
        } else if (i == 1) {
            ((ActMessageCenterVM) this.mViewModel).messageType = 1;
        }
        ((ObservableSubscribeProxy) Injection.net().deleteMessage(((ActMessageCenterVM) this.mViewModel).messageType).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMessageCenter.this.lambda$showClearMessageDialog$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActMessageCenter.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMessageCenter.this.lambda$showClearMessageDialog$1(i, obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showClearMessageDialog$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showClearMessageDialog$1(int i, Object obj) throws Exception {
        ((IMessageCenter) ((ActMessageCenterVM) this.mViewModel).tabContents.get(i).getFragment()).clearData();
    }

    private void initViewPager(boolean dataChange) {
        if (dataChange || ((ActMessageCenterBinding) this.mViewBinding).viewpager.getAdapter() == null) {
            ((ActMessageCenterVM) this.mViewModel).initTabList();
            ((ActMessageCenterBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.my.ActMessageCenter.1
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter
                public Fragment createFragment(int position) {
                    return ((ActMessageCenterVM) ActMessageCenter.this.mViewModel).tabContents.get(position).getFragment();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return ((ActMessageCenterVM) ActMessageCenter.this.mViewModel).tabContents.size();
                }
            });
            ((ActMessageCenterBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
            ((ActMessageCenterBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter.2
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    ActMessageCenter actMessageCenter = ActMessageCenter.this;
                    actMessageCenter.changeTab(tab, ((ActMessageCenterVM) actMessageCenter.mViewModel).tabContents.get(tab.getPosition()), true);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    ActMessageCenter actMessageCenter = ActMessageCenter.this;
                    actMessageCenter.changeTab(tab, ((ActMessageCenterVM) actMessageCenter.mViewModel).tabContents.get(tab.getPosition()), false);
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                    ActMessageCenter actMessageCenter = ActMessageCenter.this;
                    actMessageCenter.changeTab(tab, ((ActMessageCenterVM) actMessageCenter.mViewModel).tabContents.get(tab.getPosition()), true);
                }
            });
            ((ActMessageCenterBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
            new TabLayoutMediator(((ActMessageCenterBinding) this.mViewBinding).tabs, ((ActMessageCenterBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.my.ActMessageCenter$$ExternalSyntheticLambda5
                @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
                public final void onConfigureTab(TabLayout.Tab tab, int i) {
                    ActMessageCenter.this.lambda$initViewPager$4(tab, i);
                }
            }).attach();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$4(TabLayout.Tab tab, int i) {
        changeTab(tab, ((ActMessageCenterVM) this.mViewModel).tabContents.get(i), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTab(TabLayout.Tab tab, ActMessageCenterVM.TabContent tabContent, boolean select) {
        tab.setText(tabContent.getTitleRes());
    }
}