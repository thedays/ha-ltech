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
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.StateConditionVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.SelectDoubleListWheelDialog;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectAcStatusDetailCondition extends VMActivity<ActSelectBleCurtainActionBinding, StateConditionVM> {
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
    private int select2ValuePos = 0;

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
            int value = deviceConditionParam.getValue();
            deviceConditionParam.getValue2();
            int subIndex = deviceConditionParam.getSubIndex();
            i = deviceConditionParam.getOperator();
            i2 = value;
            i3 = subIndex;
        } else {
            i = 1;
            i2 = 1;
            i3 = 1;
        }
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        arrayList.add(new StateConditionVM.Condition(1, 0, 1, i3 == 1 && i2 == 1));
        arrayList.add(new StateConditionVM.Condition(1, 0, 0, i3 == 1 && i2 == 0));
        arrayList.add(new StateConditionVM.Condition(2, 2, 0, i2, i3 == 2 && i == 2));
        arrayList.add(new StateConditionVM.Condition(2, 3, 0, i2, i3 == 2 && i == 3));
        arrayList.add(new StateConditionVM.Condition(3, 0, i2, i3 == 3));
        arrayList.add(new StateConditionVM.Condition(4, 0, i2, i3 == 4));
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
        BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder>(R.layout.item_env_status_detail_condition) { // from class: com.ltech.smarthome.ui.automation.ActSelectAcStatusDetailCondition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, StateConditionVM.Condition condition) {
                boolean z = false;
                if (condition.getType() == 1) {
                    baseViewHolder.setText(R.id.tv_name, String.format(ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_ac_power_status), condition.getMax() == 0 ? ActSelectAcStatusDetailCondition.this.getString(R.string.function_close) : ActSelectAcStatusDetailCondition.this.getString(R.string.function_open)));
                } else {
                    String str = "";
                    if (condition.getType() == 2) {
                        int operator = condition.getOperator();
                        if (operator == 2) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_above);
                        } else if (operator == 3) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_blow);
                        }
                        baseViewHolder.setText(R.id.tv_name, ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_ac_temp_set) + str);
                        baseViewHolder.setText(R.id.tv_sub_name, String.format(ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_specify_parameters), ActSelectAcStatusDetailCondition.this.getString(R.string.temperature)));
                        baseViewHolder.setText(R.id.tv_value, condition.getMax() + "°C");
                    } else if (condition.getType() == 3) {
                        baseViewHolder.setText(R.id.tv_name, ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_ac_wind_set));
                        baseViewHolder.setText(R.id.tv_sub_name, String.format(ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_specify_parameters), ActSelectAcStatusDetailCondition.this.getString(R.string.ir_wind_speed)));
                        int max = condition.getMax();
                        if (max == 0) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.fan_speed_1);
                        } else if (max == 1) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.fan_speed_4);
                        } else if (max == 2) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.fan_speed_3);
                        } else if (max == 3) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.fan_speed_5);
                        } else if (max == 4) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.fan_speed_2);
                        } else if (max == 5) {
                            str = ActSelectAcStatusDetailCondition.this.getString(R.string.fan_speed_6);
                        }
                        baseViewHolder.setText(R.id.tv_value, str);
                    } else if (condition.getType() == 4) {
                        baseViewHolder.setText(R.id.tv_name, ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_ac_mode_set));
                        baseViewHolder.setText(R.id.tv_sub_name, String.format(ActSelectAcStatusDetailCondition.this.getString(R.string.app_str_specify_parameters), ActSelectAcStatusDetailCondition.this.getString(R.string.ir_mode)));
                        switch (condition.getMax()) {
                            case 1:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.air_mode_1);
                                break;
                            case 2:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.air_mode_5);
                                break;
                            case 3:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.refreshing);
                                break;
                            case 4:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.air_mode_4);
                                break;
                            case 5:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.automatic_dehumidification);
                                break;
                            case 6:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.intimate_sleep);
                                break;
                            case 8:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.air_mode_2);
                                break;
                            case 9:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.floor_heat);
                                break;
                            case 10:
                                str = ActSelectAcStatusDetailCondition.this.getString(R.string.strong_heating);
                                break;
                        }
                        baseViewHolder.setText(R.id.tv_value, str);
                    }
                }
                if (condition.isSel() && (condition.getType() == 2 || condition.getType() == 3 || condition.getType() == 4)) {
                    z = true;
                }
                baseViewHolder.setGone(R.id.layout_cur_value, z);
                baseViewHolder.setImageResource(R.id.iv_select, condition.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                baseViewHolder.addOnClickListener(R.id.layout_cur_value);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAcStatusDetailCondition.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (i != ActSelectAcStatusDetailCondition.this.lastSelectedPos) {
                    StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectAcStatusDetailCondition.this.mAdapter.getData().get(i);
                    condition.setSel(true);
                    ActSelectAcStatusDetailCondition.this.mAdapter.setData(i, condition);
                    StateConditionVM.Condition condition2 = (StateConditionVM.Condition) ActSelectAcStatusDetailCondition.this.mAdapter.getData().get(ActSelectAcStatusDetailCondition.this.lastSelectedPos);
                    condition2.setSel(false);
                    ActSelectAcStatusDetailCondition.this.mAdapter.setData(ActSelectAcStatusDetailCondition.this.lastSelectedPos, condition2);
                    ActSelectAcStatusDetailCondition.this.lastSelectedPos = i;
                }
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAcStatusDetailCondition.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectAcStatusDetailCondition.this.mAdapter.getData().get(i);
                ActSelectAcStatusDetailCondition.this.showSingleSelectedDialog(condition.getType(), condition.getOperator(), condition.getMax(), i);
            }
        });
    }

    private void showMultiSelectedDialog(int type, final int pos) {
        String string;
        if (type == 2) {
            string = getString(R.string.app_str_above);
        } else if (type == 3) {
            string = getString(R.string.app_str_blow);
        } else if (type != 4) {
            string = "";
        } else {
            string = getString(R.string.app_str_range);
        }
        SelectDoubleListWheelDialog.asDefault().setTitle(this.typeName + string).setSelectList(this.dialogList).setSelectPosition(this.selectValuePos).setSelect2Position(this.select2ValuePos).setSpecify(this.typeUnit).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<int[]>() { // from class: com.ltech.smarthome.ui.automation.ActSelectAcStatusDetailCondition.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(int[] array) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectAcStatusDetailCondition.this.mAdapter.getData().get(pos);
                condition.setMin(Integer.parseInt((String) ActSelectAcStatusDetailCondition.this.dialogList.get(array[0])));
                condition.setMax(Integer.parseInt((String) ActSelectAcStatusDetailCondition.this.dialogList.get(array[1])));
                ActSelectAcStatusDetailCondition.this.mAdapter.setData(pos, condition);
            }
        }).showDialog(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSingleSelectedDialog(final int type, int operator, int value, final int pos) {
        String string;
        String str = "";
        this.typeUnit = "";
        if (type == 2) {
            if (operator == 2) {
                string = getString(R.string.app_str_above);
            } else if (operator != 3) {
                string = "";
            } else {
                string = getString(R.string.app_str_blow);
            }
            for (int i = 16; i < 31; i++) {
                this.dialogList.add(i + "");
                if (i == value) {
                    this.selectValuePos = this.dialogList.size() - 1;
                }
            }
            this.typeName = getString(R.string.temperature) + string;
            this.typeUnit = "°C";
        } else if (type == 3) {
            for (int i2 = 1; i2 < 5; i2++) {
                if (i2 == 1) {
                    str = getString(R.string.fan_speed_1);
                } else if (i2 == 2) {
                    str = getString(R.string.fan_speed_2);
                } else if (i2 == 3) {
                    str = getString(R.string.fan_speed_3);
                } else if (i2 == 4) {
                    str = getString(R.string.fan_speed_4);
                }
                this.dialogList.add(str);
                if (i2 == value) {
                    this.selectValuePos = this.dialogList.size() - 1;
                }
            }
            this.typeName = getString(R.string.ir_wind_speed);
        } else if (type == 4) {
            for (int i3 = 1; i3 < 6; i3++) {
                if (i3 == 1) {
                    str = getString(R.string.air_mode_1);
                } else if (i3 == 2) {
                    str = getString(R.string.air_mode_2);
                } else if (i3 == 3) {
                    str = getString(R.string.air_mode_3);
                } else if (i3 == 4) {
                    str = getString(R.string.air_mode_4);
                } else if (i3 == 5) {
                    str = getString(R.string.air_mode_5);
                }
                this.dialogList.add(str);
                if (i3 == value) {
                    this.selectValuePos = this.dialogList.size() - 1;
                }
            }
            this.typeName = getString(R.string.mode);
        }
        SelectListWheelDialog.asDefault(true).setTitle(this.typeName).setSelectList(this.dialogList).setSpecify(this.typeUnit).setSelectPosition(this.selectValuePos).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.automation.ActSelectAcStatusDetailCondition.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectAcStatusDetailCondition.this.mAdapter.getData().get(pos);
                int i4 = type;
                if (i4 == 3 || i4 == 4) {
                    condition.setMax(integer.intValue() + 1);
                } else {
                    condition.setMax(Integer.parseInt((String) ActSelectAcStatusDetailCondition.this.dialogList.get(integer.intValue())));
                }
                ActSelectAcStatusDetailCondition.this.mAdapter.setData(pos, condition);
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
        deviceConditionParam.type = 6;
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