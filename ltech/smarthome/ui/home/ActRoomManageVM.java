package com.ltech.smarthome.ui.home;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MediatorLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes4.dex */
public class ActRoomManageVM extends BaseViewModel {
    public long floorId;
    public Listing<Floor> floorListing;
    public long placeId;
    public MediatorLiveData<Floor> floorResult = new MediatorLiveData<>();
    public List<Floor> mFloorList = new ArrayList();
    public SingleLiveEvent<Void> changeFloorEvent = new SingleLiveEvent<>();
    public ObservableList<Room> mObservableList = new ObservableArrayList();
    public MergeObservableList<Object> mMergeObservableList = new MergeObservableList().insertList(this.mObservableList).insertItem(ActivityUtils.getTopActivity().getString(R.string.add_room));
    public OnItemBindClass<Object> itemBinding = new OnItemBindClass().map(Room.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActRoomManageVM$$ExternalSyntheticLambda2
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActRoomManageVM.this.lambda$new$1(itemBinding, i, (Room) obj);
        }
    }).map(String.class, new OnItemBind() { // from class: com.ltech.smarthome.ui.home.ActRoomManageVM$$ExternalSyntheticLambda3
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActRoomManageVM.this.lambda$new$2(itemBinding, i, (String) obj);
        }
    });
    public BindingRecyclerViewAdapter.ItemIds<Object> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.ui.home.ActRoomManageVM$$ExternalSyntheticLambda4
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            return ActRoomManageVM.lambda$new$3(i, obj);
        }
    };
    public BindingCommand changeFloor = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActRoomManageVM$$ExternalSyntheticLambda5
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            ActRoomManageVM.this.lambda$new$4();
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ItemBinding itemBinding, int i, final Room room) {
        itemBinding.clearExtras().set(40, R.layout.item_room_manage).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActRoomManageVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActRoomManageVM.this.lambda$new$0(room);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Room room) {
        navigation(NavUtils.destination(ActEditRoomName.class).withString(Constants.ROOM_NAME, room.getRoomName()).withLong(Constants.ROOM_ID, room.getRoomId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(ItemBinding itemBinding, int i, String str) {
        itemBinding.clearExtras().set(40, R.layout.footer_add).bindExtra(81, Integer.valueOf(ContextCompat.getColor(Utils.getApp(), R.color.color_text_red))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.home.ActRoomManageVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActRoomManageVM.this.goAddRoom();
            }
        }));
    }

    static /* synthetic */ long lambda$new$3(int i, Object obj) {
        return obj instanceof Room ? ((Room) obj).getRoomId() : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4() {
        this.changeFloorEvent.call();
    }

    public Floor checkFloor(List<Floor> floorList) {
        Floor value = this.floorResult.getValue();
        if (value != null) {
            for (Floor floor : floorList) {
                if (floor.getFloorId() == value.getFloorId()) {
                    value.setFloorName(floor.getFloorName());
                    return value;
                }
            }
        } else {
            for (Floor floor2 : floorList) {
                if (floor2.getFloorId() == this.floorId) {
                    return floor2;
                }
            }
        }
        return floorList.get(0);
    }

    public boolean setCurFloor(Floor floor) {
        this.floorResult.setValue(floor);
        return true;
    }

    public Floor getCurFloor() {
        return this.floorResult.getValue();
    }

    public void goAddRoom() {
        String[] strArr = new String[this.mObservableList.size()];
        int size = this.mObservableList.size();
        for (int i = 0; i < size; i++) {
            strArr[i] = this.mObservableList.get(i).getRoomName();
        }
        navigation(NavUtils.destination(ActDiyRoom.class).withLong(Constants.PLACE_ID, this.placeId).withLong(Constants.FLOOR_ID, getCurFloor().getFloorId()).withStringArray(Constants.ROOM_NAME_ARRAY, strArr));
    }
}