package com.ltech.smarthome.ui.device.central.tepanel;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtAcDialogBinding;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager;
import com.ltech.smarthome.view.layoutmanager.ScaleTransformer;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtAcDialog extends VMFragment<FtAcDialogBinding, ActTePanelVM> {
    private int curTempPosition;
    private GalleryLayoutManager layoutManager;
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;
    private BaseQuickAdapter<Integer, BaseViewHolder> tempAdapter;
    private G4teBleParam mParam = new G4teBleParam();
    private boolean isScrollByuser = true;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_ac_dialog;
    }

    @Override // com.ltech.smarthome.base.VMFragment
    protected boolean shareVM() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        initTempRv();
        initRvKey();
        ((FtAcDialogBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtAcDialog.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.layout_temp_left /* 2131297678 */:
                if (((ActTePanelVM) this.mViewModel).powerOn.getValue().booleanValue()) {
                    int max = Math.max(this.mParam.temperature - 1, ((ActTePanelVM) this.mViewModel).getTempList().get(0).intValue());
                    this.mParam.temperature = max;
                    ((ActTePanelVM) this.mViewModel).changeTemperature();
                    ((ActTePanelVM) this.mViewModel).tempChange.setValue(Integer.valueOf(max));
                    break;
                }
                break;
            case R.id.layout_temp_right /* 2131297679 */:
                if (((ActTePanelVM) this.mViewModel).powerOn.getValue().booleanValue()) {
                    int min = Math.min(this.mParam.temperature + 1, ((ActTePanelVM) this.mViewModel).getTempList().get(((ActTePanelVM) this.mViewModel).getTempList().size() - 1).intValue());
                    this.mParam.temperature = min;
                    ((ActTePanelVM) this.mViewModel).changeTemperature();
                    ((ActTePanelVM) this.mViewModel).tempChange.setValue(Integer.valueOf(min));
                    break;
                }
                break;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((ActTePanelVM) this.mViewModel).g4teParam.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAcDialog.this.lambda$startObserve$1((G4teBleParam) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).tempChange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAcDialog.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((ActTePanelVM) this.mViewModel).powerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtAcDialog.this.lambda$startObserve$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(G4teBleParam g4teBleParam) {
        if (((ActTePanelVM) this.mViewModel).isAcControl) {
            this.mParam = g4teBleParam;
            if (g4teBleParam.on == 0) {
                ((ActTePanelVM) this.mViewModel).powerOn.setValue(false);
            } else {
                ((ActTePanelVM) this.mViewModel).powerOn.setValue(true);
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        if (((ActTePanelVM) this.mViewModel).getTempList().indexOf(num) != this.curTempPosition) {
            this.isScrollByuser = false;
            ((FtAcDialogBinding) this.mViewBinding).rvTemp.smoothScrollToPosition(((ActTePanelVM) this.mViewModel).getTempList().indexOf(num));
        }
        this.tempAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        this.mAdapter.setNewData(((ActTePanelVM) this.mViewModel).getTeKeyItemList(this.mParam));
        ((ActTePanelVM) this.mViewModel).tempChange.setValue(Integer.valueOf(this.mParam.temperature));
        if (bool.booleanValue()) {
            ((FtAcDialogBinding) this.mViewBinding).rvTemp.setOnFlingListener(null);
            ((FtAcDialogBinding) this.mViewBinding).rvTemp.clearOnScrollListeners();
            this.layoutManager.setCanScroll(true);
            this.layoutManager.attach(((FtAcDialogBinding) this.mViewBinding).rvTemp, this.mParam.temperature - 16);
            return;
        }
        this.layoutManager.setCanScroll(false);
    }

    private void initTempRv() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_temp, ((ActTePanelVM) this.mViewModel).getTempList()) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.getView(R.id.layout_bg).getLayoutParams().height = ((FtAcDialogBinding) FtAcDialog.this.mViewBinding).rvContent.getMeasuredHeight();
                helper.getView(R.id.layout_bg).getLayoutParams().width = ((FtAcDialogBinding) FtAcDialog.this.mViewBinding).rvContent.getMeasuredWidth() / 6;
                helper.setText(R.id.tv_temp, item + "°");
                ((AppCompatTextView) helper.getView(R.id.tv_temp)).getPaint().setFakeBoldText(true);
                if (Boolean.TRUE.equals(((ActTePanelVM) FtAcDialog.this.mViewModel).powerOn.getValue()) && FtAcDialog.this.mParam.temperature == item.intValue()) {
                    helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_blue));
                } else {
                    helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_text_gray));
                }
            }
        };
        this.tempAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((FtAcDialogBinding) this.mViewBinding).rvTemp);
        ((FtAcDialogBinding) this.mViewBinding).rvTemp.setHasFixedSize(true);
        ((DefaultItemAnimator) ((FtAcDialogBinding) this.mViewBinding).rvTemp.getItemAnimator()).setSupportsChangeAnimations(false);
        GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
        this.layoutManager = galleryLayoutManager;
        galleryLayoutManager.setItemTransformer(new ScaleTransformer());
        this.layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager.OnItemSelectedListener
            public final void onItemSelected(RecyclerView recyclerView, View view, int i, boolean z) {
                FtAcDialog.this.lambda$initTempRv$4(recyclerView, view, i, z);
            }
        });
        this.layoutManager.attach(((FtAcDialogBinding) this.mViewBinding).rvTemp, 0);
        ((FtAcDialogBinding) this.mViewBinding).rvTemp.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(15.0f), 0, ConvertUtils.dp2px(15.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTempRv$4(RecyclerView recyclerView, View view, int i, boolean z) {
        if (z) {
            return;
        }
        this.curTempPosition = i;
        this.mParam.temperature = this.tempAdapter.getData().get(i).intValue();
        if (this.isScrollByuser) {
            ((ActTePanelVM) this.mViewModel).changeTemperature();
        }
        this.tempAdapter.notifyDataSetChanged();
        this.isScrollByuser = true;
    }

    private void initRvKey() {
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(R.layout.item_ir_key_te, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setText(R.id.tv_name, item.getName()).setEnabled(R.id.tv_name, item.isEnable());
                AppCompatImageView appCompatImageView = (AppCompatImageView) helper.getView(R.id.iv_icon);
                appCompatImageView.setImageResource(item.getIconRes());
                if (helper.getBindingAdapterPosition() != 3) {
                    if (Boolean.TRUE.equals(((ActTePanelVM) FtAcDialog.this.mViewModel).powerOn.getValue()) && item.isEnable()) {
                        appCompatImageView.setImageAlpha(255);
                        return;
                    } else {
                        appCompatImageView.setImageAlpha(160);
                        return;
                    }
                }
                if (FtAcDialog.this.mParam.timeValue == 0) {
                    appCompatImageView.setImageResource(R.mipmap.icon_ir_timing);
                    helper.setText(R.id.tv_name, R.string.timing);
                    return;
                }
                appCompatImageView.setImageResource(R.mipmap.icon_timing_sel);
                if (FtAcDialog.this.mParam.timeOn == 0) {
                    helper.setText(R.id.tv_name, FtAcDialog.this.getString(R.string.timing_close, Float.valueOf(r7.mParam.timeValue * 0.5f)));
                } else {
                    helper.setText(R.id.tv_name, FtAcDialog.this.getString(R.string.timing_open, Float.valueOf(r7.mParam.timeValue * 0.5f)));
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.FtAcDialog$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtAcDialog.this.lambda$initRvKey$5(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((FtAcDialogBinding) this.mViewBinding).rvContent);
        ((FtAcDialogBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ((FtAcDialogBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvKey$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.mAdapter.getData().get(i).isEnable()) {
            if (i == 0) {
                ((ActTePanelVM) this.mViewModel).changeMode(this.mParam);
            } else if (i == 1) {
                ((ActTePanelVM) this.mViewModel).changeWindSpeed(this.mParam);
            } else if (i == 2) {
                ((ActTePanelVM) this.mViewModel).changeWindDirection(this.mParam);
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
}