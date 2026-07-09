package com.ltech.smarthome.ui.device.mesh_gateway;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActMeshGatewaySettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.parser.IUpgradeParser;

/* loaded from: classes4.dex */
public class ActMeshGatewaySetting extends BaseDeviceSetActivity<ActMeshGatewaySettingBinding, ActMeshGatewaySettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mesh_gateway_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMeshGatewaySettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActMeshGatewaySettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshGatewaySetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActMeshGatewaySettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshGatewaySetting.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActMeshGatewaySettingVM) this.mViewModel).controlDevice.setValue(device);
        queryMeshGatewayVersion(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActMeshGatewaySettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActMeshGatewaySettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
    }

    private void queryMeshGatewayVersion(Device device) {
        CmdAssistant.getGatewayAssistant(device, new int[0]).queryWifiProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMeshGatewaySetting.this.lambda$queryMeshGatewayVersion$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryMeshGatewayVersion$2(ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "queryMeshGatewayVersion=" + responseMsg);
        if (responseMsg == null) {
            return;
        }
        LHomeLog.i(getClass(), "queryMeshGatewayVersion=" + responseMsg.getAgreementId());
        ProductVersionInfo parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData())));
        if (parserUpgradeInfo == null) {
            return;
        }
        LHomeLog.i(getClass(), "versionInfo.getDeviceModel() =" + parserUpgradeInfo.getDeviceModel() + "  " + parserUpgradeInfo.gethVer() + "   " + parserUpgradeInfo.getsVer());
        UpgradeInfo upgradeInfo = UpgradeFactory.getUpgradeInfo(parserUpgradeInfo);
        if (this.mViewBinding != 0) {
            if (upgradeInfo == null || upgradeInfo.getsVer().compareTo(parserUpgradeInfo.getsVer().toUpperCase()) <= 0) {
                ((ActMeshGatewaySettingBinding) this.mViewBinding).ivTipDot.setVisibility(8);
            } else {
                ((ActMeshGatewaySettingBinding) this.mViewBinding).ivTipDot.setVisibility(0);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (6 == helper.getCode()) {
            ((ActMeshGatewaySettingBinding) this.mViewBinding).ivTipDot.setVisibility(8);
        }
    }
}