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
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.TrigToBleExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.TbUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSwitchConditionActionDialog extends BaseNormalActivity<ActSelectConditionDeviceBinding> implements ISelectAction {
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
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

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
    public /* synthetic */ void lambda$edit$0(BaseNormalActivity baseNormalActivity) {
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
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        int intExtra = getIntent().getIntExtra(Constants.CONDITION_INDEX, -1);
        if (intExtra > 0) {
            this.isEdit = true;
            if (this.productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                this.selectPosition = intExtra - 1;
            } else if (!this.productId.equals(ProductId.ID_SMART_SWITCH_SQ) && !this.productId.equals(ProductId.ID_SMART_SWITCH_SQB) && !this.productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                this.selectPosition = (intExtra - 1) / 2;
            } else if (intExtra < 9) {
                this.selectPosition = intExtra - 1;
            } else {
                this.selectPosition = intExtra - 7;
            }
        }
        ArrayList arrayList = new ArrayList();
        setItemData(arrayList);
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectSwitchConditionActionDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_name, item.getMainText()).setText(R.id.tv_sub, item.getSubText()).setGone(R.id.tv_sub, !StringUtils.isTrimEmpty(item.getSubText())).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectSwitchConditionActionDialog.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectSwitchConditionActionDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectSwitchConditionActionDialog.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent);
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectConditionDeviceBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        if (ProductId.ID_SMART_PANEL_S6B.equals(this.productId)) {
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutRoot.setBackgroundColor(getResources().getColor(R.color.white));
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutS6.setVisibility(0);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutPic.setVisibility(8);
            showAsDialog(0.7f);
            return;
        }
        if (ProductId.ID_SMART_SWITCH_S6_PRO.equals(this.productId)) {
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutS6.setVisibility(8);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.auto_s6_pic);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutPic.setVisibility(0);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).tvTip.setVisibility(8);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).tvLogoTip.setVisibility(8);
            setTitle(getString(R.string.select_single_action));
            showAsDialog(0.7f);
            return;
        }
        if (ProductId.ID_SMART_SWITCH_S6.equals(this.productId)) {
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutS6.setVisibility(8);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.auto_s6_pic);
            ((ActSelectConditionDeviceBinding) this.mViewBinding).layoutPic.setVisibility(0);
            setTitle(getString(R.string.select_single_action));
            showAsDialog(0.7f);
            return;
        }
        showAsDialog(0.8f);
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
        TbUtils.ContentState contentState;
        this.switch_sum = getIntent().getIntExtra(Constants.SWITCH_KEY_SUM, 1);
        this.device_id = getIntent().getLongExtra("device_id", 1L);
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.device_id);
        String[] stringArray = getResources().getStringArray(R.array.key4_switch_click_condition);
        String[] strArr = new String[0];
        if (this.switch_sum == 1) {
            if (this.productId.equals(ProductId.ID_SMART_SWITCH_SQ) || this.productId.equals(ProductId.ID_SMART_SWITCH_SQB) || this.productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                stringArray = getResources().getStringArray(R.array.key_sq_condition);
            } else {
                stringArray = getResources().getStringArray(R.array.key1_switch_click_condition);
            }
        }
        if (this.switch_sum == 2) {
            stringArray = getResources().getStringArray(R.array.key2_switch_click_condition);
        }
        if (this.switch_sum == 3) {
            stringArray = getResources().getStringArray(R.array.key3_switch_click_condition);
        }
        int i = 4;
        if (this.switch_sum == 4) {
            stringArray = getResources().getStringArray(R.array.key4_switch_click_condition);
        }
        if (this.switch_sum == 6) {
            if (this.productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO) || this.productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                stringArray = getResources().getStringArray(R.array.smart_scene_panel_s6_key_select);
            } else {
                stringArray = getResources().getStringArray(R.array.key6_switch_click_condition);
            }
        }
        if (this.switch_sum == 8) {
            stringArray = getResources().getStringArray(R.array.key8_switch_click_condition);
        }
        int i2 = this.switch_sum;
        if (i2 == 15) {
            stringArray = getResources().getStringArray(R.array.key_trig_to_ble_condition);
        } else if ((i2 == 16 || i2 == 17) && deviceByDeviceId != null) {
            TrigToBleExtParam trigToBleExtParam = new TrigToBleExtParam();
            trigToBleExtParam.fillMapWithString(deviceByDeviceId.getExtParam());
            ArrayList arrayList = new ArrayList();
            int i3 = this.switch_sum;
            if (i3 == 16) {
                stringArray = getResources().getStringArray(R.array.key_trig8_to_ble_condition);
                strArr = getResources().getStringArray(R.array.key_trig8_to_ble_sub_condition);
                i = 8;
            } else if (i3 != 17) {
                i = 15;
            } else {
                stringArray = getResources().getStringArray(R.array.key_trig4_to_ble_condition);
                strArr = getResources().getStringArray(R.array.key_trig4_to_ble_sub_condition);
            }
            for (int i4 = 1; i4 <= i; i4++) {
                if (trigToBleExtParam.getRelateInfo(i4) != null) {
                    arrayList.add(trigToBleExtParam.getRelateInfo(i4));
                } else {
                    arrayList.add(new TrigToBleExtParam.TrigRelateInfo());
                }
            }
            List<TbUtils.ContentState> contentStates = TbUtils.getContentStates(this, arrayList);
            for (int i5 = 0; i5 < stringArray.length; i5++) {
                if (i5 < contentStates.size() && (contentState = contentStates.get(i5)) != null && !StringUtils.isTrimEmpty(contentState.name)) {
                    stringArray[i5] = contentState.name;
                }
            }
        }
        for (int i6 = 0; i6 < stringArray.length; i6++) {
            mItemAction.add(new GoItem().setMainText(stringArray[i6]).setSubText(strArr.length > 0 ? strArr[i6] : ""));
        }
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
        if (this.switch_sum < 15) {
            if (this.productId.equals(ProductId.ID_SMART_SWITCH_SQ) || this.productId.equals(ProductId.ID_SMART_SWITCH_SQB) || this.productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                deviceConditionParam.type = 4;
                int i = this.selectPosition;
                if (i < 2) {
                    deviceConditionParam.index = i + 1;
                } else {
                    deviceConditionParam.index = i + 7;
                }
            } else {
                deviceConditionParam.type = 1;
                deviceConditionParam.index = (this.selectPosition * 2) + 1;
            }
        } else {
            deviceConditionParam.type = 3;
            deviceConditionParam.index = this.selectPosition + 1;
        }
        deviceConditionParam.productid = this.productId;
        deviceConditionParam.floorRoom = this.floorName + this.roomName;
        deviceConditionParam.deviceName = this.deviceName;
        deviceConditionParam.mac = this.mac;
        SceneHelper.instance().conditionParam = deviceConditionParam;
        SceneHelper.instance().conditionParamType = 8;
        if (!this.isEdit) {
            lambda$edit$0(this);
        } else {
            setResult(3003);
            finishActivity();
        }
    }
}