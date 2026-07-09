package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActModuleSwitchBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;

/* loaded from: classes4.dex */
public class ActModuleSwitch extends BaseControlActivity<ActModuleSwitchBinding, ActModuleSwitchVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_module_switch;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActModuleSwitchVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActModuleSwitchVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActModuleSwitchVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        if (((ActModuleSwitchVM) this.mViewModel).groupControl) {
            ((ActModuleSwitchVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActModuleSwitchVM) this.mViewModel).controlId));
        } else {
            ((ActModuleSwitchVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActModuleSwitchVM) this.mViewModel).controlId));
        }
        ((ActModuleSwitchVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActModuleSwitch$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActModuleSwitch.this.lambda$startObserve$0(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (obj instanceof Group) {
            LHomeLog.i(getClass(), "startObserve: isgroup");
            Group group = (Group) obj;
            setTitle(group.getGroupName());
            ((ActModuleSwitchVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
            selectState(group);
            return;
        }
        if (obj instanceof Device) {
            LHomeLog.i(getClass(), "startObserve: isdevice");
            Device device = (Device) obj;
            setTitle(device.getDeviceName());
            ((ActModuleSwitchVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
            selectState(device);
        }
    }

    private void selectState(final Group group) {
        MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.device.light.ActModuleSwitch.1
            @Override // com.smart.message.MessageManager.LightStatusCallBack
            public void onDataReceive(int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean rgbOn, boolean supportK, boolean rhythmPlay, int rgbColor) {
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                if (groupByGroupId != null) {
                    if ((deviceAddress <= 49152 || groupByGroupId.getGroupAddress() != deviceAddress) && !(groupByGroupId.getFirstDevUniAddr() == deviceAddress && group.getLeaderSup() == 0)) {
                        return;
                    }
                    groupByGroupId.getGroupState().setOn(isOn);
                    groupByGroupId.getGroupState().setOnlineState(1);
                    Injection.repo().group().saveGroup(groupByGroupId);
                }
            }
        });
        CmdAssistant.getQueryCmdAssistant(group, new int[0]).queryPanelState(ActivityUtils.getTopActivity());
    }

    private void selectState(final Device device) {
        ProductRepository.getBleProductInfoByType(device);
        if (RelaySeparationHelper.isSwitchRelay(device)) {
            final int relayNum = RelaySeparationHelper.getRelayNum(device);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActModuleSwitch.2
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public void onDataReceive(ResponseMsg msg) {
                    SwitchPanelState parserSwitchPanelState = ((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSwitchPanelState(msg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), 2, msg.getResData());
                    if (parserSwitchPanelState != null && parserSwitchPanelState.getSwitchPanelZoneStateList().size() > 1) {
                        int i = relayNum;
                        if (i == 1) {
                            device.getDeviceState().setOn(parserSwitchPanelState.getSwitchPanelZoneStateList().get(0).isSwitchOnOff());
                        } else if (i == 2) {
                            device.getDeviceState().setOn(parserSwitchPanelState.getSwitchPanelZoneStateList().get(1).isSwitchOnOff());
                        }
                    }
                    device.getDeviceState().setOnlineState(1);
                    ((ActModuleSwitchVM) ActModuleSwitch.this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
                    Injection.repo().device().saveDevice(device);
                }
            });
            query(device);
        } else {
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActModuleSwitch.3
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public void onDataReceive(int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean rgbOn, boolean supportK, boolean rhythmPlay, int rgbColor) {
                    if (((BleParam) device.getParam(BleParam.class)).getUnicastAddress() == deviceAddress) {
                        device.getDeviceState().setOn(isOn);
                        device.getDeviceState().setOnlineState(1);
                        ((ActModuleSwitchVM) ActModuleSwitch.this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
                        Injection.repo().device().saveDevice(device);
                    }
                }
            });
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActModuleSwitch.4
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public void onDataReceive(ResponseMsg msg) {
                    SwitchPanelState parserSwitchPanelState = ((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSwitchPanelState(msg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), 2, msg.getResData());
                    if (parserSwitchPanelState != null && parserSwitchPanelState.getSwitchPanelZoneStateList().size() > 0) {
                        device.getDeviceState().setOn(parserSwitchPanelState.getSwitchPanelZoneStateList().get(0).isSwitchOnOff());
                    }
                    device.getDeviceState().setOnlineState(1);
                    ((ActModuleSwitchVM) ActModuleSwitch.this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
                    Injection.repo().device().saveDevice(device);
                }
            });
            CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelState(ActivityUtils.getTopActivity());
        }
    }

    private void query(Device device) {
        CmdAssistant.getQueryCmdAssistant(Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid()), new int[0]).queryPanelState(ActivityUtils.getTopActivity());
    }
}