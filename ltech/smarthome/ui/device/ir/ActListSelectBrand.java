package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActListSelectBrand extends BaseListActivity<String> {
    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go_1;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_brand));
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActListSelectBrand$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActListSelectBrand.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActMotorConfig.class).withString(Constants.BRAND_NAME, (String) this.mAdapter.getData().get(i)).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        ArrayList arrayList = new ArrayList();
        if (ProductId.ID_IR_HANGER.equals(ConfigHelper.instance().productInfo.getProductId())) {
            arrayList.add(getString(R.string.hanger_lbest));
            return arrayList;
        }
        if (ProductId.ID_IR_CURTAIN.equals(ConfigHelper.instance().productInfo.getProductId())) {
            arrayList.add(getString(R.string.motor_aoke));
            arrayList.add(getString(R.string.motor_baizhen));
            arrayList.add(getString(R.string.motor_duya));
            arrayList.add(getString(R.string.motor_lansen));
            arrayList.add(getString(R.string.motor_idemo));
            arrayList.add(getString(R.string.motor_sien));
            arrayList.add(getString(R.string.motor_chuangming));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_main, item);
        ((TextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }
}