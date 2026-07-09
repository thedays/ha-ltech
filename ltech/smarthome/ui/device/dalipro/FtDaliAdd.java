package com.ltech.smarthome.ui.device.dalipro;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import anet.channel.util.ErrorConstant;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtDaliAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ActSelectCgdProDaliAdd;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.popup.FloorPopup;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtDaliAdd extends VMFragment<FtDaliAddBinding, FtDaliAddVM> {
    private static FtDaliAdd mFtDaliAdd;
    public long deviceId;
    private boolean hasSaveScene;
    private Device macDevice;
    private long placeId;
    private long sceneId;
    public FtRoomDaliAdd selectFt;
    public List<Floor> mFloorList = new ArrayList();
    public SingleLiveEvent<Void> editClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> saveClickEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> refreshEdit = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectNumber = new MutableLiveData<>(0);

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_dali_add;
    }

    public static FtDaliAdd getObj() {
        return mFtDaliAdd;
    }

    public static FtDaliAdd newInstance(long deviceId) {
        FtDaliAdd ftDaliAdd = new FtDaliAdd();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        ftDaliAdd.setArguments(bundle);
        return ftDaliAdd;
    }

    public static FtDaliAdd newInstance(long sceneId, int listType) {
        FtDaliAdd ftDaliAdd = new FtDaliAdd();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.SCENE_ID, sceneId);
        bundle.putInt(Constants.LIST_TYPE, listType);
        ftDaliAdd.setArguments(bundle);
        return ftDaliAdd;
    }

    public static FtDaliAdd newInstance(long deviceId, int listType, int showType) {
        FtDaliAdd ftDaliAdd = new FtDaliAdd();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putInt(Constants.LIST_TYPE, listType);
        bundle.putInt(Constants.SHOW_TYPE, showType);
        ftDaliAdd.setArguments(bundle);
        return ftDaliAdd;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.deviceId = arguments.getLong("device_id");
            this.macDevice = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
            this.sceneId = arguments.getLong(Constants.SCENE_ID);
            ((FtDaliAddVM) this.mViewModel).listType = arguments.getInt(Constants.LIST_TYPE, 1);
            ((FtDaliAddVM) this.mViewModel).showType = arguments.getInt(Constants.SHOW_TYPE, 0);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        mFtDaliAdd = this;
        ((FtDaliAddBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtDaliAdd.this.lambda$initView$0((View) obj);
            }
        }));
        ((FtDaliAddBinding) this.mViewBinding).tvFloorContent.setText(getString(R.string.all_floor));
        ((FtDaliAddVM) this.mViewModel).needFloorRoomMemory = false;
        if (((FtDaliAddVM) this.mViewModel).listType == 2) {
            ((FtDaliAddVM) this.mViewModel).scene = Injection.repo().scene().getSceneBySceneId(this.sceneId);
            this.deviceId = ((FtDaliAddVM) this.mViewModel).scene.getMacdeviceid();
            if (((FtDaliAddVM) this.mViewModel).scene.getSceneContents() != null) {
                for (Scene.SceneContent sceneContent : ((FtDaliAddVM) this.mViewModel).scene.getSceneContents()) {
                    if (MaskType.isDeviceAction(sceneContent.getActionType())) {
                        LocalDeviceParam localDeviceParam = (LocalDeviceParam) sceneContent.getExecuteCommand(LocalDeviceParam.class);
                        ((FtDaliAddVM) this.mViewModel).actionMap.put(Long.valueOf(localDeviceParam.deviceid), localDeviceParam);
                        ((FtDaliAddVM) this.mViewModel).selectDeviceIds.add(Long.valueOf(localDeviceParam.deviceid));
                    }
                }
            }
            ((FtDaliAddVM) this.mViewModel).selectCountLiveData.setValue(Integer.valueOf(((FtDaliAddVM) this.mViewModel).selectDeviceIds.size()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.tv_floor_content) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mFloorList.size(); i++) {
            arrayList.add(new GoItem().setMainText(this.mFloorList.get(i).getFloorName()).setSelect(this.mFloorList.get(i).equals(((FtDaliAddVM) this.mViewModel).getCurFloor())));
        }
        showFloorPopup(arrayList);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (((FtDaliAddVM) this.mViewModel).mDisposable != null) {
            ((FtDaliAddVM) this.mViewModel).mDisposable.dispose();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtDaliAddVM) this.mViewModel).placeId.setValue(Long.valueOf(Injection.repo().home().getSelectPlace().getValue().getPlaceId()));
        ((FtDaliAddVM) this.mViewModel).placeId.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$1((Long) obj);
            }
        });
        ((FtDaliAddVM) this.mViewModel).selectFloor.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$2((Floor) obj);
            }
        });
        this.editClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((FtDaliAddVM) this.mViewModel).selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$4((Integer) obj);
            }
        });
        this.saveClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((FtDaliAddVM) this.mViewModel).saveFinishEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$6((Boolean) obj);
            }
        });
        ((FtDaliAddVM) this.mViewModel).refreshPageEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliAdd.this.lambda$startObserve$7((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Long l) {
        this.placeId = l.longValue();
        ((FtDaliAddVM) this.mViewModel).initFloorList(this.placeId);
        this.mFloorList.clear();
        this.mFloorList.addAll(((FtDaliAddVM) this.mViewModel).floors);
        FtDaliAddVM ftDaliAddVM = (FtDaliAddVM) this.mViewModel;
        FtDaliAddVM ftDaliAddVM2 = (FtDaliAddVM) this.mViewModel;
        List<Floor> list = ((FtDaliAddVM) this.mViewModel).floors;
        Device device = this.macDevice;
        ftDaliAddVM.setCurFloor(ftDaliAddVM2.checkFloor(list, "", device == null ? 0L : device.getFloorId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Floor floor) {
        ((FtDaliAddBinding) this.mViewBinding).tvFloorContent.setText(floor.getFloorName());
        List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(this.placeId, floor.getFloorId());
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        if (floor.getFloorId() == -1) {
            Floor floor2 = new Floor();
            floor2.setFloorName(getString(R.string.app_str_not_distribution));
            floor2.setFloorId(0L);
            floor2.setPlaceId(this.placeId);
            if (((FtDaliAddVM) this.mViewModel).hasUnConfigRole(0L, -1L, this.deviceId)) {
                floorListByPlaceId.add(0, floor2);
            }
        } else {
            Room room = new Room();
            room.setRoomName(getString(R.string.type_all));
            room.setRoomId(-1L);
            room.setFloorId(((FtDaliAddVM) this.mViewModel).getCurFloor().getFloorId());
            room.setPlaceId(this.placeId);
            Room room2 = new Room();
            room2.setRoomName(getString(R.string.app_str_not_distribution));
            room2.setRoomId(0L);
            room2.setFloorId(0L);
            room2.setPlaceId(this.placeId);
            if (((FtDaliAddVM) this.mViewModel).hasUnConfigRole(room2.getFloorId(), room2.getRoomId(), this.deviceId)) {
                roomListByFloorId.add(0, room2);
            }
            if (this.sceneId > 0) {
                Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(this.sceneId);
                if (sceneBySceneId != null) {
                    ((FtDaliAddVM) this.mViewModel).roomPosition = ((FtDaliAddVM) this.mViewModel).getRoomIndex(roomListByFloorId, sceneBySceneId.getRoomId());
                }
            } else if (this.macDevice != null) {
                ((FtDaliAddVM) this.mViewModel).controlDevice.setValue(this.macDevice);
                ((FtDaliAddVM) this.mViewModel).roomPosition = ((FtDaliAddVM) this.mViewModel).getRoomIndex(roomListByFloorId, this.macDevice.getRoomId());
            }
        }
        setRoomView(roomListByFloorId, floorListByPlaceId);
        ((FtDaliAddVM) this.mViewModel).isFirst = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        ((FtDaliAddVM) this.mViewModel).selectAll();
        this.selectFt.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        ((FtDaliAddVM) this.mViewModel).selectAll = true;
        if (((FtDaliAddVM) this.mViewModel).allRoleData.size() > 0) {
            Iterator<Role> it = ((FtDaliAddVM) this.mViewModel).allRoleData.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Role next = it.next();
                if (!((FtDaliAddVM) this.mViewModel).selectDeviceIds.contains(Long.valueOf(next.getObjectId())) && !((FtDaliAddVM) this.mViewModel).selectGroupIds.contains(Long.valueOf(next.getObjectId()))) {
                    ((FtDaliAddVM) this.mViewModel).selectAll = false;
                    break;
                }
            }
        } else {
            ((FtDaliAddVM) this.mViewModel).selectAll = false;
        }
        if (((FtDaliAddVM) this.mViewModel).listType == 2) {
            this.selectNumber.setValue(Integer.valueOf(((FtDaliAddVM) this.mViewModel).selectDeviceIds.size()));
        } else {
            this.selectNumber.setValue(num);
        }
        this.refreshEdit.setValue(Boolean.valueOf(((FtDaliAddVM) this.mViewModel).selectAll));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r6) {
        if (((FtDaliAddVM) this.mViewModel).listType == 2) {
            if (((FtDaliAddVM) this.mViewModel).actionMap.size() > 0) {
                ((FtDaliAddVM) this.mViewModel).batchControl(getContext());
            }
        } else if (((FtDaliAddVM) this.mViewModel).listType == 3) {
            ArrayList arrayList = new ArrayList();
            if (((FtDaliAddVM) this.mViewModel).selectGroupIds.size() > 0 || ((FtDaliAddVM) this.mViewModel).selectDeviceIds.size() > 0) {
                for (Role role : ((FtDaliAddVM) this.mViewModel).allRoleData) {
                    if (((FtDaliAddVM) this.mViewModel).selectGroupIds.contains(Long.valueOf(role.getObjectId())) || ((FtDaliAddVM) this.mViewModel).selectDeviceIds.contains(Long.valueOf(role.getObjectId()))) {
                        arrayList.add(role);
                    }
                }
            }
            SceneHelper.instance().controlObject = arrayList;
            requireActivity().setResult(3001);
            ((ActSelectCgdProDaliAdd) requireActivity()).finishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        if (bool.booleanValue()) {
            updateSceneContent(DaliProHelper.convert2DaliSceneContent(((FtDaliAddVM) this.mViewModel).actionMap));
        } else {
            showErrorDialog(getString(R.string.save_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        refreshData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        FtRoomDaliAdd ftRoomDaliAdd = this.selectFt;
        if (ftRoomDaliAdd != null) {
            ftRoomDaliAdd.getData();
        }
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        FtRoomDaliAdd ftRoomDaliAdd = this.selectFt;
        if (ftRoomDaliAdd != null) {
            ftRoomDaliAdd.getData();
        }
    }

    private void setRoomView(List<Room> rooms, List<Floor> floorList) {
        if (this.mViewModel == 0) {
            return;
        }
        ((FtDaliAddVM) this.mViewModel).initRoomList(rooms, floorList, this.deviceId);
        initViewPager();
        if (this.mViewBinding != 0) {
            ((FtDaliAddBinding) this.mViewBinding).viewpager.setCurrentItem(((FtDaliAddVM) this.mViewModel).roomPosition, false);
            if (((FtDaliAddVM) this.mViewModel).mRoomList.size() > ((FtDaliAddVM) this.mViewModel).roomPosition) {
                FtRoomDaliAdd ftDaliAdd = ((FtDaliAddVM) this.mViewModel).mRoomList.get(((FtDaliAddVM) this.mViewModel).roomPosition).getFtDaliAdd();
                this.selectFt = ftDaliAdd;
                ftDaliAdd.getData();
            }
        }
    }

    private void initViewPager() {
        ((FtDaliAddBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtDaliAddVM) FtDaliAdd.this.mViewModel).mRoomList.get(position).getFtDaliAdd();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtDaliAddVM) FtDaliAdd.this.mViewModel).mRoomList.size();
            }
        });
        ((FtDaliAddBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((FtDaliAddBinding) this.mViewBinding).tabs, ((FtDaliAddBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                FtDaliAdd.this.lambda$initViewPager$8(tab, i);
            }
        }).attach();
        ((FtDaliAddBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                ((FtDaliAddVM) FtDaliAdd.this.mViewModel).roomPosition = position;
                FtDaliAdd ftDaliAdd = FtDaliAdd.this;
                ftDaliAdd.selectFt = ((FtDaliAddVM) ftDaliAdd.mViewModel).mRoomList.get(position).getFtDaliAdd();
                FtDaliAdd.this.selectFt.getData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$8(TabLayout.Tab tab, int i) {
        tab.setText(((FtDaliAddVM) this.mViewModel).mRoomList.get(i).getRoomName());
    }

    private void updateSceneContent(final List<Scene.SceneContent> sceneContentList) {
        ((ObservableSubscribeProxy) Injection.net().updateSceneContent(this.sceneId, sceneContentList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtDaliAdd.this.lambda$updateSceneContent$9(sceneContentList, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSceneContent$9(List list, Object obj) throws Exception {
        Injection.repo().scene().updateSceneContent(this.sceneId, list);
        this.hasSaveScene = true;
        requireActivity().setResult(3001);
        ((ActEditDaliScene) requireActivity()).finishActivity();
    }

    private void showFloorPopup(List<GoItem> itemList) {
        final FloorPopup apply = FloorPopup.create(getActivity()).setData(itemList).apply();
        apply.setArrowLeft().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAdd$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                FtDaliAdd.this.lambda$showFloorPopup$10(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((FtDaliAddBinding) this.mViewBinding).tvFloorContent, 2, 2, ErrorConstant.ERROR_NO_NETWORK, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$10(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!this.mFloorList.get(i).equals(((FtDaliAddVM) this.mViewModel).getCurFloor())) {
            ((FtDaliAddVM) this.mViewModel).floorPosition = i;
            ((FtDaliAddVM) this.mViewModel).setCurFloor(this.mFloorList.get(i));
        }
        floorPopup.dismiss();
    }

    public boolean checkActionChange(Map<Long, LocalDeviceParam> lastActionMap) {
        if (this.hasSaveScene) {
            return false;
        }
        Iterator<Long> it = ((FtDaliAddVM) this.mViewModel).actionMap.keySet().iterator();
        while (it.hasNext()) {
            if (!((FtDaliAddVM) this.mViewModel).selectDeviceIds.contains(it.next())) {
                it.remove();
            }
        }
        if (((FtDaliAddVM) this.mViewModel).actionMap.size() != lastActionMap.size()) {
            return true;
        }
        if (((FtDaliAddVM) this.mViewModel).actionMap.size() == 0) {
            return false;
        }
        for (Long l : ((FtDaliAddVM) this.mViewModel).actionMap.keySet()) {
            l.longValue();
            LocalDeviceParam localDeviceParam = lastActionMap.get(l);
            LocalDeviceParam localDeviceParam2 = ((FtDaliAddVM) this.mViewModel).actionMap.get(l);
            if (localDeviceParam == null || !localDeviceParam.instruct.equals(localDeviceParam2.instruct)) {
                return true;
            }
        }
        return false;
    }
}