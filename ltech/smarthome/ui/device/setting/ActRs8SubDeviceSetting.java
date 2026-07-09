package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActRs8SubDeviceSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.view.dialog.MultiSelectListDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRs8SubDeviceSetting extends BaseDeviceSetActivity<ActRs8SubDeviceSettingBinding, ActRs8SubDeviceSettingVM> {
    private BaseQuickAdapter<Rs8CodeLibResponse.CodeLib.Action, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rs8_sub_device_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        initMoreList();
    }

    private void initMoreList() {
        ((ActRs8SubDeviceSettingBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActRs8SubDeviceSettingBinding) this.mViewBinding).rv;
        BaseQuickAdapter<Rs8CodeLibResponse.CodeLib.Action, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs8CodeLibResponse.CodeLib.Action, BaseViewHolder>(this, R.layout.item_rs8_sub_device) { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Rs8CodeLibResponse.CodeLib.Action action) {
                baseViewHolder.setText(R.id.tv_title, LanguageUtils.isChinese(this.mContext) ? action.getCname() : action.getEname());
                ((TextView) baseViewHolder.getView(R.id.tv_title)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ((ActRs8SubDeviceSettingVM) ActRs8SubDeviceSetting.this.mViewModel).send(((Rs8CodeLibResponse.CodeLib.Action) ActRs8SubDeviceSetting.this.mAdapter.getData().get(i)).getInstruct());
            }
        });
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActRs8SubDeviceSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRs8SubDeviceSettingVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActRs8SubDeviceSettingVM) this.mViewModel).controlId);
        ((ActRs8SubDeviceSettingVM) this.mViewModel).controlDevice.setValue(((ActRs8SubDeviceSettingVM) this.mViewModel).device);
        ((ActRs8SubDeviceSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRs8SubDeviceSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActRs8SubDeviceSettingVM) this.mViewModel).showActionsDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (((ActRs8SubDeviceSettingVM) ActRs8SubDeviceSetting.this.mViewModel).moreActions == null || ((ActRs8SubDeviceSettingVM) ActRs8SubDeviceSetting.this.mViewModel).moreActions.isEmpty()) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (Rs8CodeLibResponse.CodeLib.Action action : ((ActRs8SubDeviceSettingVM) ActRs8SubDeviceSetting.this.mViewModel).moreActions) {
                    arrayList.add(new MultiSelectListDialog.SelectItem(LanguageUtils.isChinese(ActRs8SubDeviceSetting.this.activity) ? action.getCname() : action.getEname(), action.isSel()));
                }
                MultiSelectListDialog.asDefault().setSelectList(arrayList).setTitle(ActRs8SubDeviceSetting.this.getString(R.string.app_str_more_cmd)).setCancelString(ActRs8SubDeviceSetting.this.getString(R.string.cancel)).setConfirmString(ActRs8SubDeviceSetting.this.getString(R.string.confirm)).setMultiSelectListener(new MultiSelectListDialog.IMultiSelectListener() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting.3.1
                    @Override // com.ltech.smarthome.view.dialog.MultiSelectListDialog.IMultiSelectListener
                    public void onSelect(List<Integer> selectPositions) {
                        ((ActRs8SubDeviceSettingVM) ActRs8SubDeviceSetting.this.mViewModel).save(selectPositions);
                        ActRs8SubDeviceSetting.this.setResult(-1);
                    }
                }).showDialog(ActRs8SubDeviceSetting.this.activity);
            }
        });
        ((ActRs8SubDeviceSettingVM) this.mViewModel).moreActionEvent.observe(this, new Observer<List<Rs8CodeLibResponse.CodeLib.Action>>() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Rs8CodeLibResponse.CodeLib.Action> actions) {
                ActRs8SubDeviceSetting.this.mAdapter.replaceData(actions);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ActRs8SubDeviceSetting actRs8SubDeviceSetting;
        ((ActRs8SubDeviceSettingBinding) this.mViewBinding).tvDeviceName.setText(device.getDeviceName());
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
        String str = "";
        if (deviceByDeviceId == null) {
            actRs8SubDeviceSetting = this;
            ((ActRs8SubDeviceSettingBinding) actRs8SubDeviceSetting.mViewBinding).tvSubordinateDevice.setText("");
            ((ActRs8SubDeviceSettingBinding) actRs8SubDeviceSetting.mViewBinding).tvRoomName.setText("");
        } else {
            Floor floor = Injection.repo().home().getFloor(device.getFloorId());
            Room room = Injection.repo().home().getRoom(device.getRoomId());
            ((ActRs8SubDeviceSettingBinding) this.mViewBinding).tvSubordinateDevice.setText(deviceByDeviceId.getDeviceName());
            AppCompatTextView appCompatTextView = ((ActRs8SubDeviceSettingBinding) this.mViewBinding).tvRoomName;
            if (room != null) {
                str = floor.getFloorName() + " " + room.getRoomName();
            } else if (floor != null) {
                str = floor.getFloorName();
            }
            appCompatTextView.setText(str);
            actRs8SubDeviceSetting = this;
            ((ActRs8SubDeviceSettingVM) this.mViewModel).roomPickHelper.startObserve(actRs8SubDeviceSetting, device.getPlaceId(), device.getRoomId());
            ((ActRs8SubDeviceSettingBinding) actRs8SubDeviceSetting.mViewBinding).ivRoomNameGo.setVisibility(0);
        }
        ((ActRs8SubDeviceSettingVM) actRs8SubDeviceSetting.mViewModel).loadCodeLib();
    }
}