package com.ltech.smarthome.model.repo;

import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.User;
import com.ltech.smarthome.model.bean.User_;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.user.BindUserResponse;
import com.ltech.smarthome.net.response.user.GetUserInfoResponse;
import com.ltech.smarthome.net.response.user.LoginResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes4.dex */
public final class UserRepository implements IUser {
    private BoxStore mBoxStore;
    private KeyCreator mKeyCreator;
    private RateLimiter mRateLimiter;
    private User mUser;

    public UserRepository(BoxStore boxStore, RateLimiter rateLimiter, KeyCreator keyCreator) {
        this.mBoxStore = boxStore;
        this.mRateLimiter = rateLimiter;
        this.mKeyCreator = keyCreator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Box<User> getUserBox() {
        return this.mBoxStore.boxFor(User.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public User getUser() {
        if (this.mUser == null) {
            User findFirst = getUserBox().query().build().findFirst();
            this.mUser = findFirst;
            if (findFirst == null) {
                this.mUser = new User();
            }
        }
        return this.mUser;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void parseLoginResult(LoginResponse response) {
        getUser().setAppId(response.getAppid());
        getUser().setSession(response.getSession());
        getUser().setSecretKey(response.getSecretkey());
        getUser().setUserName(response.getUsername());
        getUser().setUserId(response.getUserid());
        getUserBox().put((Box<User>) getUser());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void parseBindUserResult(BindUserResponse.ParamBean bean) {
        getUser().setDeviceSecret(bean.getDeviceSecret());
        getUser().setIotId(bean.getIotId());
        getUser().setProductKey(bean.getProductKey());
        getUser().setDeviceName(bean.getDeviceName());
        getUserBox().put((Box<User>) getUser());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public Listing<User> getUserInfo(final LifecycleOwner owner) {
        return new WrapperResource<User, GetUserInfoResponse>() { // from class: com.ltech.smarthome.model.repo.UserRepository.1
            @Override // com.ltech.smarthome.model.WrapperResource
            protected boolean shouldFetch() {
                return UserRepository.this.mRateLimiter.shouldFetch(UserRepository.this.mKeyCreator.userInfoKey(UserRepository.this.getUserId()));
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected QueryBuilder<User> getDbQueryBuilder() {
                return UserRepository.this.getUserBox().query().equal(User_.userId, UserRepository.this.getUserId());
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected void netCall(Observer<GetUserInfoResponse> observer) {
                ((ObservableSubscribeProxy) Injection.net().getUserInfo().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.model.WrapperResource
            public void saveDataFromNet(GetUserInfoResponse response) {
                UserRepository.this.getUser().setEmail(response.getEmail());
                UserRepository.this.getUser().setMobilePhone(response.getMobilephone());
                UserRepository.this.getUser().setHeadUrl(response.getHeadurl());
                UserRepository.this.getUser().setDevicetotal(response.getDevicetotal());
                UserRepository.this.getUser().setPlacetotal(response.getPlacetotal());
                UserRepository.this.getUserBox().put((Box) UserRepository.this.getUser());
            }
        }.toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void setUserName(String name) {
        getUser().setUserName(name);
        getUserBox().put((Box<User>) getUser());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void setHeaderUrl(String url) {
        getUser().setHeadUrl(url);
        getUserBox().put((Box<User>) getUser());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void changePhone(String phone) {
        getUser().setMobilePhone(phone);
        getUserBox().put((Box<User>) getUser());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void changeEmail(String email) {
        getUser().setEmail(email);
        getUserBox().put((Box<User>) getUser());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public boolean isLogin() {
        return getUserId() > 0 && !TextUtils.isEmpty(getIotId());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public long getAppId() {
        return getUser().getAppId();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public String getSession() {
        return getUser().getSession();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public String getSecretKey() {
        return getUser().getSecretKey();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public String getIotId() {
        return getUser().getIotId();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public String getProductKey() {
        return getUser().getProductKey();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public String getDeviceSecret() {
        return getUser().getDeviceSecret();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public String getDeviceName() {
        return getUser().getDeviceName();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public long getUserId() {
        return getUser().getUserId();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public void clear() {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.UserRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UserRepository.this.lambda$clear$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clear$0() {
        getUserBox().removeAll();
        this.mUser = null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IUser
    public User getUserByDb() {
        if (this.mUser == null) {
            this.mUser = getUserBox().query().build().findFirst();
        }
        return this.mUser;
    }
}