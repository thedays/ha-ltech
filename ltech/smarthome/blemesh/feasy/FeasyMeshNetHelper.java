package com.ltech.smarthome.blemesh.feasy;

import android.text.TextUtils;
import com.feasycom.feasymesh.library.utils.MeshAddress;
import com.feasycom.feasymesh.library.utils.MeshParserUtils;
import com.feasycom.feasymesh.library.utils.SecureUtils;
import com.ltech.smarthome.blemesh.bean.MeshJsonBean;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes3.dex */
public class FeasyMeshNetHelper {
    public static String createMeshUUID() {
        return MeshParserUtils.uuidToHex(UUID.randomUUID().toString().toUpperCase(Locale.US));
    }

    public static String createNetKey() {
        return SecureUtils.generateRandomNetworkKey();
    }

    public static String createAppKey() {
        return "63964771734FBD76E3B40519D1D94A48";
    }

    public static MeshJsonBean getMeshJsonBean(Place place, String netKey, String appKey, String provisionAddress) {
        MeshJsonBean meshJsonBean = new MeshJsonBean();
        meshJsonBean.set$schema("http://json-schema.org/draft-04/schema#");
        meshJsonBean.setId("TBD");
        meshJsonBean.setVersion("1.0");
        meshJsonBean.setMeshUUID(place.getMeshUUID());
        meshJsonBean.setMeshName("nRF Mesh Network");
        meshJsonBean.setTimestamp(MeshParserUtils.formatTimeStamp(System.currentTimeMillis()));
        ArrayList arrayList = new ArrayList();
        arrayList.add(generateNetKey(arrayList.size(), netKey, false));
        meshJsonBean.setNetKeys(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(generateAppKey(arrayList2.size(), appKey));
        meshJsonBean.setAppKeys(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(generateDefaultProvisioner(provisionAddress));
        meshJsonBean.setProvisioners(arrayList3);
        ArrayList arrayList4 = new ArrayList();
        MeshJsonBean.ProvisionersBean provisionersBean = arrayList3.get(0);
        arrayList4.add(generateProvisionerNode(provisionersBean.getUUID(), provisionersBean.getProvisionerName(), provisionAddress, true, arrayList, arrayList2));
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(place.getPlaceId());
        if (deviceListByPlaceId != null && !deviceListByPlaceId.isEmpty()) {
            for (Device device : deviceListByPlaceId) {
                BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                if (bleParam != null && bleParam.getUnicastAddress() != 0 && !TextUtils.isEmpty(bleParam.getDeviceKey()) && !TextUtils.isEmpty(device.getWifiMac())) {
                    ArrayList arrayList5 = arrayList2;
                    ArrayList arrayList6 = arrayList;
                    MeshJsonBean.NodesBean generateDeviceNode = generateDeviceNode(bleParam.getDeviceUUID(), bleParam.getDeviceKey(), Integer.toString(ProductRepository.getAgreementIdByPid(device.getProductId())), bleParam.getUnicastAddress(), false, arrayList6, arrayList5);
                    arrayList = arrayList6;
                    arrayList2 = arrayList5;
                    arrayList4.add(generateDeviceNode);
                }
            }
        }
        meshJsonBean.setNodes(arrayList4);
        meshJsonBean.setGroups(new ArrayList());
        meshJsonBean.setScenes(new ArrayList());
        return meshJsonBean;
    }

    private static MeshJsonBean.NetKeysBean generateNetKey(int index, String netKey, boolean minSecurity) {
        MeshJsonBean.NetKeysBean netKeysBean = new MeshJsonBean.NetKeysBean();
        netKeysBean.setName("Network Key " + (index + 1));
        netKeysBean.setIndex(index);
        netKeysBean.setPhase(0);
        netKeysBean.setMinSecurity(minSecurity ? "low" : "high");
        netKeysBean.setKey(netKey);
        netKeysBean.setTimestamp(MeshParserUtils.formatTimeStamp(System.currentTimeMillis()));
        return netKeysBean;
    }

    private static MeshJsonBean.AppKeysBean generateAppKey(int index, String appKey) {
        MeshJsonBean.AppKeysBean appKeysBean = new MeshJsonBean.AppKeysBean();
        appKeysBean.setName("Application Key " + (index + 1));
        appKeysBean.setIndex(index);
        appKeysBean.setKey(appKey);
        return appKeysBean;
    }

    private static MeshJsonBean.ProvisionersBean generateDefaultProvisioner(String provisionAddress) {
        MeshJsonBean.ProvisionersBean provisionersBean = new MeshJsonBean.ProvisionersBean();
        provisionersBean.setProvisionerName("nRF Mesh Provisioner");
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MeshJsonBean.ProvisionersBean.AllocatedUnicastRangeBean(provisionAddress, Integer.toHexString(28671)));
        provisionersBean.setAllocatedUnicastRange(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new MeshJsonBean.ProvisionersBean.AllocatedGroupRangeBean(Integer.toHexString(MeshAddress.START_GROUP_ADDRESS), Integer.toHexString(65024)));
        provisionersBean.setAllocatedGroupRange(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new MeshJsonBean.ProvisionersBean.AllocatedSceneRangeBean(Integer.toHexString(1), Integer.toHexString(13107)));
        provisionersBean.setAllocatedSceneRange(arrayList3);
        provisionersBean.setUUID(MeshParserUtils.uuidToHex(UUID.randomUUID().toString()));
        return provisionersBean;
    }

    private static MeshJsonBean.NodesBean generateProvisionerNode(String uuid, String nodeName, String unicastAddress, boolean minSecurity, List<MeshJsonBean.NetKeysBean> netKeyList, List<MeshJsonBean.AppKeysBean> appKeyList) {
        MeshJsonBean.NodesBean nodesBean = new MeshJsonBean.NodesBean();
        nodesBean.setUUID(uuid);
        nodesBean.setName(nodeName);
        nodesBean.setDeviceKey(MeshParserUtils.bytesToHex(SecureUtils.generateRandomNumber(), false));
        nodesBean.setUnicastAddress(unicastAddress);
        nodesBean.setSecurity(minSecurity ? "low" : "high");
        nodesBean.setConfigComplete(true);
        nodesBean.setFeatures(new MeshJsonBean.NodesBean.FeaturesBean(2, 2, 2, 2));
        nodesBean.setDefaultTTL(16);
        ArrayList arrayList = new ArrayList();
        for (MeshJsonBean.NetKeysBean netKeysBean : netKeyList) {
            MeshJsonBean.NodesBean.NetKeysBeanX netKeysBeanX = new MeshJsonBean.NodesBean.NetKeysBeanX();
            netKeysBeanX.setIndex(netKeysBean.getIndex());
            arrayList.add(netKeysBeanX);
        }
        nodesBean.setNetKeys(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (MeshJsonBean.AppKeysBean appKeysBean : appKeyList) {
            MeshJsonBean.NodesBean.AppKeysBeanX appKeysBeanX = new MeshJsonBean.NodesBean.AppKeysBeanX();
            appKeysBeanX.setIndex(appKeysBean.getIndex());
            arrayList2.add(appKeysBeanX);
        }
        nodesBean.setAppKeys(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(generateElement(1, new String[]{"0000", "0001", "0002", "11111111"}));
        nodesBean.setElements(arrayList3);
        nodesBean.setBlacklisted(false);
        return nodesBean;
    }

    private static MeshJsonBean.NodesBean generateDeviceNode(String uuid, String deviceKey, String nodeName, int unicastAddress, boolean minSecurity, List<MeshJsonBean.NetKeysBean> netKeyList, List<MeshJsonBean.AppKeysBean> appKeyList) {
        MeshJsonBean.NodesBean nodesBean = new MeshJsonBean.NodesBean();
        nodesBean.setUUID(uuid);
        nodesBean.setName(nodeName);
        nodesBean.setDeviceKey(deviceKey);
        nodesBean.setUnicastAddress(Integer.toHexString(unicastAddress));
        nodesBean.setSecurity(minSecurity ? "low" : "high");
        nodesBean.setConfigComplete(false);
        nodesBean.setFeatures(new MeshJsonBean.NodesBean.FeaturesBean(2, 2, 1, 1));
        nodesBean.setDefaultTTL(16);
        ArrayList arrayList = new ArrayList();
        for (MeshJsonBean.NetKeysBean netKeysBean : netKeyList) {
            MeshJsonBean.NodesBean.NetKeysBeanX netKeysBeanX = new MeshJsonBean.NodesBean.NetKeysBeanX();
            netKeysBeanX.setIndex(netKeysBean.getIndex());
            arrayList.add(netKeysBeanX);
        }
        nodesBean.setNetKeys(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (MeshJsonBean.AppKeysBean appKeysBean : appKeyList) {
            MeshJsonBean.NodesBean.AppKeysBeanX appKeysBeanX = new MeshJsonBean.NodesBean.AppKeysBeanX();
            appKeysBeanX.setIndex(appKeysBean.getIndex());
            arrayList2.add(appKeysBeanX);
        }
        nodesBean.setAppKeys(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(generateElement(unicastAddress, new String[]{"11111111"}));
        nodesBean.setElements(arrayList3);
        nodesBean.setBlacklisted(false);
        LHomeLog.e(FeasyMeshNetHelper.class, String.format("BLE==>NODE:\nUnicastAddress:%s\nTTL:%s", nodesBean.getUnicastAddress(), Integer.valueOf(nodesBean.getDefaultTTL())));
        return nodesBean;
    }

    private static MeshJsonBean.NodesBean.ElementsBean generateElement(int unicastAddress, String[] modelIds) {
        MeshJsonBean.NodesBean.ElementsBean elementsBean = new MeshJsonBean.NodesBean.ElementsBean();
        elementsBean.setName("Element: " + unicastAddress);
        elementsBean.setIndex(0);
        elementsBean.setLocation("0000");
        ArrayList arrayList = new ArrayList();
        for (String str : modelIds) {
            MeshJsonBean.NodesBean.ElementsBean.ModelsBean modelsBean = new MeshJsonBean.NodesBean.ElementsBean.ModelsBean();
            modelsBean.setModelId(str);
            modelsBean.setBind(new ArrayList(Collections.singletonList(0)));
            modelsBean.setSubscribe(new ArrayList());
            arrayList.add(modelsBean);
        }
        elementsBean.setModels(arrayList);
        return elementsBean;
    }

    private static MeshJsonBean.GroupBean generateGroup(String groupName, String address, String parentAddress) {
        MeshJsonBean.GroupBean groupBean = new MeshJsonBean.GroupBean();
        groupBean.setName(groupName);
        groupBean.setAddress(address);
        groupBean.setParentAddress(parentAddress);
        return groupBean;
    }
}