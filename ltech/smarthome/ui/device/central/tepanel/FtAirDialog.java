package com.ltech.smarthome.ui.device.central.tepanel;

import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtAirDialogBinding;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtAirDialog extends VMFragment<FtAirDialogBinding, ActTePanelVM> {
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;
    public G4teBleParam mParam = new G4teBleParam();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_air_dialog;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        initRvContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((ActTePanelVM) this.mViewModel).g4teParam.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAirDialog$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAirDialog.this.lambda$startObserve$0((G4teBleParam) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).airPowerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAirDialog$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAirDialog.this.lambda$startObserve$1((Boolean) obj);
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
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        updateUi(this.mParam);
        this.mAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeKeyItemList(this.mParam));
    }

    private void initRvContent() {
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(R.layout.item_ir_key_te, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAirDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setText(R.id.tv_name, item.getName()).setEnabled(R.id.tv_name, item.isEnable());
                AppCompatImageView appCompatImageView = (AppCompatImageView) helper.getView(R.id.iv_icon);
                appCompatImageView.setImageResource(item.getIconRes());
                if (helper.getBindingAdapterPosition() != 3) {
                    if (Boolean.TRUE.equals(((ActTePanelVM) FtAirDialog.this.mViewModel).airPowerOn.getValue()) && item.isEnable()) {
                        appCompatImageView.setImageAlpha(255);
                        return;
                    } else {
                        appCompatImageView.setImageAlpha(160);
                        return;
                    }
                }
                if (FtAirDialog.this.mParam.airTimeValue == 0) {
                    appCompatImageView.setImageResource(R.mipmap.icon_ir_timing);
                    helper.setText(R.id.tv_name, R.string.timing);
                    return;
                }
                appCompatImageView.setImageResource(R.mipmap.icon_timing_sel);
                if (FtAirDialog.this.mParam.airTimeOn == 0) {
                    helper.setText(R.id.tv_name, FtAirDialog.this.getString(R.string.timing_close, Float.valueOf(r7.mParam.airTimeValue * 0.5f)));
                } else {
                    helper.setText(R.id.tv_name, FtAirDialog.this.getString(R.string.timing_open, Float.valueOf(r7.mParam.airTimeValue * 0.5f)));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAirDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtAirDialog.this.lambda$initRvContent$2(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtAirDialogBinding) this.mViewBinding).rvContent);
        ((FtAirDialogBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ((FtAirDialogBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvContent$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
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
        if (param != null) {
            ((FtAirDialogBinding) this.mViewBinding).tvPmValue.setText(String.valueOf(param.pmValue));
            ((FtAirDialogBinding) this.mViewBinding).tvTempValue.setText(String.valueOf(param.airRoomTemp));
        }
    }
}