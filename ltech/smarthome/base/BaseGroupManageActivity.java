package com.ltech.smarthome.base;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseGroupManageVM;
import com.ltech.smarthome.databinding.ActGroupManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public abstract class BaseGroupManageActivity<V extends ActGroupManageBinding, VM extends BaseGroupManageVM> extends VMActivity<V, VM> implements TextWatcher {
    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected boolean filterDevice(Device device) {
        return true;
    }

    protected boolean filterGroup(Group group) {
        return true;
    }

    protected void getDevices() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_group_manage;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((BaseGroupManageVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        setBackImage(R.mipmap.icon_back);
        ((ActGroupManageBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActGroupManageBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseGroupManageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActGroupManageBinding) BaseGroupManageActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        handleData(Transformations.switchMap(((BaseGroupManageVM) this.mViewModel).selectRoom, new Function1() { // from class: com.ltech.smarthome.base.BaseGroupManageActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = BaseGroupManageActivity.this.lambda$startObserve$0((Room) obj);
                return lambda$startObserve$0;
            }
        }), new IAction() { // from class: com.ltech.smarthome.base.BaseGroupManageActivity$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseGroupManageActivity.this.lambda$startObserve$1((List) obj);
            }
        });
        ((BaseGroupManageVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.base.BaseGroupManageActivity.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).placeId, floor.getFloorId());
                if (((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(BaseGroupManageActivity.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).mRoomList = roomListByFloorId;
                ((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).setCurRoom(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).checkRoom(roomListByFloorId));
                BaseGroupManageActivity.this.initRoomSpinner();
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(((BaseGroupManageVM) this.mViewModel).placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(((BaseGroupManageVM) this.mViewModel).placeId);
        floorListByPlaceId.add(0, floor);
        ((BaseGroupManageVM) this.mViewModel).mFloorList = floorListByPlaceId;
        ((BaseGroupManageVM) this.mViewModel).setCurFloor(((BaseGroupManageVM) this.mViewModel).checkFloor(floorListByPlaceId));
        initFloorSpinner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Room room) {
        if (room == null) {
            return ResourceEmptyLiveData.create();
        }
        List<Group> groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(((BaseGroupManageVM) this.mViewModel).placeId, ((BaseGroupManageVM) this.mViewModel).getCurRoom().getFloorId(), ((BaseGroupManageVM) this.mViewModel).getCurRoom().getRoomId());
        ArrayList arrayList = new ArrayList();
        for (Group group : groupListByRoomIdFromDb) {
            if (filterGroup(group)) {
                arrayList.add(group);
            }
        }
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.setValue(Resource.success(arrayList));
        return mediatorLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ((BaseGroupManageVM) this.mViewModel).mGroupList.clear();
        Collections.sort(list, new Comparator<Group>(this) { // from class: com.ltech.smarthome.base.BaseGroupManageActivity.2
            @Override // java.util.Comparator
            public int compare(Group o1, Group o2) {
                return o1.getIndex() > o2.getIndex() ? 1 : -1;
            }
        });
        ((BaseGroupManageVM) this.mViewModel).mGroupList.addAll(list);
        ((BaseGroupManageVM) this.mViewModel).allData = new ArrayList();
        ((BaseGroupManageVM) this.mViewModel).allData.addAll(((BaseGroupManageVM) this.mViewModel).mGroupList);
        ((ActGroupManageBinding) this.mViewBinding).rvContent.getAdapter().notifyDataSetChanged();
        ((BaseGroupManageVM) this.mViewModel).selectCountLiveData.setValue(Integer.valueOf(((BaseGroupManageVM) this.mViewModel).selectGroupIdList.size()));
        controlShowEmpty();
        getDevices();
        if (this.mViewBinding == 0 || ((ActGroupManageBinding) this.mViewBinding).searchBar.searchEdtTxt.getText() == null) {
            return;
        }
        String obj = ((ActGroupManageBinding) this.mViewBinding).searchBar.searchEdtTxt.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            return;
        }
        ((BaseGroupManageVM) this.mViewModel).search(obj);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((BaseGroupManageVM) this.mViewModel).mFloorList));
        ((ActGroupManageBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActGroupManageBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActGroupManageBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseGroupManageActivity.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).mFloorList.get(position).equals(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).selectFloor.getValue())) {
                    ((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).setCurFloor(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActGroupManageBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActGroupManageBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((BaseGroupManageVM) this.mViewModel).mRoomList));
        ((ActGroupManageBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseGroupManageActivity.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).mRoomList.get(position).equals(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).selectRoom.getValue())) {
                    ((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).setCurRoom(((BaseGroupManageVM) BaseGroupManageActivity.this.mViewModel).mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActGroupManageBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActGroupManageBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActGroupManageBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActGroupManageBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((BaseGroupManageVM) this.mViewModel).placeInfoResult.retry();
    }

    protected void controlShowEmpty() {
        if (((BaseGroupManageVM) this.mViewModel).mGroupList.isEmpty() || Injection.repo().device().getSuperPanel() == null) {
            showEmpty();
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ((BaseGroupManageVM) this.mViewModel).search(editable.toString());
    }
}