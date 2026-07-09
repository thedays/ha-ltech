package com.ltech.smarthome.model.repo;

import androidx.lifecycle.LifecycleOwner;
import com.aispeech.dca.Callback;
import com.aispeech.dca.DcaListener;
import com.aispeech.dca.DcaSdk;
import com.aispeech.dca.smartHome.bean.SmartHomeSkillDetail;
import com.aispeech.dca.smartHome.bean.SmartHomeTokenRequest;
import com.aispeech.dui.account.AccountListener;
import com.aispeech.dui.account.AccountManager;
import com.aispeech.dui.account.OAuthCodeListener;
import com.aispeech.dui.account.OAuthManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.DcaRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.utils.LHomeLog;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class DcaRepository {
    private AuthCallBack authCallBack;
    private long currentPlaceId;
    private LifecycleOwner owner;

    public interface AuthCallBack {
        void onAuthFailed();

        void onAuthSuccess(String authCode, String codeVerifier, String userId);
    }

    public void init(LifecycleOwner owner) {
        this.owner = owner;
    }

    public void login() {
        Place selPlace = Injection.repo().home().getSelPlace();
        if (selPlace != null) {
            this.currentPlaceId = selPlace.getPlaceId();
            AccountManager.getInstance().linkAccount(String.valueOf(this.currentPlaceId), Injection.repo().user().getSession() + ProductId.SPLIT + this.currentPlaceId, ApiConstants.getDcaManufactureSecret(), new AccountListener() { // from class: com.ltech.smarthome.model.repo.DcaRepository.1
                @Override // com.aispeech.dui.account.AccountListener
                public void onError(int i, String s) {
                    LHomeLog.i("dca", getClass(), "link account error ---> i--" + i + "  s---" + s);
                    SharedPreferenceUtil.edit().removeBean(String.valueOf(DcaRepository.this.currentPlaceId), DcaInfo.class);
                }

                @Override // com.aispeech.dui.account.AccountListener
                public void onSuccess() {
                    DcaRepository.this.setAuth();
                }
            });
        }
    }

    public void login(final String mac) {
        Place selPlace = Injection.repo().home().getSelPlace();
        if (selPlace == null || mac == null) {
            return;
        }
        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showLoadingDialog("");
        this.currentPlaceId = selPlace.getPlaceId();
        AccountManager.getInstance().linkAccount(mac, Injection.repo().user().getSession() + ProductId.SPLIT + mac, ApiConstants.getDcaManufactureSecret(), new AccountListener() { // from class: com.ltech.smarthome.model.repo.DcaRepository.2
            @Override // com.aispeech.dui.account.AccountListener
            public void onError(int i, String s) {
                LHomeLog.i("dca", getClass(), "link account error ---> i--" + i + "  s---" + s);
                SharedPreferenceUtil.edit().removeBean(mac, DcaInfo.class);
                ThreadUtils.runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.model.repo.DcaRepository.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ((BaseNormalActivity) ActivityUtils.getTopActivity()).showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.add_device_fail));
                    }
                });
            }

            @Override // com.aispeech.dui.account.AccountListener
            public void onSuccess() {
                DcaRepository.this.setAuth();
            }
        });
    }

    public void setAuth() {
        LHomeLog.i("dca", getClass(), "link account success");
        if (AccountManager.getInstance().isLogined()) {
            final String genCodeVerifier = OAuthManager.getInstance().genCodeVerifier();
            final String genCodeChallenge = OAuthManager.getInstance().genCodeChallenge(genCodeVerifier);
            LHomeLog.i("dca", getClass(), "codeVerifier -->" + genCodeVerifier);
            LHomeLog.i("dca", getClass(), "codeChallenge -->" + genCodeChallenge);
            final String str = AccountManager.getInstance().getUserId() + "";
            LHomeLog.i("dca", getClass(), "userId -->" + str);
            OAuthManager.getInstance().setOAuthListener(new OAuthCodeListener() { // from class: com.ltech.smarthome.model.repo.DcaRepository.3
                @Override // com.aispeech.dui.account.OAuthCodeListener
                public void onError(String s) {
                    LHomeLog.i("dca", getClass(), "onError --> " + s);
                    SharedPreferenceUtil.edit().removeBean(String.valueOf(DcaRepository.this.currentPlaceId), DcaInfo.class);
                    if (DcaRepository.this.authCallBack != null) {
                        DcaRepository.this.authCallBack.onAuthFailed();
                    }
                }

                @Override // com.aispeech.dui.account.OAuthCodeListener
                public void onSuccess(final String s) {
                    ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.model.repo.DcaRepository.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DcaInfo dcaInfo = new DcaInfo();
                            dcaInfo.setAuthCode(s);
                            dcaInfo.setCodeChallenge(genCodeChallenge);
                            dcaInfo.setCodeVerifier(genCodeVerifier);
                            dcaInfo.setUserId(str);
                            SharedPreferenceUtil.edit().putBean(String.valueOf(DcaRepository.this.currentPlaceId), dcaInfo);
                            if (DcaRepository.this.authCallBack != null) {
                                DcaRepository.this.authCallBack.onAuthSuccess(s, genCodeVerifier, str);
                            }
                            DcaRepository.this.querySkillDetail();
                        }
                    });
                }
            });
            OAuthManager.getInstance().requestAuthCode(genCodeChallenge, "http://dui.callback", ApiConstants.getDcaClientId());
        }
    }

    public void querySkillDetail() {
        DcaSdk.getSmartHomeManager().querySmartHomeSkillDetail(ApiConstants.getDcaSkillId(), new Callback<SmartHomeSkillDetail>() { // from class: com.ltech.smarthome.model.repo.DcaRepository.4
            @Override // com.aispeech.dca.Callback
            public void onFailure(int i, String s) {
            }

            @Override // com.aispeech.dca.Callback
            public void onSuccess(SmartHomeSkillDetail smartHomeSkillDetail) {
                String productId = ConfigHelper.instance().productInfo.getProductId();
                String str = ConfigHelper.instance().mac;
                if (ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(productId)) {
                    DcaRepository.this.skipSkillLogin(smartHomeSkillDetail.getVersion(), Constants.DCA_PRODUCT_ID_12S, str);
                    return;
                }
                if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(productId)) {
                    DcaRepository.this.skipSkillLogin(smartHomeSkillDetail.getVersion(), Constants.DCA_PRODUCT_ID_6S, str);
                    return;
                }
                if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(productId)) {
                    DcaRepository.this.skipSkillLogin(smartHomeSkillDetail.getVersion(), Constants.DCA_PRODUCT_ID_PRO, str);
                } else if (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(productId)) {
                    DcaRepository.this.skipSkillLogin(smartHomeSkillDetail.getVersion(), Constants.DCA_PRODUCT_ID_G4_MAX, str);
                } else {
                    DcaRepository.this.skipSkillLogin(smartHomeSkillDetail.getVersion(), Constants.DCA_PRODUCT_ID_MINI, str);
                }
            }
        });
    }

    public void skipSkillLogin(String skillVersion, String dcaId, String mac) {
        SmartHomeTokenRequest smartHomeTokenRequest = new SmartHomeTokenRequest();
        smartHomeTokenRequest.setProductId(dcaId);
        smartHomeTokenRequest.setSkillId(ApiConstants.getDcaSkillId());
        smartHomeTokenRequest.setSkillVersion(skillVersion);
        smartHomeTokenRequest.setSmartHomeAccessToken(Injection.repo().user().getSession() + ProductId.SPLIT + mac);
        smartHomeTokenRequest.setSmartHomeRefreshToken(Injection.repo().user().getSession() + ProductId.SPLIT + mac);
        smartHomeTokenRequest.setAccessTokenExpiresIn(7200);
        if (smartHomeTokenRequest.getSmartHomeAccessToken() == null) {
            return;
        }
        DcaSdk.getSmartHomeManager().updateSmartHomeTokenInfo(smartHomeTokenRequest, new AnonymousClass5());
    }

    /* renamed from: com.ltech.smarthome.model.repo.DcaRepository$5, reason: invalid class name */
    class AnonymousClass5 implements DcaListener {
        @Override // com.aispeech.dca.DcaListener
        public void onFailure(IOException e) {
        }

        AnonymousClass5() {
        }

        @Override // com.aispeech.dca.DcaListener
        public void onResult(int httpResponseCode, String httpResponseBody) {
            LHomeLog.i("dca", DcaRepository.this.getClass(), "onResult  httpResponseCode-->" + httpResponseCode + "  httpResponseBody -->" + httpResponseBody);
            Injection.net().notifySbc(DcaRepository.this.currentPlaceId).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.DcaRepository$5$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DcaRepository.AnonymousClass5.this.lambda$onResult$0(obj);
                }
            }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.model.repo.DcaRepository.5.1
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    super.action(throwable);
                    LHomeLog.i("dca", getClass(), "action: " + throwable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(Object obj) throws Exception {
            LHomeLog.i("dca", getClass(), "notifySbc: " + obj);
        }
    }

    public AuthCallBack getAuthCallBack() {
        return this.authCallBack;
    }

    public void setAuthCallBack(AuthCallBack authCallBack) {
        this.authCallBack = authCallBack;
    }

    public static final class DcaInfo {
        String authCode;
        String codeChallenge;
        String codeVerifier;
        String userId;

        public String getCodeVerifier() {
            return this.codeVerifier;
        }

        public void setCodeVerifier(String codeVerifier) {
            this.codeVerifier = codeVerifier;
        }

        public String getCodeChallenge() {
            return this.codeChallenge;
        }

        public void setCodeChallenge(String codeChallenge) {
            this.codeChallenge = codeChallenge;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAuthCode() {
            return this.authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }
    }
}