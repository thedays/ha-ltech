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
public class ActSelectCurtainStatusDetailCondition extends VMActivity<ActSelectBleCurtainActionBinding, StateConditionVM> {
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
    private String typeUnit = "%";
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
        this.typeName = getString(R.string.app_str_curtain_status);
        if (SceneHelper.instance().conditionParam != null) {
            DeviceConditionParam deviceConditionParam = (DeviceConditionParam) SceneHelper.instance().conditionParam;
            int value = deviceConditionParam.getValue();
            int value2 = deviceConditionParam.getValue2();
            deviceConditionParam.getSubIndex();
            i = deviceConditionParam.getOperator();
            i2 = value;
            i3 = value2;
        } else {
            i = 2;
            i2 = 1;
            i3 = 1;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < 101; i5++) {
            this.dialogList.add(i5 + "");
            if (i5 == i2) {
                this.selectValuePos = this.dialogList.size() - 1;
            }
            if (i == 4 && i5 == i3) {
                this.select2ValuePos = this.dialogList.size() - 1;
            }
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StateConditionVM.Condition(2, 2, 0, i2, i == 2));
        arrayList.add(new StateConditionVM.Condition(2, 3, 0, i2, i == 3));
        arrayList.add(new StateConditionVM.Condition(2, 4, i2, i3, i == 4));
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
        BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder>(R.layout.item_env_status_detail_condition) { // from class: com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, StateConditionVM.Condition condition) {
                String string;
                int operator = condition.getOperator();
                if (operator == 2) {
                    string = ActSelectCurtainStatusDetailCondition.this.getString(R.string.great_than);
                } else if (operator == 3) {
                    string = ActSelectCurtainStatusDetailCondition.this.getString(R.string.less_than);
                } else {
                    string = operator != 4 ? "" : ActSelectCurtainStatusDetailCondition.this.getString(R.string.app_str_range);
                }
                baseViewHolder.setText(R.id.tv_name, ActSelectCurtainStatusDetailCondition.this.typeName + string);
                baseViewHolder.setGone(R.id.layout_cur_value, condition.isSel());
                baseViewHolder.setImageResource(R.id.iv_select, condition.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                if (condition.isSel()) {
                    baseViewHolder.addOnClickListener(R.id.layout_cur_value);
                    baseViewHolder.setText(R.id.tv_sub_name, String.format(ActSelectCurtainStatusDetailCondition.this.getString(R.string.app_str_specify_parameters), ActSelectCurtainStatusDetailCondition.this.getString(R.string.app_str_location)));
                    if (condition.getOperator() == 4) {
                        baseViewHolder.setText(R.id.tv_value, condition.getMin() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER + condition.getMax() + ActSelectCurtainStatusDetailCondition.this.typeUnit);
                        return;
                    }
                    baseViewHolder.setText(R.id.tv_value, condition.getMax() + ActSelectCurtainStatusDetailCondition.this.typeUnit);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (i != ActSelectCurtainStatusDetailCondition.this.lastSelectedPos) {
                    StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectCurtainStatusDetailCondition.this.mAdapter.getData().get(i);
                    condition.setSel(true);
                    ActSelectCurtainStatusDetailCondition.this.mAdapter.setData(i, condition);
                    StateConditionVM.Condition condition2 = (StateConditionVM.Condition) ActSelectCurtainStatusDetailCondition.this.mAdapter.getData().get(ActSelectCurtainStatusDetailCondition.this.lastSelectedPos);
                    condition2.setSel(false);
                    ActSelectCurtainStatusDetailCondition.this.mAdapter.setData(ActSelectCurtainStatusDetailCondition.this.lastSelectedPos, condition2);
                    ActSelectCurtainStatusDetailCondition.this.lastSelectedPos = i;
                }
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectCurtainStatusDetailCondition.this.mAdapter.getData().get(i);
                if (condition.getOperator() == 4) {
                    ActSelectCurtainStatusDetailCondition.this.showMultiSelectedDialog(condition.getType(), i);
                } else {
                    ActSelectCurtainStatusDetailCondition.this.showSingleSelectedDialog(condition.getOperator(), i);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMultiSelectedDialog(int type, final int pos) {
        String string;
        if (type == 2) {
            string = getString(R.string.great_than);
        } else if (type == 3) {
            string = getString(R.string.less_than);
        } else if (type != 4) {
            string = "";
        } else {
            string = getString(R.string.app_str_range);
        }
        SelectDoubleListWheelDialog.asDefault().setTitle(this.typeName + string).setSelectList(this.dialogList).setSelectPosition(this.selectValuePos).setSelect2Position(this.select2ValuePos).setSpecify(this.typeUnit).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<int[]>() { // from class: com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(int[] array) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectCurtainStatusDetailCondition.this.mAdapter.getData().get(pos);
                condition.setMin(Integer.parseInt((String) ActSelectCurtainStatusDetailCondition.this.dialogList.get(array[0])));
                condition.setMax(Integer.parseInt((String) ActSelectCurtainStatusDetailCondition.this.dialogList.get(array[1])));
                ActSelectCurtainStatusDetailCondition.this.mAdapter.setData(pos, condition);
            }
        }).showDialog(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSingleSelectedDialog(int operator, final int pos) {
        String string;
        if (operator == 2) {
            string = getString(R.string.great_than);
        } else if (operator != 3) {
            string = "";
        } else {
            string = getString(R.string.less_than);
        }
        SelectListWheelDialog.asDefault(true).setTitle(this.typeName + string).setSelectList(this.dialogList).setSpecify(this.typeUnit).setSelectPosition(this.selectValuePos).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectCurtainStatusDetailCondition.this.mAdapter.getData().get(pos);
                condition.setMax(Integer.parseInt((String) ActSelectCurtainStatusDetailCondition.this.dialogList.get(integer.intValue())));
                ActSelectCurtainStatusDetailCondition.this.mAdapter.setData(pos, condition);
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
        deviceConditionParam.type = 5;
        deviceConditionParam.index = 1;
        deviceConditionParam.subIndex = condition.getType();
        deviceConditionParam.operator = condition.getOperator();
        if (deviceConditionParam.operator == 4) {
            deviceConditionParam.value = condition.getMin();
            deviceConditionParam.value2 = condition.getMax();
        } else {
            deviceConditionParam.value = condition.getMax();
        }
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        SceneHelper.instance().eventtype = 2;
        setResult(5013);
        finishActivity();
    }
}