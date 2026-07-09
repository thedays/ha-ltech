package com.ltech.smarthome.ui.mode;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActModeBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ViewHelpUtil;

/* loaded from: classes4.dex */
public class ActMode extends VMActivity<ActModeBinding, ActModeVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.viewpager;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mode;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActModeBinding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActModeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActModeVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActModeVM) this.mViewModel).zoneNum = getIntent().getIntExtra(Constants.ZONE_NUM, 1);
        ((ActModeVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 1);
        ((ActModeVM) this.mViewModel).initList();
        if (((ActModeVM) this.mViewModel).fragmentList.size() == 1) {
            ((ActModeBinding) this.mViewBinding).tabs.setSelectedTabIndicatorColor(0);
        }
        ((ActModeBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.mode.ActMode.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActModeVM) ActMode.this.mViewModel).fragmentList.get(position).fragment;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActModeVM) ActMode.this.mViewModel).fragmentList.size();
            }
        });
        ((ActModeBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.mode.ActMode.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ActMode actMode = ActMode.this;
                ViewHelpUtil.createTabCustomView(actMode, tab, ((ActModeVM) actMode.mViewModel).fragmentList.get(tab.getPosition()).name, true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ActMode actMode = ActMode.this;
                ViewHelpUtil.createTabCustomView(actMode, tab, ((ActModeVM) actMode.mViewModel).fragmentList.get(tab.getPosition()).name, false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                ActMode actMode = ActMode.this;
                ViewHelpUtil.createTabCustomView(actMode, tab, ((ActModeVM) actMode.mViewModel).fragmentList.get(tab.getPosition()).name, true);
            }
        });
        if (((ActModeVM) this.mViewModel).fragmentList.size() > 1) {
            ((ActModeBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(((ActModeVM) this.mViewModel).fragmentList.size() - 1);
        }
        new TabLayoutMediator(((ActModeBinding) this.mViewBinding).tabs, ((ActModeBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.mode.ActMode$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActMode.this.lambda$initView$0(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(TabLayout.Tab tab, int i) {
        ViewHelpUtil.createTabCustomView(this, tab, ((ActModeVM) this.mViewModel).fragmentList.get(i).name, false);
    }
}