package com.ltech.smarthome.utils.cmd_assistant;

import android.util.ArrayMap;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.QueryDeviceStateRunnable;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.smart.message.SmartUtils;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class CmdAssistant {
    private static volatile ArrayMap<String, BaseAssistant> assistantMap = new ArrayMap<>(4);

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:17:0x0035
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private static <T extends com.ltech.smarthome.utils.cmd_assistant.BaseAssistant> T getAssistant(java.lang.Class<T> r4, java.lang.Object r5, int... r6) {
        /*
            android.util.ArrayMap<java.lang.String, com.ltech.smarthome.utils.cmd_assistant.BaseAssistant> r0 = com.ltech.smarthome.utils.cmd_assistant.CmdAssistant.assistantMap
            java.lang.String r1 = r4.getSimpleName()
            java.lang.Object r0 = r0.get(r1)
            com.ltech.smarthome.utils.cmd_assistant.BaseAssistant r0 = (com.ltech.smarthome.utils.cmd_assistant.BaseAssistant) r0
            if (r0 != 0) goto L38
            java.lang.Class<com.ltech.smarthome.utils.cmd_assistant.CmdAssistant> r1 = com.ltech.smarthome.utils.cmd_assistant.CmdAssistant.class
            monitor-enter(r1)     // Catch: java.lang.Exception -> L47
            android.util.ArrayMap<java.lang.String, com.ltech.smarthome.utils.cmd_assistant.BaseAssistant> r2 = com.ltech.smarthome.utils.cmd_assistant.CmdAssistant.assistantMap     // Catch: java.lang.Throwable -> L35
            java.lang.String r3 = r4.getSimpleName()     // Catch: java.lang.Throwable -> L35
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> L35
            com.ltech.smarthome.utils.cmd_assistant.BaseAssistant r2 = (com.ltech.smarthome.utils.cmd_assistant.BaseAssistant) r2     // Catch: java.lang.Throwable -> L35
            if (r2 != 0) goto L2f
            java.lang.Object r0 = r4.newInstance()     // Catch: java.lang.Throwable -> L32
            com.ltech.smarthome.utils.cmd_assistant.BaseAssistant r0 = (com.ltech.smarthome.utils.cmd_assistant.BaseAssistant) r0     // Catch: java.lang.Throwable -> L32
            android.util.ArrayMap<java.lang.String, com.ltech.smarthome.utils.cmd_assistant.BaseAssistant> r2 = com.ltech.smarthome.utils.cmd_assistant.CmdAssistant.assistantMap     // Catch: java.lang.Throwable -> L35
            java.lang.String r4 = r4.getSimpleName()     // Catch: java.lang.Throwable -> L35
            r2.put(r4, r0)     // Catch: java.lang.Throwable -> L35
            r2 = r0
        L2f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L32
            r0 = r2
            goto L38
        L32:
            r4 = move-exception
            r0 = r2
            goto L36
        L35:
            r4 = move-exception
        L36:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L35
            throw r4     // Catch: java.lang.Exception -> L47
        L38:
            r0.setControlObject(r5)     // Catch: java.lang.Exception -> L47
            int r4 = r6.length     // Catch: java.lang.Exception -> L47
            if (r4 <= 0) goto L42
            r4 = 0
            r4 = r6[r4]     // Catch: java.lang.Exception -> L47
            goto L43
        L42:
            r4 = 1
        L43:
            r0.setZoneNum(r4)     // Catch: java.lang.Exception -> L47
            return r0
        L47:
            r4 = move-exception
            r4.printStackTrace()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.utils.cmd_assistant.CmdAssistant.getAssistant(java.lang.Class, java.lang.Object, int[]):com.ltech.smarthome.utils.cmd_assistant.BaseAssistant");
    }

    public static LightAssistant getLightCmdAssistant(Object controlObject, int... zoneNum) {
        if (ProductRepository.isCgdPro(controlObject)) {
            return (LightAssistant) getAssistant(LightAssistant.class, controlObject, DaliProHelper.BROADCAST_ADD);
        }
        if (ProductRepository.isDaliLightGroup(controlObject)) {
            return (LightAssistant) getAssistant(LightAssistant.class, controlObject, DaliProHelper.getZoneNum(controlObject));
        }
        if (ProductRepository.isRelaySeparationSub(controlObject)) {
            return (LightAssistant) getAssistant(LightAssistant.class, controlObject, RelaySeparationHelper.getZoneNum(controlObject));
        }
        return (LightAssistant) getAssistant(LightAssistant.class, controlObject, zoneNum);
    }

    public static ModeAssistant getModeCmdAssistant(Object controlObject, int... zoneNum) {
        return (ModeAssistant) getAssistant(ModeAssistant.class, controlObject, zoneNum);
    }

    public static SettingAssistant getSettingCmdAssistant(Object controlObject, int... zoneNum) {
        if (ProductRepository.isCgdPro(controlObject)) {
            return (SettingAssistant) getAssistant(SettingAssistant.class, controlObject, DaliProHelper.BROADCAST_ADD);
        }
        if (ProductRepository.isDaliLightGroup(controlObject)) {
            return (SettingAssistant) getAssistant(SettingAssistant.class, controlObject, DaliProHelper.getZoneNum(controlObject));
        }
        return (SettingAssistant) getAssistant(SettingAssistant.class, controlObject, zoneNum);
    }

    public static SceneAssistant getSceneCmdAssistant(Object controlObject, int... zoneNum) {
        return (SceneAssistant) getAssistant(SceneAssistant.class, controlObject, zoneNum);
    }

    public static QueryAssistant getQueryCmdAssistant(Object controlObject, int... zoneNum) {
        return (QueryAssistant) getAssistant(QueryAssistant.class, controlObject, zoneNum);
    }

    public static GatewayAssistant getGatewayAssistant(Object controlObject, int... zoneNum) {
        return (GatewayAssistant) getAssistant(GatewayAssistant.class, controlObject, zoneNum);
    }

    public static DeviceAssistant getDeviceAssistant(Object controlObject, int... zoneNum) {
        return (DeviceAssistant) getAssistant(DeviceAssistant.class, controlObject, zoneNum);
    }

    public static MusicAssistant getMusicAssistant(Object controlObject, int... zoneNum) {
        return (MusicAssistant) getAssistant(MusicAssistant.class, controlObject, zoneNum);
    }

    public static BleMusicPlayerAssistant getBleMusicPlayerAssistant(Object controlObject, int... zoneNum) {
        return (BleMusicPlayerAssistant) getAssistant(BleMusicPlayerAssistant.class, controlObject, zoneNum);
    }

    public static RhythmsAssistant getLightRhythmsCmdAssistant(Object controlObject, int... zoneNum) {
        return (RhythmsAssistant) getAssistant(RhythmsAssistant.class, controlObject, zoneNum);
    }

    public static SyncDataAssistant getSyncDataAssistant(Object controlObject, int... zoneNum) {
        return (SyncDataAssistant) getAssistant(SyncDataAssistant.class, controlObject, zoneNum);
    }

    public static void selectStatusDevice(Device device, QueryDeviceStateRunnable.UpdateDeviceState updateDeviceStatus) {
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        QueryDeviceStateRunnable queryDeviceStateRunnable = new QueryDeviceStateRunnable(device, bleParam.getUnicastAddress(), SmartUtils.getICtrlConverter().convert(device), CmdBleFactory.querDeviceState(0, bleParam.getUnicastAddress()));
        queryDeviceStateRunnable.setUpdateDeviceState(updateDeviceStatus);
        ArrayList<QueryDeviceStateRunnable> arrayList = new ArrayList<>();
        arrayList.add(queryDeviceStateRunnable);
        ThreadPoolManager.getInstance().execRunnable(arrayList);
    }
}