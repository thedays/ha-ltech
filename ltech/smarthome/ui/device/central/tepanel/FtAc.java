package com.ltech.smarthome.ui.device.central.tepanel;

import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtAcBinding;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.view.CircleBar;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtAc extends VMFragment<FtAcBinding, ActTePanelVM> {
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;
    public G4teBleParam mParam = new G4teBleParam();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_ac;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        initRvKey();
        ((FtAcBinding) this.mViewBinding).circleBar.setProgressChangeListener(new CircleBar.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.CircleBar.OnProgressChangeListener
            public final void onProgressChanged(float f, int i, boolean z) {
                FtAc.this.lambda$initView$0(f, i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(float f, int i, boolean z) {
        ((FtAcBinding) this.mViewBinding).tvTemp.setText(String.valueOf(i));
        if (z) {
            LHomeLog.i(getClass(), "percent -->" + f + "  progress -->" + i);
            ((ActTePanelVM) this.mViewModel).tempChange.setValue(Integer.valueOf(i));
            ((ActTePanelVM) this.mViewModel).changeTemperature();
        }
    }

    private void initRvKey() {
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(R.layout.item_ir_key_te, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setText(R.id.tv_name, item.getName()).setEnabled(R.id.tv_name, item.isEnable());
                AppCompatImageView appCompatImageView = (AppCompatImageView) helper.getView(R.id.iv_icon);
                appCompatImageView.setImageResource(item.getIconRes());
                if (helper.getBindingAdapterPosition() != 3) {
                    if (((ActTePanelVM) FtAc.this.mViewModel).powerOn.getValue().booleanValue() && item.isEnable()) {
                        appCompatImageView.setImageAlpha(255);
                        return;
                    } else {
                        appCompatImageView.setImageAlpha(160);
                        return;
                    }
                }
                if (FtAc.this.mParam.timeValue == 0) {
                    appCompatImageView.setImageResource(R.mipmap.icon_ir_timing);
                    helper.setText(R.id.tv_name, R.string.timing);
                    return;
                }
                appCompatImageView.setImageResource(R.mipmap.icon_timing_sel);
                if (FtAc.this.mParam.timeOn == 0) {
                    helper.setText(R.id.tv_name, FtAc.this.getString(R.string.timing_close, Float.valueOf(r7.mParam.timeValue * 0.5f)));
                } else {
                    helper.setText(R.id.tv_name, FtAc.this.getString(R.string.timing_open, Float.valueOf(r7.mParam.timeValue * 0.5f)));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtAc.this.lambda$initRvKey$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtAcBinding) this.mViewBinding).rvContent);
        ((FtAcBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ((FtAcBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvKey$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.mAdapter.getData().get(i).isEnable()) {
            if (i == 0) {
                ((ActTePanelVM) this.mViewModel).changeMode(this.mParam);
                updateUi();
            } else if (i == 1) {
                ((ActTePanelVM) this.mViewModel).changeWindSpeed(this.mParam);
                updateUi();
            } else if (i == 2) {
                ((ActTePanelVM) this.mViewModel).changeWindDirection(this.mParam);
                updateUi();
            } else if (i == 3) {
                if (this.mParam.timeValue == 0) {
                    ((ActTePanelVM) this.mViewModel).showTimingDialog();
                } else {
                    ((ActTePanelVM) this.mViewModel).showCancelTimingDialog();
                }
            }
            this.mAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeKeyItemList(this.mParam));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((ActTePanelVM) this.mViewModel).g4teParam.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAc.this.lambda$startObserve$2((G4teBleParam) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).tempChange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAc.this.lambda$startObserve$3((Integer) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).powerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAc.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).offLine.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAc$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAc.this.lambda$startObserve$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(G4teBleParam g4teBleParam) {
        if (((ActTePanelVM) this.mViewModel).isAcControl) {
            this.mParam = g4teBleParam;
            if (g4teBleParam.on == 0) {
                ((ActTePanelVM) this.mViewModel).powerOn.setValue(false);
            } else {
                ((ActTePanelVM) this.mViewModel).powerOn.setValue(true);
            }
            if (g4teBleParam.errorCode == 0) {
                ((FtAcBinding) this.mViewBinding).layoutErrorTip.setVisibility(8);
            } else {
                ((FtAcBinding) this.mViewBinding).layoutErrorTip.setVisibility(0);
                ((FtAcBinding) this.mViewBinding).tvErrorTip.setText(getString(R.string.device_error_tip, Integer.valueOf(g4teBleParam.errorCode)));
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        this.mParam.temperature = num.intValue();
        ((FtAcBinding) this.mViewBinding).tvTemp.setText(num + "");
        ((FtAcBinding) this.mViewBinding).circleBar.setProgress(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        LHomeLog.i(getClass(), "on=" + bool);
        if (bool.booleanValue()) {
            updateUi();
        }
        ((FtAcBinding) this.mViewBinding).groupOn.setVisibility((((ActTePanelVM) this.mViewModel).offLine.getValue().booleanValue() || !bool.booleanValue()) ? 8 : 0);
        this.mAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeKeyItemList(this.mParam));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActTePanelVM) this.mViewModel).powerOn.setValue(Boolean.valueOf(this.mParam.on != 0));
        }
    }

    private void updateUi() {
        if (this.mParam.temperature == 0) {
            this.mParam.temperature = 16;
        }
        ((FtAcBinding) this.mViewBinding).tvTemp.setText(this.mParam.temperature + "");
        if (this.mParam.mode == 8) {
            ((FtAcBinding) this.mViewBinding).circleBar.setBarColor(ContextCompat.getColor(requireContext(), R.color.bar_blue_1), ContextCompat.getColor(requireContext(), R.color.bar_red));
        } else {
            ((FtAcBinding) this.mViewBinding).circleBar.setBarColor(ContextCompat.getColor(requireContext(), R.color.bar_green), ContextCompat.getColor(requireContext(), R.color.bar_blue));
        }
        ((FtAcBinding) this.mViewBinding).circleBar.setMax(30);
        ((FtAcBinding) this.mViewBinding).circleBar.setMin(16);
        ((FtAcBinding) this.mViewBinding).circleBar.setProgress(this.mParam.temperature);
        ((FtAcBinding) this.mViewBinding).tvState.setText(((ActTePanelVM) this.mViewModel).getStateString(this.mParam.mode, this.mParam.speed));
    }
}