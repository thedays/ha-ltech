package com.ltech.smarthome.model;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.blemesh.IMeshManager;
import com.ltech.smarthome.blemesh.MeshManager;
import com.ltech.smarthome.iot.IotManager;
import com.ltech.smarthome.model.bean.MyObjectBox;
import com.ltech.smarthome.model.product.ProductFactory;
import com.ltech.smarthome.model.repo.AutomationRepository;
import com.ltech.smarthome.model.repo.DcaRepository;
import com.ltech.smarthome.model.repo.DeviceRepository;
import com.ltech.smarthome.model.repo.GroupRepository;
import com.ltech.smarthome.model.repo.HomeRepository;
import com.ltech.smarthome.model.repo.IntercomRepository;
import com.ltech.smarthome.model.repo.McuRepository;
import com.ltech.smarthome.model.repo.ModeRepository;
import com.ltech.smarthome.model.repo.RoleRepository;
import com.ltech.smarthome.model.repo.SceneRepository;
import com.ltech.smarthome.model.repo.SongRepository;
import com.ltech.smarthome.model.repo.UserRepository;
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
import com.ltech.smarthome.net.SmartHomeRetrofit;
import com.ltech.smarthome.preference_bean.MusicBean;
import com.ltech.smarthome.push.PushManager;
import com.ltech.smarthome.service.music.IPlayerManager;
import com.ltech.smarthome.service.music.PlayerManager;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.singleton.StateManager;
import com.ltech.smarthome.ui.splash.ActSplash;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.smart.message.MessageManager;
import com.smart.message.StrategyFactory;
import com.smart.message.base.IMessageController;
import com.smart.message.base.IStrategyFactory;
import io.objectbox.BoxStore;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;

/* loaded from: classes4.dex */
public class Injection {
    private static BoxStore mBoxStore;

    private Injection() {
    }

    public static void init(Context context) {
        mBoxStore = MyObjectBox.builder().androidContext(context.getApplicationContext()).build();
    }

    private static void clearData(Context context) {
        try {
            File file = new File(context.getFilesDir().getPath() + "/objectbox/objectbox/data.mdb");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BoxStore boxStore() {
        return mBoxStore;
    }

    public static Repository repo() {
        return Repository.getInstance();
    }

    public static SmartHomeRetrofit net() {
        if (((SmartHomeRetrofit) Singleton.getSingleton(SmartHomeRetrofit.class)).getClientTime() != 15000) {
            Singleton.removeSingleton(SmartHomeRetrofit.class);
            SmartHomeRetrofit.timeOutSeconds = 15;
        }
        return (SmartHomeRetrofit) Singleton.getSingleton(SmartHomeRetrofit.class);
    }

    public static SmartHomeRetrofit net(int timeOutSeconds) {
        if (((SmartHomeRetrofit) Singleton.getSingleton(SmartHomeRetrofit.class)).getClientTime() != timeOutSeconds * 1000) {
            Singleton.removeSingleton(SmartHomeRetrofit.class);
            SmartHomeRetrofit.timeOutSeconds = 30;
        }
        return (SmartHomeRetrofit) Singleton.getSingleton(SmartHomeRetrofit.class);
    }

    public static RateLimiter limiter() {
        return (RateLimiter) Singleton.getSingleton(RateLimiter.class);
    }

    public static KeyCreator keyCreator() {
        return (KeyCreator) Singleton.getSingleton(KeyCreator.class);
    }

    public static IotManager iot() {
        return (IotManager) Singleton.getSingleton(IotManager.class);
    }

    public static IMessageController message() {
        return MessageManager.getInstance();
    }

    public static IStrategyFactory strategy() {
        return StrategyFactory.getInstance();
    }

    public static IPlayerManager<MusicBean> player() {
        return (IPlayerManager) Singleton.getSingleton(PlayerManager.class);
    }

    public static IMeshManager mesh() {
        return (IMeshManager) Singleton.getSingleton(MeshManager.class);
    }

    public static StateManager state() {
        return (StateManager) Singleton.getSingleton(StateManager.class);
    }

    public static PushManager push() {
        return (PushManager) Singleton.getSingleton(PushManager.class);
    }

    public static ProductFactory productFactory() {
        return (ProductFactory) Singleton.getSingleton(ProductFactory.class);
    }

    public static DcaRepository dca() {
        return (DcaRepository) Singleton.getSingleton(DcaRepository.class);
    }

    public static IntercomRepository intercom() {
        return (IntercomRepository) Singleton.getSingleton(IntercomRepository.class);
    }

    static IUser provideUserApi() {
        return new UserRepository(boxStore(), limiter(), keyCreator());
    }

    static IHome provideHomeApi() {
        return new HomeRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IScene provideSceneApi() {
        return new SceneRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IDevice provideDeviceApi() {
        return new DeviceRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IAutomation provideAutomationApi() {
        return new AutomationRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IGroup provideGroupApi() {
        return new GroupRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IMode provideModeApi() {
        return new ModeRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static ISong provideSongApi() {
        return new SongRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IRole provideRoleApi() {
        return new RoleRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    static IMcu provideMcuApi() {
        return new McuRepository(boxStore(), limiter(), keyCreator(), repo().user());
    }

    public static void logout() {
        push().pushUnbind((AppCompatActivity) ActivityUtils.getTopActivity());
        Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.ltech.smarthome.model.Injection.2
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                VoiceCallManager.getInstance().logout();
                Injection.limiter().clear();
                Injection.repo().user().clear();
                Injection.stopLocationService();
                Injection.iot().deInit();
                Injection.mesh().deInit();
                Injection.intercom().cleanSIPAccount();
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() { // from class: com.ltech.smarthome.model.Injection.1
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean aBoolean) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                Intent intent = new Intent(ActivityUtils.getTopActivity(), (Class<?>) ActSplash.class);
                intent.setFlags(268468224);
                ActivityUtils.getTopActivity().startActivity(intent);
            }
        });
    }

    public static void stopLocationService() {
        MyApplication.stopLocationService();
    }
}