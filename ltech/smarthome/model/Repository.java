package com.ltech.smarthome.model;

import com.ltech.smarthome.model.repo.ifun.IAutomation;
import com.ltech.smarthome.model.repo.ifun.IDevice;
import com.ltech.smarthome.model.repo.ifun.IGroup;
import com.ltech.smarthome.model.repo.ifun.IHome;
import com.ltech.smarthome.model.repo.ifun.IMcu;
import com.ltech.smarthome.model.repo.ifun.IMode;
import com.ltech.smarthome.model.repo.ifun.IRole;
import com.ltech.smarthome.model.repo.ifun.IScene;
import com.ltech.smarthome.model.repo.ifun.ISong;
import com.ltech.smarthome.model.repo.ifun.IUser;

/* loaded from: classes4.dex */
public final class Repository {
    private static volatile Repository sInstance;
    private IAutomation mIAutomation;
    private IDevice mIDevice;
    private IGroup mIGroup;
    private IHome mIHome;
    private IMode mIMode;
    private IScene mIScene;
    private ISong mISong;
    private IUser mIUser;
    private IMcu mMcu;
    private IRole mRole;

    private Repository() {
    }

    public static Repository getInstance() {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository();
                }
            }
        }
        return sInstance;
    }

    public IUser user() {
        if (this.mIUser == null) {
            this.mIUser = Injection.provideUserApi();
        }
        return this.mIUser;
    }

    public IHome home() {
        if (this.mIHome == null) {
            this.mIHome = Injection.provideHomeApi();
        }
        return this.mIHome;
    }

    public IDevice device() {
        if (this.mIDevice == null) {
            this.mIDevice = Injection.provideDeviceApi();
        }
        return this.mIDevice;
    }

    public IScene scene() {
        if (this.mIScene == null) {
            this.mIScene = Injection.provideSceneApi();
        }
        return this.mIScene;
    }

    public IAutomation auto() {
        if (this.mIAutomation == null) {
            this.mIAutomation = Injection.provideAutomationApi();
        }
        return this.mIAutomation;
    }

    public IGroup group() {
        if (this.mIGroup == null) {
            this.mIGroup = Injection.provideGroupApi();
        }
        return this.mIGroup;
    }

    public IMode mode() {
        if (this.mIMode == null) {
            this.mIMode = Injection.provideModeApi();
        }
        return this.mIMode;
    }

    public ISong song() {
        if (this.mISong == null) {
            this.mISong = Injection.provideSongApi();
        }
        return this.mISong;
    }

    public IRole role() {
        if (this.mRole == null) {
            this.mRole = Injection.provideRoleApi();
        }
        return this.mRole;
    }

    public IMcu mcu() {
        if (this.mMcu == null) {
            this.mMcu = Injection.provideMcuApi();
        }
        return this.mMcu;
    }
}