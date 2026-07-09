package com.ltech.smarthome.ui.device.musicplayer;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActBleMusicPlayerBinding;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ViewHelpUtil;

/* loaded from: classes4.dex */
public class ActBleMusicPlayer extends BaseControlActivity<ActBleMusicPlayerBinding, ActBleMusicPlayerVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_music_player;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActBleMusicPlayerVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        initViewPager();
        ((ActBleMusicPlayerVM) this.mViewModel).loadDevice();
    }

    private void initViewPager() {
        ((ActBleMusicPlayerVM) this.mViewModel).initTabList();
        ((ActBleMusicPlayerBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.musicplayer.ActBleMusicPlayer.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActBleMusicPlayerVM) ActBleMusicPlayer.this.mViewModel).tabs.get(position).getFragment();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActBleMusicPlayerVM) ActBleMusicPlayer.this.mViewModel).tabs.size();
            }
        });
        ((ActBleMusicPlayerBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActBleMusicPlayer.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActBleMusicPlayer.this.activity;
                ActBleMusicPlayer actBleMusicPlayer = ActBleMusicPlayer.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actBleMusicPlayer.getString(((ActBleMusicPlayerVM) actBleMusicPlayer.mViewModel).tabs.get(tab.getPosition()).getTitleRes()), true);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActBleMusicPlayer.this.activity;
                ActBleMusicPlayer actBleMusicPlayer = ActBleMusicPlayer.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actBleMusicPlayer.getString(((ActBleMusicPlayerVM) actBleMusicPlayer.mViewModel).tabs.get(tab.getPosition()).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActBleMusicPlayer.this.activity;
                ActBleMusicPlayer actBleMusicPlayer = ActBleMusicPlayer.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actBleMusicPlayer.getString(((ActBleMusicPlayerVM) actBleMusicPlayer.mViewModel).tabs.get(tab.getPosition()).getTitleRes()), true);
            }
        });
        new TabLayoutMediator(((ActBleMusicPlayerBinding) this.mViewBinding).tabs, ((ActBleMusicPlayerBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.musicplayer.ActBleMusicPlayer$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActBleMusicPlayer.this.lambda$initViewPager$0(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$0(TabLayout.Tab tab, int i) {
        ViewHelpUtil.createTabCustomView(this.activity, tab, getString(((ActBleMusicPlayerVM) this.mViewModel).tabs.get(i).getTitleRes()), false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
    }
}