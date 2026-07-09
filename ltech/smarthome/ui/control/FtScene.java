package com.ltech.smarthome.ui.control;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import anet.channel.util.ErrorConstant;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.newselect.FtRoomScene;
import com.ltech.smarthome.ui.scene.ActSortSceneNew;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.popup.FloorPopup;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtScene extends VMFragment<FtSceneBinding, FtSceneVM> implements IIntelligence {
    private long deviceId;
    private long floorId;
    private boolean groupControl;
    private long placeId;
    private boolean relateSceneChange;
    private long[] relateSceneIds;
    private long roomId;
    public FtRoomScene selectFt;
    private RoomPickHelper roomPickHelper = new RoomPickHelper();
    public List<Floor> mFloorList = new ArrayList();

    static /* synthetic */ boolean lambda$showNoPermissionDialog$8(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected boolean isRootViewClickEnable() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_scene;
    }

    public static FtScene newInstance(long deviceId, boolean groupControl) {
        FtScene ftScene = new FtScene();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        ftScene.setArguments(bundle);
        return ftScene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.groupControl = arguments.getBoolean(Constants.GROUP_CONTROL);
            this.deviceId = arguments.getLong("device_id");
            Role roleByRoleId = ((FtSceneVM) this.mViewModel).getRoleByRoleId(this.deviceId);
            if (roleByRoleId != null) {
                this.floorId = roleByRoleId.getFloorId();
                this.roomId = roleByRoleId.getRoomId();
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtSceneBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((FtSceneBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtSceneBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtSceneBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda3
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtScene.this.lambda$initView$0(refreshLayout);
            }
        });
        ((FtSceneBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtScene.this.lambda$initView$1((View) obj);
            }
        }));
        ((FtSceneBinding) this.mViewBinding).tvFloorContent.setText(getString(R.string.all_floor));
        ((FtSceneVM) this.mViewModel).needFloorRoomMemory = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.refresh();
        } else {
            queryScene();
        }
        ((FtSceneBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        if (view.getId() != R.id.tv_floor_content) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mFloorList.size(); i++) {
            arrayList.add(new GoItem().setMainText(this.mFloorList.get(i).getFloorName()).setSelect(this.mFloorList.get(i).equals(((FtSceneVM) this.mViewModel).getCurFloor())));
        }
        showFloorPopup(arrayList);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.deviceId > 0) {
            queryScene();
            for (Floor floor : ((FtSceneVM) this.mViewModel).floors) {
                if (floor.getFloorId() == this.floorId) {
                    ((FtSceneVM) this.mViewModel).setCurFloor(floor);
                }
            }
            return;
        }
        if (!(ActivityUtils.getTopActivity() instanceof ActHome) || ((FtSceneVM) this.mViewModel).floors == null) {
            return;
        }
        ((FtSceneVM) this.mViewModel).setCurFloor(((FtSceneVM) this.mViewModel).checkFloor(((FtSceneVM) this.mViewModel).floors, ""));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        if (this.deviceId <= 0) {
            handleData(((FtSceneVM) this.mViewModel).sceneLiveData, new IAction() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtScene.this.lambda$startObserve$2((List) obj);
                }
            });
        } else {
            ((FtSceneVM) this.mViewModel).placeId.setValue(Long.valueOf(Injection.repo().home().getSelectPlace().getValue().getPlaceId()));
        }
        ((FtSceneVM) this.mViewModel).placeId.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtScene.this.lambda$startObserve$3((Long) obj);
            }
        });
        ((FtSceneVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.ui.control.FtScene.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                ((FtSceneBinding) FtScene.this.mViewBinding).tvFloorContent.setText(floor.getFloorName());
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(FtScene.this.placeId, floor.getFloorId());
                List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(FtScene.this.placeId);
                if (floor.getFloorId() == -1) {
                    Floor floor2 = new Floor();
                    floor2.setFloorName(FtScene.this.getString(R.string.app_str_not_distribution));
                    floor2.setFloorId(0L);
                    floor2.setPlaceId(FtScene.this.placeId);
                    if (FtScene.this.showUnConfig(0L, -1L)) {
                        floorListByPlaceId.add(0, floor2);
                    }
                } else {
                    Room room = new Room();
                    room.setRoomName(FtScene.this.getString(R.string.type_all));
                    room.setRoomId(-1L);
                    room.setFloorId(((FtSceneVM) FtScene.this.mViewModel).getCurFloor().getFloorId());
                    room.setPlaceId(FtScene.this.placeId);
                    Room room2 = new Room();
                    room2.setRoomName(FtScene.this.getString(R.string.app_str_not_distribution));
                    room2.setRoomId(0L);
                    room2.setFloorId(0L);
                    room2.setPlaceId(FtScene.this.placeId);
                    if (FtScene.this.showUnConfig(room2.getFloorId(), room2.getRoomId())) {
                        roomListByFloorId.add(0, room2);
                    }
                }
                FtScene.this.setRoomView(roomListByFloorId, floorListByPlaceId);
                ((FtSceneVM) FtScene.this.mViewModel).isFirst = false;
            }
        });
        ((FtSceneVM) this.mViewModel).addSceneEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtScene.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        ((FtSceneVM) this.mViewModel).allData.clear();
        ((FtSceneVM) this.mViewModel).allData.addAll(list);
        ((FtSceneVM) this.mViewModel).placeId.setValue(Long.valueOf(Injection.repo().home().getSelectPlace().getValue().getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Long l) {
        this.placeId = l.longValue();
        ((FtSceneVM) this.mViewModel).initFloorList(this.placeId);
        if (((FtSceneVM) this.mViewModel).floors.get(0).getFloorId() == -1) {
            ((FtSceneBinding) this.mViewBinding).layoutFloor.setVisibility(8);
        } else {
            ((FtSceneBinding) this.mViewBinding).layoutFloor.setVisibility(0);
            this.mFloorList.clear();
            this.mFloorList.addAll(((FtSceneVM) this.mViewModel).floors);
        }
        ((FtSceneVM) this.mViewModel).setCurFloor(((FtSceneVM) this.mViewModel).checkFloor(((FtSceneVM) this.mViewModel).floors, Constants.USER_CUR_FLOOR_FOR_SCENE));
        this.roomPickHelper.resetObserve();
        this.roomPickHelper.startObserve(this, this.placeId, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        goAdd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showUnConfig(long floorId, long roomId) {
        if (this.deviceId <= 0) {
            return ((FtSceneVM) this.mViewModel).hasUnConfigScene(floorId, roomId);
        }
        return ((FtSceneVM) this.mViewModel).hasUnConfigRelateScene(((FtSceneVM) this.mViewModel).allData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRoomView(List<Room> rooms, List<Floor> floorList) {
        if (this.mViewModel == 0) {
            return;
        }
        boolean checkRoomChange = ((FtSceneVM) this.mViewModel).checkRoomChange(rooms, floorList);
        if (this.deviceId <= 0 && checkRoomChange) {
            ((FtSceneVM) this.mViewModel).initRoomList(rooms, floorList);
            initViewPager();
        } else if (checkRoomChange || this.relateSceneChange) {
            ((FtSceneVM) this.mViewModel).initRoomList(rooms, floorList, this.relateSceneIds);
            initViewPager();
        }
        if (this.deviceId > 0) {
            ((FtSceneVM) this.mViewModel).roomPosition = 0;
            int i = 0;
            while (true) {
                if (i >= ((FtSceneVM) this.mViewModel).mRoomList.size()) {
                    break;
                }
                if (this.roomId == ((FtSceneVM) this.mViewModel).mRoomList.get(i).getRoomId()) {
                    ((FtSceneVM) this.mViewModel).roomPosition = i;
                    break;
                }
                i++;
            }
        } else {
            Activity topActivity = ActivityUtils.getTopActivity();
            if (topActivity instanceof ActHome) {
                ActHome actHome = (ActHome) topActivity;
                ((FtSceneVM) this.mViewModel).roomPosition = 0;
                int i2 = 0;
                while (true) {
                    if (i2 >= ((FtSceneVM) this.mViewModel).mRoomList.size()) {
                        break;
                    }
                    if (actHome.getSelectRoomId() == ((FtSceneVM) this.mViewModel).mRoomList.get(i2).getRoomId()) {
                        ((FtSceneVM) this.mViewModel).roomPosition = i2;
                        break;
                    }
                    i2++;
                }
            }
        }
        if (this.mViewBinding != 0) {
            ((FtSceneBinding) this.mViewBinding).viewpager.setCurrentItem(((FtSceneVM) this.mViewModel).roomPosition, false);
            if (((FtSceneVM) this.mViewModel).mRoomList.size() > ((FtSceneVM) this.mViewModel).roomPosition) {
                FtRoomScene ftScene = ((FtSceneVM) this.mViewModel).mRoomList.get(((FtSceneVM) this.mViewModel).roomPosition).getFtScene();
                this.selectFt = ftScene;
                ftScene.getData();
            }
        }
    }

    private void initViewPager() {
        ((FtSceneBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.control.FtScene.2
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtSceneVM) FtScene.this.mViewModel).mRoomList.get(position).getFtScene();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtSceneVM) FtScene.this.mViewModel).mRoomList.size();
            }
        });
        ((FtSceneBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((FtSceneBinding) this.mViewBinding).tabs, ((FtSceneBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda5
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                FtScene.this.lambda$initViewPager$5(tab, i);
            }
        }).attach();
        ((FtSceneBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.control.FtScene.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                ((FtSceneVM) FtScene.this.mViewModel).roomPosition = position;
                FtScene ftScene = FtScene.this;
                ftScene.selectFt = ((FtSceneVM) ftScene.mViewModel).mRoomList.get(position).getFtScene();
                FtScene.this.selectFt.getData();
                Activity topActivity = ActivityUtils.getTopActivity();
                if (topActivity instanceof ActHome) {
                    ((ActHome) topActivity).setSelectRoomId(((FtSceneVM) FtScene.this.mViewModel).mRoomList.get(position).getRoomId());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$5(TabLayout.Tab tab, int i) {
        tab.setText(((FtSceneVM) this.mViewModel).mRoomList.get(i).getRoomName());
    }

    private void queryScene() {
        if (this.groupControl) {
            ((ObservableSubscribeProxy) Injection.net().queryGroupScene(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtScene.this.lambda$queryScene$6((ListSceneResponse) obj);
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().queryDeviceScene(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtScene.this.lambda$queryScene$7((ListSceneResponse) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$6(ListSceneResponse listSceneResponse) throws Exception {
        showRelateScene(Injection.repo().scene().getSceneListFromNet(listSceneResponse, 3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$7(ListSceneResponse listSceneResponse) throws Exception {
        showRelateScene(Injection.repo().scene().getSceneListFromNet(listSceneResponse, 3));
    }

    private void showRelateScene(List<Scene> list) {
        long[] jArr = this.relateSceneIds;
        this.relateSceneChange = jArr == null || jArr.length != list.size();
        if (list.size() > 0) {
            showContent();
            ((FtSceneVM) this.mViewModel).allData.clear();
            ((FtSceneVM) this.mViewModel).allData.addAll(list);
            this.relateSceneIds = new long[list.size()];
            for (int i = 0; i < list.size(); i++) {
                this.relateSceneIds[i] = list.get(i).getSceneId();
            }
            ((FtSceneVM) this.mViewModel).placeId.setValue(Long.valueOf(((FtSceneVM) this.mViewModel).getCurPlaceId()));
            return;
        }
        this.relateSceneIds = null;
        showEmpty();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSort() {
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            NavUtils.destination(ActSortSceneNew.class).withLong(Constants.PLACE_ID, place.getPlaceId()).withInt(Constants.SCENE_TYPE, 3).navigation(this);
        } else {
            showNoPermissionDialog();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goAdd() {
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            if (this.mViewModel == 0 || this.roomPickHelper == null) {
                return;
            }
            ((FtSceneVM) this.mViewModel).showEditNameDialog(this.roomPickHelper);
            return;
        }
        showNoPermissionDialog();
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSearch() {
        NavUtils.destination(ActSearchScene.class).withLong(Constants.PLACE_ID, ((Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class)).getPlaceId()).withBoolean(Constants.IS_LOCAL_SCENE, true).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(ActivityUtils.getTopActivity() instanceof ActIntelligence ? R.string.no_relate_scene : R.string.no_scence).emptyTryStringRes(R.string.add_group1));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        goAdd();
    }

    private void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtScene.lambda$showNoPermissionDialog$8(baseDialog, view);
            }
        });
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.refresh();
        } else {
            queryScene();
        }
    }

    private void showFloorPopup(List<GoItem> itemList) {
        final FloorPopup apply = FloorPopup.create(getActivity()).setData(itemList).apply();
        apply.setArrowLeft().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.FtScene$$ExternalSyntheticLambda7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                FtScene.this.lambda$showFloorPopup$9(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((FtSceneBinding) this.mViewBinding).tvFloorContent, 2, 2, ErrorConstant.ERROR_NO_NETWORK, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$9(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!this.mFloorList.get(i).equals(((FtSceneVM) this.mViewModel).getCurFloor())) {
            ((FtSceneVM) this.mViewModel).floorPosition = i;
            ((FtSceneVM) this.mViewModel).setCurFloor(this.mFloorList.get(i));
        }
        floorPopup.dismiss();
    }
}