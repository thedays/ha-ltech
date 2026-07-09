package com.ltech.smarthome.ui.device.mesh_gateway;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActMeshGatewayLightSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.AnnularColorPickView;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActMeshGatewayLightSetting extends VMActivity<ActMeshGatewayLightSettingBinding, ActMeshGatewayLightSettingVM> {
    private BaseQuickAdapter<Integer, BaseViewHolder> colorAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_mesh_gateway_light_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        setTitle(getString(R.string.app_str_ambient_light));
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).sbOn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActMeshGatewayLightSetting.this.lambda$initView$0(switchButton, z);
            }
        });
        setColorView();
        initRgbAnnular();
        initLightBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(SwitchButton switchButton, boolean z) {
        ((ActMeshGatewayLightSettingVM) this.mViewModel).getGatewayCmdHelper().sendAmbientLightOnOff(this, z, new IAction[0]);
        ((ActMeshGatewayLightSettingVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(z));
    }

    private void initLightBar() {
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).getGatewayCmdHelper().sendAmbientLightBrt(ActMeshGatewayLightSetting.this, ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).createSendData(1, seekBar.getProgress()), false);
                    ((ActMeshGatewayLightSettingBinding) ActMeshGatewayLightSetting.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).getGatewayCmdHelper().sendAmbientLightBrt(ActMeshGatewayLightSetting.this, ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).createSendData(1, seekBar.getProgress()), true);
                ((ActMeshGatewayLightSettingBinding) ActMeshGatewayLightSetting.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).tvBrt.setText(((ActMeshGatewayLightSettingBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMeshGatewayLightSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshGatewayLightSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActMeshGatewayLightSettingVM) this.mViewModel).stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMeshGatewayLightSetting.this.lambda$startObserve$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(device.getDeviceState().getRgbBrt()));
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).tvBrt.setText(((ActMeshGatewayLightSettingBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        ((ActMeshGatewayLightSettingVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).groupOpen.setVisibility(bool.booleanValue() ? 8 : 0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActMeshGatewayLightSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        final Device deviceById = Injection.repo().device().getDeviceById(((ActMeshGatewayLightSettingVM) this.mViewModel).controlId);
        ((ActMeshGatewayLightSettingVM) this.mViewModel).controlObject.setValue(deviceById);
        if (deviceById != null) {
            if (deviceById.getProductId().equals(ProductId.ID_MESH_GATEWAY)) {
                ((ActMeshGatewayLightSettingVM) this.mViewModel).getGatewayCmdHelper().queryAmbientLightStatus(this, new IAction() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActMeshGatewayLightSetting.this.lambda$onResume$3(deviceById, (ResponseMsg) obj);
                    }
                });
            } else {
                MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting$$ExternalSyntheticLambda2
                    @Override // com.smart.message.MessageManager.LightStatusCallBack
                    public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                        ActMeshGatewayLightSetting.this.lambda$onResume$4(deviceById, i, z, i2, i3, i4, z2, z3, z4, i5);
                    }
                });
                CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$3(Device device, ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "responseMsg->> " + responseMsg);
        if (responseMsg != null) {
            LHomeLog.i(getClass(), "responseMsg data ->> " + responseMsg.getResData());
            boolean z = ((ActMeshGatewayLightSettingVM) this.mViewModel).getBoolean(responseMsg.getResData().substring(0, 2));
            LHomeLog.i(getClass(), "responseMsg onoff ->> " + z);
            String substring = responseMsg.getResData().substring(2, 8);
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(8, 10), 16);
            ((ActMeshGatewayLightSettingBinding) this.mViewBinding).civColor.setImageDrawable(new ColorDrawable(Color.parseColor(MqttTopic.MULTI_LEVEL_WILDCARD + substring)));
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId());
            if (deviceByDeviceId != null) {
                deviceByDeviceId.getDeviceState().setOn(z);
                deviceByDeviceId.getDeviceState().setRgbBrt(parseInt);
                Injection.repo().device().saveDevice(deviceByDeviceId);
                ((ActMeshGatewayLightSettingVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$4(Device device, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Device deviceByDeviceId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId())) == null) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOn(z);
        deviceByDeviceId.getDeviceState().setRgbBrt(i4);
        Injection.repo().device().saveDevice(deviceByDeviceId);
        ((ActMeshGatewayLightSettingVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
    }

    private void initRgbAnnular() {
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting.2
            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                ((ActMeshGatewayLightSettingBinding) ActMeshGatewayLightSetting.this.mViewBinding).civColor.setImageDrawable(new ColorDrawable(color));
                ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).getGatewayCmdHelper().sendAmbientLightColor(ActMeshGatewayLightSetting.this, ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).createSendData(0, color), false);
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).getGatewayCmdHelper().sendAmbientLightColor(ActMeshGatewayLightSetting.this, ((ActMeshGatewayLightSettingVM) ActMeshGatewayLightSetting.this.mViewModel).createSendData(0, color), true);
            }
        });
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.pic_panel_rgb)).into((RequestBuilder<Bitmap>) new CustomViewTarget<AnnularColorPickView, Bitmap>(((ActMeshGatewayLightSettingBinding) this.mViewBinding).annularColorPickView) { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting.3
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
                ((ActMeshGatewayLightSettingBinding) ActMeshGatewayLightSetting.this.mViewBinding).annularColorPickView.setBitmapBack(resource);
            }
        });
    }

    private void setColorView() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, ((ActMeshGatewayLightSettingVM) this.mViewModel).getColorList(this)) { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        this.colorAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActMeshGatewayLightSetting.this.lambda$setColorView$5(baseQuickAdapter2, view, i);
            }
        });
        this.colorAdapter.bindToRecyclerView(((ActMeshGatewayLightSettingBinding) this.mViewBinding).rvColor);
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(this, 8));
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting.5
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorView$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActMeshGatewayLightSettingBinding) this.mViewBinding).civColor.setImageDrawable(new ColorDrawable(this.colorAdapter.getData().get(i).intValue()));
        ((ActMeshGatewayLightSettingVM) this.mViewModel).getGatewayCmdHelper().sendAmbientLightColor(this, ((ActMeshGatewayLightSettingVM) this.mViewModel).createSendData(0, this.colorAdapter.getData().get(i).intValue()), true);
    }
}