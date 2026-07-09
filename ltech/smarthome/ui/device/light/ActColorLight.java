package com.ltech.smarthome.ui.device.light;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.SeekBar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActColorLightBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectPhoto;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.VersionUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.ColorDotView;
import com.ltech.smarthome.view.ColorPickerPointView;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.ColorBrtControlDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.WyDialog;
import com.smart.message.MessageManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageHelper;
import com.yuyh.library.imgsel.config.ISListConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActColorLight extends BaseControlActivity<ActColorLightBinding, ActColorLightVM> {
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_WRITE_STORAGE = 3;
    private BaseQuickAdapter<GoItem, BaseViewHolder> actionAdapter;
    private List<GoItem> actionList;
    private File camerafile;
    private BaseQuickAdapter<Integer, BaseViewHolder> colorAdapter;
    private BaseQuickAdapter<ActColorLightVM.Item, BaseViewHolder> deviceAdpater;
    private Device mSeleDevice;
    private int number;
    private int selPos;
    private boolean wy_RGBon_off;
    private boolean wy_on_off;
    private int wy_brt1 = 1;
    private int w_brt1 = 1;
    private int wy1 = 1;
    private int rgb_brt = 1;
    int onNum = 0;
    int offNum = 0;
    int offline = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_color_light;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActColorLightVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActColorLightVM) this.mViewModel).colorType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 3);
        ((ActColorLightVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        if (isE6()) {
            setTitle(getString(R.string.knob_control));
        } else {
            setEditImage(R.mipmap.ic_setting);
        }
        initActionRv();
        initDevicesRv();
        setColorView();
        int i = ((ActColorLightVM) this.mViewModel).colorType;
        if (i == 4) {
            setRgbwView();
        } else if (i == 5) {
            setRgbwyView();
        } else {
            setRgbView();
        }
        ((ActColorLightBinding) this.mViewBinding).layoutCcpv.setTargetView(((ActColorLightBinding) this.mViewBinding).ccpv);
        ((ActColorLightBinding) this.mViewBinding).layoutCcpv2.setTargetView(((ActColorLightBinding) this.mViewBinding).ccpv2);
    }

    private void initDevicesRv() {
        ((ActColorLightBinding) this.mViewBinding).rvDevices.setLayoutManager(new LinearLayoutManager(this.activity, 0, false));
        RecyclerView recyclerView = ((ActColorLightBinding) this.mViewBinding).rvDevices;
        BaseQuickAdapter<ActColorLightVM.Item, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActColorLightVM.Item, BaseViewHolder>(R.layout.item_gradient_group_device) { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, final ActColorLightVM.Item item) {
                holder.setImageResource(R.id.iv, ProductRepository.getProductIcon(item.getDevice()));
                ((ColorDotView) holder.getView(R.id.color_dot_view)).setDotColor(item.getColor());
                holder.setText(R.id.tv, item.getDevice().getName());
                SwitchButton switchButton = (SwitchButton) holder.getView(R.id.sb);
                switchButton.setCheckedNotByUser(item.isOn());
                holder.setBackgroundRes(R.id.bg, item.isSel() ? R.drawable.shape_blue_stroke_bt_sel : R.drawable.shape_white_round_bg_10);
                switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.1.1
                    @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        item.setOn(isChecked);
                        ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(item.getDevice()).sendOnOff(ActColorLight.this, false);
                    }
                });
            }
        };
        this.deviceAdpater = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((ActColorLightBinding) this.mViewBinding).rvDevices.setHasFixedSize(true);
        ((ActColorLightBinding) this.mViewBinding).rvDevices.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = ConvertUtils.dp2px(5.0f);
                outRect.left = ConvertUtils.dp2px(5.0f);
            }
        });
        this.deviceAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (ActColorLight.this.selPos != i) {
                    ActColorLightVM.Item item = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(i);
                    item.setSel(true);
                    ActColorLight.this.deviceAdpater.setData(i, item);
                    ActColorLight.this.mSeleDevice = item.getDevice();
                    ActColorLightVM.Item item2 = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(ActColorLight.this.selPos);
                    item2.setSel(false);
                    ActColorLight.this.deviceAdpater.setData(ActColorLight.this.selPos, item2);
                    ActColorLight.this.selPos = i;
                    ((ActColorLightBinding) ActColorLight.this.mViewBinding).ccpv2.setSelectedPointIndex(i);
                }
            }
        });
    }

    private void initActionRv() {
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_light_action) { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_content, item.getMainText()).setImageResource(R.id.iv_icon, item.getImageRes());
            }
        };
        this.actionAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((GoItem) ActColorLight.this.actionAdapter.getData().get(position)).getAction().execute();
            }
        });
        this.actionAdapter.bindToRecyclerView(((ActColorLightBinding) this.mViewBinding).rvAction);
        ((DefaultItemAnimator) ((ActColorLightBinding) this.mViewBinding).rvAction.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActColorLightVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActColorLightVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorLight.this.lambda$startObserve$0(obj);
            }
        });
        ((ActColorLightVM) this.mViewModel).colorControlLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorLight.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        ((ActColorLightVM) this.mViewModel).showWyDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorLight.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActColorLightVM) this.mViewModel).showWDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorLight.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActColorLightVM) this.mViewModel).stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorLight.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        ((ActColorLightVM) this.mViewModel).stateOnUI.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActColorLight.this.onNum += ActColorLight.this.offNum;
                    ActColorLight.this.offNum = 0;
                } else {
                    ActColorLight actColorLight = ActColorLight.this;
                    actColorLight.offNum = actColorLight.onNum + ActColorLight.this.offNum;
                    ActColorLight.this.onNum = 0;
                }
                StringBuilder sb = new StringBuilder();
                if (ActColorLight.this.onNum > 0) {
                    sb.append(String.format(ActColorLight.this.getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(ActColorLight.this.onNum)));
                }
                if (ActColorLight.this.offNum > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActColorLight.this.getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(ActColorLight.this.offNum)));
                }
                if (ActColorLight.this.offline > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActColorLight.this.getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(ActColorLight.this.offline)));
                }
                ActColorLight.this.setSubTitle(sb.toString());
            }
        });
        ((ActColorLightVM) this.mViewModel).gradientMode.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActColorLight.this.setGradientActionView();
                } else {
                    ActColorLight.this.setActionView();
                }
            }
        });
        ((ActColorLightVM) this.mViewModel).colorBrtControlDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActColorLight.this.showColorBrt();
            }
        });
        ((ActColorLightVM) this.mViewModel).paletteBitmap.observe(this, new Observer<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    ((ActColorLightBinding) ActColorLight.this.mViewBinding).ccpv2.updateCircleBg(bitmap);
                }
            }
        });
        ((ActColorLightVM) this.mViewModel).showPhotoAlbumEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (ActColorLight.this.checkWriteStoragePermission(3)) {
                    ActColorLight.this.goPhotoSelect();
                }
            }
        });
        ((ActColorLightVM) this.mViewModel).showTakePicEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActColorLight.this.goCamera();
            }
        });
        ((ActColorLightVM) this.mViewModel).addGradientSceneEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.13
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActColorLight.this.showCreateGradientSceneDialog();
            }
        });
        ((ActColorLightVM) this.mViewModel).lightControlEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.14
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (Boolean.TRUE.equals(((ActColorLightVM) ActColorLight.this.mViewModel).gradientMode.getValue())) {
                    ActColorLightVM.Item item = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(ActColorLight.this.selPos);
                    if (item.isOn()) {
                        return;
                    }
                    item.setOn(true);
                    ActColorLight.this.deviceAdpater.setData(ActColorLight.this.selPos, item);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            this.wy_on_off = group.getGroupState().isWOn();
            this.wy_RGBon_off = group.getGroupState().isRGBOn();
            this.wy1 = group.getGroupState().getWy();
            this.wy_brt1 = group.getGroupState().getWyBrt();
            this.w_brt1 = group.getGroupState().getWyBrt();
            this.rgb_brt = group.getGroupState().getRgbBrt();
            ((ActColorLightBinding) this.mViewBinding).sbBrt.setProgress(this.rgb_brt);
            ((ActColorLightBinding) this.mViewBinding).tvBrt.setText(((ActColorLightBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
            if (!isE6()) {
                setTitle(group.getGroupName());
            }
            ((ActColorLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
            ((ActColorLightBinding) this.mViewBinding).ivGradient.setVisibility(0);
            setMultiColorPickerMode();
        } else {
            Device device = (Device) obj;
            this.wy_on_off = device.getDeviceState().isWOn();
            this.wy_RGBon_off = device.getDeviceState().isRGBOn();
            this.wy1 = device.getDeviceState().getWy();
            this.wy_brt1 = device.getDeviceState().getWyBrt();
            this.w_brt1 = device.getDeviceState().getWyBrt();
            this.rgb_brt = device.getDeviceState().getRgbBrt();
            ((ActColorLightBinding) this.mViewBinding).sbBrt.setProgress(this.rgb_brt);
            ((ActColorLightBinding) this.mViewBinding).tvBrt.setText(((ActColorLightBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
            if (!isE6()) {
                setTitle(device.getDeviceName());
            }
            ((ActColorLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
            ((ActColorLightBinding) this.mViewBinding).ivGradient.setVisibility(8);
        }
        setActionView();
        setSingleColorPickerMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        ((ActColorLightBinding) this.mViewBinding).ivCt.setImageResource(bool.booleanValue() ? R.mipmap.icon_light_ct : R.mipmap.icon_light_rgb);
        this.colorAdapter.setNewData(bool.booleanValue() ? ((ActColorLightVM) this.mViewModel).getColorList(this) : ((ActColorLightVM) this.mViewModel).getCtList(this));
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(bool.booleanValue() ? R.mipmap.bg_palette_1 : R.mipmap.bg_ct)).into((RequestBuilder<Bitmap>) new CustomViewTarget<ColorPickerPointView, Bitmap>(((ActColorLightBinding) this.mViewBinding).ccpv) { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.6
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActColorLightBinding) ActColorLight.this.mViewBinding).ccpv.updateCircleBg(resource);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showWyDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showWDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        int i = 0;
        if (bool.booleanValue()) {
            this.actionList.get(0).setImageRes(R.drawable.selector_power_on_bg_rgb);
        } else {
            this.actionList.get(0).setImageRes(R.drawable.selector_power_off_bg_rgb);
        }
        this.actionAdapter.setData(0, this.actionList.get(0));
        if (Boolean.TRUE.equals(((ActColorLightVM) this.mViewModel).gradientMode.getValue())) {
            for (ActColorLightVM.Item item : this.deviceAdpater.getData()) {
                item.setOn(bool.booleanValue());
                this.deviceAdpater.setData(i, item);
                i++;
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActColorLightVM) this.mViewModel).groupControl) {
            this.offline = 0;
            this.onNum = 0;
            this.offNum = 0;
            final Group groupById = Injection.repo().group().getGroupById(((ActColorLightVM) this.mViewModel).controlId);
            if (groupById != null) {
                if (!groupById.getDeviceIds().isEmpty()) {
                    if (groupById.getLeaderSup() == 1) {
                        MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda11
                            @Override // com.smart.message.MessageManager.LightStatusCallBack
                            public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                ActColorLight.this.lambda$startObjectObserve$5(groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                            }
                        });
                        CmdAssistant.getQueryCmdAssistant(groupById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
                    } else {
                        List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(groupById.getDeviceIds());
                        final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(groupById.getDeviceIds().get(0).longValue());
                        if (deviceByDeviceId != null) {
                            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda12
                                @Override // com.smart.message.MessageManager.LightStatusCallBack
                                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                    ActColorLight.this.lambda$startObjectObserve$6(deviceByDeviceId, groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                                }
                            });
                            CmdAssistant.getQueryCmdAssistant(deviceByDeviceId, new int[0]).queryLightState(ActivityUtils.getTopActivity());
                        }
                        for (Device device : devicesByIds) {
                            if (!device.getDeviceState().isOnline()) {
                                this.offline++;
                            } else if (device.getDeviceState().isOn()) {
                                this.onNum++;
                            } else {
                                this.offNum++;
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        if (this.onNum > 0) {
                            sb.append(String.format(getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(this.onNum)));
                        }
                        if (this.offNum > 0) {
                            if (sb.length() > 0) {
                                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                                sb.append(" ");
                            }
                            sb.append(String.format(getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(this.offNum)));
                        }
                        if (this.offline > 0) {
                            if (sb.length() > 0) {
                                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                                sb.append(" ");
                            }
                            sb.append(String.format(getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(this.offline)));
                        }
                        setSubTitle(sb.toString());
                    }
                }
                ((ActColorLightVM) this.mViewModel).controlObject.setValue(groupById);
                return;
            }
            return;
        }
        final Device deviceById = Injection.repo().device().getDeviceById(((ActColorLightVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActColorLightVM) this.mViewModel).controlObject.setValue(deviceById);
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda13
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                    ActColorLight.this.lambda$startObjectObserve$7(deviceById, i, z, i2, i3, i4, z2, z3, z4, i5);
                }
            });
            CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$5(Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (Boolean.TRUE.equals(((ActColorLightVM) this.mViewModel).gradientMode.getValue()) || i != group.getGroupAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        groupByGroupId.getGroupState().setWOn(i2 != 0);
        groupByGroupId.getGroupState().setRGBOn(i4 != 0);
        groupByGroupId.getGroupState().setWy(i3);
        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActColorLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$6(Device device, Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (Boolean.TRUE.equals(((ActColorLightVM) this.mViewModel).gradientMode.getValue()) || i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        groupByGroupId.getGroupState().setWOn(i2 != 0);
        groupByGroupId.getGroupState().setRGBOn(i4 != 0);
        groupByGroupId.getGroupState().setWy(i3);
        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActColorLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$7(Device device, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Device deviceByDeviceId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId())) == null) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOn(z);
        deviceByDeviceId.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        deviceByDeviceId.getDeviceState().setWOn(i2 != 0);
        deviceByDeviceId.getDeviceState().setWy(i3);
        deviceByDeviceId.getDeviceState().setRGBOn(i4 != 0);
        deviceByDeviceId.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        Injection.repo().device().saveDevice(deviceByDeviceId);
        ((ActColorLightVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
    }

    private void setRgbwyView() {
        setRgbView();
        ((ActColorLightBinding) this.mViewBinding).groupWy.setVisibility(0);
        ((ActColorLightBinding) this.mViewBinding).ivWy.setImageResource(R.mipmap.icon_light_cw);
    }

    private void setRgbwView() {
        setRgbView();
        ((ActColorLightBinding) this.mViewBinding).groupWy.setVisibility(0);
        ((ActColorLightBinding) this.mViewBinding).ivWy.setImageResource(R.mipmap.icon_light_w);
    }

    private void setRgbView() {
        setGradientRgbView();
        ((ActColorLightBinding) this.mViewBinding).ccpv.setOnColorChangedListener(new ColorPickerPointView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.15
            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorStarted(int color, ColorPickerPointView.PointInfo pointInfo, int selectedIndex) {
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onSelectedPosition(ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorChanged(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                ActColorLight.this.number++;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgb(ActColorLight.this, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onChangedFinish(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                if (ActColorLight.this.number > 10) {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgb(ActColorLight.this, color, true);
                } else {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgbDC(ActColorLight.this, color, true);
                }
                ActColorLight.this.number = 0;
            }
        });
        ((ActColorLightBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.16
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActColorLight.this, progress, false);
                    ((ActColorLightBinding) ActColorLight.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActColorLight.this, seekBar.getProgress(), true);
                ((ActColorLightBinding) ActColorLight.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActColorLightBinding) this.mViewBinding).tvBrt.setText(((ActColorLightBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    private void setGradientRgbView() {
        ((ActColorLightBinding) this.mViewBinding).ccpv2.setOnColorChangedListener(new ColorPickerPointView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.17
            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorStarted(int color, ColorPickerPointView.PointInfo pointInfo, int selectedIndex) {
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorChanged(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                ActColorLightVM.Item item = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(selectedPointIndex);
                item.setColor(color);
                item.setX(pointInfo.x);
                item.setY(pointInfo.y);
                ActColorLight.this.deviceAdpater.setData(selectedPointIndex, item);
                ActColorLight.this.number++;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendRgb(ActColorLight.this, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onChangedFinish(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                ((ActColorLightVM) ActColorLight.this.mViewModel).lightControlEvent.call();
                ActColorLightVM.Item item = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(selectedPointIndex);
                item.setColor(color);
                item.setX(pointInfo.x);
                item.setY(pointInfo.y);
                ActColorLight.this.deviceAdpater.setData(selectedPointIndex, item);
                if (ActColorLight.this.number > 10) {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendRgb(ActColorLight.this, color, true);
                } else {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendRgbDC(ActColorLight.this, color, true);
                }
                ActColorLight.this.number = 0;
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onSelectedPosition(ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                if (selectedPointIndex >= ActColorLight.this.deviceAdpater.getData().size() || ActColorLight.this.selPos == selectedPointIndex) {
                    return;
                }
                ActColorLightVM.Item item = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(selectedPointIndex);
                item.setSel(true);
                ActColorLight.this.deviceAdpater.setData(selectedPointIndex, item);
                ActColorLight.this.mSeleDevice = item.getDevice();
                ActColorLightVM.Item item2 = (ActColorLightVM.Item) ActColorLight.this.deviceAdpater.getData().get(ActColorLight.this.selPos);
                item2.setSel(false);
                ActColorLight.this.deviceAdpater.setData(ActColorLight.this.selPos, item2);
                ActColorLight.this.selPos = selectedPointIndex;
                ((ActColorLightBinding) ActColorLight.this.mViewBinding).rvDevices.scrollToPosition(selectedPointIndex);
            }
        });
    }

    private void setColorView() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, ((ActColorLightVM) this.mViewModel).getColorList(this)) { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.18
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        this.colorAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda19
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActColorLight.this.lambda$setColorView$8(baseQuickAdapter2, view, i);
            }
        });
        this.colorAdapter.bindToRecyclerView(((ActColorLightBinding) this.mViewBinding).rvColor);
        ((ActColorLightBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(this, 8));
        ((ActColorLightBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.19
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
        ((ActColorLightBinding) this.mViewBinding).rvColor.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorView$8(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActColorLightVM) this.mViewModel).getLightCmdHelper().sendRgbDC(this, this.colorAdapter.getData().get(i).intValue(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGradientActionView() {
        ArrayList arrayList = new ArrayList();
        this.actionList = arrayList;
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_power_off_bg_rgb).setMainText(getString(R.string.on_off)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorLight.this.lambda$setGradientActionView$9();
            }
        })));
        this.actionList.add(new GoItem().setImageRes(R.drawable.selector_collect_bg_rgb).setMainText(getString(R.string.collection)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorLight.this.lambda$setGradientActionView$10();
            }
        })));
        this.actionList.add(new GoItem().setImageRes(R.drawable.selector_scene_bg_rgb).setMainText(getString(R.string.app_str_gradient_scene)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorLight.this.lambda$setGradientActionView$11();
            }
        })));
        ((ActColorLightBinding) this.mViewBinding).rvAction.setLayoutManager(new GridLayoutManager(this, this.actionList.size()));
        this.actionAdapter.setNewData(this.actionList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGradientActionView$9() {
        if (Boolean.TRUE.equals(((ActColorLightVM) this.mViewModel).stateOn.getValue())) {
            ((ActColorLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this, false);
            ((ActColorLightVM) this.mViewModel).stateOn.setValue(false);
            if (((ActColorLightVM) this.mViewModel).groupControl) {
                ((ActColorLightVM) this.mViewModel).stateOnUI.setValue(false);
                return;
            }
            return;
        }
        ((ActColorLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this, true);
        ((ActColorLightVM) this.mViewModel).stateOn.setValue(true);
        if (((ActColorLightVM) this.mViewModel).groupControl) {
            ((ActColorLightVM) this.mViewModel).stateOnUI.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGradientActionView$10() {
        ((ActColorLightVM) this.mViewModel).addGradientSceneEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGradientActionView$11() {
        NavUtils.destination(ActGradientScene.class).withLong(Constants.CONTROL_ID, ((ActColorLightVM) this.mViewModel).controlId).withDefaultRequestCode().navigation(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreateGradientSceneDialog() {
        EditDialog.asDefault().setContent("").setTitle(getString(R.string.app_str_gradient_scene_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActColorLight.this.lambda$showCreateGradientSceneDialog$12((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreateGradientSceneDialog$12(String str) {
        ((ActColorLightVM) this.mViewModel).createGradientScene(str, this.deviceAdpater.getData());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setActionView() {
        ArrayList arrayList = new ArrayList();
        this.actionList = arrayList;
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_power_off_bg_rgb).setMainText(getString(R.string.on_off)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorLight.this.lambda$setActionView$13();
            }
        })));
        if (ProductRepository.supportDynamicMode(((ActColorLightVM) this.mViewModel).controlObject.getValue())) {
            this.actionList.add(new GoItem().setImageRes(R.drawable.selector_theme_bg).setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda15
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActColorLight.this.lambda$setActionView$14();
                }
            })));
            this.actionList.add(new GoItem().setImageRes(R.drawable.selector_model_bg).setMainText(getString(R.string.mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda16
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActColorLight.this.lambda$setActionView$15();
                }
            })));
        }
        if (!isE6()) {
            this.actionList.add(new GoItem().setImageRes(R.drawable.selector_music_bg).setMainText(getString(R.string.music)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda17
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActColorLight.this.lambda$setActionView$16();
                }
            })));
        }
        if (((ActColorLightVM) this.mViewModel).groupControl) {
            this.actionList.add(new GoItem().setImageRes(R.drawable.selector_group_bg).setMainText(getString(R.string.group_device)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda18
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActColorLight.this.lambda$setActionView$17();
                }
            })));
        }
        ((ActColorLightBinding) this.mViewBinding).rvAction.setLayoutManager(new GridLayoutManager(this, this.actionList.size()));
        this.actionAdapter.setNewData(this.actionList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$13() {
        if (Boolean.TRUE.equals(((ActColorLightVM) this.mViewModel).stateOn.getValue())) {
            ((ActColorLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this, false);
            ((ActColorLightVM) this.mViewModel).stateOn.setValue(false);
            if (((ActColorLightVM) this.mViewModel).groupControl) {
                ((ActColorLightVM) this.mViewModel).stateOnUI.setValue(false);
                return;
            }
            return;
        }
        ((ActColorLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this, true);
        ((ActColorLightVM) this.mViewModel).stateOn.setValue(true);
        if (((ActColorLightVM) this.mViewModel).groupControl) {
            ((ActColorLightVM) this.mViewModel).stateOnUI.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$14() {
        NavUtils.destination(ActDefaultMode.class).withLong(Constants.CONTROL_ID, ((ActColorLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActColorLightVM) this.mViewModel).groupControl).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActColorLightVM) this.mViewModel).controlObject.getValue())).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$15() {
        NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActColorLightVM) this.mViewModel).controlObject.getValue())).withLong(Constants.CONTROL_ID, ((ActColorLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActColorLightVM) this.mViewModel).groupControl).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$16() {
        NavUtils.destination(ActMusic.class).withLong(Constants.CONTROL_ID, ((ActColorLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActColorLightVM) this.mViewModel).groupControl).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$17() {
        NavUtils.destination(ActLightGroupSubItemControl.class).withLong(Constants.CONTROL_ID, ((ActColorLightVM) this.mViewModel).controlId).navigation(this);
    }

    private void showWyDialog() {
        WyDialog.wy().setWy(this.wy1).setBrtProgress(this.wy_brt1).setWOn(this.wy_on_off).setRgbOn(this.wy_RGBon_off).setStateChangedListener(new WyDialog.OnStateChangedListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.20
            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onRgbOnOffChanged(boolean on) {
                ActColorLight.this.wy_RGBon_off = on;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgbOnOff(ActColorLight.this, on);
            }

            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onWyOnOffChanged(boolean on) {
                ActColorLight.this.wy_on_off = on;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendWyOnOff(ActColorLight.this, on);
            }

            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onWyBrtChanged(int brtProgress, boolean finish) {
                ActColorLight.this.wy_brt1 = brtProgress;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendWyBrtHas1to9(ActColorLight.this, brtProgress, finish);
            }

            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onWyChanged(int wy, boolean finish) {
                ActColorLight.this.wy1 = wy;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendWy(ActColorLight.this, wy, finish);
            }
        }).showDialog(this);
    }

    private void showWDialog() {
        WyDialog.w().setBrtProgress(this.w_brt1).setWOn(this.wy_on_off).setRgbOn(this.wy_RGBon_off).setStateChangedListener(new WyDialog.OnStateChangedListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.21
            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onWyChanged(int wy, boolean finish) {
            }

            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onRgbOnOffChanged(boolean on) {
                ActColorLight.this.wy_RGBon_off = on;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendRgbOnOff(ActColorLight.this, on);
            }

            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onWyOnOffChanged(boolean on) {
                ActColorLight.this.wy_on_off = on;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendWOnOff(ActColorLight.this, on);
            }

            @Override // com.ltech.smarthome.view.dialog.WyDialog.OnStateChangedListener
            public void onWyBrtChanged(int brtProgress, boolean finish) {
                ActColorLight.this.w_brt1 = brtProgress;
                ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper().sendWHas1to9(ActColorLight.this, brtProgress, finish);
            }
        }).showDialog(this);
    }

    private void setSingleColorPickerMode() {
        if (((ActColorLightBinding) this.mViewBinding).ccpv.getPoints().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ((ActColorLightBinding) this.mViewBinding).ccpv.setTouchPointCircle(true);
            arrayList.add(new ColorPickerPointView.PointInfo(((ActColorLightBinding) this.mViewBinding).ccpv.getWidth() / 2.0f, ((ActColorLightBinding) this.mViewBinding).ccpv.getWidth() / 2.0f, 0, true));
            ((ActColorLightBinding) this.mViewBinding).ccpv.initData(arrayList);
        }
    }

    private void setMultiColorPickerMode() {
        if (((ActColorLightBinding) this.mViewBinding).ccpv2.getPoints().isEmpty()) {
            Object value = ((ActColorLightVM) this.mViewModel).controlObject.getValue();
            if (value instanceof Group) {
                Group group = (Group) value;
                ArrayList arrayList = new ArrayList();
                if (group.getDeviceIds().isEmpty()) {
                    ((ActColorLightBinding) this.mViewBinding).ivEmptyDevices.setVisibility(0);
                    ((ActColorLightBinding) this.mViewBinding).tvEmptyDevices.setVisibility(0);
                } else {
                    ((ActColorLightBinding) this.mViewBinding).ivEmptyDevices.setVisibility(8);
                    ((ActColorLightBinding) this.mViewBinding).tvEmptyDevices.setVisibility(8);
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<Long> it = group.getDeviceIds().iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                        if (deviceByDeviceId != null) {
                            if (i == 0) {
                                this.mSeleDevice = deviceByDeviceId;
                            }
                            arrayList2.add(new ActColorLightVM.Item(deviceByDeviceId, i == 0, deviceByDeviceId.getDeviceState().isOn()));
                            arrayList.add(new ColorPickerPointView.PointInfo(((ActColorLightBinding) this.mViewBinding).ccpv2.getWidth() / 2.0f, ((ActColorLightBinding) this.mViewBinding).ccpv2.getWidth() / 2.0f, ProductRepository.getProductIcon(deviceByDeviceId), i == 0));
                        }
                        i++;
                    }
                    this.deviceAdpater.setNewData(arrayList2);
                }
                setPaletteBg(group, arrayList);
            }
        }
    }

    private void setPaletteBg(Group group, final List<ColorPickerPointView.PointInfo> infos) {
        if (((ActColorLightVM) this.mViewModel).getColorPaletteType() == 3 && group.getColorPaletteUrl() != null) {
            ((ActColorLightVM) this.mViewModel).bgUrl = group.getColorPaletteUrl();
            Glide.with((FragmentActivity) this.activity).asBitmap().load(((ActColorLightVM) this.mViewModel).bgUrl).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.22
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(Drawable placeholder) {
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object bitmap, Transition transition) {
                    onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    ((ActColorLightBinding) ActColorLight.this.mViewBinding).ccpv2.initData(bitmap, infos);
                }
            });
        } else if (((ActColorLightVM) this.mViewModel).getColorPaletteType() == 2) {
            ((ActColorLightBinding) this.mViewBinding).ccpv2.initData(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_palette_2), infos);
        } else {
            ((ActColorLightBinding) this.mViewBinding).ccpv2.initData(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_palette_1), infos);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showColorBrt() {
        Object value = ((ActColorLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            ColorBrtControlDialog.asDefault(((ActColorLightVM) this.mViewModel).colorType).setRgbBrt(LightUtils.brt2ProgressHasBelowZero(group.getGroupState().getRgbBrt())).setCWBrt(LightUtils.brt2ProgressHasBelowZero(group.getGroupState().getWyBrt())).setCw(group.getGroupState().getWy()).setOnColorBrtCallBack(new ColorBrtControlDialog.OnColorBrtCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.23
                @Override // com.ltech.smarthome.view.dialog.ColorBrtControlDialog.OnColorBrtCallBack
                public void onBrt1Change(int brt, boolean finish) {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).lightControlEvent.call();
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendRgbBrtHas1to9(ActColorLight.this.activity, brt, finish);
                }

                @Override // com.ltech.smarthome.view.dialog.ColorBrtControlDialog.OnColorBrtCallBack
                public void onBrt2Change(int brt, boolean finish) {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).lightControlEvent.call();
                    if (((ActColorLightVM) ActColorLight.this.mViewModel).colorType == 4) {
                        ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendWHas1to9(ActColorLight.this.activity, brt, finish);
                    } else {
                        ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendWyBrtHas1to9(ActColorLight.this.activity, brt, finish);
                    }
                }

                @Override // com.ltech.smarthome.view.dialog.ColorBrtControlDialog.OnColorBrtCallBack
                public void onBrt3Change(int brt, boolean finish) {
                    ((ActColorLightVM) ActColorLight.this.mViewModel).lightControlEvent.call();
                    ((ActColorLightVM) ActColorLight.this.mViewModel).getLightCmdHelper(ActColorLight.this.mSeleDevice).sendWy(ActColorLight.this.activity, brt, finish);
                }
            }).showDialog(this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goCamera() {
        String[] strArr;
        Object value = ((ActColorLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            final Group group = (Group) value;
            if (VersionUtils.isAndroidQ()) {
                strArr = new String[]{Permission.CAMERA};
            } else {
                strArr = new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA};
            }
            AndPermission.with((Activity) this).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda9
                @Override // com.yanzhenjie.permission.Action
                public final void onAction(Object obj) {
                    ActColorLight.this.lambda$goCamera$18(group, (List) obj);
                }
            }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight$$ExternalSyntheticLambda10
                @Override // com.yanzhenjie.permission.Action
                public final void onAction(Object obj) {
                    ActColorLight.this.lambda$goCamera$19((List) obj);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$18(Group group, List list) {
        String cacheDir = PathManager.getCacheDir(this.activity);
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.getSuperPanelPicName(group.getGroupId() + ""));
        sb.append(System.currentTimeMillis());
        sb.append(".jpg");
        this.camerafile = new File(cacheDir, sb.toString());
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriForFile = FileProvider.getUriForFile(this, "com.ltech.smarthome.provider", this.camerafile);
        intent.addFlags(1);
        intent.putExtra("output", uriForFile);
        startActivityForResult(intent, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$19(List list) {
        SmartToast.showShort(getString(R.string.permission_deny));
    }

    private void goClipPhoto() {
        Object value = ((ActColorLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            NavUtils.destination(ActSuperPanelClipPhoto.class).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.GROUP_ID, group.getGroupId()).withString(Constants.MAC_ADDRESS, group.getGroupId() + "").withBoolean(Constants.ROUND_CUT, true).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goPhotoSelect() {
        Object value = ((ActColorLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(true).maxNum(1).rememberSelected(false).needCamera(false).checkRes(R.mipmap.ic_img_checked, R.mipmap.ic_img_uncheck).build());
            NavUtils.destination(ActSuperPanelSelectPhoto.class).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.GROUP_ID, group.getGroupId()).withString(Constants.MAC_ADDRESS, group.getGroupId() + "").withBoolean(Constants.ROUND_CUT, true).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        long[] longArrayExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && 100 == requestCode) {
            ImageHelper.imageList.clear();
            ImageHelper.imageList.add(this.camerafile.getAbsolutePath());
            ImageHelper.tempImageList.clear();
            ImageHelper.tempImageList.add(this.camerafile.getAbsolutePath());
            ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(false).maxNum(1).rememberSelected(false).needCamera(false).build());
            goClipPhoto();
            return;
        }
        if (resultCode == 3021) {
            if (data != null) {
                ((ActColorLightVM) this.mViewModel).bgUrl = data.getStringExtra("data");
                Glide.with((FragmentActivity) this.activity).asBitmap().load(((ActColorLightVM) this.mViewModel).bgUrl).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.ActColorLight.24
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable placeholder) {
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object bitmap, Transition transition) {
                        onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        ((ActColorLightBinding) ActColorLight.this.mViewBinding).ccpv2.updateCircleBg(bitmap);
                    }
                });
                return;
            }
            return;
        }
        if (resultCode != 5012 || data == null || (longArrayExtra = data.getLongArrayExtra("data")) == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (long j : longArrayExtra) {
            Iterator<ActColorLightVM.Item> it = this.deviceAdpater.getData().iterator();
            while (true) {
                if (it.hasNext()) {
                    ActColorLightVM.Item next = it.next();
                    if (j == next.getDevice().getDeviceId()) {
                        arrayList2.add(next);
                        arrayList.add(new ColorPickerPointView.PointInfo(next.getX(), next.getY(), ProductRepository.getProductIcon(next.getDevice()), next.isSel()));
                        break;
                    }
                }
            }
        }
        this.deviceAdpater.replaceData(arrayList2);
        ((ActColorLightBinding) this.mViewBinding).ccpv2.initData(arrayList);
    }
}