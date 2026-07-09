package com.ltech.smarthome.ui.automation.local;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.SmartToast;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelForAutomation extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private long gatewayId;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_gateway));
        setEditString(getString(R.string.confirm));
        this.listType = 5;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        setSortType(1);
        this.gatewayId = getIntent().getLongExtra("device_id", 0L);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.gatewayId != 0) {
            Intent intent = new Intent();
            intent.putExtra("device_id", this.gatewayId);
            setResult(3018, intent);
            finishActivity();
            return;
        }
        SmartToast.showShort(R.string.please_choose_gateway);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(final BaseViewHolder helper, final Role role) {
        Device device = (Device) role;
        helper.setText(R.id.tv_device_name, device.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(device)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(device.getFloorId(), device.getRoomId()));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        helper.setBackgroundRes(R.id.iv_select, device.getDeviceId() == this.gatewayId ? R.mipmap.ic_tick_sel : R.color.transparent);
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.automation.local.ActSelectSuperPanelForAutomation.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Device device2 = (Device) role;
                if (ActSelectSuperPanelForAutomation.this.gatewayId != device2.getDeviceId()) {
                    ActSelectSuperPanelForAutomation.this.gatewayId = device2.getDeviceId();
                    helper.getBindingAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return ProductRepository.isSuperPanel(device.getProductId());
    }
}