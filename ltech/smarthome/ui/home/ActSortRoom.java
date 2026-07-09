package com.ltech.smarthome.ui.home;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSortActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.room.SortRoomRequest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortRoom extends BaseSortActivity<Room> {
    private Listing<Room> roomListing;

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_sort;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Listing<Room> roomList = Injection.repo().home().getRoomList(this, getIntent().getLongExtra(Constants.PLACE_ID, -1L), getIntent().getLongExtra(Constants.FLOOR_ID, -1L));
        this.roomListing = roomList;
        handleData(roomList, new IAction() { // from class: com.ltech.smarthome.ui.home.ActSortRoom$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSortRoom.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (this.dataList.isEmpty()) {
            setDataList(list);
        }
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected List<Room> getItemList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSortActivity
    public void convertView(BaseViewHolder helper, Room item) {
        helper.setText(R.id.tv_name, item.getRoomName());
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            SortRoomRequest.RoomSortBean roomSortBean = new SortRoomRequest.RoomSortBean();
            roomSortBean.roomid = ((Room) this.dataList.get(i)).getRoomId();
            arrayList.add(roomSortBean);
        }
        ((ObservableSubscribeProxy) Injection.net().sortRoom(arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActSortRoom$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortRoom.this.lambda$saveData$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActSortRoom$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSortRoom.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActSortRoom$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortRoom.this.lambda$saveData$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$2(Object obj) throws Exception {
        Injection.repo().home().sortRoom(this.dataList);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        this.roomListing.retry();
    }
}