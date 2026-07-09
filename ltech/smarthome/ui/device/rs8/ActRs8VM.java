package com.ltech.smarthome.ui.device.rs8;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.ConfigDeviceBean;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.rs8.Rs8BrandResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CategoryResponse;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActRs8VM extends BaseViewModel {
    public long brandId;
    public long categoryId;
    public List<Rs8CodeLibResponse.CodeLib> codeLibDataList;
    public long controlId;
    public int curLib;
    public Device device;
    public String deviceName;
    public boolean isSelected;
    public int maxLib;
    public long placeId;
    public List<Rs8CodeLibResponse.CodeLib.Action> waitSaveList;
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<List<Rs8CategoryResponse.Category>> categoryData = new MutableLiveData<>();
    public MutableLiveData<Rs8CodeLibResponse.CodeLib> codeLibData = new MutableLiveData<>();
    public MutableLiveData<List<Rs8BrandResponse.Brand>> brandData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isGroup = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> addFinishEvent = new SingleLiveEvent<>();
    public String img = "";
    public int addressType = 1;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActRs8VM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.bt_search /* 2131296482 */:
            case R.id.iv_search_add /* 2131297229 */:
                navigation(NavUtils.destination(ActRs8Category.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode());
                break;
            case R.id.iv_next /* 2131297154 */:
                int i = this.curLib;
                if (i < this.maxLib) {
                    this.curLib = i + 1;
                    this.codeLibData.setValue(this.codeLibDataList.get(i));
                    break;
                }
                break;
            case R.id.iv_upper /* 2131297310 */:
                int i2 = this.curLib;
                if (i2 > 1) {
                    this.curLib = i2 - 1;
                    this.codeLibData.setValue(this.codeLibDataList.get(i2 - 2));
                    break;
                }
                break;
            case R.id.tv_next /* 2131298810 */:
                if (!Boolean.FALSE.equals(this.isGroup.getValue()) || !TextUtils.isEmpty(this.address.getValue())) {
                    if (this.addressType == 1) {
                        getSubAddress();
                        break;
                    } else {
                        navigation(NavUtils.destination(ActRs8Curtain.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.PLACE_ID, this.placeId).withLong(Constants.CATEGORY_ID, this.categoryId).withLong(Constants.BRAND_ID, this.brandId).withString("image", this.img).withString(Constants.ADDRESS, this.address.getValue()).withString("device_name", this.deviceName).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode());
                        break;
                    }
                }
                break;
            case R.id.tv_response /* 2131298921 */:
                showLoadingDialog(getContext().getString(R.string.adding));
                saveBleToRs8Device(this.codeLibData.getValue() != null ? this.codeLibData.getValue() : new Rs8CodeLibResponse.CodeLib());
                break;
        }
    }

    public void initCategoryData() {
        ((ObservableSubscribeProxy) Injection.net().queryRs8Category().delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8VM.this.lambda$initCategoryData$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActRs8VM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Rs8CategoryResponse>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Rs8CategoryResponse response) throws Exception {
                if (response == null || response.getRows() == null) {
                    return;
                }
                ActRs8VM.this.categoryData.setValue(response.getRows());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCategoryData$1(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.loading));
    }

    public void initBrandData() {
        ((ObservableSubscribeProxy) Injection.net().queryRs8Brand(this.categoryId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8VM.this.lambda$initBrandData$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActRs8VM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Rs8BrandResponse>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Rs8BrandResponse response) throws Exception {
                if (response == null || response.getRows() == null) {
                    return;
                }
                ActRs8VM.this.brandData.setValue(response.getRows());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBrandData$2(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.loading));
    }

    public void sendCmd(String instruct, String replyInstruct) {
        showLoadingDialog();
        CmdAssistant.getDeviceAssistant(this.device, new int[0]).runRs485DataWithReply(getContext(), instruct, replyInstruct, 1, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActRs8VM actRs8VM = ActRs8VM.this;
                    actRs8VM.showSuccessTipDialog(actRs8VM.getContext().getString(R.string.send_to_device_success));
                } else {
                    ActRs8VM actRs8VM2 = ActRs8VM.this;
                    actRs8VM2.showErrorTipDialog(actRs8VM2.getContext().getString(R.string.send_to_device_fail));
                }
            }
        });
    }

    public void getSubAddress() {
        ((ObservableSubscribeProxy) Injection.net().queryRs8SubAddress(this.brandId, this.address.getValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8VM.this.lambda$getSubAddress$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActRs8VM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<String>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.4
            @Override // io.reactivex.functions.Consumer
            public void accept(String s) throws Exception {
                if (!StringUtils.isEmpty(s)) {
                    CmdAssistant.getDeviceAssistant(ActRs8VM.this.device, new int[0]).runRs485Data(ActRs8VM.this.getContext(), s, 1, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.4.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(Boolean aBoolean) {
                            if (aBoolean.booleanValue()) {
                                SmartToast.showCenterShort(ActRs8VM.this.getContext().getString(R.string.write_success));
                                ActRs8VM.this.navigation(NavUtils.destination(ActRs8Curtain.class).withLong(Constants.CONTROL_ID, ActRs8VM.this.controlId).withLong(Constants.PLACE_ID, ActRs8VM.this.placeId).withLong(Constants.CATEGORY_ID, ActRs8VM.this.categoryId).withLong(Constants.BRAND_ID, ActRs8VM.this.brandId).withString("image", ActRs8VM.this.img).withString(Constants.ADDRESS, ActRs8VM.this.address.getValue()).withString("device_name", ActRs8VM.this.deviceName).withBoolean(Constants.SELECT_ACTION, true).withDefaultRequestCode());
                            } else {
                                ActRs8VM.this.showErrorTipDialog(ActRs8VM.this.getContext().getString(R.string.app_str_setting_failed));
                            }
                        }
                    });
                } else {
                    ActRs8VM actRs8VM = ActRs8VM.this;
                    actRs8VM.showErrorTipDialog(actRs8VM.getContext().getString(R.string.app_str_setting_failed));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSubAddress$3(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.creating));
    }

    public void initCodeLib() {
        ((ObservableSubscribeProxy) Injection.net().queryRs8CodeLib(this.categoryId, this.brandId, this.address.getValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8VM.this.lambda$initCodeLib$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActRs8VM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Rs8CodeLibResponse>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Rs8CodeLibResponse response) throws Exception {
                if (response == null || response.getRows() == null || response.getRows().isEmpty()) {
                    return;
                }
                ActRs8VM.this.codeLibDataList = response.getRows();
                ActRs8VM.this.maxLib = response.getRows().size();
                ActRs8VM.this.curLib = 1;
                Rs8CodeLibResponse.CodeLib codeLib = ActRs8VM.this.codeLibDataList.get(0);
                codeLib.setIcon(ActRs8VM.this.img);
                ActRs8VM.this.codeLibData.setValue(codeLib);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCodeLib$4(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.loading));
    }

    private String newRs8SubData(Rs8CodeLibResponse.CodeLib lib) {
        BleParam bleParam = new BleParam();
        bleParam.setUnicastAddress(this.device.getUnicastAddress());
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).placeid(Injection.repo().home().getSelPlace().getPlaceId()).floorid(this.device.getFloorId()).roomid(this.device.getRoomId()).mac(this.device.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "")).maccode("").devicename(this.deviceName).macfalg(2).codeLibrary(ConfigHelper.instance().codeLibrary).aiPuductType(ProductId.BLE_SUB_TYPE_CG_RS8).subProductTypeName(ProductId.BLE_SUB_TYPE_CG_RS8).subManufacturerName("LTECH").subProductName(this.deviceName).macdeviceid(this.device.getDeviceId()).param(bleParam).paramext(GsonUtils.toJson(lib)).build().getConfigJson();
    }

    private Device addBleToRs8Device(AddDeviceResponse response) {
        Device device = new Device();
        device.setPlaceId(Injection.repo().home().getSelPlace().getPlaceId());
        device.setFloorId(this.device.getFloorId());
        device.setRoomId(this.device.getRoomId());
        device.setRoomName(this.device.getRoomName());
        device.setFloorName(this.device.getFloorName());
        device.setProductId(ProductId.CGRS8_SUB_DEVICE);
        device.setDeviceId(response.getDeviceid());
        device.setPlatFormDeviceId(response.getPlatformdeviceid());
        device.setOnlineFlag(1);
        device.setIndex(1000);
        device.setCreatetime(response.getCreatetime());
        device.setImageIndex(response.getImgindex());
        device.setWifiMac(response.getMac());
        device.setDeviceName(response.getDevicename());
        device.setMacfalg(response.getMacfalg());
        device.setMacdeviceid(response.getMacdeviceid());
        device.setParam(response.getParam());
        device.setExtParam(response.getParamext());
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        Injection.repo().device().saveDevice(device);
        return device;
    }

    public void saveBleToRs8Device(Rs8CodeLibResponse.CodeLib codeLib) {
        codeLib.setActionlist(this.waitSaveList);
        ((ObservableSubscribeProxy) Injection.net().addDevice(newRs8SubData(codeLib)).delaySubscription(200L, TimeUnit.MILLISECONDS).observeOn(Schedulers.single()).compose(RxUtils.io_main()).doFinally(new ActRs8VM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8VM.this.lambda$saveBleToRs8Device$5((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.6
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActRs8VM.this.dismissLoadingDialog();
                ActRs8VM actRs8VM = ActRs8VM.this;
                actRs8VM.showErrorTipDialog(actRs8VM.getContext().getString(R.string.save_fail));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBleToRs8Device$5(AddDeviceResponse addDeviceResponse) throws Exception {
        this.device = addBleToRs8Device(addDeviceResponse);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8VM.7
            @Override // java.lang.Runnable
            public void run() {
                ActRs8VM.this.addFinishEvent.call();
            }
        }, 200L);
    }

    public void loadDeviceAction() {
        Device device = this.device;
        if (device == null) {
            finishActivity();
            return;
        }
        Rs8CodeLibResponse.CodeLib codeLib = (Rs8CodeLibResponse.CodeLib) device.getExtParam(Rs8CodeLibResponse.CodeLib.class);
        if (codeLib != null) {
            this.categoryId = codeLib.getCategoryid();
            this.brandId = codeLib.getBrandid();
            this.img = codeLib.getIcon() != null ? codeLib.getIcon() : "";
            this.deviceName = this.device.getDeviceName();
            this.codeLibData.setValue(codeLib);
        }
    }

    public int getImg(String img) {
        img.hashCode();
        switch (img) {
            case "2":
                return R.mipmap.cgcur_icon_stop;
            case "3":
                return R.mipmap.cgcur_icon_off;
            case "4":
                return R.mipmap.trig_cur_icon_left;
            case "5":
                return R.mipmap.trig_cur_icon_stop;
            case "6":
                return R.mipmap.trig_cur_icon_right;
            case "7":
                return R.mipmap.icon_open_2;
            case "8":
                return R.mipmap.icon_close_2;
            default:
                return R.mipmap.cgcur_icon_on;
        }
    }
}