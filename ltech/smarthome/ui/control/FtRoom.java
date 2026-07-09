package com.ltech.smarthome.ui.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.blemesh.IRefreshNetworkCallback;
import com.ltech.smarthome.blemesh.bean.MeshJsonBean;
import com.ltech.smarthome.blemesh.feasy.FeasyMeshNetHelper;
import com.ltech.smarthome.databinding.FtRoomBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.push.PushDataHelper;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.home.ActDeviceManage;
import com.ltech.smarthome.ui.home.ActHomeManage;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.ListDialog;
import com.ltech.smarthome.view.popup.FloorPopup;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class FtRoom extends VMFragment<FtRoomBinding, FtRoomVM> {
    private ActHome actHome;
    int currentPosition;
    private boolean floorChange;
    private boolean isScreenOff;
    private ListDialog listDialog;
    private boolean needRefreshMesh = false;
    private int retryTimes = 3;
    private boolean editMode = false;
    Handler handler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.control.FtRoom.6
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                if (FtRoom.this.isBlePermissionOk()) {
                    Injection.mesh().checkNearbyDevice(msg.getData().getString("meshUUid"), FtRoom.this.getActivity());
                }
                FtRoom.this.handler.sendEmptyMessageDelayed(3, 20000L);
                return false;
            }
            if (msg.what == 2 || msg.what != 3 || Injection.mesh().getConnectedDevice() != null || Injection.repo().device().getOnlineGateway() == null) {
                return false;
            }
            ((FtRoomVM) FtRoom.this.mViewModel).starPool();
            ((FtRoomVM) FtRoom.this.mViewModel).setDeviceKey();
            return false;
        }
    });
    private boolean showAllDev = true;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.ltech.smarthome.ui.control.FtRoom.12
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            action.hashCode();
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                FtRoom.this.isScreenOff = true;
            } else if (action.equals("android.intent.action.SCREEN_ON")) {
                FtRoom.this.isScreenOff = false;
            }
        }
    };

    static /* synthetic */ boolean lambda$showNoPermissionDialog$23(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ void lambda$subscribeDevice$19(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_room;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showEmpty() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device).emptyTryStringRes(R.string.add_device));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
        ((FtRoomBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((FtRoomBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtRoomBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtRoomBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda13
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtRoom.this.lambda$initView$0(refreshLayout);
            }
        });
        if (getActivity() != null) {
            getActivity().registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
            getActivity().registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
        ((FtRoomVM) this.mViewModel).isRefresh = true;
        getPlace();
        ((FtRoomBinding) this.mViewBinding).refreshLayout.finishRefresh();
        PushDataHelper.getNoHandleData();
    }

    private void getPlace() {
        ((FtRoomVM) this.mViewModel).request.refresh();
    }

    private void initViewPager(boolean dataChange) {
        if (this.mViewBinding == 0) {
            return;
        }
        if (dataChange || ((FtRoomBinding) this.mViewBinding).viewpager.getAdapter() == null) {
            ((FtRoomBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.control.FtRoom.1
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter
                public Fragment createFragment(int position) {
                    return ((FtRoomVM) FtRoom.this.mViewModel).mRoomList.get(position).getFtDevice();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return ((FtRoomVM) FtRoom.this.mViewModel).mRoomList.size();
                }
            });
            ((FtRoomBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(10);
            ((FtRoomBinding) this.mViewBinding).tabs.removeAllTabs();
            for (int i = 0; i < ((FtRoomVM) this.mViewModel).mRoomList.size(); i++) {
                ((FtRoomBinding) this.mViewBinding).tabs.addTab(((FtRoomBinding) this.mViewBinding).tabs.newTab().setText(((FtRoomVM) this.mViewModel).mRoomList.get(i).getRoomName()));
            }
            ((FtRoomBinding) this.mViewBinding).tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.control.FtRoom.2
                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    if (!FtRoom.this.editMode) {
                        ((FtRoomBinding) FtRoom.this.mViewBinding).viewpager.setCurrentItem(tab.getPosition());
                    } else if (tab.getPosition() != FtRoom.this.currentPosition) {
                        ((FtRoomBinding) FtRoom.this.mViewBinding).tabs.getTabAt(FtRoom.this.currentPosition).select();
                    }
                }
            });
            ((FtRoomBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.control.FtRoom.3
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageSelected(int position) {
                    ((FtRoomBinding) FtRoom.this.mViewBinding).tabs.selectTab(((FtRoomBinding) FtRoom.this.mViewBinding).tabs.getTabAt(position));
                    FtRoom.this.currentPosition = position;
                    ((FtRoomVM) FtRoom.this.mViewModel).selectedRoomId = ((FtRoomVM) FtRoom.this.mViewModel).mRoomList.get(position).getRoomId();
                    if (FtRoom.this.actHome != null) {
                        FtRoom.this.actHome.setSelectRoomId(((FtRoomVM) FtRoom.this.mViewModel).selectedRoomId);
                    }
                }
            });
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtRoomVM) this.mViewModel).request = Injection.repo().home().getPlaceList(this);
        ((FtRoomVM) this.mViewModel).placeResult.addSource(((FtRoomVM) this.mViewModel).request.data(), new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda20
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$2((Resource) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).placeInfoResult.addSource(Transformations.switchMap(((FtRoomVM) this.mViewModel).placeResult, new Function1() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$3;
                lambda$startObserve$3 = FtRoom.this.lambda$startObserve$3((Place) obj);
                return lambda$startObserve$3;
            }
        }), new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$5((Resource) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).floorResult.addSource(Transformations.switchMap(((FtRoomVM) this.mViewModel).placeInfoResult, new Function1() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$6;
                lambda$startObserve$6 = FtRoom.this.lambda$startObserve$6((Place) obj);
                return lambda$startObserve$6;
            }
        }), new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$8((Resource) obj);
            }
        });
        Injection.repo().mcu().getMcuListFromNet(this, Utils.getVersionCode(getActivity()));
        handleData(((FtRoomVM) this.mViewModel).roomResult, new IAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoom.this.lambda$startObserve$9((List) obj);
            }
        });
        handleData(Transformations.switchMap(((FtRoomVM) this.mViewModel).placeInfoResult, new Function1() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$10;
                lambda$startObserve$10 = FtRoom.this.lambda$startObserve$10((Place) obj);
                return lambda$startObserve$10;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoom.this.lambda$startObserve$11((List) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).changePlaceEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$12((Void) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).changeFloorEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$13((Void) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$14((Void) obj);
            }
        });
        this.handler.sendEmptyMessageDelayed(2, 2000L);
        ((FtRoomVM) this.mViewModel).initMeshEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.control.FtRoom.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                FtRoom.this.dismissLoadingDialog();
                if (FtRoom.this.needRefreshMesh) {
                    FtRoom.this.needRefreshMesh = false;
                    FtRoom.this.refreshMeshNetwork();
                    FtRoom.this.subscribeDevice();
                }
            }
        });
        ((FtRoomVM) this.mViewModel).canScroll.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda22
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$15((Boolean) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).scanEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.control.FtRoom.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (((FtRoomVM) FtRoom.this.mViewModel).isFirstLoading || !FtRoom.this.isBlePermissionOk()) {
                    return;
                }
                ((FtRoomVM) FtRoom.this.mViewModel).scan();
            }
        });
        ((FtRoomVM) this.mViewModel).addUnConfigRoomEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda23
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$16((Void) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).editRoleMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$17((Boolean) obj);
            }
        });
        ((FtRoomVM) this.mViewModel).editSceneMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRoom.this.lambda$startObserve$18((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoom.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ((FtRoomVM) this.mViewModel).mPlaceList = list;
        if (list.isEmpty()) {
            showEmpty();
            ((FtRoomVM) this.mViewModel).setCurPlace(getContext(), null);
        } else {
            ((FtRoomVM) this.mViewModel).setCurPlace(getContext(), ((FtRoomVM) this.mViewModel).checkPlace(list));
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
    public /* synthetic */ void lambda$startObserve$5(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoom.this.lambda$startObserve$4((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(List list) {
        ((FtRoomVM) this.mViewModel).placeInfoResult.setValue(((FtRoomVM) this.mViewModel).getCurPlace());
        Place place = ((FtRoomVM) this.mViewModel).lastPlace;
        if (place == null || place.getPlaceId() != ((FtRoomVM) this.mViewModel).getCurPlace().getPlaceId()) {
            ((FtRoomVM) this.mViewModel).lastPlace = ((FtRoomVM) this.mViewModel).getCurPlace();
            ((FtRoomVM) this.mViewModel).isFirst = true;
        }
        this.needRefreshMesh = true;
        Injection.repo().mode().getAllModeFromNet(this);
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
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtRoom.this.lambda$startObserve$7((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(List list) {
        ((FtRoomVM) this.mViewModel).mFloorList = list;
        if (list.isEmpty()) {
            ((FtRoomVM) this.mViewModel).setCurFloor(null);
            if (((FtRoomVM) this.mViewModel).isRefresh) {
                setRoomView(new ArrayList(), this.showAllDev);
                return;
            }
            return;
        }
        ((FtRoomVM) this.mViewModel).setCurFloor(((FtRoomVM) this.mViewModel).checkFloor(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(List list) {
        setRoomView(list, this.showAllDev);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$10(Place place) {
        if (place == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().scene().getSceneList(this, place.getPlaceId(), true).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(List list) {
        ((FtRoomVM) this.mViewModel).commonSceneResult.setValue(Resource.success(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Void r1) {
        showPlaceDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Void r1) {
        showFloorPopup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(Boolean bool) {
        ((FtRoomBinding) this.mViewBinding).viewpager.setUserInputEnabled(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$16(Void r12) {
        if (((FtRoomVM) this.mViewModel).mRoomList == null || ((FtRoomVM) this.mViewModel).mRoomList.isEmpty() || ((FtRoomVM) this.mViewModel).mRoomList.get(0).getRoomId() == 0) {
            return;
        }
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(((FtRoomVM) this.mViewModel).getCurPlace().getPlaceId(), 0L, -1L);
        if (!Injection.repo().group().getGroupListByRoomIdFromDb(((FtRoomVM) this.mViewModel).getCurPlace().getPlaceId(), 0L, -1L).isEmpty()) {
            ((FtRoomVM) this.mViewModel).mRoomList.add(0, new FtRoomVM.RoomItem(((FtRoomVM) this.mViewModel).getCurPlace().getPlaceId(), 0L, -1, getString(R.string.app_str_not_distribution)));
            initViewPager(true);
        } else {
            if (deviceListByRoomIdFromDb.isEmpty()) {
                return;
            }
            Iterator<Device> it = deviceListByRoomIdFromDb.iterator();
            while (it.hasNext()) {
                if (((FtRoomVM) this.mViewModel).filterDevice(it.next())) {
                    ((FtRoomVM) this.mViewModel).mRoomList.add(0, new FtRoomVM.RoomItem(((FtRoomVM) this.mViewModel).getCurPlace().getPlaceId(), 0L, -1, getString(R.string.app_str_not_distribution)));
                    initViewPager(true);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$17(Boolean bool) {
        this.editMode = bool.booleanValue();
        if (bool.booleanValue()) {
            ((FtRoomVM) this.mViewModel).selectAllText.setValue(getString(R.string.select_all_device));
            ((FtRoomVM) this.mViewModel).selectNumber.setValue(getString(R.string.already_select, 0));
        }
        showEditMode(bool);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$18(Boolean bool) {
        this.editMode = bool.booleanValue();
        if (bool.booleanValue()) {
            ((FtRoomVM) this.mViewModel).selectAllText.setValue(getString(R.string.select_all_scene));
            ((FtRoomVM) this.mViewModel).selectNumber.setValue(getString(R.string.already_select, 0));
        }
        showEditMode(bool);
    }

    private void showEditMode(Boolean aBoolean) {
        ((FtRoomBinding) this.mViewBinding).refreshLayout.setEnableRefresh(!aBoolean.booleanValue());
        ((FtRoomBinding) this.mViewBinding).viewpager.setUserInputEnabled(!aBoolean.booleanValue());
        ((FtRoomBinding) this.mViewBinding).tvSelectDone.setVisibility(aBoolean.booleanValue() ? 0 : 8);
        ((FtRoomBinding) this.mViewBinding).centerSelect.setVisibility(aBoolean.booleanValue() ? 0 : 8);
        ((FtRoomBinding) this.mViewBinding).appCompatImageView2.setVisibility(aBoolean.booleanValue() ? 8 : 0);
        ((FtRoomBinding) this.mViewBinding).appCompatTextView4.setVisibility(aBoolean.booleanValue() ? 8 : 0);
        ((FtRoomBinding) this.mViewBinding).appCompatTextView5.setVisibility(aBoolean.booleanValue() ? 8 : 0);
        ((FtRoomBinding) this.mViewBinding).searchDevice.setVisibility(aBoolean.booleanValue() ? 8 : 0);
        ((FtRoomBinding) this.mViewBinding).addDevice.setVisibility(aBoolean.booleanValue() ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMeshNetwork() {
        if (((FtRoomVM) this.mViewModel).getCurPlace() != null) {
            MeshJsonBean meshJsonBean = FeasyMeshNetHelper.getMeshJsonBean(((FtRoomVM) this.mViewModel).getCurPlace(), ((FtRoomVM) this.mViewModel).getCurPlace().getNetKey(), ((FtRoomVM) this.mViewModel).getCurPlace().getAppKey(), ((FtRoomVM) this.mViewModel).getCurPlace().getProvisionerAddress());
            Injection.mesh().refreshNetwork(GsonUtils.getGson().toJson(meshJsonBean), new AnonymousClass7(meshJsonBean));
        }
    }

    /* renamed from: com.ltech.smarthome.ui.control.FtRoom$7, reason: invalid class name */
    class AnonymousClass7 implements IRefreshNetworkCallback {
        final /* synthetic */ MeshJsonBean val$bean;

        AnonymousClass7(final MeshJsonBean val$bean) {
            this.val$bean = val$bean;
        }

        @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
        public void onRefreshSuccess() {
            Injection.mesh().setIvIndex(((FtRoomVM) FtRoom.this.mViewModel).getCurPlace().getIvindex(), new IAction<Integer>() { // from class: com.ltech.smarthome.ui.control.FtRoom.7.1
                @Override // com.ltech.smarthome.base.IAction
                public void act(final Integer integer) {
                    ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtRoom.7.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ((FtRoomVM) FtRoom.this.mViewModel).updateIvIndex(integer.intValue());
                        }
                    }, 5000L);
                    ThreadUtils.runOnUiThread(new Runnable(this) { // from class: com.ltech.smarthome.ui.control.FtRoom.7.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                        }
                    });
                }
            });
            FtRoom.this.retryTimes = 3;
            Bundle bundle = new Bundle();
            bundle.putString("meshUUid", this.val$bean.getMeshUUID());
            Message message = new Message();
            message.what = 1;
            message.setData(bundle);
            ((FtRoomVM) FtRoom.this.mViewModel).isFirstLoading = false;
            if (((LifecycleOwner) ActivityUtils.getTopActivity()) == null) {
                FtRoom.this.handler.removeMessages(1);
                FtRoom.this.handler.sendMessageDelayed(message, 1000L);
            } else {
                FtRoom.this.handler.removeMessages(1);
                FtRoom.this.handler.sendMessageDelayed(message, 2000L);
            }
        }

        @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
        public void onRefreshFail() {
            if (FtRoom.this.retryTimes > 0) {
                FtRoom ftRoom = FtRoom.this;
                ftRoom.retryTimes--;
                FtRoom.this.refreshMeshNetwork();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subscribeDevice() {
        ((ObservableSubscribeProxy) Injection.net().subscribeDevice().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoom.lambda$subscribeDevice$19(obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.control.FtRoom.8
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
            }
        });
    }

    private void showPlaceDialog() {
        this.listDialog = ListDialog.asTop(R.layout.item_go_2);
        ArrayList arrayList = new ArrayList();
        for (final Place place : ((FtRoomVM) this.mViewModel).mPlaceList) {
            arrayList.add(new GoItem().setImageRes(place.equals(((FtRoomVM) this.mViewModel).getCurPlace()) ? R.mipmap.ic_family_sel : R.mipmap.ic_family).setMainText(place.getPlaceName()).setSelect(place.equals(((FtRoomVM) this.mViewModel).getCurPlace())).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda17
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    FtRoom.this.lambda$showPlaceDialog$20(place);
                }
            })));
        }
        this.listDialog.setBottomItem(new GoItem().setImageRes(R.mipmap.ic_setting).setMainText(getString(R.string.home_manage)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtRoom.this.lambda$showPlaceDialog$21();
            }
        })));
        this.listDialog.setItemList(arrayList);
        this.listDialog.showDialog(getChildFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlaceDialog$20(Place place) {
        if (!place.equals(((FtRoomVM) this.mViewModel).getCurPlace())) {
            Injection.mesh().disconnect();
            showLoadingDialog(getString(R.string.loading), 30000);
            SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
            showEmpty();
            this.currentPosition = 0;
            this.floorChange = true;
            ((FtRoomVM) this.mViewModel).setCurPlace(getContext(), place);
            Injection.repo().device().resetDeviceOnlineState(place.getPlaceId());
            initDca();
            initIntercom();
        }
        dismissListDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlaceDialog$21() {
        NavUtils.destination(ActHomeManage.class).navigation(this);
        dismissListDialog();
    }

    private void showFloorPopup() {
        ArrayList arrayList = new ArrayList();
        for (Floor floor : ((FtRoomVM) this.mViewModel).mFloorList) {
            arrayList.add(new GoItem().setMainText(floor.getFloorName()).setSelect(floor.equals(((FtRoomVM) this.mViewModel).getCurFloor())));
        }
        final FloorPopup apply = FloorPopup.create(getActivity()).setData(arrayList).apply();
        apply.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda16
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                FtRoom.this.lambda$showFloorPopup$22(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((FtRoomBinding) this.mViewBinding).appCompatTextView5, 2, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$22(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!((FtRoomVM) this.mViewModel).mFloorList.get(i).equals(((FtRoomVM) this.mViewModel).getCurFloor())) {
            this.floorChange = true;
            ((FtRoomVM) this.mViewModel).setCurFloor(((FtRoomVM) this.mViewModel).mFloorList.get(i));
        }
        floorPopup.dismiss();
    }

    private void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtRoom$$ExternalSyntheticLambda11
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtRoom.lambda$showNoPermissionDialog$23(baseDialog, view);
            }
        });
    }

    private void dismissListDialog() {
        ListDialog listDialog = this.listDialog;
        if (listDialog != null) {
            listDialog.dismissDialog();
            this.listDialog = null;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
        ((FtRoomVM) this.mViewModel).request.retry();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    private void setRoomView(List<Room> rooms, boolean showAllDev) {
        if (this.mViewModel == 0 || ((FtRoomVM) this.mViewModel).getCurPlace() == null) {
            return;
        }
        boolean initRoomList = ((FtRoomVM) this.mViewModel).initRoomList(getContext(), rooms, showAllDev);
        LHomeLog.i(getClass(), "setRoomView  rooms =" + rooms.size() + "+ showAllDev=" + showAllDev + "__" + ((FtRoomVM) this.mViewModel).getCurPlace().getPlaceName() + "floorChange=" + this.floorChange + " roomChange=" + initRoomList);
        int i = 0;
        boolean z = this.floorChange || initRoomList;
        if (z) {
            ((FtRoomVM) this.mViewModel).roleResult = null;
        }
        initViewPager(z);
        this.floorChange = false;
        if (this.actHome != null) {
            this.currentPosition = 0;
            while (true) {
                if (i >= ((FtRoomVM) this.mViewModel).mRoomList.size()) {
                    break;
                }
                if (this.actHome.getSelectRoomId() == ((FtRoomVM) this.mViewModel).mRoomList.get(i).getRoomId()) {
                    this.currentPosition = i;
                    break;
                }
                i++;
            }
        }
        if (this.mViewBinding != 0) {
            ((FtRoomBinding) this.mViewBinding).viewpager.setCurrentItem(this.currentPosition);
            TabLayout.Tab tabAt = ((FtRoomBinding) this.mViewBinding).tabs.getTabAt(this.currentPosition);
            if (tabAt != null) {
                tabAt.select();
            }
        }
        if (z) {
            return;
        }
        if (this.mViewModel != 0 && ((FtRoomVM) this.mViewModel).isRefresh && ((FtRoomVM) this.mViewModel).roleResult != null) {
            ((FtRoomVM) this.mViewModel).loadData();
            ((FtRoomVM) this.mViewModel).roleResult.observe(this, new Observer<Resource<List<Role>>>() { // from class: com.ltech.smarthome.ui.control.FtRoom.9
                @Override // androidx.lifecycle.Observer
                public void onChanged(Resource<List<Role>> listResource) {
                    if (((FtRoomVM) FtRoom.this.mViewModel).isRefresh) {
                        ((FtRoomVM) FtRoom.this.mViewModel).isRefresh = false;
                        ((FtRoomVM) FtRoom.this.mViewModel).starPool();
                    }
                }
            });
        } else if (SharedPreferenceUtil.queryBooleanValue(Constants.DEVICE_CHANGED)) {
            ((FtRoomVM) this.mViewModel).loadData();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(this.mBroadcastReceiver);
        }
        super.onDestroy();
        this.handler.removeCallbacks(null);
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        ((FtRoomVM) this.mViewModel).request.refresh();
    }

    private void initDca() {
        Injection.dca().init(this);
        Injection.dca().setAuthCallBack(null);
    }

    private void initIntercom() {
        Injection.intercom().init(this, getActivity());
        Injection.intercom().login();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (Injection.mesh().getConnectedDevice() != null && ((FtRoomVM) this.mViewModel).needCheckDeviceStatus) {
            ((FtRoomVM) this.mViewModel).starPool();
        }
        ((FtRoomVM) this.mViewModel).scanEvent.call();
        if (((FtRoomVM) this.mViewModel).editRoleMode.getValue().booleanValue()) {
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtRoom.10
                @Override // java.lang.Runnable
                public void run() {
                    ((FtRoomVM) FtRoom.this.mViewModel).editRoleMode.setValue(((FtRoomVM) FtRoom.this.mViewModel).editRoleMode.getValue());
                }
            }, 200L);
        }
        if (((FtRoomVM) this.mViewModel).editSceneMode.getValue().booleanValue()) {
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtRoom.11
                @Override // java.lang.Runnable
                public void run() {
                    ((FtRoomVM) FtRoom.this.mViewModel).editSceneMode.setValue(((FtRoomVM) FtRoom.this.mViewModel).editSceneMode.getValue());
                }
            }, 200L);
        }
        if (this.actHome == null || ((FtRoomVM) this.mViewModel).mFloorList.isEmpty()) {
            return;
        }
        ((FtRoomVM) this.mViewModel).setCurFloor(((FtRoomVM) this.mViewModel).checkFloor(((FtRoomVM) this.mViewModel).mFloorList));
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActHome) {
            this.actHome = (ActHome) context;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void handleBusEvent(LiveBusHelper helper) {
        if (helper.getCode() != 9 || (ActivityUtils.getTopActivity() instanceof ActDeviceManage)) {
            return;
        }
        ((FtRoomVM) this.mViewModel).loadData();
    }
}