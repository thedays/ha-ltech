package com.ltech.smarthome.ui.intercom;

import android.view.View;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomRecordBinding;
import com.ltech.smarthome.utils.ViewHelpUtil;

/* loaded from: classes4.dex */
public class ActIntercomRecord extends VMActivity<ActIntercomRecordBinding, ActIntercomRecordVM> {
    private int currentTab = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_record;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActIntercomRecordBinding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActIntercomRecordBinding) this.mViewBinding).ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomRecord$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActIntercomRecord.this.lambda$initView$0(view);
            }
        });
        TabLayout.Tab text = ((ActIntercomRecordBinding) this.mViewBinding).tabTitle.newTab().setText(R.string.open_door_log);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.open_door_log), false);
        ((ActIntercomRecordBinding) this.mViewBinding).tabTitle.addTab(text);
        ((ActIntercomRecordBinding) this.mViewBinding).tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomRecord.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActIntercomRecord.this, tab, tab.getText().toString(), true);
                if (tab.getPosition() != ActIntercomRecord.this.currentTab) {
                    ActIntercomRecord.this.changeFragment(tab.getPosition());
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActIntercomRecord.this, tab, tab.getText().toString(), false);
            }
        });
        ((ActIntercomRecordVM) this.mViewModel).initFragmentList();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ((ActIntercomRecordVM) this.mViewModel).fragmentList.get(0)).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeFragment(int tabPosition) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.hide(((ActIntercomRecordVM) this.mViewModel).fragmentList.get(this.currentTab));
        this.currentTab = tabPosition;
        if (((ActIntercomRecordVM) this.mViewModel).fragmentList.get(this.currentTab).isAdded()) {
            beginTransaction.show(((ActIntercomRecordVM) this.mViewModel).fragmentList.get(this.currentTab));
        } else {
            beginTransaction.add(R.id.fragment_container, ((ActIntercomRecordVM) this.mViewModel).fragmentList.get(this.currentTab));
        }
        beginTransaction.commit();
    }
}