package com.ltech.smarthome.ui.device.smartpanel;

import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseSmartPanelActionListStringActivity<V extends ActSelectBinding, VM extends ActSmartPanelSelectActionVM> extends VMActivity<V, VM> {
    protected BaseItemDraggableAdapter<String, BaseViewHolder> mAdapter;
    protected List<String> dataList = new ArrayList();
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    protected abstract void convertView(BaseViewHolder helper, String item);

    protected abstract List<String> getList();

    protected abstract int itemLayout();

    protected void onItemClick(int position) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    protected abstract void save();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setUpData();
        BaseItemDraggableAdapter<String, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<String, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                BaseSmartPanelActionListStringActivity.this.convertView(helper, item);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseSmartPanelActionListStringActivity.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
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

    protected void setUpData() {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.dataList.addAll(getList());
    }

    protected void setDataList(List<String> list) {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.dataList.clear();
        this.dataList.addAll(list);
        this.mAdapter.replaceData(list);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else if (ProductId.ID_SMART_PANEL_S6B.equals(((ActSmartPanelSelectActionVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListStringActivity$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    BaseSmartPanelActionListStringActivity.this.lambda$edit$1(imageTipDialog);
                }
            });
        } else {
            showLoadingDialog(getString(R.string.subscribing));
            save();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(ImageTipDialog imageTipDialog) {
        save();
        imageTipDialog.dismissDialog();
    }
}