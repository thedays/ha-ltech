package com.ltech.smarthome.ui.device.microwave_sensor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActWaveSensorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectBrtDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.WaveSensorState;
import com.sun.jna.platform.win32.WinError;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActWaveSensor extends BaseControlActivity<ActWaveSensorBinding, ActWaveSensorVM> {
    private static int QUERY_STATE_STEP = 999;
    private boolean expand1;
    private boolean expand2;
    private boolean expand3;
    private boolean isMR04;
    private MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> mAdapter1;
    private MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> mAdapter2;
    private MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> mAdapter3;
    private boolean queryOver;
    private MutableLiveData<Integer> queryStep = new MutableLiveData<>();
    private boolean sendTimeFinish;

    static /* synthetic */ boolean lambda$showApplyDialog$19(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_wave_sensor;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActWaveSensorVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActWaveSensorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActWaveSensorVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActWaveSensorBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActWaveSensor.this.lambda$initView$1((View) obj);
            }
        }));
        this.expand1 = SharedPreferenceUtil.queryBooleanValue(Constants.EXPAND + ((ActWaveSensorVM) this.mViewModel).controlId + "1", false);
        this.expand2 = SharedPreferenceUtil.queryBooleanValue(Constants.EXPAND + ((ActWaveSensorVM) this.mViewModel).controlId + "2", false);
        this.expand3 = SharedPreferenceUtil.queryBooleanValue(Constants.EXPAND + ((ActWaveSensorVM) this.mViewModel).controlId + "3", false);
        MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> createAdapter = createAdapter();
        this.mAdapter1 = createAdapter;
        createAdapter.finishInitialize();
        this.mAdapter1.bindToRecyclerView(((ActWaveSensorBinding) this.mViewBinding).rvSensorParams1);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams1.setLayoutManager(new GridLayoutManager(this, 1));
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams1.setHasFixedSize(true);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams1.addItemDecoration(((ActWaveSensorVM) this.mViewModel).decoration);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams1.setVisibility(this.expand1 ? 0 : 8);
        AppCompatImageView appCompatImageView = ((ActWaveSensorBinding) this.mViewBinding).ivArrow1;
        boolean z = this.expand1;
        int i = R.mipmap.ic_up_gray;
        appCompatImageView.setImageResource(z ? R.mipmap.ic_up_gray : R.mipmap.ic_down_gray);
        MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> createAdapter2 = createAdapter();
        this.mAdapter2 = createAdapter2;
        createAdapter2.finishInitialize();
        this.mAdapter2.bindToRecyclerView(((ActWaveSensorBinding) this.mViewBinding).rvSensorParams2);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams2.setLayoutManager(new GridLayoutManager(this, 1));
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams2.setHasFixedSize(true);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams2.addItemDecoration(((ActWaveSensorVM) this.mViewModel).decoration);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams2.setVisibility(this.expand2 ? 0 : 8);
        ((ActWaveSensorBinding) this.mViewBinding).ivArrow2.setImageResource(this.expand2 ? R.mipmap.ic_up_gray : R.mipmap.ic_down_gray);
        ((ActWaveSensorBinding) this.mViewBinding).sbSensor2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z2) {
                ActWaveSensor.this.lambda$initView$2(switchButton, z2);
            }
        });
        MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> createAdapter3 = createAdapter();
        this.mAdapter3 = createAdapter3;
        createAdapter3.finishInitialize();
        this.mAdapter3.bindToRecyclerView(((ActWaveSensorBinding) this.mViewBinding).rvSensorParams3);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams3.setLayoutManager(new GridLayoutManager(this, 1));
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams3.setHasFixedSize(true);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams3.addItemDecoration(((ActWaveSensorVM) this.mViewModel).decoration);
        ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams3.setVisibility(this.expand3 ? 0 : 8);
        AppCompatImageView appCompatImageView2 = ((ActWaveSensorBinding) this.mViewBinding).ivArrow3;
        if (!this.expand3) {
            i = R.mipmap.ic_down_gray;
        }
        appCompatImageView2.setImageResource(i);
        ((ActWaveSensorBinding) this.mViewBinding).sbSensor3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z2) {
                ActWaveSensor.this.lambda$initView$3(switchButton, z2);
            }
        });
        ((ActWaveSensorBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActWaveSensorBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActWaveSensorBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActWaveSensorBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda7
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActWaveSensor.this.lambda$initView$4(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        long id;
        WaveSensorState waveSensorState;
        if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
            return;
        }
        int id2 = view.getId();
        int i = R.mipmap.ic_up_gray;
        switch (id2) {
            case R.id.bt_smart /* 2131296483 */:
                if (!((ActWaveSensorVM) this.mViewModel).groupControl || !((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
                    if (((ActWaveSensorVM) this.mViewModel).controlObject.getValue() instanceof Group) {
                        id = ((Group) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue()).getId();
                    } else {
                        id = ((Device) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue()).getId();
                    }
                    NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withString(Constants.AUTOMATION_NAME, getString(R.string.automation) + 1).withLong(Constants.CONTROL_ID, id).withBoolean(Constants.GROUP_CONTROL, ((ActWaveSensorVM) this.mViewModel).controlObject.getValue() instanceof Group).navigation(this);
                    break;
                }
                break;
            case R.id.iv_arrow_1 /* 2131296943 */:
                this.expand1 = !this.expand1;
                SharedPreferenceUtil.edit().keepShared(Constants.EXPAND + ((ActWaveSensorVM) this.mViewModel).controlId + "1", this.expand1);
                ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams1.setVisibility(this.expand1 ? 0 : 8);
                AppCompatImageView appCompatImageView = ((ActWaveSensorBinding) this.mViewBinding).ivArrow1;
                if (!this.expand1) {
                    i = R.mipmap.ic_down_gray;
                }
                appCompatImageView.setImageResource(i);
                break;
            case R.id.iv_arrow_2 /* 2131296944 */:
                this.expand2 = !this.expand2;
                SharedPreferenceUtil.edit().keepShared(Constants.EXPAND + ((ActWaveSensorVM) this.mViewModel).controlId + "2", this.expand2);
                ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams2.setVisibility(this.expand2 ? 0 : 8);
                AppCompatImageView appCompatImageView2 = ((ActWaveSensorBinding) this.mViewBinding).ivArrow2;
                if (!this.expand2) {
                    i = R.mipmap.ic_down_gray;
                }
                appCompatImageView2.setImageResource(i);
                break;
            case R.id.iv_arrow_3 /* 2131296945 */:
                this.expand3 = !this.expand3;
                SharedPreferenceUtil.edit().keepShared(Constants.EXPAND + ((ActWaveSensorVM) this.mViewModel).controlId + "3", this.expand3);
                ((ActWaveSensorBinding) this.mViewBinding).rvSensorParams3.setVisibility(this.expand3 ? 0 : 8);
                AppCompatImageView appCompatImageView3 = ((ActWaveSensorBinding) this.mViewBinding).ivArrow3;
                if (!this.expand3) {
                    i = R.mipmap.ic_down_gray;
                }
                appCompatImageView3.setImageResource(i);
                break;
            case R.id.iv_sensor_close /* 2131297246 */:
            case R.id.spreadview_sensor /* 2131298264 */:
                if (!((ActWaveSensorVM) this.mViewModel).groupControl) {
                    waveSensorState = ((Device) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue()).getDeviceState().getWaveSensorState();
                } else {
                    waveSensorState = ((Group) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue()).getGroupState().getWaveSensorState();
                }
                final boolean isEnable = waveSensorState.isEnable();
                showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
                CmdAssistant.getDeviceAssistant(((ActWaveSensorVM) this.mViewModel).controlObject.getValue(), new int[0]).setWaveEnable(ActivityUtils.getTopActivity(), !isEnable, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda16
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActWaveSensor.this.lambda$initView$0(isEnable, (ResponseMsg) obj);
                    }
                });
                break;
            case R.id.layout_illumilance /* 2131297494 */:
                goSetting(ActIlluminanceSetting.class);
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                goSetting(ActSensitivitySetting.class);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(boolean z, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && responseMsg.getStateCode() != 3) {
            if (!((ActWaveSensorVM) this.mViewModel).groupControl) {
                Device device = (Device) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
                device.getDeviceState().getWaveSensorState().setEnable(!z);
                Injection.repo().device().saveDevice(device);
                changeSensorView(device.getDeviceState().getWaveSensorState());
                return;
            }
            Group group = (Group) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
            group.getGroupState().getWaveSensorState().setEnable(!z);
            Injection.repo().group().saveGroup(group);
            changeSensorView(group.getGroupState().getWaveSensorState());
            return;
        }
        SmartToast.showShort(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(SwitchButton switchButton, boolean z) {
        setPeriodEnable(1, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(SwitchButton switchButton, boolean z) {
        setPeriodEnable(2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(RefreshLayout refreshLayout) {
        if (((ActWaveSensorVM) this.mViewModel).controlObject.getValue() != null) {
            startQueryState(((ActWaveSensorVM) this.mViewModel).controlObject.getValue());
        }
        ((ActWaveSensorBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    private void setPeriodEnable(final int index, final boolean enable) {
        showLoadingDialog(getString(R.string.saving));
        getCmdHelper(index).setPeriodEnable(this, index, enable, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$setPeriodEnable$6(index, enable, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPeriodEnable$6(final int i, boolean z, ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            if (i == 1) {
                ((ActWaveSensorBinding) this.mViewBinding).sbSensor2.setCheckedNotByUser(!z);
            } else if (i == 2) {
                ((ActWaveSensorBinding) this.mViewBinding).sbSensor3.setCheckedNotByUser(!z);
            }
        }
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                ((ActWaveSensorVM) this.mViewModel).mrExtParam.setEffectTimeEnable(i, z ? 1 : 0);
                ((ActWaveSensorVM) this.mViewModel).updateWaveSensorExt(((ActWaveSensorVM) this.mViewModel).mrExtParam);
                showSuccessDialog(getString(R.string.save_success));
                return;
            } else if (responseMsg.getStateCode() == 24) {
                MessageDialog.show(this, getString(R.string.set_fail), getString(R.string.sensor_period_enable_set_fail)).setOkButton(getString(R.string.go_to_setting), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda22
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$setPeriodEnable$5;
                        lambda$setPeriodEnable$5 = ActWaveSensor.this.lambda$setPeriodEnable$5(i, baseDialog, view);
                        return lambda$setPeriodEnable$5;
                    }
                }).setCancelButton(getString(R.string.ok)).show();
                dismissLoadingDialog();
                return;
            }
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setPeriodEnable$5(int i, BaseDialog baseDialog, View view) {
        String format = String.format(Locale.US, "%02d:%02d", Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeStartH(i)), Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeStartM(i)));
        String format2 = String.format(Locale.US, "%02d:%02d", Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEndH(i)), Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEndM(i)));
        String repeatString = ((ActWaveSensorVM) this.mViewModel).getRepeatString(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeRepeat(i));
        WaveSensorHelper.instance().index = i;
        WaveSensorHelper.instance().controlObject = ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
        WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam;
        NavUtils.destination(ActWaveSensorEffectPeriod.class).withString(Constants.START_TIME, format).withString(Constants.END_TIME, format2).withString(Constants.WEEKS, repeatString).withInt(Constants.WAVE_INDEX, i).withDefaultRequestCode().navigation(this);
        return false;
    }

    private MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> createAdapter() {
        return new MultipleItemRvAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ActWaveSensorVM.ParamItem paramItem) {
                return paramItem.type;
            }

            /* renamed from: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$1$1, reason: invalid class name and collision with other inner class name */
            class C01371 extends BaseItemProvider<ActWaveSensorVM.ParamItem, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_mr_param;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 0;
                }

                C01371() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final ActWaveSensorVM.ParamItem item, int i) {
                    helper.setText(R.id.title, item.title);
                    if (item.firstGoItem.getMainText().startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        String mainText = item.firstGoItem.getMainText();
                        int rgb = Color.rgb(Integer.parseInt(mainText.substring(1, 3), 16), Integer.parseInt(mainText.substring(3, 5), 16), Integer.parseInt(mainText.substring(5, 7), 16));
                        helper.setText(R.id.tv_main_1, mainText.substring(7)).setGone(R.id.civ_color_main, true);
                        ((CircleImageView) helper.getView(R.id.civ_color_main)).setImageDrawable(new ColorDrawable(rgb));
                    } else {
                        helper.setText(R.id.tv_main_1, item.firstGoItem.getMainText()).setGone(R.id.civ_color_main, false);
                    }
                    if (item.firstGoItem.getSubText() != null && item.firstGoItem.getSubText().startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                        String subText = item.firstGoItem.getSubText();
                        int rgb2 = Color.rgb(Integer.parseInt(subText.substring(1, 3), 16), Integer.parseInt(subText.substring(3, 5), 16), Integer.parseInt(subText.substring(5, 7), 16));
                        helper.setText(R.id.tv_sub_1, subText.substring(7)).setGone(R.id.civ_color_sub, true);
                        ((CircleImageView) helper.getView(R.id.civ_color_sub)).setImageDrawable(new ColorDrawable(rgb2));
                    } else {
                        helper.setText(R.id.tv_sub_1, item.firstGoItem.getSubText()).setGone(R.id.civ_color_sub, false);
                    }
                    helper.setGone(R.id.tv_sub_1, !TextUtils.isEmpty(item.firstGoItem.getSubText()));
                    helper.setImageResource(R.id.iv_icon_1, item.firstGoItem.getImageRes());
                    ((AppCompatTextView) helper.getView(R.id.tv_main_1)).getPaint().setFakeBoldText(true);
                    ((AppCompatTextView) helper.getView(R.id.tv_main_1)).setTextColor(ActWaveSensor.this.getResources().getColor(item.firstGoItem.isEnable() ? R.color.color_text_black : R.color.color_text_gray));
                    helper.getView(R.id.first_go).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$1$1$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ActWaveSensor.AnonymousClass1.C01371.lambda$convert$0(ActWaveSensorVM.ParamItem.this, view);
                        }
                    });
                    if (item.secondGoItem != null) {
                        helper.setVisible(R.id.second_go, true);
                        helper.setText(R.id.tv_main_2, item.secondGoItem.getMainText());
                        helper.setImageResource(R.id.iv_icon_2, item.secondGoItem.getImageRes());
                        ((AppCompatTextView) helper.getView(R.id.tv_main_2)).getPaint().setFakeBoldText(true);
                        helper.setText(R.id.tv_sub_2, item.secondGoItem.getSubText());
                        helper.setGone(R.id.tv_sub_2, !TextUtils.isEmpty(item.secondGoItem.getSubText()));
                        helper.getView(R.id.second_go).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$1$1$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActWaveSensor.AnonymousClass1.C01371.lambda$convert$1(ActWaveSensorVM.ParamItem.this, view);
                            }
                        });
                        return;
                    }
                    helper.setGone(R.id.second_go, false);
                }

                static /* synthetic */ void lambda$convert$0(ActWaveSensorVM.ParamItem paramItem, View view) {
                    if (paramItem.firstGoItem.getAction() != null) {
                        paramItem.firstGoItem.getAction().execute();
                    }
                }

                static /* synthetic */ void lambda$convert$1(ActWaveSensorVM.ParamItem paramItem, View view) {
                    if (paramItem.secondGoItem.getAction() != null) {
                        paramItem.secondGoItem.getAction().execute();
                    }
                }
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new C01371());
                this.mProviderDelegate.registerProvider(new AnonymousClass2());
            }

            /* renamed from: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$1$2, reason: invalid class name */
            class AnonymousClass2 extends BaseItemProvider<ActWaveSensorVM.ParamItem, BaseViewHolder> {
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_switch_button;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return 1;
                }

                AnonymousClass2() {
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, final ActWaveSensorVM.ParamItem item, int i) {
                    helper.setText(R.id.title, item.title);
                    ((AppCompatTextView) helper.getView(R.id.title)).getPaint().setFakeBoldText(true);
                    final SwitchButton switchButton = (SwitchButton) helper.getView(R.id.sb_function);
                    switchButton.setCheckedNotByUser(item.on);
                    switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$1$2$$ExternalSyntheticLambda1
                        @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                        public final void onCheckedChanged(SwitchButton switchButton2, boolean z) {
                            ActWaveSensor.AnonymousClass1.AnonymousClass2.this.lambda$convert$1(item, switchButton, switchButton2, z);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(final ActWaveSensorVM.ParamItem paramItem, final SwitchButton switchButton, SwitchButton switchButton2, final boolean z) {
                    ActWaveSensor.this.getCmdHelper(paramItem.index).setRelayState(ActWaveSensor.this, z, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$1$2$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActWaveSensor.AnonymousClass1.AnonymousClass2.this.lambda$convert$0(switchButton, paramItem, z, (Boolean) obj);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(SwitchButton switchButton, ActWaveSensorVM.ParamItem paramItem, boolean z, Boolean bool) {
                    if (bool.booleanValue()) {
                        ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).mrExtParam.setRelayAlwaysOn(paramItem.index, z ? 1 : 0);
                        ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).updateWaveSensorExt(((ActWaveSensorVM) ActWaveSensor.this.mViewModel).mrExtParam);
                    } else {
                        switchButton.setCheckedNotByUser(false);
                    }
                }
            }
        };
    }

    private BaseQuickAdapter<ActWaveSensorVM.ParamItem, BaseViewHolder> getAdapter(int index) {
        if (index == 0) {
            return this.mAdapter1;
        }
        if (index == 1) {
            return this.mAdapter2;
        }
        return this.mAdapter3;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActWaveSensorVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda24
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensor.this.lambda$startObserve$7(obj);
            }
        });
        this.queryStep.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda25
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensor.this.lambda$startObserve$8((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            WaveSensorState waveSensorState = group.getGroupState().getWaveSensorState();
            if (group.getDeviceIds().isEmpty()) {
                waveSensorState.setEnable(false);
            }
            refreshSensorState(waveSensorState);
            if (group.getExtParam() != null && ((ActWaveSensorVM) this.mViewModel).mrExtParam == null) {
                ((ActWaveSensorVM) this.mViewModel).mrExtParam = new WaveSensorExtParam();
                ((ActWaveSensorVM) this.mViewModel).mrExtParam.fillMapWithString(group.getExtParam());
            }
        } else {
            Device device = (Device) obj;
            ((ActWaveSensorVM) this.mViewModel).offline.setValue(Boolean.valueOf(!device.getDeviceState().isOnline()));
            refreshSensorState(device.getDeviceState().getWaveSensorState());
            if (device.getExtParam() != null && ((ActWaveSensorVM) this.mViewModel).mrExtParam == null) {
                ((ActWaveSensorVM) this.mViewModel).mrExtParam = new WaveSensorExtParam();
                ((ActWaveSensorVM) this.mViewModel).mrExtParam.fillMapWithString(device.getExtParam());
            }
        }
        if (((ActWaveSensorVM) this.mViewModel).mrExtParam == null) {
            ((ActWaveSensorVM) this.mViewModel).mrExtParam = new WaveSensorExtParam();
        }
        initParamList(0);
        initParamList(1);
        initParamList(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Integer num) {
        if (num.intValue() == QUERY_STATE_STEP) {
            startQueryState(((ActWaveSensorVM) this.mViewModel).controlObject.getValue());
            return;
        }
        if (num.intValue() >= 0 && num.intValue() <= 2) {
            querySensorParams(((ActWaveSensorVM) this.mViewModel).controlObject.getValue(), num.intValue());
        } else {
            if (num.intValue() <= 2 || this.sendTimeFinish) {
                return;
            }
            CmdAssistant.getDeviceAssistant(((ActWaveSensorVM) this.mViewModel).controlObject.getValue(), new int[0]).setTimeToSensor(this);
            this.sendTimeFinish = true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActWaveSensorVM) this.mViewModel).groupControl) {
            final Group groupById = Injection.repo().group().getGroupById(((ActWaveSensorVM) this.mViewModel).controlId);
            if (groupById != null) {
                setTitle(groupById.getGroupName());
                this.isMR04 = ProductId.BLE_GROUP_WAVE_SENSOR_MR04.equals(groupById.getGroupKey());
                ((ActWaveSensorVM) this.mViewModel).controlObject.setValue(groupById);
                this.queryStep.setValue(Integer.valueOf(QUERY_STATE_STEP));
                MessageManager.getInstance().setWaveSensorStatusCallBack(new MessageManager.WaveSensorStatusCallBack() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda9
                    @Override // com.smart.message.MessageManager.WaveSensorStatusCallBack
                    public final void onDataReceive(ResponseMsg responseMsg) {
                        ActWaveSensor.this.lambda$startObjectObserve$9(groupById, responseMsg);
                    }
                });
                return;
            }
            return;
        }
        final Device deviceById = Injection.repo().device().getDeviceById(((ActWaveSensorVM) this.mViewModel).controlId);
        if (deviceById != null) {
            setTitle(deviceById.getDeviceName());
            this.isMR04 = ProductId.ID_SENSOR_MR04.equals(deviceById.getProductId());
            ((ActWaveSensorVM) this.mViewModel).controlObject.setValue(deviceById);
            this.queryStep.setValue(Integer.valueOf(QUERY_STATE_STEP));
            MessageManager.getInstance().setWaveSensorStatusCallBack(new MessageManager.WaveSensorStatusCallBack() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda10
                @Override // com.smart.message.MessageManager.WaveSensorStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActWaveSensor.this.lambda$startObjectObserve$10(deviceById, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$9(Group group, ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId != null && parseInt == ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress()) {
                Group group2 = (Group) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
                ((ActWaveSensorVM) this.mViewModel).convertState(responseMsg.getResData(), group2.getGroupState().getWaveSensorState());
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group2.getGroupId());
                if (groupByGroupId != null) {
                    groupByGroupId.getGroupState().setWaveSensorState(group2.getGroupState().getWaveSensorState());
                    Injection.repo().group().saveGroup(groupByGroupId);
                    ((ActWaveSensorVM) this.mViewModel).controlObject.setValue(groupByGroupId);
                }
                refreshSensorState(group2.getGroupState().getWaveSensorState());
                if (this.queryStep.getValue().intValue() == QUERY_STATE_STEP) {
                    this.queryStep.setValue(0);
                    return;
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$10(Device device, ResponseMsg responseMsg) {
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) == ((BleParam) device.getParam(BleParam.class)).getUnicastAddress()) {
            this.queryOver = true;
            Device device2 = (Device) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
            ((ActWaveSensorVM) this.mViewModel).convertState(responseMsg.getResData(), device2.getDeviceState().getWaveSensorState());
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device2.getDeviceId());
            if (deviceByDeviceId != null) {
                deviceByDeviceId.getDeviceState().setOnlineState(1);
                deviceByDeviceId.getDeviceState().setWaveSensorState(device2.getDeviceState().getWaveSensorState());
                Injection.repo().device().saveDevice(deviceByDeviceId);
                ((ActWaveSensorVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
            }
            refreshSensorState(device2.getDeviceState().getWaveSensorState());
            if (this.queryStep.getValue().intValue() == QUERY_STATE_STEP) {
                this.queryStep.setValue(0);
            }
        }
    }

    private void startQueryState(Object object) {
        CmdAssistant.getQueryCmdAssistant(object, new int[0]).queryWaveSensorState(this);
        if (((ActWaveSensorVM) this.mViewModel).groupControl) {
            return;
        }
        this.queryOver = false;
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                ActWaveSensor.this.lambda$startQueryState$11();
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startQueryState$11() {
        if (this.queryOver) {
            return;
        }
        Device device = (Device) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
        device.getDeviceState().setOnlineState(2);
        Injection.repo().device().saveDevice(device);
        ((ActWaveSensorVM) this.mViewModel).controlObject.setValue(device);
        this.queryOver = true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam;
        NavHelper.goSetting(((ActWaveSensorVM) this.mViewModel).controlObject.getValue());
    }

    private void goSetting(Class clz) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
            return;
        }
        NavUtils.Builder destination = NavUtils.destination(clz);
        if (((ActWaveSensorVM) this.mViewModel).groupControl) {
            destination.withLong(Constants.CONTROL_ID, ((Group) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue()).getId()).withBoolean(Constants.GROUP_CONTROL, true);
        } else {
            destination.withLong(Constants.CONTROL_ID, ((Device) ((ActWaveSensorVM) this.mViewModel).controlObject.getValue()).getId()).withBoolean(Constants.GROUP_CONTROL, false);
        }
        destination.navigation(this);
    }

    public void querySensorParams(final Object object, final int index) {
        getCmdHelper(index).querySensorParams(this, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$querySensorParams$12(index, object, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySensorParams$12(int i, Object obj, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            String resData = responseMsg.getResData();
            int parseInt = Integer.parseInt(responseMsg.getDeviceFlag(), 16);
            if (Integer.parseInt(responseMsg.getResData().substring(4, 6), 16) == (1 << i)) {
                if (obj instanceof Device) {
                    if (parseInt == ((BleParam) ((Device) obj).getParam(BleParam.class)).getUnicastAddress()) {
                        ((ActWaveSensorVM) this.mViewModel).convertParams(resData, i);
                        initParamList(i);
                        this.queryStep.setValue(Integer.valueOf(i + 1));
                        return;
                    }
                    return;
                }
                if (parseInt == ((Group) obj).getGroupAddress()) {
                    ((ActWaveSensorVM) this.mViewModel).convertParams(resData, i);
                    initParamList(i);
                    this.queryStep.setValue(Integer.valueOf(i + 1));
                }
            }
        }
    }

    private void changeSensorView(WaveSensorState sensorState) {
        if (this.mViewBinding != 0) {
            ((ActWaveSensorBinding) this.mViewBinding).setStateOn(Boolean.valueOf(sensorState.isEnable()));
            if (sensorState.isEnable()) {
                ((ActWaveSensorBinding) this.mViewBinding).tvState.setText(getResources().getStringArray(R.array.wave_sensor_state)[sensorState.getCurState()]);
                ((ActWaveSensorBinding) this.mViewBinding).spreadviewSensor.setAnimate(false);
                ((ActWaveSensorBinding) this.mViewBinding).spreadviewSensor.requestLayout();
                ((ActWaveSensorBinding) this.mViewBinding).spreadviewSensor.changeSpreadColor(getResources().getIntArray(R.array.wave_sensor_color)[sensorState.getCurState()]);
                ((ActWaveSensorBinding) this.mViewBinding).spreadviewSensor.setAnimate(true);
            } else {
                ((ActWaveSensorBinding) this.mViewBinding).tvState.setText(getString(R.string.state_close));
            }
            if (((ActWaveSensorVM) this.mViewModel).offline.getValue().booleanValue()) {
                ((ActWaveSensorBinding) this.mViewBinding).setStateOn(false);
                ((ActWaveSensorBinding) this.mViewBinding).tvState.setText(getString(R.string.offline));
            }
        }
    }

    private void refreshSensorState(WaveSensorState sensorState) {
        if (this.mViewBinding == 0) {
            return;
        }
        changeSensorView(sensorState);
        if (sensorState.getIlluminance() == 0) {
            ((ActWaveSensorBinding) this.mViewBinding).setIlluminance(getString(R.string.illuminance_value_disable));
        } else {
            ((ActWaveSensorBinding) this.mViewBinding).setIlluminance(getResources().getStringArray(R.array.wave_sensor_illuminance)[sensorState.getIlluminance() - 1]);
        }
        ((ActWaveSensorBinding) this.mViewBinding).setSensitivity(getResources().getStringArray(R.array.wave_sensor_sensitivity)[sensorState.getSensitivity()]);
    }

    private void initParamList(int index) {
        if (index == 0) {
            ((ActWaveSensorBinding) this.mViewBinding).tvTime1.setText(getPeriodTime(0));
        } else if (index == 1) {
            ((ActWaveSensorBinding) this.mViewBinding).sbSensor2.setCheckedNotByUser(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEnable(index) != 0);
            ((ActWaveSensorBinding) this.mViewBinding).tvTime2.setText(getPeriodTime(1));
        } else if (index == 2) {
            ((ActWaveSensorBinding) this.mViewBinding).sbSensor3.setCheckedNotByUser(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEnable(index) != 0);
            ((ActWaveSensorBinding) this.mViewBinding).tvTime3.setText(getPeriodTime(2));
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(createApplyItem(index));
        arrayList.add(createDelayItem(index));
        if ((((ActWaveSensorVM) this.mViewModel).mrExtParam.getStayParam(index).destAddress != 0 && ((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index).actionType != 0) || ((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index).actionType == 4) {
            arrayList.add(createStayItem(index));
        }
        arrayList.add(createExitItem(index));
        arrayList.add(createValidItem(index));
        if (WaveSensorHelper.instance().isSuppportRelaySet(index, ((ActWaveSensorVM) this.mViewModel).mrExtParam)) {
            arrayList.add(createRelayItem(index));
        }
        getAdapter(index).setNewData(arrayList);
    }

    public ActWaveSensorVM.ParamItem createApplyItem(final int index) {
        return ActWaveSensorVM.ParamItem.paramOne(getString(R.string.conditions_met_triggered_on), new GoItem().setMainText(getApplyStr(((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index))).setSubText(((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index).actionType == 255 ? null : getContentStr(((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index))).setImageRes(R.mipmap.icon_mr_someone).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActWaveSensor.this.lambda$createApplyItem$13(index);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createApplyItem$13(int i) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
        } else {
            if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
                return;
            }
            showApplyDialog(i, 1);
        }
    }

    public ActWaveSensorVM.ParamItem createDelayItem(final int index) {
        return ActWaveSensorVM.ParamItem.paramOne(getString(R.string.triggered_nobody_delay), new GoItem().setMainText(getTimeStr(((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index))).setImageRes(R.mipmap.icon_mr_delaytime).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda26
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActWaveSensor.this.lambda$createDelayItem$14(index);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDelayItem$14(int i) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
        } else {
            if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
                return;
            }
            showSelectTimeDialog(i, ((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(i).delayTime, 1);
        }
    }

    public ActWaveSensorVM.ParamItem createStayItem(final int index) {
        String applyStr;
        final WaveSensorExtParam.SensorParam stayParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam.getStayParam(index);
        String str = null;
        if (((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index).actionType != 4) {
            applyStr = getContentStr(stayParam);
        } else {
            applyStr = getApplyStr(stayParam);
            if (stayParam.actionType != 255) {
                str = getContentStr(stayParam);
            }
        }
        return ActWaveSensorVM.ParamItem.paramTwo(getString(R.string.delay_end_start_stay), new GoItem().setMainText(applyStr).setSubText(str).setImageRes(R.mipmap.icon_mr_bright).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActWaveSensor.this.lambda$createStayItem$15(index, stayParam);
            }
        })), new GoItem().setMainText(getString(R.string.stay_time_1)).setSubText(getTimeStr(stayParam)).setImageRes(R.mipmap.icon_mr_standbyt).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActWaveSensor.this.lambda$createStayItem$16(index);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createStayItem$15(int i, WaveSensorExtParam.SensorParam sensorParam) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
            return;
        }
        if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
            return;
        }
        if (((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(i).actionType == 4) {
            showApplyDialog(i, 2);
            return;
        }
        if (sensorParam.actionType == 1 || sensorParam.actionType == 2) {
            showBrtDialog(sensorParam.actionType, sensorParam.actionPart, LightUtils.brt2PercentHasBelowZero(sensorParam.optionValue));
        } else if (sensorParam.actionType == 3) {
            goActionSetting(i, sensorParam, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createStayItem$16(int i) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
        } else {
            if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
                return;
            }
            showSelectStayTimeDialog(i, ((ActWaveSensorVM) this.mViewModel).mrExtParam.getStayParam(i).delayTime, 2);
        }
    }

    public ActWaveSensorVM.ParamItem createExitItem(final int index) {
        String applyStr;
        final boolean z;
        final WaveSensorExtParam.SensorParam exitParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam.getExitParam(index);
        final int i = ((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index).actionType;
        int i2 = ((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(index).actionType;
        int i3 = R.string.light_off;
        boolean z2 = false;
        if (i2 == 4) {
            applyStr = getApplyStr(exitParam);
            if (i < 3) {
                r2 = getString(R.string.light_off);
                z = false;
            } else {
                r2 = exitParam.actionType != 255 ? getContentStr(exitParam) : null;
                if (i == 3) {
                    z2 = ((ActWaveSensorVM) this.mViewModel).isExitEnable(exitParam);
                } else {
                    z = true;
                }
            }
            return ActWaveSensorVM.ParamItem.paramOne(getString(R.string.end_and_start_noone), new GoItem().setMainText(applyStr).setSubText(r2).setEnable(z).setImageRes(R.mipmap.icon_mr_noone).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActWaveSensor.this.lambda$createExitItem$17(i, index, exitParam, z);
                }
            })));
        }
        if (i < 3) {
            if (i == 0) {
                i3 = R.string.off_1;
            }
            applyStr = getString(i3);
        } else {
            applyStr = getContentStr(exitParam);
            if (i == 3) {
                z2 = ((ActWaveSensorVM) this.mViewModel).isExitEnable(exitParam);
            } else if (i != 255) {
                z2 = true;
            }
        }
        z = z2;
        return ActWaveSensorVM.ParamItem.paramOne(getString(R.string.end_and_start_noone), new GoItem().setMainText(applyStr).setSubText(r2).setEnable(z).setImageRes(R.mipmap.icon_mr_noone).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActWaveSensor.this.lambda$createExitItem$17(i, index, exitParam, z);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createExitItem$17(int i, int i2, WaveSensorExtParam.SensorParam sensorParam, boolean z) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
            return;
        }
        if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
            return;
        }
        if (i == 4) {
            showApplyDialog(i2, 3);
        } else if (sensorParam.actionType == 3 && z) {
            goActionSetting(i2, sensorParam, 3);
        }
    }

    public ActWaveSensorVM.ParamItem createValidItem(final int index) {
        final String format = String.format(Locale.US, "%02d:%02d", Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeStartH(index)), Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeStartM(index)));
        final String format2 = String.format(Locale.US, "%02d:%02d", Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEndH(index)), Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEndM(index)));
        final String repeatString = ((ActWaveSensorVM) this.mViewModel).getRepeatString(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeRepeat(index));
        return ActWaveSensorVM.ParamItem.paramOne(getString(R.string.sensor_effective_period), new GoItem().setMainText(format + " - " + format2).setSubText(HelpUtils.getWeeksString(this, repeatString)).setImageRes(R.mipmap.icon_mr_validperiod).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActWaveSensor.this.lambda$createValidItem$18(index, format, format2, repeatString);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createValidItem$18(int i, String str, String str2, String str3) {
        if (!((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActWaveSensorVM) this.mViewModel).getCurrentPlace().isManager()) {
            showNoPermissionDialog();
            return;
        }
        if (((ActWaveSensorVM) this.mViewModel).groupControl && ((ActWaveSensorVM) this.mViewModel).isGroupEmpty()) {
            return;
        }
        WaveSensorHelper.instance().index = i;
        WaveSensorHelper.instance().controlObject = ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
        WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam;
        NavUtils.destination(ActWaveSensorEffectPeriod.class).withString(Constants.START_TIME, str).withString(Constants.END_TIME, str2).withString(Constants.WEEKS, str3).withInt(Constants.WAVE_INDEX, i).withDefaultRequestCode().navigation(this);
    }

    private String getPeriodTime(int index) {
        return String.format(Locale.US, "(%s-%s)", String.format(Locale.US, "%02d:%02d", Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeStartH(index)), Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeStartM(index))), String.format(Locale.US, "%02d:%02d", Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEndH(index)), Integer.valueOf(((ActWaveSensorVM) this.mViewModel).mrExtParam.getEffectTimeEndM(index))));
    }

    public ActWaveSensorVM.ParamItem createRelayItem(int index) {
        return ActWaveSensorVM.ParamItem.paramThree(index, getString(R.string.relay_always_on), ((ActWaveSensorVM) this.mViewModel).mrExtParam.getRelayAlwaysOn(index) != 0);
    }

    public String[] getApplicationNames() {
        return getResources().getStringArray(R.array.wave_sensor_application);
    }

    private String getApplyStr(WaveSensorExtParam.SensorParam param) {
        if (param.actionType == 3) {
            return ((ActWaveSensorVM) this.mViewModel).getRelateName(param);
        }
        if (param.destAddress != 0) {
            if (param.actionType == 255) {
                return getApplicationNames()[5];
            }
            return getApplicationNames()[param.actionType];
        }
        return getString(R.string.please_choose);
    }

    private String getContentStr(WaveSensorExtParam.SensorParam param) {
        int i = param.actionType;
        if (i == 0) {
            if (param.destAddress != 0) {
                return "1".equals(param.option) ? getString(R.string.on_1) : getString(R.string.off_1);
            }
            return getString(R.string.please_choose);
        }
        if (i == 1 || i == 2) {
            int brt2PercentHasBelowZero = LightUtils.brt2PercentHasBelowZero(param.optionValue);
            if (param.actionPart == 2) {
                brt2PercentHasBelowZero = Math.min(brt2PercentHasBelowZero, 50);
            }
            if (brt2PercentHasBelowZero != 0) {
                return String.format(Locale.US, getString(R.string.brt) + "%d%%", Integer.valueOf(brt2PercentHasBelowZero));
            }
            return getString(R.string.light_off);
        }
        if (i == 3) {
            return ((ActWaveSensorVM) this.mViewModel).getRelateContent(param);
        }
        if (i == 4) {
            for (Scene scene : Injection.repo().scene().getSceneListByPlaceId(((ActWaveSensorVM) this.mViewModel).placeId, true)) {
                if (scene.getSceneId() == param.objectId) {
                    return scene.getSceneName();
                }
            }
        } else if (i == 255) {
            return getString(R.string.application_no_action);
        }
        return getString(R.string.please_choose);
    }

    private String getTimeStr(WaveSensorExtParam.SensorParam param) {
        if (param.delayTime == 65535) {
            return getString(R.string.mr_stay_time_11);
        }
        return String.format(Locale.US, "%02d" + getString(R.string.min_new) + " %02d" + getString(R.string.sec), Integer.valueOf(param.delayTime / 60), Integer.valueOf(param.delayTime % 60));
    }

    private void goActionSetting(int index, WaveSensorExtParam.SensorParam param, int dataType) {
        ((ActWaveSensorVM) this.mViewModel).getRelateObject(param);
        WaveSensorHelper.instance().index = index;
        WaveSensorHelper.instance().controlObject = ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
        WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam;
        WaveSensorHelper.instance().setDataType = dataType;
        WaveSensorHelper.instance().goSelectAction(this, ((ActWaveSensorVM) this.mViewModel).relateObject);
    }

    private void showApplyDialog(final int index, final int dataType) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.application_1));
        if (!this.isMR04) {
            arrayList.add(getString(R.string.application_2));
            arrayList.add(getString(R.string.application_3));
        }
        arrayList.add(getString(R.string.application_4));
        arrayList.add(getString(R.string.application_5));
        arrayList.add(getString(R.string.application_no_action));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$showApplyDialog$20(index, dataType, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showApplyDialog$20(int i, final int i2, Integer num) {
        WaveSensorHelper.instance().index = i;
        if (this.isMR04 && num.intValue() != 0) {
            num = Integer.valueOf(num.intValue() + 2);
        }
        int intValue = num.intValue();
        if (intValue == 0) {
            showOnOffDialog(i2);
            return;
        }
        if (intValue == 1) {
            showBrtDialog(1, i2, 0);
            return;
        }
        if (intValue == 2) {
            showBrtDialog(2, i2, 0);
        } else if (intValue == 3 || intValue == 4 || intValue == 5) {
            final int intValue2 = num.intValue();
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_wave_sensor_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor.2
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    WaveSensorHelper.instance().controlObject = ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).controlObject.getValue();
                    WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).mrExtParam;
                    WaveSensorHelper.instance().setDataType = i2;
                    int i3 = intValue2;
                    if (i3 == 3) {
                        NavUtils.destination(ActWaveSensorSelectBleDeviceAndGroup.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).groupControl).withDefaultRequestCode().navigation(ActWaveSensor.this.activity);
                    } else if (i3 == 4) {
                        NavUtils.destination(ActWaveSensorSelectScene.class).withLong(Constants.PLACE_ID, ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).placeId).withLong(Constants.CONTROL_ID, ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActWaveSensorVM) ActWaveSensor.this.mViewModel).groupControl).navigation(ActWaveSensor.this.activity);
                    } else {
                        WaveSensorHelper.instance().setSensorRelateParam(255, 0);
                        if (WaveSensorHelper.instance().isChangeAll) {
                            WaveSensorHelper.instance().setDataType = 2;
                            WaveSensorHelper.instance().setSensorRelateParam(255, 0);
                            WaveSensorHelper.instance().setDataType = 3;
                            WaveSensorHelper.instance().setSensorRelateParam(255, 0);
                        }
                    }
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda1
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    return ActWaveSensor.lambda$showApplyDialog$19(baseDialog, view);
                }
            });
        }
    }

    private void showOnOffDialog(final int dataType) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.off_1));
        arrayList.add(getString(R.string.on_1));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$showOnOffDialog$21(dataType, (Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOnOffDialog$21(int i, Integer num) {
        WaveSensorHelper.instance().controlObject = ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
        WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam;
        WaveSensorHelper.instance().setDataType = i;
        WaveSensorHelper.instance().setSensorRelateParam(0, num.intValue());
        if (WaveSensorHelper.instance().isChangeAll) {
            WaveSensorHelper.instance().setDataType = 2;
            WaveSensorHelper.instance().setSensorRelateParam(0, 0);
            WaveSensorHelper.instance().setDataType = 3;
            WaveSensorHelper.instance().setSensorRelateParam(0, 0);
        }
    }

    private void showBrtDialog(final int trigType, final int dataType, int brt) {
        SelectBrtDialog.asDefault(true).setBrt(brt).setHalfMode(dataType == 2).setTitle(getString(dataType == 2 ? R.string.please_select_stay_state : R.string.please_choose)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda23
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$showBrtDialog$22(dataType, trigType, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBrtDialog$22(int i, int i2, Integer num) {
        WaveSensorHelper.instance().controlObject = ((ActWaveSensorVM) this.mViewModel).controlObject.getValue();
        WaveSensorHelper.instance().extParam = ((ActWaveSensorVM) this.mViewModel).mrExtParam;
        WaveSensorHelper.instance().setDataType = i;
        WaveSensorHelper.instance().setSensorRelateParam(i2, LightUtils.progress2BrtHasBelowOne(num.intValue()));
        if (WaveSensorHelper.instance().isChangeAll) {
            WaveSensorHelper.instance().setDataType = 2;
            WaveSensorHelper.instance().setSensorRelateParam(i2, LightUtils.progress2BrtHasBelowOne(Math.max(num.intValue() / 2, 1)));
            WaveSensorHelper.instance().setDataType = 3;
            WaveSensorHelper.instance().setSensorRelateParam(i2, 0);
        }
    }

    private void showSelectTimeDialog(final int index, int sec, final int position) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 60; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        TimeSelectWithLimitDialog.asDefault().setTitle(getString(R.string.please_select_delay_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setSelectMinPosition(sec / 60).setSelectSecPosition(sec % 60).setSelectListener(new TimeSelectWithLimitDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActWaveSensor.this.lambda$showSelectTimeDialog$24(index, position, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$24(final int i, final int i2, final int i3, final int i4) {
        getCmdHelper(i).setDelayTime(this, (i3 * 60) + i4, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$showSelectTimeDialog$23(i, i3, i4, i2, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$23(int i, int i2, int i3, int i4, Boolean bool) {
        if (bool.booleanValue()) {
            ((ActWaveSensorVM) this.mViewModel).mrExtParam.getDelayParam(i).delayTime = (i2 * 60) + i3;
            getAdapter(i).setData(i4, createDelayItem(i));
            ((ActWaveSensorVM) this.mViewModel).updateWaveSensorExt(((ActWaveSensorVM) this.mViewModel).mrExtParam);
        }
    }

    private void showSelectStayTimeDialog(final int index, int stayTime, final int position) {
        List<String> asList = Arrays.asList(getResources().getStringArray(R.array.wave_sensor_stay_time));
        final int[] iArr = {0, 5, 10, 30, 60, 180, 300, 600, 1200, WinError.ERROR_INVALID_PRIORITY, 65535};
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_stay_time)).setMinList(asList).withUnit(false).setSelectMinPosition(Arrays.binarySearch(iArr, stayTime)).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActWaveSensor.this.lambda$showSelectStayTimeDialog$26(index, iArr, position, i, i2);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectStayTimeDialog$26(final int i, final int[] iArr, final int i2, final int i3, int i4) {
        getCmdHelper(i).setStayTime(this, iArr[i3], new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensor.this.lambda$showSelectStayTimeDialog$25(i, iArr, i3, i2, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectStayTimeDialog$25(int i, int[] iArr, int i2, int i3, Boolean bool) {
        if (bool.booleanValue()) {
            ((ActWaveSensorVM) this.mViewModel).mrExtParam.getStayParam(i).delayTime = iArr[i2];
            getAdapter(i).setData(i3, createStayItem(i));
            ((ActWaveSensorVM) this.mViewModel).updateWaveSensorExt(((ActWaveSensorVM) this.mViewModel).mrExtParam);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DeviceAssistant getCmdHelper(int index) {
        return CmdAssistant.getDeviceAssistant(((ActWaveSensorVM) this.mViewModel).controlObject.getValue(), 1 << index);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3004) {
            ((ActWaveSensorVM) this.mViewModel).mrExtParam = WaveSensorHelper.instance().extParam;
            initParamList(WaveSensorHelper.instance().index);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (8 == helper.getCode()) {
            initParamList(WaveSensorHelper.instance().index);
            return;
        }
        if (10 == helper.getCode()) {
            ((ActWaveSensorVM) this.mViewModel).mrExtParam.setMicroWaveSensitive(((Integer) helper.getData()).intValue());
            ((ActWaveSensorVM) this.mViewModel).updateWaveSensorExt(((ActWaveSensorVM) this.mViewModel).mrExtParam);
        } else if (11 == helper.getCode()) {
            ((ActWaveSensorVM) this.mViewModel).mrExtParam.setMicroWaveLux(((Integer) helper.getData()).intValue());
            ((ActWaveSensorVM) this.mViewModel).updateWaveSensorExt(((ActWaveSensorVM) this.mViewModel).mrExtParam);
        }
    }
}