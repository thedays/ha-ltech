package com.ltech.smarthome.ui.share;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseMultiTypesSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.IFlyRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.InvitationPlaceUserRequest;
import com.ltech.smarthome.net.request.role.UpdateRoomRoleRequest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSelectRoomForPermission extends BaseMultiTypesSelectActivity {
    public Listing<Place> mPlaceListing;
    private long placeId;
    public Listing<Place> request;
    private long userId;
    public List<Floor> floorList = new ArrayList();
    public List<Room> roomList = new ArrayList();
    List<MultiItemEntity> dataList = new ArrayList();
    public List<Place> mPlaceList = new ArrayList();
    public List<Floor> mFloorList = new ArrayList();
    public MediatorLiveData<Place> placeResult = new MediatorLiveData<>();
    public MediatorLiveData<Place> placeInfoResult = new MediatorLiveData<>();
    public MediatorLiveData<Floor> floorResult = new MediatorLiveData<>();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> checkUserEvent = new SingleLiveEvent<>();
    public LiveData<Resource<List<Room>>> roomResult = Transformations.switchMap(this.floorResult, new Function1() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            LiveData lambda$new$0;
            lambda$new$0 = ActSelectRoomForPermission.this.lambda$new$0((Floor) obj);
            return lambda$new$0;
        }
    });

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$new$0(Floor floor) {
        if (floor == null || getCurPlace() == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().home().getRoomList(this, getCurPlace().getPlaceId(), -1L).data();
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        initData();
        setTitle(getString(R.string.room_permission));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            setInitSelectAll(false);
        } else if (PlaceShareHelper.getInstance().roomIds == null) {
            setInitSelectAll(true);
        } else {
            setInitSelectAll(false);
        }
    }

    private void initData() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.userId = getIntent().getLongExtra(Constants.USER_ID, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity
    protected void save() {
        PlaceShareHelper.getInstance().roomIds = getSelectRoom();
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            updateRoomPermission();
        } else {
            finishActivity();
        }
    }

    private boolean isSelectAll() {
        boolean z = true;
        for (int i = 1; i < this.selectArray.length; i++) {
            z &= this.selectArray[i];
        }
        return z;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.request = Injection.repo().home().getPlaceDetailInfo(this, this.placeId);
        this.mPlaceListing = Injection.repo().home().getPlace(this, this.placeId);
        Listing<Place> placeList = Injection.repo().home().getPlaceList(this);
        this.request = placeList;
        this.placeResult.addSource(placeList.data(), new Observer() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$2((Resource) obj);
            }
        });
        this.placeInfoResult.addSource(Transformations.switchMap(this.placeResult, new Function1() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$3;
                lambda$startObserve$3 = ActSelectRoomForPermission.this.lambda$startObserve$3((Place) obj);
                return lambda$startObserve$3;
            }
        }), new Observer() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$5((Resource) obj);
            }
        });
        this.floorResult.addSource(Transformations.switchMap(this.placeInfoResult, new Function1() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$6;
                lambda$startObserve$6 = ActSelectRoomForPermission.this.lambda$startObserve$6((Place) obj);
                return lambda$startObserve$6;
            }
        }), new Observer() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$8((Resource) obj);
            }
        });
        handleData(this.roomResult, new IAction() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$9(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$1(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        List<Place> list = (List) obj;
        this.mPlaceList = list;
        if (list.isEmpty()) {
            showEmpty();
            setCurPlace(this, null);
        } else {
            setCurPlace(this, checkPlace(list));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$3(Place place) {
        if (place == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().home().getPlaceDetailInfo(this, place.getPlaceId()).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Object obj) {
        this.placeInfoResult.setValue(getCurPlace());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$4(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$6(Place place) {
        if (place == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().home().getFloorList(this, place.getPlaceId()).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectRoomForPermission.this.lambda$startObserve$7(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Object obj) {
        List<Floor> list = (List) obj;
        this.mFloorList = list;
        if (list.isEmpty()) {
            showEmpty();
            setCurFloor(null);
        } else {
            setCurFloor(checkFloor(list));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Object obj) {
        updateDataList((List) obj, this.mFloorList);
    }

    private void updateDataList(List<Room> rooms, List<Floor> mFloorList) {
        for (int i = 0; i < mFloorList.size(); i++) {
            this.dataList.add(new TitleTypeItem(mFloorList.get(i).getFloorName(), mFloorList.get(i).getFloorId()));
            for (int i2 = 0; i2 < rooms.size(); i2++) {
                if (rooms.get(i2).getFloorId() == mFloorList.get(i).getFloorId()) {
                    this.dataList.add(new RoomBriefInfo(rooms.get(i2).getRoomName(), rooms.get(i2).getRoomId()));
                }
            }
        }
        setDataList(this.dataList);
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity
    protected void setUpData() {
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity
    protected void initSelectArray() {
        Arrays.fill(this.selectArray, false);
        for (int i = 0; i < this.selectArray.length; i++) {
            if (this.dataList.get(i).getItemType() == 1) {
                RoomBriefInfo roomBriefInfo = (RoomBriefInfo) this.dataList.get(i);
                Iterator<Long> it = PlaceShareHelper.getInstance().roomIds.iterator();
                while (it.hasNext()) {
                    if (it.next().longValue() == roomBriefInfo.getRoomId()) {
                        this.selectArray[i] = true;
                    }
                }
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity
    protected List getList() {
        ArrayList arrayList = new ArrayList();
        this.dataList = arrayList;
        return arrayList;
    }

    private void updateRoomPermission() {
        ((ObservableSubscribeProxy) Injection.net().updateRoomRole(generateRequest()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectRoomForPermission.this.lambda$updateRoomPermission$10((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectRoomForPermission.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectRoomForPermission$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectRoomForPermission.this.lambda$updateRoomPermission$11(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRoomPermission$10(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRoomPermission$11(Object obj) throws Exception {
        LHomeLog.i(getClass(), "updateRoomRole: response enter");
        finishActivity();
    }

    private UpdateRoomRoleRequest generateRequest() {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = PlaceShareHelper.getInstance().roomIds.iterator();
        while (it.hasNext()) {
            arrayList.add(new InvitationPlaceUserRequest.RoomId(it.next().longValue()));
        }
        return new UpdateRoomRoleRequest(this.placeId, this.userId, arrayList);
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity
    protected int[] itemLayouts() {
        return new int[]{R.layout.item_default, R.layout.item_select};
    }

    @Override // com.ltech.smarthome.base.BaseMultiTypesSelectActivity
    protected void convertView(BaseViewHolder helper, MultiItemEntity item) {
        if (item.getItemType() == 0) {
            helper.setText(R.id.tv_name, ((TitleTypeItem) item).getTitleName());
        } else {
            helper.setText(R.id.tv_name, ((RoomBriefInfo) item).getRoomName()).setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
            ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
        }
    }

    public List<Long> getSelectRoom() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.dataList.get(i).getItemType() == 1 && this.selectArray[i]) {
                arrayList.add(Long.valueOf(((RoomBriefInfo) this.dataList.get(i)).getRoomId()));
            }
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_room));
    }

    public boolean setCurPlace(Context context, Place place) {
        if (place == null) {
            return false;
        }
        this.placeResult.setValue(place);
        Injection.repo().home().setSelectPlace(place);
        if (context == null) {
            new IFlyRepository().getAppToken(ActivityUtils.getTopActivity(), this, place.getPlaceId());
            return true;
        }
        new IFlyRepository().getAppToken(context, this, place.getPlaceId());
        return true;
    }

    public Place checkPlace(List<Place> placeList) {
        for (Place place : placeList) {
            if (place.getPlaceId() == this.placeId) {
                return place;
            }
        }
        return null;
    }

    public Place getCurPlace() {
        return this.placeResult.getValue();
    }

    public Floor getCurFloor() {
        return this.floorResult.getValue();
    }

    public boolean setCurFloor(Floor floor) {
        this.floorResult.setValue(floor);
        return true;
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
        }
        return floorList.get(0);
    }
}