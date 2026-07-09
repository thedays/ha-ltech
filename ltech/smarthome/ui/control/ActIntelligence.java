package com.ltech.smarthome.ui.control;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntelligenceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ViewHelpUtil;
import java.util.HashSet;

/* loaded from: classes4.dex */
public class ActIntelligence extends VMActivity<ActIntelligenceBinding, FtIntelligenceVM> {
    private HashSet<Long> createIds = new HashSet<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intelligence;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((FtIntelligenceVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((FtIntelligenceVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (((FtIntelligenceVM) this.mViewModel).deviceId > 0 && !((FtIntelligenceVM) this.mViewModel).groupControl) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(((FtIntelligenceVM) this.mViewModel).deviceId);
            ((FtIntelligenceVM) this.mViewModel).initTabList(deviceByDeviceId.getProductId());
            if (((FtIntelligenceVM) this.mViewModel).onlyShowAutomation(deviceByDeviceId.getProductId())) {
                ((ActIntelligenceBinding) this.mViewBinding).tabs.setSelectedTabIndicatorColor(0);
            }
        } else {
            ((FtIntelligenceVM) this.mViewModel).initTabList();
        }
        this.createIds.clear();
        ((ActIntelligenceBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.control.ActIntelligence.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtIntelligenceVM) ActIntelligence.this.mViewModel).getTabContentList().get(position).getFragment();
            }

            @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtIntelligenceVM) ActIntelligence.this.mViewModel).getTabContentList().size();
            }
        });
        ((ActIntelligenceBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.control.ActIntelligence.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ((FtIntelligenceVM) ActIntelligence.this.mViewModel).selectPosition = tab.getPosition();
                ((FtIntelligenceVM) ActIntelligence.this.mViewModel).selectFragment = ((FtIntelligenceVM) ActIntelligence.this.mViewModel).getTabContentList().get(tab.getPosition()).getFragment();
                BaseNormalActivity baseNormalActivity = ActIntelligence.this.activity;
                ActIntelligence actIntelligence = ActIntelligence.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actIntelligence.getString(((FtIntelligenceVM) actIntelligence.mViewModel).getTabContentList().get(tab.getPosition()).getTitleRes()), true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActIntelligence.this.activity;
                ActIntelligence actIntelligence = ActIntelligence.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actIntelligence.getString(((FtIntelligenceVM) actIntelligence.mViewModel).getTabContentList().get(tab.getPosition()).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActIntelligence.this.activity;
                ActIntelligence actIntelligence = ActIntelligence.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actIntelligence.getString(((FtIntelligenceVM) actIntelligence.mViewModel).getTabContentList().get(tab.getPosition()).getTitleRes()), true);
            }
        });
        new TabLayoutMediator(((ActIntelligenceBinding) this.mViewBinding).tabs, ((ActIntelligenceBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.control.ActIntelligence$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActIntelligence.this.lambda$initView$0(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(TabLayout.Tab tab, int i) {
        ViewHelpUtil.createTabCustomView(this.activity, tab, getString(((FtIntelligenceVM) this.mViewModel).getTabContentList().get(i).getTitleRes()), false);
    }
}