package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectListBinding;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ParamMap;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectDefaultConditionDialog extends BaseNormalActivity<DialogSelectListBinding> implements ISelectAction {
    long deviceId;
    String deviceName;
    String floorName;
    private boolean isEdit;
    private BaseQuickAdapter<ParamMap, BaseViewHolder> mAdapter;
    String mac;
    String productId;
    String roomName;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.dialog_select_list;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initActionView$3(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((DialogSelectListBinding) this.mViewBinding).tvTitle.setText(getString(R.string.please_choose));
        ((DialogSelectListBinding) this.mViewBinding).tvCancel.setText(getString(R.string.cancel));
        ((DialogSelectListBinding) this.mViewBinding).tvConfirm.setText(getString(R.string.confirm));
        ((DialogSelectListBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectDefaultConditionDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectDefaultConditionDialog.this.lambda$initView$0((View) obj);
            }
        }));
        this.floorName = getIntent().getStringExtra(Constants.FLOOR_NAME);
        this.roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
        this.deviceName = getIntent().getStringExtra("device_name");
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        ArrayList arrayList = new ArrayList();
        setItemData(arrayList);
        BaseQuickAdapter<ParamMap, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ParamMap, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectDefaultConditionDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ParamMap item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setBackgroundRes(R.id.iv_select, helper.getBindingAdapterPosition() == ActSelectDefaultConditionDialog.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectDefaultConditionDialog$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectDefaultConditionDialog.this.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((DialogSelectListBinding) this.mViewBinding).rvContent);
        ((DialogSelectListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((DialogSelectListBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        int i = 0;
        ((DefaultItemAnimator) ((DialogSelectListBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        int intExtra = getIntent().getIntExtra(Constants.CONDITION_INDEX, -1);
        if (intExtra >= 0) {
            this.isEdit = true;
            while (true) {
                if (i >= this.mAdapter.getData().size()) {
                    break;
                }
                if (intExtra == this.mAdapter.getData().get(i).getValue()) {
                    this.selectPosition = i;
                    break;
                }
                i++;
            }
        }
        showAsDialog(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
            back();
        } else {
            if (id != R.id.tv_confirm) {
                return;
            }
            edit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
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

    private void setItemData(List<ParamMap> mItemAction) {
        ArrayList arrayList = new ArrayList();
        if (ProductId.ID_DOOR_SENSOR.equals(this.productId)) {
            arrayList.add(new ParamMap(getString(R.string.function_open), 2));
            arrayList.add(new ParamMap(getString(R.string.function_close), 1));
        }
        mItemAction.addAll(arrayList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        DeviceConditionParam deviceConditionParam = new DeviceConditionParam();
        deviceConditionParam.deviceid = this.deviceId;
        deviceConditionParam.type = 1;
        deviceConditionParam.index = this.mAdapter.getData().get(this.selectPosition).getValue();
        deviceConditionParam.productid = this.productId;
        deviceConditionParam.floorRoom = this.floorName + this.roomName;
        deviceConditionParam.deviceName = this.deviceName;
        deviceConditionParam.mac = this.mac;
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        if (!this.isEdit) {
            lambda$initActionView$3(this);
        } else {
            setResult(3003);
            finishActivity();
        }
    }
}