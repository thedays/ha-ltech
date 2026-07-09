package com.ltech.smarthome.base;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;

/* loaded from: classes3.dex */
public abstract class BaseSingleSelectActivity<T> extends BaseListActivity<T> {
    private String productId;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    protected void onItemClick(int position) {
    }

    protected abstract void save();

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.base.BaseSingleSelectActivity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseSingleSelectActivity.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
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
        onItemClick(i);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else if (ProductId.ID_SMART_PANEL_S6B.equals(this.productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.base.BaseSingleSelectActivity$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    BaseSingleSelectActivity.this.lambda$edit$1(imageTipDialog);
                }
            });
        } else {
            save();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(ImageTipDialog imageTipDialog) {
        save();
        imageTipDialog.dismissDialog();
    }
}