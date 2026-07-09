package com.ltech.smarthome.ui.device.central.tepanel;

import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtAirBinding;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.central.airpro.FreshAirInfoItem;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtAir extends VMFragment<FtAirBinding, ActTePanelVM> {
    private BaseQuickAdapter<FreshAirInfoItem, BaseViewHolder> infoAdapter;
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;
    public G4teBleParam mParam = new G4teBleParam();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_air;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        initRvInfo();
        initRvKey();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((ActTePanelVM) this.mViewModel).g4teParam.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAir$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAir.this.lambda$startObserve$0((G4teBleParam) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).airPowerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAir$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAir.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).offLine.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAir$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAir.this.lambda$startObserve$2((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(G4teBleParam g4teBleParam) {
        if (((ActTePanelVM) this.mViewModel).isAcControl) {
            return;
        }
        this.mParam = g4teBleParam;
        if (g4teBleParam.airOn == 0) {
            ((ActTePanelVM) this.mViewModel).airPowerOn.setValue(false);
        } else {
            ((ActTePanelVM) this.mViewModel).airPowerOn.setValue(true);
        }
        if (g4teBleParam.airErrorCode == 0) {
            ((FtAirBinding) this.mViewBinding).layoutErrorTip.setVisibility(8);
        } else {
            ((FtAirBinding) this.mViewBinding).layoutErrorTip.setVisibility(0);
            ((FtAirBinding) this.mViewBinding).tvErrorTip.setText(getString(R.string.device_error_tip, Integer.valueOf(g4teBleParam.airErrorCode)));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (!((ActTePanelVM) this.mViewModel).offLine.getValue().booleanValue() && bool.booleanValue()) {
            updateUi(this.mParam);
            ((FtAirBinding) this.mViewBinding).ivCircle.setImageResource(R.mipmap.pic_fresh_air_open);
        } else {
            ((FtAirBinding) this.mViewBinding).ivCircle.setImageResource(R.mipmap.pic_fresh_air_close);
        }
        ((FtAirBinding) this.mViewBinding).groupOn.setVisibility((((ActTePanelVM) this.mViewModel).offLine.getValue().booleanValue() || !bool.booleanValue()) ? 8 : 0);
        this.mAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeKeyItemList(this.mParam));
        this.infoAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeAirInfoItemList(this.mParam));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActTePanelVM) this.mViewModel).airPowerOn.setValue(Boolean.valueOf(this.mParam.airOn != 0));
        }
    }

    private void initRvInfo() {
        BaseQuickAdapter<FreshAirInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<FreshAirInfoItem, BaseViewHolder>(this, R.layout.item_fresh_air_info, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAir.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, FreshAirInfoItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName()).setText(R.id.tv_value, item.getValue());
            }
        };
        this.infoAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((FtAirBinding) this.mViewBinding).rvInfo);
        ((FtAirBinding) this.mViewBinding).rvInfo.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ((FtAirBinding) this.mViewBinding).rvInfo.setHasFixedSize(true);
    }

    private void initRvKey() {
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(R.layout.item_ir_key_te, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAir.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setText(R.id.tv_name, item.getName()).setEnabled(R.id.tv_name, item.isEnable());
                AppCompatImageView appCompatImageView = (AppCompatImageView) helper.getView(R.id.iv_icon);
                appCompatImageView.setImageResource(item.getIconRes());
                if (helper.getBindingAdapterPosition() != 3) {
                    if (((ActTePanelVM) FtAir.this.mViewModel).airPowerOn.getValue().booleanValue() && item.isEnable()) {
                        appCompatImageView.setImageAlpha(255);
                        return;
                    } else {
                        appCompatImageView.setImageAlpha(160);
                        return;
                    }
                }
                if (FtAir.this.mParam.airTimeValue == 0) {
                    appCompatImageView.setImageResource(R.mipmap.icon_ir_timing);
                    helper.setText(R.id.tv_name, R.string.timing);
                    return;
                }
                appCompatImageView.setImageResource(R.mipmap.icon_timing_sel);
                if (FtAir.this.mParam.airTimeOn == 0) {
                    helper.setText(R.id.tv_name, FtAir.this.getString(R.string.timing_close, Float.valueOf(r7.mParam.airTimeValue * 0.5f)));
                } else {
                    helper.setText(R.id.tv_name, FtAir.this.getString(R.string.timing_open, Float.valueOf(r7.mParam.airTimeValue * 0.5f)));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAir$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtAir.this.lambda$initRvKey$3(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtAirBinding) this.mViewBinding).rvContent);
        ((FtAirBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ((FtAirBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvKey$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (i == 3) {
            if (this.mParam.airTimeValue == 0) {
                ((ActTePanelVM) this.mViewModel).showTimingDialog();
            } else {
                ((ActTePanelVM) this.mViewModel).showCancelTimingDialog();
            }
        } else {
            if (!Boolean.TRUE.equals(((ActTePanelVM) this.mViewModel).airPowerOn.getValue())) {
                return;
            }
            ((ActTePanelVM) this.mViewModel).airChangeWindSpeed(i);
            updateUi(this.mParam);
        }
        this.mAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeKeyItemList(this.mParam));
    }

    private void updateUi(G4teBleParam param) {
        ((FtAirBinding) this.mViewBinding).tvTemp.setText(param.pmValue + "");
        ((FtAirBinding) this.mViewBinding).tvState.setText(R.string.pm25_unit);
    }
}