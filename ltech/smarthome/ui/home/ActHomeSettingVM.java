package com.ltech.smarthome.ui.home;

import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.share.ActAddPlaceUser;
import com.ltech.smarthome.ui.share.ActPlaceUserSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActHomeSettingVM extends BaseViewModel {
    public static final int POS_HOME_NAME = 0;
    public boolean dataChanged;
    public MergeObservableList<Object> mergeObservableList;
    public Listing<Place> placeListing;
    public int posHomeLocation = 1;
    public MutableLiveData<Place> place = new MutableLiveData<>();
    public SingleLiveEvent<Void> showDeleteHomeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showExitHomeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> goMapEvent = new SingleLiveEvent<>();
    public BindingRecyclerViewAdapter.ItemIds<Object> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda9
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            return ActHomeSettingVM.lambda$new$5(i, obj);
        }
    };
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(GoItem.class, 40, R.layout.item_go_1).map(PlaceUser.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda10
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActHomeSettingVM.this.lambda$new$7(itemBinding, i, (PlaceUser) obj);
        }
    }).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda11
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActHomeSettingVM.this.lambda$new$10(itemBinding, i, (String) obj);
        }
    });
    public ObservableList<GoItem> goItemObservableList = new ObservableArrayList();
    public ObservableList<PlaceUser> placeUserObservableList = new ObservableArrayList();
    public ObservableList<String> headerObservableList = new ObservableArrayList();
    public ObservableList<String> footerObservableList = new ObservableArrayList();

    public ActHomeSettingVM() {
        MergeObservableList<Object> mergeObservableList = new MergeObservableList<>();
        this.mergeObservableList = mergeObservableList;
        mergeObservableList.insertList(this.goItemObservableList).insertList(this.headerObservableList).insertList(this.placeUserObservableList).insertList(this.footerObservableList);
    }

    public void initList(final Place place) {
        this.goItemObservableList.clear();
        this.footerObservableList.clear();
        if (this.goItemObservableList.isEmpty()) {
            GoItem subText = new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.home_name)).setSubText(place.getPlaceName());
            if (place.isOwner() || place.isManager()) {
                subText.setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActHomeSettingVM.this.lambda$initList$0(place);
                    }
                }));
            }
            this.goItemObservableList.add(subText);
            GoItem subText2 = new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.home_location)).setSubText(place.getLocation());
            if (place.isOwner() || place.isManager()) {
                subText2.setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActHomeSettingVM.this.lambda$initList$1();
                    }
                }));
            }
            this.goItemObservableList.add(subText2);
            this.posHomeLocation = this.goItemObservableList.size() - 1;
            if (place.isOwner() || place.isManager()) {
                this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.home_qr_code)).setGoRes(R.mipmap.icon_more).setSubImageRes(R.mipmap.ic_family_qrcode).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActHomeSettingVM.this.lambda$initList$2(place);
                    }
                })));
                this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.floor_manage)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActHomeSettingVM.this.lambda$initList$3(place);
                    }
                })));
                this.goItemObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.room_manage)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActHomeSettingVM.this.lambda$initList$4(place);
                    }
                })));
            }
        } else {
            ObservableList<GoItem> observableList = this.goItemObservableList;
            observableList.set(0, observableList.get(0).setSubText(place.getPlaceName()));
            ObservableList<GoItem> observableList2 = this.goItemObservableList;
            int i = this.posHomeLocation;
            observableList2.set(i, observableList2.get(i).setSubText(place.getLocation()));
        }
        if (this.footerObservableList.isEmpty()) {
            if (place.isOwner() || place.isManager()) {
                if (this.headerObservableList.isEmpty()) {
                    this.headerObservableList.add(ActivityUtils.getTopActivity().getString(R.string.home_member));
                }
                this.footerObservableList.add(0, ActivityUtils.getTopActivity().getString(R.string.add_member));
            }
            this.footerObservableList.add(ActivityUtils.getTopActivity().getString(place.isOwner() ? R.string.remove_home : R.string.exit_home));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$0(Place place) {
        navigation(NavUtils.destination(ActEditHomeName.class).withString(Constants.PLACE_NAME, place.getPlaceName()).withLong(Constants.PLACE_ID, place.getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$1() {
        this.goMapEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$2(Place place) {
        navigation(NavUtils.destination(ActHomeQrCode.class).withLong(Constants.PLACE_ID, place.getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$3(Place place) {
        navigation(NavUtils.destination(ActFloorManage.class).withLong(Constants.PLACE_ID, place.getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$4(Place place) {
        navigation(NavUtils.destination(ActRoomManage.class).withLong(Constants.PLACE_ID, place.getPlaceId()));
    }

    static /* synthetic */ long lambda$new$5(int i, Object obj) {
        if (obj instanceof PlaceUser) {
            return ((PlaceUser) obj).getPlaceUserId();
        }
        return obj.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(ItemBinding itemBinding, int i, final PlaceUser placeUser) {
        itemBinding.clearExtras().set(89, R.layout.item_place_user).bindExtra(64, PlaceUser.getRoleString(ActivityUtils.getTopActivity(), placeUser.getRoleType())).bindExtra(2, getUserAccount(placeUser)).bindExtra(57, this.place.getValue());
        LHomeLog.i(getClass(), "place roletype -->" + this.place.getValue().getRoleType() + "  item roletype -->" + placeUser.getRoleType());
        if (this.place.getValue().isOwner() && placeUser.isOwner()) {
            return;
        }
        if ((!this.place.getValue().isManager() || placeUser.isMember()) && !this.place.getValue().isMember()) {
            itemBinding.bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActHomeSettingVM.this.lambda$new$6(placeUser);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(PlaceUser placeUser) {
        navigation(NavUtils.destination(ActPlaceUserSetting.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()).withLong(Constants.PLACE_USER_ID, placeUser.getPlaceUserId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(ItemBinding itemBinding, int i, String str) {
        if (str.equals(ActivityUtils.getTopActivity().getString(R.string.add_member))) {
            itemBinding.clearExtras().set(40, R.layout.item_text_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(39, Integer.valueOf(R.mipmap.ic_add_red)).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda15
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActHomeSettingVM.this.lambda$new$8();
                }
            }));
        } else if (str.equals(ActivityUtils.getTopActivity().getString(R.string.home_member))) {
            itemBinding.clearExtras().set(40, R.layout.item_text).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_gray)));
        } else {
            itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(31, 17).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActHomeSettingVM.this.lambda$new$9();
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8() {
        navigation(NavUtils.destination(ActAddPlaceUser.class).withLong(Constants.PLACE_ID, this.place.getValue().getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9() {
        if (this.place.getValue().isOwner()) {
            this.showDeleteHomeDialogEvent.call();
        } else {
            this.showExitHomeDialogEvent.call();
        }
    }

    public void deletePlace(final long placeId) {
        ((ObservableSubscribeProxy) Injection.net().deletePlace(placeId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHomeSettingVM.this.lambda$deletePlace$11((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActHomeSettingVM$$ExternalSyntheticLambda7(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHomeSettingVM.this.lambda$deletePlace$12(placeId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deletePlace$11(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deletePlace$12(long j, Object obj) throws Exception {
        Injection.repo().home().removePlace(j);
        Injection.mesh().clearSharedPreferencesByMeshUUID();
        finishActivity();
    }

    public void quitPlace(final long placeId) {
        ((ObservableSubscribeProxy) Injection.net().quitPlace(placeId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHomeSettingVM.this.lambda$quitPlace$13((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActHomeSettingVM$$ExternalSyntheticLambda7(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomeSettingVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHomeSettingVM.this.lambda$quitPlace$14(placeId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$quitPlace$13(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.mode_adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$quitPlace$14(long j, Object obj) throws Exception {
        Injection.repo().home().removePlace(j);
        finishActivity();
    }

    public String getUserAccount(PlaceUser user) {
        if (!TextUtils.isEmpty(user.getMobilephone())) {
            return user.getMobilephone();
        }
        if (!TextUtils.isEmpty(user.getEmail())) {
            return user.getEmail();
        }
        return "";
    }
}