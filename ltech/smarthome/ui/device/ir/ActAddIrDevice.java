package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActAddIrDeviceBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddIrDevice extends BaseNormalActivity<ActAddIrDeviceBinding> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_ir_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_ir_device));
        String stringExtra = getIntent().getStringExtra(Constants.PRODUCT_ID);
        List<ActAddDeviceVM.ProductItem> irProduct = ActAddDeviceVM.getIrProduct();
        if (stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL) || stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || stringExtra.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
            for (int i = 0; i < 3; i++) {
                irProduct.remove(irProduct.size() - 1);
            }
        }
        initDeviceRv(irProduct);
    }

    private void initDeviceRv(List<ActAddDeviceVM.ProductItem> productItemList) {
        final BaseQuickAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder>(this, R.layout.item_go, productItemList) { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDevice.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActAddDeviceVM.ProductItem item) {
                helper.setText(R.id.tv_main, item.productInfo.getDefaultName(this.mContext)).setImageResource(R.id.iv_icon, item.productInfo.getDefaultIconRes()).setImageResource(R.id.iv_go, R.mipmap.icon_more);
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActAddIrDevice$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActAddIrDevice.this.lambda$initDeviceRv$0(baseQuickAdapter, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActAddIrDeviceBinding) this.mViewBinding).rvContent);
        ((ActAddIrDeviceBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActAddIrDeviceBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceRv$0(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        ActAddDeviceVM.ProductItem productItem = (ActAddDeviceVM.ProductItem) baseQuickAdapter.getItem(i);
        nav(productItem.productInfo, productItem.navClz);
    }

    private void nav(ProductInfo info, Class clazz) {
        if (clazz == null) {
            return;
        }
        ConfigHelper.instance().productInfo = info;
        NavUtils.destination(clazz).navigation(this);
    }
}