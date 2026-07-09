package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectConditionDeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ParamMap;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelecEurConditionAction extends BaseNormalActivity<ActSelectConditionDeviceBinding> implements ISelectAction {
    private Device device;
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
    protected int provideLayoutId() {
        return R.layout.act_select_condition_device;
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
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        this.floorName = getIntent().getStringExtra(Constants.FLOOR_NAME);
        this.roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
        this.deviceName = getIntent().getStringExtra("device_name");
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        this.device = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        ArrayList arrayList = new ArrayList();
        setItemData(arrayList);
        BaseQuickAdapter<ParamMap, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ParamMap, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelecEurConditionAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ParamMap item) {
                helper.setText(R.id.tv_name, item.getName());
                if ((!ProductId.ID_AS_PANEL_U4S.equals(ActSelecEurConditionAction.this.productId) && !ProductId.ID_AS_PANEL_U5S.equals(ActSelecEurConditionAction.this.productId) && !ProductId.ID_EUR_PANEL_EB5.equals(ActSelecEurConditionAction.this.productId) && !ProductId.ID_RC_B5.equals(ActSelecEurConditionAction.this.productId)) || EurHelper.isPositionBindScene(ActSelecEurConditionAction.this.device, helper.getBindingAdapterPosition())) {
                    helper.setTextColor(R.id.tv_name, ActSelecEurConditionAction.this.getResources().getColor(R.color.color_text_black)).setBackgroundRes(R.id.iv_select, helper.getBindingAdapterPosition() == ActSelecEurConditionAction.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                } else {
                    helper.setTextColor(R.id.tv_name, ActSelecEurConditionAction.this.getResources().getColor(R.color.color_text_dark_gray));
                }
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelecEurConditionAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelecEurConditionAction.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent);
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        int intExtra = getIntent().getIntExtra(Constants.CONDITION_INDEX, -1);
        if (intExtra >= 0) {
            this.isEdit = true;
            for (int i = 0; i < this.mAdapter.getData().size(); i++) {
                if (intExtra == this.mAdapter.getData().get(i).getValue()) {
                    this.selectPosition = i;
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2;
        if (((ProductId.ID_AS_PANEL_U4S.equals(this.productId) || ProductId.ID_AS_PANEL_U5S.equals(this.productId) || ProductId.ID_EUR_PANEL_EB5.equals(this.productId) || ProductId.ID_RC_B5.equals(this.productId)) && !EurHelper.isPositionBindScene(this.device, i)) || (i2 = this.selectPosition) == i) {
            return;
        }
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

    private void setItemData(List<ParamMap> mItemAction) {
        ArrayList arrayList = new ArrayList();
        if (ProductId.ID_EUR_PANEL_EB6.equals(this.productId)) {
            arrayList.add(new ParamMap(getString(R.string.single_click_knob), 1));
            arrayList.add(new ParamMap(getString(R.string.double_click_knob), 2));
            arrayList.add(new ParamMap(getString(R.string.rotate_knob), 9));
            arrayList.add(new ParamMap(getString(R.string.press_and_rotate), 10));
        }
        if (ProductRepository.isEurPanel(this.productId) || ProductRepository.isAsPanel(this.productId)) {
            arrayList.add(new ParamMap(getString(R.string.click_scene_1), 11));
            arrayList.add(new ParamMap(getString(R.string.click_scene_2), 12));
            arrayList.add(new ParamMap(getString(R.string.click_scene_3), 13));
            if (!ProductId.ID_EUR_PANEL_EB5.equals(this.productId)) {
                arrayList.add(new ParamMap(getString(R.string.click_scene_4), 14));
            }
            if (ProductId.ID_EUR_PANEL_EB8.equals(this.productId) || ProductId.ID_AS_PANEL_UB8.equals(this.productId)) {
                arrayList.add(new ParamMap(getString(R.string.click_scene_5), 15));
                arrayList.add(new ParamMap(getString(R.string.click_scene_6), 16));
                arrayList.add(new ParamMap(getString(R.string.click_scene_7), 17));
                arrayList.add(new ParamMap(getString(R.string.click_scene_8), 18));
            }
        } else {
            arrayList.add(new ParamMap(getString(R.string.click_scene_1), 1));
            arrayList.add(new ParamMap(getString(R.string.click_scene_2), 2));
            arrayList.add(new ParamMap(getString(R.string.click_scene_3), 3));
            if (!ProductId.ID_RC_B5.equals(this.productId)) {
                arrayList.add(new ParamMap(getString(R.string.click_scene_4), 4));
            }
            if (ProductId.ID_RC_B8.equals(this.productId)) {
                arrayList.add(new ParamMap(getString(R.string.click_scene_5), 5));
                arrayList.add(new ParamMap(getString(R.string.click_scene_6), 6));
                arrayList.add(new ParamMap(getString(R.string.click_scene_7), 7));
                arrayList.add(new ParamMap(getString(R.string.click_scene_8), 8));
            }
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
        if (ProductId.ID_EUR_PANEL_EB6.equals(this.productId)) {
            deviceConditionParam.type = 6;
        } else {
            deviceConditionParam.type = 1;
        }
        deviceConditionParam.index = this.mAdapter.getData().get(this.selectPosition).getValue();
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