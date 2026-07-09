package com.ltech.smarthome.ui.control.provider;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.SeekBar;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.expand.LightGroupSubExpandableItem;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.LightQuickDialog;
import com.smart.message.utils.LHomeLog;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes4.dex */
public class LightGroupSubExpandableItemProvider extends BaseItemProvider<LightGroupSubExpandableItem, BaseViewHolder> {
    private int RgbBrt;
    private int Wy;
    private int color;
    private final ActLightGroupChildItemControlVM mViewModel;

    public interface OnCallback {
        void onBrtChanged(int progress, boolean finish);

        void onColorChanged(float xProgress, int color, boolean finish);

        void onMoreAction();
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_light_group_control_sub_device;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 1;
    }

    public LightGroupSubExpandableItemProvider(ActLightGroupChildItemControlVM viewModel) {
        this.mViewModel = viewModel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, final LightGroupSubExpandableItem data, final int position) {
        int lightColorType = ProductRepository.getLightColorType(data.getItem());
        if (lightColorType == 1) {
            if (data.getItem() instanceof Device) {
                setRgbBrt(((Device) data.getItem()).getDeviceState().getWyBrt());
            } else if (data.getItem() instanceof Group) {
                setRgbBrt(((Group) data.getItem()).getGroupState().getWyBrt());
            }
            dim(helper, new OnCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.3
                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onColorChanged(float xProgress, int color, boolean finish) {
                }

                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onMoreAction() {
                }

                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onBrtChanged(int progress, boolean finish) {
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendDimBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
                    if (data.getItem() instanceof Group) {
                        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(data.getItem().getObjectId());
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
                        LightGroupSubExpandableItemProvider.this.mViewModel.updateStateGroup(deviceByDeviceId);
                    }
                }
            });
            return;
        }
        if (lightColorType == 2) {
            if (data.getItem() instanceof Device) {
                Device device = (Device) data.getItem();
                setWy(device.getDeviceState().getWy());
                setRgbBrt(device.getDeviceState().getWyBrt());
            } else if (data.getItem() instanceof Group) {
                Group group = (Group) data.getItem();
                setWy(group.getGroupState().getWy());
                setRgbBrt(group.getGroupState().getWyBrt());
            }
            ct(helper, new OnCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.1
                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onMoreAction() {
                }

                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onColorChanged(float xProgress, int color, boolean finish) {
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendCW(ActivityUtils.getTopActivity(), 255 - LightUtils.percent2C(xProgress), finish);
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
                    if (data.getItem() instanceof Group) {
                        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(data.getItem().getObjectId());
                        groupByGroupId.getGroupState().setOn(true);
                        groupByGroupId.getGroupState().setWy(LightUtils.percent2C(xProgress));
                        groupByGroupId.getGroupState().setOnlineState(1);
                        Injection.repo().group().saveGroup(groupByGroupId);
                        data.change(groupByGroupId);
                    }
                }

                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onBrtChanged(int progress, boolean finish) {
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendCtBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
                    if (data.getItem() instanceof Group) {
                        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(data.getItem().getObjectId());
                        groupByGroupId.getGroupState().setOn(true);
                        groupByGroupId.getGroupState().setWyBrt(progress);
                        groupByGroupId.getGroupState().setOnlineState(1);
                        Injection.repo().group().saveGroup(groupByGroupId);
                        data.change(groupByGroupId);
                    }
                }
            });
            return;
        }
        if (lightColorType == 3 || lightColorType == 4 || lightColorType == 5 || lightColorType == 20) {
            if (data.getItem() instanceof Device) {
                Device device2 = (Device) data.getItem();
                setRgbBrt(device2.getDeviceState().getRgbBrt());
                setColor(Color.rgb(device2.getDeviceState().getRed(), device2.getDeviceState().getGreen(), device2.getDeviceState().getBlue()));
            } else if (data.getItem() instanceof Group) {
                Group group2 = (Group) data.getItem();
                setRgbBrt(group2.getGroupState().getRgbBrt());
                setColor(Color.rgb(group2.getGroupState().getRed(), group2.getGroupState().getGreen(), group2.getGroupState().getBlue()));
            }
            rgb(helper, new OnCallback() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.2
                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onMoreAction() {
                }

                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onColorChanged(float xProgress, int color, boolean finish) {
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendRgb(ActivityUtils.getTopActivity(), color, finish);
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
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
                        LightGroupSubExpandableItemProvider.this.mViewModel.updateStateGroup(deviceByDeviceId);
                    }
                }

                @Override // com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.OnCallback
                public void onBrtChanged(int progress, boolean finish) {
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).sendRgbBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
                    LightGroupSubExpandableItemProvider.this.getLightAssistant(data.getItem()).setState(true);
                    if (data.getItem() instanceof Group) {
                        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(data.getItem().getObjectId());
                        groupByGroupId.getGroupState().setOnlineState(1);
                        Injection.repo().group().saveGroup(groupByGroupId);
                        data.change(groupByGroupId);
                        return;
                    }
                    if ((data.getItem() instanceof Device) && position == 4 && finish) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(data.getItem().getObjectId());
                        deviceByDeviceId.getDeviceState().setOnlineState(1);
                        Injection.repo().device().saveDevice(deviceByDeviceId);
                        LightGroupSubExpandableItemProvider.this.mViewModel.updateStateGroup(deviceByDeviceId);
                    }
                }
            });
        }
    }

    public void ct(BaseViewHolder helper, OnCallback callback) {
        initBrt(helper, callback);
        initCt(helper, callback);
    }

    public void rgb(BaseViewHolder helper, OnCallback callback) {
        initBrt(helper, callback);
        initRgb(helper, callback);
    }

    public void dim(BaseViewHolder helper, OnCallback callback) {
        initBrt(helper, callback);
        helper.getView(R.id.group_color).setVisibility(8);
    }

    private void initRgb(final BaseViewHolder helper, final OnCallback callback) {
        helper.setText(R.id.tv_color_tip, this.mContext.getString(R.string.color));
        ((ColorSeekBar) helper.getView(R.id.csb_color_bar)).changeColorBar(BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.pic_rgb));
        ((ColorSeekBar) helper.getView(R.id.csb_color_bar)).post(new Runnable() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LightGroupSubExpandableItemProvider.this.lambda$initRgb$0(helper);
            }
        });
        ((ColorSeekBar) helper.getView(R.id.csb_color_bar)).setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener(this) { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.4
            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                OnCallback onCallback;
                ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(color));
                if (!isFromUser || (onCallback = callback) == null) {
                    return;
                }
                onCallback.onColorChanged(xProgress, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                OnCallback onCallback;
                ((CircleImageView) helper.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(color));
                if (!isFromUser || (onCallback = callback) == null) {
                    return;
                }
                onCallback.onColorChanged(xProgress, color, true);
            }
        });
        helper.getView(R.id.group_color).setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRgb$0(BaseViewHolder baseViewHolder) {
        ((CircleImageView) baseViewHolder.getView(R.id.civ_color)).setImageDrawable(new ColorDrawable(this.color));
    }

    private void initCt(final BaseViewHolder helper, final OnCallback callback) {
        helper.setGone(R.id.civ_color, false);
        helper.setGone(R.id.tv_ct_percent, true);
        helper.setText(R.id.tv_color_tip, this.mContext.getString(R.string.ct));
        ((ColorSeekBar) helper.getView(R.id.csb_color_bar)).changeColorBar(BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.pic_ct1));
        ((ColorSeekBar) helper.getView(R.id.csb_color_bar)).setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener(this) { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.5
            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                OnCallback onCallback;
                helper.setText(R.id.tv_ct_percent, String.format("%s", Integer.valueOf(LightUtils.percent2C(xProgress))));
                if (!isFromUser || (onCallback = callback) == null) {
                    return;
                }
                onCallback.onColorChanged(xProgress, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
                OnCallback onCallback;
                helper.setText(R.id.tv_ct_percent, String.format("%s", Integer.valueOf(LightUtils.percent2C(xProgress))));
                if (!isFromUser || (onCallback = callback) == null) {
                    return;
                }
                onCallback.onColorChanged(xProgress, color, true);
            }
        });
        ((ColorSeekBar) helper.getView(R.id.csb_color_bar)).setProgress(LightUtils.c2percent(this.Wy));
        helper.setText(R.id.tv_ct_percent, String.valueOf(this.Wy));
        helper.getView(R.id.group_color).setVisibility(0);
    }

    private void initBrt(final BaseViewHolder helper, final OnCallback callback) {
        ((LightBrtBar) helper.getView(R.id.sb_brt)).setIncludeZero(this.RgbBrt == 0);
        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.LightGroupSubExpandableItemProvider.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                OnCallback onCallback;
                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                helper.setText(R.id.tv_brt, lightBrtBar.getProgressHasBelowOne());
                LightGroupSubExpandableItemProvider.this.RgbBrt = progress;
                LHomeLog.i(LightQuickDialog.class, "onProgressChanged=" + lightBrtBar.getProgressHasBelowOne());
                if (!fromUser || (onCallback = callback) == null) {
                    return;
                }
                onCallback.onBrtChanged(progress, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LightGroupSubExpandableItemProvider.this.RgbBrt = seekBar.getProgress();
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

    public void setColor(int color) {
        this.color = color;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }
}