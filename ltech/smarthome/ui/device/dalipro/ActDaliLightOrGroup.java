package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliLightBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDaliLightOrGroup extends VMActivity<ActDaliLightBinding, ActDaliLightOrGroupVM> {
    private Fragment curFragment;
    private List<Fragment> mFragmentList = new ArrayList();
    private boolean isFirstSetOpenLight = true;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_light;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActDaliLightOrGroupVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDaliLightOrGroupVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActDaliLightOrGroupVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
        ((ActDaliLightOrGroupVM) this.mViewModel).lightType = DaliProHelper.convertDaliType(((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue());
        initFragment();
        initTabLayout();
    }

    private void initFragment() {
        this.mFragmentList.clear();
        int i = ((ActDaliLightOrGroupVM) this.mViewModel).lightType;
        if (i == 1) {
            this.mFragmentList.add(FtDimLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        } else if (i == 2) {
            this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        } else if (i == 3) {
            this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        } else if (i == 5) {
            if (DaliProHelper.isSupportDim((Role) ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue())) {
                this.mFragmentList.add(FtDimLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, true));
                this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, true));
                this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, true));
            } else {
                this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, true));
                this.mFragmentList.add(FtRgbLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, true));
            }
        }
        if (this.mFragmentList.size() == 0) {
            this.mFragmentList.add(FtCtLight.newInstance(((ActDaliLightOrGroupVM) this.mViewModel).deviceId, ((ActDaliLightOrGroupVM) this.mViewModel).controlId, ((ActDaliLightOrGroupVM) this.mViewModel).groupControl));
        }
        FragmentUtils.removeAll(getSupportFragmentManager());
        FragmentUtils.add(getSupportFragmentManager(), this.mFragmentList, R.id.fragment_container, 0);
    }

    private void initTabLayout() {
        ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup.1
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
                        ActDaliLightOrGroup.this.showCurrentFragment(3);
                        break;
                    case R.layout.custom_tab_ct /* 2131493238 */:
                        ActDaliLightOrGroup.this.showCurrentFragment(2);
                        break;
                    case R.layout.custom_tab_dim /* 2131493239 */:
                        ActDaliLightOrGroup.this.showCurrentFragment(1);
                        break;
                }
                ((ActDaliLightOrGroupVM) ActDaliLightOrGroup.this.mViewModel).brtProgress.setValue(Integer.valueOf(LightUtils.brt2PercentHasBelowZero(((ActDaliLightOrGroupVM) ActDaliLightOrGroup.this.mViewModel).brt)));
            }
        });
        ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.removeAllTabs();
        if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl && ((ActDaliLightOrGroupVM) this.mViewModel).lightType == 5) {
            if (DaliProHelper.isSupportDim((Role) ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue())) {
                ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.addTab(((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_dim)).setCustomView(R.layout.custom_tab_dim));
                ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.addTab(((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_ct)).setCustomView(R.layout.custom_tab_ct));
                ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.addTab(((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_color)).setCustomView(R.layout.custom_tab_color));
            } else {
                ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.addTab(((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_ct)).setCustomView(R.layout.custom_tab_ct));
                ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.addTab(((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.newTab().setTag(Integer.valueOf(R.layout.custom_tab_color)).setCustomView(R.layout.custom_tab_color));
            }
        }
        int fragmentByType = getFragmentByType(2);
        if (fragmentByType == -1) {
            fragmentByType = 0;
        }
        this.curFragment = this.mFragmentList.get(fragmentByType);
        FragmentUtils.showHide(fragmentByType, this.mFragmentList);
        if (((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.getTabCount() != 0) {
            ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.getTabAt(fragmentByType).select();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl) {
            NavUtils.destination(ActDaliLightGroupSetting.class).withLong("device_id", ((ActDaliLightOrGroupVM) this.mViewModel).deviceId).withLong(Constants.CONTROL_ID, ((ActDaliLightOrGroupVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ((ActDaliLightOrGroupVM) this.mViewModel).lightType).navigation(ActivityUtils.getTopActivity());
        } else {
            NavUtils.destination(ActDaliLightSetting.class).withLong("device_id", ((ActDaliLightOrGroupVM) this.mViewModel).deviceId).withLong(Constants.CONTROL_ID, ((ActDaliLightOrGroupVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ((ActDaliLightOrGroupVM) this.mViewModel).lightType).navigation(ActivityUtils.getTopActivity());
        }
    }

    public void onFragmentCreated() {
        boolean isOn;
        if (this.isFirstSetOpenLight) {
            if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl) {
                isOn = ((Group) ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue()).getGroupState().isOn();
            } else {
                isOn = ((Device) ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.getValue()).getDeviceState().isOn();
            }
            ((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.setValue(Boolean.valueOf(isOn));
            this.isFirstSetOpenLight = false;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDaliLightOrGroupVM) this.mViewModel).openOrCloseEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightOrGroup.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightOrGroup.this.lambda$startObserve$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r3) {
        ((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.setValue(Boolean.valueOf(Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue())));
        ((ActDaliLightOrGroupVM) this.mViewModel).getLightCmdHelper().sendOnOff(this, ((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue().booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        setTabStats();
        ((ActDaliLightOrGroupVM) this.mViewModel).stateOn.setValue(bool);
    }

    private void setTabStats() {
        View customView;
        for (int i = 0; i < 3; i++) {
            if (((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.getTabAt(i) != null && (customView = ((ActDaliLightBinding) this.mViewBinding).bottomTabLayout.getTabAt(i).getCustomView()) != null) {
                customView.setEnabled(Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()));
                customView.setClickable(Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()));
                ImageView imageView = (ImageView) customView.findViewById(R.id.dali_tab_iv);
                imageView.setAlpha(Boolean.TRUE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()) ? 1.0f : 0.5f);
                if (i == 0) {
                    imageView.setBackground(getDrawable(Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()) ? R.drawable.selector_dali_dim_without_press : R.drawable.selector_dali_dim));
                } else if (i == 1) {
                    imageView.setBackground(getDrawable(Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()) ? R.drawable.selector_dali_ct_without_press : R.drawable.selector_dali_ct));
                } else {
                    imageView.setBackground(getDrawable(Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()) ? R.drawable.selector_dali_color_without_press : R.drawable.selector_dali_color));
                }
                TextView textView = (TextView) customView.findViewById(R.id.dali_tab_tv);
                textView.setAlpha(Boolean.TRUE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue()) ? 1.0f : 0.5f);
                if (Boolean.FALSE.equals(((ActDaliLightOrGroupVM) this.mViewModel).isOpenLight.getValue())) {
                    textView.setTextColor(getResources().getColor(R.color.color_text_black));
                } else {
                    textView.setTextColor(getResources().getColorStateList(R.color.selector_dali_tab_text));
                }
            }
        }
    }

    private void startObjectObserve() {
        if (((ActDaliLightOrGroupVM) this.mViewModel).groupControl) {
            final Group groupById = Injection.repo().group().getGroupById(((ActDaliLightOrGroupVM) this.mViewModel).controlId);
            if (groupById != null) {
                setTitle(groupById.getGroupName());
                ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.setValue(groupById);
                MessageManager.getInstance().setDaliLightStatusCallBack(new MessageManager.DaliLightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup.2
                    @Override // com.smart.message.MessageManager.DaliLightStatusCallBack
                    public void onDataReceive(int zoneNum, int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean supportK, int rgbColor) {
                        Group groupByGroupId;
                        if (Injection.repo().group().getGroupByGroupId(groupById.getGroupId()) == null || zoneNum != DaliProHelper.getZoneNum(groupById) || deviceAddress != groupById.getFirstDevUniAddr() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(groupById.getGroupId())) == null) {
                            return;
                        }
                        groupByGroupId.getGroupState().setOn(isOn);
                        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                        groupByGroupId.getGroupState().setWy(wy);
                        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                        Injection.repo().group().saveGroup(groupByGroupId);
                        ((ActDaliLightOrGroupVM) ActDaliLightOrGroup.this.mViewModel).controlObject.setValue(groupByGroupId);
                    }
                });
                CmdAssistant.getQueryCmdAssistant(groupById, new int[0]).queryLightState(this, DaliProHelper.getZoneNum(groupById));
                return;
            }
            return;
        }
        final Device deviceById = Injection.repo().device().getDeviceById(((ActDaliLightOrGroupVM) this.mViewModel).controlId);
        if (deviceById != null) {
            setTitle(deviceById.getDeviceName());
            ((ActDaliLightOrGroupVM) this.mViewModel).controlObject.setValue(deviceById);
            MessageManager.getInstance().setDaliLightStatusCallBack(new MessageManager.DaliLightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup.3
                @Override // com.smart.message.MessageManager.DaliLightStatusCallBack
                public void onDataReceive(int zoneNum, int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean supportK, int rgbColor) {
                    Device deviceByDeviceId;
                    Device deviceById2 = Injection.repo().device().getDeviceById(deviceById.getId());
                    if (deviceById2 == null || zoneNum != DaliProHelper.getZoneNum(deviceById2) || deviceAddress != deviceById.getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceById.getDeviceId())) == null) {
                        return;
                    }
                    deviceByDeviceId.getDeviceState().setOn(isOn);
                    deviceByDeviceId.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                    deviceByDeviceId.getDeviceState().setWy(wy);
                    deviceByDeviceId.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                    Injection.repo().device().saveDevice(deviceByDeviceId);
                    ((ActDaliLightOrGroupVM) ActDaliLightOrGroup.this.mViewModel).controlObject.setValue(deviceByDeviceId);
                }
            });
            CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(this, DaliProHelper.getZoneNum(deviceById));
        }
    }

    private int getFragmentByType(int type) {
        for (int i = 0; i < this.mFragmentList.size(); i++) {
            if ((type == 1 && (this.mFragmentList.get(i) instanceof FtDimLight)) || ((type == 2 && (this.mFragmentList.get(i) instanceof FtCtLight)) || (type == 3 && (this.mFragmentList.get(i) instanceof FtRgbLight)))) {
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