package com.ltech.smarthome.model.bean;

import com.aliyun.alink.h2.api.Constraint;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.ams.emas.push.notification.b;
import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.UserCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import no.nordicsemi.android.log.LogContract;

/* loaded from: classes4.dex */
public final class User_ implements EntityInfo<User> {
    public static final Property<User>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "User";
    public static final int __ENTITY_ID = 10;
    public static final String __ENTITY_NAME = "User";
    public static final Property<User> __ID_PROPERTY;
    public static final User_ __INSTANCE;
    public static final Property<User> appId;
    public static final Property<User> deviceName;
    public static final Property<User> deviceSecret;
    public static final Property<User> devicetotal;
    public static final Property<User> email;
    public static final Property<User> headUrl;
    public static final Property<User> iotId;
    public static final Property<User> mobilePhone;
    public static final Property<User> placetotal;
    public static final Property<User> productKey;
    public static final Property<User> secretKey;
    public static final Property<User> session;
    public static final Property<User> userId;
    public static final Property<User> userName;
    public static final Class<User> __ENTITY_CLASS = User.class;
    public static final CursorFactory<User> __CURSOR_FACTORY = new UserCursor.Factory();
    static final UserIdGetter __ID_GETTER = new UserIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 10;
    }

    static {
        User_ user_ = new User_();
        __INSTANCE = user_;
        Property<User> property = new Property<>(user_, 0, 1, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey, true, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property;
        Property<User> property2 = new Property<>(user_, 1, 2, Long.TYPE, b.APP_ID);
        appId = property2;
        Property<User> property3 = new Property<>(user_, 2, 3, String.class, "userName");
        userName = property3;
        Property<User> property4 = new Property<>(user_, 3, 4, String.class, LogContract.Session.SESSION_CONTENT_DIRECTORY);
        session = property4;
        Property<User> property5 = new Property<>(user_, 4, 5, String.class, "secretKey");
        secretKey = property5;
        Property<User> property6 = new Property<>(user_, 5, 6, String.class, "productKey");
        productKey = property6;
        Property<User> property7 = new Property<>(user_, 6, 7, String.class, Constraint.PARAM_DEVICE_SECRET);
        deviceSecret = property7;
        Property<User> property8 = new Property<>(user_, 7, 8, String.class, TmpConstant.DEVICE_IOTID);
        iotId = property8;
        Property<User> property9 = new Property<>(user_, 8, 9, String.class, "deviceName");
        deviceName = property9;
        Property<User> property10 = new Property<>(user_, 9, 10, String.class, "email");
        email = property10;
        Property<User> property11 = new Property<>(user_, 10, 11, String.class, "mobilePhone");
        mobilePhone = property11;
        Property<User> property12 = new Property<>(user_, 11, 12, String.class, "headUrl");
        headUrl = property12;
        Property<User> property13 = new Property<>(user_, 12, 13, Integer.TYPE, "placetotal");
        placetotal = property13;
        Property<User> property14 = new Property<>(user_, 13, 14, Integer.TYPE, "devicetotal");
        devicetotal = property14;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "User";
    }

    @Override // io.objectbox.EntityInfo
    public Class<User> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "User";
    }

    @Override // io.objectbox.EntityInfo
    public Property<User>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<User> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<User> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<User> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class UserIdGetter implements IdGetter<User> {
        UserIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(User object) {
            return object.getUserId();
        }
    }
}