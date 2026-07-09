package com.ltech.smarthome.ui.my;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.push.PlaceMsgListResponse;
import com.ltech.smarthome.push.PlaceMessage;
import com.ltech.smarthome.push.PushDataHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

/* loaded from: classes4.dex */
public class FtMessageHomeVM extends BaseViewModel {
    public MutableLiveData<Boolean> updateDataList = new MutableLiveData<>();
    public SingleLiveEvent<Void> loadMoreDataFinishEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> loadTotalDataEvent = new SingleLiveEvent<>();
    public ObservableList<PlaceMessage> mPlaceMessageList = new ObservableArrayList();
    public MergeObservableList<Object> dataList = new MergeObservableList().insertList(this.mPlaceMessageList);
    public int pageNum = 1;
    public boolean isLoadTotal = false;
    public final OnItemBind<Object> itemBinding = new OnItemBind<Object>(this) { // from class: com.ltech.smarthome.ui.my.FtMessageHomeVM.1
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public void onItemBind(ItemBinding itemBinding, int position, Object item) {
            if (String.class.equals(item.getClass())) {
                itemBinding.set(30, R.layout.item_message_data_footer);
            } else if (PlaceMessage.class.equals(item.getClass())) {
                PlaceMessage placeMessage = (PlaceMessage) item;
                itemBinding.set(60, R.layout.item_message_place).bindExtra(13, placeMessage.getContent()).bindExtra(15, placeMessage.getCreateTime());
            }
        }
    };

    public void getPlaceMessageList() {
        ((ObservableSubscribeProxy) Injection.net().getPlaceMsgList(1).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.FtMessageHomeVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMessageHomeVM.this.lambda$getPlaceMessageList$0((PlaceMsgListResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPlaceMessageList$0(PlaceMsgListResponse placeMsgListResponse) throws Exception {
        LHomeLog.i(getClass(), "res -->" + placeMsgListResponse);
        handleData(placeMsgListResponse);
    }

    public void getMorePlaceMessageList(int pagenum) {
        ((ObservableSubscribeProxy) Injection.net().getPlaceMsgList(pagenum).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.my.FtMessageHomeVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMessageHomeVM.this.lambda$getMorePlaceMessageList$1((PlaceMsgListResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMorePlaceMessageList$1(PlaceMsgListResponse placeMsgListResponse) throws Exception {
        LHomeLog.i(getClass(), "res -->" + placeMsgListResponse);
        handleMoreData(placeMsgListResponse);
    }

    private void handleData(PlaceMsgListResponse response) {
        this.mPlaceMessageList.clear();
        if (response.getRows().size() == 0 || response.getTotal() == 0) {
            this.updateDataList.setValue(false);
            return;
        }
        if (response.getTotal() > response.getRows().size()) {
            this.isLoadTotal = false;
            this.loadTotalDataEvent.postValue(false);
        } else {
            this.loadTotalDataEvent.postValue(true);
            this.isLoadTotal = true;
        }
        for (int i = 0; i < response.getRows().size(); i++) {
            PlaceMsgListResponse.RowData rowData = response.getRows().get(i);
            LHomeLog.i(getClass(), rowData.getContent());
            this.mPlaceMessageList.add(new PlaceMessage(rowData.getCreatetime(), String.valueOf(((HashMap) PushDataHelper.getBuildGson().fromJson(rowData.getContent(), HashMap.class)).get("body"))));
        }
        if (this.mPlaceMessageList.isEmpty()) {
            showEmpty();
        }
        this.updateDataList.setValue(true);
    }

    private void handleMoreData(PlaceMsgListResponse response) {
        if (response.getRows().size() == 0 || response.getTotal() == 0) {
            this.loadMoreDataFinishEvent.call();
            return;
        }
        if (response.getTotal() > ((this.pageNum - 1) * 20) + response.getRows().size()) {
            this.isLoadTotal = false;
            this.loadTotalDataEvent.postValue(false);
        } else {
            this.isLoadTotal = true;
            this.loadTotalDataEvent.postValue(true);
        }
        for (int i = 0; i < response.getRows().size(); i++) {
            PlaceMsgListResponse.RowData rowData = response.getRows().get(i);
            LHomeLog.i(getClass(), rowData.getContent());
            this.mPlaceMessageList.add(new PlaceMessage(rowData.getCreatetime(), rowData.getContent()));
        }
        if (this.mPlaceMessageList.isEmpty()) {
            showEmpty();
        }
        this.loadMoreDataFinishEvent.call();
    }
}