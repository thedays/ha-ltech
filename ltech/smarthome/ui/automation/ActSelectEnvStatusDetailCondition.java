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
public class ActSelectEnvStatusDetailCondition extends VMActivity<ActSelectBleCurtainActionBinding, StateConditionVM> {
    private BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> mAdapter;
    private long placeId;
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
            i3 = deviceConditionParam.getValue2();
            i = deviceConditionParam.getOperator();
            if (i3 < i2) {
                i3 = i2;
                i2 = i3;
            }
        } else {
            i = 2;
            i2 = -999;
            i3 = -999;
        }
        ArrayList arrayList = new ArrayList();
        int i4 = this.type;
        int i5 = 0;
        if (i4 == 1) {
            this.typeName = getString(R.string.temperature);
            this.typeUnit = "°C";
            for (int i6 = -40; i6 < 101; i6++) {
                this.dialogList.add(i6 + "");
                if (i6 == i2) {
                    this.selectValuePos = this.dialogList.size() - 1;
                }
                if (i6 == i3) {
                    this.select2ValuePos = this.dialogList.size() - 1;
                }
            }
            arrayList.add(new StateConditionVM.Condition(2, 0, i3 == -999 ? 26 : i3, i == 2));
            arrayList.add(new StateConditionVM.Condition(3, 0, i3 == -999 ? 20 : i3, i == 3));
            if (i2 == -999) {
                i2 = 20;
            }
            if (i3 == -999) {
                i3 = 26;
            }
            arrayList.add(new StateConditionVM.Condition(4, i2, i3, i == 4));
        } else if (i4 == 2) {
            this.typeName = getString(R.string.humidity);
            this.typeUnit = "%";
            for (int i7 = 0; i7 < 101; i7++) {
                this.dialogList.add(i7 + "");
                if (i7 == i2) {
                    this.selectValuePos = this.dialogList.size() - 1;
                }
                if (i7 == i3) {
                    this.select2ValuePos = this.dialogList.size() - 1;
                }
            }
            arrayList.add(new StateConditionVM.Condition(2, 0, i3 == -999 ? 70 : i3, i == 2));
            arrayList.add(new StateConditionVM.Condition(3, 0, i3 == -999 ? 40 : i3, i == 3));
            if (i2 == -999) {
                i2 = 40;
            }
            if (i3 == -999) {
                i3 = 70;
            }
            arrayList.add(new StateConditionVM.Condition(4, i2, i3, i == 4));
        } else if (i4 == 3) {
            this.typeName = getString(R.string.pm_25);
            this.typeUnit = "μg/m³";
            for (int i8 = 0; i8 < 301; i8++) {
                this.dialogList.add(i8 + "");
                if (i8 == i2) {
                    this.selectValuePos = this.dialogList.size() - 1;
                }
                if (i8 == i3) {
                    this.select2ValuePos = this.dialogList.size() - 1;
                }
            }
            arrayList.add(new StateConditionVM.Condition(2, 0, i3 == -999 ? 75 : i3, i == 2));
            arrayList.add(new StateConditionVM.Condition(3, 0, i3 == -999 ? 35 : i3, i == 3));
            if (i2 == -999) {
                i2 = 35;
            }
            if (i3 == -999) {
                i3 = 75;
            }
            arrayList.add(new StateConditionVM.Condition(4, i2, i3, i == 4));
        }
        while (true) {
            if (i5 >= arrayList.size()) {
                break;
            }
            if (((StateConditionVM.Condition) arrayList.get(i5)).isSel()) {
                this.lastSelectedPos = i5;
                break;
            }
            i5++;
        }
        this.mAdapter.setNewData(arrayList);
    }

    private void initRv() {
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent;
        BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder>(R.layout.item_env_status_detail_condition) { // from class: com.ltech.smarthome.ui.automation.ActSelectEnvStatusDetailCondition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, StateConditionVM.Condition condition) {
                String string;
                int type = condition.getType();
                if (type == 2) {
                    string = ActSelectEnvStatusDetailCondition.this.getString(R.string.app_str_above);
                } else if (type == 3) {
                    string = ActSelectEnvStatusDetailCondition.this.getString(R.string.app_str_blow);
                } else {
                    string = type != 4 ? "" : ActSelectEnvStatusDetailCondition.this.getString(R.string.app_str_range);
                }
                baseViewHolder.setText(R.id.tv_name, ActSelectEnvStatusDetailCondition.this.typeName + string);
                baseViewHolder.setGone(R.id.layout_cur_value, condition.isSel());
                baseViewHolder.setImageResource(R.id.iv_select, condition.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                if (condition.isSel()) {
                    baseViewHolder.addOnClickListener(R.id.layout_cur_value);
                    baseViewHolder.setText(R.id.tv_sub_name, String.format(ActSelectEnvStatusDetailCondition.this.getString(R.string.app_str_specify_parameters), ActSelectEnvStatusDetailCondition.this.typeName));
                    if (baseViewHolder.getLayoutPosition() == 2) {
                        baseViewHolder.setText(R.id.tv_value, condition.getMin() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER + condition.getMax() + ActSelectEnvStatusDetailCondition.this.typeUnit);
                        return;
                    }
                    baseViewHolder.setText(R.id.tv_value, condition.getMax() + ActSelectEnvStatusDetailCondition.this.typeUnit);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectEnvStatusDetailCondition.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (i != ActSelectEnvStatusDetailCondition.this.lastSelectedPos) {
                    StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectEnvStatusDetailCondition.this.mAdapter.getData().get(i);
                    condition.setSel(true);
                    ActSelectEnvStatusDetailCondition.this.mAdapter.setData(i, condition);
                    StateConditionVM.Condition condition2 = (StateConditionVM.Condition) ActSelectEnvStatusDetailCondition.this.mAdapter.getData().get(ActSelectEnvStatusDetailCondition.this.lastSelectedPos);
                    condition2.setSel(false);
                    ActSelectEnvStatusDetailCondition.this.mAdapter.setData(ActSelectEnvStatusDetailCondition.this.lastSelectedPos, condition2);
                    ActSelectEnvStatusDetailCondition.this.lastSelectedPos = i;
                }
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectEnvStatusDetailCondition.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectEnvStatusDetailCondition.this.mAdapter.getData().get(i);
                if (condition.getType() == 4) {
                    ActSelectEnvStatusDetailCondition.this.showMultiSelectedDialog(condition.getType(), i);
                } else {
                    ActSelectEnvStatusDetailCondition.this.showSingleSelectedDialog(condition.getType(), i);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMultiSelectedDialog(int type, final int pos) {
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
        SelectDoubleListWheelDialog.asDefault().setTitle(this.typeName + string).setSelectList(this.dialogList).setSelectPosition(this.selectValuePos).setSelect2Position(this.select2ValuePos).setSpecify(this.typeUnit).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<int[]>() { // from class: com.ltech.smarthome.ui.automation.ActSelectEnvStatusDetailCondition.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(int[] array) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectEnvStatusDetailCondition.this.mAdapter.getData().get(pos);
                condition.setMin(Integer.parseInt((String) ActSelectEnvStatusDetailCondition.this.dialogList.get(array[0])));
                condition.setMax(Integer.parseInt((String) ActSelectEnvStatusDetailCondition.this.dialogList.get(array[1])));
                ActSelectEnvStatusDetailCondition.this.mAdapter.setData(pos, condition);
            }
        }).setOutCancel(false).showDialog(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSingleSelectedDialog(int type, final int pos) {
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
        SelectListWheelDialog.asDefault(true).setTitle(this.typeName + string).setSelectList(this.dialogList).setSpecify(this.typeUnit).setSelectPosition(this.selectValuePos).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.automation.ActSelectEnvStatusDetailCondition.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectEnvStatusDetailCondition.this.mAdapter.getData().get(pos);
                condition.setMax(Integer.parseInt((String) ActSelectEnvStatusDetailCondition.this.dialogList.get(integer.intValue())));
                ActSelectEnvStatusDetailCondition.this.mAdapter.setData(pos, condition);
            }
        }).setOutCancel(false).showDialog(this.activity);
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
        deviceConditionParam.type = 1;
        deviceConditionParam.index = 5;
        deviceConditionParam.subIndex = this.type;
        deviceConditionParam.operator = condition.getType();
        if (condition.getType() == 4) {
            deviceConditionParam.value = condition.getMin();
            deviceConditionParam.value2 = condition.getMax();
        } else {
            deviceConditionParam.value = condition.getMax();
        }
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 9;
        SceneHelper.instance().eventtype = 2;
        setResult(5013);
        finishActivity();
    }
}