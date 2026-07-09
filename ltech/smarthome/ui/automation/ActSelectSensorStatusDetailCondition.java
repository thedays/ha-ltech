package com.ltech.smarthome.ui.automation;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectBleCurtainActionBinding;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.StateConditionVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSensorStatusDetailCondition extends VMActivity<ActSelectBleCurtainActionBinding, StateConditionVM> {
    private long deviceId;
    private String deviceName;
    private String floorName;
    private BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> mAdapter;
    private String mac;
    private long placeId;
    private String productId;
    private String roomName;
    private int type;
    private String typeName;
    private String typeUnit;
    private int lastSelectedPos = 0;
    private List<String> dialogList = new ArrayList();
    private int selectValuePos = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_ble_curtain_action;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_condition));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.type = getIntent().getIntExtra(Constants.SELECT_TYPE, -1);
        this.floorName = getIntent().getStringExtra(Constants.FLOOR_NAME);
        this.roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
        this.deviceName = getIntent().getStringExtra("device_name");
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        initRv();
        initData();
    }

    private void initData() {
        int i;
        int i2;
        int i3;
        if (SceneHelper.instance().conditionParam != null) {
            DeviceConditionParam deviceConditionParam = (DeviceConditionParam) SceneHelper.instance().conditionParam;
            i2 = deviceConditionParam.getValue();
            i3 = deviceConditionParam.getSubIndex();
            i = deviceConditionParam.getOperator();
        } else {
            i = 1;
            i2 = -999;
            i3 = 1;
        }
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        if (ProductId.ID_SENSOR_HSD.equals(this.productId)) {
            arrayList.add(new StateConditionVM.Condition(2, 3, 0, (i2 != -999 && i3 == 2 && i == 3) ? i2 : 0, i3 == 2 && i == 3));
            arrayList.add(new StateConditionVM.Condition(2, 2, 0, (i2 != -999 && i3 == 2 && i == 2) ? i2 : 0, i3 == 2 && i == 2));
            arrayList.add(new StateConditionVM.Condition(2, 3, 0, 10, 10, false));
            arrayList.add(new StateConditionVM.Condition(2, 2, 0, 100, 100, false));
            arrayList.add(new StateConditionVM.Condition(3, 1, 0, 2, 2, i3 == 3 && i2 == 2));
            arrayList.add(new StateConditionVM.Condition(3, 1, 0, 5, 5, i3 == 3 && i2 == 5));
            arrayList.add(new StateConditionVM.Condition(3, 1, 0, 65535, 65535, i3 == 3 && i2 == 65535));
        } else {
            arrayList.add(new StateConditionVM.Condition(1, 0, 1, i3 == 1 && (i2 == 1 || i2 == -999)));
            arrayList.add(new StateConditionVM.Condition(1, 0, 0, i3 == 1 && i2 == 0));
            if (ProductId.ID_SENSOR_MS03.equals(this.productId)) {
                arrayList.add(new StateConditionVM.Condition(2, 2, 0, (i2 != -999 && i3 == 2 && i == 2) ? i2 : 0, i3 == 2 && i == 2));
                arrayList.add(new StateConditionVM.Condition(2, 3, 0, (i2 != -999 && i3 == 2 && i == 3) ? i2 : 0, i3 == 2 && i == 3));
                arrayList.add(new StateConditionVM.Condition(3, 2, 0, (i2 != -999 && i3 == 3 && i == 2) ? i2 : 1000, i3 == 3 && i == 2));
                arrayList.add(new StateConditionVM.Condition(3, 3, 0, (i2 != -999 && i3 == 3 && i == 3) ? i2 : 1000, i3 == 3 && i == 3));
            }
        }
        this.lastSelectedPos = -1;
        while (true) {
            if (i4 >= arrayList.size()) {
                break;
            }
            if (((StateConditionVM.Condition) arrayList.get(i4)).isSel()) {
                this.lastSelectedPos = i4;
                break;
            }
            i4++;
        }
        this.mAdapter.setNewData(arrayList);
    }

    private void initRv() {
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent;
        BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder>(R.layout.item_env_status_detail_condition) { // from class: com.ltech.smarthome.ui.automation.ActSelectSensorStatusDetailCondition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, StateConditionVM.Condition condition) {
                baseViewHolder.setText(R.id.tv_name, ActSelectSensorStatusDetailCondition.this.getItemTitle(condition));
                baseViewHolder.setGone(R.id.layout_cur_value, condition.isSel() && condition.getOperator() != 0 && condition.getSetValue() == 0);
                baseViewHolder.setImageResource(R.id.iv_select, condition.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                if (condition.isSel() && condition.getOperator() != 0 && condition.getSetValue() == 0) {
                    baseViewHolder.addOnClickListener(R.id.layout_cur_value);
                    baseViewHolder.setText(R.id.tv_sub_name, String.format(ActSelectSensorStatusDetailCondition.this.getString(R.string.app_str_specify_parameters), condition.getType() == 2 ? ActSelectSensorStatusDetailCondition.this.getString(R.string.lux_title) : ActSelectSensorStatusDetailCondition.this.getString(R.string.ct_title)));
                    StringBuilder sb = new StringBuilder();
                    sb.append(condition.getMax());
                    sb.append(condition.getType() == 2 ? "Lux" : "K");
                    baseViewHolder.setText(R.id.tv_value, sb.toString());
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectSensorStatusDetailCondition.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (i != ActSelectSensorStatusDetailCondition.this.lastSelectedPos) {
                    StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectSensorStatusDetailCondition.this.mAdapter.getData().get(i);
                    condition.setSel(true);
                    ActSelectSensorStatusDetailCondition.this.mAdapter.setData(i, condition);
                    if (ActSelectSensorStatusDetailCondition.this.lastSelectedPos != -1) {
                        StateConditionVM.Condition condition2 = (StateConditionVM.Condition) ActSelectSensorStatusDetailCondition.this.mAdapter.getData().get(ActSelectSensorStatusDetailCondition.this.lastSelectedPos);
                        condition2.setSel(false);
                        ActSelectSensorStatusDetailCondition.this.mAdapter.setData(ActSelectSensorStatusDetailCondition.this.lastSelectedPos, condition2);
                    }
                    ActSelectSensorStatusDetailCondition.this.lastSelectedPos = i;
                }
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectSensorStatusDetailCondition.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectSensorStatusDetailCondition.this.mAdapter.getData().get(i);
                ActSelectSensorStatusDetailCondition.this.showSingleSelectedDialog(condition.getType(), condition.getOperator(), condition.getMax(), i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getItemTitle(StateConditionVM.Condition condition) {
        if (condition.getType() == 1) {
            return condition.getMax() == 0 ? getString(R.string.mr_state_value_1) : getString(R.string.mr_state_value_5);
        }
        int type = condition.getType();
        int i = R.string.str_above;
        if (type == 2) {
            if (condition.getSetValue() == 10) {
                return getString(R.string.low_ambient);
            }
            if (condition.getSetValue() == 100) {
                return getString(R.string.high_ambient);
            }
            if (condition.getOperator() != 2) {
                i = R.string.str_blow;
            }
            return getString(i, new Object[]{getString(R.string.lux_title)});
        }
        if (condition.getType() == 3) {
            if (ProductId.ID_SENSOR_HSD.equals(this.productId)) {
                if (condition.getSetValue() == 2) {
                    return getString(R.string.no_motion_2_min);
                }
                if (condition.getSetValue() == 5) {
                    return getString(R.string.no_motion_5_min);
                }
                return getString(R.string.diy_no_motion_time);
            }
            if (condition.getOperator() != 2) {
                i = R.string.str_blow;
            }
            return getString(i, new Object[]{getString(R.string.ct_title)});
        }
        return getString(R.string.mr_state_value_1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSingleSelectedDialog(int type, int operator, int value, final int pos) {
        String string;
        if (operator == 2) {
            string = getString(R.string.app_str_above);
        } else if (operator != 3) {
            string = "";
        } else {
            string = getString(R.string.app_str_blow);
        }
        this.dialogList.clear();
        if (type == 2) {
            for (int i = 0; i <= 1000; i++) {
                if (i % 10 == 0) {
                    this.dialogList.add(i + "");
                    if (i == value) {
                        this.selectValuePos = this.dialogList.size() - 1;
                    }
                }
            }
        } else if (type == 3) {
            for (int i2 = 1000; i2 <= 10000; i2++) {
                if (i2 % 100 == 0) {
                    this.dialogList.add(i2 + "");
                    if (i2 == value) {
                        this.selectValuePos = this.dialogList.size() - 1;
                    }
                }
            }
        }
        this.typeUnit = type == 2 ? "Lux" : "K";
        this.typeName = getString(type == 2 ? R.string.lux_title : R.string.ct_title);
        SelectListWheelDialog.asDefault(true).setTitle(this.typeName + string).setSelectList(this.dialogList).setSpecify(this.typeUnit).setSelectPosition(this.selectValuePos).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.automation.ActSelectSensorStatusDetailCondition.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectSensorStatusDetailCondition.this.mAdapter.getData().get(pos);
                condition.setMax(Integer.parseInt((String) ActSelectSensorStatusDetailCondition.this.dialogList.get(integer.intValue())));
                ActSelectSensorStatusDetailCondition.this.mAdapter.setData(pos, condition);
            }
        }).showDialog(this.activity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        for (StateConditionVM.Condition condition : this.mAdapter.getData()) {
            if (condition.isSel()) {
                save(condition);
                return;
            }
        }
    }

    private void save(StateConditionVM.Condition condition) {
        DeviceConditionParam deviceConditionParam = new DeviceConditionParam();
        deviceConditionParam.deviceid = this.deviceId;
        deviceConditionParam.productid = this.productId;
        deviceConditionParam.floorRoom = this.floorName + this.roomName;
        deviceConditionParam.deviceName = this.deviceName;
        deviceConditionParam.mac = this.mac;
        if (ProductId.ID_SENSOR_HSD.equals(this.productId)) {
            deviceConditionParam.type = 8;
        } else {
            deviceConditionParam.type = 2;
        }
        deviceConditionParam.index = 1;
        deviceConditionParam.subIndex = condition.getType();
        deviceConditionParam.operator = condition.getOperator();
        deviceConditionParam.value = condition.getMax();
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        SceneHelper.instance().eventtype = 2;
        setResult(5013);
        finishActivity();
    }
}