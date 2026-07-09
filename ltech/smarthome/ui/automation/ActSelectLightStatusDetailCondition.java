package com.ltech.smarthome.ui.automation;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectBleCurtainActionBinding;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.StateConditionVM;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectLightStatusDetailCondition extends VMActivity<ActSelectBleCurtainActionBinding, StateConditionVM> {
    private long deviceId;
    private String deviceName;
    private String floorName;
    private int keyNum;
    private BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> mAdapter;
    private String mac;
    private long placeId;
    private String productId;
    private String roomName;
    private int type;
    private int lastSelectedPos = 0;
    private List<String> dialogList = new ArrayList();

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
        this.keyNum = getIntent().getIntExtra(Constants.SWITCH_KEY_SUM, 1);
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
        int value = SceneHelper.instance().conditionParam != null ? ((DeviceConditionParam) SceneHelper.instance().conditionParam).getValue() : 1;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.add(new StateConditionVM.Condition(1, 0, 1, value == 1));
        arrayList.add(new StateConditionVM.Condition(1, 0, 0, value == 0));
        while (true) {
            if (i >= arrayList.size()) {
                break;
            }
            if (((StateConditionVM.Condition) arrayList.get(i)).isSel()) {
                this.lastSelectedPos = i;
                break;
            }
            i++;
        }
        this.mAdapter.setNewData(arrayList);
    }

    private void initRv() {
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent;
        BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<StateConditionVM.Condition, BaseViewHolder>(R.layout.item_env_status_detail_condition) { // from class: com.ltech.smarthome.ui.automation.ActSelectLightStatusDetailCondition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, StateConditionVM.Condition condition) {
                baseViewHolder.setText(R.id.tv_name, condition.getMax() == 0 ? ActSelectLightStatusDetailCondition.this.getString(R.string.app_str_off_status) : ActSelectLightStatusDetailCondition.this.getString(R.string.app_str_on_status));
                baseViewHolder.setImageResource(R.id.iv_select, condition.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectLightStatusDetailCondition.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (i != ActSelectLightStatusDetailCondition.this.lastSelectedPos) {
                    StateConditionVM.Condition condition = (StateConditionVM.Condition) ActSelectLightStatusDetailCondition.this.mAdapter.getData().get(i);
                    condition.setSel(true);
                    ActSelectLightStatusDetailCondition.this.mAdapter.setData(i, condition);
                    StateConditionVM.Condition condition2 = (StateConditionVM.Condition) ActSelectLightStatusDetailCondition.this.mAdapter.getData().get(ActSelectLightStatusDetailCondition.this.lastSelectedPos);
                    condition2.setSel(false);
                    ActSelectLightStatusDetailCondition.this.mAdapter.setData(ActSelectLightStatusDetailCondition.this.lastSelectedPos, condition2);
                    ActSelectLightStatusDetailCondition.this.lastSelectedPos = i;
                }
            }
        });
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
        deviceConditionParam.type = 3;
        deviceConditionParam.index = condition.getType();
        deviceConditionParam.subIndex = 1;
        deviceConditionParam.operator = 1;
        deviceConditionParam.value = condition.getMax();
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        SceneHelper.instance().eventtype = 2;
        setResult(5013);
        finishActivity();
    }
}