package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogPowerStateSelectorBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectKValueDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectKValueDialog extends SmartDialog<DialogPowerStateSelectorBinding> {
    private Object controlObject;
    public List<OnOffState> dataList;
    private MultipleItemRvAdapter<OnOffState, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private String saveText;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;
    private int max = 6500;
    private int min = 2700;
    private boolean isNeedQuery = true;

    public interface OnSaveListener {
        void cancel();

        boolean onSave(OnOffState onOffState, int selectPos);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_power_state_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectKValueDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPowerStateSelectorBinding, SelectKValueDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPowerStateSelectorBinding viewBinding, final SelectKValueDialog dialog) {
            dialog.initRv(viewBinding);
            if (dialog.saveText != null) {
                viewBinding.tvSave.setText(dialog.saveText);
            }
            viewBinding.appCompatTextView33.setText(ActivityUtils.getTopActivity().getString(R.string.k_range));
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectKValueDialog.AnonymousClass1.lambda$convertView$0(SelectKValueDialog.this, (View) obj);
                }
            }));
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$convertView$0(SelectKValueDialog selectKValueDialog, View view) {
            int id = view.getId();
            if (id != R.id.tv_cancel) {
                if (id == R.id.tv_save && selectKValueDialog.selectPosition != -1) {
                    if (selectKValueDialog.mOnSaveListener != null) {
                        if (selectKValueDialog.mOnSaveListener.onSave((OnOffState) selectKValueDialog.mAdapter.getItem(selectKValueDialog.selectPosition), selectKValueDialog.selectPosition)) {
                            selectKValueDialog.dismissDialog();
                            return;
                        }
                        return;
                    }
                    selectKValueDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectKValueDialog.dismissDialog();
            if (selectKValueDialog.mOnSaveListener != null) {
                selectKValueDialog.mOnSaveListener.cancel();
            }
        }
    }

    public static SelectKValueDialog asDefault() {
        return (SelectKValueDialog) new SelectKValueDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogPowerStateSelectorBinding viewBinding) {
        this.dataList = getContentList();
        MultipleItemRvAdapter<OnOffState, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<OnOffState, BaseViewHolder>(this.dataList) { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(OnOffState onOffState) {
                return onOffState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog.2.1
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
                        if (helper.getLayoutPosition() == SelectKValueDialog.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        } else {
                            helper.setGone(R.id.iv_select, false);
                        }
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog.2.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_k_value;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return OnOffState.TYPE_DIY;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(final BaseViewHolder helper, final OnOffState data, int position) {
                        helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        ((RangeSeekBar) helper.getView(R.id.sb_brt)).setRange(1000.0f, 10000.0f, 50.0f);
                        ((RangeSeekBar) helper.getView(R.id.sb_brt)).setProgressDrawableId(R.mipmap.pic_ct);
                        if (data.getMin() > data.getMax()) {
                            int min = data.getMin();
                            data.setMin(data.getMax());
                            data.setMax(min);
                        }
                        if (data.getMin() < 1000) {
                            data.setMin(1000);
                        }
                        if (data.getMax() > 10000 || data.getMax() < 1) {
                            data.setMax(10000);
                        }
                        ((RangeSeekBar) helper.getView(R.id.sb_brt)).setProgress(data.getMin(), data.getMax());
                        if (helper.getLayoutPosition() == SelectKValueDialog.this.selectPosition) {
                            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true).setGone(R.id.layout_brt, true).setEnabled(R.id.sb_brt, true).setText(R.id.tv_k_left, data.getMin() + "K").setText(R.id.tv_k_right, data.getMax() + "K");
                            ((RangeSeekBar) helper.getView(R.id.sb_brt)).setOnRangeChangedListener(new OnRangeChangedListener(this) { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog.2.2.1
                                @Override // com.jaygoo.widget.OnRangeChangedListener
                                public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                                }

                                @Override // com.jaygoo.widget.OnRangeChangedListener
                                public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                                }

                                @Override // com.jaygoo.widget.OnRangeChangedListener
                                public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                                    int i = (int) leftValue;
                                    if (LightUtils.getStepK(i, 50) >= 1000) {
                                        data.setMin(LightUtils.getStepK(i, 50));
                                    }
                                    int i2 = (int) rightValue;
                                    if (LightUtils.getStepK(i2, 50) >= 1000) {
                                        data.setMax(LightUtils.getStepK(i2, 50));
                                    }
                                    helper.setText(R.id.tv_k_left, data.getMin() + "K");
                                    helper.setText(R.id.tv_k_right, data.getMax() + "K");
                                }
                            });
                            return;
                        }
                        helper.setGone(R.id.iv_select, false).setGone(R.id.layout_brt, true).setEnabled(R.id.sb_brt, false).setText(R.id.tv_k_left, data.getMin() + "K").setText(R.id.tv_k_right, data.getMax() + "K");
                        ((RangeSeekBar) helper.getView(R.id.sb_brt)).setProgressDrawableId(R.drawable.style_seekbar_gray);
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SelectKValueDialog.this.lambda$initRv$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent);
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        setK(this.max, this.min);
        queryKRange();
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

    private void queryKRange() {
        Object obj = this.controlObject;
        if (obj == null || !this.isNeedQuery) {
            return;
        }
        CmdAssistant.getQueryCmdAssistant(obj, new int[0]).queryCtRange(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg == null || responseMsg.getResData().length() < 28) {
                    return;
                }
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16);
                int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(24, 28), 16);
                if (parseInt < 1000 || parseInt > 10000) {
                    parseInt = 1000;
                }
                if (parseInt2 < parseInt || parseInt2 > 10000) {
                    parseInt2 = 10000;
                }
                SelectKValueDialog.this.setK(parseInt2, parseInt);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setK(int maxK, int minK) {
        if (minK == 2700 && maxK == 6500) {
            this.selectPosition = 0;
        } else {
            this.selectPosition = 1;
            this.mAdapter.getData().get(1).setMax(maxK);
            this.mAdapter.getData().get(1).setMin(minK);
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.view.dialog.SelectKValueDialog.4
            @Override // java.lang.Runnable
            public void run() {
                SelectKValueDialog.this.mAdapter.notifyItemChanged(0);
                SelectKValueDialog.this.mAdapter.notifyItemChanged(1);
            }
        });
    }

    private List<OnOffState> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, getString(R.string.mode_default), getString(R.string.default_k_range), 1, 0));
        arrayList.add(new OnOffState(OnOffState.TYPE_DIY, getString(R.string.actual_k_range_tip), getString(R.string.diy_k_range), 2, 0));
        return arrayList;
    }

    public SelectKValueDialog setMax(int max) {
        this.max = max;
        return this;
    }

    public SelectKValueDialog setIsNeedQuery(boolean isNeedQuery) {
        this.isNeedQuery = isNeedQuery;
        return this;
    }

    public int getMax() {
        return this.max;
    }

    public SelectKValueDialog setMin(int min) {
        this.min = min;
        return this;
    }

    public int getMin() {
        return this.min;
    }

    public static final class OnOffState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int mainValue;
        private int max = 6500;
        private int min = 2700;
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

        public int getMax() {
            return this.max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return this.min;
        }

        public void setMin(int min) {
            this.min = min;
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

    public void notifyDialog(ResponseMsg msg) {
        int parseInt = Integer.parseInt(msg.getResData().substring(16, 18), 16);
        int size = this.mAdapter.getData().size();
        for (int i = 0; i < size; i++) {
            if (parseInt == this.mAdapter.getData().get(i).mainValue) {
                this.selectPosition = i;
                this.mAdapter.getData().get(this.selectPosition).setSubValue(LightUtils.brt2ProgressHasBelowZero(Integer.parseInt(msg.getResData().substring(12, 14), 16)));
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public SelectKValueDialog controlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    public SelectKValueDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public SelectKValueDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_power_state_selector";
    }
}