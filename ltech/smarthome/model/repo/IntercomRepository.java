package com.ltech.smarthome.model.repo;

import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.akuvox.mobile.libcommon.bean.AccountData;
import com.akuvox.mobile.libcommon.defined.ConfigDefined;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.intercom.GetOpenDoorLogResponse;
import com.ltech.smarthome.net.response.intercom.IntercomTokenResponse;
import com.ltech.smarthome.net.response.intercom.KeyListResponse;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class IntercomRepository {
    private Context context;
    private long currentPlaceId;
    private UserInfoResponse.User intercomUser;
    private LifecycleOwner owner;
    private List<UserInfoResponse.DevInfo> intercomDevList = new ArrayList();
    private String accessToken = "";
    private List<KeyListResponse.OpenDoorTempKey> tempKeyList = new ArrayList();
    private List<GetOpenDoorLogResponse.OpenDoorBean> openDoorList = new ArrayList();

    public void init(LifecycleOwner owner, Context context) {
        this.owner = owner;
        this.context = context;
    }

    public void login() {
        Place selPlace = Injection.repo().home().getSelPlace();
        if (selPlace != null) {
            cleanSIPAccount();
            this.intercomUser = null;
            this.intercomDevList = new ArrayList();
            this.currentPlaceId = selPlace.getPlaceId();
            ((ObservableSubscribeProxy) Injection.net().getToken(this.currentPlaceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.IntercomRepository$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IntercomRepository.this.lambda$login$0((IntercomTokenResponse) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$login$0(IntercomTokenResponse intercomTokenResponse) throws Exception {
        this.accessToken = intercomTokenResponse.getAccess_token();
        getUserInfo();
        MediaManager.getInstance(this.context).initMedia(this.context);
    }

    public void getUserInfo() {
        ((ObservableSubscribeProxy) Injection.net().getIntercomUserInfo().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.IntercomRepository$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IntercomRepository.this.lambda$getUserInfo$1((UserInfoResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUserInfo$1(UserInfoResponse userInfoResponse) throws Exception {
        this.intercomUser = userInfoResponse.getUser();
        for (UserInfoResponse.DevInfo devInfo : userInfoResponse.getDev_list()) {
            if (!devInfo.getMac().startsWith("DC")) {
                this.intercomDevList.add(devInfo);
            }
        }
        UserInfoResponse.User user = this.intercomUser;
        if (user != null) {
            requestReg(user);
        }
        EventBusUtils.post(new LiveBusHelper(12));
    }

    public boolean isLogin() {
        return this.intercomUser != null;
    }

    public List<UserInfoResponse.DevInfo> getIntercomDevList() {
        return this.intercomDevList;
    }

    public void setIntercomDevList(List<UserInfoResponse.DevInfo> intercomDevList) {
        this.intercomDevList = intercomDevList;
    }

    public UserInfoResponse.User getIntercomUser() {
        return this.intercomUser;
    }

    public List<KeyListResponse.OpenDoorTempKey> getTempKeyList() {
        return this.tempKeyList;
    }

    public void setTempKeyList(List<KeyListResponse.OpenDoorTempKey> tempKeyList) {
        this.tempKeyList = tempKeyList;
    }

    public void deleteTempKey(long key) {
        for (int i = 0; i < this.tempKeyList.size(); i++) {
            if (this.tempKeyList.get(i).getTemp_key() == key) {
                this.tempKeyList.remove(i);
                return;
            }
        }
    }

    public void addTempKey(KeyListResponse.OpenDoorTempKey tempKey) {
        this.tempKeyList.add(tempKey);
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public GetOpenDoorLogResponse.OpenDoorBean getOpenDoorList(String time) {
        for (int i = 0; i < this.openDoorList.size(); i++) {
            if (this.openDoorList.get(i).getCaptureTime().equalsIgnoreCase(time)) {
                return this.openDoorList.get(i);
            }
        }
        return null;
    }

    public void setOpenDoorList(List<GetOpenDoorLogResponse.OpenDoorBean> openDoorList) {
        this.openDoorList = openDoorList;
    }

    public void requestReg(UserInfoResponse.User user) {
        AccountData accountData = new AccountData();
        accountData.stringMap.put(ConfigDefined.DB_COL_ACCOUNT_USER_NAME, user.getUser_sip());
        accountData.stringMap.put(ConfigDefined.DB_COL_ACCOUNT_REG_NAME, user.getUser_sip());
        accountData.stringMap.put("DisplayName", user.getDisplay_name());
        accountData.stringMap.put(ConfigDefined.DB_COL_ACCOUNT_PASSWORD, user.getSip_passwd());
        String[] split = user.getSip_server().split(Constants.COLON_SEPARATOR);
        accountData.stringMap.put(ConfigDefined.DB_COL_ACCOUNT_REG_SERVER_URL, split[0]);
        accountData.stringMap.put(ConfigDefined.DB_COL_ACCOUNT_REG_SERVER_PORT, split[1]);
        accountData.accountId = 0;
        accountData.isLineEnabled = true;
        MediaManager.getInstance(this.context).setSIPAccount(accountData, com.ltech.smarthome.utils.Constants.AKCSURL, com.ltech.smarthome.utils.Constants.INTERCOM_CLIENT_ID);
    }

    public void cleanSIPAccount() {
        MediaManager.getInstance(this.context).cleanSIPAccount();
    }
}