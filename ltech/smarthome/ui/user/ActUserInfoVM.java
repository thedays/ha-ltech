package com.ltech.smarthome.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.User;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.user.UpdateUserInfoResponse;
import com.ltech.smarthome.net.response.user.UploadImageFileResponse;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.splash.ActSplash;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActUserInfoVM extends BaseViewModel {
    public ObservableList<GoItem> goItemObservableList;
    public MergeObservableList<Object> mergeObservableList;
    public int posEmail;
    public int posPhone;
    public int posUserHead;
    public int posUserName;
    public Listing<User> userLising;
    public SingleLiveEvent<String> showEditDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showRemoveTipDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPicDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showLogoutDialogEvent = new SingleLiveEvent<>();
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(GoItem.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda8
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActUserInfoVM.this.lambda$new$3(itemBinding, i, (GoItem) obj);
        }
    }).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda9
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActUserInfoVM.this.lambda$new$6(itemBinding, i, (String) obj);
        }
    });

    public ActUserInfoVM() {
        this.posUserHead = 0;
        this.posUserName = 1;
        this.posPhone = 2;
        this.posEmail = 3;
        ObservableArrayList observableArrayList = new ObservableArrayList();
        this.goItemObservableList = observableArrayList;
        this.posUserHead = observableArrayList.size();
        this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.user_head)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActUserInfoVM.this.lambda$new$0();
            }
        })));
        this.posUserName = this.goItemObservableList.size();
        this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.user_name)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActUserInfoVM.this.lambda$new$1();
            }
        })));
        this.posPhone = this.goItemObservableList.size();
        this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.phone_num)).setGoRes(R.mipmap.icon_more));
        this.posEmail = this.goItemObservableList.size();
        this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.email)).setGoRes(R.mipmap.icon_more));
        this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.account_and_security)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActUserInfoVM.this.lambda$new$2();
            }
        })));
        MergeObservableList<Object> mergeObservableList = new MergeObservableList<>();
        this.mergeObservableList = mergeObservableList;
        mergeObservableList.insertList(this.goItemObservableList).insertItem(ActivityUtils.getTopActivity().getString(R.string.log_out));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.showPicDialogEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        this.showEditDialogEvent.setValue(this.goItemObservableList.get(this.posUserName).getSubText());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        navigation(NavUtils.destination(AccountAndSecurity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ItemBinding itemBinding, int i, GoItem goItem) {
        if (i == this.posUserHead) {
            itemBinding.clearExtras().set(40, R.layout.item_pic).bindExtra(58, Integer.valueOf(R.mipmap.ic_my_photo_default)).bindExtra(28, Integer.valueOf(R.mipmap.ic_my_photo_default)).bindExtra(9, true);
        } else {
            itemBinding.clearExtras().set(40, R.layout.item_go_1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(ItemBinding itemBinding, int i, String str) {
        if (str.equals(ActivityUtils.getTopActivity().getString(R.string.disable_account))) {
            itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_black))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActUserInfoVM.this.lambda$new$4();
                }
            }));
        } else {
            itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(31, 17).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActUserInfoVM.this.lambda$new$5();
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4() {
        this.showRemoveTipDialogEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5() {
        this.showLogoutDialogEvent.call();
    }

    public void updateName(String name) {
        ((ObservableSubscribeProxy) Injection.net().updateUserName(name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActUserInfoVM.this.lambda$updateName$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActUserInfoVM$$ExternalSyntheticLambda4(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Injection.repo().user().setUserName(((UpdateUserInfoResponse) obj).getUsername());
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    public void removeUser() {
        Injection.push().pushUnbind((AppCompatActivity) ActivityUtils.getTopActivity());
        ((ObservableSubscribeProxy) Injection.net().removeUser().delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActUserInfoVM.this.lambda$removeUser$9((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActUserInfoVM$$ExternalSyntheticLambda4(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_STOP)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActUserInfoVM.lambda$removeUser$10(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeUser$9(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    static /* synthetic */ void lambda$removeUser$10(Object obj) throws Exception {
        VoiceCallManager.getInstance().logout();
        Injection.limiter().clear();
        Injection.repo().user().clear();
        Injection.stopLocationService();
        Injection.iot().deInit();
        NavUtils.destination(ActSplash.class).navigation(ActivityUtils.getTopActivity());
        SmartToast.showShort(R.string.remove_account_success);
    }

    public void updateUserHead(String cropImagePath) {
        ((ObservableSubscribeProxy) Injection.net().uploadHeaderFile(new File(cropImagePath)).flatMap(new Function() { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource updateUserHead;
                updateUserHead = Injection.net().updateUserHead(((UploadImageFileResponse) obj).getUrl());
                return updateUserHead;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<UpdateUserInfoResponse>(this) { // from class: com.ltech.smarthome.ui.user.ActUserInfoVM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(UpdateUserInfoResponse updateUserInfoResponse) throws Exception {
                Injection.repo().user().setHeaderUrl(updateUserInfoResponse.getHeadurl());
            }
        }, new SmartErrorComsumer());
    }
}