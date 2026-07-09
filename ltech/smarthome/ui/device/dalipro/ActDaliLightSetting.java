package com.ltech.smarthome.ui.device.dalipro;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliLightSettingBinding;
import com.ltech.smarthome.ltnfc.utils.BrightUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import com.ltech.smarthome.view.dialog.SelectDimCurveDialog;
import com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog;
import com.ltech.smarthome.view.dialog.SelectDimRangeDialog;
import com.ltech.smarthome.view.dialog.SelectKValueDialog;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDaliLightSetting extends VMActivity<ActDaliLightSettingBinding, ActDaliLightSettingVM> {
    public RoomPickHelper roomPickHelper = new RoomPickHelper();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_light_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        ((ActDaliLightSettingVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        initViewSetting();
        ((ActDaliLightSettingBinding) this.mViewBinding).sbAddToSmart.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActDaliLightSetting.this.lambda$initView$0(switchButton, z);
            }
        });
        ((ActDaliLightSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActDaliLightSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActDaliLightSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActDaliLightSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda12
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActDaliLightSetting.this.lambda$initView$1(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(SwitchButton switchButton, boolean z) {
        ((ActDaliLightSettingVM) this.mViewModel).setAddToSmart(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RefreshLayout refreshLayout) {
        queryState();
        ((ActDaliLightSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    private void initViewSetting() {
        if (((ActDaliLightSettingVM) this.mViewModel).lightType == 2) {
            ((ActDaliLightSettingBinding) this.mViewBinding).layoutCtRange.setVisibility(0);
        } else {
            ((ActDaliLightSettingBinding) this.mViewBinding).layoutCtRange.setVisibility(8);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDaliLightSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda20
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$2((Device) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).parentDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda22
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$3((Device) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda23
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).changeRoomEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda24
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).changeIconEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda25
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).dimmingFadeTimeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).dimmingRangeTimeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$8((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).kRangeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).dimmingCurveTimeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$10((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).lightOnStateEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$11((Void) obj);
            }
        });
        ((ActDaliLightSettingVM) this.mViewModel).failureStateEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightSetting.this.lambda$startObserve$12((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        ((ActDaliLightSettingBinding) this.mViewBinding).tvDeviceName.setText(device.getDeviceName());
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActDaliLightSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        this.roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ((ActDaliLightSettingVM) this.mViewModel).zoneNum = DaliProHelper.getDeviceAddress(device);
        CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class);
        ((ActDaliLightSettingBinding) this.mViewBinding).tvDeviceNumber.setText(cgdProLightExtParam.getDaliAddr() + "");
        int i = 0;
        ((ActDaliLightSettingVM) this.mViewModel).isAddToRoom.setValue(Boolean.valueOf(cgdProLightExtParam.getDaliHidden() == 0));
        int imageIndex = device.getImageIndex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        while (true) {
            if (i >= lightIconValue.length) {
                break;
            }
            if (i == imageIndex) {
                ((ActDaliLightSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                break;
            }
            i++;
        }
        queryState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        ((ActDaliLightSettingBinding) this.mViewBinding).tvGatewayName.setText(device.getDeviceName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        showEditNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r1) {
        showSelectDeviceIconDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        showDimFadeTimeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Void r1) {
        showDimRangeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r1) {
        showKValueDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Void r1) {
        showDimCurveDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Void r2) {
        SelectDaliPowerStateDialog.asDefault().controlObject(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue()).setZoneNum(((ActDaliLightSettingVM) this.mViewModel).zoneNum).setBrt(((ActDaliLightSettingVM) this.mViewModel).lightOnBrt).setWy(((ActDaliLightSettingVM) this.mViewModel).lightOnWy).setColor(((ActDaliLightSettingVM) this.mViewModel).lightOnColor).setOnSaveListener(new AnonymousClass1()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$1, reason: invalid class name */
    class AnonymousClass1 implements SelectDaliPowerStateDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.OnSaveListener
        public boolean onSave(final SelectDaliPowerStateDialog.OnOffState onOffState, final int brt, final int wy, final int color) {
            CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).zoneNum).setOnState(ActDaliLightSetting.this, onOffState.getMainValue(), brt, brt, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliLightSetting.AnonymousClass1.this.lambda$onSave$0(onOffState, brt, wy, color, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectDaliPowerStateDialog.OnOffState onOffState, int i, int i2, int i3, Boolean bool) {
            if (bool.booleanValue()) {
                if (onOffState.getMainValue() == 4) {
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnBrt = i;
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnWy = i2;
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnColor = i3;
                    if (((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightType == 3 && ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnColor != Integer.MIN_VALUE) {
                        ((ActDaliLightSettingBinding) ActDaliLightSetting.this.mViewBinding).civColorLightOn.setVisibility(0);
                        ((ActDaliLightSettingBinding) ActDaliLightSetting.this.mViewBinding).civColorLightOn.setImageDrawable(new ColorDrawable(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnColor));
                    }
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnState.setValue(ActDaliLightSetting.this.getDiyStr(i, i2));
                    return;
                }
                ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightOnState.setValue(onOffState.getName());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Void r2) {
        SelectDaliPowerStateDialog.asDefault().setTitle(getString(R.string.failure_state)).setZoneNum(((ActDaliLightSettingVM) this.mViewModel).zoneNum).setStateType(2).controlObject(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue()).setBrt(((ActDaliLightSettingVM) this.mViewModel).failBrt).setWy(((ActDaliLightSettingVM) this.mViewModel).failWy).setColor(((ActDaliLightSettingVM) this.mViewModel).failColor).setOnSaveListener(new AnonymousClass2()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$2, reason: invalid class name */
    class AnonymousClass2 implements SelectDaliPowerStateDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass2() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.OnSaveListener
        public boolean onSave(final SelectDaliPowerStateDialog.OnOffState onOffState, final int brt, final int wy, final int color) {
            CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).zoneNum).setFailState(ActDaliLightSetting.this, onOffState.getMainValue(), brt, brt, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliLightSetting.AnonymousClass2.this.lambda$onSave$0(onOffState, brt, wy, color, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectDaliPowerStateDialog.OnOffState onOffState, int i, int i2, int i3, Boolean bool) {
            if (bool.booleanValue()) {
                if (onOffState.getMainValue() == 4) {
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failBrt = i;
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failWy = i2;
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failColor = i3;
                    if (((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).lightType == 3 && ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failColor != Integer.MIN_VALUE) {
                        ((ActDaliLightSettingBinding) ActDaliLightSetting.this.mViewBinding).civColorFail.setVisibility(0);
                        ((ActDaliLightSettingBinding) ActDaliLightSetting.this.mViewBinding).civColorFail.setImageDrawable(new ColorDrawable(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failColor));
                    }
                    ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failureState.setValue(ActDaliLightSetting.this.getDiyStr(i, i2));
                    return;
                }
                ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).failureState.setValue(onOffState.getName());
            }
        }
    }

    private void queryState() {
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).queryDimCurve(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$queryState$13((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$13(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        String resData = responseMsg.getResData();
        if (resData.length() >= 18) {
            int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
            ((ActDaliLightSettingVM) this.mViewModel).dimCurveType = parseInt;
            ((ActDaliLightSettingVM) this.mViewModel).dimmingCurve.setValue(getString(parseInt == 1 ? R.string.string_log : R.string.string_linear));
            queryDimRange();
        }
    }

    private void queryDimRange() {
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).queryDimRange(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$queryDimRange$14((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDimRange$14(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        String resData = responseMsg.getResData();
        if (resData.length() >= 24) {
            ((ActDaliLightSettingVM) this.mViewModel).rgbLowLight = Integer.parseInt(resData.substring(16, 18), 16);
            ((ActDaliLightSettingVM) this.mViewModel).rgbHighLight = Integer.parseInt(resData.substring(18, 20), 16);
            ((ActDaliLightSettingVM) this.mViewModel).ctLowLight = Integer.parseInt(resData.substring(20, 22), 16);
            ((ActDaliLightSettingVM) this.mViewModel).ctHighLight = Integer.parseInt(resData.substring(22, 24), 16);
            setDimRange();
            queryDimFadeTime();
        }
    }

    private void setDimRange() {
        String str;
        String str2;
        if (((ActDaliLightSettingVM) this.mViewModel).dimCurveType == 1) {
            if (((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_LIGHT_RGB)) {
                str = BrightUtils.getLogPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).rgbLowLight));
                str2 = BrightUtils.getLogPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).rgbHighLight));
            } else {
                str = BrightUtils.getLogPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).ctLowLight));
                str2 = BrightUtils.getLogPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).ctHighLight));
            }
        } else if (((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_LIGHT_RGB)) {
            str = BrightUtils.getLinnerPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).rgbLowLight));
            str2 = BrightUtils.getLinnerPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).rgbHighLight));
        } else {
            str = BrightUtils.getLinnerPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).ctLowLight));
            str2 = BrightUtils.getLinnerPercent().get(Integer.valueOf(((ActDaliLightSettingVM) this.mViewModel).ctHighLight));
        }
        ((ActDaliLightSettingVM) this.mViewModel).dimmingRange.setValue(str + " - " + str2);
    }

    private void queryDimFadeTime() {
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).queryDimFadeTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$queryDimFadeTime$15((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDimFadeTime$15(ResponseMsg responseMsg) {
        String str;
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        String resData = responseMsg.getResData();
        ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition = Integer.parseInt(resData.substring(16, 18), 16);
        ((ActDaliLightSettingVM) this.mViewModel).dimFadeTime = Integer.parseInt(resData.substring(18, 20), 16);
        ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount = Integer.parseInt(resData.substring(20, 22), 16);
        if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition != 0) {
            ((ActDaliLightSettingVM) this.mViewModel).dimmingFadeTime.setValue((String) Arrays.asList(getResources().getStringArray(R.array.fade_time)).get(Math.min(((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition, 15)));
        } else {
            if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 0) {
                str = "0ms";
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 1) {
                str = (((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount * 100) + "ms";
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 2) {
                str = ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount + NotifyType.SOUND;
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 3) {
                str = (((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount * 10) + NotifyType.SOUND;
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime != 4) {
                str = "";
            } else {
                str = ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount + Constants.MIN;
            }
            ((ActDaliLightSettingVM) this.mViewModel).dimmingFadeTime.setValue(str);
        }
        if (((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_LIGHT_CT)) {
            queryCtRange();
        } else {
            queryOnState();
        }
    }

    private void queryCtRange() {
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).queryCtRange(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$queryCtRange$16((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryCtRange$16(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        if (responseMsg.getResData().length() >= 28) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16);
            int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(24, 28), 16);
            if (parseInt < 1000) {
                parseInt = 1000;
            }
            if (parseInt2 < 1000 || parseInt2 > 10000) {
                parseInt2 = 10000;
            }
            ((ActDaliLightSettingVM) this.mViewModel).minK = parseInt;
            ((ActDaliLightSettingVM) this.mViewModel).maxK = parseInt2;
            ((ActDaliLightSettingVM) this.mViewModel).ctRange.setValue(parseInt + "K - " + parseInt2 + "K");
        }
        queryOnState();
    }

    private void queryOnState() {
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$queryOnState$17((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryOnState$17(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        String resData = responseMsg.getResData();
        List<SelectDaliPowerStateDialog.OnOffState> contentList = SelectDaliPowerStateDialog.getContentList();
        int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
        int size = contentList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            if (parseInt == contentList.get(i).getMainValue()) {
                ((ActDaliLightSettingVM) this.mViewModel).lightOnBrt = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
                if (parseInt == 4) {
                    if (responseMsg.getResData().length() > 18) {
                        ((ActDaliLightSettingVM) this.mViewModel).lightOnBrt = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
                        if (responseMsg.getResData().length() > 22) {
                            int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(22, 24), 16);
                            int parseInt3 = Integer.parseInt(responseMsg.getResData().substring(24, 26), 16);
                            int parseInt4 = Integer.parseInt(responseMsg.getResData().substring(26, 28), 16);
                            ((ActDaliLightSettingVM) this.mViewModel).lightOnColor = Color.rgb(parseInt2, parseInt3, parseInt4);
                            ((ActDaliLightSettingVM) this.mViewModel).lightOnWy = Integer.parseInt(responseMsg.getResData().substring(30, 32), 16);
                        }
                    }
                    if (((ActDaliLightSettingVM) this.mViewModel).lightType == 3 && ((ActDaliLightSettingVM) this.mViewModel).lightOnColor != Integer.MIN_VALUE) {
                        ((ActDaliLightSettingBinding) this.mViewBinding).civColorLightOn.setVisibility(0);
                        ((ActDaliLightSettingBinding) this.mViewBinding).civColorLightOn.setImageDrawable(new ColorDrawable(((ActDaliLightSettingVM) this.mViewModel).lightOnColor));
                    }
                    ((ActDaliLightSettingVM) this.mViewModel).lightOnState.setValue(getDiyStr(((ActDaliLightSettingVM) this.mViewModel).lightOnBrt, ((ActDaliLightSettingVM) this.mViewModel).lightOnWy));
                } else {
                    ((ActDaliLightSettingVM) this.mViewModel).lightOnState.setValue(contentList.get(i).getName());
                }
            } else {
                i++;
            }
        }
        queryFailState();
    }

    private void queryFailState() {
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).queryFailState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$queryFailState$18((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryFailState$18(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0) {
            return;
        }
        String resData = responseMsg.getResData();
        List<SelectDaliPowerStateDialog.OnOffState> contentList = SelectDaliPowerStateDialog.getContentList();
        int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
        int size = contentList.size();
        for (int i = 0; i < size; i++) {
            if (parseInt == contentList.get(i).getMainValue()) {
                if (parseInt == 4) {
                    if (responseMsg.getResData().length() > 18) {
                        ((ActDaliLightSettingVM) this.mViewModel).failBrt = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
                        if (responseMsg.getResData().length() > 22) {
                            int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(22, 24), 16);
                            int parseInt3 = Integer.parseInt(responseMsg.getResData().substring(24, 26), 16);
                            int parseInt4 = Integer.parseInt(responseMsg.getResData().substring(26, 28), 16);
                            ((ActDaliLightSettingVM) this.mViewModel).failColor = Color.rgb(parseInt2, parseInt3, parseInt4);
                            ((ActDaliLightSettingVM) this.mViewModel).failWy = Integer.parseInt(responseMsg.getResData().substring(30, 32), 16);
                        }
                    }
                    if (((ActDaliLightSettingVM) this.mViewModel).lightType == 3 && ((ActDaliLightSettingVM) this.mViewModel).failColor != Integer.MIN_VALUE) {
                        ((ActDaliLightSettingBinding) this.mViewBinding).civColorFail.setVisibility(0);
                        ((ActDaliLightSettingBinding) this.mViewBinding).civColorFail.setImageDrawable(new ColorDrawable(((ActDaliLightSettingVM) this.mViewModel).failColor));
                    }
                    ((ActDaliLightSettingVM) this.mViewModel).failureState.setValue(getDiyStr(((ActDaliLightSettingVM) this.mViewModel).failBrt, ((ActDaliLightSettingVM) this.mViewModel).failWy));
                    return;
                }
                ((ActDaliLightSettingVM) this.mViewModel).failureState.setValue(contentList.get(i).getName());
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDiyStr(int brt, int wy) {
        if (((ActDaliLightSettingVM) this.mViewModel).lightType == 2) {
            return LightUtils.getKValue(wy, ((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue()) + " " + LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(brt)) + getString(R.string.brt);
        }
        return LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(brt)) + getString(R.string.brt);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActDaliLightSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        ((ActDaliLightSettingVM) this.mViewModel).parentDevice.setValue(Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L)));
    }

    private void showDimFadeTimeDialog() {
        SelectDimFadeTimeDialog.asDefault().setFadeTimePosition(((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition).setFadeTime(((ActDaliLightSettingVM) this.mViewModel).dimFadeTime).setFadeTimeCount(((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount).setOnSaveListener(new SelectDimFadeTimeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.view.dialog.SelectDimFadeTimeDialog.OnSaveListener
            public final void onSave(int i, int i2, int i3) {
                ActDaliLightSetting.this.lambda$showDimFadeTimeDialog$20(i, i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimFadeTimeDialog$20(final int i, final int i2, final int i3) {
        showLoadingDialog(getString(R.string.saving));
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).setFadeTime(this, i, i2, i3, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$showDimFadeTimeDialog$19(i, i2, i3, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimFadeTimeDialog$19(int i, int i2, int i3, ResponseMsg responseMsg) {
        String str;
        dismissLoadingDialog();
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition = i;
            ((ActDaliLightSettingVM) this.mViewModel).dimFadeTime = i2;
            ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount = i3;
            if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition != 0) {
                ((ActDaliLightSettingVM) this.mViewModel).dimmingFadeTime.setValue((String) Arrays.asList(getResources().getStringArray(R.array.fade_time)).get(Math.min(((ActDaliLightSettingVM) this.mViewModel).dimFadeTimePosition, 15)));
                return;
            }
            if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 0) {
                str = "0ms";
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 1) {
                str = (((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount * 100) + "ms";
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 2) {
                str = ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount + NotifyType.SOUND;
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime == 3) {
                str = (((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount * 10) + NotifyType.SOUND;
            } else if (((ActDaliLightSettingVM) this.mViewModel).dimFadeTime != 4) {
                str = "";
            } else {
                str = ((ActDaliLightSettingVM) this.mViewModel).dimFadeTimeCount + Constants.MIN;
            }
            ((ActDaliLightSettingVM) this.mViewModel).dimmingFadeTime.setValue(str);
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    private void showDimRangeDialog() {
        int i;
        int i2;
        if (((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_LIGHT_RGB)) {
            i = ((ActDaliLightSettingVM) this.mViewModel).rgbLowLight;
            i2 = ((ActDaliLightSettingVM) this.mViewModel).rgbHighLight;
        } else {
            i = ((ActDaliLightSettingVM) this.mViewModel).ctLowLight;
            i2 = ((ActDaliLightSettingVM) this.mViewModel).ctHighLight;
        }
        SelectDimRangeDialog.asDefault().setCurveType(((ActDaliLightSettingVM) this.mViewModel).dimCurveType).setLowPos(i).setHighPos(i2).setOnSaveListener(new SelectDimRangeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.view.dialog.SelectDimRangeDialog.OnSaveListener
            public final void onSave(int i3, int i4) {
                ActDaliLightSetting.this.lambda$showDimRangeDialog$22(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimRangeDialog$22(int i, int i2) {
        showLoadingDialog(getString(R.string.saving));
        if (((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_LIGHT_RGB)) {
            ((ActDaliLightSettingVM) this.mViewModel).rgbLowLight = i;
            ((ActDaliLightSettingVM) this.mViewModel).rgbHighLight = i2;
        } else {
            ((ActDaliLightSettingVM) this.mViewModel).ctLowLight = i;
            ((ActDaliLightSettingVM) this.mViewModel).ctHighLight = i2;
        }
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).setDimRange(this, ((ActDaliLightSettingVM) this.mViewModel).rgbLowLight, ((ActDaliLightSettingVM) this.mViewModel).rgbHighLight, ((ActDaliLightSettingVM) this.mViewModel).ctLowLight, ((ActDaliLightSettingVM) this.mViewModel).ctHighLight, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$showDimRangeDialog$21((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimRangeDialog$21(ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            setDimRange();
        } else {
            showErrorDialog(getString(R.string.save_fail));
        }
    }

    private void showKValueDialog() {
        Device value = ((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue();
        if (value != null) {
            SelectKValueDialog.asDefault().controlObject(value).setMax(((ActDaliLightSettingVM) this.mViewModel).maxK).setMin(((ActDaliLightSettingVM) this.mViewModel).minK).setIsNeedQuery(false).setOnSaveListener(new AnonymousClass3(value)).showDialog(this);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$3, reason: invalid class name */
    class AnonymousClass3 implements SelectKValueDialog.OnSaveListener {
        final /* synthetic */ Device val$device;

        @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass3(final Device val$device) {
            this.val$device = val$device;
        }

        @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
        public boolean onSave(final SelectKValueDialog.OnOffState onOffState, int selectPos) {
            ActDaliLightSetting actDaliLightSetting = ActDaliLightSetting.this;
            actDaliLightSetting.showLoadingDialog(actDaliLightSetting.getString(R.string.saving));
            LightAssistant lightCmdAssistant = CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).zoneNum);
            ActDaliLightSetting actDaliLightSetting2 = ActDaliLightSetting.this;
            int min = onOffState.getMin();
            int max = onOffState.getMax();
            final Device device = this.val$device;
            lightCmdAssistant.setKRange(actDaliLightSetting2, min, max, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$3$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliLightSetting.AnonymousClass3.this.lambda$onSave$1(device, onOffState, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(final Device device, final SelectKValueDialog.OnOffState onOffState, Boolean bool) {
            if (bool.booleanValue()) {
                ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), onOffState.getMax(), onOffState.getMin()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActDaliLightSetting.this.getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$3$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActDaliLightSetting.AnonymousClass3.this.lambda$onSave$0(device, onOffState, obj);
                    }
                }, new AnonymousClass1());
            } else {
                ActDaliLightSetting actDaliLightSetting = ActDaliLightSetting.this;
                actDaliLightSetting.showErrorDialog(actDaliLightSetting.getString(R.string.save_fail));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Device device, SelectKValueDialog.OnOffState onOffState, Object obj) throws Exception {
            ActDaliLightSetting.this.dismissLoadingDialog();
            device.setMinkelvin(onOffState.getMin());
            device.setMaxkelvin(onOffState.getMax());
            Injection.repo().device().saveDevice(device);
            ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).ctRange.setValue(device.getMinkelvin() + "K - " + device.getMaxkelvin() + "K");
            ActDaliLightSetting.this.showSaveKValueDialog();
        }

        /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$3$1, reason: invalid class name */
        class AnonymousClass1 implements Consumer<Throwable> {
            AnonymousClass1() {
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.getValue(), new int[0]).setKRange(ActDaliLightSetting.this, ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).minK, ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).maxK, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$3$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActDaliLightSetting.AnonymousClass3.AnonymousClass1.this.lambda$accept$0((Boolean) obj);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$accept$0(Boolean bool) {
                if (!Injection.state().isConnectOuterNet()) {
                    SmartToast.showShort(R.string.no_network);
                }
                ActDaliLightSetting.this.showErrorDialog(ActDaliLightSetting.this.getString(R.string.save_fail));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSaveKValueDialog() {
        MessageDialog.show(this, getString(R.string.app_str_setting_success), getString(R.string.k_range_save_tip));
    }

    private void showDimCurveDialog() {
        SelectDimCurveDialog.asDefault().setPosition(((ActDaliLightSettingVM) this.mViewModel).dimCurveType).setOnSaveListener(new SelectDimCurveDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.view.dialog.SelectDimCurveDialog.OnSaveListener
            public final void onSave(int i) {
                ActDaliLightSetting.this.lambda$showDimCurveDialog$24(i);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimCurveDialog$24(final int i) {
        showLoadingDialog(getString(R.string.saving));
        CmdAssistant.getLightCmdAssistant(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue(), ((ActDaliLightSettingVM) this.mViewModel).zoneNum).setDimCurve(this, i, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$showDimCurveDialog$23(i, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimCurveDialog$23(int i, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            ((ActDaliLightSettingVM) this.mViewModel).dimCurveType = i;
            ((ActDaliLightSettingVM) this.mViewModel).dimmingCurve.setValue(getString(((ActDaliLightSettingVM) this.mViewModel).dimCurveType == 1 ? R.string.string_log : R.string.string_linear));
            setDimRange();
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    private void showSelectDeviceIconDialog() {
        SelectDeviceIconDialog.asDefault().imageIndex(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getImageIndex()).setOnSaveListener(new AnonymousClass4()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$4, reason: invalid class name */
    class AnonymousClass4 implements SelectDeviceIconDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass4() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public boolean onSave(final int selectPos) {
            Observable<R> compose = Injection.net().updateDeviceImgIndex(((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.getValue().getDeviceId(), selectPos).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$4$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliLightSetting.AnonymousClass4.this.lambda$onSave$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main());
            final ActDaliLightSetting actDaliLightSetting = ActDaliLightSetting.this;
            ((ObservableSubscribeProxy) compose.doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$4$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActDaliLightSetting.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActDaliLightSetting.this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$4$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliLightSetting.AnonymousClass4.this.lambda$onSave$1(selectPos, obj);
                }
            }, new SmartErrorComsumer());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Disposable disposable) throws Exception {
            ActDaliLightSetting actDaliLightSetting = ActDaliLightSetting.this;
            actDaliLightSetting.showLoadingDialog(actDaliLightSetting.getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(int i, Object obj) throws Exception {
            Device value = ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.getValue();
            value.setImageIndex(i);
            Injection.repo().device().saveDevice(value);
            ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).controlDevice.setValue(value);
            SmartToast.showShort(R.string.save_success);
        }
    }

    protected void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActDaliLightSettingVM) this.mViewModel).controlDevice.getValue().getDeviceName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightSetting.this.lambda$showEditNameDialog$25((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$25(String str) {
        ((ActDaliLightSettingVM) this.mViewModel).changeDeviceName(str);
    }

    private void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSetting.5
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActDaliLightSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ((ActDaliLightSettingVM) ActDaliLightSetting.this.mViewModel).changeDevicePlace(ActDaliLightSetting.this.roomPickHelper.getSelectFloorId(), ActDaliLightSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActDaliLightSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }
}