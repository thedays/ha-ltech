package com.ltech.smarthome.ui.home;

import android.content.Context;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.SmartHomeRetrofit;
import com.ltech.smarthome.net.response.floor.AddFloorResponse;
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
public class ActAddFloorVM extends BaseViewModel {
    public long placeId;
    public ObservableField<String> floorName = new ObservableField<>();
    public ObservableList<SelectItem> observableList = new ObservableArrayList();
    public MergeObservableList<Object> mergeObservableList = new MergeObservableList().insertList(this.observableList).insertItem(ActivityUtils.getTopActivity().getString(R.string.add_room));
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(SelectItem.class, 40, R.layout.item_select).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActAddFloorVM$$ExternalSyntheticLambda0
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActAddFloorVM.this.lambda$new$1(itemBinding, i, (String) obj);
        }
    });

    public ActAddFloorVM() {
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_1));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_2));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_3));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_4));
        addRoom(ActivityUtils.getTopActivity().getString(R.string.room_default_name_5));
    }

    public void addRoom(String name) {
        this.observableList.add(new SelectItem(name, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ItemBinding itemBinding, int i, String str) {
        itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActAddFloorVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAddFloorVM.this.lambda$new$0();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        String[] strArr = new String[this.observableList.size()];
        int size = this.observableList.size();
        for (int i = 0; i < size; i++) {
            strArr[i] = this.observableList.get(i).getName();
        }
        navigation(NavUtils.destination(ActDiyRoom.class).withStringArray(Constants.ROOM_NAME_ARRAY, strArr).withDefaultRequestCode());
    }

    public void createFloor(final Context context) {
        if (TextUtils.isEmpty(this.floorName.get())) {
            SmartToast.showShort(R.string.input_floor_name);
            return;
        }
        ArrayList arrayList = new ArrayList();
        int size = this.observableList.size();
        for (int i = 0; i < size; i++) {
            if (this.observableList.get(i).isSelect().getValue().booleanValue()) {
                arrayList.add(this.observableList.get(i).getName());
            }
        }
        SmartHomeRetrofit net = Injection.net();
        long j = this.placeId;
        String str = this.floorName.get();
        if (arrayList.size() <= 0) {
            arrayList = null;
        }
        ((ObservableSubscribeProxy) net.addFloor(j, str, arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActAddFloorVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddFloorVM.this.lambda$createFloor$2(context, (Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActAddFloorVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActAddFloorVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActAddFloorVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddFloorVM.this.lambda$createFloor$3((AddFloorResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFloor$2(Context context, Disposable disposable) throws Exception {
        showLoadingDialog(context.getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFloor$3(AddFloorResponse addFloorResponse) throws Exception {
        Floor floor = new Floor();
        floor.setPlaceId(addFloorResponse.getPlaceid());
        floor.setFloorId(addFloorResponse.getFloorid());
        floor.setFloorName(addFloorResponse.getFloorname());
        floor.setIndex(addFloorResponse.getFloorindex());
        Injection.repo().home().saveFloor(floor);
        if (addFloorResponse.getRooms().getTotal() > 0) {
            for (AddFloorResponse.RoomsBean.RowsBean rowsBean : addFloorResponse.getRooms().getRows()) {
                Room room = new Room();
                room.setPlaceId(addFloorResponse.getPlaceid());
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