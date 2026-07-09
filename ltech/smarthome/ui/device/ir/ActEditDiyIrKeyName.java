package com.ltech.smarthome.ui.device.ir;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActEditKeyNameBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActEditDiyIrKeyName extends BaseNormalActivity<ActEditKeyNameBinding> {
    private long controlId;
    private BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_key_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_key_name));
        BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder>(this, R.layout.item_edit_diy_key_name, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.ir.ActEditDiyIrKeyName.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, DiyIrParam.DiyIrKey item) {
                helper.setText(R.id.tv_name, item.getKeyName());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActEditDiyIrKeyName$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActEditDiyIrKeyName.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActEditKeyNameBinding) this.mViewBinding).rvContent);
        ((ActEditKeyNameBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActEditKeyNameBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActEditKeyNameBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((ActEditKeyNameBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.ir.ActEditDiyIrKeyName.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActEditKeyName.class).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.KEY_POSITION, i).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(this.controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditDiyIrKeyName$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditDiyIrKeyName.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        DiyIrParam diyIrParam;
        if (device == null || (diyIrParam = (DiyIrParam) device.getParam(DiyIrParam.class)) == null) {
            return;
        }
        this.mAdapter.setNewData(diyIrParam.getDiyIrKeyList());
    }
}