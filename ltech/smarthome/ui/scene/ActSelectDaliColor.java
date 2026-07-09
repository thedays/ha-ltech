package com.ltech.smarthome.ui.scene;

import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.FragmentUtils;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectDaliColorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroupVM;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.dalipro.FtCtLight;
import com.ltech.smarthome.ui.device.dalipro.FtDimLight;
import com.ltech.smarthome.ui.device.dalipro.FtRgbLight;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectDaliColor extends VMActivity<ActSelectDaliColorBinding, ActDaliLightOrGroupVM> {
    private Fragment curFragment;
    private List<Fragment> mFragmentList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_dali_color;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_color));
        setEditString(getString(R.string.confirm));
        ((ActDaliLightOrGroupVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDaliLightOrGroupVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActDaliLightOrGroupVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActDaliLightOrGroupVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActDaliLightOrGroupVM) this.mViewModel).isLocalScene = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        ((ActDaliLightOrGroupVM) this.mViewModel).selectAction = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
        if (SceneHelper.instance().bindingType == 0 || ((ActDaliLightOrGroupVM) this.mViewModel).lightType == 10009) {
            if (ProductRepository.isDaliLightGroup(((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue())) {
                ((ActDaliLightOrGroupVM) this.mViewModel).lightType = DaliProHelper.convertDaliType(((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue());
            }
            initFragment((Role) ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue());
            initTabLayout((Role) ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue());
        } else {
            initFragmentByType();
        }
        if (this.mFragmentList.size() <= 0 || !(this.mFragmentList.get(0) instanceof FtDimLight)) {
            return;
        }
        setTitle(getString(R.string.select_brightness));
    }

    private void initFragment(Role role) {
        this.mFragmentList.clear();
        if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl) {
            if (DaliProHelper.isSupportDim(role)) {
                this.mFragmentList.add(FtDimLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            }
            if (DaliProHelper.isSupportCt(role)) {
                this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            }
            if (DaliProHelper.isSupportColor(role)) {
                this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            }
        } else {
            int i = ((ActDaliLightOrGroupVM) this.mViewModel).lightType;
            if (i == 1) {
                this.mFragmentList.add(FtDimLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            } else if (i == 2) {
                this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            } else if (i == 3) {
                this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            } else if (i == 10009) {
                ((ActDaliLightOrGroupVM) this.mViewModel).isBroadcast = true;
                this.mFragmentList.add(FtDimLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
                this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
                this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
            }
        }
        if (this.mFragmentList.size() == 0) {
            this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        }
        FragmentUtils.removeAll(getSupportFragmentManager());
        FragmentUtils.add(getSupportFragmentManager(), this.mFragmentList, R.id.fragment_container, 0);
    }

    private void initTabLayout(Role role) {
        if (this.mFragmentList.size() <= 1) {
            ((ActSelectDaliColorBinding) this.mViewBinding).layoutTab.setVisibility(8);
            return;
        }
        ((ActSelectDaliColorBinding) this.mViewBinding).layoutTab.setVisibility(0);
        ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliColor.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                switch (((Integer) tab.getTag()).intValue()) {
                    case R.layout.custom_tab_color /* 2131493237 */:
                        ActSelectDaliColor actSelectDaliColor = ActSelectDaliColor.this;
                        actSelectDaliColor.setTitle(actSelectDaliColor.getString(R.string.select_color));
                        ActSelectDaliColor.this.showCurrentFragment(3);
                        break;
                    case R.layout.custom_tab_ct /* 2131493238 */:
                        ActSelectDaliColor actSelectDaliColor2 = ActSelectDaliColor.this;
                        actSelectDaliColor2.setTitle(actSelectDaliColor2.getString(R.string.select_color));
                        ActSelectDaliColor.this.showCurrentFragment(2);
                        break;
                    case R.layout.custom_tab_dim /* 2131493239 */:
                        ActSelectDaliColor actSelectDaliColor3 = ActSelectDaliColor.this;
                        actSelectDaliColor3.setTitle(actSelectDaliColor3.getString(R.string.select_brightness));
                        ActSelectDaliColor.this.showCurrentFragment(1);
                        break;
                }
                ((ActDaliLightOrGroupVM) ActSelectDaliColor.this.mViewModel).controlObject.setValue(((ActDaliLightOrGroupVM) ActSelectDaliColor.this.mViewModel).controlObject.getValue());
            }
        });
        ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.removeAllTabs();
        if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl) {
            if (DaliProHelper.isSupportDim(role)) {
                ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addTab(((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_dim)).setCustomView(R.layout.custom_tab_dim));
            }
            if (DaliProHelper.isSupportCt(role)) {
                ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addTab(((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_ct)).setCustomView(R.layout.custom_tab_ct));
            }
            if (DaliProHelper.isSupportColor(role)) {
                ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addTab(((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_color)).setCustomView(R.layout.custom_tab_color));
            }
        } else if (((ActDaliLightOrGroupVM) this.mViewModel).isBroadcast) {
            ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addTab(((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_dim)).setCustomView(R.layout.custom_tab_dim));
            ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addTab(((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_ct)).setCustomView(R.layout.custom_tab_ct));
            ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.addTab(((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_color)).setCustomView(R.layout.custom_tab_color));
        }
        int fragmentByType = getFragmentByType(2);
        int i = fragmentByType != -1 ? fragmentByType : 0;
        this.curFragment = this.mFragmentList.get(i);
        FragmentUtils.showHide(i, this.mFragmentList);
        if (((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.getTabCount() != 0) {
            ((ActSelectDaliColorBinding) this.mViewBinding).bottomTabLayout.getTabAt(i).select();
        }
    }

    private void initFragmentByType() {
        this.mFragmentList.clear();
        if (((ActDaliLightOrGroupVM) this.mViewModel).lightType == 1) {
            this.mFragmentList.add(FtDimLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        } else if (((ActDaliLightOrGroupVM) this.mViewModel).lightType == 2) {
            this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        } else if (((ActDaliLightOrGroupVM) this.mViewModel).lightType == 3) {
            this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        }
        if (this.mFragmentList.size() == 0) {
            this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        }
        FragmentUtils.removeAll(getSupportFragmentManager());
        FragmentUtils.add(getSupportFragmentManager(), this.mFragmentList, R.id.fragment_container, 0);
        this.curFragment = this.mFragmentList.get(0);
        ((ActSelectDaliColorBinding) this.mViewBinding).layoutTab.setVisibility(8);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.mFragmentList.size() == 1 && SceneHelper.instance().bindingType == 0) {
            ((ActDaliLightOrGroupVM) this.mViewModel).saveDataWithType(((ActDaliLightOrGroupVM) this.mViewModel).lightType);
            return;
        }
        if (((ActDaliLightOrGroupVM) this.mViewModel).isBroadcast && SceneHelper.instance().bindingType == 1) {
            Fragment fragment = this.curFragment;
            if (fragment instanceof FtDimLight) {
                ((ActDaliLightOrGroupVM) this.mViewModel).saveData(16);
                return;
            } else if (fragment instanceof FtCtLight) {
                ((ActDaliLightOrGroupVM) this.mViewModel).saveData(32);
                return;
            } else {
                ((ActDaliLightOrGroupVM) this.mViewModel).saveData(48);
                return;
            }
        }
        Fragment fragment2 = this.curFragment;
        if (fragment2 instanceof FtDimLight) {
            ((ActDaliLightOrGroupVM) this.mViewModel).saveDataWithType(1);
        } else if (fragment2 instanceof FtCtLight) {
            ((ActDaliLightOrGroupVM) this.mViewModel).saveDataWithType(2);
        } else {
            ((ActDaliLightOrGroupVM) this.mViewModel).saveDataWithType(3);
        }
    }

    private void startObjectObserve() {
        if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActDaliLightOrGroupVM) this.mViewModel).controlId);
            if (groupById != null) {
                ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.setValue(groupById);
                return;
            }
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActDaliLightOrGroupVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.setValue(deviceById);
        }
    }

    private int getFragmentByType(int type) {
        for (int i = 0; i < this.mFragmentList.size(); i++) {
            if (type == 1 && (this.mFragmentList.get(i) instanceof FtDimLight)) {
                setTitle(getString(R.string.select_brightness));
                return i;
            }
            if (type == 2 && (this.mFragmentList.get(i) instanceof FtCtLight)) {
                setTitle(getString(R.string.select_color));
                return i;
            }
            if (type == 3 && (this.mFragmentList.get(i) instanceof FtRgbLight)) {
                setTitle(getString(R.string.select_color));
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCurrentFragment(int type) {
        int fragmentByType = getFragmentByType(type);
        if (fragmentByType == -1) {
            fragmentByType = 0;
        }
        this.curFragment = this.mFragmentList.get(fragmentByType);
        FragmentUtils.showHide(fragmentByType, this.mFragmentList);
    }
}