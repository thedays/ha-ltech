package com.ltech.smarthome.ui.device.spicontroller;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectSpiForActionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.smart.product_agreement.param.ModeCmdParam;

/* loaded from: classes4.dex */
public class ActSelectSpiForAction extends VMActivity<ActSelectSpiForActionBinding, ActSpiControllerVM> implements ISelectAction {
    private boolean isLocal;
    private int selectTab = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_spi_for_action;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        ((ActSpiControllerVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSpiControllerVM) this.mViewModel).initTabListForAction();
        initViewpager();
        ((ActSpiControllerVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActSpiControllerVM) this.mViewModel).controlId));
        ((ActSpiControllerVM) this.mViewModel).selectAction = true;
    }

    private void initViewpager() {
        ((ActSelectSpiForActionBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).tabContentList.get(position).getFragment();
            }

            @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).tabContentList.size();
            }
        });
        ((ActSelectSpiForActionBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction.2
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ActSelectSpiForAction.this.selectTab = tab.getPosition();
                ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).selectFragment = ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).tabContentList.get(tab.getPosition()).getFragment();
                BaseNormalActivity baseNormalActivity = ActSelectSpiForAction.this.activity;
                ActSelectSpiForAction actSelectSpiForAction = ActSelectSpiForAction.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actSelectSpiForAction.getString(((ActSpiControllerVM) actSelectSpiForAction.mViewModel).tabContentList.get(tab.getPosition()).getTitleRes()), true);
                if (ActSelectSpiForAction.this.selectTab == 0) {
                    ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).isPlayList = false;
                    ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).queryModeInfo();
                } else if (ActSelectSpiForAction.this.selectTab == 1) {
                    ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).isPlayList = true;
                    ((ActSpiControllerVM) ActSelectSpiForAction.this.mViewModel).queryPlayListInfo();
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActSelectSpiForAction.this.activity;
                ActSelectSpiForAction actSelectSpiForAction = ActSelectSpiForAction.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actSelectSpiForAction.getString(((ActSpiControllerVM) actSelectSpiForAction.mViewModel).tabContentList.get(tab.getPosition()).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActSelectSpiForAction.this.activity;
                ActSelectSpiForAction actSelectSpiForAction = ActSelectSpiForAction.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actSelectSpiForAction.getString(((ActSpiControllerVM) actSelectSpiForAction.mViewModel).tabContentList.get(tab.getPosition()).getTitleRes()), true);
            }
        });
        new TabLayoutMediator(((ActSelectSpiForActionBinding) this.mViewBinding).tabs, ((ActSelectSpiForActionBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActSelectSpiForAction.this.lambda$initViewpager$0(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewpager$0(TabLayout.Tab tab, int i) {
        ViewHelpUtil.createTabCustomView(this.activity, tab, getString(((ActSpiControllerVM) this.mViewModel).tabContentList.get(i).getTitleRes()), false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            int i = this.selectTab;
            if (i == 0) {
                if (((ActSpiControllerVM) this.mViewModel).selectModeNum == 0) {
                    ToastUtils.showShort(R.string.please_choose);
                    return;
                } else {
                    lambda$edit$1(this);
                    ComboCmdHelper.getInstance().selectSpiMode(((ActSpiControllerVM) this.mViewModel).selectModeNum, false);
                    return;
                }
            }
            if (i == 1) {
                if (((ActSpiControllerVM) this.mViewModel).selectPlayList == 0) {
                    ToastUtils.showShort(R.string.please_choose);
                    return;
                } else {
                    lambda$edit$1(this);
                    ComboCmdHelper.getInstance().selectSpiMode(((ActSpiControllerVM) this.mViewModel).selectPlayList, true);
                    return;
                }
            }
            return;
        }
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setCmdType(16);
        modeCmdParam.setPlayType(5);
        int i2 = this.selectTab;
        if (i2 == 0) {
            if (((ActSpiControllerVM) this.mViewModel).selectModeNum == 0) {
                ToastUtils.showShort(R.string.please_choose);
                return;
            }
            modeCmdParam.setModeNum(((ActSpiControllerVM) this.mViewModel).selectModeNum);
            modeCmdParam.setListPlay(false);
            modeCmdParam.addExtParam(SceneHelper.OPTION, "10");
            modeCmdParam.addExtParam(SceneHelper.OPTION_VALUE, String.valueOf(((ActSpiControllerVM) this.mViewModel).selectModeNum));
        } else if (i2 == 1) {
            if (((ActSpiControllerVM) this.mViewModel).selectPlayList == 0) {
                ToastUtils.showShort(R.string.please_choose);
                return;
            }
            modeCmdParam.setModeNum(((ActSpiControllerVM) this.mViewModel).selectPlayList);
            modeCmdParam.setListPlay(true);
            modeCmdParam.addExtParam(SceneHelper.OPTION, "11");
            modeCmdParam.addExtParam(SceneHelper.OPTION_VALUE, getString(R.string.play_list));
        }
        SceneHelper.instance().cmdParam = modeCmdParam;
        if (this.isLocal) {
            setResult(3001);
            finishActivity();
        } else {
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectSpiForAction.this.lambda$edit$1((Boolean) obj);
                }
            });
        }
    }

    public boolean isGq() {
        return getIntent().getBooleanExtra(Constants.IS_GQ, false);
    }
}