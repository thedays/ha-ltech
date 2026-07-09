package com.ltech.smarthome.ui.device.microwave_sensor;

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
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIlluminanceSetting extends BaseListActivity<String> {
    private long controlId;
    private Object controlObject;
    private boolean groupControl;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        int illuminance;
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.illuminance));
        setEditString(getString(R.string.save));
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.illuminance_set_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.groupControl = booleanExtra;
        if (!booleanExtra) {
            Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
            illuminance = deviceById.getDeviceState().getWaveSensorState().getIlluminance();
            this.controlObject = deviceById;
        } else {
            Group groupById = Injection.repo().group().getGroupById(this.controlId);
            illuminance = groupById.getDeviceState().getWaveSensorState().getIlluminance();
            this.controlObject = groupById;
        }
        if (illuminance == 0) {
            this.selectPosition = this.mAdapter.getData().size() - 1;
        } else {
            this.selectPosition = illuminance - 1;
        }
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActIlluminanceSetting$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActIlluminanceSetting.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.illuminance_set_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
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
    protected List<String> getList() {
        return Arrays.asList(getResources().getStringArray(R.array.wave_sensor_illuminance));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        int i = this.selectPosition;
        if (i == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        final int i2 = i == getList().size() + (-1) ? 0 : this.selectPosition + 1;
        if (isHsd()) {
            RelateInfoUtils.showImageTipDialog((Device) this.controlObject, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActIlluminanceSetting$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActIlluminanceSetting.this.lambda$edit$1(i2, imageTipDialog);
                }
            });
        } else {
            setIlluminance(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(int i, ImageTipDialog imageTipDialog) {
        imageTipDialog.dismiss();
        setIlluminance(i);
    }

    private void setIlluminance(final int value) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.controlObject, new int[0]).setIllumincance(ActivityUtils.getTopActivity(), isHsd(), value, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActIlluminanceSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActIlluminanceSetting.this.lambda$setIlluminance$2(value, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIlluminance$2(int i, Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            if (this.controlObject instanceof Device) {
                Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
                deviceById.getDeviceState().getWaveSensorState().setIlluminance(i);
                Injection.repo().device().saveDevice(deviceById);
            } else {
                Group groupById = Injection.repo().group().getGroupById(this.controlId);
                groupById.getGroupState().getWaveSensorState().setIlluminance(i);
                Injection.repo().group().saveGroup(groupById);
            }
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
            EventBusUtils.post(new LiveBusHelper(11, Integer.valueOf(i)));
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