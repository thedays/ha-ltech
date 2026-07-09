package com.ltech.smarthome.ui.device.hsd;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActHsdSensorSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.HsdExtParam;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestPrepare;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActHsdSensorSetting extends BaseDeviceSetActivity<ActHsdSensorSettingBinding, BaseDeviceSetViewModel> {
    private HsdExtParam extParam = new HsdExtParam();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_hsd_sensor_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        ((BaseDeviceSetViewModel) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActHsdSensorSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActHsdSensorSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActHsdSensorSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActHsdSensorSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda5
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActHsdSensorSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        ((ActHsdSensorSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActHsdSensorSetting.this.lambda$initView$1((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue() != null) {
            querySettingState(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue());
        }
        ((ActHsdSensorSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        int id = view.getId();
        if (id == R.id.layout_battery) {
            ((BaseDeviceSetViewModel) this.mViewModel).getBatteryEvent.call();
        } else if (id == R.id.layout_sense_setting) {
            NavUtils.destination(ActSenseSetting.class).withLong(Constants.CONTROL_ID, ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getId()).navigation(this);
        } else {
            if (id != R.id.layout_test_mode) {
                return;
            }
            goTestMode(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue());
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHsdSensorSetting.this.lambda$startObserve$2((Device) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHsdSensorSetting.this.lambda$startObserve$3((Integer) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).getBatteryEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHsdSensorSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActHsdSensorSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        if (isOwnerOrManager()) {
            ((ActHsdSensorSettingBinding) this.mViewBinding).layoutSenseSetting.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        ((ActHsdSensorSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r2) {
        this.dialog = RelateInfoUtils.showImageTipDialog(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActHsdSensorSetting.this.lambda$startObserve$4(imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(ImageTipDialog imageTipDialog) {
        CmdAssistant.getQueryCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).queryWaveSensorState(this);
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    private void goTestMode(final Device device) {
        MessageDialog.show(this, R.string.test_of_hsd, R.string.test_of_hsd_tip).setOkButton(R.string.enter_test_mode, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$goTestMode$6;
                lambda$goTestMode$6 = ActHsdSensorSetting.this.lambda$goTestMode$6(device, baseDialog, view);
                return lambda$goTestMode$6;
            }
        }).setCancelButton(R.string.cancel_next).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$goTestMode$6(Device device, BaseDialog baseDialog, View view) {
        WaveSensorHelper.instance().controlObject = device;
        NavUtils.destination(ActTestPrepare.class).withInt(Constants.TEST_MODE, 4).navigation(this);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(((BaseDeviceSetViewModel) this.mViewModel).controlId);
        if (deviceById != null && deviceById.getExtParam() != null) {
            this.extParam = (HsdExtParam) deviceById.getExtParam(HsdExtParam.class);
        }
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.setValue(deviceById);
        querySettingState(deviceById);
        if (deviceById != null) {
            ((BaseDeviceSetViewModel) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }

    private void querySettingState(final Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryE6Setting(this, new IAction() { // from class: com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHsdSensorSetting.this.lambda$querySettingState$7(device, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySettingState$7(Device device, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            parseResponseMsg(device, responseMsg.getResData());
        }
    }

    public void parseResponseMsg(Device device, String msg) {
        if (msg.length() >= 26) {
            String substring = msg.substring(16);
            if (Integer.parseInt(substring.substring(0, 2), 16) == 4) {
                this.extParam.setSingleReport((Integer.parseInt(substring.substring(4, 6), 16) >> 7) & 1);
                this.extParam.setTimeNoMotion(Integer.parseInt(substring.substring(6, 8), 16));
                this.extParam.setTimeMotion(Integer.parseInt(substring.substring(8, 10), 16));
                device.setParam(this.extParam);
                Injection.repo().device().saveDevice(device);
            }
        }
    }
}