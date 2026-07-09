package com.ltech.smarthome.ui.device.setting;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActBleMusicPlayerSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActBleMusicPlayerSetting extends BaseDeviceSetActivity<ActBleMusicPlayerSettingBinding, ActBleMusicPlayerSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_music_player_setting;
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
        ((ActBleMusicPlayerSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleMusicPlayerSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActBleMusicPlayerSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMusicPlayerSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActBleMusicPlayerSettingVM) this.mViewModel).regDeviceStateLisener();
        ((ActBleMusicPlayerSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMusicPlayerSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActBleMusicPlayerSettingVM) this.mViewModel).showBluetoothStateEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMusicPlayerSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActBleMusicPlayerSettingVM) this.mViewModel).showIgnoreCurrentConnectionsEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMusicPlayerSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActBleMusicPlayerSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActBleMusicPlayerSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActBleMusicPlayerSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActBleMusicPlayerSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda5
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActBleMusicPlayerSetting.this.lambda$startObserve$4(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActBleMusicPlayerSettingVM) this.mViewModel).controlDevice.setValue(device);
        ((ActBleMusicPlayerSettingVM) this.mViewModel).deviceId = device.getDeviceId();
        ((ActBleMusicPlayerSettingVM) this.mViewModel).checkDeviceState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActBleMusicPlayerSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActBleMusicPlayerSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showModelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showTipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(RefreshLayout refreshLayout) {
        if (((ActBleMusicPlayerSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            ((ActBleMusicPlayerSettingVM) this.mViewModel).checkDeviceState();
        }
        ((ActBleMusicPlayerSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    private void showTipDialog() {
        MessageDialog.show(this, getString(R.string.music_player_ingnore_confirm_tip), getString(R.string.music_player_ingnore_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTipDialog$5;
                lambda$showTipDialog$5 = ActBleMusicPlayerSetting.this.lambda$showTipDialog$5(baseDialog, view);
                return lambda$showTipDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTipDialog$5(BaseDialog baseDialog, View view) {
        ((ActBleMusicPlayerSettingVM) this.mViewModel).ignoreConnect();
        return false;
    }

    private void showModelDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(((ActBleMusicPlayerSettingVM) this.mViewModel).curBleState - 1);
        arrayList.add(getString(R.string.bluetooth_search));
        arrayList.add(getString(R.string.bluetooth_receive));
        arrayList.add(getString(R.string.bluetooth_close));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleMusicPlayerSetting.this.lambda$showModelDialog$6((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showModelDialog$6(Integer num) {
        ((ActBleMusicPlayerSettingVM) this.mViewModel).changeBluetoothModel(num.intValue() + 1);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3014 || data == null) {
            return;
        }
        ((ActBleMusicPlayerSettingVM) this.mViewModel).changePowerOnModel(data.getIntExtra(Constants.MUSIC_PLAYER_POWER_STATE, 3));
    }
}