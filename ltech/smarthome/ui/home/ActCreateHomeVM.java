package com.ltech.smarthome.ui.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.feasy.FeasyMeshNetHelper;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.SmartHomeRetrofit;
import com.ltech.smarthome.net.request.room.RoomName;
import com.ltech.smarthome.net.response.place.AddPlaceResponse;
import com.ltech.smarthome.ui.item.SelectItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActCreateHomeVM extends BaseViewModel {
    public ObservableField<String> homeName = new ObservableField<>();
    public ObservableField<String> homeLocation = new ObservableField<>("");
    public ObservableList<SelectItem> mObservableList = new ObservableArrayList();
    public MergeObservableList<Object> mMergeObservableList = new MergeObservableList().insertList(this.mObservableList).insertItem(ActivityUtils.getTopActivity().getString(R.string.add_room));
    public SingleLiveEvent<Void> selectHomePositionEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> creatHomeLocationTipEvent = new SingleLiveEvent<>();
    public double latitude = -1.0d;
    public double longitude = -1.0d;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.home.ActCreateHomeVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCreateHomeVM.this.lambda$new$0((View) obj);
        }
    });
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(SelectItem.class, 40, R.layout.item_select).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActCreateHomeVM$$ExternalSyntheticLambda5
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActCreateHomeVM.this.lambda$new$2(itemBinding, i, (String) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.layout_home_position) {
            return;
        }
        this.selectHomePositionEvent.call();
    }

    public ActCreateHomeVM() {
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_1));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_2));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_3));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_4));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_5));
    }

    public void addRoom(String name) {
        this.mObservableList.add(new SelectItem(name, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(ItemBinding itemBinding, int i, String str) {
        itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActCreateHomeVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActCreateHomeVM.this.lambda$new$1();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        String[] strArr = new String[this.mObservableList.size()];
        int size = this.mObservableList.size();
        for (int i = 0; i < size; i++) {
            strArr[i] = this.mObservableList.get(i).getName();
        }
        navigation(NavUtils.destination(ActDiyRoom.class).withStringArray(Constants.ROOM_NAME_ARRAY, strArr).withDefaultRequestCode());
    }

    public void createHome(final Context context) {
        if (TextUtils.isEmpty(this.homeName.get())) {
            SmartToast.showShort(R.string.input_home_name);
            return;
        }
        if (TextUtils.isEmpty(this.homeLocation.get())) {
            this.creatHomeLocationTipEvent.call();
            return;
        }
        ArrayList arrayList = new ArrayList();
        int size = this.mObservableList.size();
        for (int i = 0; i < size; i++) {
            if (this.mObservableList.get(i).isSelect().getValue().booleanValue()) {
                arrayList.add(new RoomName(this.mObservableList.get(i).getName()));
            }
        }
        String createMeshUUID = FeasyMeshNetHelper.createMeshUUID();
        String createNetKey = FeasyMeshNetHelper.createNetKey();
        String createAppKey = FeasyMeshNetHelper.createAppKey();
        SmartHomeRetrofit net = Injection.net();
        String str = this.homeName.get();
        if (arrayList.size() <= 0) {
            arrayList = null;
        }
        ((ObservableSubscribeProxy) net.addPlace(str, arrayList, this.homeLocation.get(), this.latitude, this.longitude, createNetKey, createAppKey, createMeshUUID).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActCreateHomeVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCreateHomeVM.this.lambda$createHome$3(context, (Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActCreateHomeVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActCreateHomeVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActCreateHomeVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCreateHomeVM.this.lambda$createHome$4((AddPlaceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createHome$3(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createHome$4(AddPlaceResponse addPlaceResponse) throws Exception {
        Place place = new Place();
        place.setPlaceId(addPlaceResponse.getPlaceid());
        place.setPlaceName(addPlaceResponse.getPlacename());
        place.setLatitude(addPlaceResponse.getLatitude());
        place.setLongitude(addPlaceResponse.getLongitude());
        place.setQrCode(addPlaceResponse.getQrcode());
        place.setOwner();
        place.setAppKey(addPlaceResponse.getApplicationkey());
        place.setNetKey(addPlaceResponse.getNetkey());
        place.setMeshUUID(addPlaceResponse.getMeshuuid());
        place.setAppToken(addPlaceResponse.getMeshuuid());
        place.setProvisionerAddress(addPlaceResponse.getProvisioneraddress());
        Injection.repo().home().savePlace(place);
        if (addPlaceResponse.getFloors() != null && addPlaceResponse.getFloors().getTotal() > 0) {
            for (AddPlaceResponse.FloorsBean.RowsBeanX rowsBeanX : addPlaceResponse.getFloors().getRows()) {
                Floor floor = new Floor();
                floor.setPlaceId(rowsBeanX.getPlaceid());
                floor.setFloorId(rowsBeanX.getFloorid());
                floor.setFloorName(rowsBeanX.getFloorname());
                floor.setIndex(rowsBeanX.getFloorindex());
                Injection.repo().home().saveFloor(floor);
            }
        }
        if (addPlaceResponse.getRooms() != null && addPlaceResponse.getRooms().getTotal() > 0) {
            for (AddPlaceResponse.RoomsBean.RowsBean rowsBean : addPlaceResponse.getRooms().getRows()) {
                Room room = new Room();
                room.setPlaceId(addPlaceResponse.getPlaceid());
                room.setRoomId(rowsBean.getRoomid());
                room.setFloorId(rowsBean.getFloorid());
                room.setRoomName(rowsBean.getRoomname());
                room.setIndex(rowsBean.getRoomindex());
                Injection.repo().home().saveRoom(room);
            }
        }
        finishActivity();
    }
}