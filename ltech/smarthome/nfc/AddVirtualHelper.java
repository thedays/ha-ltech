package com.ltech.smarthome.nfc;

import android.content.Context;
import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.blemesh.feasy.FeasyMeshNetHelper;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.mesh.GetProvisioningAddressResponse;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class AddVirtualHelper {
    public static final int END = 3;
    public static final int GET_CONFIG_INFO = 1;
    public static final int SAVE_DEVICE = 2;
    private BaseNormalActivity activity;
    private ActAddDeviceVM.ProductItem addItem;
    private int deviceType;
    private List<Integer> processList = new ArrayList();
    private int statePos = -1;
    private int unicastAddress;

    public static AddVirtualHelper instance() {
        return (AddVirtualHelper) Singleton.getSingleton(AddVirtualHelper.class);
    }

    public void init(BaseNormalActivity activity) {
        this.activity = activity;
    }

    public void init(BaseNormalActivity activity, ActAddDeviceVM.ProductItem addItem, int deviceType) {
        this.activity = activity;
        this.addItem = addItem;
        this.deviceType = deviceType;
    }

    private Context getContext() {
        BaseNormalActivity baseNormalActivity = this.activity;
        return baseNormalActivity == null ? ActivityUtils.getTopActivity() : baseNormalActivity;
    }

    public int nextState() {
        int i = this.statePos + 1;
        this.statePos = i;
        if (i >= this.processList.size()) {
            this.statePos = this.processList.size() - 1;
        }
        return this.processList.get(this.statePos).intValue();
    }

    public void addVirtualDevice() {
        this.processList.add(1);
        this.processList.add(2);
        this.processList.add(3);
        addVirtualDevice(this.processList.get(0).intValue());
    }

    public void addVirtualDevice(int state) {
        if (state == 1) {
            getConfigInfo();
        } else if (state == 2) {
            saveDevice();
        } else {
            if (state != 3) {
                return;
            }
            setSuccessView();
        }
    }

    private void getConfigInfo() {
        BaseNormalActivity baseNormalActivity = this.activity;
        baseNormalActivity.showLoadingDialog(baseNormalActivity.getString(R.string.getting_address));
        ((ObservableSubscribeProxy) Injection.net().getProvisioningAddress(ConfigHelper.instance().placeId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.AddVirtualHelper$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddVirtualHelper.this.lambda$getConfigInfo$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.AddVirtualHelper$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddVirtualHelper.this.lambda$getConfigInfo$1((GetProvisioningAddressResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.nfc.AddVirtualHelper$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddVirtualHelper.this.lambda$getConfigInfo$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfigInfo$0(Disposable disposable) throws Exception {
        BaseNormalActivity baseNormalActivity = this.activity;
        baseNormalActivity.showLoadingDialog(baseNormalActivity.getString(R.string.getting_address));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfigInfo$1(GetProvisioningAddressResponse getProvisioningAddressResponse) throws Exception {
        this.unicastAddress = Integer.parseInt(getProvisioningAddressResponse.getProvisioneraddress(), 16);
        addVirtualDevice(nextState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConfigInfo$2(Throwable th) throws Exception {
        setErrorView();
    }

    private void saveDevice() {
        BleParam createBleParam;
        String productId;
        createBleParam = createBleParam();
        ConfigHelper.instance().param = createBleParam;
        BaseExtParam baseExtParam = new BaseExtParam();
        baseExtParam.setBinNum(this.addItem.binNum);
        baseExtParam.setBinName(this.addItem.modelName);
        ConfigHelper.instance().paramext = GsonUtils.toJson(baseExtParam);
        productId = ConfigHelper.instance().productInfo.getProductId();
        productId.hashCode();
        switch (productId) {
            case "120033108251501":
            case "120033108255901":
            case "120033108263401":
            case "120033108265701":
            case "120033108272201":
            case "4002207473371776":
            case "4002205681371776":
            case "4002206372514432":
            case "4002206816422528":
                ConfigHelper.instance().codeLibrary = CodeLibraryUtil.generateLightcodeLibraryData(this.unicastAddress, createBleParam.getDeviceType());
                break;
            case "4249823578721536":
                RelatedInfoExtParam extParam = RelateInfoUtils.initSmartPanelRelateInfoList(productId).getExtParam();
                extParam.setBinNum(this.addItem.binNum);
                extParam.setBinName(this.addItem.modelName);
                ConfigHelper.instance().paramext = extParam.getRelateParamMapString();
            case "3895993722014848":
                ConfigHelper.instance().codeLibrary = CodeLibraryUtil.getSmartPanelCodeLibrary(this.unicastAddress, productId);
                break;
            default:
                ConfigHelper.instance().codeLibrary = "{}";
                break;
        }
        ((ObservableSubscribeProxy) Injection.net().addDevice(ConfigHelper.instance().bleVirtualData()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.nfc.AddVirtualHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddVirtualHelper.this.lambda$saveDevice$3((AddDeviceResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.nfc.AddVirtualHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AddVirtualHelper.this.lambda$saveDevice$4((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDevice$3(AddDeviceResponse addDeviceResponse) throws Exception {
        LHomeLog.i(getClass(), "response=" + addDeviceResponse.toString());
        Device addDevice = ConfigHelper.instance().addDevice(addDeviceResponse, new String[0]);
        addDevice.setVirtual(1);
        addDevice.setWritable(1);
        Injection.repo().device().saveDevice(addDevice);
        ReplaceHelper.instance().backupData(this.activity, addDevice.getDeviceId());
        addVirtualDevice(nextState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDevice$4(Throwable th) throws Exception {
        setErrorView();
    }

    public String getDeviceName(long placeId, ActAddDeviceVM.ProductItem item) {
        String str = "";
        String replace = getContext().getString(item.productInfo.getAddNameRes()).replace("\n", "");
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            if (LanguageUtils.isChinese(getContext())) {
                str = replace + i;
            } else {
                str = replace + " " + i;
            }
            z = Injection.repo().device().isDeviceNameExist(placeId, str);
        }
        return str;
    }

    public BleParam createBleParam() {
        BleParam bleParam = new BleParam();
        bleParam.setUnicastAddress(this.unicastAddress);
        bleParam.setDeviceKey(FeasyMeshNetHelper.createNetKey());
        ProductInfo productInfo = ConfigHelper.instance().productInfo;
        if (!TextUtils.isEmpty(productInfo.getProductKey()) && !TextUtils.isEmpty(productInfo.getSubProductKey())) {
            bleParam.setBleType((Integer.parseInt(productInfo.getProductKey(), 16) << 8) | Integer.parseInt(productInfo.getSubProductKey(), 16));
        }
        if (productInfo.getControlType() != 0) {
            bleParam.setDeviceType(productInfo.getControlType());
        }
        if (this.deviceType == 0) {
            if (productInfo.getControlType() != 0) {
                bleParam.setDeviceType(productInfo.getControlType());
            }
            return bleParam;
        }
        if (bleParam.getBleType() == 523 && this.deviceType == 5) {
            bleParam.setDeviceType(20);
        } else {
            bleParam.setDeviceType(this.deviceType);
        }
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setDeviceType(this.deviceType));
        return bleParam;
    }

    private void setSuccessView() {
        this.activity.dismissLoadingDialog();
        BaseNormalActivity baseNormalActivity = this.activity;
        baseNormalActivity.showSuccessDialog(baseNormalActivity.getString(R.string.add_success));
    }

    private void setErrorView() {
        this.activity.dismissLoadingDialog();
        BaseNormalActivity baseNormalActivity = this.activity;
        baseNormalActivity.showErrorDialog(baseNormalActivity.getString(R.string.add_fail));
    }
}