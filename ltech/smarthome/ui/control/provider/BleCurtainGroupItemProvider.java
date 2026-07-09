package com.ltech.smarthome.ui.control.provider;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzy.tvmao.KKNonACManager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.IrQuickDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class BleCurtainGroupItemProvider extends BaseDeviceProvider<Group> {
    private long deviceId;
    private KKNonACManager kkNonACManager;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_ir;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 15;
    }

    public BleCurtainGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Group data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, false);
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BleCurtainGroupItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) BleCurtainGroupItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) BleCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) BleCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) BleCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) BleCurtainGroupItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) BleCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                BleCurtainGroupItemProvider bleCurtainGroupItemProvider = BleCurtainGroupItemProvider.this;
                bleCurtainGroupItemProvider.nav(data, bleCurtainGroupItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        showBleMotorDialog(data, this.viewModel);
        checkGroupStatus(data);
    }

    private void showBleMotorDialog(final Group group, final BaseViewModel viewModel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP));
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP));
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN));
        IrQuickDialog.motor(true).setTitle(group.getGroupName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.control.provider.BleCurtainGroupItemProvider.2
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(MotorKeyItem item) {
                CmdAssistant.getDeviceAssistant(group, new int[0]).controlCurtain(ActivityUtils.getTopActivity(), Integer.parseInt(item.getKey()));
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                BleCurtainGroupItemProvider.this.nav(group, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Group group, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActBleMotor.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withLong(Constants.CONTROL_ID, group.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withBoolean(Constants.GROUP_CONTROL, true));
    }
}