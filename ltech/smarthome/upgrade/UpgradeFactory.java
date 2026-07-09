package com.ltech.smarthome.upgrade;

import android.content.Context;
import android.os.Handler;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.McuVersion;
import com.ltech.smarthome.model.product.ProductId;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.product101.Product101UpgradeHelper;
import com.smart.product_agreement.productBle.BleSegmentSyncGqDataHelper;
import com.smart.product_agreement.productBle.BleSyncGqDataHelper;
import com.smart.product_agreement.productBle.BleUpgradeHelper;
import com.smart.product_agreement.productBle.BleUpgradeThemeHelper;
import com.smart.product_agreement.productBle.BleZipUpgradeHelper;
import com.smart.product_agreement.productBle.MeshUpgradeHelper;
import com.smart.product_agreement.productBle.MeshUpgradeThemeHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;

/* loaded from: classes4.dex */
public class UpgradeFactory {
    private static final String B5_DMX_4A = "SVer000.000.000";
    private static final String CG_AIR = "SVer000.003.000";
    private static final String CG_AUDIO = "SVer000.002.000";
    private static final String CG_CUR = "SVer000.004.000";
    private static final String CG_D = "SVer000.002.000";
    private static final String CG_DAM = "SVer000.003.000";
    private static final String CG_T = "SVer000.003.000";
    private static final String CG_TRIG = "SVer000.002.000";
    private static final String GAM_BLE = "SVer000.007.000";
    private static final String HAM_BLE = "SVer000.002.000";
    private static final String HAM_WF = "SVer000.005.000";
    private static final String HAM_WF_BLE = "SVer000.000.000";
    private static final String HVER_BASE = "000.001.000";
    public static final int ID_BLE_UPDATE_GQ_DATA_HELPER = 6;
    public static final int ID_BLE_UPDATE_GQ_SEGMENT_DATA_HELPER = 8;
    public static final int ID_BLE_UPDATE_HELPER = 3;
    public static final int ID_BLE_UPDATE_THEME_HELPER = 4;
    public static final int ID_BLE_ZIP_UPDATE_HELPER = 7;
    public static final int ID_GATEWAY_UPDATE_HELPER = 2;
    public static final int ID_MESH_UPDATE_THEME_HELPER = 5;
    public static final int ID_MESH_UPGRADE_HELPER = 1;
    private static final String LM_150_24_G1B1 = "SVer000.006.000";
    private static final String LM_150_24_G2B1 = "SVer000.006.000";
    private static final String LM_150_24_G4B1 = "SVer000.002.000";
    private static final String LM_150_CV_G1B2 = "SVer000.006.000";
    private static final String LM_150_CV_G2B2 = "SVer000.006.000";
    private static final String LM_75_24_G1B1 = "SVer000.002.000";
    private static final String LM_75_24_G2B1 = "SVer000.002.000";
    private static final String MR03 = "SVer000.003.000";
    private static final String RELAY_BLE01 = "SVer000.003.000";
    private static final String S1C = "SVer000.004.000";
    private static final String S2C = "SVer000.004.000";
    private static final String S3C = "SVer000.004.000";
    private static final String S4M = "SVer000.011.000";
    private static final String S8M = "SVer000.011.000";
    private static final String SE_12_100_400_W1B = "SVer000.009.003";
    private static final String SE_12_100_450_W2B = "SVer000.003.000";
    private static final String SE_20_250_1000_W2B2 = "SVer000.009.003";
    private static final String ST_75_W1B = "SVer000.006.000";
    private static final String ST_75_W2B = "SVer000.006.000";
    private static final String ST_75_W3B = "SVer000.006.000";
    private static final String SVER_BASE = "001.000.000";
    private static final String UB1 = "SVer000.000.005";
    private static final String UB2 = "SVer000.000.005";
    private static final String UB4 = "SVer000.000.005";
    private static final String UB5 = "SVer000.000.005";
    private static final boolean USE_CLOUD_MCU = true;

    public static UpgradeInfo getUpgradeInfo(ProductVersionInfo versionInfo) {
        String trim = versionInfo.getDeviceModel().trim();
        String str = versionInfo.gethVer();
        if (str.startsWith("HVer") && str.substring(4).compareTo(HVER_BASE) > 0) {
            trim = trim + ProductId.SPLIT + str;
        }
        String str2 = versionInfo.getsVer();
        if (str2.startsWith("SVer") && str2.substring(4).compareTo(SVER_BASE) >= 0) {
            trim = trim + "_oem" + str2.substring(4, 7);
        }
        McuVersion deviceVersion = Injection.repo().mcu().getDeviceVersion(trim);
        if (deviceVersion == null || deviceVersion.getFirmwareData() == null) {
            return null;
        }
        if (trim.equals("HAM-WF")) {
            return new UpgradeInfo(deviceVersion.getFirmwareData(), deviceVersion.getVersionname(), str, 2);
        }
        if (!trim.equals("SQ") || versionInfo.getsVer().substring(4).compareTo(HVER_BASE) > 0) {
            return new UpgradeInfo(deviceVersion.getFirmwareData(), deviceVersion.getVersionname(), str, 1, deviceVersion.isMeshUpdate());
        }
        return null;
    }

    public static BaseUpgradeHelper getUpgradeHelper(Context context, int helperId, String fileName, Handler handler) {
        if (helperId == 1) {
            return new MeshUpgradeHelper(context, fileName, handler);
        }
        if (helperId == 2) {
            return new Product101UpgradeHelper(context, fileName, handler);
        }
        throw new RuntimeException("no this upgrade helper !!!");
    }

    public static BaseUpgradeHelper getUpgradeHelper(Context context, int helperId, byte[] firmwareData, Handler handler) {
        switch (helperId) {
            case 1:
                return new MeshUpgradeHelper(context, firmwareData, handler);
            case 2:
                return new Product101UpgradeHelper(context, firmwareData, handler);
            case 3:
                return new BleUpgradeHelper(context, firmwareData, handler);
            case 4:
                return new BleUpgradeThemeHelper(context, firmwareData, handler);
            case 5:
                return new MeshUpgradeThemeHelper(context, firmwareData, handler);
            case 6:
                return new BleSyncGqDataHelper(context, firmwareData, handler);
            case 7:
                return new BleZipUpgradeHelper(context, firmwareData, handler);
            default:
                throw new RuntimeException("no this upgrade helper !!!");
        }
    }

    public static BaseUpgradeHelper getUpgradeHelper(Context context, int helperId, byte[] firmwareData, Handler handler, int size, int curSize) {
        if (helperId == 8) {
            return new BleSegmentSyncGqDataHelper(context, firmwareData, handler, size, curSize);
        }
        throw new RuntimeException("no this upgrade helper !!!");
    }
}