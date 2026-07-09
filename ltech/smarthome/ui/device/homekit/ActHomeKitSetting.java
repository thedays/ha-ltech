package com.ltech.smarthome.ui.device.homekit;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActHomeKitSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefaultVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActHomeKitSetting extends BaseDeviceSetActivity<ActHomeKitSettingBinding, ActDeviceSettingDefaultVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_kit_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        ((ActHomeKitSettingBinding) this.mViewBinding).sbLight.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKitSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActHomeKitSetting.this.lambda$initView$1(switchButton, z);
            }
        });
        ((ActHomeKitSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActHomeKitSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActHomeKitSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActHomeKitSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKitSetting$$ExternalSyntheticLambda3
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActHomeKitSetting.this.lambda$initView$2(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(SwitchButton switchButton, final boolean z) {
        getCmdHelper().setBuzzerState(this.activity, 2, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKitSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeKitSetting.this.lambda$initView$0(z, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(boolean z, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            ((ActHomeKitSettingBinding) this.mViewBinding).sbLight.setCheckedNotByUser(!z);
            SmartToast.showCenterShort(getString(R.string.save_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(RefreshLayout refreshLayout) {
        if (((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.getValue() != null) {
            queryLightSetting();
        }
        ((ActHomeKitSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDeviceSettingDefaultVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDeviceSettingDefaultVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKitSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeKitSetting.this.lambda$startObserve$3((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActHomeKitSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActDeviceSettingDefaultVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        queryLightSetting();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActDeviceSettingDefaultVM) this.mViewModel).controlId));
    }

    private void queryLightSetting() {
        getCmdHelper().queryBuzzerState(this, 2, new IAction() { // from class: com.ltech.smarthome.ui.device.homekit.ActHomeKitSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeKitSetting.this.lambda$queryLightSetting$4((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightSetting$4(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        ((ActHomeKitSettingBinding) this.mViewBinding).layoutLight.setVisibility(0);
        ((ActHomeKitSettingBinding) this.mViewBinding).sbLight.setChecked(parseInt == 1);
    }
}