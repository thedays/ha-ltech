package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.circadianlighting.ActSelectDeviceLightPlan;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActSelectDeviceForMode extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private int lightType;
    private int modeNum;
    private int modeType;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int itemLayout() {
        return R.layout.item_select_device_import_mode;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(0);
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        setSortType(1);
        this.modeNum = getIntent().getIntExtra(Constants.MODE_NUM, 1);
        int intExtra = getIntent().getIntExtra(Constants.MODE_TYPE, 2);
        this.modeType = intExtra;
        if (intExtra == 3) {
            setTitle(getString(R.string.import_device_plan));
            this.listType = 4;
        } else {
            setTitle(getString(R.string.choose_import_device));
            this.listType = 5;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(BaseViewHolder helper, Role item) {
        final Device device = (Device) item;
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId()));
        helper.getView(R.id.tv_import).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.mode.ActSelectDeviceForMode$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectDeviceForMode.this.lambda$convertView$2(device, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$2(final Device device, View view) {
        MessageDialog.show(this, "", getString(R.string.import_mode_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.mode.ActSelectDeviceForMode$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view2) {
                boolean lambda$convertView$1;
                lambda$convertView$1 = ActSelectDeviceForMode.this.lambda$convertView$1(device, baseDialog, view2);
                return lambda$convertView$1;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$convertView$1(Device device, BaseDialog baseDialog, View view) {
        showLoadingDialog("");
        CmdAssistant.getModeCmdAssistant(device, new int[0]).queryAdvancedModeInfo(this, this.modeNum, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActSelectDeviceForMode$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceForMode.this.lambda$convertView$0((ResponseMsg) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$0(ResponseMsg responseMsg) {
        if (responseMsg == null) {
            showErrorDialog(getString(R.string.import_fail));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_DATA, responseMsg.getResData().substring(16));
        setResult(6002, intent);
        SmartToast.showShort(R.string.import_success);
        finishActivity();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        NavUtils.destination(ActSelectDeviceLightPlan.class).withLong("device_id", device.getDeviceId()).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (!device.isVirtual() && ProductRepository.supportLightMode(device.getProductId())) {
            int i = this.modeType;
            if (i == 2) {
                int lightColorType = ProductRepository.getLightColorType((Object) device);
                if (lightColorType == 20) {
                    lightColorType = 5;
                }
                return lightColorType == this.lightType;
            }
            if (i == 3 && ProductRepository.getLightColorType((Object) device) == 2 && device.getMaxkelvin() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6002) {
            setResult(6002, data);
            finishActivity();
        }
    }
}