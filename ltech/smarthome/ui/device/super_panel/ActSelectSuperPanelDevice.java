package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseDeviceManageActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActDeviceManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelDevice extends BaseDeviceManageActivity<ActDeviceManageBinding, ActSelectSuperPanelDeviceVM> {
    private boolean selectAll;

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.all_device));
        ((ActDeviceManageBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActDeviceManageBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{0}));
        ((DefaultItemAnimator) ((ActDeviceManageBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.DEVICE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                ((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList.add(Long.valueOf(j));
            }
        }
        ((ActDeviceManageBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDevice$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectSuperPanelDevice.this.lambda$initView$2((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelDevice(getIntent().getLongExtra("device_id", -1L), ((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDevice$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSuperPanelDevice.this.lambda$initView$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDevice$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectSuperPanelDevice.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDevice$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSuperPanelDevice.this.lambda$initView$1((SetSuperPanelResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(SetSuperPanelResponse setSuperPanelResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (setSuperPanelResponse.getDevices() != null) {
            Iterator<SetSuperPanelResponse.DevicesBean> it = setSuperPanelResponse.getDevices().iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().getDeviceid()));
            }
        }
        Injection.repo().device().setSuperPanelDevice(setSuperPanelResponse.getInfo().getPanelid(), arrayList);
        SmartToast.showShort(getString(R.string.save_success));
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSelectSuperPanelDeviceVM) this.mViewModel).selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDevice$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectSuperPanelDevice.this.lambda$startObserve$3((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        ((ActDeviceManageBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{num}));
        this.selectAll = true;
        Iterator<Device> it = ((ActSelectSuperPanelDeviceVM) this.mViewModel).mDeviceList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (!((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList.contains(Long.valueOf(it.next().getDeviceId()))) {
                this.selectAll = false;
                break;
            }
        }
        if (this.selectAll) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        this.selectAll = !this.selectAll;
        if (((ActSelectSuperPanelDeviceVM) this.mViewModel).mDeviceList.size() > 0) {
            if (this.selectAll) {
                for (Device device : ((ActSelectSuperPanelDeviceVM) this.mViewModel).mDeviceList) {
                    if (!((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList.contains(Long.valueOf(device.getDeviceId()))) {
                        ((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList.add(Long.valueOf(device.getDeviceId()));
                    }
                }
            } else {
                ((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList.clear();
            }
            ((ActDeviceManageBinding) this.mViewBinding).rvContent.getAdapter().notifyDataSetChanged();
        }
        ((ActSelectSuperPanelDeviceVM) this.mViewModel).selectCountLiveData.setValue(Integer.valueOf(((ActSelectSuperPanelDeviceVM) this.mViewModel).selectDeviceIdList.size()));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity
    protected boolean filterDevice(Device device) {
        Device deviceByDeviceId;
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1777527685:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1777494050:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1776694498:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1776638760:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1776570529:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 14;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 15;
                    break;
                }
                break;
            case 2003796:
                if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                    c2 = 16;
                    break;
                }
                break;
            case 2003797:
                if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                    c2 = 17;
                    break;
                }
                break;
            case 2003798:
                if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                    c2 = 18;
                    break;
                }
                break;
            case 2256543:
                if (productId.equals(ProductId.ID_IR_AC)) {
                    c2 = 19;
                    break;
                }
                break;
            case 2256546:
                if (productId.equals(ProductId.ID_IR_FAN)) {
                    c2 = 20;
                    break;
                }
                break;
            case 69953013:
                if (productId.equals(ProductId.ID_IR_CURTAIN)) {
                    c2 = 21;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 22;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 23;
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 24;
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 25;
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 27;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 7:
            case 14:
            case 15:
            case 23:
            case 25:
            case 26:
                BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                return bleParam.getGroupId() == -1 || bleParam.getGroupId() == 0;
            case 22:
                return !RelaySeparationHelper.isRelaySeparationSub(device) || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid())) == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null || ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 27:
                return true;
            case 24:
                return device.getParam(DryContactBleParam.class) != null && ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 0;
            default:
                return false;
        }
    }
}