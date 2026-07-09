package com.ltech.smarthome.ui.newselect;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import anet.channel.util.ErrorConstant;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.home.ActHideDeviceManagerNew;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.InOrOutManager;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.MultiSortTypeDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.popup.FloorPopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseRoomDeviceGroupActivity<V extends ActSelect3Binding, VM extends FtDeviceGroupVM> extends VMActivity<V, VM> implements TextWatcher {
    protected static int TYPE_ALL = 0;
    protected static int TYPE_LIGHT = 3;
    protected static int TYPE_OTHER = 1;
    protected static int TYPE_PANEL = 2;
    protected static int TYPE_SCENE_ALL = 3;
    protected static int TYPE_SCENE_CLOUD = 1;
    protected static int TYPE_SCENE_DALI = 4;
    protected static int TYPE_SCENE_LOCAL = 2;
    public List<Floor> floors;
    public int matterRealSelectedNum;
    protected long placeId;
    public int selMax;
    protected boolean selectAll;
    protected int selectDeviceType;
    public FtDeviceGroup selectFt;
    protected int selectSceneType;
    public int syncTotalNum;
    public List<Floor> mFloorList = new ArrayList();
    protected int selectSortType = 0;
    protected int selectSortSceneType = 0;
    public List<Integer> multiSortType = new ArrayList();
    public List<Role> allRoleData = new ArrayList();
    public List<Long> selectRoleIds = new ArrayList();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>();
    public int listType = 1;
    public boolean includeScene = false;
    public boolean useLayoutTabTitle = false;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected boolean canSelected(Role role) {
        return true;
    }

    protected void changeSelectCount(int selectCount) {
    }

    protected boolean checkSceneType(Scene scene) {
        return true;
    }

    protected void convertView(BaseViewHolder helper, Role item) {
    }

    protected void deviceClick(Device device) {
    }

    protected boolean filterDevice(Device device) {
        return true;
    }

    protected boolean filterGroup(Group group) {
        return true;
    }

    protected boolean filterScene(Scene scene) {
        return false;
    }

    protected void groupClick(Group group) {
    }

    protected void initData() {
    }

    protected boolean isMatter() {
        return false;
    }

    protected int itemLayout() {
        return R.layout.item_select_device_with_place;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select3;
    }

    protected void roleClick(Role role) {
    }

    protected void syncLocalScene(Device item) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.selMax = getIntent().getIntExtra(Constants.MAX, -1);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.floors = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        ((FtDeviceGroupVM) this.mViewModel).floorList.addAll(this.floors);
        ((FtDeviceGroupVM) this.mViewModel).roomList = Injection.repo().home().getRoomList(this.placeId);
        if (!((FtDeviceGroupVM) this.mViewModel).showUnconfigRoom) {
            ((FtDeviceGroupVM) this.mViewModel).placeId.setValue(Long.valueOf(this.placeId));
        }
        ((ActSelect3Binding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                BaseRoomDeviceGroupActivity.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).setTitleGone(true);
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).layoutSearch.setVisibility(0);
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
            }
        });
        ((ActSelect3Binding) this.mViewBinding).ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).setTitleGone(true);
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).layoutSearch.setVisibility(0);
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
                if (BaseRoomDeviceGroupActivity.this.useLayoutTabTitle) {
                    ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).layoutTab.setVisibility(8);
                }
            }
        });
        ((ActSelect3Binding) this.mViewBinding).searchBar.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).setTitleGone(Boolean.valueOf(BaseRoomDeviceGroupActivity.this.useLayoutTabTitle));
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).layoutSearch.setVisibility(8);
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
                if (BaseRoomDeviceGroupActivity.this.useLayoutTabTitle) {
                    ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).layoutTab.setVisibility(0);
                }
            }
        });
        ((ActSelect3Binding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        ((ActSelect3Binding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSelect3Binding) this.mViewBinding).radioSelectType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.5
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_other) {
                    BaseRoomDeviceGroupActivity.this.selectDeviceType = BaseRoomDeviceGroupActivity.TYPE_OTHER;
                } else if (checkedId == R.id.radio_panel) {
                    BaseRoomDeviceGroupActivity.this.selectDeviceType = BaseRoomDeviceGroupActivity.TYPE_PANEL;
                } else if (checkedId == R.id.radio_light) {
                    BaseRoomDeviceGroupActivity.this.selectDeviceType = BaseRoomDeviceGroupActivity.TYPE_LIGHT;
                } else {
                    BaseRoomDeviceGroupActivity.this.selectDeviceType = BaseRoomDeviceGroupActivity.TYPE_ALL;
                }
                if (((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).needFloorRoomMemory) {
                    SPUtils.getInstance().put(Constants.USER_CUR_DEVICE_TYPE, BaseRoomDeviceGroupActivity.this.selectDeviceType);
                }
                if (BaseRoomDeviceGroupActivity.this.selectFt != null) {
                    BaseRoomDeviceGroupActivity.this.selectFt.getData(((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
                }
            }
        });
        showSortText(this.selectSortType);
        ((ActSelect3Binding) this.mViewBinding).radioSceneSelectType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.6
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_local) {
                    BaseRoomDeviceGroupActivity.this.selectSceneType = BaseRoomDeviceGroupActivity.TYPE_SCENE_LOCAL;
                } else if (checkedId == R.id.radio_cloud) {
                    BaseRoomDeviceGroupActivity.this.selectSceneType = BaseRoomDeviceGroupActivity.TYPE_SCENE_CLOUD;
                } else if (checkedId == R.id.radio_dali) {
                    BaseRoomDeviceGroupActivity.this.selectSceneType = BaseRoomDeviceGroupActivity.TYPE_SCENE_DALI;
                } else {
                    BaseRoomDeviceGroupActivity.this.selectSceneType = BaseRoomDeviceGroupActivity.TYPE_SCENE_ALL;
                }
                if (BaseRoomDeviceGroupActivity.this.selectFt != null) {
                    BaseRoomDeviceGroupActivity.this.selectFt.getData(((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
                }
            }
        });
        ((ActSelect3Binding) this.mViewBinding).radioSceneSelectType.check(R.id.radio_scene_all);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.iv_back /* 2131296947 */:
                clickBack();
                break;
            case R.id.tv_edit /* 2131298613 */:
                edit();
                break;
            case R.id.tv_floor_content /* 2131298660 */:
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < this.mFloorList.size(); i++) {
                    arrayList.add(new GoItem().setMainText(this.mFloorList.get(i).getFloorName()).setSelect(this.mFloorList.get(i).equals(((FtDeviceGroupVM) this.mViewModel).getCurFloor())));
                }
                showFloorPopup(arrayList);
                break;
            case R.id.tv_scene_sort_1 /* 2131298938 */:
                showSortSceneDialog();
                break;
            case R.id.tv_sort /* 2131298982 */:
                if (ActivityUtils.getTopActivity() instanceof ActHideDeviceManagerNew) {
                    showSortDeviceDialog(true);
                    break;
                } else {
                    showSortDeviceDialog(false);
                    break;
                }
            case R.id.tv_sort_1 /* 2131298983 */:
                showSortDeviceDialog(true);
                break;
        }
    }

    protected void clickBack() {
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        initDeviceType();
        initFloorRoom();
        ((FtDeviceGroupVM) this.mViewModel).placeId.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseRoomDeviceGroupActivity.this.lambda$startObserve$1((Long) obj);
            }
        });
        ((FtDeviceGroupVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                ((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).tvFloorContent.setText(floor.getFloorName());
                if (((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).needFloorRoomMemory) {
                    SPUtils.getInstance().put(Constants.USER_CUR_FLOOR, floor.getFloorId());
                }
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).getCurPlaceId(), floor.getFloorId());
                ArrayList arrayList = new ArrayList(BaseRoomDeviceGroupActivity.this.floors);
                if (floor.getFloorId() == -1) {
                    Floor floor2 = new Floor();
                    floor2.setFloorName(BaseRoomDeviceGroupActivity.this.getString(R.string.app_str_not_distribution));
                    floor2.setFloorId(0L);
                    floor2.setPlaceId(BaseRoomDeviceGroupActivity.this.placeId);
                    if (BaseRoomDeviceGroupActivity.this.hasUnConfigRole(0L, -1L)) {
                        arrayList.add(0, floor2);
                    }
                    ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).roomPosition = BaseRoomDeviceGroupActivity.this.getFloorIndex(arrayList);
                } else {
                    Room room = new Room();
                    room.setRoomName(BaseRoomDeviceGroupActivity.this.getString(R.string.type_all));
                    room.setRoomId(-1L);
                    room.setFloorId(((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).getCurFloor().getFloorId());
                    room.setPlaceId(BaseRoomDeviceGroupActivity.this.placeId);
                    roomListByFloorId.add(0, room);
                    Room room2 = new Room();
                    room2.setRoomName(BaseRoomDeviceGroupActivity.this.getString(R.string.app_str_not_distribution));
                    room2.setRoomId(0L);
                    room2.setFloorId(0L);
                    room2.setPlaceId(BaseRoomDeviceGroupActivity.this.placeId);
                    if (BaseRoomDeviceGroupActivity.this.hasUnConfigRole(room2.getFloorId(), room2.getRoomId())) {
                        roomListByFloorId.add(0, room2);
                    }
                    ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).roomPosition = BaseRoomDeviceGroupActivity.this.getRoomIndex(roomListByFloorId);
                }
                BaseRoomDeviceGroupActivity.this.setRoomView(roomListByFloorId, arrayList);
                ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).isFirst = false;
            }
        });
        this.selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseRoomDeviceGroupActivity.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Long l) {
        if (this.floors.size() == 0) {
            Floor floor = new Floor();
            floor.setFloorName(getString(R.string.all_floor));
            floor.setFloorId(-1L);
            floor.setPlaceId(this.placeId);
            this.floors.add(0, floor);
        }
        if (this.floors.get(0).getFloorId() == -1) {
            ((ActSelect3Binding) this.mViewBinding).layoutFloor.setVisibility(8);
        } else {
            ((ActSelect3Binding) this.mViewBinding).layoutFloor.setVisibility(0);
        }
        this.mFloorList.clear();
        this.mFloorList.addAll(this.floors);
        ((FtDeviceGroupVM) this.mViewModel).setCurFloor(((FtDeviceGroupVM) this.mViewModel).checkFloor(this.floors));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        this.selectAll = false;
        int size = this.allRoleData.size();
        int i = this.selMax;
        if (i > 0 && i < this.allRoleData.size()) {
            size = this.selMax;
        }
        if (this.selectRoleIds.size() >= size) {
            this.selectAll = true;
        }
        if (getString(R.string.app_str_cancel_select_all).equals(getTitleBar().getEditString()) || getString(R.string.app_str_select_all).equals(getTitleBar().getEditString())) {
            if (this.selectAll) {
                setEditString(getString(R.string.app_str_cancel_select_all));
            } else {
                setEditString(getString(R.string.app_str_select_all));
            }
        }
        changeSelectCount(num.intValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        selectAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRoomView(List<Room> rooms, List<Floor> floorList) {
        if (this.mViewModel == 0) {
            return;
        }
        ((FtDeviceGroupVM) this.mViewModel).initRoomList(rooms, floorList);
        initViewPager();
        if (this.mViewBinding != 0) {
            ((ActSelect3Binding) this.mViewBinding).viewpager.setCurrentItem(((FtDeviceGroupVM) this.mViewModel).roomPosition, false);
            TabLayout.Tab tabAt = ((ActSelect3Binding) this.mViewBinding).tabs.getTabAt(((FtDeviceGroupVM) this.mViewModel).roomPosition);
            if (tabAt != null) {
                tabAt.select();
            }
        }
    }

    private void initViewPager() {
        ((ActSelect3Binding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.8
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).mRoomList.get(position).getFtDevice();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).mRoomList.size();
            }
        });
        ((ActSelect3Binding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((ActSelect3Binding) this.mViewBinding).tabs, ((ActSelect3Binding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda6
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                BaseRoomDeviceGroupActivity.this.lambda$initViewPager$3(tab, i);
            }
        }).attach();
        ((ActSelect3Binding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.9
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).roomPosition = position;
                BaseRoomDeviceGroupActivity baseRoomDeviceGroupActivity = BaseRoomDeviceGroupActivity.this;
                baseRoomDeviceGroupActivity.selectFt = ((FtDeviceGroupVM) baseRoomDeviceGroupActivity.mViewModel).mRoomList.get(position).getFtDevice();
                BaseRoomDeviceGroupActivity.this.selectFt.getData(((ActSelect3Binding) BaseRoomDeviceGroupActivity.this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
                if (((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).needFloorRoomMemory) {
                    if (((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).getCurFloor().getFloorId() == -1) {
                        SPUtils.getInstance().put(Constants.USER_CUR_ROOM, ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).mRoomList.get(position).getFloorId());
                    } else {
                        SPUtils.getInstance().put(Constants.USER_CUR_ROOM, ((FtDeviceGroupVM) BaseRoomDeviceGroupActivity.this.mViewModel).mRoomList.get(position).getRoomId());
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$3(TabLayout.Tab tab, int i) {
        tab.setText(((FtDeviceGroupVM) this.mViewModel).mRoomList.get(i).getRoomName());
    }

    private void initFloorRoom() {
        long longExtra = getIntent().getLongExtra(Constants.FLOOR_ID, -2L);
        long longExtra2 = getIntent().getLongExtra(Constants.ROOM_ID, -2L);
        if (longExtra != -2 && longExtra2 != -2) {
            ((FtDeviceGroupVM) this.mViewModel).orgFloorId = longExtra;
            ((FtDeviceGroupVM) this.mViewModel).orgRoomId = longExtra2;
            return;
        }
        long longExtra3 = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (longExtra3 != -1) {
            Role roleById = Injection.repo().role().getRoleById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L), booleanExtra);
            ((FtDeviceGroupVM) this.mViewModel).orgFloorId = roleById.getFloorId();
            ((FtDeviceGroupVM) this.mViewModel).orgRoomId = roleById.getRoomId();
        }
    }

    private void initDeviceType() {
        if (((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory && ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.getVisibility() == 0) {
            this.selectDeviceType = SPUtils.getInstance().getInt(Constants.USER_CUR_DEVICE_TYPE);
        }
        int i = this.selectDeviceType;
        if (i == TYPE_OTHER) {
            ((ActSelect3Binding) this.mViewBinding).radioSelectType.check(R.id.radio_other);
            return;
        }
        if (i == TYPE_PANEL) {
            ((ActSelect3Binding) this.mViewBinding).radioSelectType.check(R.id.radio_panel);
        } else if (i == TYPE_LIGHT) {
            ((ActSelect3Binding) this.mViewBinding).radioSelectType.check(R.id.radio_light);
        } else {
            ((ActSelect3Binding) this.mViewBinding).radioSelectType.check(R.id.radio_all);
        }
    }

    private void showFloorPopup(List<GoItem> itemList) {
        final FloorPopup apply = FloorPopup.create(this).setData(itemList).apply();
        apply.setArrowLeft().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRoomDeviceGroupActivity.this.lambda$showFloorPopup$4(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((ActSelect3Binding) this.mViewBinding).tvFloorContent, 2, 2, ErrorConstant.ERROR_NO_NETWORK, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$4(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!this.mFloorList.get(i).equals(((FtDeviceGroupVM) this.mViewModel).getCurFloor())) {
            ((FtDeviceGroupVM) this.mViewModel).floorPosition = i;
            ((FtDeviceGroupVM) this.mViewModel).setCurFloor(this.mFloorList.get(i));
        }
        floorPopup.dismiss();
    }

    protected void selectAll() {
        if (this.allRoleData.isEmpty()) {
            return;
        }
        if (!this.selectAll) {
            Iterator<Role> it = this.allRoleData.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Role next = it.next();
                int relayCount = ProductRepository.getRelayCount(ProductRepository.getLightColorType(next));
                if (!isMatter() && this.selMax > 0 && this.selectRoleIds.size() >= this.selMax) {
                    SmartToast.showCenterShort(String.format(getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(this.selMax), getString(R.string.device)));
                    break;
                } else if (!this.selectRoleIds.contains(Long.valueOf(next.getObjectId()))) {
                    this.matterRealSelectedNum += relayCount;
                    this.selectRoleIds.add(Long.valueOf(next.getObjectId()));
                }
            }
        } else {
            for (Role role : this.allRoleData) {
                this.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
                this.matterRealSelectedNum -= ProductRepository.getRelayCount(ProductRepository.getLightColorType(role));
            }
        }
        this.selectFt.refreshList.call();
        this.selectCountLiveData.setValue(Integer.valueOf(isMatter() ? this.matterRealSelectedNum : this.selectRoleIds.size()));
    }

    public void setItemMap() {
        ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().inOrOutGroupItemMap = InOrOutManager.getInstance().getTaskMap();
    }

    public void setStateParam(FtDeviceGroupVM.StateParam stateParam) {
        ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().changeProgress(stateParam);
    }

    public void showSortText(int type) {
        int i;
        int i2 = R.string.app_sort_time;
        if (type == 1) {
            i = R.mipmap.ic_time_down;
        } else if (type == 2) {
            i = R.mipmap.ic_time_up;
        } else {
            i2 = R.string.app_sort_name;
            if (type == 3) {
                i = R.mipmap.ic_name_down;
            } else if (type == 4) {
                i = R.mipmap.ic_name_up;
            } else if (type == 5) {
                i = R.mipmap.ic_integrated;
                i2 = R.string.app_sort_integrated;
            } else {
                i = R.mipmap.ic_groups;
                i2 = R.string.group;
            }
        }
        Drawable drawable = getResources().getDrawable(i);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((ActSelect3Binding) this.mViewBinding).tvSort.setCompoundDrawables(drawable, null, null, null);
        if (this.selectSortType < 5) {
            ((ActSelect3Binding) this.mViewBinding).tvSort.setText((CharSequence) Arrays.asList(NameRepository.getSortRoleType(this)).get(type));
        } else {
            ((ActSelect3Binding) this.mViewBinding).tvSort.setText(i2);
        }
        ((ActSelect3Binding) this.mViewBinding).tvSort1.setCompoundDrawables(drawable, null, null, null);
        ((ActSelect3Binding) this.mViewBinding).tvSort1.setText(i2);
    }

    protected void setSortType(int type) {
        this.selectSortType = type;
        ((ActSelect3Binding) this.mViewBinding).tvSort.setText((CharSequence) Arrays.asList(NameRepository.getSortRoleType(this)).get(type));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    protected boolean checkDeviceType(Device device) {
        if (this.selectDeviceType == TYPE_ALL) {
            return true;
        }
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 14;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 15;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 16;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 17;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 18;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 19;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 20;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 21;
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 22;
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = 23;
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = 24;
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 25;
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 613226983:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = 27;
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = 28;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 29;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = 30;
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = 31;
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '!';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = '#';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 30:
            case 31:
            case '!':
            case '\"':
            case '#':
                if (this.selectDeviceType == TYPE_PANEL) {
                }
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 17:
            case 19:
            case 22:
            case 24:
            case 26:
            case 28:
            case ' ':
                if (this.selectDeviceType == TYPE_LIGHT) {
                }
                break;
            default:
                if (this.selectDeviceType == TYPE_OTHER) {
                }
                break;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean checkGroupType(com.ltech.smarthome.model.bean.Group r4) {
        /*
            r3 = this;
            int r0 = r3.selectDeviceType
            int r1 = com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.TYPE_ALL
            r2 = 1
            if (r0 != r1) goto L8
            return r2
        L8:
            int r4 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r4)
            r0 = 0
            if (r4 == r2) goto L35
            r1 = 2
            if (r4 == r1) goto L35
            r1 = 3
            if (r4 == r1) goto L35
            r1 = 4
            if (r4 == r1) goto L35
            r1 = 5
            if (r4 == r1) goto L35
            r1 = 101(0x65, float:1.42E-43)
            if (r4 == r1) goto L35
            switch(r4) {
                case 7: goto L35;
                case 8: goto L2d;
                case 9: goto L2d;
                case 10: goto L2d;
                case 11: goto L2d;
                default: goto L22;
            }
        L22:
            switch(r4) {
                case 18: goto L2d;
                case 19: goto L2d;
                case 20: goto L35;
                default: goto L25;
            }
        L25:
            int r4 = r3.selectDeviceType
            int r1 = com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.TYPE_OTHER
            if (r4 != r1) goto L2c
            return r2
        L2c:
            return r0
        L2d:
            int r4 = r3.selectDeviceType
            int r1 = com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.TYPE_PANEL
            if (r4 != r1) goto L34
            return r2
        L34:
            return r0
        L35:
            int r4 = r3.selectDeviceType
            int r1 = com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.TYPE_LIGHT
            if (r4 != r1) goto L3c
            return r2
        L3c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity.checkGroupType(com.ltech.smarthome.model.bean.Group):boolean");
    }

    protected void showSortDeviceDialog(boolean includeGroup) {
        MultiSortTypeDialog.asDefault(includeGroup).setTitle(getString(R.string.app_sort_type)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPositions(this.selectSortType == 5 ? this.multiSortType : new ArrayList<>(Arrays.asList(Integer.valueOf(this.selectSortType)))).setMultiSelectListener(new MultiSortTypeDialog.IMultiSelectListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.MultiSortTypeDialog.IMultiSelectListener
            public final void onSelect(List list) {
                BaseRoomDeviceGroupActivity.this.lambda$showSortDeviceDialog$5(list);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSortDeviceDialog$5(List list) {
        if (list.size() == 1) {
            this.selectSortType = ((Integer) list.get(0)).intValue();
            this.multiSortType.clear();
        } else {
            this.selectSortType = 5;
            this.multiSortType = list;
        }
        showSortText(this.selectSortType);
        ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().sortType.setValue(Integer.valueOf(this.selectSortType));
    }

    protected void showSortSceneDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.mipmap.ic_time_down));
        arrayList.add(Integer.valueOf(R.mipmap.ic_time_up));
        arrayList.add(Integer.valueOf(R.mipmap.ic_name_down));
        arrayList.add(Integer.valueOf(R.mipmap.ic_name_up));
        final List<String> asList = Arrays.asList(NameRepository.getSortSceneType(this));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.app_sort_type)).setSelectPosition(this.selectSortSceneType).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseRoomDeviceGroupActivity.this.lambda$showSortSceneDialog$6(asList, (Integer) obj);
            }
        }).setSelectList(asList).setSelectDrawableList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSortSceneDialog$6(List list, Integer num) {
        if (this.selectSortSceneType != num.intValue()) {
            this.selectSortSceneType = num.intValue();
            ((ActSelect3Binding) this.mViewBinding).tvSort.setText((CharSequence) list.get(num.intValue()));
            showSortSceneText(this.selectSortSceneType);
            ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().sortSceneType.setValue(Integer.valueOf(this.selectSortSceneType));
        }
    }

    public void showSortSceneText(int type) {
        int i;
        int i2 = R.string.app_sort_time;
        if (type == 1) {
            i = R.mipmap.ic_time_up;
        } else {
            if (type == 2) {
                i = R.mipmap.ic_name_down;
            } else if (type == 3) {
                i = R.mipmap.ic_name_up;
            } else {
                i = R.mipmap.ic_time_down;
            }
            i2 = R.string.app_sort_name;
        }
        Drawable drawable = getResources().getDrawable(i);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((ActSelect3Binding) this.mViewBinding).tvSort.setCompoundDrawables(drawable, null, null, null);
        ((ActSelect3Binding) this.mViewBinding).tvSceneSort1.setCompoundDrawables(drawable, null, null, null);
        ((ActSelect3Binding) this.mViewBinding).tvSceneSort1.setText(i2);
    }

    public List<Role> getRoleList() {
        return this.selectFt.roleList;
    }

    public boolean hasUnConfigRole(long floorId, long roomId) {
        return Injection.repo().device().getDeviceListByRoomIdFromDb(this.placeId, floorId, roomId).size() > 0 || Injection.repo().group().getGroupListByRoomIdFromDb(this.placeId, floorId, roomId).size() > 0;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        FtDeviceGroup ftDeviceGroup = this.selectFt;
        if (ftDeviceGroup != null) {
            ftDeviceGroup.getData(editable.toString());
        }
    }

    protected int getFloorIndex(List<Floor> floorList) {
        if (!((FtDeviceGroupVM) this.mViewModel).showUnconfigRoom && ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory && ((FtDeviceGroupVM) this.mViewModel).isFirst) {
            long j = ((FtDeviceGroupVM) this.mViewModel).orgRoomId;
            if (j != -2) {
                for (int i = 0; i < floorList.size(); i++) {
                    if (floorList.get(i).getFloorId() == j) {
                        ((FtDeviceGroupVM) this.mViewModel).setCurRoom(null);
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    protected int getRoomIndex(List<Room> roomList) {
        if (((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory && ((FtDeviceGroupVM) this.mViewModel).isFirst) {
            long j = ((FtDeviceGroupVM) this.mViewModel).orgRoomId;
            if (j != -2) {
                for (int i = 0; i < roomList.size(); i++) {
                    Room room = roomList.get(i);
                    if (room.getRoomId() == j) {
                        ((FtDeviceGroupVM) this.mViewModel).setCurRoom(room);
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    public MultipleItemRvAdapter<Role, BaseViewHolder> getAdapter() {
        return this.selectFt.getAdapter();
    }

    protected boolean filterDaliSubDeviceGroup(Role role) {
        return role.getDaliHidden() == 0;
    }
}