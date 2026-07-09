package com.ltech.smarthome.ui.home;

import android.content.Context;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
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
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActHomeTransferSettingVM extends BaseViewModel {
    public MergeObservableList<Object> mergeObservableList;
    public Listing<Place> placeListing;
    public MutableLiveData<Place> place = new MutableLiveData<>();
    public SingleLiveEvent<PlaceUser> showTransferDialogEvent = new SingleLiveEvent<>();
    public BindingRecyclerViewAdapter.ItemIds<Object> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSettingVM$$ExternalSyntheticLambda4
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            return ActHomeTransferSettingVM.lambda$new$0(i, obj);
        }
    };
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(GoItem.class, 40, R.layout.item_go_1).map(PlaceUser.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSettingVM$$ExternalSyntheticLambda5
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActHomeTransferSettingVM.this.lambda$new$2(itemBinding, i, (PlaceUser) obj);
        }
    });
    public ObservableList<GoItem> goItemObservableList = new ObservableArrayList();
    public ObservableList<PlaceUser> placeUserObservableList = new ObservableArrayList();

    public void initList(Place place) {
    }

    public ActHomeTransferSettingVM() {
        MergeObservableList<Object> mergeObservableList = new MergeObservableList<>();
        this.mergeObservableList = mergeObservableList;
        mergeObservableList.insertList(this.goItemObservableList).insertList(this.placeUserObservableList);
    }

    static /* synthetic */ long lambda$new$0(int i, Object obj) {
        if (obj instanceof PlaceUser) {
            return ((PlaceUser) obj).getPlaceUserId();
        }
        return obj.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(ItemBinding itemBinding, int i, final PlaceUser placeUser) {
        itemBinding.clearExtras().set(89, R.layout.item_place_user_transform_place).bindExtra(64, PlaceUser.getRoleString(ActivityUtils.getTopActivity(), placeUser.getRoleType()));
        if ((this.place.getValue().isOwner() && placeUser.isOwner()) || this.place.getValue().isManager() || this.place.getValue().isMember()) {
            return;
        }
        itemBinding.bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSettingVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActHomeTransferSettingVM.this.lambda$new$1(placeUser);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(PlaceUser placeUser) {
        this.showTransferDialogEvent.setValue(placeUser);
    }

    public void transferHome(final Context context, final long placeId, long userId) {
        ((ObservableSubscribeProxy) Injection.net().transferPlace(placeId, userId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHomeTransferSettingVM.this.lambda$transferHome$3(context, (Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActHomeTransferSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActHomeTransferSettingVM.this.lambda$transferHome$4(placeId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$transferHome$3(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$transferHome$4(long j, Object obj) throws Exception {
        Injection.limiter().reset(Injection.keyCreator().placeUserKey(j));
        Injection.limiter().reset(Injection.keyCreator().placeKey(j));
        setResult(Constants.TRANSFORM_FAMILY_SUCCESS);
        finishActivity();
    }
}