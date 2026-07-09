package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.dialog.SelectLoopTimesDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectLoopTimesDialog extends SmartDialog<DialogPowerStateSelectorBinding> {
    public List<OnOffState> dataList;
    private MultipleItemRvAdapter<OnOffState, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private String saveText;
    private int times;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    public interface OnSaveListener {
        void cancel();

        boolean onSave(int times);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_power_state_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPowerStateSelectorBinding, SelectLoopTimesDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPowerStateSelectorBinding viewBinding, final SelectLoopTimesDialog dialog) {
            if (dialog.times == -1) {
                dialog.selectPosition = 0;
            } else {
                dialog.selectPosition = 1;
            }
            dialog.initRv(viewBinding);
            if (dialog.saveText != null) {
                viewBinding.tvSave.setText(dialog.saveText);
            }
            viewBinding.appCompatTextView33.setText(ActivityUtils.getTopActivity().getString(R.string.app_str_loop_times_title));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectLoopTimesDialog.AnonymousClass1.lambda$convertView$0(SelectLoopTimesDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectLoopTimesDialog selectLoopTimesDialog, View view) {
            int id = view.getId();
            if (id != R.id.tv_cancel) {
                if (id == R.id.tv_save && selectLoopTimesDialog.selectPosition != -1) {
                    if (selectLoopTimesDialog.mOnSaveListener != null) {
                        if (selectLoopTimesDialog.mOnSaveListener.onSave(selectLoopTimesDialog.dataList.get(selectLoopTimesDialog.selectPosition).getMainValue())) {
                            selectLoopTimesDialog.dismissDialog();
                            return;
                        }
                        return;
                    }
                    selectLoopTimesDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectLoopTimesDialog.dismissDialog();
            if (selectLoopTimesDialog.mOnSaveListener != null) {
                selectLoopTimesDialog.mOnSaveListener.cancel();
            }
        }
    }

    public static SelectLoopTimesDialog asDefault() {
        return (SelectLoopTimesDialog) new SelectLoopTimesDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogPowerStateSelectorBinding viewBinding) {
        this.dataList = getContentList();
        MultipleItemRvAdapter<OnOffState, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<OnOffState, BaseViewHolder>(this.dataList) { // from class: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(OnOffState onOffState) {
                return onOffState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog.2.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_sub;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return OnOffState.TYPE_DEFAULT;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, OnOffState data, int position) {
                        helper.setText(R.id.tv_name, data.getName());
                        helper.setGone(R.id.tv_sub_name, false);
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (helper.getLayoutPosition() == SelectLoopTimesDialog.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        } else {
                            helper.setGone(R.id.iv_select, false);
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog.2.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_loop_value;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return OnOffState.TYPE_DIY;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(final BaseViewHolder helper, final OnOffState data, int position) {
                        helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        ((StepSetView) helper.getView(R.id.sb_brt)).setRange(1, 255);
                        ((StepSetView) helper.getView(R.id.sb_brt)).setProgress(data.getMainValue());
                        if (SelectLoopTimesDialog.this.selectPosition == 0) {
                            helper.setText(R.id.tv_sub_value, "1");
                            ((StepSetView) helper.getView(R.id.sb_brt)).setProgress(1);
                        } else if (SelectLoopTimesDialog.this.times >= 1) {
                            helper.setText(R.id.tv_sub_value, SelectLoopTimesDialog.this.times + "");
                            ((StepSetView) helper.getView(R.id.sb_brt)).setProgress(SelectLoopTimesDialog.this.times);
                        }
                        if (helper.getLayoutPosition() == SelectLoopTimesDialog.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true).setEnabled(R.id.sb_brt, true);
                            ((StepSetView) helper.getView(R.id.sb_brt)).setOnProgressChangeListener(new StepSetView.OnProgressChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog.2.2.1
                                @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
                                public void onProgressChanged(int v, boolean finish, boolean fromUser) {
                                    data.setMainValue(v);
                                    helper.setText(R.id.tv_sub_value, data.getMainValue() + "");
                                }
                            });
                        } else {
                            helper.setGone(R.id.iv_select, false).setEnabled(R.id.sb_brt, false);
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectLoopTimesDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SelectLoopTimesDialog.this.lambda$initRv$0(baseQuickAdapter, view, i);
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
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, getString(R.string.app_str_loop_times_infinite), "", -1));
        arrayList.add(new OnOffState(OnOffState.TYPE_DIY, getString(R.string.mode_diy), getString(R.string.app_str_loop_times), 1));
        return arrayList;
    }

    public SelectLoopTimesDialog setTimes(int times) {
        this.times = times;
        return this;
    }

    public int getTimes() {
        return this.times;
    }

    public static final class OnOffState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int mainValue;
        private String name;
        private String subName;
        private int type;

        OnOffState(int type, String name, String subName, int mainValue) {
            this.type = type;
            this.name = name;
            this.subName = subName;
            this.mainValue = mainValue;
        }

        public void setMainValue(int mainValue) {
            this.mainValue = mainValue;
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

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }
    }

    public SelectLoopTimesDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public SelectLoopTimesDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_power_state_selector";
    }
}