package com.ltech.smarthome.ui.control.provider;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.expand.LightGroupExpandableItem;
import com.ltech.smarthome.model.expand.LightGroupSubExpandableItem;
import com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class LightGroupExpandableItemProvider extends BaseItemProvider<LightGroupExpandableItem, BaseViewHolder> {
    private final ActLightGroupChildItemControlVM mViewModel;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_light_group_control;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 0;
    }

    public LightGroupExpandableItemProvider(ActLightGroupChildItemControlVM viewModel) {
        this.mViewModel = viewModel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, final LightGroupExpandableItem data, final int position) {
        helper.setText(R.id.tv_name, data.getName());
        helper.setGone(R.id.iv_device_more, data.getItem() instanceof Device);
        helper.setGone(R.id.tv_virtual, (data.getItem() instanceof Device) && data.getItem().isVirtual());
        if (data.getItem() instanceof Device) {
            helper.addOnClickListener(R.id.iv_device_more);
        }
        final SwitchButton switchButton = (SwitchButton) helper.getView(R.id.sb);
        if (data.getItem() instanceof Device) {
            Device device = (Device) data.getItem();
            switchButton.setCheckedNotByUser(device.getDeviceState().isOn());
            StringBuilder sb = new StringBuilder();
            if (device.getDeviceState().isOnline()) {
                sb.append(this.mContext.getString(R.string.online));
                if (device.getDeviceState().isOn() && device.getDeviceState().isRhythmPlay()) {
                    sb.append(" ");
                    sb.append(this.mContext.getString(R.string.app_str_rhythm_playing));
                } else {
                    this.mContext.getString(R.string.offline);
                }
            }
            helper.setText(R.id.tv_floor, device.getFloorName() + " " + device.getRoomName() + " | " + ((Object) sb));
        } else if (data.getItem() instanceof Group) {
            Group group = (Group) data.getItem();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (Device device2 : Injection.repo().device().getDevicesByIds(group.getDeviceIds())) {
                if (!device2.getDeviceState().isOnline()) {
                    i3++;
                } else if (device2.getDeviceState().isOn()) {
                    i++;
                    if (device2.getDeviceState().isRhythmPlay()) {
                        i4++;
                    }
                } else {
                    i2++;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            if (i > 0) {
                sb2.append(String.format(this.mContext.getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(i)));
            }
            if (i2 > 0) {
                if (sb2.length() > 0) {
                    sb2.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    sb2.append(" ");
                }
                sb2.append(String.format(this.mContext.getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(i2)));
            }
            if (i3 > 0) {
                if (sb2.length() > 0) {
                    sb2.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    sb2.append(" ");
                }
                sb2.append(String.format(this.mContext.getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(i3)));
            }
            if (i4 > 0) {
                if (sb2.length() > 0) {
                    sb2.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    sb2.append(" ");
                }
                sb2.append(String.format(this.mContext.getString(R.string.app_str_group_sub_status_title_rhythm), Integer.valueOf(i4)));
            }
            helper.setText(R.id.tv_floor, sb2.toString());
            switchButton.setCheckedNotByUser(group.getGroupState().isOn());
        } else {
            switchButton.setCheckedNotByUser(false);
        }
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupExpandableItemProvider.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                LightGroupExpandableItemProvider.this.getLightAssistant(data.getItem()).sendOnOff(ActivityUtils.getTopActivity(), isChecked);
                if (data.getItem() instanceof Group) {
                    Group group2 = (Group) data.getItem();
                    group2.getGroupState().setOn(isChecked);
                    Injection.repo().group().saveGroup(group2);
                    LightGroupExpandableItemProvider.this.mViewModel.groupSwitchChange.postValue(group2);
                    return;
                }
                if ((data.getItem() instanceof Device) && position == 3) {
                    Device device3 = (Device) data.getItem();
                    device3.getDeviceState().setOn(isChecked);
                    LightGroupExpandableItemProvider.this.mViewModel.updateStateGroup(device3);
                }
            }
        });
        helper.setImageResource(R.id.iv_device_more, data.isExpanded() ? R.mipmap.ic_up_gray : R.mipmap.ic_down_gray);
        if (data.getItem() instanceof Group) {
            data.subItemChange(new LightGroupSubExpandableItem.DataChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupExpandableItemProvider.2
                @Override // com.ltech.smarthome.model.expand.LightGroupSubExpandableItem.DataChangeListener
                public void change(Group group2) {
                    switchButton.setCheckedNotByUser(true);
                    LightGroupExpandableItemProvider.this.mViewModel.groupSwitchChange.postValue(group2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }
}