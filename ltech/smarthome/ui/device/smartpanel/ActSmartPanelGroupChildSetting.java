package com.ltech.smarthome.ui.device.smartpanel;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSmartPanelGroupChildSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/* loaded from: classes4.dex */
public class ActSmartPanelGroupChildSetting extends VMActivity<ActSmartPanelGroupChildSettingBinding, ActSmartPanelGroupChildSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel_group_child_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting$$ExternalSyntheticLambda2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActSmartPanelGroupChildSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).sbAddToSmart.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelGroupChildSettingVM) ActSmartPanelGroupChildSetting.this.mViewModel).changeHideGroup(isChecked);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelGroupChildSetting.this.lambda$startObserve$1((Group) obj);
            }
        });
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelGroupChildSetting.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).nameChangeSuccess.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                int relayNum = RelaySeparationHelper.getRelayNum(((ActSmartPanelGroupChildSettingVM) ActSmartPanelGroupChildSetting.this.mViewModel).controlObject.getValue());
                for (int i = 0; i < RelateInfoUtils.relatedInfoList.size(); i++) {
                    RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(i);
                    if (relateInfoItem.relateInfo != null && relateInfoItem.type == 0 && relateInfoItem.relateInfo.switchIndex == relayNum) {
                        RelaySeparationHelper.setScreenData(ActSmartPanelGroupChildSetting.this.activity, i, ((ActSmartPanelGroupChildSettingBinding) ActSmartPanelGroupChildSetting.this.mViewBinding).tvDeviceName.getText().toString(), ((ActSmartPanelGroupChildSettingVM) ActSmartPanelGroupChildSetting.this.mViewModel).controlObject.getValue(), RelateInfoUtils.relateInfoAssistant, true, new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting.2.1
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(Integer integer) {
                                if (integer.intValue() == 0) {
                                    ActSmartPanelGroupChildSetting.this.showLoadingDialog("");
                                } else if (integer.intValue() == 1) {
                                    ActSmartPanelGroupChildSetting.this.dismissLoadingDialog();
                                } else if (integer.intValue() == 2) {
                                    ActSmartPanelGroupChildSetting.this.showErrorDialog(ActSmartPanelGroupChildSetting.this.getString(R.string.save_fail));
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
    public /* synthetic */ void lambda$startObserve$1(Group group) {
        if (group == null) {
            finishActivity();
            return;
        }
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).controlGroup = group;
        int i = 0;
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).sbAddToSmart.setCheckedNotByUser(group.getSubhide() == 0);
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).tvDeviceName.setText(group.getGroupName());
        Floor floor = Injection.repo().home().getFloor(group.getFloorId());
        Room room = Injection.repo().home().getRoom(group.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).roomPickHelper.startObserve(this, group.getPlaceId(), group.getRoomId());
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).isAddToRoom.setValue(Boolean.valueOf(group.getSubhide() == 0));
        int imgindex = group.getImgindex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        while (true) {
            if (i >= lightIconValue.length) {
                break;
            }
            if (i == imgindex) {
                ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                break;
            }
            i++;
        }
        ((ActSmartPanelGroupChildSettingVM) this.mViewModel).queryScene(group.getGroupId());
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getMaingroupid());
        if (groupByGroupId == null) {
            ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).tvSubordinateDevice.setText("");
        } else {
            ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).tvSubordinateDevice.setText(groupByGroupId.getGroupName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((ActSmartPanelGroupChildSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }
}