package com.ltech.smarthome.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseSelectDeviceActivity extends BaseListActivity<Device> {
    public MediatorLiveData<Resource<List<Device>>> deviceResult;
    protected long floorId;
    protected Listing<Floor> mRequest;
    protected long placeId;
    protected String productId;
    protected long roomId;
    protected MediatorLiveData<List<Room>> roomList = new MediatorLiveData<>();
    protected MediatorLiveData<List<Floor>> floorList = new MediatorLiveData<>();
    protected List<Device> allData = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Device> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        this.roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.mRequest = Injection.repo().home().getFloorList(this, this.placeId);
        this.floorList.setValue(Injection.repo().home().getFloorListByPlaceId(this.placeId));
        this.roomList.setValue(Injection.repo().home().getRoomListByFloorId(this.placeId, -1L));
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
        this.deviceResult = new MediatorLiveData<>();
        Collections.sort(deviceListByPlaceId, new Comparator<Device>(this) { // from class: com.ltech.smarthome.base.BaseSelectDeviceActivity.1
            @Override // java.util.Comparator
            public int compare(Device o1, Device o2) {
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() < o2.getIndex()) {
                    return -1;
                }
                if (o1.getCreatetime() == null || o2.getCreatetime() == null) {
                    return 0;
                }
                return o1.getCreatetime().compareTo(o2.getCreatetime());
            }
        });
        this.deviceResult.setValue(Resource.success(deviceListByPlaceId));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        Listing<Floor> listing = this.mRequest;
        if (listing != null) {
            listing.retry();
        }
    }

    protected String getPlaceInfo(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        if (this.floorList.getValue() != null) {
            Iterator<Floor> it = this.floorList.getValue().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Floor next = it.next();
                if (floorId == next.getFloorId()) {
                    sb.append(next.getFloorName());
                    break;
                }
            }
        }
        if (this.roomList.getValue() != null) {
            Iterator<Room> it2 = this.roomList.getValue().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Room next2 = it2.next();
                if (roomId == next2.getRoomId()) {
                    sb.append(next2.getRoomName());
                    break;
                }
            }
        }
        return sb.toString();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Device>>() { // from class: com.ltech.smarthome.base.BaseSelectDeviceActivity.3
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Device>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Device device : BaseSelectDeviceActivity.this.allData) {
                        if (device.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(device);
                        }
                    }
                } else {
                    arrayList.addAll(BaseSelectDeviceActivity.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Device>>() { // from class: com.ltech.smarthome.base.BaseSelectDeviceActivity.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Device> items) {
                BaseSelectDeviceActivity.this.deviceResult.postValue(Resource.success(items));
            }
        });
    }
}