package com.ltech.smarthome.ui.device.super_panel;

import android.graphics.Rect;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SpinnerAdapter;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActChildMcuUpgradeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgradeVM;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActChildMcuUpgrade extends VMActivity<ActChildMcuUpgradeBinding, ActChildMcuUpgradeVM> {
    private BaseQuickAdapter<ActChildMcuUpgradeVM.NeedUpgradeDevice, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_child_mcu_upgrade;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditImage(R.mipmap.icon_refresh);
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_device_mcu_upgrade));
        initRv();
        ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((ActChildMcuUpgradeBinding) ActChildMcuUpgrade.this.mViewBinding).tvOk.getText().toString().equals(ActChildMcuUpgrade.this.getString(R.string.finish))) {
                    ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).queryNeedUpgradeList();
                    return;
                }
                if (((ActChildMcuUpgradeBinding) ActChildMcuUpgrade.this.mViewBinding).tvOk.getText().toString().equals(ActChildMcuUpgrade.this.getString(R.string.finish))) {
                    ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).send(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).panelId, ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).failIds);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                List data = ActChildMcuUpgrade.this.mAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    ActChildMcuUpgradeVM.NeedUpgradeDevice needUpgradeDevice = (ActChildMcuUpgradeVM.NeedUpgradeDevice) data.get(i);
                    if (needUpgradeDevice.isSel()) {
                        needUpgradeDevice.setNeedUpgrade(2);
                        needUpgradeDevice.setSel(false);
                        ActChildMcuUpgrade.this.mAdapter.setData(i, needUpgradeDevice);
                        arrayList.add(Long.valueOf(needUpgradeDevice.getDeviceid()));
                    }
                }
                ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).send(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).panelId, arrayList);
            }
        });
        ((ActChildMcuUpgradeBinding) this.mViewBinding).tvStop.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActChildMcuUpgrade.this.showStopDialog();
            }
        });
        ((ActChildMcuUpgradeBinding) this.mViewBinding).tvSelect.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean equals = ((ActChildMcuUpgradeBinding) ActChildMcuUpgrade.this.mViewBinding).tvSelect.getText().equals(ActChildMcuUpgrade.this.getString(R.string.app_str_select_all));
                for (int i = 0; i < ActChildMcuUpgrade.this.mAdapter.getData().size(); i++) {
                    ActChildMcuUpgradeVM.NeedUpgradeDevice needUpgradeDevice = (ActChildMcuUpgradeVM.NeedUpgradeDevice) ActChildMcuUpgrade.this.mAdapter.getData().get(i);
                    needUpgradeDevice.setSel(equals);
                    ActChildMcuUpgrade.this.mAdapter.setData(i, needUpgradeDevice);
                }
                ActChildMcuUpgrade.this.refreshUpgradeBtn();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showStopDialog() {
        MessageDialog.show(this.activity, getString(R.string.app_str_stop_upgrade), getString(R.string.app_str_device_mcu_upgrade_stop_tip)).setCancelButton(getString(R.string.cancel)).setCancelable(false).setOkButton(getString(R.string.add_cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).stop();
                return false;
            }
        });
    }

    private void initRv() {
        ((ActChildMcuUpgradeBinding) this.mViewBinding).rv.clearAnimation();
        ((ActChildMcuUpgradeBinding) this.mViewBinding).rv.setItemAnimator(null);
        ((ActChildMcuUpgradeBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActChildMcuUpgradeBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.5
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(12.0f);
            }
        });
        RecyclerView recyclerView = ((ActChildMcuUpgradeBinding) this.mViewBinding).rv;
        BaseQuickAdapter<ActChildMcuUpgradeVM.NeedUpgradeDevice, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActChildMcuUpgradeVM.NeedUpgradeDevice, BaseViewHolder>(R.layout.item_child_mcu_upgrade) { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, final ActChildMcuUpgradeVM.NeedUpgradeDevice device) {
                baseViewHolder.setText(R.id.tv_title, device.getName());
                baseViewHolder.setImageResource(R.id.iv, device.getIco());
                baseViewHolder.setVisible(R.id.cb, !((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).isUpgrading);
                baseViewHolder.setGone(R.id.iv_upgrade_waiting, false);
                baseViewHolder.setGone(R.id.iv_upgrade, false);
                if (((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).isUpgrading) {
                    if (device.getNeedUpgrade() == 2) {
                        baseViewHolder.setGone(R.id.iv_upgrade_waiting, true);
                    } else if (device.getNeedUpgrade() == 4) {
                        baseViewHolder.setGone(R.id.iv_upgrade, true).setImageResource(R.id.iv_upgrade, R.mipmap.upgrade_icon_success);
                    } else if (device.getNeedUpgrade() == 5) {
                        baseViewHolder.setGone(R.id.iv_upgrade, true).setImageResource(R.id.iv_upgrade, R.mipmap.upgrade_icon_fail);
                    }
                }
                baseViewHolder.setText(R.id.tv_sub, String.format(ActChildMcuUpgrade.this.getString(R.string.cur_version_1), device.getMcuversion()) + "\n" + String.format(ActChildMcuUpgrade.this.getString(R.string.new_version_1), device.getLastversion()));
                CheckBox checkBox = (CheckBox) baseViewHolder.getView(R.id.cb);
                checkBox.setChecked(device.isSel());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.6.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isPressed()) {
                            device.setSel(isChecked);
                            ActChildMcuUpgrade.this.refreshUpgradeBtn();
                        }
                    }
                });
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUpgradeBtn() {
        Iterator<ActChildMcuUpgradeVM.NeedUpgradeDevice> it = this.mAdapter.getData().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSel()) {
                i++;
            }
        }
        ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setText(getString(R.string.app_str_now_upgrade) + "(" + i + ")");
        if (i > 0) {
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setEnabled(true);
        } else {
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setEnabled(false);
        }
        if (!this.mAdapter.getData().isEmpty()) {
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvSelect.setVisibility(((ActChildMcuUpgradeVM) this.mViewModel).isUpgrading ? 8 : 0);
            if (i == this.mAdapter.getData().size()) {
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvSelect.setText(getString(R.string.app_str_cancel_select_all));
            } else {
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvSelect.setText(getString(R.string.app_str_select_all));
            }
        } else {
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvSelect.setVisibility(8);
        }
        ((ActChildMcuUpgradeBinding) this.mViewBinding).tvStop.setEnabled(((ActChildMcuUpgradeVM) this.mViewModel).isUpgrading);
        if (((ActChildMcuUpgradeVM) this.mViewModel).isUpgrading) {
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvStop.setVisibility(0);
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setVisibility(8);
        } else {
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setVisibility(0);
            ((ActChildMcuUpgradeBinding) this.mViewBinding).tvStop.setVisibility(8);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActChildMcuUpgradeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActChildMcuUpgradeVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        ((ActChildMcuUpgradeVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActChildMcuUpgradeVM) this.mViewModel).controlId));
        ((ActChildMcuUpgradeVM) this.mViewModel).controlDevice.observe(this, new Observer<Device>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Device device) {
                ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).panelId = device.getDeviceId();
            }
        });
        ((ActChildMcuUpgradeVM) this.mViewModel).data.observe(this, new Observer<List<ActChildMcuUpgradeVM.NeedUpgradeDevice>>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActChildMcuUpgradeVM.NeedUpgradeDevice> devices) {
                ((ActChildMcuUpgradeBinding) ActChildMcuUpgrade.this.mViewBinding).tvNum.setText(ActChildMcuUpgrade.this.getString(R.string.device) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + devices.size());
                ActChildMcuUpgrade.this.mAdapter.replaceData(devices);
                ActChildMcuUpgrade.this.refreshUpgradeBtn();
            }
        });
        ((ActChildMcuUpgradeVM) this.mViewModel).refreshBtn.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActChildMcuUpgrade.this.mAdapter.notifyDataSetChanged();
                ActChildMcuUpgrade.this.refreshUpgradeBtn();
            }
        });
        ((ActChildMcuUpgradeVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).placeId, floor.getFloorId());
                if (((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(ActChildMcuUpgrade.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).mRoomList = roomListByFloorId;
                ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).setCurRoom(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).checkRoom(roomListByFloorId));
                ActChildMcuUpgrade.this.initRoomSpinner();
            }
        });
        ((ActChildMcuUpgradeVM) this.mViewModel).selectRoom.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActChildMcuUpgrade.this.lambda$startObserve$0((Room) obj);
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(((ActChildMcuUpgradeVM) this.mViewModel).placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(((ActChildMcuUpgradeVM) this.mViewModel).placeId);
        floorListByPlaceId.add(0, floor);
        ((ActChildMcuUpgradeVM) this.mViewModel).mFloorList = floorListByPlaceId;
        ((ActChildMcuUpgradeVM) this.mViewModel).setCurFloor(((ActChildMcuUpgradeVM) this.mViewModel).checkFloor(floorListByPlaceId));
        initFloorSpinner();
        refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Room room) {
        if (((ActChildMcuUpgradeVM) this.mViewModel).getCurFloor().getFloorId() == -1 && ((ActChildMcuUpgradeVM) this.mViewModel).getCurRoom().getRoomId() == -1) {
            ((ActChildMcuUpgradeVM) this.mViewModel).data.setValue(((ActChildMcuUpgradeVM) this.mViewModel).devices);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (ActChildMcuUpgradeVM.NeedUpgradeDevice needUpgradeDevice : ((ActChildMcuUpgradeVM) this.mViewModel).devices) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(needUpgradeDevice.getDeviceid());
            if ((((ActChildMcuUpgradeVM) this.mViewModel).getCurRoom().getRoomId() == -1 && ((ActChildMcuUpgradeVM) this.mViewModel).getCurFloor().getFloorId() == deviceByDeviceId.getFloorId()) || deviceByDeviceId.getRoomId() == ((ActChildMcuUpgradeVM) this.mViewModel).getCurRoom().getRoomId()) {
                arrayList.add(needUpgradeDevice);
            }
        }
        ((ActChildMcuUpgradeVM) this.mViewModel).data.setValue(arrayList);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActChildMcuUpgradeVM) this.mViewModel).mFloorList));
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.11
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).mFloorList.get(position).equals(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).selectFloor.getValue())) {
                    ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).setCurFloor(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        int selectedItemPosition = ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition();
        if (((ActChildMcuUpgradeVM) this.mViewModel).getCurFloor() != null) {
            selectedItemPosition = ((ActChildMcuUpgradeVM) this.mViewModel).getCurFloorPos();
        }
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerFloor.setSelection(selectedItemPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((ActChildMcuUpgradeVM) this.mViewModel).mRoomList));
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActChildMcuUpgrade.12
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).mRoomList.get(position).equals(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).selectRoom.getValue())) {
                    ((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).setCurRoom(((ActChildMcuUpgradeVM) ActChildMcuUpgrade.this.mViewModel).mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        int selectedItemPosition = ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition();
        if (((ActChildMcuUpgradeVM) this.mViewModel).getCurRoom() != null) {
            selectedItemPosition = ((ActChildMcuUpgradeVM) this.mViewModel).getCurRoomPos();
        }
        ((ActChildMcuUpgradeBinding) this.mViewBinding).spinnerRoom.setSelection(selectedItemPosition);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        refresh();
    }

    public void refresh() {
        ((ActChildMcuUpgradeVM) this.mViewModel).queryNeedUpgradeList();
    }

    public void send(long panelId, List<Long> ids) {
        ((ActChildMcuUpgradeVM) this.mViewModel).send(panelId, ids);
    }

    public void refreshDevice(long panelId, List<Long> ids, List<Long> successIds, boolean needRetry) {
        ((ActChildMcuUpgradeVM) this.mViewModel).failIds = ids;
        if (((ActChildMcuUpgradeVM) this.mViewModel).panelId == panelId) {
            if (needRetry) {
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setText(getString(R.string.app_str_child_mcu_upgrade_continue));
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setVisibility(0);
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setEnabled(true);
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvStop.setVisibility(8);
            } else {
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvStop.setVisibility(8);
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setText(getString(R.string.finish));
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setEnabled(true);
                ((ActChildMcuUpgradeBinding) this.mViewBinding).tvOk.setVisibility(0);
            }
            List<ActChildMcuUpgradeVM.NeedUpgradeDevice> data = this.mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                ActChildMcuUpgradeVM.NeedUpgradeDevice needUpgradeDevice = data.get(i);
                long deviceid = needUpgradeDevice.getDeviceid();
                if (ids.contains(Long.valueOf(deviceid))) {
                    needUpgradeDevice.setNeedUpgrade(5);
                } else if (successIds.contains(Long.valueOf(deviceid))) {
                    needUpgradeDevice.setNeedUpgrade(4);
                }
                this.mAdapter.setData(i, needUpgradeDevice);
            }
        }
    }
}