package com.ltech.smarthome.upgrade;

/* loaded from: classes4.dex */
public class UpgradeInfo {
    private static final String HARDWARE_VERSION_PREFIX = "HVER";
    private static final String SOFTWARE_VERSION_PREFIX = "SVER";
    private String fileName;
    private byte[] firmwareData;
    private String hVer;
    private int helperId;
    private boolean meshUpdate;
    private String sVer;

    public UpgradeInfo(String fileName, String sVer, String hVer, int helperId) {
        this.fileName = fileName;
        this.sVer = sVer;
        this.hVer = hVer;
        this.helperId = helperId;
    }

    public UpgradeInfo(byte[] firmwareData, String sVer, String hVer, int helperId) {
        this.firmwareData = firmwareData;
        this.sVer = sVer;
        this.hVer = hVer;
        this.helperId = helperId;
    }

    public UpgradeInfo(byte[] firmwareData, String sVer, String hVer, int helperId, boolean meshUpdate) {
        this.firmwareData = firmwareData;
        this.sVer = sVer;
        this.hVer = hVer;
        this.helperId = helperId;
        this.meshUpdate = meshUpdate;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getsVer() {
        return this.sVer.toUpperCase();
    }

    public String gethVer() {
        return this.hVer;
    }

    public int getHelperId() {
        return this.helperId;
    }

    public static String getSoftwareVersion(String version) {
        return version.toUpperCase().replaceAll(SOFTWARE_VERSION_PREFIX, "");
    }

    public static String getHardwareVersion(String version) {
        return version.toUpperCase().replaceAll(HARDWARE_VERSION_PREFIX, "");
    }

    public byte[] getFirmwareData() {
        return this.firmwareData;
    }

    public boolean isMeshUpdate() {
        return this.meshUpdate;
    }
}