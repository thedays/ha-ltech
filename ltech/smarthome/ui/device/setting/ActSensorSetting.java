package com.ltech.smarthome.ui.device.setting;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSensorSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.setting.ActSensorSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSensorSetting extends BaseDeviceSetActivity<ActSensorSettingBinding, ActSensorSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sensor_setting;
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
        ((ActSensorSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActSensorSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSensorSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActSensorSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSensorSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActSensorSettingVM) this.mViewModel).sensitivityEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSetting.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                for (int i2 = 2; i2 <= 100; i2 += 2) {
                    if (i2 == 10) {
                        arrayList.add(i2 + "DB(" + ActSensorSetting.this.getString(R.string.mode_default) + ")");
                    } else {
                        arrayList.add(i2 + "DB");
                    }
                    if (i2 < 10) {
                        i++;
                    }
                }
                SelectListWheelDialog.asDefault(true).setTitle(ActSensorSetting.this.getString(R.string.sensitivity)).setSelectList(arrayList).setTip(ActSensorSetting.this.getString(R.string.app_str_sensitivity_tip)).setCancelString(ActSensorSetting.this.getString(R.string.cancel)).setConfirmString(ActSensorSetting.this.getString(R.string.ok)).setSelectPosition(i).setConfirmAction(new C01441()).showDialog(ActSensorSetting.this.activity);
            }

            /* renamed from: com.ltech.smarthome.ui.device.setting.ActSensorSetting$1$1, reason: invalid class name and collision with other inner class name */
            class C01441 implements IAction<Integer> {
                C01441() {
                }

                @Override // com.ltech.smarthome.base.IAction
                public void act(final Integer integer) {
                    RelateInfoUtils.showImageTipDialog(ActSensorSetting.this.getString(R.string.app_str_low_power_bt_ota_tip2), R.mipmap.hs_pic_clickfast, ActSensorSetting.this.activity, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSetting$1$1$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                        public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                            ActSensorSetting.AnonymousClass1.C01441.this.lambda$act$0(integer, imageTipDialog);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$act$0(Integer num, ImageTipDialog imageTipDialog) {
                    ((ActSensorSettingVM) ActSensorSetting.this.mViewModel).setSensitivity((num.intValue() * 2) + 2);
                    imageTipDialog.dismissDialog();
                }
            }
        });
        ((ActSensorSettingVM) this.mViewModel).reportIntervalEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                for (int i2 = 10; i2 <= 120; i2++) {
                    if (i2 == 30) {
                        arrayList.add(i2 + ActSensorSetting.this.getString(R.string.sec) + "(" + ActSensorSetting.this.getString(R.string.mode_default) + ")");
                    } else {
                        arrayList.add(i2 + ActSensorSetting.this.getString(R.string.sec));
                    }
                    if (i2 < 30) {
                        i++;
                    }
                }
                SelectListWheelDialog.asDefault(true).setTitle(ActSensorSetting.this.getString(R.string.app_str_time_interval)).setTip(ActSensorSetting.this.getString(R.string.app_str_time_interval_tip)).setSelectList(arrayList).setSelectPosition(i).setCancelString(ActSensorSetting.this.getString(R.string.cancel)).setConfirmString(ActSensorSetting.this.getString(R.string.ok)).setConfirmAction(new AnonymousClass1()).showDialog(ActSensorSetting.this.activity);
            }

            /* renamed from: com.ltech.smarthome.ui.device.setting.ActSensorSetting$2$1, reason: invalid class name */
            class AnonymousClass1 implements IAction<Integer> {
                AnonymousClass1() {
                }

                @Override // com.ltech.smarthome.base.IAction
                public void act(final Integer integer) {
                    RelateInfoUtils.showImageTipDialog(ActSensorSetting.this.getString(R.string.app_str_low_power_bt_ota_tip2), R.mipmap.hs_pic_clickfast, ActSensorSetting.this.activity, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActSensorSetting$2$1$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                        public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                            ActSensorSetting.AnonymousClass2.AnonymousClass1.this.lambda$act$0(integer, imageTipDialog);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$act$0(Integer num, ImageTipDialog imageTipDialog) {
                    ((ActSensorSettingVM) ActSensorSetting.this.mViewModel).setReportInterval(num.intValue() + 10);
                    imageTipDialog.dismissDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActSensorSettingVM) this.mViewModel).controlDevice.setValue(device);
        ((ActSensorSettingVM) this.mViewModel).querySensorStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActSensorSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActSensorSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
    }
}