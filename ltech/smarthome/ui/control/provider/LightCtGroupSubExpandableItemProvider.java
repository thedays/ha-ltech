package com.ltech.smarthome.ui.control.provider;

import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.expand.LightGroupSubExpandableItem;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.LightQuickDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class LightCtGroupSubExpandableItemProvider extends BaseItemProvider<LightGroupSubExpandableItem, BaseViewHolder> {
    private int RgbBrt;
    private int Wy;
    private final ActLightGroupChildItemControlVM mViewModel;

    public interface OnCallback {
        void onBrtChanged(int progress, boolean finish);

        void onColorChanged(float xProgress, int color, boolean finish);

        void onMoreAction();
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_ct_light_group_control_sub_device;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 1;
    }

    public LightCtGroupSubExpandableItemProvider(ActLightGroupChildItemControlVM viewModel) {
        this.mViewModel = viewModel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, final LightGroupSubExpandableItem data, final int position) {
        if (ProductRepository.getLightColorType(data.getItem()) != 2) {
            return;
        }
        if (data.getItem() instanceof Device) {
            Device device = (Device) data.getItem();
            setWy(device.getDeviceState().getWy());
            setRgbBrt(device.getDeviceState().getWyBrt());
        } else if (data.getItem() instanceof Group) {
            Group group = (Group) data.getItem();
            setWy(group.getGroupState().getWy());
            setRgbBrt(group.getGroupState().getWyBrt());
        }
        ct(data.getItem(), helper, new OnCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider.1
            @Override // com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider.OnCallback
            public void onMoreAction() {
            }

            @Override // com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider.OnCallback
            public void onColorChanged(float xProgress, int color, boolean finish) {
                LightCtGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendCW(ActivityUtils.getTopActivity(), LightUtils.percent2C(xProgress), finish);
                LightCtGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
                if (data.getItem() instanceof Group) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(data.getItem().getObjectId());
                    groupByGroupId.getGroupState().setOn(true);
                    groupByGroupId.getGroupState().setOnlineState(1);
                    Injection.repo().group().saveGroup(groupByGroupId);
                    data.change(groupByGroupId);
                    return;
                }
                if ((data.getItem() instanceof Device) && position == 4 && finish) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(data.getItem().getObjectId());
                    deviceByDeviceId.getDeviceState().setOnlineState(1);
                    Injection.repo().device().saveDevice(deviceByDeviceId);
                    LightCtGroupSubExpandableItemProvider.this.mViewModel.updateStateGroup(deviceByDeviceId);
                }
            }

            @Override // com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider.OnCallback
            public void onBrtChanged(int progress, boolean finish) {
                LightCtGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendCtBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                LightCtGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
                if (data.getItem() instanceof Group) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(data.getItem().getObjectId());
                    groupByGroupId.getGroupState().setOn(true);
                    groupByGroupId.getGroupState().setWyBrt(progress);
                    groupByGroupId.getGroupState().setOnlineState(1);
                    Injection.repo().group().saveGroup(groupByGroupId);
                    data.change(groupByGroupId);
                    return;
                }
                if ((data.getItem() instanceof Device) && position == 4 && finish) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(data.getItem().getObjectId());
                    deviceByDeviceId.getDeviceState().setOnlineState(1);
                    Injection.repo().device().saveDevice(deviceByDeviceId);
                    LightCtGroupSubExpandableItemProvider.this.mViewModel.updateStateGroup(deviceByDeviceId);
                }
            }
        });
    }

    public void ct(Role role, BaseViewHolder helper, OnCallback callback) {
        initBrt(helper, callback);
        initCt(role, helper, callback);
    }

    private void initCt(Role role, BaseViewHolder helper, final OnCallback callback) {
        helper.setGone(R.id.civ_color, false);
        helper.setGone(R.id.tv_ct_percent, true);
        helper.setText(R.id.tv_color_tip, this.mContext.getString(R.string.ct));
        new CtControlHelper((RangeSeekBar) helper.getView(R.id.csb_color_bar), (AppCompatTextView) helper.getView(R.id.tv_ct_percent), role, new CtControlHelper.OnCtChangedListener(this) { // from class: com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider.2
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public void onRangeChanged(int v, boolean finish) {
                OnCallback onCallback = callback;
                if (onCallback != null) {
                    onCallback.onColorChanged(LightUtils.c2percent(v), 0, finish);
                }
            }
        });
    }

    private void initBrt(final BaseViewHolder helper, final OnCallback callback) {
        ((LightBrtBar) helper.getView(R.id.sb_brt)).setIncludeZero(this.RgbBrt == 0);
        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.LightCtGroupSubExpandableItemProvider.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                OnCallback onCallback;
                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                helper.setText(R.id.tv_brt, lightBrtBar.getProgressHasBelowOne());
                LightCtGroupSubExpandableItemProvider.this.RgbBrt = progress;
                LHomeLog.i(LightQuickDialog.class, "onProgressChanged=" + lightBrtBar.getProgressHasBelowOne());
                if (!fromUser || (onCallback = callback) == null) {
                    return;
                }
                onCallback.onBrtChanged(progress, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LightCtGroupSubExpandableItemProvider.this.RgbBrt = seekBar.getProgress();
                helper.setText(R.id.tv_brt, ((LightBrtBar) seekBar).getProgressHasBelowOne());
                OnCallback onCallback = callback;
                if (onCallback != null) {
                    onCallback.onBrtChanged(seekBar.getProgress(), true);
                }
            }
        });
        helper.setText(R.id.tv_brt, ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressHasBelowOne());
        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(this.RgbBrt);
    }

    public void setRgbBrt(int RgbBrt) {
        this.RgbBrt = RgbBrt;
    }

    public void setWy(int Wy) {
        this.Wy = Wy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }
}