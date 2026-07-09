package com.ltech.smarthome.ui.automation.trigger;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectConditionDeviceBinding;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.utils.Constants;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseTriggerCondition extends BaseNormalActivity<ActSelectConditionDeviceBinding> {
    protected long deviceId;
    protected String deviceName;
    protected String floorName;
    protected BaseQuickAdapter<Condition, BaseViewHolder> mAdapter;
    protected String mac;
    protected String productId;
    protected String roomName;
    protected DeviceConditionParam deviceConditionParam = new DeviceConditionParam();
    protected int selectedPos = -1;

    protected void convertView(BaseViewHolder helper, Condition condition) {
    }

    protected abstract List<Condition> getList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_condition_device;
    }

    protected void showSingleSelectedDialog(int operator, int pos) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.floorName = getIntent().getStringExtra(Constants.FLOOR_NAME);
        this.roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
        this.deviceName = getIntent().getStringExtra("device_name");
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        initAdapter();
    }

    protected void initAdapter() {
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent;
        BaseQuickAdapter<Condition, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Condition, BaseViewHolder>(R.layout.item_env_status_detail_condition, getList()) { // from class: com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Condition condition) {
                if (condition.sel) {
                    BaseTriggerCondition.this.selectedPos = helper.getBindingAdapterPosition();
                }
                helper.setText(R.id.tv_name, condition.title);
                helper.setGone(R.id.layout_cur_value, condition.sel && condition.operator != 0 && condition.setValue == 0);
                helper.setImageResource(R.id.iv_select, condition.sel ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                helper.setText(R.id.tv_sub_name, condition.subTitle);
                BaseTriggerCondition.this.convertView(helper, condition);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.trigger.BaseTriggerCondition$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                BaseTriggerCondition.this.lambda$initAdapter$0(baseQuickAdapter2, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (i != this.selectedPos) {
            Condition condition = this.mAdapter.getData().get(i);
            this.deviceConditionParam.index = condition.type;
            this.deviceConditionParam.operator = condition.operator;
            this.deviceConditionParam.subIndex = condition.subIndex;
            this.deviceConditionParam.value = condition.setValue;
            if (this.deviceConditionParam.operator == 0 || this.deviceConditionParam.value != 0) {
                refreshData(i);
            } else {
                showSingleSelectedDialog(this.deviceConditionParam.operator, i);
            }
        }
    }

    public void refreshData(int position) {
        if (this.selectedPos != -1) {
            Condition condition = this.mAdapter.getData().get(this.selectedPos);
            condition.sel = false;
            this.mAdapter.setData(this.selectedPos, condition);
        }
        Condition condition2 = this.mAdapter.getData().get(position);
        condition2.sel = true;
        this.mAdapter.setData(position, condition2);
    }

    public static class Condition {
        public int operator;
        public boolean sel;
        public int setValue;
        public int subIndex;
        public String subTitle;
        public String title;
        public int type;

        public Condition(String title, int type, boolean sel) {
            this.title = title;
            this.type = type;
            this.sel = sel;
        }

        public Condition(String title, int type, int setValue, boolean sel) {
            this.title = title;
            this.type = type;
            this.setValue = setValue;
            this.sel = sel;
        }

        public Condition(String title, int type, int operator, int setValue, boolean sel) {
            this.title = title;
            this.type = type;
            this.operator = operator;
            this.setValue = setValue;
            this.sel = sel;
        }

        public Condition(String title, String secondTitle, int type, int subIndex, int operator, boolean sel) {
            this.title = title;
            this.subTitle = secondTitle;
            this.type = type;
            this.subIndex = subIndex;
            this.operator = operator;
            this.sel = sel;
        }

        public Condition(String title, int type, int subIndex, int operator, int setValue) {
            this.title = title;
            this.type = type;
            this.subIndex = subIndex;
            this.operator = operator;
            this.setValue = setValue;
        }
    }
}