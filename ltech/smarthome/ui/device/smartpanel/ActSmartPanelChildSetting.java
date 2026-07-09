package com.ltech.smarthome.ui.device.smartpanel;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSmartPanelChildSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/* loaded from: classes4.dex */
public class ActSmartPanelChildSetting extends BaseDeviceSetActivity<ActSmartPanelChildSettingBinding, ActSmartPanelChildSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel_child_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting$$ExternalSyntheticLambda2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActSmartPanelChildSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).sbAddToSmart.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelChildSettingVM) ActSmartPanelChildSetting.this.mViewModel).changeHide(isChecked);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        ((ActSmartPanelChildSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelChildSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelChildSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActSmartPanelChildSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelChildSetting.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((ActSmartPanelChildSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        ((ActSmartPanelChildSettingVM) this.mViewModel).nameChangeSuccess.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                int relayNum = RelaySeparationHelper.getRelayNum(((ActSmartPanelChildSettingVM) ActSmartPanelChildSetting.this.mViewModel).controlDevice.getValue());
                for (int i = 0; i < RelateInfoUtils.relatedInfoList.size(); i++) {
                    RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(i);
                    if (relateInfoItem.relateInfo != null && relateInfoItem.type == 0 && relateInfoItem.relateInfo.switchIndex == relayNum) {
                        RelaySeparationHelper.setScreenData(ActSmartPanelChildSetting.this.activity, i, ((ActSmartPanelChildSettingBinding) ActSmartPanelChildSetting.this.mViewBinding).tvDeviceName.getText().toString(), ((ActSmartPanelChildSettingVM) ActSmartPanelChildSetting.this.mViewModel).controlDevice.getValue(), RelateInfoUtils.relateInfoAssistant, true, new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting.2.1
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(Integer integer) {
                                if (integer.intValue() == 0) {
                                    ActSmartPanelChildSetting.this.showLoadingDialog("");
                                } else if (integer.intValue() == 1) {
                                    ActSmartPanelChildSetting.this.dismissLoadingDialog();
                                } else if (integer.intValue() == 2) {
                                    ActSmartPanelChildSetting.this.showErrorDialog(ActSmartPanelChildSetting.this.getString(R.string.save_fail));
                                }
                            }
                        });
                        return;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        int i = 0;
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).sbAddToSmart.setCheckedNotByUser(device.getSubhide() == 0);
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).tvDeviceName.setText(device.getDeviceName());
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActSmartPanelChildSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        ((ActSmartPanelChildSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ((ActSmartPanelChildSettingVM) this.mViewModel).isAddToRoom.setValue(Boolean.valueOf(device.getSubhide() == 0));
        int imageIndex = device.getImageIndex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        while (true) {
            if (i >= lightIconValue.length) {
                break;
            }
            if (i == imageIndex) {
                ((ActSmartPanelChildSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                break;
            }
            i++;
        }
        ((ActSmartPanelChildSettingVM) this.mViewModel).queryScene(device.getDeviceId());
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
        if (deviceByDeviceId == null) {
            ((ActSmartPanelChildSettingBinding) this.mViewBinding).tvSubordinateDevice.setText("");
        } else {
            RelateInfoUtils.initRelateInfoList(deviceByDeviceId);
            ((ActSmartPanelChildSettingBinding) this.mViewBinding).tvSubordinateDevice.setText(deviceByDeviceId.getDeviceName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((ActSmartPanelChildSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }
}