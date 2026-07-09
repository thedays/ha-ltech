package com.ltech.smarthome.model.repo;

import android.text.TextUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.CgdProParam;
import com.ltech.smarthome.model.device_param.ExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ifun.IRole;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.net.response.group.ListGroupResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class RoleRepository extends BaseRepository implements IRole {
    private boolean isFirst;
    private long mLastPlaceId;
    private boolean needOldStatus;

    public RoleRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
        this.isFirst = true;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IRole
    public Observable<List<Role>> getRoleList(long placeId, long floorId, long roomId) {
        if (this.mLastPlaceId != placeId) {
            this.mLastPlaceId = placeId;
            this.isFirst = true;
            this.needOldStatus = false;
        } else {
            this.needOldStatus = true;
        }
        return getRoleFromNet(placeId, floorId, roomId);
    }

    private Observable<List<Role>> getRoleFromNet(final long placeId, final long floorId, final long roomId) {
        if (SharedPreferenceUtil.queryBooleanValue(Constants.GROUP_CHANGED)) {
            final MergeData mergeData = new MergeData();
            return Injection.net().listDevice(placeId, this.mUser.getUserId()).flatMap(new Function<ListDeviceResponse, ObservableSource<List<Role>>>() { // from class: com.ltech.smarthome.model.repo.RoleRepository.1
                @Override // io.reactivex.functions.Function
                public ObservableSource<List<Role>> apply(ListDeviceResponse listDeviceResponse) throws Exception {
                    mergeData.setDeviceResponse(listDeviceResponse);
                    return Injection.net().listGroup(placeId).map(new Function<ListGroupResponse, List<Role>>() { // from class: com.ltech.smarthome.model.repo.RoleRepository.1.1
                        @Override // io.reactivex.functions.Function
                        public List<Role> apply(ListGroupResponse listGroupResponse) throws Exception {
                            SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, false);
                            mergeData.setGroupResponse(listGroupResponse);
                            return RoleRepository.this.merge(placeId, mergeData);
                        }
                    });
                }
            });
        }
        return Injection.net().listDevice(placeId, this.mUser.getUserId()).flatMap(new Function<ListDeviceResponse, ObservableSource<List<Role>>>() { // from class: com.ltech.smarthome.model.repo.RoleRepository.2
            @Override // io.reactivex.functions.Function
            public ObservableSource<List<Role>> apply(ListDeviceResponse listDeviceResponse) throws Exception {
                final ArrayList arrayList = new ArrayList(RoleRepository.this.setDeviceData(placeId, listDeviceResponse));
                return Observable.create(new ObservableOnSubscribe<List<Role>>() { // from class: com.ltech.smarthome.model.repo.RoleRepository.2.1
                    @Override // io.reactivex.ObservableOnSubscribe
                    public void subscribe(ObservableEmitter<List<Role>> emitter) throws Exception {
                        arrayList.addAll(RoleRepository.this.mBoxBuilderFactory.queryGroupList(RoleRepository.this.mUser.getUserId(), placeId, floorId, roomId).build().find());
                        emitter.onNext(arrayList);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IRole
    public Observable<List<Role>> getGroupFromNet(final long placeId) {
        return Injection.net().listGroup(placeId).map(new Function<ListGroupResponse, List<Role>>() { // from class: com.ltech.smarthome.model.repo.RoleRepository.3
            @Override // io.reactivex.functions.Function
            public List<Role> apply(ListGroupResponse listGroupResponse) throws Exception {
                SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, false);
                return new ArrayList(RoleRepository.this.setGroupData(placeId, listGroupResponse));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Role> merge(long placeId, MergeData mergeData) {
        ArrayList arrayList = new ArrayList();
        if (mergeData.getDeviceResponse() != null && mergeData.getDeviceResponse().getRows() != null) {
            arrayList.addAll(setDeviceData(placeId, mergeData.getDeviceResponse()));
        }
        if (mergeData.getGroupResponse() != null && mergeData.getGroupResponse().getRows() != null) {
            arrayList.addAll(setGroupData(placeId, mergeData.getGroupResponse()));
        }
        return arrayList;
    }

    private Observable<List<Role>> getRoleFromDb(final long placeId, final long floorId, final long roomId) {
        return Observable.create(new ObservableOnSubscribe<List<Role>>() { // from class: com.ltech.smarthome.model.repo.RoleRepository.4
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Role>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                List<Device> find = RoleRepository.this.mBoxBuilderFactory.queryDeviceList(RoleRepository.this.mUser.getUserId(), placeId, floorId, roomId).build().find();
                List<Group> find2 = RoleRepository.this.mBoxBuilderFactory.queryGroupList(RoleRepository.this.mUser.getUserId(), placeId, floorId, roomId).build().find();
                arrayList.addAll(find);
                arrayList.addAll(find2);
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        });
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IRole
    public List<Device> setDeviceData(long placeId, ListDeviceResponse response) {
        Device deviceData;
        Box boxFor = this.mBoxStore.boxFor(Device.class);
        List<Device> find = this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), placeId, -1L, -1L).build().find();
        ArrayList arrayList = new ArrayList(response.getTotal());
        ArrayList arrayList2 = new ArrayList();
        if (response.getTotal() > 0) {
            for (ListDeviceResponse.RowsBean rowsBean : response.getRows()) {
                if (rowsBean.getMacfalg() == 2) {
                    String subDeviceId = getSubDeviceId(rowsBean.getAipuducttype());
                    if (!TextUtils.isEmpty(subDeviceId)) {
                        deviceData = setDeviceData(rowsBean);
                        if (deviceData.getExtParam() != null && deviceData.getExtParam(ExtParam.class) != null && ((ExtParam) deviceData.getExtParam(ExtParam.class)).getACType() == 1 && deviceData.isSubDevice()) {
                            deviceData.setProductId(ProductId.CENTRAL_AIR_SUB_DEVICE);
                        } else if (deviceData.getExtParam() != null && deviceData.getExtParam(ExtParam.class) != null && ((ExtParam) deviceData.getExtParam(ExtParam.class)).getACType() == 2) {
                            deviceData.setProductId(ProductId.FRESH_AIR_SUB_DEVICE);
                        } else if (deviceData.getExtParam() != null && deviceData.getExtParam(ExtParam.class) != null && ((ExtParam) deviceData.getExtParam(ExtParam.class)).getACType() == 3) {
                            deviceData.setProductId(ProductId.FLOOR_HEAT_SUB_DEVICE);
                        } else {
                            deviceData.setProductId(subDeviceId);
                        }
                        arrayList2.add(deviceData);
                    } else {
                        deviceData = setDeviceData(rowsBean);
                        Iterator<ListDeviceResponse.RowsBean> it = response.getRows().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ListDeviceResponse.RowsBean next = it.next();
                            if (next.getDeviceid() == deviceData.getMacdeviceid()) {
                                deviceData.setMainProductId(next.getProductid());
                                break;
                            }
                        }
                        arrayList2.add(deviceData);
                    }
                    if (rowsBean.getParam() != null && !rowsBean.getParam().isEmpty()) {
                        CgdProParam cgdProParam = (CgdProParam) rowsBean.getParam(CgdProParam.class);
                        if (cgdProParam.getDeviceType() >= 101 && cgdProParam.getDeviceType() <= 105) {
                            deviceData = setDeviceData(rowsBean);
                            arrayList2.add(deviceData);
                        }
                    }
                } else {
                    deviceData = ProductRepository.checkDevice(rowsBean.getProductid()) ? setDeviceData(rowsBean) : null;
                }
                if (deviceData != null) {
                    if (this.isFirst) {
                        deviceData.setCheckTime(System.currentTimeMillis());
                    }
                    restoreState(find, deviceData);
                    arrayList.add(deviceData);
                }
            }
            this.isFirst = false;
            for (int i = 0; i < arrayList2.size(); i++) {
                Device device = (Device) arrayList2.get(i);
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        Device device2 = (Device) it2.next();
                        if (device2.getDeviceId() == device.getMacdeviceid()) {
                            device.setOnlineFlag(device2.getOnlineFlag());
                            break;
                        }
                    }
                }
            }
        }
        this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), placeId, -1L, -1L).build().remove();
        boxFor.put((Collection) arrayList);
        return arrayList;
    }

    public List<Group> setGroupData(long placeId, ListGroupResponse response) {
        Box boxFor = this.mBoxStore.boxFor(Group.class);
        List<Group> find = this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), placeId).build().find();
        ArrayList arrayList = new ArrayList();
        if (response.getTotal() > 0) {
            for (ListGroupResponse.RowsBean rowsBean : response.getRows()) {
                Group group = new Group();
                group.setUserId(this.mUser.getUserId());
                group.setGroupId(rowsBean.getGroupid());
                group.setPlaceId(rowsBean.getPlaceid());
                group.setGroupName(rowsBean.getGroupname());
                group.setModuleType(rowsBean.getType());
                group.setControlType(rowsBean.getColortype());
                group.setGroupIndex(rowsBean.getGroupindex());
                group.setGroupAddress(TextUtils.isEmpty(rowsBean.getIndex()) ? 0 : Integer.parseInt(rowsBean.getIndex(), 16));
                group.setParam(rowsBean.getParam());
                group.setExtParam(rowsBean.getParamext());
                group.setRoomId(rowsBean.getRoomid());
                group.setFloorId(rowsBean.getFloorid());
                group.setCreatetime(rowsBean.getCreatetime());
                group.setImgindex(rowsBean.getImgindex());
                group.setMaxkelvin(rowsBean.getMaxkelvin());
                group.setMinkelvin(rowsBean.getMinkelvin());
                group.setMacdeviceid(rowsBean.getMacdeviceid());
                group.setSubkey(rowsBean.getSubkey());
                group.setMaingroupid(rowsBean.getMaingroupid());
                group.setSubhide(rowsBean.getSubhide());
                group.setPresetKValues(rowsBean.getPresetKValues());
                group.setEditTime(System.currentTimeMillis());
                group.setColorPaletteUrl(rowsBean.getColorPaletteUrl());
                if (this.isFirst) {
                    group.setReportinstruct(rowsBean.getReportinstruct());
                }
                group.setLeaderSup(rowsBean.getLeaderSup());
                Floor floor = Injection.repo().home().getFloor(rowsBean.getFloorid());
                String str = "";
                group.setFloorName(floor != null ? floor.getFloorName() : "");
                Room room = Injection.repo().home().getRoom(rowsBean.getRoomid());
                if (room != null) {
                    str = room.getRoomName();
                }
                group.setRoomName(str);
                if (rowsBean.getDevices() != null) {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<ListGroupResponse.RowsBean.DevicesBean> it = rowsBean.getDevices().iterator();
                    while (it.hasNext()) {
                        arrayList2.add(Long.valueOf(it.next().getDeviceid()));
                    }
                    group.setDeviceIds(arrayList2);
                }
                if (!group.getDeviceIds().isEmpty()) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
                    if (deviceByDeviceId != null) {
                        group.setFirstDevUniAddr(deviceByDeviceId.getUnicastAddress());
                    }
                }
                if (group.getMaingroupid() > 0) {
                    Iterator<ListGroupResponse.RowsBean> it2 = response.getRows().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            ListGroupResponse.RowsBean next = it2.next();
                            if (next.getGroupid() == group.getMaingroupid()) {
                                group.setMainModuleType(next.getType());
                                group.setMainControlType(next.getColortype());
                                break;
                            }
                        }
                    }
                }
                restoreState(find, group);
                arrayList.add(group);
            }
        }
        this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), placeId, -1L, -1L).build().remove();
        boxFor.put((Collection) arrayList);
        return arrayList;
    }

    private void restoreState(List<Group> groupList, Group group) {
        Iterator<Group> it = groupList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Group next = it.next();
            if (next.equals(group)) {
                group.setId(next.getId());
                if (next.getGroupState() != null) {
                    group.setGroupState(next.getGroupState());
                    if (next.getGroupState().getOnOffStates() == null) {
                        int zoneCount = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group));
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < zoneCount; i++) {
                            arrayList.add(false);
                        }
                        group.getGroupState().setOnOffStates(arrayList);
                    }
                }
            }
        }
        if (group.getGroupState() == null) {
            group.setGroupState(new DeviceState());
            if (group.getMaingroupid() > 0) {
                group.getGroupState().setOn(false);
                return;
            }
            int zoneCount2 = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group));
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < zoneCount2; i2++) {
                arrayList2.add(false);
            }
            group.getGroupState().setOnOffStates(arrayList2);
        }
    }

    static class MergeData {
        private ListDeviceResponse deviceResponse;
        private ListGroupResponse groupResponse;

        MergeData() {
        }

        public ListGroupResponse getGroupResponse() {
            return this.groupResponse;
        }

        public void setGroupResponse(ListGroupResponse groupResponse) {
            this.groupResponse = groupResponse;
        }

        public ListDeviceResponse getDeviceResponse() {
            return this.deviceResponse;
        }

        public void setDeviceResponse(ListDeviceResponse deviceResponse) {
            this.deviceResponse = deviceResponse;
        }
    }

    private String getSubDeviceId(String type) {
        return ProductRepository.getIrProductIdByType(type);
    }

    private Device setDeviceData(ListDeviceResponse.RowsBean bean) {
        BleParam bleParam;
        Device device = new Device();
        device.setUserId(this.mUser.getUserId());
        device.setDeviceId(bean.getDeviceid());
        device.setDevicesn(bean.getDevicesn());
        device.setPlaceId(bean.getPlaceid());
        device.setFloorId(bean.getFloorid());
        device.setRoomId(bean.getRoomid());
        device.setWifiMac(bean.getMac());
        device.setProductId(bean.getProductid());
        device.setPlatFormDeviceId(bean.getPlatformdeviceid());
        device.setDeviceName(bean.getDevicename());
        device.setIndex(bean.getDeviceindex());
        device.setCreatetime(bean.getCreatetime());
        device.setMcuversion(bean.getMcuversion());
        device.setFirmwareversion(bean.getFirmwareversion());
        device.setMaxkelvin(bean.getMaxkelvin());
        device.setMinkelvin(bean.getMinkelvin());
        device.setBleversion(bean.getBleversion());
        device.setMaccode(bean.getMaccode());
        device.setOauthtype(bean.getOauthtype());
        if (ProductRepository.isSuperPanel(bean.getProductid())) {
            device.setBtmodule("bt100");
        } else {
            device.setBtmodule(bean.getBtmodule());
        }
        device.setSubhide(bean.getSubhide());
        device.setPanelColor(bean.getPanelColor());
        if (this.isFirst) {
            device.setReportinstruct(bean.getReportinstruct());
        }
        device.setStatus(bean.getStatus());
        device.setVirtual(bean.getVirtual());
        device.setWritable(bean.getWritable());
        device.setPresetKValues(bean.getPresetKValues());
        if (ProductRepository.getAgreementIdByPid(bean.getProductid()) != 2) {
            device.setOnlineFlag(bean.getOnlineflag());
        }
        device.setImageIndex(bean.getImgindex());
        device.setMacfalg(bean.getMacfalg());
        device.setMacdeviceid(bean.getMacdeviceid());
        device.setParam(bean.getParam());
        device.setExtParam(bean.getParamext());
        Floor floor = Injection.repo().home().getFloor(bean.getFloorid());
        String str = "";
        device.setFloorName(floor != null ? floor.getFloorName() : "");
        Room room = Injection.repo().home().getRoom(bean.getRoomid());
        if (room != null) {
            str = room.getRoomName();
        }
        device.setRoomName(str);
        if (!TextUtils.isEmpty(device.getParam()) && (bleParam = (BleParam) device.getParam(BleParam.class)) != null) {
            device.setUnicastAddress(bleParam.getUnicastAddress());
        }
        if (ProductRepository.getLightColorType((Object) device) == 2 && device.isVirtual() && device.getMaxkelvin() == 0) {
            device.setMaxkelvin(6500);
            device.setMinkelvin(2700);
        }
        return device;
    }

    private void restoreState(List<Device> deviceList, Device device) {
        int agreementIdByPid = ProductRepository.getAgreementIdByPid(device.getProductId());
        Iterator<Device> it = deviceList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Device next = it.next();
            if (next.equals(device)) {
                device.setId(next.getId());
                if (next.getDeviceState() != null) {
                    device.setDeviceState(next.getDeviceState());
                }
                if (this.needOldStatus) {
                    device.setCheckTime(next.getCheckTime());
                }
                if (agreementIdByPid == 2) {
                    device.setOnlineFlag(next.getOnlineFlag());
                }
                if (ProductRepository.isCentralAcSubDevice(device.getProductId())) {
                    device.setParam(next.getParam());
                }
            }
        }
        if (device.getDeviceState() == null) {
            device.setDeviceState(new DeviceState());
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IRole
    public List<Role> getRoleListByRoomIdFromDb(long placeId, long floorId, long roomId) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), placeId, floorId, roomId).build().find());
        arrayList.addAll(this.mBoxBuilderFactory.queryGroupList(this.mUser.getUserId(), placeId, floorId, roomId).build().find());
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IRole
    public Role getRoleById(long controlId, boolean group) {
        if (group) {
            return Injection.repo().group().getGroupById(controlId);
        }
        return Injection.repo().device().getDeviceById(controlId);
    }
}