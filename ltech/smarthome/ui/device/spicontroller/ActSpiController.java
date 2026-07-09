package com.ltech.smarthome.ui.device.spicontroller;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSpiControllerBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.light.BrtControlHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.SmartSwitch;
import com.smart.message.MessageManager;

/* loaded from: classes4.dex */
public class ActSpiController extends BaseControlActivity<ActSpiControllerBinding, ActSpiControllerVM> {
    private BrtControlHelper brtHelper;
    private int selectTab = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_spi_controller;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActSpiControllerVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActSpiControllerVM) this.mViewModel).initTabList();
        initViewpager();
        ((ActSpiControllerBinding) this.mViewBinding).setColorControl(false);
        ((ActSpiControllerBinding) this.mViewBinding).setExpand(false);
        ((ActSpiControllerBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSpiController.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActSpiControllerBinding) this.mViewBinding).spiSwitch.setOnUserCheckedChangeListener(new SmartSwitch.OnUserCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
            public final void onUserCheckedChanged(SmartSwitch smartSwitch, boolean z) {
                ActSpiController.this.lambda$initView$1(smartSwitch, z);
            }
        });
        BrtControlHelper brtControlHelper = new BrtControlHelper(((ActSpiControllerBinding) this.mViewBinding).sbBrt, ((ActSpiControllerBinding) this.mViewBinding).tvBrt, new BrtControlHelper.OnBrtChangedListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController.1
            @Override // com.ltech.smarthome.ui.device.light.BrtControlHelper.OnBrtChangedListener
            public void onProgressChanged(int progress, boolean finish) {
                if (finish) {
                    ((ActSpiControllerVM) ActSpiController.this.mViewModel).rgbBrt = LightUtils.progress2BrtHasBelowOne(progress);
                    ((ActSpiControllerVM) ActSpiController.this.mViewModel).playMode();
                }
            }
        });
        this.brtHelper = brtControlHelper;
        brtControlHelper.setProgress(100);
        initSpeedView();
        changeDirection(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.iv_console /* 2131296984 */:
            case R.id.tv_console /* 2131298532 */:
                ((ActSpiControllerBinding) this.mViewBinding).setExpand(Boolean.valueOf(!((ActSpiControllerBinding) this.mViewBinding).getExpand().booleanValue()));
                ((ActSpiControllerBinding) this.mViewBinding).ivConsole.setImageResource(((ActSpiControllerBinding) this.mViewBinding).getExpand().booleanValue() ? R.mipmap.ic_spi_down : R.mipmap.ic_spi_up);
                break;
            case R.id.tv_control /* 2131298537 */:
                ((ActSpiControllerVM) this.mViewModel).isPlay = !((ActSpiControllerVM) this.mViewModel).isPlay;
                if (((ActSpiControllerVM) this.mViewModel).isPlay) {
                    ((ActSpiControllerVM) this.mViewModel).playMode();
                    break;
                } else {
                    ((ActSpiControllerVM) this.mViewModel).pauseMode();
                    break;
                }
            case R.id.tv_direction /* 2131298604 */:
                ((ActSpiControllerVM) this.mViewModel).forward = !((ActSpiControllerVM) this.mViewModel).forward;
                changeDirection(((ActSpiControllerVM) this.mViewModel).forward);
                ((ActSpiControllerVM) this.mViewModel).playMode();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(SmartSwitch smartSwitch, boolean z) {
        ((ActSpiControllerVM) this.mViewModel).getLightCmdHelper().sendOnOff(this.activity, z);
        ((ActSpiControllerVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(z));
    }

    private void initViewpager() {
        ((ActSpiControllerBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController.2
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActSpiControllerVM) ActSpiController.this.mViewModel).tabContentList.get(position).getFragment();
            }

            @Override // androidx.viewpager2.adapter.FragmentStateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActSpiControllerVM) ActSpiController.this.mViewModel).tabContentList.size();
            }
        });
        ((ActSpiControllerBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController.3
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ActSpiController.this.selectTab = tab.getPosition();
                ((ActSpiControllerVM) ActSpiController.this.mViewModel).selectFragment = ((ActSpiControllerVM) ActSpiController.this.mViewModel).tabContentList.get(tab.getPosition()).getFragment();
                BaseNormalActivity baseNormalActivity = ActSpiController.this.activity;
                ActSpiController actSpiController = ActSpiController.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actSpiController.getString(((ActSpiControllerVM) actSpiController.mViewModel).tabContentList.get(tab.getPosition()).getTitleRes()), true);
                if (ActSpiController.this.selectTab == 0) {
                    ((ActSpiControllerVM) ActSpiController.this.mViewModel).isPlayList = false;
                    ((ActSpiControllerBinding) ActSpiController.this.mViewBinding).setColorControl(false);
                } else if (ActSpiController.this.selectTab == 1) {
                    ((ActSpiControllerVM) ActSpiController.this.mViewModel).isPlayList = true;
                    ((ActSpiControllerBinding) ActSpiController.this.mViewBinding).setColorControl(false);
                } else {
                    ((ActSpiControllerBinding) ActSpiController.this.mViewBinding).setExpand(false);
                    ((ActSpiControllerBinding) ActSpiController.this.mViewBinding).setColorControl(true);
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActSpiController.this.activity;
                ActSpiController actSpiController = ActSpiController.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actSpiController.getString(((ActSpiControllerVM) actSpiController.mViewModel).tabContentList.get(tab.getPosition()).getTitleRes()), false);
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                BaseNormalActivity baseNormalActivity = ActSpiController.this.activity;
                ActSpiController actSpiController = ActSpiController.this;
                ViewHelpUtil.createTabCustomView(baseNormalActivity, tab, actSpiController.getString(((ActSpiControllerVM) actSpiController.mViewModel).tabContentList.get(tab.getPosition()).getTitleRes()), true);
            }
        });
        new TabLayoutMediator(((ActSpiControllerBinding) this.mViewBinding).tabs, ((ActSpiControllerBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController$$ExternalSyntheticLambda2
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                ActSpiController.this.lambda$initViewpager$2(tab, i);
            }
        }).attach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewpager$2(TabLayout.Tab tab, int i) {
        ViewHelpUtil.createTabCustomView(this.activity, tab, getString(((ActSpiControllerVM) this.mViewModel).tabContentList.get(i).getTitleRes()), false);
    }

    private void initSpeedView() {
        ((ActSpiControllerBinding) this.mViewBinding).sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int i = progress + 1;
                ((ActSpiControllerVM) ActSpiController.this.mViewModel).speed = i;
                ((ActSpiControllerBinding) ActSpiController.this.mViewBinding).tvSpeed.setText(String.valueOf(i));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActSpiControllerVM) ActSpiController.this.mViewModel).playMode();
            }
        });
        ((ActSpiControllerBinding) this.mViewBinding).sbSpeed.setProgress(3);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActSpiControllerVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSpiControllerVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSpiController.this.lambda$startObserve$3((Device) obj);
            }
        });
        ((ActSpiControllerVM) this.mViewModel).refreshConsole.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSpiController.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        setTitle(device.getDeviceName());
        ((ActSpiControllerBinding) this.mViewBinding).spiSwitch.setChecked(device.getDeviceState().isOn());
        ((ActSpiControllerVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        changeDirection(((ActSpiControllerVM) this.mViewModel).forward);
        this.brtHelper.setProgress(LightUtils.brt2PercentHasBelowZero(((ActSpiControllerVM) this.mViewModel).rgbBrt));
        ((ActSpiControllerBinding) this.mViewBinding).sbSpeed.setProgress(((ActSpiControllerVM) this.mViewModel).speed - 1);
        ((ActSpiControllerVM) this.mViewModel).playMode();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        final Device deviceById = Injection.repo().device().getDeviceById(((ActSpiControllerVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActSpiControllerVM) this.mViewModel).controlObject.setValue(deviceById);
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiController$$ExternalSyntheticLambda3
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                    ActSpiController.this.lambda$startObjectObserve$5(deviceById, i, z, i2, i3, i4, z2, z3, z4, i5);
                }
            });
            CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$5(Device device, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Device deviceByDeviceId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId())) == null) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOn(z);
        deviceByDeviceId.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        ((ActSpiControllerVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
    }

    private void changeDirection(boolean forward) {
        ((ActSpiControllerBinding) this.mViewBinding).tvDirection.setText(forward ? R.string.spi_backward : R.string.spi_forward);
        Drawable drawable = getResources().getDrawable(forward ? R.drawable.selector_spi_control_backward : R.drawable.selector_spi_control_forward);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((ActSpiControllerBinding) this.mViewBinding).tvDirection.setCompoundDrawables(drawable, null, null, null);
    }
}