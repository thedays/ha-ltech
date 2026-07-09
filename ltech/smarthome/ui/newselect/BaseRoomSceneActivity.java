package com.ltech.smarthome.ui.newselect;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import anet.channel.util.ErrorConstant;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.popup.FloorPopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseRoomSceneActivity<V extends ActSelectSceneBinding, VM extends FtRoomSceneVM> extends VMActivity<V, VM> implements TextWatcher {
    protected static int TYPE_ALL = 3;
    protected static int TYPE_CLOUD = 1;
    protected static int TYPE_DALI = 4;
    protected static int TYPE_LOCAL = 2;
    public boolean allowMultiSelect;
    protected boolean isLocalAutomation;
    public long placeId;
    public int selMax;
    protected boolean selectAll;
    public FtRoomScene selectFt;
    protected int selectSceneType;
    public List<Floor> mFloorList = new ArrayList();
    protected int selectSortType = 0;
    public List<Scene> allSceneData = new ArrayList();
    public List<Scene> selectSceneList = new ArrayList();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>();
    public int listType = 1;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected void changeSelectCount(int selectCount) {
    }

    protected boolean filterScene(Scene scene) {
        return true;
    }

    protected int getSceneType() {
        return 3;
    }

    protected boolean isMatter() {
        return false;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_scene;
    }

    protected void sceneClick(Scene scene) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.selMax = getIntent().getIntExtra(Constants.MAX, -1);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.isLocalAutomation = getIntent().getBooleanExtra(Constants.IS_LOCAL_AUTOMATION, false);
        ((FtRoomSceneVM) this.mViewModel).initFloorList(this.placeId);
        if (((FtRoomSceneVM) this.mViewModel).floors.get(0).getFloorId() == -1) {
            ((ActSelectSceneBinding) this.mViewBinding).layoutFloor.setVisibility(8);
        } else {
            ((ActSelectSceneBinding) this.mViewBinding).layoutFloor.setVisibility(0);
            this.mFloorList.addAll(((FtRoomSceneVM) this.mViewModel).floors);
        }
        int intExtra = getIntent().getIntExtra(Constants.SCENE_TYPE, -1);
        if (intExtra == -1) {
            this.selectSceneType = getSceneType();
        } else {
            this.selectSceneType = intExtra;
        }
        int i = this.selectSceneType;
        int i2 = R.string.select_scene;
        if (i == 1 || i == 2) {
            if (i == 2) {
                i2 = R.string.select_local_scene;
            }
            setTitle(getString(i2));
            ((ActSelectSceneBinding) this.mViewBinding).layoutSort.setVisibility(0);
            ((ActSelectSceneBinding) this.mViewBinding).layoutSortAndType.setVisibility(8);
        } else {
            setTitle(getString(R.string.select_scene));
            ((ActSelectSceneBinding) this.mViewBinding).layoutSort.setVisibility(8);
            ((ActSelectSceneBinding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        }
        ((ActSelectSceneBinding) this.mViewBinding).radioSelectType.check(R.id.radio_all);
        if (this.isLocalAutomation) {
            ((ActSelectSceneBinding) this.mViewBinding).radioCloud.setVisibility(8);
        }
        ((ActSelectSceneBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                BaseRoomSceneActivity.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActSelectSceneBinding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).setTitleGone(true);
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).layoutSearch.setVisibility(0);
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
            }
        });
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).setTitleGone(false);
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).layoutSearch.setVisibility(8);
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSelectSceneBinding) this.mViewBinding).radioSelectType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.4
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_local) {
                    BaseRoomSceneActivity.this.selectSceneType = BaseRoomSceneActivity.TYPE_LOCAL;
                } else if (checkedId == R.id.radio_cloud) {
                    BaseRoomSceneActivity.this.selectSceneType = BaseRoomSceneActivity.TYPE_CLOUD;
                } else if (checkedId == R.id.radio_dali) {
                    BaseRoomSceneActivity.this.selectSceneType = BaseRoomSceneActivity.TYPE_DALI;
                } else {
                    BaseRoomSceneActivity.this.selectSceneType = BaseRoomSceneActivity.TYPE_ALL;
                }
                if (BaseRoomSceneActivity.this.selectFt != null) {
                    BaseRoomSceneActivity.this.selectFt.getData(((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
                }
            }
        });
        showSortText(this.selectSortType);
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
                    arrayList.add(new GoItem().setMainText(this.mFloorList.get(i).getFloorName()).setSelect(this.mFloorList.get(i).equals(((FtRoomSceneVM) this.mViewModel).getCurFloor())));
                }
                showFloorPopup(arrayList);
                break;
            case R.id.tv_sort /* 2131298982 */:
            case R.id.tv_sort_1 /* 2131298983 */:
                showSortDialog();
                break;
        }
    }

    protected void clickBack() {
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        initFloorRoom();
        ((FtRoomSceneVM) this.mViewModel).placeId.setValue(Long.valueOf(this.placeId));
        ((FtRoomSceneVM) this.mViewModel).placeId.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseRoomSceneActivity.this.lambda$startObserve$1((Long) obj);
            }
        });
        ((FtRoomSceneVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                ((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).tvFloorContent.setText(floor.getFloorName());
                if (((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).needFloorRoomMemory) {
                    SPUtils.getInstance().put(Constants.USER_CUR_FLOOR_FOR_SCENE, floor.getFloorId());
                }
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(BaseRoomSceneActivity.this.placeId, floor.getFloorId());
                ArrayList arrayList = new ArrayList(((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).floors);
                if (floor.getFloorId() == -1) {
                    Floor floor2 = new Floor();
                    floor2.setFloorName(BaseRoomSceneActivity.this.getString(R.string.app_str_not_distribution));
                    floor2.setFloorId(0L);
                    floor2.setPlaceId(BaseRoomSceneActivity.this.placeId);
                    if (((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).hasUnConfigScene(0L, -1L)) {
                        arrayList.add(0, floor2);
                    }
                    ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).roomPosition = BaseRoomSceneActivity.this.getFloorIndex(arrayList);
                } else {
                    Room room = new Room();
                    room.setRoomName(BaseRoomSceneActivity.this.getString(R.string.type_all));
                    room.setRoomId(-1L);
                    room.setFloorId(((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).getCurFloor().getFloorId());
                    room.setPlaceId(BaseRoomSceneActivity.this.placeId);
                    roomListByFloorId.add(0, room);
                    Room room2 = new Room();
                    room2.setRoomName(BaseRoomSceneActivity.this.getString(R.string.app_str_not_distribution));
                    room2.setRoomId(0L);
                    room2.setFloorId(0L);
                    room2.setPlaceId(BaseRoomSceneActivity.this.placeId);
                    if (((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).hasUnConfigScene(room2.getFloorId(), room2.getRoomId())) {
                        roomListByFloorId.add(0, room2);
                    }
                    ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).roomPosition = BaseRoomSceneActivity.this.getRoomIndex(roomListByFloorId);
                }
                BaseRoomSceneActivity.this.setRoomView(roomListByFloorId, arrayList);
                ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).isFirst = false;
            }
        });
        this.selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseRoomSceneActivity.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Long l) {
        ((FtRoomSceneVM) this.mViewModel).setCurFloor(((FtRoomSceneVM) this.mViewModel).checkFloor(((FtRoomSceneVM) this.mViewModel).floors, Constants.USER_CUR_FLOOR_FOR_SCENE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        this.selectAll = true;
        if (this.allSceneData.size() > 0) {
            Iterator<Scene> it = this.allSceneData.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (!this.selectSceneList.contains(it.next())) {
                    this.selectAll = false;
                    break;
                }
            }
        } else {
            this.selectAll = false;
        }
        if (!TextUtils.isEmpty(getTitleBar().getEditString()) && this.listType != 3) {
            if (this.selectAll) {
                setEditString(getString(R.string.app_str_cancel_select_all));
            } else {
                setEditString(getString(R.string.app_str_select_all));
            }
        }
        changeSelectCount(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        selectAll();
    }

    private void initFloorRoom() {
        long longExtra = getIntent().getLongExtra(Constants.FLOOR_ID, -2L);
        long longExtra2 = getIntent().getLongExtra(Constants.ROOM_ID, -2L);
        if (longExtra != -2 && longExtra2 != -2) {
            ((FtRoomSceneVM) this.mViewModel).orgFloorId = longExtra;
            ((FtRoomSceneVM) this.mViewModel).orgRoomId = longExtra2;
            return;
        }
        long longExtra3 = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (longExtra3 != -1) {
            Role roleById = Injection.repo().role().getRoleById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L), booleanExtra);
            ((FtRoomSceneVM) this.mViewModel).orgFloorId = roleById.getFloorId();
            ((FtRoomSceneVM) this.mViewModel).orgRoomId = roleById.getRoomId();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRoomView(List<Room> rooms, List<Floor> floorList) {
        if (this.mViewModel == 0) {
            return;
        }
        ((FtRoomSceneVM) this.mViewModel).initRoomList(rooms, floorList);
        initViewPager();
        if (this.mViewBinding != 0) {
            ((ActSelectSceneBinding) this.mViewBinding).viewpager.setCurrentItem(((FtRoomSceneVM) this.mViewModel).roomPosition, false);
            TabLayout.Tab tabAt = ((ActSelectSceneBinding) this.mViewBinding).tabs.getTabAt(((FtRoomSceneVM) this.mViewModel).roomPosition);
            if (tabAt != null) {
                tabAt.select();
            }
        }
    }

    private void initViewPager() {
        ((ActSelectSceneBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.6
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).mRoomList.get(position).getFtScene();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).mRoomList.size();
            }
        });
        ((ActSelectSceneBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(-1);
        new TabLayoutMediator(((ActSelectSceneBinding) this.mViewBinding).tabs, ((ActSelectSceneBinding) this.mViewBinding).viewpager, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity$$ExternalSyntheticLambda5
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                BaseRoomSceneActivity.this.lambda$initViewPager$3(tab, i);
            }
        }).attach();
        ((ActSelectSceneBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity.7
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).roomPosition = position;
                BaseRoomSceneActivity baseRoomSceneActivity = BaseRoomSceneActivity.this;
                baseRoomSceneActivity.selectFt = ((FtRoomSceneVM) baseRoomSceneActivity.mViewModel).mRoomList.get(position).getFtScene();
                BaseRoomSceneActivity.this.selectFt.getData(((ActSelectSceneBinding) BaseRoomSceneActivity.this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
                if (((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).needFloorRoomMemory) {
                    if (((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).getCurFloor().getFloorId() == -1) {
                        SPUtils.getInstance().put(Constants.USER_CUR_ROOM_FOR_SCENE, ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).mRoomList.get(position).getFloorId());
                    } else {
                        SPUtils.getInstance().put(Constants.USER_CUR_ROOM_FOR_SCENE, ((FtRoomSceneVM) BaseRoomSceneActivity.this.mViewModel).mRoomList.get(position).getRoomId());
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViewPager$3(TabLayout.Tab tab, int i) {
        tab.setText(((FtRoomSceneVM) this.mViewModel).mRoomList.get(i).getRoomName());
    }

    public List<Scene> getSceneList() {
        return this.selectFt.sceneList;
    }

    private void showFloorPopup(List<GoItem> itemList) {
        final FloorPopup apply = FloorPopup.create(this).setData(itemList).apply();
        apply.setArrowLeft().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRoomSceneActivity.this.lambda$showFloorPopup$4(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((ActSelectSceneBinding) this.mViewBinding).tvFloorContent, 2, 2, ErrorConstant.ERROR_NO_NETWORK, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFloorPopup$4(FloorPopup floorPopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!this.mFloorList.get(i).equals(((FtRoomSceneVM) this.mViewModel).getCurFloor())) {
            ((FtRoomSceneVM) this.mViewModel).floorPosition = i;
            ((FtRoomSceneVM) this.mViewModel).setCurFloor(this.mFloorList.get(i));
        }
        floorPopup.dismiss();
    }

    private void selectAll() {
        if (this.allSceneData.isEmpty()) {
            return;
        }
        if (!this.selectAll) {
            Iterator<Scene> it = this.allSceneData.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Scene next = it.next();
                if (this.selMax > 0 && this.selectSceneList.size() >= this.selMax) {
                    SmartToast.showCenterShort(String.format(getString(R.string.app_str_gqpro_syning_num_tip), Integer.valueOf(this.selMax), getString(R.string.app_scene)));
                    break;
                } else if (!this.selectSceneList.contains(next)) {
                    this.selectSceneList.add(next);
                }
            }
        } else {
            Iterator<Scene> it2 = this.allSceneData.iterator();
            while (it2.hasNext()) {
                this.selectSceneList.remove(it2.next());
            }
        }
        this.selectFt.refreshList.call();
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectSceneList.size()));
    }

    private void showSortText(int type) {
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
        ((ActSelectSceneBinding) this.mViewBinding).tvSort.setCompoundDrawables(drawable, null, null, null);
        ((ActSelectSceneBinding) this.mViewBinding).tvSort1.setCompoundDrawables(drawable, null, null, null);
        ((ActSelectSceneBinding) this.mViewBinding).tvSort1.setText(i2);
    }

    protected void showSortDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.mipmap.ic_time_down));
        arrayList.add(Integer.valueOf(R.mipmap.ic_time_up));
        arrayList.add(Integer.valueOf(R.mipmap.ic_name_down));
        arrayList.add(Integer.valueOf(R.mipmap.ic_name_up));
        final List<String> asList = Arrays.asList(NameRepository.getSortSceneType(this));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.app_sort_type)).setSelectPosition(this.selectSortType).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseRoomSceneActivity.this.lambda$showSortDialog$5(asList, (Integer) obj);
            }
        }).setSelectList(asList).setSelectDrawableList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSortDialog$5(List list, Integer num) {
        if (this.selectSortType != num.intValue()) {
            this.selectSortType = num.intValue();
            ((ActSelectSceneBinding) this.mViewBinding).tvSort.setText((CharSequence) list.get(num.intValue()));
            showSortText(this.selectSortType);
            ((FtRoomSceneVM) this.mViewModel).mRoomList.get(((FtRoomSceneVM) this.mViewModel).roomPosition).getFtScene().sortType.setValue(Integer.valueOf(this.selectSortType));
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        FtRoomScene ftRoomScene = this.selectFt;
        if (ftRoomScene != null) {
            ftRoomScene.getData(editable.toString());
        }
    }

    protected int getFloorIndex(List<Floor> floorList) {
        if (((FtRoomSceneVM) this.mViewModel).needFloorRoomMemory && ((FtRoomSceneVM) this.mViewModel).isFirst) {
            long j = ((FtRoomSceneVM) this.mViewModel).orgRoomId;
            if (j != -2) {
                for (int i = 0; i < floorList.size(); i++) {
                    if (floorList.get(i).getFloorId() == j) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    protected int getRoomIndex(List<Room> roomList) {
        if (((FtRoomSceneVM) this.mViewModel).needFloorRoomMemory && ((FtRoomSceneVM) this.mViewModel).isFirst) {
            long j = ((FtRoomSceneVM) this.mViewModel).orgRoomId;
            if (j != -2) {
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getRoomId() == j) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    protected boolean filterDaliScene(Scene scene) {
        return scene.getDaliHidden() == 0;
    }
}