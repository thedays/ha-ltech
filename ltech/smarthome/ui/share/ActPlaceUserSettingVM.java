package com.ltech.smarthome.ui.share;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.ListGroupResponse;
import com.ltech.smarthome.net.response.place.QueryPlaceInfoResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActPlaceUserSettingVM extends BaseViewModel {
    public Listing<Place> placeListing;
    public MutableLiveData<Place> place = new MutableLiveData<>();
    public MutableLiveData<PlaceUser> placeUser = new MutableLiveData<>();
    public MutableLiveData<Boolean> pOwner = new MutableLiveData<>();
    public MutableLiveData<Boolean> bManager = new MutableLiveData<>();
    public MutableLiveData<Boolean> bOwner = new MutableLiveData<>();
    public SingleLiveEvent<Void> removeEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda5
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActPlaceUserSettingVM.this.lambda$new$8((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(View view) {
        switch (view.getId()) {
            case R.id.layout_device /* 2131297427 */:
                ((ObservableSubscribeProxy) Injection.net().queryPlaceDetailInfo(this.place.getValue().getPlaceId(), this.placeUser.getValue().getUserId()).delaySubscription(100L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda8
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$2((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda9
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$3((QueryPlaceInfoResponse) obj);
                    }
                }, new SmartErrorComsumer());
                break;
            case R.id.layout_group /* 2131297485 */:
                ((ObservableSubscribeProxy) Injection.net().listGroup(this.place.getValue().getPlaceId(), this.placeUser.getValue().getUserId()).delaySubscription(100L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda10
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$4((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda11
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$5((ListGroupResponse) obj);
                    }
                }, new SmartErrorComsumer());
                break;
            case R.id.layout_name /* 2131297551 */:
                if (this.place.getValue().isManager() || this.place.getValue().isOwner()) {
                    navigation(NavUtils.destination(ActEditMemberName.class).withLong(Constants.PLACE_USER_ID, this.placeUser.getValue().getPlaceUserId()).withString(Constants.MEMBER_NICK_NAME, this.placeUser.getValue().getRemark()).withInt(Constants.USER_ROLE_TYPE, this.placeUser.getValue().getRoleType()).withDefaultRequestCode());
                    break;
                }
                break;
            case R.id.layout_room /* 2131297603 */:
                ((ObservableSubscribeProxy) Injection.net().queryPlaceDetailInfo(this.place.getValue().getPlaceId(), this.placeUser.getValue().getUserId()).delaySubscription(100L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$0((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$1((QueryPlaceInfoResponse) obj);
                    }
                }, new SmartErrorComsumer());
                break;
            case R.id.layout_scene /* 2131297611 */:
                ((ObservableSubscribeProxy) Injection.net().listAllSceneByUser(this.place.getValue().getPlaceId(), this.placeUser.getValue().getUserId()).delaySubscription(100L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$6((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActPlaceUserSettingVM.this.lambda$new$7((ListSceneResponse) obj);
                    }
                }, new SmartErrorComsumer());
                break;
            case R.id.tv_remove /* 2131298912 */:
                this.removeEvent.call();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(QueryPlaceInfoResponse queryPlaceInfoResponse) throws Exception {
        LHomeLog.i(getClass(), "listRoom: response enter");
        PlaceShareHelper.getInstance().roomIds = new ArrayList();
        for (int i = 0; i < queryPlaceInfoResponse.getRooms().getTotal(); i++) {
            PlaceShareHelper.getInstance().roomIds.add(Long.valueOf(queryPlaceInfoResponse.getRooms().getRows().get(i).getRoomid()));
        }
        dismissLoadingDialog();
        NavUtils.destination(ActSelectRoomForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).withLong(Constants.USER_ID, this.placeUser.getValue().getUserId()).navigation(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(QueryPlaceInfoResponse queryPlaceInfoResponse) throws Exception {
        LHomeLog.i(getClass(), "listRoom: response enter");
        PlaceShareHelper.getInstance().deviceIds = new ArrayList();
        for (int i = 0; i < queryPlaceInfoResponse.getDevices().getTotal(); i++) {
            PlaceShareHelper.getInstance().deviceIds.add(Long.valueOf(queryPlaceInfoResponse.getDevices().getRows().get(i).getDeviceid()));
        }
        dismissLoadingDialog();
        NavUtils.destination(ActSelectDeviceForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).withLong(Constants.USER_ID, this.placeUser.getValue().getUserId()).navigation(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(ListGroupResponse listGroupResponse) throws Exception {
        LHomeLog.i(getClass(), "listRoom: response enter");
        PlaceShareHelper.getInstance().groupIds = new ArrayList();
        for (int i = 0; i < listGroupResponse.getRows().size(); i++) {
            PlaceShareHelper.getInstance().groupIds.add(Long.valueOf(listGroupResponse.getRows().get(i).getGroupid()));
        }
        dismissLoadingDialog();
        NavUtils.destination(ActSelectGroupForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).withLong(Constants.USER_ID, this.placeUser.getValue().getUserId()).navigation(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(ListSceneResponse listSceneResponse) throws Exception {
        LHomeLog.i(getClass(), "listRoom: response enter");
        PlaceShareHelper.getInstance().sceneIds = new ArrayList();
        for (int i = 0; i < listSceneResponse.getTotal(); i++) {
            PlaceShareHelper.getInstance().sceneIds.add(Long.valueOf(listSceneResponse.getRows().get(i).getSceneid()));
        }
        dismissLoadingDialog();
        NavUtils.destination(ActSelectSceneForPermission.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).withLong(Constants.USER_ID, this.placeUser.getValue().getUserId()).navigation(ActivityUtils.getTopActivity());
    }

    public void removePlaceUser(final Context context) {
        ((ObservableSubscribeProxy) Injection.net().deletePlaceUser(this.place.getValue().getPlaceId(), this.placeUser.getValue().getUserId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPlaceUserSettingVM.this.lambda$removePlaceUser$9(context, (Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActPlaceUserSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPlaceUserSettingVM.this.lambda$removePlaceUser$10(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removePlaceUser$9(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removePlaceUser$10(Object obj) throws Exception {
        Injection.limiter().reset(Injection.keyCreator().placeUserKey(this.place.getValue().getPlaceId()));
        finishActivity();
    }
}