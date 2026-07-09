package com.ltech.smarthome.ui.control;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtIntelligenceBinding;
import com.ltech.smarthome.utils.ViewHelpUtil;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: classes4.dex */
public class FtIntelligence extends VMFragment<FtIntelligenceBinding, FtIntelligenceVM> {
    private HashSet<Long> createIds = new HashSet<>();
    private ArrayList<Long> ids = new ArrayList<Long>() { // from class: com.ltech.smarthome.ui.control.FtIntelligence.1
        {
            add(1L);
            add(2L);
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_intelligence;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtIntelligenceVM) this.mViewModel).initTabList();
        this.createIds.clear();
        ((FtIntelligenceBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.control.FtIntelligence.2
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtIntelligenceVM) FtIntelligence.this.mViewModel).getTabContentList().get(position).getFragment();
            }

            @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtIntelligenceVM) FtIntelligence.this.mViewModel).getTabContentList().size();
            }
        });
        ((FtIntelligenceBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.control.FtIntelligence.3
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ((FtIntelligenceVM) FtIntelligence.this.mViewModel).selectPosition = tab.getPosition();
                ((FtIntelligenceVM) FtIntelligence.this.mViewModel).selectFragment = ((FtIntelligenceVM) FtIntelligence.this.mViewModel).getTabContentList().get(tab.getPosition()).getFragment();
                FragmentActivity activity = FtIntelligence.this.getActivity();
                FtIntelligence ftIntelligence = FtIntelligence.this;
                ViewHelpUtil.createTabCustomView(activity, tab, ftIntelligence.getString(((FtIntelligenceVM) ftIntelligence.mViewModel).getTabContentList().get(tab.getPosition()).getTitleRes()), true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                FragmentActivity activity = FtIntelligence.this.getActivity();
                FtIntelligence ftIntelligence = FtIntelligence.this;
                ViewHelpUtil.createTabCustomView(activity, tab, ftIntelligence.getString(((FtIntelligenceVM) ftIntelligence.mViewModel).getTabContentList().get(tab.getPosition()).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                FragmentActivity activity = FtIntelligence.this.getActivity();
                FtIntelligence ftIntelligence = FtIntelligence.this;
                ViewHelpUtil.createTabCustomView(activity, tab, ftIntelligence.getString(((FtIntelligenceVM) ftIntelligence.mViewModel).getTabContentList().get(tab.getPosition()).getTitleRes()), true);
            }
        });
        new TabLayoutMediator(((FtIntelligenceBinding) this.mViewBinding).tabs, ((FtIntelligenceBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.control.FtIntelligence$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                FtIntelligence.this.lambda$initView$0(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(TabLayout.Tab tab, int i) {
        ViewHelpUtil.createTabCustomView(getActivity(), tab, getString(((FtIntelligenceVM) this.mViewModel).getTabContentList().get(i).getTitleRes()), false);
    }
}