package com.ltech.smarthome.ui.device.cg485;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActCg485SettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActCg485Setting extends BaseDeviceSetActivity<ActCg485SettingBinding, ActCg485SettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_cg_485_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        isOwnerOrManager();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActCg485SettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActCg485SettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActCg485SettingVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActCg485SettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActCg485SettingVM) this.mViewModel).controlId));
        ((ActCg485SettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Setting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Setting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActCg485SettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Setting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Setting.this.lambda$startObserve$1((Integer) obj);
            }
        });
        ((ActCg485SettingVM) this.mViewModel).queryParamResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485Setting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485Setting.this.lambda$startObserve$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        String floorName;
        if (device != null) {
            Floor floor = Injection.repo().home().getFloor(device.getFloorId());
            Room room = Injection.repo().home().getRoom(device.getRoomId());
            AppCompatTextView appCompatTextView = ((ActCg485SettingBinding) this.mViewBinding).tvRoomName;
            if (room != null) {
                floorName = floor.getFloorName() + " " + room.getRoomName();
            } else {
                floorName = floor != null ? floor.getFloorName() : "";
            }
            appCompatTextView.setText(floorName);
            ((ActCg485SettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        ((ActCg485SettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            ((ActCg485SettingVM) this.mViewModel).goSerialSettings();
        } else {
            showErrorDialog(getString(R.string.search_fail));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (((ActCg485SettingVM) this.mViewModel).controlDevice.getValue() != null) {
            ((ActCg485SettingVM) this.mViewModel).queryScene(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue().getDeviceId());
        }
    }
}