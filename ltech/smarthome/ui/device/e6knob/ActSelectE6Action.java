package com.ltech.smarthome.ui.device.e6knob;

import android.content.Intent;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect4Binding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ParamMap;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectE6Action extends BaseNormalActivity<ActSelect4Binding> implements ISelectAction {
    private int lightType;
    private BaseQuickAdapter<ParamMap, BaseViewHolder> mAdapter;
    private String productId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select4;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initView$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_action));
        setBackImage(R.mipmap.icon_back);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        initAdapter();
    }

    private void initAdapter() {
        BaseQuickAdapter<ParamMap, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ParamMap, BaseViewHolder>(R.layout.item_e6d_action, getList(this.lightType)) { // from class: com.ltech.smarthome.ui.device.e6knob.ActSelectE6Action.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ParamMap item) {
                helper.setText(R.id.tv_name, item.getName()).setGone(R.id.iv_more, item.getValue() != 1);
                if (item.getValue() == 28 && ActSelectE6Action.this.lightType > 2) {
                    helper.setBackgroundRes(R.id.tv_tip, R.drawable.shape_gray_5).setGone(R.id.tv_tip, true).setText(R.id.tv_tip, ActSelectE6Action.this.getString(R.string.normal_mode)).setTextColor(R.id.tv_tip, ContextCompat.getColor(ActSelectE6Action.this.activity, R.color.color_text_black));
                } else if (item.getValue() == 32 || item.getValue() == 33 || item.getValue() == 34) {
                    helper.setBackgroundRes(R.id.tv_tip, R.drawable.shape_light_red_bt_5_1).setGone(R.id.tv_tip, true).setText(R.id.tv_tip, ActSelectE6Action.this.getString(R.string.profession_mode)).setTextColor(R.id.tv_tip, ContextCompat.getColor(ActSelectE6Action.this.activity, R.color.color_text_red));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActSelectE6Action$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectE6Action.this.lambda$initAdapter$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelect4Binding) this.mViewBinding).rvContent);
        ((ActSelect4Binding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelect4Binding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        E6Helper.instance().goSelectActionById(this.lightType, this.mAdapter.getItem(i).getValue());
    }

    private List<ParamMap> getList(int lightType) {
        ArrayList arrayList = new ArrayList();
        if (lightType == 1) {
            arrayList.add(new ParamMap(getString(R.string.dim_static), 28));
        } else if (lightType == 2) {
            arrayList.add(new ParamMap(getString(R.string.ct_static), 28));
        } else {
            arrayList.add(new ParamMap(getString(R.string.rgb_static), 28));
        }
        if (ProductId.ID_KNOB_PANEL_E6D.equals(this.productId)) {
            if (lightType > 2) {
                arrayList.add(new ParamMap(getString(R.string.set_xy_brt), 32));
                arrayList.add(new ParamMap(getString(R.string.set_rgbwaf_brt), 33));
            }
            arrayList.add(new ParamMap(getString(R.string.call_scene), 12));
        } else if (ProductId.ID_KNOB_PANEL_E6M.equals(this.productId)) {
            arrayList.add(new ParamMap(getString(R.string.str_channel), 34));
        }
        if (!ProductId.ID_KNOB_PANEL_E6D.equals(this.productId)) {
            arrayList.add(new ParamMap(getString(R.string.theme), 29));
            if (lightType >= 2) {
                arrayList.add(new ParamMap(getString(R.string.general_mode), 31));
            }
            arrayList.add(new ParamMap(getString(R.string.diy_mode), 30));
        }
        arrayList.add(new ParamMap(getString(R.string.light_off_1), 1));
        return arrayList;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            lambda$initView$0(this);
        }
    }
}