package com.ltech.smarthome.ui.control;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.User;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet;
import com.ltech.smarthome.ui.device.light.ActChoiceLightType;
import com.ltech.smarthome.ui.home.ActDeviceManage;
import com.ltech.smarthome.ui.home.ActHideDeviceManagerNew;
import com.ltech.smarthome.ui.home.ActHomeManage;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.ui.intercom.ActIntercom;
import com.ltech.smarthome.ui.intercom.ActIntercomTips;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.my.ActAbout;
import com.ltech.smarthome.ui.my.ActLanguageSelect;
import com.ltech.smarthome.ui.my.ActMessageCenter;
import com.ltech.smarthome.ui.user.ActUserInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.Utils;
import com.smart.message.utils.LHomeLog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class FtMyVM extends BaseViewModel {
    public boolean hasNewVersion;
    public Listing<Place> request;
    VersionInfo versionInfo;
    public ObservableList<GoItem> mObservableList = new ObservableArrayList();
    public MutableLiveData<User> user = new MutableLiveData<>();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> needBleEvent = new SingleLiveEvent<>();
    public ItemBinding<GoItem> itemBinding = ItemBinding.of(40, R.layout.item_go);
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda5
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtMyVM.this.lambda$new$14((View) obj);
        }
    });

    public FtMyVM() {
        initMyList();
        if (Utils.isYYBFlavor(ActivityUtils.getTopActivity())) {
            checkVersion();
        }
    }

    public void initMyList() {
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.home_manage)).setImageRes(R.mipmap.ic_my_home).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMyVM.this.lambda$initMyList$0();
            }
        })));
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.device_manage)).setGoRes(R.mipmap.icon_more).setImageRes(R.mipmap.ico_my_list_icon_device).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda12
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$1();
                }
            })));
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_str_hide_devices)).setGoRes(R.mipmap.icon_more).setImageRes(R.mipmap.ico_my_list_icon_hide).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$2();
                }
            })));
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.light_mode)).setImageRes(R.mipmap.ic_my_mode).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$3();
                }
            })));
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.circadian_lighting)).setImageRes(R.mipmap.my_list_icon_cl).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda15
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$4();
                }
            })));
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.intercom)).setImageRes(R.mipmap.ic_intercom).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda16
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$5();
                }
            })));
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.firmware_upgrade)).setImageRes(R.mipmap.device_upgrate).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$6();
                }
            })));
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_str_batch_set)).setImageRes(R.mipmap.ico_my_list_icon_tool).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$7();
                }
            })));
        }
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.language_setting)).setImageRes(R.mipmap.ic_my_list_language).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMyVM.this.lambda$initMyList$8();
            }
        })));
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_str_third_party)).setImageRes(R.mipmap.icon_my_list_platform).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMyVM.this.lambda$initMyList$9();
            }
        })));
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_str_engineering_mode)).setImageRes(R.mipmap.icon_my_list_engineer).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$initMyList$10();
                }
            })));
        }
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.help_center)).setImageRes(R.mipmap.ic_my_help).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMyVM.this.lambda$initMyList$11();
            }
        })));
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.feedback)).setImageRes(R.mipmap.ic_my_feedback).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMyVM.this.lambda$initMyList$12();
            }
        })));
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.about)).setImageRes(R.mipmap.ic_my_setting).setGoRes(R.mipmap.icon_more).setSubImageRes(0).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMyVM.this.lambda$initMyList$13();
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$0() {
        navigation(NavUtils.destination(ActHomeManage.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$1() {
        navigation(NavUtils.destination(ActDeviceManage.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$2() {
        navigation(NavUtils.destination(ActHideDeviceManagerNew.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$3() {
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            navigation(NavUtils.destination(ActChoiceLightType.class));
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$4() {
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            navigation(NavUtils.destination(ActLightPlanBatchSet.class));
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$5() {
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            if (Injection.intercom().isLogin()) {
                navigation(NavUtils.destination(ActIntercom.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()));
                return;
            } else {
                navigation(NavUtils.destination(ActIntercomTips.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()));
                return;
            }
        }
        this.showNoPermissionDialogEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$6() {
        this.needBleEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$7() {
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            NavUtils.destination(ActEngineeringMode.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()).navigation(ActivityUtils.getTopActivity());
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$8() {
        navigation(NavUtils.destination(ActLanguageSelect.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$9() {
        if (getCurPlace() != null && (getCurPlace().isManager() || getCurPlace().isOwner())) {
            navigation(NavUtils.destination(ActSmartSpeaker.class));
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$10() {
        navigation(NavUtils.destination(ActEditPassword.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$11() {
        navigation(NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActivityUtils.getTopActivity().getString(R.string.faq_url)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$12() {
        navigation(NavUtils.destination(ActFeedback.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$13() {
        NavUtils.Builder destination = NavUtils.destination(ActAbout.class);
        if (this.hasNewVersion) {
            destination.withString("content", this.versionInfo.getContent()).withInt("versioncode", this.versionInfo.getAppversionnum()).withString("fileurl", this.versionInfo.getFileurl());
        }
        destination.navigation(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$14(View view) {
        int id = view.getId();
        if (id == R.id.cardview) {
            navigation(NavUtils.destination(ActUserInfo.class));
        } else if (id == R.id.home_news) {
            NavUtils.destination(ActMessageCenter.class).navigation(ActivityUtils.getTopActivity());
        } else {
            if (id != R.id.tv_sub) {
                return;
            }
            navigation(NavUtils.destination(ActHomeManage.class));
        }
    }

    private void checkVersion() {
        PackageInfo packageInfo;
        try {
            packageInfo = ActivityUtils.getTopActivity().getPackageManager().getPackageInfo(ActivityUtils.getTopActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        final int i = packageInfo.versionCode;
        Injection.net().checkAppVersion("Android", i + "", ActivityUtils.getTopActivity().getPackageName()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMyVM.this.lambda$checkVersion$16(i, (VersionInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkVersion$16(int i, VersionInfo versionInfo) throws Exception {
        this.versionInfo = versionInfo;
        if (versionInfo.getAppversionnum() > i) {
            LHomeLog.i(getClass(), "update:has version");
            this.hasNewVersion = true;
            ObservableList<GoItem> observableList = this.mObservableList;
            observableList.remove(observableList.size() - 1);
            this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.about)).setImageRes(R.mipmap.ic_my_setting).setGoRes(R.mipmap.icon_more).setSubImageRes(R.drawable.dot_red).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtMyVM$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtMyVM.this.lambda$checkVersion$15();
                }
            })));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkVersion$15() {
        NavUtils.Builder destination = NavUtils.destination(ActAbout.class);
        if (this.hasNewVersion) {
            destination.withBoolean("newversion", true).withString("versionname", this.versionInfo.getAppversioncode()).withInt("versioncode", this.versionInfo.getAppversionnum()).withString("fileurl", this.versionInfo.getFileurl());
        }
        destination.navigation(ActivityUtils.getTopActivity());
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }
}