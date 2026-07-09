package com.ltech.smarthome.ui.device.dalipro;

import android.text.TextUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.device_param.CgdProParam;
import com.ltech.smarthome.model.device_param.CgdProSceneExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.smart.message.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class CgdProDataManager {
    private static CgdProDataManager instance;
    private List<String> deviceByteDataList = new ArrayList();
    private List<List<String>> sceneByteDataList = new ArrayList(16);
    private List<List<Integer>> groupPositionList = new ArrayList(16);

    public static CgdProDataManager getInstance() {
        if (instance == null) {
            synchronized (CgdProDataManager.class) {
                if (instance == null) {
                    instance = new CgdProDataManager();
                }
            }
        }
        return instance;
    }

    public void addDeviceByteData(String data) {
        this.deviceByteDataList.add(data);
    }

    public void addSceneByteDataList(int index, String data) {
        this.sceneByteDataList.get(index).add(data);
    }

    public List<String> getDeviceByteDataList() {
        return this.deviceByteDataList;
    }

    public List<Device> getDeviceList(Device controlDevice, boolean isCleanData) {
        List<Device> subDeviceListFromDb = Injection.repo().device().getSubDeviceListFromDb(controlDevice.getPlaceId(), -1L, -1L, controlDevice.getDeviceId());
        ArrayList arrayList = new ArrayList();
        if (subDeviceListFromDb != null && subDeviceListFromDb.size() > 0) {
            arrayList = new ArrayList(subDeviceListFromDb);
            Injection.boxStore().boxFor(Device.class).remove((Collection) subDeviceListFromDb);
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.deviceByteDataList.size(); i++) {
            String str = this.deviceByteDataList.get(i);
            int parseInt = Integer.parseInt(str.substring(0, 4), 16);
            addGroupPositionList(parseInt, i);
            int parseInt2 = Integer.parseInt(str.substring(4, 6), 16);
            int parseInt3 = Integer.parseInt(str.substring(6, 8), 16);
            Device deviceByAdd = getDeviceByAdd(arrayList, parseInt3);
            if (deviceByAdd == null || isCleanData) {
                deviceByAdd = new Device();
                CgdProParam cgdProParam = new CgdProParam();
                if (parseInt2 == 1) {
                    deviceByAdd.setProductId(ProductId.ID_BLE_LIGHT_DIM);
                    cgdProParam.setDeviceType(101);
                } else if (parseInt2 == 2) {
                    deviceByAdd.setProductId(ProductId.ID_BLE_LIGHT_CT);
                    cgdProParam.setDeviceType(102);
                } else if (parseInt2 == 3) {
                    deviceByAdd.setProductId(ProductId.ID_BLE_LIGHT_RGB);
                    cgdProParam.setDeviceType(103);
                } else if (parseInt2 == 4) {
                    deviceByAdd.setProductId(ProductId.ID_BLE_LIGHT_RGBW);
                    cgdProParam.setDeviceType(104);
                } else if (parseInt2 == 5) {
                    deviceByAdd.setProductId(ProductId.ID_BLE_LIGHT_RGBWY);
                    cgdProParam.setDeviceType(105);
                }
                cgdProParam.setUnicastAddress(controlDevice.getUnicastAddress());
                cgdProParam.setBleType(0);
                deviceByAdd.setParam(cgdProParam);
                deviceByAdd.setUserId(Injection.repo().user().getUserId());
                deviceByAdd.setPlaceId(Injection.repo().home().getSelPlace().getPlaceId());
                deviceByAdd.setFloorId(controlDevice.getFloorId());
                deviceByAdd.setRoomId(controlDevice.getRoomId());
                deviceByAdd.setMacdeviceid(controlDevice.getDeviceId());
                if (parseInt3 != 0) {
                    deviceByAdd.setDeviceName(ActivityUtils.getTopActivity().getResources().getString(R.string.light) + parseInt3);
                } else {
                    deviceByAdd.setDeviceName(ActivityUtils.getTopActivity().getResources().getString(R.string.light));
                }
                deviceByAdd.setMacfalg(2);
                deviceByAdd.setWifiMac(controlDevice.getWifiMac() + ProductId.SPLIT + StringUtils.demToHexDouble(parseInt3));
                deviceByAdd.setDevicesn(controlDevice.getWifiMac() + ProductId.SPLIT + StringUtils.demToHexDouble(parseInt3));
                if (parseInt2 == 2) {
                    deviceByAdd.setMinkelvin(2700);
                    deviceByAdd.setMaxkelvin(6500);
                }
                CgdProLightExtParam cgdProLightExtParam = new CgdProLightExtParam();
                cgdProLightExtParam.setDaliGroupBit(parseInt);
                cgdProLightExtParam.setDaliAddr(parseInt3);
                cgdProLightExtParam.setDaliType(parseInt2);
                cgdProLightExtParam.setDaliHidden(1);
                deviceByAdd.setExtParam(cgdProLightExtParam);
            }
            arrayList2.add(deviceByAdd);
        }
        return arrayList2;
    }

    private void addGroupPositionList(int groupInfo, int index) {
        for (int i = 0; i < 16; i++) {
            if (((1 << i) & groupInfo) != 0) {
                this.groupPositionList.get(i).add(Integer.valueOf(index));
            }
        }
    }

    public void clearDeviceByteList() {
        this.deviceByteDataList.clear();
    }

    public void clearGroupPositionList() {
        this.groupPositionList.clear();
        for (int i = 0; i < 16; i++) {
            this.groupPositionList.add(new ArrayList());
        }
    }

    public void clearScenePositionList() {
        this.sceneByteDataList.clear();
        for (int i = 0; i < 16; i++) {
            this.sceneByteDataList.add(new ArrayList());
        }
    }

    public List<Group> getGroupList(Device controlDevice, List<Device> deviceList, boolean isCleanData) {
        List<Group> subGroupListFromDb = Injection.repo().group().getSubGroupListFromDb(controlDevice.getPlaceId(), -1L, -1L, controlDevice.getDeviceId());
        if (subGroupListFromDb != null && subGroupListFromDb.size() > 0 && !isCleanData) {
            Collections.sort(subGroupListFromDb, new Comparator() { // from class: com.ltech.smarthome.ui.device.dalipro.CgdProDataManager$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return CgdProDataManager.lambda$getGroupList$0((Group) obj, (Group) obj2);
                }
            });
            for (int i = 0; i < subGroupListFromDb.size(); i++) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < this.groupPositionList.get(i).size(); i2++) {
                    arrayList.add(Long.valueOf(deviceList.get(this.groupPositionList.get(i).get(i2).intValue()).getDeviceId()));
                }
                Group group = subGroupListFromDb.get(i);
                group.setDeviceIds(arrayList);
                CgdProGroupExtParam cgdProGroupExtParam = (CgdProGroupExtParam) group.getExtParam(CgdProGroupExtParam.class);
                cgdProGroupExtParam.setMultiTypeBit(DaliProHelper.getDaliGroupType(group));
                group.setExtParam(cgdProGroupExtParam);
            }
            return subGroupListFromDb;
        }
        if (subGroupListFromDb != null && subGroupListFromDb.size() > 0) {
            Injection.boxStore().boxFor(Group.class).remove((Collection) subGroupListFromDb);
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < 16; i3++) {
            Group group2 = new Group();
            group2.setUserId(Injection.repo().user().getUserId());
            group2.setPlaceId(Injection.repo().home().getSelPlace().getPlaceId());
            group2.setFloorId(controlDevice.getFloorId());
            group2.setRoomId(controlDevice.getRoomId());
            group2.setMacdeviceid(controlDevice.getDeviceId());
            group2.setControlType(String.valueOf(101));
            group2.setImgindex(0);
            if (i3 != 0) {
                group2.setGroupName(ActivityUtils.getTopActivity().getResources().getString(R.string.group_dali) + i3);
            } else {
                group2.setGroupName(ActivityUtils.getTopActivity().getResources().getString(R.string.group_dali));
            }
            ArrayList arrayList3 = new ArrayList();
            for (int i4 = 0; i4 < this.groupPositionList.get(i3).size(); i4++) {
                arrayList3.add(Long.valueOf(deviceList.get(this.groupPositionList.get(i3).get(i4).intValue()).getDeviceId()));
            }
            group2.setDeviceIds(arrayList3);
            CgdProParam cgdProParam = new CgdProParam();
            cgdProParam.setDeviceType(101);
            group2.setParam(cgdProParam);
            CgdProGroupExtParam cgdProGroupExtParam2 = new CgdProGroupExtParam();
            cgdProGroupExtParam2.setDaliAddr(i3);
            cgdProGroupExtParam2.setDaliHidden(1);
            cgdProGroupExtParam2.setMultiTypeBit(DaliProHelper.getDaliGroupType(group2));
            group2.setExtParam(cgdProGroupExtParam2);
            arrayList2.add(group2);
        }
        return arrayList2;
    }

    static /* synthetic */ int lambda$getGroupList$0(Group group, Group group2) {
        return DaliProHelper.getDaliAddress(group) - DaliProHelper.getDaliAddress(group2);
    }

    public List<Scene> getSceneList(Device controlDevice, boolean isCleanData) {
        int parseInt;
        Device deviceByAdd;
        List<Device> list;
        int parseInt2;
        Device deviceByAdd2;
        List<Scene> sceneListByRoomId = Injection.repo().scene().getSceneListByRoomId(controlDevice.getPlaceId(), -1L, -1L, controlDevice.getDeviceId());
        int i = 16;
        int i2 = 0;
        if (sceneListByRoomId != null && sceneListByRoomId.size() > 0 && !isCleanData) {
            List<Device> subDeviceListFromDb = Injection.repo().device().getSubDeviceListFromDb(controlDevice.getPlaceId(), -1L, -1L, controlDevice.getDeviceId());
            int i3 = 0;
            while (i3 < sceneListByRoomId.size()) {
                HashMap hashMap = new HashMap();
                int i4 = 0;
                while (i4 < this.sceneByteDataList.get(i3).size()) {
                    String str = this.sceneByteDataList.get(i3).get(i4);
                    if (TextUtils.isEmpty(str) || (deviceByAdd2 = getDeviceByAdd(subDeviceListFromDb, (parseInt2 = Integer.parseInt(str.substring(i2, 2), 16)))) == null) {
                        list = subDeviceListFromDb;
                    } else {
                        LocalDeviceParam localDeviceParam = new LocalDeviceParam();
                        list = subDeviceListFromDb;
                        localDeviceParam.deviceid = deviceByAdd2.getDeviceId();
                        localDeviceParam.instruct = str.substring(2);
                        if (localDeviceParam.instruct.length() == 4) {
                            localDeviceParam.option = "4";
                        } else {
                            localDeviceParam.option = "0";
                        }
                        localDeviceParam.sceneZone = parseInt2;
                        hashMap.put(Long.valueOf(deviceByAdd2.getDeviceId()), localDeviceParam);
                    }
                    i4++;
                    subDeviceListFromDb = list;
                    i2 = 0;
                }
                List<Scene.SceneContent> convert2DaliSceneContent = DaliProHelper.convert2DaliSceneContent(hashMap);
                sceneListByRoomId.get(i3).setSceneContents(convert2DaliSceneContent);
                Injection.repo().scene().updateSceneContent(sceneListByRoomId.get(i3).getSceneId(), convert2DaliSceneContent);
                i3++;
                subDeviceListFromDb = subDeviceListFromDb;
                i2 = 0;
            }
            return sceneListByRoomId;
        }
        if (sceneListByRoomId != null && sceneListByRoomId.size() > 0) {
            Injection.boxStore().boxFor(Scene.class).remove((Collection) sceneListByRoomId);
        }
        ArrayList arrayList = new ArrayList();
        int i5 = 0;
        while (i5 < i) {
            Scene scene = new Scene();
            scene.setUserId(Injection.repo().user().getUserId());
            scene.setPlaceId(Injection.repo().home().getSelPlace().getPlaceId());
            scene.setFloorId(controlDevice.getFloorId());
            scene.setRoomId(controlDevice.getRoomId());
            scene.setMacdeviceid(controlDevice.getDeviceId());
            scene.setSceneType(4);
            int i6 = i5 + 1;
            scene.setSceneNum(i6);
            List<Device> subDeviceListFromDb2 = Injection.repo().device().getSubDeviceListFromDb(controlDevice.getPlaceId(), -1L, -1L, controlDevice.getDeviceId());
            HashMap hashMap2 = new HashMap();
            int i7 = 0;
            while (i7 < this.sceneByteDataList.get(i5).size()) {
                String str2 = this.sceneByteDataList.get(i5).get(i7);
                if (!TextUtils.isEmpty(str2) && (deviceByAdd = getDeviceByAdd(subDeviceListFromDb2, (parseInt = Integer.parseInt(str2.substring(0, 2), i)))) != null) {
                    LocalDeviceParam localDeviceParam2 = new LocalDeviceParam();
                    localDeviceParam2.deviceid = deviceByAdd.getDeviceId();
                    localDeviceParam2.instruct = str2.substring(2);
                    if (localDeviceParam2.instruct.length() == 4) {
                        localDeviceParam2.option = "4";
                    } else {
                        localDeviceParam2.option = "0";
                    }
                    localDeviceParam2.sceneZone = parseInt;
                    hashMap2.put(Long.valueOf(deviceByAdd.getDeviceId()), localDeviceParam2);
                }
                i7++;
                i = 16;
            }
            scene.setSceneContents(DaliProHelper.convert2DaliSceneContent(hashMap2));
            if (i5 != 0) {
                scene.setSceneName(ActivityUtils.getTopActivity().getResources().getString(R.string.app_scene) + i5);
            } else {
                scene.setSceneName(ActivityUtils.getTopActivity().getResources().getString(R.string.app_scene));
            }
            CgdProSceneExtParam cgdProSceneExtParam = new CgdProSceneExtParam();
            cgdProSceneExtParam.setDaliAddr(i6);
            cgdProSceneExtParam.setDaliHidden(1);
            scene.setExtParam(cgdProSceneExtParam);
            arrayList.add(scene);
            i5 = i6;
            i = 16;
        }
        return arrayList;
    }

    private Device getDeviceByAdd(List<Device> deviceList, int add) {
        for (Device device : deviceList) {
            if (DaliProHelper.getDeviceAddress(device) == add) {
                return device;
            }
        }
        return null;
    }
}