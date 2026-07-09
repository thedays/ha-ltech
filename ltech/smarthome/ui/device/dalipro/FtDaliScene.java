package com.ltech.smarthome.ui.device.dalipro;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtDaliAddBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.newselect.FtRoomScene;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.ui.scene.ActSelectDaliScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.popup.FloorPopup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtDaliScene extends VMFragment<FtDaliAddBinding, FtRoomSceneVM> {
    private static FtDaliScene mFtDaliScene;
    private long deviceId;
    private Device macDevice;
    private long placeId;
    public FtRoomScene selectFt;
    public List<Floor> mFloorList = new ArrayList();
    public SingleLiveEvent<Void> refreshDataEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> firstIn = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> editClickEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> saveClickEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> refreshEdit = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectNumber = new MutableLiveData<>(0);

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int layoutLoadId() {
        return R.id.viewpager;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_dali_add;
    }

    public static FtDaliScene getObj() {
        return mFtDaliScene;
    }

    public static FtDaliScene newInstance(long deviceId) {
        FtDaliScene ftDaliScene = new FtDaliScene();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        ftDaliScene.setArguments(bundle);
        return ftDaliScene;
    }

    public static FtDaliScene newInstance(long deviceId, boolean multiSelect) {
        FtDaliScene ftDaliScene = new FtDaliScene();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putBoolean(Constants.MULTI_SELECT, multiSelect);
        ftDaliScene.setArguments(bundle);
        return ftDaliScene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.deviceId = arguments.getLong("device_id");
            this.macDevice = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
            ((FtRoomSceneVM) this.mViewModel).allowMultiSelect = arguments.getBoolean(Constants.MULTI_SELECT, false);
            if ((ActivityUtils.getTopActivity() instanceof ActSelectDaliScene) && (SceneHelper.instance().controlObject instanceof List)) {
                ((FtRoomSceneVM) this.mViewModel).selectSceneList.addAll((List) SceneHelper.instance().controlObject);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        mFtDaliScene = this;
        ((FtDaliAddBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtDaliScene.this.lambda$initView$0((View) obj);
            }
        }));
        ((FtRoomSceneVM) this.mViewModel).request = Injection.repo().scene().getSceneList(this, Injection.repo().home().getSelectPlace().getValue().getPlaceId(), 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.tv_floor_content) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mFloorList.size(); i++) {
            arrayList.add(new GoItem().setMainText(this.mFloorList.get(i).getFloorName()).setSelect(this.mFloorList.get(i).equals(((FtRoomSceneVM) this.mViewModel).getCurFloor())));
        }
        showFloorPopup(arrayList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtRoomSceneVM) this.mViewModel).placeId.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliScene.this.lambda$startObserve$1((Long) obj);
            }
        });
        ((FtRoomSceneVM) this.mViewModel).selectFloor.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliScene.this.lambda$startObserve$2((Floor) obj);
            }
        });
        this.refreshDataEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliScene.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((FtRoomSceneVM) this.mViewModel).selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliScene.this.lambda$startObserve$4((Integer) obj);
            }
        });
        this.editClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliScene.this.lambda$startObserve$5((Void) obj);
            }
        });
        this.saveClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliScene.this.lambda$startObserve$7((Void) obj);
            }
        });
        handleData(((FtRoomSceneVM) this.mViewModel).request, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliScene.this.lambda$startObserve$8((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Long l) {
        this.placeId = l.longValue();
        ((FtRoomSceneVM) this.mViewModel).initFloorList(this.placeId);
        this.mFloorList.clear();
        this.mFloorList.addAll(((FtRoomSceneVM) this.mViewModel).floors);
        FtRoomSceneVM ftRoomSceneVM = (FtRoomSceneVM) this.mViewModel;
        FtRoomSceneVM ftRoomSceneVM2 = (FtRoomSceneVM) this.mViewModel;
        List<Floor> list = ((FtRoomSceneVM) this.mViewModel).floors;
        Device device = this.macDevice;
        ftRoomSceneVM.setCurFloor(ftRoomSceneVM2.checkFloor(list, Constants.USER_CUR_FLOOR_FOR_DALI_ADD, device == null ? 0L : device.getFloorId()));
        this.firstIn.call();
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
            if (((FtRoomSceneVM) this.mViewModel).hasUnConfigScene(0L, -1L, this.deviceId)) {
                floorListByPlaceId.add(0, floor2);
            }
        } else {
            Room room = new Room();
            room.setRoomName(getString(R.string.type_all));
            room.setRoomId(-1L);
            room.setFloorId(((FtRoomSceneVM) this.mViewModel).getCurFloor().getFloorId());
            room.setPlaceId(this.placeId);
            Room room2 = new Room();
            room2.setRoomName(getString(R.string.app_str_not_distribution));
            room2.setRoomId(0L);
            room2.setFloorId(0L);
            room2.setPlaceId(this.placeId);
            if (((FtRoomSceneVM) this.mViewModel).hasUnConfigScene(room2.getFloorId(), room2.getRoomId(), this.deviceId)) {
                roomListByFloorId.add(0, room2);
            }
            FtRoomSceneVM ftRoomSceneVM = (FtRoomSceneVM) this.mViewModel;
            FtRoomSceneVM ftRoomSceneVM2 = (FtRoomSceneVM) this.mViewModel;
            Device device = this.macDevice;
            ftRoomSceneVM.roomPosition = ftRoomSceneVM2.getRoomIndex(roomListByFloorId, device != null ? device.getRoomId() : 0L);
        }
        setRoomView(roomListByFloorId, floorListByPlaceId);
        ((FtRoomSceneVM) this.mViewModel).isFirst = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r3) {
        ((FtRoomSceneVM) this.mViewModel).placeId.setValue(Long.valueOf(Injection.repo().home().getSelectPlace().getValue().getPlaceId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        ((FtRoomSceneVM) this.mViewModel).selectAll = true;
        if (((FtRoomSceneVM) this.mViewModel).allSceneData.size() > 0) {
            Iterator<Scene> it = ((FtRoomSceneVM) this.mViewModel).allSceneData.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (!((FtRoomSceneVM) this.mViewModel).selectSceneList.contains(it.next())) {
                    ((FtRoomSceneVM) this.mViewModel).selectAll = false;
                    break;
                }
            }
        } else {
            ((FtRoomSceneVM) this.mViewModel).selectAll = false;
        }
        this.selectNumber.setValue(Integer.valueOf(((FtRoomSceneVM) this.mViewModel).selectSceneList.size()));
        this.refreshEdit.setValue(Boolean.valueOf(((FtRoomSceneVM) this.mViewModel).selectAll));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        ((FtRoomSceneVM) this.mViewModel).selectAll();
        this.selectFt.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r3) {
        if (SceneHelper.instance().bindingType == 0 || SceneHelper.instance().bindingType == 4 || SceneHelper.instance().bindingType == 5) {
            SceneHelper.instance().controlObject = ((FtRoomSceneVM) this.mViewModel).selectSceneList;
        } else {
            SceneHelper.instance().controlObject = ((FtRoomSceneVM) this.mViewModel).selectSceneList.get(0);
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliScene.lambda$startObserve$6((Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        ActivityUtils.getTopActivity().setResult(3001);
        ActivityUtils.getTopActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(List list) {
        this.refreshDataEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_scence));
    }

    public List<Scene> getSelectSceneList() {
        return ((FtRoomSceneVM) this.mViewModel).selectSceneList;
    }

    private void setRoomView(List<Room> rooms, List<Floor> floorList) {
        if (this.mViewModel == 0) {
            return;
        }
        ((FtRoomSceneVM) this.mViewModel).initRoomList(rooms, floorList, this.deviceId);
        initViewPager();
        if (this.mViewBinding != 0) {
            ((FtDaliAddBinding) this.mViewBinding).viewpager.setCurrentItem(((FtRoomSceneVM) this.mViewModel).roomPosition, false);
            if (((FtRoomSceneVM) this.mViewModel).mRoomList.size() > ((FtRoomSceneVM) this.mViewModel).roomPosition) {
                FtRoomScene ftScene = ((FtRoomSceneVM) this.mViewModel).mRoomList.get(((FtRoomSceneVM) this.mViewModel).roomPosition).getFtScene();
                this.selectFt = ftScene;
                ftScene.getDaliData();
            }
        }
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        if (this.mViewBinding == 0 || ((FtRoomSceneVM) this.mViewModel).mRoomList.size() <= ((FtRoomSceneVM) this.mViewModel).roomPosition) {
            return;
        }
        FtRoomScene ftScene = ((FtRoomSceneVM) this.mViewModel).mRoomList.get(((FtRoomSceneVM) this.mViewModel).roomPosition).getFtScene();
        this.selectFt = ftScene;
        ftScene.getDaliData();
    }

    private void initViewPager() {
        ((FtDaliAddBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtRoomSceneVM) FtDaliScene.this.mViewModel).mRoomList.get(position).getFtScene();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtRoomSceneVM) FtDaliScene.this.mViewModel).mRoomList.size();
            }
        });
        ((FtDaliAddBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((FtDaliAddBinding) this.mViewBinding).tabs, ((FtDaliAddBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda1
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                FtDaliScene.this.lambda$initViewPager$9(tab, i);
            }
        }).attach();
        ((FtDaliAddBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                ((FtRoomSceneVM) FtDaliScene.this.mViewModel).roomPosition = position;
                FtDaliScene ftDaliScene = FtDaliScene.this;
                ftDaliScene.selectFt = ((FtRoomSceneVM) ftDaliScene.mViewModel).mRoomList.get(position).getFtScene();
                FtDaliScene.this.selectFt.getDaliData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$9(TabLayout.Tab tab, int i) {
        tab.setText(((FtRoomSceneVM) this.mViewModel).mRoomList.get(i).getRoomName());
    }

    private void showFloorPopup(List<GoItem> itemList) {
        final FloorPopup apply = FloorPopup.create(getActivity()).setData(itemList).apply();
        apply.setArrowLeft().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliScene$$ExternalSyntheticLambda9
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                FtDaliScene.this.lambda$showFloorPopup$10(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((FtDaliAddBinding) this.mViewBinding).tvFloorContent, 2, 2, ErrorConstant.ERROR_NO_NETWORK, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$10(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!this.mFloorList.get(i).equals(((FtRoomSceneVM) this.mViewModel).getCurFloor())) {
            ((FtRoomSceneVM) this.mViewModel).floorPosition = i;
            ((FtRoomSceneVM) this.mViewModel).setCurFloor(this.mFloorList.get(i));
        }
        floorPopup.dismiss();
    }
}