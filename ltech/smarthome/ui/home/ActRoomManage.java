package com.ltech.smarthome.ui.home;

import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.adapter.RoomLoadingAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActRoomManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.popup.FloorPopup;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActRoomManage extends VMActivity<ActRoomManageBinding, ActRoomManageVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_room_manage;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new RoomLoadingAdapter().setNoFloorStringRes(R.string.no_floor).setNoFloorTryStringRes(R.string.add_floor).setRetryCallback(new RoomLoadingAdapter.IRetryCallback() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.adapter.RoomLoadingAdapter.IRetryCallback
            public final void retry(int i) {
                ActRoomManage.this.lambda$createGLoading$0(i);
            }
        }).emptyStringRes(R.string.no_room).emptyTryStringRes(R.string.add_room));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGLoading$0(int i) {
        NavUtils.destination(ActAddFloor.class).withLong(Constants.PLACE_ID, ((ActRoomManageVM) this.mViewModel).placeId).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.room_manage));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showFloorPopup();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActRoomManageVM) this.mViewModel).changeFloorEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRoomManage.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActRoomManageVM) this.mViewModel).floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        ((ActRoomManageVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActRoomManageVM) this.mViewModel).floorListing = Injection.repo().home().getFloorList(this, ((ActRoomManageVM) this.mViewModel).placeId);
        ((ActRoomManageVM) this.mViewModel).floorResult.addSource(((ActRoomManageVM) this.mViewModel).floorListing.data(), new Observer() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRoomManage.this.lambda$startObserve$3((Resource) obj);
            }
        });
        handleData(Transformations.switchMap(((ActRoomManageVM) this.mViewModel).floorResult, new Function1() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$4;
                lambda$startObserve$4 = ActRoomManage.this.lambda$startObserve$4((Floor) obj);
                return lambda$startObserve$4;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRoomManage.this.lambda$startObserve$5((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRoomManage.this.lambda$startObserve$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        ((ActRoomManageVM) this.mViewModel).mFloorList = list;
        if (list.isEmpty()) {
            showLoadingState(66);
        } else {
            ((ActRoomManageVM) this.mViewModel).setCurFloor(((ActRoomManageVM) this.mViewModel).checkFloor(list));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$4(Floor floor) {
        if (floor == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().home().getRoomList(this, ((ActRoomManageVM) this.mViewModel).placeId, floor.getFloorId()).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(List list) {
        ((ActRoomManageVM) this.mViewModel).mObservableList.clear();
        ((ActRoomManageVM) this.mViewModel).mObservableList.addAll(list);
        setEditImage(R.mipmap.ic_sort);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(ActSortRoom.class).withLong(Constants.PLACE_ID, ((ActRoomManageVM) this.mViewModel).placeId).withLong(Constants.FLOOR_ID, ((ActRoomManageVM) this.mViewModel).getCurFloor().getFloorId()).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onEmptyTry() {
        super.onEmptyTry();
        ((ActRoomManageVM) this.mViewModel).goAddRoom();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        setEditImage(0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActRoomManageVM) this.mViewModel).floorListing.retry();
    }

    private void showFloorPopup() {
        ArrayList arrayList = new ArrayList();
        for (Floor floor : ((ActRoomManageVM) this.mViewModel).mFloorList) {
            arrayList.add(new GoItem().setMainText(floor.getFloorName()).setSelect(floor.equals(((ActRoomManageVM) this.mViewModel).getCurFloor())));
        }
        final FloorPopup apply = FloorPopup.create(this).setData(arrayList).apply();
        apply.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.home.ActRoomManage$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActRoomManage.this.lambda$showFloorPopup$6(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((ActRoomManageBinding) this.mViewBinding).appCompatTextView5, 2, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$6(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!((ActRoomManageVM) this.mViewModel).mFloorList.get(i).equals(((ActRoomManageVM) this.mViewModel).getCurFloor())) {
            ((ActRoomManageVM) this.mViewModel).setCurFloor(((ActRoomManageVM) this.mViewModel).mFloorList.get(i));
        }
        floorPopup.dismiss();
    }
}