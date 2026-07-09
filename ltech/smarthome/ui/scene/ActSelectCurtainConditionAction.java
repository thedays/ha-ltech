package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectConditionDeviceBinding;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectCurtainConditionAction extends BaseNormalActivity<ActSelectConditionDeviceBinding> implements ISelectAction {
    String deviceName;
    long device_id;
    String floorName;
    private boolean isEdit;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;
    String mac;
    String productId;
    String roomName;
    int switch_sum;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_condition_device;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$save$5(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        this.floorName = getIntent().getStringExtra(Constants.FLOOR_NAME);
        this.roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
        this.deviceName = getIntent().getStringExtra("device_name");
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        int intExtra = getIntent().getIntExtra(Constants.CONDITION_INDEX, -1);
        if (intExtra > 0) {
            this.isEdit = true;
            if (intExtra == 2) {
                this.selectPosition = 2;
            } else if (intExtra == 3) {
                this.selectPosition = 1;
            } else {
                this.selectPosition = 0;
            }
        }
        ArrayList arrayList = new ArrayList();
        setItemData(arrayList);
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectCurtainConditionAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_name, item.getMainText()).setText(R.id.tv_sub, item.getSubText()).setGone(R.id.tv_sub, !StringUtils.isTrimEmpty(item.getSubText())).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectCurtainConditionAction.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectCurtainConditionAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectCurtainConditionAction.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent);
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
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

    private void setItemData(List<GoItem> mItemAction) {
        this.switch_sum = getIntent().getIntExtra(Constants.SWITCH_KEY_SUM, 1);
        this.device_id = getIntent().getLongExtra("device_id", 1L);
        mItemAction.add(new GoItem().setMainText(getString(R.string.when_curtain_on)).setSubText(""));
        mItemAction.add(new GoItem().setMainText(getString(R.string.when_curtain_stop)).setSubText(""));
        mItemAction.add(new GoItem().setMainText(getString(R.string.when_curtain_close)).setSubText(""));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        DeviceConditionParam deviceConditionParam = new DeviceConditionParam();
        deviceConditionParam.deviceid = this.device_id;
        deviceConditionParam.type = 7;
        int i = this.selectPosition;
        if (i == 1) {
            deviceConditionParam.index = 3;
        } else if (i == 2) {
            deviceConditionParam.index = 2;
        } else {
            deviceConditionParam.index = 1;
        }
        deviceConditionParam.productid = this.productId;
        deviceConditionParam.floorRoom = this.floorName + this.roomName;
        deviceConditionParam.deviceName = this.deviceName;
        deviceConditionParam.mac = this.mac;
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        if (!this.isEdit) {
            lambda$save$5(this);
        } else {
            setResult(3003);
            finishActivity();
        }
    }
}