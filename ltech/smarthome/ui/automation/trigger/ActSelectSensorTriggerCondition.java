package com.ltech.smarthome.ui.automation.trigger;

import android.view.View;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSensorTriggerCondition extends BaseTriggerCondition implements ISelectAction {
    private boolean isEdit;

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$save$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
    }

    @Override // com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition
    protected List<BaseTriggerCondition.Condition> getList() {
        if (getIntent().getIntExtra(Constants.CONDITION_INDEX, -1) >= 0) {
            this.isEdit = true;
            this.deviceConditionParam = (DeviceConditionParam) SceneHelper.instance().conditionParam;
        }
        if (ProductId.ID_SENSOR_MS03.equals(this.productId)) {
            return getMS03List();
        }
        if (ProductId.ID_SENSOR_HSD.equals(this.productId)) {
            return getHsdList();
        }
        return new ArrayList();
    }

    protected List<BaseTriggerCondition.Condition> getMS03List() {
        int intExtra = getIntent().getIntExtra(Constants.CONDITION_INDEX, -1);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.mr_state_value_5), 1, intExtra == 1));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.mr_state_value_1), 0, intExtra == 0));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.str_above, new Object[]{getString(R.string.lux_title)}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.lux_title)}), 5, 1, 2, intExtra == 5 && this.deviceConditionParam.getSubIndex() == 1 && this.deviceConditionParam.getOperator() == 2));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.str_blow, new Object[]{getString(R.string.lux_title)}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.lux_title)}), 5, 1, 3, intExtra == 5 && this.deviceConditionParam.getSubIndex() == 1 && this.deviceConditionParam.getOperator() == 3));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.str_above, new Object[]{getString(R.string.ct_title)}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.ct_title)}), 5, 2, 2, intExtra == 5 && this.deviceConditionParam.getSubIndex() == 2 && this.deviceConditionParam.getOperator() == 2));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.str_blow, new Object[]{getString(R.string.ct_title)}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.ct_title)}), 5, 2, 3, intExtra == 5 && this.deviceConditionParam.getSubIndex() == 2 && this.deviceConditionParam.getOperator() == 3));
        return arrayList;
    }

    protected List<BaseTriggerCondition.Condition> getHsdList() {
        int intExtra = getIntent().getIntExtra(Constants.CONDITION_INDEX, -1);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.someone_move), 1, 65535, intExtra == 1 && this.deviceConditionParam.getValue() == 65535));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.someone_move_and_other, new Object[]{getString(R.string.someone_move), getString(R.string.str_blow, new Object[]{getString(R.string.lux_title)})}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.lux_title)}), 1, 1, 3, intExtra == 1 && this.deviceConditionParam.getSubIndex() == 1 && this.deviceConditionParam.getOperator() == 3));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.someone_move_and_other, new Object[]{getString(R.string.someone_move), getString(R.string.str_above, new Object[]{getString(R.string.lux_title)})}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.lux_title)}), 1, 1, 2, intExtra == 1 && this.deviceConditionParam.getSubIndex() == 1 && this.deviceConditionParam.getOperator() == 2));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.someone_move_and_other, new Object[]{getString(R.string.someone_move), getString(R.string.low_ambient)}), 1, 1, 3, 10));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.someone_move_and_other, new Object[]{getString(R.string.someone_move), getString(R.string.high_ambient)}), 1, 1, 2, 100));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.str_blow, new Object[]{getString(R.string.lux_title)}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.lux_title)}), 5, 1, 3, intExtra == 5 && this.deviceConditionParam.getSubIndex() == 1 && this.deviceConditionParam.getOperator() == 3));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.str_above, new Object[]{getString(R.string.lux_title)}), getString(R.string.app_str_specify_parameters, new Object[]{getString(R.string.lux_title)}), 5, 1, 2, intExtra == 5 && this.deviceConditionParam.getSubIndex() == 1 && this.deviceConditionParam.getOperator() == 2));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.low_ambient), 5, 1, 3, 10));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.high_ambient), 5, 1, 2, 100));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.no_motion_2_min), 0, 1, 2, intExtra == 0 && this.deviceConditionParam.getValue() == 2));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.no_motion_5_min), 0, 1, 5, intExtra == 0 && this.deviceConditionParam.getValue() == 5));
        arrayList.add(new BaseTriggerCondition.Condition(getString(R.string.diy_no_motion_time), 0, 1, 65535, intExtra == 0 && this.deviceConditionParam.getValue() == 65535));
        return arrayList;
    }

    @Override // com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition
    protected void convertView(final BaseViewHolder helper, final BaseTriggerCondition.Condition condition) {
        super.convertView(helper, condition);
        StringBuilder sb = new StringBuilder();
        sb.append(this.deviceConditionParam.getValue());
        sb.append(condition.subIndex == 1 ? "Lux" : "K");
        helper.setText(R.id.tv_value, sb.toString());
        helper.getView(R.id.layout_cur_value).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.automation.trigger.ActSelectSensorTriggerCondition$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectSensorTriggerCondition.this.lambda$convertView$0(condition, helper, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$0(BaseTriggerCondition.Condition condition, BaseViewHolder baseViewHolder, View view) {
        showSingleSelectedDialog(condition.operator, baseViewHolder.getBindingAdapterPosition());
    }

    @Override // com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition
    protected void showSingleSelectedDialog(int operator, final int pos) {
        final ArrayList arrayList = new ArrayList();
        int i = -1;
        if (this.deviceConditionParam.getSubIndex() == 1) {
            for (int i2 = 0; i2 <= 1000; i2++) {
                if (i2 % 10 == 0) {
                    arrayList.add(i2 + "");
                }
                if (i2 == this.deviceConditionParam.value) {
                    i = arrayList.size() - 1;
                }
            }
        } else if (this.deviceConditionParam.getSubIndex() == 2) {
            for (int i3 = 1000; i3 <= 10000; i3++) {
                if (i3 % 100 == 0) {
                    arrayList.add(i3 + "");
                }
                if (i3 == this.deviceConditionParam.value) {
                    i = arrayList.size() - 1;
                }
            }
        }
        String str = this.deviceConditionParam.getSubIndex() == 1 ? "Lux" : "K";
        String string = getString(this.deviceConditionParam.getSubIndex() == 1 ? R.string.lux_title : R.string.ct_title);
        SelectListWheelDialog.asDefault(true).setTitle(operator == 2 ? getString(R.string.str_above, new Object[]{string}) : getString(R.string.str_blow, new Object[]{string})).setSelectList(arrayList).setSpecify(str).setSelectPosition(i).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.automation.trigger.ActSelectSensorTriggerCondition$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectSensorTriggerCondition.this.lambda$showSingleSelectedDialog$1(arrayList, pos, (Integer) obj);
            }
        }).showDialog(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSingleSelectedDialog$1(List list, int i, Integer num) {
        int intValue = num.intValue() == -1 ? 0 : num.intValue();
        Integer valueOf = Integer.valueOf(intValue);
        DeviceConditionParam deviceConditionParam = this.deviceConditionParam;
        valueOf.getClass();
        deviceConditionParam.value = Integer.parseInt((String) list.get(intValue));
        refreshData(i);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectedPos == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        this.deviceConditionParam.deviceid = this.deviceId;
        if (ProductId.ID_SENSOR_HSD.equals(this.productId)) {
            this.deviceConditionParam.type = 8;
        } else {
            this.deviceConditionParam.type = 2;
        }
        this.deviceConditionParam.productid = this.productId;
        this.deviceConditionParam.floorRoom = this.floorName + this.roomName;
        this.deviceConditionParam.deviceName = this.deviceName;
        this.deviceConditionParam.mac = this.mac;
        SceneHelper.instance().conditionParam = this.deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        if (!this.isEdit) {
            lambda$save$0(this);
        } else {
            setResult(3003);
            finishActivity();
        }
    }
}