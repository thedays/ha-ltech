package com.ltech.smarthome.ui.device.screenpanel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.G4IconUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SmartPanelScreenState;
import com.smart.product_agreement.parser.IPanelParser;
import com.smart.product_agreement.utils.GZipUtil;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelThemeVM extends BaseViewModel {
    public int[] applyThemes;
    public long controlId;
    public int curScreen;
    public List<Integer> downloadThemes;
    public boolean groupControl;
    public int num;
    public int screensaverMode;
    private List<Item> themeList;
    public int themeNum;
    private List<String> timeList;
    public MutableLiveData<Integer> screensaverTime = new MutableLiveData<>(30);
    public final List<NeedDownloadDevice> connectAddress = new ArrayList();
    public final List<FailDevice> failDevices = new ArrayList();
    public MutableLiveData<Integer> downloadEvent = new MutableLiveData<>();
    public MutableLiveData<Integer> applyEvent = new MutableLiveData<>();
    public SingleLiveEvent<Object> controlObject = new SingleLiveEvent<>();
    public final String[] themeNames = {StringUtils.getString(R.string.app_str_smart_panel_theme_4), StringUtils.getString(R.string.app_str_smart_panel_theme_1), StringUtils.getString(R.string.app_str_smart_panel_theme_5), StringUtils.getString(R.string.app_str_smart_panel_theme_2), StringUtils.getString(R.string.app_str_smart_panel_theme_3), StringUtils.getString(R.string.app_str_smart_panel_theme_6), StringUtils.getString(R.string.app_str_smart_panel_theme_7), StringUtils.getString(R.string.app_str_smart_panel_theme_8), StringUtils.getString(R.string.app_str_smart_panel_theme_9), StringUtils.getString(R.string.app_str_smart_panel_theme_10), StringUtils.getString(R.string.app_str_smart_panel_theme_11)};
    public final int[] themePic = {R.mipmap.g4_set_list_im4, R.mipmap.g4_set_list_im1, R.mipmap.g4_set_list_im5, R.mipmap.g4_set_list_im2, R.mipmap.g4_set_list_im3, R.mipmap.g4_set_list_im6, R.mipmap.g4_set_list_im7, R.mipmap.g4_set_list_im8, R.mipmap.g4_set_list_im9, R.mipmap.g4_set_list_im10, R.mipmap.g4_set_list_im11};

    public void changeScreenSaverTime(int i) {
        final int i2 = 30;
        switch (i) {
            case 1:
                i2 = 60;
                break;
            case 2:
                i2 = 180;
                break;
            case 3:
                i2 = 300;
                break;
            case 4:
                i2 = 600;
                break;
            case 5:
                i2 = WinError.ERROR_INVALID_PRIORITY;
                break;
            case 6:
                i2 = 65535;
                break;
        }
        this.screensaverTime.setValue(Integer.valueOf(i2));
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setSmartPanelScreensaverTime(getContext(), i2, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    Object value = ActSmartPanelThemeVM.this.controlObject.getValue();
                    if (value instanceof Device) {
                        Device device = (Device) value;
                        ReplaceHelper.instance().backupData(ActSmartPanelThemeVM.this.getLifecycleOwner(), device.getDeviceId(), UpdateBackDataRequest.SCREEN_REST, CmdAssistant.getDeviceAssistant(device, new int[0]).setSmartPanelScreensaverTime(i2));
                    }
                }
            }
        });
    }

    public void screenClose(final boolean isChecked) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setSmartPanelScreensaverMode(getContext(), isChecked ? 2 : 1, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    Object value = ActSmartPanelThemeVM.this.controlObject.getValue();
                    if (value instanceof Device) {
                        Device device = (Device) value;
                        ReplaceHelper.instance().backupData(ActSmartPanelThemeVM.this.getLifecycleOwner(), device.getDeviceId(), UpdateBackDataRequest.SCREEN_SAVER, CmdAssistant.getDeviceAssistant(device, new int[0]).setSmartPanelScreensaverMode(isChecked ? 2 : 1));
                    }
                }
            }
        });
    }

    public void changeTheme(final int i) {
        showLoadingDialog();
        if (this.controlObject.getValue() instanceof Device) {
            CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setSmartPanelScreenTheme(getContext(), 1 << this.curScreen, i, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.3
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg msg) {
                    ActSmartPanelThemeVM.this.dismissLoadingDialog();
                    if (msg != null) {
                        if (msg.getStateCode() == 38) {
                            ActSmartPanelThemeVM.this.downloadEvent.postValue(Integer.valueOf(i));
                            return;
                        } else if (msg.getStateCode() == 0) {
                            ActSmartPanelThemeVM.this.applyEvent.postValue(Integer.valueOf(i - 1));
                            return;
                        } else {
                            ActSmartPanelThemeVM actSmartPanelThemeVM = ActSmartPanelThemeVM.this;
                            actSmartPanelThemeVM.showErrorTipDialog(actSmartPanelThemeVM.getContext().getString(R.string.set_fail));
                            return;
                        }
                    }
                    ActSmartPanelThemeVM actSmartPanelThemeVM2 = ActSmartPanelThemeVM.this;
                    actSmartPanelThemeVM2.showErrorTipDialog(actSmartPanelThemeVM2.getContext().getString(R.string.set_fail));
                }
            });
            return;
        }
        Object value = this.controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            if (group.getDeviceIds().isEmpty()) {
                showErrorTipDialog(getContext().getString(R.string.set_fail));
            } else if (i > 5) {
                this.downloadEvent.postValue(Integer.valueOf(i));
            } else {
                CmdAssistant.getDeviceAssistant(group, new int[0]).setSmartPanelScreenTheme(getContext(), 1 << this.curScreen, i, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.4
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg msg) {
                        ActSmartPanelThemeVM.this.dismissLoadingDialog();
                        if (msg != null) {
                            if (msg.getStateCode() == 38) {
                                ActSmartPanelThemeVM.this.downloadEvent.postValue(Integer.valueOf(i));
                                return;
                            } else if (msg.getStateCode() == 0) {
                                ActSmartPanelThemeVM.this.applyThemes[ActSmartPanelThemeVM.this.curScreen] = i;
                                ActSmartPanelThemeVM.this.applyEvent.postValue(Integer.valueOf(i - 1));
                                return;
                            } else {
                                ActSmartPanelThemeVM actSmartPanelThemeVM = ActSmartPanelThemeVM.this;
                                actSmartPanelThemeVM.showErrorTipDialog(actSmartPanelThemeVM.getContext().getString(R.string.set_fail));
                                return;
                            }
                        }
                        ActSmartPanelThemeVM actSmartPanelThemeVM2 = ActSmartPanelThemeVM.this;
                        actSmartPanelThemeVM2.showErrorTipDialog(actSmartPanelThemeVM2.getContext().getString(R.string.set_fail));
                    }
                });
            }
        }
    }

    public void initData() {
        ArrayList arrayList = new ArrayList();
        this.themeList = arrayList;
        arrayList.add(new Item(this.themePic[0], this.themeNames[0]));
        this.themeList.add(new Item(this.themePic[1], this.themeNames[1]));
        this.themeList.add(new Item(this.themePic[2], this.themeNames[2]));
        this.themeList.add(new Item(this.themePic[3], this.themeNames[3]));
        this.themeList.add(new Item(this.themePic[4], this.themeNames[4]));
        this.themeList.add(new Item(this.themePic[5], this.themeNames[5]));
        this.themeList.add(new Item(this.themePic[6], this.themeNames[6]));
        this.themeList.add(new Item(this.themePic[7], this.themeNames[7]));
        this.themeList.add(new Item(this.themePic[8], this.themeNames[8]));
        this.themeList.add(new Item(this.themePic[9], this.themeNames[9]));
        this.themeList.add(new Item(this.themePic[10], this.themeNames[10]));
        int i = 0;
        while (i < getThemeList().size()) {
            int i2 = i + 1;
            Item item = getThemeList().get(i);
            if (this.groupControl) {
                item.setApply(false);
                item.setDownload(true);
            } else {
                if (this.themeNum == i2) {
                    item.setApply(true);
                } else {
                    item.setApply(false);
                }
                if (i2 < 6) {
                    item.setDownload(true);
                } else {
                    List<Integer> list = this.downloadThemes;
                    item.setDownload((list == null || list.isEmpty() || !this.downloadThemes.contains(Integer.valueOf(i2))) ? false : true);
                }
            }
            i = i2;
        }
        ArrayList arrayList2 = new ArrayList();
        this.timeList = arrayList2;
        arrayList2.add(ProductId.BLE_KNOB_PANEL_E6T + StringUtils.getString(R.string.sec));
        this.timeList.add("1" + StringUtils.getString(R.string.min));
        this.timeList.add("3" + StringUtils.getString(R.string.min));
        this.timeList.add("5" + StringUtils.getString(R.string.min));
        this.timeList.add("10" + StringUtils.getString(R.string.min));
        this.timeList.add(ProductId.BLE_KNOB_PANEL_E6T + StringUtils.getString(R.string.min));
        this.timeList.add(StringUtils.getString(R.string.mr_stay_time_11));
    }

    public String getTimeString() {
        int intValue = this.screensaverTime.getValue().intValue();
        if (intValue == 30) {
            return ProductId.BLE_KNOB_PANEL_E6T + StringUtils.getString(R.string.sec);
        }
        if (intValue == 60) {
            return "1" + StringUtils.getString(R.string.min);
        }
        if (intValue == 180) {
            return "3" + StringUtils.getString(R.string.min);
        }
        if (intValue == 300) {
            return "5" + StringUtils.getString(R.string.min);
        }
        if (intValue == 600) {
            return "10" + StringUtils.getString(R.string.min);
        }
        if (intValue == 1800) {
            return ProductId.BLE_KNOB_PANEL_E6T + StringUtils.getString(R.string.min);
        }
        if (intValue == 65535) {
            return StringUtils.getString(R.string.mr_stay_time_11);
        }
        return ProductId.BLE_KNOB_PANEL_E6T + StringUtils.getString(R.string.sec);
    }

    public int getTimePos() {
        int intValue = this.screensaverTime.getValue() == null ? 30 : this.screensaverTime.getValue().intValue();
        if (intValue == 60) {
            return 1;
        }
        if (intValue == 180) {
            return 2;
        }
        if (intValue == 300) {
            return 3;
        }
        if (intValue == 600) {
            return 4;
        }
        if (intValue != 1800) {
            return intValue != 65535 ? 0 : 6;
        }
        return 5;
    }

    public void getTheme() {
        CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelScreenState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelThemeVM.this.lambda$getTheme$0((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTheme$0(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            Object value = this.controlObject.getValue();
            if (value instanceof Device) {
                BleParam bleParam = (BleParam) ((Device) value).getParam(BleParam.class);
                parserPanelScreenState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelScreenState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, 12, responseMsg.getResData()));
                return;
            }
            Object value2 = this.controlObject.getValue();
            if (value2 instanceof Group) {
                Group group = (Group) value2;
                if (group.getDeviceIds().isEmpty()) {
                    return;
                }
                BleParam bleParam2 = (BleParam) Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue()).getParam(BleParam.class);
                parserPanelScreenState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelScreenState(responseMsg.getAgreementId(), bleParam2 != null ? bleParam2.getUnicastAddress() : 0, 12, responseMsg.getResData()));
            }
        }
    }

    private void parserPanelScreenState(SmartPanelScreenState smartPanelScreenState) {
        if (smartPanelScreenState == null || smartPanelScreenState.getDownloads() == null || smartPanelScreenState.getDownloads().isEmpty()) {
            return;
        }
        this.downloadThemes = smartPanelScreenState.getDownloads();
    }

    public List<Item> getThemeList() {
        return this.themeList;
    }

    public void setThemeList(List<Item> themeList) {
        this.themeList = themeList;
    }

    public List<String> getTimeList() {
        return this.timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public byte[] getFirmwareData(int num) {
        return GZipUtil.compress(G4IconUtils.getThemeData(num));
    }

    public void setGroupTheme(int num, final IAction<Boolean> iAction) {
        ArrayList arrayList = new ArrayList();
        Iterator<NeedDownloadDevice> it = this.connectAddress.iterator();
        while (it.hasNext()) {
            arrayList.add(setTheme(it.next(), num));
        }
        ((ObservableSubscribeProxy) Observable.concat(arrayList).subscribeOn(Schedulers.io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Boolean>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.5
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean aBoolean) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(false);
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(true);
                }
            }
        });
    }

    private Observable<Boolean> setTheme(final NeedDownloadDevice needDownloadDevice, final int num) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.6
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                CmdAssistant.getDeviceAssistant(needDownloadDevice.getDevice(), new int[0]).setSmartPanelScreenTheme(ActSmartPanelThemeVM.this.getContext(), 1 << ActSmartPanelThemeVM.this.curScreen, num, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM.6.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg msg) {
                        if (msg != null) {
                            if (msg.getStateCode() == 0) {
                                ActSmartPanelThemeVM.this.connectAddress.remove(needDownloadDevice);
                            } else if (msg.getStateCode() != 38) {
                                ActSmartPanelThemeVM.this.failDevices.add(new FailDevice(needDownloadDevice.device, needDownloadDevice.address));
                                ActSmartPanelThemeVM.this.connectAddress.remove(needDownloadDevice);
                            }
                        } else {
                            ActSmartPanelThemeVM.this.failDevices.add(new FailDevice(needDownloadDevice.device, needDownloadDevice.address));
                            ActSmartPanelThemeVM.this.connectAddress.remove(needDownloadDevice);
                        }
                        emitter.onNext(true);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    static class Item {
        private boolean apply;
        private int img;
        private boolean isDownload;
        private String name;

        public Item(int img, String name) {
            this.img = img;
            this.name = name;
        }

        public boolean isDownload() {
            return this.isDownload;
        }

        public void setDownload(boolean download) {
            this.isDownload = download;
        }

        public boolean isApply() {
            return this.apply;
        }

        public void setApply(boolean apply) {
            this.apply = apply;
        }

        public int getImg() {
            return this.img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class NeedDownloadDevice {
        private String address;
        private Device device;

        public NeedDownloadDevice(Device device, String address) {
            this.device = device;
            this.address = address;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class FailDevice {
        private String address;
        private Device device;

        public FailDevice(Device device, String address) {
            this.device = device;
            this.address = address;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}