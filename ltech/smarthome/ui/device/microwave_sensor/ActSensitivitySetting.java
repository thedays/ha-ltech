package com.ltech.smarthome.ui.device.microwave_sensor;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.item.SelectItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSensitivitySetting extends BaseListActivity<SelectItem> {
    private long controlId;
    private Object controlObject;
    private boolean fromSetting;
    private boolean groupControl;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_with_sub;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.sensitivity));
        setEditString(getString(R.string.save));
        this.selectPosition = getIntent().getIntExtra(Constants.SELECT_POSITION, -1);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.SETTING_PAGE, true);
        this.fromSetting = booleanExtra;
        if (booleanExtra) {
            this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
            boolean booleanExtra2 = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
            this.groupControl = booleanExtra2;
            if (!booleanExtra2) {
                Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
                this.selectPosition = deviceById.getDeviceState().getWaveSensorState().getSensitivity();
                this.controlObject = deviceById;
            } else {
                Group groupById = Injection.repo().group().getGroupById(this.controlId);
                this.selectPosition = groupById.getDeviceState().getWaveSensorState().getSensitivity();
                this.controlObject = groupById;
            }
        }
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSensitivitySetting$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSensitivitySetting.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBinding) this.mViewBinding).setBottomText(getString(R.string.sensitivity_set_tip));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<SelectItem> getList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SelectItem(getString(R.string.sensitivity_value_1), getString(R.string.sensitivity_set_tip_1)));
        arrayList.add(new SelectItem(getString(R.string.sensitivity_value_2), getString(R.string.sensitivity_set_tip_2)));
        arrayList.add(new SelectItem(getString(R.string.sensitivity_value_3), getString(R.string.sensitivity_set_tip_3)));
        arrayList.add(new SelectItem(getString(R.string.sensitivity_value_4), getString(R.string.sensitivity_set_tip_4)));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, SelectItem item) {
        helper.setText(R.id.tv_name, item.getName()).setText(R.id.tv_sub, item.getSubName()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        if (this.fromSetting) {
            if (isHsd()) {
                RelateInfoUtils.showImageTipDialog((Device) this.controlObject, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSensitivitySetting$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSensitivitySetting.this.lambda$edit$1(imageTipDialog);
                    }
                });
                return;
            } else {
                setSensitivity();
                return;
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.SENSITIVITY, this.selectPosition);
        setResult(5007, intent);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismiss();
        setSensitivity();
    }

    private void setSensitivity() {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.controlObject, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), this.selectPosition, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSensitivitySetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSensitivitySetting.this.lambda$setSensitivity$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensitivity$2(Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            if (this.controlObject instanceof Device) {
                Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
                deviceById.getDeviceState().getWaveSensorState().setSensitivity(this.selectPosition);
                Injection.repo().device().saveDevice(deviceById);
            } else {
                Group groupById = Injection.repo().group().getGroupById(this.controlId);
                groupById.getGroupState().getWaveSensorState().setSensitivity(this.selectPosition);
                Injection.repo().group().saveGroup(groupById);
            }
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
            EventBusUtils.post(new LiveBusHelper(10, Integer.valueOf(this.selectPosition)));
            finishActivity();
            return;
        }
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
    }

    private boolean isHsd() {
        Object obj = this.controlObject;
        return (obj instanceof Device) && ProductId.ID_SENSOR_HSD.equals(((Device) obj).getProductId());
    }
}