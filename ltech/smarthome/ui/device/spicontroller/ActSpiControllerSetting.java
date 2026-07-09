package com.ltech.smarthome.ui.device.spicontroller;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSpiControllerSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.SpiLightExtParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.SwitchButton;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActSpiControllerSetting extends BaseDeviceSetActivity<ActSpiControllerSettingBinding, ActSpiControllerSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_spi_controller_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        ((ActSpiControllerSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActSpiControllerSetting.this.lambda$initView$1(switchButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(SwitchButton switchButton, final boolean z) {
        getCmdHelper().setOnState(ActivityUtils.getTopActivity(), z ? 3 : 2, 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSpiControllerSetting.this.lambda$initView$0(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this.activity, ((ActSpiControllerSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.POWER_STATUS, getCmdHelper().setOnState(z ? 3 : 2, 0, 0));
        } else {
            ((ActSpiControllerSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setCheckedNotByUser(!z);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSpiControllerSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSpiControllerSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSpiControllerSettingVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSpiControllerSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActSpiControllerSettingVM) this.mViewModel).controlId));
        ((ActSpiControllerSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSpiControllerSetting.this.lambda$startObserve$2((Device) obj);
            }
        });
        queryState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActSpiControllerSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActSpiControllerSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        if (device.getExtParam() != null) {
            try {
                ((ActSpiControllerSettingVM) this.mViewModel).extParam = (SpiLightExtParam) device.getExtParam(SpiLightExtParam.class);
                ((ActSpiControllerSettingBinding) this.mViewBinding).tvStripParam.setText(getResources().getStringArray(R.array.spi_light_ic)[((ActSpiControllerSettingVM) this.mViewModel).extParam.getIcType()] + " " + getResources().getStringArray(R.array.spi_light_order)[((ActSpiControllerSettingVM) this.mViewModel).extParam.getIcLine()] + " " + ((ActSpiControllerSettingVM) this.mViewModel).extParam.getPixel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActSpiControllerSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActSpiControllerSettingVM) this.mViewModel).controlId));
    }

    private void queryState() {
        getCmdHelper().queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSpiControllerSetting.this.lambda$queryState$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$3(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getStateCode() != 0 || responseMsg.getResData().length() < 18) {
            return;
        }
        ((ActSpiControllerSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setCheckedNotByUser(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 3);
    }
}