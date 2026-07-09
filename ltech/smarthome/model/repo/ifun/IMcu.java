package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.bean.McuVersion;

/* loaded from: classes4.dex */
public interface IMcu {
    McuVersion getDeviceVersion(String deviceModel);

    void getMcuListFromNet(LifecycleOwner owner, int versionCode);

    void saveMcu(McuVersion... mcuVersions);
}