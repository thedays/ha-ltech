package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.super_panel.ListNeedUpgradeResponse;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActChildMcuUpgradeVM extends BaseViewModel {
    public long controlId;
    public List<Long> failIds;
    public long panelId;
    public long placeId;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    public MediatorLiveData<List<NeedUpgradeDevice>> data = new MediatorLiveData<>();
    public SingleLiveEvent<Void> refreshBtn = new SingleLiveEvent<>();
    public List<NeedUpgradeDevice> devices = new ArrayList();
    public List<Floor> mFloorList = new ArrayList();
    public List<Room> mRoomList = new ArrayList();
    public boolean isUpgrading = false;

    public Floor checkFloor(List<Floor> floorList) {
        Floor floor;
        Floor value = this.selectFloor.getValue();
        if (value != null) {
            Iterator<Floor> it = floorList.iterator();
            while (it.hasNext()) {
                if (it.next().getFloorId() == value.getFloorId()) {
                    return value;
                }
            }
        } else if (this.controlDevice.getValue() != null && (floor = Injection.repo().home().getFloor(this.controlDevice.getValue().getFloorId())) != null) {
            return floor;
        }
        return floorList.get(0);
    }

    public boolean setCurFloor(Floor floor) {
        this.selectFloor.setValue(floor);
        return true;
    }

    public Floor getCurFloor() {
        return this.selectFloor.getValue();
    }

    public int getCurFloorPos() {
        Iterator<Floor> it = this.mFloorList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getFloorId() == getCurFloor().getFloorId()) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public int getCurRoomPos() {
        Iterator<Room> it = this.mRoomList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getRoomId() == getCurRoom().getRoomId()) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public Room checkRoom(List<Room> roomList) {
        Room room;
        Room value = this.selectRoom.getValue();
        if (value != null) {
            Iterator<Room> it = roomList.iterator();
            while (it.hasNext()) {
                if (it.next().getRoomId() == value.getRoomId()) {
                    return value;
                }
            }
        } else if (this.controlDevice.getValue() != null && (room = Injection.repo().home().getRoom(this.controlDevice.getValue().getRoomId())) != null) {
            return room;
        }
        return roomList.get(0);
    }

    public boolean setCurRoom(Room room) {
        this.selectRoom.setValue(room);
        return true;
    }

    public Room getCurRoom() {
        return this.selectRoom.getValue();
    }

    /* renamed from: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM$1, reason: invalid class name */
    class AnonymousClass1 implements IAction<Boolean> {
        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.base.IAction
        public void act(Boolean aBoolean) {
            ActChildMcuUpgradeVM.this.isUpgrading = aBoolean.booleanValue();
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.1.1
                @Override // java.lang.Runnable
                public void run() {
                    ((ObservableSubscribeProxy) Injection.net().queryNeedUpgradeList(ActChildMcuUpgradeVM.this.panelId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActChildMcuUpgradeVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<ListNeedUpgradeResponse>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.1.1.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(ListNeedUpgradeResponse listNeedUpgradeResponse) throws Exception {
                            ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                            if (listNeedUpgradeResponse == null || listNeedUpgradeResponse.getRows() == null) {
                                return;
                            }
                            ArrayList arrayList = new ArrayList();
                            Iterator<ListNeedUpgradeResponse.RowsBean> it = listNeedUpgradeResponse.getRows().iterator();
                            while (it.hasNext()) {
                                NeedUpgradeDevice needUpgradeDevice = ActChildMcuUpgradeVM.getNeedUpgradeDevice(it.next());
                                if (needUpgradeDevice != null) {
                                    arrayList.add(needUpgradeDevice);
                                }
                            }
                            ActChildMcuUpgradeVM.this.devices = arrayList;
                            Collections.reverse(ActChildMcuUpgradeVM.this.devices);
                            if (ActChildMcuUpgradeVM.this.getCurFloor().getFloorId() == -1 && ActChildMcuUpgradeVM.this.getCurRoom().getRoomId() == -1) {
                                ActChildMcuUpgradeVM.this.data.setValue(ActChildMcuUpgradeVM.this.devices);
                                return;
                            }
                            ArrayList arrayList2 = new ArrayList();
                            for (NeedUpgradeDevice needUpgradeDevice2 : ActChildMcuUpgradeVM.this.devices) {
                                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(needUpgradeDevice2.getDeviceid());
                                if ((ActChildMcuUpgradeVM.this.getCurRoom().getRoomId() == -1 && ActChildMcuUpgradeVM.this.getCurFloor().getFloorId() == deviceByDeviceId.getFloorId()) || deviceByDeviceId.getRoomId() == ActChildMcuUpgradeVM.this.getCurRoom().getRoomId()) {
                                    arrayList2.add(needUpgradeDevice2);
                                }
                            }
                            ActChildMcuUpgradeVM.this.data.setValue(arrayList2);
                        }
                    }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.1.1.2
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Throwable throwable) throws Exception {
                            ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                        }
                    });
                }
            }, 1500L);
        }
    }

    public void stop() {
        showLoadingDialog();
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).stopChildMcuUpgrade(getContext(), new AnonymousClass1());
    }

    public void queryNeedUpgradeList() {
        showLoadingDialog();
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).queryChildMcuIsUpgrade(getContext(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                if (msg != null) {
                    if (msg.getStateCode() != 0 || msg.getResData() == null || msg.getResData().length() <= 16) {
                        SmartToast.showCenterShort(ActChildMcuUpgradeVM.this.getContext().getString(R.string.cmd_firmware_need_upgrade));
                        return;
                    }
                    ActChildMcuUpgradeVM.this.isUpgrading = Integer.parseInt(msg.getResData().substring(16, 18), 16) == 1;
                    ((ObservableSubscribeProxy) Injection.net().queryNeedUpgradeList(ActChildMcuUpgradeVM.this.panelId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActChildMcuUpgradeVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<ListNeedUpgradeResponse>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.2.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(ListNeedUpgradeResponse listNeedUpgradeResponse) throws Exception {
                            ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                            if (listNeedUpgradeResponse == null || listNeedUpgradeResponse.getRows() == null) {
                                return;
                            }
                            ArrayList arrayList = new ArrayList();
                            Iterator<ListNeedUpgradeResponse.RowsBean> it = listNeedUpgradeResponse.getRows().iterator();
                            while (it.hasNext()) {
                                NeedUpgradeDevice needUpgradeDevice = ActChildMcuUpgradeVM.getNeedUpgradeDevice(it.next());
                                if (needUpgradeDevice != null) {
                                    arrayList.add(needUpgradeDevice);
                                }
                            }
                            ActChildMcuUpgradeVM.this.devices = arrayList;
                            Collections.reverse(ActChildMcuUpgradeVM.this.devices);
                            if (ActChildMcuUpgradeVM.this.getCurFloor().getFloorId() == -1 && ActChildMcuUpgradeVM.this.getCurRoom().getRoomId() == -1) {
                                ActChildMcuUpgradeVM.this.data.setValue(ActChildMcuUpgradeVM.this.devices);
                                return;
                            }
                            ArrayList arrayList2 = new ArrayList();
                            for (NeedUpgradeDevice needUpgradeDevice2 : ActChildMcuUpgradeVM.this.devices) {
                                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(needUpgradeDevice2.getDeviceid());
                                if ((ActChildMcuUpgradeVM.this.getCurRoom().getRoomId() == -1 && ActChildMcuUpgradeVM.this.getCurFloor().getFloorId() == deviceByDeviceId.getFloorId()) || deviceByDeviceId.getRoomId() == ActChildMcuUpgradeVM.this.getCurRoom().getRoomId()) {
                                    arrayList2.add(needUpgradeDevice2);
                                }
                            }
                            ActChildMcuUpgradeVM.this.data.setValue(arrayList2);
                        }
                    }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.2.2
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Throwable throwable) throws Exception {
                            ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                        }
                    });
                    return;
                }
                ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                SmartToast.showCenterShort(ActChildMcuUpgradeVM.this.getContext().getString(R.string.error_loading));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NeedUpgradeDevice getNeedUpgradeDevice(ListNeedUpgradeResponse.RowsBean rowsBean) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(rowsBean.getDeviceid());
        if (deviceByDeviceId == null) {
            return null;
        }
        NeedUpgradeDevice needUpgradeDevice = new NeedUpgradeDevice();
        needUpgradeDevice.setUpgradeid(rowsBean.getUpgradeid());
        needUpgradeDevice.setUserid(rowsBean.getUserid());
        needUpgradeDevice.setPanelid(rowsBean.getPanelid());
        needUpgradeDevice.setPanelmac(rowsBean.getPanelmac());
        needUpgradeDevice.setDeviceid(rowsBean.getDeviceid());
        needUpgradeDevice.setNeedUpgrade(rowsBean.getNeedUpgrade());
        needUpgradeDevice.setLastversion(rowsBean.getLastversion());
        needUpgradeDevice.setMcuversion(rowsBean.getMcuversion());
        needUpgradeDevice.setName(deviceByDeviceId.getName());
        needUpgradeDevice.setIco(ProductRepository.getProductIcon(deviceByDeviceId));
        return needUpgradeDevice;
    }

    public void send(long panelId, List<Long> ids) {
        if (ids == null) {
            return;
        }
        this.failIds = null;
        showLoadingDialog();
        ((ObservableSubscribeProxy) Injection.net().sendChildMcuUpgrade(panelId, ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object o) throws Exception {
                ActChildMcuUpgradeVM.this.isUpgrading = true;
                ActChildMcuUpgradeVM.this.dismissLoadingDialog();
                ActChildMcuUpgradeVM.this.refreshBtn.call();
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", ActChildMcuUpgradeVM.this.getContext().getString(R.string.app_str_child_mcu_upgrade)).setOkButton(ActChildMcuUpgradeVM.this.getContext().getString(R.string.i_know)).setCancelable(false).setOkButton(new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.3.1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        return false;
                    }
                });
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActChildMcuUpgradeVM.this.dismissLoadingDialog();
            }
        });
    }

    public static class NeedUpgradeDevice {
        private long deviceid;
        private int ico;
        private boolean isSel;
        private String lastversion;
        private String mcuversion;
        private String name;
        private int needUpgrade;
        private long panelid;
        private String panelmac;
        private long upgradeid;
        private long userid;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIco() {
            return this.ico;
        }

        public void setIco(int ico) {
            this.ico = ico;
        }

        public String getLastversion() {
            return this.lastversion;
        }

        public void setLastversion(String lastversion) {
            this.lastversion = lastversion;
        }

        public String getMcuversion() {
            return this.mcuversion;
        }

        public void setMcuversion(String mcuversion) {
            this.mcuversion = mcuversion;
        }

        public boolean isSel() {
            return this.isSel;
        }

        public void setSel(boolean sel) {
            this.isSel = sel;
        }

        public long getUpgradeid() {
            return this.upgradeid;
        }

        public void setUpgradeid(long upgradeid) {
            this.upgradeid = upgradeid;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public long getPanelid() {
            return this.panelid;
        }

        public void setPanelid(long panelid) {
            this.panelid = panelid;
        }

        public String getPanelmac() {
            return this.panelmac;
        }

        public void setPanelmac(String panelmac) {
            this.panelmac = panelmac;
        }

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public int getNeedUpgrade() {
            return this.needUpgrade;
        }

        public void setNeedUpgrade(int needUpgrade) {
            this.needUpgrade = needUpgrade;
        }
    }
}