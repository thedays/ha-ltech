package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectListBinding;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectCg485Condition extends BaseNormalActivity<ActSelectListBinding> implements ISelectAction {
    private BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> adapter;
    private long deviceId;
    String deviceName;
    String floorName;
    boolean isEdit;
    String mac;
    String productId;
    String roomName;
    private List<Rs485ExtParam.Instruct> selectList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_list;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelectListBinding) this.mViewBinding).tvTitle.setText(getString(R.string.please_choose));
        ((ActSelectListBinding) this.mViewBinding).tvConfirm.setText(getString(R.string.confirm));
        ((ActSelectListBinding) this.mViewBinding).tvCancel.setText(getString(R.string.cancel));
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.floorName = getIntent().getStringExtra(Constants.FLOOR_NAME);
        this.roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
        this.deviceName = getIntent().getStringExtra("device_name");
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        if (getIntent().getIntExtra(Constants.CONDITION_INDEX, -1) > 0) {
            this.isEdit = true;
        }
        this.selectList.addAll(Cg485Helper.getCategory(2).getAction());
        initAdapter();
        ((ActSelectListBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Condition$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectCg485Condition.this.lambda$initView$0((View) obj);
            }
        }));
        showAsDialog(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
            finishActivity();
        } else {
            if (id != R.id.tv_confirm) {
                return;
            }
            if (this.selectPosition == -1) {
                SmartToast.showShort(R.string.please_choose);
            } else {
                saveCondition();
            }
        }
    }

    private void initAdapter() {
        BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder>(R.layout.item_select_list_with_icon, this.selectList) { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Condition.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Rs485ExtParam.Instruct item) {
                helper.setText(R.id.tv_name, item.getCmd()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectCg485Condition.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Condition$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectCg485Condition.this.lambda$initAdapter$1(baseQuickAdapter2, view, i);
            }
        });
        this.adapter.bindToRecyclerView(((ActSelectListBinding) this.mViewBinding).rvContent);
        ((ActSelectListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectListBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectPosition = i;
        this.adapter.notifyDataSetChanged();
    }

    private void saveCondition() {
        DeviceConditionParam deviceConditionParam = new DeviceConditionParam();
        deviceConditionParam.deviceid = this.deviceId;
        deviceConditionParam.type = 5;
        deviceConditionParam.index = this.adapter.getData().get(this.selectPosition).getCmdIdx();
        deviceConditionParam.productid = this.productId;
        deviceConditionParam.floorRoom = this.floorName + this.roomName;
        deviceConditionParam.deviceName = this.deviceName;
        deviceConditionParam.mac = this.mac;
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        if (!this.isEdit) {
            lambda$edit$1(this);
        } else {
            setResult(3003);
            finishActivity();
        }
    }
}