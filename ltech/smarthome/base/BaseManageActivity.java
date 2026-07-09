package com.ltech.smarthome.base;

import android.content.Intent;
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
import com.ltech.smarthome.base.BaseManageVM;
import com.ltech.smarthome.databinding.ActDeviceGroupManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public abstract class BaseManageActivity<V extends ActDeviceGroupManageBinding, VM extends BaseManageVM> extends VMActivity<V, VM> implements TextWatcher {
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
        return R.layout.act_device_group_manage;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void showError() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((BaseManageVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        setBackImage(R.mipmap.icon_back);
        ((ActDeviceGroupManageBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActDeviceGroupManageBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseManageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActDeviceGroupManageBinding) BaseManageActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        handleData(Transformations.switchMap(((BaseManageVM) this.mViewModel).selectRoom, new Function1<Room, LiveData<Resource<List<Role>>>>() { // from class: com.ltech.smarthome.base.BaseManageActivity.2
            @Override // kotlin.jvm.functions.Function1
            public LiveData<Resource<List<Role>>> invoke(Room room) {
                if (room == null) {
                    return ResourceEmptyLiveData.create();
                }
                ArrayList arrayList = new ArrayList();
                List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(((BaseManageVM) BaseManageActivity.this.mViewModel).placeId, ((BaseManageVM) BaseManageActivity.this.mViewModel).getCurRoom().getFloorId(), ((BaseManageVM) BaseManageActivity.this.mViewModel).getCurRoom().getRoomId());
                List<Group> groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(((BaseManageVM) BaseManageActivity.this.mViewModel).placeId, ((BaseManageVM) BaseManageActivity.this.mViewModel).getCurRoom().getFloorId(), ((BaseManageVM) BaseManageActivity.this.mViewModel).getCurRoom().getRoomId());
                arrayList.addAll(deviceListByRoomIdFromDb);
                arrayList.addAll(groupListByRoomIdFromDb);
                Collections.sort(arrayList, new Comparator<Role>(this) { // from class: com.ltech.smarthome.base.BaseManageActivity.2.1
                    @Override // java.util.Comparator
                    public int compare(Role o1, Role o2) {
                        if (o1.getIndex() > o2.getIndex()) {
                            return 1;
                        }
                        if (o1.getIndex() < o2.getIndex()) {
                            return -1;
                        }
                        return o1.getCreatetime().compareTo(o2.getCreatetime());
                    }
                });
                MediatorLiveData mediatorLiveData = new MediatorLiveData();
                mediatorLiveData.setValue(Resource.success(arrayList));
                return mediatorLiveData;
            }
        }), new IAction() { // from class: com.ltech.smarthome.base.BaseManageActivity$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseManageActivity.this.lambda$startObserve$0((List) obj);
            }
        });
        ((BaseManageVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.base.BaseManageActivity.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((BaseManageVM) BaseManageActivity.this.mViewModel).placeId, floor.getFloorId());
                if (((BaseManageVM) BaseManageActivity.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(BaseManageActivity.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((BaseManageVM) BaseManageActivity.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((BaseManageVM) BaseManageActivity.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((BaseManageVM) BaseManageActivity.this.mViewModel).mRoomList = roomListByFloorId;
                ((BaseManageVM) BaseManageActivity.this.mViewModel).setCurRoom(((BaseManageVM) BaseManageActivity.this.mViewModel).checkRoom(roomListByFloorId));
                BaseManageActivity.this.initRoomSpinner();
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(((BaseManageVM) this.mViewModel).placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(((BaseManageVM) this.mViewModel).placeId);
        floorListByPlaceId.add(0, floor);
        ((BaseManageVM) this.mViewModel).mFloorList = floorListByPlaceId;
        ((BaseManageVM) this.mViewModel).setCurFloor(((BaseManageVM) this.mViewModel).checkFloor(floorListByPlaceId));
        initFloorSpinner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        ((BaseManageVM) this.mViewModel).mRoleList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Role role = (Role) it.next();
            if (role instanceof Device) {
                if (filterDevice((Device) role)) {
                    ((BaseManageVM) this.mViewModel).mRoleList.add(role);
                }
            } else if ((role instanceof Group) && filterGroup((Group) role)) {
                ((BaseManageVM) this.mViewModel).mRoleList.add(role);
            }
        }
        ((BaseManageVM) this.mViewModel).allData = new ArrayList();
        ((BaseManageVM) this.mViewModel).allData.addAll(((BaseManageVM) this.mViewModel).mRoleList);
        controlShowEmpty();
        getDevices();
        if (this.mViewBinding == 0 || ((ActDeviceGroupManageBinding) this.mViewBinding).searchBar.searchEdtTxt.getText() == null) {
            return;
        }
        String obj = ((ActDeviceGroupManageBinding) this.mViewBinding).searchBar.searchEdtTxt.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            return;
        }
        ((BaseManageVM) this.mViewModel).search(obj);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((BaseManageVM) this.mViewModel).mFloorList));
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseManageActivity.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((BaseManageVM) BaseManageActivity.this.mViewModel).mFloorList.get(position).equals(((BaseManageVM) BaseManageActivity.this.mViewModel).selectFloor.getValue())) {
                    ((BaseManageVM) BaseManageActivity.this.mViewModel).setCurFloor(((BaseManageVM) BaseManageActivity.this.mViewModel).mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActDeviceGroupManageBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((BaseManageVM) this.mViewModel).mRoomList));
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseManageActivity.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((BaseManageVM) BaseManageActivity.this.mViewModel).mRoomList.get(position).equals(((BaseManageVM) BaseManageActivity.this.mViewModel).selectRoom.getValue())) {
                    ((BaseManageVM) BaseManageActivity.this.mViewModel).setCurRoom(((BaseManageVM) BaseManageActivity.this.mViewModel).mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActDeviceGroupManageBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActDeviceGroupManageBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((BaseManageVM) this.mViewModel).placeInfoResult.retry();
    }

    protected void controlShowEmpty() {
        if (((BaseManageVM) this.mViewModel).mRoleList.isEmpty() || Injection.repo().device().getSuperPanel() == null) {
            showEmpty();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((BaseManageVM) this.mViewModel).setCurRoom(((BaseManageVM) this.mViewModel).checkRoom(((BaseManageVM) this.mViewModel).mRoomList));
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ((BaseManageVM) this.mViewModel).search(editable.toString());
    }
}