package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.User;
import com.ltech.smarthome.net.response.user.BindUserResponse;
import com.ltech.smarthome.net.response.user.LoginResponse;

/* loaded from: classes4.dex */
public interface IUser {
    void changeEmail(String email);

    void changePhone(String phone);

    void clear();

    long getAppId();

    String getDeviceName();

    String getDeviceSecret();

    String getIotId();

    String getProductKey();

    String getSecretKey();

    String getSession();

    User getUserByDb();

    long getUserId();

    Listing<User> getUserInfo(LifecycleOwner owner);

    boolean isLogin();

    void parseBindUserResult(BindUserResponse.ParamBean bean);

    void parseLoginResult(LoginResponse response);

    void setHeaderUrl(String url);

    void setUserName(String name);
}