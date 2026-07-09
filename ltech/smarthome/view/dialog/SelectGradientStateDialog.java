package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogPowerStateSelectorBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.SelectGradientStateDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectGradientStateDialog extends SmartDialog<DialogPowerStateSelectorBinding> {
    public List<OnOffState> dataList;
    private int gradientTime;
    private MultipleItemRvAdapter<OnOffState, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    public interface OnSaveListener {
        void cancel();

        boolean onSave(OnOffState onOffState, int selectPos);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_power_state_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectGradientStateDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPowerStateSelectorBinding, SelectGradientStateDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPowerStateSelectorBinding viewBinding, final SelectGradientStateDialog dialog) {
            dialog.initRv(viewBinding);
            viewBinding.appCompatTextView33.setText(ActivityUtils.getTopActivity().getString(R.string.app_gradient_mode));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectGradientStateDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectGradientStateDialog.AnonymousClass1.lambda$convertView$0(SelectGradientStateDialog.this, (View) obj);
                }
            }));
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$convertView$0(SelectGradientStateDialog selectGradientStateDialog, View view) {
            int id = view.getId();
            if (id != R.id.tv_cancel) {
                if (id == R.id.tv_save && selectGradientStateDialog.selectPosition != -1) {
                    if (selectGradientStateDialog.mOnSaveListener != null) {
                        if (selectGradientStateDialog.mOnSaveListener.onSave((OnOffState) selectGradientStateDialog.mAdapter.getItem(selectGradientStateDialog.selectPosition), selectGradientStateDialog.selectPosition)) {
                            selectGradientStateDialog.dismissDialog();
                            return;
                        }
                        return;
                    }
                    selectGradientStateDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectGradientStateDialog.dismissDialog();
            if (selectGradientStateDialog.mOnSaveListener != null) {
                selectGradientStateDialog.mOnSaveListener.cancel();
            }
        }
    }

    public static SelectGradientStateDialog asDefault() {
        return (SelectGradientStateDialog) new SelectGradientStateDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectGradientStateDialog$2, reason: invalid class name */
    class AnonymousClass2 extends MultipleItemRvAdapter<OnOffState, BaseViewHolder> {
        AnonymousClass2(List data) {
            super(data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public int getViewType(OnOffState onOffState) {
            return onOffState.getType();
        }

        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public void registerItemProvider() {
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectGradientStateDialog.2.1
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_subtext_ver;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DEFAULT;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, OnOffState data, int position) {
                    helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == SelectGradientStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectGradientStateDialog.2.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_subtext_ver_onoff_diy;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(final BaseViewHolder helper, OnOffState data, final int position) {
                    helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == SelectGradientStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true).setText(R.id.tv_brt, LightUtils.getProgressHasBelowOne(((OnOffState) SelectGradientStateDialog.this.mAdapter.getData().get(1)).getSubValue())).setEnabled(R.id.sb_brt, true).setGone(R.id.layout_brt, true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(SelectGradientStateDialog.this.gradientTime);
                        helper.setText(R.id.tv_brt, SelectGradientStateDialog.this.gradientTime + SelectGradientStateDialog.this.getString(R.string.min_new));
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setMax(60);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setMinProgressValue(0);
                        Rect bounds = ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().getBounds();
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgressDrawable(ContextCompat.getDrawable(SelectGradientStateDialog.this.getContext(), R.drawable.style_seekbar_red));
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().setBounds(bounds);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectGradientStateDialog.2.2.1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                helper.setText(R.id.tv_brt, progress + SelectGradientStateDialog.this.getString(R.string.min_new));
                                ((OnOffState) SelectGradientStateDialog.this.mAdapter.getData().get(position)).setSubValue(progress);
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                SelectGradientStateDialog.this.gradientTime = seekBar.getProgress();
                                helper.setText(R.id.tv_brt, seekBar.getProgress() + SelectGradientStateDialog.this.getString(R.string.min_new));
                                ((OnOffState) SelectGradientStateDialog.this.mAdapter.getData().get(position)).setSubValue(seekBar.getProgress());
                            }
                        });
                        return;
                    }
                    helper.setGone(R.id.iv_select, false).setText(R.id.tv_brt, LightUtils.getProgressHasBelowOneWithoutPercent(((OnOffState) SelectGradientStateDialog.this.mAdapter.getData().get(1)).getSubValue()) + SelectGradientStateDialog.this.getString(R.string.min_new)).setEnabled(R.id.sb_brt, false).setGone(R.id.layout_brt, true);
                    ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(((OnOffState) SelectGradientStateDialog.this.mAdapter.getData().get(1)).getSubValue());
                    Rect bounds2 = ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().getBounds();
                    ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgressDrawable(ContextCompat.getDrawable(SelectGradientStateDialog.this.getContext(), R.drawable.style_seekbar_gray));
                    ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().setBounds(bounds2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogPowerStateSelectorBinding viewBinding) {
        this.dataList = getContentList();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(getContentList());
        this.mAdapter = anonymousClass2;
        anonymousClass2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectGradientStateDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SelectGradientStateDialog.this.lambda$initRv$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent);
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
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
    }

    private List<OnOffState> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, getString(R.string.app_gradient_full), getString(R.string.app_gradient_full_tip), 0, 0));
        arrayList.add(new OnOffState(OnOffState.TYPE_DIY, getString(R.string.app_gradient_diy), getString(R.string.app_gradient_diy_tip), 0, this.gradientTime));
        return arrayList;
    }

    public void setGradientTime(int gradientTime) {
        if (gradientTime == 0) {
            this.gradientTime = 1;
        } else {
            this.gradientTime = gradientTime;
        }
    }

    public int getGradientTime() {
        return this.gradientTime;
    }

    public static final class OnOffState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int mainValue;
        private String name;
        private String subName;
        private int subValue;
        private int type;

        OnOffState(int type, String name, String subName, int mainValue, int subValue) {
            this.type = type;
            this.name = name;
            this.subName = subName;
            this.mainValue = mainValue;
            this.subValue = subValue;
        }

        int getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }

        public int getMainValue() {
            return this.mainValue;
        }

        public int getSubValue() {
            return this.subValue;
        }

        void setSubValue(int subValue) {
            this.subValue = subValue;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }
    }

    public SelectGradientStateDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public int getSelectPosition() {
        return this.selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "SelectGradientStateDialog";
    }
}