package com.ltech.smarthome.base;

import android.text.TextUtils;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes3.dex */
public abstract class BaseGroupManageVM extends BaseViewModel {
    public long placeId;
    public Listing<Place> placeInfoResult;
    public List<Floor> mFloorList = new ArrayList();
    public List<Room> mRoomList = new ArrayList();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    public ObservableList<Group> mGroupList = new ObservableArrayList();
    public ItemBinding<Group> itemBinding = getItemBinding();
    public BindingRecyclerViewAdapter.ItemIds<Group> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.base.BaseGroupManageVM$$ExternalSyntheticLambda2
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            long id;
            id = ((Group) obj).getId();
            return id;
        }
    };
    public List<Long> selectGroupIdList = new ArrayList();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>(Integer.valueOf(this.selectGroupIdList.size()));
    public List<Group> allData = new ArrayList();

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: clickItem, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$getItemBinding$1(int position, Group Group);

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getItemBinding$2(ItemBinding itemBinding, final int i, final Group group) {
        itemBinding.clearExtras().set(32, R.layout.item_group_manage).bindExtra(59, getPlaceName(group.getFloorId(), group.getRoomId())).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(group))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.base.BaseGroupManageVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                BaseGroupManageVM.this.lambda$getItemBinding$1(i, group);
            }
        }));
    }

    protected ItemBinding<Group> getItemBinding() {
        return ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.base.BaseGroupManageVM$$ExternalSyntheticLambda1
            @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
            public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
                BaseGroupManageVM.this.lambda$getItemBinding$2(itemBinding, i, (Group) obj);
            }
        });
    }

    public Floor checkFloor(List<Floor> floorList) {
        Floor value = this.selectFloor.getValue();
        if (value != null) {
            Iterator<Floor> it = floorList.iterator();
            while (it.hasNext()) {
                if (it.next().getFloorId() == value.getFloorId()) {
                    return value;
                }
            }
        }
        return floorList.get(0);
    }

    public boolean setCurFloor(Floor floor) {
        this.selectFloor.setValue(floor);
        return true;
    }

    public Floor getCurFloor() {
        return this.selectFloor.getValue();
    }

    public Room checkRoom(List<Room> roomList) {
        Room value = this.selectRoom.getValue();
        if (value != null) {
            Iterator<Room> it = roomList.iterator();
            while (it.hasNext()) {
                if (it.next().getRoomId() == value.getRoomId()) {
                    return value;
                }
            }
        }
        return roomList.get(0);
    }

    public boolean setCurRoom(Room room) {
        this.selectRoom.setValue(room);
        return true;
    }

    public Room getCurRoom() {
        return this.selectRoom.getValue();
    }

    public String getPlaceName(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        Iterator<Floor> it = this.mFloorList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Floor next = it.next();
            if (next.getFloorId() != -1 && next.getFloorId() == floorId) {
                sb.append(next.getFloorName());
                break;
            }
        }
        Iterator<Room> it2 = this.mRoomList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Room next2 = it2.next();
            if (next2.getRoomId() != -1 && next2.getRoomId() == roomId) {
                sb.append(next2.getRoomName());
                break;
            }
        }
        if (TextUtils.isEmpty(sb.toString())) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.no_set_room));
        }
        return sb.toString();
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Group>>() { // from class: com.ltech.smarthome.base.BaseGroupManageVM.2
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Group>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Group group : BaseGroupManageVM.this.allData) {
                        if (group.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(group);
                        }
                    }
                } else {
                    arrayList.addAll(BaseGroupManageVM.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Group>>() { // from class: com.ltech.smarthome.base.BaseGroupManageVM.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Group> items) {
                BaseGroupManageVM.this.mGroupList.clear();
                BaseGroupManageVM.this.mGroupList.addAll(items);
            }
        });
    }
}