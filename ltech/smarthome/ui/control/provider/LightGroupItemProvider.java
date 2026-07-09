package com.ltech.smarthome.ui.control.provider;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.light.ActColorCCLight;
import com.ltech.smarthome.ui.device.light.ActColorLight;
import com.ltech.smarthome.ui.device.light.ActCtLight;
import com.ltech.smarthome.ui.device.light.ActDimLight;
import com.ltech.smarthome.ui.device.light.ActModuleSwitch;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SmartSwitch;
import com.ltech.smarthome.view.dialog.LightQuickDialog;
import com.smart.message.MessageManager;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class LightGroupItemProvider extends BaseDeviceProvider<Group> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_group_light;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 14;
    }

    public LightGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public LightGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Group data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        int i = 0;
        if (isNormalMode()) {
            helper.setVisible(R.id.iv_device_more, ProductRepository.getLightColorType((Object) data) != 7);
        }
        helper.setGone(R.id.appCompatTextView18, ProductRepository.isDaliLightGroup(data));
        SmartSwitch smartSwitch = (SmartSwitch) helper.getView(R.id.sb);
        smartSwitch.setChecked(data.getGroupState().isOn());
        if (data.isVirtual()) {
            smartSwitch.setVisibility(8);
        } else {
            if (!isNormalMode() || (data.getDeviceIds().isEmpty() && !RelaySeparationHelper.isRelaySeparationSub(data))) {
                i = 8;
            }
            smartSwitch.setVisibility(i);
        }
        smartSwitch.setOnUserCheckedChangeListener(new SmartSwitch.OnUserCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.1
            @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
            public void onUserCheckedChanged(SmartSwitch view, boolean isChecked) {
                LightGroupItemProvider.this.getLightAssistant(data).sendOnOff(ActivityUtils.getTopActivity(), isChecked);
                LightGroupItemProvider.this.updateStateLight(data, isChecked);
            }
        });
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) LightGroupItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) LightGroupItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) LightGroupItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) LightGroupItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) LightGroupItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) LightGroupItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                LightGroupItemProvider lightGroupItemProvider = LightGroupItemProvider.this;
                lightGroupItemProvider.nav(data, lightGroupItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        showQuickDialog(data, this.viewModel);
        checkGroupStatus(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Group group, BaseViewModel viewModel) {
        NavUtils.Builder destination;
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 1) {
            destination = NavUtils.destination(ActDimLight.class);
        } else if (lightColorType == 2) {
            destination = NavUtils.destination(ActCtLight.class);
        } else if (lightColorType == 3 || lightColorType == 4 || lightColorType == 5) {
            destination = NavUtils.destination(ActColorLight.class);
        } else if (lightColorType == 7) {
            destination = NavUtils.destination(ActModuleSwitch.class);
        } else if (lightColorType == 20) {
            destination = NavUtils.destination(ActColorCCLight.class);
        } else if (lightColorType != 101) {
            destination = null;
        } else {
            destination = NavUtils.destination(ActDaliLightOrGroup.class);
            destination.withLong(Constants.PLACE_ID, group.getPlaceId()).withLong("device_id", group.getMacdeviceid());
        }
        if (destination != null) {
            viewModel.navigation(destination.withLong(Constants.CONTROL_ID, group.getId()).withInt(Constants.LIGHT_TYPE, lightColorType).withBoolean(Constants.GROUP_CONTROL, true));
        }
    }

    private void showQuickDialog(final Group group, final BaseViewModel viewModel) {
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (ProductRepository.isDaliLightGroup(group) && (lightColorType = DaliProHelper.convertDaliType(group)) == 5) {
            LightQuickDialog ctRgb = LightQuickDialog.ctRgb();
            ctRgb.setLightName(group.getName()).setOnline(group.getDeviceState().isOnline()).setSwitchOn(group.getDeviceState().isOn()).setWy(group.getDeviceState().getWy()).setControlObject(group).setRgbBrt(group.getDeviceState().getRgbBrt()).setCallback(new LightQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.3
                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onColorChanged(float xProgress, int color, boolean finish) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendRgb(ActivityUtils.getTopActivity(), color, finish);
                    LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                    LightGroupItemProvider.this.changeSwitchNotByUser(group);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onBrtChanged(int progress, boolean finish) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendCtBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                    LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                    LightGroupItemProvider.this.changeSwitchNotByUser(group);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onSwitch(boolean on) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendOnOff(ActivityUtils.getTopActivity(), on);
                    LightGroupItemProvider.this.updateStateLight(group, on);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onMoreAction() {
                    LightGroupItemProvider.this.nav(group, viewModel);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onCtChanged(float xProgress, int color, boolean finish) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendCW(ActivityUtils.getTopActivity(), LightUtils.percent2C(xProgress), finish);
                    LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                    LightGroupItemProvider.this.changeSwitchNotByUser(group);
                }
            }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(group, ctRgb);
            return;
        }
        if (lightColorType == 1) {
            LightQuickDialog dim = LightQuickDialog.dim();
            dim.setLightName(group.getGroupName()).setSwitchOn(group.getGroupState().isOn()).setRgbBrt(group.getGroupState().getWyBrt()).setCallback(new LightQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.6
                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onColorChanged(float xProgress, int color, boolean finish) {
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onCtChanged(float xProgress, int color, boolean finish) {
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onBrtChanged(int progress, boolean finish) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendDimBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                    LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                    LightGroupItemProvider.this.changeSwitchNotByUser(group);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onSwitch(boolean on) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendOnOff(ActivityUtils.getTopActivity(), on);
                    LightGroupItemProvider.this.updateStateLight(group, on);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onMoreAction() {
                    LightGroupItemProvider.this.nav(group, viewModel);
                }
            }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(group, dim);
            return;
        }
        if (lightColorType == 2) {
            LightQuickDialog ct = LightQuickDialog.ct();
            ct.setLightName(group.getGroupName()).setRgbBrt(group.getGroupState().getWyBrt()).setWy(group.getGroupState().getWy()).setControlObject(group).setSwitchOn(group.getGroupState().isOn()).setCallback(new LightQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.4
                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onColorChanged(float xProgress, int color, boolean finish) {
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onBrtChanged(int progress, boolean finish) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendCtBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                    LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                    LightGroupItemProvider.this.changeSwitchNotByUser(group);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onSwitch(boolean on) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendOnOff(ActivityUtils.getTopActivity(), on);
                    LightGroupItemProvider.this.updateStateLight(group, on);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onMoreAction() {
                    LightGroupItemProvider.this.nav(group, viewModel);
                }

                @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
                public void onCtChanged(float xProgress, int color, boolean finish) {
                    LightGroupItemProvider.this.getLightAssistant(group).sendCW(ActivityUtils.getTopActivity(), LightUtils.percent2C(xProgress), finish);
                    LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                    LightGroupItemProvider.this.changeSwitchNotByUser(group);
                }
            }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(group, ct);
            return;
        }
        if (lightColorType != 3 && lightColorType != 4 && lightColorType != 5) {
            if (lightColorType == 7) {
                viewModel.navigation(NavUtils.destination(ActModuleSwitch.class).withLong(Constants.CONTROL_ID, group.getId()).withInt(Constants.LIGHT_TYPE, lightColorType).withBoolean(Constants.GROUP_CONTROL, true));
                return;
            } else if (lightColorType != 20) {
                return;
            }
        }
        LightQuickDialog rgb = LightQuickDialog.rgb();
        rgb.setLightName(group.getGroupName()).setRgbBrt(group.getGroupState().getRgbBrt()).setSwitchOn(group.getGroupState().isOn()).setCallback(new LightQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.5
            @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
            public void onCtChanged(float xProgress, int color, boolean finish) {
            }

            @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
            public void onColorChanged(float xProgress, int color, boolean finish) {
                LightGroupItemProvider.this.getLightAssistant(group).sendRgb(ActivityUtils.getTopActivity(), color, finish);
                LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                LightGroupItemProvider.this.changeSwitchNotByUser(group);
            }

            @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
            public void onBrtChanged(int progress, boolean finish) {
                LightGroupItemProvider.this.getLightAssistant(group).sendRgbBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                LightGroupItemProvider.this.getLightAssistant(group).setState(true);
                LightGroupItemProvider.this.changeSwitchNotByUser(group);
            }

            @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
            public void onSwitch(boolean on) {
                LightGroupItemProvider.this.getLightAssistant(group).sendOnOff(ActivityUtils.getTopActivity(), on);
                LightGroupItemProvider.this.updateStateLight(group, on);
            }

            @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
            public void onMoreAction() {
                LightGroupItemProvider.this.nav(group, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        selectStatus(group, rgb);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeSwitchNotByUser(Group group) {
        if (group.getGroupState().isOn()) {
            return;
        }
        updateStateLight(group, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStateLight(Group group, boolean isChecked) {
        group.getGroupState().setOn(isChecked);
        Injection.repo().group().saveGroup(group);
        if (RelaySeparationHelper.isRelaySeparationSub(group)) {
            Group changeMainGroupRelayOnOff = RelaySeparationHelper.changeMainGroupRelayOnOff(group);
            if (changeMainGroupRelayOnOff != null) {
                this.onDataChangeListener.onDataChange(changeMainGroupRelayOnOff);
            }
        } else {
            for (Group group2 : Injection.repo().group().getGroupByDeviceUnicastAddress(group.getPlaceId(), group.getFirstDevUniAddr())) {
                if (group2.getLeaderSup() != 1) {
                    group2.getGroupState().setOn(isChecked);
                    Injection.repo().group().saveGroup(group2);
                    this.onDataChangeListener.onDataChange(group2);
                }
            }
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                long longValue = it.next().longValue();
                List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
                if (value != null) {
                    for (int i = 0; i < value.size(); i++) {
                        if (longValue == value.get(i).getDeviceId()) {
                            value.get(i).getDeviceState().setOn(isChecked);
                        }
                    }
                    Injection.repo().device().saveDevice(value);
                }
            }
        }
        if (this.onDataChangeListener != null) {
            this.onDataChangeListener.onGroupOfflineChange(group.getGroupAddress());
        }
    }

    private void selectStatus(final Group group, final LightQuickDialog lightQuickDialog) {
        if (ProductRepository.isDaliLightGroup(group)) {
            MessageManager.getInstance().setDaliLightStatusCallBack(new MessageManager.DaliLightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.7
                @Override // com.smart.message.MessageManager.DaliLightStatusCallBack
                public void onDataReceive(int zoneNum, int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean supportK, int rgbColor) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                    int zoneNum2 = DaliProHelper.getZoneNum(groupByGroupId);
                    if (groupByGroupId == null || zoneNum2 != zoneNum) {
                        return;
                    }
                    groupByGroupId.getGroupState().setOn(isOn);
                    groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                    groupByGroupId.getGroupState().setWy(wy);
                    groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                    groupByGroupId.getGroupState().setOnlineState(1);
                    Injection.repo().group().saveGroup(groupByGroupId);
                    lightQuickDialog.setOnline(true).setSwitchOn(isOn);
                    lightQuickDialog.setWy(wy);
                    lightQuickDialog.setRgb(rgbColor);
                    int convertDaliType = DaliProHelper.convertDaliType(group);
                    if (convertDaliType == 1 || convertDaliType == 2) {
                        lightQuickDialog.setRgbBrt(groupByGroupId.getGroupState().getWyBrt());
                    } else {
                        lightQuickDialog.setRgbBrt(groupByGroupId.getGroupState().getRgbBrt());
                    }
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            lightQuickDialog.notifyDialog();
                        }
                    });
                }
            });
        } else {
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.8
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public void onDataReceive(int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean rgbOn, boolean supportK, boolean rhythmPlay, int rgbColor) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                    if (groupByGroupId != null) {
                        if ((deviceAddress <= 49152 || groupByGroupId.getGroupAddress() != deviceAddress) && !(groupByGroupId.getFirstDevUniAddr() == deviceAddress && group.getLeaderSup() == 0)) {
                            return;
                        }
                        groupByGroupId.getGroupState().setOn(isOn);
                        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                        groupByGroupId.getGroupState().setWy(wy);
                        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                        groupByGroupId.getGroupState().setOnlineState(1);
                        Injection.repo().group().saveGroup(groupByGroupId);
                        lightQuickDialog.setOnline(true).setSwitchOn(isOn);
                        lightQuickDialog.setWy(wy);
                        lightQuickDialog.setRgb(rgbColor);
                        int lightColorType = ProductRepository.getLightColorType((Object) groupByGroupId);
                        if (lightColorType == 1 || lightColorType == 2) {
                            lightQuickDialog.setRgbBrt(groupByGroupId.getGroupState().getWyBrt());
                        } else {
                            lightQuickDialog.setRgbBrt(groupByGroupId.getGroupState().getRgbBrt());
                        }
                        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupItemProvider.8.1
                            @Override // java.lang.Runnable
                            public void run() {
                                lightQuickDialog.notifyDialog();
                            }
                        });
                    }
                }
            });
        }
    }
}