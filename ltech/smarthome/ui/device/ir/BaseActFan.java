package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActFanBinding;
import com.ltech.smarthome.ui.device.ir.BaseIrVM;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes4.dex */
public abstract class BaseActFan<VM extends BaseIrVM> extends BaseIrVMActivity<ActFanBinding, VM> {
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;

    protected abstract int devicePicRes();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_fan;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActFanBinding) this.mViewBinding).ivDevice.setImageResource(devicePicRes());
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(R.layout.item_ir_key, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.ir.BaseActFan.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.getView(R.id.layout_bg).getLayoutParams().height = ((ActFanBinding) BaseActFan.this.mViewBinding).rvContent.getMeasuredHeight() / 2;
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                helper.setEnabled(R.id.tv_name, item.isEnable());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.BaseActFan$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                BaseActFan.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActFanBinding) this.mViewBinding).rvContent);
        ((ActFanBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActFanBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        IrKeyItem irKeyItem = this.mAdapter.getData().get(i);
        if (irKeyItem.isEnable()) {
            if (irKeyItem.getFid() < 0) {
                showFunDialog();
            } else {
                ((BaseIrVM) this.mViewModel).sendIrControl(irKeyItem.getFid());
                ((BaseIrVM) this.mViewModel).clickAnimate(view);
            }
        }
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseIrVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseActFan$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseActFan.this.lambda$startObserve$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActFanBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActFanBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void getRcCodeSuccess(int ridPos) {
        ((ActFanBinding) this.mViewBinding).cardviewAdd.setVisibility(0);
        this.mAdapter.setNewData(((BaseIrVM) this.mViewModel).getKeyItemList());
        ((ActFanBinding) this.mViewBinding).tvIndex.setText(String.format(Locale.US, "%d/%d", Integer.valueOf(ridPos + 1), Integer.valueOf(((BaseIrVM) this.mViewModel).rids.size())));
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void initControlView() {
        this.mAdapter.setNewData(((BaseIrVM) this.mViewModel).getKeyItemList());
        setEditImage(R.mipmap.ic_setting);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void initSelectActionView() {
        this.mAdapter.setNewData(((BaseIrVM) this.mViewModel).getKeyItemList());
        setEditString(getString(R.string.save));
    }
}